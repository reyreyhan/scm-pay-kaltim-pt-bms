package com.bm.main.scm.feature.kulakan.main

import android.content.Context
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.bm.main.scm.R
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.callback.PermissionCallback
import com.bm.main.scm.models.cart.Cart
import com.bm.main.scm.models.product.Product
import com.bm.main.scm.models.product.ProductRestModel
import com.bm.main.scm.models.supplier.Supplier
import com.bm.main.scm.models.transaction.Order
import com.bm.main.scm.models.transaction.RequestKulakan
import com.bm.main.scm.models.transaction.TransactionRestModel
import com.bm.main.scm.utils.Helper
import com.bm.main.scm.utils.PermissionUtil

class KulakanPresenter(val context: Context, val view: KulakanContract.View) : BasePresenter<KulakanContract.View>(),
    KulakanContract.Presenter, KulakanContract.InteractorOutput {

    private var interactor = KulakanInteractor(this)
    private var productRestModel = ProductRestModel(context)
    private var transactionRestModel = TransactionRestModel(context)

    private var permissionUtil: PermissionUtil = PermissionUtil(context)
    private lateinit var cameraPermission: PermissionCallback
    private var carts:HashMap<String,Cart> = HashMap()
    private var tempBarcode:String?=null
    private var supplier:Supplier?=null
    private var date:CalendarDay?=null

    override fun onViewCreated() {
        cameraPermission = object : PermissionCallback{
            override fun onSuccess() {
                view.openScanPage()
            }

            override fun onFailed() {
                view.showMessage(999,context.getString(R.string.reason_permission_camera))
            }
        }
        carts = HashMap()
        countCart()
    }

    override fun addCart(data: Product) {
        data.let {
            val stok = it.stok!!.toDouble()
            if(carts.containsKey(it.id_barang)){
                val cart = carts[it.id_barang]
                val count = cart?.count!!.plus(1)
                cart.count = count
                view.addCart(cart)
            }
            else{
                val cart = Cart()
                cart.product = it
                cart.count = 1.0
                carts.put(it.id_barang!!,cart)
                view.addCart(cart)
            }
            countCart()
        }
    }

    override fun checkCart(data: Product) {
        if(data.posisi == true){
            addCart(data)
        }
        else{
            view.openEditManual(data)
        }
    }

    override fun increaseCart(data: Cart,position:Int) {
        val id = data.product!!.id_barang
        if(!carts.containsKey(id)){
            return
        }
        val cart = carts[id]!!
        val stok = cart.product!!.stok!!.toDouble()
        val count = cart.count!!.plus(1)
        cart.count = count
        carts[id!!] = cart
        view.updateCart(cart,position)
        countCart()
    }

    override fun decreaseCart(data: Cart,position:Int) {
        val id = data.product!!.id_barang
        if(!carts.containsKey(id)){
            return
        }
        val cart = carts[id]!!
        val stok = cart.product!!.stok!!.toDouble()
        val count = cart.count!!.minus(1)
        if(count < 0){
            return
        }
        if(count == 0.0){
            deleteCart(data,position)
            return
        }
        cart.count = count
        carts[id!!] = cart
        view.updateCart(cart,position)
        countCart()
    }

    override fun updateCart(data: Cart,position:Int) {
        val id = data.product!!.id_barang
        if(!carts.containsKey(id)){
            return
        }
        carts[id!!] = data
        carts[id!!]?.let { view.updateCart(it,position) }
        countCart()
    }

    override fun deleteCart(data: Cart,position:Int) {
        carts.remove(data.product!!.id_barang)
        view.deleteCart(position)
        countCart()
    }

    override fun countCart() {
        if(carts.size == 0){
            view.setCartText("Rp 0")
            view.showErrorView("Silakan cek data kulakan di laporan history kulakan")
            return
        }

        var total = 0.0
        var count = 0.0

        for(cart:Cart in carts.values){
            count += cart.count!!
            val sell = cart.product!!.hargabeli!!.toDouble()
            val subtotal = cart.count!!*sell
            total += subtotal
        }

        if(count > 99){
            view.setCartText("Rp ${Helper.convertToCurrency(total)}")
        }
        else{
            view.setCartText("Rp ${Helper.convertToCurrency(total)}")
        }
        view.showContentView()
    }

    override fun onCheckScan() {
        permissionUtil.checkCameraPermission(cameraPermission)
    }

    override fun searchByBarcode(search:String) {
        tempBarcode = search
        interactor.callSearchByBarcodeAPI(context,productRestModel,search)
    }

    override fun onSuccessByBarcode(list: List<Product>) {
        view.hideLoadingDialog()
        if(list.isNotEmpty()){
            val product = list[0]
            checkCart(product)
        }
        else{
            view.openAddManual(tempBarcode!!)
            tempBarcode = ""
        }


    }

    override fun onFailedBarcode(code: Int, msg: String) {
        if("tidak ada data" == msg){
            view.hideLoadingDialog()
            view.openAddManual(tempBarcode!!)
            tempBarcode = ""
        }
        else{
            onFailedAPI(code,msg)
        }
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.hideLoadingDialog()
        view.showMessage(code,msg)
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun updateSupplier(data: Supplier?) {
        supplier = data
        view.setSupplierName(data)
    }

    override fun setSelectedDate(date: CalendarDay?) {
        this.date = date
    }

    override fun getSelectedDate(): CalendarDay? {
        return date
    }

    override fun checkTunai() {
        if(supplier == null){
            view.showMessage(999,"Data supplier belum diisi")
            return
        }

        val total = view.getTotalValue()
        val req = RequestKulakan()
        req.tipe_pembayaran = 1
        req.total_order = total.toInt()
        req.id_supplier = supplier?.id_supplier
        req.barang = getBarang()

        interactor.callOrderAPI(context,transactionRestModel,req)

    }

    override fun checkPiutang() {
        if(supplier == null){
            view.showMessage(999,"Data supplier belum diisi")
            return
        }
        if(date == null){
            view.showMessage(999,"Jatuh tempo belum diisi")
            return
        }
        val total = view.getTotalValue()
        val req = RequestKulakan()
        req.tipe_pembayaran = 3
        req.total_order = total.toInt()
        req.id_supplier = supplier?.id_supplier
        req.jatuh_tempo = date?.date.toString()
        req.barang = getBarang()

        interactor.callOrderAPI(context,transactionRestModel,req)
    }

    private fun getBarang():List<RequestKulakan.Barang>{
        val list = ArrayList<RequestKulakan.Barang>()
        if(carts.size == 0){
            return list
        }
        for(cart in carts.values){
            val barang = RequestKulakan.Barang()
            barang.id_barang = cart.product?.id_barang
            barang.jumlah_barang = cart.count?.toInt()
            list.add(barang)
        }
        return list
    }

    override fun onSuccessOrder(order: Order) {
        view.hideLoadingDialog()
        if(order.invoice == null){
            view.showMessage(999,"Nomor invoice tidak ditemukan")
            return
        }
        view.openSuccessPage(order.invoice!!)
    }

    override fun getCartsSize():Int {
        return carts.size
    }

}
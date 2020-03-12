package com.bm.main.pos.feature.sell.main

import android.content.Context
import android.util.Log
import android.view.View
import com.bm.main.fpl.activities.BaseActivity
import com.bm.main.fpl.constants.EventParam
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.bm.main.pos.R
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.callback.PermissionCallback
import com.bm.main.pos.models.DialogModel
import com.bm.main.pos.models.Message
import com.bm.main.pos.models.cart.Cart
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.models.product.Product
import com.bm.main.pos.models.product.ProductRestModel
import com.bm.main.pos.models.transaction.Order
import com.bm.main.pos.models.transaction.RequestTransaction
import com.bm.main.pos.models.transaction.TransactionRestModel
import com.bm.main.pos.utils.Helper
import com.bm.main.pos.utils.PermissionUtil
import com.google.firebase.analytics.FirebaseAnalytics

class SellPresenter(val context: Context, val view: SellContract.View) : BasePresenter<SellContract.View>(),
    SellContract.Presenter, SellContract.InteractorOutput {

    private var interactor = SellInteractor(this)
    private var productRestModel = ProductRestModel(context)
    private var transactionRestModel = TransactionRestModel(context)

    private var permissionUtil: PermissionUtil = PermissionUtil(context)
    private lateinit var cameraPermission: PermissionCallback
    private var carts:HashMap<String,Cart> = HashMap()
    private var tempBarcode:String?=null
    private var customer:Customer?=null
    private var date:CalendarDay?=null

    override fun onViewCreated() {
        (context as BaseActivity).logEventFireBase(
            "Penjualan",
            "Penjualan Profit",
            EventParam.EVENT_ACTION_VISIT,
            EventParam.EVENT_SUCCESS,
            SellFragment::class.java.getSimpleName())
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
                if(count <= stok){
                    cart.count = count
                    view.addCart(cart)
                }
                else{
                    view.showMessage(999,"Stok max ${Helper.convertToCurrency(stok)}")
                }

            }
            else{
                if(stok > 0){
                    val cart = Cart()
                    cart.product = it
                    cart.count = 1.0
                    carts.put(it.id_barang!!,cart)
                    view.addCart(cart)
                }
                else{
                    view.showMessage(999,"Stok produk kosong")
                }

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
        if(count > stok){
            return
        }
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
            view.setCartText("0","Rp 0")
            view.showErrorView("Keranjang belanja kosong")
            return
        }

        var total = 0.0
        var count = 0.0

        for(cart:Cart in carts.values){
            count += cart.count!!
            val sell = cart.product!!.hargajual!!.toDouble()
            val subtotal = cart.count!!*sell
            total += subtotal
        }

        if(count > 99){
            view.setCartText(">99","Rp ${Helper.convertToCurrency(total)}")
        }
        else{
            view.setCartText(Helper.convertToCurrency(count),"Rp ${Helper.convertToCurrency(total)}")
        }
        view.showContentView()
        countCashback()
    }

    override fun countCashback() {
        val pay = view.getPayValue()
        val total = view.getTotalValue()
        if(pay == 0.0 || total == 0.0){
            view.hideShowCashback(View.GONE)
            //view.enableBtnBuy(false)
            return
        }
        val cashback = total - pay
        view.setCashback(cashback)

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

    override fun updateCustomer(data: Customer?) {
        customer = data
        view.setCustomerName(data)
    }

    override fun setSelectedDate(date: CalendarDay?) {
        this.date = date
    }

    override fun getSelectedDate(): CalendarDay? {
        return date
    }

    override fun checkTunai() {
        var pay = view.getPayValue()

        if(pay == 0.0){
//            view.showMessage(999,"Uang yang diterima tidak boleh kosong")
//            return
            pay = view.getTotalValue()
        }
        val total = view.getTotalValue()
        val cashback = total - pay
        if(cashback > 0){
            view.showMessage(999,"Kurang bayar Rp ${Helper.convertToCurrency(cashback)}")
            return
        }

        val req = RequestTransaction()
        req.tipe_pembayaran = 1
        req.jumlah_pembayaran = pay.toInt()
        req.total_order = total.toInt()
        req.barang = getBarang()
        (context as BaseActivity).logEventFireBase(
            "Penjualan",
            "Tunai",
            EventParam.EVENT_ACTION_SELL_PRODUCT,

            EventParam.EVENT_ACTION_SELL_PRODUCT,
            SellFragment::class.java!!.getSimpleName())
        interactor.callOrderAPI(context,transactionRestModel,req)

    }

    override fun checkPiutang() {
        if(customer == null){
            view.showMessage(999,"Data pelanggan belum diisi")
            return
        }
        if(date == null){
            view.showMessage(999,"Jatuh tempo belum diisi")
            return
        }
        val total = view.getTotalValue()
        val req = RequestTransaction()
        req.tipe_pembayaran = 3
        req.total_order = total.toInt()
        req.id_pelanggan = customer?.id_pelanggan
        req.jatuh_tempo = date?.date.toString()
        req.barang = getBarang()
        (context as BaseActivity).logEventFireBase(
            "Penjualan",
            "Hutang",
            EventParam.EVENT_ACTION_SELL_PRODUCT,

            EventParam.EVENT_ACTION_SELL_PRODUCT,
            SellFragment::class.java!!.getSimpleName())
        interactor.callOrderAPI(context,transactionRestModel,req)
    }

    private fun getBarang():List<RequestTransaction.Barang>{
        val list = ArrayList<RequestTransaction.Barang>()
        if(carts.size == 0){
            return list
        }
        for(cart in carts.values){
            val barang = RequestTransaction.Barang()
            barang.id_barang = cart.product?.id_barang
            barang.jumlah_barang = cart.count?.toInt()
            barang.catatan = cart.note.toString()
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
package com.bm.main.pos.feature.sell.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseFragment
import com.bm.main.pos.feature.scan.ScanCodeActivity
import com.bm.main.pos.feature.scan.ScanCodeFragment
import com.bm.main.pos.feature.sell.add.AddActivity
import com.bm.main.pos.feature.sell.addCustomer.AddCustomerActivity
import com.bm.main.pos.feature.sell.chooseCustomer.ChooseCustomerActivity
import com.bm.main.pos.feature.sell.chooseProduct.ChooseProductFragment
import com.bm.main.pos.feature.sell.edit.EditActivity
import com.bm.main.pos.feature.transaction.success.SuccessActivity
import com.bm.main.pos.models.cart.Cart
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.models.product.Product
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.ui.OnFragmentBackPressed
import com.bm.main.pos.ui.PaymentNumberTextWatcher
import com.bm.main.pos.ui.ext.toast
import com.bm.main.pos.utils.AppConstant
import com.bm.main.pos.utils.Helper
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.fragment_penjualan.view.*
import kotlinx.android.synthetic.main.layout_bayar_hutang.view.*
import kotlinx.android.synthetic.main.layout_bayar_non_tunai.view.*
import kotlinx.android.synthetic.main.layout_bayar_tunai.view.*
import org.threeten.bp.LocalDate

class SellFragment : BaseFragment<SellPresenter, SellContract.View>(),
    SellContract.View, OnFragmentBackPressed {

    private val CODE_OPEN_SCAN = 1001
    private val CODE_OPEN_CHOOSE_PRODUCT = 1002
    private val CODE_OPEN_ADD_MANUAL = 1003
    private val CODE_OPEN_EDIT_MANUAL = 1004
    private val CODE_OPEN_CHOOSE_CUSTOMER = 1005
    private val CODE_OPEN_ADD_CUSTOMER = 1006

    private var showDate: ShowDate? = null

    private lateinit var _view: View
    private val adapter = SellAdapter()
    private var payType = 1
    private var ft: FragmentTransaction? = null
    private val chooseProductFragment = ChooseProductFragment.newInstance()
    private val scanCodeFragment = ScanCodeFragment.newInstance()

    companion object {

        @JvmStatic
        fun newInstance() =
            SellFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun createPresenter(): SellPresenter {
        return SellPresenter(activity as Context, this)
    }

    override fun onCreateLayout(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_penjualan, container, false)
    }


    override fun initAction(view: View) {
        _view = view
        renderView()
        getPresenter()?.onViewCreated()
        checkCarts()
    }

    private fun renderView() {
        val layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        _view.rv_list.layoutManager = layoutManager
        _view.rv_list.adapter = adapter
        adapter.callback = object : SellAdapter.ItemClickCallback {
            override fun onCountDialog(data: Cart, position: Int) {
                val dialog = ProductDialog.newInstance().apply {
                    arguments = Bundle().apply {
                        putSerializable(AppConstant.DATA, data)
                        putInt("CartPosition", position)
                    }
                }
                dialog.setTargetFragment(this@SellFragment, 101)
                dialog.show(fragmentManager!!, ProductDialog.TAG)
            }

            override fun onNote(data: Cart, position: Int) {
                showDate?.openNoteDialog(data, position)
            }

            override fun onDecrease(data: Cart, position: Int) {
                getPresenter()?.decreaseCart(data, position)
            }

            override fun onIncrease(data: Cart, position: Int) {
                getPresenter()?.increaseCart(data, position)
            }

            override fun onDelete(data: Cart, position: Int) {
                getPresenter()?.deleteCart(data, position)

            }
        }

        _view.btn_scanner.setOnClickListener {
            getPresenter()?.onCheckScan()
        }

        _view.btn_search.setOnClickListener {
            openChooseProduct()
        }

        _view.et_pay.addTextChangedListener(PaymentNumberTextWatcher(_view.et_pay, getPresenter()!!))

        _view.btn_tunai.isSelected = true
        _view.layout_bayar_tunai.visibility = View.VISIBLE
        _view.layout_bayar_hutang.visibility = View.GONE

        _view.btn_tunai.setOnClickListener{
            payType = 1
            _view.btn_tunai.isSelected = true
            _view.btn_non_tunai.isSelected = false
            _view.btn_hutang.isSelected = false
            showTunaiView()
        }

        _view.btn_non_tunai.setOnClickListener {
            payType = 2
            _view.btn_tunai.isSelected = false
            _view.btn_non_tunai.isSelected = true
            _view.btn_hutang.isSelected = false
            showNonTunaiView()
        }

        _view.btn_hutang.setOnClickListener {
            payType = 3
            _view.btn_tunai.isSelected = false
            _view.btn_non_tunai.isSelected = false
            _view.btn_hutang.isSelected = true
            showPiutangView()
        }

//        _view.rg_payment.setOnCheckedChangeListener { _, p1 ->
//            when (p1) {
//                R.id.rb_tunai    -> showTunaiView()
//                R.id.rb_nontunai -> showNonTunaiView()
//                R.id.rb_piutang  -> showPiutangView()
//            }
//        }
//        _view.rg_payment.check(R.id.rb_tunai)

        _view.et_data_pelanggan.setOnClickListener {
            openChooseCustomer()
        }


        _view.btn_bayar.setOnClickListener {
            showLoadingDialog()
            when (payType) {
                1 -> showConfirmPayTunaiDialog(Helper.convertToCurrency(getPayValue()) , getPresenter()!!.calculateCashBack(), getPresenter()!!.countAllBarang())
                2 -> {
                    hideLoadingDialog()
                    showToast("Fitur belum tersedia")
                }
                3 -> getPresenter()?.checkPiutang()
            }
        }
    }

    override fun checkCarts() {
        if (getPresenter()?.getCartsSize() == 0) {
            openChooseProduct()
        }
    }

    override fun showContentView() {
        _view.rv_list.visibility = View.VISIBLE
        _view.bottom_menu.visibility = View.VISIBLE
        _view.ll_error.visibility = View.GONE
    }

    override fun hideContentView() {
        _view.rv_list.visibility = View.GONE
        _view.bottom_menu.visibility = View.GONE
        _view.ll_error.visibility = View.GONE
    }

    override fun showErrorView(err: String) {
        _view.bottom_menu.visibility = View.GONE
        _view.rv_list.visibility = View.GONE
        _view.ll_error.visibility = View.VISIBLE
        _view.tv_error.text = err
    }

    override fun setCartText(count: String, nominal: String) {
        _view.tv_number.text = count
        _view.tv_total_harga.text = "Rp $nominal"
        //_view.et_pay.setText(nominal)
    }

    override fun addCart(data: Cart) {
        adapter.addItem(data)
    }

    override fun showMessage(code: Int, msg: String?) {
        hideLoadingDialog()
        if (code == RestException.CODE_USER_NOT_FOUND) {
            restartLoginActivity()
        } else {
            msg?.let {
                toast(it)
            }
        }
    }

    override fun openScanPage() {
//        val intent = Intent(activity, ScanCodeActivity::class.java)
//        intent.putExtra(AppConstant.SCAN.TYPE, AppConstant.SCAN.SELL)
//        startActivityForResult(intent, CODE_OPEN_SCAN)
        hideContentView()
        showContainerFragment(CODE_OPEN_SCAN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODE_OPEN_SCAN && resultCode == Activity.RESULT_OK) {
            val type = data?.getIntExtra(AppConstant.SCAN.TYPE, -1)
            if (type == AppConstant.SCAN.SELL_ADD) {
                openAddManual("")
            } else if (type == AppConstant.SCAN.SELL_SEARCH) {
                openChooseProduct()
            } else {
                val code = data?.getStringExtra(AppConstant.DATA)
                if (code.isNullOrBlank() || code.isNullOrEmpty()) {
                    openAddManual("")
                } else {
                    showLoadingDialog()
                    getPresenter()?.searchByBarcode(code)
                }
            }

        } else if (requestCode == CODE_OPEN_CHOOSE_PRODUCT && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                toast("Data tidak ditemukan")
                return
            }
            if (data.getSerializableExtra(AppConstant.DATA) == null) {
                toast("Data tidak ditemukan")
                return
            }
            val product = data.getSerializableExtra(AppConstant.DATA) as Product
            if (product.id_barang == null) {
                toast("Data tidak ditemukan")
            } else {
                getPresenter()?.checkCart(product)
            }
        } else if (requestCode == CODE_OPEN_ADD_MANUAL && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                toast("Data tidak ditemukan")
                return
            }
            if (data.getSerializableExtra(AppConstant.DATA) == null) {
                toast("Data tidak ditemukan")
                return
            }
            val product = data.getSerializableExtra(AppConstant.DATA) as Product
            if (product.id_barang == null) {
                toast("Data tidak ditemukan")
            } else {
                getPresenter()?.addCart(product)
            }
        } else if (requestCode == CODE_OPEN_EDIT_MANUAL && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                toast("Data tidak ditemukan")
                return
            }
            if (data.getSerializableExtra(AppConstant.DATA) == null) {
                toast("Data tidak ditemukan")
                return
            }
            val product = data.getSerializableExtra(AppConstant.DATA) as Product
            if (product.id_barang == null) {
                toast("Data tidak ditemukan")
            } else {
                getPresenter()?.addCart(product)
            }
        } else if (requestCode == CODE_OPEN_CHOOSE_CUSTOMER && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                toast("Data tidak ditemukan")
                return
            }
            if (data.getSerializableExtra(AppConstant.DATA) == null) {
                toast("Data tidak ditemukan")
                return
            }
            val customer = data.getSerializableExtra(AppConstant.DATA) as Customer
            if (customer.id_pelanggan == null) {
                toast("Data tidak ditemukan")
            } else {
                getPresenter()?.updateCustomer(customer)
            }
        } else if (requestCode == 101 && resultCode == 1001){
            val cart = data?.getSerializableExtra(AppConstant.DATA) as Cart
            val pos = data.getIntExtra("CartPosition", 0)
            getPresenter()?.updateCart(cart, pos)
        }
        else if (requestCode == 101 && resultCode == 1002){
            val cart = data?.getSerializableExtra(AppConstant.DATA) as Cart
            val pos = data.getIntExtra("CartPosition", 0)
            getPresenter()?.deleteCart(cart, pos)
        }
        else if (requestCode == 102 && resultCode == Activity.RESULT_OK){
            getPresenter()?.checkTunai()
        }
        else if (requestCode == 102 && resultCode == Activity.RESULT_CANCELED){
            hideLoadingDialog()
        }
    }

    override fun onDetach() {
        super.onDetach()
        showDate = null
        getPresenter()?.onDestroy()
    }

    override fun openChooseProduct() {
        hideContentView()
        showContainerFragment(CODE_OPEN_CHOOSE_PRODUCT)
    }

    override fun openAddManual(barcode: String) {
        val intent = Intent(activity, AddActivity::class.java)
        intent.putExtra(AppConstant.DATA, barcode)
        startActivityForResult(intent, CODE_OPEN_ADD_MANUAL)
    }

    override fun openEditManual(product: Product) {
        val intent = Intent(activity, EditActivity::class.java)
        intent.putExtra(AppConstant.DATA, product)
        startActivityForResult(intent, CODE_OPEN_EDIT_MANUAL)
    }

    override fun getTotalValue(): Double {
        val value = _view.tv_total_harga.text.toString().replace("Rp ", "").replace(".", "")
        if (value.isBlank() || value.isEmpty()) {
            return 0.0
        }
        return value.toDouble()
    }

    override fun getPayValue(): Double {
        val value = _view.et_pay.text.toString().replace(Regex("\\D"), "").replace(".", "")
        //val value = _view.et_pay.text.toString()
        if (value.isBlank() || value.isEmpty()) {
            return 0.0
        }
        return value.toDouble()
    }

    @SuppressLint("SetTextI18n")
    override fun setCashback(value: Double) {
        enableBtnBuy(true)
        when {
            value == 0.0 -> {
                hideShowCashback(View.INVISIBLE)
            }
            value < 0.0  -> {
                val ret = -1 * value
                hideShowCashback(View.VISIBLE)
                _view.tv_kembalian.text = "Kembalian Rp ${Helper.convertToCurrency(ret)}"
                _view.tv_kembalian.setTextColor(ContextCompat.getColor(activity!!, R.color.colorAccent))
            }
            else -> {
                hideShowCashback(View.VISIBLE)
                _view.tv_kembalian.text = "Kurang bayar Rp ${Helper.convertToCurrency(value)}"
                _view.tv_kembalian.setTextColor(ContextCompat.getColor(activity!!, R.color.vermillion))
            }
        }
    }

    override fun hideShowCashback(value: Int) {
        _view.tv_kembalian.visibility = value
    }

    override fun enableBtnBuy(isEnable: Boolean) {
        _view.btn_bayar.isEnabled = isEnable
    }

    override fun updateCart(cart: Cart, position: Int) {
        adapter.updateItem(cart, position)
    }

    override fun deleteCart(position: Int) {
        adapter.deleteItem(position)
    }

    override fun showTunaiView() {
        _view.layout_bayar_hutang.visibility = View.GONE
        _view.layout_bayar_non_tunai.visibility = View.GONE
        _view.layout_bayar_tunai.visibility = View.VISIBLE
    }

    override fun showConfirmPayTunaiDialog(jumlah:String, cashback:String, jumlahBarang:Int) {
        val dialog = ConfirmPayDialog.newInstance().apply {
            arguments = Bundle().apply {
                putString("JumlahPembayaran", "Rp $jumlah")
                putString("Cashback", cashback)
                putString("JumlahBarang", "$jumlahBarang Barang")
            }
        }
        dialog.setTargetFragment(this@SellFragment, 102)
        dialog.show(fragmentManager!!, ConfirmPayDialog.TAG)
    }

    override fun showNonTunaiView() {
        _view.layout_bayar_hutang.visibility = View.GONE
        _view.layout_bayar_non_tunai.visibility = View.VISIBLE
        _view.layout_bayar_tunai.visibility = View.GONE
    }

    override fun showPiutangView() {
        _view.layout_bayar_hutang.visibility = View.VISIBLE
        _view.layout_bayar_non_tunai.visibility = View.GONE
        _view.layout_bayar_tunai.visibility = View.GONE
    }

    override fun setCustomerName(data: Customer?) {
        data?.let{
            _view.et_data_pelanggan.text = it.nama_pelanggan
            enableBtnBuy(true)
        }
    }

    override fun openChooseCustomer() {
        val intent = Intent(activity, ChooseCustomerActivity::class.java)
        startActivityForResult(intent, CODE_OPEN_CHOOSE_CUSTOMER)
    }

    override fun openAddCustomer() {
        val intent = Intent(activity, AddCustomerActivity::class.java)
        startActivityForResult(intent, CODE_OPEN_ADD_CUSTOMER)
    }

    override fun openSuccessPage(id: String) {
        val intent = Intent(activity, SuccessActivity::class.java)
        intent.putExtra(AppConstant.DATA, id)
        startActivity(intent)
    }


    interface ShowDate {
        fun openSingleDatePickerDialog(selected: CalendarDay?, minDate: LocalDate?, maxDate: LocalDate?, type: Int)
        fun openNoteDialog(selected: Cart, pos: Int)
        fun openCountDialog(selected: Cart, pos: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ShowDate) {
            showDate = context
        } else {
            throw RuntimeException("$context must implement ShowDate")
        }
    }

    override fun setSelectedDate(date: CalendarDay?) {
        getPresenter()?.setSelectedDate(date)
    }

    override fun onNoteSaved(selected: Cart, pos: Int) {
        getPresenter()?.updateCart(selected, pos)
    }

    override fun onCountSaved(selected: Cart, pos: Int) {
        getPresenter()?.updateCart(selected, pos)
    }

    fun hideContainerFragment(){
        _view.container_fragment.visibility = View.GONE
        /*if (!scanCodeFragment.isHidden){
            ft!!.hide(scanCodeFragment)
        }else if(!chooseProductFragment.isHidden){
            ft!!.hide(chooseProductFragment)
        }*/
    }

    private fun showContainerFragment(code: Int){
        _view.container_fragment.visibility = View.VISIBLE
        ft = fragmentManager?.beginTransaction()
        if (code == CODE_OPEN_SCAN) {
//            if (scanCodeFragment.isAdded){
//                ft!!.show(scanCodeFragment)
//            }else{
//                ft!!.add(R.id.container_fragment, scanCodeFragment)
//            }
//            ft!!.commit()
//            hideContainerFragment()
            //                hideFragment(ft!!, chooseProductFragment)
            ft!!.replace(R.id.container_fragment,scanCodeFragment)
            ft!!.commit()
        }
        else if (code == CODE_OPEN_CHOOSE_PRODUCT) {
            //hideContainerFragment()
//            if (chooseProductFragment.isAdded){
//                ft!!.show(chooseProductFragment)
//            }else{
//                ft!!.add(R.id.container_fragment, chooseProductFragment)
//            }
//            ft!!.commit()
//            hideContainerFragment()
            //                hideFragment(ft!!, scanCodeFragment)
            ft!!.replace(R.id.container_fragment, chooseProductFragment)
            ft!!.commit()
        }
    }

    override fun onFragmentBackPressed() {
        //hideContainerFragment()
    }

    private fun hideFragment(ft: FragmentTransaction, fragment: Fragment) {
        if (fragment.isAdded) {
            ft.hide(fragment)
        }
    }

}
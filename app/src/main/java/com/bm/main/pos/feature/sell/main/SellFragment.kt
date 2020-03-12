package com.bm.main.pos.feature.sell.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.bm.main.pos.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_sell.view.*
import com.bm.main.pos.R
import com.bm.main.pos.models.cart.Cart
import com.bm.main.pos.feature.scan.ScanCodeActivity
import com.bm.main.pos.ui.ext.toast
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.utils.AppConstant
import com.bm.main.pos.feature.sell.chooseProduct.ChooseProductActivity
import com.bm.main.pos.feature.sell.chooseCustomer.ChooseCustomerActivity
import com.bm.main.pos.feature.sell.addCustomer.AddCustomerActivity
import com.bm.main.pos.feature.transaction.success.SuccessActivity
import com.bm.main.pos.feature.sell.add.AddActivity
import com.bm.main.pos.feature.sell.edit.EditActivity
import com.bm.main.pos.models.customer.Customer
import com.bm.main.pos.models.product.Product
import com.bm.main.pos.ui.PaymentNumberTextWatcher
import com.bm.main.pos.utils.Helper
import kotlinx.android.synthetic.main.fragment_sell.*
import org.threeten.bp.LocalDate

class SellFragment : BaseFragment<SellPresenter, SellContract.View>(),
    SellContract.View {

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
        return inflater.inflate(R.layout.fragment_sell, container, false)
    }


    override fun initAction(view: View) {
        _view = view
        renderView()
        getPresenter()?.onViewCreated()
        checkCarts()
    }

    private val ic_check by lazy { ResourcesCompat.getDrawable(resources, R.drawable.circle_thick_gray, null) }
    private val ic_checked by lazy { ResourcesCompat.getDrawable(resources, R.drawable.ic_checked_circle, null) }

    private fun renderView() {
        val layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        _view.rv_list.layoutManager = layoutManager
        _view.rv_list.adapter = adapter

        adapter.callback = object : SellAdapter.ItemClickCallback {
            override fun onCountDialog(data: Cart, position: Int) {
                showDate?.openCountDialog(data, position)
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

        _view.ll_search.setOnClickListener {
            openChooseProduct()
        }

        _view.btn_manual.setOnClickListener {
            openAddManual("")
        }

        _view.et_pay.addTextChangedListener(PaymentNumberTextWatcher(_view.et_pay, getPresenter()!!))

        _view.rb_tunai.setOnClickListener {
            payType = 1
            _view.ic_check_tunai.setImageDrawable(ic_checked)
            _view.ic_check_nontunai.setImageDrawable(ic_check)
            _view.ic_check_piutang.setImageDrawable(ic_check)
            showTunaiView()
        }
        _view.rb_tunai.performClick()

        _view.rb_nontunai.setOnClickListener {
            payType = 2
            _view.ic_check_tunai.setImageDrawable(ic_check)
            _view.ic_check_nontunai.setImageDrawable(ic_checked)
            _view.ic_check_piutang.setImageDrawable(ic_check)
            showNonTunaiView()
        }

        _view.rb_piutang.setOnClickListener {
            payType = 3
            _view.ic_check_tunai.setImageDrawable(ic_check)
            _view.ic_check_nontunai.setImageDrawable(ic_check)
            _view.ic_check_piutang.setImageDrawable(ic_checked)
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

        _view.et_customer.setOnClickListener {
            openChooseCustomer()
        }

        _view.btn_add_customer.setOnClickListener {
            openAddCustomer()
        }

        _view.btn_delete_customer.setOnClickListener {
            getPresenter()?.updateCustomer(null)
        }

        _view.btn_date.setOnClickListener {
            val now = LocalDate.now()
            showDate?.openSingleDatePickerDialog(getPresenter()?.getSelectedDate(), now, null, AppConstant.Code.CODE_FILTER_DATE_SELL)
        }

        _view.btn_bayar.setOnClickListener {
            showLoadingDialog()
//            when (_view.rg_payment.checkedRadioButtonId) {
            when (payType) {
                1 -> getPresenter()?.checkTunai()
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
        _view.ll_content.visibility = View.VISIBLE
        _view.ll_error.visibility = View.GONE
    }

    override fun showErrorView(err: String) {
        _view.ll_content.visibility = View.GONE
        _view.ll_error.visibility = View.VISIBLE
        _view.tv_error.text = err
    }

    override fun setCartText(count: String, nominal: String) {
        _view.tv_number.text = count
        _view.tv_total.text = nominal
        _view.et_pay.setText(nominal)
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
        val intent = Intent(activity, ScanCodeActivity::class.java)
        intent.putExtra(AppConstant.SCAN.TYPE, AppConstant.SCAN.SELL)
        startActivityForResult(intent, CODE_OPEN_SCAN)
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
        } else if (requestCode == CODE_OPEN_ADD_CUSTOMER && resultCode == Activity.RESULT_OK) {
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
        }
    }

    override fun onDetach() {
        super.onDetach()
        showDate = null
        getPresenter()?.onDestroy()
    }

    override fun openChooseProduct() {
        val intent = Intent(activity, ChooseProductActivity::class.java)
        startActivityForResult(intent, CODE_OPEN_CHOOSE_PRODUCT)
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
        val value = _view.tv_total.text.toString().replace("Rp", "").replace(".", "")
        if (value.isBlank() || value.isEmpty()) {
            return 0.0
        }
        return value.toDouble()

    }

    override fun getPayValue(): Double {
        val value = _view.et_pay.text.toString().replace(Regex("\\D"), "").replace(".", "")
        if (value.isBlank() || value.isEmpty()) {
            return 0.0
        }
        return value.toDouble()
    }

    @SuppressLint("SetTextI18n")
    override fun setCashback(value: Double) {
        when {
            value == 0.0 -> {
                hideShowCashback(View.GONE)
                //enableBtnBuy(true)
            }
            value < 0.0  -> {
                val ret = -1 * value
                hideShowCashback(View.VISIBLE)
                //enableBtnBuy(true)
                _view.tv_kembalian.text = "Kembalian Rp ${Helper.convertToCurrency(ret)}"
                _view.tv_kembalian.setTextColor(ContextCompat.getColor(activity!!, R.color.colorAccent))
            }
            else         -> {
                hideShowCashback(View.VISIBLE)
                //enableBtnBuy(false)
                _view.tv_kembalian.text = "Kurang bayar Rp ${Helper.convertToCurrency(value)}"
                _view.tv_kembalian.setTextColor(ContextCompat.getColor(activity!!, R.color.vermillion))
            }
        }
    }

    override fun hideShowCashback(value: Int) {
        _view.tv_kembalian.visibility = value
    }

    override fun enableBtnBuy(isEnable: Boolean) {
//        when(_view.rg_payment.checkedRadioButtonId){
//            R.id.rb_tunai -> _view.btn_bayar.isEnabled = isEnable
//            R.id.rb_nontunai -> _view.btn_bayar.isEnabled = true
//            R.id.rb_piutang -> _view.btn_bayar.isEnabled = true
//        }
        _view.btn_bayar.isEnabled = isEnable
        if (isEnable) {
            _view.btn_bayar.backgroundTintList = ContextCompat.getColorStateList(activity!!, R.color.orange)
        } else {
            _view.btn_bayar.backgroundTintList = ContextCompat.getColorStateList(activity!!, R.color.divider)
        }
    }

    override fun updateCart(cart: Cart, position: Int) {
        adapter.updateItem(cart, position)
    }

    override fun deleteCart(position: Int) {
        adapter.deleteItem(position)
    }

    override fun showTunaiView() {
        _view.ll_tunai.visibility = View.VISIBLE
        _view.ll_hutang.visibility = View.GONE
        _view.tv_pay.visibility = View.VISIBLE
        _view.et_pay.visibility = View.VISIBLE
        getPresenter()?.countCashback()
    }

    override fun showNonTunaiView() {
        _view.ll_tunai.visibility = View.VISIBLE
        _view.ll_hutang.visibility = View.GONE
        _view.et_pay.visibility = View.GONE
        _view.tv_pay.visibility = View.GONE
        _view.tv_kembalian.visibility = View.GONE
        //enableBtnBuy(true)
    }

    override fun showPiutangView() {
        _view.ll_tunai.visibility = View.GONE
        _view.ll_hutang.visibility = View.VISIBLE
        //enableBtnBuy(true)
    }

    override fun setCustomerName(data: Customer?) {
        _view.et_customer.text = ""
        _view.btn_delete_customer.visibility = View.GONE
        data?.let {
            _view.et_customer.text = it.nama_pelanggan
            _view.btn_delete_customer.visibility = View.VISIBLE
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
        if (date == null) {
            et_date.text = ""
        } else {
            et_date.text = Helper.getDateFormat(activity!!, date.date.toString(), "yyyy-MM-dd", "dd MMMM yyyy")
        }
    }

    override fun onNoteSaved(selected: Cart, pos: Int) {
        getPresenter()?.updateCart(selected, pos)
    }

    override fun onCountSaved(selected: Cart, pos: Int) {
        getPresenter()?.updateCart(selected, pos)
    }

}

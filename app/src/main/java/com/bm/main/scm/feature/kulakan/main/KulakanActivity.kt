package com.bm.main.scm.feature.kulakan.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.bm.main.scm.feature.dialog.CartCountDialog
import com.bm.main.scm.feature.dialog.SingleDateDialog
import com.bm.main.scm.feature.kulakan.chooseSupplier.ChooseSupplierActivity
import com.bm.main.scm.feature.report.kulakan.KulakanActivity
import com.bm.main.scm.feature.scan.ScanCodeActivity
import com.bm.main.scm.feature.sell.chooseProduct.ChooseProductActivity
import com.bm.main.scm.feature.sell.edit.EditActivity
import com.bm.main.scm.models.cart.Cart
import com.bm.main.scm.models.product.Product
import com.bm.main.scm.models.supplier.Supplier
import com.bm.main.scm.rest.entity.RestException
import com.bm.main.scm.ui.ext.toast
import com.bm.main.scm.utils.AppConstant
import com.bm.main.scm.utils.Helper
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.activity_kulakan.*
import org.threeten.bp.LocalDate

class KulakanActivity : BaseActivity<KulakanPresenter, KulakanContract.View>(),
    KulakanContract.View,
    SingleDateDialog.Listener,
    CartCountDialog.Listener {

    private val CODE_OPEN_SCAN = 1001
    private val CODE_OPEN_CHOOSE_PRODUCT = 1002
    private val CODE_OPEN_ADD_MANUAL = 1003
    private val CODE_OPEN_EDIT_MANUAL = 1004
    private val CODE_OPEN_CHOOSE_SUPPLIER = 1005

    private val adapter = KulakanAdapter()

    override fun createPresenter(): KulakanPresenter {
        return KulakanPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_kulakan
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated()
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "Data Kulakan"

            val backArrow = resources.getDrawable(R.drawable.ic_back_pos)
            setHomeAsUpIndicator(backArrow)
        }
        title = "Data Kulakan"
    }

    private fun renderView() {
        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_list.layoutManager = layoutManager
        rv_list.adapter = adapter

        adapter.callback = object : KulakanAdapter.ItemClickCallback {
            override fun onCountDialog(data: Cart, position: Int) {
                openCountDialog(data, position)
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

        btn_scanner.setOnClickListener {
            getPresenter()?.onCheckScan()
        }

        ll_search.setOnClickListener {
            openChooseProduct()
        }

        btn_manual.setOnClickListener {
            openAddManual("")
        }

        rg_payment.setOnCheckedChangeListener { _, p1 ->
            when (p1) {
                R.id.rb_tunai   -> showTunaiView()
                R.id.rb_piutang -> showPiutangView()
            }
        }

        rg_payment.check(R.id.rb_tunai)

        et_supplier.setOnClickListener {
            openChooseSupplier()
        }

        btn_delete_supplier.setOnClickListener {
            getPresenter()?.updateSupplier(null)
        }

        btn_date.setOnClickListener {
            openSingleDatePickerDialog(getPresenter()?.getSelectedDate())
        }

        btn_bayar.setOnClickListener {
            showLoadingDialog()
            when (rg_payment.checkedRadioButtonId) {
                R.id.rb_tunai   -> {
                    getPresenter()?.checkTunai()
                }
                R.id.rb_piutang -> {
                    getPresenter()?.checkPiutang()
                }
            }
        }

        setupToolbar()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item!!)
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

    override fun showMessage(code: Int, msg: String?) {
        hideLoadingDialog()
        if (code == RestException.CODE_USER_NOT_FOUND) {
            restartLoginActivity()
        } else {
            msg?.let {
                toast(this, it)
            }

        }
    }

    override fun openScanPage() {
        val intent = Intent(this, ScanCodeActivity::class.java)
        intent.putExtra(AppConstant.SCAN.TYPE, AppConstant.SCAN.SELL)
        startActivityForResult(intent, CODE_OPEN_SCAN)

    }

    override fun openChooseProduct() {
        val intent = Intent(this, ChooseProductActivity::class.java)
        intent.putExtra(AppConstant.DATA, false)
        startActivityForResult(intent, CODE_OPEN_CHOOSE_PRODUCT)
    }

    override fun openAddManual(barcode: String) {
        val intent = Intent(this, com.bm.main.scm.feature.sell.add.AddActivity::class.java)
        intent.putExtra(AppConstant.DATA, barcode)
        intent.putExtra("title", "Tambah Kulakan")
        startActivityForResult(intent, CODE_OPEN_ADD_MANUAL)
    }

    override fun openEditManual(product: Product) {
        val intent = Intent(this, EditActivity::class.java)
        intent.putExtra(AppConstant.DATA, product)
        startActivityForResult(intent, CODE_OPEN_EDIT_MANUAL)
    }

    override fun getTotalValue(): Double {
        val value = tv_total.text.toString().replace("Rp", "").replace(".", "")
        if (value.isBlank() || value.isEmpty()) {
            return 0.0
        }
        return value.toDouble()

    }

    override fun showContentView() {
        ll_content.visibility = View.VISIBLE
        ll_error.visibility = View.GONE
    }

    override fun showErrorView(err: String) {
        ll_content.visibility = View.GONE
        ll_error.visibility = View.VISIBLE
        tv_error.text = err
    }

    override fun openSuccessPage(id: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Berhasil")
        builder.setMessage("Kulakan Anda berhasil!")
        builder.setCancelable(false)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
            openHistoryKulakan()
        }
        builder.show()
    }

    override fun setCartText(nominal: String) {
        tv_total.text = nominal
        tv_subtotal.text = nominal
    }

    override fun addCart(data: Cart) {
        adapter.addItem(data)
    }

    override fun updateCart(cart: Cart, position: Int) {
        adapter.updateItem(cart, position)
    }

    override fun deleteCart(position: Int) {
        adapter.deleteItem(position)
    }

    override fun showTunaiView() {
        ll_hutang.visibility = View.GONE
    }

    override fun showPiutangView() {
        ll_hutang.visibility = View.VISIBLE
    }

    override fun setSupplierName(data: Supplier?) {
        et_supplier.text = ""
        btn_delete_supplier.visibility = View.GONE
        data?.let {
            et_supplier.text = it.nama_supplier
            btn_delete_supplier.visibility = View.VISIBLE
        }
    }

    override fun openChooseSupplier() {
        val intent = Intent(this, ChooseSupplierActivity::class.java)
        startActivityForResult(intent, CODE_OPEN_CHOOSE_SUPPLIER)
    }

    override fun openSingleDatePickerDialog(selected: CalendarDay?) {
        val dateDialog = SingleDateDialog.newInstance()
        val now = LocalDate.now()
        dateDialog.setData(selected, now, null, -1)
        dateDialog.show(this.supportFragmentManager, SingleDateDialog.TAG)
    }

    override fun openCountDialog(selected: Cart, pos: Int) {
        val dialog = CartCountDialog.newInstance()
        dialog.setData(selected, pos, false)
        dialog.show(this.supportFragmentManager, CartCountDialog.TAG)
    }

    override fun onDateClicked(selected: CalendarDay?, type: Int) {
        getPresenter()?.setSelectedDate(selected)
        if (selected == null) {
            et_date.text = ""
        } else {
            et_date.text = Helper.getDateFormat(this!!, selected.date.toString(), "yyyy-MM-dd", "dd MMMM yyyy")
        }
    }

    override fun onCountSaved(selected: Cart, pos: Int) {
        getPresenter()?.updateCart(selected, pos)
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
                toast(this, "Data tidak ditemukan")
                return
            }
            if (data.getSerializableExtra(AppConstant.DATA) == null) {
                toast(this, "Data tidak ditemukan")
                return
            }
            val product = data.getSerializableExtra(AppConstant.DATA) as Product
            if (product.id_barang == null) {
                toast(this, "Data tidak ditemukan")
            } else {
                getPresenter()?.checkCart(product)
            }
        } else if (requestCode == CODE_OPEN_ADD_MANUAL && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                toast(this, "Data tidak ditemukan")
                return
            }
            if (data.getSerializableExtra(AppConstant.DATA) == null) {
                toast(this, "Data tidak ditemukan")
                return
            }
            val product = data.getSerializableExtra(AppConstant.DATA) as Product
            if (product.id_barang == null) {
                toast(this, "Data tidak ditemukan")
            } else {
                getPresenter()?.addCart(product)
            }
        } else if (requestCode == CODE_OPEN_EDIT_MANUAL && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                toast(this, "Data tidak ditemukan")
                return
            }
            if (data.getSerializableExtra(AppConstant.DATA) == null) {
                toast(this, "Data tidak ditemukan")
                return
            }
            val product = data.getSerializableExtra(AppConstant.DATA) as Product
            if (product.id_barang == null) {
                toast(this, "Data tidak ditemukan")
            } else {
                getPresenter()?.addCart(product)
            }
        } else if (requestCode == CODE_OPEN_CHOOSE_SUPPLIER && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                toast(this, "Data tidak ditemukan")
                return
            }
            if (data.getSerializableExtra(AppConstant.DATA) == null) {
                toast(this, "Data tidak ditemukan")
                return
            }
            val customer = data.getSerializableExtra(AppConstant.DATA) as Supplier
            if (customer.id_supplier == null) {
                toast(this, "Data tidak ditemukan")
            } else {
                getPresenter()?.updateSupplier(customer)
            }
        }
    }

    override fun openHistoryKulakan() {
        val intent = Intent(this, KulakanActivity::class.java)
        intent.putExtra(AppConstant.CODE, AppConstant.Code.CODE_TRANSACTION_SUPPLIER)
        startActivity(intent)
    }


}

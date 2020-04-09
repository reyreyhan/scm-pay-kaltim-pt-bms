package com.bm.main.pos.feature.manage.product.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseActivity
import com.bm.main.pos.di.userComponent
import com.bm.main.pos.feature.dialog.BottomDialog
import com.bm.main.pos.feature.manage.product.ProductViewModel
import com.bm.main.pos.feature.manage.product.add.AddProductFragment
import com.bm.main.pos.feature.manage.product.list.ProductListFragment
import com.bm.main.pos.feature.scan.ScanCodeFragment
import com.bm.main.pos.models.DialogModel
import com.bm.main.pos.models.product.Product
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.ui.ext.toast
import com.bm.main.pos.utils.AppConstant
import kotlinx.android.synthetic.main.activity_add_product_new.*
import timber.log.Timber

const val ADD_PRODUCT_SCAN = 101
const val ADD_PRODUCT_INPUT = 101
const val ADD_PRODUCT_SEARCH = 103
const val ADD_PRODUCT_MANUAL = 104

class AddProductMainActivity : BaseActivity<AddProductMainPresenter, AddProductMainContract.View>(),
    AddProductMainContract.View,
    ScanCodeFragment.OnProductSelectedListener,
    ProductListFragment.OnProductSearchSelectedListener,
    BottomDialog.Listener,
    AddProductFragment.OnProductAdded{

    private val categoryDialog = BottomDialog.newInstance()
    private var ft: FragmentTransaction? = null

    private val productViewModel by lazy {
        ViewModelProvider(
            this,
            userComponent!!.productComponentFactory()
        ).get(ProductViewModel::class.java)
    }

    override fun createPresenter(): AddProductMainPresenter {
        return AddProductMainPresenter(
            this,
            this
        )
    }

    override fun createLayout(): Int {
        return R.layout.activity_add_product_new
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated(intent)
    }

    private fun renderView() {
        btn_scan.isSelected = true
        btn_cari.isSelected = false
        btn_manual.isSelected = false

        btn_scan.setOnClickListener {
            btn_scan.isSelected = true
            btn_cari.isSelected = false
            btn_manual.isSelected = false
            openScanFragment()
        }

        btn_cari.setOnClickListener {
            btn_scan.isSelected = false
            btn_cari.isSelected = true
            btn_manual.isSelected = false
            openSearchFragment()
        }

        btn_manual.setOnClickListener {
            btn_scan.isSelected = false
            btn_cari.isSelected = false
            btn_manual.isSelected = true
            openAddProductFragment()
        }
    }

    override fun openScanFragment(){
        ft = supportFragmentManager.beginTransaction()
        val fragment = ScanCodeFragment.newInstance()
        ft!!.replace(R.id.container_fragment, fragment)
        ft!!.commit()
    }

    override fun openAddProductFragmentFromScan(barcode:String){
        ft = supportFragmentManager.beginTransaction()
        Log.d("SELECTEDTOADDPRODUCT", "TEST")
        val bundle = Bundle().apply {
            putBoolean("FromScan", true)
            putString(AppConstant.DATA, barcode)
        }
        val fragment = AddProductFragment.newInstance(bundle)
        ft!!.replace(R.id.container_fragment, fragment, AddProductFragment::class.java.simpleName)
        ft!!.commit()
    }

    private fun openSearchFragment(){
        ft = supportFragmentManager.beginTransaction()
        val fragment = ProductListFragment.newInstance()
        ft!!.replace(R.id.container_fragment, fragment, ProductListFragment::class.java.simpleName)
        ft!!.commit()
    }

    private fun openAddProductFragment(){
        ft = supportFragmentManager.beginTransaction()
        val bundle = Bundle().apply {
            putBoolean("FromScan", false)
        }
        val fragment = AddProductFragment.newInstance(bundle)
        ft!!.replace(R.id.container_fragment, fragment, AddProductFragment::class.java.simpleName)
        ft!!.commit()
    }

    private fun openAddProductFragmentFromSearch(data:Product){
        ft = supportFragmentManager.beginTransaction()
        val bundle = Bundle().apply {
            putBoolean("FromScan", false)
            putSerializable(AppConstant.DATA, data)
        }
        val fragment = AddProductFragment.newInstance(bundle)
        ft!!.replace(R.id.container_fragment, fragment, AddProductFragment::class.java.simpleName)
        ft!!.commit()
    }

    override fun changeFragmentPage(page: Int, barcode: String?) {
        ft = supportFragmentManager.beginTransaction()
        if (page == ADD_PRODUCT_SCAN) {
            val fragment = ScanCodeFragment.newInstance()
            ft!!.replace(R.id.container_fragment, fragment)
            ft!!.commit()
        }
        else if (page == ADD_PRODUCT_INPUT) {
            Log.d("SELECTEDTOADDPRODUCT", "TEST")
            val bundle = Bundle().apply {
                putBoolean("FromScan", true)
                putString("Barcode", barcode!!)
            }
            val fragment = AddProductFragment.newInstance(bundle)
            ft!!.replace(R.id.container_fragment, fragment)
            ft!!.commit()
        }
        else if (page == ADD_PRODUCT_MANUAL) {
            val bundle = Bundle().apply {
                putBoolean("FromScan", false)
            }
            val fragment = AddProductFragment.newInstance(bundle)
            ft!!.replace(R.id.container_fragment, fragment)
            ft!!.commit()
        }
    }

    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "Tambah Barang"

            val backArrow = resources.getDrawable(R.drawable.ic_toolbar_back)
            setHomeAsUpIndicator(backArrow)
        }
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
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

    override fun onClose(msg: String?, status: Int) {
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Timber.d(requestCode.toString())
//        if (requestCode == ADD_PRODUCT_SCAN && resultCode == Activity.RESULT_OK) {
//            val code = data?.getStringExtra(AppConstant.DATA)
//            if (!code.isNullOrBlank() || !code.isNullOrEmpty()) {
//                changeFragmentPage(ADD_PRODUCT_INPUT, code)
//            }
//            else {
//                super.onActivityResult(requestCode, resultCode, data)
//            }
//        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onProductSelected(data: String) {
        openAddProductFragmentFromScan(data)
    }

    override fun onItemClicked(data: DialogModel, type: Int) {
        val fragment = supportFragmentManager.findFragmentByTag(AddProductFragment::class.java.simpleName)
        if (fragment is AddProductFragment){
            fragment.getPresenter()?.setSelectedCategory(data)
        }
    }

    override fun onProductSearchSelectedListener(data: Product) {
        openAddProductFragmentFromSearch(data)
    }


    override fun onResult(data: Product) {
        val newintent: Intent = intent
        newintent.putExtra(AppConstant.DATA, data)
        setResult(Activity.RESULT_OK, newintent)
        finish()
    }

    override fun setOnProductAdded(data: String) {
        getPresenter()?.searchByBarcode(data)
    }
}

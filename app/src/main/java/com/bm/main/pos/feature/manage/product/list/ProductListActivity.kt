package com.bm.main.pos.feature.manage.product.list

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseActivity
import com.bm.main.pos.feature.manage.product.edit.EditProductActivity
import com.bm.main.pos.feature.manage.product.main.AddProductMainActivity
import com.bm.main.pos.feature.scan.ScanCodeActivity
import com.bm.main.pos.models.product.Product
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.ui.EndlessRecyclerViewScrollListener
import com.bm.main.pos.ui.ext.toast
import com.bm.main.pos.utils.AppConstant
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_product_search.*
import org.jetbrains.anko.backgroundColor


class ProductListActivity : BaseActivity<ProductListPresenter, ProductListContract.View>(),
    ProductListContract.View {

    val adapter = ProductListAdapter()
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private val CODE_OPEN_ADD = 1001
    private val CODE_OPEN_EDIT = 1002
    private val CODE_OPEN_SCAN = 1003

    lateinit var fab:FloatingActionButton

    override fun createPresenter(): ProductListPresenter {
        return ProductListPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.fragment_product_search
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated()
        getPresenter()?.loadProducts()
    }

    private fun renderView() {
        addFABButton()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            layout_search.backgroundColor = getColor(R.color.colorAccent)
        }
        sw_refresh.isRefreshing = false
        sw_refresh.setOnRefreshListener {
            scrollListener.resetState()
            reloadData()
        }

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_list_barang.layoutManager = layoutManager
        rv_list_barang.adapter = adapter

        scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onFirstItemVisible(isFirstItem: Boolean) {
                sw_refresh.isEnabled = isFirstItem && adapter.itemCount > 0
            }

            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {

            }
        }
        rv_list_barang.addOnScrollListener(scrollListener)

        adapter.callback = object : ProductListAdapter.ItemClickCallback{
            override fun onClick(data: Product) {
                data?.let {
                    openEditPage(data)
                }
            }

//            override fun onDelete(data: Product) {
//                showLoadingDialog()
//                getPresenter()?.deleteProduct(data.id_barang!!)
//            }
        }

        et_search.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                adapter.clearAdapter()
                sw_refresh.isRefreshing = true
                getPresenter()?.searchProduct(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        fab.setOnClickListener {
            openAddPage()
        }

//        btn_scanner.setOnClickListener {
//            getPresenter()?.onCheckScan()
//        }
//
//        btn_sort.setOnClickListener {
//            sw_refresh.isRefreshing = true
//            adapter.clearAdapter()
//            getPresenter()?.onCheckSort()
//        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item!!)
    }


    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = getString(R.string.lbl_manage_produk_title)

            val backArrow = resources.getDrawable(R.drawable.ic_toolbar_back)
            setHomeAsUpIndicator(backArrow)
        }

    }

    override fun setProducts(list: List<Product>) {
        hideLoadingDialog()
        sw_refresh.isRefreshing = false
        adapter.setItems(list)
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    override fun showErrorMessage(code: Int, msg: String) {
        hideLoadingDialog()
        sw_refresh.isRefreshing = false
        if(code == RestException.CODE_USER_NOT_FOUND){
            restartLoginActivity()
        }
        else{
            toast(this,msg)
        }

    }

    override fun showSuccessMessage(msg: String?) {
        hideLoadingDialog()
        sw_refresh.isRefreshing = false
        msg?.let {
            Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
        }
        reloadData()

    }

    override fun reloadData() {
        sw_refresh.isRefreshing = true
        adapter.clearAdapter()
        getPresenter()?.loadProducts()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CODE_OPEN_SCAN){
            if(resultCode == Activity.RESULT_OK){
                val text = data?.getStringExtra(AppConstant.DATA)
                text?.let {
                    showLoadingDialog()
                    getPresenter()?.searchByBarcode(it)
                }
            }
        }
        else if (requestCode == CODE_OPEN_ADD){
            if(resultCode == Activity.RESULT_OK){
                if(data == null){
                    reloadData()
                    return
                }
                if(data?.getSerializableExtra(AppConstant.DATA) == null){
                    reloadData()
                    return
                }
                val product = data?.getSerializableExtra(AppConstant.DATA) as Product
                if(product == null){
                    reloadData()
                }
                else{
                    openEditPage(product)
                }
            }
        }

        else if (requestCode == CODE_OPEN_ADD || requestCode == CODE_OPEN_EDIT){
            if(resultCode == Activity.RESULT_OK){
                reloadData()
            }
        }



    }

    override fun openAddPage() {
        val intent = Intent(this, AddProductMainActivity::class.java)
        startActivity(intent)
    }

    override fun openEditPage(data: Product) {
        hideLoadingDialog()
        val intent = Intent(this,EditProductActivity::class.java)
        intent.putExtra(AppConstant.DATA,data)
        startActivityForResult(intent,CODE_OPEN_EDIT)
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

    override fun openScanPage() {
        val intent = Intent(this,ScanCodeActivity::class.java)
        startActivityForResult(intent,CODE_OPEN_SCAN)
    }

    fun addFABButton(){
        fab = FloatingActionButton(this)
        val rel = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        rel.setMargins(15, 15, 15, 15)
        rel.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        rel.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        fab.layoutParams = rel
        fab.setImageResource(R.drawable.ic_add_white)
        fab.size = FloatingActionButton.SIZE_NORMAL
        fab.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.md_red_A700))
        layout_parent.addView(fab)
    }
}

package com.bm.main.scm.feature.manage.category.list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseActivity
import com.bm.main.scm.feature.manage.category.add.AddCategoryActivity
import com.bm.main.scm.feature.manage.category.edit.EditCategoryActivity
import com.bm.main.scm.models.category.Category
import com.bm.main.scm.rest.entity.RestException
import com.bm.main.scm.ui.EndlessRecyclerViewScrollListener
import com.bm.main.scm.ui.ext.toast
import com.bm.main.scm.utils.AppConstant
import kotlinx.android.synthetic.main.activity_list_category.*

class CategoryListActivity : BaseActivity<CategoryListPresenter, CategoryListContract.View>(),
    CategoryListContract.View {

    val adapter = CategoryListAdapter()
    private val data = ArrayList<Category>()
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener
    private val CODE_OPEN_ADD = 101
    private val CODE_OPEN_EDIT = 102

    override fun createPresenter(): CategoryListPresenter {
        return CategoryListPresenter(this, this)
    }

    override fun createLayout(): Int {
        return R.layout.activity_list_category
    }

    override fun startingUpActivity(savedInstanceState: Bundle?) {
        renderView()
        getPresenter()?.onViewCreated()
    }

    private fun renderView() {
        sw_refresh.setOnRefreshListener {
            scrollListener.resetState()
            reloadData()
        }

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_list.layoutManager = layoutManager
        rv_list.adapter = adapter

        scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onFirstItemVisible(isFirstItem: Boolean) {
                sw_refresh.isEnabled = isFirstItem && adapter.itemCount > 0

            }

            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {

            }
        }
        rv_list.addOnScrollListener(scrollListener)

        adapter.callback = object : CategoryListAdapter.ItemClickCallback {
            override fun onClick(data: Category) {
                data?.let {
                    openEditCategoryPage(data)
                }
            }

            override fun onDelete(data: Category) {
                showLoadingDialog()
                getPresenter()?.deleteCategory(data.id_kategori!!)
            }
        }

        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                handler.removeCallbacks(search)
                handler.postDelayed(search, 1000)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        btn_add.setOnClickListener {
            openAddCategoryPage()
        }
    }

    private val handler by lazy { Handler() }
    private val search by lazy {
        Runnable {
            adapter.clearAdapter()
            sw_refresh.isRefreshing = true
            getPresenter()?.searchCategory(et_search.text.toString().trim())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item!!)
    }


    private fun setupToolbar() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = getString(R.string.menu_category)

            val backArrow = resources.getDrawable(R.drawable.ic_back_pos)
            setHomeAsUpIndicator(backArrow)
        }

    }

    override fun setData(list: List<Category>) {
        hideLoadingDialog()
        sw_refresh.isRefreshing = false
        data.clear()
        data.addAll(list)
        adapter.clearAdapter()
        adapter.setItems(data)
    }

    override fun onResume() {
        super.onResume()
        setupToolbar()
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

    override fun showErrorMessage(code: Int, msg: String?) {
        hideLoadingDialog()
        sw_refresh.isRefreshing = false
        if (code == RestException.CODE_USER_NOT_FOUND) {
            restartLoginActivity()
        } else {
            msg?.let {
                toast(this, it)
            }

        }

    }

    override fun showSuccessMessage(msg: String?) {
        hideLoadingDialog()
        sw_refresh.isRefreshing = false
        msg?.let {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
        reloadData()

    }

    override fun reloadData() {
        sw_refresh.isRefreshing = true
        adapter.clearAdapter()
        getPresenter()?.loadCategories()
    }

    override fun openAddCategoryPage() {
        val intent = Intent(this, AddCategoryActivity::class.java)
        startActivityForResult(intent, CODE_OPEN_ADD)
    }

    override fun openEditCategoryPage(data: Category) {
        val intent = Intent(this, EditCategoryActivity::class.java)
        intent.putExtra(AppConstant.DATA, data)
        startActivityForResult(intent, CODE_OPEN_EDIT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            reloadData()
        }
    }


}

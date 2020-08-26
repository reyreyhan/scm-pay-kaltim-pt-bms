package com.bm.main.scm.feature.manage.product.search

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bm.main.fpl.activities.BaseActivity
import com.bm.main.fpl.constants.RConfig
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.scm.BuildConfig
import com.bm.main.scm.R
import com.bm.main.scm.callback.AdapterItemCallback
import com.bm.main.scm.datasources.NETWORK_FAILED
import com.bm.main.scm.datasources.NETWORK_LOADING
import com.bm.main.scm.datasources.NETWORK_SUCCESS
import com.bm.main.scm.datasources.NetworkState
import com.bm.main.scm.di.userComponent
import com.bm.main.scm.feature.manage.product.ProductViewModel
import com.bm.main.scm.feature.scan.ScanCodeActivity
import com.bm.main.scm.models.product.Product
import com.bm.main.scm.ui.ext.toast
import com.bm.main.scm.utils.AppConstant
import kotlinx.android.synthetic.main.activity_product_search.*
import timber.log.Timber

class ProductSearchActivity : BaseActivity() {

    private val productViewModel by lazy { ViewModelProvider(this, userComponent!!.productComponentFactory()).get(ProductViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_product_search)

        setSupportActionBar(toolbarx)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = ""
            toolbar_title.text = "Cari Barang"

            setHomeAsUpIndicator(ResourcesCompat.getDrawable(resources, R.drawable.ic_back_pos, null))
            toolbarx.setNavigationOnClickListener { finish() }
        }

        scan.setOnClickListener(clickListener)

        search.addTextChangedListener(textChangeListener)
        search.setOnKeyListener(textKeyChangeListener)

        rv_product.isNestedScrollingEnabled = true
        rv_product.adapter = adapter
    }

    private val adapter by lazy {
        val baseUri = if (BuildConfig.DEBUG) {
            PreferenceClass.getString(RConfig.API_URL_POS_DEVEL, "").orEmpty()
        } else {
            PreferenceClass.getString(RConfig.API_URL_POS, "").orEmpty()
        }
        ProductSearchAdapter(itemHandler, baseUri + "images/small_no_product.jpg")
    }

    private val productObserver by lazy { Observer(adapter::submitList) }
    private val loadingObserver by lazy {
        Observer<NetworkState> {
            Timber.e(it.toString())
            loading.visibility = if (it.state == NETWORK_LOADING) View.VISIBLE else View.GONE
        }
    }

    private val initObserver by lazy {
        Observer<NetworkState> {
            Timber.e(it.toString())
            loading.visibility = if (it.state == NETWORK_LOADING) View.VISIBLE else View.GONE
            ll_error.visibility = if (it.state == NETWORK_FAILED) View.VISIBLE else View.GONE

            if (it.state == NETWORK_SUCCESS) {
                productViewModel.loadState.observe(this, loadingObserver)
            }
        }
    }

    private val itemHandler by lazy {
        object : AdapterItemCallback<Product> {
            override fun onItemClick(item: Product, pos: Int) {
                setResult(
                    RESULT_OK,
                    intent.putExtra("item", productViewModel.moshi.adapter(Product::class.java).toJson(item))
                )
                finish()
            }

            override fun onItemLongClick(item: Product, pos: Int) {
            }
        }
    }

    private val clickListener by lazy {
        View.OnClickListener {
            when (it) {
                scan -> startActivityForResult(
                    Intent(this, ScanCodeActivity::class.java),
                    CODE_OPEN_SCAN
                )
            }
        }
    }

    private val textKeyChangeListener by lazy {
        View.OnKeyListener { v, keyCode, event ->
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                val s = search.text.toString()
                if (!s.isBlank() && s.length > 2) {
                    handler.removeCallbacks(searchProduct)
                    handler.postDelayed(searchProduct, 200)
                }
            }
            false
        }
    }

    private val textChangeListener by lazy {
        object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrBlank() && s.length > 2) {
                    handler.removeCallbacks(searchProduct)
                    handler.postDelayed(searchProduct, 200)
                }
            }
        }
    }

    private val handler by lazy { Handler() }
    private val searchProduct by lazy {
        Runnable {
            productViewModel.productList.removeObserver(productObserver)
            productViewModel.initState.removeObserver(initObserver)
            productViewModel.initState.observe(this, initObserver)

            if (productViewModel.loadState.hasObservers() == true) {
                productViewModel.loadState.removeObserver(loadingObserver)
            }

            loading.visibility = View.VISIBLE
            ll_error.visibility = View.GONE
            productViewModel.searchProduct(search.text.toString().trim())
            productViewModel.productList.observe(this, productObserver)
        }
    }

    private val CODE_OPEN_SCAN = 1001
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CODE_OPEN_SCAN && resultCode == RESULT_OK) {
            data?.getStringExtra(AppConstant.DATA)?.let {
                if (it.isNotBlank()) {
                    loadingDialog.show()
                    productViewModel.searchBarcode(it) {
                        loadingDialog.dismiss()
                        it?.let {
                            setResult(RESULT_OK)
                            intent.putExtra("item", it.json())
                            finish()
                        } ?: toast(this, "Barang tidak ditemukan")
                    }
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private val loadingDialog by lazy { AlertDialog.Builder(this).setCancelable(false).setView(R.layout.layout_progress_dialog).create() }
}
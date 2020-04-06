package com.bm.main.pos.feature.manage.product.list

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.pos.R
import com.bm.main.pos.base.BaseFragment
import com.bm.main.pos.models.product.Product
import com.bm.main.pos.ui.EndlessRecyclerViewScrollListener
import com.bm.main.pos.ui.ext.toast
import kotlinx.android.synthetic.main.fragment_product_search.view.*

class ProductListFragment : BaseFragment<ProductListPresenter, ProductListContract.View>(),
    ProductListContract.View{

    private var productListener: OnProductSearchSelectedListener? = null

    interface OnProductSearchSelectedListener {
        fun onProductSearchSelectedListener(data: Product)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ProductListFragment()
    }

    private lateinit var _view: View
    val adapter = ProductListAdapter()
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener

    override fun createPresenter(): ProductListPresenter {
        return ProductListPresenter(activity as Context, this)
    }

    override fun onCreateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_product_search, container, false)
    }

    override fun initAction(view: View) {
        _view = view
        renderView()
        getPresenter()?.onViewCreated()
//        getPresenter()?.searchProductMaster("")
    }

    private fun renderView() {
        _view.sw_refresh.isRefreshing = false
        _view.sw_refresh.setOnRefreshListener {
            scrollListener.resetState()
            reloadData()
        }

        val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        _view.rv_list_barang.layoutManager = layoutManager
        _view.rv_list_barang.adapter = adapter

        scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onFirstItemVisible(isFirstItem: Boolean) {
                _view.sw_refresh.isEnabled = isFirstItem && adapter.itemCount > 0
            }

            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
//                reloadData()
                getPresenter()?.searchProductMaster(_view.et_search.editableText.toString(), adapter.itemCount)
            }
        }
        _view.rv_list_barang.addOnScrollListener(scrollListener)

        adapter.callback = object : ProductListAdapter.ItemClickCallback{
            override fun onClick(data: Product) {
                data.let {
                    productListener?.onProductSearchSelectedListener(data)
                }
            }
        }

        _view.et_search.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                adapter.clearAdapter()
                _view.sw_refresh.isRefreshing = true
                getPresenter()?.searchProductMaster(p0.toString(), 0)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
    }

    override fun setProducts(list: List<Product>) {
        hideLoadingDialog()
        _view.sw_refresh.isRefreshing = false
        adapter.addItems(list)
    }

    override fun showErrorMessage(code: Int, msg: String) {
        hideLoadingDialog()
        _view.sw_refresh.isRefreshing = false
//        if(code == RestException.CODE_USER_NOT_FOUND){
//            restartLoginActivity()
//        }
//        else{
//            toast(this,msg)
//        }
    }
    override fun showSuccessMessage(msg: String?) {
        hideLoadingDialog()
        _view.sw_refresh.isRefreshing = false
        msg?.let {
            toast(msg)
        }
        reloadData()

    }

    override fun reloadData() {
        _view.sw_refresh.isRefreshing = true
        adapter.clearAdapter()
        getPresenter()?.searchProductMaster("", 0)
    }

    override fun openAddPage() {
    }

    override fun openEditPage(data: Product) {
    }

    override fun openScanPage() {
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnProductSearchSelectedListener) {
            productListener = context
        } else {
            throw RuntimeException("$context must implement OnProductSearchSelectedListener")
        }
    }

    override fun onDetach() {
        productListener = null
        super.onDetach()
    }
}

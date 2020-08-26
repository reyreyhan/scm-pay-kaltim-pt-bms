package com.bm.main.scm.feature.sell.chooseProduct

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.fpl.activities.BaseActivity
import com.bm.main.fpl.constants.EventParam
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseFragment
import com.bm.main.scm.models.product.Product
import com.bm.main.scm.rest.entity.RestException
import com.bm.main.scm.ui.EndlessRecyclerViewScrollListener
import com.bm.main.scm.ui.ext.toast
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.fragment_penjualan_cari.view.*

class ChooseProductFragment : BaseFragment<ChooseProductPresenter, ChooseProductContract.View>(), ChooseProductContract.View {
    private var productListener: OnProductSelectedListener? = null

    interface OnProductSelectedListener {
        fun onProductSelected(data: Product)
    }

    override fun createPresenter(): ChooseProductPresenter {
        return ChooseProductPresenter(activity as Context, this)
    }

    private lateinit var _view: View

    val adapter = ChooseProductAdapter()
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener

    companion object {

        @JvmStatic
        fun newInstance() =
            ChooseProductFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnProductSelectedListener) {
            productListener = context
        } else {
            throw RuntimeException("$context must implement OnProductSelectedListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        productListener = null
    }

    override fun onCreateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_penjualan_cari, container, false)
    }

    override fun initAction(view: View) {
        _view = view
        renderView()
        getPresenter()?.onFragmentViewCreated(requireActivity().intent)
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

            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {}
        }
        _view.rv_list_barang.addOnScrollListener(scrollListener)

        adapter.callback = object : ChooseProductAdapter.ItemClickCallback{
            override fun onClick(data: Product) {
                data?.let {
                    onSelected(it)
                }
            }
        }

//        _view.et_search.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
//            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
//            }
//            override fun afterTextChanged(editable: Editable) {
//                adapter.filter.filter(editable.toString())
//            }
//        })


        _view.et_search.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                adapter.clearAdapter()
                _view.sw_refresh.isRefreshing = true
                getPresenter()?.searchProduct(p0.toString())
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
        adapter.setItems(list)
    }

    override fun showErrorMessage(code: Int, msg: String) {
        hideLoadingDialog()
        _view.sw_refresh.isRefreshing = false
        if(code == RestException.CODE_USER_NOT_FOUND){
            restartLoginActivity()
        }
        else{
            toast(msg)
        }
    }

    override fun showSuccessMessage(msg: String?) {
        hideLoadingDialog()
        _view.sw_refresh.isRefreshing = false
        msg?.let {
            Toast.makeText(requireActivity(),msg, Toast.LENGTH_SHORT).show()
        }
        reloadData()

    }

    override fun reloadData() {
        _view.sw_refresh.isRefreshing = true
        adapter.clearAdapter()
        getPresenter()?.loadProducts()
    }

    override fun onDestroy() {
        super.onDestroy()
        getPresenter()?.onDestroy()
    }

    override fun onSelected(data: Product) {
        (context as BaseActivity).logEventFireBase(
            "Pilih Product",
            data.nama_barang,
            EventParam.EVENT_ACTION_CHOICE_PRODUCT,
            FirebaseAnalytics.Event.ADD_TO_WISHLIST,
            EventParam.EVENT_SUCCESS,
            ChooseProductActivity::class.java.simpleName
        )
        if (productListener != null){
            productListener!!.onProductSelected(data)
        }
    }

    override fun checkStockProducts(isCheck: Boolean) {
        adapter.setCheckStok(isCheck)
    }
}
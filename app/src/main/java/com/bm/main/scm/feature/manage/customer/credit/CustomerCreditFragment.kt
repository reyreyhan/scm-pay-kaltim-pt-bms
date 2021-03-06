package com.bm.main.scm.feature.manage.customer.credit


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.R
import com.bm.main.scm.base.BaseFragment
import com.bm.main.scm.events.onReloadTransaction
import com.bm.main.scm.feature.transaction.detail.old.DetailActivity
import com.bm.main.scm.models.customer.Customer
import com.bm.main.scm.models.transaction.Transaction
import com.bm.main.scm.rest.entity.RestException
import com.bm.main.scm.utils.AppConstant
import kotlinx.android.synthetic.main.fragment_customer_credit.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ContentFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ContentFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */

class CustomerCreditFragment : BaseFragment<CustomerCreditPresenter, CustomerCreditContract.View>(),
    CustomerCreditContract.View {

    private val TAG = CustomerCreditFragment::class.java.simpleName

    private val ARGUMENT_PARAM = "ARGUMENT_PARAM"

    private lateinit var _view: View
    val adapter = CustomerCreditAdapter()


    companion object {

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param intros ArrayList Intro.
         * @return A new instance of fragment ContentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(data : Customer?) =
                CustomerCreditFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(ARGUMENT_PARAM, data)
                    }
                }
    }

    override fun createPresenter(): CustomerCreditPresenter {
        return CustomerCreditPresenter(activity as Context, this)
    }

    override fun onCreateLayout(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        return inflater.inflate(R.layout.fragment_customer_credit, container, false)
    }


    override fun initAction(view: View) {
        EventBus.getDefault().register(this)
        _view = view
        renderView()
        var data: Customer? = null
        if (arguments != null) {
            data = arguments?.getSerializable(ARGUMENT_PARAM) as Customer
        }
        getPresenter()?.onViewCreated(data)
    }

    private fun renderView(){
        _view.sw_refresh.setOnRefreshListener {
            _view.sw_refresh.isRefreshing = true
            adapter.clearAdapter()
            getPresenter()?.loadTransactions()
        }
        val layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        _view.rv_list.layoutManager = layoutManager
        _view.rv_list.adapter = adapter

        adapter.callback = object : CustomerCreditAdapter.ItemClickCallback{
            override fun onClick(data: Transaction) {
                data?.let {
                    openDetail(it.no_invoice!!)
                }
            }
        }
    }

    override fun setData(list: List<Transaction>) {
        _view.sw_refresh.isRefreshing = false
        _view.rv_list.visibility = View.VISIBLE
        _view.tv_error.visibility = View.GONE
        adapter.setItems(list)
    }

    override fun showErrorMessage(code: Int, msg: String) {
        _view.sw_refresh.isRefreshing = false
        if(code == RestException.CODE_USER_NOT_FOUND){
            restartLoginActivity()
        }
        else{
            _view.rv_list.visibility = View.GONE
            _view.tv_error.visibility = View.VISIBLE
            _view.tv_error.text = msg
        }



    }

    override fun onDetach() {
        super.onDetach()
        getPresenter()?.onDestroy()
        EventBus.getDefault().unregister(this)

    }

    override fun openDetail(id: String) {
        val intent = Intent(activity,
            DetailActivity::class.java)
        intent.putExtra(AppConstant.DATA,id)
        startActivity(intent)
    }
    @Subscribe
    fun onEvent(event: onReloadTransaction){
        if(event.isReload){
            _view.sw_refresh.isRefreshing = true
            adapter.clearAdapter()
            getPresenter()?.loadTransactions()
        }
    }



}

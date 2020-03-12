package com.bm.main.pos.feature.transaction.historyTransaction

import android.app.Activity
import android.content.Context
import android.content.Intent
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
import com.bm.main.pos.events.onHistoryChangedDate
import com.bm.main.pos.events.onHistoryChangedStatus
import com.bm.main.pos.events.onReloadTransaction
import com.bm.main.pos.feature.filterDate.main.MainActivity
import com.bm.main.pos.feature.transaction.detail.DetailSuccessActivity
import com.bm.main.pos.models.DialogModel
import com.bm.main.pos.models.FilterDialogDate
import com.bm.main.pos.models.transaction.Transaction
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.ui.EndlessRecyclerViewScrollListener
import com.bm.main.pos.utils.AppConstant
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.fragment_history_transaction.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.threeten.bp.LocalDate

class TransactionFragment : BaseFragment<TransactionPresenter, TransactionContract.View>(), TransactionContract.View {

    private val openFilter = 1100

    private lateinit var _view: View
    val adapter = TransactionAdapter()
    private var listener: Listener? = null
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener


    companion object {

        @JvmStatic
        fun newInstance() = TransactionFragment()
    }

    override fun createPresenter(): TransactionPresenter {
        return TransactionPresenter(activity as Context, this)
    }

    override fun onCreateLayout(inflater: LayoutInflater,
                                container: ViewGroup?,
                                savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_history_transaction, container, false)
    }


    override fun initAction(view: View) {
        EventBus.getDefault().register(this)
        _view = view
        renderView()
        _view.sw_refresh.isRefreshing = true
        getPresenter()?.onViewCreated()
    }

    private fun renderView() {
        _view.sw_refresh.setOnRefreshListener {
            reloadData()
        }

        val layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        _view.rv_list.layoutManager = layoutManager
        _view.rv_list.adapter = adapter

        adapter.callback = object : TransactionAdapter.ItemClickCallback {
            override fun onClick(data: Transaction) {
                data?.let {
                    openDetail(it.no_invoice!!)
                }
            }
        }

        _view.btn_date.setOnClickListener {
            openFilter(getPresenter()?.getFilterDateSelected())
            //listener?.openFilterDateDialog(null,getPresenter()?.getTodayDate()?.date,getPresenter()?.getFirstDate(),getPresenter()?.getLastDate(),AppConstant.Code.CODE_FILTER_DATE_HISTORY)
        }

        _view.btn_sort.setOnClickListener {
            listener?.openFilterByStatusDialog("Filter berdasarkan status",
                    getPresenter()?.getFilters()!!,
                    getPresenter()?.getFilterSelected(),
                    AppConstant.Code.CODE_FILTER_DATE_HISTORY)
        }

        _view.et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                adapter.clearAdapter()
                _view.sw_refresh.isRefreshing = true
                getPresenter()?.onSearch(p0.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })
    }

    override fun reloadData() {
        _view.sw_refresh.isRefreshing = true
        adapter.clearAdapter()
        getPresenter()?.loadTransaction()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Listener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement Listener")
        }
    }


    override fun onDetach() {
        super.onDetach()
        getPresenter()?.onDestroy()
        EventBus.getDefault().unregister(this)
        listener = null
    }


    interface Listener {
        fun openFilterDateDialog(minDate: LocalDate?,
                                 maxDate: LocalDate?,
                                 firstDate: CalendarDay?,
                                 lastDate: CalendarDay?,
                                 type: Int)

        fun openFilterByStatusDialog(title: String,
                                     list: List<DialogModel>,
                                     selected: DialogModel?,
                                     type: Int)
    }

    override fun setList(list: List<Transaction>) {
        _view.sw_refresh.isRefreshing = false
        _view.rv_list.visibility = View.VISIBLE
        _view.tv_error.visibility = View.GONE
        adapter.setItems(list)
    }

    override fun showErrorMessage(code: Int, msg: String) {
        _view.sw_refresh.isRefreshing = false
        if (code == RestException.CODE_USER_NOT_FOUND) {
            restartLoginActivity()
        } else {
            _view.rv_list.visibility = View.GONE
            _view.tv_error.visibility = View.VISIBLE
            _view.tv_error.text = msg
        }
    }

    override fun openDetail(id: String) {
        val intent = Intent(activity, DetailSuccessActivity::class.java)
        intent.putExtra(AppConstant.DATA, id)
        startActivity(intent)
    }

    @Subscribe
    fun onEvent(event: onHistoryChangedDate) {
        adapter.clearAdapter()
        _view.sw_refresh.isRefreshing = true
        getPresenter()?.onChangeDate(event.firstDate, event.lastDate)

    }

    @Subscribe
    fun onEvent(event: onHistoryChangedStatus) {
        adapter.clearAdapter()
        _view.sw_refresh.isRefreshing = true
        getPresenter()?.onChangeStatus(event.selected)

    }

    @Subscribe
    fun onEvent(event: onReloadTransaction) {
        if (event.isReload) {
            reloadData()
        }
    }

    override fun openFilter(data: FilterDialogDate?) {
        val intent = Intent(activity!!, MainActivity::class.java)
        intent.putExtra(AppConstant.DATA, data)
        startActivityForResult(intent, openFilter)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == openFilter) {
            val filter = data?.getParcelableExtra(AppConstant.DATA) as FilterDialogDate
            adapter.clearAdapter()
            _view.sw_refresh.isRefreshing = true
            getPresenter()?.setFilterDateSelected(filter)
        }
    }


}

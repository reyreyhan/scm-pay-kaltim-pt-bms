package com.bm.main.scm.feature.filterDate.weekly

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.scm.base.BaseFragment
import com.bm.main.scm.R
import com.bm.main.scm.models.FilterDialogDate
import kotlinx.android.synthetic.main.fragment_filter_weekly.view.*

class WeeklyFragment : BaseFragment<WeeklyPresenter, WeeklyContract.View>(),
    WeeklyContract.View {

    private val adapter = WeeklyAdapter()
    private lateinit var _view: View
    private var listener: Listener? = null

    companion object {

        @JvmStatic
        fun newInstance() =
            WeeklyFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun createPresenter(): WeeklyPresenter {
        return WeeklyPresenter(activity as Context, this)
    }

    override fun onCreateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(R.layout.fragment_filter_weekly, container, false)
    }


    override fun initAction(view: View) {
        _view = view
        renderView()
        getPresenter()?.onViewCreated(activity!!.intent)
    }

    private fun renderView() {

        val layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        _view.rv_list.layoutManager = layoutManager
        _view.rv_list.adapter = adapter

        adapter.callback = object : WeeklyAdapter.ItemClickCallback {
            override fun onClick(data: FilterDialogDate) {
                listener?.onSelected(data)
            }
        }

        _view.btn_next.setOnClickListener {
            getPresenter()?.onNextMonth()
        }

        _view.btn_prev.setOnClickListener {
            getPresenter()?.onPrevMonth()
        }
    }

    override fun setMonthYear(year: String) {
        _view.tv_year.text = year
    }

    override fun setList(list: List<FilterDialogDate>, selected: FilterDialogDate) {
        adapter.setItems(list, selected)
    }

    override fun hideNext() {
        _view.btn_next.visibility = View.GONE
    }

    override fun showNext() {
        _view.btn_next.visibility = View.VISIBLE
    }

    override fun hidePrev() {
        _view.btn_prev.visibility = View.GONE
    }

    override fun showPrev() {
        _view.btn_prev.visibility = View.VISIBLE
    }

    interface Listener {
        fun onSelected(data: FilterDialogDate?)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Listener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement Listener")
        }
    }
}

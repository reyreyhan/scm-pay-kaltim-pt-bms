package com.bm.main.scm.feature.transaction.history

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.google.android.material.tabs.TabLayout
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.bm.main.scm.base.BaseFragment
import com.bm.main.scm.R
import com.bm.main.scm.feature.drawer.DrawerActivity
import com.bm.main.scm.models.TabModel
import com.bm.main.scm.ui.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_history.view.*
import com.bm.main.scm.feature.transaction.historyPiutang.PiutangFragment

class HistoryFragment : BaseFragment<HistoryPresenter, HistoryContract.View>(),
    HistoryContract.View {

    private lateinit var _view: View

    private var mFragmentManager: FragmentManager? = null
    private var viewPagerAdapter: ViewPagerAdapter? = null
    //private val transactionFragment = TransactionHistoryActivity.newInstance()
    private val piutangFragment = PiutangFragment.newInstance()

    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment()
    }

    override fun createPresenter(): HistoryPresenter {
        return HistoryPresenter(activity as Context, this)
    }

    override fun onCreateLayout(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun initAction(view: View) {
        _view = view
        renderView()
        getPresenter()?.onViewCreated(activity!!.intent)

    }

    private fun renderView() {
        val parent = activity!! as DrawerActivity
        mFragmentManager = parent.supportFragmentManager
        viewPagerAdapter = ViewPagerAdapter(mFragmentManager)

        setupTab()
    }

    private fun setupTab() {
        val type: ArrayList<TabModel> = ArrayList()
        val tab1 = TabModel()
        //tab1.fragment = transactionFragment
        tab1.title = "Daftar Transaksi"
        val tab2 = TabModel()
        tab2.fragment = piutangFragment
        tab2.title = "Ringkasan Piutang"
        type.add(tab1)
        type.add(tab2)
        setupViewPager(type)
    }

    private fun setupViewPager(list: List<TabModel>) {
        for (type in list) {
            viewPagerAdapter?.addFragment(type.fragment, type.title)
        }
        _view.viewpager.adapter = viewPagerAdapter
        _view.tabs.tabMode = TabLayout.MODE_FIXED
        _view.tabs.setupWithViewPager(_view.viewpager)

    }

    override fun setSelectTab(position: Int) {
        _view.tabs.getTabAt(position)?.select()
    }

    override fun checkTab(position: Int) {
        setSelectTab(position)
        if (position == 0) {
            //transactionFragment.reloadData()
        } else {
            piutangFragment.reloadData()
        }

    }

    override fun setSelectedDate(date: CalendarDay?) {
    }
}

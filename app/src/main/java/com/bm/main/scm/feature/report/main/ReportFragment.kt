package com.bm.main.scm.feature.report.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bm.main.scm.base.BaseFragment
import com.bm.main.scm.R
import com.bm.main.scm.feature.report.transaction.TransactionActivity
import com.bm.main.scm.feature.report.labarugi.main.MainActivity
import com.bm.main.scm.feature.report.mutasi.MutasiActivity
import com.bm.main.scm.feature.report.stock.StockActivity
import com.bm.main.scm.feature.report.kulakan.KulakanActivity



import kotlinx.android.synthetic.main.fragment_report.view.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ContentFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ContentFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */

class ReportFragment : BaseFragment<ReportPresenter, ReportContract.View>(),
    ReportContract.View {

    private val TAG = ReportFragment::class.java.simpleName

    private val ARGUMENT_PARAM = "ARGUMENT_PARAM"

    private lateinit var _view: View

    private val CODE_LOGIN = 1001

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
        fun newInstance() =
                ReportFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }

    override fun createPresenter(): ReportPresenter {
        return ReportPresenter(activity as Context, this)
    }

    override fun onCreateLayout(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        return inflater.inflate(R.layout.fragment_report, container, false)
    }


    override fun initAction(view: View) {
        _view = view
        renderView()
        getPresenter()?.onViewCreated()
    }

    private fun renderView(){
        _view.btn_stock.setOnClickListener {
            openStock()
        }

        _view.btn_kulakan.setOnClickListener {
            openKulakan()
        }

        _view.btn_transaksion.setOnClickListener {
            openTransaction()
        }

        _view.btn_profit.setOnClickListener {
            openProfit()
        }

        _view.btn_mutasi.setOnClickListener {
            openMutasi()
        }
    }

    override fun openTransaction() {
        startActivity(Intent(activity, TransactionActivity::class.java))
    }

    override fun openKulakan() {
        startActivity(Intent(activity, KulakanActivity::class.java))
    }

    override fun openStock() {
        startActivity(Intent(activity, StockActivity::class.java))
    }

    override fun openProfit() {
        startActivity(Intent(activity, MainActivity::class.java))
    }

    override fun openMutasi() {
        startActivity(Intent(activity, MutasiActivity::class.java))
    }


}

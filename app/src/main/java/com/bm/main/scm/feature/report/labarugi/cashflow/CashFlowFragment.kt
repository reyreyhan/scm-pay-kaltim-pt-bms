package com.bm.main.scm.feature.report.labarugi.cashflow

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.bm.main.scm.base.BaseFragment
import com.bm.main.scm.R
import com.bm.main.scm.utils.Helper
import com.bm.main.scm.models.report.ReportLabaRugi
import kotlinx.android.synthetic.main.fragment_report_cashflow.*
import kotlinx.android.synthetic.main.fragment_report_cashflow.view.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ContentFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ContentFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */

class CashFlowFragment : BaseFragment<CashFlowPresenter, CashFlowContract.View>(),
    CashFlowContract.View {

    private val TAG = CashFlowFragment::class.java.simpleName

    private val ARGUMENT_PARAM = "ARGUMENT_PARAM"

    private lateinit var _view: View


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
                CashFlowFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }

    override fun createPresenter(): CashFlowPresenter {
        return CashFlowPresenter(activity as Context, this)
    }

    override fun onCreateLayout(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        return inflater.inflate(R.layout.fragment_report_cashflow, container, false)
    }


    override fun initAction(view: View) {
        _view = view
        renderView()
        getPresenter()?.onViewCreated()
    }

    private fun renderView(){
        _view.ll_penjualan.setOnClickListener {
//            showPenjualan()
//            hideLabaRugi()
            if(_view.ll_detail_penjualan.isVisible){
                hidePenjualan()
            }
            else{
                showPenjualan()
            }
        }

        _view.ll_labarugi.setOnClickListener {
//            showLabaRugi()
//            hidePenjualan()
            if(_view.ll_detail_labarugi.isVisible){
                hideLabaRugi()
            }
            else{
                showLabaRugi()
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        getPresenter()?.onDestroy()

    }

    @SuppressLint("SetTextI18n")
    override fun setData(data:ReportLabaRugi.Keuangan?) {
        if(data == null){
            tv_gross.text = "Rp 0"
            tv_gross_labarugi.text = "Rp 0"
            tv_diskon.text = "Rp 0"
            tv_diskon_labarugi.text = "Rp 0"
            tv_pembatalan.text = "Rp 0"
            tv_pembatalan_labarugi.text = "Rp 0"
            tv_nett.text = "Rp 0"
            tv_nett_labarugi.text = "Rp 0"
            tv_pajak.text = "Rp 0"
            tv_admin.text = "Rp 0"
            tv_harga_pokok.text = "Rp 0"
            tv_total.text = "Rp 0"
            tv_laba.text = "Rp 0"
            return
        }
        data?.let {keuangan ->
            tv_gross.text = "Rp ${Helper.convertToCurrency(keuangan.penjualan_kotor!!)}"
            tv_gross_labarugi.text = "Rp ${Helper.convertToCurrency(keuangan.penjualan_kotor)}"
            tv_diskon.text = "Rp ${Helper.convertToCurrency(keuangan.diskon!!)}"
            tv_diskon_labarugi.text = "Rp ${Helper.convertToCurrency(keuangan.diskon)}"
            tv_pembatalan.text = "Rp ${Helper.convertToCurrency(keuangan.pembatalan!!)}"
            tv_pembatalan_labarugi.text = "Rp ${Helper.convertToCurrency(keuangan.pembatalan)}"
            tv_nett.text = "Rp ${Helper.convertToCurrency(keuangan.penjualan_bersih!!)}"
            tv_nett_labarugi.text = "Rp ${Helper.convertToCurrency(keuangan.penjualan_bersih)}"
            tv_pajak.text = "Rp ${Helper.convertToCurrency(keuangan.pajak!!)}"
            tv_admin.text = "Rp ${Helper.convertToCurrency(keuangan.admin!!)}"
            val hargaPokok = keuangan.harga_pokok_penjualan ?: "0"
            tv_harga_pokok.text = "Rp ${Helper.convertToCurrency(hargaPokok!!)}"
            tv_total.text = "Rp ${Helper.convertToCurrency(keuangan.total_pendapatan!!)}"
            tv_laba.text = "Rp ${Helper.convertToCurrency(keuangan.laba_kotor!!)}"
        }
    }

    override fun showPenjualan() {
        _view.ll_detail_penjualan.visibility = View.VISIBLE
        _view.iv_penjualan.rotation = 0f
    }

    override fun hidePenjualan() {
        _view.ll_detail_penjualan.visibility = View.GONE
        _view.iv_penjualan.rotation = -90f
    }

    override fun showLabaRugi() {
        _view.ll_detail_labarugi.visibility = View.VISIBLE
        _view.iv_labarugi.rotation = 0f
    }

    override fun hideLabaRugi() {
        _view.ll_detail_labarugi.visibility = View.GONE
        _view.iv_labarugi.rotation = -90f
    }

}

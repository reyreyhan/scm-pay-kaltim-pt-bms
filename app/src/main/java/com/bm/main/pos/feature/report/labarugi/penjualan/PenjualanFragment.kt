package com.bm.main.pos.feature.report.labarugi.penjualan

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.pos.base.BaseFragment
import com.bm.main.pos.R
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.utils.AppConstant
import com.bm.main.pos.feature.report.labarugi.penjualan.PenjualanAdapter
import com.bm.main.pos.models.FilterDialogDate
import com.bm.main.pos.models.report.ReportLabaRugi
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.fragment_report_penjualan.view.*


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ContentFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ContentFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */

class PenjualanFragment : BaseFragment<PenjualanPresenter, PenjualanContract.View>(),
    PenjualanContract.View {

    private val TAG = PenjualanFragment::class.java.simpleName

    private val ARGUMENT_PARAM = "ARGUMENT_PARAM"

    private lateinit var _view: View
    val adapter = PenjualanAdapter()


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
                PenjualanFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }

    override fun createPresenter(): PenjualanPresenter {
        return PenjualanPresenter(activity as Context, this)
    }

    override fun onCreateLayout(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        return inflater.inflate(R.layout.fragment_report_penjualan, container, false)
    }


    override fun initAction(view: View) {
        _view = view
        renderView()
//        getPresenter()?.onViewCreated()
    }

    private fun renderView(){
        val layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        _view.rv_list.layoutManager = layoutManager
        _view.rv_list.adapter = adapter

    }

    override fun setList(list: List<ReportLabaRugi.Penjualan>) {
        _view.rv_list.visibility = View.VISIBLE
        _view.tv_error.visibility = View.GONE
        adapter.clearAdapter()
        adapter.setItems(list)
    }

    override fun showErrorMessage(isToday: Boolean?, code: Int, msg: String) {
        if(code == RestException.CODE_USER_NOT_FOUND){
            restartLoginActivity()
        }
        else{
            _view.rv_list.visibility = View.GONE
            _view.tv_error.visibility = View.VISIBLE
            _view.tv_error.text = msg
        }



    }

    override fun setYesterdayData(data: ReportLabaRugi) {

    }

    override fun setDate(firstDate: String, lastDate: String) {
        TODO("Not yet implemented")
    }

    override fun reloadData() {
        TODO("Not yet implemented")
    }

    override fun openFilter(data: FilterDialogDate?) {
        TODO("Not yet implemented")
    }

    override fun onDetach() {
        super.onDetach()
        getPresenter()?.onDestroy()

    }

//    override fun loadData() {
//    }
//
//    override fun getToday(): CalendarDay? {
//        return null
//    }
//
//    override fun setFilterDateSelected(data: FilterDialogDate?) {
//    }
//
//    override fun getFilterDateSelected(): FilterDialogDate? {
//        return null
//    }

    override fun setData(list: List<ReportLabaRugi.Penjualan>?) {
        getPresenter()?.onCheck(list)
    }

    override fun setData(data: ReportLabaRugi) {
        TODO("Not yet implemented")
    }

}

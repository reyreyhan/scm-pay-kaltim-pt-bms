package com.bm.main.scm.feature.qris

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bm.main.fpl.templates.StickHeaderItemDecoration
import com.bm.main.scm.R
import com.bm.main.scm.rabbit.QrMutation
import com.bm.main.scm.ui.LinearItemDecoration
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.qr_saldo_fragment.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class QrSaldoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.qr_saldo_fragment, container, false)

    private val dateFormat by lazy { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    private val itemDateFormat by lazy { SimpleDateFormat("d MMMM yyyy", Locale.getDefault()) }
    private val respDateFormat by lazy {
        SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss.SSS",
            Locale.getDefault()
        )
    }

//    private val qrisViewModel by lazy {
//        ViewModelProvider(parentFragment!!, userComponent!!.qrisComponentFactory()).get(
//            QrisViewModel::class.java
//        )
//    }

    private var disposable: Disposable? = null

    private val adapter by lazy { MutationAdapter(ArrayList()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_trx.adapter = adapter
        rv_trx.addItemDecoration(LinearItemDecoration())
        rv_trx.addItemDecoration(StickHeaderItemDecoration(adapter))

        loadData(Date())

        swipe.setOnRefreshListener {
            listDates.clear()
            adapter.listItem.clear()
            adapter.notifyDataSetChanged()
            loadData(Date())
        }
    }

    private fun loadData(date: Date) {
        val calendarNow = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -30) }
        val calendar = Calendar.getInstance().apply { time = date }

        disposable?.dispose()
//        disposable = qrisViewModel.service.getMutasiSaldo(
//            PreferenceClass.getString("id_speedcash"),
//            dateFormat.format(date)
//        ).observeOn(AndroidSchedulers.mainThread()).subscribe({ result ->
//            swipe.isRefreshing = false
//
//            if (result.rc == "00" && result.data.isNotEmpty()) {
//                result.data
//                    .sortedByDescending { it.mutation_date }
//                    .forEach {
//                        it.time = respDateFormat.parse(it.mutation_date)
//                        it.mutation_date = itemDateFormat.format(it.time)
//
//                        if (!listDates.contains(it.mutation_date)) {
//                            listDates.add(it.mutation_date)
//                            adapter.listItem.add(QrItem(0, it))
//                        }
//                        adapter.listItem.add(QrItem(1, it))
//                    }
//                adapter.notifyItemRangeInserted(adapter.listItem.size, result.data.size)
//            }
//
//            if (calendar.get(Calendar.DAY_OF_YEAR) > calendarNow.get(Calendar.DAY_OF_YEAR)) {
//                loadData(calendar.apply { add(Calendar.DAY_OF_YEAR, -1) }.time)
//            } else if (adapter.listItem.isEmpty()) {
//                t_error.visibility = View.VISIBLE
//            }
//        }, {
//            if (calendar.get(Calendar.DAY_OF_YEAR) > calendarNow.get(Calendar.DAY_OF_YEAR)) {
//                loadData(calendar.apply { add(Calendar.DAY_OF_YEAR, -1) }.time)
//            } else if (adapter.listItem.isEmpty()) {
//                t_error.visibility = View.VISIBLE
//            }
//        })
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }

    data class QrItem(var type: Int = 0, var item: QrMutation)

    private val listDates by lazy { ArrayList<String>() }

    class MutationAdapter(val listItem: ArrayList<QrItem>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>(),
        StickHeaderItemDecoration.StickyHeaderInterface {
        private val itemTimeFormat by lazy { SimpleDateFormat("hh:mm:ss", Locale.getDefault()) }

        override fun getItemViewType(position: Int): Int = listItem[position].type

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            if (viewType == 0) HeadViewHolder(parent) else ItemViewHolder(parent)

        override fun getItemCount(): Int = listItem.size

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val item = listItem[position].item
            if (holder is HeadViewHolder) {
                holder.text.text = item.mutation_date
            } else {
                holder as ItemViewHolder
                holder.trx_id.text = "#" + item.trx_reff
                holder.trx_time.text = itemTimeFormat.format(item.time)
                holder.trx_name.text = item.merchant_reff
//                if (item.debit > 0) {
//                    holder.trx_nominal.text = "-Rp " + item.debit
//                    holder.trx_nominal.setTextColor(Color.parseColor("#b40b3a"))
//                } else {
//                    holder.trx_nominal.text = "Rp " + item.credit
//                    holder.trx_nominal.setTextColor(Color.parseColor("#139943"))
//                }
            }
        }

        override fun isHeader(itemPosition: Int): Boolean = listItem[itemPosition].type == 0

        override fun getHeaderLayout(headerPosition: Int): Int = R.layout.item_qr_trx_head

        override fun getHeaderPositionForItem(itemPosition: Int): Int = listItem.subList(
            0,
            itemPosition
        ).lastOrNull { it.type == 0 }?.let { listItem.indexOf(it) } ?: RecyclerView.NO_POSITION

        override fun bindHeaderData(header: View?, headerPosition: Int) {
            header?.findViewById<TextView>(R.id.text)?.text = listItem[headerPosition].item.mutation_date
        }
    }

    class HeadViewHolder(
        val parent: ViewGroup,
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_qr_trx_head,
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(view) {
        val text = view.findViewById<TextView>(R.id.text)
    }

    class ItemViewHolder(
        val parent: ViewGroup,
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_qr_mutasi,
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(view) {
        val trx_id = view.findViewById<TextView>(R.id.trx_id)
        val trx_nominal = view.findViewById<TextView>(R.id.trx_nominal)
        val trx_name = view.findViewById<TextView>(R.id.trx_name)
        val trx_time = view.findViewById<TextView>(R.id.trx_time)
    }
}
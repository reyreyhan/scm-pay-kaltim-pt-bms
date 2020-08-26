package com.bm.main.scm.feature.qris

import androidx.paging.ItemKeyedDataSource
import com.github.kittinunf.fuel.httpPost
import com.google.gson.Gson
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class QrTrxDataSource: ItemKeyedDataSource<Date, QrTransactionItem>() {

    private var date = Date()
    private val dateFormat by lazy { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) }
    private val respDateFormat by lazy {
        SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            Locale.getDefault()
        )
    }
    private val itemDateFormat by lazy { SimpleDateFormat("d MMMM yyyy", Locale.getDefault()) }
    private val listDates by lazy { ArrayList<String>() }

    override fun loadInitial(
        params: LoadInitialParams<Date>,
        callback: LoadInitialCallback<QrTransactionItem>
    ) {
        val reqdata = listOf(
            "id_sc" to "12016",
//            "date" to "2020-01-29"
//            "id_sc" to PreferenceClass.getString("id_speedcash"),
            "date" to dateFormat.format(date)
        )

        "https://mpdesktop.fastpay.co.id/mobile_android_3_0_X/index.php/qris/get_transaksi"
            .httpPost(reqdata)
            .responseString { request, response, result ->
                if (response.statusCode == 200) {
                    Timber.e("response: ${result.get()}")
                    val res = Gson().fromJson(result.get(), QrTransactionResult::class.java)
                    if (res.rc == "00") {
                        val listItem by lazy { ArrayList<QrTransactionItem>() }

                        res.data
                            .sortedByDescending { it.time_request }
                            .forEach {
                                it.time = respDateFormat.parse(it.time_request)
                                it.time_request = itemDateFormat.format(it.time)

                                if (!listDates.contains(it.time_request)) {
                                    listDates.add(it.time_request)
                                    listItem.add(QrTransactionItem(0, it.apply { isDate = true }))
                                }
                                listItem.add(QrTransactionItem(1, it))
                            }
                        callback.onResult(listItem)
                        date = Calendar.getInstance().apply {
                            time = date
                            add(Calendar.DAY_OF_YEAR, -1)
                        }.time
                    }
                } else {
                    callback.onError(Throwable())
                }
            }
    }

    override fun loadAfter(params: LoadParams<Date>, callback: LoadCallback<QrTransactionItem>) {
        val reqdata = listOf(
            "id_sc" to "12016",
//            "date" to "2020-01-29"
//            "id_sc" to PreferenceClass.getString("id_speedcash"),
            "date" to dateFormat.format(date)
        )

        "https://mpdesktop.fastpay.co.id/mobile_android_3_0_X/index.php/qris/get_transaksi"
            .httpPost(reqdata)
            .responseString { request, response, result ->
                if (response.statusCode == 200) {
                    Timber.e("response: ${result.get()}")
                    val res = Gson().fromJson(result.get(), QrTransactionResult::class.java)
                    if (res.rc == "00") {
                        val listItem by lazy { ArrayList<QrTransactionItem>() }

                        res.data
                            .sortedByDescending { it.time_request }
                            .forEach {
                                it.time = respDateFormat.parse(it.time_request)
                                it.time_request = itemDateFormat.format(it.time)

                                if (!listDates.contains(it.time_request)) {
                                    listDates.add(it.time_request)
                                    listItem.add(QrTransactionItem(0, it.apply { isDate = true }))
                                }
                                listItem.add(QrTransactionItem(1, it))
                            }
                        callback.onResult(listItem)
                        date = Calendar.getInstance().apply {
                            time = date
                            add(Calendar.DAY_OF_YEAR, -1)
                        }.time
                    }
                } else {
                    callback.onError(Throwable())
                }
            }
    }

    override fun loadBefore(params: LoadParams<Date>, callback: LoadCallback<QrTransactionItem>) {}

    override fun getKey(item: QrTransactionItem): Date = date
}
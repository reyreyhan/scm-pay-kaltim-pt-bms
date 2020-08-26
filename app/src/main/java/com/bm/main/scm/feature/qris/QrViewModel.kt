package com.bm.main.scm.feature.qris

import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import java.util.*

class QrViewModel : ViewModel() {

    var trxDataSource: QrTrxDataSource? = null
    val trxDataSourceFactory by lazy {
        object : DataSource.Factory<Date, QrTransactionItem>() {
            override fun create(): DataSource<Date, QrTransactionItem> = QrTrxDataSource().also { trxDataSource = it }
        }
    }

    val trxList by lazy {
        LivePagedListBuilder<Date, QrTransactionItem>(trxDataSourceFactory, 1).build()
    }
}
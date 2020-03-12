package com.bm.main.pos.feature.qris

import java.util.Date

data class QrTransactionResult(val rc: String, val data: List<QrTransactionItemType>)

data class QrTransactionItem(var type: Int = 0, var item: QrTransactionItemType)

data class QrTransactionItemType(
    val id_transaksi: String,
    val nominal: Int,
    val nominal_admin: Int,
    val fee: Int,
    var time_request: String,
    val issuer_reff: String,
    val buyer_reff: String,
    @Transient var nominal_show: Int,
    @Transient var buyer_reff_show: String,
    @Transient var minus: Boolean = false,
    @Transient var isDate: Boolean = false,
    @Transient var time: Date
)
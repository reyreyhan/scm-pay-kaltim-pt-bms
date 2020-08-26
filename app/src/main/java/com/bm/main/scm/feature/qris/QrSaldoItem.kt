package com.bm.main.scm.feature.qris

import java.util.*

data class QrSaldoItem(
    val trx_reff: String,
    val mutation_id: String,
    val credit: Float,
    val debit: Float,
    var mutation_date: String,
    val merchant_reff: String,
    @Transient var time: Date
)
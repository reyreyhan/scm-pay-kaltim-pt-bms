package com.bm.main.scm.feature.qris

import java.util.*

data class QrSaldoItem(
    val mutation_id: String,
    var mutation_date: String,
    val merchant_reff: String,
    val trx_reff: String,
    val credit: Float,
    val debit: Float,
    val current_balance: Float,
    @Transient var time: Date
)
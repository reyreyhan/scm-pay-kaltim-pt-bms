package com.bm.main.scm.rabbit

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
@Keep
data class CheckResponse(
    val rc: String = "",
    val rd: String = "",
    val result: List<CheckResult> = emptyList()
)

@JsonClass(generateAdapter = true)
@Keep
data class CheckResult(
    val url_qr: String,
    val nmid: String,
    val id_speedcash: String,
    val nama_toko: String,
    val nama_pemilik: String
)

@JsonClass(generateAdapter = true)
@Keep
data class TransactionResponse(
    val rc: String = "",
    val rd: String = "",
    val data: List<QrTransaction> = emptyList()
)

@JsonClass(generateAdapter = true)
@Keep
data class QrTransaction(
    val id_transaksi: String,
    val nominal: Int,
    val nominal_admin: Int,
    val fee: Int,
    var time_request: String,
    val issuer_reff: String,
    val buyer_reff: String,
    @Transient var minus: Boolean = false,
    @Transient var time: Date = Date()
)

@JsonClass(generateAdapter = true)
@Keep
data class MutationResponse(
    val rc: String = "",
    val rd: String = "",
    val data: List<QrMutation> = emptyList()
)

@JsonClass(generateAdapter = true)
@Keep
data class QrMutation(
    val trx_reff: String,
    val mutation_id: String,
    val credit: Float,
    val debit: Float,
    var mutation_date: String,
    val merchant_reff: String,
    @Transient var time: Date = Date()
)
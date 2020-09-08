package com.bm.main.scm.rabbit

import android.os.Parcel
import android.os.Parcelable
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
    val id: String,
    val nmid: String,
    val id_outlet: String,
    val id_speedcash: String,
    val nama_toko: String,
    val nama_pemilik: String,
    val hp: String,
    val email: String,
    val created_date: String,
    val valid_date: String?,
    val userid: String?,
    val status: String,
    val json_raw: String?,
    val raw_request: String?,
    val raw_response: String?,
    val url_qr: String
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
    val nominal: String,
    val nominal_admin: String,
    val fee: String,
    var time_request: String,
    var merchant_repo: String,
    val issuer_reff: String,
    val buyer_reff: String,
    val nmid: String,
    val id_merchant: String,
    @Transient var minus: Boolean = false,
    var time: Long? = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
       parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id_transaksi)
        parcel.writeString(nominal)
        parcel.writeString(nominal_admin)
        parcel.writeString(fee)
        parcel.writeString(time_request)
        parcel.writeString(merchant_repo)
        parcel.writeString(issuer_reff)
        parcel.writeString(buyer_reff)
        parcel.writeString(nmid)
        parcel.writeString(id_merchant)
        parcel.writeByte(if (minus) 1 else 0)
        parcel.writeLong(time!!)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QrTransaction> {
        override fun createFromParcel(parcel: Parcel): QrTransaction {
            return QrTransaction(parcel)
        }

        override fun newArray(size: Int): Array<QrTransaction?> {
            return arrayOfNulls(size)
        }
    }
}

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
    val mutation_id: Int,
    val mutation_type: String,
    var mutation_date: String,
    val trx_reff: String,
    val merchant_reff: String,
    val product_id: String,
    val product_name: String,
    val credit: String,
    val debit: String,
    val current_balance: String,
    @Transient var time: Date = Date()
)

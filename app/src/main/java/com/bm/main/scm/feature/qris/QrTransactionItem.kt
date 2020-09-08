package com.bm.main.scm.feature.qris

import android.os.Parcel
import android.os.Parcelable
import java.util.*

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
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        TODO("time")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id_transaksi)
        parcel.writeInt(nominal)
        parcel.writeInt(nominal_admin)
        parcel.writeInt(fee)
        parcel.writeString(time_request)
        parcel.writeString(issuer_reff)
        parcel.writeString(buyer_reff)
        parcel.writeInt(nominal_show)
        parcel.writeString(buyer_reff_show)
        parcel.writeByte(if (minus) 1 else 0)
        parcel.writeByte(if (isDate) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QrTransactionItemType> {
        override fun createFromParcel(parcel: Parcel): QrTransactionItemType {
            return QrTransactionItemType(parcel)
        }

        override fun newArray(size: Int): Array<QrTransactionItemType?> {
            return arrayOfNulls(size)
        }
    }
}
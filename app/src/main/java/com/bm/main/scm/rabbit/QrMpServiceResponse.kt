package com.bm.main.scm.rabbit

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Keep
data class CheckQrMpResponse(
    val response_code: String = "",
    val keterangan: String = "",
    val step: String = "",
    val data:List<CheckQr>? = null
)

@JsonClass(generateAdapter = true)
@Keep
data class CheckQr(
    val id_outlet: String? = null,
    val kategori_usaha: String? = null,
    val nama: String? = null,
    val jenis_kelamin: String? = null,
    val nik: String? = null,
    val kriteria_usaha: String? = null,
    val izin_usaha: String? = null,
    val npwp: String? = null,
    val id_propinsi: String? = null,
    val id_kota: String? = null,
    val id_kecamatan: String? = null,
    val id_kelurahan: String? = null,
    val alamat: String? = null,
    val date_created: String? = null,
    val date_verified: String? = null,
    val is_verified: String? = null
)

@JsonClass(generateAdapter = true)
@Keep
data class FastpayDataResponse(
    val response_code: String = "",
    val keterangan: String = "",
    val result: FastpayData = FastpayData()
)

@JsonClass(generateAdapter = true)
@Keep
data class FastpayData(
    val id_outlet:String? = "",
    val id_setting_komisi:String? = "",
    val nama_pemilik:String? = "",
    val alamat_pemilik:String? = "",
    val kota:String? = "",
    val kode_pos:String? = "",
    val kota_regional:String? = "",
    val notelp_pemilik:String? = "",
    val tanggal_aktifasi:String? = "",
    val tanggal_registrasi:String? = "",
    val biaya_aktifasi:String? = "",
    val is_active:String? = "",
    val is_regional:String? = "",
    val upline:String? = "",
    val nama_outlet:String? = "",
    val alamat_outlet:String? = "",
    val notelp_outlet:String? = "",
    val ym:String? = "",
    val norek_an:String? = "",
    val gtalk:String? = "",
    val last_active:String? = "",
    val jenis_struk:String? = "",
    val id_tipe_outlet:String? = "",
    val keterangan:String? = "",
    val id_propinsi:String? = "",
    val email:String? = "",
    val tgl_lahir:String? = "",
    val no_ktp:String? = "",
    val nama_ibu_kandung:String? = "",
    val is_notelp_active:String? = "",
    val is_need_data_completion:String? = "",
    val id_group:String? = "",
    val id_kota:String? = "",
    val id_segmentasi:String? = "",
    val minimum_balance:String? = "",
    val longitude:String? = "",
    val latitude:String? = "",
    val id_kecamatan:String? = "",
    val id_kelurahan:String? = "",
    val rt:String? = "",
    val rw:String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id_outlet)
        parcel.writeString(id_setting_komisi)
        parcel.writeString(nama_pemilik)
        parcel.writeString(alamat_pemilik)
        parcel.writeString(kota)
        parcel.writeString(kode_pos)
        parcel.writeString(kota_regional)
        parcel.writeString(notelp_pemilik)
        parcel.writeString(tanggal_aktifasi)
        parcel.writeString(tanggal_registrasi)
        parcel.writeString(biaya_aktifasi)
        parcel.writeString(is_active)
        parcel.writeString(is_regional)
        parcel.writeString(upline)
        parcel.writeString(nama_outlet)
        parcel.writeString(alamat_outlet)
        parcel.writeString(notelp_outlet)
        parcel.writeString(ym)
        parcel.writeString(norek_an)
        parcel.writeString(gtalk)
        parcel.writeString(last_active)
        parcel.writeString(jenis_struk)
        parcel.writeString(id_tipe_outlet)
        parcel.writeString(keterangan)
        parcel.writeString(id_propinsi)
        parcel.writeString(email)
        parcel.writeString(tgl_lahir)
        parcel.writeString(no_ktp)
        parcel.writeString(nama_ibu_kandung)
        parcel.writeString(is_notelp_active)
        parcel.writeString(is_need_data_completion)
        parcel.writeString(id_group)
        parcel.writeString(id_kota)
        parcel.writeString(id_segmentasi)
        parcel.writeString(minimum_balance)
        parcel.writeString(longitude)
        parcel.writeString(latitude)
        parcel.writeString(id_kecamatan)
        parcel.writeString(id_kelurahan)
        parcel.writeString(rt)
        parcel.writeString(rw)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FastpayData> {
        override fun createFromParcel(parcel: Parcel): FastpayData {
            return FastpayData(parcel)
        }

        override fun newArray(size: Int): Array<FastpayData?> {
            return arrayOfNulls(size)
        }
    }
}

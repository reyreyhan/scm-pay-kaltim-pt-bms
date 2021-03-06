package com.bm.main.scm.models.user

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by Richie on 7/15/17.
 */

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class Profile : Serializable {
    var password: String? = ""
    var nama_lengkap: String? = ""
    var tanggal: String? = ""
    var kota: String? = ""
    var master: String? = ""
    var alamat: String? = ""
    var email: String? = ""
    var no_telp: String? = ""
    var level: String? = ""
    var blokir: String? = ""
    var id_session: String? = ""
    var gbr: String? = ""
    var paket: String? = ""
    var fastpay_id: String? = ""
    var fastpay_pin: String? = ""
    var omset: String? = ""

    fun json(): String {
        return Gson().toJson(this)
    }
}

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class User : Serializable {
//    var key: String? = null
//    var email: String? = null
//    var nama_lengkap: String? = ""
//    var no_telp: String? = ""
//    var alamat: String? = ""
//    var kota: String? = ""
//    var omset: String? = "0"
//    var gbr: String? = ""
//    var level: String? = ""
    var id_session: String? = null
    var no_telp:String? = null
    var level:String? = null
    var master:String? = null
    var fastpay_id:String? = null
    var fastpay_pin:String? = null

    fun json(): String {
        return Gson().toJson(this)
    }
}

data class RegisterMerchantRequest(
    var email: String,
    var additional_data: String,
    var no_telp:String,
    var nama_lengkap:String,
    var nama_toko:String,
    var password:String,
    var prop_code:String,
    var city_code:String,
    var kec_code:String,
    var kel_code:String,
    var kode_pos:String,
    var alamat:String
)
package com.bm.main.scm.models.user.merchant

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by Richie on 7/15/17.
 */

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class MerchantUser : Serializable {
    var password: String? = null
    var nama_lengkap: String? = null
    var tanggal: String? = null
    var kota: String? = null
    var alamat: String? = null
    var email: String? = null
    var blokir: String? = null
    var gbr: String? = null
    var paket: String? = null
    var id_session: String? = null
    var no_telp: String? = null
    var level: String? = null
    var master: String? = null
    var fastpay_id: String? = null
    var fastpay_pin: String? = null

    fun json(): String {
        return Gson().toJson(this)
    }
}

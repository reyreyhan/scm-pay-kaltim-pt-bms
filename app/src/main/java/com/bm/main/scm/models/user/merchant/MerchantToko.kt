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
class MerchantToko : Serializable {
    var id_toko: String? = null
    var user: String? = null
    var nama_toko: String? = null
    var email: String? = null
    var nohp: String? = null
    var alamat: String? = null

    fun json(): String {
        return Gson().toJson(this)
    }
}

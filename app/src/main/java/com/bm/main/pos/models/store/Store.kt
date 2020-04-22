package com.bm.main.pos.models.store

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by Richie on 7/15/17.
 */

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class Store : Serializable {
    var nama_toko: String? = null
    var nohp: String? = ""
    var alamat: String? = ""
    var email: String? = ""

    fun json(): String {
        return Gson().toJson(this)
    }
}

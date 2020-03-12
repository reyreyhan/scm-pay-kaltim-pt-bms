package com.bm.main.pos.models.user

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by Richie on 7/15/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
class User : Serializable {
    var key: String? = null
    var email: String? = null
    var nama_lengkap: String? = ""
    var no_telp: String? = ""
    var alamat: String? = ""
    var kota: String? = ""
    var omset: String? = "0"
    var gbr: String? = ""
    var level: String? = ""

    fun json(): String {
        return Gson().toJson(this)
    }
}

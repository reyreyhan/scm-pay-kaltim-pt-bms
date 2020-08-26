package com.bm.main.scm.models.staff

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by Richie on 7/15/17.
 */

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class Staff : Serializable {
    var key: String? = null
    var email: String? = null
    var nama_lengkap: String? = ""
    var no_telp: String? = ""
    var alamat: String? = ""
    var kota: String? = ""
    var gbr: String? = ""
    var level: String? = ""

    fun json(): String {
        return Gson().toJson(this)
    }
}

package com.bm.main.pos.models.customer

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by Richie on 7/15/17.
 */

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class Customer : Serializable {
    var id_pelanggan: String? = null
    var email: String? = ""
    var nama_pelanggan: String? = ""
    var telpon: String? = ""
    var alamat: String? = ""
    var gbr: String? = ""


    fun json(): String {
        return Gson().toJson(this)
    }
}

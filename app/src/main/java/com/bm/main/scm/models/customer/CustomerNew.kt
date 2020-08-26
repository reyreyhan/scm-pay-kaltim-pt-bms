package com.bm.main.scm.models.customer

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by Richie on 7/15/17.
 */

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class CustomerNew : Serializable {
    var id_pelanggan: String? = null
    var email: String? = ""
    var nama_pelanggan: String? = ""
    var telpon: String? = ""
    var alamat: String? = ""
    var gbr: String? = ""
    var aktiv: String? = ""
    var total_hutang:String? = ""
    var total_bayar:String? = ""
    var sisa_hutang:String? = ""


    fun json(): String {
        return Gson().toJson(this)
    }
}

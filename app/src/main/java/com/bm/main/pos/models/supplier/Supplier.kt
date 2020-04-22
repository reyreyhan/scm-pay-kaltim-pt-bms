package com.bm.main.pos.models.supplier

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by Richie on 7/15/17.
 */

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class Supplier : Serializable {
    var id_supplier: String? = null
    var nama_supplier: String? = ""
    var email: String? = ""
    var telpon: String? = ""
    var alamat: String? = ""
    var profinsi: String? = ""
    var kota: String? = ""
    var gbr: String? = ""

    fun json(): String {
        return Gson().toJson(this)
    }
}

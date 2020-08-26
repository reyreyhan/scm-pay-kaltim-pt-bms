package com.bm.main.scm.models.report

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by Richie on 7/15/17.
 */

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class ReportStock : Serializable {
    val id_barang : String? = null
    val nama_barang : String? = "0"
    val tanggal_awal : String? = null
    val tanggal_akhir : String? = "0"
    val terjual : String? = null
    val stok : String? = "0"
    val minimal_stok : String? = null
    var gbr:String? = null
    var folder:String? = null
    val datastok: List<Detail>? = null

    @JsonIgnoreProperties(ignoreUnknown = true)
    class Detail : Serializable {
        val sisa_stok: String? = "0"
        val minimal_stok: String? = "0"
        val tanggal: String? = null

        fun json(): String {
            return Gson().toJson(this)
        }
    }

    fun json(): String {
        return Gson().toJson(this)
    }
}

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
class ReportKulakan : Serializable {
    var totalordersemua : String? = "0"
    var tanggal : String? = "0"
    var detail: List<Detail>? = null

    @Keep
    @JsonIgnoreProperties(ignoreUnknown = true)
    class Detail : Serializable {
        var nama_barang: String? = null
        var jumlah: String? = null
        var no_invoice: String? = null
        var tanggal: String? = null
        var id_supplier: String? = null
        var totalorder: String? = null
        var status: String? = null
        var nama_supplier: String? = null

        fun json(): String {
            return Gson().toJson(this)
        }
    }

    fun json(): String {
        return Gson().toJson(this)
    }
}

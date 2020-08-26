package com.bm.main.scm.models.transaction

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by Richie on 7/15/17.
 */

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class RequestKulakan : Serializable {
    var key: String ?= null
    var tipe_pembayaran: Int? = 1
    var total_order: Int? = 0
    var id_supplier: String? = null
    var jatuh_tempo: String? = null
    var barang: List<Barang>? = null

    @Keep
    @JsonIgnoreProperties(ignoreUnknown = true)
    class Barang : Serializable {
        var id_barang: String? = null
        var jumlah_barang: Int? = 0

        fun json(): String {
            return Gson().toJson(this)
        }
    }

    fun json(): String {
        return Gson().toJson(this)
    }
}

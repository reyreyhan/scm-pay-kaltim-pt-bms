package com.bm.main.pos.models.transaction

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by Richie on 7/15/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
class RequestTransaction : Serializable {
    var key: String ?= null
    var tipe_pembayaran: Int? = 1
    var jumlah_pembayaran: Int? = 0
    var total_order: Int? = 0
    var id_pelanggan: String? = null
    var jatuh_tempo: String? = null
    var barang: List<Barang>? = null

    @JsonIgnoreProperties(ignoreUnknown = true)
    class Barang : Serializable {
        var id_barang: String? = null
        var jumlah_barang: Int? = 0
        var catatan: String? = ""

        fun json(): String {
            return Gson().toJson(this)
        }
    }

    fun json(): String {
        return Gson().toJson(this)
    }
}

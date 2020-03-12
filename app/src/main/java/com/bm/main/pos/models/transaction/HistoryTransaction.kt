package com.bm.main.pos.models.transaction

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by Richie on 7/15/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
class HistoryTransaction : Serializable {
    var jumlah_transaksi : String? = "0"
    var jumlah_profit : String? = "0"
    var totalordersemua : String? = "0"
    var tanggal : String? = "0"
    var detail: List<Transaction>? = null

    fun json(): String {
        return Gson().toJson(this)
    }
}

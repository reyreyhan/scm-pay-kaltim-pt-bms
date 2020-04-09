package com.bm.main.pos.models.transaction

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by Richie on 7/15/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
class HistoryTransaction : Serializable {
    var jumlah_transaksi : Int? = null
    var jumlah_profit : Int? = null
    var totalordersemua : String? = null
    var tanggal : String? = null
    var detail: List<Transaction>? = null

    fun json(): String {
        return Gson().toJson(this)
    }
}

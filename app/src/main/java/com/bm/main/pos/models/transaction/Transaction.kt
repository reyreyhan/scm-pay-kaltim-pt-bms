package com.bm.main.pos.models.transaction

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by Richie on 7/15/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
class Transaction : Serializable {
    var nama_barang: String? = null
    var nama_supplier: String? = null
    var gbr: String? = null
    var no_invoice: String? = null
    var tanggal: String? = null
    var pembayaran: String? = null
    var totalorder: String? = null
    var totalomset: String? = null
    var totalprofit: String? = null
    var status: String? = null
    var type: String? = null
    var pos: Int? = 0

    fun json(): String {
        return Gson().toJson(this)
    }
}

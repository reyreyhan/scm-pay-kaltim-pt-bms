package com.bm.main.pos.models.report

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by Richie on 7/15/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
class ReportTransaksi : Serializable {
    var id_barang: String? = null
    var nama_barang: String? = null
    var tanggal_awal: String? = null
    var tanggal_akhir: String? = null
    var totalorder: String? = null
    var penjualan: String? = null
    var stok_terakhir: String? = null
    var harga_beli: String? = null
    var harga_jual: String? = null
    var raba_rugi: String? = null
    var gbr:String? = null
    var folder:String? = null

    fun json(): String {
        return Gson().toJson(this)
    }
}

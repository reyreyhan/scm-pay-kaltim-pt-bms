package com.bm.main.pos.models.report

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by Richie on 7/15/17.
 */

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class ReportMutasi : Serializable {
    val info: Info? = null
    val transaksi: List<Transaksi>? = null

    @Keep
    @JsonIgnoreProperties(ignoreUnknown = true)
    class Info : Serializable {
        val penjualan_bersih: String? = "0"
        val jumlah_transaksi: String? = "0"


        fun json(): String {
            return Gson().toJson(this)
        }
    }

    @Keep
    @JsonIgnoreProperties(ignoreUnknown = true)
    class Transaksi : Serializable {
        val no_invoice: String? = null
        val tanggal: String? = null
        val omset: String? = "0"
        val margin: String? = "0"
        val barang: List<Barang> ?= null

        fun json(): String {
            return Gson().toJson(this)
        }

        @Keep
        @JsonIgnoreProperties(ignoreUnknown = true)
        class Barang : Serializable {
            val id_barang: String? = null
            val nama_barang: String? = ""
            val margin: String? = "0"
            val laba: String? = "0"
            val qty: String? = "0"
            val harga_jual: String? = "0"
            val harga_beli: String? = "0"

            fun json(): String {
                return Gson().toJson(this)
            }
        }
    }

    fun json(): String {
        return Gson().toJson(this)
    }
}

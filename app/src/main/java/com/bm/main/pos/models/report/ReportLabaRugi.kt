package com.bm.main.pos.models.report

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by Richie on 7/15/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
class ReportLabaRugi : Serializable {
    val info: Info? = null
    val laporan_keuangan: Keuangan? = null
    val laporan_penjualan: List<Penjualan>? = null

    @JsonIgnoreProperties(ignoreUnknown = true)
    class Info : Serializable {
        val penjualan_bersih: String? = "0"
        val rata_rata: String? = "0"
        val jumlah_transaksi: String? = "0"
        val omset: String? = "0"
        val modal: String? = "0"
        val jumlah_barang: String? = "0"


        fun json(): String {
            return Gson().toJson(this)
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    class Penjualan : Serializable {
        val id_barang: String? = null
        val nama_barang: String? = ""
        val jumlah: String? = "0"
        val totalharga: String? = "0"
        val harga: String? = "0"
        val status: String? = "sukses"

        fun json(): String {
            return Gson().toJson(this)
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    class Keuangan : Serializable {
        val penjualan_kotor: String? = "0"
        val diskon: String? = "0"
        val pembatalan: String? = "0"
        val penjualan_bersih: String? = "0"
        val pajak: String? = "0"
        val admin: String? = "0"
        val total_pendapatan: String? = "0"
        var harga_pokok_penjualan: String? = "0"
        val laba_kotor: String? = "0"

        fun json(): String {
            return Gson().toJson(this)
        }
    }

    fun json(): String {
        return Gson().toJson(this)
    }
}

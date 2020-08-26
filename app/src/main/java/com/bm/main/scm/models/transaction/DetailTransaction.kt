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
class DetailTransaction : Serializable {
    var struk: Struk? = null
    var data: List<Data>? = null
    var url_struk: String? = null

    @Keep
    @JsonIgnoreProperties(ignoreUnknown = true)
    class Struk : Serializable {
        var nama_toko: String? = null
        var email: String? = null
        var nohp: String? = null
        var alamat: String? = null
        var no_invoice: String? = null
        var operator: String? = null
        var id_pelanggan: String? = null
        var nama_pelanggan: String? = null
//        var id_supplier: String? = null
//        var nama_supplier: String? = null
        var tanggal: String? = null
        var pembayaran: String? = null
        var totalorder: String? = null
        var totalbayar: String? = null
        var kembalian: String? = null
        var status: String? = null
        var keterangan: String? = null
        var jatuh_tempo: String? = null

        fun json(): String {
            return Gson().toJson(this)
        }
    }

    @Keep
    @JsonIgnoreProperties(ignoreUnknown = true)
    class Data : Serializable {
        var id_penjualan: String? = null
        var id_pelanggan: String? = null
        var id_barang: String? = null
        var user: String? = null
        var no_invoice: String? = null
        var jumlah: String? = null
        var harga: String? = null
        var totalharga: String? = null
        var totalmodal: String? = null
        var tanggal: String? = null
        var status: String? = null
        var catatan: String? = null
        var sisa_stok: String? = null
        var kodebarang: String? = null
        var nama_barang: String? = null

        fun json(): String {
            return Gson().toJson(this)
        }
    }

    fun json(): String {
        return Gson().toJson(this)
    }
}

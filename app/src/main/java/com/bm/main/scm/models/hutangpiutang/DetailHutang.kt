package com.bm.main.scm.models.hutangpiutang

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by Richie on 7/15/17.
 */

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class DetailHutang : Serializable {
    var datapiutang: Hutang? = null
    var sudah_bayar: List<Data>? = null

    @Keep
    @JsonIgnoreProperties(ignoreUnknown = true)
    class Hutang : Serializable {
        var total_tagihan: String? = null
        var jumlah_piutang: String? = null
        var jatuh_tempo: String? = null
        var total_dibayar: String? = null

        fun json(): String {
            return Gson().toJson(this)
        }
    }

    @Keep
    @JsonIgnoreProperties(ignoreUnknown = true)
    class Data : Serializable {
        var id_supplier: String? = null
        var nama_supplier: String? = null
        var tanggal: String? = null
        var no_invoice: String? = null
        var nominal: String? = null

        fun json(): String {
            return Gson().toJson(this)
        }
    }

    fun json(): String {
        return Gson().toJson(this)
    }
}

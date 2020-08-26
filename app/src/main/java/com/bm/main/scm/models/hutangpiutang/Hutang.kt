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
class Hutang : Serializable {
    var datapiutang: Hutang? = null
    var list: List<Data>? = null

    @Keep
    @JsonIgnoreProperties(ignoreUnknown = true)
    class Hutang : Serializable {
        var jumlah_hutang: Double? = 0.0
        var nominal_hutang: String? = null
        var jatuh_tempo: Double? = 0.0
        var belum_lunas: Double? = 0.0

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
        var pembayaran: String? = null
        var keterangan: String? = null
        var totalorder: String? = null
        var jatuh_tempo: String? = null
        var status: String? = null

        fun json(): String {
            return Gson().toJson(this)
        }
    }

    fun json(): String {
        return Gson().toJson(this)
    }
}

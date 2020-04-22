package com.bm.main.pos.models.hutangpiutang

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by Richie on 7/15/17.
 */

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class DetailPiutangNew : Serializable {
    var datapiutang: Piutang? = null
    var history: List<Data>? = null

    @Keep
    @JsonIgnoreProperties(ignoreUnknown = true)
    class Piutang : Serializable {
        var id_pelanggan:String? = null
        var nama_pelanggan:String? = null
        var jumlah_piutang: String? = null
        var tanggal_hutang: String? = null

        fun json(): String {
            return Gson().toJson(this)
        }
    }

    @Keep
    @JsonIgnoreProperties(ignoreUnknown = true)
    class Data : Serializable {
        var id_historipiutangpelanggan: String? = null
        var id_pelanggan: String? = null
        var status: String? = null
        var user: String? = null
        var no_invoice: String? = null
        var nominal: String? = null
        var tanggal: String? = null
        var nama_pelanggan: String? = null

        fun json(): String {
            return Gson().toJson(this)
        }
    }

    fun json(): String {
        return Gson().toJson(this)
    }
}

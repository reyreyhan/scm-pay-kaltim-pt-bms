package com.bm.main.pos.rest

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class ProductResponse(
    var status: String = "",
    var msg: String = "",
    var errCode: String = "",
    var data: List<ProductResponseData> = emptyList()
)

@Keep
@JsonClass(generateAdapter = true)
data class ProductResponseData(
    var id_barang: String = "",
    var nama_barang: String = "",
    var gbr: String = "",
    var kodebarang: String = "",
    var id_kategori: String = "",
    var nama_kategori: String = "",
    var active: Boolean = false,
    var hargajual: Int = 0,
    var hargabeli: Int = 0,
    var stok: Int = 0,
    var minimalstok: Int = 0,
    var deskripsi: String = "-"
)
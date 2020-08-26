package com.bm.main.scm.models.product

import androidx.annotation.Keep
import com.bm.main.scm.di.MoshiModule
import com.squareup.moshi.JsonClass
import java.io.Serializable

/**
 * Created by Richie on 7/15/17.
 */

@Keep
@JsonClass(generateAdapter = true)
data class Product(
        var id_barang: String = "",
        var nama_barang: String = "",
        var gbr: String = "",
        var kodebarang: String = "",
        var id_kategori: String = "",
        var nama_kategori: String = "",
        var active: Boolean = false,
        var hargajual: String = "0",
        var hargabeli: String = "0",
        var stok: String = "0",
        var minimalstok: String = "0",
        var deskripsi: String = "-",
        var diskon: String = "",
        var posisi: Boolean = false
) : Serializable {
    fun json(): String {
        return MoshiModule.instance().adapter(Product::class.java).toJson(this)
    }
}

package com.bm.main.pos.models.category

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by Richie on 7/15/17.
 */

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class Category : Serializable {
    var id_kategori: String? = null
    var nama_kategori: String? = ""

    fun json(): String {
        return Gson().toJson(this)
    }
}

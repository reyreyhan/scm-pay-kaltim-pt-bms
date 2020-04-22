package com.bm.main.pos.models.cart

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.Gson
import com.bm.main.pos.models.product.Product
import java.io.Serializable

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class Cart : Serializable {
    var position:Int? = -1
    var count:Double? = 0.0
    var note:String? = ""
    var product:Product? = null

    fun json(): String {
        return Gson().toJson(this)
    }
}
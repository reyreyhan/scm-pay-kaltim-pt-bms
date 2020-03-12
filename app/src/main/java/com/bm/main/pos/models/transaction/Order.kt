package com.bm.main.pos.models.transaction

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by Richie on 7/15/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
class Order : Serializable {
    var invoice: String? = null

    fun json(): String {
        return Gson().toJson(this)
    }
}

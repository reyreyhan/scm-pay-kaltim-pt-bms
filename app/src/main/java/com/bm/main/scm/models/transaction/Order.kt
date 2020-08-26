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
class Order : Serializable {
    var invoice: String? = null

    fun json(): String {
        return Gson().toJson(this)
    }
}

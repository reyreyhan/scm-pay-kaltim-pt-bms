package com.bm.main.scm.models.discount

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by Richie on 7/15/17.
 */

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class Discount : Serializable {
    var id: String? = null
    var name: String? = ""
    var info: String? = ""

    fun json(): String {
        return Gson().toJson(this)
    }
}

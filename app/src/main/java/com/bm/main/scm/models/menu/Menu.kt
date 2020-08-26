package com.bm.main.scm.models.menu

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.Gson
import java.io.Serializable
import com.bm.main.scm.R


/**
 * Created by Richie on 7/15/17.
 */

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class Menu : Serializable {
    var id: Int? = null
        get() = field
        set(value) {
            field = value
        }
    var name: String? = ""
        get() = field
        set(value) {
            field = value
        }
    var image: Int? = R.drawable.logo
        get() = field
        set(value) {
            field = value
        }
    var enabled = true

    fun json(): String {
        return Gson().toJson(this)
    }
}

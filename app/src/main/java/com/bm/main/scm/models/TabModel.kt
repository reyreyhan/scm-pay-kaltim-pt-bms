package com.bm.main.scm.models

import androidx.annotation.Keep
import androidx.fragment.app.Fragment
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by Richie on 7/15/17.
 */

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class TabModel : Serializable {
    var title: String? = null
    var fragment: Fragment? = null

    fun json(): String {
        return Gson().toJson(this)
    }
}

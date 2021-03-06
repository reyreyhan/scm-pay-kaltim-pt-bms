package com.bm.main.scm.models

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.google.gson.Gson
import java.io.Serializable

/**
 * Created by Richie on 7/15/17.
 */

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class StockChartModel : Serializable {
    var name: String? = ""
    var stock: Double? = 0.0
    var minimal: Double? = 0.0

    fun json(): String {
        return Gson().toJson(this)
    }
}

package com.bm.main.pos.models

import androidx.annotation.Keep
import com.bm.main.pos.di.MoshiModule
import com.squareup.moshi.JsonClass
import java.io.Serializable

/**
 * Created by Richie on 7/15/17.
 */

@Keep
@JsonClass(generateAdapter = true)
class Message : Serializable {
    var message: String? = null

    fun json(): String {
        return MoshiModule.instance().adapter(Message::class.java).toJson(this)
    }
}

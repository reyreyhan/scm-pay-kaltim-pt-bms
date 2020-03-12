package com.bm.main.pos.feature.drawer

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class CustomMenu(
        val code: String = "",
        val label: String = "",
        val isActive: Boolean = true,
        val menuType: String = "",
        val icon: String = "",
        val order: Int = 0,
        val pageType: String = "",
        val pageUrl: String = "",
        val action: String = ""
)
package com.bm.main.pos.rest.entity

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class BaseResponse(val errCode: String = "", val msg: String = "", val status: String = "", val data: Any)
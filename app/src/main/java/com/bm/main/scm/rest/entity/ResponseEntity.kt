package com.bm.main.scm.rest.entity

import androidx.annotation.Keep
import com.google.gson.JsonElement
import java.io.Serializable

@Keep
data class ResponseEntity(
        val data: JsonElement,
        val status: String,
        val msg: String,
        val errCode: String
) : Serializable
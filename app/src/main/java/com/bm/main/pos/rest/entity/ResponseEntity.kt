package com.bm.main.pos.rest.entity

import com.google.gson.JsonElement
import java.io.Serializable

data class ResponseEntity(
        val data: JsonElement,
        val status: String,
        val msg: String,
        val errCode: String
) : Serializable
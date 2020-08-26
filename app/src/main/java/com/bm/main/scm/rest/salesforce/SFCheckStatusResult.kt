package com.bm.main.scm.rest.salesforce

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class SFCheckStatusResult(var status: Int = 0)

data class SFCheckStatusResultData(var id_outlet: String = "")
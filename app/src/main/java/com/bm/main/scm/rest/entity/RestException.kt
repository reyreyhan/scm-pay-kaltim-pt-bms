package com.bm.main.scm.rest.entity

import androidx.annotation.Keep
import java.io.IOException

@Keep
class RestException(message: String?, val errorCode: Int) : IOException(message) {

    @Keep
    companion object {
        const val RESPONSE_SUCCESS = "00"
        const val RESPONSE_USER_NOT_FOUND = "01"
        const val RESPONSE_DATA_NOT_FOUND = "02"
        const val RESPONSE_ERROR = "99"

        const val CODE_ERROR_NO_CONNECTION = 998
        const val CODE_ERROR_UNKNOWN = 999
        const val CODE_DATA_NOT_FOUND = 2
        const val CODE_USER_NOT_FOUND = 0
        const val CODE_RESPONSE_ERROR = 1
    }

}


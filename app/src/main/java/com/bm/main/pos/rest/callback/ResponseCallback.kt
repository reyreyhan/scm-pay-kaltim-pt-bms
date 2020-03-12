package com.bm.main.pos.rest.callback

interface ResponseCallback<T> {

    fun onRequestSuccess(data: T)
    fun onRequestFailed(errorCode: Int, message: String?)
}
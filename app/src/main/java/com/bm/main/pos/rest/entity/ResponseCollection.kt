package com.bm.main.pos.rest.entity

data class ResponseCollection<T> (
    val data: ArrayList<T>
)
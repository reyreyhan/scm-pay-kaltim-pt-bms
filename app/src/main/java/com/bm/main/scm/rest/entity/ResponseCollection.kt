package com.bm.main.scm.rest.entity

data class ResponseCollection<T> (
    val data: ArrayList<T>
)
package com.bm.main.scm.callback

interface AdapterItemCallback<T> {

    fun onItemClick(item: T, pos: Int)
    fun onItemLongClick(item: T, pos: Int)
}
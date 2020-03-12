package com.bm.main.pos.base

interface BasePresenterImpl<V : BaseViewImpl> {

    fun attachView(view: V)

    fun detachView()
}
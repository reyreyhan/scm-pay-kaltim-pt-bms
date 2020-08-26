package com.bm.main.scm.base

interface BasePresenterImpl<V : BaseViewImpl> {

    fun attachView(view: V)

    fun detachView()
}
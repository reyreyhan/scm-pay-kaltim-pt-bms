package com.bm.main.pos.feature.manage.product

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.bm.main.pos.datasources.NetworkState
import com.bm.main.pos.models.product.Product
import com.bm.main.pos.rest.ApiService
import timber.log.Timber
import javax.inject.Named

class ProductDataSource constructor(
        val api: ApiService, @Named("token") val token: String,
        val query: String,
        val limit: Int = 10
) :
    ItemKeyedDataSource<Int, Product>() {

    private var loaded = 0

    @SuppressLint("CheckResult")
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Product>) {
        Timber.e("init load")

        val size = params.requestedInitialKey ?: limit
        api.searchText(token, query, size).subscribe({ res ->
            Timber.e("init result size: ${res.size}")
            callback.onResult(res)
            loaded = size
        }, {
            callback.onResult(emptyList())
            Timber.e(it)
        })
    }

    @SuppressLint("CheckResult")
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Product>) {
        Timber.e("load more")
        api.searchText(token, query, params.requestedLoadSize, params.key).subscribe({ res ->
            Timber.e("load more result size: ${res.size}")
            callback.onResult(res)
            loaded += params.requestedLoadSize
        }, {
            callback.onResult(emptyList())
            Timber.e(it)
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Product>) {}
    override fun getKey(item: Product): Int = loaded
}
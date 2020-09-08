package com.bm.main.scm.rest

import androidx.annotation.Keep
import com.bm.main.scm.di.UserScope
import com.bm.main.scm.models.product.Product
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

@Keep
@UserScope
interface ApiService {

    @GET("barang/searchtext.php")
    fun searchText(
            @Query(value = "key") key: String = "",
            @Query(value = "search") query: String = "",
            @Query(value = "limit") limit: Int = 10,
            @Query(value = "offset") offset: Int = 0
    ): Single<List<Product>>

    @GET("barang/searchbarcode.php")
    fun searchByBarcode(
            @Query("key") key: String,
            @Query("kodebarang") id: String
    ): Single<List<Product>>
}
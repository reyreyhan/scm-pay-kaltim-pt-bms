package com.bm.main.pos.rest

import com.bm.main.pos.models.product.Product
import com.bm.sc.bebasbayar.social.di.UserScope
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

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
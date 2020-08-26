package com.bm.main.scm.models.cart

import androidx.annotation.Keep
import com.bm.main.scm.models.product.Product
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

@Keep
interface CartRestInterface {

    @Multipart
    @POST("penjualan/insert.php")
    fun add(
            @Part("key") key: RequestBody,
            @Part("nama_barang") nama: RequestBody,
            @Part("kodebarang") kodebarang: RequestBody,
            @Part("hargabeli") hargabeli: RequestBody,
            @Part("hargajual") hargajual: RequestBody,
            @Part("stok") stok: RequestBody,
            @Part gbr: MultipartBody.Part?,
            @Part("desc") desc: RequestBody
    ): Observable<List<Product>>

    @FormUrlEncoded
    @POST("penjualan/insert.php")
    fun addWithoutImg(
            @Part("key") key: RequestBody,
            @Part("nama_barang") nama: RequestBody,
            @Part("kodebarang") kodebarang: RequestBody,
            @Part("hargabeli") hargabeli: RequestBody,
            @Part("hargajual") hargajual: RequestBody,
            @Part("stok") stok: RequestBody,
            @Part("gbr") gbr: RequestBody,
            @Part("desc") desc: RequestBody
    ): Observable<List<Product>>

    @FormUrlEncoded
    @POST("penjualan/insert.php")
    fun addWithBarcode(
            @Field("key") key: String,
            @Field("nama_barang") nama: String,
            @Field("kodebarang") kodebarang: String,
            @Field("hargabeli") hargabeli: String,
            @Field("hargajual") hargajual: String,
            @Field("stok") stok: String
    ): Observable<List<Product>>

    @FormUrlEncoded
    @POST("penjualan/update.php")
    fun update(
            @Field("key") key: String,
            @Field("id_barang") id: String,
            @Field("nama_barang") nama: String,
            @Field("kodebarang") kodebarang: String,
            @Field("hargabeli") hargabeli: String,
            @Field("hargajual") hargajual: String,
            @Field("stok") stok: String
    ): Observable<List<Product>>

}
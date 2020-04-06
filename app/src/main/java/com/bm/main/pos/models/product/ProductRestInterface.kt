package com.bm.main.pos.models.product

import com.bm.main.pos.models.Message
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ProductRestInterface {

    @GET("barang/list.php")
    fun gets(@Query("key") key: String): Observable<List<Product>>

    @Multipart
    @POST("barang/insert.php")
    fun add(
            @Part("key") key: RequestBody,
            @Part("nama_barang") nama: RequestBody,
            @Part("kodebarang") kode: RequestBody,
            @Part("id_kategori") idkategori: RequestBody,
            @Part("hargabeli") beli: RequestBody,
            @Part("hargajual") jual: RequestBody,
            @Part("stok") stok: RequestBody,
            @Part("minimalstok") minstok: RequestBody,
            @Part("deskripsi") deskripsi: RequestBody,
            @Part gbr: MultipartBody.Part?
    ): Observable<Message>

    @Multipart
    @POST("barang/insert.php")
    fun addWithoutImg(
            @Part("key") key: RequestBody,
            @Part("nama_barang") nama: RequestBody,
            @Part("kodebarang") kode: RequestBody,
            @Part("id_kategori") idkategori: RequestBody,
            @Part("hargabeli") beli: RequestBody,
            @Part("hargajual") jual: RequestBody,
            @Part("stok") stok: RequestBody,
            @Part("minimalstok") minstok: RequestBody,
            @Part("deskripsi") deskripsi: RequestBody,
            @Part("gbr") gbr: RequestBody
    ): Observable<Message>

    @Multipart
    @POST("barang/update.php")
    fun update(
            @Part("key") key: RequestBody,
            @Part("id_barang") id: RequestBody,
            @Part("nama_barang") nama: RequestBody,
            @Part("kodebarang") kode: RequestBody,
            @Part("id_kategori") idkategori: RequestBody,
            @Part("hargabeli") beli: RequestBody,
            @Part("hargajual") jual: RequestBody,
            @Part("stok") stok: RequestBody,
            @Part("minimalstok") minstok: RequestBody,
            @Part("deskripsi") deskripsi: RequestBody,
            @Part gbr: MultipartBody.Part?
    ): Observable<Message>

    @GET("barang/delete.php")
    fun delete(
            @Query("key") key: String,
            @Query("id") id: String
    ): Observable<Message>

    @GET("barang/search.php")
    fun search(
            @Query("key") key: String,
            @Query("search") id: String
    ): Observable<List<Product>>

    @GET("barang/searchbarcode.php")
    fun searchByBarcode(
            @Query("key") key: String,
            @Query("kodebarang") id: String
    ): Observable<List<Product>>

    @GET("barang/searchtext.php")
    fun searchByName(
            @Query("key") key: String,
            @Query("search") id: String,
            @Query("limit") limit: Int,
            @Query("offset") offset: Int
    ): Observable<List<Product>>

    @GET("barang/sort.php")
    fun sort(
            @Query("key") key: String
    ): Observable<List<Product>>

    @FormUrlEncoded
    @POST("barang/update_stok.php")
    fun updateStok(
        @Field("key") key:String,
        @Field("id_barang") id:String,
        @Field("stok") stok:String):Observable<Message>

}
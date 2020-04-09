package com.bm.main.pos.models.customer

import com.bm.main.pos.models.Message
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface CustomerRestInterface {

    @GET("pelanggan/list.php")
    fun gets(
        @Query("key") key:String): Observable<List<Customer>>

    @Multipart
    @POST("pelanggan/insert.php")
    fun add(
        @Part("key") key: RequestBody,
        @Part("nama_pelanggan") nama: RequestBody,
        @Part("email") email: RequestBody,
        @Part("telpon") telpon: RequestBody,
        @Part("alamat") alamat: RequestBody,
        @Part gbr:MultipartBody.Part?): Observable<Message>

    @Multipart
    @POST("pelanggan/update.php")
    fun update(
        @Part("key") key: RequestBody,
        @Part("id") id: RequestBody,
        @Part("nama_pelanggan") nama: RequestBody,
        @Part("email") email: RequestBody,
        @Part("telpon") telpon: RequestBody,
        @Part("alamat") alamat: RequestBody,
        @Part gbr: MultipartBody.Part?): Observable<Message>

    @GET("pelanggan/delete.php")
    fun delete(
        @Query("key") key:String,
        @Query("id") id:String): Observable<Message>

    @GET("pelanggan/search.php")
    fun search(
        @Query("key") key:String,
        @Query("search") id:String): Observable<List<Customer>>

    @GET("pelanggan/detail.php")
    fun detail(
        @Query("key") key:String,
        @Query("id_pelanggan") id:String): Observable<Customer>

    @FormUrlEncoded
    @POST("pelanggan/insertpenjualan.php")
    fun addPenjualan(
        @Field("key") key: String,
        @Field("nama_pelanggan") nama: String,
        @Field("email") email: String,
        @Field("telpon") telpon: String,
        @Field("alamat") alamat: String): Observable<Customer>
}
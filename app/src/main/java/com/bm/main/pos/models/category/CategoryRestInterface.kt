package com.bm.main.pos.models.category

import com.google.gson.JsonObject
import com.bm.main.pos.models.Message
import com.bm.main.pos.rest.entity.ResponseEntity
import io.reactivex.Observable
import retrofit2.http.*

interface CategoryRestInterface {

    @GET("kategori/list.php")
    fun getCategories(
        @Query("key") key:String): Observable<List<Category>>

    @GET("kategori/pilihkategori.php")
    fun chooseCategories(
        @Query("key") key:String): Observable<List<Category>>

    @FormUrlEncoded
    @POST("kategori/insert.php")
    fun addCategory(
        @Field("key") key: String,
        @Field("nama_kategori") kategori: String): Observable<Message>

    @FormUrlEncoded
    @POST("kategori/update.php")
    fun updateCategory(
        @Field("key") key: String,
        @Field("id") id: String,
        @Field("nama_kategori") kategori: String): Observable<Message>

    @GET("kategori/delete.php")
    fun deleteCategory(
        @Query("key") key:String,
        @Query("id") id:String): Observable<Message>

    @GET("kategori/search.php")
    fun searchCategory(
        @Query("key") key:String,
        @Query("search") id:String): Observable<List<Category>>

}
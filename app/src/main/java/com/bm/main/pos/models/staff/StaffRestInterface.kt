package com.bm.main.pos.models.staff

import com.bm.main.pos.models.Message
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface StaffRestInterface {

    @GET("pengaturan/datastaff.php")
    fun getStaff(
        @Query("key") key:String): Observable<List<Staff>>

    @GET("pengaturan/hapusstaff.php")
    fun delete(
        @Query("key") key:String,
        @Query("no_telp") id:String): Observable<Message>

    @GET("pengaturan/caristaff.php")
    fun search(
        @Query("key") key:String,
        @Query("search") id:String): Observable<List<Staff>>

    @Multipart
    @POST("pengaturan/tambahstaff.php")
    fun add(
        @Part("key") key: RequestBody,
        @Part("nama_lengkap") nama: RequestBody,
        @Part("email") email: RequestBody,
        @Part("no_telp") telpon: RequestBody,
        @Part("alamat") alamat: RequestBody,
        @Part("level") level: RequestBody,
        @Part gbr:MultipartBody.Part?): Observable<Message>

    @Multipart
    @POST("pengaturan/updatestaff.php")
    fun update(
        @Part("key") key: RequestBody,
        @Part("id") id: RequestBody,
        @Part("nama_lengkap") nama: RequestBody,
        @Part("email") email: RequestBody,
        @Part("no_telp") telpon: RequestBody,
        @Part("alamat") alamat: RequestBody,
        @Part("level") level: RequestBody,
        @Part gbr:MultipartBody.Part?): Observable<Message>


}
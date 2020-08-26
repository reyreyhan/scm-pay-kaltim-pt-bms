package com.bm.main.scm.models.user

import androidx.annotation.Keep
import com.bm.main.scm.models.Message
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

@Keep
interface UserRestInterface {

    @GET("profil/dataakun.php")
    fun getProfile(
        @Query("key") key:String): Observable<List<User>>

    @Multipart
    @POST("pengaturan/updateakun.php")
    fun updateProfile(
        @Part("key") key: RequestBody,
        @Part("nama_lengkap") nama: RequestBody,
        @Part("email") email: RequestBody,
        @Part("no_telp") telpon: RequestBody,
        @Part("alamat") alamat: RequestBody,
        @Part gbr: MultipartBody.Part?): Observable<Message>

    @FormUrlEncoded
    @POST("pengaturan/updatepassword.php")
    fun changePassword(
        @Field("key") key: String,
        @Field("password_lama") lama: String,
        @Field("password_baru") baru: String): Observable<Message>

    @FormUrlEncoded
    @POST("profil/login.php")
    fun login(
        @Field("user") key: String,
        @Field("password") lama: String): Observable<List<User>>



}
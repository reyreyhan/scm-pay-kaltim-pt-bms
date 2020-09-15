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
        @Query("key") key:String): Observable<List<Profile>>

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
    @POST("merchant/login.php")
    fun login(
        @Field("user") key: String,
        @Field("password") lama: String): Observable<User>

    @FormUrlEncoded
    @POST("merchant/register.php")
    fun register(
        @Field("email") email: String,
        @Field("additional_data") additional_data: String,
        @Field("no_telp") no_telp: String,
        @Field("nama_lengkap") nama_lengkap: String,
        @Field("nama_toko") nama_toko: String,
        @Field("password") password: String,
        @Field("prop_code") prop_code: String,
        @Field("city_code") city_code: String,
        @Field("kec_code") kec_code: String,
        @Field("kel_code") kel_code: String,
        @Field("kode_pos") kode_pos: String,
        @Field("alamat") alamat: String
    ): Observable<Message>

//    @POST("merchant/register.php")
//    fun register(
//        @Body request:RequestBody
//    ): Observable<Message>
}
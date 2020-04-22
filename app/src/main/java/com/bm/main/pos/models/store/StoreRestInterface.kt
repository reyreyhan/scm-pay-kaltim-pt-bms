package com.bm.main.pos.models.store

import androidx.annotation.Keep
import com.google.gson.JsonObject
import com.bm.main.pos.models.Message
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

@Keep
interface StoreRestInterface {

    @GET("pengaturan/detailtoko.php")
    fun getStore(
        @Query("key") key:String): Observable<List<Store>>

    @FormUrlEncoded
    @POST("pengaturan/updatetoko.php")
    fun updateStore(
        @Field("key") key: String,
        @Field("nama_toko") nama: String,
        @Field("email") email: String,
        @Field("nohp") hp: String,
        @Field("alamat") alamat: String): Observable<Message>



}
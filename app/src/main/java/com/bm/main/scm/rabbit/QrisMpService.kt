package com.bm.main.scm.rabbit

import androidx.annotation.Keep
import com.bm.main.scm.models.user.merchant.LengkapiQrisResponse
import io.reactivex.Single
import okhttp3.RequestBody
import retrofit2.http.*

@Keep
interface QrisMpService {

    @GET("fastpay/data_merchant")
    fun checkRegistered(@Query("id") id_outlet: String): Single<CheckQrMpResponse>

    @GET("index.php/fastpay/data_outlet_detail")
    fun getFastpayData(@Query("id") id_outlet: String): Single<FastpayDataResponse>

    @Multipart
    @POST("register/register_merchant")
    fun registerQris(
        @Part("id_outlet") id_outlet:RequestBody,
        @Part("kategori_usaha") kategori_usaha:RequestBody,
        @Part("nama") nama:RequestBody,
        @Part("jenis_kelamin") jenis_kelamin:RequestBody,
        @Part("nik") nik:RequestBody,
        @Part("kriteria_usaha") kriteria_usaha:RequestBody,
        @Part("izin_usaha") izin_usaha:RequestBody,
        @Part("npwp") npwp:RequestBody,
        @Part("id_propinsi") id_propinsi:RequestBody,
        @Part("id_kota") id_kota:RequestBody,
        @Part("id_kecamatan") id_kecamatan:RequestBody,
        @Part("id_kelurahan") id_kelurahan:RequestBody,
        @Part("alamat") alamat:RequestBody
    ): Single<LengkapiQrisResponse>
}
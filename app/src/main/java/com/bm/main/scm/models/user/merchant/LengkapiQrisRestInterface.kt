package com.bm.main.scm.models.user.merchant

import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface LengkapiQrisRestInterface {

    @Multipart
    @POST("api/index.php/daftar")
    fun daftarQqris(
        @Part("id_outlet") id_outlet: RequestBody,
        @Part("nama") nama: RequestBody,
        @Part("no_idt") no_idt: RequestBody,
        @Part("tipe_idt") tipe_idt: RequestBody,
        @Part("gendre") gendre: RequestBody,
        @Part("id_propinsi") id_propinsi: RequestBody,
        @Part("id_kota") id_kota: RequestBody,
        @Part("id_kecamatan") id_kecamatan: RequestBody,
        @Part("id_kelurahan") id_kelurahan: RequestBody,
        @Part("alamat") alamat: RequestBody,
        @Part foto_ktp: MultipartBody.Part?,
        @Part foto_selfi: MultipartBody.Part?,
        @Part foto_toko_dpn: MultipartBody.Part?,
        @Part("kd_pos") kd_pos: RequestBody,
        @Part("nama_toko") nama_toko: RequestBody,
        @Part("kd_pos_toko") kd_pos_toko: RequestBody,
        @Part("kelurahan_toko") kelurahan_toko: RequestBody,
        @Part("kabupaten_toko") kabupaten_toko: RequestBody,
        @Part("propinsi_toko") propinsi_toko: RequestBody,
        @Part("alamat_toko") alamat_toko: RequestBody,
        @Part("email") email: RequestBody,
        @Part("handphone") handphone: RequestBody,
        @Part("no_whatsapp") no_whatsapp: RequestBody,
        @Part("created") created: RequestBody
    ): Single<LengkapiQrisResponse>

}
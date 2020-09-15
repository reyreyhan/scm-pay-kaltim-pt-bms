package com.bm.main.scm.models.user.merchant

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass
import okhttp3.MultipartBody
import okhttp3.RequestBody

@JsonClass(generateAdapter = true)
@Keep
data class LengkapiQrisResponse(
    val response_code: String = "",
    val keterangan: String = ""
)

data class LengkapiQrisRequest(
   var id_outlet: RequestBody,
   var nama: RequestBody,
   var no_idt: RequestBody,
   var tipe_idt: RequestBody,
   var gendre: RequestBody,
   var id_propinsi: RequestBody,
   var id_kota: RequestBody,
   var id_kecamatan: RequestBody,
   var id_kelurahan: RequestBody,
   var alamat: RequestBody,
   var foto_ktp: MultipartBody.Part?,
   var foto_selfi: MultipartBody.Part?,
   var foto_toko_dpn: MultipartBody.Part?,
   var kd_pos: RequestBody,
   var nama_toko: RequestBody,
   var kd_pos_toko: RequestBody,
   var kelurahan_toko: RequestBody,
   var kabupaten_toko: RequestBody,
   var propinsi_toko: RequestBody,
   var alamat_toko: RequestBody,
   var email: RequestBody,
   var handphone: RequestBody,
   var no_whatsapp: RequestBody,
   var created: RequestBody
)
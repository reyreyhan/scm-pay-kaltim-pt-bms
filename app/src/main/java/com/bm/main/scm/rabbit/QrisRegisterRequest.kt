package com.bm.main.scm.rabbit

import okhttp3.RequestBody

data class QrisRegisterRequest(
    var id_outlet: RequestBody,
    var kategori_usaha: RequestBody,
    var nama: RequestBody,
    var jenis_kelamin: RequestBody,
    var nik: RequestBody,
    var kriteria_usaha: RequestBody,
    var izin_usaha: RequestBody,
    var npwp: RequestBody,
    var id_propinsi: RequestBody,
    var id_kota: RequestBody,
    var id_kecamatan: RequestBody,
    var id_kelurahan: RequestBody,
    var alamat: RequestBody
)
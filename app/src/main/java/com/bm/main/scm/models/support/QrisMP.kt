package com.bm.main.scm.models.support

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class StatusQRISResponseEntity: Serializable {
    var data: List<StatusQRIS>? = null
    var step: String? = null
    var keterangan: String? = null
    var response_code: String? = null
}

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class StatusQRIS : Serializable {
    var id: String?=null
    var nmid: String?=null
    var id_outlet: String?=null
    var id_speedcash: String?=null
    var nama_toko: String?=null
    var nama_pemilik: String?=null
    var hp: String?=null
    var email: String?=null
    var created_date: String?=null
    var valid_date: String?=null
    var userid: String?=null
    var status: String?=null
    var json_raw: String?=null
    var raw_request: String?=null
    var raw_response: String?=null
    var alamat: String?=null
}

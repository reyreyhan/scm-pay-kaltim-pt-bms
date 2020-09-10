package com.bm.main.scm.models.support

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable

@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class SupportResponseEntity<T> : Serializable {
    var response_value: List<T>? = null
    var response_desc: String? = null
    var response_code: String? = null
}


@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class Provinsi: Serializable{
    var prop_code: String? = null
    var prop_name: String? = null
}


@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class Kota: Serializable{
    var city_code: String? = null
    var city_name: String? = null
}


@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class Kecamatan: Serializable{
    var kecamatan_code: String? = null
    var kecamatan_name: String? = null
}


@Keep
@JsonIgnoreProperties(ignoreUnknown = true)
class Kelurahan: Serializable{
    var id_kelurahan: String? = null
    var kode_pos: String? = null
    var detail: String? = null
}
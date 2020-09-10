package com.bm.main.scm.models.support

import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface SupportRestInterface {

    @POST("simulator/")
    fun getProvinsi(@Body raw: RequestBody): Observable<SupportResponseEntity<Provinsi>>

    @POST("simulator/")
    fun getKota(@Body raw: RequestBody  ): Observable<SupportResponseEntity<Kota>>

    @POST("simulator/")
    fun getKecamatan(@Body raw: RequestBody  ): Observable<SupportResponseEntity<Kecamatan>>

    @POST("simulator/")
    fun getKelurahan(@Body raw: RequestBody  ): Observable<SupportResponseEntity<Kelurahan>>
}
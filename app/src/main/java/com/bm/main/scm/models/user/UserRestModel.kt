package com.bm.main.scm.models.user

import android.content.Context
import androidx.annotation.Keep
import com.bm.main.scm.models.Message
import com.bm.main.scm.rest.RestClient
import com.bm.main.scm.rest.RestModel
import com.bm.main.scm.utils.Helper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Keep
class UserRestModel(context: Context) : RestModel<UserRestInterface>(context) {

    override fun createRestInterface(): UserRestInterface {
        return RestClient.getInstance()!!.createInterface(UserRestInterface::class.java)
    }

    fun getProfile(key: String): Observable<List<Profile>> {
        return restInterface.getProfile(key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun updateProfile(
        key: String,
        name: String,
        email: String,
        telpon: String,
        alamat: String,
        gbr: String?
    ): Observable<Message> {
        return restInterface.updateProfile(
            Helper.createPartFromString(key),
            Helper.createPartFromString(name),
            Helper.createPartFromString(email),
            Helper.createPartFromString(telpon),
            Helper.createPartFromString(alamat),
            Helper.createPartFromFile(gbr, "gbr")
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun changePassword(key: String, lama: String, baru: String): Observable<Message> {
        return restInterface.changePassword(key, lama, baru)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun login(user: String, password: String): Observable<User> {
        return restInterface.login(user, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

        fun register(
        email: String,
        additional_data: String,
        no_telp:String,
        nama_lengkap:String,
        nama_toko:String,
        password:String,
        prop_code:String,
        city_code:String,
        kec_code:String,
        kel_code:String,
        kode_pos:String,
        alamat:String
    ): Observable<Message> {
        return restInterface.register(
           email, additional_data, no_telp, nama_lengkap, nama_toko, password, prop_code, city_code, kec_code, kel_code, kode_pos, alamat
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
//    fun register(
//        requestBody: RequestBody
//    ): Observable<Message> {
//        return restInterface.register(
//            requestBody
//        )
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//    }
}
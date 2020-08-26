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

    fun getProfile(key:String): Observable<List<User>> {
        return restInterface.getProfile(key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun updateProfile(key:String,name:String,email:String,telpon:String,alamat:String,gbr:String?): Observable<Message> {
        return restInterface.updateProfile(
            Helper.createPartFromString(key),
            Helper.createPartFromString(name),
            Helper.createPartFromString(email),
            Helper.createPartFromString(telpon),
            Helper.createPartFromString(alamat),
            Helper.createPartFromFile(gbr,"gbr"))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun changePassword(key:String,lama:String,baru:String): Observable<Message> {
        return restInterface.changePassword(key,lama,baru)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun login(user:String,password:String): Observable<List<User>> {
        return restInterface.login(user,password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}
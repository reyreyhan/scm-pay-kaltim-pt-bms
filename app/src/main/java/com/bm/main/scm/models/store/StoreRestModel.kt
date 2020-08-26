package com.bm.main.scm.models.store

import android.content.Context
import androidx.annotation.Keep
import com.bm.main.scm.models.Message
import com.bm.main.scm.rest.RestClient
import com.bm.main.scm.rest.RestModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Keep
class StoreRestModel(context: Context) : RestModel<StoreRestInterface>(context) {

    override fun createRestInterface(): StoreRestInterface {
        return RestClient.getInstance()!!.createInterface(StoreRestInterface::class.java)
    }

    fun getStore(key:String): Observable<List<Store>> {
        return restInterface.getStore(key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun updateStore(key:String,name:String,email:String,telpon:String,alamat:String): Observable<Message> {
        return restInterface.updateStore(key,name,email,telpon,alamat)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}
package com.bm.main.pos.models.store

import android.content.Context
import com.bm.main.pos.models.Message
import com.bm.main.pos.rest.RestClient
import com.bm.main.pos.rest.RestModel
import com.bm.main.pos.rest.entity.ResponseEntity
import com.bm.main.pos.utils.Helper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

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
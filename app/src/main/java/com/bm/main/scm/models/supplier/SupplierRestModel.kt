package com.bm.main.scm.models.supplier

import android.content.Context
import androidx.annotation.Keep
import com.bm.main.scm.models.Message
import com.bm.main.scm.rest.RestClient
import com.bm.main.scm.rest.RestModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Keep
class SupplierRestModel(context: Context) : RestModel<SupplierRestInterface>(context) {

    override fun createRestInterface(): SupplierRestInterface {
        return RestClient.getInstance()!!.createInterface(SupplierRestInterface::class.java)
    }

    fun gets(key:String): Observable<List<Supplier>> {
        return restInterface.gets(key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun add(key:String,name:String,email:String,telpon:String,prov:String,kota:String,alamat:String): Observable<Message> {
        return restInterface.add(key,name,email,telpon,prov,kota,alamat)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun update(key:String,id:String,name:String,email:String,telpon:String,prov:String,kota:String,alamat:String): Observable<Message> {
        return restInterface.update(key,id,name,email,telpon,prov,kota,alamat)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun delete(key:String,id:String): Observable<Message> {
        return restInterface.delete(key,id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun search(key:String,search:String): Observable<List<Supplier>> {
        return restInterface.search(key,search)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun detail(key:String,id:String): Observable<Supplier> {
        return restInterface.detail(key,id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}
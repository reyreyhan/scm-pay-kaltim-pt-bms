package com.bm.main.pos.models.customer

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

class CustomerRestModel(context: Context) : RestModel<CustomerRestInterface>(context) {

    override fun createRestInterface(): CustomerRestInterface {
        return RestClient.getInstance()!!.createInterface(CustomerRestInterface::class.java)
    }

    fun gets(key:String): Observable<List<Customer>> {
        return restInterface.gets(key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun add(key:String,name:String,email:String,telpon:String,alamat:String,gbr:String?): Observable<Message> {
        return restInterface.add(
            Helper.createPartFromString(key),
            Helper.createPartFromString(name),
            Helper.createPartFromString(email),
            Helper.createPartFromString(telpon),
            Helper.createPartFromString(alamat),
            Helper.createPartFromFile(gbr,"gbr"))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun update(key:String,id:String,name:String,email:String,telpon:String,alamat:String,gbr:String?): Observable<Message> {
        return restInterface.update(
            Helper.createPartFromString(key),
            Helper.createPartFromString(id),
            Helper.createPartFromString(name),
            Helper.createPartFromString(email),
            Helper.createPartFromString(telpon),
            Helper.createPartFromString(alamat),
            Helper.createPartFromFile(gbr,"gbr"))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun delete(key:String,id:String): Observable<Message> {
        return restInterface.delete(key,id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun search(key:String,search:String): Observable<List<Customer>> {
        return restInterface.search(key,search)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun detail(key:String,id:String): Observable<Customer> {
        return restInterface.detail(key,id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun addPenjualan(key:String,name:String,email:String,telpon:String,alamat:String): Observable<List<Customer>> {
        return restInterface.addPenjualan(key,name,email,telpon,alamat)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}
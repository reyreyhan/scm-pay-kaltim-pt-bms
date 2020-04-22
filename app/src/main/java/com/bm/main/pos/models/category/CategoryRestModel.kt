package com.bm.main.pos.models.category

import android.content.Context
import androidx.annotation.Keep
import com.bm.main.pos.models.Message
import com.bm.main.pos.rest.RestClient
import com.bm.main.pos.rest.RestModel
import com.bm.main.pos.rest.entity.ResponseEntity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

@Keep
class CategoryRestModel(context: Context) : RestModel<CategoryRestInterface>(context) {

    override fun createRestInterface(): CategoryRestInterface {
        return RestClient.getInstance()!!.createInterface(CategoryRestInterface::class.java)
    }

    fun getCategories(key:String): Observable<List<Category>> {
        return restInterface.getCategories(key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun chooseCategories(key:String): Observable<List<Category>> {
        return restInterface.chooseCategories(key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun addCategory(key:String,name:String): Observable<Message> {
        return restInterface.addCategory(key,name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun updateCategory(key:String,id:String,name:String): Observable<Message> {
        return restInterface.updateCategory(key,id,name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteCategory(key:String,id:String): Observable<Message> {
        return restInterface.deleteCategory(key,id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun searchCategory(key:String,search:String): Observable<List<Category>> {
        return restInterface.searchCategory(key,search)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}
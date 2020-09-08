package com.bm.main.scm.models.user.merchant

import android.content.Context
import androidx.annotation.Keep
import com.bm.main.scm.rest.RestClient
import com.bm.main.scm.rest.RestModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@Keep
class MerchantUserRestModel(context: Context) : RestModel<MerchantUserRestInterface>(context) {

    override fun createRestInterface(): MerchantUserRestInterface {
        return RestClient.getInstance()!!.createInterface(MerchantUserRestInterface::class.java)
    }

    fun getDetail(key:String): Observable<List<MerchantUser>> {
        return restInterface.getDetail(key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getToko(key:String): Observable<List<MerchantToko>> {
        return restInterface.getToko(key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun login(user:String,password:String): Observable<MerchantUser> {
        return restInterface.login(user,password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}
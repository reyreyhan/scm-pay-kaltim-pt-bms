package com.bm.main.pos.models.cart

import android.content.Context
import com.bm.main.pos.models.Message
import com.bm.main.pos.models.product.Product
import com.bm.main.pos.rest.RestClient
import com.bm.main.pos.rest.RestModel
import com.bm.main.pos.rest.entity.ResponseEntity
import com.bm.main.pos.utils.Helper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CartRestModel(context: Context) : RestModel<CartRestInterface>(context) {

    override fun createRestInterface(): CartRestInterface {
        return RestClient.getInstance()!!.createInterface(CartRestInterface::class.java)
    }

    fun add(key: String, name: String, kodebarang: String, buy: String, sell: String, stock: String, gbr: String, desc: String): Observable<List<Product>> {
        return restInterface.add(
            Helper.createPartFromString(key),
            Helper.createPartFromString(name),
            Helper.createPartFromString(kodebarang),
            Helper.createPartFromString(buy),
            Helper.createPartFromString(sell),
            Helper.createPartFromString(stock),
            Helper.createPartFromFile(gbr, "gbr"),
            Helper.createPartFromString(desc)
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun addWithoutImg(key: String, name: String, kodebarang: String, buy: String, sell: String, stock: String, gbr: String, desc: String): Observable<List<Product>> {
        return restInterface.addWithoutImg(
            Helper.createPartFromString(key),
            Helper.createPartFromString(name),
            Helper.createPartFromString(kodebarang),
            Helper.createPartFromString(buy),
            Helper.createPartFromString(sell),
            Helper.createPartFromString(stock),
            Helper.createPartFromString(gbr),
            Helper.createPartFromString(desc)
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun addWithBarcode(key: String, name: String, barcode: String, buy: String, sell: String, stock: String): Observable<List<Product>> {
        return restInterface.addWithBarcode(key, name, barcode, buy, sell, stock)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun update(key: String, id: String, name: String, barcode: String, buy: String, sell: String, stock: String): Observable<List<Product>> {
        return restInterface.update(key, id, name, barcode, buy, sell, stock)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


}
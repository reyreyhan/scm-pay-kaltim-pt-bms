package com.bm.main.scm.models.product

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
class ProductRestModel(context: Context) : RestModel<ProductRestInterface>(context) {

    override fun createRestInterface(): ProductRestInterface {
        return RestClient.getInstance()!!.createInterface(ProductRestInterface::class.java)
    }

    fun gets(key: String): Observable<List<Product>> {
        return restInterface.gets(key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun sort(key: String): Observable<List<Product>> {
        return restInterface.sort(key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun add(key: String, name: String, kode: String, idkategori: String, jual: String,
            beli: String, stok: String, minstok: String, gbr: String?, desk: String): Observable<Message> {
        return restInterface.add(
                Helper.createPartFromString(key),
                Helper.createPartFromString(name),
                Helper.createPartFromString(kode),
                Helper.createPartFromString(idkategori),
                Helper.createPartFromString(beli),
                Helper.createPartFromString(jual),
                Helper.createPartFromString(stok),
                Helper.createPartFromString(minstok),
                Helper.createPartFromString(desk),
                Helper.createPartFromFile(gbr, "gbr")
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun addFromMaster(key: String, name: String, kode: String, idkategori: String, jual: String,
            beli: String, stok: String, minstok: String, photoUrl: String, desk: String): Observable<Message> {
        return restInterface.addFromMaster(
            Helper.createPartFromString(key),
            Helper.createPartFromString(name),
            Helper.createPartFromString(kode),
            Helper.createPartFromString(idkategori),
            Helper.createPartFromString(beli),
            Helper.createPartFromString(jual),
            Helper.createPartFromString(stok),
            Helper.createPartFromString(minstok),
            Helper.createPartFromString(desk),
            Helper.createPartFromString(photoUrl)
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun update(key: String, id: String, name: String, kode: String, idkategori: String, jual: String, beli: String, stok: String, minstok: String, gbr: String?, desk: String): Observable<Message> {
        return restInterface.update(
                Helper.createPartFromString(key),
                Helper.createPartFromString(id),
                Helper.createPartFromString(name),
                Helper.createPartFromString(kode),
                Helper.createPartFromString(idkategori),
                Helper.createPartFromString(beli),
                Helper.createPartFromString(jual),
                Helper.createPartFromString(stok),
                Helper.createPartFromString(minstok),
                Helper.createPartFromString(desk),
                Helper.createPartFromFile(gbr, "gbr")
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun delete(key: String, id: String): Observable<Message> {
        return restInterface.delete(key, id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun search(key: String, search: String): Observable<List<Product>> {
        return restInterface.search(key, search)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun searchByBarcode(key: String, barcode: String): Observable<List<Product>> {
        return restInterface.searchByBarcode(key, barcode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun searchByName(key: String, name: String, limit: Int, offset:Int): Observable<List<Product>> {
        return restInterface.searchByName(key, name, limit, offset)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
    fun updateStok(key:String, id:String, stok:String): Observable<Message>{
        return restInterface.updateStok(key, id, stok)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
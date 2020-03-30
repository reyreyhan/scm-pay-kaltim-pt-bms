package com.bm.main.pos.feature.report.stock

import android.content.Context
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.pos.models.Message
import com.bm.main.pos.models.product.Product
import com.bm.main.pos.models.product.ProductRestModel
import com.bm.main.pos.models.report.ReportRestModel
import com.bm.main.pos.models.report.ReportStock
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.utils.AppSession
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class StockInteractor(var output: StockContract.InteractorOutput?) : StockContract.Interactor {

    private var disposable = CompositeDisposable()
    private val appSession = AppSession()

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onRestartDisposable() {
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    override fun callGetReportsAPI(context: Context, restModel: ReportRestModel, awal:String, akhir:String) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(restModel.stock(key!!,awal,akhir).subscribeWith(object : DisposableObserver<List<ReportStock>>() {

            override fun onNext(@NonNull response: List<ReportStock>) {
                output?.onSuccessGetReports(response)
            }

            override fun onError(@NonNull e: Throwable) {
                e.printStackTrace()
                var errorCode = 999
                var errorMessage = "Terjadi kesalahan"
                if (e is RestException) {
                    errorCode = e.errorCode
                    errorMessage = e.message ?: "Terjadi kesalahan"
                }
                else{
                    errorMessage = e.message.toString()
                }
                output?.onFailedAPI(errorCode,errorMessage)
            }

            override fun onComplete() {

            }
        }))
    }

    override fun callSearchAPI(context: Context, restModel: ReportRestModel, cari:String, awal:String, akhir:String) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(restModel.searchStock(key!!,cari,awal,akhir).subscribeWith(object : DisposableObserver<List<ReportStock>>() {

            override fun onNext(@NonNull response: List<ReportStock>) {
                output?.onSuccessGetReports(response)
            }

            override fun onError(@NonNull e: Throwable) {
                e.printStackTrace()
                var errorCode = 999
                var errorMessage = "Terjadi kesalahan"
                if (e is RestException) {
                    errorCode = e.errorCode
                    errorMessage = e.message ?: "Terjadi kesalahan"
                }
                else{
                    errorMessage = e.message.toString()
                }
                output?.onFailedAPI(errorCode,errorMessage)
            }

            override fun onComplete() {

            }
        }))
    }

    override fun callSortByStockAPI(context: Context, restModel: ReportRestModel, awal:String, akhir:String) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(restModel.sortStock(key!!,awal,akhir).subscribeWith(object : DisposableObserver<List<ReportStock>>() {
            override fun onNext(@NonNull response: List<ReportStock>) {
                output?.onSuccessGetReports(response)
            }

            override fun onError(@NonNull e: Throwable) {
                e.printStackTrace()
                var errorCode = 999
                var errorMessage = "Terjadi kesalahan"
                if (e is RestException) {
                    errorCode = e.errorCode
                    errorMessage = e.message ?: "Terjadi kesalahan"
                }
                else{
                    errorMessage = e.message.toString()
                }
                output?.onFailedAPI(errorCode,errorMessage)
            }

            override fun onComplete() {

            }
        }))
    }

    override fun callSortByNameAPI(context: Context, restModel: ReportRestModel, awal:String, akhir:String) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(restModel.sortPriceStock(key!!,awal,akhir).subscribeWith(object : DisposableObserver<List<ReportStock>>() {

            override fun onNext(@NonNull response: List<ReportStock>) {
                output?.onSuccessGetReports(response)
            }

            override fun onError(@NonNull e: Throwable) {
                e.printStackTrace()
                var errorCode = 999
                var errorMessage = "Terjadi kesalahan"
                if (e is RestException) {
                    errorCode = e.errorCode
                    errorMessage = e.message ?: "Terjadi kesalahan"
                }
                else{
                    errorMessage = e.message.toString()
                }
                output?.onFailedAPI(errorCode,errorMessage)
            }

            override fun onComplete() {

            }
        }))
    }

    override fun callUpdateBarangAPI(
        context: Context,
        model: ProductRestModel,
        id:String,
        name: String,
        kode: String,
        idkategori: String,
        jual: String,
        beli: String,
        stok: String,
        minstok: String,
        desk: String
    ) {
        val key = PreferenceClass.getTokenPos()
        val request = model.update(key!!, id, name, kode, idkategori, jual, beli, stok, minstok, null, desk)
        disposable.add(
            request.subscribeWith(object : DisposableObserver<Message>() {
                override fun onNext(@NonNull response: Message) {
                    output?.onSuccessUpdateBarang(response.message, kode)
                }

                override fun onError(@NonNull e: Throwable) {
                    e.printStackTrace()
                    var errorCode = 999
                    var errorMessage: String
                    if (e is RestException) {
                        errorCode = e.errorCode
                        errorMessage = e.message ?: "Terjadi kesalahan"
                    } else {
                        errorMessage = e.message.toString()
                    }
                    output?.onFailedAPI(errorCode, errorMessage)
                }

                override fun onComplete() {
                }
            })
        )
    }

    override fun callSearchProductAPI(
        context: Context,
        restModel: ProductRestModel,
        search: String,
        stock:String?
    ) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(restModel.search(key!!,search).subscribeWith(object : DisposableObserver<List<Product>>() {

            override fun onNext(@NonNull response: List<Product>) {
                output?.onSuccessGetProducts(response, stock)
            }

            override fun onError(@NonNull e: Throwable) {
                e.printStackTrace()
                var errorCode = 999
                var errorMessage = "Terjadi kesalahan"
                if (e is RestException) {
                    errorCode = e.errorCode
                    errorMessage = e.message ?: "Terjadi kesalahan"
                }
                else{
                    errorMessage = e.message.toString()
                }
                output?.onFailedAPI(errorCode,errorMessage)
            }

            override fun onComplete() {

            }
        }))
    }
}
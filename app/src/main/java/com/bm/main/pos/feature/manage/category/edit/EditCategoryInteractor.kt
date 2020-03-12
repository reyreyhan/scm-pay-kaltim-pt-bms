package com.bm.main.pos.feature.manage.category.edit

import android.content.Context
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.pos.models.Message
import com.bm.main.pos.models.category.CategoryRestModel
import com.bm.main.pos.rest.entity.RestException
import com.bm.main.pos.utils.AppSession
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class EditCategoryInteractor(var output: EditCategoryContract.InteractorOutput?) : EditCategoryContract.Interactor {

    private var disposable = CompositeDisposable()
    private val appSession = AppSession()

    override fun onDestroy() {
        disposable.clear()
    }

    override fun onRestartDisposable() {
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    override fun callEditCategoryAPI(context: Context, model: CategoryRestModel, id:String, name: String) {
        val key = PreferenceClass.getTokenPos()
        disposable.add(model.updateCategory(key!!,id,name).subscribeWith(object : DisposableObserver<Message>() {

            override fun onNext(@NonNull response: Message) {
                output?.onSuccessEditCategory(response.message)
            }

            override fun onError(@NonNull e: Throwable) {
                e.printStackTrace()
                var errorCode = 999
                val errorMessage: String
                if (e is RestException) {
                    errorCode = e.errorCode
                    errorMessage = e.message ?: "Terjadi kesalahan"
                }
                else{
                    errorMessage = e.message.toString()
                }
                output?.onFailedEditCategory(errorCode,errorMessage)
            }

            override fun onComplete() {

            }
        }))
    }
}
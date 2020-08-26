package com.bm.main.scm.feature.setting.password

import android.app.Activity
import android.content.Context
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.models.user.UserRestModel

class PasswordPresenter(val context: Context, val view: PasswordContract.View) : BasePresenter<PasswordContract.View>(),
    PasswordContract.Presenter, PasswordContract.InteractorOutput {


    private var interactor = PasswordInteractor(this)
    private var restModel = UserRestModel(context)


    override fun onViewCreated() {

    }

    override fun onCheck(lama:String,baru:String,konfirmasi:String) {
        if(lama.isBlank() || lama.isEmpty()){
            view.showMessage(999,"Password harus diisi")
            return
        }

        if(lama.length < 6){
            view.showMessage(999,"Password minimal 6 karakter")
            return
        }

        if(baru.isBlank() || baru.isEmpty()){
            view.showMessage(999,"Password baru harus diisi")
            return
        }

        if(baru.length < 6){
            view.showMessage(999,"Password baru minimal 6 karakter")
            return
        }

        if(konfirmasi.isBlank() || konfirmasi.isEmpty()){
            view.showMessage(999,"Konfirmasi password harus diisi")
            return
        }

        if(konfirmasi != baru){
            view.showMessage(999,"Konfirmasi password salah")
            return
        }

        interactor.callUpdateAPI(context,restModel,lama,baru)
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onSuccessAPI(msg: String?) {
        view.onClose(msg,Activity.RESULT_OK)
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showMessage(code,msg)
    }

}
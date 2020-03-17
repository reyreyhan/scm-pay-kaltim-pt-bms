package com.bm.main.pos.feature.newhome

import android.content.Context
import android.content.Intent
import com.bm.main.pos.R
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.models.user.User
import com.bm.main.pos.models.user.UserRestModel
import com.bm.main.pos.utils.AppConstant

class NewHomePresenter(val context: Context, val view: NewHomeContract.View) :
    BasePresenter<NewHomeContract.View>(),
    NewHomeContract.Presenter, NewHomeContract.InteractorOutput {

    private var interactor: NewHomeInteractor = NewHomeInteractor(this)
    private var userRestModel = UserRestModel(context)
    private var idMenu: Int = R.id.nav_home
    private var position = 0

    override fun onViewCreated(intent: Intent) {
        loadProfile()
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun loadProfile() {
        interactor.callGetProfileAPI(context, userRestModel)
    }

    override fun onSuccessGetProfile(list: List<User>) {
        if (list.isEmpty()) {
            //view.showErrorMessage(999,"User tidak ditemukan")
            return
        }

        val user = list[0]
        interactor.saveUser(user)
        //view.setProfile(user.nama_lengkap!!, user.alamat!!, user.kota!!, user.no_telp!!, user.gbr!!)
    }

    override fun onFailedAPI(code: Int, msg: String) {
        //view.showErrorMessage(code,msg)
    }
}
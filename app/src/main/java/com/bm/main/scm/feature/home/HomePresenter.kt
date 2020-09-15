package com.bm.main.scm.feature.home

import android.content.Context
import android.content.Intent
import com.bm.main.scm.R
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.models.user.Profile
import com.bm.main.scm.models.user.UserRestModel

class HomePresenter(val context: Context, val view: HomeContract.View) :
    BasePresenter<HomeContract.View>(),
    HomeContract.Presenter, HomeContract.InteractorOutput {

    private var interactor: HomeInteractor = HomeInteractor(this)
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

    override fun onSuccessGetProfile(list: List<Profile>) {
        if (list.isEmpty()) {
            //view.showErrorMessage(999,"User tidak ditemukan")
            return
        }

        val user = list[0]
        interactor.saveUser(user)
//        view.setProfile(user.nama_lengkap!!, user.alamat!!, user.kota!!, user.no_telp!!, user.gbr!!)
    }

    override fun onFailedAPI(code: Int, msg: String) {
        //view.showErrorMessage(code,msg)
    }
}
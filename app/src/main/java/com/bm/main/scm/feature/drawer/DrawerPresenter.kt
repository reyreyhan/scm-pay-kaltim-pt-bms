package com.bm.main.scm.feature.drawer

import android.content.Context
import android.content.Intent
import com.bm.main.scm.R
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.models.user.User
import com.bm.main.scm.models.user.UserRestModel
import com.bm.main.scm.utils.AppConstant

class DrawerPresenter(val context: Context, val view: DrawerContract.View) :
    BasePresenter<DrawerContract.View>(),
    DrawerContract.Presenter, DrawerContract.InteractorOutput {

    private var interactor: DrawerInteractor = DrawerInteractor(this)
    private var userRestModel = UserRestModel(context)
    private var idMenu: Int = R.id.nav_home
    private var position = 0

    override fun onViewCreated(intent: Intent) {
        idMenu = intent.getIntExtra(AppConstant.DATA, R.id.nav_home)
        position = intent.getIntExtra(AppConstant.POSITION, 0)
        view.selectMenu(idMenu)
        loadProfile()
    }

    override fun setSelectedIdMenu(id: Int) {
        idMenu = id
    }

    override fun getSelectedIdMenu(): Int {
        return idMenu
    }

    override fun setSelectedPosition(position: Int) {
        this.position = position
    }

    override fun getSelectedPosition(): Int {
        return position
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
        view.setProfile(user.nama_lengkap!!, user.alamat!!, user.kota!!, user.no_telp!!, user.gbr!!)
    }

    override fun onFailedAPI(code: Int, msg: String) {
        //view.showErrorMessage(code,msg)
    }
}
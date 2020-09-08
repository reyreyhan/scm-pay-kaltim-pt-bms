package com.bm.main.scm.feature.drawer

import android.content.Context
import android.content.Intent
import com.bm.main.scm.R
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.models.user.merchant.MerchantToko
import com.bm.main.scm.models.user.merchant.MerchantUser
import com.bm.main.scm.models.user.merchant.MerchantUserRestModel
import com.bm.main.scm.utils.AppConstant

class DrawerPresenter(val context: Context, val view: DrawerContract.View) :
    BasePresenter<DrawerContract.View>(),
    DrawerContract.Presenter, DrawerContract.InteractorOutput {

    private var interactor: DrawerInteractor = DrawerInteractor(this)
    private var merchantUserRestModel = MerchantUserRestModel(context)
    private var idMenu: Int = R.id.nav_home
    private var position = 0

    override fun onViewCreated(intent: Intent) {
        idMenu = intent.getIntExtra(AppConstant.DATA, R.id.nav_home)
        position = intent.getIntExtra(AppConstant.POSITION, 0)
        view.selectMenu(idMenu)
        interactor.checkProfileToko()
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

    override fun loadProfileRemote() {
        interactor.callGetProfileAPI(context, merchantUserRestModel)
        interactor.callGetTokoAPI(context, merchantUserRestModel)
    }

    override fun loadProfileLocal() {
        interactor.getLocalProfileToko()
    }

    override fun logOut() {
        interactor.resetAppSession()
    }

    override fun onSuccessGetProfile(list: List<MerchantUser>) {
//        if (list.isEmpty()) {
//            //view.showErrorMessage(999,"User tidak ditemukan")
//            return
//        }
        val user = list[0]
        interactor.saveUser(user)
        view.setProfilePict(user.gbr!!)
    }

    override fun onSuccessGetToko(list: List<MerchantToko>) {
        val toko = list[0]
        interactor.saveToko(toko)
        view.setProfile(toko.nama_toko!!, toko.id_toko!!)
    }

    override fun onProfileTokoExisting(isExisting: Boolean) {
        if (!isExisting){
            loadProfileRemote()
        }else{
            loadProfileLocal()
        }
    }

    override fun onSuccessGetProfileTokoLocal(user: MerchantUser, toko: MerchantToko) {
        view.setProfile(toko.nama_toko!!, toko.id_toko!!)
        view.setProfilePict(user.gbr!!)
    }

    override fun onFailedAPI(code: Int, msg: String) {
//        view.showErrorMessage(code,msg)
    }
}
package com.bm.main.pos.feature.home

import android.content.Context
import android.content.Intent
import android.util.Log
import com.bm.main.fpl.activities.BaseActivity
import com.bm.main.fpl.constants.EventParam
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.models.menu.Menu
import com.bm.main.pos.R
import com.bm.main.pos.feature.drawer.DrawerActivity
import com.bm.main.pos.models.user.User
import com.bm.main.pos.models.user.UserRestModel
import com.bm.main.pos.utils.Helper

class HomePresenter(val context: Context,
                    val view: HomeContract.View) : BasePresenter<HomeContract.View>(), HomeContract.Presenter, HomeContract.InteractorOutput {

    private var interactor: HomeInteractor = HomeInteractor(this)
    private var userRestModel = UserRestModel(context)

    override fun onViewCreated() {
        (context as BaseActivity).logEventFireBase("Home",
                "Home Profit",
                EventParam.EVENT_ACTION_VISIT,
                EventParam.EVENT_SUCCESS,
                HomeFragment::class.java!!.getSimpleName())
        menu()
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    val menus by lazy { mutableListOf(
        Menu().apply {
            id = R.id.nav_sell
            name = context.getString(R.string.menu_sell)
            image = R.drawable.icon_home_manajemen
        },
//        Menu().apply {
//            id = R.id.nav_qris
//            name = context.getString(R.string.menu_qris)
//            image = R.drawable.icon_home_qr
//        },
        Menu().apply {
            id = R.id.nav_history
            name = context.getString(R.string.menu_history)
            image = R.drawable.icon_home_transaksi
        },
        Menu().apply {
            id = R.id.nav_report
            name = context.getString(R.string.menu_report)
            image = R.drawable.icon_home_laporan
        },
        Menu().apply {
            id = R.id.nav_management
            name = context.getString(R.string.menu_management)
            image = R.drawable.icon_home_penjualan
        },
        Menu().apply {
            id = R.id.nav_setting
            name = context.getString(R.string.menu_setting)
            image = R.drawable.icon_home_pengaturan
        },
        Menu().apply {
            id = R.id.nav_help
            name = context.getString(R.string.menu_help)
            image = R.drawable.icon_home_bantuan
        }
    ) }

    private fun menu() {
        view.setMenu(menus)
    }

    override fun loadProfile() {
        interactor.callGetProfileAPI(context, userRestModel)
    }

    override fun onSuccessGetProfile(list: List<User>) {
        if (list.isEmpty()) {
            view.showErrorMessage(999, "User tidak ditemukan")
            return
        }

        val user = list[0]
        interactor.saveUser(user)
        view.setProfile(user.nama_lengkap!!,
                user.alamat!!,
                user.kota!!,
                user.no_telp!!,
                user.gbr!!,
                "Rp " + Helper.convertToCurrency(user.omset!!))
    }

    override fun onFailedAPI(code: Int, msg: String) {
        view.showErrorMessage(code, msg)
    }
}
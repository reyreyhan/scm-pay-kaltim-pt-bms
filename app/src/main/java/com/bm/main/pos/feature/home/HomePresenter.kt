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
            id = 0
            name = "Tambah Barang"
            image = R.drawable.ic_tambah_barang
        },
        Menu().apply {
            id = 1
            name = "Aktivitas"
            image = R.drawable.ic_aktifitas
        },
        Menu().apply {
            id = 2
            name = "Laporan"
            image = R.drawable.ic_laporan
        },
        Menu().apply {
            id = 3
            name = "Data Produk"
            image = R.drawable.ic_data_produk
        },
        Menu().apply {
            id = 4
            name = "Data Piutang"
            image = R.drawable.ic_tambah_barang
        },
        Menu().apply {
            id = 5
            name = "Data Pelanggan"
            image = R.drawable.ic_data_pelanggan
        },
        Menu().apply {
            id = 6
            name = "Tips Jitu Profit"
            image = R.drawable.ic_tips_jitu_profit
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
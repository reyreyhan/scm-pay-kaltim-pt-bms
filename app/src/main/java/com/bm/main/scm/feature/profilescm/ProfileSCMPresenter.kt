package com.bm.main.scm.feature.profilescm

import android.content.Context
import com.bm.main.scm.base.BasePresenter
import com.bm.main.scm.models.user.merchant.MerchantToko
import com.bm.main.scm.models.user.merchant.MerchantUser

class ProfileSCMPresenter(val context: Context, val view: ProfileSCMContract.View) : BasePresenter<ProfileSCMContract.View>(),
    ProfileSCMContract.Presenter, ProfileSCMContract.InteractorOutput {

    private var interactor: ProfileSCMInteractor = ProfileSCMInteractor(this)

    override fun onViewCreated() {
        interactor.getProfile()
    }

    override fun onDestroy() {
        interactor.onDestroy()
    }

    override fun onSuccessGetProfile(user: MerchantUser, toko:MerchantToko) {
        val list = mutableListOf<String>()
        //NIK
        list.add("")
        //NoHP
        list.add(toko.nohp!!)
        //Email
        list.add(toko.email!!)
        //Kategori Usaha
        list.add("")
        //Kriteria Usaha
        list.add("")
        //NPWP
        list.add("")
        //Alamat
        list.add(toko.alamat!!)
        //Provinsi
        list.add("")
        //Kota
        list.add("")
        //Kecamatan
        list.add("")
        //Kelurahan
        list.add("")

        val tokoName = toko.nama_toko!!
        val tokoId = toko.id_toko!!
        val tokoPict = user.gbr!!

        view.setProfileHeader(tokoName, tokoId, tokoPict)
        view.setProfileData(list)
    }
}
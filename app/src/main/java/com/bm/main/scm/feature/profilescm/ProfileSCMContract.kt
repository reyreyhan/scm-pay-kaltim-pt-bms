package com.bm.main.scm.feature.profilescm

import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl
import com.bm.main.scm.models.user.merchant.MerchantToko
import com.bm.main.scm.models.user.merchant.MerchantUser

interface ProfileSCMContract {

    interface View : BaseViewImpl {
        fun setProfileData(list:List<String>)
        fun setProfileHeader(name:String, id:String, url: String)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onDestroy()
        fun onViewCreated()
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
        fun getProfile()
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
        fun onSuccessGetProfile(user: MerchantUser, toko:MerchantToko)
    }
}
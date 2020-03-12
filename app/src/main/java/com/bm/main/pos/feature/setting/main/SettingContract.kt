package com.bm.main.pos.feature.setting.main

import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl

interface SettingContract {

    interface View : BaseViewImpl {
        fun openPrivacyPage()
        fun openTermsPage()
        fun openHelpPage()
        fun openAccountPage()
        fun openPrinterPage()
        fun openStorePage()
        fun openStaffPage()
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated()

    }

    interface Interactor : BaseInteractorImpl {

    }

    interface InteractorOutput : BaseInteractorOutputImpl {

    }


}
package com.bm.main.scm.feature.setting.main

import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl

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
package com.bm.main.scm.feature.report.main

import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl

interface ReportContract {

    interface View : BaseViewImpl {
        fun openTransaction()
        fun openKulakan()
        fun openStock()
        fun openProfit()
        fun openMutasi()
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated()

    }

    interface Interactor : BaseInteractorImpl {

    }

    interface InteractorOutput : BaseInteractorOutputImpl {

    }


}
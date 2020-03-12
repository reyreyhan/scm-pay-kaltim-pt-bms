package com.bm.main.pos.feature.report.main

import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl

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
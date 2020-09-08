package com.bm.main.scm.feature.reportscm.detail

import android.content.Intent
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl

interface ReportTransactionDetailContract {

    interface View : BaseViewImpl {

    }

    interface Presenter : BasePresenterImpl<View> {
        fun onDestroy()
        fun onViewCreated(intent: Intent)

        fun setAdapterList(adapter:ReportTransactionDetailAdapter)
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
    }

    interface InteractorOutput : BaseInteractorOutputImpl {
    }
}
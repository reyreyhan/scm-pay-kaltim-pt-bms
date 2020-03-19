package com.bm.main.pos.feature.scan

import android.content.Intent
import android.os.Bundle
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl


interface ScanCodeContract {
    interface View : BaseViewImpl {
        fun renderView()
        fun resumeCamera()
        fun hideShowSearchHeader(visibility:Int)
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewFragmentCreated(bundle: Bundle)
        fun onViewCreated(intent: Intent)
        fun onDestroy()
    }

    interface Interactor : BaseInteractorImpl {
        fun destroy()

    }

    interface InteractorOutput :BaseInteractorOutputImpl {

    }
}
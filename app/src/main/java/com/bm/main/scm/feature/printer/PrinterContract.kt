package com.bm.main.scm.feature.printer

import android.bluetooth.BluetoothDevice
import android.content.Intent
import com.bm.main.scm.base.BaseInteractorImpl
import com.bm.main.scm.base.BaseInteractorOutputImpl
import com.bm.main.scm.base.BasePresenterImpl
import com.bm.main.scm.base.BaseViewImpl

interface PrinterContract {

    interface View : BaseViewImpl {
        fun onClose()
        fun openBluetoothSetting()
        fun showEmpty()
        fun showContent()
        fun addAll(data: List<BluetoothDevice>)
        fun clearList()
    }

    interface Presenter : BasePresenterImpl<View> {
        fun onViewCreated(intent: Intent)
        fun onDestroy()
        fun checkDevice()
        fun onPrint(device: BluetoothDevice?)
    }

    interface Interactor : BaseInteractorImpl {
        fun onDestroy()
        fun onRestartDisposable()
    }

    interface InteractorOutput : BaseInteractorOutputImpl {

    }


}
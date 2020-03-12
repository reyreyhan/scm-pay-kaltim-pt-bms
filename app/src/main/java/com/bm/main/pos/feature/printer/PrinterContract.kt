package com.bm.main.pos.feature.printer

import android.bluetooth.BluetoothDevice
import android.content.Intent
import com.bm.main.pos.base.BaseInteractorImpl
import com.bm.main.pos.base.BaseInteractorOutputImpl
import com.bm.main.pos.base.BasePresenterImpl
import com.bm.main.pos.base.BaseViewImpl

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
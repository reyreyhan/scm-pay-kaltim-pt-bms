package com.bm.main.pos.feature.printer

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.Drawable
import com.bm.main.pos.R
import com.bm.main.pos.base.BasePresenter
import com.bm.main.pos.callback.BluetoothCallback
import com.bm.main.pos.models.transaction.DetailTransaction
import com.bm.main.pos.utils.AppConstant
import com.bm.main.pos.utils.BluetoothConnectTask
import com.bm.main.pos.utils.BluetoothStateReceiver
import com.bm.main.pos.utils.BluetoothUtil
import com.bm.main.pos.utils.print.PrinterUtil
import timber.log.Timber
import java.io.IOException

class PrinterPresenter(val context: Context,
                       val view: PrinterContract.View) : BasePresenter<PrinterContract.View>(), PrinterContract.Presenter, PrinterContract.InteractorOutput {

    private var interactor = PrinterInteractor(this)
    private var data: DetailTransaction? = null
    private var bluetoothCallback: BluetoothCallback? = null
    private var bluetoothSocket: BluetoothSocket? = null
    private var bluetoothStateReceiver: BluetoothStateReceiver? = null
    private var connectBluetoothTask: BluetoothConnectTask? = null
    private var connectedDevice: BluetoothDevice? = null

    override fun onViewCreated(intent: Intent) {
        bluetoothCallback = object : BluetoothCallback {
            override fun onPowerOn(intent: Intent?) {
                if (BluetoothUtil.isBluetoothOn()) {
                    checkDevice()
                } else {
                    view.showEmpty()
                }
            }

            override fun onPowerOff(intent: Intent?) {
                view.showEmpty()
            }

            override fun onConnected(socket: BluetoothSocket?,
                                     taskType: Int,
                                     device: BluetoothDevice?) {
                bluetoothSocket = socket
                view.hideLoadingDialog()
                PrinterUtil.print(bluetoothSocket,
                        data,
                        null,
                        context.getString(R.string.app_name),
                        Drawable.createFromStream(context.assets.open("logo_profit.bmp"), null),
                        device?.name.orEmpty())
            }

            override fun onError(msg: String?) {
                view.hideLoadingDialog()
                view.showToast(msg!!)
            }
        }

        data = intent.getSerializableExtra(AppConstant.DATA) as DetailTransaction
        val ims = context.assets.open("logo_profit.bmp")
        bluetoothStateReceiver = BluetoothStateReceiver(bluetoothCallback)
        val filter = IntentFilter()
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        context.registerReceiver(bluetoothStateReceiver, filter)
        checkDevice()
    }

    override fun onDestroy() {
        interactor.onDestroy()
        clearBluetoothSocket()
        cancelBluetoothConnectTask()
        context.unregisterReceiver(bluetoothStateReceiver)
    }

    override fun checkDevice() {
        val devices = BluetoothUtil.getPairedDevices()
        if (devices == null) {
            view.showEmpty()
            return
        }
        if (devices.isEmpty()) {
            view.showEmpty()
            return
        }
        view.clearList()
        view.showContent()
        view.addAll(devices)
    }

    override fun onPrint(device: BluetoothDevice?) {
        Timber.e("device: ${device?.name}")
        if (device == null) {
            view.showToast("Gagal menghubungkan printer")
            return
        }
        if (connectedDevice === device && bluetoothSocket != null) {
            PrinterUtil.print(bluetoothSocket,
                    data,
                    null,
                    context.getString(R.string.app_name),
                    Drawable.createFromStream(context.assets.open("logo_profit.bmp"), null),
                    device.name.orEmpty())
        } else {
            view.showLoadingDialog()
            connectBluetoothTask = BluetoothConnectTask(bluetoothCallback,
                    1,
                    "Gagal menghubungkan printer")
            connectBluetoothTask!!.execute(device)
            connectedDevice = device
        }
    }

    private fun clearBluetoothSocket() {
        try {
            if (bluetoothSocket != null) {
                bluetoothSocket!!.close()
            }

        } catch (e: IOException) {
            e.printStackTrace()
            bluetoothSocket = null
        }

    }

    private fun cancelBluetoothConnectTask() {
        if (connectBluetoothTask != null) {
            connectBluetoothTask!!.cancel(true)
            connectBluetoothTask = null
        }
    }
}
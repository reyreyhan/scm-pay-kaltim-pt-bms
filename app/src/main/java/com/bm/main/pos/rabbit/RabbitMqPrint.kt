package com.bm.main.pos.rabbit

import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.graphics.drawable.Drawable
import com.bm.main.fpl.activities.BaseActivity
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.pos.callback.BluetoothCallback
import com.bm.main.pos.callback.PermissionCallback
import com.bm.main.pos.utils.BluetoothConnectTask
import com.bm.main.pos.utils.BluetoothUtil
import com.bm.main.pos.utils.NotifUtil
import com.bm.main.pos.utils.PermissionUtil
import com.bm.main.pos.utils.print.PrinterUtil
import timber.log.Timber
import java.io.IOException
import java.util.concurrent.LinkedBlockingQueue

object RabbitMqPrint {

    private val listDevice by lazy { BluetoothUtil.getPairedPrinterDevices() }

    val task by lazy { LinkedBlockingQueue<String>() }

    @JvmStatic
    fun printStrukRabbit(
        msg: String,
        activity: Activity,
        imgFile: String? = null,
        then: ((Boolean) -> Unit)? = { success -> }
    ) {
        Timber.e("print from: ${activity.localClassName}")
        Timber.e("msg: ${msg}")
        NotifUtil.showIndeterminateNotif(activity, "Mencetak struk transaksi QR")

        PermissionUtil(activity).checkBluetoothPermission(object : PermissionCallback {
            override fun onSuccess() {
                Timber.e("bluetooth permission granted")
                if (BluetoothUtil.isBluetoothOn()) {
                    Timber.e("bluetooth turned on")

                    Timber.e("tasks: $task")
                    if (task.isNotEmpty()) {
                        task.add(msg)
                        Timber.e("queue task: $msg")
                        return
                    }

                    if (listDevice.isEmpty()) {
                        Timber.e("no device connected")
                        NotifUtil.cancelNotif(activity)
                        NotifUtil.showNotif(
                            activity,
                            "Gagal mencetak struk, tidak terdapat printer bluetooth yang aktif",
                            NotifUtil.ACTION_RETRY
                        )
                    } else {
                        BluetoothConnectTask(object : BluetoothCallback {
                            override fun onPowerOn(intent: Intent) {}
                            override fun onPowerOff(intent: Intent) {
                                Timber.e("${listDevice[0].name} - ${listDevice[0].address}: Power off")
                                listDevice.removeAt(0)
                                printStrukRabbit(msg, activity, imgFile)
                            }

                            override fun onConnected(
                                socket: BluetoothSocket,
                                taskType: Int,
                                device: BluetoothDevice
                            ) {
                                try {
                                    val after: (Boolean) -> Unit = {
                                        then?.invoke(it)
                                        if (it) task.poll()?.let {
                                            printStrukRabbit(
                                                it,
                                                activity,
                                                imgFile
                                            )
                                        }
                                        else task.peek()?.let {
                                            printStrukRabbit(
                                                it,
                                                activity,
                                                imgFile
                                            )
                                        }
                                    }

                                    val icon: String
                                    val footer: String
                                    if (PreferenceClass.getString("qrStrukJogjaKita") == "1") {
                                        icon = "icon-jogjakita-trans.png"
                                        footer = ""
                                    } else {
                                        icon = "logo_profit.bmp"
                                        footer = "https://profit.fastpay.co.id"
                                    }

                                    imgFile?.let {
                                        PrinterUtil.printImg(
                                            socket,
                                            Drawable.createFromPath(it),
                                            Drawable.createFromStream(activity.assets.open(icon),null),
                                            footer,
                                            after
                                        )
                                    } ?: run {
                                        PrinterUtil.printText(
                                            socket,
                                            msg
                                                .replace(
                                                    "STORENAME",
                                                    PreferenceClass.getString("nama_toko")
                                                )
                                                .replace(
                                                    "ADDRESS",
                                                    PreferenceClass.getString("alamat_toko")
                                                )
                                                .replace(
                                                    "PHONE",
                                                    PreferenceClass.getNotelp_pemilik().orEmpty()
                                                ),
                                            Drawable.createFromStream(activity.assets.open(icon),null),
                                            footer,
                                            after
                                        )
                                    }
                                } catch (e: IOException) {
                                    e.printStackTrace()
                                }

                                NotifUtil.cancelNotif(activity)
                                listDevice.union(BluetoothUtil.getPairedDevices())
                            }

                            override fun onError(msg: String) {
                                Timber.e("${listDevice[0].name} - ${listDevice[0].address}: Error $msg")
                                listDevice.removeAt(0)
                                printStrukRabbit(msg, activity, imgFile)
                            }
                        }, 1, "").execute(listDevice[0])
                    }
                } else {
                    activity.intent.putExtra(BaseActivity.QR_STRUK_PRINT, msg)
                    activity.intent.putExtra(BaseActivity.QR_IMG_PRINT, imgFile)
                    BluetoothUtil.openBluetooth(activity)
                }
            }

            override fun onFailed() {
                NotifUtil.cancelNotif(activity)
                NotifUtil.showNotif(
                    activity,
                    "Gagal mencetak struk, aplikasi tidak diijinkan untuk menggunakan bluetooth",
                    NotifUtil.ACTION_RETRY
                )
            }
        })
    }
}
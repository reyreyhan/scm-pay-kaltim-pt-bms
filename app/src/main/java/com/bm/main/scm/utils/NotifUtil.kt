package com.bm.main.scm.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.bm.main.fpl.activities.BaseActivity
import com.bm.main.scm.R
import com.bm.main.scm.feature.drawer.DrawerActivity

object NotifUtil {

    @JvmStatic
    val notifId = 9
    val ACTION_RETRY = 1

    private fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.app_name)
            val descriptionText = context.getString(R.string.app_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(name, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @JvmStatic
    fun showNotif(context: Context, message: String, action: Int = 0) {
        createChannel(context)
        val title = context.getString(R.string.app_name)
        val notificationIntent =
            Intent(context, DrawerActivity::class.java).putExtra("page", "qr-transaction")

        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val intent = PendingIntent.getActivity(
            context, 0, notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(context, title)
        mBuilder
            .setSmallIcon(R.drawable.logo)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setContentIntent(intent)
            .setDefaults(Notification.DEFAULT_ALL)
            .setAutoCancel(true)

        if (action == ACTION_RETRY)
            mBuilder.addAction(
                android.R.drawable.btn_default,
                "Ulangi",
                PendingIntent.getActivity(
                    context,
                    0,
                    notificationIntent.putExtra(
                        BaseActivity.ACTION_REQ_BLUETOOTH_PERM,
                        ""
                    ).putExtra(BaseActivity.QR_STRUK_PRINT, message),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )

        val notificationmanager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationmanager.notify(notifId, mBuilder.build())
    }

    @JvmStatic
    fun showIndeterminateNotif(context: Context, message: String) {
        createChannel(context)
        val title = context.getString(R.string.app_name)
//        val notificationIntent =
//            Intent(context, DrawerActivity::class.java).putExtra("page", "qr-transaction")

//        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
//        val intent = PendingIntent.getActivity(
//            context, 0, notificationIntent,
//            PendingIntent.FLAG_UPDATE_CURRENT
//        )

        val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(context, title)
        mBuilder
            .setSmallIcon(R.drawable.logo)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setProgress(0, 0, true)
//            .setContentIntent(intent)
            .setDefaults(Notification.DEFAULT_ALL)
            .setAutoCancel(true)

        val notificationmanager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationmanager.notify(notifId, mBuilder.build())
    }

    @JvmStatic
    fun cancelNotif(context: Context) {
        val notificationmanager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationmanager.cancel(notifId)
    }
}
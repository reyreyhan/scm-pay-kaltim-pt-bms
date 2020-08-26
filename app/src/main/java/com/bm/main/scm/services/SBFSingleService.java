package com.bm.main.scm.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import static com.google.android.gms.stats.GCoreWakefulBroadcastReceiver.completeWakefulIntent;

/**
 * Created by sarifhidayat on 4/6/18.
 **/
public class SBFSingleService extends BroadcastReceiver{
    @NonNull
    static String TAG=SBFSingleService.class.getSimpleName();
    @Override
    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmMgr.cancel(alarmIntent);
        completeWakefulIntent(intent);

        // start the GcmTaskService
     //   SBFService.showNotification(context);

      //  Log.i(TAG, "onReceive: BroadcastReceiver");
    }
//    public SBFSingleService(String airLineName) {
//        super("SBFService");
//    }
//    RemoteMessage gcm;
//    @Override
//    protected void onHandleIntent(@Nullable Intent intent) {
//        Bundle extras = intent.getExtras();
//
//        String messageType = gcm.getMessageType();
//        android.util.Log.i("hi","test");
//        if (!extras.isEmpty()) {
//
//            if (RemoteMessage.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
//               // Logger.logInUI(tag, "Received: " + extras.toString());
//            }
//        }
//        FirebaseDataReceiver.completeWakefulIntent(intent);
//    }
}

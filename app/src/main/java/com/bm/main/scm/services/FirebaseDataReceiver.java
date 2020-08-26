package com.bm.main.scm.services;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.NonNull;
import android.util.Log;

import static com.google.android.gms.stats.GCoreWakefulBroadcastReceiver.completeWakefulIntent;
import static com.google.android.gms.stats.GCoreWakefulBroadcastReceiver.startWakefulService;

//import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by sarifhidayat on 7/24/17.
 */

public class FirebaseDataReceiver extends BroadcastReceiver {
    private final String TAG = "FirebaseDataReceiver";

    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
      //  Toast.makeText(context,"zdkfdlknvds,nflds",Toast.LENGTH_LONG).show();
        Log.d(TAG, "I'm in!!!"+intent.getAction());
//implements SBFApplication.ActivityLifecycleCallback
        // cancel any further alarms

        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_ONE_SHOT);
        alarmMgr.cancel(alarmIntent);
        completeWakefulIntent(intent);

        // start the GcmTaskService
        //SBFService.showNotification(context);


        if (intent.getExtras() != null) {
//            AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//            PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//            alarmMgr.cancel(alarmIntent);
//            completeWakefulIntent(intent);
            for (String key : intent.getExtras().keySet()) {
                Object value = intent.getExtras().get(key);
//                NotificationModel nm=new NotificationModel();
//                if(key.equals("title")) {
//                    nm.setTitle(String.valueOf(value));
//                }
//                if(key.equals("message")) {
//                    nm.setMessage(String.valueOf(value));
//                }
//                if(key.equals("image")) {
//                    nm.setImage(String.valueOf(value));
//                }

                Log.d("FirebaseDataReceiver", "Key: " + key + " Value: " + value);
            }


            ComponentName comp = new ComponentName(context.getPackageName(), SBFService.class.getName());
            startWakefulService(context, (intent.setComponent(comp)));
            setResultCode(Activity.RESULT_OK);
        }
    }

    public FirebaseDataReceiver() {
        super();
      //  Log.i(TAG, "FirebaseDataReceiver: ");
    }

    @Override
    public IBinder peekService(Context myContext, Intent service) {
        Log.d(TAG, "peekService: ");
        return super.peekService(myContext, service);

    }
}

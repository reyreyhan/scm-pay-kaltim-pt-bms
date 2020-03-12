package com.bm.main.pos.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import android.util.Log;

import com.bm.main.fpl.utils.Utils;

/**
 * Created by sarifhidayat on 4/10/18.
 **/
public class BootBroadcast extends BroadcastReceiver {
    final static String TAG = BootBroadcast.class.getSimpleName();
    Context mContext;

    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
        mContext = context;

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pi = PendingIntent.getService(context, 0, new Intent(context, SBFService.class), PendingIntent.FLAG_UPDATE_CURRENT);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, 5000, pi);

        String action = intent.getAction();
        Log.d(TAG, "onReceive: " + action);
        Intent i = new Intent(context, SBFService.class);
        if (Intent.ACTION_SCREEN_OFF.equals(action)) {
            Log.d(TAG, "Screen is turn off.");
            if ("com.google.android.c2dm.intent.RECEIVE".equals(action)) {
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startService(i);
            }
        } else if (Intent.ACTION_SCREEN_ON.equals(action)) {
            Log.d(TAG, "Screen is turn on.");
            if (!Utils.isMyServiceRunning(SBFService.class, context)) {
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startService(i);
            }
        }
    }
}
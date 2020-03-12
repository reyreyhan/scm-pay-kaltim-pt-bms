package com.bm.main.pos.services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import timber.log.Timber;

public class ScreenOnOffBackgroundService extends Service {

    @Nullable
    private BroadcastReceiver screenOnOffReceiver = null;
    private final LocalBinder mBinder = new LocalBinder();
    protected Handler handler;

    public class LocalBinder extends Binder {
        @NonNull
        public ScreenOnOffBackgroundService getService() {
            return ScreenOnOffBackgroundService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Create an IntentFilter instance.
        IntentFilter intentFilter = new IntentFilter();

        // Add network connectivity change action.
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("com.google.android.c2dm.intent.RECEIVE");
        intentFilter.addAction("com.google.android.c2dm.intent.REGISTRATION");

        // Set broadcast receiver priority.
        intentFilter.setPriority(999);

        // Create a network change broadcast receiver.
        screenOnOffReceiver = new BootBroadcast();

        // Register the broadcast receiver with the intent filter object.
        registerReceiver(screenOnOffReceiver, intentFilter);

        Timber.d("Service onCreate: screenOnOffReceiver is registered.");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Unregister screenOnOffReceiver when destroy.
        if (screenOnOffReceiver != null) {
            unregisterReceiver(screenOnOffReceiver);
            Timber.d("Service onDestroy: screenOnOffReceiver is unregistered.");
        }
    }
}
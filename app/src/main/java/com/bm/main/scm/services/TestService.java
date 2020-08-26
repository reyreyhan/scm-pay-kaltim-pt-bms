package com.bm.main.scm.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by sarifhidayat on 4/10/18.
 **/
public class TestService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }
}

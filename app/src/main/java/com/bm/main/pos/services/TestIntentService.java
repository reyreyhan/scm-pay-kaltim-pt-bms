package com.bm.main.pos.services;

import android.app.IntentService;
import android.content.Intent;
import androidx.annotation.NonNull;
import android.util.Log;

/**
 * Created by sarifhidayat on 4/10/18.
 **/
public class TestIntentService extends IntentService {
    @NonNull
    static String TAG=TestIntentService.class.getSimpleName();
    public TestIntentService() {
        super("TestIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: ");
    }
}

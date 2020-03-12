package com.bm.main.pos.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import android.util.Log;

/**
 * Created by sarifhidayat on 4/12/19.
 **/
public class RestarterBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(@NonNull Context context, Intent intent) {
        Log.d(RestarterBroadcastReceiver.class.getSimpleName(), "Service Stops! Oooooooooooooppppssssss!!!!");
        context.startService(new Intent(context, SBFService.class));
    }
}

package com.bm.main.fpl.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;

import timber.log.Timber;

/**
 * Created by sarifhidayat on 10/26/18.
 **/
public class DetectConnection {

    public static boolean isOnline(@NonNull Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean isConnected = false;
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            isConnected = (activeNetwork != null) && (activeNetwork.isConnectedOrConnecting());
        }
        Timber.d("isOnline: %s", isConnected);

        return isConnected;
    }
}

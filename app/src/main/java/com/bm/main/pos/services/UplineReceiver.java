package com.bm.main.pos.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;

import com.bm.main.fpl.utils.PreferenceClass;
import com.google.android.gms.analytics.CampaignTrackingReceiver;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

//import java.net.URL;

public class UplineReceiver extends BroadcastReceiver {
    @NonNull
    static String TAG=UplineReceiver.class.getSimpleName();
//    InstallReferrerClient mReferrerClient;
private static final String ACTION_INSTALL_REFERRER = "com.android.vending.INSTALL_REFERRER";
    private static final String KEY_REFERRER = "referrer";

    @Override
    public void onReceive(@NonNull Context context, @Nullable final Intent intent) {
//        mReferrerClient = InstallReferrerClient.newBuilder(this).build();
//        mReferrerClient.startConnection(this);
       // PreferenceClass preferenceClass = new PreferenceClass(context);
        Log.d(TAG, "onReceive: "+intent);



        //
        if (intent == null) {
            Log.e("ReferrerReceiver", "Intent is null");
            return;
        }
        if (!ACTION_INSTALL_REFERRER.equals(intent.getAction())) {
            Log.e("ReferrerReceiver", "Wrong action! Expected: " + ACTION_INSTALL_REFERRER + " but was: " + intent.getAction());
            return;
        }


        final Bundle extras = intent.getExtras();
        if (intent.getExtras() == null) {
            Log.e("ReferrerReceiver", "No data in intent");
            return;
        }


        //if (extras != null) {
            String referrerString = extras.getString(KEY_REFERRER); //==> ini query string nya
            //   String [] ref=referrerString.split("\\&");
            Log.d(TAG, "onReceive: " + referrerString);
            try {

                assert referrerString != null;
                for (String key : splitQuery(referrerString).keySet()) {
                    Object value = splitQuery(referrerString).get(key);
                    Log.d("RefererDataReceiver", "Key: " + key + " Value: " + value);
                    if (key.equals("utm_source")) {
                        Log.d("utm_source", "onReceive: " + value);
                        PreferenceClass.setUpline(String.valueOf(value));
                    }
                }

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                PreferenceClass.setUpline("");
                //  Log.d("utm_source", "UnsupportedEncodingException: " +ref[0].toString() );
            }



//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    for (String key : splitQuery(extras.getString(KEY_REFERRER)).keySet()) {
//                        Object value = splitQuery(extras.getString(KEY_REFERRER)).get(key);
//                        Log.d("RefererDataReceiver", "Key: " + key + " Value: " + value);
//                        if (key.equals("utm_source")) {
//                            Log.d("utm_source", "onReceive: " + value);
//                            PreferenceClass.setUpline(String.valueOf(value));
//                        }
////                        try {
////                            Log.d(TAG, "run: MARKER, key "+ String.valueOf(intent.getExtras().get(key)));
////                        } catch (Exception e) {
////                            Log.d(TAG, "run: error(MARKER, caught exception in on key retrieval ", e);
////                        }
//                    }
//                } catch (Exception e) {
//                    Log.d(TAG, "caught exception in key loop ", e);
//                }
//            }
//        });
//        executorService.shutdown();

        new CampaignTrackingReceiver().onReceive(context, intent);

//        } else {
//            //   Log.d("utm_source", "extra null");
//            PreferenceClass.setUpline("");
//        }


    }

//    public static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
//        Map<String, String> query_pairs = new LinkedHashMap<>();
//        String query = url.getQuery();
//        String[] pairs = query.split("&");
//        for (String pair : pairs) {
//            int idx = pair.indexOf("=");
//            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
//        }
//        return query_pairs;
//    }

    @NonNull
    public static Map<String, String> splitQuery(@NonNull String url) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap<>();
        String[] pairs = url.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return query_pairs;
    }

//    @Override
//    public void onInstallReferrerSetupFinished(int responseCode) {
//        switch (responseCode) {
//            case InstallReferrerClient.InstallReferrerResponse.OK:
//                try {
//                    Log.d(TAG, "InstallReferrer conneceted");
//                    ReferrerDetails response = mReferrerClient.getInstallReferrer();
//                    Log.d(TAG, "onInstallReferrerSetupFinished: "+response.getInstallReferrer());
//                    //   handleReferrer(response);
//                    mReferrerClient.endConnection();
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//                break;
//            case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
//                Log.d(TAG, "InstallReferrer not supported");
//                break;
//            case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
//                Log.d(TAG, "Unable to connect to the service");
//                break;
//            default:
//                Log.d(TAG, "responseCode not found.");
//        }
//
//    }
//
//    @Override
//    public void onInstallReferrerServiceDisconnected() {
//        Log.d(TAG, "onInstallReferrerServiceDisconnected: ");
//        mReferrerClient.startConnection(this);
//    }
}

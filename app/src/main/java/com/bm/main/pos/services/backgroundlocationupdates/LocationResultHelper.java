/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bm.main.pos.services.backgroundlocationupdates;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bm.main.pos.R;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Class to process location results.
 */
class LocationResultHelper {

    final static String KEY_LOCATION_UPDATES_RESULT = "location";
    final static String KEY_PLACE_UPDATES_RESULT = "place";

    final private static String PRIMARY_CHANNEL = "default";


    private Context mContext;
    private List<Location> mLocations;
    private NotificationManager mNotificationManager;

    @TargetApi(Build.VERSION_CODES.Q)
    LocationResultHelper(Context context, List<Location> locations) {
        mContext = context;
        mLocations = locations;

//        NotificationChannel channel = new NotificationChannel(PRIMARY_CHANNEL,
//                context.getString(R.string.default_channel), NotificationManager.IMPORTANCE_DEFAULT);
//        channel.setLightColor(Color.GREEN);
//        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
//        getNotificationManager().createNotificationChannel(channel);
    }

    /**
     * Returns the title for reporting about a list of {@link Location} objects.
     */
    @NonNull
    private String getLocationResultTitle() {
        String numLocationsReported = mContext.getResources().getQuantityString(
                R.plurals.num_locations_reported, mLocations.size(), mLocations.size());
        return numLocationsReported + ": " + DateFormat.getDateTimeInstance().format(new Date());
    }

    @NonNull
    private String getLocationResultText() {
        if (mLocations.isEmpty()) {
            return mContext.getString(R.string.unknown_location);
        }

        /*------- To get city airLineName from coordinates -------- */
        String cityName = "";
        String placeName = "";
        String slongitude="0.0";
        String slatitude="0.0";
        Geocoder gcd = new Geocoder(mContext, Locale.getDefault());
        List<Address> addresses;


        StringBuilder sb = new StringBuilder();
//        for (Location location : mLocations) {
//            sb.append("(");
//            sb.append(location.getLatitude());
//            sb.append(", ");
//            sb.append(location.getLongitude());
//            sb.append(")");
//
//            sb.append("\n");
//        }

        for (Location location : mLocations) {
            try {
                addresses = gcd.getFromLocation(location.getLatitude(),
                        location.getLongitude(), 1);
                slongitude=String.valueOf(location.getLongitude());
                slatitude=String.valueOf(location.getLatitude());
                if (addresses.size() > 0) {
                    // System.out.println(addresses.get(0).getLocality());
                    // System.out.println(addresses.get(0));

                    cityName = addresses.get(0).getLocality();
                    placeName = addresses.get(0).getAddressLine(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String s = slongitude + "," + slatitude ;
        sb.append(s);


        return sb.toString();
    }
    @NonNull
    private String getPlaceResultText() {
        if (mLocations.isEmpty()) {
            return mContext.getString(R.string.unknown_location);
        }

        /*------- To get city airLineName from coordinates -------- */
        String cityName = "";
        String placeName = "";
        String slongitude="0.0";
        String slatitude="0.0";
        Geocoder gcd = new Geocoder(mContext, Locale.getDefault());
        List<Address> addresses;


        StringBuilder sb = new StringBuilder();
//        for (Location location : mLocations) {
//            sb.append("(");
//            sb.append(location.getLatitude());
//            sb.append(", ");
//            sb.append(location.getLongitude());
//            sb.append(")");
//
//            sb.append("\n");
//        }

        for (Location location : mLocations) {
            try {
                addresses = gcd.getFromLocation(location.getLatitude(),
                        location.getLongitude(), 1);
                slongitude=String.valueOf(location.getLongitude());
                slatitude=String.valueOf(location.getLatitude());
                if (addresses.size() > 0) {
                    // System.out.println(addresses.get(0).getLocality());
                    // System.out.println(addresses.get(0));

                    cityName = addresses.get(0).getLocality();
                    placeName = addresses.get(0).getAddressLine(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        String s = placeName;
        String place=placeName==null?"": placeName.replaceAll(",", "-");
        sb.append(place);


        return sb.toString();
    }

    /**
     * Saves location result as a string to {@link android.content.SharedPreferences}.
     */
    void saveResultsLocation() {
        PreferenceManager.getDefaultSharedPreferences(mContext)
                .edit()
                .putString(KEY_LOCATION_UPDATES_RESULT,
                        getLocationResultText())
                .apply();
    }
    void saveResultsPlace() {
        PreferenceManager.getDefaultSharedPreferences(mContext)
                .edit()
                .putString(KEY_PLACE_UPDATES_RESULT,
                        getPlaceResultText())
                .apply();
    }

    /**
     * Fetches location results from {@link android.content.SharedPreferences}.
     */
    @Nullable
    static String getSavedLocationResult(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(KEY_LOCATION_UPDATES_RESULT, "");
    }

    @Nullable
    static String getSavedPlaceResult(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(KEY_PLACE_UPDATES_RESULT, "");
    }

    /**
     * Get the notification mNotificationManager.
     * <p>
     * Utility method as this helper works with it a lot.
     *
     * @return The system service NotificationManager
     */
//    private NotificationManager getNotificationManager() {
//        if (mNotificationManager == null) {
//            mNotificationManager = (NotificationManager) mContext.getSystemService(
//                    Context.NOTIFICATION_SERVICE);
//        }
//        return mNotificationManager;
//    }

    /**
     * Displays a notification with the location results.
     */
//    @TargetApi(Build.VERSION_CODES.O)
//    void showNotification() {
//        Intent notificationIntent = new Intent(mContext, MainActivity.class);
//
//        // Construct a task stack.
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
//
//        // Add the main Activity to the task stack as the parent.
//        stackBuilder.addParentStack(MainActivity.class);
//
//        // Push the content Intent onto the stack.
//        stackBuilder.addNextIntent(notificationIntent);
//
//        // Get a PendingIntent containing the entire back stack.
//        PendingIntent notificationPendingIntent =
//                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        Notification.Builder notificationBuilder = new Notification.Builder(mContext,
//                PRIMARY_CHANNEL)
//                .setContentTitle(getLocationResultTitle())
//                .setContentText(getLocationResultText())
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setAutoCancel(true)
//                .setContentIntent(notificationPendingIntent);
//
//        getNotificationManager().notify(0, notificationBuilder.build());
//    }
}

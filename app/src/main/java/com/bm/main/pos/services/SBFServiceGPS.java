package com.bm.main.pos.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.bm.main.fpl.constants.globalconstant;
import com.bm.main.fpl.utils.PreferenceClass;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by sarifhidayat on 3/14/19.
 **/
//public class SBFServiceGPS extends Service
//{
//    private static final String TAG = "BOOMBOOMTESTGPS";
//    private LocationManager mLocationManager = null;
//    private static final int LOCATION_INTERVAL = 1000;
//    private static final float LOCATION_DISTANCE = 10f;
//
//    private class LocationListener implements android.location.LocationListener{
//        Location mLastLocation;
//         LocationListener(String provider)
//        {
//            Log.e(TAG, "LocationListener " + provider);
//            mLastLocation = new Location(provider);
//        }
//        public void onLocationChanged(Location location)
//        {
//            Log.e(TAG, "onLocationChanged: " + location.getLatitude() +"....."+ location.getLongitude());
//            globalconstant.lat  = location.getLatitude();
//            globalconstant.lon  = location.getLongitude();
//            String lat = String.valueOf(location.getLatitude());
//            String lng = String.valueOf(location.getLongitude());
//            PreferenceClass.putString("lat", lat);
//            PreferenceClass.putString("long", lng);
//
//            PreferenceClass.putString("location", lat + "," + lng);
//
//            /*------- To get city airLineName from coordinates -------- */
//            String cityName = null;
//            String placeName = null;
//            Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
//            List<Address> addresses;
//            try {
//                addresses = gcd.getFromLocation(location.getLatitude(),
//                        location.getLongitude(), 1);
//                if (addresses.size() > 0) {
//                    // System.out.println(addresses.get(0).getLocality());
//                    // System.out.println(addresses.get(0));
//                    cityName = addresses.get(0).getLocality();
//                    placeName = addresses.get(0).getAddressLine(0);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            String s = lng + "\n" + lat + "\n\nMy Current City is: "
//                    + placeName;
//             Log.e(TAG, "onLocationChanged: " + s);
//            String place = placeName == null ? "" : placeName.replaceAll(",", "-");
//            PreferenceClass.putString("place", place);
//
//
//
//
//         //   Toast.makeText(getApplicationContext(), location.getLatitude() +"....."+ location.getLongitude(), Toast.LENGTH_LONG).show();
//            mLastLocation.set(location);
//        }
//        public void onProviderDisabled(String provider)
//        {
//            Log.e(TAG, "onProviderDisabled: " + provider);
//        }
//        public void onProviderEnabled(String provider)
//        {
//            Log.e(TAG, "onProviderEnabled: " + provider);
//        }
//        public void onStatusChanged(String provider, int status, Bundle extras)
//        {
//            Log.e(TAG, "onStatusChanged: " + provider);
//        }
//    }
//    LocationListener[] mLocationListeners = new LocationListener[] {
//            new LocationListener(LocationManager.PASSIVE_PROVIDER),
//            new LocationListener(LocationManager.NETWORK_PROVIDER)
//    };
//    @Override
//    public IBinder onBind(Intent arg0)
//    {
//        return null;
//    }
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId)
//    {
//        Log.e(TAG, "onStartCommand");
//        super.onStartCommand(intent, flags, startId);
//        return START_STICKY;
//    }
//    @Override
//    public void onCreate()
//    {
//        Log.e(TAG, "onCreate");
//        initializeLocationManager();
//        try {
//            mLocationManager.requestLocationUpdates(
//                    LocationManager.NETWORK_PROVIDER,LOCATION_INTERVAL, LOCATION_DISTANCE,
//                    mLocationListeners[1]);
//
////            if(!mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
////                isAvailableNetworkProvider=false;
////            }
//
//        } catch (SecurityException ex) {
//            Log.e(TAG, "fail to request location update, ignore", ex);
//        } catch (IllegalArgumentException ex) {
//            Log.e(TAG, "network provider does not exist, " + ex.getMessage());
//        }
//        try {
//            mLocationManager.requestLocationUpdates(
//                    LocationManager.PASSIVE_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
//                    mLocationListeners[0]);
//        } catch (SecurityException ex) {
//            Log.e(TAG, "fail to request location update, ignore", ex);
//        } catch (IllegalArgumentException ex) {
//            Log.e(TAG, "passive provider does not exist " + ex.getMessage());
//        }
//    }
//    @Override
//    public void onDestroy()
//    {
//        Log.e(TAG, "onDestroy");
//        super.onDestroy();
//        if (mLocationManager != null) {
//            for (int i = 0; i < mLocationListeners.length; i++) {
//                try {
//                    mLocationManager.removeUpdates(mLocationListeners[i]);
//                } catch (Exception ex) {
//                    Log.e(TAG, "fail to remove location listners, ignore", ex);
//                }
//            }
//        }
//    }
//    private void initializeLocationManager() {
//        Log.e(TAG, "initializeLocationManager");
//        if (mLocationManager == null) {
//            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
//        }
//    }


public class SBFServiceGPS extends Service
{
    //    private final LocationServiceBinder binder = new LocationServiceBinder();
    private static final String TAG = "SBFServiceGPS";
    private LocationListener[] mLocationListener;
    @Nullable
    private LocationManager mLocationManager = null;
    private static final long LOCATION_INTERVAL = 1000;
    private static final long LOCATION_DISTANCE = 0;
    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
        // return binder;
    }
    private class LocationListener implements android.location.LocationListener{
        Location mLastLocation;
        LocationListener(String provider)
        {
            Log.d(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
        }
        public void onLocationChanged(@NonNull Location location)
        {
            Log.d(TAG, "onLocationChanged: " + location.getLatitude() +"....."+ location.getLongitude());
            globalconstant.lat  = location.getLatitude();
            globalconstant.lon  = location.getLongitude();
            String lat = String.valueOf(location.getLatitude());
            String lng = String.valueOf(location.getLongitude());
            PreferenceClass.putString("lat", lat);
            PreferenceClass.putString("long", lng);

            PreferenceClass.putString("location", lat + "," + lng);

            /*------- To get city airLineName from coordinates -------- */
//            String cityName = null;
            String placeName = null;
            Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(location.getLatitude(),
                        location.getLongitude(), 1);
                if (addresses.size() > 0) {
                    // System.out.println(addresses.get(0).getLocality());
                    // System.out.println(addresses.get(0));
//                    cityName = addresses.get(0).getLocality();
                    placeName = addresses.get(0).getAddressLine(0);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String s = lng + "\n" + lat + "\n\nMy Current City is: "
                    + placeName;
            Log.d(TAG, "onLocationChanged: " + s);
            String place = placeName == null ? "" : placeName.replaceAll(",", "-");
            PreferenceClass.putString("place", place);




            Toast.makeText(getApplicationContext(), location.getLatitude() +"....."+ location.getLongitude(), Toast.LENGTH_LONG).show();
            mLastLocation.set(location);
        }
        public void onProviderDisabled(String provider)
        {
            Log.d(TAG, "onProviderDisabled: " + provider);
//            if(provider.equals("network")){
//                isAvailableNetworkProvider=false;
//            }else {
//                isAvailableGPSProvider=false;
//            }
        }
        public void onProviderEnabled(String provider)
        {
            Log.d(TAG, "onProviderEnabled: " + provider);
//            if(provider.equals("network")){
//                isAvailableNetworkProvider=true;
//            }else {
//                isAvailableGPSProvider=true;
//            }
        }
        public void onStatusChanged(String provider, int status, Bundle extras)
        {
            Log.d(TAG, "onStatusChanged: " + provider);
        }
    }
//    LocationListener[] mLocationListeners = new LocationListener[] {
//            new LocationListener(LocationManager.PASSIVE_PROVIDER),
//            new LocationListener(LocationManager.NETWORK_PROVIDER)
//    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.d(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }
    @Override
    public void onCreate()
    {
        Log.d(TAG, "onCreate");
        startTracking();
        // startForeground(87654321, getNotification());
//        initializeLocationManager();
//        try {
//            mLocationManager.requestLocationUpdates(
//                    LocationManager.NETWORK_PROVIDER,LOCATION_INTERVAL, LOCATION_DISTANCE,
//                    mLocationListeners[1]);
//            Log.d(TAG, "onCreate: NETWORK_PROVIDER "+mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) );
////            if(!mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
//                isAvailableNetworkProvider=mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
////            }
//
//        } catch (java.lang.SecurityException ex) {
//            Log.d(TAG, "fail to request location update, ignore", ex);
//        } catch (IllegalArgumentException ex) {
//            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
//        }
//        try {
//            mLocationManager.requestLocationUpdates(
//                    LocationManager.PASSIVE_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
//                    mLocationListeners[0]);
//            Log.d(TAG, "onCreate: GPS_PROVIDER "+mLocationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER));
////            if(!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
////                isAvailableGPSProvider=false;
////            }else{
////                isAvailableGPSProvider=mLocationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER);
////            }
//        } catch (java.lang.SecurityException ex) {
//            Log.d(TAG, "fail to request location update, ignore", ex);
//        } catch (IllegalArgumentException ex) {
//            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
//        }
    }
    @Override
    public void onDestroy()
    {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        if (mLocationManager != null) {
            for (LocationListener mLocationListener : mLocationListener) {
                try {
                    mLocationManager.removeUpdates(mLocationListener);
                } catch (Exception ex) {
                    Log.d(TAG, "fail to remove location listners, ignore", ex);
                }
            }
        }
    }
    private void initializeLocationManager() {
        Log.d(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }
    //    public class LocationServiceBinder extends Binder {
//        public AMMSServiceGPS getService() {
//            return AMMSServiceGPS.this;
//        }
//    }
    public void startTracking() {
        initializeLocationManager();
        mLocationListener = new LocationListener[]{
                new LocationListener(LocationManager.PASSIVE_PROVIDER),
                new LocationListener(LocationManager.NETWORK_PROVIDER)
        };

        try {
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, mLocationListener[1]);

        } catch (java.lang.SecurityException ex) {
            // Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            // Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }

        try {
            mLocationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, mLocationListener[0]);

        } catch (java.lang.SecurityException ex) {
            // Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            // Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
//        try {
//            mLocationManager.requestLocationUpdates(
//                    LocationManager.NETWORK_PROVIDER,LOCATION_INTERVAL, LOCATION_DISTANCE,
//                    mLocationListeners[1]);
//            Log.d(TAG, "onCreate: NETWORK_PROVIDER "+mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) );
////            if(!mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
//            isAvailableNetworkProvider=mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
////            }
//
//        } catch (java.lang.SecurityException ex) {
//            Log.d(TAG, "fail to request location update, ignore", ex);
//        } catch (IllegalArgumentException ex) {
//            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
//        }
//        try {
//            mLocationManager.requestLocationUpdates(
//                    LocationManager.PASSIVE_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
//                    mLocationListeners[0]);
//            Log.d(TAG, "onCreate: GPS_PROVIDER "+mLocationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER));
////            if(!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
////                isAvailableGPSProvider=false;
////            }else{
////                isAvailableGPSProvider=mLocationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER);
////            }
//        } catch (java.lang.SecurityException ex) {
//            Log.d(TAG, "fail to request location update, ignore", ex);
//        } catch (IllegalArgumentException ex) {
//            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
//        }
    }
}

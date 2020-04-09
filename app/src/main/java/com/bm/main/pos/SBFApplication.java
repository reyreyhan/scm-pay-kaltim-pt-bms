package com.bm.main.pos;

import android.app.Activity;
import android.app.Application;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Process;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.bm.main.fpl.constants.Info;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;
import com.bm.main.pos.callback.BluetoothCallback;
import com.bm.main.pos.callback.PermissionCallback;
import com.bm.main.pos.di.AppComponent;
import com.bm.main.pos.di.AppComponentProvider;
import com.bm.main.pos.di.DaggerAppComponent;
import com.bm.main.pos.feature.printer.PrinterActivity;
import com.bm.main.pos.rabbit.RabbitMqThread;
import com.bm.main.pos.services.ScreenOnOffBackgroundService;
import com.bm.main.pos.utils.AppConstant;
import com.bm.main.pos.utils.BluetoothConnectTask;
import com.bm.main.pos.utils.BluetoothUtil;
import com.bm.main.pos.utils.DialogUtils;
import com.bm.main.pos.utils.PermissionUtil;
import com.bm.main.pos.utils.print.PrinterUtil;
import com.bm.main.single.ftl.utils.MemoryStore;
import com.bm.sc.bebasbayar.social.di.UserComponent;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import io.fabric.sdk.android.Fabric;
import kotlin.Unit;
import timber.log.Timber;

/**
 * Created by sarifhidayat on 4/5/18.
 **/

public class SBFApplication extends MultiDexApplication implements Application.ActivityLifecycleCallbacks, AppComponentProvider {

    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;

    public static DisplayMetrics displayMetrics;
    public static Configuration config;
    private static SBFApplication instance;
    private static FirebaseAnalytics firebaseAnalytics;
    public Boolean isAppBackground = false;
    public RabbitMqThread rabbitThread;

    @Override
    public void onCreate() {

        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        instance = this;

//        if (BuildConfig.DEBUG) {
//            Timber.plant(new Timber.DebugTree());
//        }

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
//        AndroidThreeTen.init(this);
        PreferenceClass.initialize();
        MemoryStore.initialize();
RequestUtils.initialize();
        sAnalytics = GoogleAnalytics.getInstance(this);
        sAnalytics.setLocalDispatchPeriod(1800);

        Fabric.with(this, new Crashlytics());

        FirebaseApp.initializeApp(this);

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        sTracker = getDefaultTracker();
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker(R.xml.global_tracker);
            sTracker.enableExceptionReporting(true);
            sTracker.enableAdvertisingIdCollection(true);
            sTracker.enableAutoActivityTracking(true);
            sTracker.setAppVersion(Info.getPackageInfo(this).versionName);
            // sTracker.setReferrer("gclid=gclidValue");
        }

        registerActivityLifecycleCallbacks(this);
        try {
            ProviderInstaller.installIfNeeded(this);
        } catch (GooglePlayServicesRepairableException e) {
            Timber.e(e);
            Toast.makeText(this, "Layanan Google Play tidak dapat diperbaiki", Toast.LENGTH_LONG).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            Timber.e(e);
            Toast.makeText(this, "Google Play Services Tidak Tersedia", Toast.LENGTH_LONG).show();
        }

        Timber.d("onCreate: %s", sTracker.get("referrer"));

     //   getAppComponent();
        Resources res = this.getResources();
        config = res.getConfiguration();
        displayMetrics = res.getDisplayMetrics();
        float widthDpi = displayMetrics.xdpi;
        float heightDpi = displayMetrics.ydpi;
        float widthInches = displayMetrics.widthPixels / widthDpi;
        float heightInches = displayMetrics.heightPixels / heightDpi;
//        double diagonalInches = Math.sqrt(
//                (widthInches * widthInches)
//                        + (heightInches * heightInches));
//        float smallestWidth = Math.min(widthDpi, heightDpi);
//
//
//        switch (displayMetrics.densityDpi) {
//            case DisplayMetrics.DENSITY_LOW:
//
//                config.fontScale = 0.93f;
//
//                break;
//            case DisplayMetrics.DENSITY_MEDIUM:
//
//                if (displayMetrics.widthPixels >= 600) {
//
//                    config.fontScale = 1.3f;
//
//                } else {
//
//                    config.fontScale = 0.9f;
//
//                }
//
//
//                break;
//            case DisplayMetrics.DENSITY_HIGH:
//                if (displayMetrics.widthPixels >= 600) {
//
//                    config.fontScale = 1.7f;
//
//                } else {
//
//                    config.fontScale = 0.6f;
//
//                }
//
//                break;
//            case DisplayMetrics.DENSITY_XHIGH:
//
//                config.fontScale = 0.95f;
//
//
//                break;
//            case DisplayMetrics.DENSITY_XXHIGH:
//
//                config.fontScale = 0.98f;
//
//
//                break;
//            case DisplayMetrics.DENSITY_XXXHIGH:
//
//                config.fontScale = 1.0f;
//
//
//            case DisplayMetrics.DENSITY_TV:
//                if (displayMetrics.widthPixels >= 600) {
//
//                    config.fontScale = 1.4f;
//
//                } else {
//                    config.fontScale = 1.0f;
//
//                }
//
//                break;
//
//            default:
//                if (displayMetrics.widthPixels >= 600) {
//
//                    config.fontScale = 17.5f;
//
//                } else {
//
//                    config.fontScale = 1.0f;
//                }
//
//
//
//        }

        config.locale = new Locale("in", "ID");

        config.setTo(getResources().getConfiguration());
        onConfigurationChanged(config);

    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onActivityCreated(@NotNull Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(@NotNull Activity activity) {
        Timber.d("onActivityStarted: ");
    }

    @Override
    public void onActivityResumed(@NotNull Activity activity) {
        isAppBackground = false;
        Timber.d("onActivityResumed: ");
    }

    @Override
    public void onActivityPaused(@NotNull Activity activity) {
        isAppBackground = true;
        Timber.d("onActivityPaused: ");
    }

    @Override
    public void onActivityStopped(@NotNull Activity activity) {
        Timber.d("onActivityStopped: ");
    }

    @Override
    public void onActivitySaveInstanceState(@NotNull Activity activity, @NotNull Bundle
            outState) {
        Timber.d("onActivitySaveInstanceState: ");
    }

    @Override
    public void onActivityDestroyed(@NotNull Activity activity) {
        Intent backgroundService = new Intent(getApplicationContext(), ScreenOnOffBackgroundService.class);
        startService(backgroundService);
    }

    /**
     * Gets the default {@link Tracker} for this {@link SBFApplication}.
     *
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker(R.xml.global_tracker);
            sTracker.setClientId(PreferenceClass.getId());
            sTracker.enableExceptionReporting(true);
            sTracker.enableAdvertisingIdCollection(true);
            sTracker.enableAutoActivityTracking(true);
            sTracker.enableAdvertisingIdCollection(true);
            sTracker.setAppVersion(Info.getPackageInfo(this).versionName);
        }

        return sTracker;
    }

    public static void sendToAnalylic(String itemCategory, String itemName, String
            action, String tag) {
        sTracker.setScreenName(tag);

        if (PreferenceClass.getUser() != null) {
            sTracker.set("outlet", PreferenceClass.getUser().getId_outlet());
            sTracker.setClientId(PreferenceClass.getUser().getId_outlet());
        } else {
            sTracker.setClientId(PreferenceClass.getId());
        }

        sTracker.send(new HitBuilders.EventBuilder()
                .setLabel(itemName)
                .setCategory(itemCategory)
                .setAction(action)
                .build());
    }


    public static void sendToAnalylic(String itemCategory, String itemName, Product
            product, String action, String tag) {
        sTracker.setScreenName(tag);

        if (PreferenceClass.getUser() != null) {
            sTracker.set("outlet", PreferenceClass.getUser().getId_outlet());
            sTracker.setClientId(PreferenceClass.getUser().getId_outlet());
        } else {
            sTracker.setClientId(PreferenceClass.getId());
        }

        sTracker.send(new HitBuilders.EventBuilder()
                .setLabel(itemName)
                .addProduct(product)
                .setCategory(itemCategory)
                .setAction(action)
                .build());
    }

    /**
     * Release memory when the UI becomes hidden or when system resources become low.
     *
     * @param level the memory-related event that was raised.
     */
    @Override
    public void onTrimMemory(int level) {

        // Determine which lifecycle or system event was raised.
        switch (level) {

            case ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN:
                Timber.d("onTrimMemory: TRIM_MEMORY_UI_HIDDEN");
                /*
                   Release any UI objects that currently hold memory.

                   The user interface has moved to the background.
                */
                break;
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE:
                Timber.d("onTrimMemory: TRIM_MEMORY_RUNNING_MODERATE");
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW:
                Timber.d("onTrimMemory: TRIM_MEMORY_RUNNING_LOW");
                Runtime.getRuntime().gc();
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL:
                Timber.d("onTrimMemory: TRIM_MEMORY_RUNNING_CRITICAL");
                Runtime.getRuntime().gc();
                /*
                   Release any memory that your app doesn't need to run.

                   The device is running low on memory while the app is running.
                   The event raised indicates the severity of the memory-related event.
                   If the event is TRIM_MEMORY_RUNNING_CRITICAL, then the system will
                   begin killing background processes.
                */

                break;
            case ComponentCallbacks2.TRIM_MEMORY_BACKGROUND:
                Timber.d("onTrimMemory: TRIM_MEMORY_BACKGROUND");
            case ComponentCallbacks2.TRIM_MEMORY_MODERATE:
                Timber.d("onTrimMemory: TRIM_MEMORY_MODERATE");
            case ComponentCallbacks2.TRIM_MEMORY_COMPLETE:
                Timber.d("onTrimMemory: TRIM_MEMORY_COMPLETE");

                /*
                   Release as much memory as the process can.

                   The app is on the LRU list and the system is running low on memory.
                   The event raised indicates where the app sits within the LRU list.
                   If the event is TRIM_MEMORY_COMPLETE, the process will be one of
                   the first to be terminated.
                */

                break;

            default:
                Timber.d("onTrimMemory: default");
                /*
                  Release any non-critical data structures.

                  The app received an unrecognized memory level value
                  from the system. Treat this as a generic low-memory message.
                */
                break;
        }
        super.onTrimMemory(level);
    }

    public static SBFApplication getInstance() {
        return instance;
    }

    public static void sendEvent(@NonNull String
                                         eventName, @NonNull HashMap<String, String> map) {
        Bundle bundle = new Bundle();

        log(map.toString());

        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            bundle.putString(pair.getKey().toString(), pair.getValue() == null ? null : pair.getValue().toString());

            it.remove();
        }

        firebaseAnalytics.logEvent(eventName, bundle);
    }

    public static void sendEvent(@NonNull String eventName, Bundle bundle) {
        firebaseAnalytics.logEvent(eventName, bundle);
    }

    public static void log(String content) {
        Timber.i(content);
    }

    public static void log(String tag, String content) {
        Timber.tag(tag).i(content);
    }

    static AppComponent appComponent;

    @NotNull
    @Override
    public AppComponent getAppComponent() {
        if (appComponent == null) {
            setAppComponent(DaggerAppComponent.factory().create(getApplicationContext(), this));
        }
        return appComponent;
    }

    @Override
    public void setAppComponent(@NotNull AppComponent appComponent) {
        SBFApplication.appComponent = appComponent;
    }

    static UserComponent userComponent;

    @Nullable
    @Override
    public UserComponent getUserComponent() {
        return userComponent;
    }

    public static void initUserComponent(String token) {
        userComponent = appComponent.userComponentFactory().create(token, BuildConfig.VERSION_NAME);
    }

    public static void clearUserComponent() {
        userComponent = null;
    }
}
package com.bm.main.scm

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks2
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.bm.main.fpl.constants.Info
import com.bm.main.fpl.utils.PreferenceClass
import com.bm.main.fpl.utils.RequestUtils
import com.bm.main.scm.rabbit.RabbitMqThread
import com.bm.main.single.ftl.utils.MemoryStore
import com.crashlytics.android.Crashlytics
import com.google.android.gms.analytics.GoogleAnalytics
import com.google.android.gms.analytics.HitBuilders.EventBuilder
import com.google.android.gms.analytics.Tracker
import com.google.android.gms.analytics.ecommerce.Product
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.security.ProviderInstaller
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.HiltAndroidApp
import io.fabric.sdk.android.Fabric
import org.jetbrains.annotations.NotNull
import timber.log.Timber
import timber.log.Timber.DebugTree
import java.util.*

@HiltAndroidApp
class SBFApplication : MultiDexApplication(), Application.ActivityLifecycleCallbacks {
    companion object{
        lateinit var _instance:SBFApplication
        @JvmStatic
        lateinit var displayMetrics: DisplayMetrics
        @JvmStatic
        lateinit var config: Configuration
        @JvmStatic
        lateinit var firebaseAnalytics: FirebaseAnalytics
        @JvmStatic
        lateinit var sAnalytics: GoogleAnalytics
        @JvmStatic
        var sTracker: Tracker? = null

        @JvmStatic
        fun getInstance(): SBFApplication {
            return _instance
        }

        @JvmStatic
        fun sendToAnalylic(itemCategory: String?, itemName: String?, action: String?, tag: String?) {
            sTracker!!.setScreenName(tag)
            if (PreferenceClass.getUser() != null) {
                sTracker!!.set("outlet", PreferenceClass.getUser().id_outlet)
                sTracker!!.setClientId(PreferenceClass.getUser().id_outlet)
            } else {
                sTracker!!.setClientId(PreferenceClass.getId())
            }
            sTracker!!.send(
                EventBuilder()
                    .setLabel(itemName)
                    .setCategory(itemCategory)
                    .setAction(action)
                    .build()
            )
        }

        @JvmStatic
        fun sendToAnalylic(
            itemCategory: String?,
            itemName: String?,
            product: Product?,
            action: String?,
            tag: String?
        ) {
            sTracker!!.setScreenName(tag)
            if (PreferenceClass.getUser() != null) {
                sTracker!!.set("outlet", PreferenceClass.getUser().id_outlet)
                sTracker!!.setClientId(PreferenceClass.getUser().id_outlet)
            } else {
                sTracker!!.setClientId(PreferenceClass.getId())
            }
            sTracker!!.send(
                EventBuilder()
                    .setLabel(itemName)
                    .addProduct(product)
                    .setCategory(itemCategory)
                    .setAction(action)
                    .build()
            )
        }

        @JvmStatic
        fun sendEvent(@NonNull eventName: String?, @NonNull map: HashMap<String?, String?>) {
            val bundle = Bundle()
            log(map.toString())
            val it: MutableIterator<*> = map.entries.iterator()
            while (it.hasNext()) {
                val pair = it.next() as Map.Entry<*, *>
                bundle.putString(
                    pair.key.toString(),
                    if (pair.value == null) null else pair.value.toString()
                )
                it.remove()
            }
            firebaseAnalytics.logEvent(eventName!!, bundle)
        }

        @JvmStatic
        fun sendEvent(@NonNull eventName: String?, bundle: Bundle?) {
            firebaseAnalytics.logEvent(eventName!!, bundle)
        }

        @JvmStatic
        fun log(content: String?) {
            Timber.i(content)
        }

        @JvmStatic
        fun log(tag: String?, content: String?) {
            Timber.tag(tag).i(content)
        }
    }

    var isAppBackground = false
    var rabbitThread: RabbitMqThread? = null

    override fun onCreate() {
        super.onCreate()
        //        Timber.plant(new Timber.DebugTree());
        _instance = this
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        //        AndroidThreeTen.init(this);
        PreferenceClass.initialize()
        MemoryStore.initialize()
        RequestUtils.initialize()
        sAnalytics = GoogleAnalytics.getInstance(this)
        sAnalytics.setLocalDispatchPeriod(1800)
        Fabric.with(this, Crashlytics())
        FirebaseApp.initializeApp(this)
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        sTracker = getDefaultTracker()
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker(R.xml.global_tracker)
            sTracker!!.enableExceptionReporting(true)
            sTracker!!.enableAdvertisingIdCollection(true)
            sTracker!!.enableAutoActivityTracking(true)
            sTracker!!.setAppVersion(Info.getPackageInfo(this)!!.versionName)
            // sTracker.setReferrer("gclid=gclidValue");
        }
        registerActivityLifecycleCallbacks(this)
        try {
            ProviderInstaller.installIfNeeded(this)
        } catch (e: GooglePlayServicesRepairableException) {
            Timber.e(e)
            Toast.makeText(this, "Layanan Google Play tidak dapat diperbaiki", Toast.LENGTH_LONG)
                .show()
        } catch (e: GooglePlayServicesNotAvailableException) {
            Timber.e(e)
            Toast.makeText(this, "Google Play Services Tidak Tersedia", Toast.LENGTH_LONG).show()
        }
        Timber.d("onCreate: %s", sTracker!!.get("referrer"))
        val res: Resources = this.resources
        config = res.configuration
        displayMetrics = res.displayMetrics
        val widthDpi = displayMetrics!!.xdpi
        val heightDpi = displayMetrics!!.ydpi
        val widthInches = displayMetrics!!.widthPixels / widthDpi
        val heightInches = displayMetrics!!.heightPixels / heightDpi
        config.locale = Locale("in", "ID")
        config.setTo(resources.configuration)
        onConfigurationChanged(config)
    }

    protected override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onActivityCreated(@NotNull activity: Activity?, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(@NotNull activity: Activity?) {
        Timber.d("onActivityStarted: ")
    }

    override fun onActivityResumed(@NotNull activity: Activity?) {
        isAppBackground = false
        Timber.d("onActivityResumed: ")
    }

    override fun onActivityPaused(@NotNull activity: Activity?) {
        isAppBackground = true
        Timber.d("onActivityPaused: ")
    }

    override fun onActivityStopped(@NotNull activity: Activity?) {
        Timber.d("onActivityStopped: ")
    }

    override fun onActivitySaveInstanceState(
        @NotNull activity: Activity?,
        @NotNull outState: Bundle?
    ) {
        Timber.d("onActivitySaveInstanceState: ")
    }

    override fun onActivityDestroyed(@NotNull activity: Activity?) {
//        Intent backgroundService = new Intent(getApplicationContext(), ScreenOnOffBackgroundService.class);
//        startService(backgroundService);
    }

    /**
     * Gets the default [Tracker] for this [SBFApplication].
     *
     * @return tracker
     */
    @Synchronized
    fun getDefaultTracker(): Tracker? {
        // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
        if (sTracker == null) {
            sTracker = sAnalytics.newTracker(R.xml.global_tracker)
            sTracker!!.setClientId(PreferenceClass.getId())
            sTracker!!.enableExceptionReporting(true)
            sTracker!!.enableAdvertisingIdCollection(true)
            sTracker!!.enableAutoActivityTracking(true)
            sTracker!!.enableAdvertisingIdCollection(true)
            sTracker!!.setAppVersion(Info.getPackageInfo(this)!!.versionName)
        }
        return sTracker
    }

    /**
     * Release memory when the UI becomes hidden or when system resources become low.
     *
     * @param level the memory-related event that was raised.
     */
    override fun onTrimMemory(level: Int) {

        // Determine which lifecycle or system event was raised.
        when (level) {
            ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN -> Timber.d("onTrimMemory: TRIM_MEMORY_UI_HIDDEN")
            ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE -> {
                Timber.d("onTrimMemory: TRIM_MEMORY_RUNNING_MODERATE")
                Timber.d("onTrimMemory: TRIM_MEMORY_RUNNING_LOW")
                Runtime.getRuntime().gc()
                Timber.d("onTrimMemory: TRIM_MEMORY_RUNNING_CRITICAL")
                Runtime.getRuntime().gc()
            }
            ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW -> {
                Timber.d("onTrimMemory: TRIM_MEMORY_RUNNING_LOW")
                Runtime.getRuntime().gc()
                Timber.d("onTrimMemory: TRIM_MEMORY_RUNNING_CRITICAL")
                Runtime.getRuntime().gc()
            }
            ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL -> {
                Timber.d("onTrimMemory: TRIM_MEMORY_RUNNING_CRITICAL")
                Runtime.getRuntime().gc()
            }
            ComponentCallbacks2.TRIM_MEMORY_BACKGROUND -> {
                Timber.d("onTrimMemory: TRIM_MEMORY_BACKGROUND")
                Timber.d("onTrimMemory: TRIM_MEMORY_MODERATE")
                Timber.d("onTrimMemory: TRIM_MEMORY_COMPLETE")
            }
            ComponentCallbacks2.TRIM_MEMORY_MODERATE -> {
                Timber.d("onTrimMemory: TRIM_MEMORY_MODERATE")
                Timber.d("onTrimMemory: TRIM_MEMORY_COMPLETE")
            }
            ComponentCallbacks2.TRIM_MEMORY_COMPLETE -> Timber.d("onTrimMemory: TRIM_MEMORY_COMPLETE")
            else -> Timber.d("onTrimMemory: default")
        }
        super.onTrimMemory(level)
    }
}
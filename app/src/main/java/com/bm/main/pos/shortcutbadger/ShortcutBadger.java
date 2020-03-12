package com.bm.main.pos.shortcutbadger;

import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;

import com.bm.main.pos.shortcutbadger.impl.AdwHomeBadger;
import com.bm.main.pos.shortcutbadger.impl.ApexHomeBadger;
import com.bm.main.pos.shortcutbadger.impl.AsusHomeBadger;
import com.bm.main.pos.shortcutbadger.impl.DefaultBadger;
import com.bm.main.pos.shortcutbadger.impl.EverythingMeHomeBadger;
import com.bm.main.pos.shortcutbadger.impl.HuaweiHomeBadger;
import com.bm.main.pos.shortcutbadger.impl.LGHomeBadger;
import com.bm.main.pos.shortcutbadger.impl.NewHtcHomeBadger;
import com.bm.main.pos.shortcutbadger.impl.NovaHomeBadger;
import com.bm.main.pos.shortcutbadger.impl.OPPOHomeBader;
import com.bm.main.pos.shortcutbadger.impl.SamsungHomeBadger;
import com.bm.main.pos.shortcutbadger.impl.SonyHomeBadger;
import com.bm.main.pos.shortcutbadger.impl.VivoHomeBadger;
import com.bm.main.pos.shortcutbadger.impl.XiaomiHomeBadger;
import com.bm.main.pos.shortcutbadger.impl.ZTEHomeBadger;
import com.bm.main.pos.shortcutbadger.impl.ZukHomeBadger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;


/**
 * @author Leo Lin
 */
public final class ShortcutBadger {
@NonNull
static  String TAG=ShortcutBadger.class.getSimpleName();
   // private static final String TAG = "ShortcutBadger";
    private static final int SUPPORTED_CHECK_ATTEMPTS = 3;

    private static final List<Class<? extends Badger>> BADGERS = new LinkedList<Class<? extends Badger>>();

    private volatile static Boolean sIsBadgeCounterSupported;
    private final static Object sCounterSupportedLock = new Object();

    static {
        BADGERS.add(AdwHomeBadger.class);
        BADGERS.add(ApexHomeBadger.class);
        BADGERS.add(DefaultBadger.class);
        BADGERS.add(NewHtcHomeBadger.class);
        BADGERS.add(NovaHomeBadger.class);
        BADGERS.add(SonyHomeBadger.class);
        BADGERS.add(AsusHomeBadger.class);
        BADGERS.add(HuaweiHomeBadger.class);
        BADGERS.add(LGHomeBadger.class);
        BADGERS.add(OPPOHomeBader.class);
        BADGERS.add(SamsungHomeBadger.class);
        BADGERS.add(ZukHomeBadger.class);
        BADGERS.add(VivoHomeBadger.class);
        BADGERS.add(XiaomiHomeBadger.class);
        BADGERS.add(ZTEHomeBadger.class);
        BADGERS.add(EverythingMeHomeBadger.class);
    }

    @Nullable
    private static Badger sShortcutBadger;
    @Nullable
    private static ComponentName sComponentName;

    /**
     * Tries to update the notification count
     *
     * @param context    Caller context
     * @param badgeCount Desired badge count
     * @return true in case of success, false otherwise
     */
    public static boolean applyCount(@NonNull Context context, int badgeCount) {
        try {
            applyCountOrThrow(context, badgeCount);
            return true;
        } catch (ShortcutBadgeException e) {
            if (Log.isLoggable(TAG, Log.DEBUG)) {
                Log.d(TAG, "Unable to execute badge", e);
            }
            return false;
        }
    }

    /**
     * Tries to update the notification count, throw da {@link ShortcutBadgeException} if it fails
     *
     * @param context    Caller context
     * @param badgeCount Desired badge count
     */
    public static void applyCountOrThrow(@NonNull Context context, int badgeCount) throws ShortcutBadgeException {
        if (sShortcutBadger == null) {
            boolean launcherReady = initBadger(context);

            if (!launcherReady)
                throw new ShortcutBadgeException("No default launcher available");
        }

        try {
            sShortcutBadger.executeBadge(context, sComponentName, badgeCount);
        } catch (Exception e) {
            throw new ShortcutBadgeException("Unable to execute badge", e);
        }
    }

    /**
     * Tries to remove the notification count
     *
     * @param context Caller context
     * @return true in case of success, false otherwise
     */
    public static boolean removeCount(@NonNull Context context) {
        return applyCount(context, 0);
    }

    /**
     * Tries to remove the notification count, throw da {@link ShortcutBadgeException} if it fails
     *
     * @param context Caller context
     */
    public static void removeCountOrThrow(@NonNull Context context) throws ShortcutBadgeException {
        applyCountOrThrow(context, 0);
    }

    /**
     * Whether this platform launcher supports shortcut badges. Doing this check causes the side
     * effect of resetting the counter if it's supported, so this method should be followed by
     * da call that actually sets the counter to the desired value, if the counter is supported.
     */
    public static boolean isBadgeCounterSupported(@NonNull Context context) {
        // Checking outside synchronized block to avoid synchronization in the common case (flag
        // already set), and improve perf.
        if (sIsBadgeCounterSupported == null) {
            synchronized (sCounterSupportedLock) {
                // Checking again inside synch block to avoid setting the flag twice.
                if (sIsBadgeCounterSupported == null) {
                    String lastErrorMessage = null;
                    for (int i = 0; i < SUPPORTED_CHECK_ATTEMPTS; i++) {
                        try {
                            Log.i(TAG, "Checking if platform supports badge counters, attempt "
                                    + String.format("%d/%d.", i + 1, SUPPORTED_CHECK_ATTEMPTS));
                            if (initBadger(context)) {
                                sShortcutBadger.executeBadge(context, sComponentName, 0);
                                sIsBadgeCounterSupported = true;
                                Log.i(TAG, "Badge counter is supported in this platform.");
                                break;
                            } else {
                                lastErrorMessage = "Failed to initialize the badge counter.";
                            }
                        } catch (Exception e) {
                            // Keep retrying as long as we can. No need to dump the stack trace here
                            // because this error will be the norm, not exception, for unsupported
                            // platforms. So we just save the last error message to display later.
                            lastErrorMessage = e.getMessage();
                        }
                    }

                    if (sIsBadgeCounterSupported == null) {
                        Log.w(TAG, "Badge counter seems not supported for this platform: "
                                + lastErrorMessage);
                        sIsBadgeCounterSupported = false;
                    }
                }
            }
        }
        return sIsBadgeCounterSupported;
    }

    /**
     * @param context      Caller context
     * @param notification
     * @param badgeCount
     */
    public static void applyNotification(Context context, @NonNull Notification notification, int badgeCount) {
        if (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
            try {
                Field field = notification.getClass().getDeclaredField("extraNotification");
                Object extraNotification = field.get(notification);
                Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", int.class);
                method.invoke(extraNotification, badgeCount);
            } catch (Exception e) {
                if (Log.isLoggable(TAG, Log.DEBUG)) {
                    Log.d(TAG, "Unable to execute badge", e);
                }
            }
        }
    }

    // Initialize Badger if da launcher is availalble (eg. set as default on the device)
    // Returns true if da launcher is available, in this case, the Badger will be set and sShortcutBadger will be non null.
    private static boolean initBadger(@NonNull Context context) {
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        if (launchIntent == null) {
            Log.e(TAG, "Unable to find launch intent for package " + context.getPackageName());
            return false;
        }

        sComponentName = launchIntent.getComponent();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfos = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        for (ResolveInfo resolveInfo : resolveInfos) {
            String currentHomePackage = resolveInfo.activityInfo.packageName;

            for (Class<? extends Badger> badger : BADGERS) {
                Badger shortcutBadger = null;
                try {
                    shortcutBadger = badger.newInstance();
                } catch (Exception ignored) {
                }
                if (shortcutBadger != null && shortcutBadger.getSupportLaunchers().contains(currentHomePackage)) {
                    sShortcutBadger = shortcutBadger;
                    break;
                }
            }
            if (sShortcutBadger != null) {
                break;
            }
        }
        Log.d(TAG, "initBadger: "+Build.MANUFACTURER);
        //if (sShortcutBadger == null) {
            if (Build.MANUFACTURER.equalsIgnoreCase("ZUK")) {
                sShortcutBadger = new ZukHomeBadger();
            }else if (Build.MANUFACTURER.equalsIgnoreCase("OPPO")) {
                sShortcutBadger = new OPPOHomeBader();
            }else if (Build.MANUFACTURER.equals("vivo")) {
                Log.d(TAG, "INI VIVO: ");
                sShortcutBadger = new VivoHomeBadger();
            }else if (Build.MANUFACTURER.equalsIgnoreCase("ZTE")) {
                sShortcutBadger = new ZTEHomeBadger();
            }else if (Build.MANUFACTURER.equalsIgnoreCase("LGE")) {
                sShortcutBadger = new LGHomeBadger();
            }else {
                Log.d(TAG, "INI DEFAULT: ");
                sShortcutBadger = new DefaultBadger();
            }
      //  }

        return true;
    }

    // Avoid anybody to instantiate this class
    private ShortcutBadger() {

    }
}

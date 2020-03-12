package com.bm.main.pos.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;

import com.bm.main.pos.shortcutbadger.Badger;
import com.bm.main.pos.shortcutbadger.ShortcutBadgeException;
import com.bm.main.pos.shortcutbadger.util.BroadcastHelper;

import java.util.Arrays;
import java.util.List;

/**
 * @author leolin
 */
public class VivoHomeBadger implements Badger {
    private static final String INTENT_ACTION = "android.intent.action.BADGE_COUNT_UPDATE";
    private static final String INTENT_EXTRA_BADGE_COUNT = "badge_count";
    private static final String INTENT_EXTRA_PACKAGENAME = "badge_count_package_name";
    private static final String INTENT_EXTRA_ACTIVITY_NAME = "badge_count_class_name";
    @Override
    public void executeBadge(@NonNull Context context, @NonNull ComponentName componentName, int badgeCount) throws ShortcutBadgeException {
//        Intent intent = new Intent("launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM");
//        intent.putExtra("packageName", context.getPackageName());
//        intent.putExtra("className", componentName.getClassName());
//        intent.putExtra("notificationNum", badgeCount);
//        context.sendBroadcast(intent);
        Intent intent = new Intent(INTENT_ACTION);
        intent.putExtra(INTENT_EXTRA_BADGE_COUNT, badgeCount);
        intent.putExtra(INTENT_EXTRA_PACKAGENAME, componentName.getPackageName());
        intent.putExtra(INTENT_EXTRA_ACTIVITY_NAME, componentName.getClassName());
        if(BroadcastHelper.canResolveBroadcast(context, intent)) {
            context.sendBroadcast(intent);
        } else {
            throw new ShortcutBadgeException("unable to resolve intent: " + intent.toString());
        }
    }


    @Override
    public List<String> getSupportLaunchers() {
        return Arrays.asList("com.vivo.launcher","com.vivo.launcher.scene.comm","com.bbk.launcher2","com.bbk.launcher2.Launcher");
    }
}

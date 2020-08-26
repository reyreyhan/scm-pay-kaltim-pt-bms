package com.bm.main.scm.shortcutbadger.impl;

import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.bm.main.scm.shortcutbadger.Badger;
import com.bm.main.scm.shortcutbadger.ShortcutBadgeException;

import java.util.ArrayList;
import java.util.List;

public class ZTEHomeBadger implements Badger {

    @Override
    public void executeBadge(@NonNull Context context, @NonNull ComponentName componentName, int badgeCount)
            throws ShortcutBadgeException {
        Bundle extra = new Bundle();
        extra.putInt("app_badge_count", badgeCount);
        extra.putString("app_badge_component_name", componentName.flattenToString());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            context.getContentResolver().call(
                    Uri.parse("content://com.android.launcher3.cornermark.unreadbadge"),
                    "setAppUnreadCount", null, extra);
        }
    }

    @NonNull
    @Override
    public List<String> getSupportLaunchers() {
        return new ArrayList<String>(0);
    }
} 

package com.bm.main.pos.shortcutbadger.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import androidx.annotation.NonNull;

import java.util.List;

/**
 * Created by mahijazi on 17/05/16.
 */
public class BroadcastHelper {
	public static boolean canResolveBroadcast(@NonNull Context context, Intent intent) {
		PackageManager packageManager = context.getPackageManager();
		List<ResolveInfo> receivers = packageManager.queryBroadcastReceivers(intent, 0);
		return receivers != null && receivers.size() > 0;
	}
}

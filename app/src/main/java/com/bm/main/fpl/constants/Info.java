package com.bm.main.fpl.constants;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;

/**
 * Created by Sarif Hidayat on 13/05/2017.
 */

public class Info {


    @Nullable
    public static PackageInfo getPackageInfo(@NonNull Context context) {
        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.e("yourTagHere", e.getMessage());
        }
        return pi;
    }
}

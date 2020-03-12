package com.bm.main.fpl.templates.badge;

import android.content.Context;
import androidx.annotation.NonNull;

/**
 * @author chqiu
 *         Email:qstumn@163.com
 */

public class DisplayUtil {
    public static int dp2px(@NonNull Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2dp(@NonNull Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}

package com.bm.main.fpl.listener;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.MenuActionCode;
import com.bm.main.fpl.utils.DetectConnection;
import com.bm.main.fpl.utils.Device;
import com.bm.main.fpl.utils.PreferenceClass;

public class ShowModulePinjamDanaOnClickListener implements View.OnClickListener {
    private AppCompatActivity context;
    private int actionCode;
    @NonNull
    Handler handler = new Handler(Looper.getMainLooper());

    public ShowModulePinjamDanaOnClickListener(AppCompatActivity context, int actionCode) {
        this.context = context;
        this.actionCode = actionCode;

    }

    @Override
    public void onClick(View view) {
        if (actionCode == MenuActionCode.DOKU) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (!DetectConnection.isOnline(context)) {
                        ((BaseActivity) context).new_popup_alert_failure(context, "Cek Koneksi Internet Anda");
                    } else {
                        String apps = "com.android.chrome";
                        boolean installed = Device.checkAppInstall(context, apps);
                        if (installed) {
                            ((BaseActivity) context).logEventFireBase("Home", "Pinjam Dana", EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, context.getClass().getSimpleName());
                            CustomTabsIntent customTabsIntent = ((BaseActivity) context).buildCustomTabsIntent();

                            customTabsIntent.launchUrl(context, Uri.parse(PreferenceClass.getUser().getPinjaman_dana()));
                        } else {
                            Toast.makeText(context, "Aplikasi Google Chrome tidak di temukan, Silahkan Intall Google Chrome terlebih dahulu", Toast.LENGTH_LONG).show();
                            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + apps));
                            context.startActivity(webIntent);
                        }
                    }
                }
            });
        } else if (actionCode == MenuActionCode.TUNAIKU) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (!DetectConnection.isOnline(context)) {
                        ((BaseActivity) context).new_popup_alert_failure(context, "Cek Koneksi Internet Anda");
                    } else {
                        String apps = "com.android.chrome";
                        boolean installed = Device.checkAppInstall(context, apps);
                        if (installed) {
                            ((BaseActivity) context).logEventFireBase("Home", "TunaiKu", EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, context.getClass().getSimpleName());
                            CustomTabsIntent customTabsIntent = ((BaseActivity) context).buildCustomTabsIntent();

                            customTabsIntent.launchUrl(context, Uri.parse(PreferenceClass.getUser().getTunaiku()));
                        } else {
                            Toast.makeText(context, "Aplikasi Google Chrome tidak di temukan, Silahkan Intall Google Chrome terlebih dahulu", Toast.LENGTH_LONG).show();
                            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + apps));
                            context.startActivity(webIntent);
                        }
                    }
                }
            });
        }
    }
}

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

import com.bm.main.fpl.activities.AsuransiActivity;
import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.activities.BpjsActivity;
import com.bm.main.fpl.activities.CicilanActivity;
import com.bm.main.fpl.activities.GameActivity;
import com.bm.main.fpl.activities.GasActivity;
import com.bm.main.fpl.activities.HomeActivity;
import com.bm.main.fpl.activities.IndiHomeActivity;
import com.bm.main.fpl.activities.KartuKreditActivity;
import com.bm.main.fpl.activities.PajakActivity;
import com.bm.main.fpl.activities.PascaBayarActivity;
import com.bm.main.fpl.activities.PdamActivity;
import com.bm.main.fpl.activities.PerbankanActivity;
import com.bm.main.fpl.activities.PgnActivity;
import com.bm.main.fpl.activities.PinjamDanaActivity;
import com.bm.main.fpl.activities.PlnActivity;
import com.bm.main.fpl.activities.PulsaActivity;
import com.bm.main.fpl.activities.SamsatActivity;
import com.bm.main.fpl.activities.TeleponActivity;
import com.bm.main.fpl.activities.TopUpEmoneyActivity;
import com.bm.main.fpl.activities.ToursTiketingActivity;
import com.bm.main.fpl.activities.TvKabelActivity;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.MenuActionCode;
import com.bm.main.fpl.constants.PreferenceKey;
import com.bm.main.fpl.staggeredgridApp.ItemObjectsMenuBeranda;
import com.bm.main.fpl.utils.DetectConnection;
import com.bm.main.fpl.utils.Device;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.single.ftl.flight.activities.FlightSearchRevActivity;
import com.bm.main.single.ftl.ship.activities.ShipSearchRevActivity;
import com.bm.main.single.ftl.train.activities.TrainSearchRevActivity;

public class ShowModuleOnClickListener implements View.OnClickListener {
    private AppCompatActivity context;
    private int actionCode;
    private ItemObjectsMenuBeranda item;
    @NonNull
    private
    Handler handler = new Handler(Looper.getMainLooper());

    public ShowModuleOnClickListener(AppCompatActivity context, int actionCode, ItemObjectsMenuBeranda item) {
        this(context, actionCode);
        this.item = item;
    }

    public ShowModuleOnClickListener(AppCompatActivity context, int actionCode) {
        this.context = context;
        this.actionCode = actionCode;
    }

    @Override
    public void onClick(View view) {
        switch (actionCode) {
            case MenuActionCode.JUAL_BARANG:
                context.runOnUiThread(() -> {
                    if (!DetectConnection.isOnline(context)) {
                        ((BaseActivity) context).new_popup_alert_failure(context, "Cek Koneksi Internet Anda");
                    } else {
                        String apps = "com.android.chrome";
                        boolean installed = Device.checkAppInstall(view.getContext(), apps);
                        if (installed) {
                            ((BaseActivity) context).logEventFireBase("Home", "Jual Barang", EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, context.getClass().getSimpleName());
                            CustomTabsIntent customTabsIntent = ((BaseActivity) context).buildCustomTabsIntent();

                            customTabsIntent.launchUrl(context, Uri.parse(PreferenceClass.getUser().getBelanja() + PreferenceClass.getAuth()));
                        } else {
                            Toast.makeText(context, "Aplikasi Google Chrome tidak di temukan, Silahkan Intall Google Chrome terlebih dahulu", Toast.LENGTH_LONG).show();
                            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + apps));
                            context.startActivity(webIntent);
                        }
                    }
                });
                break;

            case MenuActionCode.PLN:
                context.runOnUiThread(() -> context.startActivity(new Intent(context, PlnActivity.class)));
                break;

            case MenuActionCode.PULSA:
                context.runOnUiThread(() -> context.startActivity(new Intent(context, PulsaActivity.class)));
                break;

            case MenuActionCode.PDAM:
                handler.post(() -> context.startActivity(new Intent(context, PdamActivity.class)));
                break;

            case MenuActionCode.TIKET_TRAVEL:
                handler.post(() -> context.startActivity(new Intent(context, ToursTiketingActivity.class)));
                break;

            case MenuActionCode.BPJS:
                context.runOnUiThread(() -> context.startActivity(new Intent(context, BpjsActivity.class)));
                break;

            case MenuActionCode.INDIHOME:
                context.runOnUiThread(() -> context.startActivity(new Intent(context, IndiHomeActivity.class)));
                break;

            case MenuActionCode.TELKOM:
                context.runOnUiThread(() -> context.startActivity(new Intent(context, TeleponActivity.class)));
                break;

            case MenuActionCode.PASCABAYAR:
                context.runOnUiThread(() -> context.startActivity(new Intent(context, PascaBayarActivity.class)));
                break;

            case MenuActionCode.TVBERLANGGAN:
                context.runOnUiThread(() -> context.startActivity(new Intent(context, TvKabelActivity.class)));
                break;

            case MenuActionCode.CICILAN:
                context.runOnUiThread(() -> context.startActivity(new Intent(context, CicilanActivity.class)));
                break;

            case MenuActionCode.GAMEONLINE:
                context.runOnUiThread(() -> context.startActivity(new Intent(context, GameActivity.class)));
                break;

            case MenuActionCode.EMONEY:
                context.runOnUiThread(() -> context.startActivity(new Intent(context, TopUpEmoneyActivity.class)));
                break;

            case MenuActionCode.ASURANSI:
                context.runOnUiThread(() -> context.startActivity(new Intent(context, AsuransiActivity.class)));
                break;

            case MenuActionCode.KARTUKREDIT:
                context.runOnUiThread(() -> context.startActivity(new Intent(context, KartuKreditActivity.class)));
                break;

            case MenuActionCode.PERBANKAN:
                context.runOnUiThread(() -> context.startActivity(new Intent(context, PerbankanActivity.class)));
                break;

            case MenuActionCode.GAS:
                context.runOnUiThread(() -> context.startActivity(new Intent(context, GasActivity.class)));
                break;

            case MenuActionCode.PAJAK:
                context.runOnUiThread(() -> context.startActivity(new Intent(context, PajakActivity.class)));
                break;

            case MenuActionCode.PKB:
                context.runOnUiThread(() -> context.startActivity(new Intent(context, SamsatActivity.class)));
                break;

            case MenuActionCode.EXPEDISI:
                context.runOnUiThread(() -> {
                    if (!DetectConnection.isOnline(context)) {
                        ((BaseActivity) context).new_popup_alert_failure(context, "Cek Koneksi Internet Anda");
                    } else {
                        String apps = "com.android.chrome";
                        boolean installed = Device.checkAppInstall(view.getContext(), apps);
                        if (installed) {
                            ((BaseActivity) context).logEventFireBase("Home", "Expedisi", EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, context.getClass().getSimpleName());
                            CustomTabsIntent customTabsIntent = ((BaseActivity) context).buildCustomTabsIntent();

                            customTabsIntent.launchUrl(context, Uri.parse(PreferenceClass.getUser().getLion_parcel() + PreferenceClass.getAuth()));
                        } else {
                            Toast.makeText(context, "Aplikasi Google Chrome tidak di temukan, Silahkan Intall Google Chrome terlebih dahulu", Toast.LENGTH_LONG).show();
                            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + apps));
                            context.startActivity(webIntent);
                        }
                    }
                });
                break;

            case MenuActionCode.PINJAMDANA:
                context.runOnUiThread(() -> context.startActivity(new Intent(context, PinjamDanaActivity.class)));
                break;

            case MenuActionCode.KEAGENAN:
                context.runOnUiThread(() -> {
                    if (!DetectConnection.isOnline(context)) {
                        ((BaseActivity) context).new_popup_alert_failure(context, "Cek Koneksi Internet Anda");
                    } else {
                        String apps = "com.android.chrome";
                        boolean installed = Device.checkAppInstall(view.getContext(), apps);
                        if (installed) {
                            ((BaseActivity) context).logEventFireBase("Home", "Keagenan", EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, context.getClass().getSimpleName());
                            CustomTabsIntent customTabsIntent = ((BaseActivity) context).buildCustomTabsIntent();

                            customTabsIntent.launchUrl(context, Uri.parse(PreferenceClass.getUser().getKeagenan() + PreferenceClass.getAuth()));
                        } else {
                            Toast.makeText(context, "Aplikasi Google Chrome tidak di temukan, Silahkan Intall Google Chrome terlebih dahulu", Toast.LENGTH_LONG).show();
                            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + apps));
                            context.startActivity(webIntent);
                        }
                    }
                });
                break;

            case MenuActionCode.BAYARBARANG:
                context.runOnUiThread(() -> {
                    if (!DetectConnection.isOnline(context)) {
                        ((BaseActivity) context).new_popup_alert_failure(context, "Cek Koneksi Internet Anda");
                    } else {
                        String apps = "com.android.chrome";
                        boolean installed = Device.checkAppInstall(view.getContext(), apps);
                        if (installed) {
                            ((BaseActivity) context).logEventFireBase("Home", "Bayar Barang", EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, context.getClass().getSimpleName());
                            CustomTabsIntent customTabsIntent = ((BaseActivity) context).buildCustomTabsIntent();

                            customTabsIntent.launchUrl(context, Uri.parse(PreferenceClass.getUser().getBayar() + PreferenceClass.getAuth()));
                        } else {
                            Toast.makeText(context, "Aplikasi Google Chrome tidak di temukan, Silahkan Intall Google Chrome terlebih dahulu", Toast.LENGTH_LONG).show();
                            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + apps));
                            context.startActivity(webIntent);
                        }
                    }
                });
                break;

            case MenuActionCode.PESAWAT:
                handler.post(() -> context.startActivity(new Intent(context, FlightSearchRevActivity.class)));
                break;

            case MenuActionCode.KERETA:
                handler.post(() -> context.startActivity(new Intent(context, TrainSearchRevActivity.class)));
                break;

            case MenuActionCode.PELNI:
                handler.post(() -> context.startActivity(new Intent(context, ShipSearchRevActivity.class)));
                break;

            case MenuActionCode.ADDMENU:
                context.runOnUiThread(() -> {
                    if (!DetectConnection.isOnline(context)) {
                        ((BaseActivity) context).new_popup_alert_failure(context, "Cek Koneksi Internet Anda");
                    } else {
                        ((BaseActivity) context).logEventFireBase("Home", "AddMenu", EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, context.getClass().getSimpleName());

                        String apps = "com.android.chrome";
                        if (Device.checkAppInstall(context, apps)) {
                            CustomTabsIntent customTabsIntent = ((BaseActivity) context).buildCustomTabsIntent();

                            if (item != null) {
                                if (Integer.parseInt(PreferenceClass.getString(PreferenceKey.isGangguan, "0")) == 1) {
                                    ((HomeActivity) context).new_popup_alert(context, "Info", "Produk Sedang dalam Maintenance");
                                } else if (Integer.parseInt(PreferenceClass.getString(PreferenceKey.isComingsoon, "0")) == 1) {
                                    ((HomeActivity) context).new_popup_alert(context, "Info", "Akan Segera Hadir");
                                } else if (!item.urlProduk.isEmpty()) {
                                    customTabsIntent.launchUrl(context, Uri.parse(item.urlProduk + (item.isUseIdOutlet ? PreferenceClass.getId() : "")));
                                }
                            } else {
                                ((HomeActivity) context).new_popup_alert(context, "Info", "Produk Sedang dalam Maintenance");
                            }
                        } else {
                            Toast.makeText(context, "Aplikasi Google Chrome tidak di temukan, Silahkan Intall Google Chrome terlebih dahulu", Toast.LENGTH_LONG).show();
                            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + apps));
                            context.startActivity(webIntent);
                        }
                    }
                });
                break;
        }
    }
}
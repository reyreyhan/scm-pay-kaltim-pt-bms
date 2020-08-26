package com.bm.main.fpl.staggeredgridApp;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bm.main.fpl.activities.AkunSayaActivity;
import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.activities.CekSaldoEmoneyActivity;
import com.bm.main.fpl.activities.HomeActivity;
import com.bm.main.fpl.activities.HubungiKamiActivity;
import com.bm.main.fpl.activities.KunciActivity;
import com.bm.main.fpl.activities.LaporanGridActivity;
import com.bm.main.fpl.activities.LinkMarketingActivity;
import com.bm.main.fpl.activities.LoginActivity;
import com.bm.main.fpl.activities.PengaturanActivity;
import com.bm.main.fpl.activities.TransferSaldoActivity;
import com.bm.main.fpl.activities.UbahPinActivity;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.utils.Device;
import com.bm.main.fpl.utils.DialogUtils;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.scm.R;

public class SolventViewHoldersAdmin extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView countryName;
    public ImageView countryPhoto;

    public SolventViewHoldersAdmin(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);

        countryName = itemView.findViewById(R.id.country_name);
        countryPhoto = itemView.findViewById(R.id.country_photo);
    }

    @Override
    public void onClick(@NonNull View view) {
        HomeActivity x = (HomeActivity) view.getContext();
        Intent intent;
        switch (getAdapterPosition()) {
            case 0:
                intent = new Intent(view.getContext(), AkunSayaActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                x.startActivity(intent);
                break;
            case 1:
                intent = new Intent(view.getContext(), LinkMarketingActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                x.startActivity(intent);
                break;

            case 2:
                intent = new Intent(view.getContext(), PengaturanActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                x.startActivity(intent);
                break;
            case 3:
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    x.new_popup_alert(view.getContext(), "Info", "Anda belum bisa menikmati fiture ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                    return;
                } else {
                    String apps = "com.android.chrome";
                    boolean installed = Device.checkAppInstall(view.getContext(), apps);
                    if (installed && view.getContext() instanceof BaseActivity) {
                        BaseActivity activity = (BaseActivity) view.getContext();
                        activity.logEventFireBase("Web Report", "Web Report", EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, "");
                        String url = PreferenceClass.getUser().getWeb_report();
                        activity.buildCustomTabsIntent().launchUrl(view.getContext(), Uri.parse(url));
                    } else {
                        Toast.makeText(view.getContext(), "Aplikasi Google Chrome tidak di temukan, Silahkan Intall Google Chrome terlebih dahulu", Toast.LENGTH_LONG).show();
                        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + apps));
                        view.getContext().startActivity(webIntent);
                    }
                    break;
                }

            case 4:
                intent = new Intent(view.getContext(), UbahPinActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                x.startActivity(intent);
                break;

            case 5:
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    x.new_popup_alert(view.getContext(), "Info", "Anda belum bisa menikmati fiture ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                    return;
                } else {
                    intent = new Intent(view.getContext(), LaporanGridActivity.class);

                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    x.startActivity(intent);
                    break;
                }

            case 6:
                intent = new Intent(view.getContext(), TransferSaldoActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                x.startActivity(intent);
                break;

            case 7:
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    x.new_popup_alert(view.getContext(), "Info", "Anda belum bisa menikmati fiture ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                    return;
                } else {
                    DialogUtils.new_popup_bantuan(x);
                    return;
                }

            case 8:
                intent = new Intent(view.getContext(), CekSaldoEmoneyActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                x.startActivity(intent);
                break;

            case 9:
                intent = new Intent(view.getContext(), HubungiKamiActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                x.startActivity(intent);
                break;

            case 10:
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    x.new_popup_alertDemo(x, "Info", "Anda belum bisa menikmati fitur ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                } else {
                    intent = new Intent(view.getContext(), KunciActivity.class);
                    PreferenceClass.setLocked();
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    x.startActivity(intent);
                    x.finish();
                }
                break;

            case 11:
                x.requestLogout();
                PreferenceClass.setLogOut();

                intent = new Intent(view.getContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                x.startActivity(intent);
                x.finish();
                break;

            default:
                intent = new Intent(view.getContext(), HomeActivity.class);
                x.startActivity(intent);
                break;
        }


        //x.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

}

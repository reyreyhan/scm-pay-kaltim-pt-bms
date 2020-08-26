package com.bm.main.fpl.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.activities.DepositActivity;
import com.bm.main.fpl.activities.HomeActivity;
import com.bm.main.fpl.activities.PrivacyPolicyActivity;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.models.BaseObject;
import com.bm.main.fpl.models.SaldoModel;
import com.bm.main.fpl.staggeredgridApp.ItemObjects;
import com.bm.main.fpl.staggeredgridApp.SolventRecyclerViewAdminAdapter;
import com.bm.main.fpl.utils.FormatString;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;
import com.bm.main.fpl.utils.StringJson;
import com.bm.main.scm.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

public class AdminFragment extends Fragment implements ProgressResponseCallback, TextToSpeech.OnInitListener {
    private static final String TAG = AdminFragment.class.getSimpleName();

    RecyclerView recycler_menu;
    public View rootView;
    Context context;
    TextView textViewPlusSaldo, textViewPlusKomisi;
    @NonNull
    private int[] menuIcons = {
            R.drawable.ic_admin_akun_saya,
            R.drawable.ic_admin_link_marketing,
            R.drawable.ic_admin_pengaturan,

            R.drawable.ic_admin_webreport,
            R.drawable.ic_admin_ubah_pin,
            R.drawable.ic_admin_laporan,

            R.drawable.ic_admin_transfer_saldo,
            R.drawable.ic_admin_komplain,
            R.drawable.ic_admin_cek_nfc,
            R.drawable.ic_admin_hubungi_kami,
            R.drawable.ic_admin_kunci,
            R.drawable.ic_admin_keluar
    };

    @NonNull
    private String[] menuTitle = {
            "Akun Saya",
            "Link Ajak Bisnis",
            "Pengaturan",
            "Member Area",
            "Ubah Pin",
            "Laporan Transaksi",
            "Transfer Saldo",
            "Komplain",
            "Cek Saldo Emoney",
            "Hubungi Kami",
            "Kunci Aplikasi",
            "Keluar"
    };

    BaseObject baseObject;
    public FrameLayout frameDeposit;
    public ImageView img_notification, img_menu;
    public FrameLayout frame_bagde_count_notif;
    public TextView textViewCountNotif;
    public ImageView imageViewRatingOutlet;
    public TextView textViewTypeOutletToolbar;
    public LinearLayout linGradeOutlet;
    TextView textViewPrivacyPolicy;
    private RelativeLayout relNotif;
    public RelativeLayout rel_card;
    private int isTap = 0;

    public AdminFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_admin, container, false);

        context = getActivity();//rootView.getContext();
        ((HomeActivity) context).textToSpeech = new TextToSpeech(getContext(), this);
        ((HomeActivity) context).logEventFireBase("Home", "Admin", EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, TAG);

        rel_card = rootView.findViewById(R.id.rel_card);
        rel_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        isTap = 1;
                        cekSaldo();
                    }
                });
                ((HomeActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        isTap = 1;
                        requestKomisi();
                    }
                });
            }
        });

        Glide.with(this).asBitmap().load(R.drawable.admin_card_batik).diskCacheStrategy(DiskCacheStrategy.NONE).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onLoadFailed(Drawable errorDrawable) {
            }

            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> animation) {
                Drawable drawable = new BitmapDrawable(getResources(), resource);
                rel_card.setBackground(drawable);
            }
        });

        frame_bagde_count_notif = rootView.findViewById(R.id.frame_bagde_count_notif);
        textViewPrivacyPolicy = rootView.findViewById(R.id.textViewPrivacyPolicy);
        textViewPrivacyPolicy.setText(FormatString.htmlString(getString(R.string.syarat_ketentuan_dan_kebijakan_privacy_fastpay)));
        textViewPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PrivacyPolicyActivity.class);
                startActivity(intent);
            }
        });

        textViewCountNotif = rootView.findViewById(R.id.textViewCountNotif);
        imageViewRatingOutlet = rootView.findViewById(R.id.imageViewRatingOutlet);
        textViewTypeOutletToolbar = rootView.findViewById(R.id.textViewTypeOutletToolbar);
        linGradeOutlet = rootView.findViewById(R.id.linGradeOutlet);

        linGradeOutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) context).toggleGradeOutlet(context);
            }
        });

        img_menu = rootView.findViewById(R.id.img_menu);
        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseActivity) context).openTopDialog(true);
            }
        });
        img_notification = rootView.findViewById(R.id.img_notification);
        relNotif = rootView.findViewById(R.id.relNotif);

        relNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) context).toggleNotification();
            }
        });

        ImageView imageViewDeposit = rootView.findViewById(R.id.imageViewDeposit);

        imageViewDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    ((BaseActivity) context).new_popup_alert(view.getContext(), "Info", "Anda belum bisa menikmati layanan ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");

                } else {
                    Intent intent = new Intent(context, DepositActivity.class);
                    startActivity(intent);
                }
            }
        });
        textViewPlusSaldo = rootView.findViewById(R.id.textViewPlusSaldo);
        textViewPlusKomisi = rootView.findViewById(R.id.textViewPlusKomisi);

        recycler_menu = rootView.findViewById(R.id.recycler_admin);
        LinearLayout carouselView = rootView.findViewById(R.id.carouselView);
        float heightDp = (float) (context.getResources().getDisplayMetrics().heightPixels * 2) / 6;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) carouselView.getLayoutParams();

        lp.height = (int) heightDp;
        carouselView.setMinimumHeight(lp.height);
        frameDeposit = rootView.findViewById(R.id.frameDeposit);
        requestKomisi();
        cekSaldo();
        setMenu();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity) context).runOnUiThread(() -> ((HomeActivity) context).openShowCaseAdmin(context));
        getTypeOutletToolbarAdmin();
        if (PreferenceClass.getInt("notifSize", 0) > 0) {
            frame_bagde_count_notif.setVisibility(View.VISIBLE);
        } else {
            frame_bagde_count_notif.setVisibility(View.GONE);
        }
    }

    public void getTypeOutletToolbarAdmin() {
        textViewTypeOutletToolbar.setText(PreferenceClass.getUser().getAlias().toUpperCase());
        switch (PreferenceClass.getUser().getRating()) {
            case "1":
                Glide.with(this).load(R.drawable.ic_grade_satu).into(imageViewRatingOutlet);
                break;
            case "3":
                Glide.with(this).load(R.drawable.ic_grade_tiga).into(imageViewRatingOutlet);
                break;
            case "5":
                Glide.with(this).load(R.drawable.ic_grade_lima).into(imageViewRatingOutlet);
                break;
        }
    }

    private void requestKomisi() {
        StringJson stringJson = new StringJson(getActivity());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestKomisi());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse((AppCompatActivity) context, jsonObject, ActionCode.REQUEST_KOMISI, this);
    }

    private void cekSaldo() {
        StringJson stringJson = new StringJson(getActivity());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestCekSaldo());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse((AppCompatActivity) context, jsonObject, ActionCode.CEK_SALDO, this);
    }

    private void setMenu() {
        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        recycler_menu.setLayoutManager(mLayoutManager);
        List<ItemObjects> menuList = new ArrayList<>();
        for (int i = 0; i < menuTitle.length; i++) {
            ItemObjects itemObjects = new ItemObjects(menuTitle[i], menuIcons[i]);
            menuList.add(itemObjects);
        }
        SolventRecyclerViewAdminAdapter mAdapter = new SolventRecyclerViewAdminAdapter(getActivity(), menuList);
        recycler_menu.setAdapter(mAdapter);
    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        if (actionCode == ActionCode.CEK_SALDO) {
            Timber.d("onSuccess: %s", response.toString());
            SaldoModel saldoModel = ((BaseActivity) context).gson.fromJson(response.toString(), SaldoModel.class);
            if (saldoModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                String saldo = saldoModel.getResponse_value();
                PreferenceClass.putString("saldo", FormatString.CurencyIDR(saldo));
                textViewPlusSaldo.setText("Rp " + PreferenceClass.getString("saldo", "0"));
            }
        } else if (actionCode == ActionCode.REQUEST_KOMISI) {
            Timber.d("onSuccess: %s", response.toString());
            String saldo = "0";
            String arrNominalSpace = "0";
            baseObject = ((BaseActivity) context).gson.fromJson(response.toString(), BaseObject.class);
            if (baseObject.getResponse_code().equals(ResponseCode.SUCCESS)) {
                String keterangan = baseObject.getResponse_desc().toString();
                String[] arrKeterangan = keterangan.split(" ");
                arrNominalSpace = arrKeterangan[5];
                textViewPlusKomisi.setText("Rp " + FormatString.CurencyIDR(arrNominalSpace.replace(",", "")));

                try {
                    saldo = response.getString("response_value");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                PreferenceClass.putString("saldo", FormatString.CurencyIDR(saldo));
                textViewPlusSaldo.setText("Rp " + PreferenceClass.getString("saldo", "0"));
            } else {
                textViewPlusKomisi.setText("Rp 0");
            }

            if (isTap == 1) {
                ((HomeActivity) context).textToSpeech.speak("Saldo Anda sebesar " + saldo + " Rupiah dan Komisi Anda sebesar " + arrNominalSpace.replace(",", "") + " rupiah. Tingkatkan terus transaksi Anda"
                        , TextToSpeech.QUEUE_FLUSH, null);
                if (!((HomeActivity) context).textToSpeech.isSpeaking()) {
                    ((HomeActivity) context).textToSpeech = new TextToSpeech(context, this);
                    System.out.println("tts restarted");
                    ((HomeActivity) context).textToSpeech.speak("Saldo Anda sebesar " + saldo + " Rupiah dan Komisi Anda sebesar " + arrNominalSpace.replace(",", "") + " rupiah. Tingkatkan terus transaksi Anda", TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        }
    }

    @Override
    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {
        textViewPlusKomisi.setText("Rp 0");
    }

    @Override
    public void onUpdate(int actionCode, long bytesRead, long totalSize, boolean done) {
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int ttsLang = ((BaseActivity) context).textToSpeech.setLanguage(new Locale("id", "ID"));
            Timber.d("onInit: %s", ttsLang);
            if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                    || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                Timber.d("The Language is not supported!" + TextToSpeech.LANG_MISSING_DATA + " " + TextToSpeech.LANG_NOT_SUPPORTED);
                ((BaseActivity) context).textToSpeech.setLanguage(Locale.US);
            } else {
                Timber.d("Language Supported.");
                ((BaseActivity) context).textToSpeech.setLanguage(new Locale("id", "ID"));
            }
            Timber.d("Initialization success.");
        } else {
            Toast.makeText(context, "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
        }
    }
}

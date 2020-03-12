package com.bm.main.fpl.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.MenuActionCode;
import com.bm.main.fpl.constants.PreferenceKey;
import com.bm.main.fpl.constants.RConfig;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.fragments.AdminFragment;
import com.bm.main.fpl.fragments.BerandaFragment;
import com.bm.main.fpl.fragments.MemberBaruFragment;
import com.bm.main.fpl.interfaces.JsonObjectResponseCallback;
import com.bm.main.fpl.listener.ShowModuleOnClickListener;
import com.bm.main.fpl.models.AjakBisnisModel;
import com.bm.main.fpl.models.BaseObject;
import com.bm.main.fpl.models.NotificationModel;
import com.bm.main.fpl.models.PanduanAjakBisnisModel;
import com.bm.main.fpl.models.UserModel;
import com.bm.main.fpl.staggeredgridApp.ItemObjectsMenuBeranda;
import com.bm.main.fpl.templates.showcaseview.GuideView;
import com.bm.main.fpl.templates.tooltip.SimpleTooltip;
import com.bm.main.fpl.utils.Device;
import com.bm.main.fpl.utils.DialogUtils;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;
import com.bm.main.fpl.utils.Utils;
import com.bm.main.fpl.webview.SBFWebActivity;
import com.bm.main.pos.R;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONException;
import org.json.JSONObject;

import timber.log.Timber;

public class HomeActivity extends BaseActivity implements JsonObjectResponseCallback {
    private static final String TAG = HomeActivity.class.getSimpleName();
    private AdminFragment adminFragment;
    private BerandaFragment berandaFragment;
    public int selectedTab;
    boolean liveChat, admin, home;
    Intent intent;

    public RelativeLayout bottom_home;
    private LinearLayout linToggleHome;
    private LinearLayout linToggleAjak;
    private LinearLayout linToggleDeposit;
    private TextView textViewAjakBisnis;
    private TextView textViewAdmin;
    private boolean isPaused;
    private Context context;

    private final int KEY_SMS_REQ_CODE = 4382;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        setContentView(R.layout.activity_home);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        intent = this.getIntent();
        context = this;
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setAction(Intent.ACTION_MAIN);

        bottom_home = findViewById(R.id.bottom_home);

        linToggleAjak = findViewById(R.id.linToggleMemberBaru);
        textViewAjakBisnis = findViewById(R.id.textViewAjakBisnis);
        linToggleHome = findViewById(R.id.linNavBeranda);
        LinearLayout linToggleLiveChat = findViewById(R.id.linToggleBantuanLiveChat);
        LinearLayout linToggleAdmin = findViewById(R.id.linToggleAdmin);
        textViewAdmin = findViewById(R.id.textViewAdmin);

        linToggleDeposit = findViewById(R.id.linNavBottomHomeDeposit);

        checkLogin();

        if (savedInstanceState == null) {
            toggleHome(linToggleHome);
        }

        if (intent != null) {
            home = intent.getBooleanExtra("home", false);
            liveChat = intent.getBooleanExtra("live_chat", false);
            admin = intent.getBooleanExtra("admin", false);
            if (liveChat) {
                toggleBantuanLiveChat(linToggleLiveChat);
            } else if (admin) {
                toggleAdmin(linToggleAdmin);
            } else if (home) {
                toggleHome(linToggleHome);
            }
        }

        if (PreferenceClass.getString(RConfig.IS_ToolTipAjakBisnis, "0").equals("1")) {
            if (context instanceof AppCompatActivity && !((AppCompatActivity) context).isFinishing()) {
                showTooltipAjakBisnis();
            }
        }

        requestAjakBisnis();
        requestPanduan();
        getPromoToShow();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tooltipAjakBisnis != null && tooltipAjakBisnis.isShowing())
            tooltipAjakBisnis.dismiss();
    }

    private SimpleTooltip tooltipAjakBisnis;

    private void showTooltipAjakBisnis() {
        LinearLayout viewToolTips = (LinearLayout) View.inflate(this, R.layout.layout_tooltips, null);
        tooltipAjakBisnis = new SimpleTooltip.Builder(this)
                .anchorView(linToggleAjak)
                .contentView(viewToolTips, R.id.textViewContentTips)
                .text(PreferenceClass.getString(RConfig.ContentToolTipAjakBisnis, "0"))
                .gravity(Gravity.TOP)
                .arrowColor(Color.RED)
                .animated(true)
                .onShowListener(tooltip12 -> {
                    Timber.d("onShow: ");
                    Animation startAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blinking_animation);
                    linToggleAjak.setBackgroundColor(ContextCompat.getColor(HomeActivity.this, R.color.md_red_500));
                    linToggleAjak.startAnimation(startAnimation);
                })
                .onDismissListener(tooltip1 -> {
                    Timber.d("onDismiss: ");
                    linToggleAjak.setBackground(ContextCompat.getDrawable(HomeActivity.this, R.drawable.button_background));
                    linToggleAjak.clearAnimation();
                })
                .build();
        tooltipAjakBisnis.show();
    }

    private void getPromoToShow() {
        if (PreferenceClass.getString(RConfig.IS_PopupPromo, "0").equals("1")) {
            if (!PreferenceClass.getString(PreferenceKey.PopupPromoReceiveDate, "").equals(Utils.getDateNow())) {
                Glide.with(this).asBitmap().load(PreferenceClass.getString(RConfig.UrlImgPopupPromo, "")).encodeFormat(Bitmap.CompressFormat.WEBP).encodeQuality(50).diskCacheStrategy(DiskCacheStrategy.NONE).into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> animation) {
                        show_popupPromo(resource);
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                    }
                });
            }
        }
    }

    private void show_popupPromo(Bitmap resource) {
        @SuppressLint("InflateParams") final FrameLayout view = (FrameLayout) View.inflate(this, R.layout.layout_popup_apps, null);
        FrameLayout appCompatButtonClosed = view.findViewById(R.id.frame_closePopup);
        ImageView imageViewPromo = view.findViewById(R.id.imageViewPopUp);
        imageViewPromo.setImageBitmap(resource);
        AppCompatButton appCompatButtonLink = view.findViewById(R.id.appCompatButtonCTAPopup);

        mPromoDialog = new Dialog(this,
                R.style.MaterialDialogSheet);
        mPromoDialog.setContentView(view);
        mPromoDialog.setCancelable(false);
        mPromoDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        mPromoDialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);
        mPromoDialog.show();

        if (mPromoDialog.isShowing()) {
            PreferenceClass.putString(PreferenceKey.PopupPromoReceiveDate, Utils.getDateNow());
            logEventFireBase("Home", "Promo Popup", EventParam.EVENT_ACTION_RECEIVED, EventParam.EVENT_SUCCESS, TAG);
            Timber.d("show_popupPromo: ");
        }

        appCompatButtonLink.setOnClickListener(v -> {
            openPromo();
            if (mPromoDialog.isShowing()) {
                mPromoDialog.dismiss();
            }
        });
        appCompatButtonClosed.setOnClickListener(v -> {
            if (mPromoDialog.isShowing()) {
                logEventFireBase("Home", "Promo Popup", EventParam.EVENT_ACTION_DENIED, EventParam.EVENT_SUCCESS, TAG);
                mPromoDialog.dismiss();
            }
        });
    }

    private void openPromo() {
        String apps = "com.android.chrome";
        boolean installed = Device.checkAppInstall(this, apps);
        if (installed) {
            logEventFireBase("Home", "Promo Popup", EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, TAG);
            String url = PreferenceClass.getString(RConfig.UrlCTAPopupPromo, "https://fastpay.co.id");
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } else {
            Toast.makeText(context, "Aplikasi Google Chrome tidak di temukan, Silahkan Intall Google Chrome terlebih dahulu", Toast.LENGTH_LONG).show();
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + apps));
            startActivity(webIntent);
        }
    }

    private void requestPanduan() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestGetContenPanduanAjakBisnis());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithJSONObjectResponse(this, jsonObject, ActionCode.REQUEST_PANDUAN_AJAK_BISNIS, this);
    }

    private void requestAjakBisnis() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestGetContenAjakBisnis());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithJSONObjectResponse(this, jsonObject, ActionCode.REQUEST_AJAK_BISNIS, this);
    }

    @Override
    protected void onResume() {
        Timber.d("onResume: ");
        if (isPaused) {
            isPaused = false;
        }

        requestNotificationHome();
        super.onResume();
    }

    private void requestNotificationHome() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestListNotifHome());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.LIST_NOTIF_HOME, this);
    }

    public void checkLogin() {
        UserModel userModel = PreferenceClass.getUser();
        if (userModel == null || !PreferenceClass.isLoggedIn()) {
            PreferenceClass.clearToken();
            Intent a = new Intent(HomeActivity.this, LoginActivity.class);
            a.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(a);
            finish();
        } else if (!PreferenceClass.getBoolean("otp_set", false)) {
            Intent a = new Intent(this, KeySmsActivity.class);
            a.putExtra("id", PreferenceClass.getId());
            a.putExtra("pass", PreferenceClass.getPin());
            a.putExtra("desc", "");
//            a.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//            startActivityForResult(a, KEY_SMS_REQ_CODE);
            startActivity(a);
            finish();
        } else if (PreferenceClass.isLoggedIn() && PreferenceClass.isLocked()) {
            Intent a = new Intent(HomeActivity.this, KunciActivity.class);
            startActivity(a);
            finish();
        }

        if (PreferenceClass.getToken().equals("")) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject = new JSONObject(stringJson.requestSignOnTravel(userModel.getId_outlet(), PreferenceClass.getPin(), PreferenceClass.getDeviceToken()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            Timber.d("doLogin travel: %s", jsonObject.toString());
            RequestUtils.transportWithProgressResponse(HomeActivity.this, jsonObject, TravelActionCode.LOGIN, this);
        }
    }

    private void requestLayout() {
        Timber.d("requestLayout: %s", selectedTab);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        switch (selectedTab) {
            case 0:
                textViewAdmin.setSelected(false);
                textViewAjakBisnis.setSelected(false);

                berandaFragment = new BerandaFragment();
                fragmentTransaction.replace(R.id.frame_container, berandaFragment);
                break;
            case 1:
                textViewAdmin.setSelected(false);
                textViewAjakBisnis.setSelected(true);
                MemberBaruFragment memberBaruFragment = new MemberBaruFragment();
                fragmentTransaction.replace(R.id.frame_container, memberBaruFragment);
                break;
            case 2:
                textViewAdmin.setSelected(true);
                textViewAjakBisnis.setSelected(false);
                adminFragment = new AdminFragment();
                fragmentTransaction.replace(R.id.frame_container, adminFragment);
                break;
        }
        fm.executePendingTransactions();
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        Timber.d("onSuccess: %s", response.toString());
        if (actionCode == ActionCode.LIST_NOTIF_HOME) {

            PreferenceClass.putJSONObject("notif_home", response);

            final NotificationModel notificationModel = gson.fromJson(response.toString(), NotificationModel.class);

            if (notificationModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                if (notificationModel.getResponse_value().size() > 0) {
                    PreferenceClass.putInt("notifSize", notificationModel.getResponse_value().size());
                } else {
                    PreferenceClass.putInt("notifSize", 0);
                }
            } else if (notificationModel.getResponse_code().equals("03")) {
                new_popup_alert_session(this, "Informasi", notificationModel.getResponse_desc());
            }
        } else if (actionCode == TravelActionCode.LOGIN) {
            Timber.d("onSuccess: Login Travel: %s", response.toString());
            BaseObject baseObject = gson.fromJson(response.toString(), BaseObject.class);
            if (baseObject.getResponse_code().equals("00")) {
                try {
                    String token = response.getString("response_token");
                    PreferenceClass.setToken(token);
                } catch (JSONException jsone) {
                    jsone.printStackTrace();
                }
            }
        } else if (actionCode == ActionCode.REQUEST_AJAK_BISNIS) {
            AjakBisnisModel ajakBisnisModel = gson.fromJson(response.toString(), AjakBisnisModel.class);
            if (ajakBisnisModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                PreferenceClass.putJSONObject(PreferenceKey.ajakBisnis, response);
            }
        } else if (actionCode == ActionCode.REQUEST_PANDUAN_AJAK_BISNIS) {
            PanduanAjakBisnisModel panduanAjakBisnisModel = gson.fromJson(response.toString(), PanduanAjakBisnisModel.class);
            if (panduanAjakBisnisModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                PreferenceClass.putJSONObject(PreferenceKey.panduanBisnis, response);
            }
        }
    }

    @Override
    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {
    }

    public void JualBarang() {
        Timber.d("JualBarang: %s", PreferenceClass.getUser().getBelanja());
        Intent intent = new Intent(HomeActivity.this, SBFWebActivity.class);
        intent.putExtra("urlValue", PreferenceClass.getUser().getBelanja());
        intent.putExtra("titleValue", "Penjualan Barang");
        intent.setData(Uri.parse(PreferenceClass.getUser().getBelanja() + PreferenceClass.getAuth()));
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (selectedTab != 0) {
            toggleHome(bottom_home);
            getFragmentManager().popBackStack();

        } else {
            if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                requestLogout();
                PreferenceClass.setLogOut();
                intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                finish();
            } else {
                finish();
            }
        }
    }

    @Override
    protected void onPause() {
        Timber.d("onPause: ");
        Thread.currentThread().interrupt();
        isPaused = true;

        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == KEY_SMS_REQ_CODE) {
            if (resultCode == RESULT_OK) {
                PreferenceClass.setUnlock();
            } else {
                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void openShowCaseAdmin(Context context) {
        if (PreferenceClass.getInt("AdminFrag", 0) != 1) {
            showCaseFirst(context, "", "Tap di area ini untuk mengupdate saldo dan komisi outlet Anda", adminFragment.rel_card);
            mGbuilder.setGuideListener(view -> {
                Timber.d("onDismiss: Admin");
                switch (view.getId()) {
                    case R.id.rel_card:
                        mGbuilder
                                .setTitle("")
                                .setContentText("Klik Icon untuk mengambil Tiket Deposit")
                                .setTargetView(adminFragment.frameDeposit)
                                .build();
                        break;
                    case R.id.frameDeposit:
                        PreferenceClass.putInt("AdminFrag", 1);
                        return;
                }
                mGuideView = mGbuilder.build();
                mGuideView.show();

            });
            mGuideView = mGbuilder.build();
            mGuideView.show();
        }
    }

    public void toggleHome(View view) {
        selectedTab = 0;

        linToggleHome.setVisibility(View.GONE);
        linToggleDeposit.setVisibility(View.VISIBLE);
        requestLayout();
    }

    public void toggleDeposit(View view) {
        Timber.d("toggleDeposit: home");
        if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
            new_popup_alertDemo(HomeActivity.this, "Info", "Anda belum bisa menikmati layanan ini.\n" +
                    "Daftar & Aktifasi sekarang juga ID Anda");
        } else {
            selectedTab = 0;
            Intent intent = new Intent(HomeActivity.this, DepositActivity.class);
            startActivity(intent);
        }
    }

    public void toggleMemberBaru(View view) {
        if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
            new_popup_alertDemo(HomeActivity.this, "Info", "Anda belum bisa menikmati layanan ini.\n" +
                    "Daftar & Aktifasi sekarang juga ID Anda");
        } else {
            selectedTab = 1;
            linToggleHome.setVisibility(View.VISIBLE);
            linToggleDeposit.setVisibility(View.GONE);
            requestLayout();
        }
    }

    public void toggleBantuanLiveChat(View view) {
        if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
            new_popup_alertDemo(HomeActivity.this, "Info", "Anda belum bisa menikmati layanan ini.\n" +
                    "Daftar & Aktifasi sekarang juga ID Anda");
        } else {
            DialogUtils.new_popup_bantuan(this);
        }
    }

    public void toggleAdmin(View view) {
        if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
            new_popup_alertDemo(HomeActivity.this, "Info", "Anda belum bisa menikmati layanan ini.\n" +
                    "Daftar & Aktifasi sekarang juga ID Anda");
        } else {
            if (selectedTab != 2) {
                selectedTab = 2;
                linToggleHome.setVisibility(View.VISIBLE);
                linToggleDeposit.setVisibility(View.GONE);
                requestLayout();
            }
        }
    }

    protected void onShowKeyboard(int keyboardHeight) {
        // do things when keyboard is shown
        Timber.d("onShowKeyboard: %s", keyboardHeight);
        bottom_home.setVisibility(View.GONE);
    }

    protected void onHideKeyboard() {
        Timber.d("onHideKeyboard: ");
        // do things when keyboard is hidden
        bottom_home.setVisibility(View.VISIBLE);
    }

    public void toggleGradeOutlet(@NonNull Context context) {
        if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
            new_popup_alertDemo(this, "Info", "Anda belum bisa menikmati layanan ini.\n" +
                    "Daftar & Aktifasi sekarang juga ID Anda");
        } else {
            Timber.d("toggleGradeOutlet: " + PreferenceClass.getUser().getUpgrade() + PreferenceClass.getAuth());
            if (PreferenceClass.getUser().getRating().equals("5")) {
                new_popup_alert(context, "Informasi", "Loket Anda sudah berada pada posisi tertinggi");
            } else {
                String apps = "com.android.chrome";
                boolean installed = Device.checkAppInstall(this, apps);
                if (installed) {
                    CustomTabsIntent customTabsIntent = buildCustomTabsIntent();
                    customTabsIntent.launchUrl(context, Uri.parse(PreferenceClass.getUser().getUpgrade() + PreferenceClass.getAuth()));
                    overridePendingTransition(0, 0);
                } else {
                    Toast.makeText(this, "Aplikasi Google Chrome tidak di temukan, Silahkan Intall Google Chrome terlebih dahulu", Toast.LENGTH_LONG).show();
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + apps));
                    startActivity(webIntent);
                }
            }
        }
    }

    public void toggleNotification() {
        Intent to_notification = new Intent(this, NotificationActivity.class);
        overridePendingTransition(0, 0);
        startActivity(to_notification);
    }

    public void showCaseFirstBeranda(Context context, String title, String description, View viewFirst) {
        mGbuilder = new GuideView.Builder(context)
                .setTitle(title)
                .setBackgroundColor(R.color.black_overlay)
                .setContentText(description)
                .setGravity(GuideView.Gravity.center)
                .setDismissType(GuideView.DismissType.anywhere)
                .setTargetView(viewFirst);
    }

    public void openShowCaseBeranda(Context context) {
        if (PreferenceClass.getInt(TAG, 0) != 1) {
            showCaseFirstBeranda(context, "", "Status Paket Anda", berandaFragment.linGradeOutlet);
            mGbuilder.setGuideListener(view -> {
                switch (view.getId()) {
                    case R.id.linGradeOutlet:
                        mGbuilder
                                .setTitle("")
                                .setBackgroundColor(R.color.black_overlay)
                                .setContentText("Klik Icon untuk melihat promo terbaru dari Sentra Bisnis Fastpay untuk Anda")
                                .setTargetView(berandaFragment.img_notification)
                                .build();
                        break;


                    case R.id.img_notification:
                        PreferenceClass.putInt(TAG, 1);
                        return;
                }
                mGuideView = mGbuilder.build();
                mGuideView.show();

            });
            mGuideView = mGbuilder.build();
            mGuideView.show();
        }
    }

    public void getMenuBeranda(View view, int position, ItemObjectsMenuBeranda item) {
        switch (position) {
            case 0:
                new ShowModuleOnClickListener(this, MenuActionCode.PLN).onClick(view);
                break;
            case 1:
                new ShowModuleOnClickListener(this, MenuActionCode.PULSA).onClick(view);
                break;
            case 2:
                new ShowModuleOnClickListener(this, MenuActionCode.PDAM).onClick(view);
                break;
            case 3:
                new ShowModuleOnClickListener(this, MenuActionCode.BPJS).onClick(view);
                break;
            case 4:
                new ShowModuleOnClickListener(this, MenuActionCode.EMONEY).onClick(view);
                break;
            case 5:
                new ShowModuleOnClickListener(this, MenuActionCode.INDIHOME).onClick(view);
                break;
            case 6:
                new ShowModuleOnClickListener(this, MenuActionCode.PESAWAT).onClick(view);
                break;
            case 7:
                new ShowModuleOnClickListener(this, MenuActionCode.KERETA).onClick(view);
                break;
            case 8:
                new ShowModuleOnClickListener(this, MenuActionCode.TELKOM).onClick(view);
                break;
            case 9:
                new ShowModuleOnClickListener(this, MenuActionCode.CICILAN).onClick(view);
                break;
            case 10:
                new ShowModuleOnClickListener(this, MenuActionCode.PASCABAYAR).onClick(view);
                break;
            case 11:
                new ShowModuleOnClickListener(this, MenuActionCode.TVBERLANGGAN).onClick(view);
                break;
            case 12:
                new ShowModuleOnClickListener(this, MenuActionCode.PELNI).onClick(view);
                break;
            case 13:
                new ShowModuleOnClickListener(this, MenuActionCode.PERBANKAN).onClick(view);
                break;
            case 14:
                new ShowModuleOnClickListener(this, MenuActionCode.GAMEONLINE).onClick(view);
                break;
            case 15:
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    new_popup_alert(this, "Info", "Anda belum bisa menikmati layanan ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                    return;
                } else {
                    new ShowModuleOnClickListener(this, MenuActionCode.EXPEDISI).onClick(view);
                    break;
                }
            case 16:
                new ShowModuleOnClickListener(this, MenuActionCode.ASURANSI).onClick(view);
                break;
            case 17:
                new ShowModuleOnClickListener(this, MenuActionCode.KARTUKREDIT).onClick(view);
                break;
            case 18:
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    new_popup_alert(this, "Info", "Anda belum bisa menikmati layanan ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                    return;
                } else {
                    new ShowModuleOnClickListener(this, MenuActionCode.PINJAMDANA).onClick(view);
                    break;
                }
            case 19:
                Timber.d("MENU - onClick:" + PreferenceClass.getUser().getBelanja() + PreferenceClass.getAuth());

                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    new_popup_alert(this, "Info", "Anda belum bisa menikmati layanan ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                    return;
                } else {
                    new ShowModuleOnClickListener(this, MenuActionCode.JUAL_BARANG).onClick(view);
                    break;
                }
            case 20:
                new ShowModuleOnClickListener(this, MenuActionCode.GAS).onClick(view);
                break;
            case 21:
                new ShowModuleOnClickListener(this, MenuActionCode.PAJAK).onClick(view);
                break;
            case 22:
                new ShowModuleOnClickListener(this, MenuActionCode.PKB).onClick(view);
                break;
            case 25:
            case 26:
                new_popup_alert(this, "Info", "Akan Segera Hadir");
                return;
            case 23:
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    new_popup_alert(this, "Info", "Anda belum bisa menikmati layanan ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                    return;
                } else {
                    new ShowModuleOnClickListener(this, MenuActionCode.KEAGENAN).onClick(view);
                    break;
                }
            case 24:
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    new_popup_alert(this, "Info", "Anda belum bisa menikmati layanan ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                    return;
                } else {
                    new ShowModuleOnClickListener(this, MenuActionCode.BAYARBARANG).onClick(view);
                    break;
                }
            case 27:
                //    x.new_popup_alert(view.getContext(), "Info", "Akan Segera Hadir");
                new ShowModuleOnClickListener(this, MenuActionCode.ADDMENU, item).onClick(view);
                return;
            default:
                break;
        }
    }
}

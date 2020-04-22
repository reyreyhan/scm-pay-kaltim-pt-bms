package com.bm.main.pos;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bm.main.fpl.activities.BigPromoActivity;
import com.bm.main.fpl.activities.LoginActivity;
import com.bm.main.fpl.constants.RConfig;
import com.bm.main.fpl.templates.MutedVideoView;
import com.bm.main.fpl.utils.DetectConnection;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;
import com.bm.main.pos.feature.newhome.NewHomeActivity;
import com.bm.main.pos.services.SBFService;
import com.bm.main.single.ftl.utils.RequestUtilsTravel;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.text.DecimalFormat;
import java.util.Locale;

import timber.log.Timber;

//import com.bm.main.pos.feature.login.LoginActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = SplashScreenActivity.class.getSimpleName();
    Context context;
    boolean isFirstStart;
    MutedVideoView videoView;
    private boolean ispaused = false;
    ImageView imageViewPowered;
    private Thread workerThread;
    public static DisplayMetrics displayMetrics;
    public Configuration config;
    FirebaseRemoteConfig firebaseRemoteConfig;
    //    RelativeLayout rel_footer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        runMemory();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Resources res = this.getResources();
        config = res.getConfiguration();
        displayMetrics = res.getDisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        switch (displayMetrics.densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                Timber.d("onCreate: base density low font " + config.fontScale + " screen" + config.screenLayout);
                config.fontScale = 0.93f;
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                if (displayMetrics.widthPixels >= 600) {
                    Timber.d("onCreate: base density medium font " + config.fontScale + " screen" + config.screenLayout);
                    config.fontScale = 1.3f;
                } else {
                    Timber.d("onCreate: base density medium font " + config.fontScale + " screen" + config.screenLayout);
                    config.fontScale = 0.9f;
                }
                break;
            case DisplayMetrics.DENSITY_HIGH:
                if (displayMetrics.widthPixels >= 600) {
                    Timber.d("onCreate: base density high font " + config.fontScale + " screen" + config.screenLayout);
                    config.fontScale = 1.7f;
                } else {
                    Timber.d("onCreate: base density high font " + config.fontScale + " screen" + config.screenLayout);
                    config.fontScale = 0.6f;
                }
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                Timber.d("onCreate: base density xhigh font " + config.fontScale + " screen" + config.screenLayout);
                config.fontScale = 0.95f;
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                Timber.d("onCreate: base density xxhigh font " + config.fontScale + " screen" + config.screenLayout);
                config.fontScale = 0.98f;
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                Timber.d("onCreate: base density xxxhigh font " + config.fontScale + " screen" + config.screenLayout);
                config.fontScale = 1.0f;
                break;
            case DisplayMetrics.DENSITY_TV:
                if (displayMetrics.widthPixels >= 600) {
                    Timber.d("onCreate: base density TV font " + config.fontScale + " screen" + config.screenLayout);
                    config.fontScale = 1.4f;
                } else {
                    config.fontScale = 1.0f;
                }
                break;
            default:
                if (displayMetrics.widthPixels >= 600) {
                    Timber.d("onCreate: base density else font " + config.fontScale + " screen" + config.screenLayout);
                    config.fontScale = 17.5f;
                } else {
                    Timber.d("onCreate: base density else font " + config.fontScale + " screen" + config.screenLayout);
                    config.fontScale = 1.0f;
                }
        }

        config.setTo(getResources().getConfiguration());
        //  Configuration.setTo(getResources().getConfiguration());
        config.locale = new Locale("in", "ID");
        onConfigurationChanged(config);

        context = getApplicationContext();
        isFirstStart = PreferenceClass.getBoolean("firstStart", true);

        videoView = findViewById(R.id.videoView);
        float heightDp = (float) (getResources().getDisplayMetrics().heightPixels * 2) / 5;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) videoView.getLayoutParams();

        lp.height = (int) heightDp;
        videoView.setMinimumHeight(lp.height);
        imageViewPowered = findViewById(R.id.imageViewPowered);

        findViewById(R.id.img_footer).setMinimumHeight(displayMetrics.heightPixels * 2 / 7);

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.intro_splash);

        if (videoView != null) {
            videoView.setVideoURI(uri);
            videoView.setZOrderOnTop(true);
            videoView.setOnErrorListener((mediaPlayer, i, i1) -> false);
            videoView.start();
            start();
        } else {
            start();
        }

        slideToTop(imageViewPowered);

     //   startService(new Intent(this, SBFService.class));
    }

    public void slideToTop(@NonNull View view) {
        TranslateAnimation animate = new TranslateAnimation(0, 0, 0, -view.getHeight());
        animate.setDuration(1000);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ispaused = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ispaused) {
            start();
        }
    }

    private Intent intent;

    private void cekFirst() {
        Timber.d("cekFirst: %s", isFirstStart);
        workerThread = new Thread() {

            @Override
            public void run() {

                Timber.d("run: %s", isFirstStart);
                if (isFirstStart) {
                    PreferenceClass.putInt("deposit_bank_non", 0);
                    intent = new Intent(SplashScreenActivity.this, OnBoardingActivity.class);
                } else {
                    if (PreferenceClass.getString(RConfig.IS_BIG_PROMO, "0").equals("1")) {
                        intent = new Intent(SplashScreenActivity.this, BigPromoActivity.class);
                    } else {
                        if (PreferenceClass.isLoggedIn()) {
                            if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                                intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            } else {
                                PreferenceClass.setLocked();
                                SBFApplication.initUserComponent(PreferenceClass.getTokenPos());

//                                RabbitViewModel vm = new ViewModelProvider(SplashScreenActivity.this, SBFApplication.userComponent.rabbitComponent()).get(RabbitViewModel.class);
//                                vm.getThread().start();
//                                intent = new Intent(SplashScreenActivity.this, KunciActivity.class);

                                intent = new Intent(SplashScreenActivity.this, NewHomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            }
                        } else {
                            intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        }
                    }
                }

                startActivity(intent);
                finish();
            }

        };
        workerThread.start();
    }

    @Override
    protected void onStart() {
        Runtime.getRuntime().gc();
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        videoView.clearAnimation();
        videoView.removeCallbacks(workerThread);
        Runtime.getRuntime().gc();
        super.onDestroy();
    }

    private void start() {

        if (!DetectConnection.isOnline(this)) {
            Toast.makeText(this, "Cek Koneksi Internet Anda", Toast.LENGTH_LONG).show();
            return;
        }

        Timber.d("start: running");

//        long cacheExpiration = (BuildConfig.DEBUG) ? 10 : 3600;
        long cacheExpiration = 1;
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(cacheExpiration)
                .build();

        firebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        firebaseRemoteConfig.setDefaultsAsync(R.xml.default_config);
        firebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(task -> {
                    setConfig();
                    cekFirst();
                });
    }

    private void setConfig() {
        PreferenceClass.putString(RConfig.API_URL_FP, firebaseRemoteConfig.getString("apiUrlFP"));
        PreferenceClass.putString(RConfig.API_URL_FP_DEVEL, firebaseRemoteConfig.getString("apiUrlFPDevel"));
        PreferenceClass.putString(RConfig.API_URL_FT, firebaseRemoteConfig.getString("apiUrlFT"));
        PreferenceClass.putString(RConfig.API_URL_POS, firebaseRemoteConfig.getString("apiUrlPOSv2"));
        PreferenceClass.putString(RConfig.API_URL_POS_DEVEL, firebaseRemoteConfig.getString("apiUrlPOSv2"));
        PreferenceClass.putString(RConfig.API_YOU_TUBE, firebaseRemoteConfig.getString("api_youtube"));
        PreferenceClass.putString(RConfig.MIN_VERSION_CODE, firebaseRemoteConfig.getString("minVersionCode"));
        SBFApplication.log(RConfig.MIN_VERSION_CODE, firebaseRemoteConfig.getString("minVersionCode"));
        PreferenceClass.putString(RConfig.FORCE_UPDATE, firebaseRemoteConfig.getString("forceUpdate"));
        PreferenceClass.putString(RConfig.ON_BOARD, firebaseRemoteConfig.getString("onBoard"));
        PreferenceClass.putString(RConfig.ADD_MENU, firebaseRemoteConfig.getString("addMenu"));
        PreferenceClass.putString(RConfig.BIG_PROMO, firebaseRemoteConfig.getString("bigPromo"));
        PreferenceClass.putString(RConfig.IS_BIG_PROMO, firebaseRemoteConfig.getString("isBigPromo"));
        PreferenceClass.putString(RConfig.IS_ToolTipAjakBisnis, firebaseRemoteConfig.getString("isToolTipAjakBisnis"));
        PreferenceClass.putString(RConfig.ContentToolTipAjakBisnis, firebaseRemoteConfig.getString("contentToolTipAjakBisnis"));
        PreferenceClass.putString(RConfig.IS_PopupPromo, firebaseRemoteConfig.getString("isPopupPromo"));
        PreferenceClass.putString(RConfig.UrlImgPopupPromo, firebaseRemoteConfig.getString("urlImgPopupPromo"));
        PreferenceClass.putString(RConfig.UrlCTAPopupPromo, firebaseRemoteConfig.getString("urlCTAPopupPromo"));
        PreferenceClass.putString(RConfig.DateRangeMutasi, firebaseRemoteConfig.getString("date_range_mutasi"));
        PreferenceClass.putString(RConfig.DateRangeReport, firebaseRemoteConfig.getString("date_range_report"));
        PreferenceClass.putString(RConfig.DateRangeKomisi, firebaseRemoteConfig.getString("date_range_komisi"));
        PreferenceClass.putString(RConfig.ListBank, firebaseRemoteConfig.getString("listBank"));
        PreferenceClass.putString("qrStrukJogjaKita", firebaseRemoteConfig.getString("qrStrukJogjaKita"));
        Timber.d("onComplete: " + firebaseRemoteConfig.getString("isBigPromo") + " " + firebaseRemoteConfig.getString("isToolTipAjakBisnis"));
        if (BuildConfig.DEBUG) {
            SBFApplication.log(RConfig.API_URL_FP_DEVEL, firebaseRemoteConfig.getString("apiUrlFPDevel"));
            RequestUtils.setUrl(firebaseRemoteConfig.getString("apiUrlFPDevel"));
        } else {
            SBFApplication.log(RConfig.API_URL_FP, firebaseRemoteConfig.getString("apiUrlFP"));
            RequestUtils.setUrl(firebaseRemoteConfig.getString("apiUrlFP"));
        }
        RequestUtils.setUrl(firebaseRemoteConfig.getString("apiUrlFP"));
//        Log.e("PROFF", "check current url : "+ RequestUtils.getUrl());
//        Log.e("PROFF", "prod link : "+firebaseRemoteConfig.getString("apiUrlFP"));
//        Log.e("PROFF", "prod link api pos : "+firebaseRemoteConfig.getString("apiUrlPOSv2"));
        RequestUtilsTravel.setUrl(firebaseRemoteConfig.getString("apiUrlFT"));

        SBFApplication.log("Saving remote config...");
        Timber.d("onComplete: %s", firebaseRemoteConfig.getString("apiUrlPOSDevel2"));
    }


    private void runMemory() {
        int size = (1024 * 1024 * 40);//Integer.MAX_VALUE;
        int factor = 10;

        try {
            while (true) {
                try {
                    Timber.d("Trying to allocate %s", getFileSize(size));
//                    byte[] bytes = new byte[size];

                    Runtime.getRuntime().gc();
                    Timber.d("Succeed! %s", getFileSize(Runtime.getRuntime().freeMemory()));
                    break;
                } catch (Exception e) {
                    Timber.d("runOutOfMemory: %s", e.toString());
                } catch (OutOfMemoryError e) {
                    Timber.d("runOutOfMemory: OOME .. Trying again with 10x less %s", getFileSize(size /= factor));
                    size /= factor;
//                       byte[] bytes = new byte[size];
                    Runtime.getRuntime().gc();

                }
            }
        } catch (RuntimeException e) {
            Timber.d("runOutOfMemory: %s", e.toString());
            Timber.d("runOutOfMemory: ampun memory habis");
        }
    }


    @NonNull
    public static String getFileSize(long size) {
        if (size <= 0)
            return "0";

        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));

        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
package com.bm.main.fpl.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationProvider;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.text.Spanned;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.FCMConstants;
import com.bm.main.fpl.constants.Info;
import com.bm.main.fpl.constants.RequestCode;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.BarcodeListener;
import com.bm.main.fpl.interfaces.BodyObjectResponseCallback;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.models.NotificationModel;
import com.bm.main.fpl.models.PromoProdukModel;
import com.bm.main.fpl.models.SaldoModel;
import com.bm.main.fpl.templates.indicators.AVLoadingIndicatorView;
import com.bm.main.fpl.templates.materialbarcodescanner.MaterialBarcodeScanner;
import com.bm.main.fpl.templates.materialbarcodescanner.MaterialBarcodeScannerBuilder;
import com.bm.main.fpl.templates.showcaseview.GuideView;
import com.bm.main.fpl.templates.topsheet.TopSheetDialog;
import com.bm.main.fpl.utils.Device;
import com.bm.main.fpl.utils.DialogUtils;
import com.bm.main.fpl.utils.ExceptionHandler;
import com.bm.main.fpl.utils.FileUtils;
import com.bm.main.fpl.utils.FormatString;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.PrintPic;
import com.bm.main.fpl.utils.ReadKeyInbox;
import com.bm.main.fpl.utils.RequestUtils;
import com.bm.main.fpl.utils.StringJson;
import com.bm.main.pos.BuildConfig;
import com.bm.main.pos.R;
import com.bm.main.pos.SBFApplication;
import com.bm.main.pos.callback.PermissionCallback;
import com.bm.main.pos.rabbit.RabbitMqPrint;
import com.bm.main.pos.rabbit.RabbitMqThread;
import com.bm.main.pos.services.BootBroadcast;
import com.bm.main.pos.services.SensorService;
import com.bm.main.pos.shortcutbadger.ShortcutBadger;
import com.bm.main.pos.utils.NotifUtil;
import com.bm.main.pos.utils.PermissionUtil;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bm.main.single.ftl.utils.RequestUtilsTravel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.security.ProviderInstaller;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.WriterException;
import com.google.zxing.integration.android.IntentIntegrator;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import timber.log.Timber;

import static com.bm.main.fpl.utils.DialogUtils.closeBootomSheetDialog;
import static com.bm.main.fpl.utils.DialogUtils.openBottomSheetDialog;
import static com.bm.main.fpl.utils.FormatString.fromHtml;

/**
 * Created by sarifhidayat on 10/27/17.
 **/

@SuppressWarnings("ALL")
public class BaseActivity extends AppCompatActivity implements ProgressResponseCallback, BodyObjectResponseCallback, ComponentCallbacks2, ProviderInstaller.ProviderInstallListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private Location mylocation;
    private GoogleApiClient googleApiClient;
    public ReadKeyInbox readKeyInbox;
    public RelativeLayout bottom_toolbar;

    public GuideView mGuideView;
    public GuideView.Builder mGbuilder;

    @NonNull
    public Handler mHandler = new Handler();
    public int recent_version_code;
    public String pathLogoStrukFp;
    private LocationProvider locationProviderLow, locationProviderHigh;
    private BootBroadcast bootBroadcast;
    public TextToSpeech textToSpeech;

    private static final String TAG = BaseActivity.class.getSimpleName();
    public static StringJson stringJson;
    public static Configuration config;

    public Snackbar snackbar;
    public Typeface tfLight;

    Dialog mPromoDialog;
    public Dialog mProgressDialog;

    public TopSheetDialog dialog;

    public Toolbar toolbar;
    public static DisplayMetrics displayMetrics;

    @NonNull
    public static String isSave = "false";

    public Gson gson;

    public String strukTercetak;
    public String qrCode;
    public static Animation animShake;

    public BluetoothSocket socket;
    public BluetoothDevice mmDevice;
    static OutputStream os;
    public boolean finishPrinting = false;
    public static Animation slideUp;
    public static Animation slideDown;
    private boolean isPrint = false;

    public int countText = 0;

    private static final long INTERVAL = 1000 * 3;
    private static final long FASTEST_INTERVAL = 1000 * 3;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    private static final long UPDATE_INTERVAL = 10 * 3000;

    /**
     * The fastest rate for active location updates. Updates will never be more frequent
     * than this value, but they may be less frequent.
     */
    private static final long FASTEST_UPDATE_INTERVAL = UPDATE_INTERVAL / 2;

    /**
     * The max time before batched results are delivered by location services. Results may be
     * delivered sooner than this interval.
     */
    private static final long MAX_WAIT_TIME = UPDATE_INTERVAL * 3;

    private SensorService mSensorService;

    public static final String ACTION_REQ_BLUETOOTH_PERM = "profit-perm-bluetooth";
    public static final String QR_STRUK_PRINT = "profit-struk-msg";
    public static final String QR_IMG_PRINT = "profit-qr-img";
    private RequestManager glide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!BuildConfig.DEBUG) {
            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        }

        Timber.d("onCreate: getPackageName " + getPackageName());

        ProviderInstaller.installIfNeededAsync(this, this);

        ActivityManager.MemoryInfo memoryInfo = getAvailableMemory();
        Timber.d("onCreate avail: " + FileUtils.getFileSize(memoryInfo.availMem));
        Timber.d("onCreate total mem: " + FileUtils.getFileSize(memoryInfo.totalMem));
        if (!memoryInfo.lowMemory) {
            // Do memory intensive work ...
            Timber.d("onCreate: " + !memoryInfo.lowMemory);
            // runOutOfMemory();
        }

        super.onCreate(savedInstanceState);

        FCMConstants.isActivityRunning = true;
        mInstanceBaseActivity = this;

        recent_version_code = BuildConfig.VERSION_CODE;

        setUpGClient();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Resources res = this.getResources();
        config = res.getConfiguration();
        ShortcutBadger.applyCount(this, 0);

        displayMetrics = res.getDisplayMetrics();
        float widthDpi = displayMetrics.xdpi;
        float heightDpi = displayMetrics.ydpi;
        float widthInches = displayMetrics.widthPixels / widthDpi;
        float heightInches = displayMetrics.heightPixels / heightDpi;
        double diagonalInches = Math.sqrt(
                (widthInches * widthInches)
                        + (heightInches * heightInches));
        float smallestWidth = Math.min(widthDpi, heightDpi);
        Timber.d("onCreate: dimension inchi " + diagonalInches + " smallest width " + smallestWidth);
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

        config.locale = new Locale("in", "ID");
        config.setTo(getResources().getConfiguration());
        onConfigurationChanged(config);

        Timber.d("onCreate base activity: pixel " + displayMetrics.widthPixels + " density " + displayMetrics.densityDpi + " width " + displayMetrics.widthPixels + " height " + displayMetrics.heightPixels);
        Timber.d("after font scale:  " + getResources().getDimension(R.dimen.textVerySmall));

        tfLight = ResourcesCompat.getFont(this, R.font.roboto_light);
        dialog = new TopSheetDialog(this);
        animShake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);

        stringJson = new StringJson(this);
        readKeyInbox = new ReadKeyInbox(this);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(@NonNull InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Timber.d("newToken ->" + newToken);
                PreferenceClass.saveDeviceToken(newToken);
            }
        });

        gson = new GsonBuilder().create();
        slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                mMessageReceiver,
                new IntentFilter("BROADCAST_TOPUP")
        );

        if (getIntent().hasExtra(ACTION_REQ_BLUETOOTH_PERM)) {
            NotifUtil.cancelNotif(this);
            new PermissionUtil(this).checkBluetoothPermission(new PermissionCallback() {
                @Override
                public void onSuccess() {
                    String msg = getIntent().getStringExtra(QR_STRUK_PRINT);
                    if (msg != null && !msg.isEmpty()) {
                        RabbitMqPrint.printStrukRabbit(msg, BaseActivity.this, null, null);
                    }
                }

                @Override
                public void onFailed() {
                }
            });
        }

        if (PreferenceClass.isLoggedIn()) {
            if (SBFApplication.getInstance().rabbitThread == null) {
                SBFApplication.getInstance().rabbitThread = new RabbitMqThread(this);
                SBFApplication.getInstance().rabbitThread.start();
            } else if (!SBFApplication.getInstance().rabbitThread.isAlive()) {
                SBFApplication.getInstance().rabbitThread.start();
            }
        }
        glide = Glide.with(this);
    }

    public static final int back = 0;
    public static final int close = 1;
    public static final int drawer = 2;

    /**
     * This method init.
     *
     * @param type 0=back
     *             1=close
     *             2=drawer
     */
    public void init(int type) {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            toolbar.setTitleTextAppearance(this, R.style.TextAppearance_Widget_ActionBar_Title);
            toolbar.setSubtitleTextAppearance(this, R.style.TextAppearance_Widget_ActionBar_Subtitle);
            toolbar.setContentInsetStartWithNavigation(0);
            toolbar.setContentInsetEndWithActions(0);
            toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.md_white_1000));
            toolbar.setSubtitleTextColor(ContextCompat.getColor(this, R.color.md_blue_100));

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayUseLogoEnabled(false);
            getSupportActionBar().setElevation(0);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            if (type == back) {
                getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_toolbar_back));
            } else if (type == close) {
                getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_cancel));
            } else if (type == drawer) {
                getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_drawer));
            }
        }
    }

    public void showToast(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int DEFAULT = 0;
    public int ALERT = 1;
    public int WARNING = 2;
    public int INFO = 3;

    /**
     * This method snackBarCustomAction.
     *
     * @param view          view layout
     * @param message       pesan yang akan ditampikan
     * @param response_desc
     * @param type          DEFAULT,
     *                      ALERT,
     *                      WARNING,
     */
    public void snackBarCustomAction(@NonNull View view, int message, String response_desc, int type) {
        int colorBackground;
        switch (type) {
            case 1:
                colorBackground = R.color.md_red_500;
                break;
            case 2:
                colorBackground = R.color.md_orange_500;
                break;
            case 3:
                colorBackground = R.color.md_light_blue_500;
                break;
            default:
                colorBackground = R.color.md_black_1000;
                break;
        }

        snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG);

        snackbar.setActionTextColor(getResources().getColor(R.color.md_white_1000));

        ViewGroup layout = (ViewGroup) snackbar.getView();
        layout.setBackgroundColor(ContextCompat.getColor(BaseActivity.this, colorBackground));
        View snackView = View.inflate(view.getContext(), R.layout.supertoast_button_custom, (ViewGroup) findViewById(R.id.linRootToastSuper));
        TextView textMsg = snackView.findViewById(R.id.message);

        if (message == 0) {
            textMsg.setText(response_desc);
        } else {
            textMsg.setText(getResources().getString(message));
        }

        textMsg.setTextColor(getResources().getColor(R.color.md_white_1000));
        Button buttonAction = snackView.findViewById(R.id.buttonActionToast);
        buttonAction.setText("Tutup");
        buttonAction.setBackgroundResource(R.color.colorTransparent);
        buttonAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });

        layout.addView(snackView, 0);

        snackbar.show();
    }

    public void openProgressBarDialog(@NonNull Context context, @NonNull View v) {
        AVLoadingIndicatorView avi = v.findViewById(R.id.avi);
        avi.setIndicator("com.bm.main.fpl.templates.indicators.BallPulseIndicator");

        mProgressDialog = new Dialog(context, R.style.MaterialDialogSheet);
        mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mProgressDialog.setContentView(v);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    public void closeProgressBarDialog() {
        try {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (Exception e) {
        }
    }

    protected static BaseActivity mInstanceBaseActivity;

    public static synchronized BaseActivity getInstanceBaseActivity() {
        return mInstanceBaseActivity;
    }

    @Nullable
    TextView version, textViewHome, textViewAdmin, textViewLogout, liveChat, textViewLaporan, textViewDeposit, textViewKunci;

    public void openTopDialog(boolean isHome) {
        dialog.setContentView(R.layout.sheet_content);

        version = dialog.findViewById(R.id.textViewVersion);

        textViewHome = dialog.findViewById(R.id.textViewHome);
        textViewHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {
                closeTopSheet(v);
            }
        });

        textViewAdmin = dialog.findViewById(R.id.textViewAdmin);
        textViewAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {
                closeTopSheet(v);
            }
        });

        textViewLogout = dialog.findViewById(R.id.textViewLogout);
        textViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {
                closeTopSheet(v);
            }
        });

        liveChat = dialog.findViewById(R.id.textViewLiveChat);
        liveChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {
                closeTopSheet(v);
            }
        });

        textViewLaporan = dialog.findViewById(R.id.textViewLaporan);
        textViewLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {
                closeTopSheet(v);
            }
        });

        textViewDeposit = dialog.findViewById(R.id.textViewDeposit);
        textViewDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {
                closeTopSheet(v);
            }
        });

        textViewKunci = dialog.findViewById(R.id.textViewKunci);
        textViewKunci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {
                closeTopSheet(v);
            }
        });

        textViewLaporan.setVisibility(isHome ? View.VISIBLE : View.GONE);
        textViewDeposit.setVisibility(isHome ? View.VISIBLE : View.GONE);
        textViewKunci.setVisibility(isHome ? View.VISIBLE : View.GONE);
        textViewHome.setVisibility(isHome ? View.GONE : View.VISIBLE);
        liveChat.setVisibility(isHome ? View.GONE : View.VISIBLE);
        textViewAdmin.setVisibility(isHome ? View.GONE : View.VISIBLE);

        version.setText("v " + Info.getPackageInfo(this).versionName);
        dialog.show();
    }

    public void closeTopSheet(@NonNull View view) {
        dialog.dismiss();
        int id = view.getId();

        Intent intent;
        if (id == R.id.textViewLiveChat) {
            if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                new_popup_alertDemo(BaseActivity.this, "Info", "Anda belum bisa menikmati fiture ini.\n" +
                        "Daftar & Aktifasi sekarang juga ID Anda");
            } else {
                DialogUtils.new_popup_bantuan(this);
            }
        } else if (id == R.id.textViewAdmin) {
            intent = new Intent(BaseActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("admin", true);
            startActivity(intent);
        } else if (id == R.id.textViewLogout) {
            requestLogout();
            PreferenceClass.setLogOut();
            intent = new Intent(BaseActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        } else if (id == R.id.textViewHome) {
            intent = new Intent(BaseActivity.this, HomeActivity.class);
            intent.putExtra("home", true);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else if (id == R.id.textViewLaporan) {
            if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                new_popup_alertDemo(BaseActivity.this, "Info", "Anda belum bisa menikmati fiture ini.\n" +
                        "Daftar & Aktifasi sekarang juga ID Anda");
            } else {
                intent = new Intent(BaseActivity.this, LaporanGridActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        } else if (id == R.id.textViewDeposit) {
            if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                new_popup_alertDemo(BaseActivity.this, "Info", "Anda belum bisa menikmati fiture ini.\n" +
                        "Daftar & Aktifasi sekarang juga ID Anda");
            } else {
                intent = new Intent(BaseActivity.this, DepositActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        } else if (id == R.id.textViewKunci) {
            if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                new_popup_alertDemo(BaseActivity.this, "Info", "Anda belum bisa menikmati fiture ini.\n" +
                        "Daftar & Aktifasi sekarang juga ID Anda");
            } else {
                PreferenceClass.setLocked();
                intent = new Intent(BaseActivity.this, KunciActivity.class);
                intent.putExtra("bottom", true);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

                startActivity(intent);
            }
        }
    }

    public void liveChat() {
        String apps = "com.android.chrome";
        boolean installed = Device.checkAppInstall(this, apps);
        if (installed) {
            logEventFireBase("Bantuan", "Live Chat", EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, TAG);
            String url = "http://lc-fastpay.fastpay.co.id/phplive/phplive.php?d=0&onpage=livechatimagelink&title=Live+Chat+Image+Link&namachat=" + PreferenceClass.getLoggedUser().getId();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setDataAndType(Uri.parse(url), getContentResolver().getType(Uri.parse(url)));
            startActivity(i);
        } else {
            Toast.makeText(this, "Aplikasi Google Chrome tidak di temukan, Silahkan Intall Google Chrome terlebih dahulu", Toast.LENGTH_LONG).show();
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + apps));
            startActivity(webIntent);
        }
    }

    public void closeKeyboard(@NonNull Context context) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        Timber.d("closeKeyboard: " + inputManager);
        if (inputManager != null) {
            if ((getCurrentFocus()) != null) {
                //  inputManager.hideSoftInputFromWindow((getCurrentFocus()).getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                inputManager.hideSoftInputFromWindow((getCurrentFocus()).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public void closeKeyboardHome(@NonNull Context context) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        Timber.d("closeKeyboard: " + inputManager);
        if (inputManager != null) {
            if ((this.getCurrentFocus()) != null) {
                inputManager.hideSoftInputFromWindow((this.getCurrentFocus()).getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public void closeKeyboardDialog(@NonNull final View view) {
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 1);
    }

    public void navigationBottomCustom(@Nullable RelativeLayout bottom_toolbar, String saldo) {
        Timber.d("navigationBottomCustom: " + saldo);
        if (bottom_toolbar != null) {
            FrameLayout frame_bottom_ribbon_demo = bottom_toolbar.findViewById(R.id.frame_bottom_ribbon_demo);
            if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo()) || PreferenceClass.getId().substring(0, 2).equals("BS")) {
                frame_bottom_ribbon_demo.setVisibility(View.VISIBLE);
            } else {
                frame_bottom_ribbon_demo.setVisibility(View.GONE);
            }
            TextView textViewPlusSaldoBottom = bottom_toolbar.findViewById(R.id.TextViewPlusSaldoBottom);
            textViewPlusSaldoBottom.setText("Rp " + PreferenceClass.getString("saldo", "0"));
            bottom_toolbar.invalidate();
        }
    }

    public void toggleSaldo(View view) {
        Timber.d("toggleSaldo: ");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestCekSaldo());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.CEK_SALDO, this);
    }

    public void toggleDeposit(View view) {
        Timber.d("toggleDeposit base: ");
        if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
            new_popup_alertDemo(BaseActivity.this, "Info", "Anda belum bisa menikmati layanan ini.\n" +
                    "Daftar & Aktifasi sekarang juga ID Anda");
        } else {
            Intent intent = new Intent(this, DepositActivity.class);
            startActivity(intent);
        }
    }

    public void togglePrinter(View view) {
        Timber.d("togglePrinter: ");
        Intent intent = new Intent(this, SettingPrinterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("bottom", true);
        startActivity(intent);
    }

    public void toggleKunci(@NonNull View view) {
        Timber.d("toggleKunci: ");
        if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
            new_popup_alert(view.getContext(), "Info", "Anda belum bisa menikmati layanan ini.\n" +
                    "Daftar & Aktifasi sekarang juga ID Anda");
        } else {

            Intent intent = new Intent(this, KunciActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("bottom", true);
            startActivity(intent);
        }
    }

    public CountDownTimer myCountDownTimer;

    public void countDownLimit(@NonNull final TextView v) {
        myCountDownTimer = new CountDownTimer((long) 60000, 1000) {

            public void onTick(long millisUntilFinished) {
                v.setText("Menunggu Key SMS  " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                v.setText("Fastpay tidak dapat menerima Key SMS.\n Silahkan Cek Inbox SMS Anda.");
            }
        }.start();
    }

    @Nullable
    public Bitmap bitmapQRCode;
    String id_pelQRCode;
    static Thread t;
    String imagePathQR;
    @Nullable
    BluetoothAdapter bluetoothAdapter = null;

    synchronized public void cetak(@NonNull Context mContext) {
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    print(mContext);
                } catch (Exception e) {
                    Timber.d("ERROR", "Printing", e);
                } finally {
                    Timber.d("run: ");
                    try {

                        if (socket != null) {
                            try {
                                Thread.sleep(4000);
                            } catch (InterruptedException e) {
                                Timber.e(e);
                            }
                            socket.close();
                        }
                    } catch (IOException e) {
                        showToast("Tidak dapat mencetak struk ke printer bluetooth");
                    }
                }
            }
        });
        t.start();
    }

    public void print(@NonNull Context mContext) {
        boolean isSocket;
        String deviceName;

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            Timber.d("print: pairedDevices.size() " + pairedDevices.size());
            try {
                if (!bluetoothAdapter.isEnabled()) {
                    if (!bluetoothAdapter.enable()) {
                        showToast("pastikan bluetooth handphone anda menyala");
                    }
                }

                for (BluetoothDevice device : pairedDevices) {
                    deviceName = device.getName();
                    String a = device.getAddress();
                    mmDevice = bluetoothAdapter.getRemoteDevice(device.getAddress());
                    UUID BPP = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
                    try {
                        socket = mmDevice.createRfcommSocketToServiceRecord(BPP);
                    } catch (IOException e) {
                        showToast("Tidak dapat melakukan koneksi awal dengan printer bluetooth"); // Unable
                    }
                    Method m = mmDevice.getClass().getMethod("createRfcommSocket", int.class);
                    try {
                        socket = (BluetoothSocket) m.invoke(mmDevice, 1);
                        socket.connect();

                        Timber.d("Connected 1 " + socket.isConnected());
                    } catch (Exception e) {
                        try {
                            Timber.d("Trying fallback..." + e.toString());
                            Timber.d("mencoba menghubungkan kembali ke printer");
                            socket = mmDevice.createRfcommSocketToServiceRecord(BPP);
                            socket = (BluetoothSocket) m.invoke(mmDevice, 1);
                            socket.connect();

                            Timber.d("Connected 2 " + socket.isConnected());
                        } catch (Exception e2) {
                            Timber.e(e2);
                            try {
                                snackBarCustomAction(findViewById(R.id.bottom_toolbar), 0, "Tidak dapat terhubung ke printer bluetooth " + deviceName + "\n Pastikan printer bluetooth " + deviceName + "anda telah menyala", WARNING);
                            } catch (Exception eq) {
                                snackBarCustomAction(findViewById(R.id.btn_test_print), 0, "Tidak dapat terhubung ke printer bluetooth " + deviceName + "\n Pastikan printer bluetooth " + deviceName + "anda telah menyala", WARNING);
                            }
                        }
                    }

                    Timber.d("print socket: " + socket.isConnected());
                    if (socket.isConnected()) {
                        try {
                            os = socket.getOutputStream();
                            try {
                                os.write(ResponseCode.InitializePrinter.getBytes());
                                printLogo();
                                os.write(strukTercetak.getBytes("UTF-8"), 0, strukTercetak.getBytes("UTF-8").length);
                                if (PreferenceClass.getBoolean("switchQRCodeStruk", true) == true) {
                                    if (bitmapQRCode != null) {
                                        printQRCode();
                                    }
                                }
                                os.write(0xA);
                                os.write(0xA);
                                os.flush();
                            } catch (Exception e) {
                                showToast("Tidak dapat mencetak struk ke printer bluetooth" + e.toString()); //
                            } finally {
                                Timber.d("print: finnaly");
                                if (os != null) {
                                    try {
                                        Thread.sleep(4000);
                                    } catch (InterruptedException e) {
                                        Timber.e(e);
                                    }
                                    os.close();

                                }
                            }
                        } catch (Exception e) {
                            showToast("Tidak dapat mencetak struk ke printer bluetooth");
                        }
                    } else {
                        Timber.d("print: ga konek");
                    }
                }

                Intent intent = new Intent("BROADCAST_PRINTING");
                // Put the random number to intent to broadcast it
                intent.putExtra("finish", true);

                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                Timber.d("print: finish");
                t.currentThread().interrupt();
            } catch (Exception e) {
                Timber.d("print exception this: " + e.toString());
                t.currentThread().interrupt();
            }
        } else {
            Intent intentBrod = new Intent("BROADCAST_PRINTING");
            // Put the random number to intent to broadcast it
            intentBrod.putExtra("finish", true);

            LocalBroadcastManager.getInstance(mContext).sendBroadcast(intentBrod);
            snackBarCustomAction(findViewById(R.id.bottom_toolbar), 0, "Pastikan printer bluetooth anda On dan telah melakukan pairing dengan printer bluetooth", WARNING);
            snackbar.dismiss();
            t.currentThread().interrupt();
            Intent intent = new Intent(this, SettingPrinterActivity.class);
            startActivity(intent);
        }
    }

    public void printLogo() throws IOException {
        Drawable d = Drawable.createFromStream(getAssets().open("logo_fp.bmp"), null);

        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
        PrintPic pg = PrintPic.getInstance();
        pg.initCanvas(350);
        pg.initPaint();
        pg.drawImage(58, 0, bitmap);
        byte[] bitmapdata = pg.printDraw();
        os.write(bitmapdata, 0, bitmapdata.length);
    }

    private void printText() {
    }

    public void printQRCode() throws IOException {
        Bitmap bitmap = bitmapQRCode;
        PrintPic pgQR = PrintPic.getInstance();
        byte[] sendDataQR;
        pgQR.initCanvas(380);
        pgQR.initPaint();
        if (isMatrix == 1) {
            pgQR.drawImage(0, 0, bitmap);
        } else {
            pgQR.drawImage(125, 0, bitmap);
        }
        sendDataQR = pgQR.printDraw();
        if (sendDataQR != null) {
            os.write(sendDataQR, 0, sendDataQR.length);
        }
    }

    private static final int BUFFER_SIZE = 4096;

    @NonNull
    public CFAlertDialog.CFAlertActionAlignment getButtonGravity(int pilih) {
        if (pilih == 0) {
            return CFAlertDialog.CFAlertActionAlignment.START;
        }
        if (pilih == 1) {
            return CFAlertDialog.CFAlertActionAlignment.CENTER;
        }
        if (pilih == 2) {
            return CFAlertDialog.CFAlertActionAlignment.END;
        }
        if (pilih == 3) {
            return CFAlertDialog.CFAlertActionAlignment.JUSTIFIED;
        }
        return CFAlertDialog.CFAlertActionAlignment.JUSTIFIED;
    }

    private static final int ERROR_DIALOG_REQUEST_CODE = 200;

    private boolean mRetryProviderInstall;

    @Override
    public void onProviderInstalled() {
    }

    @Override
    public void onProviderInstallFailed(int errorCode, Intent intent) {
        if (GooglePlayServicesUtil.isUserRecoverableError(errorCode)) {
            // Recoverable error. Show a dialog prompting the user to
            // install/update/enable Google Play services.
            GooglePlayServicesUtil.showErrorDialogFragment(
                    errorCode,
                    this,
                    ERROR_DIALOG_REQUEST_CODE,
                    new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            // The user chose not to take the recovery action
                            onProviderInstallerNotAvailable();
                        }
                    });
        } else {
            // Google Play services is not available.
            onProviderInstallerNotAvailable();
        }
    }

    private void onProviderInstallerNotAvailable() {
        Toast.makeText(this, "Google Play Services Tidal Tersedia", Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (mRetryProviderInstall) {
            // We can now safely retry installation.
            // ProviderInstall.installIfNeededAsync(this, this);
            ProviderInstaller.installIfNeededAsync(this, this);
        }
        mRetryProviderInstall = false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RequestCode.REQUEST_CHECK_SETTINGS_GPS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        getMyLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        new_popup_alert_two_buttonPermisionGPS(BaseActivity.this, "Peringatan", R.string.content_alert_gps);
                        break;
                }
                break;
            case ERROR_DIALOG_REQUEST_CODE:
                mRetryProviderInstall = true;
                break;
            case 666:
                Timber.e("return from requestCode %s, with result %s", requestCode, resultCode);
                if (getIntent().hasExtra(QR_STRUK_PRINT) || getIntent().hasExtra(QR_IMG_PRINT))
                    RabbitMqPrint.printStrukRabbit(getIntent().getStringExtra(QR_STRUK_PRINT), this, getIntent().getStringExtra(QR_IMG_PRINT), null);
                else
                    super.onActivityResult(requestCode, resultCode, data);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    public void requestLogout() {
        SBFApplication.getInstance().rabbitThread.setRunning(false);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestSignOut(PreferenceClass.getLoggedUser().getId()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.REQUEST_LOGOUT, this);
    }

    public void openScanner(@NonNull AppCompatActivity context) {
        Timber.d("openScanner: masuk " + context.getClass().getSimpleName());

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            grantPermissions(RequestCode.ActionCode_CAMERA, Manifest.permission.CAMERA);
            return;
        }
        IntentIntegrator integrator = new IntentIntegrator(this);

        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt(String.valueOf(FormatString.htmlString(getString(R.string.scan_qrCode))));
        integrator.setOrientationLocked(false);
        integrator.setRequestCode(ActionCode.BARCODE);
        integrator.setBeepEnabled(true);
        integrator.initiateScan();
    }

    public void grantPermissions(int requestCode, @NonNull String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        } else {
            ActivityCompat.requestPermissions(this, permissions, requestCode);
        }
    }

    public void grantPermissionsGroup(int requestCode, @NonNull String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        } else {
            ActivityCompat.requestPermissions(this, permissions, requestCode);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Timber.d("onRequestPermissionsResult: " + requestCode);
        if (requestCode == RequestCode.ActionCode_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Timber.d("onRequestPermissionsResult: masuk camera");
                IntentIntegrator integrator = new IntentIntegrator(this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt(String.valueOf(FormatString.htmlString(getResources().getString(R.string.scan_qrCode))));
                integrator.setOrientationLocked(false);
                integrator.setBeepEnabled(true);
                integrator.setRequestCode(ActionCode.BARCODE);
                integrator.initiateScan();
            } else {
                new_popup_alert_two_buttonPermision(this, "Peringatan", R.string.content_alert_camera);
            }
        } else if (requestCode == RequestCode.ActionCode_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                this.startActivityForResult(intent, ActionCode.PICK_CONTACT);
            } else {
                new_popup_alert_two_buttonPermision(this, "Peringatan", R.string.content_alert_kontak);
            }
        } else if (requestCode == RequestCode.ActionCode_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startServiceGPSSBF();
            }
        }
    }

    public Bitmap mergeBitmaps(@NonNull Bitmap overlay, @NonNull Bitmap bitmap) {
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();

        Bitmap combined = Bitmap.createBitmap(width, height, bitmap.getConfig());
        Canvas canvas = new Canvas(combined);
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        canvas.drawBitmap(bitmap, new Matrix(), null);

        int centreX = (canvasWidth - overlay.getWidth()) / 2;
        int centreY = (canvasHeight - overlay.getHeight()) / 2;
        canvas.drawBitmap(overlay, centreX, centreY, null);

        return combined;
    }

    String path;
    File dir;
    File file;

    public File saveImageBaseFile(@NonNull WebView webView_) {
        webView_.measure(View.MeasureSpec.makeMeasureSpec(
                View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        webView_.layout(0, 0, webView_.getMeasuredWidth(), webView_.getMeasuredHeight());
        webView_.setDrawingCacheEnabled(true);
        webView_.buildDrawingCache();
        Bitmap b = Bitmap.createBitmap(webView_.getMeasuredWidth(), webView_.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        Paint paint = new Paint();
        int iHeight = b.getHeight();
        c.drawBitmap(b, 0, iHeight, paint);
        webView_.draw(c);

        return SaveImage(b);
    }

    public File saveImageBaseFile(@NonNull NestedScrollView linStruk) {
        int totalHeight = linStruk.getChildAt(0).getHeight();
        int totalWidth = linStruk.getChildAt(0).getWidth();

        Bitmap mBitmap = getBitmapFromView(linStruk, totalHeight, totalWidth);
        return SaveImage(mBitmap);
    }

    private Bitmap getBitmapFromView(View view, int height, int width) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }

    public File saveImageBaseFileImageView(@NonNull ImageView webView_) {
        webView_.measure(View.MeasureSpec.makeMeasureSpec(
                View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        webView_.layout(0, 0, webView_.getMeasuredWidth(), webView_.getMeasuredHeight());
        webView_.setDrawingCacheEnabled(true);
        webView_.buildDrawingCache();
        Bitmap b = Bitmap.createBitmap(webView_.getMeasuredWidth(), webView_.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        Paint paint = new Paint();
        int iHeight = b.getHeight();
        c.drawBitmap(b, 0, iHeight, paint);
        webView_.draw(c);

        return SaveImage(b);
    }

    public File SaveImage(@NonNull Bitmap b) {
        FileOutputStream fos;
        try {
            path = Environment.getExternalStorageDirectory().toString();
            dir = new File(path, "/Profit/struk/img/");
            if (!dir.isDirectory()) {
                dir.mkdirs();
            }
            String arquivo = "produk_" + System.currentTimeMillis() + ".jpg";
            file = new File(dir, arquivo);

            fos = new FileOutputStream(file);
            String imagePath = file.getAbsolutePath();
            //scan the image so show up in album
            MediaScannerConnection.scanFile(BaseActivity.this, new String[]{imagePath},
                    null, new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {

                        }
                    });
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * @param encodedString
     * @return bitmap (from given string)
     */
    @Nullable
    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @NonNull
    List<PromoProdukModel.Response_value> data = new ArrayList<>();
    @NonNull
    ArrayList<NotificationModel.Response_value> data_promo = new ArrayList<>();
    PromoProdukModel promoProdukModel;

    public void requestPopUpPromo(String produk) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestPopUpPromoProduk(produk));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.POPUP_PROMO_PRODUK, this);

    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        Timber.d("onSuccess on Base: " + actionCode + " " + response.toString());
        if (actionCode == ActionCode.POPUP_PROMO_PRODUK) {
            promoProdukModel = gson.fromJson(response.toString(), PromoProdukModel.class);
            data.clear();
            if (promoProdukModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                data.addAll(promoProdukModel.getResponse_value());
                final View view = View.inflate(getApplicationContext(), R.layout.popup_promo_produk, null);

                DialogUtils.openPromoDialog(this, view, promoProdukModel.getResponse_value());
            }
        } else if (actionCode == ActionCode.SLIDE_HOME) {
            Timber.d("onSuccess: " + response.toString());
        } else if (actionCode == ActionCode.CEK_SALDO) {
            Timber.d("onSuccess: ActionCode.CEK_SALDO" + response.toString());
            SaldoModel saldoModel = gson.fromJson(response.toString(), SaldoModel.class);
            if (saldoModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                PreferenceClass.putString("saldo", FormatString.CurencyIDR(saldoModel.getResponse_value()));
                navigationBottomCustom((RelativeLayout) findViewById(R.id.bottom_toolbar), PreferenceClass.getString("saldo", "0"));
            }
        } else if (actionCode == ActionCode.CEK_SALDO_FROM_NOTIF) {
            Timber.d("onSuccess: ActionCode.CEK_SALDO_FROM_NOTIF" + response.toString());
            SaldoModel saldoModel = gson.fromJson(response.toString(), SaldoModel.class);
            if (saldoModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                PreferenceClass.putString("saldo", FormatString.CurencyIDR(saldoModel.getResponse_value()));
                navigationBottomCustom((RelativeLayout) findViewById(R.id.bottom_toolbar), PreferenceClass.getString("saldo", "0"));
            }
        } else if (actionCode == ActionCode.UPDATE_LOCATION) {
            Timber.d("onSuccess: " + response.toString());
        }
    }

    int isMatrix = 0;

    @Override
    public void onSuccess(int actionCode, @NonNull String response) {
        Timber.d("onSuccess: " + response);
        isMatrix = 1;
        try {
            bitmapQRCode = FormatString.TextToImageEncodeMatrix(kodeBooking);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        strukTercetak = response.replace("*", "\n").replace("|", " ");
        if (initCetak == 1) {
            cetak(this.mTravelContext);
        }
    }

    @Override
    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {
    }

    @Override
    public void onUpdate(int actionCode, long bytesRead, long totalSize, boolean done) {
    }

    /**
     * This method new_popup_alert.
     *
     * @param title title
     * @param pesan pesan yang akan ditampikan
     */
    public void new_popup_alert(@NonNull Context context, String title, String pesan) {
        ViewGroup parent = findViewById(R.id.contentHost);
        @SuppressLint("InflateParams")
        View v = View.inflate(context, R.layout.dialog_header_response_layout, parent);

        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(context);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                .setBackgroundColor(ContextCompat.getColor(context, R.color.black_overlay_dark))
                .setTitle(title)
                .setMessage(pesan)
                .setTextGravity(Gravity.CENTER)
                .setHeaderView(v)
                .setCancelable(true);

        builder.addButton("Tutup", -1, ContextCompat.getColor(this, R.color.md_red_500), CFAlertDialog.CFAlertActionStyle.POSITIVE, getButtonGravity(3), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(@NonNull DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.onDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });

        if (context instanceof AppCompatActivity && !((AppCompatActivity) context).isFinishing()) {
            builder.show();
        }
    }

    public void new_popup_alert_session(@NonNull Context context, String title, String pesan) {
        ViewGroup parent = findViewById(R.id.contentHost);
        View v = View.inflate(context, R.layout.dialog_header_response_layout, parent);

        ImageView imageView = v.findViewById(R.id.imageViewNotif);
        imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_timing));
        final CFAlertDialog.Builder builder = new CFAlertDialog.Builder(context);

        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                .setBackgroundColor(ContextCompat.getColor(context, R.color.black_overlay_dark))
                .setTitle(title)
                .setMessage(pesan)
                .setTextGravity(Gravity.CENTER)
                .setHeaderView(v)
                .setCancelable(false);

        builder.addButton("Tutup", -1, ContextCompat.getColor(this, R.color.md_red_500), CFAlertDialog.CFAlertActionStyle.POSITIVE, getButtonGravity(3), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(@NonNull DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.onDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                requestLogout();
                PreferenceClass.setLogOut();
                Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
            }
        });

        if (context instanceof AppCompatActivity && !((AppCompatActivity) context).isFinishing()) {
            if (builder == null || !builder.create().isShowing()) {
                builder.show();
            }
        }
    }

    public void new_popup_alert_session_deposit(@NonNull Context context, String title, String pesan) {
        ViewGroup parent = findViewById(R.id.contentHost);
        View v = View.inflate(context, R.layout.dialog_header_response_layout, parent);

        ImageView imageView = v.findViewById(R.id.imageViewNotif);
        imageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.no_balance));
        final CFAlertDialog.Builder builder = new CFAlertDialog.Builder(context);

        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                .setBackgroundColor(ContextCompat.getColor(context, R.color.black_overlay_dark))
                .setTitle(title)
                .setMessage(pesan + "\n Silahkan melakukan deposit")
                .setTextGravity(Gravity.CENTER)
                .setHeaderView(v)
                .setCancelable(false);

        builder.addButton("Deposit", -1, ContextCompat.getColor(context, R.color.md_red_500), CFAlertDialog.CFAlertActionStyle.POSITIVE, getButtonGravity(3), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(@NonNull DialogInterface dialog, int which) {
                Intent intent = new Intent(BaseActivity.this, DepositActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                dialog.cancel();
            }
        });

        builder.addButton("Tutup", -1, ContextCompat.getColor(this, R.color.md_green_500), CFAlertDialog.CFAlertActionStyle.POSITIVE, getButtonGravity(3), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(@NonNull DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        if (context instanceof AppCompatActivity && !((AppCompatActivity) context).isFinishing()) {
            if (builder == null || !builder.create().isShowing()) {
                builder.show();
            }
        }
    }

    public void new_popup_alert_regitrasi(@NonNull Context context, String message) {
        ViewGroup parent = findViewById(R.id.contentHost);
        View v = View.inflate(context, R.layout.dialog_header_fmcg_response_layout, parent);

        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(context);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                .setBackgroundColor(ContextCompat.getColor(context, R.color.black_overlay_dark))
                .setTitle("Info")
                .setMessage(message)
                .setTextGravity(Gravity.CENTER)
                .setHeaderView(v)
                .setCancelable(true);

        builder.addButton("Tutup", -1, ContextCompat.getColor(this, R.color.md_red_500), CFAlertDialog.CFAlertActionStyle.POSITIVE, getButtonGravity(3), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(@NonNull DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.onDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
            }
        });

        if (context instanceof AppCompatActivity && !((AppCompatActivity) context).isFinishing()) {
            builder.show();
        }
    }

    public void new_popup_alertDemo(@NonNull final AppCompatActivity appCompatActivity, String title, String pesan) {
        ViewGroup parent = findViewById(R.id.contentHost);
        View v = View.inflate(appCompatActivity, R.layout.dialog_header_response_layout, parent);

        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(appCompatActivity);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                .setBackgroundColor(ContextCompat.getColor(appCompatActivity, R.color.black_overlay_dark))
                .setTitle(title)
                .setMessage(pesan)
                .setTextGravity(Gravity.CENTER)
                .setHeaderView(v)
                .setCancelable(true);

        builder.addButton("Tutup", -1, ContextCompat.getColor(this, R.color.md_red_500), CFAlertDialog.CFAlertActionStyle.POSITIVE, getButtonGravity(3), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(@NonNull DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.onDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                registerIfDemo(appCompatActivity);
            }
        });

        if (appCompatActivity instanceof AppCompatActivity && !((AppCompatActivity) appCompatActivity).isFinishing()) {
            builder.show();
        }
    }

    public void new_popup_alert_two_button(@NonNull final Context context, String title, String pesan, final File file, final LinearLayout webView, @NonNull final String isPilih) {
        ViewGroup parent = findViewById(R.id.contentHost);
        View v = View.inflate(context, R.layout.dialog_header_response_layout, parent);

        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(context);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                .setBackgroundColor(ContextCompat.getColor(context, R.color.black_overlay_dark))
                .setTitle(title)
                .setMessage(pesan)
                .setTextGravity(Gravity.CENTER)
                .setHeaderView(v)
                .setCancelable(true);

        builder.addButton("Buka", -1, ContextCompat.getColor(context, R.color.md_green_500), CFAlertDialog.CFAlertActionStyle.POSITIVE, getButtonGravity(3), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(@NonNull DialogInterface dialog, int which) {
                if (isPilih.equals("isPdf")) {
                    FileUtils.saveToPdf(context, webView);
                } else if (isPilih.equals("isImage")) {
                    FileUtils.openImage(BaseActivity.this, file);
                }
                dialog.cancel();
            }
        });

        builder.addButton("Tutup", -1, ContextCompat.getColor(context, R.color.md_red_500), CFAlertDialog.CFAlertActionStyle.POSITIVE, getButtonGravity(3), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(@NonNull DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        if (context instanceof AppCompatActivity && !((AppCompatActivity) context).isFinishing()) {
            builder.show();
        }

        closeBootomSheetDialog();
    }

    public void new_popup_alert_two_buttonPermision(@NonNull final AppCompatActivity appCompatActivity, String title, int pesan) {
        ViewGroup parent = findViewById(R.id.contentHost);
        View v = View.inflate(appCompatActivity, R.layout.dialog_header_response_layout, parent);

        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(appCompatActivity);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                .setBackgroundColor(ContextCompat.getColor(appCompatActivity, R.color.black_overlay_dark))
                .setTitle(title)
                .setMessage(Html.fromHtml(getString(pesan)))
                .setTextGravity(Gravity.CENTER)
                .setHeaderView(v)
                .setCancelable(false);

        builder.addButton("Buka Daftar Ijin", -1, ContextCompat.getColor(appCompatActivity, R.color.md_green_500), CFAlertDialog.CFAlertActionStyle.POSITIVE, getButtonGravity(3), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(@NonNull DialogInterface dialog, int which) {
                Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
                myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(myAppSettings, RequestCode.ActionCode_SETTING);
                dialog.cancel();
            }
        });

        if (appCompatActivity instanceof AppCompatActivity && !((AppCompatActivity) appCompatActivity).isFinishing()) {
            builder.show();
        }
    }

    private void new_popup_alert_two_buttonPermisionGPS(@NonNull final Context context, String title, int pesan) {
        ViewGroup parent = findViewById(R.id.contentHost);
        View v = View.inflate(context, R.layout.dialog_header_response_layout, parent);

        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(context);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                .setBackgroundColor(ContextCompat.getColor(context, R.color.black_overlay_dark))
                .setTitle(title)
                .setMessage(Html.fromHtml(getString(pesan)))
                .setTextGravity(Gravity.CENTER)
                .setHeaderView(v)
                .setCancelable(false);

        builder.addButton("Buka Daftar Ijin", -1, ContextCompat.getColor(context, R.color.md_green_500), CFAlertDialog.CFAlertActionStyle.POSITIVE, getButtonGravity(3), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(@NonNull DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                dialog.cancel();
            }
        });

        if (context instanceof AppCompatActivity && !((AppCompatActivity) context).isFinishing()) {
            builder.show();
        }
    }

    public void new_popup_alert_failure(@NonNull Context context, String responseDescription) {
        ViewGroup parent = findViewById(R.id.contentHost);
        View v = View.inflate(context, R.layout.dialog_header_failure_layout, parent);

        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(context);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                .setBackgroundColor(ContextCompat.getColor(context, R.color.black_overlay_dark))
                .setTitle("Informasi")
                .setMessage(responseDescription)
                .setTextGravity(Gravity.CENTER)
                .setHeaderView(v)
                .setCancelable(true);

        builder.addButton("Tutup", -1, ContextCompat.getColor(this, R.color.md_red_500), CFAlertDialog.CFAlertActionStyle.POSITIVE, getButtonGravity(3), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(@NonNull DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        if (context instanceof AppCompatActivity && !((AppCompatActivity) context).isFinishing()) {
            builder.show();
        }
    }

    public void new_popup_alert_failure_pay(@NonNull Context context, String responseDescription) {
        ViewGroup parent = findViewById(R.id.contentHost);
        View v = View.inflate(context, R.layout.dialog_header_fail_pay_layout, parent);

        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(context);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                .setBackgroundColor(ContextCompat.getColor(context, R.color.black_overlay_dark))
                .setTitle("Informasi")
                .setMessage(responseDescription)
                .setTextGravity(Gravity.CENTER)
                .setHeaderView(v)
                .setCancelable(true);

        builder.addButton("Tutup", -1, ContextCompat.getColor(context, R.color.md_red_500), CFAlertDialog.CFAlertActionStyle.POSITIVE, getButtonGravity(3), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(@NonNull DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.onDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
            }
        });

        if (context instanceof AppCompatActivity && !((AppCompatActivity) context).isFinishing()) {
            builder.show();
        }
    }

    public void showCaseFirst(Context context, String title, String description, View viewFirst) {
        mGbuilder = new GuideView.Builder(context)
                .setTitle(title)
                .setContentText(description)
                .setGravity(GuideView.Gravity.center)
                .setDismissType(GuideView.DismissType.anywhere)
                .setTargetView(viewFirst);
    }

    /**
     * Release memory when the UI becomes hidden or when system resources become low.
     *
     * @param level the memory-related event that was raised.
     */
    public void onTrimMemory(int level) {

        // Determine which lifecycle or system event was raised.
        switch (level) {

            case ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN:
                Timber.d("onTrimMemory: TRIM_MEMORY_UI_HIDDEN");
                /*
                   Release any UI objects that currently hold memory.

                   The user interface has moved to the background.
                */
                break;

            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE:
                Timber.d("onTrimMemory: TRIM_MEMORY_RUNNING_MODERATE");
            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW:
                Timber.d("onTrimMemory: TRIM_MEMORY_RUNNING_LOW");
                Runtime.getRuntime().gc();

            case ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL:
                Timber.d("onTrimMemory: TRIM_MEMORY_RUNNING_CRITICAL");
                Runtime.getRuntime().gc();
                /*
                   Release any memory that your app doesn't need to run.

                   The device is running low on memory while the app is running.
                   The event raised indicates the severity of the memory-related event.
                   If the event is TRIM_MEMORY_RUNNING_CRITICAL, then the system will
                   begin killing background processes.
                */
                break;

            case ComponentCallbacks2.TRIM_MEMORY_BACKGROUND:
                Timber.d("onTrimMemory: TRIM_MEMORY_BACKGROUND");
            case ComponentCallbacks2.TRIM_MEMORY_MODERATE:
                Timber.d("onTrimMemory: TRIM_MEMORY_MODERATE");
            case ComponentCallbacks2.TRIM_MEMORY_COMPLETE:
                Timber.d("onTrimMemory: TRIM_MEMORY_COMPLETE");
                /*
                   Release as much memory as the process can.

                   The app is on the LRU list and the system is running low on memory.
                   The event raised indicates where the app sits within the LRU list.
                   If the event is TRIM_MEMORY_COMPLETE, the process will be one of
                   the first to be terminated.
                */

                break;

            default:
                Timber.d("onTrimMemory: default");
                /*
                  Release any non-critical data structures.

                  The app received an unrecognized memory level value
                  from the system. Treat this as a generic low-memory message.
                */
                break;
        }
    }

    // Get a MemoryInfo object for the device's current memory status.
    @NonNull
    private ActivityManager.MemoryInfo getAvailableMemory() {
        ActivityManager activityManager = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        assert activityManager != null;
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo;
    }


    private void registerIfDemo(@NonNull AppCompatActivity appCompatActivity) {
        requestLogout();
        PreferenceClass.setLogOut();

        Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        intent.putExtra("isDemo", 1);
        startActivity(intent);
        appCompatActivity.finish();
    }

    @NonNull
    public CustomTabsIntent buildCustomTabsIntent() {
        CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder();

        // Show the title
        intentBuilder.setShowTitle(false);
        intentBuilder.enableUrlBarHiding();

        // Set the color of Toolbar
        intentBuilder.setToolbarColor(R.color.colorPrimary_ppob);
        intentBuilder.setSecondaryToolbarColor(R.color.colorPrimary_ppob);

        // Display custom back button
        intentBuilder.setCloseButtonIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_back));

        intentBuilder.setStartAnimations(this, 0, 0);
        intentBuilder.setExitAnimations(this, 0, 0);

        return intentBuilder.build();
    }

    long minTime = 1000;
    long minDistance = 0;

    private void GPSPermision() {
        if (ContextCompat.checkSelfPermission(BaseActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            grantPermissions(RequestCode.ActionCode_ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION);
            return;
        }

        startServiceGPSSBF();
    }

    private void startServiceGPSSBF() {
        getMyLocation();
    }

    public void doUpdateLocation(String key) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.update_long_lat(key, PreferenceClass.getString("lat", "0.0"), PreferenceClass.getString("long", "0.0")));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponseUpdateLocation(this, jsonObject, ActionCode.UPDATE_LOCATION, this);
    }

    /**
     * This method logEventFireBase.
     *
     * @param itemCategory FirebaseAnalytics.Param.ITEM_CATEGORY
     * @param itemName     FirebaseAnalytics.Param.ITEM_NAME
     * @param action       FirebaseAnalytics.Param.CONTENT_TYPE
     * @param success      FirebaseAnalytics.Param.SUCCESS
     * @param tag          ClassName
     */
    public void logEventFireBase(String itemCategory, String itemName, String action, String success, String tag) {
        Bundle bundle = new Bundle();
        if (PreferenceClass.getUser() != null) {
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, PreferenceClass.getUser().getId_outlet());
        } else {
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, PreferenceClass.getId());
        }
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, itemName);
        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, itemCategory);
        bundle.putString(FirebaseAnalytics.Param.SOURCE, tag);
        bundle.putString(FirebaseAnalytics.Param.SUCCESS, success);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, action);
        SBFApplication.sendEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        SBFApplication.sendToAnalylic(itemCategory, itemName, action, tag);
    }

    public void logEventFireBase(String itemCategory, String itemName, String action, String event, String success, String tag) {
        Bundle bundle = new Bundle();
        if (PreferenceClass.getUser() != null) {
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, PreferenceClass.getUser().getId_outlet());
        } else {
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, PreferenceClass.getId());
        }
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, itemName);
        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, itemCategory);
        bundle.putString(FirebaseAnalytics.Param.SOURCE, tag);
        bundle.putString(FirebaseAnalytics.Param.SUCCESS, success);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, action);
        SBFApplication.sendEvent(event, bundle);
        SBFApplication.sendToAnalylic(itemCategory, itemName, action, tag);
    }

    public void logEventFireBase(String itemCategory, String itemName, String origin, String destination, String monthPeriode, String action, String success, String tag) {
        Bundle bundle = new Bundle();
        if (PreferenceClass.getUser() != null) {
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, PreferenceClass.getUser().getId_outlet());
        } else {
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, PreferenceClass.getId());
        }
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, itemName);
        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, itemCategory);
        bundle.putString(FirebaseAnalytics.Param.SOURCE, tag);
        bundle.putString(FirebaseAnalytics.Param.SUCCESS, success);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, action);
        bundle.putString(FirebaseAnalytics.Param.ORIGIN, origin);
        bundle.putString(FirebaseAnalytics.Param.DESTINATION, destination);

        bundle.putString(FirebaseAnalytics.Param.START_DATE, monthPeriode);

        // mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        //   mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        SBFApplication.sendEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        // Log.d(TAG, "logEventFireBase: "+mFirebaseAnalytics.getFirebaseInstanceId()+" "+mFirebaseAnalytics.getAppInstanceId());
        SBFApplication.sendToAnalylic(itemCategory, itemName, action, tag);

    }
    public void logEventFireBase(String itemCategory, String itemName, @NonNull String price, String origin, String destination, String flightNumber, String startDate, String isTransit, String action, String success, String tag) {
        Bundle bundle = new Bundle();
        Product product = new Product();
        product.setCategory(itemCategory);
        product.setName(itemName);
        product.setPrice(Double.parseDouble(price));

        if (PreferenceClass.getUser() != null) {
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, PreferenceClass.getUser().getId_outlet());
            product.setId(PreferenceClass.getUser().getId_outlet());
        } else {
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, PreferenceClass.getId());
            product.setId(PreferenceClass.getId());
        }
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, itemName);
        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, itemCategory);
        bundle.putString(FirebaseAnalytics.Param.SOURCE, tag);
        bundle.putString(FirebaseAnalytics.Param.SUCCESS, success);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, action);
//        bundle.putString(FirebaseAnalytics.Param.CONTENT, content);
        bundle.putString(FirebaseAnalytics.Param.ORIGIN, origin);
        bundle.putString(FirebaseAnalytics.Param.DESTINATION, destination);
        bundle.putString(FirebaseAnalytics.Param.PRICE, price);
        bundle.putString(FirebaseAnalytics.Param.FLIGHT_NUMBER, flightNumber);
        bundle.putString(FirebaseAnalytics.Param.START_DATE, startDate);
        bundle.putString("IS_TRANSIT", isTransit);

        // mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        //   mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        SBFApplication.sendEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        // Log.d(TAG, "logEventFireBase: "+mFirebaseAnalytics.getFirebaseInstanceId()+" "+mFirebaseAnalytics.getAppInstanceId());

        SBFApplication.sendToAnalylic(itemCategory, itemName, product, action, tag);

    }

    public void logEventFireBase(String itemCategory, String itemName, String price, String origin, String destination, String flightNumber, String startDate, String isTransit, String bookCode, String action, String success, String tag) {
        Bundle bundle = new Bundle();
        if (PreferenceClass.getUser() != null) {
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, PreferenceClass.getUser().getId_outlet());
        } else {
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, PreferenceClass.getId());
        }
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, itemName);
        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, itemCategory);
        bundle.putString(FirebaseAnalytics.Param.SOURCE, tag);
        bundle.putString(FirebaseAnalytics.Param.SUCCESS, success);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, action);
//        bundle.putString(FirebaseAnalytics.Param.CONTENT, content);
        bundle.putString(FirebaseAnalytics.Param.ORIGIN, origin);
        bundle.putString(FirebaseAnalytics.Param.DESTINATION, destination);
        bundle.putString(FirebaseAnalytics.Param.PRICE, price);
        bundle.putString(FirebaseAnalytics.Param.FLIGHT_NUMBER, flightNumber);
        bundle.putString(FirebaseAnalytics.Param.START_DATE, startDate);
        bundle.putString("is_transit", isTransit);
        bundle.putString("book_code", bookCode);

        // mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        //   mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        SBFApplication.sendEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        // Log.d(TAG, "logEventFireBase: "+mFirebaseAnalytics.getFirebaseInstanceId()+" "+mFirebaseAnalytics.getAppInstanceId());
        SBFApplication.sendToAnalylic(itemCategory, itemName, action, tag);

    }
    private synchronized void setUpGClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(BaseActivity.this, 0, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        GPSPermision();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Timber.d("onConnectionFailed: " + connectionResult.toString());
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        mylocation = location;

        if (mylocation != null) {
            Double latitude = mylocation.getLatitude();
            Double longitude = mylocation.getLongitude();

            String lat = String.valueOf(location.getLatitude());
            String lng = String.valueOf(location.getLongitude());
            PreferenceClass.putString("lat", String.valueOf(latitude));
            PreferenceClass.putString("long", String.valueOf(longitude));

            PreferenceClass.putString("location", String.valueOf(latitude) + "," + String.valueOf(longitude));

            /*------- To get city airLineName from coordinates -------- */
            String cityName = "";
            String placeName = "";

            String adminArea = "";
            String thoroughFare = "";
            Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
            List<Address> addresses;
            try {
                addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                if (addresses.size() > 0) {
                    cityName = addresses.get(0).getLocality();
                    placeName = addresses.get(0).getAddressLine(0);
                    adminArea = addresses.get(0).getAdminArea();
                    thoroughFare = addresses.get(0).getThoroughfare();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String s = lng + "\n" + lat + "\n\nMy Current City is: "
                    + placeName + " " + adminArea + " " + thoroughFare;
            if (placeName != null) {
                String place = placeName.replaceAll(",", "-");
                PreferenceClass.putString("place", place);
            }

            mylocation.set(location);
        }
    }

    private void getMyLocation() {
        if (googleApiClient != null) {
            if (googleApiClient.isConnected()) {
                int permissionLocation = ContextCompat.checkSelfPermission(BaseActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
                if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                    mylocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    LocationRequest locationRequest = new LocationRequest();
                    locationRequest.setInterval(5000);
                    locationRequest.setFastestInterval(5000);
                    locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                            .addLocationRequest(locationRequest);
                    builder.setAlwaysShow(true);
                    LocationServices.FusedLocationApi
                            .requestLocationUpdates(googleApiClient, locationRequest, this);
                    PendingResult<LocationSettingsResult> result =
                            LocationServices.SettingsApi
                                    .checkLocationSettings(googleApiClient, builder.build());
                    result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

                        @Override
                        public void onResult(@NonNull LocationSettingsResult result) {
                            final Status status = result.getStatus();
                            switch (status.getStatusCode()) {
                                case LocationSettingsStatusCodes.SUCCESS:
                                    // All location settings are satisfied.
                                    // You can initialize location requests here.
                                    int permissionLocation = ContextCompat
                                            .checkSelfPermission(BaseActivity.this,
                                                    Manifest.permission.ACCESS_FINE_LOCATION);
                                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                                        mylocation = LocationServices.FusedLocationApi
                                                .getLastLocation(googleApiClient);
                                    }
                                    break;
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                    // Location settings are not satisfied.
                                    // But could be fixed by showing the user a dialog.
                                    try {
                                        // Show the dialog by calling startResolutionForResult(),
                                        // and check the result in onActivityResult().
                                        // Ask to turn on GPS automatically
                                        status.startResolutionForResult(BaseActivity.this,
                                                RequestCode.REQUEST_CHECK_SETTINGS_GPS);
                                    } catch (IntentSender.SendIntentException e) {
                                    }


                                    break;
                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    // Location settings are not satisfied.
                                    // However, we have no way
                                    // to fix the
                                    // settings so we won't show the dialog.
                                    break;
                            }
                        }
                    });
                }
            }
        }
    }

    int initCetak;
    public String kodeBooking = "";
    Context mTravelContext;

    public void getStruk(String url_struk, int i, Context mTravelContext) {
        Timber.d("getStruk: " + url_struk);
        this.mTravelContext = mTravelContext;
        initCetak = i;
        RequestUtilsTravel.transportWithJSONObjectResponseGet(this, url_struk, TravelActionCode.STRUK, this);
    }

    @NonNull
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, @NonNull Intent intent) {
            Timber.d("onReceive: cek saldo ");
            if (!FCMConstants.isStillRunningRequest) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject = new JSONObject(stringJson.requestCekSaldo());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RequestUtils.transportWithProgressResponse(BaseActivity.this, jsonObject, ActionCode.CEK_SALDO_FROM_NOTIF, BaseActivity.this);
            }
        }
    };

    public void openScannerKey(AppCompatActivity context, BarcodeListener barcodeListener) {

        this.barcodeListener=barcodeListener;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            grantPermissions(RequestCode.ActionCode_CAMERA, Manifest.permission.CAMERA);
            return;
        }

        MaterialBarcodeScanner   materialBarcodeScanner = new MaterialBarcodeScannerBuilder()
                .withActivity(this)
                .withBarcodeFormats(Barcode.QR_CODE)
                .withEnableAutoFocus(true)
                .withBleepEnabled(true)
                .withBackfacingCamera()
                .withCenterTracker()
                .withText("Scanning...")

                .withResultListener(new MaterialBarcodeScanner.OnResultListener() {
                    @Override
                    public void onResult(Barcode barcode) {
                        barcodeListener.onResultBarcode(barcode);

                    }
                })
                .build();
        materialBarcodeScanner.startScan();

    }

    public void startScan(AppCompatActivity activity, BarcodeListener barcodeListener) {
        /**
         * Build a new MaterialBarcodeScanner
         */
        this.barcodeListener=barcodeListener;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            grantPermissions(RequestCode.ActionCode_CAMERA, Manifest.permission.CAMERA);
            return;
        }



        MaterialBarcodeScanner materialBarcodeScanner = new MaterialBarcodeScannerBuilder()
                .withActivity(activity)
                .withEnableAutoFocus(true)
                .withBarcodeFormats(Barcode.ALL_FORMATS)
                .withBleepEnabled(true)
                .withBackfacingCamera()
                .withCenterTracker()
                .withText("Scanning...").withTrackerColor(R.color.md_green_500)
                .withResultListener(new MaterialBarcodeScanner.OnResultListener() {
                    @Override
                    public void onResult(Barcode barcode) {
                        //   barcodeResult = barcode;
                        barcodeListener.onResultBarcode(barcode);
                        // result.setText(barcode.rawValue);
                    }
                })
                .build();
        materialBarcodeScanner.startScan();

    }
    BarcodeListener barcodeListener;


    public void openSendVia(@NonNull View v, final Uri parse, @NonNull final String finalproduk, final String finalnamaProduk, final String finalkode, final String finalnama, @NonNull final File file) {
        final int id = v.getId();
//        final View view = getLayoutInflater().inflate(R.layout.travel_layout_action_share, null);
        final View view = View.inflate(getApplicationContext(), R.layout.travel_layout_action_share, null);
        openBottomSheetDialog(this, view);
        String pesanBody = "";
        if (finalproduk.equals("Pesawat")) {
            pesanBody = "Silakan download eTicket " + finalproduk + " " + finalnamaProduk + " Anda dalam format PDF. Mohon diperhatikan Anda akan diminta untuk membawa hasil cetak eTicket Anda untuk masuk ke bandara dan untuk check-in bersama dengan membawa kartu identitas yang masih berlaku dan sesuai dengan data Anda.<br />Kami ucapkan terima kasih telah menggunakan layanan travel di Fastpay.<br /><br />";
        } else if (finalproduk.equals("Kereta")) {
            pesanBody = "Silakan download eTicket Anda dalam format PDF. Diharapkan Anda tiba 1 (satu) jam sebelum keberangkatan dengan membawa eTicket ini untuk mencetak Tiket Penumpang Kereta Api di mesin cetak yang ada di stasiun. Anda WAJIB membawa kartu identitas yang masih berlaku dan sesuai dengan data Anda untuk melakukan check in.<br />Kami ucapkan terima kasih telah menggunakan layanan travel di Fastpay.<br /><br />";
        } else if (finalproduk.equals("Pelni")) {
            pesanBody = "Silakan download eTicket Anda dalam format PDF. Bawalah eTicket ini untuk mencetak Tiket Penumpang PELNI di kantor perwakilan Pelni terdekat. Anda juga WAJIB membawa kartu identitas yang masih berlaku dan sesuai dengan data Anda untuk melakukan check in.<br />Kami ucapkan terima kasih telah menggunakan layanan travel di Fastpay.<br /><br />";
        } else if (finalproduk.equals("Paket Wisata")) {
            pesanBody = "Silakan download eTicket Anda dalam format PDF. Bawalah eTicket ini untuk ditunjukkan pada tim wisata kami saat Anda tiba di lokasi. Anda juga WAJIB membawa kartu identitas yang masih berlaku dan sesuai dengan data Anda untuk verifikasi.<br />Kami ucapkan terima kasih telah menggunakan layanan travel di Fastpay.<br /><br />";
        } else if (finalproduk.equals("Hotel")) {
            pesanBody = "Silakan download eTicket Anda dalam format PDF. Bawalah eTicket ini saat melakukan reservasi hotel. Anda juga WAJIB membawa kartu identitas yang masih berlaku dan sesuai dengan data Anda untuk melakukan check in.<br />Kami ucapkan terima kasih telah menggunakan layanan travel di Fastpay.<br /><br />";
        }

        final String finalPesanBody = pesanBody;
        ImageView imageViewWa = view.findViewById(R.id.imageViewWa);

        imageViewWa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String apps = "com.whatsapp";


                boolean installed = checkAppInstall(apps);
                if (!installed) {
                    apps = "com.whatsapp.w4b";
                }
                installed = checkAppInstall(apps);
                if (installed) {
                    openWhatsApp(apps, parse, file);
                } else {

                    Toast.makeText(getApplicationContext(),
                            "Aplikasi Whatsapp tidak ditemukan, Silahkan Install aplikasi Whatsapp terlebih dahulu", Toast.LENGTH_LONG).show();

                }


            }
        });
        ImageView imageViewGmail = view.findViewById(R.id.imageViewGmail);

        imageViewGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String apps = "com.google.android.gm";

                boolean installed = checkAppInstall(apps);
                if (installed) {
                    String subject = "E-ticket " + finalproduk + " " + finalnamaProduk + " (" + finalkode + ") - " + finalnama;
                    String pesanHeader = "Dengan hormat " + finalnama + ",<br /><br />";

                    String pesanFooter = "Hormat kami";
                    Spanned pesan = fromHtml("<html>" + pesanHeader + finalPesanBody + pesanFooter + "</html>");
                    //  String pesan= "<html>"+pesanHeader+pesanBody+pesanFooter+"</html>";
                    sendGmail(apps, subject, pesan, parse);
                } else {

                    Toast.makeText(getApplicationContext(),
                            "Aplikasi Gmail tidak ditemukan, Silahkan Install aplikasi Gmail terlebih dahulu", Toast.LENGTH_LONG).show();

                }


            }
        });
        ImageView imageViewYmail = view.findViewById(R.id.imageViewYmail);
        imageViewYmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String apps = "com.yahoo.mobile.client.android.mail";
                boolean installed = checkAppInstall(apps);
                if (installed) {
//                    String subject="E-ticket Lion Air (XXXXXX) - Tuan Adi Gunawan";
//                    String pesanHeader="Dengan hormat Tuan Adi Gunawan,<br>";
////                    String pesanBody="Silakan download eTicket Lion Air Anda dalam format PDF. Mohon diperhatikan Anda akan diminta untuk membawa hasil cetak eTicket Anda untuk masuk ke bandara dan untuk check-in bersama dengan membawa kartu identitas yang masih berlaku dan sesuai dengan data Anda.<br>Kami ucapkan terima kasih telah menggunakan layanan travel di Fastpay.<br>";
//                    String pesanFooter="Hormat kami";
//                    Spanned pesan= fromHtml("<html>"+pesanHeader+finalPesanBody+pesanFooter+"</html>");
                    String subject = "E-ticket " + finalproduk + " " + finalnamaProduk + " (" + finalkode + ") - " + finalnama;
                    String pesanHeader = "Dengan hormat " + finalnama + ",<br /><br />";

                    String pesanFooter = "Hormat kami";
                    Spanned pesan = fromHtml("<html>" + pesanHeader + finalPesanBody + pesanFooter + "</html>");
                    sendYmail(apps, subject, pesan, parse);
                } else {

                    Toast.makeText(getApplicationContext(),
                            "Aplikasi Yahoo Mail tidak ditemukan, Silahkan Install aplikasi Yahoo Mail terlebih dahulu", Toast.LENGTH_LONG).show();

                }

            }
        });
        ImageView imageViewEmail = view.findViewById(R.id.imageViewEmail);
        imageViewEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String apps = "com.android.email";


                boolean installed = checkAppInstall(apps);
                if (!installed) {
                    apps = "com.lge.email";
                }

                if (installed) {
//                    String subject="E-ticket Lion Air (XXXXXX) - Tuan Adi Gunawan";
//                    String pesanHeader="Dengan hormat Tuan Adi Gunawan,<br>";
////                    String pesanBody="Silakan download eTicket Lion Air Anda dalam format PDF. Mohon diperhatikan Anda akan diminta untuk membawa hasil cetak eTicket Anda untuk masuk ke bandara dan untuk check-in bersama dengan membawa kartu identitas yang masih berlaku dan sesuai dengan data Anda.<br>Kami ucapkan terima kasih telah menggunakan layanan travel di Fastpay.<br>";
//                    String pesanFooter="Hormat kami";
//                    Spanned pesan= fromHtml("<html>"+pesanHeader+finalPesanBody+pesanFooter+"</html>");
                    String subject = "E-ticket " + finalproduk + " " + finalnamaProduk + " (" + finalkode + ") - " + finalnama;
                    String pesanHeader = "Dengan hormat " + finalnama + ",<br /><br />";

                    String pesanFooter = "Hormat kami";
                    Spanned pesan = fromHtml("<html>" + pesanHeader + finalPesanBody + pesanFooter + "</html>");
                    sendEmail(apps, subject, pesan, parse);
                } else {

                    Toast.makeText(getApplicationContext(),
                            "Aplikasi EMail tidak ditemukan, Silahkan Install aplikasi EMail terlebih dahulu", Toast.LENGTH_LONG).show();

                }

            }
        });


    }
    public boolean checkAppInstall(String uri) {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG, "checkAppInstall: " + e.toString());
        }

        return false;
    }

    protected void openWhatsApp(String apps, Uri url, @NonNull File file) {

        try {
//            Uri fileUri = FileProvider.getUriForFile(
//                    this,
//                    "com.bm.main.fpl.fileprovider",
//                    file);
//            Log.d(TAG, "openWhatsApp: "+fileUri.toString());

            Intent share = new Intent();

            share.setAction(Intent.ACTION_SEND);

            share.setType("application/pdf");

//            share.setDataAndType(fileUri,getContentResolver().getType(fileUri));
//            share.putExtra(Intent.EXTRA_STREAM, fileUri);
//            share.addFlags(FLAG_GRANT_READ_URI_PERMISSION|FLAG_GRANT_WRITE_URI_PERMISSION);
            // share.setDataAndType(setType("image/png");)
            share.setPackage(apps);
            share.putExtra(Intent.EXTRA_STREAM, url);
            startActivity(share);
        } catch (IllegalArgumentException e) {
            Log.d("File Selector",
                    "The selected file can't be shared: " + e.toString() + " " + file.toString());
        }

        closeBootomSheetDialog();

    }

    protected void sendGmail(String apps, String subject, Spanned textPesan, Uri uri) {
        Log.i("Send email", "");
        String[] TO = {""};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, "");
//        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, textPesan);
        emailIntent.setType("application/pdf");
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
//            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            emailIntent.setPackage(apps);
            startActivity(emailIntent);
            //   finish();
            Log.i(TAG, "Finished sending email...");
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(BaseActivity.this, "There is no Gmail client installed.", Toast.LENGTH_SHORT).show();
        }
        closeBootomSheetDialog();
    }


    protected void sendYmail(String apps, String subject, Spanned textPesan, Uri uri) {
        Log.i("Send email", "");
        String[] TO = {""};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/html");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
//        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, textPesan);
        emailIntent.setType("application/pdf");
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
//            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            emailIntent.setPackage(apps);
            startActivity(emailIntent);
            //   finish();
            Log.i(TAG, "Finished sending email...");
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(BaseActivity.this, "There is no Yahoo Mail client installed.", Toast.LENGTH_SHORT).show();
        }
        closeBootomSheetDialog();
    }

    protected void sendEmail(String apps, String subject, Spanned textPesan, Uri uri) {
        Log.i("Send email", "");
        String[] TO = {""};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/html");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
//        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, textPesan);
        emailIntent.setType("application/pdf");
        emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
//            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            emailIntent.setPackage(apps);
            startActivity(emailIntent);
            //   finish();
            Log.i(TAG, "Finished sending email...");
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(BaseActivity.this, "There is no Email client installed.", Toast.LENGTH_SHORT).show();
        }
        closeBootomSheetDialog();
    }
    public void getar() {
        if (Build.VERSION.SDK_INT >= 26) {
            ((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(150, 10));
        } else {
            ((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(150);
        }
    }
}

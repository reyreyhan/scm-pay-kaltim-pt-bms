package com.bm.main.fpl.activities;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.text.method.HideReturnsTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.models.BaseObject;
import com.bm.main.fpl.models.CheckUpdateModel;
import com.bm.main.fpl.models.DemoModel;
import com.bm.main.fpl.models.SignOn;
import com.bm.main.fpl.models.UserModel;
import com.bm.main.fpl.templates.AsteriskPasswordTransformationMethod;
import com.bm.main.fpl.templates.PasswordEditText;
import com.bm.main.fpl.templates.indicators.AVLoadingIndicatorView;
import com.bm.main.fpl.templates.toast.MyDynamicToast;
import com.bm.main.fpl.utils.DetectConnection;
import com.bm.main.fpl.utils.Device;
import com.bm.main.fpl.utils.FormatString;
import com.bm.main.fpl.utils.MobileAES;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;
import com.bm.main.fpl.utils.StringJson;
import com.bm.main.fpl.utils.saring_karakter;
import com.bm.main.materialedittext.MaterialEditText;
import com.bm.main.scm.R;
import com.bm.main.scm.SBFApplication;
import com.bm.main.scm.feature.newhome.NewHomeActivity;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

import timber.log.Timber;

//import android.support.design.widget.CoordinatorLayout;
//import com.bumptech.glide.load.resource.drawable.GlideDrawable;
//import com.bumptech.glide.request.animation.GlideAnimation;

public class LoginActivity extends KeyboardListenerActivity implements ProgressResponseCallback {
    private static final String TAG = LoginActivity.class.getSimpleName();

    /**
     * New Login Layout activity_login_pos
     */

    EditText editTextUser;
    EditText editTextPassword;
    Button btnLogin;
    CheckBox btnShowPassword;
    TextView btnBantuan;
    View btnRegister;

    TextView textViewDaftar;
    TextView textViewPlusInfo, text_lupa_password;
    AppCompatButton appCompatButtonLogin;
    MaterialEditText materialEditTextUserId;
    MaterialEditText materialEditTextKey;
    PasswordEditText materialEditTextPassword;

    @Nullable
    String keyUser;
    Context context;

    @Nullable
    String keyx = "";
    boolean isDemo = false;

    RelativeLayout layout_update_apps;
    AppCompatButton button_nanti, button_perbaharui, button_no, button_yes;
    TextView textViewMessage;
    LinearLayout linUrgentYes, linUrgentNo, linDemo;

    TextView demo;

    @Nullable
    private String token;

    AVLoadingIndicatorView aviLoading;
    private boolean activeSession = true;
    private boolean allowLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pos);
        logEventFireBase("Login", "Login", EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, TAG);
        context = this;

        if (this.getIntent() != null) {
            if (this.getIntent().getIntExtra("isDemo", 0) == 1) {
                Intent intent = new Intent(this, RegistrasiActivity.class);
                startActivity(intent);
            }
        }
        keyUser = PreferenceClass.getKey();
        aviLoading = findViewById(R.id.aviLoading);
        /*
        layout_update_apps = findViewById(R.id.layout_update_apps);
        demo = findViewById(R.id.textViewDemo);
        String text = "<font color=#1565C0>Coba Aplikasi </font> <font color=#2196F3>Demo</font>";
        demo.setText(Html.fromHtml(text));
        linUrgentYes = findViewById(R.id.linUrgentYes);
        linUrgentNo = findViewById(R.id.linUrgentNo);
        linDemo = findViewById(R.id.linDemo);

        demo.setOnClickListener(v -> requestLoginDemo());
        button_nanti = findViewById(R.id.button_nanti);
        button_nanti.setOnClickListener(v -> layout_update_apps.setVisibility(View.GONE));
        button_perbaharui = findViewById(R.id.button_perbaharui);
        button_perbaharui.setOnClickListener(v -> {
            String apps = "com.android.chrome";
            boolean installed = Device.checkAppInstall(v.getContext(), apps);
            if (installed) {
                update_app();
                finishAffinity();
            } else {
                Toast.makeText(context, "Aplikasi Google Chrome tidak di temukan, Silahkan Intall Google Chrome terlebih dahulu", Toast.LENGTH_LONG).show();
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + apps));
                startActivity(webIntent);
            }
        });
        button_no = findViewById(R.id.button_no);
        button_no.setOnClickListener(v -> finishAffinity());
        button_yes = findViewById(R.id.button_yes);
        button_yes.setOnClickListener(v -> {
            String apps = "com.android.chrome";
            boolean installed = Device.checkAppInstall(v.getContext(), apps);
            if (installed) {
                update_app();
                finishAffinity();
            } else {
                Toast.makeText(context, "Aplikasi Google Chrome tidak di temukan, Silahkan Intall Google Chrome terlebih dahulu", Toast.LENGTH_LONG).show();
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + apps));
                startActivity(webIntent);
            }
        });
        textViewMessage = findViewById(R.id.textViewMessage);

        textViewPlusInfo = findViewById(R.id.textViewPlusInfo);
        textViewPlusInfo.setText(FormatString.htmlString(getString(R.string.string_info_login)));

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> {
            ViewGroup parent = findViewById(R.id.contentHost);
            final FrameLayout view = (FrameLayout) View.inflate(this, R.layout.loading_bar_full_dialog, parent);
            TextView tv = view.findViewById(R.id.textContentProgressBar);
            tv.setText("Mohon Tunggu, sedang di proses");
            openProgressBarDialog(LoginActivity.this, view);

            readInboxGranted();
//            doLogin();
            Device.vibrate(LoginActivity.this);
        });

        textViewDaftar = findViewById(R.id.textViewDaftar);
        String textDemo = "<font color=#1565C0>Belum memiliki akun? </font> <font color=#2196F3>Daftar Sekarang </font>";
        textViewDaftar.setText(Html.fromHtml(textDemo));
        textViewDaftar.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistrasiActivity.class);
            startActivity(intent);
            Device.vibrate(LoginActivity.this);
        });
*/
        /**
         * Edit Text Material OLD
         */
        /*
        editTextUser = findViewById(R.id.editTextUser);

        editTextUser.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                checkRequestStatus();
            }
        });

        InputFilter[] filter = new saring_karakter().DisableSpecialCharactersAllCaps(20);
        editTextUser.setFilters(filter);
        editTextPassword = findViewById(R.id.editTextPassword);
        materialEditTextKey = findViewById(R.id.materialEditTextKey);
        editTextPassword.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        editTextPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && editTextUser.getText() != null) {
                if (editTextUser.getText().length() == 0 || editTextUser.getText().toString().isEmpty()) {
                    editTextUser.setFocusable(true);
                    editTextUser.setFocusableInTouchMode(true);
                    editTextUser.requestFocus();
                }
            }
        });
        materialEditTextKey.setOnClickListener(v -> readInboxGranted());

        text_lupa_password = findViewById(R.id.textViewPlusLupaPassword);
        text_lupa_password.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, LupaPinActivity.class).putExtra("flag", 1);
            startActivity(intent);
        });

        if (recent_version_code < Integer.parseInt(PreferenceClass.getString(RConfig.MIN_VERSION_CODE, "1"))) {
            layout_update_apps.setVisibility(View.VISIBLE);
            if (PreferenceClass.getString(RConfig.FORCE_UPDATE, "no").equals("yes")) {
                linUrgentYes.setVisibility(View.VISIBLE);
                linUrgentNo.setVisibility(View.GONE);
                textViewMessage.setText("Untuk dapat melanjutkan,\n silakan perbarui aplikasi Anda.");
            } else {
                linUrgentYes.setVisibility(View.GONE);
                linUrgentNo.setVisibility(View.VISIBLE);
                textViewMessage.setText("Silahkan perbarui.");
            }
        }
        */
        editTextUser = findViewById(R.id.et_no_hp);
        editTextUser.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                checkRequestStatus();
            }
        });
        InputFilter[] filter = new saring_karakter().DisableSpecialCharactersAllCaps(16);
        editTextUser.setFilters(filter);
        editTextPassword = findViewById(R.id.et_password);
        editTextPassword.setTransformationMethod(AsteriskPasswordTransformationMethod.getInstance());
        editTextPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && editTextUser.getText() != null) {
                if (editTextUser.getText().length() == 0 || editTextUser.getText().toString().isEmpty()) {
                    v.setFocusable(false);
                    editTextUser.setFocusable(true);
                    editTextUser.setFocusableInTouchMode(true);
                    editTextUser.requestFocus();
                    editTextUser.setError("Nomor Handphone Tidak Boleh Kosong!");
                }
            }
        });

        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(v -> {
            ViewGroup parent = findViewById(R.id.contentHost);
            final FrameLayout view = (FrameLayout) View.inflate(this, R.layout.loading_bar_full_dialog, parent);
            TextView tv = view.findViewById(R.id.textContentProgressBar);
            tv.setText("Mohon Tunggu, sedang di proses");
            openProgressBarDialog(LoginActivity.this, view);
            readInboxGranted();
            //doLogin();
            Device.vibrate(LoginActivity.this);
        });
        btnShowPassword = findViewById(R.id.btn_show_password);
        btnShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // show password
                    editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // hide password
                    editTextPassword.setTransformationMethod(AsteriskPasswordTransformationMethod.getInstance());
                }
            }
        });
        btnBantuan = findViewById(R.id.btn_bantuan);
        btnBantuan.setOnClickListener(v -> {
            String url = "https://lc-fastpay.fastpay.co.id/phplive/phplive.php";
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        });

        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistrasiActivity.class);
            startActivity(intent);
            Device.vibrate(LoginActivity.this);
        });

        attachKeyboardListeners();
    }

    private void checkRequestStatus() {
        token = PreferenceClass.getDeviceToken();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestCheckStatus(editTextUser.getEditableText().toString(), token));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(LoginActivity.this, jsonObject, ActionCode.REQUEST_STATUS, LoginActivity.this);
    }

    private void readInboxGranted() {
        if (!PreferenceClass.getKey().equals(PreferenceClass.getKeyDemo())) {
            keyx = PreferenceClass.getKey().equals("") ? "" : PreferenceClass.getKey();
        }

        if (keyx.equalsIgnoreCase("") || keyx.isEmpty()) {
            //materialEditTextKey.setText("");
            if (editTextUser.getEditableText().toString().equalsIgnoreCase("")) {
                editTextUser.setAnimation(animShake);
                editTextUser.startAnimation(animShake);
                editTextUser.setError("Nomor Handphone Tidak Boleh Kosong!");
                Device.vibrate(LoginActivity.this);
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    closeProgressBarDialog();
                }
            } else if (editTextPassword.getEditableText().toString().equalsIgnoreCase("")) {
                editTextPassword.setAnimation(animShake);
                editTextPassword.startAnimation(animShake);
                editTextPassword.setError("Password Atau PIN Tidak Boleh Kosong");
                Device.vibrate(LoginActivity.this);
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    closeProgressBarDialog();
                }
            } else {
                cekAktif(editTextUser.getEditableText().toString());
            }
        } else {
            //materialEditTextKey.setText(keyx);
            if (!PreferenceClass.isLoggedIn()) {
//                if (!checkInternetDialog()){
//                    return;
//                }
                doLogin();
            }
        }
    }

    private void getDemo() {
        StringJson stringJson = new StringJson(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestGetDemo());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.GET_DEMO, this);
    }

    private void cekUpdate() {
        StringJson stringJson = new StringJson(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestCheckUpdateApps());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.CHECK_UPDATE, this);
    }

    private void cekAktif(String id) {
        StringJson stringJson = new StringJson(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestCheckAktif(id));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.CHECK_AKTIF, this);
        if (aviLoading.getVisibility() == View.GONE) {
            aviLoading.setVisibility(View.VISIBLE);
        }
    }

    private void check_avail_key() {
        StringJson stringJson = new StringJson(this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestCheckAvailKey(editTextUser.getEditableText().toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.CHECK_AVAIL_KEY, this);
    }

    public void checkKey(String desc) {
        doLogin();
    }

    private void doLogin() {
        if (editTextUser.getEditableText().toString().equalsIgnoreCase("")) {
            editTextUser.setAnimation(animShake);
            editTextUser.startAnimation(animShake);
            editTextUser.setError("Nomor Handphone Tidak Boleh Kosong!");
            Device.vibrate(LoginActivity.this);
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                closeProgressBarDialog();
            }
        } else if (editTextPassword.getEditableText().toString().equalsIgnoreCase("")) {
            editTextPassword.setAnimation(animShake);
            editTextPassword.startAnimation(animShake);
            editTextPassword.setError("Password atau Pin Tidak Boleh Kosong!");
            Device.vibrate(LoginActivity.this);
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                closeProgressBarDialog();
            }
        } else {
            if (activeSession) {
                checkPin();
                requestLogin(editTextUser.getEditableText().toString().trim(), editTextPassword.getEditableText().toString().trim(), "");
            }else{
                requestLogin(editTextUser.getEditableText().toString().trim(), editTextPassword.getEditableText().toString().trim(), "");
            }
            // else {
//                if (materialEditTextKey.getEditableText().toString().equalsIgnoreCase("")) {
//                    materialEditTextKey.setAnimation(animShake);
//                    materialEditTextKey.startAnimation(animShake);
//                    materialEditTextKey.setError("Sms Key Tidak Boleh Kosong");
//                    Device.vibrate(LoginActivity.this);
//                } else {
//                    requestLogin(editTextUser.getEditableText().toString(), editTextPassword.getEditableText().toString(), keyx);
//                }
//            }
        }
    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        Timber.d("onSuccess: %s", response.toString());

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            closeProgressBarDialog();
        }

        if (actionCode == ActionCode.LOGIN) {
            Timber.d("onSuccess: %s", response.toString());
            final UserModel userModel = gson.fromJson(response.toString(), UserModel.class);
            final SignOn signOn = gson.fromJson(response.toString(), SignOn.class);
            switch (userModel.getResponse_code()) {
                case ResponseCode.SUCCESS:
                    logEventFireBase("Login", "Login", EventParam.EVENT_ACTION_RESULT_LOGIN, EventParam.EVENT_SUCCESS, TAG);
                    PreferenceClass.storedLoggedUser(signOn);
                    String keyLite = "R4h4s14a3w3s";
                    //  String plainKey = signOn.getId() + '|' + editTextPassword.getText().toString() + '|' + Calendar.getInstance().getTimeInMillis();
                    String plainKey = userModel.getId_outlet() + '|' + editTextPassword.getEditableText().toString() + '|' + Calendar.getInstance().getTimeInMillis();
                    //String plainKey ="TEST";
                    MobileAES.defineKey(keyLite);
                    String plainTextKey = plainKey;
                    //Timber.d("onResponse login materialEditTextKey: %s", plainTextKey);
                    plainTextKey = MobileAES.encrypt(plainTextKey);
                    //Timber.d("onResponse login materialEditTextKey encript: %s", plainTextKey);
                    PreferenceClass.setAuth(plainTextKey);
                    Timber.d("onSuccess: %s", userModel.getId_outlet());
                    Timber.d("onSuccess: ->%s", MobileAES.decrypt("98da895e95cadcd2aa1e1ca552e6e0c1d258ce006f9a2be8879c98a3b467f355"));
                    PreferenceClass.setTokenPos(userModel.getKeyPos());
                    PreferenceClass.storedUser(userModel);
                    PreferenceClass.setId(userModel.getId_outlet());
                    if (isDemo) {
                        PreferenceClass.setPin(PreferenceClass.getPinDemo());
                        PreferenceClass.setKey(PreferenceClass.getKeyDemo());
                    } else {
                        PreferenceClass.setPin(editTextPassword.getEditableText().toString());
                        //PreferenceClass.setKey(materialEditTextKey.getEditableText().toString());
                    }

                    PreferenceClass.putString("nama_logo", userModel.getNama_logo());
                    PreferenceClass.putString("checksumFP", userModel.getCek_sum_logo());
                    PreferenceClass.putString("saldo", FormatString.CurencyIDR(userModel.getSaldo()));
                    //doUpdateLocation(materialEditTextKey.getEditableText().toString());
                    Bundle bundle = new Bundle();
                    bundle.putString(FirebaseAnalytics.Param.ITEM_ID, userModel.getId_outlet());
                    bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, userModel.getNama_pemilik());
                    bundle.putString(FirebaseAnalytics.Param.LOCATION, PreferenceClass.getString("place", ""));
                    bundle.putString(FirebaseAnalytics.Param.ITEM_LOCATION_ID, PreferenceClass.getString("location", "0,0"));

                    SBFApplication.sendEvent(FirebaseAnalytics.Event.LOGIN, bundle);

                    if (!PreferenceClass.getString("last_id", "").isEmpty() && PreferenceClass.getBoolean("otp_set", true)) {
                        PreferenceClass.putBoolean("otp_set", PreferenceClass.getString("last_id", "").equals(PreferenceClass.getId()));
                    }

                    Intent toHome = new Intent(LoginActivity.this, NewHomeActivity.class);
                    toHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    toHome.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

//                    SBFApplication.initUserComponent(PreferenceClass.getTokenPos());

                    startActivity(toHome);
                    finish();
                    break;
                case "03":
                case "02":
//                    checkKey("");
//                    break;
                case "04":
                    logEventFireBase("Login", "Login", EventParam.EVENT_ACTION_RESULT_LOGIN, EventParam.EVENT_NOT_SUCCESS, TAG);
                    editTextUser.setAnimation(animShake);
                    editTextUser.startAnimation(animShake);
                    editTextUser.setError(userModel.getResponse_desc());
                    editTextPassword.setAnimation(animShake);
                    editTextPassword.startAnimation(animShake);
                    editTextPassword.setError(userModel.getResponse_desc());
                    Device.vibrate(LoginActivity.this);
                    break;
                default:
                    logEventFireBase("Login", "Login", EventParam.EVENT_ACTION_RESULT_LOGIN, EventParam.EVENT_NOT_SUCCESS, TAG);
                    MyDynamicToast.warningMessage(LoginActivity.this, userModel.getResponse_desc());

                    break;
            }
        } else if (actionCode == TravelActionCode.LOGIN) {
            Timber.d("onSuccess: Login Travel%s", response.toString());
            BaseObject baseObject = gson.fromJson(response.toString(), BaseObject.class);
            if (baseObject.getResponse_code().equals("00")) {
                try {
                    String token = response.getString("response_token");
                    PreferenceClass.setToken(token);
                } catch (JSONException jsone) {
                    jsone.printStackTrace();
                }
            }

        } else if (actionCode == ActionCode.CHECK_UPDATE) {

            CheckUpdateModel checkUpdateModel = gson.fromJson(response.toString(), CheckUpdateModel.class);
            PreferenceClass.setInfo(checkUpdateModel.getInfo());
            if (checkUpdateModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                if (recent_version_code < checkUpdateModel.getVersion_code()) {
                    layout_update_apps.setVisibility(View.VISIBLE);
                    if (checkUpdateModel.getUrgent().equals("true")) {
                        linUrgentYes.setVisibility(View.VISIBLE);
                        linUrgentNo.setVisibility(View.GONE);
                        textViewMessage.setText("Untuk dapat melanjutkan,\n silakan perbarui aplikasi Anda.");
                    } else {
                        linUrgentYes.setVisibility(View.GONE);
                        linUrgentNo.setVisibility(View.VISIBLE);
                        textViewMessage.setText("Silahkan perbarui.");
                    }
                }
            }
        } else if (actionCode == ActionCode.GET_DEMO) {
            Timber.d("onSuccess: %s", response.toString());
            DemoModel demoModel = gson.fromJson(response.toString(), DemoModel.class);
            if (demoModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                linDemo.setVisibility(View.VISIBLE);
                PreferenceClass.setIdDemo(demoModel.getId_outlet_demo());
                PreferenceClass.setPinDemo(demoModel.getPin_outlet_demo());
                PreferenceClass.setKeyDemo(demoModel.getKey_outlet_demo());
                PreferenceClass.setEdukasiLogin(demoModel.getEdukasi_login());
            } else {
                linDemo.setVisibility(View.GONE);
            }

        } else if (actionCode == ActionCode.CHECK_AKTIF) {
            Timber.d("onSuccess: %s", response.toString());
            try {
                switch (response.getString("response_code")) {
                    case "00":
                        check_avail_key();
                        break;
                    case "XY":
                        if (aviLoading.getVisibility() == View.VISIBLE) {
                            aviLoading.setVisibility(View.GONE);
                        }
                        new_popup_alert_Aktifasi(ActionCode.CHECK_AKTIF, response.getString("nominal"), response.getString("id_outlet"));
                        break;
                    case "XZ":
                        if (aviLoading.getVisibility() == View.VISIBLE) {
                            aviLoading.setVisibility(View.GONE);
                        }
                        new_popup_alert_Aktifasi(ActionCode.CHECK_KADALUARSA, response.getString("nominal"), response.getString("id_outlet"));
                        break;
                    default:
                        popup_failed(response.getString("response_desc"));
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else if (actionCode == ActionCode.REQUEST_STATUS) {
            try {
                switch (response.getString("response_code")) {
                    case "00":
                        //materialEditTextKey.setVisibility(View.GONE);
                        activeSession = true;
                        PreferenceClass.putString("expired_time", response.getString("expired_time"));
                        break;
                    case "02":
                    case "03":
                    case "04":
                        logEventFireBase("LOGIN", "CHECK OUTLET DEVICE", EventParam.EVENT_ACTION_RESULT_LOGIN, EventParam.EVENT_NOT_SUCCESS, TAG);
                        new_popup_alert(this, "Informasi", response.getString("response_desc"));
                        editTextUser.setText("");
                        editTextUser.requestFocus();
                        PreferenceClass.putBoolean("otp_set", false);
                        break;
                    default:
//                        materialEditTextKey.setVisibility(View.VISIBLE);
                        PreferenceClass.putBoolean("otp_set", false);
                        activeSession = false;
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            allowLogin = true;
        } else if (actionCode == ActionCode.CHECK_AVAIL_KEY) {
            if (aviLoading.getVisibility() == View.VISIBLE) {
                aviLoading.setVisibility(View.GONE);
            }
            try {
                switch (response.getString("response_code")) {
                    case "00":
                        checkKey(response.getString("response_desc"));
                        break;
                    case "RK":
                        checkKey("");
                        break;
                }
            } catch (JSONException e) {
                logEventFireBase("LOGIN", "CHECK AVAIL KEY", EventParam.EVENT_ACTION_RESULT_LOGIN, EventParam.EVENT_NOT_SUCCESS, TAG);
                popup_failed("Gagal login, silahkan ulangi beberapa saat lagi.");
                e.printStackTrace();
            }
        } else if (actionCode == ActionCode.REQUEST_VALIDATION) {
            try {
                Timber.e("pin valid: %s", response.getString("response_code").equals("00"));
                PreferenceClass.putBoolean("otp_set", response.getString("response_code").equals("00"));
            } catch (JSONException e) {
                Timber.e(e);
            } finally {
                Timber.e("pin valid: %s", PreferenceClass.getBoolean("otp_set", false));
                requestLogin(editTextUser.getText().toString(), editTextPassword.getText().toString(), keyx);
            }
        }
    }

    @Override
    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            closeProgressBarDialog();
        }

        if (actionCode == ActionCode.LOGIN) {
            popup_failed(responseDescription);
        } else if (actionCode == ActionCode.UPDATE_LOCATION) {
            Timber.d("onFailure: %s", responseDescription);
        } else if (actionCode == ActionCode.CHECK_AKTIF) {
            if (aviLoading.getVisibility() == View.VISIBLE) {
                aviLoading.setVisibility(View.GONE);
            }
            popup_failed(responseDescription);
        } else {
            Timber.e(throwable);
//            showToast("Gagal: " + responseDescription + "\ne: " + throwable.getLocalizedMessage());
        }
    }

    void popup_failed(String responseDescription) {
        ViewGroup parent = findViewById(R.id.contentHost);
        View v = View.inflate(this, R.layout.dialog_header_failure_layout, parent);

        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                .setBackgroundColor(ContextCompat.getColor(this, R.color.black_overlay_dark))
                .setTitle("Informasi")
                .setMessage(responseDescription)
                .setTextGravity(Gravity.CENTER)
                .setHeaderView(v)
                .setCancelable(true);

        builder.addButton("Tutup", -1, ContextCompat.getColor(this, R.color.md_red_500), CFAlertDialog.CFAlertActionStyle.POSITIVE, getButtonGravity(3), (dialog, which) -> dialog.cancel());
        builder.addButton("Bantuan", -1, ContextCompat.getColor(context, R.color.md_green_500), CFAlertDialog.CFAlertActionStyle.NEGATIVE, getButtonGravity(3), (dialog, which) -> {
            liveChat();
            dialog.cancel();
        });

        if (!this.isFinishing()) {
            builder.show();
        }
    }

    private void checkPin() {
        if (allowLogin) {
            try {
                JSONObject jsonObject = new JSONObject(stringJson.requestCheckValidation(editTextPassword.getText().toString(), editTextUser.getText().toString()));
                RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.REQUEST_VALIDATION, this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            new Handler().postDelayed(this::checkPin, 10);
        }
    }

    private void requestLogin(String id, String pin, String key) {
        logEventFireBase("Login", "Login", EventParam.EVENT_ACTION_REQUEST_LOGIN, EventParam.EVENT_SUCCESS, TAG);

        token = PreferenceClass.getDeviceToken();

        Timber.d("sendTokenToServer: %s", token);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestSignOn(id, pin, "", token));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(LoginActivity.this, jsonObject, ActionCode.LOGIN, this);
    }

    private void requestLoginDemo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            Timber.d("showContacts: ");
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 200);
        } else {
            token = PreferenceClass.getDeviceToken();
            Timber.d("sendTokenToServer: %s", token);
            isDemo = true;
            logEventFireBase("Demo Akun", "Demo Akun", EventParam.EVENT_ACTION_RESULT_LOGIN, EventParam.EVENT_SUCCESS, TAG);
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject = new JSONObject(stringJson.requestSignOn(PreferenceClass.getIdDemo(), PreferenceClass.getPinDemo(), PreferenceClass.getKeyDemo(), token));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            RequestUtils.transportWithProgressResponse(LoginActivity.this, jsonObject, ActionCode.LOGIN, this);

//            ViewGroup parent = findViewById(R.id.contentHost);
//            final FrameLayout view = (FrameLayout) View.inflate(this, R.layout.loading_bar_full_dialog, parent);
//            TextView text = view.findViewById(R.id.textContentProgressBar);
//            text.setText("Mohon Tunggu, sedang di proses");
//            openProgressBarDialog(LoginActivity.this, view);
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    protected void onShowKeyboard(int keyboardHeight) {
        Timber.d("onShowKeyboard: %s", keyboardHeight);
    }

    @Override
    protected void onHideKeyboard() {
        Timber.d("onHideKeyboard: ");
    }

    public void new_popup_alert_Aktifasi(final int actionCode, final String nominal, final String id_outlet) {
        ViewGroup parent = findViewById(R.id.contentHost);
        View v = View.inflate(this, R.layout.dialog_header_response_layout, parent);
        ImageView imageView = v.findViewById(R.id.imageViewNotif);
        String pesan = "";
        if (actionCode == ActionCode.CHECK_AKTIF) {


            imageView.setImageResource(R.drawable.ic_aktivasi);
            pesan = "Maaf akun Fastpay Anda masih belum Aktif.Silahkan cek informasi selengkapnya disini :";
        } else if (actionCode == ActionCode.CHECK_KADALUARSA) {

            imageView.setImageResource(R.drawable.ic_kadaluarsa);
            pesan = "Mohon maaf, akun Fastpay Anda masih belum aktif, dan Tiket Aktivasi Anda sudah kadaluarsa. Info selengkapnya cek disini :";
        }

        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                .setBackgroundColor(ContextCompat.getColor(this, R.color.black_overlay_dark))
                .setTitle("Informasi")
                .setMessage(pesan)
                .setTextGravity(Gravity.CENTER)
                .setHeaderView(v)
                .setCancelable(true);


        builder.addButton("AKTIFKAN SEKARANG", -1, ContextCompat.getColor(this, R.color.md_red_500), CFAlertDialog.CFAlertActionStyle.POSITIVE, getButtonGravity(3), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(@NonNull DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.onDismissListener(dialog -> {
            if (actionCode == ActionCode.CHECK_AKTIF) {
                aktifvasi_akun(nominal, id_outlet);
            } else if (actionCode == ActionCode.CHECK_KADALUARSA) {
                editTextUser.setText("");
                editTextPassword.setText("");

                CustomTabsIntent customTabsIntent = buildCustomTabsIntent();
                customTabsIntent.launchUrl(LoginActivity.this, Uri.parse("https://www.fastpay.co.id/aktivasi"));
            }
        });

        if (context instanceof AppCompatActivity && !((AppCompatActivity) context).isFinishing()) {
            builder.show();
        }
    }

    private void aktifvasi_akun(String nominal, String id_outlet) {
        Intent intent = new Intent(LoginActivity.this, AktivasiAkunActivity.class);
        intent.putExtra("id_outlet", id_outlet);
        intent.putExtra("nominal", nominal);
        startActivityForResult(intent, ActionCode.AKTIVASI_AKUN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == ActionCode.AKTIVASI_AKUN) {
                editTextUser.setText("");
                editTextPassword.setText("");
            }
        }
    }

    private void update_app() {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName()));
        boolean marketFound = false;
        // find all applications able to handle our intent
        final List<ResolveInfo> otherApps = getPackageManager().queryIntentActivities(i, 0);
        for (ResolveInfo otherApp : otherApps) {
            // look for Google Play application
            if (otherApp.activityInfo.applicationInfo.packageName.equals("com.bm.main.pos")) {
                ActivityInfo otherAppActivity = otherApp.activityInfo;
                ComponentName componentName = new ComponentName(
                        otherAppActivity.applicationInfo.packageName,
                        otherAppActivity.name
                );
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                i.setComponent(componentName);
                startActivity(i);
                marketFound = true;
                break;
            }
        }

        // if GP not present on device, open web browser
        if (!marketFound) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
            startActivity(webIntent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkInternetDialog();
    }

    private boolean checkInternetDialog(){
        if (!DetectConnection.checkInternet(this)){
            new AlertDialog.Builder(this, R.style.AlertDialogNoInternet)
                    .setTitle("Tidak Ada Koneksi Internet!")
                    .setMessage("Anda tidak sedang terhubung ke internet. Tolong periksa kembali koneksi internet Anda!")
                    .setCancelable(true)
                    //.setNeutralButton(android.R.string.ok, (dialog, which) -> dialog.dismiss())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return false;
        }else{
            return true;
        }
    }
}

package com.bm.main.fpl.activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.main.fpl.constants.RequestCode;
import com.bm.main.fpl.utils.Device;
import com.bm.main.pos.R;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.models.BaseObject;
import com.bm.main.fpl.models.KeyRequest;
import com.bm.main.fpl.models.SignOn;
import com.bm.main.fpl.models.UserModel;
import com.bm.main.fpl.templates.toast.MyDynamicToast;
import com.bm.main.fpl.utils.FormatString;
import com.bm.main.fpl.utils.MobileAES;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;
import com.bm.main.materialedittext.MaterialEditText;
import com.bm.main.pos.feature.drawer.DrawerActivity;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import timber.log.Timber;

public class KeySmsActivity extends BaseActivity implements ProgressResponseCallback, View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private static final String TAG = KeySmsActivity.class.getSimpleName();
    RelativeLayout card_key_baru, card_key_sms;
    TextView text_menu_radio, text_request_key, text_input_sms;
    TextView title, textViewPlusWaitingReqKey;
    RadioGroup radioGroupKey;
    Button button_kirim, button_cancel;
    String userId, password;
    MaterialEditText edit_input_key_sms;
    Intent a;
    ImageView imageViewReqKey, imageViewInputKey;

    private RadioButton radioButtonRK;
    private String RK;
    LinearLayout lin_textKeySms, lin_requestKey;
    ImageView imageViewSmsInbox;

    @Nullable
    String keyx;
    CoordinatorLayout rootLayout;
    RelativeLayout rel_header;

    ImageView imageViewQRCodeScan;
    TextView textViewDescAvailKey;
    LinearLayout lin_footer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_sms);
        logEventFireBase("KEY SMS", "KEY SMS", EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, TAG);
        rootLayout = findViewById(R.id.rootLayout);
        lin_footer = findViewById(R.id.lin_footer);
        rel_header = findViewById(R.id.rel_header);

        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver,
                new IntentFilter("BROADCAST_KEY")
        );

        Glide.with(this).asBitmap().load(R.drawable.bg_batik_putih).encodeFormat(Bitmap.CompressFormat.WEBP).encodeQuality(75).diskCacheStrategy(DiskCacheStrategy.NONE).override(displayMetrics.widthPixels, displayMetrics.heightPixels).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
            }

            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> animation) {
                Drawable drawable = new BitmapDrawable(getResources(), resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    rootLayout.setBackground(drawable);
                }
            }
        });
        float heightDpFooter = (getResources().getDisplayMetrics().heightPixels * 2) / 7;
        CoordinatorLayout.LayoutParams lpFooter = (CoordinatorLayout.LayoutParams) lin_footer.getLayoutParams();

        lpFooter.height = (int) heightDpFooter;
        lin_footer.setMinimumHeight(lpFooter.height);
        Glide.with(this).asBitmap().load(R.drawable.bg_footer).encodeFormat(Bitmap.CompressFormat.PNG).encodeQuality(75).diskCacheStrategy(DiskCacheStrategy.NONE).override(displayMetrics.widthPixels, displayMetrics.heightPixels).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
            }

            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> animation) {
                Drawable drawable = new BitmapDrawable(getResources(), resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    lin_footer.setBackground(drawable);
                }
            }
        });

        a = getIntent();
        userId = a.getStringExtra("id");
        password = a.getStringExtra("pass");
        String desc = a.getStringExtra("desc");
        card_key_baru = findViewById(R.id.card_key_baru);
        card_key_baru.setOnClickListener(this);
        card_key_sms = findViewById(R.id.card_key_sms);
        card_key_sms.setOnClickListener(this);
        title = findViewById(R.id.title);
        imageViewSmsInbox = findViewById(R.id.imageViewSmsInbox);
        textViewDescAvailKey = findViewById(R.id.textViewDescAvailKey);
        textViewDescAvailKey.setText(desc);

        lin_textKeySms = findViewById(R.id.lin_textKeySms);
        lin_requestKey = findViewById(R.id.lin_requestKey);
        textViewPlusWaitingReqKey = findViewById(R.id.textViewPlusWaitingReqKey);
        textViewPlusWaitingReqKey.setVisibility(View.GONE);
        text_menu_radio = findViewById(R.id.text_menu_radio);
        radioGroupKey = findViewById(R.id.radioGroupKey);
        radioGroupKey.setOnCheckedChangeListener(this);
        button_kirim = findViewById(R.id.button_kirim);
        button_kirim.setOnClickListener(this);
        button_cancel = findViewById(R.id.button_cancel);
        button_cancel.setOnClickListener(this);
        text_request_key = findViewById(R.id.text_request_key);
        text_input_sms = findViewById(R.id.text_input_key);
        text_input_sms.setTextColor(FormatString.setColor(this, R.color.md_blue_600));
        edit_input_key_sms = findViewById(R.id.edit_input_key_sms);
        imageViewReqKey = findViewById(R.id.imageViewReqKey);
        imageViewInputKey = findViewById(R.id.imageViewInputKey);
        int selectedId = radioGroupKey.getCheckedRadioButtonId();
        Timber.d("onCreate: %s", selectedId);
        radioButtonRK = findViewById(selectedId);
        RK = radioButtonRK.getTag().toString();

        imageViewSmsInbox.setOnClickListener(v -> {
            keyx = PreferenceClass.getKey().equals("") ? "" : PreferenceClass.getKey();
            edit_input_key_sms.setText("");
            edit_input_key_sms.setText(keyx);
            if (!edit_input_key_sms.getEditableText().toString().equalsIgnoreCase("")) {
                requestLogin(userId, password, keyx);
            }
        });

        imageViewQRCodeScan = findViewById(R.id.imageViewQRCodeScan);
        imageViewQRCodeScan.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                grantPermissions(RequestCode.ActionCode_CAMERA, Manifest.permission.CAMERA);
                return;
            }
            IntentIntegrator integrator = new IntentIntegrator(this);

            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
            integrator.setPrompt(String.valueOf(FormatString.htmlString(getString(R.string.scan_qrCodeKey))));

            integrator.setOrientationLocked(false);
            integrator.setRequestCode(ActionCode.BARCODE);
            integrator.setBeepEnabled(true);
            integrator.initiateScan();
        });

        if (PreferenceClass.getInt(TAG, 0) != 1) {
            showCaseFirst(this, "Input Key SMS", "Klik Icon ini bila Anda sudah memiliki Key SMS", card_key_sms);
            mGbuilder.setGuideListener(view -> {
                switch (view.getId()) {
                    case R.id.card_key_sms:
                        mGbuilder
                                .setTitle("Request Key SMS")
                                .setContentText("Klik Icon ini untuk meminta Key SMS yang baru")
                                .setTargetView(card_key_baru)
                                .build();
                        visibleKeyBaru();
                        break;
                    case R.id.card_key_baru:
                        PreferenceClass.putInt(TAG, 1);
                        return;
                }
                mGuideView = mGbuilder.build();
                mGuideView.show();

            });
            mGuideView = mGbuilder.build();
            mGuideView.show();
        }

        if (desc.length() > 0) {
            visibleInputSms();
        } else {
            visibleKeyBaru();
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        finish();
    }

    private void visibleInputSms() {
        edit_input_key_sms.setText("");
        text_input_sms.setTextColor(FormatString.setColor(this, R.color.md_blue_600));

        text_request_key.setTextColor(FormatString.setColor(this, R.color.md_black_1000));

        lin_textKeySms.setVisibility(View.VISIBLE);
        edit_input_key_sms.requestFocus();
        button_kirim.setText("MASUK");
        textViewPlusWaitingReqKey.setText("");
    }

    private void visibleKeyBaru() {
        text_request_key.setTextColor(FormatString.setColor(this, R.color.md_blue_600));
        text_input_sms.setTextColor(FormatString.setColor(this, R.color.md_black_1000));
        textViewPlusWaitingReqKey.setVisibility(View.GONE);
        button_kirim.setText("KIRIM");
        lin_textKeySms.setVisibility(View.GONE);
        closeKeyboard(this);
    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        if (actionCode == ActionCode.REQUEST_KEY) {
            closeProgressBarDialog();
            KeyRequest keyRequest = gson.fromJson(response.toString(), KeyRequest.class);
            if (keyRequest.getResponse_code().equals(ResponseCode.SUCCESS)) {
                logEventFireBase("KEY SMS", "KEY SMS", EventParam.EVENT_ACTION_RESULT_KEY, EventParam.EVENT_SUCCESS, TAG);
                visibleInputSms();
                PreferenceClass.putInt("limit", keyRequest.getDay_limit());
                PreferenceClass.putString("startDate", keyRequest.getStart_date());
                PreferenceClass.putString("endDate", keyRequest.getEnd_date());
                new_popup_alert(this, "Informasi", keyRequest.getResponse_desc());
            } else {
                logEventFireBase("KEY SMS", "KEY SMS", EventParam.EVENT_ACTION_RESULT_KEY, EventParam.EVENT_NOT_SUCCESS, TAG);
                new_popup_alert(this, "Informasi", keyRequest.getResponse_desc());
            }

        } else if (actionCode == ActionCode.LOGIN) {
            Timber.d("onSuccess: %s", response.toString());
            closeProgressBarDialog();

            final UserModel userModel = gson.fromJson(response.toString(), UserModel.class);
            final SignOn signOn = gson.fromJson(response.toString(), SignOn.class);

            switch (userModel.getResponse_code()) {
                case ResponseCode.SUCCESS:
                    logEventFireBase("KEY SMS", "KEY SMS", EventParam.EVENT_ACTION_RESULT_LOGIN, EventParam.EVENT_SUCCESS, TAG);
                    PreferenceClass.storedLoggedUser(signOn);
                    String keyLite = "R4h4s14a3w3s";
                    String plainKey = userModel.getId_outlet() + '|' + password + '|' + Calendar.getInstance().getTimeInMillis();
                    MobileAES.defineKey(keyLite);
                    String plainTextKey = plainKey;
                    plainTextKey = MobileAES.encrypt(plainTextKey);
                    PreferenceClass.setAuth(plainTextKey);

                    PreferenceClass.setTokenPos(userModel.getKeyPos());
                    PreferenceClass.storedUser(userModel);
                    PreferenceClass.setId(userModel.getId_outlet());
                    PreferenceClass.setPin(password);
                    PreferenceClass.setKey(keyx);
                    PreferenceClass.putBoolean("otp_set", true);

                    PreferenceClass.putString("nama_logo", userModel.getNama_logo());
                    PreferenceClass.putString("checksumFP", userModel.getCek_sum_logo());
                    PreferenceClass.putString("saldo", FormatString.CurencyIDR(userModel.getSaldo()));
                    doUpdateLocation(keyx);

//                    setResult(RESULT_OK);
//                    finish();

                    PreferenceClass.setUnlock();
                    Intent toHome = new Intent(KeySmsActivity.this, HomeActivity.class);
                    toHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    toHome.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    toHome.addCategory(Intent.ACTION_MAIN);
                    toHome.addCategory(Intent.CATEGORY_HOME);
                    startActivity(toHome);
                    finish();
                    break;
                case "02":
                case "03":
                case "04":
                    logEventFireBase("KEY SMS", "KEY SMS", EventParam.EVENT_ACTION_RESULT_LOGIN, EventParam.EVENT_NOT_SUCCESS, TAG);
                    edit_input_key_sms.setAnimation(animShake);
                    edit_input_key_sms.startAnimation(animShake);
                    edit_input_key_sms.setError(userModel.getResponse_desc());
                    Device.vibrate(this);
                    break;
                default:
                    logEventFireBase("KEY SMS", "KEY SMS", EventParam.EVENT_ACTION_RESULT_LOGIN, EventParam.EVENT_NOT_SUCCESS, TAG);
                    new_popup_alert(this, "Informasi", userModel.getResponse_desc());
                    break;
            }
        } else if (actionCode == ActionCode.UPDATE_LOCATION) {
            Timber.d("onSuccess: %s", response.toString());
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
        }
    }

    @Override
    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {
        closeProgressBarDialog();
        if (actionCode == ActionCode.REQUEST_KEY) {
            MyDynamicToast.errorMessage(KeySmsActivity.this, responseDescription);
        } else if (actionCode == ActionCode.LOGIN) {
            Timber.d("onFailure: %s", responseDescription);
            MyDynamicToast.errorMessage(KeySmsActivity.this, responseDescription);
        } else if (actionCode == ActionCode.UPDATE_LOCATION) {
            Timber.d("onFailure: %s", responseDescription);
        }
    }

    @Override
    public void onCheckedChanged(@NonNull RadioGroup group, int checkedId) {
        int selectedId = group.getCheckedRadioButtonId();
        radioButtonRK = findViewById(selectedId);
        RK = radioButtonRK.getTag().toString();
    }

    @Override
    public void onClick(@NonNull View v) {
        switch (v.getId()) {
            case R.id.card_key_baru:
                visibleKeyBaru();
                break;
            case R.id.card_key_sms:
                visibleInputSms();
                break;
            case R.id.button_kirim:
                if (button_kirim.getText().equals("MASUK")) {
                    if (edit_input_key_sms.getEditableText().toString().equalsIgnoreCase("")) {
                        edit_input_key_sms.setAnimation(animShake);
                        edit_input_key_sms.startAnimation(animShake);
                        edit_input_key_sms.setError("Sms Key Tidak Boleh Kosong");
                        Device.vibrate(this);
                    } else {
                        keyx = edit_input_key_sms.getEditableText().toString();
                        requestLogin(userId, password, keyx);
                    }
                } else {
                    requestKey(userId, password, RK);
                }

                break;
            case R.id.button_cancel:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    public void requestKey(String id, String pin, String rk) {
        logEventFireBase("KEY SMS", "KEY SMS", EventParam.EVENT_ACTION_REQUEST_KEY, EventParam.EVENT_SUCCESS, TAG);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestKey(id, pin, "RK"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.REQUEST_KEY, this);
        ViewGroup parent = findViewById(R.id.contentHost);
        final FrameLayout view = (FrameLayout) View.inflate(this, R.layout.loading_bar_full_dialog, parent);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText("Mohon Tunggu, sedang di proses");
        openProgressBarDialog(KeySmsActivity.this, view);
    }

    private void requestLogin(String id, String pin, String key) {

        logEventFireBase("KEY SMS", "KEY SMS", EventParam.EVENT_ACTION_REQUEST_LOGIN, EventParam.EVENT_SUCCESS, TAG);
        final String token = PreferenceClass.getDeviceToken();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestSignOn(id, pin, key, token));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Timber.d("sendTokenToServer: " + jsonObject);
        RequestUtils.transportWithProgressResponse(KeySmsActivity.this, jsonObject, ActionCode.LOGIN, this);

        ViewGroup parent = findViewById(R.id.contentHost);
        final FrameLayout view = (FrameLayout) View.inflate(this, R.layout.loading_bar_full_dialog, parent);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText("Mohon Tunggu, sedang di proses");
        openProgressBarDialog(KeySmsActivity.this, view);
    }

//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(KeySmsActivity.this, LoginActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//        startActivity(intent);
//        finish();
//    }

    public void doUpdateLocation(String key) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.update_long_lat(key, PreferenceClass.getString("lat", "0.0"), PreferenceClass.getString("long", "0.0")));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Timber.d("doUpdateLocation: " + jsonObject);
        RequestUtils.transportWithProgressResponseUpdateLocation(this, jsonObject, ActionCode.UPDATE_LOCATION, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Timber.d("onActivityResult: " + requestCode + " " + resultCode);
        if (resultCode == AppCompatActivity.RESULT_OK) {

            if (requestCode == ActionCode.BARCODE) {
                Timber.d("onActivityResult: %s", data.getStringExtra(Intents.Scan.RESULT));
                String qr = data.getStringExtra(Intents.Scan.RESULT);
                if (qr != null) {
                    if (qr.length() > 10) {
                        requestLogin(userId, password, qr);
                    } else {
                        new_popup_alert(KeySmsActivity.this, "Info", "QRCode tidak bisa di baca");
                    }
                } else {
                    new_popup_alert(KeySmsActivity.this, "Info", "QRCode tidak bisa di baca");
                }
            }
        }
    }

    @NonNull
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, @NonNull Intent intent) {
            String receivedKey = intent.getStringExtra("key");
            edit_input_key_sms.setText(receivedKey);
            keyx = receivedKey;
            requestLogin(userId,
                    password, keyx
            );
        }
    };
}

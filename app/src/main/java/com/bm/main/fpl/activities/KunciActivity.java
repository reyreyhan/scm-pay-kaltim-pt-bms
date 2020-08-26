package com.bm.main.fpl.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.main.fpl.templates.AsteriskPasswordTransformationMethod;
import com.bm.main.fpl.utils.Device;
import com.bm.main.scm.R;
import com.bm.main.scm.SBFApplication;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.models.BaseObject;
import com.bm.main.fpl.templates.indicators.AVLoadingIndicatorView;
import com.bm.main.fpl.utils.FormatString;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;
import com.bm.main.materialedittext.MaterialEditText;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import timber.log.Timber;

public class KunciActivity extends BaseActivity implements View.OnClickListener, ProgressResponseCallback {

    private static final String TAG = KunciActivity.class.getSimpleName();
    AppCompatButton appCompatButtonMasuk;
    TextView textViewPlusLupa;
    MaterialEditText edit_input_pin;

    TextView textViewKeluar, textViewHapus;

    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView0;

    boolean bottom;
    RelativeLayout linSlider;
    private LinearLayout.LayoutParams lp;
    AVLoadingIndicatorView avi;

    LinearLayout rel_footer;
    CoordinatorLayout rootLayout;

    int lupa = 0;
    RelativeLayout rel_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Runtime.getRuntime().gc();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kunci);
        logEventFireBase("Kunci", "Kunci", EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, TAG);
        overridePendingTransition(0, 0);
        Intent intent = getIntent();

        bottom = intent.getBooleanExtra("bottom", false);

        rootLayout = findViewById(R.id.rootLayout);
        rel_footer = findViewById(R.id.rel_footer);
        linSlider = findViewById(R.id.linSlider);

        avi = findViewById(R.id.avi);
        avi.setVisibility(View.GONE);
        float heightDp = (getResources().getDisplayMetrics().heightPixels * 2) / 7;
        lp = (LinearLayout.LayoutParams) linSlider.getLayoutParams();


        lp.height = (int) heightDp;
        linSlider.setMinimumHeight(lp.height);

        Glide.with(this).asBitmap().load(R.drawable.bg_batik_putih).encodeFormat(Bitmap.CompressFormat.WEBP).encodeQuality(50).diskCacheStrategy(DiskCacheStrategy.NONE).override(displayMetrics.widthPixels, displayMetrics.heightPixels).into(new SimpleTarget<Bitmap>() {
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
        CoordinatorLayout.LayoutParams lpFooter = (CoordinatorLayout.LayoutParams) rel_footer.getLayoutParams();

        lpFooter.height = (int) heightDpFooter;
        rel_footer.setMinimumHeight(lpFooter.height);
        Glide.with(this).asBitmap().load(R.drawable.bg_footer).encodeFormat(Bitmap.CompressFormat.PNG).encodeQuality(50).diskCacheStrategy(DiskCacheStrategy.NONE).override(displayMetrics.widthPixels, lpFooter.height).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
            }

            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> animation) {
                Drawable drawable = new BitmapDrawable(getResources(), resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    rel_footer.setBackground(drawable);
                }
            }
        });

        textView1 = findViewById(R.id.textView1);
        textView1.setOnClickListener(this);
        textView2 = findViewById(R.id.textView2);
        textView2.setOnClickListener(this);
        textView3 = findViewById(R.id.textView3);
        textView3.setOnClickListener(this);
        textView4 = findViewById(R.id.textView4);
        textView4.setOnClickListener(this);
        textView5 = findViewById(R.id.textView5);
        textView5.setOnClickListener(this);
        textView6 = findViewById(R.id.textView6);
        textView6.setOnClickListener(this);
        textView7 = findViewById(R.id.textView7);
        textView7.setOnClickListener(this);
        textView8 = findViewById(R.id.textView8);
        textView8.setOnClickListener(this);
        textView9 = findViewById(R.id.textView9);
        textView9.setOnClickListener(this);
        textView0 = findViewById(R.id.textView0);
        textView0.setOnClickListener(this);
        edit_input_pin = findViewById(R.id.edit_input_pin);
        edit_input_pin.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        edit_input_pin.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(@NonNull Editable s) {
                if (s.length() == 6) {
                    checkPin();
                }
            }
        });

        textViewHapus = findViewById(R.id.textViewHapus);
        textViewHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit_input_pin.length() > 0) {
                    edit_input_pin.setText(edit_input_pin.getText().toString().substring(0, edit_input_pin.length() - 1));
                }
            }
        });

        textViewKeluar = findViewById(R.id.textViewKeluar);
        textViewKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);
            }
        });

        textViewPlusLupa = findViewById(R.id.textViewPlusLupa);
        textViewPlusLupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KunciActivity.this, LupaPinActivity.class).putExtra("flag", 1);
                startActivity(intent);
            }
        });

        appCompatButtonMasuk = findViewById(R.id.appCompatButtonMasuk);
        appCompatButtonMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPin();
            }
        });

        String token = PreferenceClass.getDeviceToken();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestCheckStatus(PreferenceClass.getLoggedUser().getId(), token));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestUtils.transportWithProgressResponse(KunciActivity.this, jsonObject, ActionCode.REQUEST_STATUS, KunciActivity.this);
    }

    private void checkPin() {
        Device.vibrate(this);
        if (edit_input_pin.getText().toString().equals(PreferenceClass.getPin())) {
            synchronized (this) {
                avi.setVisibility(View.VISIBLE);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject = new JSONObject(stringJson.requestCheckValidation(edit_input_pin.getText().toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.REQUEST_VALIDATION, this);
            }
        } else {
            ++lupa;
            Timber.d("checkPin: %s", lupa);
            if (lupa == 3) {
                new_popup_alert_failurepin(this, getString(R.string.salahpin));
            } else {
                clearwhenwrong();
            }
        }
    }

    private void clearwhenwrong() {
        edit_input_pin.setText("");
        edit_input_pin.setAnimation(BaseActivity.animShake);
        edit_input_pin.startAnimation(BaseActivity.animShake);
        edit_input_pin.setError("Pin Anda salah");
        Device.vibrate(this);
        avi.setVisibility(View.GONE);
    }

    @Override
    public void onClick(@NonNull View view) {
        int id = view.getId();

        if (id == R.id.textView0) {
            edit_input_pin.setText(edit_input_pin.getText() + "0");
        } else if (id == R.id.textView1) {
            edit_input_pin.setText(edit_input_pin.getText() + "1");
        } else if (id == R.id.textView2) {
            edit_input_pin.setText(edit_input_pin.getText() + "2");
        } else if (id == R.id.textView3) {
            edit_input_pin.setText(edit_input_pin.getText() + "3");
        } else if (id == R.id.textView4) {
            edit_input_pin.setText(edit_input_pin.getText() + "4");
        } else if (id == R.id.textView5) {
            edit_input_pin.setText(edit_input_pin.getText() + "5");
        } else if (id == R.id.textView6) {
            edit_input_pin.setText(edit_input_pin.getText() + "6");
        } else if (id == R.id.textView7) {
            edit_input_pin.setText(edit_input_pin.getText() + "7");
        } else if (id == R.id.textView8) {
            edit_input_pin.setText(edit_input_pin.getText() + "8");
        } else if (id == R.id.textView9) {
            edit_input_pin.setText(edit_input_pin.getText() + "9");
        }

    }

    public void new_popup_alert_failurepin(Context context, String responseDescription) {
        ViewGroup parent = findViewById(R.id.contentHost);
        View v = View.inflate(context, R.layout.dialog_header_response_layout, parent);

        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(context);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                .setBackgroundColor(ContextCompat.getColor(this, R.color.black_overlay_dark))
                .setTitle("Informasi")
                .setMessage(FormatString.htmlString(responseDescription))
                .setTextGravity(Gravity.CENTER)

                .setHeaderView(v)
                .setCancelable(true);

        builder.addButton("Tutup", -1, ContextCompat.getColor(this, R.color.md_red_500), CFAlertDialog.CFAlertActionStyle.POSITIVE, getButtonGravity(3), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(@NonNull DialogInterface dialog, int which) {
                dialog.cancel();
                lupa = 0;
                clearwhenwrong();
            }
        });

        if (context instanceof AppCompatActivity && !((AppCompatActivity) context).isFinishing()) {
            builder.show();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        Timber.d("onSuccess: %s", response.toString());
        if (actionCode == ActionCode.REQUEST_VALIDATION) {

            BaseObject baseObject = gson.fromJson(response.toString(), BaseObject.class);

            if (baseObject.getResponse_code().equals(ResponseCode.SUCCESS)) {
                PreferenceClass.setUnlock();
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, PreferenceClass.getId());
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, PreferenceClass.getUser().getNama_pemilik());
                bundle.putString(FirebaseAnalytics.Param.LOCATION, PreferenceClass.getString("place", ""));
                bundle.putString(FirebaseAnalytics.Param.ITEM_LOCATION_ID, PreferenceClass.getString("location", "0,0"));

                SBFApplication.sendEvent(FirebaseAnalytics.Event.LOGIN, bundle);
                if (bottom) {
                    finish();
                } else {
                    Intent out = new Intent(KunciActivity.this, HomeActivity.class);
                    out.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    out.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    out.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(out);
                    finish();
                }
            } else {
                requestLogout();
                PreferenceClass.setLogOut();
                Intent intent = new Intent(KunciActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                finish();
            }
        } else if (actionCode == ActionCode.REQUEST_STATUS) {
            try {
                if ("00".equals(response.getString("response_code"))) {
                    PreferenceClass.putString("expired_time", response.getString("expired_time"));
                } else {
                    requestLogout();
                    PreferenceClass.setLogOut();
                    Intent intent = new Intent(KunciActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    startActivity(intent);
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
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
        }
    }

    @Override
    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {
    }
}

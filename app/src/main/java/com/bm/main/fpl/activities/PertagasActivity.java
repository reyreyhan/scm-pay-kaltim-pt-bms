package com.bm.main.fpl.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.ProdukCode;
import com.bm.main.fpl.constants.ProdukGroup;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.JsonObjectResponseCallback;
import com.bm.main.fpl.models.InqModel;
import com.bm.main.fpl.models.SaldoModel;
import com.bm.main.fpl.templates.CustomFontCheckBox;
import com.bm.main.fpl.templates.showcaseview.GuideView;
import com.bm.main.fpl.utils.Device;
import com.bm.main.fpl.utils.FormatString;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;
import com.bm.main.materialedittext.MaterialEditText;
import com.bm.main.pos.R;
import com.google.zxing.client.android.Intents;

import org.json.JSONException;
import org.json.JSONObject;

import timber.log.Timber;

import static com.bm.main.fpl.constants.ResponseCode.NETWORK_ERROR;

public class PertagasActivity extends KeyboardListenerActivity implements JsonObjectResponseCallback {
    private static final String TAG = PertagasActivity.class.getSimpleName();
    //    private RadioButton radioTabsItemButton;
//    private RadioGroup radioTabs;
    MaterialEditText materialEditTextIdPelanggan;
    CustomFontCheckBox checkboxSimpanId;
    AppCompatButton button_lanjutkan;
    //  private LinearLayout bottom_toolbar;
    AppCompatButton appButtonItemTabIdPelanggan;
    AppCompatButton appButtonItemTabDaftarPelanggan;
    LinearLayout linMain, layout_no_connection;
    ImageView imageViewAddressBook;
    private View menuItemView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pertagas);
        logEventFireBase(ProdukGroup.PERTAGAS, ProdukCode.PERTAGAS, EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, TAG);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pertamina Gas");
        init(0);
        bottom_toolbar = findViewById(R.id.bottom_toolbar);
        linMain = findViewById(R.id.linMain);
        layout_no_connection = findViewById(R.id.layout_no_connection);

        findViewById(R.id.button_coba_lagi).setOnClickListener(v -> requestInq());

        materialEditTextIdPelanggan = findViewById(R.id.materialEditTextIdPelanggan);
        materialEditTextIdPelanggan.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(@NonNull CharSequence s, int start, int before, int count) {
                //here is your code
                countText = s.length();
                if (s.length() == 0) {
                    checkboxSimpanId.setVisibility(View.VISIBLE);

                    checkboxSimpanId.setChecked(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        appButtonItemTabIdPelanggan = findViewById(R.id.appButtonItemTabIdPelanggan);
        appButtonItemTabIdPelanggan.setOnClickListener(v -> requestLayout(0));

        appButtonItemTabDaftarPelanggan = findViewById(R.id.appButtonItemTabDaftarPelanggan);
        appButtonItemTabDaftarPelanggan.setOnClickListener(v -> requestLayout(1));

        imageViewAddressBook = findViewById(R.id.imageViewAddressBook);
        imageViewAddressBook.setOnClickListener(v -> requestLayout(1));

        checkboxSimpanId = findViewById(R.id.checkboxSimpanId);
        checkboxSimpanId.setOnClickListener(view -> {
            if (((CheckBox) view).isChecked()) {
                Timber.d("onClick: true ");
                isSave = "true";
            } else {
                Timber.d("onClick: false");
                isSave = "false";
            }
        });

        button_lanjutkan = findViewById(R.id.button_lanjutkan);
        button_lanjutkan.setOnClickListener(view -> {

            if (materialEditTextIdPelanggan.getText().toString().isEmpty()) {
                materialEditTextIdPelanggan.setAnimation(animShake);
                materialEditTextIdPelanggan.startAnimation(animShake);
                materialEditTextIdPelanggan.setError("ID Pelanggan Tidak Boleh Kosong");
                Device.vibrate(this);
                return;
            }

            requestInq();
        });

        requestPopUpPromo("GAS");
        navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));
        attachKeyboardListeners();
    }

    private void requestInq() {
        logEventFireBase(ProdukGroup.PERTAGAS, ProdukCode.PERTAGAS, EventParam.EVENT_ACTION_REQUEST_INQ, EventParam.EVENT_SUCCESS, TAG);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestPertagasInquiry(materialEditTextIdPelanggan.getText().toString(), isSave));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithJSONObjectResponse(this, jsonObject, ActionCode.INQ, this);
        @SuppressLint("InflateParams")
        ViewGroup parent = findViewById(R.id.contentHost);
        final FrameLayout view = (FrameLayout) View.inflate(this, R.layout.loading_bar_full_dialog, parent);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText(R.string.progress_bar_wording);
        openProgressBarDialog(this, view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scanner, menu);
        new Handler().post(() -> {
            menuItemView = findViewById(R.id.action_right_scanner);
            if (PreferenceClass.getInt(TAG, 0) != 1) {
                showCaseFirst(PertagasActivity.this, "", getResources().getString(R.string.content_icon_scanner), menuItemView);

                mGbuilder.setGuideListener(view -> {
                    switch (view.getId()) {
                        case R.id.action_right_scanner:
                            mGbuilder
                                    .setTitle("")
                                    .setContentText(getResources().getString(R.string.content_icon_book)).setGravity(GuideView.Gravity.center)
                                    .setDismissType(GuideView.DismissType.anywhere)
                                    .setTargetView(imageViewAddressBook)
                                    .build();
                            break;
                        case R.id.imageViewAddressBook:
                            mGbuilder
                                    .setTitle("")
                                    .setContentText(getResources().getString(R.string.content_cek_simpan)).setGravity(GuideView.Gravity.center)
                                    .setDismissType(GuideView.DismissType.anywhere)
                                    .setTargetView(checkboxSimpanId)
                                    .build();
                            break;

                        case R.id.checkboxSimpanId:
                            PreferenceClass.putInt(TAG, 1);
                            return;
                    }
                    mGuideView = mGbuilder.build();
                    mGuideView.show();
                });
                mGuideView = mGbuilder.build();
                mGuideView.show();
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_right_drawer) {

            openTopDialog(false);
        } else if (id == R.id.action_right_scanner) {

            openScanner(PertagasActivity.this);
        } else if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (bottom_toolbar.getVisibility() == View.GONE) {
            closeKeyboard(this);
        }
        finish();
        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


    }

    private void requestLayout(int selectedTab) {
        Timber.d("requestLayout: " + selectedTab);
        switch (selectedTab) {
            case 0:
                checkboxSimpanId.setVisibility(View.VISIBLE);
                checkboxSimpanId.setChecked(false);
                materialEditTextIdPelanggan.setText("");
                appButtonItemTabIdPelanggan.setBackgroundDrawable(ContextCompat.getDrawable(PertagasActivity.this, R.drawable.selector_tab_btn));
                appButtonItemTabDaftarPelanggan.setBackgroundDrawable(ContextCompat.getDrawable(PertagasActivity.this, R.drawable.unselector_tab_btn));
                appButtonItemTabIdPelanggan.setTextColor(ContextCompat.getColor(PertagasActivity.this, R.color.md_white_1000));
                appButtonItemTabDaftarPelanggan.setTextColor(ContextCompat.getColor(PertagasActivity.this, R.color.md_grey_300));
                break;
            case 1:
                appButtonItemTabIdPelanggan.setBackgroundDrawable(ContextCompat.getDrawable(PertagasActivity.this, R.drawable.unselector_tab_btn));
                appButtonItemTabDaftarPelanggan.setBackgroundDrawable(ContextCompat.getDrawable(PertagasActivity.this, R.drawable.selector_tab_btn));
                appButtonItemTabIdPelanggan.setTextColor(ContextCompat.getColor(PertagasActivity.this, R.color.md_grey_300));
                appButtonItemTabDaftarPelanggan.setTextColor(ContextCompat.getColor(PertagasActivity.this, R.color.md_white_1000));

                Intent intent = new Intent(PertagasActivity.this, DaftarPelangganActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("title", "Pelanggan PERTAGAS");
                intent.putExtra("product", ProdukCode.PERTAGAS);
                intent.putExtra("hint", R.string.hint_cari_pelanggan);
                startActivityForResult(intent, ActionCode.LIST_PELANGGAN);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == ActionCode.LIST_PELANGGAN) {
                checkboxSimpanId.setVisibility(View.GONE);
                checkboxSimpanId.setChecked(false);
                String idPel = data.getStringExtra("id_pel");
                materialEditTextIdPelanggan.setText(idPel);
            } else if (requestCode == 2) {
                navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));
                requestLayout(0);
            } else if (requestCode == ActionCode.BARCODE) {
                String idPel = data.getStringExtra(Intents.Scan.RESULT);
                materialEditTextIdPelanggan.setText(idPel);
            }
        } else if (resultCode == AppCompatActivity.RESULT_CANCELED) {
            if (requestCode == ActionCode.LIST_PELANGGAN) {
                checkboxSimpanId.setVisibility(View.VISIBLE);
                checkboxSimpanId.setChecked(false);
            }
        }
    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        Timber.d("onSuccess: " + response.toString());
        if (actionCode == ActionCode.INQ) {
            closeProgressBarDialog();
            if (layout_no_connection.getVisibility() == View.VISIBLE) {
                layout_no_connection.setVisibility(View.GONE);
            }
            if (linMain.getVisibility() == View.GONE) {
                linMain.setVisibility(View.VISIBLE);
            }

            InqModel inqModel = gson.fromJson(response.toString(), InqModel.class);
            switch (inqModel.getResponse_code()) {
                case ResponseCode.SUCCESS:
                    logEventFireBase(ProdukGroup.PERTAGAS, ProdukCode.PERTAGAS, EventParam.EVENT_ACTION_RESULT_INQ, EventParam.EVENT_SUCCESS, TAG);
                    PreferenceClass.putString("saldo", FormatString.CurencyIDR(inqModel.getSaldo()));
                    PreferenceClass.putJSONObject("inqResult", response);
                    Intent intent = new Intent(PertagasActivity.this, InqueryResultActivity.class);

                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("title", "PERTAGAS");
                    intent.putExtra("produkCode", ProdukCode.PERTAGAS);
                    intent.putExtra("subject", "PERTAGAS");
                    intent.putExtra("produkGroup", ProdukGroup.PERTAGAS);

                    intent.putExtra("id_pel", materialEditTextIdPelanggan.getText().toString());
                    startActivityForResult(intent, 2);
                    break;
                case "03":
                    new_popup_alert_session(this, "Informasi", inqModel.getResponse_desc());
                    break;
                default:
                    logEventFireBase(ProdukGroup.PERTAGAS, ProdukCode.PERTAGAS, EventParam.EVENT_ACTION_RESULT_INQ, EventParam.EVENT_NOT_SUCCESS, TAG);
                    materialEditTextIdPelanggan.setError(inqModel.getResponse_desc());
                    break;
            }
        } else if (actionCode == ActionCode.CEK_SALDO) {
            Timber.d("onSuccess: " + response.toString());
            SaldoModel saldoModel = gson.fromJson(response.toString(), SaldoModel.class);
            if (saldoModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                PreferenceClass.putString("saldo", FormatString.CurencyIDR(saldoModel.getResponse_value()));
                navigationBottomCustom((RelativeLayout) findViewById(R.id.bottom_toolbar), PreferenceClass.getString("saldo", "0"));
            }
        }
    }

    @Override
    public void onFailure(int actionCode, @NonNull String responseCode, String responseDescription, Throwable throwable) {
        if (responseCode.equals(NETWORK_ERROR)) {
            if (actionCode == ActionCode.INQ) {
                linMain.setVisibility(View.GONE);
                layout_no_connection.setVisibility(View.VISIBLE);
            }
        }
        if (actionCode == ActionCode.INQ) {
            closeProgressBarDialog();
        }
        Timber.d("onFailure: " + responseCode + " " + responseDescription);
    }

    @Override
    protected void onShowKeyboard(int keyboardHeight) {
        // do things when keyboard is shown
        Timber.d("onShowKeyboard: " + keyboardHeight);
        bottom_toolbar.setVisibility(View.GONE);
    }

    @Override
    protected void onHideKeyboard() {
        Timber.d("onHideKeyboard: ");
        // do things when keyboard is hidden
        bottom_toolbar.setVisibility(View.VISIBLE);
    }

    protected void onResume() {
        Timber.d("onResume: ");
        navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));
        super.onResume();
    }
}

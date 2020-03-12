package com.bm.main.fpl.activities;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.bm.main.fpl.templates.CustomFontCheckBox;
import com.bm.main.fpl.templates.MaterialNumberPicker;
import com.bm.main.fpl.templates.showcaseview.GuideView;
import com.bm.main.fpl.utils.Device;
import com.bm.main.fpl.utils.DialogUtils;
import com.bm.main.fpl.utils.FormatString;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;
import com.bm.main.materialedittext.MaterialEditText;
import com.bm.main.pos.R;
import com.google.zxing.client.android.Intents;

import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDate;

import timber.log.Timber;

import static com.bm.main.fpl.constants.ResponseCode.NETWORK_ERROR;

public class PajakActivity extends KeyboardListenerActivity implements JsonObjectResponseCallback {
    private static final String TAG = PajakActivity.class.getSimpleName();
    MaterialEditText materialEditTextIdPelanggan, materialEditTextProduk, materialEditTextTahunPajak;
    CustomFontCheckBox checkboxSimpanId, checkboxSimpanDefaultPajak;
    AppCompatButton button_lanjutkan;
    @Nullable
    private String produkCode;
    AppCompatButton appButtonItemTabIdPelanggan;
    AppCompatButton appButtonItemTabDaftarPelanggan;
    LinearLayout linMain, layout_no_connection;
    private AppCompatButton button_coba_lagi;
    ImageView imageViewAddressBook;
    private View menuItemView;
    private MaterialNumberPicker numberPickerYear;
    private AppCompatButton btnBatal, btnPilih;

    LocalDate currentDate = LocalDate.now();
    private String isSaveDefaultPajak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pajak);
        logEventFireBase(ProdukGroup.PAJAK, ProdukCode.PBB, EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, TAG);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("PAJAK BUMI DAN BANGUNNAN");
        init(0);

        imageViewAddressBook = findViewById(R.id.imageViewAddressBook);

        bottom_toolbar = findViewById(R.id.bottom_toolbar);
        linMain = findViewById(R.id.linMain);
        layout_no_connection = findViewById(R.id.layout_no_connection);

        button_coba_lagi = findViewById(R.id.button_coba_lagi);
        button_coba_lagi.setOnClickListener(v -> requestInq());

        appButtonItemTabIdPelanggan = findViewById(R.id.appButtonItemTabIdPelanggan);
        appButtonItemTabIdPelanggan.setOnClickListener(v -> requestLayout(0));

        appButtonItemTabDaftarPelanggan = findViewById(R.id.appButtonItemTabDaftarPelanggan);
        appButtonItemTabDaftarPelanggan.setOnClickListener(v -> requestLayout(1));

        materialEditTextProduk = findViewById(R.id.materialEditTextProduk);
        materialEditTextProduk.setOnClickListener(view -> callProduk());

        materialEditTextIdPelanggan = findViewById(R.id.materialEditTextIdPelanggan);
        materialEditTextIdPelanggan.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(@NonNull CharSequence s, int start, int before, int count) {
                countText = s.length();
                if (s.length() == 0) {
                    checkboxSimpanId.setVisibility(View.VISIBLE);
                    checkboxSimpanId.setChecked(false);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

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
        checkboxSimpanDefaultPajak = findViewById(R.id.checkboxSimpanDefaultPajak);
        produkCode = PreferenceClass.getString("produkCode", "");
        materialEditTextProduk.setText(PreferenceClass.getString("produkName", ""));
        if (!produkCode.equals("")) {
            checkboxSimpanDefaultPajak.setChecked(true);
        } else {
            callProduk();
        }

        checkboxSimpanDefaultPajak.setOnClickListener(view -> {
            if (((CheckBox) view).isChecked()) {
                Timber.d("onClick: true ");
                isSaveDefaultPajak = "true";
            } else {
                Timber.d("onClick: false");
                isSaveDefaultPajak = "false";
            }
        });

        materialEditTextTahunPajak = findViewById(R.id.materialEditTextTahunPajak);
        materialEditTextTahunPajak.setText(String.valueOf(currentDate.getYear()));
        materialEditTextTahunPajak.setOnClickListener(v -> openBottomSheet(v));

        button_lanjutkan = findViewById(R.id.button_lanjutkan);
        button_lanjutkan.setOnClickListener(view -> {

            if (materialEditTextProduk.getText().toString().isEmpty()) {
                materialEditTextProduk.setAnimation(animShake);
                materialEditTextProduk.startAnimation(animShake);
                materialEditTextProduk.setError("Produk PAJAK Tidak Boleh Kosong");
                Device.vibrate(this);
                return;
            }

            if (materialEditTextIdPelanggan.getText().toString().isEmpty()) {
                materialEditTextIdPelanggan.setAnimation(animShake);
                materialEditTextIdPelanggan.startAnimation(animShake);
                materialEditTextIdPelanggan.setError("Harus terdiri dari 3-24 karakter");

                Device.vibrate(this);
                return;
            }

            if (materialEditTextIdPelanggan.getText().length() < 3) {
                if (materialEditTextIdPelanggan.getText().length() < 3) {
                    materialEditTextIdPelanggan.setAnimation(animShake);
                    materialEditTextIdPelanggan.startAnimation(animShake);
                    materialEditTextIdPelanggan.setError("Harus terdiri dari 3-24 karakter");
                    Device.vibrate(this);
                    return;
                }
            }

            requestInq();
        });
        requestPopUpPromo("PAJAK");

        attachKeyboardListeners();
        navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));
    }

    private void callProduk() {
        runOnUiThread(() -> {
            Intent intent = new Intent(PajakActivity.this, ListProductActivity.class);
            intent.putExtra("title", "Produk Pajak");
            intent.putExtra("product", ProdukGroup.PAJAK);
            intent.putExtra("hint", "Cari PAJAK");
            startActivityForResult(intent, ActionCode.LIST_PRODUK);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scanner, menu);
        new Handler().post(() -> {

            menuItemView = findViewById(R.id.action_right_scanner);
            if (PreferenceClass.getInt(TAG, 0) != 1) {
                showCaseFirst(PajakActivity.this, "", getResources().getString(R.string.content_icon_scanner), menuItemView);

                mGbuilder.setGuideListener(view -> {
                    switch (view.getId()) {
                        case R.id.action_right_scanner:
                            mGbuilder
                                    .setTitle("")
                                    .setContentText(getResources().getString(R.string.content_cek_fav_pajak)).setGravity(GuideView.Gravity.center)
                                    .setDismissType(GuideView.DismissType.anywhere)
                                    .setTargetView(checkboxSimpanDefaultPajak)
                                    .build();
                            break;
                        case R.id.checkboxSimpanDefaultPajak:
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
                                    .setContentText(getResources().getString(R.string.content_pilih_tahun_pajak)).setGravity(GuideView.Gravity.center)
                                    .setDismissType(GuideView.DismissType.anywhere)
                                    .setTargetView(materialEditTextTahunPajak)
                                    .build();
                            break;
                        case R.id.materialEditTextTahunPajak:
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

            openScanner(PajakActivity.this);
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
    }

    private void requestInq() {
        logEventFireBase(ProdukGroup.PAJAK, materialEditTextProduk.getText().toString(), EventParam.EVENT_ACTION_REQUEST_INQ, EventParam.EVENT_SUCCESS, TAG);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestInquiryPajak(produkCode, materialEditTextIdPelanggan.getText().toString(), materialEditTextTahunPajak.getText().toString(), isSave));
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
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        Timber.d("onSuccess: %s", response.toString());
        if (actionCode == ActionCode.INQ) {
            closeProgressBarDialog();
            InqModel inqModel = gson.fromJson(response.toString(), InqModel.class);
            if (layout_no_connection.getVisibility() == View.VISIBLE) {
                layout_no_connection.setVisibility(View.GONE);
            }

            if (linMain.getVisibility() == View.GONE) {
                linMain.setVisibility(View.VISIBLE);
            }

            if (inqModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                logEventFireBase(ProdukGroup.PAJAK, materialEditTextProduk.getText().toString(), EventParam.EVENT_ACTION_RESULT_INQ, EventParam.EVENT_SUCCESS, TAG);
                PreferenceClass.putString("saldo", FormatString.CurencyIDR(inqModel.getSaldo()));
                if (isSaveDefaultPajak.equals("true")) {
                    PreferenceClass.putString("produkCode", produkCode);
                    PreferenceClass.putString("produkName", materialEditTextProduk.getText().toString());
                } else {
                    PreferenceClass.putString("produkCode", "");
                    PreferenceClass.putString("produkName", "");
                }

                PreferenceClass.putJSONObject("inqResult", response);
                Intent intent = new Intent(PajakActivity.this, InqueryResultActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("title", ProdukGroup.PAJAK);
                intent.putExtra("produkCode", produkCode);
                intent.putExtra("produkGroup", ProdukGroup.PAJAK);
                intent.putExtra("subject", materialEditTextProduk.getText().toString());
                intent.putExtra("id_pel", materialEditTextIdPelanggan.getText().toString());
                intent.putExtra("tahun_pajak", materialEditTextTahunPajak.getText().toString());
                startActivityForResult(intent, 2);
            } else if (inqModel.getResponse_code().equals("03")) {
                new_popup_alert_session(this, "Informasi", inqModel.getResponse_desc());
            } else {
                logEventFireBase(ProdukGroup.PAJAK, materialEditTextProduk.getText().toString(), EventParam.EVENT_ACTION_RESULT_INQ, EventParam.EVENT_NOT_SUCCESS, TAG);
                materialEditTextIdPelanggan.setError(inqModel.getResponse_desc());
            }
        }
    }

    @Override
    public void onFailure(int actionCode, @NonNull String responseCode, String responseDescription, @NonNull Throwable throwable) {
        if (responseCode.equals(NETWORK_ERROR)) {
            linMain.setVisibility(View.GONE);
            layout_no_connection.setVisibility(View.VISIBLE);
        }
        if (actionCode == ActionCode.INQ) {
            closeProgressBarDialog();
        }

        Timber.d("onFailure: " + responseCode + " " + responseDescription + " " + throwable.toString());
    }

    private void requestLayout(int selectedTab) {
        Timber.d("requestLayout: %s", selectedTab);
        switch (selectedTab) {
            case 0:
                checkboxSimpanId.setVisibility(View.VISIBLE);
                checkboxSimpanId.setChecked(false);
                materialEditTextIdPelanggan.setText("");
                appButtonItemTabIdPelanggan.setBackgroundDrawable(ContextCompat.getDrawable(PajakActivity.this, R.drawable.selector_tab_btn));
                appButtonItemTabDaftarPelanggan.setBackgroundDrawable(ContextCompat.getDrawable(PajakActivity.this, R.drawable.unselector_tab_btn));
                appButtonItemTabIdPelanggan.setTextColor(ContextCompat.getColor(PajakActivity.this, R.color.md_white_1000));
                appButtonItemTabDaftarPelanggan.setTextColor(ContextCompat.getColor(PajakActivity.this, R.color.md_grey_300));
                break;
            case 1:
                appButtonItemTabIdPelanggan.setBackgroundDrawable(ContextCompat.getDrawable(PajakActivity.this, R.drawable.unselector_tab_btn));
                appButtonItemTabDaftarPelanggan.setBackgroundDrawable(ContextCompat.getDrawable(PajakActivity.this, R.drawable.selector_tab_btn));
                appButtonItemTabIdPelanggan.setTextColor(ContextCompat.getColor(PajakActivity.this, R.color.md_grey_300));
                appButtonItemTabDaftarPelanggan.setTextColor(ContextCompat.getColor(PajakActivity.this, R.color.md_white_1000));
                if (materialEditTextProduk.getText().toString().isEmpty()) {
                    materialEditTextProduk.setAnimation(animShake);
                    materialEditTextProduk.startAnimation(animShake);
                    materialEditTextProduk.setError("Produk PAJAK Tidak Boleh Kosong");
                    Device.vibrate(this);
                    requestLayout(0);
                } else {
                    Intent intent = new Intent(this, DaftarPelangganActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("title", "Pelanggan PAJAK");
                    intent.putExtra("product", produkCode);
                    startActivityForResult(intent, ActionCode.LIST_PELANGGAN);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Timber.d("onActivityResult: " + requestCode + " " + requestCode);
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == ActionCode.LIST_PRODUK) {
                produkCode = data.getStringExtra("kodeProduk");
                materialEditTextProduk.setText(data.getStringExtra("namaProduk"));
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.showSoftInput(materialEditTextIdPelanggan, InputMethodManager.SHOW_IMPLICIT);
                }
            } else if (requestCode == ActionCode.LIST_PELANGGAN) {
                String idPel = data.getStringExtra("id_pel").equals("") ? "" : data.getStringExtra("id_pel");
                materialEditTextIdPelanggan.setText(idPel);
                checkboxSimpanId.setVisibility(View.GONE);
                checkboxSimpanId.setChecked(false);
            } else if (requestCode == 2) {
                navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));
                requestLayout(0);

                produkCode = PreferenceClass.getString("produkCode", "");
                materialEditTextProduk.setText(PreferenceClass.getString("produkName", ""));
                if (!produkCode.equals("")) {
                    checkboxSimpanDefaultPajak.setChecked(true);
                } else {
                    checkboxSimpanDefaultPajak.setChecked(false);
                    materialEditTextProduk.setText("");
                }
                materialEditTextProduk.requestFocus();
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
    protected void onShowKeyboard(int keyboardHeight) {
        Timber.d("onShowKeyboard: " + keyboardHeight);
        bottom_toolbar.setVisibility(View.GONE);
    }

    @Override
    protected void onHideKeyboard() {
        Timber.d("onHideKeyboard: ");
        bottom_toolbar.setVisibility(View.VISIBLE);
    }

    protected void onResume() {
        Timber.d("onResume: ");
        navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));
        super.onResume();
    }

    public void openBottomSheet(View v) {
        final FrameLayout view = (FrameLayout) View.inflate(this, R.layout.bottom_sheet_year, null);
        numberPickerYear = view.findViewById(R.id.numberPickerYear);

        numberPickerYear.setMaxValue(currentDate.getYear());
        numberPickerYear.setMinValue(currentDate.minusYears(20).getYear());
        numberPickerYear.setValue(currentDate.getYear());
        btnBatal = view.findViewById(R.id.btn_batal);
        btnPilih = view.findViewById(R.id.btn_pilih);
        btnBatal.setOnClickListener(v12 -> DialogUtils.closeBootomSheetDialog());
        btnPilih.setOnClickListener(v1 -> {
            DialogUtils.closeBootomSheetDialog();
            materialEditTextTahunPajak.setText(String.valueOf(numberPickerYear.getValue()));
        });
        numberPickerYear.setOnValueChangedListener((picker, oldVal, newVal) -> {
        });

        DialogUtils.openBottomSheetDialog(this, view);
    }
}

package com.bm.main.fpl.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bm.main.fpl.utils.DetectConnection;
import com.bm.main.fpl.utils.Device;
import com.bm.main.scm.R;
import com.bm.main.scm.SBFApplication;
import com.bm.main.fpl.adapters.ListGridPaketAdapter;
import com.bm.main.fpl.adapters.ListPaketOutletAdapter;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.models.PaketModel;
import com.bm.main.fpl.models.PaketModelModelResponse_valueTipe_loket_detail;
import com.bm.main.fpl.models.PaketModelModelResponse_valueTipe_loket_footer;
import com.bm.main.fpl.templates.indicators.AVLoadingIndicatorView;
import com.bm.main.fpl.utils.FormatString;
import com.bm.main.fpl.utils.RequestUtils;
import com.bm.main.fpl.utils.saring_karakter;
import com.bm.main.materialedittext.MaterialEditText;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class RegistrasiActivity extends BaseActivity implements ProgressResponseCallback, ListGridPaketAdapter.OnClickProduk {
    private static final String TAG = RegistrasiActivity.class.getSimpleName();
    @Nullable
    String paketCode = "", paketName = "", propCode = "", kabCode = "", kecCode = "", kodePos = "", paketAlias = "", paketUrl = "", paketHarga = "";
    PaketModel paketModel;
    RecyclerView recyclerViewPaketOutlet;
    ListGridPaketAdapter listGridPaketAdapter;
    ListPaketOutletAdapter listPaketOutletAdapter;
    RecyclerView.LayoutManager mLayoutManagerPaket;
    @NonNull
    ArrayList<PaketModel.Response_value> dataPaket = new ArrayList<>();
    TextView textViewNominalOutlet, textViewTypeOutlet;

    LinearLayout linPaket;
    TextView imageInfoPaket;
    MaterialEditText materialEditTextIdUplineReg, materialEditTextNamaReg,
            materialEditTextNamaTokoReg, materialEditTextNoHpReg, materialEditTextEmailReg, materialEditTextAlamatReg,
            materialEditTextPropinsiReg, materialEditTextKotaReg, materialEditTextKecamatanReg, materialEditTextKodePosReg;
    AppCompatButton button_registrasi;

    EditText etName, etNoHp, etEmail, etNamaToko, etAlamat, etProvinsi, etKota, etKecamatan, etKodePos;
    Button btnRegister;

    TextView textViewErrorPaket;
    private ScrollView scrollViewRegistrasi;

    AVLoadingIndicatorView avi;

    @NonNull
    String product = "list_paket_reg";
    @NonNull
    ArrayList<PaketModelModelResponse_valueTipe_loket_detail> dataDetailPaket = new ArrayList<>();
    @NonNull
    ArrayList<PaketModelModelResponse_valueTipe_loket_footer> dataDetailPaketFooter = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        logEventFireBase("Registrasi", "Registrasi", EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, TAG);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Daftar");
        init(0);

        scrollViewRegistrasi = findViewById(R.id.scrollViewRegistrasi);
        /*
        textViewErrorPaket = findViewById(R.id.textViewErrorPaket);
        avi = findViewById(R.id.avi);
        materialEditTextIdUplineReg = findViewById(R.id.materialEditTextIdUplineReg);
        materialEditTextIdUplineReg.setText(PreferenceClass.getUpline());
        Log.d(TAG, "onCreate: " + PreferenceClass.getUpline());
        if (!PreferenceClass.getUpline().isEmpty() || !PreferenceClass.getUpline().equals("")) {
            requestGetUpline(PreferenceClass.getUpline());
        }
        materialEditTextNamaReg = findViewById(R.id.materialEditTextNamaReg);
        etNamaToko = findViewById(R.id.etNamaToko);
        etNoHp = findViewById(R.id.etNoHp);
        etEmail = findViewById(R.id.etEmail);
        etAlamat = findViewById(R.id.etAlamat);
        etProvinsi = findViewById(R.id.etProvinsi);
        etProvinsi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(@NonNull View v, boolean hasFocus) {
                Log.d("member", "onFocusChange: " + hasFocus + " " + v.getId());
                if (hasFocus) {
                    Intent intent = new Intent(RegistrasiActivity.this, ListPropinsiActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivityForResult(intent, ActionCode.LIST_PROPINSI);
                }
            }
        });
        etProvinsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrasiActivity.this, ListPropinsiActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, ActionCode.LIST_PROPINSI);
            }
        });
        etKota = findViewById(R.id.etKota);
        etKota.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (etProvinsi.getEditableText().toString().isEmpty()) {
                        etProvinsi.setAnimation(BaseActivity.animShake);
                        etProvinsi.startAnimation(BaseActivity.animShake);
                        etProvinsi.setError("Propinsi Tidak Boleh Kosong");
                        etKota.requestFocus();
                        Device.vibrate(RegistrasiActivity.this);

                        return;
                    }
                    Intent intent = new Intent(RegistrasiActivity.this, ListKabupatenActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("propCode", propCode);
                    startActivityForResult(intent, ActionCode.LIST_KABUPATEN);
                }
            }
        });
        etKota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etProvinsi.getEditableText().toString().isEmpty()) {
                    etProvinsi.setAnimation(BaseActivity.animShake);
                    etProvinsi.startAnimation(BaseActivity.animShake);
                    etProvinsi.setError("Propinsi Tidak Boleh Kosong");
                    Device.vibrate(RegistrasiActivity.this);

                    return;
                }
                Intent intent = new Intent(RegistrasiActivity.this, ListKabupatenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("propCode", propCode);
                startActivityForResult(intent, ActionCode.LIST_KABUPATEN);
            }
        });
        etKecamatan = findViewById(R.id.etKecamatan);
        etKecamatan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (etKota.getEditableText().toString().isEmpty()) {
                        etKota.setAnimation(BaseActivity.animShake);
                        etKota.startAnimation(BaseActivity.animShake);
                        etKota.setError("Kabupaten/Kota Tidak Boleh Kosong");
                        etKecamatan.requestFocus();
                        Device.vibrate(RegistrasiActivity.this);

                        return;
                    }
                    Intent intent = new Intent(RegistrasiActivity.this, ListKecamatanActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("kabCode", kabCode);
                    startActivityForResult(intent, ActionCode.LIST_KECAMATAN);
                }
            }
        });
        etKecamatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etKota.getEditableText().toString().isEmpty()) {
                    etKota.setAnimation(BaseActivity.animShake);
                    etKota.startAnimation(BaseActivity.animShake);
                    etKota.setError("Kabupaten/Kota Tidak Boleh Kosong");
                    Device.vibrate(RegistrasiActivity.this);

                    return;
                }
                Intent intent = new Intent(RegistrasiActivity.this, ListKecamatanActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("kabCode", kabCode);
                startActivityForResult(intent, ActionCode.LIST_KECAMATAN);
            }
        });

        etKodePos = findViewById(R.id.etKodePos);
        etKodePos.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (etKecamatan.getEditableText().toString().isEmpty()) {
                        etKecamatan.setAnimation(BaseActivity.animShake);
                        etKecamatan.startAnimation(BaseActivity.animShake);
                        etKecamatan.setError("Kecamatan Tidak Boleh Kosong");
                        etKodePos.requestFocus();
                        Device.vibrate(RegistrasiActivity.this);

                        return;
                    }
                    Intent intent = new Intent(RegistrasiActivity.this, ListKodePosActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("kecCode", kecCode);
                    startActivityForResult(intent, ActionCode.LIST_KODEPOS);
                }
            }
        });
        etKodePos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etKecamatan.getEditableText().toString().isEmpty()) {
                    etKecamatan.setAnimation(BaseActivity.animShake);
                    etKecamatan.startAnimation(BaseActivity.animShake);
                    etKecamatan.setError("Kecamatan Tidak Boleh Kosong");
                    Device.vibrate(RegistrasiActivity.this);

                    return;
                }
                Intent intent = new Intent(RegistrasiActivity.this, ListKodePosActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("kecCode", kecCode);
                startActivityForResult(intent, ActionCode.LIST_KODEPOS);
            }
        });


        recyclerViewPaketOutlet = findViewById(R.id.recyclerViewPaketOutlet);
        textViewNominalOutlet = findViewById(R.id.textViewNominalOutlet);
        textViewTypeOutlet = findViewById(R.id.textViewTypeOutlet);
        linPaket = findViewById(R.id.linPaket);
        imageInfoPaket = findViewById(R.id.imageInfoPaket);
        imageInfoPaket.setOnClickListener(view -> {
            ViewGroup parent = findViewById(R.id.linPromo);
            final View viewx = View.inflate(RegistrasiActivity.this, R.layout.popup_detail_paket, parent);
            DialogUtils.openPaketDetailDialog(RegistrasiActivity.this, viewx, dataDetailPaket, dataDetailPaketFooter, paketAlias, paketUrl, paketHarga, listPaketOutletAdapter);
        });


        button_registrasi = findViewById(R.id.button_registrasi);
        button_registrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callRegister();
            }
        });
        mLayoutManagerPaket = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewPaketOutlet.setHasFixedSize(false);
        recyclerViewPaketOutlet.setLayoutManager(mLayoutManagerPaket);

        listGridPaketAdapter = new ListGridPaketAdapter(dataPaket, this, this);
        recyclerViewPaketOutlet.setAdapter(listGridPaketAdapter);*/

        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_mail);
        etNoHp = findViewById(R.id.et_phone);
        etNamaToko = findViewById(R.id.et_nama_toko);
        etAlamat = findViewById(R.id.et_address);
        etProvinsi = findViewById(R.id.et_province);
        etProvinsi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(@NonNull View v, boolean hasFocus) {
                Log.d("member", "onFocusChange: " + hasFocus + " " + v.getId());
                if (hasFocus) {
                    Intent intent = new Intent(RegistrasiActivity.this, ListPropinsiActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivityForResult(intent, ActionCode.LIST_PROPINSI);
                }
            }
        });
        etProvinsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrasiActivity.this, ListPropinsiActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, ActionCode.LIST_PROPINSI);
            }
        });
        etKota = findViewById(R.id.et_city);
        etKota.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (etProvinsi.getEditableText().toString().isEmpty()) {
                        etProvinsi.setAnimation(BaseActivity.animShake);
                        etProvinsi.startAnimation(BaseActivity.animShake);
                        etProvinsi.setError("Propinsi Tidak Boleh Kosong");
                        etProvinsi.requestFocus();
                        Device.vibrate(RegistrasiActivity.this);
                        return;
                    }
                    Intent intent = new Intent(RegistrasiActivity.this, ListKabupatenActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("propCode", propCode);
                    startActivityForResult(intent, ActionCode.LIST_KABUPATEN);
                }
            }
        });
        etKota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etProvinsi.getEditableText().toString().isEmpty()) {
                    etProvinsi.setAnimation(BaseActivity.animShake);
                    etProvinsi.startAnimation(BaseActivity.animShake);
                    etProvinsi.setError("Propinsi Tidak Boleh Kosong");
                    Device.vibrate(RegistrasiActivity.this);
                    return;
                }
                Intent intent = new Intent(RegistrasiActivity.this, ListKabupatenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("propCode", propCode);
                startActivityForResult(intent, ActionCode.LIST_KABUPATEN);
            }
        });
        etKecamatan = findViewById(R.id.et_district);
        etKecamatan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (etKota.getEditableText().toString().isEmpty()) {
                        etKota.setAnimation(BaseActivity.animShake);
                        etKota.startAnimation(BaseActivity.animShake);
                        etKota.setError("Kabupaten/Kota Tidak Boleh Kosong");
                        etKota.requestFocus();
                        Device.vibrate(RegistrasiActivity.this);
                        return;
                    }
                    Intent intent = new Intent(RegistrasiActivity.this, ListKecamatanActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("kabCode", kabCode);
                    startActivityForResult(intent, ActionCode.LIST_KECAMATAN);
                }
            }
        });
        etKecamatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etKota.getEditableText().toString().isEmpty()) {
                    etKota.setAnimation(BaseActivity.animShake);
                    etKota.startAnimation(BaseActivity.animShake);
                    etKota.setError("Kabupaten/Kota Tidak Boleh Kosong");
                    Device.vibrate(RegistrasiActivity.this);
                    return;
                }
                Intent intent = new Intent(RegistrasiActivity.this, ListKecamatanActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("kabCode", kabCode);
                startActivityForResult(intent, ActionCode.LIST_KECAMATAN);
            }
        });

        etKodePos = findViewById(R.id.et_postcode);
        etKodePos.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (etKecamatan.getEditableText().toString().isEmpty()) {
                        etKecamatan.setAnimation(BaseActivity.animShake);
                        etKecamatan.startAnimation(BaseActivity.animShake);
                        etKecamatan.setError("Kecamatan Tidak Boleh Kosong");
                        etKecamatan.requestFocus();
                        Device.vibrate(RegistrasiActivity.this);
                        return;
                    }
                    Intent intent = new Intent(RegistrasiActivity.this, ListKodePosActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("kecCode", kecCode);
                    startActivityForResult(intent, ActionCode.LIST_KODEPOS);
                }
            }
        });
        etKodePos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etKecamatan.getEditableText().toString().isEmpty()) {
                    etKecamatan.setAnimation(BaseActivity.animShake);
                    etKecamatan.startAnimation(BaseActivity.animShake);
                    etKecamatan.setError("Kecamatan Tidak Boleh Kosong");
                    Device.vibrate(RegistrasiActivity.this);
                    return;
                }
                Intent intent = new Intent(RegistrasiActivity.this, ListKodePosActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("kecCode", kecCode);
                startActivityForResult(intent, ActionCode.LIST_KODEPOS);
            }
        });
        btnRegister = findViewById(R.id.btn_register_now);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callRegister();
            }
        });
    }

    private void callRegister() {
//        if (!checkInternetDialog()){
//            return;
//        }

        if (etName.getEditableText().toString().trim().isEmpty() || etName.getEditableText().toString().trim().equals("")) {
            etName.setAnimation(animShake);
            etName.startAnimation(animShake);
            etName.setError("Nama Tidak Boleh Kosong");
            Device.vibrate(RegistrasiActivity.this);
            scrollViewRegistrasi.fullScroll(ScrollView.FOCUS_DOWN);
            return;
        }

        if (etNoHp.getEditableText().toString().trim().isEmpty() || etNoHp.getEditableText().toString().trim().equals("")) {
            etNoHp.setAnimation(animShake);
            etNoHp.startAnimation(animShake);
            etNoHp.setError("No Handphone Tidak Boleh Kosong");
            Device.vibrate(RegistrasiActivity.this);
            return;
        }

        boolean isValid = new saring_karakter().isEmailValid(etEmail.getEditableText().toString());
        if (!isValid) {
            etEmail.setAnimation(animShake);
            etEmail.startAnimation(animShake);
            etEmail.setError("Format email salah, Pastikan format email : contoh@sbf.com");
            Device.vibrate(RegistrasiActivity.this);
            return;
        }

        if (etNamaToko.getEditableText().toString().trim().isEmpty() || etNamaToko.getEditableText().toString().trim().equals("")) {
            etNamaToko.setAnimation(animShake);
            etNamaToko.startAnimation(animShake);
            etNamaToko.setError("Nama Toko Tidak Boleh Kosong");
            Device.vibrate(RegistrasiActivity.this);
            scrollViewRegistrasi.fullScroll(ScrollView.FOCUS_DOWN);
            return;
        }

        if (etAlamat.getEditableText().toString().trim().isEmpty() || etAlamat.getEditableText().toString().trim().equals("")) {
            etAlamat.setAnimation(animShake);
            etAlamat.startAnimation(animShake);
            etAlamat.setError("Alamat Tidak Boleh Kosong");
            Device.vibrate(RegistrasiActivity.this);
            return;
        }

        if (etProvinsi.getEditableText().toString().trim().isEmpty() || etProvinsi.getEditableText().toString().trim().equals("") || propCode.equals("")) {
            etProvinsi.setAnimation(animShake);
            etProvinsi.startAnimation(animShake);
            etProvinsi.setError("Propinsi Tidak Boleh Kosong");
            Device.vibrate(RegistrasiActivity.this);
            return;
        }

        if (etKota.getEditableText().toString().trim().isEmpty() || etKota.getEditableText().toString().trim().equals("") || kabCode.equals("")) {
            etKota.setAnimation(animShake);
            etKota.startAnimation(animShake);
            etKota.setError("Kota/Kabupaten Tidak Boleh Kosong");
            Device.vibrate(RegistrasiActivity.this);
            return;
        }

        if (etKecamatan.getEditableText().toString().trim().isEmpty() || etKecamatan.getEditableText().toString().trim().equals("") || kecCode.equals("")) {
            etKecamatan.setAnimation(animShake);
            etKecamatan.startAnimation(animShake);
            etKecamatan.setError("Kecamatan Tidak Boleh Kosong");
            Device.vibrate(RegistrasiActivity.this);
            return;
        }

        if (etKodePos.getText().toString().trim().isEmpty() || etKodePos.getText().toString().trim().equals("") || kodePos.equals("")) {
            etKodePos.setAnimation(animShake);
            etKodePos.startAnimation(animShake);
            etKodePos.setError("Kodepos Tidak Boleh Kosong");
            Device.vibrate(RegistrasiActivity.this);
            return;
        }
        requestRegistrasi();
    }

    private void requestRegistrasi() {
        logEventFireBase("Registrasi", "Registrasi", EventParam.EVENT_ACTION_REQUEST_REGISTRASI, EventParam.EVENT_SUCCESS, TAG);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestRegister(
                    etNoHp.getEditableText().toString(),
                    etName.getEditableText().toString(),
                    etNamaToko.getEditableText().toString(),
                    etAlamat.getEditableText().toString(),
                    propCode,
                    kabCode,
                    kecCode,
                    etEmail.getEditableText().toString(),
                    kodePos,
                    paketCode,
                    ""));
                    //materialEditTextIdUplineReg.getEditableText().toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.REQUEST_MEMBER, this);
        @SuppressLint("InflateParams")
        ViewGroup parent = findViewById(R.id.contentHost);
        final FrameLayout view = (FrameLayout) View.inflate(this, R.layout.loading_bar_full_dialog, parent);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText(R.string.progress_bar_wording);
        openProgressBarDialog(this, view);
    }

    private void requestListPaket() {
        //    StringJson stringJson = new StringJson(getActivity());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestLocket_register(materialEditTextIdUplineReg.getEditableText().toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.LIST_PRODUK, this);
    }

    private void requestGetUpline(String upline) {
        //    StringJson stringJson = new StringJson(getActivity());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.getReferalUpline(upline));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.GET_UPLINE, this);
    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        Log.d(TAG, "onSuccess: " + response.toString());
        if (actionCode == ActionCode.LIST_PRODUK) {
//            dataPaket.clear();
            if (avi.getVisibility() == View.VISIBLE) {
                avi.setVisibility(View.GONE);
            }
            paketModel = gson.fromJson(response.toString(), PaketModel.class);
            if (paketModel.getResponse_code().equals(ResponseCode.SUCCESS)) {

                if (recyclerViewPaketOutlet.getVisibility() == View.GONE) {
                    recyclerViewPaketOutlet.setVisibility(View.VISIBLE);
                }
                dataPaket.clear();
                dataPaket.addAll(paketModel.getResponse_value());

                listGridPaketAdapter.notifyDataSetChanged();
            } else {
                if (recyclerViewPaketOutlet.getVisibility() == View.VISIBLE) {
                    recyclerViewPaketOutlet.setVisibility(View.GONE);
                }
                if (textViewErrorPaket.getVisibility() == View.GONE) {
                    textViewErrorPaket.setVisibility(View.VISIBLE);
                    textViewErrorPaket.setText(paketModel.getResponse_desc());
                }
            }
        } else if (actionCode == ActionCode.REQUEST_MEMBER) {
            closeProgressBarDialog();
            try {
                if (response.getString("response_code").equals(ResponseCode.SUCCESS)) {
                    logEventFireBase("Registrasi", "Registrasi", EventParam.EVENT_ACTION_RESULT_REGISTRASI, EventParam.EVENT_SUCCESS, TAG);
                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this);
                    builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                            .setBackgroundColor(ContextCompat.getColor(this, R.color.black_overlay))
                            .setTitle("Berhasil")
                            .setMessage(response.getString("response_desc"))
                            .setTextGravity(Gravity.CENTER_HORIZONTAL)
                            .setHeaderView(R.layout.dialog_header_layout)
                            .setCancelable(true);

                    builder.addButton("Tutup", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, getButtonGravity(1), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(@NonNull DialogInterface dialog, int which) {
                            dialog.dismiss();
                            onBackPressed();
                        }
                    });
                    builder.show();

                    HashMap<String, String> eventMap = new HashMap<>();
                    eventMap.put("upline", "");//materialEditTextIdUplineReg.getEditableText().toString());
                    eventMap.put("is_register", "1");
                    eventMap.put("paket", paketName);
                    eventMap.put("email", etEmail.getEditableText().toString());
                    eventMap.put("nama", etName.getEditableText().toString());
                    eventMap.put("no_handphone", etNoHp.getEditableText().toString());
                    eventMap.put("kabupaten", etKota.getEditableText().toString());
                    eventMap.put("propinsi", etProvinsi.getEditableText().toString());

                    SBFApplication.sendEvent(FirebaseAnalytics.Event.SIGN_UP, eventMap);
                } else {
                    logEventFireBase("Registrasi", "Registrasi", EventParam.EVENT_ACTION_RESULT_REGISTRASI, EventParam.EVENT_NOT_SUCCESS, TAG);
                    new_popup_alert_regitrasi(this, response.getString("response_desc"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {
        if (actionCode == ActionCode.REQUEST_MEMBER) {
            closeProgressBarDialog();
        }
        new_popup_alert_failure(RegistrasiActivity.this, responseDescription);
    }

    @Override
    public void onClickProduk(@NonNull PaketModel.Response_value produk) {
        paketUrl = produk.getTipe_loket_url();
        paketAlias = produk.getTipe_loket_alias();
        paketCode = produk.getTipe_loket_code();
        paketName = produk.getTipe_loket_alias();
        paketHarga = "Rp " + FormatString.CurencyIDR(produk.getTipe_loket_harga());
        textViewNominalOutlet.setText(paketHarga);
        textViewTypeOutlet.setText(produk.getTipe_loket_name());
        switch (paketAlias) {
            case "BASIC":
                imageInfoPaket.setText("Lihat Kelengkapan Paket Basic");
                break;
            case "PROFESSIONAL":
                imageInfoPaket.setText("Lihat Kelengkapan Paket Pro");
                break;
            case "ENTERPRISE":
                imageInfoPaket.setText("Lihat Kelengkapan Paket Enterprise");
                break;
        }

        dataDetailPaket.clear();
        for (int x = 0; x < produk.getTipe_loket_detail().length; x++) {
            dataDetailPaket.add(produk.getTipe_loket_detail()[x]);
        }

        dataDetailPaketFooter.clear();
        for (int x = 0; x < produk.getTipe_loket_detail_footer().length; x++) {
            dataDetailPaketFooter.add(produk.getTipe_loket_detail_footer()[x]);
        }

        listPaketOutletAdapter = new ListPaketOutletAdapter(dataDetailPaket, this);

        if (linPaket.getVisibility() == View.GONE) {
            linPaket.setVisibility(View.VISIBLE);
        }

        if (textViewErrorPaket.getVisibility() == View.VISIBLE) {
            textViewErrorPaket.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == ActionCode.LIST_PROPINSI) {
                propCode = data.getStringExtra("kodePropinsi");
                etProvinsi.setText(data.getStringExtra("namaPropinsi"));
            } else if (requestCode == ActionCode.LIST_KABUPATEN) {
                kabCode = data.getStringExtra("kodeKabupaten");
                etKota.setText(data.getStringExtra("namaKabupaten"));
            } else if (requestCode == ActionCode.LIST_KECAMATAN) {
                kecCode = data.getStringExtra("kodeKecamatan");
                etKecamatan.setText(data.getStringExtra("namaKecamatan"));
            } else if (requestCode == ActionCode.LIST_KODEPOS) {
                kodePos = data.getStringExtra("kodepos");
                etKodePos.setText(data.getStringExtra("detail"));
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
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

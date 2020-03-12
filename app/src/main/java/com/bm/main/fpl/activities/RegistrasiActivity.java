package com.bm.main.fpl.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bm.main.fpl.utils.Device;
import com.bm.main.fpl.utils.DialogUtils;
import com.bm.main.pos.R;
import com.bm.main.pos.SBFApplication;
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
import com.bm.main.fpl.utils.PreferenceClass;
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
    MaterialEditText materialEditTextIdUplineReg, materialEditTextNamaReg, materialEditTextNamaTokoReg, materialEditTextNoHpReg, materialEditTextEmailReg, materialEditTextAlamatReg, materialEditTextPropinsiReg, materialEditTextKotaReg, materialEditTextKecamatanReg, materialEditTextKodePosReg;
    AppCompatButton button_registrasi;

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
        setContentView(R.layout.activity_registrasi);
        logEventFireBase("Registrasi", "Registrasi", EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, TAG);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Daftar");
        init(1);

        scrollViewRegistrasi = findViewById(R.id.scrollViewRegistrasi);
        textViewErrorPaket = findViewById(R.id.textViewErrorPaket);
        avi = findViewById(R.id.avi);
        materialEditTextIdUplineReg = findViewById(R.id.materialEditTextIdUplineReg);
        materialEditTextIdUplineReg.setText(PreferenceClass.getUpline());
        Log.d(TAG, "onCreate: " + PreferenceClass.getUpline());
        if (!PreferenceClass.getUpline().isEmpty() || !PreferenceClass.getUpline().equals("")) {
            requestGetUpline(PreferenceClass.getUpline());
        }
        materialEditTextNamaReg = findViewById(R.id.materialEditTextNamaReg);
        materialEditTextNamaTokoReg = findViewById(R.id.materialEditTextNamaTokoReg);
        materialEditTextNoHpReg = findViewById(R.id.materialEditTextNoHpReg);
        materialEditTextEmailReg = findViewById(R.id.materialEditTextEmailReg);
        materialEditTextAlamatReg = findViewById(R.id.materialEditTextAlamatReg);
        materialEditTextPropinsiReg = findViewById(R.id.materialEditTextPropinsiReg);
        materialEditTextPropinsiReg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        materialEditTextPropinsiReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrasiActivity.this, ListPropinsiActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, ActionCode.LIST_PROPINSI);
            }
        });
        materialEditTextKotaReg = findViewById(R.id.materialEditTextKotaReg);
        materialEditTextKotaReg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (materialEditTextPropinsiReg.getEditableText().toString().isEmpty()) {
                        materialEditTextPropinsiReg.setAnimation(BaseActivity.animShake);
                        materialEditTextPropinsiReg.startAnimation(BaseActivity.animShake);
                        materialEditTextPropinsiReg.setError("Propinsi Tidak Boleh Kosong");
                        materialEditTextKotaReg.requestFocus();
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
        materialEditTextKotaReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (materialEditTextPropinsiReg.getEditableText().toString().isEmpty()) {
                    materialEditTextPropinsiReg.setAnimation(BaseActivity.animShake);
                    materialEditTextPropinsiReg.startAnimation(BaseActivity.animShake);
                    materialEditTextPropinsiReg.setError("Propinsi Tidak Boleh Kosong");
                    Device.vibrate(RegistrasiActivity.this);

                    return;
                }
                Intent intent = new Intent(RegistrasiActivity.this, ListKabupatenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("propCode", propCode);
                startActivityForResult(intent, ActionCode.LIST_KABUPATEN);
            }
        });
        materialEditTextKecamatanReg = findViewById(R.id.materialEditTextKecamatanReg);
        materialEditTextKecamatanReg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (materialEditTextKotaReg.getEditableText().toString().isEmpty()) {
                        materialEditTextKotaReg.setAnimation(BaseActivity.animShake);
                        materialEditTextKotaReg.startAnimation(BaseActivity.animShake);
                        materialEditTextKotaReg.setError("Kabupaten/Kota Tidak Boleh Kosong");
                        materialEditTextKecamatanReg.requestFocus();
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
        materialEditTextKecamatanReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (materialEditTextKotaReg.getEditableText().toString().isEmpty()) {
                    materialEditTextKotaReg.setAnimation(BaseActivity.animShake);
                    materialEditTextKotaReg.startAnimation(BaseActivity.animShake);
                    materialEditTextKotaReg.setError("Kabupaten/Kota Tidak Boleh Kosong");
                    Device.vibrate(RegistrasiActivity.this);

                    return;
                }
                Intent intent = new Intent(RegistrasiActivity.this, ListKecamatanActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("kabCode", kabCode);
                startActivityForResult(intent, ActionCode.LIST_KECAMATAN);
            }
        });

        materialEditTextKodePosReg = findViewById(R.id.materialEditTextKodePosReg);
        materialEditTextKodePosReg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (materialEditTextKecamatanReg.getEditableText().toString().isEmpty()) {
                        materialEditTextKecamatanReg.setAnimation(BaseActivity.animShake);
                        materialEditTextKecamatanReg.startAnimation(BaseActivity.animShake);
                        materialEditTextKecamatanReg.setError("Kecamatan Tidak Boleh Kosong");
                        materialEditTextKodePosReg.requestFocus();
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
        materialEditTextKodePosReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (materialEditTextKecamatanReg.getEditableText().toString().isEmpty()) {
                    materialEditTextKecamatanReg.setAnimation(BaseActivity.animShake);
                    materialEditTextKecamatanReg.startAnimation(BaseActivity.animShake);
                    materialEditTextKecamatanReg.setError("Kecamatan Tidak Boleh Kosong");
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
        recyclerViewPaketOutlet.setAdapter(listGridPaketAdapter);
    }

    private void callRegister() {
        if (materialEditTextNamaReg.getEditableText().toString().trim().isEmpty() || materialEditTextNamaReg.getEditableText().toString().trim().equals("")) {
            materialEditTextNamaReg.setAnimation(animShake);
            materialEditTextNamaReg.startAnimation(animShake);
            materialEditTextNamaReg.setError("Nama Tidak Boleh Kosong");
            Device.vibrate(RegistrasiActivity.this);
            scrollViewRegistrasi.fullScroll(ScrollView.FOCUS_DOWN);
            return;
        }

        if (materialEditTextNamaTokoReg.getEditableText().toString().trim().isEmpty() || materialEditTextNamaTokoReg.getEditableText().toString().trim().equals("")) {
            materialEditTextNamaTokoReg.setAnimation(animShake);
            materialEditTextNamaTokoReg.startAnimation(animShake);
            materialEditTextNamaTokoReg.setError("Nama Toko Tidak Boleh Kosong");
            Device.vibrate(RegistrasiActivity.this);
            scrollViewRegistrasi.fullScroll(ScrollView.FOCUS_DOWN);
            return;
        }

        if (materialEditTextNoHpReg.getEditableText().toString().trim().isEmpty() || materialEditTextNoHpReg.getEditableText().toString().trim().equals("")) {
            materialEditTextNoHpReg.setAnimation(animShake);
            materialEditTextNoHpReg.startAnimation(animShake);
            materialEditTextNoHpReg.setError("No Handphone Tidak Boleh Kosong");
            Device.vibrate(RegistrasiActivity.this);
            return;
        }

        boolean isValid = new saring_karakter().isEmailValid(materialEditTextEmailReg.getEditableText().toString());
        if (!isValid) {
            materialEditTextEmailReg.setAnimation(animShake);
            materialEditTextEmailReg.startAnimation(animShake);
            materialEditTextEmailReg.setError("Format email salah, Pastikan format email : contoh@sbf.com");
            Device.vibrate(RegistrasiActivity.this);
            return;
        }

        if (materialEditTextAlamatReg.getEditableText().toString().trim().isEmpty() || materialEditTextAlamatReg.getEditableText().toString().trim().equals("")) {
            materialEditTextAlamatReg.setAnimation(animShake);
            materialEditTextAlamatReg.startAnimation(animShake);
            materialEditTextAlamatReg.setError("Alamat Tidak Boleh Kosong");
            Device.vibrate(RegistrasiActivity.this);
            return;
        }

        if (materialEditTextPropinsiReg.getEditableText().toString().trim().isEmpty() || materialEditTextPropinsiReg.getEditableText().toString().trim().equals("") || propCode.equals("")) {
            materialEditTextPropinsiReg.setAnimation(animShake);
            materialEditTextPropinsiReg.startAnimation(animShake);
            materialEditTextPropinsiReg.setError("Propinsi Tidak Boleh Kosong");
            Device.vibrate(RegistrasiActivity.this);
            return;
        }

        if (materialEditTextKotaReg.getEditableText().toString().trim().isEmpty() || materialEditTextKotaReg.getEditableText().toString().trim().equals("") || kabCode.equals("")) {
            materialEditTextKotaReg.setAnimation(animShake);
            materialEditTextKotaReg.startAnimation(animShake);
            materialEditTextKotaReg.setError("Kota/Kabupaten Tidak Boleh Kosong");
            Device.vibrate(RegistrasiActivity.this);
            return;
        }

        if (materialEditTextKecamatanReg.getEditableText().toString().trim().isEmpty() || materialEditTextKecamatanReg.getEditableText().toString().trim().equals("") || kecCode.equals("")) {
            materialEditTextKecamatanReg.setAnimation(animShake);
            materialEditTextKecamatanReg.startAnimation(animShake);
            materialEditTextKecamatanReg.setError("Kecamatan Tidak Boleh Kosong");
            Device.vibrate(RegistrasiActivity.this);
            return;
        }

        if (materialEditTextKodePosReg.getText().toString().trim().isEmpty() || materialEditTextKodePosReg.getText().toString().trim().equals("") || kodePos.equals("")) {
            materialEditTextKodePosReg.setAnimation(animShake);
            materialEditTextKodePosReg.startAnimation(animShake);
            materialEditTextKodePosReg.setError("Kodepos Tidak Boleh Kosong");
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
                    materialEditTextNoHpReg.getEditableText().toString(), materialEditTextNamaReg.getEditableText().toString(), materialEditTextNamaTokoReg.getEditableText().toString(), materialEditTextAlamatReg.getEditableText().toString(), propCode, kabCode, kecCode, materialEditTextEmailReg.getEditableText().toString(), kodePos, paketCode, materialEditTextIdUplineReg.getEditableText().toString()));
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
                    eventMap.put("upline", materialEditTextIdUplineReg.getEditableText().toString());
                    eventMap.put("is_register", "1");
                    eventMap.put("paket", paketName);
                    eventMap.put("email", materialEditTextEmailReg.getEditableText().toString());
                    eventMap.put("nama", materialEditTextNamaReg.getEditableText().toString());
                    eventMap.put("no_handphone", materialEditTextNoHpReg.getEditableText().toString());
                    eventMap.put("kabupaten", materialEditTextKotaReg.getEditableText().toString());
                    eventMap.put("propinsi", materialEditTextPropinsiReg.getEditableText().toString());

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
                materialEditTextPropinsiReg.setText(data.getStringExtra("namaPropinsi"));
            } else if (requestCode == ActionCode.LIST_KABUPATEN) {
                kabCode = data.getStringExtra("kodeKabupaten");
                materialEditTextKotaReg.setText(data.getStringExtra("namaKabupaten"));
            } else if (requestCode == ActionCode.LIST_KECAMATAN) {
                kecCode = data.getStringExtra("kodeKecamatan");
                materialEditTextKecamatanReg.setText(data.getStringExtra("namaKecamatan"));
            } else if (requestCode == ActionCode.LIST_KODEPOS) {
                kodePos = data.getStringExtra("kodepos");
                materialEditTextKodePosReg.setText(data.getStringExtra("detail"));
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
}

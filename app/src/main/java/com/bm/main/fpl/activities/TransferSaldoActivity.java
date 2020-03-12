package com.bm.main.fpl.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bm.main.fpl.utils.Device;
import com.bm.main.pos.R;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.JsonObjectResponseCallback;
import com.bm.main.fpl.templates.CurrencyEditText;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;
import com.bm.main.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class TransferSaldoActivity extends BaseActivity implements JsonObjectResponseCallback {
    MaterialEditText materialEditTextIdDownline, materialEditTextPinTransfer;
    CurrencyEditText materialEditTextNominalTransfer;
    AppCompatButton button_lanjutkan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_saldo);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Transfer Saldo");
        init(0);
        materialEditTextIdDownline = findViewById(R.id.materialEditTextIdDownline);
        InputFilter[] filter = new InputFilter[2];
        filter[0] = new InputFilter.LengthFilter(20);
        filter[1] = new InputFilter.AllCaps();
        materialEditTextIdDownline.setFilters(filter);
        materialEditTextNominalTransfer = findViewById(R.id.materialEditTextNominalTransfer);
        materialEditTextPinTransfer = findViewById(R.id.materialEditTextPinTransfer);
        button_lanjutkan = findViewById(R.id.button_lanjutkan);
        button_lanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    new_popup_alertDemo(TransferSaldoActivity.this, "Info", "Anda belum bisa menikmati fiture ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                } else {
                    callTransfer();
                }
            }
        });

    }

    private void callTransfer() {

        if (materialEditTextIdDownline.getText().toString().isEmpty() || materialEditTextIdDownline.getText().toString().equalsIgnoreCase(" ")) {
            materialEditTextIdDownline.setAnimation(animShake);
            materialEditTextIdDownline.startAnimation(animShake);
            materialEditTextIdDownline.setError("ID/FA Outlet Tujuan Tidak Boleh Kosong");
            Device.vibrate(TransferSaldoActivity.this);
            return;
        }
        if (materialEditTextNominalTransfer.getText().toString().isEmpty() || materialEditTextNominalTransfer.getText().toString().equalsIgnoreCase(" ")) {
            materialEditTextNominalTransfer.setAnimation(animShake);
            materialEditTextNominalTransfer.startAnimation(animShake);
            materialEditTextNominalTransfer.setError("Nominal Tidak Boleh Kosong");
            Device.vibrate(TransferSaldoActivity.this);
            return;
        }

        if (materialEditTextPinTransfer.getText().toString().isEmpty() || materialEditTextPinTransfer.getText().toString().equalsIgnoreCase(" ")) {
            materialEditTextPinTransfer.setAnimation(animShake);
            materialEditTextPinTransfer.startAnimation(animShake);
            materialEditTextPinTransfer.setError("Password Tidak Boleh Kosong");
            Device.vibrate(TransferSaldoActivity.this);
            return;
        }
        requestTransfer();

    }

    private void requestTransfer() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestTransfer(
                    materialEditTextIdDownline.getText().toString(), materialEditTextNominalTransfer.getText().toString().replace(".", ""), materialEditTextPinTransfer.getText().toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithJSONObjectResponse(this, jsonObject, ActionCode.REQUEST_TRANSFER, this);

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
        getMenuInflater().inflate(R.menu.menu_rumah, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_right_drawer) {

            openTopDialog(false);
        } else if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {


        finish();
        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        closeProgressBarDialog();
        try {
            if (response.getString("response_code").equals(ResponseCode.SUCCESS)) {
                Intent intent = new Intent(getIntent());
                startActivity(intent);
                //  showToastCustom(this, 3, response.getString("response_desc"));
                //  MyDynamicToast.successMessage(TransferSaldoActivity.this,response.getString("response_desc"));
                new_popup_alert(TransferSaldoActivity.this, "Information", response.getString("response_desc"));
            } else if (response.getString("response_code").equals("03")) {
                new_popup_alert_session(this, "Informasi", response.getString("response_desc"));
            } else {
                //      showToastCustom(this, 2, response.getString("response_desc"));
                //  MyDynamicToast.warningMessage(TransferSaldoActivity.this,response.getString("response_desc"));
                new_popup_alert(TransferSaldoActivity.this, "Information", response.getString("response_desc"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {
        closeProgressBarDialog();
        //  showToastCustom(this,1,responseDescription);
        // MyDynamicToast.errorMessage(TransferSaldoActivity.this,responseDescription);
        new_popup_alert_failure_pay(TransferSaldoActivity.this, responseDescription);
    }
}

package com.bm.main.fpl.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.ProdukCode;
import com.bm.main.fpl.constants.ProdukGroup;
import com.bm.main.fpl.constants.RequestCode;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.JsonObjectResponseCallback;
import com.bm.main.fpl.models.InqModel;
import com.bm.main.fpl.templates.CustomFontCheckBox;
import com.bm.main.fpl.templates.RadioGroupTable;
import com.bm.main.fpl.templates.showcaseview.GuideView;
import com.bm.main.fpl.utils.Device;
import com.bm.main.fpl.utils.FormatString;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;
import com.bm.main.materialedittext.MaterialEditText;
import com.bm.main.materialedittext.OnCutCopyPasteListener;
import com.bm.main.scm.R;
import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import timber.log.Timber;

import static com.bm.main.fpl.constants.ResponseCode.NETWORK_ERROR;

public class BpjsActivity extends KeyboardListenerActivity implements JsonObjectResponseCallback {

    private static final String TAG = BpjsActivity.class.getSimpleName();
    RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5, radioButton6, radioButton7, radioButton8, radioButton9, radioButton10, radioButton11, radioButton12;
    RadioGroupTable radioGroupPeriode;
    MaterialEditText materialEditTextPrefix, materialEditTextIdPelanggan;
    MaterialEditText materialEditTextNoHandphone;
    CustomFontCheckBox checkboxSimpanId;
    AppCompatButton button_lanjutkan;
    String periode;
    RadioButton radioButtonPeriode;
    int selectedId;
    LinearLayout linMain, layout_no_connection;
    ImageView imageViewAddressBook, imageViewContactBook;
    int countText = 0;
    private View menuItemView;
    public MenuItem scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bpjs);
        logEventFireBase(ProdukGroup.BPJS, ProdukCode.ASRBPJSKS, EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, TAG);
        Timber.d("onCreate: ");
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("BPJS Kesehatan");
        init(0);
        imageViewAddressBook = findViewById(R.id.imageViewAddressBook);
        imageViewContactBook = findViewById(R.id.imageViewContactBook);
        bottom_toolbar = findViewById(R.id.bottom_toolbar);
        linMain = findViewById(R.id.linMain);
        layout_no_connection = findViewById(R.id.layout_no_connection);
        AppCompatButton button_coba_lagi = findViewById(R.id.button_coba_lagi);
        button_coba_lagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestInq();
            }
        });
        materialEditTextPrefix = findViewById(R.id.materialEditTextPrefix);

        imageViewAddressBook.setOnClickListener(v -> requestLayout(1));
        imageViewContactBook.setOnClickListener(v -> showContacts());
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
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                Timber.d("beforeTextChanged: ");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Timber.d("afterTextChanged: ");
            }
        });
        materialEditTextNoHandphone = findViewById(R.id.materialEditTextNoHandphone);

        materialEditTextNoHandphone.setOnCutCopyPasteListener(new OnCutCopyPasteListener() {

            @Override
            public void onCut() {
                // Do your onCut reactions
            }

            @Override
            public void onCopy() {
                // Do your onCopy reactions
            }

            @Override
            public void onPaste() {
                String selectedNumber = materialEditTextNoHandphone.getEditableText().toString();
                // selectedNumber= selectedNumber.replaceAll("....", "$0 ");
                selectedNumber = selectedNumber.replace("-", "");
                //  phoneInput.setText(selectedNumber);
                selectedNumber = selectedNumber.replaceAll("[^0-9]", "");
                if (selectedNumber.startsWith("62")) {
                    if (!selectedNumber.startsWith("9")) {
                        selectedNumber = "0" + selectedNumber.replaceFirst("^62*(.*)", "$1");
                    }
                }
                materialEditTextNoHandphone.setText("");
                materialEditTextNoHandphone.setText(selectedNumber);
            }
        });

        materialEditTextNoHandphone.setText(PreferenceClass.getUser().getNotelp_pemilik());
        radioGroupPeriode = findViewById(R.id.radioGroupPeriode);

        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton4 = findViewById(R.id.radioButton4);
        radioButton5 = findViewById(R.id.radioButton5);
        radioButton6 = findViewById(R.id.radioButton6);
        radioButton7 = findViewById(R.id.radioButton7);
        radioButton8 = findViewById(R.id.radioButton8);
        radioButton9 = findViewById(R.id.radioButton9);
        radioButton10 = findViewById(R.id.radioButton10);
        radioButton11 = findViewById(R.id.radioButton11);
        radioButton12 = findViewById(R.id.radioButton12);

        radioButton1.setButtonDrawable(new StateListDrawable());
        radioButton2.setButtonDrawable(new StateListDrawable());
        radioButton3.setButtonDrawable(new StateListDrawable());
        radioButton4.setButtonDrawable(new StateListDrawable());
        radioButton5.setButtonDrawable(new StateListDrawable());
        radioButton6.setButtonDrawable(new StateListDrawable());
        radioButton7.setButtonDrawable(new StateListDrawable());
        radioButton8.setButtonDrawable(new StateListDrawable());
        radioButton9.setButtonDrawable(new StateListDrawable());
        radioButton10.setButtonDrawable(new StateListDrawable());
        radioButton11.setButtonDrawable(new StateListDrawable());
        radioButton12.setButtonDrawable(new StateListDrawable());

        radioButton1.setText("1 Bulan");
        radioButton2.setText("2 Bulan");
        radioButton3.setText("3 Bulan");
        radioButton4.setText("4 Bulan");
        radioButton5.setText("5 Bulan");
        radioButton6.setText("6 Bulan");
        radioButton7.setText("7 Bulan");
        radioButton8.setText("8 Bulan");
        radioButton9.setText("9 Bulan");
        radioButton10.setText("10 Bulan");
        radioButton11.setText("11 Bulan");
        radioButton12.setText("12 Bulan");

        selectedId = radioGroupPeriode.getCheckedRadioButtonId();
        Timber.d("onCreate: %s", selectedId);
        radioButtonPeriode = findViewById(selectedId);

        radioGroupPeriode.onClick(radioButtonPeriode);
        Timber.d("onCreate: %s", radioButtonPeriode.getTag().toString());
        periode = radioButtonPeriode.getTag().toString();

        checkboxSimpanId = findViewById(R.id.checkboxSimpanId);
        checkboxSimpanId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {
                if (((CheckBox) view).isChecked()) {
                    Timber.d("onClick: true ");
                    isSave = "true";
                } else {
                    Timber.d("onClick: false");
                    isSave = "false";
                }
            }
        });
        button_lanjutkan = findViewById(R.id.button_lanjutkan);
        button_lanjutkan.setOnClickListener(view -> {
            if (materialEditTextIdPelanggan.getEditableText().toString().isEmpty()) {
                materialEditTextIdPelanggan.setAnimation(animShake);
                materialEditTextIdPelanggan.startAnimation(animShake);
                materialEditTextIdPelanggan.setError("ID Pelanggan Tidak Boleh Kosong");
                Device.vibrate(this);
                return;
            } else if (materialEditTextNoHandphone.getEditableText().toString().isEmpty()) {
                materialEditTextNoHandphone.setAnimation(animShake);
                materialEditTextNoHandphone.startAnimation(animShake);
                materialEditTextNoHandphone.setError("No Handphone Tidak Boleh Kosong");
                Device.vibrate(this);
                return;
            }

            selectedId = radioGroupPeriode.getCheckedRadioButtonId();

            radioButtonPeriode = findViewById(selectedId);

            radioGroupPeriode.onClick(radioButtonPeriode);
            Timber.d("on lanjutkan: %s", radioButtonPeriode.getTag().toString());
            periode = radioButtonPeriode.getTag().toString();


            requestInq();
        });
        requestPopUpPromo(ProdukGroup.BPJS);
        attachKeyboardListeners();
        navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));
    }

    private void requestInq() {
        logEventFireBase(ProdukGroup.BPJS, ProdukCode.ASRBPJSKS, EventParam.EVENT_ACTION_REQUEST_INQ, EventParam.EVENT_SUCCESS, TAG);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(BaseActivity.stringJson.requestInquiryBPJSKS(materialEditTextIdPelanggan.getEditableText().toString(), materialEditTextNoHandphone.getEditableText().toString().replace(" ", ""), periode, isSave));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithJSONObjectResponse(this, jsonObject, ActionCode.INQ, this);
        ViewGroup parent = findViewById(R.id.contentHost);
        @SuppressLint("InflateParams") final FrameLayout view = (FrameLayout) View.inflate(this, R.layout.loading_bar_full_dialog, parent);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText(R.string.progress_bar_wording);
        openProgressBarDialog(this, view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scanner, menu);
        new Handler().post(new Runnable() {
            @Override
            public void run() {

                menuItemView = findViewById(R.id.action_right_scanner);
                if (PreferenceClass.getInt(TAG, 0) != 1) {
                    showCaseFirst(BpjsActivity.this, "", getResources().getString(R.string.content_icon_scanner), menuItemView);

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
            scanner = item;
            openScanner(BpjsActivity.this);
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

    private void requestLayout(int selectedTab) {
        Timber.d("requestLayout: " + selectedTab);
        switch (selectedTab) {
            case 0:

                checkboxSimpanId.setVisibility(View.VISIBLE);
                checkboxSimpanId.setChecked(false);
                materialEditTextIdPelanggan.setText("");
                break;
            case 1:
                Intent intent = new Intent(this, DaftarPelangganActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("title", "Pelanggan BPJS Kesehatan");
                intent.putExtra("product", ProdukCode.ASRBPJSKS);
                intent.putExtra("hint", R.string.hint_cari_pelanggan);

                startActivityForResult(intent, ActionCode.LIST_PELANGGAN);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Timber.d("onActivityResult: " + requestCode + " " + requestCode);


        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == ActionCode.LIST_PELANGGAN) {
                checkboxSimpanId.setVisibility(View.GONE);
                checkboxSimpanId.setChecked(false);

                String idPel = data.getStringExtra("id_pel");
                idPel = idPel.substring(5);
                materialEditTextIdPelanggan.setText(idPel);
            } else if (requestCode == 2) {
                requestLayout(0);
            } else if (requestCode == ActionCode.BARCODE) {
                String idPel = data.getStringExtra(Intents.Scan.RESULT);
                if (idPel.substring(0, 2).equals("00")) {
                    materialEditTextIdPelanggan.setText(idPel.substring(2));
                } else {
                    materialEditTextIdPelanggan.setText(idPel);
                }
            } else if (requestCode == ActionCode.PICK_CONTACT) {
                Cursor cursor = null;
                String phoneNumber = "";
                final List<String> allNumbers = new ArrayList<>();
                int phoneIdx = 0;
                try {
                    Uri result = data.getData();
                    assert result != null;
                    String id = result.getLastPathSegment();
                    cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{id}, null);
                    assert cursor != null;
                    phoneIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    if (cursor.moveToFirst()) {
                        while (!cursor.isAfterLast()) {
                            phoneNumber = cursor.getString(phoneIdx);
                            allNumbers.add(phoneNumber);
                            cursor.moveToNext();
                        }
                    }
                } catch (Exception e) {
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                    HashSet hs = new HashSet();
                    hs.addAll(allNumbers);
                    allNumbers.clear();
                    allNumbers.addAll(hs);
                    final String[] items = allNumbers.toArray(new String[allNumbers.size()]);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(BpjsActivity.this);

                    ViewGroup parent = findViewById(R.id.contentHost);
                    View dialogView = View.inflate(this, R.layout.dialog_custom_contact, parent);

                    builder.setView(dialogView);
                    TextView textViewTitleDialog = dialogView.findViewById(R.id.textViewTitleDialog);
                    textViewTitleDialog.setText("Pilih Nomor Handphone Tujuan");

                    //Prepare ListView in dialog
                    ListView listViewPhoneNumber = (ListView) dialogView.findViewById(R.id.listViewPhoneNumber);
                    final AlertDialog alert = builder.create();

                    ArrayAdapter<String> adapter
                            = new ArrayAdapter<String>(this,
                            R.layout.layout_item_phonenumber, R.id.textViewItemPhone, items);
                    listViewPhoneNumber.setAdapter(adapter);
                    listViewPhoneNumber.setOnItemClickListener((parent1, view, position, id) -> {
                        String selectedNumber = items[position].toString();
                        selectedNumber = selectedNumber.replace("-", "");
                        selectedNumber = selectedNumber.replaceAll("[^0-9]", "");
                        if (selectedNumber.startsWith("62")) {
                            if (!selectedNumber.startsWith("9")) {
                                selectedNumber = "0" + selectedNumber.replaceFirst("^62*(.*)", "$1");
                            }
                        }
                        materialEditTextNoHandphone.setText("");
                        materialEditTextNoHandphone.setText(selectedNumber);
                        alert.dismiss();
                    });

                    if (allNumbers.size() > 1) {
                        alert.show();
                        alert.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.alert_dialog_edge));
                    } else {
                        String selectedNumber = phoneNumber;
                        selectedNumber = selectedNumber.replace("-", "");
                        selectedNumber = selectedNumber.replaceAll("[^0-9]", "");
                        if (selectedNumber.startsWith("62")) {
                            if (!selectedNumber.startsWith("9")) {
                                selectedNumber = "0" + selectedNumber.replaceFirst("^62*(.*)", "$1");
                            }
                        }
                        materialEditTextNoHandphone.setText("");
                        materialEditTextNoHandphone.setText(selectedNumber);
                    }

                    if (phoneNumber.length() == 0) {
                        //no numbers found actions
                    }
                }

            } else if (requestCode == RequestCode.ActionCode_SETTING) {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "All permissions granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permissions not granted.", Toast.LENGTH_LONG).show();
                }
            }
        } else if (resultCode == AppCompatActivity.RESULT_CANCELED) {

            Timber.d("onActivityResult: cancel " + requestCode);
            if (requestCode == ActionCode.LIST_PELANGGAN) {
                checkboxSimpanId.setVisibility(View.VISIBLE);
                checkboxSimpanId.setChecked(false);
            }
        }
    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        Timber.d("onSuccess: %s", response.toString());
        if (actionCode == ActionCode.INQ) {
            closeProgressBarDialog();

            if (layout_no_connection.getVisibility() == View.VISIBLE) {
                layout_no_connection.setVisibility(View.GONE);
            }
            if (linMain.getVisibility() == View.GONE) {
                linMain.setVisibility(View.VISIBLE);
            }

            InqModel inqModel = gson.fromJson(response.toString(), InqModel.class);
            if (inqModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                logEventFireBase(ProdukGroup.BPJS, ProdukCode.ASRBPJSKS, EventParam.EVENT_ACTION_RESULT_INQ, EventParam.EVENT_SUCCESS, TAG);
                PreferenceClass.putString("saldo", FormatString.CurencyIDR(inqModel.getSaldo()));
                Intent intent = new Intent(BpjsActivity.this, InqueryResultActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("title", "BPJS Kesehatan");
                intent.putExtra("periode", periode);
                intent.putExtra("produkCode", ProdukCode.ASRBPJSKS);
                intent.putExtra("produkGroup", ProdukGroup.BPJS);

                intent.putExtra("subject", "BPJS Kesehatan");
                intent.putExtra("id_pel", materialEditTextIdPelanggan.getEditableText().toString());
                intent.putExtra("no_hp", materialEditTextNoHandphone.getEditableText().toString().replace(" ", ""));
                intent.putExtra("inq_result", inqModel);
                intent.putExtra("struk", inqModel.getResponse_desc());
                intent.putExtra("nominal", inqModel.getNominal_inq());
                intent.putExtra("nominal_admin", inqModel.getNominal_admin_inq());
                intent.putExtra("struk", inqModel.getResponse_desc());
                intent.putExtra("struk_show", inqModel.getStruk_show());
                startActivityForResult(intent, 2);
            } else if (inqModel.getResponse_code().equals("03")) {
                new_popup_alert_session(this, "Informasi", inqModel.getResponse_desc());
            } else {
                logEventFireBase(ProdukGroup.BPJS, ProdukCode.ASRBPJSKS, EventParam.EVENT_ACTION_RESULT_INQ, EventParam.EVENT_NOT_SUCCESS, TAG);
                materialEditTextIdPelanggan.setError(inqModel.getResponse_desc());
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
    }

    @Override
    protected void onShowKeyboard(int keyboardHeight) {
        Timber.d("onShowKeyboard: " + keyboardHeight);
        bottom_toolbar.setVisibility(View.GONE);
    }

    @Override
    protected void onHideKeyboard() {
        Timber.d("onHideKeyboard: ");
        // do things when keyboard is hidden
        bottom_toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        Timber.d("onResume: ");
        navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));
        super.onResume();
    }

    public void showContacts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            Timber.d("showContacts: ");
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, RequestCode.ActionCode_READ_CONTACTS);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, ActionCode.PICK_CONTACT);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RequestCode.ActionCode_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                new_popup_alert_two_buttonPermision(BpjsActivity.this, "Peringatan", R.string.content_alert_kontak);
            }
        } else if (requestCode == RequestCode.ActionCode_CAMERA) {
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
        }
    }
}

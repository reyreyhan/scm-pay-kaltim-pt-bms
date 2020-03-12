package com.bm.main.fpl.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
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
import android.widget.TextView;

import com.bm.main.fpl.utils.Device;
import com.bm.main.pos.R;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.ProdukCode;
import com.bm.main.fpl.constants.ProdukGroup;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.JsonObjectResponseCallback;
import com.bm.main.fpl.models.InqModel;
import com.bm.main.fpl.templates.CustomFontCheckBox;
import com.bm.main.fpl.templates.showcaseview.GuideView;
import com.bm.main.fpl.utils.FormatString;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;
import com.bm.main.materialedittext.MaterialEditText;
import com.google.zxing.client.android.Intents;

import org.json.JSONException;
import org.json.JSONObject;

import static com.bm.main.fpl.constants.ResponseCode.NETWORK_ERROR;

public class IndiHomeActivity extends KeyboardListenerActivity implements JsonObjectResponseCallback {
    private static final String TAG = IndiHomeActivity.class.getSimpleName();
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
        setContentView(R.layout.activity_indi_home);
        logEventFireBase(ProdukGroup.TELKOM,ProdukCode.TELKOM_SPEEDY,EventParam.EVENT_ACTION_VISIT,EventParam.EVENT_SUCCESS,TAG);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("IndiHome");
        init(0);
        bottom_toolbar = findViewById(R.id.bottom_toolbar);
        //radioTabs = findViewById(R.id.radioGroupTabs);
        linMain = findViewById(R.id.linMain);
        layout_no_connection = findViewById(R.id.layout_no_connection);
        AppCompatButton button_coba_lagi = findViewById(R.id.button_coba_lagi);
        button_coba_lagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestInq();
            }
        });
        materialEditTextIdPelanggan = findViewById(R.id.materialEditTextIdPelanggan);
        materialEditTextIdPelanggan.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(@NonNull CharSequence s, int start, int before, int count) {
                //here is your code
                countText = s.length();

//                if (s.length() >= 4) {
//                    Log.d(TAG, "onTextChanged: "+s.length());
//
//
//                    // requestPrefixInternet(s.toString(),"INTERNET");
//                } else if (s.length() < 4) {
//
//
//                } else if (s.length() < 8) {
//
//                    //adapterProduk.clearSelections();
//
//                }
//                else
                if (s.length() == 0) {
                    checkboxSimpanId.setVisibility(View.VISIBLE);

                    checkboxSimpanId.setChecked(false);
                }
//                else{
//                    //if(s.length()==1){
//                    // if(textViewPlusSukses.getVisibility()==View.VISIBLE){
//                    //      textViewPlusSukses.setVisibility(View.GONE);
//                    //  }
//
//
//
//                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
                Log.d(TAG, "beforeTextChanged: ");
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                Log.d(TAG, "afterTextChanged: ");
//                if(s.length()>0) {
//
//                }else if(s.length()<8){
//
//                }
            }
        });
        //radioTabs.setOnCheckedChangeListener(this);
        appButtonItemTabIdPelanggan = findViewById(R.id.appButtonItemTabIdPelanggan);
        //appButtonItemTabIdPelanggan.setFocusable(true);
        appButtonItemTabIdPelanggan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLayout(0);
            }
        });
        appButtonItemTabDaftarPelanggan = findViewById(R.id.appButtonItemTabDaftarPelanggan);
        //  appButtonItemTabDaftarPelanggan.setFocusable(false);
        appButtonItemTabDaftarPelanggan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLayout(1);
            }
        });
        imageViewAddressBook = findViewById(R.id.imageViewAddressBook);
        imageViewAddressBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLayout(1);
            }
        });
        checkboxSimpanId = findViewById(R.id.checkboxSimpanId);
        checkboxSimpanId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {
                if (((CheckBox) view).isChecked()) {
                    Log.d(TAG, "onClick: true ");
                    isSave = "true";
                } else {
                    Log.d(TAG, "onClick: false");
                    isSave = "false";
                }
            }
        });
        button_lanjutkan = findViewById(R.id.button_lanjutkan);
        button_lanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (materialEditTextIdPelanggan.getText().toString().isEmpty()) {
                    materialEditTextIdPelanggan.setAnimation(animShake);
                    materialEditTextIdPelanggan.startAnimation(animShake);
                    materialEditTextIdPelanggan.setError("ID Pelanggan Tidak Boleh Kosong");
                    Device.vibrate(IndiHomeActivity.this);
                    return;
                }

                requestInq();
            }
        });
        requestPopUpPromo("INDIHOME");
        navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));
        attachKeyboardListeners();
        //showCaseIndi();

    }

    private void requestInq() {
        logEventFireBase(ProdukGroup.TELKOM,ProdukCode.TELKOM_SPEEDY,EventParam.EVENT_ACTION_REQUEST_INQ,EventParam.EVENT_SUCCESS,TAG);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestSpeedyInquiry(materialEditTextIdPelanggan.getText().toString(), isSave));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithJSONObjectResponse(this, jsonObject, ActionCode.INQ, this);
        @SuppressLint("InflateParams")
        ViewGroup parent = findViewById(R.id.contentHost);
        final FrameLayout view =(FrameLayout) View.inflate(this,R.layout.loading_bar_full_dialog, parent);
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
                    showCaseFirst(IndiHomeActivity.this, "", getResources().getString(R.string.content_icon_scanner), menuItemView);

//            BaseActivity.getInstanceBaseActivity().showCaseFirst(context, "", "Klik Icon untuk memilih ID/Kontak yang telah tersimpan",rootView.findViewById(R.id.action_right_scanner));
//                    BaseActivity.getInstanceBaseActivity().showCaseFirst(context, "", "Klik Icon untuk memilih ID/Kontak yang telah tersimpan", plnPascaFragment.imageViewAddressBook);
                    // client = new GuideView.Builder(PdamActivity.this);
                    mGbuilder.setGuideListener(new GuideView.GuideListener() {
                        @Override
                        public void onDismiss(@NonNull View view) {
                            switch (view.getId()) {
//                                case R.id.action_right_scanner:
//                                    client
//                                            .setTitle("")
//                                            .setContentText(getResources().getString(R.string.content_cek_fav)).setGravity(GuideView.Gravity.center)
//                                            .setDismissType(GuideView.DismissType.outside)
//                                            .setTargetView(checkboxSimpanDefaultPdam)
//                                            .build();
//                                    break;
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

                        }
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

            openScanner(IndiHomeActivity.this);
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
        Log.d(TAG, "requestLayout: " + selectedTab);
        switch (selectedTab) {
            case 0:

                checkboxSimpanId.setVisibility(View.VISIBLE);
                checkboxSimpanId.setChecked(false);
                materialEditTextIdPelanggan.setText("");
                appButtonItemTabIdPelanggan.setBackgroundDrawable(ContextCompat.getDrawable(IndiHomeActivity.this, R.drawable.selector_tab_btn));
                appButtonItemTabDaftarPelanggan.setBackgroundDrawable(ContextCompat.getDrawable(IndiHomeActivity.this, R.drawable.unselector_tab_btn));
                appButtonItemTabIdPelanggan.setTextColor(ContextCompat.getColor(IndiHomeActivity.this, R.color.md_white_1000));
                appButtonItemTabDaftarPelanggan.setTextColor(ContextCompat.getColor(IndiHomeActivity.this, R.color.md_grey_300));
                break;
            case 1:
                appButtonItemTabIdPelanggan.setBackgroundDrawable(ContextCompat.getDrawable(IndiHomeActivity.this, R.drawable.unselector_tab_btn));
                appButtonItemTabDaftarPelanggan.setBackgroundDrawable(ContextCompat.getDrawable(IndiHomeActivity.this, R.drawable.selector_tab_btn));
                appButtonItemTabIdPelanggan.setTextColor(ContextCompat.getColor(IndiHomeActivity.this, R.color.md_grey_300));
                appButtonItemTabDaftarPelanggan.setTextColor(ContextCompat.getColor(IndiHomeActivity.this, R.color.md_white_1000));
                Intent intent = new Intent(IndiHomeActivity.this, DaftarPelangganActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("title", "Pelanggan IndiHome");
                intent.putExtra("product", ProdukCode.TELKOM_SPEEDY);
                intent.putExtra("hint", R.string.hint_cari_pelanggan);
                startActivityForResult(intent, ActionCode.LIST_PELANGGAN);
           //     overridePendingTransition(0, 0);

                break;


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == ActionCode.LIST_PELANGGAN) {
                String idPel = data.getStringExtra("id_pel");
                materialEditTextIdPelanggan.setText(idPel);
            } else if (requestCode == 2) {

                navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));
//            radioTabsItemButton = findViewById(R.id.radioButtonItemTabIdPelanggan);
//            radioTabsItemButton.setChecked(true);
                requestLayout(0);
            } else if (requestCode == ActionCode.BARCODE) {

                String idPel = data.getStringExtra(Intents.Scan.RESULT);

                materialEditTextIdPelanggan.setText(idPel);

            }
        } else if (resultCode == AppCompatActivity.RESULT_CANCELED) {
//            if(requestCode==1) {
//                radioTabsItemButton = findViewById(R.id.radioButtonItemTabIdPelanggan);
//                radioTabsItemButton.setChecked(true);
//                requestLayout(Integer.parseInt(radioTabsItemButton.getTag().toString()));
//            }
            if (requestCode == ActionCode.LIST_PELANGGAN) {
                checkboxSimpanId.setVisibility(View.VISIBLE);

                checkboxSimpanId.setChecked(false);
            }
        }

    }

//
//    @Override
//    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        int selectedId = group.getCheckedRadioButtonId();
//        Log.d(TAG, "onCheckedChanged: " + selectedId + "  " + checkedId);
//        radioTabsItemButton = findViewById(selectedId);
//        requestLayout(Integer.parseInt(radioTabsItemButton.getTag().toString()));
//    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        Log.d(TAG, "onSuccess: " + response.toString());
        if (actionCode == ActionCode.INQ) {
            closeProgressBarDialog();
            if (layout_no_connection.getVisibility() == View.VISIBLE) {
                layout_no_connection.setVisibility(View.GONE);
            }
            if (linMain.getVisibility() == View.GONE) {
                linMain.setVisibility(View.VISIBLE);
            }

//            try {
//                if (response.getString("response_code").equals("03")) {
//                    new_popup_alert_session(this, "Informasi", response.getString("response_desc"));
//                } else {
                    InqModel inqModel = gson.fromJson(response.toString(), InqModel.class);
                    if (inqModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                        logEventFireBase(ProdukGroup.TELKOM,ProdukCode.TELKOM_SPEEDY,EventParam.EVENT_ACTION_RESULT_INQ,EventParam.EVENT_SUCCESS,TAG);
                        PreferenceClass.putString("saldo", FormatString.CurencyIDR(inqModel.getSaldo()));
                        //navigationTopCustom(getActivity(),FormatString.CurencyIDR(inqModel.getSaldo()));
                        Intent intent = new Intent(IndiHomeActivity.this, InqueryResultActivity.class);

                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra("title", "INDIHOME");
                        intent.putExtra("produkCode", ProdukCode.TELKOM_SPEEDY);
                        intent.putExtra("subject", "Indi Home");
                        intent.putExtra("produkGroup", ProdukGroup.INDIHOME);

                        intent.putExtra("id_pel", materialEditTextIdPelanggan.getText().toString());
                        intent.putExtra("inq_result", inqModel);
                        intent.putExtra("struk", inqModel.getResponse_desc());
                        intent.putExtra("struk_show", inqModel.getStruk_show());
                        startActivityForResult(intent, 2);
                        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }else if (inqModel.getResponse_code().equals("03")) {
                        new_popup_alert_session(this, "Informasi", inqModel.getResponse_desc());
                    } else {
                        logEventFireBase(ProdukGroup.TELKOM,ProdukCode.TELKOM_SPEEDY,EventParam.EVENT_ACTION_RESULT_INQ,EventParam.EVENT_NOT_SUCCESS,TAG);
                        materialEditTextIdPelanggan.setError(inqModel.getResponse_desc());
                    }
//        String.format(getString(R.string.response_description),"sukses");
//                }
//            } catch (JSONException jsone) {
//                Log.d(TAG, "JSONException: " + jsone.toString());
//            }
        }

    }

    @Override
    public void onFailure(int actionCode, @NonNull String responseCode, String responseDescription, Throwable throwable) {
        // String.format(setString(R.string.response_description)
        if (responseCode.equals(NETWORK_ERROR)) {
            if (actionCode == ActionCode.INQ) {
                linMain.setVisibility(View.GONE);
                layout_no_connection.setVisibility(View.VISIBLE);
            }
        }
        if (actionCode == ActionCode.INQ) {
            closeProgressBarDialog();
        }
        Log.d(TAG, "onFailure: " + responseCode + " " + responseDescription);
//snackBarCustomAction(rootView.findViewById(R.id.fragment),),R.string.tutup,BaseActivity.getInstanceBaseActivity().WARNING);

    }

//    @Override
//    public void onUpdate(long bytesRead, long totalSize, boolean done) {
//
//    }

    @Override
    protected void onShowKeyboard(int keyboardHeight) {
        // do things when keyboard is shown
        Log.d(TAG, "onShowKeyboard: " + keyboardHeight);
//        if(bottom_toolbar.getVisibility()==View.VISIBLE) {
        bottom_toolbar.setVisibility(View.GONE);
//        }
    }

    @Override
    protected void onHideKeyboard() {
        Log.d(TAG, "onHideKeyboard: ");
        // do things when keyboard is hidden
        bottom_toolbar.setVisibility(View.VISIBLE);
    }


//    private void showCaseIndi() {
//        if (PreferenceClass.getInt(TAG, 0) != 1) {
////        showCaseFirst(PdamActivity.this, "", getResources().getString(R.string.content_icon_scanner), menuItemView);
//
////            BaseActivity.getInstanceBaseActivity().showCaseFirst(context, "", "Klik Icon untuk memilih ID/Kontak yang telah tersimpan",rootView.findViewById(R.id.action_right_scanner));
//            showCaseFirst(this, "", "Klik Icon untuk memilih ID/Kontak yang telah tersimpan", imageViewAddressBook);
//            // client = new GuideView.Builder(PdamActivity.this);
//            mGbuilder.setGuideListener(new GuideView.GuideListener() {
//                @Override
//                public void onDismiss(View view) {
//                    switch (view.getId()) {
////                    case R.id.action_right_scanner:
////                        client
////                                .setTitle("")
////                                .setContentText(getResources().getString(R.string.content_cek_fav)).setGravity(GuideView.Gravity.center)
////                                .setDismissType(GuideView.DismissType.outside)
////                                .setTargetView(checkboxSimpanDefaultPdam)
////                                .build();
////                        break;
////                    case R.id.checkboxSimpanDefaultPdam:
////                        client
////                                .setTitle("")
////                                .setContentText(getResources().getString(R.string.content_icon_book)).setGravity(GuideView.Gravity.center)
////                                .setDismissType(GuideView.DismissType.outside)
////                                .setTargetView(imageViewAddressBook)
////                                .build();
////                        break;
//                        case R.id.imageViewAddressBook:
//                            mGbuilder
//                                    .setTitle("")
//                                    .setContentText(getResources().getString(R.string.content_cek_simpan)).setGravity(GuideView.Gravity.center)
//                                    .setDismissType(GuideView.DismissType.outside)
//                                    .setTargetView(checkboxSimpanId)
//                                    .build();
//                            break;
//
//                        case R.id.checkboxSimpanId:
//                            PreferenceClass.putInt(TAG, 1);
//                            return;
//                    }
//                    mGuideView = mGbuilder.build();
//                    mGuideView.show();
//
//                }
//            });
//            mGuideView = mGbuilder.build();
//            mGuideView.show();
//        }
//    }

    protected void onResume() {
        Log.d(TAG, "onResume: ");
        navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));
        super.onResume();
    }
}

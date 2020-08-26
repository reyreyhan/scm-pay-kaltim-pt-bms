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
import com.bm.main.scm.R;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.EventParam;
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

public class TvKabelActivity extends KeyboardListenerActivity implements JsonObjectResponseCallback {
    private static final String TAG = TvKabelActivity.class.getSimpleName();
    // private Context context;
    MaterialEditText materialEditTextIdPelanggan, materialEditTextProduk;
    CustomFontCheckBox checkboxSimpanId;
    AppCompatButton button_lanjutkan;
    private String produkCode;
    //private RadioGroup radioTabs;
    // private LinearLayout bottom_toolbar;
    //    private RadioButton radioTabsItemButton;
    AppCompatButton appButtonItemTabIdPelanggan;
    AppCompatButton appButtonItemTabDaftarPelanggan;
    LinearLayout linMain, layout_no_connection;
    private AppCompatButton button_coba_lagi;
    ImageView imageViewAddressBook;
    private View menuItemView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_kabel);
        logEventFireBase(ProdukGroup.TVKABEL,ProdukGroup.TVKABEL,EventParam.EVENT_ACTION_VISIT,EventParam.EVENT_SUCCESS,TAG);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("TV Berlangganan");
        init(0);
        bottom_toolbar = findViewById(R.id.bottom_toolbar);
        linMain = findViewById(R.id.linMain);
        layout_no_connection = findViewById(R.id.layout_no_connection);
        button_coba_lagi = findViewById(R.id.button_coba_lagi);
        button_coba_lagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestInq();
            }
        });
        // radioTabs = findViewById(R.id.radioGroupTabs);
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
        materialEditTextProduk = findViewById(R.id.materialEditTextProduk);

        materialEditTextProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mHandler.postDelayed(mUpdateTimeTask, 10);
//                Intent intent = new Intent(TvKabelActivity.this, ListGridProdukActivity.class);
                Intent intent = new Intent(TvKabelActivity.this, ListProdukTabActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("title", "Produk TV Berlangganan");
                intent.putExtra("product", "TVKABEL");
                intent.putExtra("hint", "Cari TV Berlangganan");
                startActivityForResult(intent, ActionCode.LIST_PRODUK);
                //overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
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
//        radioTabs.setOnCheckedChangeListener(this);
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
                if (materialEditTextProduk.getText().toString().isEmpty()) {
                    materialEditTextProduk.setAnimation(animShake);
                    materialEditTextProduk.startAnimation(animShake);
                    materialEditTextProduk.setError("Produk TV Kabel Tidak Boleh Kosong");
                    Device.vibrate(TvKabelActivity.this);
                    return;
                }
                if (materialEditTextIdPelanggan.getText().toString().isEmpty()) {
                    materialEditTextIdPelanggan.setAnimation(BaseActivity.animShake);
                    materialEditTextIdPelanggan.startAnimation(BaseActivity.animShake);
                    materialEditTextIdPelanggan.setError("ID Pelanggan Tidak Boleh Kosong");
                    Device.vibrate(TvKabelActivity.this);
                    return;
                }

                requestInq();
            }
        });
        materialEditTextProduk.performClick();
        requestPopUpPromo("TV_KABEL");

        navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));
        attachKeyboardListeners();
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
                    showCaseFirst(TvKabelActivity.this, "", getResources().getString(R.string.content_icon_scanner), menuItemView);

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

            openScanner(TvKabelActivity.this);
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

    private void requestInq() {
        logEventFireBase(ProdukGroup.TVKABEL,materialEditTextProduk.getText().toString(),EventParam.EVENT_ACTION_REQUEST_INQ,EventParam.EVENT_SUCCESS,TAG);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestInquiryTvKabel(produkCode, materialEditTextIdPelanggan.getText().toString(), isSave));
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
//        try {
//            if (response.getString("response_code").equals("03")) {
//                new_popup_alert_session(this, "Informasi", response.getString("response_desc"));
//            } else {
                InqModel inqModel = gson.fromJson(response.toString(), InqModel.class);
                if (inqModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                    logEventFireBase(ProdukGroup.TVKABEL,materialEditTextProduk.getText().toString(),EventParam.EVENT_ACTION_RESULT_INQ,EventParam.EVENT_SUCCESS,TAG);
                    PreferenceClass.putString("saldo", FormatString.CurencyIDR(inqModel.getSaldo()));
                    // BaseActivity.getInstanceBaseActivity().navigationTopCustom(getActivity(),FormatString.CurencyIDR(inqModel.getSaldo()));
                    Intent intent = new Intent(TvKabelActivity.this, InqueryResultActivity.class);

                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("title", ProdukGroup.TVKABEL);
                    intent.putExtra("produkCode", produkCode);
                    intent.putExtra("produkGroup", ProdukGroup.TVKABEL);
                    intent.putExtra("subject", materialEditTextProduk.getText().toString());
                    intent.putExtra("id_pel", materialEditTextIdPelanggan.getText().toString());
                    intent.putExtra("nominal", "0");

                    intent.putExtra("inq_result", inqModel);
                    intent.putExtra("struk", inqModel.getResponse_desc());
                    intent.putExtra("struk_show", inqModel.getStruk_show());
                    startActivityForResult(intent, 2);
                    //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }else if (inqModel.getResponse_code().equals("03")) {
                    new_popup_alert_session(this, "Informasi", inqModel.getResponse_desc());
                } else {
                    logEventFireBase(ProdukGroup.TVKABEL,materialEditTextProduk.getText().toString(),EventParam.EVENT_ACTION_RESULT_INQ,EventParam.EVENT_NOT_SUCCESS,TAG);
                    materialEditTextIdPelanggan.setError(inqModel.getResponse_desc());
                }
//        String.format(getString(R.string.response_description),"sukses");
//            }
//        } catch (JSONException jsone) {
//            Log.d(TAG, "JSONException: " + jsone.toString());
//        }
    }

}

    @Override
    public void onFailure(int actionCode, @NonNull String responseCode, String responseDescription, Throwable throwable) {
        Log.d(TAG, "onFailure: " + responseCode);
        if (responseCode.equals(NETWORK_ERROR)) {
            if (actionCode == ActionCode.INQ) {
                linMain.setVisibility(View.GONE);
                layout_no_connection.setVisibility(View.VISIBLE);
                RequestUtils.cancel();
            }

        }
        if (actionCode == ActionCode.INQ) {
            closeProgressBarDialog();
        }

    }

    private void requestLayout(int selectedTab) {
        Log.d(TAG, "requestLayout: " + selectedTab);
        switch (selectedTab) {
            case 0:

                checkboxSimpanId.setVisibility(View.VISIBLE);
                checkboxSimpanId.setChecked(false);
                materialEditTextIdPelanggan.setText("");
                appButtonItemTabIdPelanggan.setBackgroundDrawable(ContextCompat.getDrawable(TvKabelActivity.this, R.drawable.selector_tab_btn));
                appButtonItemTabDaftarPelanggan.setBackgroundDrawable(ContextCompat.getDrawable(TvKabelActivity.this, R.drawable.unselector_tab_btn));
                appButtonItemTabIdPelanggan.setTextColor(ContextCompat.getColor(TvKabelActivity.this, R.color.md_white_1000));
                appButtonItemTabDaftarPelanggan.setTextColor(ContextCompat.getColor(TvKabelActivity.this, R.color.md_grey_300));
                break;
            case 1:
                appButtonItemTabIdPelanggan.setBackgroundDrawable(ContextCompat.getDrawable(TvKabelActivity.this, R.drawable.unselector_tab_btn));
                appButtonItemTabDaftarPelanggan.setBackgroundDrawable(ContextCompat.getDrawable(TvKabelActivity.this, R.drawable.selector_tab_btn));
                appButtonItemTabIdPelanggan.setTextColor(ContextCompat.getColor(TvKabelActivity.this, R.color.md_grey_300));
                appButtonItemTabDaftarPelanggan.setTextColor(ContextCompat.getColor(TvKabelActivity.this, R.color.md_white_1000));
                if (materialEditTextProduk.getText().toString().isEmpty()) {
                    materialEditTextProduk.setAnimation(animShake);
                    materialEditTextProduk.startAnimation(animShake);
                    materialEditTextProduk.setError("Produk TV Berlangganan Tidak Boleh Kosong");
                    Device.vibrate(TvKabelActivity.this);
                    requestLayout(0);
                } else {
                    Intent intent = new Intent(this, DaftarPelangganActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra("title", "Pelanggan TV Berlanggan");
                    intent.putExtra("product", produkCode);
                    startActivityForResult(intent, ActionCode.LIST_PELANGGAN);
                }
                break;


        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == ActionCode.LIST_PRODUK) {
                finishActivity(ActionCode.LIST_PRODUK);
                produkCode = data.getStringExtra("kodeProduk");
                materialEditTextProduk.setText(data.getStringExtra("namaProduk"));
//                radioTabsItemButton = findViewById(R.id.radioButtonItemTabDaftarPelanggan);
//                if(radioTabsItemButton.isChecked()){
//                    radioTabsItemButton = findViewById(R.id.radioButtonItemTabIdPelanggan);
//                    radioTabsItemButton.setChecked(true);
//                    requestLayout(Integer.parseInt(radioTabsItemButton.getTag().toString()));
//                   // materialEditTextProduk.setText("");
//                }
            } else if (requestCode == ActionCode.LIST_PELANGGAN) {
                finishActivity(ActionCode.LIST_PELANGGAN);
                checkboxSimpanId.setVisibility(View.GONE);
                checkboxSimpanId.setChecked(false);
                String idPel = data.getStringExtra("id_pel");
                materialEditTextIdPelanggan.setText(idPel);
            } else if (requestCode == 2) {

                navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));
//                radioTabsItemButton = findViewById(R.id.radioButtonItemTabIdPelanggan);
//                radioTabsItemButton.setChecked(true);
                requestLayout(0);
                materialEditTextProduk.setText("");

            } else if (requestCode == ActionCode.BARCODE) {
                finishActivity(ActionCode.BARCODE);
                String idPel = data.getStringExtra(Intents.Scan.RESULT);

                materialEditTextIdPelanggan.setText(idPel);

            }
        } else if (resultCode == AppCompatActivity.RESULT_CANCELED) {
            if (requestCode == ActionCode.LIST_PRODUK) {
                finishActivity(ActionCode.LIST_PRODUK);
            }
//            if(requestCode==1) {
//
//                radioTabsItemButton = findViewById(R.id.radioButtonItemTabIdPelanggan);
//                radioTabsItemButton.setChecked(true);
//                requestLayout(Integer.parseInt(radioTabsItemButton.getTag().toString()));
//                // materialEditTextProduk.setText("");
//
//            }
            if (requestCode == ActionCode.LIST_PELANGGAN) {
                finishActivity(ActionCode.LIST_PELANGGAN);
                checkboxSimpanId.setVisibility(View.VISIBLE);

                checkboxSimpanId.setChecked(false);
            }
        }
    }


//    @Override
//    public void onCheckedChanged(RadioGroup group, int checkedId) {
//        int selectedId = group.getCheckedRadioButtonId();
//        Log.d(TAG, "onCheckedChanged: " + selectedId + "  " + checkedId);
//        radioTabsItemButton = findViewById(selectedId);
//        requestLayout(Integer.parseInt(radioTabsItemButton.getTag().toString()));
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

    //    @Override
//    protected void onStart() {
//
//        materialEditTextProduk.performClick();
//        super.onStart();
//    }
    @NonNull
    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {

            // do what you need to do here after the delay
            Intent intent = new Intent(TvKabelActivity.this, ListProdukTabActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("title", "Produk TV Berlangganan");
            intent.putExtra("product", "TVKABEL");
            intent.putExtra("hint", "Cari TV Berlangganan");
            startActivityForResult(intent, ActionCode.LIST_PRODUK);
        }
    };

    protected void onResume() {
        Log.d(TAG, "onResume: ");
        navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));
        super.onResume();
    }
}

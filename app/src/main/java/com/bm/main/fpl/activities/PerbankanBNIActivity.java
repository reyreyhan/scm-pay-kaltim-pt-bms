package com.bm.main.fpl.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;

import com.bm.main.fpl.utils.Device;
import com.google.android.material.tabs.TabLayout;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.main.pos.R;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.ProdukCode;
import com.bm.main.fpl.constants.ProdukGroup;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.models.InqModel;
import com.bm.main.fpl.templates.CurrencyEditText;
import com.bm.main.fpl.templates.CustomFontCheckBox;
import com.bm.main.fpl.templates.showcaseview.GuideView;
import com.bm.main.fpl.utils.FormatString;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;
import com.bm.main.fpl.utils.Validate;
import com.bm.main.materialedittext.MaterialEditText;
import com.bm.main.materialedittext.OnCutCopyPasteListener;
import com.google.zxing.client.android.Intents;

import org.json.JSONException;
import org.json.JSONObject;

import static com.bm.main.fpl.constants.ResponseCode.NETWORK_ERROR;

public class PerbankanBNIActivity extends KeyboardListenerActivity implements ProgressResponseCallback {
    private static final String TAG = PerbankanBNIActivity.class.getSimpleName();
    String TAGSHOWCASESETOR;
    //    String TAGSHOWCASETARIK ;
//    String TAGSHOWCASETRANSFER ;
    TabLayout tabLayout;
    Context context;

    LinearLayout tabSetor, tabTarik, tabTransfer;
    TextView textTabItemSetor, textTabItemTarik, textTabItemTransfer;
    TextView textViewInfoOtp;

    private int selectedTab;
    public MenuItem scanner;
    private View menuItemView;
    //   ImageView imageTabItemPlnPasca,imageTabItemPlnPra,imageTabItemPlnNon;
    private View viewItemTabSetor, viewItemTabTarik,
            viewItemTabTransfer;

    MaterialEditText materialEditTextNoRekSetor, materialEditTextNoRekTarik, materialEditTextNoRekTransfer, materialEditTextBankTransfer, materialEditTextNoHpTransfer;
    CurrencyEditText materialEditTextNominalSetor, materialEditTextNominalTarik, materialEditTextNominalTransfer;
    CustomFontCheckBox checkboxSimpanNoRekSetor, checkboxSimpanNoRekTarik, checkboxSimpanNoRekTransfer;
    ImageView imageViewAddressBookSetor, imageViewAddressBookTarik, imageViewAddressBookTransfer;
    AppCompatButton button_lanjutkanSetor, button_lanjutkanTarik, button_lanjutkanTransfer;

    LinearLayout layout_setor_tunai, layout_tarik_tunai, layout_transfer_tunai;
//ScrollView layout_transfer_tunai;

    //    private int[] layouts;
//    private ViewPager viewPager;
    LinearLayout linMain, layout_no_connection;
    AppCompatButton button_coba_lagi;
    String bankCode, bankName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perbankan_bni);
        context = this;
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Perbankan");
        init(0);

//        layouts = new int[]{
//                R.layout.layout_setor_tunai,
//                R.layout.layout_tarik_tunai,
//                R.layout.layout_transfer_tunai
//
//        };

        bottom_toolbar = findViewById(R.id.bottom_toolbar);
        linMain = findViewById(R.id.lin_content);
        layout_no_connection = findViewById(R.id.layout_no_connection);
        textViewInfoOtp=findViewById(R.id.textViewInfoOtp);
        textViewInfoOtp.setText(FormatString.htmlString(getString(R.string.info_otp_tarik_tunai)));
        button_coba_lagi = findViewById(R.id.button_coba_lagi);
        button_coba_lagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTab == 0) {
                    requestInqSetor();
                } else if (selectedTab == 1) {
                    requestInqTarik();
                } else if (selectedTab == 2) {
                    requestInqTranfer();
                }
            }
        });
        tabLayout = findViewById(R.id.tabs);
//         viewPager = findViewById(R.id.view_pager);

        // tabLayout.setupWithViewPager(viewPager);

        layout_setor_tunai = findViewById(R.id.layout_setor_tunai);
        layout_tarik_tunai = findViewById(R.id.layout_tarik_tunai);
        layout_transfer_tunai = findViewById(R.id.layout_transfer_tunai);

        // new TabLayout.TabLayoutOnPageChangeListener(tabLayout);
        tabLayout.setSelectedTabIndicatorColor(0);
        tabSetor = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab_pln, null);
        tabTarik = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab_pln, null);
        tabTransfer = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab_pln, null);
        textTabItemSetor = tabSetor.findViewById(R.id.textTab);
        textTabItemTarik = tabTarik.findViewById(R.id.textTab);
        textTabItemTransfer = tabTransfer.findViewById(R.id.textTab);


        viewItemTabSetor = tabSetor.findViewById(R.id.viewItemTabPln);
        viewItemTabTarik = tabTarik.findViewById(R.id.viewItemTabPln);
        viewItemTabTransfer = tabTransfer.findViewById(R.id.viewItemTabPln);


        imageViewAddressBookSetor = findViewById(R.id.imageViewAddressBookSetor);
        imageViewAddressBookSetor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerbankanBNIActivity.this, DaftarPelangganActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("title", "Pelanggan Setor Tunai");
                intent.putExtra("product", ProdukCode.SETORTUNAI);
                intent.putExtra("hint", R.string.hint_cari_pelanggan);
                startActivityForResult(intent, ActionCode.LIST_PELANGGAN);
            }
        });
        imageViewAddressBookTarik = findViewById(R.id.imageViewAddressBookTarik);
        imageViewAddressBookTarik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerbankanBNIActivity.this, DaftarPelangganActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("title", "Pelanggan Tarik Tunai");
                intent.putExtra("product", ProdukCode.TARIKTUNAI1);
                intent.putExtra("hint", R.string.hint_cari_pelanggan);
                startActivityForResult(intent, ActionCode.LIST_PELANGGAN);
            }
        });
        imageViewAddressBookTransfer = findViewById(R.id.imageViewAddressBookTransfer);
        imageViewAddressBookTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerbankanBNIActivity.this, DaftarPelangganActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("title", "Pelanggan Transfer Tunai Antar Bank");
                intent.putExtra("product", ProdukCode.TRANSFERTUNAIALLBANK);
                intent.putExtra("hint", R.string.hint_cari_pelanggan);
                startActivityForResult(intent, ActionCode.LIST_PELANGGAN);
            }
        });
        materialEditTextBankTransfer = findViewById(R.id.materialEditTextBankTransfer);
        materialEditTextBankTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callProdukBankTransfer();
            }
        });
        materialEditTextNoHpTransfer = findViewById(R.id.materialEditTextNoHpTransfer);
        materialEditTextNoHpTransfer.setOnCutCopyPasteListener(new OnCutCopyPasteListener() {


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
                String selectedNumber =materialEditTextNoHpTransfer.getEditableText().toString();
                // selectedNumber= selectedNumber.replaceAll("....", "$0 ");
                selectedNumber = selectedNumber.replace("-", "");
                //  phoneInput.setText(selectedNumber);
                selectedNumber = selectedNumber.replaceAll("[^0-9]", "");
                if (selectedNumber.startsWith("62")) {
                    if (!selectedNumber.startsWith("9")) {
                        selectedNumber = "0" + selectedNumber.replaceFirst("^62*(.*)", "$1");
                    }
                }
                materialEditTextNoHpTransfer.setText("");
//                materialEditTextNoHandphone.setCharacterDelay(10);
//                materialEditTextNoHandphone.animateText(selectedNumber);
//                materialEditTextNoHandphone.setMaskedText("**** **** **** ****");
                materialEditTextNoHpTransfer.setText(selectedNumber);
                // materialEditTextNoHandphone.setSelection(selectedNumber.length());
                //requestPrefixRegular(selectedNumber);
                // Do your onPaste reactions
            }
        });
//materialEditTextNoHpTransfer.addTextChangedListener(new PhoneNumberTextWatcher(materialEditTextNoHpTransfer));

        materialEditTextNoRekSetor = findViewById(R.id.materialEditTextNoRekSetor);
        materialEditTextNoRekTarik = findViewById(R.id.materialEditTextNoRekTarik);
        materialEditTextNoRekTransfer = findViewById(R.id.materialEditTextNoRekTransfer);

        checkboxSimpanNoRekSetor = findViewById(R.id.checkboxSimpanNoRekSetor);
        checkboxSimpanNoRekSetor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {
                if (((CheckBox) view).isChecked()) {
                    //   Log.d(TAG, "onClick: true ");
                    isSave = "true";
                } else {
                    //   Log.d(TAG, "onClick: false");
                    isSave = "false";
                }
            }
        });
        checkboxSimpanNoRekTarik = findViewById(R.id.checkboxSimpanNoRekTarik);
        checkboxSimpanNoRekTarik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {
                if (((CheckBox) view).isChecked()) {
                    //   Log.d(TAG, "onClick: true ");
                    isSave = "true";
                } else {
                    //   Log.d(TAG, "onClick: false");
                    isSave = "false";
                }
            }
        });
        checkboxSimpanNoRekTransfer = findViewById(R.id.checkboxSimpanNoRekTransfer);
        checkboxSimpanNoRekTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {
                if (((CheckBox) view).isChecked()) {
                    //   Log.d(TAG, "onClick: true ");
                    isSave = "true";
                } else {
                    //   Log.d(TAG, "onClick: false");
                    isSave = "false";
                }
            }
        });
        materialEditTextNominalSetor = findViewById(R.id.materialEditTextNominalSetor);
        materialEditTextNominalSetor.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(@NonNull CharSequence s, int start, int before, int count) {
                //here is your code

                if (s.length() == 0) {
                    checkboxSimpanNoRekSetor.setVisibility(View.VISIBLE);

                    checkboxSimpanNoRekSetor.setChecked(false);
                }
//
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
//
            }
        });


        materialEditTextNominalTarik = findViewById(R.id.materialEditTextNominalTarik);
        materialEditTextNominalTarik.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(@NonNull CharSequence s, int start, int before, int count) {
                //here is your code

                if (s.length() == 0) {
                    checkboxSimpanNoRekTarik.setVisibility(View.VISIBLE);

                    checkboxSimpanNoRekTarik.setChecked(false);
                }
//
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
//
            }
        });
        materialEditTextNominalTransfer = findViewById(R.id.materialEditTextNominalTransfer);
        materialEditTextNominalTransfer.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(@NonNull CharSequence s, int start, int before, int count) {
                //here is your code

                if (s.length() == 0) {
                    checkboxSimpanNoRekTransfer.setVisibility(View.VISIBLE);

                    checkboxSimpanNoRekTransfer.setChecked(false);
                }
//
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
//
            }
        });

        button_lanjutkanSetor = findViewById(R.id.button_lanjutkanSetor);
        button_lanjutkanSetor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callReqInqSetor();
            }
        });
        button_lanjutkanTarik = findViewById(R.id.button_lanjutkanTarik);
        button_lanjutkanTarik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callReqInqTarik();
            }
        });
        button_lanjutkanTransfer = findViewById(R.id.button_lanjutkanTransfer);
        button_lanjutkanTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callReqInqTransfer();
            }
        });

        navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));
        //   requestLayout(0);


        bindWidgetsWithAnEvent();
        setupTabLayout();
        attachKeyboardListeners();
//        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter();
//        viewPager.setAdapter(myViewPagerAdapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


    }

    private void callReqInqSetor() {

       if(!Validate.checkEmptyEditText(materialEditTextNoRekSetor, "No Rekening Tidak Boleh Kosong")){
            materialEditTextNoRekSetor.setAnimation(animShake);
            materialEditTextNoRekSetor.startAnimation(animShake);
            materialEditTextNoRekSetor.requestFocus();
           Device.vibrate(this);
            return;
        }
        if (materialEditTextNominalSetor.getText().toString().equalsIgnoreCase("") || materialEditTextNominalSetor.getText().toString().equalsIgnoreCase("0")|| materialEditTextNominalSetor.getText().toString().equalsIgnoreCase("0,00")) {
            materialEditTextNominalSetor.setAnimation(animShake);
            materialEditTextNominalSetor.startAnimation(animShake);
            materialEditTextNominalSetor.setError("Nominal Yang Disetor Tidak Boleh Kosong/Nol");
            materialEditTextNominalSetor.requestFocus();
            Device.vibrate(this);
            return;
        }

        requestInqSetor();
    }

    private void callReqInqTarik() {
        if (materialEditTextNoRekTarik.getText().toString().equalsIgnoreCase("")) {
            materialEditTextNoRekTarik.setAnimation(animShake);
            materialEditTextNoRekTarik.startAnimation(animShake);
            materialEditTextNoRekTarik.setError("No Rekening Tidak Boleh Kosong");
            materialEditTextNoRekTarik.requestFocus();
            Device.vibrate(this);
            return;
        }
        if (materialEditTextNominalTarik.getText().toString().equalsIgnoreCase("") || materialEditTextNominalTarik.getText().toString().equalsIgnoreCase("0")|| materialEditTextNominalTarik.getText().toString().equalsIgnoreCase("0,00")) {
            materialEditTextNominalTarik.setAnimation(animShake);
            materialEditTextNominalTarik.startAnimation(animShake);
            materialEditTextNominalTarik.setError("Nominal Yang Ditarik Tidak Boleh Kosong/Nol");
            materialEditTextNominalTarik.requestFocus();
            Device.vibrate(this);
            return;
        }

        requestInqTarik();
    }

    private void callReqInqTransfer() {

        if (materialEditTextBankTransfer.getText().toString().equalsIgnoreCase("")) {
            materialEditTextBankTransfer.setAnimation(animShake);
            materialEditTextBankTransfer.startAnimation(animShake);
            materialEditTextBankTransfer.setError("Bank Tujuan Tidak Boleh Kosong");
            // materialEditTextBankTransfer.requestFocus();
            Device.vibrate(this);
            return;
        }

        if (materialEditTextNoRekTransfer.getText().toString().equalsIgnoreCase("")) {
            materialEditTextNoRekTransfer.setAnimation(animShake);
            materialEditTextNoRekTransfer.startAnimation(animShake);
            materialEditTextNoRekTransfer.setError("No Rekening Tidak Boleh Kosong");
            materialEditTextNoRekTransfer.requestFocus();
            Device.vibrate(this);
            return;
        }
        if (materialEditTextNominalTransfer.getText().toString().equalsIgnoreCase("") || materialEditTextNominalTransfer.getText().toString().equalsIgnoreCase("0")|| materialEditTextNominalTransfer.getText().toString().equalsIgnoreCase("0,00")) {
            materialEditTextNominalTransfer.setAnimation(animShake);
            materialEditTextNominalTransfer.startAnimation(animShake);
            materialEditTextNominalTransfer.setError("Nominal Yang Ditranfer Tidak Boleh Kosong/Nol");
            materialEditTextNominalTransfer.requestFocus();
            Device.vibrate(this);
            return;
        }

        if (materialEditTextNoHpTransfer.getEditableText().toString().equalsIgnoreCase("")) {
            materialEditTextNoHpTransfer.setAnimation(animShake);
            materialEditTextNoHpTransfer.startAnimation(animShake);
            materialEditTextNoHpTransfer.setError("No Handphone Tidak Boleh Kosong");
            materialEditTextNoHpTransfer.requestFocus();
            Device.vibrate(this);
            return;
        }

        requestInqTranfer();
    }

    private void setupTabLayout() {
        Log.d(TAG, "setupTabLayout: ");
        setupTab();
        tabSetor.setSelected(true);

    }

    private void setupTab() {


        textTabItemSetor.setText("Setor Tunai");
//imageTabItemPlnPasca.setVisibility(View.GONE);
        viewItemTabSetor.setBackground(ContextCompat.getDrawable(PerbankanBNIActivity.this, R.drawable.selector_indicator_tab_btn_group_home));
        textTabItemSetor.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_ppob));
        tabSetor.setBackground(ContextCompat.getDrawable(PerbankanBNIActivity.this, R.drawable.selector_tab_btn_first));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabSetor));

        textTabItemTarik.setText("Tarik Tunai");
//imageTabItemPlnPra.setVisibility(View.GONE);
        viewItemTabTarik.setBackground(ContextCompat.getDrawable(PerbankanBNIActivity.this, R.drawable.unselector_indicator_tab_btn_group_home));
        textTabItemTarik.setTextColor(ContextCompat.getColor(this, R.color.md_white_1000));
        tabTarik.setBackground(ContextCompat.getDrawable(PerbankanBNIActivity.this, R.drawable.unselector_tab_btn_center));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabTarik));

        textTabItemTransfer.setText("Transfer Antar Bank");
//imageTabItemPlnNon.setVisibility(View.GONE);
        viewItemTabTransfer.setBackground(ContextCompat.getDrawable(PerbankanBNIActivity.this, R.drawable.unselector_indicator_tab_btn_group_home));
        textTabItemTransfer.setTextColor(ContextCompat.getColor(this, R.color.md_white_1000));
        tabTransfer.setBackground(ContextCompat.getDrawable(PerbankanBNIActivity.this, R.drawable.unselector_tab_btn_last));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabTransfer));


    }

    private void bindWidgetsWithAnEvent() {

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(@NonNull TabLayout.Tab tab) {
                int position = tab.getPosition();
//                TextView textTab;
//                ImageView imageTab;
                selectedTab=tab.getPosition();
                switch (position) {
                    case 0:
                        viewItemTabSetor.setBackground(ContextCompat.getDrawable(PerbankanBNIActivity.this, R.drawable.selector_indicator_tab_btn_group_home));
                        textTabItemSetor.setTextColor(ContextCompat.getColor(PerbankanBNIActivity.this, R.color.colorPrimary_ppob));
                        tabSetor.setBackground(ContextCompat.getDrawable(PerbankanBNIActivity.this, R.drawable.selector_tab_btn_first));


                  //      selectedTab = 0;

                        break;
                    case 1:
                        viewItemTabTarik.setBackground(ContextCompat.getDrawable(PerbankanBNIActivity.this, R.drawable.selector_indicator_tab_btn_group_home));
                        textTabItemTarik.setTextColor(ContextCompat.getColor(PerbankanBNIActivity.this, R.color.colorPrimary_ppob));
                        tabTarik.setBackground(ContextCompat.getDrawable(PerbankanBNIActivity.this, R.drawable.selector_tab_btn_center));

                    //    selectedTab = 1;

                        break;

                    case 2:
                        viewItemTabTransfer.setBackground(ContextCompat.getDrawable(PerbankanBNIActivity.this, R.drawable.selector_indicator_tab_btn_group_home));
                        textTabItemTransfer.setTextColor(ContextCompat.getColor(PerbankanBNIActivity.this, R.color.colorPrimary_ppob));
                        tabTransfer.setBackground(ContextCompat.getDrawable(PerbankanBNIActivity.this, R.drawable.selector_tab_btn_last));

                    //    selectedTab = 2;
                        break;


                }
                requestLayout();// call activity here for daftar id pelanggan

            }

            @Override
            public void onTabUnselected(@NonNull TabLayout.Tab tab) {

                Log.d(TAG, "onTabUnselected: " + tab.getPosition());
                int position = tab.getPosition();
//                TextView textTab;
//                ImageView imageTab;
                switch (position) {
                    case 0:
                        viewItemTabSetor.setBackground(ContextCompat.getDrawable(PerbankanBNIActivity.this, R.drawable.unselector_indicator_tab_btn_group_home));
                        textTabItemSetor.setTextColor(ContextCompat.getColor(PerbankanBNIActivity.this, R.color.md_white_1000));
                        tabSetor.setBackground(ContextCompat.getDrawable(PerbankanBNIActivity.this, R.drawable.unselector_tab_btn_first));


                        break;

                    case 1:
                        viewItemTabTarik.setBackground(ContextCompat.getDrawable(PerbankanBNIActivity.this, R.drawable.unselector_indicator_tab_btn_group_home));
                        textTabItemTarik.setTextColor(ContextCompat.getColor(PerbankanBNIActivity.this, R.color.md_white_1000));
                        tabTarik.setBackground(ContextCompat.getDrawable(PerbankanBNIActivity.this, R.drawable.unselector_tab_btn_center));

                        break;
                    case 2:
                        viewItemTabTransfer.setBackground(ContextCompat.getDrawable(PerbankanBNIActivity.this, R.drawable.unselector_indicator_tab_btn_group_home));
                        textTabItemTransfer.setTextColor(ContextCompat.getColor(PerbankanBNIActivity.this, R.color.md_white_1000));
                        tabTransfer.setBackground(ContextCompat.getDrawable(PerbankanBNIActivity.this, R.drawable.unselector_tab_btn_last));

                        break;


                }


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void requestLayout() {
        Log.d(TAG, "requestLayout: " + selectedTab);
//viewPager.setCurrentItem(selectedTab);

        switch (selectedTab) {
            case 0:
                //   openShowCaseSetor();
                //requestLaporanTrx();
                layout_setor_tunai.setVisibility(View.VISIBLE);
                materialEditTextNoRekSetor.requestFocus();
                layout_tarik_tunai.setVisibility(View.GONE);

                layout_transfer_tunai.setVisibility(View.GONE);
                //  searchView.setFocusable(false);
                logEventFireBase(ProdukGroup.PERBANKAN, ProdukCode.SETORTUNAI, EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, TAG);

                break;
            case 1:

                //  requestLaporanMutasi();

                layout_setor_tunai.setVisibility(View.GONE);
                layout_tarik_tunai.setVisibility(View.VISIBLE);
                materialEditTextNoRekTarik.requestFocus();
                layout_transfer_tunai.setVisibility(View.GONE);
                logEventFireBase(ProdukGroup.PERBANKAN, ProdukCode.TARIKTUNAI1, EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, TAG);
                //   openShowCaseTarik();

                break;
            case 2:

                // requestLaporanKomisi();

                layout_setor_tunai.setVisibility(View.GONE);

                layout_tarik_tunai.setVisibility(View.GONE);
                layout_transfer_tunai.setVisibility(View.VISIBLE);
                materialEditTextNoRekTransfer.requestFocus();
                // callProdukBankTransfer();
                logEventFireBase(ProdukGroup.PERBANKAN, ProdukCode.TRANSFERTUNAIALLBANK, EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, TAG);
                //  openShowCaseTransfer();

                break;


        }
//        findViewById(android.R.id.content).invalidate();
    }


    /**
     * View pager adapter
     */
//    public class MyViewPagerAdapter extends PagerAdapter {
//        private LayoutInflater layoutInflater;
//        // public ShimmerFrameLayout mShimmerViewContainer;
//        MyViewPagerAdapter() {
//        }
//
//        @NonNull
//        @Override
//        public Object instantiateItem(@NonNull ViewGroup container, int position) {
//            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//            View view = layoutInflater.inflate(layouts[position], container, false);
//
//
//            imageViewAddressBookSetor=view.findViewById(R.id.imageViewAddressBookSetor);
////
////            FrameLayout linCurve=view.findViewById(R.id.linCurve);
////            float heightDp = (getResources().getDisplayMetrics().heightPixels * 2) / 6;
////            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) linCurve.getLayoutParams();
////
////
////            lp.height = (int) heightDp;
////            linCurve.setMinimumHeight(lp.height);
//            container.addView(view);
//
//            return view;
//        }
//
//        @Override
//        public int getCount() {
//            return layouts.length;
//        }
//
//        @Override
//        public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
//            return view == obj;
//        }
//
//
//        @Override
//        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            View view = (View) object;
//            container.removeView(view);
//        }
//
//
//    }
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                requestPopUpPromo("PERBANKAN");
            }
        }, 2000);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Log.d(TAG, "onCreateOptionsMenu: YES YES");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scanner, menu);
//        menuItemView = findViewById(R.id.action_right_scanner);
//        openShowCaseSetor();
        new Handler().post(new Runnable() {
            @Override
            public void run() {

                menuItemView = findViewById(R.id.action_right_scanner);
                // SOME OF YOUR TASK AFTER GETTING VIEW REFERENCE

                if (PreferenceClass.getInt(TAGSHOWCASESETOR, 0) != 1) {
                    // viewPager.setFocusableInTouchMode(false);
                    showCaseFirst(context, "", "Klik Scanner untuk input no rekening Pelanggan menggunakan barcode atau QR Code", menuItemView);

                    mGbuilder.setGuideListener(new GuideView.GuideListener() {
                        @Override
                        public void onDismiss(@NonNull View view) {
                            switch (view.getId()) {
                                case R.id.action_right_scanner:
                                    mGbuilder
                                            .setTitle("")
                                            .setContentText("Klik Icon untuk memilih No Rekening yang telah tersimpan").setGravity(GuideView.Gravity.center)
                                            .setDismissType(GuideView.DismissType.anywhere)
                                            .setTargetView(imageViewAddressBookSetor)
                                            .build();
                                    break;
                                case R.id.imageViewAddressBookSetor:
                                    mGbuilder
                                            .setTitle("")
                                            .setContentText("Setelah mengisi No Rekening Pelanggan, Klik Simpan No Rekening Pelanggan untuk mempermudah transaksi").setGravity(GuideView.Gravity.center)
                                            .setDismissType(GuideView.DismissType.anywhere)
                                            .setTargetView(checkboxSimpanNoRekSetor)
                                            .build();
                                    break;

                                case R.id.checkboxSimpanNoRekSetor:
                                    PreferenceClass.putInt(TAGSHOWCASESETOR, 1);

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
//
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_right_drawer) {

            openTopDialog(false);
        } else if (id == R.id.action_right_scanner) {
            scanner = item;
            openScanner(this);
        } else if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        //  Log.d(TAG, "onBackPressed: "+bottom_toolbar.getVisibility());
        if (bottom_toolbar.getVisibility() == View.GONE) {
            closeKeyboard(this);

        }

        finish();
        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


    }

//private void openShowCaseSetor(){
//            new Handler().post(new Runnable() {
//            @Override
//            public void run() {
//
//            //    menuItemView = findViewById(R.id.action_right_scanner);
//                // SOME OF YOUR TASK AFTER GETTING VIEW REFERENCE
//
//                if(PreferenceClass.getInt(TAGSHOWCASESETOR, 0)!=1) {
//                   // viewPager.setFocusableInTouchMode(false);
//                    showCaseFirst(context, "", "Klik Scanner untuk input no rekening Pelanggan menggunakan barcode atau QR Code", menuItemView);
//
//                    mGbuilder.setGuideListener(new GuideView.GuideListener() {
//                        @Override
//                        public void onDismiss(View view) {
//                            switch (view.getId()) {
//                                case R.id.action_right_scanner:
//                                    mGbuilder
//                                            .setTitle("")
//                                            .setContentText("Klik Icon untuk memilih No Rekening yang telah tersimpan").setGravity(GuideView.Gravity.center)
//                                            .setDismissType(GuideView.DismissType.anywhere)
//                                            .setTargetView(imageViewAddressBookSetor)
//                                            .build();
//                                    break;
//                                case R.id.imageViewAddressBookSetor:
//                                    mGbuilder
//                                            .setTitle("")
//                                            .setContentText("Setelah mengisi No Rekening Pelanggan, Klik Simpan No Rekening Pelanggan untuk mempermudah transaksi").setGravity(GuideView.Gravity.center)
//                                            .setDismissType(GuideView.DismissType.anywhere)
//                                            .setTargetView(checkboxSimpanNoRekSetor)
//                                            .build();
//                                    break;
//
//                                case R.id.checkboxSimpanNoRekSetor:
//                                    PreferenceClass.putInt(TAGSHOWCASESETOR,1);
//
//                                    return;
//                            }
//                            mGuideView = mGbuilder.build();
//                            mGuideView.show();
//
//                        }
//                    });
//                    mGuideView = mGbuilder.build();
//                    mGuideView.show();
//                }
//
//            }
//        });
//}
//        private void openShowCaseTarik(){
//        if(PreferenceClass.getInt(TAGSHOWCASETARIK, 0)!=1) {
//            //viewPager.setCurrentItem(1);
//            showCaseFirst(context, "", "Klik Icon untuk memilih No Rekening yang telah tersimpan",imageViewAddressBookTarik);
//
//            mGbuilder.setGuideListener(new GuideView.GuideListener() {
//                @Override
//                public void onDismiss(View view) {
//                    switch (view.getId()) {
//
//                        case R.id.imageViewAddressBookTarik:
//                            mGbuilder
//                                    .setTitle("")
//                                    .setContentText("Setelah mengisi No Rekening Pelanggan, Klik Simpan No Rekening Pelanggan untuk mempermudah transaksi").setGravity(GuideView.Gravity.center)
//                                    .setDismissType(GuideView.DismissType.anywhere)
//                                    .setTargetView(checkboxSimpanNoRekTarik)
//                                    .build();
//                            break;
//
//                        case R.id.checkboxSimpanNoRekTarik:
//
//                            PreferenceClass.putInt(TAGSHOWCASETARIK,1);
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
//    private void openShowCaseTransfer(){
//        if(PreferenceClass.getInt(TAGSHOWCASETRANSFER, 0)!=1) {
//         //   viewPager.setCurrentItem(2);
//            showCaseFirst(context, "", "Klik Icon untuk memilih No Rekening yang telah tersimpan",imageViewAddressBookTransfer);
//
//            mGbuilder.setGuideListener(new GuideView.GuideListener() {
//                @Override
//                public void onDismiss(View view) {
//                    switch (view.getId()) {
//
//                        case R.id.imageViewAddressBookTransfer:
//                            mGbuilder
//                                    .setTitle("")
//                                    .setContentText("Setelah mengisi No Rekening Pelanggan, Klik Simpan No Rekening Pelanggan untuk mempermudah transaksi").setGravity(GuideView.Gravity.center)
//                                    .setDismissType(GuideView.DismissType.anywhere)
//                                    .setTargetView(checkboxSimpanNoRekTransfer)
//                                    .build();
//                            break;
//
//                        case R.id.checkboxSimpanNoRekTransfer:
//                            //viewPager.setEnabled(true);
//                            PreferenceClass.putInt(TAGSHOWCASETRANSFER,1);
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

    private void callProdukBankTransfer() {
//        Handler handler = new Handler(Looper.getMainLooper());
//        handler.post(new Runnable() {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Intent intent = new Intent(PerbankanBNIActivity.this, ListProductActivity.class);

                // intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra("title", "Bank Tujuan");
                intent.putExtra("product", ProdukGroup.PERBANKAN);
                intent.putExtra("hint", "Cari Bank Tujuan");
                startActivityForResult(intent, ActionCode.LIST_PRODUK);
            }
        });
        //overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
    }


    private void requestInqSetor() {
        logEventFireBase(ProdukCode.SETORTUNAI, materialEditTextNoRekSetor.getText().toString(), EventParam.EVENT_ACTION_REQUEST_INQ, EventParam.EVENT_SUCCESS, TAG);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestInquirySetorTunai(materialEditTextNoRekSetor.getText().toString(), materialEditTextNominalSetor.getText().toString().replace(".", "").replace(",00", ""), isSave));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.INQ, this);
        @SuppressLint("InflateParams")
        ViewGroup parent = findViewById(R.id.contentHost);
        final FrameLayout view =(FrameLayout) View.inflate(this, R.layout.loading_bar_full_dialog, parent);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText(R.string.progress_bar_wording);
        openProgressBarDialog(this, view);
    }


    private void requestInqTarik() {
        logEventFireBase(ProdukCode.TARIKTUNAI1, materialEditTextNoRekTarik.getText().toString(), EventParam.EVENT_ACTION_REQUEST_INQ, EventParam.EVENT_SUCCESS, TAG);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestInquiryTarikTunai(materialEditTextNoRekTarik.getText().toString(), materialEditTextNominalTarik.getText().toString().replace(".", "").replace(",00", ""), isSave));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.INQ, this);
        @SuppressLint("InflateParams")
        ViewGroup parent = findViewById(R.id.contentHost);
        final FrameLayout view =(FrameLayout) View.inflate(this, R.layout.loading_bar_full_dialog, parent);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText(R.string.progress_bar_wording);
        openProgressBarDialog(this, view);
    }


    private void requestInqTranfer() {
        logEventFireBase(ProdukCode.TRANSFERTUNAIALLBANK, materialEditTextNoRekTransfer.getText().toString(), EventParam.EVENT_ACTION_REQUEST_INQ, EventParam.EVENT_SUCCESS, TAG);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestInquiryTransferTunaiAllBank(materialEditTextNoRekTransfer.getText().toString(), bankCode, bankName, materialEditTextNominalTransfer.getEditableText().toString().replace(".", ""), isSave,materialEditTextNoHpTransfer.getEditableText().toString().replace(" ","").replace(",00", "")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.INQ, this);
        @SuppressLint("InflateParams")
        ViewGroup parent = findViewById(R.id.contentHost);
        final FrameLayout view =(FrameLayout) View.inflate(this, R.layout.loading_bar_full_dialog, parent);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText(R.string.progress_bar_wording);
        openProgressBarDialog(this, view);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: " + requestCode + " " + requestCode);
        //  tr.interrupt();
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == ActionCode.LIST_PRODUK) {
                bankCode = data.getStringExtra("kodeProduk");
                bankName = data.getStringExtra("namaProduk");
                materialEditTextBankTransfer.setText(bankName);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.showSoftInput(materialEditTextNoRekTransfer, InputMethodManager.SHOW_IMPLICIT);
                }
//                radioTabsItemButton = findViewById(R.id.radioButtonItemTabDaftarPelanggan);
//                if (radioTabsItemButton.isChecked()) {
//                    radioTabsItemButton = findViewById(R.id.radioButtonItemTabIdPelanggan);
//                    radioTabsItemButton.setChecked(true);
//                    requestLayout(Integer.parseInt(radioTabsItemButton.getTag().toString()));
//                    // materialEditTextProduk.setText("");
//                }
//                new Handler().post(new Runnable() {
//                    @Override
//                    public void run() {
//                        openShowCasePDAM();
//                    }
//                });

            } else if (requestCode == ActionCode.LIST_PELANGGAN) {
                String idPel = data.getStringExtra("id_pel").equals("") ? "" : data.getStringExtra("id_pel");
                if (selectedTab == 0) {
                    materialEditTextNoRekSetor.setText(idPel);
                    checkboxSimpanNoRekSetor.setVisibility(View.GONE);
                    checkboxSimpanNoRekSetor.setChecked(false);
                } else if (selectedTab == 1) {
                    materialEditTextNoRekTarik.setText(idPel);
                    checkboxSimpanNoRekTarik.setVisibility(View.GONE);
                    checkboxSimpanNoRekTarik.setChecked(false);
                } else if (selectedTab == 2) {
                    materialEditTextNoRekTransfer.setText(idPel);
                    checkboxSimpanNoRekTransfer.setVisibility(View.GONE);
                    checkboxSimpanNoRekTransfer.setChecked(false);
                }
                //  materialEditTextNoMeter.setText(no_sambungan);

            } else if (requestCode == ActionCode.INQ_RESULT) {
                navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));
//                radioTabsItemButton = findViewById(R.id.radioButtonItemTabIdPelanggan);
//                radioTabsItemButton.setChecked(true);
                //  requestLayout();

                if (selectedTab == 0) {
                    materialEditTextNoRekSetor.setText("");
                    materialEditTextNominalSetor.setText("");
                    materialEditTextNoRekSetor.requestFocus();
                } else if (selectedTab == 1) {
                    materialEditTextNoRekTarik.setText("");
                    materialEditTextNominalTarik.setText("");
                    materialEditTextNoRekTarik.requestFocus();
                } else if (selectedTab == 2) {
                    materialEditTextNoRekTransfer.setText("");
                    materialEditTextNominalTransfer.setText("");
                    materialEditTextNoHpTransfer.setText("");
                    materialEditTextBankTransfer.setText("");
                    materialEditTextBankTransfer.requestFocus();
                }


//                produkCode = PreferenceClass.getString("produkCode", "");
//                materialEditTextProduk.setText(PreferenceClass.getString("produkName", ""));
//                if (!produkCode.equals("")) {
//                    checkboxSimpanDefaultPdam.setChecked(true);
//                } else {
//                    checkboxSimpanDefaultPdam.setChecked(false);
//                    materialEditTextProduk.setText("");
//                }


                //  materialEditTextIdPelanggan.setText("");
                //  materialEditTextNoMeter.setText("");
            } else if (requestCode == ActionCode.BARCODE) {

                String idPel = data.getStringExtra(Intents.Scan.RESULT);
                if (selectedTab == 0) {
                    materialEditTextNoRekSetor.setText(idPel);
                } else if (selectedTab == 1) {
                    materialEditTextNoRekTarik.setText(idPel);
                } else if (selectedTab == 2) {
                    materialEditTextNoRekTransfer.setText(idPel);
                }

            }
        } else if (resultCode == AppCompatActivity.RESULT_CANCELED) {

            if (requestCode == ActionCode.LIST_PELANGGAN) {
                if (selectedTab == 0) {
                    checkboxSimpanNoRekSetor.setVisibility(View.VISIBLE);

                    checkboxSimpanNoRekSetor.setChecked(false);
                } else if (selectedTab == 1) {
                    checkboxSimpanNoRekTarik.setVisibility(View.VISIBLE);

                    checkboxSimpanNoRekTarik.setChecked(false);
                } else if (selectedTab == 2) {
                    checkboxSimpanNoRekTransfer.setVisibility(View.VISIBLE);

                    checkboxSimpanNoRekTransfer.setChecked(false);
                }

            }
        }
    }


    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        //  super.onSuccess(actionCode, response);
        Log.d(TAG, "onSuccess: " + response.toString());
        if (actionCode == ActionCode.INQ) {
            closeProgressBarDialog();
            InqModel inqModel = gson.fromJson(response.toString(), InqModel.class);
            if (layout_no_connection.getVisibility() == View.VISIBLE) {
                layout_no_connection.setVisibility(View.GONE);
            }
            if (linMain.getVisibility() == View.GONE) {
                linMain.setVisibility(View.VISIBLE);
            }


            switch (inqModel.getResponse_code()) {
                case ResponseCode.SUCCESS:
                    if (selectedTab == 0) {
                        logEventFireBase(ProdukGroup.PERBANKAN, ProdukCode.SETORTUNAI, EventParam.EVENT_ACTION_RESULT_INQ, EventParam.EVENT_SUCCESS, TAG);
                    } else if (selectedTab == 1) {
                        logEventFireBase(ProdukGroup.PERBANKAN, ProdukCode.TARIKTUNAI1, EventParam.EVENT_ACTION_RESULT_INQ, EventParam.EVENT_SUCCESS, TAG);
                    } else if (selectedTab == 2) {
                        logEventFireBase(ProdukGroup.PERBANKAN, ProdukCode.TRANSFERTUNAIALLBANK, EventParam.EVENT_ACTION_RESULT_INQ, EventParam.EVENT_SUCCESS, TAG);
                    }

                    PreferenceClass.putString("saldo", FormatString.CurencyIDR(inqModel.getSaldo()));
//                if (isSaveDefaultPdam.equals("true")) {
//                    PreferenceClass.putString("produkCode", produkCode);
//                    PreferenceClass.putString("produkName", materialEditTextProduk.getText().toString());
//                } else {
//                    PreferenceClass.putString("produkCode", "");
//                    PreferenceClass.putString("produkName", "");
//                }
                    // BaseActivity.getInstanceBaseActivity().navigationTopCustom(getActivity(),FormatString.CurencyIDR(inqModel.getSaldo()));
                    String produkCode = "", subject = "", id_pel = "", bank_code = "", bank_name = "",nominal="0";
                    Intent intent = new Intent(PerbankanBNIActivity.this, InqueryResultActivity.class);

                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    if (selectedTab == 0) {

                        produkCode = ProdukCode.SETORTUNAI;
                        subject = "Setor Tunai";
                        id_pel = materialEditTextNoRekSetor.getEditableText().toString();
                    } else if (selectedTab == 1) {
                        produkCode = ProdukCode.TARIKTUNAI1;
                        subject = "Tarik Tunai";
                        id_pel = materialEditTextNoRekTarik.getEditableText().toString();
                        nominal=materialEditTextNominalTarik.getEditableText().toString().replace(".", "");
                    } else if (selectedTab == 2) {
                        produkCode = ProdukCode.TRANSFERTUNAIALLBANK;
                        subject = "Transfer Tunai Ke Bank " + materialEditTextBankTransfer.getEditableText().toString();
                        id_pel = materialEditTextNoRekTransfer.getEditableText().toString();


                    }
                    intent.putExtra("title", ProdukGroup.PERBANKAN);
                    intent.putExtra("produkCode", produkCode);
                    intent.putExtra("produkGroup", ProdukGroup.PERBANKAN);
                    intent.putExtra("subject", subject);
                    intent.putExtra("id_pel", id_pel);
                    intent.putExtra("bank_code", bankCode);
                    intent.putExtra("bank_name", bankName);
                    intent.putExtra("bank_nominal",nominal );


                    intent.putExtra("inq_result", inqModel);
                    intent.putExtra("struk", inqModel.getResponse_desc());
                    intent.putExtra("struk_show", inqModel.getStruk_show());
                    startActivityForResult(intent, ActionCode.INQ_RESULT);
                    //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;
                case "03":
                    new_popup_alert_session(this, "Informasi", inqModel.getResponse_desc());
                    break;
                default:
                    if (selectedTab == 0) {
                        if (inqModel.getResponse_code().equals("01")) {
                            materialEditTextNominalSetor.setError(inqModel.getResponse_desc());
                            materialEditTextNominalSetor.setAnimation(animShake);
                            materialEditTextNominalSetor.startAnimation(animShake);
                        } else {
                            new_popup_alert(PerbankanBNIActivity.this, "Informasi",inqModel.getResponse_desc());
                        }
                        logEventFireBase(ProdukGroup.PERBANKAN, ProdukCode.SETORTUNAI, EventParam.EVENT_ACTION_RESULT_INQ, EventParam.EVENT_NOT_SUCCESS, TAG);
                    } else if (selectedTab == 1) {
                        if (inqModel.getResponse_code().equals("01")) {
                            materialEditTextNominalTarik.setError(inqModel.getResponse_desc());
                            materialEditTextNominalTarik.setAnimation(animShake);
                            materialEditTextNominalTarik.startAnimation(animShake);
                        } else {
                            new_popup_alert(PerbankanBNIActivity.this, "Informasi",inqModel.getResponse_desc());
                        }
                        //   materialEditTextNoRekTarik.setError(inqModel.getResponse_desc());
                        logEventFireBase(ProdukGroup.PERBANKAN, ProdukCode.TARIKTUNAI1, EventParam.EVENT_ACTION_RESULT_INQ, EventParam.EVENT_NOT_SUCCESS, TAG);
                    } else if (selectedTab == 2) {
                        if (inqModel.getResponse_code().equals("01")) {
                            materialEditTextNominalTransfer.setError(inqModel.getResponse_desc());
                            materialEditTextNominalTransfer.setAnimation(animShake);
                            materialEditTextNominalTransfer.startAnimation(animShake);
                        } else {
                            new_popup_alert(PerbankanBNIActivity.this,"Informasi" ,inqModel.getResponse_desc());
                        }
                        //  materialEditTextNoRekTransfer.setError(inqModel.getResponse_desc());
                        logEventFireBase(ProdukGroup.PERBANKAN, ProdukCode.TRANSFERTUNAIALLBANK, EventParam.EVENT_ACTION_RESULT_INQ, EventParam.EVENT_NOT_SUCCESS, TAG);
                    }

//                if (materialEditTextNoMeter.getText().toString().isEmpty()) {
//                    materialEditTextIdPelanggan.setError(inqModel.getResponse_desc());
//                } else if (materialEditTextIdPelanggan.getText().toString().isEmpty()) {
//                    materialEditTextNoMeter.setError(inqModel.getResponse_desc());
//                } else {
//                materialEditTextIdPelanggan.setError(inqModel.getResponse_desc());
//                    materialEditTextNoMeter.setError(inqModel.getResponse_desc());
//                }
                    break;
            }

        }
    }

    @Override
    public void onFailure(int actionCode, @NonNull String responseCode, String responseDescription, Throwable throwable) {
        // super.onFailure(actionCode, responseCode, responseDescription, throwable);
        if (responseCode.equals(NETWORK_ERROR)) {
            linMain.setVisibility(View.GONE);
            layout_no_connection.setVisibility(View.VISIBLE);
        }
//        else{
//            showToast(responseCode + " " + responseDescription + " " + throwable.toString());
//        }
        if (actionCode == ActionCode.INQ) {
            closeProgressBarDialog();
        }

        // Log.d(TAG, "onFailure: " + responseCode + " " + responseDescription + " " + throwable.toString());
    }

    @Override
    public void onUpdate(int actionCode,long bytesRead, long totalSize, boolean done) {
        // super.onUpdate(bytesRead, totalSize, done);
    }
}

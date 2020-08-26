package com.bm.main.fpl.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bm.main.fpl.adapters.ListBankSubVAAdapter;
import com.bm.main.fpl.adapters.ListBankVAAdapter;
import com.bm.main.fpl.adapters.ListGridBankAdapter;
import com.bm.main.fpl.adapters.ListGridMerchantAdapter;
import com.bm.main.fpl.adapters.ListStepperAdapter;
import com.bm.main.fpl.adapters.ListStepperBankVaAdapter;
import com.bm.main.fpl.adapters.ListTiketHistoryAdapter;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.FCMConstants;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.JsonObjectResponseCallback;
import com.bm.main.fpl.models.BankModel;
import com.bm.main.fpl.models.BankVAModel;
import com.bm.main.fpl.models.BankVaNewModelResponse_valueVa_desc;
import com.bm.main.fpl.models.BaseObject;
import com.bm.main.fpl.models.MerchantDescModel;
import com.bm.main.fpl.models.MerchantModel;
import com.bm.main.fpl.models.TiketHistoryModel;
import com.bm.main.fpl.templates.CurrencyEditText;
import com.bm.main.fpl.templates.DividerItemDecoration;
import com.bm.main.fpl.templates.RadioGroupPlus;
import com.bm.main.fpl.templates.indicators.AVLoadingIndicatorView;
import com.bm.main.fpl.templates.toast.MyDynamicToast;
import com.bm.main.fpl.utils.Device;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;
import com.bm.main.materialedittext.MaterialEditText;
import com.bm.main.scm.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DepositActivity extends BaseActivity implements JsonObjectResponseCallback, ListGridBankAdapter.OnClickProduk, ListGridMerchantAdapter.OnClickMerchant, ListBankVAAdapter.OnClickProdukVA, ListBankSubVAAdapter.OnClickProdukSubVA {
    private int selectedTab;
    private static final String TAG = DepositActivity.class.getSimpleName();

    TabLayout tabLayout;
    LinearLayout tabBank, tabTunaiVia, tabBank24, tabHistory;
    TextView textTabItemBank, textTabItemTunaiVia, textTabItemBank24, textTabItemHistory;
    // ImageView imageTabItemBank, imageTabItemTunaiVia, imageTabItemBank24, imageTabItemHistory;

    RecyclerView recyclerViewHistory;
    RecyclerView recyclerViewBank;
    RecyclerView recyclerViewBankVa;
    RecyclerView recyclerViewMerchant;
    RecyclerView recyclerViewMerchantDesc;
    RecyclerView.LayoutManager mLayoutManagerBank, mLayoutManagerMerchant;

    ListStepperAdapter listStepperAdapter;
    ListGridBankAdapter adapterBank;

    ListGridMerchantAdapter adapterMerchant;
    ListBankVAAdapter adapterBankVA;
    ListBankSubVAAdapter adapterBankSubVA;
    ListStepperBankVaAdapter adapterStepperBankVA;
    ListTiketHistoryAdapter adapterTiketHistory;

    @NonNull
    ArrayList<MerchantModel.Response_value> dataMerchant = new ArrayList<>();
    @NonNull
    ArrayList<MerchantDescModel.Response_value> dataMerchantDesc = new ArrayList<>();
    @NonNull
    ArrayList<BankModel.Response_value> dataBank = new ArrayList<>();
    @NonNull
    ArrayList<BankVAModel.Response_value> dataBankVA = new ArrayList<>();
    //  ArrayList<BankVaNewModelResponse_valueVa_desc> dataBankSubVA = new ArrayList<>();
    @NonNull
    ArrayList<TiketHistoryModel.Response_value> dataTiketHistory = new ArrayList<>();

    BankModel bankModel;
    BankVAModel bankVaModel;
    MerchantModel merchantModel;
    MerchantDescModel merchantDescModel;
    TiketHistoryModel tiketHistoryModel;
    BaseObject baseObject;
    NestedScrollView layout_transfer_bank_va;
    ScrollView layout_transfer_bank, layout_tunai_via, layout_transfer_bank24;
    ExpandableListView layout_transfer_bank_va_expanable;
    CoordinatorLayout layout_history;

    ImageView imageViewTunaiVia;

    AVLoadingIndicatorView avi24;
    MaterialEditText materialEditTextBank;
    @Nullable
    String produkBank = null;
    @Nullable
    String produkMerchant = null;

    ImageView imageViewBank;

    CurrencyEditText materialEditTextNominal, materialEditTextNominal24;
    AppCompatButton button_lanjutkan;//, button_lanjutkan24;

    int nominal = 0;
    int nominal24 = 0;
    RadioGroupPlus radioGroupBank;
    RadioGroupPlus radioGroupBank24;
    RadioButton radioButtonNominalBank;
    RadioButton radioButtonNominalBank24;
    @Nullable
    String produkName;
    @Nullable
    String merchantName;

    FrameLayout loading_bar, loading_bar_merchant;
    private int pilihanBankNon;
    private int pilihanMerchant;

    TextView textViewNotes;
    String actionDepositCepat;
    @NonNull
    private String productBank = "ListBankDeposit";
    @NonNull
    private String productBankVA = "ListBankVADeposit";
    @NonNull
    private String productBankMerchant = "ListBankMerchantDeposit";
    @NonNull
    private String productBankMerchantDesc = "ListBankMerchantDescDeposit";
    private LinearLayout layout_empty_history_tiket;
    private LinearLayout linMain;
    private View viewItemTabDeposit24, viewItemTabDepositBank,
            viewItemTabDepositTunaiVia,
            viewItemTabHistory;
    private int actionCode;

    LinearLayout layout_no_connection_va, layout_no_connection_transfer, layout_no_connection_tunai, layout_no_connection_history;
    LinearLayout linMainDepositTransfer, linMainDepositTunai;
    LinearLayout lin_content_indomaret;
    MaterialEditText materialEditTextNominalTopUp;
    AppCompatButton button_req_code;
    private String url_image_merchant;

    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_deposit);
        logEventFireBase("Deposit", "Deposit", EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, TAG);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Deposit");
        init(0);
        FCMConstants.isActivityDeposit = true;
        loading_bar = findViewById(R.id.loading_bar);
        loading_bar_merchant = findViewById(R.id.loading_bar_merchant);
        layout_no_connection_va = findViewById(R.id.layout_no_connection_va);
        layout_no_connection_transfer = findViewById(R.id.layout_no_connection_transfer);
        layout_no_connection_tunai = findViewById(R.id.layout_no_connection_tunai);
        layout_no_connection_history = findViewById(R.id.layout_no_connection_history);

        linMainDepositTransfer = findViewById(R.id.linMainDepositTransfer);
        linMainDepositTunai = findViewById(R.id.linMainDepositTunai);
        AppCompatButton button_coba_lagi = findViewById(R.id.button_coba_lagi);
        radioGroupBank = findViewById(R.id.radioGroupBank);

        bottom_toolbar = findViewById(R.id.bottom_toolbar);
        navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));

        radioGroupBank24 = findViewById(R.id.radioGroupBank24);
        materialEditTextNominal = findViewById(R.id.materialEditTextNominal);
        materialEditTextNominal24 = findViewById(R.id.materialEditTextNominal24);
        materialEditTextNominalTopUp = findViewById(R.id.materialEditTextNominalTopUp);
        button_req_code = findViewById(R.id.button_req_code);
        materialEditTextBank = findViewById(R.id.materialEditTextBank);
        materialEditTextBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DepositActivity.this, ListBankActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("title", "Produk Bank");
                startActivityForResult(intent, 0);
            }
        });
        avi24 = findViewById(R.id.avi24);
        imageViewBank = findViewById(R.id.imageViewBank);
        layout_transfer_bank_va = findViewById(R.id.layout_transfer_bank_va);

        lin_content_indomaret = findViewById(R.id.lin_content_indomaret);
        layout_transfer_bank = findViewById(R.id.layout_transfer_bank);
        layout_tunai_via = findViewById(R.id.layout_tunai_via);
        layout_transfer_bank24 = findViewById(R.id.layout_transfer_bank24);
        layout_history = findViewById(R.id.layout_history);
        layout_empty_history_tiket = layout_history.findViewById(R.id.layout_empty_history_tiket);
        linMain = layout_history.findViewById(R.id.linMain);
        recyclerViewBank = findViewById(R.id.recyclerViewBank);
        recyclerViewBankVa = findViewById(R.id.recyclerViewBankVa);
        recyclerViewMerchant = findViewById(R.id.recyclerViewMerchant);
        recyclerViewMerchantDesc = findViewById(R.id.recyclerViewMerchantDesc);
        recyclerViewHistory = findViewById(R.id.recyclerViewHistory);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setSelectedTabIndicatorColor(0);
        tabBank24 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab_deposit, null);
        tabBank = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab_deposit, null);
        tabTunaiVia = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab_deposit, null);

        tabHistory = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab_deposit, null);

        textTabItemBank = tabBank.findViewById(R.id.textTab);
        textTabItemTunaiVia = tabTunaiVia.findViewById(R.id.textTab);
        textTabItemBank24 = tabBank24.findViewById(R.id.textTab);
        textTabItemHistory = tabHistory.findViewById(R.id.textTab);

        viewItemTabDeposit24 = tabBank24.findViewById(R.id.viewItemTabDeposit);
        viewItemTabDepositBank = tabBank.findViewById(R.id.viewItemTabDeposit);
        viewItemTabDepositTunaiVia = tabTunaiVia.findViewById(R.id.viewItemTabDeposit);
        viewItemTabHistory = tabHistory.findViewById(R.id.viewItemTabDeposit);

        LinearLayoutManager linearLayoutManagerVA = new LinearLayoutManager(this);
        recyclerViewBankVa.setHasFixedSize(false);
        ViewCompat.setNestedScrollingEnabled(recyclerViewBankVa, false);

        recyclerViewBankVa.setLayoutManager(linearLayoutManagerVA);
        adapterBankVA = new ListBankVAAdapter(dataBankVA, this, this);

        recyclerViewBankVa.setAdapter(adapterBankVA);

        mLayoutManagerBank = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewBank.setHasFixedSize(false);
        recyclerViewBank.setLayoutManager(mLayoutManagerBank);
        adapterBank = new ListGridBankAdapter(dataBank, this, this);
        recyclerViewBank.setAdapter(adapterBank);

        mLayoutManagerMerchant = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewMerchant.setHasFixedSize(false);
        recyclerViewMerchant.setLayoutManager(mLayoutManagerMerchant);
        adapterMerchant = new ListGridMerchantAdapter(dataMerchant, this, this);
        recyclerViewMerchant.setAdapter(adapterMerchant);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewHistory.setHasFixedSize(false);
        recyclerViewHistory.setLayoutManager(linearLayoutManager);
        recyclerViewHistory.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        adapterTiketHistory = new ListTiketHistoryAdapter(dataTiketHistory, this);
        recyclerViewHistory.setAdapter(adapterTiketHistory);

        LinearLayoutManager linearLayoutManagerx = new LinearLayoutManager(this);
        recyclerViewMerchantDesc.setHasFixedSize(false);
        recyclerViewMerchantDesc.setLayoutManager(linearLayoutManagerx);
        listStepperAdapter = new ListStepperAdapter(dataMerchantDesc, this);
        recyclerViewMerchantDesc.setAdapter(listStepperAdapter);

        textViewNotes = findViewById(R.id.textViewNotes);

        button_lanjutkan = findViewById(R.id.button_lanjutkan);
        button_lanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (produkBank == null) {

                    recyclerViewBank.setAnimation(animShake);
                    recyclerViewBank.startAnimation(animShake);
                    Device.vibrate(DepositActivity.this);

                    MyDynamicToast.errorMessage(DepositActivity.this, "Bank Belum Dipilih");
                    return;
                }

                if (materialEditTextNominal.getText().toString().isEmpty()) {

                    materialEditTextNominal.setAnimation(animShake);
                    materialEditTextNominal.startAnimation(animShake);

                    materialEditTextNominal.setError("Nominal Deposit Tidak Boleh Kosong");
                    Device.vibrate(materialEditTextNominal.getContext());
                    return;
                }

                if (materialEditTextNominal.getText().equals("0") || materialEditTextNominal.getText().equals("0,00")) {

                    materialEditTextNominal.setAnimation(animShake);
                    materialEditTextNominal.startAnimation(animShake);

                    materialEditTextNominal.setError("Nominal Deposit Tidak Boleh Nol");
                    Device.vibrate(materialEditTextNominal.getContext());
                    return;

                }
                requestDeposit();
            }
        });

        button_req_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (materialEditTextNominalTopUp.getText().toString().isEmpty()) {

                    materialEditTextNominalTopUp.setAnimation(animShake);
                    materialEditTextNominalTopUp.startAnimation(animShake);

                    materialEditTextNominalTopUp.setError("Nominal Deposit Tidak Boleh Kosong");
                    Device.vibrate(materialEditTextNominalTopUp.getContext());
                    return;

                }
                if (materialEditTextNominalTopUp.getText().equals("0") || materialEditTextNominal.getText().equals("0,00")) {

                    materialEditTextNominalTopUp.setAnimation(animShake);
                    materialEditTextNominalTopUp.startAnimation(animShake);

                    materialEditTextNominalTopUp.setError("Nominal Deposit Tidak Boleh Nol");
                    Device.vibrate(materialEditTextNominalTopUp.getContext());
                    return;

                }
                requestTopIndomaret();
            }
        });

        button_coba_lagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionCode == ActionCode.LIST_PRODUK_BANK_VA) {
                    //   materialEditTextNoHandphone.performClick();
                    callBank24();
                } else if (actionCode == ActionCode.LIST_PRODUK) {
                    callBank();
                } else if (actionCode == ActionCode.LIST_PRODUK_MERCHANT_DEPOSIT) {
                    callBankMerchant();
                } else if (actionCode == ActionCode.LIST_TIKET_HISTORY) {
                    requestTiketHistory();
                }
            }
        });
        bindWidgetsWithAnEvent();
        setupTabLayout();
    }

    private void requestTopIndomaret() {
        logEventFireBase("DEPOSIT", "TOPUP INDOMARET " + materialEditTextNominalTopUp.getText().toString(), EventParam.EVENT_ACTION_REQUEST_DEPOSIT, EventParam.EVENT_SUCCESS, TAG);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestTopUpIndoMaret(materialEditTextNominalTopUp.getText().toString().replace(".", "").replace(",00", "")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithJSONObjectResponse(this, jsonObject, ActionCode.REQUEST_TOP_DEPOSIT, this);
        actionCode = ActionCode.REQUEST_TOP_DEPOSIT;
        @SuppressLint("InflateParams")
        ViewGroup parent = findViewById(R.id.contentHost);
        final FrameLayout view = (FrameLayout) View.inflate(this, R.layout.loading_bar_full_dialog, parent);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText(R.string.progress_bar_wording);
        openProgressBarDialog(this, view);
    }

    private void requestDeposit() {
        logEventFireBase("DEPOSIT", "TRANSFER BANK " + productBank + " " + materialEditTextNominal.getText().toString(), EventParam.EVENT_ACTION_REQUEST_DEPOSIT, EventParam.EVENT_SUCCESS, TAG);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestDeposit(produkBank, materialEditTextNominal.getText().toString().replace(".", "").replace(",00", "")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithJSONObjectResponse(this, jsonObject, ActionCode.REQUEST_DEPOSIT, this);
        actionCode = ActionCode.REQUEST_DEPOSIT;
        @SuppressLint("InflateParams")
        ViewGroup parent = findViewById(R.id.contentHost);
        final FrameLayout view = (FrameLayout) View.inflate(this, R.layout.loading_bar_full_dialog, parent);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText(R.string.progress_bar_wording);
        openProgressBarDialog(this, view);
    }

    private void requestListMerchantDeposit() {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject = new JSONObject(stringJson.requestBank("MERCHANT"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithJSONObjectResponse(this, jsonObject, ActionCode.LIST_PRODUK_MERCHANT_DEPOSIT, this);
        actionCode = ActionCode.LIST_PRODUK_MERCHANT_DEPOSIT;
    }

    private void requestTiketHistory() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestHistoryDeposit());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithJSONObjectResponse(this, jsonObject, ActionCode.LIST_TIKET_HISTORY, this);
        actionCode = ActionCode.LIST_TIKET_HISTORY;
    }

    private void requestListBank() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestBank(""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithJSONObjectResponse(this, jsonObject, ActionCode.LIST_PRODUK, this);
        actionCode = ActionCode.LIST_PRODUK;
    }

    private void requestListBankVA() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestListBankVA());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithJSONObjectResponse(this, jsonObject, ActionCode.LIST_PRODUK_BANK_VA, this);
        actionCode = ActionCode.LIST_PRODUK_BANK_VA;
    }

    private void setupTabLayout() {
        Log.d(TAG, "setupTabLayout: ");
        setupTab();
        tabBank24.setSelected(true);
    }

    private void setupTab() {

        textTabItemBank24.setText("Deposit Cepat");
        textTabItemBank24.setTextColor(ContextCompat.getColor(DepositActivity.this, R.color.colorPrimary_ppob));
        viewItemTabDeposit24.setBackground(ContextCompat.getDrawable(DepositActivity.this, R.drawable.selector_indicator_tab_btn_group_home));
        tabBank24.setBackground(ContextCompat.getDrawable(DepositActivity.this, R.drawable.selector_tab_btn_first));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabBank24));

        textTabItemBank.setText("Transfer Bank");
        textTabItemBank.setTextColor(ContextCompat.getColor(DepositActivity.this, R.color.md_white_1000));
        viewItemTabDepositBank.setBackground(ContextCompat.getDrawable(DepositActivity.this, R.drawable.unselector_indicator_tab_btn_group_home));
        tabBank.setBackground(ContextCompat.getDrawable(DepositActivity.this, R.drawable.unselector_tab_btn_center));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabBank));

        textTabItemTunaiVia.setText("Tunai Via");
        textTabItemTunaiVia.setTextColor(ContextCompat.getColor(DepositActivity.this, R.color.md_white_1000));
        viewItemTabDepositTunaiVia.setBackground(ContextCompat.getDrawable(DepositActivity.this, R.drawable.unselector_indicator_tab_btn_group_home));
        tabTunaiVia.setBackground(ContextCompat.getDrawable(DepositActivity.this, R.drawable.unselector_tab_btn_center));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabTunaiVia));

        textTabItemHistory.setText("History Tiket Aktif");
        textTabItemHistory.setTextColor(ContextCompat.getColor(DepositActivity.this, R.color.md_white_1000));
        viewItemTabHistory.setBackground(ContextCompat.getDrawable(DepositActivity.this, R.drawable.unselector_indicator_tab_btn_group_home));
        tabHistory.setBackground(ContextCompat.getDrawable(DepositActivity.this, R.drawable.unselector_tab_btn_last));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabHistory));
    }

    private void bindWidgetsWithAnEvent() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(@NonNull TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        textTabItemBank24.setTextColor(ContextCompat.getColor(DepositActivity.this, R.color.colorPrimary_ppob));
                        viewItemTabDeposit24.setBackground(ContextCompat.getDrawable(DepositActivity.this, R.drawable.selector_indicator_tab_btn_group_home));
                        tabBank24.setBackground(ContextCompat.getDrawable(DepositActivity.this, R.drawable.selector_tab_btn_first));

                        selectedTab = 0;
                        break;
                    case 1:
                        textTabItemBank.setTextColor(ContextCompat.getColor(DepositActivity.this, R.color.colorPrimary_ppob));
                        viewItemTabDepositBank.setBackground(ContextCompat.getDrawable(DepositActivity.this, R.drawable.selector_indicator_tab_btn_group_home));
                        tabBank.setBackground(ContextCompat.getDrawable(DepositActivity.this, R.drawable.selector_tab_btn_center));

                        selectedTab = 1;
                        break;
                    case 2:
                        textTabItemTunaiVia.setTextColor(ContextCompat.getColor(DepositActivity.this, R.color.colorPrimary_ppob));
                        viewItemTabDepositTunaiVia.setBackground(ContextCompat.getDrawable(DepositActivity.this, R.drawable.selector_indicator_tab_btn_group_home));
                        tabTunaiVia.setBackground(ContextCompat.getDrawable(DepositActivity.this, R.drawable.selector_tab_btn_center));
                        selectedTab = 2;
                        break;
                    case 3:
                        textTabItemHistory.setTextColor(ContextCompat.getColor(DepositActivity.this, R.color.colorPrimary_ppob));
                        viewItemTabHistory.setBackground(ContextCompat.getDrawable(DepositActivity.this, R.drawable.selector_indicator_tab_btn_group_home));
                        tabHistory.setBackground(ContextCompat.getDrawable(DepositActivity.this, R.drawable.selector_tab_btn_last));
                        selectedTab = 3;
                        break;
                }
                requestLayout();// call activity here for daftar id pelanggan
            }

            @Override
            public void onTabUnselected(@NonNull TabLayout.Tab tab) {
                Log.d(TAG, "onTabUnselected: " + tab.getPosition());
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        textTabItemBank24.setTextColor(ContextCompat.getColor(DepositActivity.this, R.color.md_white_1000));
                        viewItemTabDeposit24.setBackground(ContextCompat.getDrawable(DepositActivity.this, R.drawable.unselector_indicator_tab_btn_group_home));
                        tabBank24.setBackground(ContextCompat.getDrawable(DepositActivity.this, R.drawable.unselector_tab_btn_first));

                        break;
                    case 1:
                        textTabItemBank.setTextColor(ContextCompat.getColor(DepositActivity.this, R.color.md_white_1000));
                        viewItemTabDepositBank.setBackground(ContextCompat.getDrawable(DepositActivity.this, R.drawable.unselector_indicator_tab_btn_group_home));
                        tabBank.setBackground(ContextCompat.getDrawable(DepositActivity.this, R.drawable.unselector_tab_btn_center));

                        if (produkBank != null) {
                            produkBank = null;
                            if (0 != RecyclerView.NO_POSITION) {
                                if (adapterBank.listener != null) {
                                    adapterBank.clearSelections();
                                    adapterBank.toggleSelection(PreferenceClass.getInt("deposit_bank_non", 0));

                                    adapterBank.listener.onClickProduk(bankModel.getResponse_value().get(PreferenceClass.getInt("deposit_bank_non", 0)), PreferenceClass.getInt("deposit_bank_non", 0));
                                }
                            }
                        }
                        radioButtonNominalBank = findViewById(R.id.radioButtonBank2);
                        radioButtonNominalBank.setChecked(true);

                        nominal = Integer.parseInt(radioButtonNominalBank.getTag().toString());
                        materialEditTextNominal.setText(String.valueOf(nominal));

                        break;
                    case 2:
                        textTabItemTunaiVia.setTextColor(ContextCompat.getColor(DepositActivity.this, R.color.md_white_1000));
                        viewItemTabDepositTunaiVia.setBackground(ContextCompat.getDrawable(DepositActivity.this, R.drawable.unselector_indicator_tab_btn_group_home));
                        tabTunaiVia.setBackground(ContextCompat.getDrawable(DepositActivity.this, R.drawable.unselector_tab_btn_center));
                        if (produkMerchant != null) {
                            produkMerchant = null;
                            if (0 != RecyclerView.NO_POSITION) {
                                if (adapterMerchant.listener != null) {
                                    adapterMerchant.clearSelections();
                                    adapterMerchant.toggleSelection(PreferenceClass.getInt("deposit_merchant", 0));

                                    adapterMerchant.listener.OnClickGridMerchant(merchantModel.getResponse_value().get(PreferenceClass.getInt("deposit_merchant", 0)), PreferenceClass.getInt("deposit_merchant", 0));
                                }
                            }
                        }
                        break;
                    case 3:
                        textTabItemHistory.setTextColor(ContextCompat.getColor(DepositActivity.this, R.color.md_white_1000));
                        viewItemTabHistory.setBackground(ContextCompat.getDrawable(DepositActivity.this, R.drawable.unselector_indicator_tab_btn_group_home));
                        tabHistory.setBackground(ContextCompat.getDrawable(DepositActivity.this, R.drawable.unselector_tab_btn_last));
                        break;
                }
            }

            @Override
            public void onTabReselected(@NonNull TabLayout.Tab tab) {
                Log.d(TAG, "onTabReselected: " + tab.getPosition());
            }
        });
    }

    private void requestLayout() {
        Log.d(TAG, "requestLayout: " + selectedTab);

        switch (selectedTab) {
            case 0:
                layout_transfer_bank.setVisibility(View.GONE);
                layout_tunai_via.setVisibility(View.GONE);
                layout_transfer_bank_va.setVisibility(View.VISIBLE);

                layout_history.setVisibility(View.GONE);
                callBank24();
                break;
            case 1:
                layout_transfer_bank.setVisibility(View.VISIBLE);
                layout_tunai_via.setVisibility(View.GONE);
                layout_transfer_bank_va.setVisibility(View.GONE);
                layout_history.setVisibility(View.GONE);

                radioGroupBank.setOnCheckedChangeListener(new RadioGroupPlus.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(@NonNull RadioGroupPlus group, @IdRes int checkedId) {
                        Log.d("TAG", checkedId + "is checked");
                        int selectedId = group.getCheckedRadioButtonId();
                        radioButtonNominalBank = findViewById(selectedId);
                        nominal = Integer.parseInt(radioButtonNominalBank.getTag().toString());
                        materialEditTextNominal.setText(String.valueOf(nominal));
                        Log.d(TAG, "onCheckedChanged: " + nominal);
                    }
                });

                callBank();
                break;
            case 2:
                layout_transfer_bank.setVisibility(View.GONE);
                layout_tunai_via.setVisibility(View.VISIBLE);
                layout_transfer_bank_va.setVisibility(View.GONE);
                layout_transfer_bank24.setVisibility(View.GONE);
                layout_history.setVisibility(View.GONE);
                callBankMerchant();

                break;
            case 3:
                layout_transfer_bank.setVisibility(View.GONE);
                layout_tunai_via.setVisibility(View.GONE);
                layout_transfer_bank_va.setVisibility(View.GONE);
                layout_history.setVisibility(View.VISIBLE);
                requestTiketHistory();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
        closeKeyboard(this);
        FCMConstants.isActivityDeposit = false;
        finish();
    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        Log.d(TAG, "onSuccess: " + response.toString());

        if (actionCode == ActionCode.REQUEST_DEPOSIT) {
            closeProgressBarDialog();
            baseObject = gson.fromJson(response.toString(), BaseObject.class);
            if (baseObject.getResponse_code().equals(ResponseCode.SUCCESS)) {
                logEventFireBase("DEPOSIT", "TRANSFER BANK " + productBank + " " + materialEditTextNominal.getText().toString(), EventParam.EVENT_ACTION_RESULT_DEPOSIT, EventParam.EVENT_SUCCESS, TAG);
                Log.d(TAG, "onSuccess: " + response.toString());
                String keterangan = baseObject.getResponse_desc();
                String[] arrKeterangan = keterangan.split(",");
                String[] arrNominalSpace = arrKeterangan[0].split(" ");
                String nominalRes = arrNominalSpace[2].replace("Rp", "Rp ");

                PreferenceClass.putInt("deposit_bank_non", pilihanBankNon);
                radioButtonNominalBank = findViewById(R.id.radioButtonBank2);
                radioButtonNominalBank.setChecked(true);

                nominal = Integer.parseInt(radioButtonNominalBank.getTag().toString());
                String rek;
                String rekNo;
                String[] rekArr;
                try {
                    arrKeterangan = keterangan.split(",");
                    rek = arrKeterangan[1];
                    rekArr = rek.split(" ");
                    rekNo = rekArr[1].replace("rek=", "");

                } catch (Exception e) {
                    rekNo = "";
                }
                gotoTiketDeposit(rekNo, nominalRes, keterangan);
            } else if (baseObject.getResponse_code().equals("03")) {
                new_popup_alert_session(this, "Informasi", baseObject.getResponse_desc());
            } else if (baseObject.getResponse_code().equals("21")) {
                new_popup_alert_failure_pay(DepositActivity.this, baseObject.getResponse_desc());
            } else {
                logEventFireBase("DEPOSIT", "TRANSFER BANK " + productBank + " " + materialEditTextNominal.getText().toString(), EventParam.EVENT_ACTION_RESULT_DEPOSIT, EventParam.EVENT_NOT_SUCCESS, TAG);
                tabLayout.getTabAt(1).select();
                new_popup_alert_failure_pay(DepositActivity.this, baseObject.getResponse_desc());
                Log.d(TAG, "onNotSuccess: " + response.toString());
            }

        } else if (actionCode == ActionCode.REQUEST_DEPOSIT24) {
            closeProgressBarDialog();
            baseObject = gson.fromJson(response.toString(), BaseObject.class);
            if (baseObject.getResponse_code().equals(ResponseCode.SUCCESS)) {
                logEventFireBase("DEPOSIT", "DEPOSIT CEPAT " + productBank + " " + materialEditTextNominal24.getText().toString(), EventParam.EVENT_ACTION_RESULT_DEPOSIT, EventParam.EVENT_SUCCESS, TAG);
                Log.d(TAG, "onSuccess: " + response.toString());
                String keterangan = baseObject.getResponse_desc();
                gotoTiketDeposit("", "", keterangan);
            } else if (baseObject.getResponse_code().equals("13")) {
                logEventFireBase("DEPOSIT", "DEPOSIT CEPAT " + productBank + " " + materialEditTextNominal24.getText().toString(), EventParam.EVENT_ACTION_RESULT_DEPOSIT, EventParam.EVENT_NOT_SUCCESS, TAG);
                new_popup_alert_failure_pay(DepositActivity.this, "TIME OUT Silahkan Mencoba Kembali");
            } else if (baseObject.getResponse_code().equals("XX")) {
                logEventFireBase("DEPOSIT", "DEPOSIT CEPAT " + productBank + " " + materialEditTextNominal24.getText().toString(), EventParam.EVENT_ACTION_RESULT_DEPOSIT, EventParam.EVENT_NOT_SUCCESS, TAG);
                new_popup_alert_failure_pay(DepositActivity.this, baseObject.getResponse_desc());
            } else if (baseObject.getResponse_code().equals("03")) {
                new_popup_alert_session(this, "Informasi", baseObject.getResponse_desc());
            } else if (baseObject.getResponse_code().equals("21")) {
                logEventFireBase("DEPOSIT", "DEPOSIT CEPAT " + productBank + " " + materialEditTextNominal24.getText().toString(), EventParam.EVENT_ACTION_RESULT_DEPOSIT, EventParam.EVENT_NOT_SUCCESS, TAG);
                new_popup_alert_failure_pay(this, baseObject.getResponse_desc());
            } else {
                logEventFireBase("DEPOSIT", "DEPOSIT CEPAT " + productBank + " " + materialEditTextNominal24.getText().toString(), EventParam.EVENT_ACTION_RESULT_DEPOSIT, EventParam.EVENT_NOT_SUCCESS, TAG);
                tabLayout.getTabAt(3).select();
                Log.d(TAG, "onNotSuccess: " + response.toString());
            }
        } else if (actionCode == ActionCode.LIST_TIKET_HISTORY) {

            dataTiketHistory.clear();
            tiketHistoryModel = gson.fromJson(response.toString(), TiketHistoryModel.class);
            if (tiketHistoryModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                if (tiketHistoryModel.getResponse_value().size() > 0) {
                    linMain.setVisibility(View.VISIBLE);
                    layout_empty_history_tiket.setVisibility(View.GONE);
                    layout_no_connection_history.setVisibility(View.GONE);
                    dataTiketHistory.addAll(tiketHistoryModel.getResponse_value());

                    adapterTiketHistory.notifyDataSetChanged();
                } else {
                    linMain.setVisibility(View.GONE);
                    layout_empty_history_tiket.setVisibility(View.VISIBLE);
                    layout_no_connection_history.setVisibility(View.GONE);
                }
            } else if (tiketHistoryModel.getResponse_code().equals("03")) {
                new_popup_alert_session(this, "Informasi", tiketHistoryModel.getResponse_desc());
            } else {
                new_popup_alert_failure_pay(DepositActivity.this, tiketHistoryModel.getResponse_desc());
            }
        } else if (actionCode == ActionCode.LIST_PRODUK) {
            if (layout_no_connection_transfer.getVisibility() == View.VISIBLE) {
                layout_no_connection_transfer.setVisibility(View.GONE);
            }

            if (linMainDepositTransfer.getVisibility() == View.GONE) {
                linMainDepositTransfer.setVisibility(View.GONE);
            }

            loading_bar.setVisibility(View.GONE);
            recyclerViewBank.setVisibility(View.VISIBLE);

            bankModel = gson.fromJson(response.toString(), BankModel.class);
            if (bankModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                JSONObject obj = PreferenceClass.getJSONObject(productBank);
                JSONArray array = new JSONArray();
                try {
                    array = obj.getJSONArray("response_value");
                } catch (JSONException e) {
                }

                if (array.length() != bankModel.getResponse_value().size()) {
                    PreferenceClass.putJSONObject(productBank, response);
                    dataBank.clear();

                    dataBank.addAll(bankModel.getResponse_value());

                    adapterBank.notifyDataSetChanged();
                }
                if (0 != RecyclerView.NO_POSITION) {
                    if (adapterBank.listener != null) {
                        adapterBank.clearSelections();
                        adapterBank.toggleSelection(PreferenceClass.getInt("deposit_bank_non", 0));

                        adapterBank.listener.onClickProduk(bankModel.getResponse_value().get(PreferenceClass.getInt("deposit_bank_non", 0)), PreferenceClass.getInt("deposit_bank_non", 0));
                        nominal = 500000;
                        materialEditTextNominal.setText(String.valueOf(nominal));
                    }
                }
            } else if (bankModel.getResponse_code().equals("03")) {
                new_popup_alert_session(this, "Informasi", bankModel.getResponse_desc());
            }
        } else if (actionCode == ActionCode.LIST_PRODUK_BANK_VA) {
            Log.d(TAG, "onSuccess: " + response.toString());
            if (layout_no_connection_va.getVisibility() == View.VISIBLE) {
                layout_no_connection_va.setVisibility(View.GONE);
            }
            recyclerViewBankVa.setVisibility(View.VISIBLE);

            bankVaModel = gson.fromJson(response.toString(), BankVAModel.class);
            if (bankVaModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                dataBankVA.clear();

                dataBankVA.addAll(bankVaModel.getResponse_value());

                adapterBankVA.notifyDataSetChanged();
            } else if (bankVaModel.getResponse_code().equals("03")) {
                new_popup_alert_session(this, "Informasi", bankVaModel.getResponse_desc());
            }
        } else if (actionCode == ActionCode.LIST_PRODUK_MERCHANT_DEPOSIT) {
            if (linMainDepositTunai.getVisibility() == View.GONE) {
                linMainDepositTunai.setVisibility(View.VISIBLE);
            }
            if (layout_no_connection_tunai.getVisibility() == View.VISIBLE) {
                layout_no_connection_tunai.setVisibility(View.GONE);
            }

            loading_bar_merchant.setVisibility(View.GONE);
            recyclerViewMerchant.setVisibility(View.VISIBLE);

            merchantModel = gson.fromJson(response.toString(), MerchantModel.class);
            if (merchantModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                JSONObject obj = PreferenceClass.getJSONObject(productBankMerchant);
                JSONArray array = new JSONArray();
                try {
                    array = obj.getJSONArray("response_value");
                } catch (JSONException e) {
                }

                if (array.length() != merchantModel.getResponse_value().size()) {
                    PreferenceClass.putJSONObject(productBankMerchant, response);
                    dataMerchant.clear();
                    dataMerchant.addAll(merchantModel.getResponse_value());
                    adapterMerchant.notifyDataSetChanged();
                }

                if (0 != RecyclerView.NO_POSITION) {
                    if (adapterMerchant.listener != null) {
                        adapterMerchant.clearSelections();
                        adapterMerchant.toggleSelection(PreferenceClass.getInt("deposit_merchant", 0));

                        adapterMerchant.listener.OnClickGridMerchant(merchantModel.getResponse_value().get(PreferenceClass.getInt("deposit_merchant", 0)), PreferenceClass.getInt("deposit_merchant", 0));
                    }
                }
            } else if (merchantModel.getResponse_code().equals("03")) {
                new_popup_alert_session(this, "Informasi", merchantModel.getResponse_desc());
            }
        } else if (actionCode == ActionCode.LIST_PRODUK_MERCHANT_DESCRIPTION) {
            merchantDescModel = gson.fromJson(response.toString(), MerchantDescModel.class);
            if (merchantDescModel.getResponse_code().equals(ResponseCode.SUCCESS)) {

                JSONObject obj = PreferenceClass.getJSONObject(productBankMerchantDesc + produkMerchant);
                JSONArray array = new JSONArray();
                try {
                    array = obj.getJSONArray("response_value");
                } catch (JSONException e) {
                }

                if (array.length() != merchantDescModel.getResponse_value().size()) {
                    PreferenceClass.putJSONObject(productBankMerchantDesc + produkMerchant, response);
                    dataMerchantDesc.clear();
                    dataMerchantDesc.addAll(merchantDescModel.getResponse_value());

                    listStepperAdapter.notifyDataSetChanged();
                    textViewNotes.setText(Html.fromHtml(merchantDescModel.getNote()));
                }
            }
        } else if (actionCode == ActionCode.REQUEST_TOP_DEPOSIT) {
            closeProgressBarDialog();
            BaseObject baseObject = gson.fromJson(response.toString(), BaseObject.class);
            if (baseObject.getResponse_code().equals(ResponseCode.SUCCESS)) {

                String paymentCode = "";
                String nominal = "";
                try {
                    paymentCode = response.getString("payment_code");
                    nominal = response.getString("nominal");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(this, DepositTopupResultActivity.class);
                intent.putExtra("produkMerchant", produkMerchant);
                intent.putExtra("nominal", nominal);
                intent.putExtra("payment_code", paymentCode);
                intent.putExtra("url_image", url_image_merchant);
                intent.putExtra("description", baseObject.getResponse_desc());
                startActivity(intent);
                materialEditTextNominalTopUp.setText("");
            } else {
                materialEditTextNominalTopUp.setError(baseObject.getResponse_desc());
                materialEditTextNominalTopUp.setAnimation(animShake);
                materialEditTextNominalTopUp.startAnimation(animShake);
                Device.vibrate(this);
            }
        }
    }

    @Override
    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {
        if (this.actionCode == ActionCode.REQUEST_DEPOSIT || this.actionCode == ActionCode.REQUEST_DEPOSIT24) {
            closeProgressBarDialog();
            new_popup_alert_failure(DepositActivity.this, responseDescription);
        } else if (this.actionCode == ActionCode.LIST_PRODUK_BANK_VA) {
            layout_no_connection_va.setVisibility(View.VISIBLE);
            recyclerViewBankVa.setVisibility(View.GONE);
        } else if (this.actionCode == ActionCode.LIST_PRODUK) {
            if (PreferenceClass.getJSONObject(productBank).length() > 0) {
                loading_bar.setVisibility(View.GONE);
                recyclerViewBank.setVisibility(View.VISIBLE);
            } else {
                linMainDepositTransfer.setVisibility(View.GONE);
                layout_no_connection_transfer.setVisibility(View.VISIBLE);
            }
        } else if (this.actionCode == ActionCode.LIST_PRODUK_MERCHANT_DEPOSIT) {
            if (PreferenceClass.getJSONObject(productBankMerchant).length() > 0) {
                loading_bar_merchant.setVisibility(View.GONE);
                recyclerViewMerchant.setVisibility(View.VISIBLE);
            } else {
                linMainDepositTunai.setVisibility(View.GONE);
                layout_no_connection_tunai.setVisibility(View.VISIBLE);
            }
        } else if (this.actionCode == ActionCode.LIST_PRODUK_MERCHANT_DESCRIPTION) {
            if (PreferenceClass.getJSONObject(productBankMerchantDesc + produkMerchant).length() > 0) {
                recyclerViewMerchantDesc.setVisibility(View.VISIBLE);
            } else {
                new_popup_alert_failure(DepositActivity.this, responseDescription);
            }
        } else if (this.actionCode == ActionCode.LIST_TIKET_HISTORY) {

            linMain.setVisibility(View.GONE);
            layout_empty_history_tiket.setVisibility(View.GONE);
            layout_no_connection_history.setVisibility(View.VISIBLE);

        } else if (actionCode == ActionCode.REQUEST_TOP_DEPOSIT) {
            closeProgressBarDialog();
        }
    }

    private void gotoTiketDeposit(String norek_bank, String nominal, String keterangan) {
        Intent intent = new Intent(DepositActivity.this, TiketDepositActivity.class);
        intent.putExtra("type_deposit", "bank");
        intent.putExtra("nama_bank", produkName);
        intent.putExtra("norek_bank", norek_bank);
        intent.putExtra("nominal", nominal);
        intent.putExtra("keterangan", keterangan);

        startActivity(intent);
    }

    @Override
    public void onClickProduk(@NonNull BankModel.Response_value produk, int adapterPos) {
        produkBank = produk.getBank_code();
        produkName = produk.getBank_name();
        pilihanBankNon = adapterPos;
        Log.d(TAG, "onClickProduk: " + adapterPos);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == 0) {
                produkBank = data.getStringExtra("kodeBank");
                materialEditTextBank.setText(data.getStringExtra("namaBank").toString());
                Glide.with(this).load(data.getStringExtra("productUrl")).into(new DrawableImageViewTarget(imageViewBank) {
                    @Override
                    protected void setResource(@Nullable Drawable resource) {
                        super.setResource(resource);
                        imageViewBank.setVisibility(View.VISIBLE);
//
                        avi24.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        avi24.setVisibility(View.GONE);
                        imageViewBank.setVisibility(View.GONE);
                    }

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        super.onResourceReady(resource, transition);
                        imageViewBank.setVisibility(View.VISIBLE);
//
                        avi24.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    @Override
    public void OnClickGridMerchant(@NonNull MerchantModel.Response_value produk, int adapterPos) {
        produkMerchant = produk.getMerchant_code();
        merchantName = produk.getMerchant_name();
        pilihanMerchant = adapterPos;
        Log.d(TAG, "OnClickGridMerchant: " + adapterPos);

        if (produkMerchant.equals("ALFAMART")) {
            if (PreferenceClass.getJSONObject(productBankMerchantDesc + produkMerchant).length() > 0) {

                merchantDescModel = gson.fromJson(PreferenceClass.getJSONObject(productBankMerchantDesc + produkMerchant).toString(), MerchantDescModel.class);

                dataMerchantDesc.clear();
                dataMerchantDesc.addAll(merchantDescModel.getResponse_value());
                //  setData(gameModel.getData());
                listStepperAdapter.notifyDataSetChanged();
                recyclerViewMerchantDesc.setVisibility(View.VISIBLE);
                lin_content_indomaret.setVisibility(View.GONE);
                textViewNotes.setVisibility(View.VISIBLE);
                textViewNotes.setText(Html.fromHtml(merchantDescModel.getNote()));
            }
            requestDescriptionMerchant();
        } else {
            recyclerViewMerchantDesc.setVisibility(View.GONE);
            lin_content_indomaret.setVisibility(View.VISIBLE);
            materialEditTextNominalTopUp.setText("");
            textViewNotes.setVisibility(View.GONE);
        }
    }

    private void requestDescriptionMerchant() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestDescreptionMerchant(produkMerchant));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithJSONObjectResponse(this, jsonObject, ActionCode.LIST_PRODUK_MERCHANT_DESCRIPTION, this);
    }

    @Override
    public void onClickProdukVA(@NonNull BankVAModel.Response_value produk, @NonNull ListBankVAAdapter.ViewHolder viewHolder) {
        adapterBankVA.dataSub.clear();
        for (int x = 0; x < produk.getVa_desc().length; x++) {
            adapterBankVA.dataSub.add(produk.getVa_desc()[x]);
        }

        LinearLayoutManager linearLayoutManagery = new LinearLayoutManager(this);
        viewHolder.recyclerViewSubVa.setHasFixedSize(false);
        ViewCompat.setNestedScrollingEnabled(recyclerViewBankVa, false);

        viewHolder.recyclerViewSubVa.setLayoutManager(linearLayoutManagery);
        adapterBankSubVA = new ListBankSubVAAdapter(adapterBankVA.dataSub, this, this);
        viewHolder.recyclerViewSubVa.setAdapter(adapterBankSubVA);

        if (adapterBankVA.expandedPosition >= 0) {
            int prev = adapterBankVA.expandedPosition;
            Log.d(TAG, "onClickProdukVA: " + prev);

            if (produk.getVa_bank_type().equals("VA")) {
                adapterBankVA.notifyItemChanged(prev, viewHolder.linHeaderVA);
            } else {
                closeKeyboard(this);
                adapterBankVA.notifyItemChanged(prev, viewHolder.linHeaderNonVA);
            }
        }

        adapterBankVA.expandedPosition = viewHolder.getAdapterPosition();
        if (produk.getVa_bank_type().equals("VA")) {
            adapterBankVA.notifyItemChanged(adapterBankVA.expandedPosition, viewHolder.linHeaderVA);

        } else {
            viewHolder.materialEditTextNominal24.requestFocus();
            adapterBankVA.notifyItemChanged(adapterBankVA.expandedPosition, viewHolder.linHeaderNonVA);
        }
    }

    @Override
    public void onClickProdukLanjutkanNonVA(@NonNull BankVAModel.Response_value produk, @NonNull ListBankVAAdapter.ViewHolder viewHolder) {
        //  Log.d(TAG, "onClickProdukLanjutkanNonVA: " + viewHolder.imageViewProduk.getTag(viewHolder.imageViewProduk.getId()) + "  " + produk.getVa_bank_name());
        if (viewHolder.materialEditTextNominal24.getText().toString().isEmpty()) {

            viewHolder.materialEditTextNominal24.setAnimation(animShake);
            viewHolder.materialEditTextNominal24.startAnimation(animShake);

            viewHolder.materialEditTextNominal24.setError("Nominal Deposit Tidak Boleh Kosong");
            Device.vibrate(this);
            return;

        }
        if (viewHolder.materialEditTextNominal24.getText().equals("0") || viewHolder.materialEditTextNominal24.getText().equals("0,00")) {

            viewHolder.materialEditTextNominal24.setAnimation(animShake);
            viewHolder.materialEditTextNominal24.startAnimation(animShake);

            viewHolder.materialEditTextNominal24.setError("Nominal Deposit Tidak Boleh Nol");
            Device.vibrate(this);
            return;
        }
        logEventFireBase("DEPOSIT", "DEPOSIT CEPAT " + produk.getVa_bank_name() + " " + materialEditTextNominal24.getText().toString(), EventParam.EVENT_ACTION_REQUEST_DEPOSIT, EventParam.EVENT_SUCCESS, TAG);
        produkName = produk.getVa_bank_name();
        actionDepositCepat = "NONVA";
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject = new JSONObject(stringJson.requestDeposit(String.valueOf(viewHolder.linHeaderVA.getTag()), viewHolder.materialEditTextNominal24.getText().toString().replace(".", "").replace(",00", "")));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithJSONObjectResponse(this, jsonObject, ActionCode.REQUEST_DEPOSIT24, this);
        actionCode = ActionCode.REQUEST_DEPOSIT24;
        @SuppressLint("InflateParams")
        ViewGroup parent = findViewById(R.id.contentHost);
        final FrameLayout view = (FrameLayout) View.inflate(this, R.layout.loading_bar_full_dialog, parent);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText(R.string.progress_bar_wording);
        openProgressBarDialog(this, view);
    }

    public void onSlideViewProdukVAClick(@NonNull View view) {
        if (isDown) {
            collapse(view);

            Log.d(TAG, "onSlideViewProdukVAClick: " + isDown);
        } else {
            expand(view);

            Log.d(TAG, "onSlideViewProdukVAClick: " + isDown);
        }
        isDown = !isDown;
    }

    boolean isDown = false;
    boolean isDownSub = false;

    @Override
    public void onClickProdukSubVA(@NonNull BankVaNewModelResponse_valueVa_desc produk, @NonNull ListBankSubVAAdapter.ViewHolder viewHolder) {
        Log.d(TAG, "onClickProdukSubVA: " + produk.getNote());
        adapterBankVA.stepper.clear();
        for (int y = 0; y < produk.getSteper_value().length; y++) {
            adapterBankVA.stepper.add(produk.getSteper_value()[y]);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        viewHolder.recyclerStepperSubVA.setHasFixedSize(false);
        ViewCompat.setNestedScrollingEnabled(viewHolder.recyclerStepperSubVA, false);
        viewHolder.recyclerStepperSubVA.setLayoutManager(layoutManager);
        adapterStepperBankVA = new ListStepperBankVaAdapter(adapterBankVA.stepper, this);
        viewHolder.recyclerStepperSubVA.setAdapter(adapterStepperBankVA);

        int prev = -1;
        if (adapterBankSubVA.expandedPosition >= 0) {
            prev = adapterBankSubVA.expandedPosition;
            Log.d(TAG, "onClickProdukSubVA col: " + prev);

            adapterBankSubVA.notifyItemChanged(prev, viewHolder.linInetVA);
        }
        adapterBankSubVA.expandedPosition = viewHolder.getAdapterPosition();
        Log.d(TAG, "onClickProdukSubVA ex: " + adapterBankSubVA.expandedPosition);
        if (adapterBankSubVA.expandedPosition == prev) {

            adapterBankSubVA.notifyItemChanged(adapterBankSubVA.expandedPosition, viewHolder.linInetVA);
            viewHolder.linInetVA.setVisibility(View.GONE);
        } else {
            adapterBankSubVA.notifyItemChanged(adapterBankSubVA.expandedPosition, viewHolder.linInetVA);
        }
    }

    @Override
    protected void onDestroy() {
        Glide.get(this).clearMemory();
        super.onDestroy();
    }


    private void callBank() {
        if (PreferenceClass.getJSONObject(productBank).length() > 0) {

            bankModel = gson.fromJson(PreferenceClass.getJSONObject(productBank).toString(), BankModel.class);

            dataBank.clear();
            dataBank.addAll(bankModel.getResponse_value());
            //  setData(gameModel.getData());
            adapterBank.notifyDataSetChanged();
            recyclerViewBank.setVisibility(View.VISIBLE);
        }
        requestListBank();
    }

    private void callBank24() {
        requestListBankVA();
    }

    private void callBankMerchant() {
        if (PreferenceClass.getJSONObject(productBankMerchant).length() > 0) {

            merchantModel = gson.fromJson(PreferenceClass.getJSONObject(productBankMerchant).toString(), MerchantModel.class);

            dataMerchant.clear();
            dataMerchant.addAll(merchantModel.getResponse_value());
            //  setData(gameModel.getData());
            adapterMerchant.notifyDataSetChanged();
            recyclerViewMerchant.setVisibility(View.VISIBLE);
        }
        requestListMerchantDeposit();
    }


    public static void expand(@NonNull final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with da height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LinearLayout.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(@NonNull final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }
}

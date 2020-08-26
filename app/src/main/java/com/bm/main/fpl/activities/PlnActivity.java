package com.bm.main.fpl.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.google.android.material.tabs.TabLayout;
//import androidx.core.app.FragmentManager;
//import androidx.core.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.main.scm.R;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.ProdukGroup;
import com.bm.main.fpl.fragments.PlnNonFragment;
import com.bm.main.fpl.fragments.PlnPascaFragment;
import com.bm.main.fpl.fragments.PlnPraFragment;
import com.bm.main.fpl.templates.showcaseview.GuideView;
import com.bm.main.fpl.utils.PreferenceClass;
import com.google.zxing.client.android.Intents;

//import android.widget.ImageView;

public class PlnActivity extends BaseActivity {

    private static final String TAG = PlnActivity.class.getSimpleName();
    String TAGSHOWCASEPASCA;
    String TAGSHOWCASEPRA;
    String TAGSHOWCASENONTAG;
    //  private RadioGroup radioPln;
    TabLayout tabLayout;
    Context context;
    PlnPascaFragment plnPascaFragment;//=new PlnPascaFragment();
    PlnPraFragment plnPraFragment;//=new PlnPraFragment();
    PlnNonFragment plnNonFragment;//=new PlnNonFragment();
    //  private RadioButton radioPlnButton;
    LinearLayout tabPlnPasca, tabPlnPra, tabPlnNon;
    TextView textTabItemPlnPasca, textTabItemPlnPra, textTabItemPlnNon;

    private int selectedTab;
    public MenuItem scanner;
    private View menuItemView;
    //   ImageView imageTabItemPlnPasca,imageTabItemPlnPra,imageTabItemPlnNon;
    private View viewItemTabPasc, viewItemTabPra,
            viewItemTabNon;

    //  public RelativeLayout bottom_toolbar;
    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pln);
        //   getWindow().setBackgroundDrawable(null);
//        mInstancePlnActivity=this;

        context = this;
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("PLN");
        init(0);
        bottom_toolbar = findViewById(R.id.bottom_toolbar);
        tabLayout = findViewById(R.id.tabs);
        // new TabLayout.TabLayoutOnPageChangeListener(tabLayout);
        tabLayout.setSelectedTabIndicatorColor(0);
        tabPlnPasca = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab_pln, null);
        tabPlnPra = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab_pln, null);
        tabPlnNon = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab_pln, null);
        textTabItemPlnPasca = tabPlnPasca.findViewById(R.id.textTab);
        textTabItemPlnPra = tabPlnPra.findViewById(R.id.textTab);
        textTabItemPlnNon = tabPlnNon.findViewById(R.id.textTab);


        viewItemTabPasc = tabPlnPasca.findViewById(R.id.viewItemTabPln);
        viewItemTabPra = tabPlnPra.findViewById(R.id.viewItemTabPln);
        viewItemTabNon = tabPlnNon.findViewById(R.id.viewItemTabPln);
//        imageTabItemPlnPasca = tabPlnPasca.findViewById(R.id.imageViewTab);
//        imageTabItemPlnPra = tabPlnPra.findViewById(R.id.imageViewTab);
//        imageTabItemPlnNon = tabPlnNon.findViewById(R.id.imageViewTab);


        //   radioPln =  findViewById(R.id.radioPln);
        //   radioPln.setOnCheckedChangeListener(this);
        navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));
        //   requestLayout(0);
        requestPopUpPromo(ProdukGroup.PLN);
        bindWidgetsWithAnEvent();
        setupTabLayout();
        //  attachKeyboardListeners();
//        if (keyboardListenersAttached) {
//            return;
//        }
//
//        rootLayout = findViewById(R.id.rootLayout);
//        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(keyboardLayoutListener);
//
//        keyboardListenersAttached = true;
//        throw new RuntimeException("This is a crash");
    }

    //    @SuppressLint("StaticFieldLeak")
//    public static PlnActivity mInstancePlnActivity;
//
//    public static PlnActivity getInstancePlnActivity() {
//        return mInstancePlnActivity;
//    }
    private void setupTabLayout() {
        Log.d(TAG, "setupTabLayout: ");
        setupTab();
        tabPlnPasca.setSelected(true);

    }

    private void setupTab() {


        textTabItemPlnPasca.setText("Pascabayar");
//imageTabItemPlnPasca.setVisibility(View.GONE);
        viewItemTabPasc.setBackground(ContextCompat.getDrawable(PlnActivity.this, R.drawable.selector_indicator_tab_btn_group_home));
        textTabItemPlnPasca.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary_ppob));
        tabPlnPasca.setBackground(ContextCompat.getDrawable(PlnActivity.this, R.drawable.selector_tab_btn_first));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabPlnPasca));

        textTabItemPlnPra.setText("Token/Prabayar");
//imageTabItemPlnPra.setVisibility(View.GONE);
        viewItemTabPra.setBackground(ContextCompat.getDrawable(PlnActivity.this, R.drawable.unselector_indicator_tab_btn_group_home));
        textTabItemPlnPra.setTextColor(ContextCompat.getColor(this, R.color.md_white_1000));
        tabPlnPra.setBackground(ContextCompat.getDrawable(PlnActivity.this, R.drawable.unselector_tab_btn_center));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabPlnPra));

        textTabItemPlnNon.setText("Non Taglis");
//imageTabItemPlnNon.setVisibility(View.GONE);
        viewItemTabNon.setBackground(ContextCompat.getDrawable(PlnActivity.this, R.drawable.unselector_indicator_tab_btn_group_home));
        textTabItemPlnNon.setTextColor(ContextCompat.getColor(this, R.color.md_white_1000));
        tabPlnNon.setBackground(ContextCompat.getDrawable(PlnActivity.this, R.drawable.unselector_tab_btn_last));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabPlnNon));


    }

    private void bindWidgetsWithAnEvent() {

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(@NonNull TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        viewItemTabPasc.setBackground(ContextCompat.getDrawable(PlnActivity.this, R.drawable.selector_indicator_tab_btn_group_home));
                        textTabItemPlnPasca.setTextColor(ContextCompat.getColor(PlnActivity.this, R.color.colorPrimary_ppob));
                        tabPlnPasca.setBackground(ContextCompat.getDrawable(PlnActivity.this, R.drawable.selector_tab_btn_first));
                        selectedTab = 0;

                        break;
                    case 1:
                        viewItemTabPra.setBackground(ContextCompat.getDrawable(PlnActivity.this, R.drawable.selector_indicator_tab_btn_group_home));
                        textTabItemPlnPra.setTextColor(ContextCompat.getColor(PlnActivity.this, R.color.colorPrimary_ppob));
                        tabPlnPra.setBackground(ContextCompat.getDrawable(PlnActivity.this, R.drawable.selector_tab_btn_center));
                        selectedTab = 1;

                        break;

                    case 2:
                        viewItemTabNon.setBackground(ContextCompat.getDrawable(PlnActivity.this, R.drawable.selector_indicator_tab_btn_group_home));
                        textTabItemPlnNon.setTextColor(ContextCompat.getColor(PlnActivity.this, R.color.colorPrimary_ppob));
                        tabPlnNon.setBackground(ContextCompat.getDrawable(PlnActivity.this, R.drawable.selector_tab_btn_last));
                        selectedTab = 2;
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
                        viewItemTabPasc.setBackground(ContextCompat.getDrawable(PlnActivity.this, R.drawable.unselector_indicator_tab_btn_group_home));
                        textTabItemPlnPasca.setTextColor(ContextCompat.getColor(PlnActivity.this, R.color.md_white_1000));
                        tabPlnPasca.setBackground(ContextCompat.getDrawable(PlnActivity.this, R.drawable.unselector_tab_btn_first));

                        break;

                    case 1:
                        viewItemTabPra.setBackground(ContextCompat.getDrawable(PlnActivity.this, R.drawable.unselector_indicator_tab_btn_group_home));
                        textTabItemPlnPra.setTextColor(ContextCompat.getColor(PlnActivity.this, R.color.md_white_1000));
                        tabPlnPra.setBackground(ContextCompat.getDrawable(PlnActivity.this, R.drawable.unselector_tab_btn_center));

                        break;
                    case 2:
                        viewItemTabNon.setBackground(ContextCompat.getDrawable(PlnActivity.this, R.drawable.unselector_indicator_tab_btn_group_home));
                        textTabItemPlnNon.setTextColor(ContextCompat.getColor(PlnActivity.this, R.color.md_white_1000));
                        tabPlnNon.setBackground(ContextCompat.getDrawable(PlnActivity.this, R.drawable.unselector_tab_btn_last));
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scanner, menu);
        new Handler().post(new Runnable() {
            @Override
            public void run() {

                menuItemView = findViewById(R.id.action_right_scanner);
                // SOME OF YOUR TASK AFTER GETTING VIEW REFERENCE

                if (PreferenceClass.getInt(TAGSHOWCASEPASCA, 0) != 1) {
                    showCaseFirst(context, "", "Klik Scanner untuk input id Pelanggan menggunakan barcode atau QR Code", menuItemView);

                    mGbuilder.setGuideListener(new GuideView.GuideListener() {
                        @Override
                        public void onDismiss(@NonNull View view) {
                            switch (view.getId()) {
                                case R.id.action_right_scanner:
                                    mGbuilder
                                            .setTitle("")
                                            .setContentText("Klik Icon untuk memilih ID/Kontak yang telah tersimpan").setGravity(GuideView.Gravity.center)
                                            .setDismissType(GuideView.DismissType.anywhere)
                                            .setTargetView(plnPascaFragment.imageViewAddressBookPasca)
                                            .build();
                                    break;
                                case R.id.imageViewAddressBookPasca:
                                    mGbuilder
                                            .setTitle("")
                                            .setContentText("Setelah mengisi ID Pelanggan, Klik Simpan ID Pelanggan mempermudah transaksi").setGravity(GuideView.Gravity.center)
                                            .setDismissType(GuideView.DismissType.anywhere)
                                            .setTargetView(plnPascaFragment.checkboxSimpanId)
                                            .build();
                                    break;

                                case R.id.checkboxSimpanIdPasca:
                                    PreferenceClass.putInt(TAGSHOWCASEPASCA, 1);
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
            scanner = item;
            openScanner(PlnActivity.this);
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

    private void requestLayout() {
        Log.d(TAG, "requestLayout: " + selectedTab);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        switch (selectedTab) {
            case 0:
                plnPascaFragment = new PlnPascaFragment();
                fragmentTransaction.replace(R.id.frame_container, plnPascaFragment);
                TAGSHOWCASEPASCA = "plnPascaFragment";

                break;
            case 1:
                plnPraFragment = new PlnPraFragment();
                fragmentTransaction.replace(R.id.frame_container, plnPraFragment);
                TAGSHOWCASEPRA = "plnPraFragment";

                break;
            case 2:
                plnNonFragment = new PlnNonFragment();
                fragmentTransaction.replace(R.id.frame_container, plnNonFragment);
                TAGSHOWCASENONTAG = "plnNonFragment";
                //openShowCasePlnNonTag();
                break;

        }
        if (bottom_toolbar.getVisibility() == View.GONE) {
            closeKeyboard(this);
        }
        fm.executePendingTransactions();
        fragmentTransaction.commit();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.popup_show, R.anim.popup_hide);
    }


    protected void onShowKeyboard(int keyboardHeight) {
        // do things when keyboard is shown
        Log.d(TAG, "onShowKeyboard: " + keyboardHeight);
        bottom_toolbar.setVisibility(View.GONE);
    }

    protected void onHideKeyboard() {
        Log.d(TAG, "onHideKeyboard: ");
        bottom_toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: " + requestCode + " " + resultCode);
        if (resultCode == AppCompatActivity.RESULT_OK) {
            Log.d(TAG, "onActivityResult: " + data.getStringExtra(Intents.Scan.RESULT));
            if (requestCode == ActionCode.BARCODE) {
                String idPel = data.getStringExtra(Intents.Scan.RESULT);

                if (selectedTab == 0) {
                    plnPascaFragment.materialEditTextIdPelanggan.setText(idPel);
                } else if (selectedTab == 1) {
                    plnPraFragment.materialEditTextIdPelanggan.setText(idPel);
                } else if (selectedTab == 2) {
                    plnNonFragment.materialEditTextIdPelanggan.setText(idPel);
                }
            }
        }
    }

    public void openShowCasePlnPra(Context context) {
        if (PreferenceClass.getInt(TAGSHOWCASEPRA, 0) != 1) {
            showCaseFirst(context, "", "Klik Icon untuk memilih ID/Kontak yang telah tersimpan", plnPraFragment.imageViewAddressBookPra);
            mGbuilder.setGuideListener(new GuideView.GuideListener() {
                @Override
                public void onDismiss(@NonNull View view) {
                    switch (view.getId()) {
                        case R.id.imageViewAddressBookPra:
                            mGbuilder
                                    .setTitle("")
                                    .setContentText("Setelah mengisi ID Pelanggan, Klik Simpan ID Pelanggan mempermudah transaksi").setGravity(GuideView.Gravity.center)
                                    .setDismissType(GuideView.DismissType.anywhere)
                                    .setTargetView(plnPraFragment.checkboxSimpanId)
                                    .build();
                            break;

                        case R.id.checkboxSimpanIdPra:
                            PreferenceClass.putInt(TAGSHOWCASEPRA, 1);
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

    public void openShowCasePlnNonTag(Context context) {
        if (PreferenceClass.getInt(TAGSHOWCASENONTAG, 0) != 1) {
            showCaseFirst(context, "", "Klik Icon untuk memilih ID/Kontak yang telah tersimpan", plnNonFragment.imageViewAddressBookNon);
            mGbuilder.setGuideListener(new GuideView.GuideListener() {
                @Override
                public void onDismiss(@NonNull View view) {
                    switch (view.getId()) {
                        case R.id.imageViewAddressBookNon:
                            mGbuilder
                                    .setTitle("")
                                    .setContentText("Setelah mengisi ID Pelanggan, Klik Simpan ID Pelanggan mempermudah transaksi").setGravity(GuideView.Gravity.center)
                                    .setDismissType(GuideView.DismissType.anywhere)
                                    .setTargetView(plnNonFragment.checkboxSimpanId)
                                    .build();
                            break;
                        case R.id.checkboxSimpanIdNon:
                            PreferenceClass.putInt(TAGSHOWCASENONTAG, 1);
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

    protected void onResume() {
        Log.d(TAG, "onResume: ");
        navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));
        super.onResume();
    }
}
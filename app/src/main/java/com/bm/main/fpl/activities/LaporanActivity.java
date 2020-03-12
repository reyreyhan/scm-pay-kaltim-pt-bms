package com.bm.main.fpl.activities;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
//import android.support.design.widget.TabLayout;
import androidx.core.content.ContextCompat;
//import androidx.core.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.LinearLayoutManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.main.pos.R;
import com.bm.main.fpl.adapters.ListLaporanKomisiAdapter;
import com.bm.main.fpl.adapters.ListLaporanMutasiAdapter;
import com.bm.main.fpl.adapters.ListLaporanTransaksiAdapter;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.RequestCode;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.JsonObjectResponseCallback;
import com.bm.main.fpl.models.CUModel;
import com.bm.main.fpl.models.LaporanKomisiModel;
import com.bm.main.fpl.models.LaporanMutasiModel;
import com.bm.main.fpl.models.LaporanTransaksiModel;
import com.bm.main.fpl.utils.RequestUtils;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class LaporanActivity extends KeyboardListenerActivity implements JsonObjectResponseCallback, ListLaporanTransaksiAdapter.OnClickProduk, SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    private static final String TAG = LaporanActivity.class.getSimpleName();
    LinearLayout layout_transaksi, layout_mutasi, layout_komisi, layout_data_empty;
    private int selectedTab = 0;
    TabLayout tabLayout;
    LinearLayout tabTrx, tabMutasi, tabKomisi;
    TextView textTabItemTrx, textTabItemMutasi, textTabItemKomisi;
    RelativeLayout bottom_toolbar_calendar;
    LinearLayout linTanggalAwal, linTanggalAkhir;
    RelativeLayout relTampilkan;
    //    int requestCode;
    SearchView searchView;
    SearchManager searchManager;
    RecyclerView recyclerViewLaporanTransaksi;
    RecyclerView recyclerViewLaporanMutasi;
    RecyclerView recyclerViewLaporanKomisi;
    ListLaporanTransaksiAdapter adapterLaporanTrx;
    ListLaporanMutasiAdapter adapterLaporanMutasi;
    ListLaporanKomisiAdapter adapterLaporanKomisi;
    @NonNull
    ArrayList<LaporanTransaksiModel.Response_value> data = new ArrayList<>();
    @NonNull
    ArrayList<LaporanMutasiModel.Response_value> dataMutasi = new ArrayList<>();
    @NonNull
    ArrayList<LaporanKomisiModel.Response_value> dataKomisi = new ArrayList<>();
    String startDate, endDate;
    TextView textViewTanggalAwal;
    TextView textViewTanggalAkhir;
    private ImageView imageViewCetakMutasi, imageViewSortMutasi, imageViewSortTrx;
    private TextView textViewCetakMutasi;
    LinearLayout linHeaderMutasi, linHeaderKomisi;

    TextView txtHeader, txtSub;
    private String id_pel, produk;
    Context mContext;

    private View viewItemTabTrx, viewItemTabMutasi,
            viewItemTabKomisi;
    private int isSortMutasi = 0;
    private int isSortTrx = 0;
    public String strukTercetak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan);
        logEventFireBase("Laporan Transaksi", "Laporan Transaksi", EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, TAG);
        mContext = this;

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Laporan");
        init(0);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(
                mMessageReceiver,
                new IntentFilter("BROADCAST_PRINTING")
        );

        linHeaderMutasi = findViewById(R.id.linHeaderMutasi);
        linHeaderKomisi = findViewById(R.id.linHeaderKomisi);
        textViewCetakMutasi = findViewById(R.id.textViewCetakMutasi);
        // TextView textViewCetakKomisi = findViewById(R.id.textViewCetakKomisi);

        layout_transaksi = findViewById(R.id.layout_transaksi);
        layout_mutasi = findViewById(R.id.layout_mutasi);
        layout_komisi = findViewById(R.id.layout_komisi);

        layout_data_empty = findViewById(R.id.layout_data_empty);
        txtHeader = findViewById(R.id.txtHeader);
        txtSub = findViewById(R.id.txtSub);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setSelectedTabIndicatorColor(0);
        ViewGroup parent = findViewById(R.id.linCustomTabProduk);

        tabTrx = (LinearLayout) View.inflate(this, R.layout.custom_tab_laporan, parent);
        tabMutasi = (LinearLayout) View.inflate(this, R.layout.custom_tab_laporan, parent);
        tabKomisi = (LinearLayout) View.inflate(this, R.layout.custom_tab_laporan, parent);


        textTabItemTrx = tabTrx.findViewById(R.id.textTab);
        textTabItemMutasi = tabMutasi.findViewById(R.id.textTab);
        textTabItemKomisi = tabKomisi.findViewById(R.id.textTab);

        viewItemTabTrx = tabTrx.findViewById(R.id.viewItemTabLaporan);
        viewItemTabMutasi = tabMutasi.findViewById(R.id.viewItemTabLaporan);
        viewItemTabKomisi = tabKomisi.findViewById(R.id.viewItemTabLaporan);

        imageViewSortMutasi = findViewById(R.id.imageViewSortMutasi);
        imageViewSortMutasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runOnUiThread(() -> {
                    if (isSortMutasi == 0) {
                        isSortMutasi = 1;
                        Collections.sort(adapterLaporanMutasi.filterData, ascIdMutasi);
                        adapterLaporanMutasi.updateList(adapterLaporanMutasi.filterData);
                    } else {
                        isSortMutasi = 0;
                        Collections.sort(adapterLaporanMutasi.filterData, descIdMutasi);
                        adapterLaporanMutasi.updateList(adapterLaporanMutasi.filterData);
                    }
                });
            }
        });

        imageViewSortTrx = findViewById(R.id.imageViewSortTrx);
        imageViewSortTrx.setOnClickListener(view -> runOnUiThread(() -> {
            if (isSortTrx == 0) {
                isSortTrx = 1;
                Collections.sort(adapterLaporanTrx.filterData, ascIdTrx);
                adapterLaporanTrx.updateList(adapterLaporanTrx.filterData);
            } else {
                isSortTrx = 0;
                Collections.sort(adapterLaporanTrx.filterData, descIdTrx);
                adapterLaporanTrx.updateList(adapterLaporanTrx.filterData);
            }
        }));

        imageViewCetakMutasi = findViewById(R.id.imageViewCetakMutasi);

        imageViewCetakMutasi.setOnClickListener(view -> {
            runOnUiThread(() -> cetak(mContext));
            imageViewCetakMutasi.setFocusableInTouchMode(false);
            imageViewCetakMutasi.setEnabled(false);
            textViewCetakMutasi.setText(R.string.on_print);

        });

        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", config.locale);
        SimpleDateFormat formatterShow = new SimpleDateFormat("dd MMM yyyy", config.locale);
        String showDateNow = formatterShow.format(now);
        String dateNow = formatter.format(now);
        startDate = dateNow;
        endDate = dateNow;
        textViewTanggalAwal = findViewById(R.id.textViewTanggalAwal);
        textViewTanggalAwal.setText(showDateNow);
        textViewTanggalAkhir = findViewById(R.id.textViewTanggalAkhir);
        textViewTanggalAkhir.setText(showDateNow);
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        recyclerViewLaporanTransaksi = findViewById(R.id.recyclerViewLaporanTransaksi);
        recyclerViewLaporanMutasi = findViewById(R.id.recyclerViewLaporanMutasi);
        recyclerViewLaporanKomisi = findViewById(R.id.recyclerViewLaporanKomisi);

        searchView = findViewById(R.id.searchViewReportTrx);
        searchView.setFocusable(false);
        SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(R.id.search_src_text);
        searchAutoComplete.setTypeface(tfLight);
        searchAutoComplete.setTextSize(14);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Cari Transaksi");
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerViewLaporanTransaksi.setHasFixedSize(false);
        recyclerViewLaporanTransaksi.setLayoutManager(linearLayoutManager);
        adapterLaporanTrx = new ListLaporanTransaksiAdapter(data, this, this);
        recyclerViewLaporanTransaksi.setAdapter(adapterLaporanTrx);

        LinearLayoutManager linearLayoutManagerMutasi = new LinearLayoutManager(this);
        recyclerViewLaporanMutasi.setHasFixedSize(false);
        recyclerViewLaporanMutasi.setLayoutManager(linearLayoutManagerMutasi);
        adapterLaporanMutasi = new ListLaporanMutasiAdapter(dataMutasi, this);
        recyclerViewLaporanMutasi.setAdapter(adapterLaporanMutasi);


        LinearLayoutManager linearLayoutManagerKomisi = new LinearLayoutManager(this);
        recyclerViewLaporanKomisi.setHasFixedSize(false);
        recyclerViewLaporanKomisi.setLayoutManager(linearLayoutManagerKomisi);
        adapterLaporanKomisi = new ListLaporanKomisiAdapter(dataKomisi, this);
        recyclerViewLaporanKomisi.setAdapter(adapterLaporanKomisi);

        bottom_toolbar_calendar = findViewById(R.id.bottom_toolbar_calendar);
        linTanggalAwal = findViewById(R.id.linTanggalAwal);
        linTanggalAwal.setOnClickListener(view -> {
            Intent intent = new Intent(LaporanActivity.this, TanggalActivity.class);
            intent.putExtra("initTabs", selectedTab);
            intent.putExtra("initTanggal", "awal");
            intent.putExtra("initValue", startDate);
            startActivityForResult(intent, RequestCode.ActionCode_TANGGAL_AWAL);
        });

        linTanggalAkhir = findViewById(R.id.linTanggalAkhir);
        linTanggalAkhir.setOnClickListener(view -> {
            Intent intent = new Intent(LaporanActivity.this, TanggalActivity.class);
            intent.putExtra("initTabs", selectedTab);
            intent.putExtra("initTanggal", "akhir");
            intent.putExtra("initValue", startDate);
            startActivityForResult(intent, RequestCode.ActionCode_TANGGAL_AKHIR);
        });

        relTampilkan = findViewById(R.id.relTampilkan);
        relTampilkan.setOnClickListener(view -> {
            if (selectedTab == 0) {
                requestLaporanTrx();
            } else if (selectedTab == 1) {
                requestLaporanMutasi();
            } else if (selectedTab == 2) {
                requestLaporanKomisi();
            }
        });

        bindWidgetsWithAnEvent();
        setupTabLayout();

        attachKeyboardListeners();
    }


    private void setupTabLayout() {
        Log.d(TAG, "setupTabLayout: ");
        setupTab();
        tabTrx.setSelected(true);
    }

    private void setupTab() {
        textTabItemTrx.setText("Transaksi");
        // imageTabItemTrx.setVisibility(View.GONE);
        viewItemTabTrx.setBackground(ContextCompat.getDrawable(LaporanActivity.this, R.drawable.selector_indicator_tab_btn_group_home));
        tabTrx.setBackground(ContextCompat.getDrawable(LaporanActivity.this, R.drawable.selector_tab_btn_first));
        textTabItemTrx.setTextColor(ContextCompat.getColor(LaporanActivity.this, R.color.colorPrimary_ppob));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabTrx));

        textTabItemMutasi.setText("Mutasi");
        // imageTabItemMutasi.setVisibility(View.GONE);
        viewItemTabMutasi.setBackground(ContextCompat.getDrawable(LaporanActivity.this, R.drawable.unselector_indicator_tab_btn_group_home));
        tabMutasi.setBackground(ContextCompat.getDrawable(LaporanActivity.this, R.drawable.unselector_tab_btn_center));
        textTabItemMutasi.setTextColor(ContextCompat.getColor(LaporanActivity.this, R.color.md_white_1000));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabMutasi));

        textTabItemKomisi.setText("Komisi");
        // imageTabItemBonus.setVisibility(View.GONE);
        viewItemTabKomisi.setBackground(ContextCompat.getDrawable(LaporanActivity.this, R.drawable.unselector_indicator_tab_btn_group_home));
        tabKomisi.setBackground(ContextCompat.getDrawable(LaporanActivity.this, R.drawable.unselector_tab_btn_last));
        textTabItemKomisi.setTextColor(ContextCompat.getColor(LaporanActivity.this, R.color.md_white_1000));
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabKomisi));


    }

    private void bindWidgetsWithAnEvent() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(@NonNull TabLayout.Tab tab) {
                int position = tab.getPosition();
//                TextView textTab;
                // ImageView imageTab;
                switch (position) {
                    case 0:

//                        textTab = tabTrx.findViewById(R.id.textTab);
//
//                        textTab.setTextColor(ContextCompat.getColor(LaporanActivity.this, R.color.md_white_1000));
//
//                        textTab.setTypeface(tfRegular, Typeface.NORMAL);
                        viewItemTabTrx.setBackground(ContextCompat.getDrawable(LaporanActivity.this, R.drawable.selector_indicator_tab_btn_group_home));
                        tabTrx.setBackground(ContextCompat.getDrawable(LaporanActivity.this, R.drawable.selector_tab_btn_first));
                        textTabItemTrx.setTextColor(ContextCompat.getColor(LaporanActivity.this, R.color.colorPrimary_ppob));

                        selectedTab = 0;
                        // type = "REGULAR";

                        break;
                    case 1:

                        viewItemTabMutasi.setBackground(ContextCompat.getDrawable(LaporanActivity.this, R.drawable.selector_indicator_tab_btn_group_home));
                        tabMutasi.setBackground(ContextCompat.getDrawable(LaporanActivity.this, R.drawable.selector_tab_btn_center));
                        textTabItemMutasi.setTextColor(ContextCompat.getColor(LaporanActivity.this, R.color.colorPrimary_ppob));
//                        textTab = tabMutasi.findViewById(R.id.textTab);
//                        textTab.setTextColor(ContextCompat.getColor(LaporanActivity.this, R.color.md_white_1000));
//
//                        textTab.setTypeface(tfRegular, Typeface.NORMAL);

                        selectedTab = 1;
                        // type = "INTERNET";
                        break;
                    case 2:
                        viewItemTabKomisi.setBackground(ContextCompat.getDrawable(LaporanActivity.this, R.drawable.selector_indicator_tab_btn_group_home));
                        tabKomisi.setBackground(ContextCompat.getDrawable(LaporanActivity.this, R.drawable.selector_tab_btn_last));
                        textTabItemKomisi.setTextColor(ContextCompat.getColor(LaporanActivity.this, R.color.colorPrimary_ppob));
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
                        viewItemTabTrx.setBackground(ContextCompat.getDrawable(LaporanActivity.this, R.drawable.unselector_indicator_tab_btn_group_home));
                        tabTrx.setBackground(ContextCompat.getDrawable(LaporanActivity.this, R.drawable.unselector_tab_btn_first));
                        textTabItemTrx.setTextColor(ContextCompat.getColor(LaporanActivity.this, R.color.md_white_1000));
                        break;

                    case 1:
                        viewItemTabMutasi.setBackground(ContextCompat.getDrawable(LaporanActivity.this, R.drawable.unselector_indicator_tab_btn_group_home));
                        tabMutasi.setBackground(ContextCompat.getDrawable(LaporanActivity.this, R.drawable.unselector_tab_btn_center));
                        textTabItemMutasi.setTextColor(ContextCompat.getColor(LaporanActivity.this, R.color.md_white_1000));
                        break;
                    case 2:
                        viewItemTabKomisi.setBackground(ContextCompat.getDrawable(LaporanActivity.this, R.drawable.unselector_indicator_tab_btn_group_home));
                        tabKomisi.setBackground(ContextCompat.getDrawable(LaporanActivity.this, R.drawable.unselector_tab_btn_last));
                        textTabItemKomisi.setTextColor(ContextCompat.getColor(LaporanActivity.this, R.color.md_white_1000));
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


        switch (selectedTab) {
            case 0:
                requestLaporanTrx();
                layout_transaksi.setVisibility(View.VISIBLE);
                layout_mutasi.setVisibility(View.GONE);
                layout_komisi.setVisibility(View.GONE);
                searchView.setFocusable(false);

                break;
            case 1:
                requestLaporanMutasi();
                layout_transaksi.setVisibility(View.GONE);
                layout_mutasi.setVisibility(View.VISIBLE);
                layout_komisi.setVisibility(View.GONE);


                break;
            case 2:
                requestLaporanKomisi();
                layout_transaksi.setVisibility(View.GONE);
                layout_mutasi.setVisibility(View.GONE);
                layout_komisi.setVisibility(View.VISIBLE);
                break;
        }
        Log.d(TAG, "requestLayout: " + selectedTab);
    }


    private void requestLaporanTrx() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestLaporanTransaksi(startDate, endDate, ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.LIST_LAPORAN_TRX, this);
//        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup parent = findViewById(R.id.contentHost);
        final FrameLayout view = (FrameLayout) View.inflate(this, R.layout.loading_bar_full_dialog, parent);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText(R.string.progress_bar_wording);
        openProgressBarDialog(this, view);
    }

    private void requestLaporanMutasi() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestLaporanMutasi(startDate, endDate));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.LIST_LAPORAN_MUTASI, this);
        ViewGroup parent = findViewById(R.id.contentHost);
        final FrameLayout view = (FrameLayout) View.inflate(this, R.layout.loading_bar_full_dialog, parent);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText(R.string.progress_bar_wording);
        openProgressBarDialog(this, view);
    }

    private void requestLaporanKomisi() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestReportKomisi(startDate, endDate));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.LIST_LAPORAN_KOMISI, this);
//        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        if (bottom_toolbar_calendar.getVisibility() == View.GONE) {
            closeKeyboard(this);
        }
        finish();
    }

    @Override
    protected void onShowKeyboard(int keyboardHeight) {
        // do things when keyboard is shown
        Log.d(TAG, "onShowKeyboard: " + keyboardHeight);
        bottom_toolbar_calendar.setVisibility(View.GONE);
    }

    @Override
    protected void onHideKeyboard() {
        Log.d(TAG, "onHideKeyboard: ");
        // do things when keyboard is hidden
        bottom_toolbar_calendar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        Log.d(TAG, "onSuccess: " + response.toString());
        closeProgressBarDialog();

        if (actionCode == ActionCode.LIST_LAPORAN_TRX) {

            Log.d(TAG, "onSuccess: " + response.toString());
            try {
                LaporanTransaksiModel laporanTransaksiModel = gson.fromJson(response.toString(), LaporanTransaksiModel.class);
                data.clear();

                if (laporanTransaksiModel.getResponse_code().equalsIgnoreCase(ResponseCode.SUCCESS)) {
                    data.addAll(laporanTransaksiModel.getResponse_value());

                    if (data.size() > 0) {
                        if (searchView.getQuery().length() > 0) {
                            adapterLaporanTrx.getFilter().filter(searchView.getQuery().toString());
                            Log.d(TAG, "onSuccess: " + adapterLaporanTrx.getItemCount());
                        }
                        searchView.setVisibility(View.VISIBLE);
                        searchView.setFocusable(false);
                        layout_transaksi.setVisibility(View.VISIBLE);
                        layout_data_empty.setVisibility(View.GONE);
                        imageViewSortTrx.setVisibility(View.VISIBLE);
                    } else {
                        layout_transaksi.setVisibility(View.GONE);
                        layout_data_empty.setVisibility(View.VISIBLE);
                        txtHeader.setText("Laporan Transaksi");
                        txtSub.setText("Tidak Ditemukan");
                        searchView.setVisibility(View.GONE);
                        imageViewSortTrx.setVisibility(View.GONE);
                    }

                    adapterLaporanTrx.notifyDataSetChanged();
                    //searchView.setFocusable(false);
                } else if (laporanTransaksiModel.getResponse_code().equals("03")) {
                    new_popup_alert_session(this, "Informasi", laporanTransaksiModel.getResponse_desc());
                } else {
                    layout_transaksi.setVisibility(View.GONE);
                    layout_data_empty.setVisibility(View.VISIBLE);
                    txtHeader.setText("Laporan Transaksi");
                    txtSub.setText("Tidak Ditemukan");
                    searchView.setVisibility(View.GONE);
                    imageViewSortTrx.setVisibility(View.GONE);
                }
            } catch (Exception jsone) {
                layout_transaksi.setVisibility(View.GONE);
                layout_data_empty.setVisibility(View.VISIBLE);
                txtHeader.setText("Ada Masalah Saat Menampilkan Data");
                txtSub.setText("Coba Lagi");
                searchView.setVisibility(View.GONE);
                imageViewSortTrx.setVisibility(View.GONE);
            }
        } else if (actionCode == ActionCode.LIST_LAPORAN_MUTASI) {
            Log.d(TAG, "onSuccess: " + response.toString());
            LaporanMutasiModel laporanMutasiModel = gson.fromJson(response.toString(), LaporanMutasiModel.class);
//            if (laporanMutasiModel != null) {
//                laporanMutasiModel = null;
//
//            }
            dataMutasi.clear();
            // laporanMutasiModel = gson.fromJson(response.toString(), LaporanMutasiModel.class);
            if (laporanMutasiModel.getResponse_code().equalsIgnoreCase(ResponseCode.SUCCESS)) {


                dataMutasi.addAll(laporanMutasiModel.getResponse_value());
                //  setData(gameModel.getData());
                adapterLaporanMutasi.notifyDataSetChanged();

                if (dataMutasi.size() > 0) {
                    //  linHeaderMutasi.setVisibility(View.VISIBLE);
                    layout_transaksi.setVisibility(View.GONE);
                    layout_data_empty.setVisibility(View.GONE);
                    imageViewSortMutasi.setVisibility(View.VISIBLE);
                } else {
                    //  linHeaderMutasi.setVisibility(View.GONE);
                    layout_transaksi.setVisibility(View.GONE);
                    layout_data_empty.setVisibility(View.VISIBLE);
                    txtHeader.setText("Laporan Mutasi");
                    txtSub.setText("Tidak Ditemukan");
                    imageViewSortMutasi.setVisibility(View.GONE);
                }

                //textViewMutasi.setText(laporanMutasiModel.getResponse_cetak().toString());
                strukTercetak = laporanMutasiModel.getResponse_cetak().replace("<br>", "\n").replace("<b>", ResponseCode.BoldOn).replace("</b>", ResponseCode.BoldOff);

                // webViewMutasi.loadData(laporanMutasiModel.getResponse_cetak(), "text/html", "UTF-8");
            } else if (laporanMutasiModel.getResponse_code().equals("03")) {
                new_popup_alert_session(this, "Informasi", laporanMutasiModel.getResponse_desc());
            } else {
                //  linHeaderMutasi.setVisibility(View.GONE);
                layout_transaksi.setVisibility(View.GONE);
                layout_data_empty.setVisibility(View.VISIBLE);
                txtHeader.setText("Laporan Mutasi");
                txtSub.setText("Tidak Ditemukan");
                imageViewSortMutasi.setVisibility(View.GONE);
            }
        } else if (actionCode == ActionCode.LIST_LAPORAN_KOMISI) {
            Log.d(TAG, "onSuccess: " + response.toString());
            LaporanKomisiModel laporanKomisiModel = gson.fromJson(response.toString(), LaporanKomisiModel.class);
//            if (laporanMutasiModel != null) {
//                laporanMutasiModel = null;
//
//            }
            dataKomisi.clear();
            // laporanMutasiModel = gson.fromJson(response.toString(), LaporanMutasiModel.class);
            if (laporanKomisiModel.getResponse_code().equalsIgnoreCase(ResponseCode.SUCCESS)) {


                dataKomisi.addAll(laporanKomisiModel.getResponse_value());
                //  setData(gameModel.getData());
                adapterLaporanKomisi.notifyDataSetChanged();

                if (dataKomisi.size() > 0) {
                    // linHeaderKomisi.setVisibility(View.VISIBLE);
                    layout_transaksi.setVisibility(View.GONE);
                    layout_data_empty.setVisibility(View.GONE);
                } else {
                    //  linHeaderKomisi.setVisibility(View.GONE);
                    layout_transaksi.setVisibility(View.GONE);
                    layout_data_empty.setVisibility(View.VISIBLE);
                    txtHeader.setText("Laporan Komisi");
                    txtSub.setText("Tidak Ditemukan");
                }

                //textViewMutasi.setText(laporanMutasiModel.getResponse_cetak().toString());
                strukTercetak = laporanKomisiModel.getResponse_cetak().replace("<br>", "\n").replace("<c>", ResponseCode.BoldOn).replace("</c>", ResponseCode.BoldOff);

                // webViewMutasi.loadData(laporanMutasiModel.getResponse_cetak(), "text/html", "UTF-8");
            } else if (laporanKomisiModel.getResponse_code().equals("03")) {
                new_popup_alert_session(this, "Informasi", laporanKomisiModel.getResponse_desc());
            } else {
                // linHeaderKomisi.setVisibility(View.GONE);
                layout_transaksi.setVisibility(View.GONE);
                layout_data_empty.setVisibility(View.VISIBLE);
                txtHeader.setText("Laporan Kimisi");
                txtSub.setText("Tidak Ditemukan");
            }
        } else if (actionCode == ActionCode.CETAK_ULANG) {
            //  closeProgressBarDialog();
//                    Gson gson = new GsonBuilder().create();
            CUModel cuModel = gson.fromJson(response.toString(), CUModel.class);
            if (cuModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                String html = cuModel.getResponse_desc();
                String struk_tercetak = cuModel.getStruk_tercetak();
                String struk_show = cuModel.getStruk_show();
                ////Log.d(TAG,response.getString("response_value"));
                Intent intent = new Intent(LaporanActivity.this, CetakUlangActivity.class);
                intent.putExtra("value", html);
                intent.putExtra("struk_tercetak", struk_tercetak);
                intent.putExtra("struk_show", struk_show);
                intent.putExtra("id_pel", id_pel);
                intent.putExtra("produk", produk);
                // intent.putExtra("saldo",cuModel.getSaldo());
                //  PreferenceClass.putString("saldo", FormatString.CurencyIDR(cuModel.getSaldo()));
                startActivity(intent);
                //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            } else {
                // MyDynamicToast.warningMessage(LaporanActivity.this,cuModel.getResponse_desc());
                new_popup_alert(LaporanActivity.this, "Informasi", cuModel.getResponse_desc());
            }

        }

//            }
//        } catch (JSONException jsone) {
//            Log.d(TAG, "JSONException: " + jsone.toString());
//        }
    }

    @Override
    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {
        closeProgressBarDialog();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RequestCode.ActionCode_TANGGAL_AWAL && resultCode == AppCompatActivity.RESULT_OK) {
            //    searchView.setFocusable(false);
            // relTampilkan.requestFocus();
            //searchView.setFocusable(false);
            // searchView.setIconifiedByDefault(false);
            //     closeKeyboard(this);
            startDate = data.getStringExtra("date");
            textViewTanggalAwal.setText(data.getStringExtra("dateShow"));
            if (selectedTab == 0) {
                searchView.setFocusable(false);
                searchView.setIconifiedByDefault(false);
            }
        } else if (requestCode == RequestCode.ActionCode_TANGGAL_AKHIR && resultCode == AppCompatActivity.RESULT_OK) {
            //  searchView.setFocusable(false);
            // relTampilkan.requestFocus();
            //  searchView.setFocusable(false);
            //  searchView.setIconifiedByDefault(false);
            //   closeKeyboard(this);
            endDate = data.getStringExtra("date");
            textViewTanggalAkhir.setText(data.getStringExtra("dateShow"));
            if (selectedTab == 0) {
                searchView.setFocusable(false);
                searchView.setIconifiedByDefault(false);
            }
        }
    }

//    @Override
//    protected void onResume() {
////        searchView.setFocusable(false);
////        searchView.setIconifiedByDefault(false);
//        closeKeyboard(this);
//        super.onResume();
//    }

    @Override
    public boolean onClose() {
        adapterLaporanTrx.getFilter().filter("");
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        adapterLaporanTrx.getFilter().filter(query);
        closeKeyboard(this);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapterLaporanTrx.getFilter().filter(newText);
        return false;
    }

//    @Override
//    public void onClick(View view) {
//
//    }

    @Override
    public void onClickProduk(@NonNull LaporanTransaksiModel.Response_value produk) {
        Log.d(TAG, "onClickProduk: " + produk.getKeterangan() + " " + produk.getProduk());
        requestCetakUlang(produk.getProduk(), produk.getKeterangan(), produk.getId_transaksi());
    }

    private void requestCetakUlang(String produk, String id_pel, String id_transaksi) {
        JSONObject jsonObject = new JSONObject();
        try {
            this.id_pel = id_pel;
            this.produk = produk;
            jsonObject = new JSONObject(BaseActivity.stringJson.requestCetakTransaksi(id_transaksi));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.CETAK_ULANG, this);
        ViewGroup parent = findViewById(R.id.contentHost);
        @SuppressLint("InflateParams") final FrameLayout view = (FrameLayout) View.inflate(this, R.layout.loading_bar_full_dialog, parent);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText(R.string.progress_bar_wording);
        openProgressBarDialog(this, view);
    }

    // Initialize da new BroadcastReceiver mInstance
    @NonNull
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, @NonNull Intent intent) {
            // Get the received random number
            boolean receivedNumber = intent.getBooleanExtra("finish", false);
            if (receivedNumber) {
                imageViewCetakMutasi.setFocusableInTouchMode(true);
                imageViewCetakMutasi.setEnabled(true);
                textViewCetakMutasi.setText("");
            }
            // Display da notification that the broadcast received
            //     Toast.makeText(context,"Received : " + receivedNumber,Toast.LENGTH_SHORT).show();
        }
    };


    @NonNull
    public Comparator<LaporanMutasiModel.Response_value> ascIdMutasi = new Comparator<LaporanMutasiModel.Response_value>() {
        public int compare(@NonNull LaporanMutasiModel.Response_value app1, @NonNull LaporanMutasiModel.Response_value app2) {
            String stringName1 = String.valueOf(app1.getId_mutasi());
            String stringName2 = String.valueOf(app2.getId_mutasi());
            Log.d(TAG, "compare id mutasi: " + stringName1 + " " + stringName2);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                return Integer.compare(Integer.parseInt(stringName1), Integer.parseInt(stringName2));
            } else {
                return ((Integer) Integer.parseInt(stringName1)).compareTo(Integer.parseInt(stringName2));
            }
        }

    };
    @NonNull
    public Comparator<LaporanMutasiModel.Response_value> descIdMutasi = new Comparator<LaporanMutasiModel.Response_value>() {
        public int compare(@NonNull LaporanMutasiModel.Response_value app1, @NonNull LaporanMutasiModel.Response_value app2) {
            String stringName1 = String.valueOf(app1.getId_mutasi());
            String stringName2 = String.valueOf(app2.getId_mutasi());
            Log.d(TAG, "compare id mutasi: " + stringName1 + " " + stringName2);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                return Integer.compare(Integer.parseInt(stringName2), Integer.parseInt(stringName1));
            } else {
                return ((Integer) Integer.parseInt(stringName2)).compareTo(Integer.parseInt(stringName1));
            }
        }

    };


    @NonNull
    public Comparator<LaporanTransaksiModel.Response_value> ascIdTrx = new Comparator<LaporanTransaksiModel.Response_value>() {
        public int compare(@NonNull LaporanTransaksiModel.Response_value app1, @NonNull LaporanTransaksiModel.Response_value app2) {
            String stringName1 = String.valueOf(app1.getId_transaksi());
            String stringName2 = String.valueOf(app2.getId_transaksi());
            Log.d(TAG, "compare id trx: " + stringName1 + " " + stringName2);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                return Integer.compare(Integer.parseInt(stringName1), Integer.parseInt(stringName2));
            } else {
                return ((Integer) Integer.parseInt(stringName1)).compareTo(Integer.parseInt(stringName2));
            }
        }

    };
    @NonNull
    public Comparator<LaporanTransaksiModel.Response_value> descIdTrx = new Comparator<LaporanTransaksiModel.Response_value>() {
        public int compare(@NonNull LaporanTransaksiModel.Response_value app1, @NonNull LaporanTransaksiModel.Response_value app2) {
            String stringName1 = String.valueOf(app1.getId_transaksi());
            String stringName2 = String.valueOf(app2.getId_transaksi());
            Log.d(TAG, "compare id trx: " + stringName1 + " " + stringName2);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                return Integer.compare(Integer.parseInt(stringName2), Integer.parseInt(stringName1));
            } else {
                return ((Integer) Integer.parseInt(stringName2)).compareTo(Integer.parseInt(stringName1));
            }
        }

    };

}

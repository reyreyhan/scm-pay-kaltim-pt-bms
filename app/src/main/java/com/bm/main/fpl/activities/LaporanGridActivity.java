package com.bm.main.fpl.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bm.main.fpl.interfaces.ClickListener;
import com.bm.main.fpl.listener.RecyclerTouchListener;
import com.bm.main.fpl.staggeredgridApp.MenuLaporanModel;
import com.bm.main.fpl.staggeredgridApp.SolventRecyclerViewLaporanAdapter;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.pos.R;
import com.bm.main.single.ftl.activities.TravelPesananActivity;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bm.main.single.ftl.flight.activities.FlightSearchRevActivity;
import com.bm.main.single.ftl.ship.activities.ShipSearchRevActivity;
import com.bm.main.single.ftl.train.activities.TrainSearchRevActivity;

import java.util.ArrayList;
import java.util.List;

public class LaporanGridActivity extends BaseActivity {

    Context context;
    @NonNull
    private int[] menuIcons = {
            R.drawable.ic_menu_laporan_ppob,
            R.drawable.ic_menu_laporan_travel,

    };
    @NonNull
    private String[] menuTitle = {
            "PPOB",
            "Tiket & Travel",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_grid);
        context = this;
        initView();
        init(0);
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Laporan Transaksi");
        RecyclerView mGridLaporanRecycler = (RecyclerView) findViewById(R.id.recycler_grid_laporan);
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        mGridLaporanRecycler.setLayoutManager(mLayoutManager);
        mGridLaporanRecycler.addOnItemTouchListener(new RecyclerTouchListener(this,
                mGridLaporanRecycler, new ClickListener() {
            @Override
            public void onClick(final View view, final int position) {
                callActivity(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        List<MenuLaporanModel> menuList = new ArrayList<>();
        for (int i = 0; i < menuIcons.length; i++) {
            MenuLaporanModel menuLaporanModel = new MenuLaporanModel(menuTitle[i], menuIcons[i]);
            menuList.add(menuLaporanModel);
        }

        // Initialize da new mInstance of RecyclerView Adapter mInstance
        SolventRecyclerViewLaporanAdapter mAdapter = new SolventRecyclerViewLaporanAdapter(this, menuList);

        // Set the adapter for RecyclerView
        mGridLaporanRecycler.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void callActivity(int position) {
        if (position == 0) {
            Intent intent = new Intent(LaporanGridActivity.this, LaporanActivity.class);
            startActivity(intent);
        } else if (position == 1) {
            if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                new_popup_alertDemo(this, "Info", "Anda belum bisa menikmati fiture ini.\n" +
                        "Daftar & Aktifasi sekarang juga ID Anda");
            } else {
                Intent intent = new Intent(LaporanGridActivity.this, TravelPesananActivity.class);
                startActivityForResult(intent, TravelActionCode.IS_FROM_PAY);
            }
        }

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TravelActionCode.IS_FROM_PAY && resultCode == RESULT_OK) {
            if (data != null && data.getAction() != null) {
                Intent intent = null;
                switch (data.getAction()) {
                    case TravelActionCode.MENU_PESAWAT:
                        intent = new Intent(this, FlightSearchRevActivity.class);
                        break;
                    case TravelActionCode.MENU_KERETA:
                        intent = new Intent(this, TrainSearchRevActivity.class);
                        break;
                    case TravelActionCode.MENU_PELNI:
                        intent = new Intent(this, ShipSearchRevActivity.class);
                        break;
                    case TravelActionCode.MENU_PESANAN:
                        if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                            new_popup_alertDemo(this, "Info", "Anda belum bisa menikmati fiture ini.\n" +
                                    "Daftar & Aktifasi sekarang juga ID Anda");
                        } else {
                            intent = new Intent(this, TravelPesananActivity.class);
                        }
                        break;
                }

                if (intent != null) {
                    startActivityForResult(intent, TravelActionCode.IS_FROM_PAY);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        }
    }
}

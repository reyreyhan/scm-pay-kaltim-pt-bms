package com.bm.main.fpl.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bm.main.fpl.staggeredgridApp.MenuGasModel;
import com.bm.main.fpl.staggeredgridApp.SolventRecyclerViewGasAdapter;
import com.bm.main.scm.R;

import java.util.ArrayList;
import java.util.List;

public class GasActivity extends BaseActivity {
    private RecyclerView recycler_menu_gas;
    @NonNull
    private int[] menuIcons = {
            R.drawable.ic_menu_pgn,
            R.drawable.ic_menu_pertagas,

    };
    @NonNull
    private String[] menuTitle = {
            "PGN",
            "PERTAGAS",
    };

    private SolventRecyclerViewGasAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<MenuGasModel> menuList;
    private MenuGasModel menuGasModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Gas");
        init(0);
        recycler_menu_gas = findViewById(R.id.recycler_menu_gas);
        setMenu();
    }

    private void setMenu() {
        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        recycler_menu_gas.setLayoutManager(mLayoutManager);
        menuList = new ArrayList<>();
        for (int i = 0; i < menuIcons.length; i++) {
            menuGasModel = new MenuGasModel(menuTitle[i], menuIcons[i]);
            menuList.add(menuGasModel);
        }

        // Initialize da new mInstance of RecyclerView Adapter mInstance
        mAdapter = new SolventRecyclerViewGasAdapter(this, menuList);

        // Set the adapter for RecyclerView
        recycler_menu_gas.setAdapter(mAdapter);
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
        finish();
    }
}

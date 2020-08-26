package com.bm.main.fpl.activities;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.bm.main.scm.R;
import com.bm.main.fpl.staggeredgridApp.MenuPerbankanModel;
import com.bm.main.fpl.staggeredgridApp.SolventRecyclerViewPerbankanAdapter;

import java.util.ArrayList;
import java.util.List;

public class PerbankanActivity extends BaseActivity {
    private static final String TAG = PerbankanActivity.class.getSimpleName();
    private RecyclerView recycler_menu_perbankan;
    @NonNull
    private int[] menuIcons = {
            R.drawable.ic_menu_bni,
            R.drawable.ic_menu_mandiri,

    };
    @NonNull
    private String[] menuTitle ={
            "Laku Pandai",
            "MANDIRI",

    };

    private SolventRecyclerViewPerbankanAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<MenuPerbankanModel> menuList;
    private MenuPerbankanModel menuPerbankanModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perbankan);


        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Perbankan");
        init(0);
        recycler_menu_perbankan = findViewById(R.id.recycler_menu_perbankan);
        setMenu();


    }

    private void setMenu() {

        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        recycler_menu_perbankan.setLayoutManager(mLayoutManager);
        menuList = new ArrayList<>();
        for (int i = 0; i < menuIcons.length; i++) {
            menuPerbankanModel = new MenuPerbankanModel(menuTitle[i], menuIcons[i]);
            menuList.add(menuPerbankanModel);
        }
        // Initialize da new mInstance of RecyclerView Adapter mInstance
        mAdapter = new SolventRecyclerViewPerbankanAdapter(this, menuList);


        // Set the adapter for RecyclerView
        recycler_menu_perbankan.setAdapter(mAdapter);
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
//        if (bottom_toolbar.getVisibility() == View.GONE) {
//            closeKeyboard(this);
//        }

        finish();
        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


    }


}

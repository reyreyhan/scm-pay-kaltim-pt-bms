package com.bm.main.fpl.activities;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;

import com.bm.main.pos.R;
import com.bm.main.fpl.staggeredgridApp.MenuPinjamDanaModel;
import com.bm.main.fpl.staggeredgridApp.SolventRecyclerViewPinjamDanaAdapter;

import java.util.ArrayList;
import java.util.List;

public class PinjamDanaActivity extends BaseActivity {
    private static final String TAG = PinjamDanaActivity.class.getSimpleName();
    private RecyclerView recycler_menu_pinjaman;
    @NonNull
    private int[] menuIcons = {
            R.drawable.ic_menu_pinjemdoku,
            R.drawable.ic_menu_tunaiku,

    };
    @NonNull
    private String[] menuTitle ={
            "Doku",
            "Tunaiku",

    };

    private SolventRecyclerViewPinjamDanaAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<MenuPinjamDanaModel> menuList;
    private MenuPinjamDanaModel menuPinjamDanaModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinjam_dana);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pinjaman Dana");
        init(0);
        recycler_menu_pinjaman = findViewById(R.id.recycler_menu_pinjaman);
        setMenu();


    }

    private void setMenu() {

        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        recycler_menu_pinjaman.setLayoutManager(mLayoutManager);
        menuList = new ArrayList<>();
        for (int i = 0; i < menuIcons.length; i++) {
            menuPinjamDanaModel = new MenuPinjamDanaModel(menuTitle[i], menuIcons[i]);
            menuList.add(menuPinjamDanaModel);
        }
        // Initialize da new mInstance of RecyclerView Adapter mInstance
        mAdapter = new SolventRecyclerViewPinjamDanaAdapter(this, menuList);


        // Set the adapter for RecyclerView
        recycler_menu_pinjaman.setAdapter(mAdapter);
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

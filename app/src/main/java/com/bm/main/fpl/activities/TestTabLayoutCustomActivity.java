package com.bm.main.fpl.activities;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.tabs.TabLayout;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.main.scm.R;

public class TestTabLayoutCustomActivity extends BaseActivity {
@NonNull
static String TAG=TestTabLayoutCustomActivity.class.getSimpleName();
    private TabLayout tabLayout;

    public int selectedTab = 0;
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tab_layout_custom);
        context=this;
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Title is Here");
        toolbar.setSubtitle("SubTitle is Here");
        tabLayout = findViewById(R.id.tabs);

       tabLayout.setSelectedTabIndicatorColor(0);

        tabInput =  (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.custom_tab, null);

        tabDaftar =  (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.custom_tab, null);

        textTabItemInput =  tabInput.findViewById(R.id.textTab);
        textTabItemDaftar =  tabDaftar.findViewById(R.id.textTab);
        init(1);
        //toolbarTitle.setText("ini title nya ");
       // toolbarSubTitle.setText("ini sub title nya x xdgdvdvdszxvxddvxvxvxcvx,");

//        toolbarTitle =  findViewById(R.id.textViewToolbarTitle);
//        toolbarTitle.setSelected(true);
//
//        toolbarSubTitle =  findViewById(R.id.textViewToolbarSubTitle);
//        if(!toolbarSubTitle.getText().equals("")) {
//            toolbarSubTitle.setVisibility(View.VISIBLE);
//            toolbarSubTitle.setSelected(true);
//        }else{
//            toolbarSubTitle.setVisibility(View.GONE);
//        }

        bindWidgetsWithAnEvent();
        setupTabLayout();

    }
    RelativeLayout tabInput, tabDaftar;
    TextView textTabItemInput, textTabItemDaftar;
    private void setupTab() {



        textTabItemInput.setText("Masukkan ID Pelanggan");
        textTabItemInput.setTextColor(ContextCompat.getColor(context,R.color.md_white_1000));

        tabLayout.addTab(tabLayout.newTab().setCustomView(tabInput));





        textTabItemDaftar.setText("Dari Daftar ID Pelanggan");
        textTabItemDaftar.setTextColor(ContextCompat.getColor(context,R.color.md_grey_500));

        tabLayout.addTab(tabLayout.newTab().setCustomView(tabDaftar));
    }

    private void setupTabLayout() {
        Log.d(TAG, "setupTabLayout: ");
        setupTab();
        tabInput.setSelected(true);
    }

    private void bindWidgetsWithAnEvent() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(@NonNull TabLayout.Tab tab) {
                int position = tab.getPosition();
                TextView textTab;
                switch (position) {
                    case 0:

                        textTab =  tabInput.findViewById(R.id.textTab);
                        textTab.setTextColor(ContextCompat.getColor(context,R.color.md_white_1000));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            tabInput.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_button_blue_big_tab_item_pressed));
                        } else {
                            tabInput.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.shape_button_blue_big_tab_item_pressed));
                        }
                       // textTabItemInput.setCompoundDrawablePadding(0);//CompoundDrawablesWithIntrinsicBounds(0, R.drawable.button_blue_big_tab_item_pressed, 0, 0);
                        selectedTab=0;

                        break;
                    case 1:

                        textTab =  tabDaftar.findViewById(R.id.textTab);
                        textTab.setTextColor(ContextCompat.getColor(context,R.color.md_white_1000));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            tabDaftar.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_button_blue_big_tab_item_pressed));
                        } else {
                            tabDaftar.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.shape_button_blue_big_tab_item_pressed));
                        }

                        selectedTab=1;

                        break;





                }
                requestLayout();// call activity here for appCompatButtonDaftar id pelanggan

            }

            @Override
            public void onTabUnselected(@NonNull TabLayout.Tab tab) {

                Log.d(TAG, "onTabUnselected: " + tab.getPosition());
                int position = tab.getPosition();
                TextView textTab;
                switch (position) {
                    case 0:
                        textTab =tabInput.findViewById(R.id.textTab);
                        textTab.setTextColor(ContextCompat.getColor(context,R.color.md_grey_500));
                       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                           tabInput.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_button_blue_big_tab_item_default));
                } else {
                           tabInput.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.shape_button_blue_big_tab_item_default));
                }


                        break;

                    case 1:

                        textTab =  tabDaftar.findViewById(R.id.textTab);
                        textTab.setTextColor(ContextCompat.getColor(context,R.color.md_grey_500));
                       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                           tabDaftar.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_button_blue_big_tab_item_default));
                } else {
                           tabDaftar.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.shape_button_blue_big_tab_item_default));
                }

                        break;


                }


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void requestLayout() {
        Log.d(TAG, "requestLayout: "+selectedTab);
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
////            Promo promo = new Promo();
////            setFragment(promo);
//            menuTop.setVisibility(View.VISIBLE);
//
//        }
            //   View contentView = getLayoutInflater().inflate(R.layout.dialog_top, toolbar);
            //    contentView.setVisibility(View.VISIBLE);

            openTopDialog(false);
        } else if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//    }
}

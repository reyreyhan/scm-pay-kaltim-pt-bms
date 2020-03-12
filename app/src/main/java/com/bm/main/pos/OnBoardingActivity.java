package com.bm.main.pos;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.annotation.NonNull;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bm.main.fpl.activities.BigPromoActivity;
import com.bm.main.fpl.activities.LoginActivity;
import com.bm.main.fpl.adapters.OnBoard_Adapter;
import com.bm.main.fpl.constants.RConfig;
import com.bm.main.fpl.models.OnBoardModel;
import com.bm.main.fpl.utils.PreferenceClass;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OnBoardingActivity extends AppCompatActivity implements View.OnClickListener {
    @NonNull
    String TAG = OnBoardingActivity.class.getSimpleName();

    public static DisplayMetrics displayMetrics;
    public static Configuration config;

    private int dotsCount;
    private ImageView[] dots;
    private OnBoard_Adapter mAdapter;
    @NonNull
    ArrayList<OnBoardModel.Response_value> onBoardItems = new ArrayList<>();
    private ViewPager mPagerView;
    private LinearLayout mLayoutDots;
    private AppCompatButton mNextBtn;

    OnBoardModel onBoardModel;
    private int current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        runMemory();
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_onboarding);
        initView();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        loadData();

        mAdapter = new OnBoard_Adapter(this, onBoardItems);
        mPagerView.setAdapter(mAdapter);
        mPagerView.setCurrentItem(0);
        mPagerView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                runMemory();
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(OnBoardingActivity.this, R.drawable.non_selected_item_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(OnBoardingActivity.this, R.drawable.selected_item_dot));

                if (position == dotsCount - 1) {
                    mNextBtn.setText("Mulai");
                } else {
                    mNextBtn.setText("Berikutnya");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                runMemory();
            }
        });

        setUiPageViewController();
    }

    private void initView() {
        mPagerView = findViewById(R.id.view_pager);
        mLayoutDots = findViewById(R.id.layoutDots);
        mNextBtn = findViewById(R.id.btn_next);
        mNextBtn.setOnClickListener(this);
    }

    public void loadData() {
        onBoardModel = new Gson().fromJson(PreferenceClass.getString(RConfig.ON_BOARD, ""), OnBoardModel.class);
        if (onBoardModel.getResponse_value() != null) {
            onBoardItems.addAll(onBoardModel.getResponse_value());
        }
    }

    private void setUiPageViewController() {

        dotsCount = mAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(OnBoardingActivity.this, R.drawable.non_selected_item_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(6, 0, 6, 0);

            mLayoutDots.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(OnBoardingActivity.this, R.drawable.selected_item_dot));
    }

    private int getItem(int i) {
        return mPagerView.getCurrentItem() + i;
    }

    @Override
    public void onClick(@NonNull View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                Runtime.getRuntime().gc();

                current = getItem(+1);
                if (current < dotsCount) {
                    mPagerView.setCurrentItem(current, true);
                } else {
                    launchHomeScreen();
                }
                break;
            default:
                break;
        }
    }

    private void launchHomeScreen() {
        Intent intent;
        PreferenceClass.putBoolean("firstStart", false);
        if (PreferenceClass.getString(RConfig.IS_BIG_PROMO, "0").equals("1")) {
            intent = new Intent(OnBoardingActivity.this, BigPromoActivity.class);
        } else {
            intent = new Intent(OnBoardingActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
        startActivity(intent);
        finish();
    }

    private void runMemory() {
        int size = (1024 * 1024 * 40);//Integer.MAX_VALUE;
        int factor = 10;

        try {
            while (true) {
                try {
                    Log.d(TAG, "Trying to allocate " + getFileSize(size));
                    Runtime.getRuntime().gc();
                    Log.d(TAG, "Succeed! " + getFileSize(Runtime.getRuntime().freeMemory()));
                    break;
                } catch (Exception e) {
                    Log.d(TAG, "runOutOfMemory: " + e.toString());
                } catch (OutOfMemoryError e) {
                    Log.d(TAG, "runOutOfMemory: OOME .. Trying again with 10x less " + getFileSize(size /= factor));
                    size /= factor;
                    Runtime.getRuntime().gc();
                }
            }
        } catch (RuntimeException e) {
            Log.d(TAG, "runOutOfMemory: " + e.toString());
            Log.d(TAG, "runOutOfMemory: ampun memory habis");
        }
    }

    @NonNull
    public static String getFileSize(long size) {
        if (size <= 0)
            return "0";

        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));

        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
package com.bm.main.fpl.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
//import android.support.annotation.NonNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
//import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.main.pos.R;
import com.bm.main.fpl.constants.RConfig;
import com.bm.main.fpl.templates.indicators.AVLoadingIndicatorView;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
//import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class BigPromoActivity extends AppCompatActivity {
    private static final String TAG =BigPromoActivity.class.getSimpleName() ;
    public static DisplayMetrics displayMetrics;
    public Configuration config;

    private ImageView[] dots;
//    private ImageView mImageViewClosed;
    TextView textViewSkip;
//    private ImageView mImageViewBigPromo;
    private LinearLayout mLayoutDots;
    private int count=0;
    private int countReverse=5;
    AVLoadingIndicatorView avi;
    private int dotCount=5;
    private Timer T;
    RelativeLayout relBigPromo;
    RelativeLayout.LayoutParams lp;
//    private CoordinatorLayout coordinator;
    private RelativeLayout linMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        runMemory();
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_big_promo);
        initView();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();

        StrictMode.setVmPolicy(builder.build());

        Resources res = this.getResources();
        config = res.getConfiguration();
        displayMetrics = res.getDisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        switch (displayMetrics.densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                //     Log.d(TAG, "onCreate: base density low font " + config.fontScale + " screen" + config.screenLayout);
                config.fontScale = 0.93f;

                break;
            case DisplayMetrics.DENSITY_MEDIUM:

                if (displayMetrics.widthPixels >= 600) {
                    //     Log.d(TAG, "onCreate: base density medium font " + config.fontScale + " screen" + config.screenLayout);
                    config.fontScale = 1.3f;

                } else {
                    //     Log.d(TAG, "onCreate: base density medium font " + config.fontScale + " screen" + config.screenLayout);
                    config.fontScale = 0.9f;

                }


                break;
            case DisplayMetrics.DENSITY_HIGH:
                if (displayMetrics.widthPixels >= 600) {
                    //       Log.d(TAG, "onCreate: base density high font " + config.fontScale + " screen" + config.screenLayout);
                    config.fontScale = 1.7f;

                } else {
                    //          Log.d(TAG, "onCreate: base density high font " + config.fontScale + " screen" + config.screenLayout);
                    config.fontScale = 0.6f;

                }

                break;
            case DisplayMetrics.DENSITY_XHIGH:
                //   Log.d(TAG, "onCreate: base density xhigh font " + config.fontScale + " screen" + config.screenLayout);
                config.fontScale = 0.95f;


                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                //   Log.d(TAG, "onCreate: base density xxhigh font " + config.fontScale + " screen" + config.screenLayout);
                config.fontScale = 0.98f;


                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                // Log.d(TAG, "onCreate: base density xxxhigh font " + config.fontScale + " screen" + config.screenLayout);
                config.fontScale = 1.0f;


            case DisplayMetrics.DENSITY_TV:
                if (displayMetrics.widthPixels >= 600) {
                    //    Log.d(TAG, "onCreate: base density TV font " + config.fontScale + " screen" + config.screenLayout);
                    config.fontScale = 1.4f;

                } else {
                    config.fontScale = 1.0f;

                }

                break;

            default:
                if (displayMetrics.widthPixels >= 600) {
                    //   Log.d(TAG, "onCreate: base density else font " + config.fontScale + " screen" + config.screenLayout);
                    config.fontScale = 17.5f;

                } else {
                    //    Log.d(TAG, "onCreate: base density else font " + config.fontScale + " screen" + config.screenLayout);
                    config.fontScale = 1.0f;
                }

        }
        config.setTo(getResources().getConfiguration());
        //  Configuration.setTo(getResources().getConfiguration());
        config.locale = new Locale("in", "ID");
        onConfigurationChanged(config);


        int heightDp = (getResources().getDisplayMetrics().heightPixels * 2) / 2;
        int weightDp = (getResources().getDisplayMetrics().widthPixels * 2) / 2;
//        int heightDpx = (rootView.getResources().getDisplayMetrics().heightPixels * 2) / 7;
//        Log.d(TAG, "onCreateView: " + heightDp);
        linMain=findViewById(R.id.linMain);
        lp = (RelativeLayout.LayoutParams) linMain.getLayoutParams();
//        lpx = (CollapsingToolbarLayout.LayoutParams) lin_header.getLayoutParams();


        lp.height = heightDp;
        lp.width = weightDp;
        // lpx.height = heightDpx;

        // carouselView.setMinimumHeight(lp.height);
        relBigPromo.setMinimumHeight(lp.height);
        relBigPromo.setMinimumWidth(lp.width);
       // final ImageView imageView =  itemView.findViewById(R.id.imageViewOnBoard);
//        imageView.setImageResource(item.getImageID());
//        Glide.with(this).load(PreferenceClass.getString(RConfig.BIG_PROMO,"")).asBitmap().encoder(new BitmapEncoder(Bitmap.CompressFormat.WEBP, 50)).diskCacheStrategy(DiskCacheStrategy.RESULT).into(new BitmapImageViewTarget(mImageViewBigPromo) {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> animation) {
//                // here it's similar to RequestListener, but with less information (e.g. no model available)
//                super.onResourceReady(resource, animation);
////                viewHolder.avi.setVisibility(View.GONE);
//                // here you can be sure it's already set
//            }
//            // +++++ OR +++++
//            @Override protected void setResource(Bitmap resource) {
//                // this.getView().setImageDrawable(resource); is about to be called
//                super.setResource(resource);
//                avi.setVisibility(View.GONE);
//                mImageViewBigPromo.setScaleType(ImageView.ScaleType.FIT_XY);
//
//                // here you can be sure it's already set
//            }
//
//            @Override
//            public void onLoadFailed(Exception e, Drawable errorDrawable) {
//                super.onLoadFailed(e, errorDrawable);
//                avi.setVisibility(View.GONE);
////                viewHolder.avi.setVisibility(View.GONE);
//                mImageViewBigPromo.setImageDrawable(ContextCompat.getDrawable(BigPromoActivity.this, R.drawable.srikandi));
//            }
//        });

//        final RelativeLayout linOnBoardItem=itemView.findViewById(R.id.linOnBoardItem);
//        float aspectRatio = 6/3f;
//        int newWidth = displayMetrics.widthPixels;
//        int newHeight = (int) (newWidth / aspectRatio);
//        Log.d(TAG, "instantiateItem: "+newWidth+" "+newHeight);

        Glide.with(this).asBitmap().load(PreferenceClass.getString(RConfig.BIG_PROMO,"")).sizeMultiplier(1).fitCenter().encodeFormat(Bitmap.CompressFormat.WEBP).encodeQuality(50).diskCacheStrategy(DiskCacheStrategy.NONE).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> animation) {
                avi.setVisibility(View.GONE);
                Drawable drawable = new BitmapDrawable(getResources(), resource);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    relBigPromo.setBackground(drawable);
                }
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
    super.onLoadFailed(errorDrawable);
                avi.setVisibility(View.GONE);
            }
        });


        setUiPageViewController();
        T = new Timer();
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // myTextView.setText("count="+count);
                        Log.d("run", "count="+count);

                        for (int i = 0; i < dotCount; i++) {
                            dots[i].setImageDrawable(ContextCompat.getDrawable(BigPromoActivity.this, R.drawable.non_selected_item_dot));
                        }


                        dots[count].setImageDrawable(ContextCompat.getDrawable(BigPromoActivity.this, R.drawable.selected_item_dot));


                        if(count==4){
                            gogogo();
                        }
                        textViewSkip.setText("Lewati "+countReverse--+"s");
                        count++;


                    }
                });
            }
        }, 1000, 1000);
       // mImageViewClosed=findViewById(R.id.imageViewClosed);
        textViewSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
gogogo();
                    }
                });
            }
        });
    }
    private void setUiPageViewController() {

       // dotsCount = mAdapter.getCount();
        dots = new ImageView[dotCount];

        for (int i = 0; i < dotCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(BigPromoActivity.this, R.drawable.non_selected_item_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(6, 0, 6, 0);

            mLayoutDots.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(BigPromoActivity.this, R.drawable.selected_item_dot));
    }
    private void gogogo() {
        T.cancel();
        Intent intent;
        if (PreferenceClass.isLoggedIn()) {


            if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                intent = new Intent(BigPromoActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

            } else {

                intent = new Intent(BigPromoActivity.this, KunciActivity.class);
            }

        } else {
            intent = new Intent(BigPromoActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);



        }
        startActivity(intent);
        finish();
    }

    private void initView() {
        avi = findViewById(R.id.avi);
        relBigPromo=findViewById(R.id.relBigPromo);
       // mImageViewClosed = findViewById(R.id.imageViewClosed);
        textViewSkip=findViewById(R.id.textViewSkip);
        textViewSkip.setText("Lewati "+countReverse+"s");
;//        mImageViewBigPromo = findViewById(R.id.imageViewBigPromo);
        mLayoutDots =  findViewById(R.id.layoutDots);
    }

    private void runMemory() {
        int size = (1024 * 1024 * 40);//Integer.MAX_VALUE;
        int factor = 10;

        try {
            while (true) {
                try {
                    //     Log.d(TAG, "Trying to allocate " + getFileSize(size));
//                    byte[] bytes = new byte[size];

                    Runtime.getRuntime().gc();
                    //    Log.d(TAG, "Succeed! " + getFileSize(Runtime.getRuntime().freeMemory()));
                    break;
                } catch (Exception e) {
//                e.printStackTrace();
                    //    Log.d(TAG, "runOutOfMemory: " + e.toString());
                } catch (OutOfMemoryError e) {
                    //    Log.d(TAG, "runOutOfMemory: OOME .. Trying again with 10x less " + getFileSize(size /= factor));
                    size /= factor;
//                       byte[] bytes = new byte[size];
                    Runtime.getRuntime().gc();

                }
            }
        } catch (RuntimeException e) {

//            Log.d(TAG, "runOutOfMemory: " + e.toString());
//            Log.d(TAG, "runOutOfMemory: ampun memory habis");
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

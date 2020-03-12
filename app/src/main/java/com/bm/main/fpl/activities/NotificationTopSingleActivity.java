package com.bm.main.fpl.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.main.pos.R;
import com.bm.main.fpl.constants.FCMConstants;
import com.bm.main.fpl.templates.indicators.AVLoadingIndicatorView;
import com.bm.main.pos.SplashScreenActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;

//import com.bm.main.fpl.utils.ExceptionHandler;
//import com.squareup.picasso.Callback;
//import com.squareup.picasso.Picasso;

public class NotificationTopSingleActivity extends AppCompatActivity {
    private static final String TAG =NotificationTopSingleActivity.class.getSimpleName() ;
    String title,url,content;

    Toolbar toolbar;
TextView textViewJudul,textViewContent;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_top_single);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll().permitAll().build());
        Intent intent=getIntent();

//        init(true);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(this, R.style.TextAppearance_Widget_ActionBar_Title);
        //     toolbar.setSubtitleTextAppearance(this, R.style.TextAppearance_Widget_ActionBar_Subtitle);
        toolbar.setTitleTextColor(Color.WHITE);
        //      toolbar.setSubtitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_cancel);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Notifikasi");

        if(intent!=null) {
            url = intent.getStringExtra("url");
            title = intent.getStringExtra("title");
            content = intent.getStringExtra("conten");
        }

        imageView=findViewById(R.id.imageViewNotif);
        textViewJudul=findViewById(R.id.textViewJudul);
        textViewJudul.setText(title);
        textViewContent=findViewById(R.id.textViewContent);
        textViewContent.setText(content);
        final AVLoadingIndicatorView avi = findViewById(R.id.avi);
//        Picasso.with(this)
//                .load(url).into(imageView, new Callback() {
//            @Override
//            public void onSuccess() {
//                avi.setVisibility(View.GONE);
//              //  imageView.getDrawingCache(true);
//            }
//
//            @Override
//            public void onError() {
//                avi.setVisibility(View.GONE);
//                imageView.setImageDrawable(ContextCompat.getDrawable(NotificationTopSingleActivity.this, R.drawable.srikandi));
//            }
//
//        });
        Log.d(TAG, "onCreate: "+url);

        Glide.with(this).asBitmap().load(url).fitCenter().encodeFormat(Bitmap.CompressFormat.WEBP).encodeQuality(50).diskCacheStrategy(DiskCacheStrategy.NONE).into(new BitmapImageViewTarget(imageView) {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> animation)  {
                // here it's similar to RequestListener, but with less information (e.g. no model available)
                super.onResourceReady(resource, animation);
                avi.setVisibility(View.GONE);
                // here you can be sure it's already set
            }
            // +++++ OR +++++
            @Override protected void setResource(Bitmap resource) {
                // this.getView().setImageDrawable(resource); is about to be called
                super.setResource(resource);
                avi.setVisibility(View.GONE);
                // here you can be sure it's already set
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
    super.onLoadFailed(errorDrawable);
                avi.setVisibility(View.GONE);
                imageView.setImageDrawable(ContextCompat.getDrawable(NotificationTopSingleActivity.this, R.drawable.srikandi));
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
//                if(FCMConstants.isActivityRunning==true){
//                    finish();
//                }else {
//                    Intent intent = new Intent(NotificationTopSingleActivity.this, SplashScreenActivity.class);
//                    startActivity(intent);
//                    ActivityCompat.finishAffinity(NotificationTopSingleActivity.this);
//                }
        }
        return true;
    }

    @Override
    public void onBackPressed()
    {
//        if (result != null && result.isDrawerOpen())
//        {
//            result.closeDrawer();
//        }
//        else
        // {
        //  super.onBackPressed();
        // }
        //  finish();
        if(FCMConstants.isActivityRunning==true){
            finish();
        }else {
            Intent intent = new Intent(NotificationTopSingleActivity.this, SplashScreenActivity.class);
            startActivity(intent);
            ActivityCompat.finishAffinity(NotificationTopSingleActivity.this);
        }
    }

    @Override
    protected void onDestroy() {
        Glide.get(this).clearMemory();
        super.onDestroy();
    }
}

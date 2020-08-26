package com.bm.main.fpl.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.main.scm.R;
import com.bm.main.fpl.interfaces.JsonObjectResponseCallback;
import com.bm.main.fpl.templates.carouselview.CarouselView;
import com.bm.main.fpl.templates.carouselview.ViewListener;

import org.json.JSONObject;

public class WelcomeSliderActivity extends BaseActivity implements JsonObjectResponseCallback,View.OnClickListener {
    CarouselView carouselView;
    int positions = 0;
    int NUMBER_OF_PAGES = 5;
    @NonNull
    int[] image_carousel = {R.mipmap.ic_launcher, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_slider);
        Resources res = getResources();
        carouselView = findViewById(R.id.carouselView);
        carouselView.setPageCount(NUMBER_OF_PAGES);
        carouselView.setViewListener(viewListener);
        viewListener.setViewForPosition(0);

        TextView textSkip = findViewById(R.id.text_carousel_skip);
        textSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goHome();

            }
        });
    }
    public void setPosition(int position){
        viewListener.setViewForPosition(position);
    }
    @NonNull
    ViewListener viewListener = new ViewListener() {
        @Override
        public View setViewForPosition(final int position) {
            positions = position;
            View customView;
            customView = getLayoutInflater().inflate(R.layout.activity_welcome_slider_content, null);
            ImageView imageCarousel = customView.findViewById(R.id.image_carousel);

            final ImageView imgNext = customView.findViewById(R.id.img_carousel_next);
            imageCarousel.setImageResource(image_carousel[position]);
            imgNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position+1!=5) {
                        carouselView.setCurrentItem(position + 1);
                    }else{
                        goHome();
                    }
                }
            });
            return customView;
        }
    };
    public void goHome(){
        Intent a = new Intent(WelcomeSliderActivity.this,LoginActivity.class);
        a.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(a);
        finish();

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onSuccess(int actionCode, JSONObject response) {

    }

    @Override
    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {

    }
}

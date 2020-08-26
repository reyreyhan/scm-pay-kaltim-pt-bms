package com.bm.main.fpl.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatRatingBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.main.scm.R;
import com.bm.main.fpl.interfaces.JsonObjectResponseCallback;
import com.bm.main.fpl.models.PromoProdukModel;
import com.bm.main.fpl.templates.indicators.AVLoadingIndicatorView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
//import com.bumptech.glide.load.resource.drawable.GlideDrawable;
//import com.bumptech.glide.request.animation.GlideAnimation;
//import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import org.json.JSONObject;

//import com.squareup.picasso.Callback;
//import com.squareup.picasso.Picasso;

public class PromoProdukDetail extends BaseActivity implements JsonObjectResponseCallback {
    private static final String TAG =PromoProdukDetail.class.getSimpleName() ;
    //  Toolbar toolbar;
    TextView text_notif_title_desc,text_notif_detail_desc;
    ImageView img_notif_desc,share_wa,share_fb,share_twitter;
    Button button_rate;
    AppCompatRatingBar ratingBar;
    PromoProdukModel.Response_value promoProdukModel;
    AVLoadingIndicatorView avi;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promoproduk_detail);
        context=this;
        Intent intent = getIntent();
        if(intent!=null) {
            promoProdukModel = intent.getParcelableExtra("promo");
            // setContent(notificationModel);
        }
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Promo "+promoProdukModel.getProduk());
        init(1);
        ratingBar=findViewById(R.id.imageViewRatingOutlet);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(@NonNull RatingBar ratingBar,
                                        float rating, boolean fromUser) {

                // Get currnet rating
                String ratedValue = String.valueOf(rating);

                // Get Number of stars
                int numStars = ratingBar.getNumStars();

                Toast.makeText(getApplicationContext(),"Your Selected Ratings  : " + String.valueOf(rating),Toast.LENGTH_LONG).show();
                // Now set the rated Value and numstars on textView
                //ratingValue_default.setText("Rating value is : " + ratedValue + "/" + numStars);
                //Log.d(TAG, "Rating value is : " + ratedValue + "/" + numStars);
            }
        });

        text_notif_detail_desc = findViewById(R.id.textViewPlusContentDetail);
        text_notif_title_desc = findViewById(R.id.textViewPlusJudulDetail);
        avi=findViewById(R.id.avi);
        share_wa = findViewById(R.id.share_wa);
//        share_fb = findViewById(R.id.share_fb);
//        share_twitter = findViewById(R.id.share_twitter);
        button_rate = findViewById(R.id.button_rate);

       // setToolbar();

        //setContent();
        img_notif_desc = findViewById(R.id.imageViewPromoDetail);
        text_notif_title_desc.setText(promoProdukModel.getJudul());
        text_notif_detail_desc.setText(promoProdukModel.getContent());
       // Log.d(TAG, "setContent: "+notificationModel.getThumbnail());
//        if(!promoProdukModel.getUrl_gambar().equalsIgnoreCase("")) {
//          runOnUiThread(new Runnable() {
//              @Override
//              public void run() {
//                  Picasso.with(PromoProdukDetail.this).load(promoProdukModel.getUrl_gambar()).fit().into(img_notif_desc);
//              }
//          });
//
//        }else{
//            img_notif_desc.setImageDrawable(FormatString.setImage(this,R.drawable.ic_logo));
//        }

//        Picasso. with(this)
//                .load(promoProdukModel.getUrl_gambar()).into(img_notif_desc, new Callback() {
//            @Override
//            public void onSuccess() {
//                avi.setVisibility(View.GONE);
//              //  img_notif_desc.getDrawingCache(true);
//            }
//
//            @Override
//            public void onError() {
//                avi.setVisibility(View.GONE);
//                img_notif_desc.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_logo));
//            }
//
//        });
//
//

        Glide.with(this).load(promoProdukModel.getUrl_gambar()).dontAnimate().diskCacheStrategy(DiskCacheStrategy.NONE).into(new DrawableImageViewTarget(img_notif_desc) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> animation) {
                // here it's similar to RequestListener, but with less information (e.g. no model available)
                super.onResourceReady(resource, animation);
                avi.setVisibility(View.GONE);
                // here you can be sure it's already set
            }
            // +++++ OR +++++
            @Override protected void setResource(Drawable resource) {
                // this.getView().setImageDrawable(resource); is about to be called
                super.setResource(resource);
                avi.setVisibility(View.GONE);
                // here you can be sure it's already set
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
    super.onLoadFailed(errorDrawable);
                avi.setVisibility(View.GONE);
                img_notif_desc.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_logo_color));
            }
        });


    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_rumah, menu);
//        //return true;
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify da parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//        if (id == R.id.action_right_filter){
//            onBackPressed();
//        }else
            if(id == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {


        finish();
        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


    }

//    public void setToolbar() {
//        toolbar.setTitleTextColor(FormatString.setColor(this,R.color.md_white_1000));
//        toolbar.setNavigationIcon(R.drawable.ic_back);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Notifikasi");
//    }
//    public void setContent(NotificationModel.Response_value notificationModel){
//    public void setContent(){
//
//
//
//    }



    @Override
    public void onSuccess(int actionCode, JSONObject response) {

    }

    @Override
    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {

    }

    @Override
    protected void onDestroy() {
        Glide.get(this).clearMemory();
        super.onDestroy();
    }
}

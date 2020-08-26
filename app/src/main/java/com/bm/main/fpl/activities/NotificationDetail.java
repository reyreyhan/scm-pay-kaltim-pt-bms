package com.bm.main.fpl.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.main.fpl.utils.Device;
import com.bm.main.scm.R;
import com.bm.main.fpl.interfaces.JsonObjectResponseCallback;
import com.bm.main.fpl.models.NotificationModel;
import com.bm.main.fpl.templates.indicators.AVLoadingIndicatorView;
import com.bm.main.fpl.utils.FormatString;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
//import com.bumptech.glide.load.resource.drawable.GlideDrawable;
//import com.bumptech.glide.request.animation.GlideAnimation;
//import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import org.json.JSONObject;

public class NotificationDetail extends BaseActivity implements JsonObjectResponseCallback {
    private static final String TAG = NotificationDetail.class.getSimpleName();
    //  Toolbar toolbar;
    TextView text_notif_title_desc, text_notif_detail_desc;
    ImageView img_notif_desc, share_wa, share_fb, share_twitter;
    Button button_rate;
    RatingBar rating_bar;
    NotificationModel.Response_value notificationModel;
    AVLoadingIndicatorView avi;
    private LinearLayout linSelenkapnya;
    private FrameLayout frame_icon;
    private RelativeLayout header;
    private LinearLayout.LayoutParams lp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);
        Intent intent = getIntent();
        if (intent != null) {
            notificationModel = intent.getParcelableExtra("notif");
        }
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Notifikasi");
        init(1);
        avi = findViewById(R.id.avi);
        text_notif_detail_desc = findViewById(R.id.textViewPlusContentDetail);
        text_notif_title_desc = findViewById(R.id.textViewPlusJudulDetail);
        linSelenkapnya = findViewById(R.id.linSelenkapnya);
        frame_icon = findViewById(R.id.frame_icon);

        button_rate = findViewById(R.id.button_rate);
        rating_bar = findViewById(R.id.imageViewRatingOutlet);

        //setContent();
        img_notif_desc = findViewById(R.id.img_notif_desc);
        text_notif_title_desc.setText(notificationModel.getSubject());
        text_notif_detail_desc.setText(notificationModel.getContent());
        Log.d(TAG, "setContent: " + notificationModel.getThumbnail());
        if (!notificationModel.getThumbnail().equalsIgnoreCase("")) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    Glide.with(NotificationDetail.this).load(notificationModel.getThumbnail()).dontAnimate().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(new DrawableImageViewTarget(img_notif_desc) {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, Transition<? super Drawable> animation) {
                            // here it's similar to RequestListener, but with less information (e.g. no model available)
                            super.onResourceReady(resource, animation);
                            avi.setVisibility(View.GONE);
                            // here you can be sure it's already set
                        }

                        // +++++ OR +++++
                        @Override
                        protected void setResource(Drawable resource) {
                            // this.getView().setImageDrawable(resource); is about to be called
                            super.setResource(resource);
                            avi.setVisibility(View.GONE);
                            // here you can be sure it's already set
                        }

                        @Override
                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            avi.setVisibility(View.GONE);
                            img_notif_desc.setImageDrawable(ContextCompat.getDrawable(NotificationDetail.this, R.mipmap.ic_launcher));
                        }
                    });

                }
            });

        } else {
            img_notif_desc.setImageDrawable(FormatString.setImage(this, R.drawable.srikandi));
            // frame_icon.setVisibility(View.GONE);
        }
        header = findViewById(R.id.header);

        float heightDp = (getResources().getDisplayMetrics().heightPixels * 2) / 7;
        lp = (LinearLayout.LayoutParams) header.getLayoutParams();

        lp.height = (int) heightDp;
        header.setMinimumHeight(lp.height);
        linSelenkapnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent();
                browserIntent.setAction(Intent.ACTION_VIEW);

                browserIntent.addCategory(Intent.CATEGORY_BROWSABLE);
                browserIntent.setData(Uri.parse(notificationModel.getLink()));
                startActivity(browserIntent);
            }
        });

        final Uri screenshotUri = FileProvider.getUriForFile(NotificationDetail.this, "com.bm.main.pos.fileprovider", saveImageBaseFileImageView(img_notif_desc));
        share_wa = findViewById(R.id.share_wa);
        share_wa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Device.SharingToSocialMedia(NotificationDetail.this, "WA", screenshotUri);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify da parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//        if (id == R.id.action_right_filter){
//            onBackPressed();
//        }else
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        Log.d(TAG, "onSuccess: " + response.toString());

    }

    @Override
    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {
    }
}

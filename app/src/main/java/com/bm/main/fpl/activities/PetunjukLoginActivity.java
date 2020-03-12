package com.bm.main.fpl.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.RConfig;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.pos.R;
import com.bm.main.pos.SBFApplication;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.analytics.FirebaseAnalytics;

//public class PetunjukLoginActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{
public class PetunjukLoginActivity extends BaseActivity implements YouTubePlayer.OnInitializedListener {
    private static final String TAG = PetunjukLoginActivity.class.getSimpleName();
    TextView textViewHowToUse;
    ImageView imageViewPetunjukLogin, imageViewCancel;
    // PreferenceClass preferenceClass;
    // Toolbar toolbar;
//    private View viewContainer;
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private int heightDp;
    //    private YouTubePlayerView youTubeView;
    @Nullable
    private YouTubePlayerFragment youTubeView;
    //private FirebaseAnalytics mFirebaseAnalytics;
    // private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petunjuk_login);
        //  overridePendingTransition(0, 0);


        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Cara Login Fastpay");
        init(1);
        //  SBFApplication application = (SBFApplication) getApplication();
//        mTracker = application.getDefaultTracker();
//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        logEventFireBase("Petunjuk Login", "Petunjuk Login Fastpay", EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, TAG);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //preferenceClass=new PreferenceClass(this);
        //   overridePendingTransition(0,0);
        //   toolbar = findViewById(R.id.toolbar);
        // toolbar.setTitleTextAppearance(this, R.style.TextAppearance_Widget_ActionBar_Title);
        //     toolbar.setSubtitleTextAppearance(this, R.style.TextAppearance_Widget_ActionBar_Subtitle);
//        toolbar.setTitleTextColor(Color.WHITE);
//        //      toolbar.setSubtitleTextColor(Color.WHITE);
//        toolbar.setNavigationIcon(R.drawable.ic_cancel);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Promo");
        //   viewContainer =  findViewById(R.id.view_container);

//        toolbar.setTitle("Cara Login Fastpay");
        //  init(1);
//        getActionBar().setHomeButtonEnabled(true);
//        getActionBar().setTitle("Cara Login Fastpay");
//        getActionBar().setLogo(R.drawable.ic_closed_blue);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            toolbar.setTitleTextAppearance(this, R.style.TextAppearance_Widget_ActionBar_Title);
//            toolbar.setTitleTextColor(Color.WHITE);
//////        //      toolbar.setSubtitleTextColor(Color.WHITE);
//            toolbar.setNavigationIcon(R.drawable.ic_cancel);
//        }
//             toolbar.setSubtitleTextAppearance(this, R.style.TextAppearance_Widget_ActionBar_Subtitle);
//
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//            setActionBar(toolbar);
//        }
//        getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setTitle("Cara Login Fastpay");
//
//        imageViewCancel=findViewById(R.id.imageViewCancel);
//        imageViewCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//        NestedScrollView nestedScrollView = findViewById(R.id.nestedscrollView);
//        nestedScrollView.setSmoothScrollingEnabled(true);
        ScrollView scrollView = findViewById(R.id.scrollView);
        ViewCompat.setNestedScrollingEnabled(scrollView, false);
//        nestedScrollView.setOnScrollChangeListener(this);
//        appBarLayout = findViewById(R.id.appBarLayout);
        imageViewPetunjukLogin = findViewById(R.id.imageViewPetunjukLogin);
        heightDp = (getResources().getDisplayMetrics().heightPixels * 2) / 7;
        Log.d(TAG, "onCreateView: " + heightDp);
        CollapsingToolbarLayout.LayoutParams lp = (CollapsingToolbarLayout.LayoutParams) imageViewPetunjukLogin.getLayoutParams();
//        CollapsingToolbarLayout.LayoutParams lp = (CollapsingToolbarLayout.LayoutParams) lin_header.getLayoutParams();

        lp.height = (int) heightDp;
//        lin_header.setMinimumHeight(lp.height);
        imageViewPetunjukLogin.setMinimumHeight(lp.height);


        // Glide.with(this).load(R.drawable.slider_cara_login).asBitmap().encoder(new BitmapEncoder(Bitmap.CompressFormat.WEBP, 50)).into(imageViewPetunjukLogin);
        Glide.with(this).load(R.drawable.slider_cara_login).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageViewPetunjukLogin);
        textViewHowToUse = findViewById(R.id.textViewHowToUse);

        textViewHowToUse.setText(Html.fromHtml(getResources().getString(R.string.petunjuk_login)));
        // String urlVideo = PreferenceClass.getEdukasiLogin();
        //  youTubeView =  findViewById(R.id.youtube_view);
//         youTubeView =
//                (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_view);
        youTubeView = (YouTubePlayerFragment)
                getFragmentManager().findFragmentById(R.id.youtube_view);
        // youTubeView.setMinimumHeight(lp.height);
        youTubeView.initialize(PreferenceClass.getString(RConfig.API_YOU_TUBE, ""), this);
//        VideoView video = findViewById(R.id.videoViewYouTube);
////        Log.d(TAG , urlVideo);
//        video.setVideoURI(Uri.parse(urlVideo));
//        MediaController mc = new MediaController(this);
//        video.setMediaController(mc);
//        video.requestFocus();
//        video.start();
//        mc.show();

//        Log.d(TAG, "onCreate: "+youTubeView.isInLayout());
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
    public void onBackPressed() {
        finish();
        //   overridePendingTransition(0, 0);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, @NonNull YouTubePlayer player,
                                        boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo(PreferenceClass.getEdukasiLogin());
        }
        if (player.isPlaying()) {
            player.setFullscreen(true);
        }
    }


    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        @NonNull YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    "YouTubePlayer Error,Pastikan Applikasi Youtube Anda telah terUpdate",
                    errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(PreferenceClass.getString(RConfig.API_YOU_TUBE, ""), this);
        }
    }

    @NonNull
    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }

    /**
     * //     * This method logEventFireBase.
     * //     *
     * //     * @param itemCategory    FirebaseAnalytics.Param.ITEM_CATEGORY
     * //     * @param itemName    FirebaseAnalytics.Param.ITEM_NAME
     * //     * @param action    FirebaseAnalytics.Param.CONTENT_TYPE
     * //     * @param success    FirebaseAnalytics.Param.SUCCESS
     * //     * @param tag    ClassName
     * <p>
     * <p>
     * //
     */
    public void logEventFireBase(String itemCategory, String itemName, String action, String success, String tag) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "Petunjuk Login");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, itemName);
        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, itemCategory);
        bundle.putString(FirebaseAnalytics.Param.SOURCE, tag);
        bundle.putString(FirebaseAnalytics.Param.SUCCESS, success);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, action);
        //  mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        SBFApplication.sendEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        // Log.d(TAG, "logEventFireBase: "+mFirebaseAnalytics.getFirebaseInstanceId()+" "+mFirebaseAnalytics.getAppInstanceId());
        SBFApplication.sendToAnalylic(itemCategory, itemName, action, tag);
    }

}

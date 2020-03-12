package com.bm.main.fpl.activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;
//import android.support.design.widget.AppBarLayout;
//import android.support.design.widget.CollapsingToolbarLayout;
//import android.support.design.widget.FloatingActionButton;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.main.fpl.utils.DialogUtils;
import com.bm.main.fpl.utils.Utils;
import com.bm.main.pos.R;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.utils.FormatString;
import com.bm.main.fpl.utils.PreferenceClass;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

//import android.widget.Toolbar;

public class AktivasiAkunActivity extends BaseActivity {
    private static final String TAG = AktivasiAkunActivity.class.getSimpleName();
    TextView textViewAktivasi1, textViewAktivasi2, textViewAktivasi3, textViewAktivasi4, textViewAktivasi5, textViewAktivasi6, textViewAktivasi7, textViewAktivasi8;
    //Toolbar toolbar;
    ImageView imageViewCancel;
    private String nominal;
    FloatingActionButton fabLiveChat;
    AppBarLayout appBarLayout;
    ImageView imageViewPetunjukAktivasi;
    private int heightDp;
    private String id_outlet;
    private boolean appBarExpanded = true;
    private Menu collapsedMenu;
    CollapsingToolbarLayout.LayoutParams lp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktivasi_akun);

        Intent intent = getIntent();
        if (intent != null)
            id_outlet = intent.getStringExtra("id_outlet");
        nominal = intent.getStringExtra("nominal");


        logEventFireBase("Aktivasi Akun", "Akun " + id_outlet + " Belum Teraktivasi", EventParam.EVENT_ACTION_VISIT, EventParam.EVENT_SUCCESS, TAG);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Aktivasi AKun");
        init(1);


//        imageViewCancel=findViewById(R.id.imageViewCancel);
//        imageViewCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        textViewAktivasi1 = findViewById(R.id.textViewAktivasi1);
        textViewAktivasi2 = findViewById(R.id.textViewAktivasi2);
        textViewAktivasi3 = findViewById(R.id.textViewAktivasi3);
        textViewAktivasi4 = findViewById(R.id.textViewAktivasi4);
        textViewAktivasi5 = findViewById(R.id.textViewAktivasi5);
        textViewAktivasi6 = findViewById(R.id.textViewAktivasi6);
        textViewAktivasi7 = findViewById(R.id.textViewAktivasi7);
        textViewAktivasi8 = findViewById(R.id.textViewAktivasi8);

        textViewAktivasi1.setText(Html.fromHtml(String.format(getString(R.string.petunjuk_aktivasi_1), FormatString.CurencyIDR(nominal))));

        textViewAktivasi2.setText(Html.fromHtml(String.format(getString(R.string.petunjuk_aktivasi_2), FormatString.CurencyIDR(nominal))));

        textViewAktivasi3.setText(Html.fromHtml(getString(R.string.petunjuk_aktivasi_3)));


        String pAktivasi4 = getResources().getString(R.string.petunjuk_aktivasi_4);
        SpannableString ss = new SpannableString(FormatString.htmlString(pAktivasi4));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View textView) {
                DialogUtils.new_popup_bantuan(AktivasiAkunActivity.this);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan, 122, 131, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // TextView textView = (TextView) findViewById(R.id.hello);
        textViewAktivasi4.setText(ss);
        textViewAktivasi4.setMovementMethod(LinkMovementMethod.getInstance());
        textViewAktivasi4.setHighlightColor(Color.TRANSPARENT);


        textViewAktivasi5.setText(Html.fromHtml(getString(R.string.petunjuk_aktivasi_5)));
        textViewAktivasi6.setText(Html.fromHtml(getString(R.string.petunjuk_aktivasi_6)));
        textViewAktivasi7.setText(Html.fromHtml(getString(R.string.petunjuk_aktivasi_7)));

        final String textLayanan = "https://www.fastpay.co.id/layanan";

        String pAktivasi8 = getResources().getString(R.string.petunjuk_aktivasi_8);
        SpannableString ss8 = new SpannableString(Html.fromHtml(String.format(getString(R.string.petunjuk_aktivasi_8), textLayanan)));
        ClickableSpan clickableSpan8 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View textView) {
//                TextView textView1=findViewById(R.id.getId());
                // startActivity(new Intent(MyActivity.this, NextActivity.class));
                //   Log.d(TAG, "onClick: "+((TextView)textView).getText().subSequence(155,148)+" "+textLayanan);
                CustomTabsIntent customTabsIntent = buildCustomTabsIntent();

                customTabsIntent.launchUrl(AktivasiAkunActivity.this, Uri.parse(textLayanan));
                //  overridePendingTransition(0, 0);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss8.setSpan(clickableSpan8, 115, 148, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // TextView textView = (TextView) findViewById(R.id.hello);
        textViewAktivasi8.setText(ss8);

        textViewAktivasi8.setMovementMethod(LinkMovementMethod.getInstance());
        textViewAktivasi8.setHighlightColor(Color.TRANSPARENT);


        NestedScrollView nestedScrollView = findViewById(R.id.nestedscrollView);
        nestedScrollView.setSmoothScrollingEnabled(true);
//        appBarLayout = findViewById(R.id.appBarLayout);
        imageViewPetunjukAktivasi = findViewById(R.id.imageViewPetunjukAktivasi);
        heightDp = (getResources().getDisplayMetrics().heightPixels * 2) / 6;
        Log.d(TAG, "onCreateView: " + heightDp);
        lp = (CollapsingToolbarLayout.LayoutParams) imageViewPetunjukAktivasi.getLayoutParams();
//        CollapsingToolbarLayout.LayoutParams lp = (CollapsingToolbarLayout.LayoutParams) lin_header.getLayoutParams();

        lp.height = (int) heightDp;
//        lin_header.setMinimumHeight(lp.height);
        imageViewPetunjukAktivasi.setMinimumHeight(lp.height);
        appBarLayout = findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //  Vertical offset == 0 indicates appBar is fully expanded.
                float alpha = (float) Math.abs(verticalOffset) / (lp.height - toolbar.getHeight());
                //  Log.d(TAG, "onOffsetChanged: "+alpha);
                if ((Math.abs(verticalOffset)) > 150) {
                    toolbar.setBackgroundColor(Utils.getColorWithAlpha(ContextCompat.getColor(AktivasiAkunActivity.this, R.color.colorPrimary_ppob), alpha));
                    if (alpha >= 1.0) {
                        toolbar.setBackground(ContextCompat.getDrawable(AktivasiAkunActivity.this, R.drawable.toolbar));
                    }
                    appBarExpanded = false;
                    invalidateOptionsMenu();
                } else {
                    toolbar.setBackgroundColor(0);
                    appBarExpanded = true;
                    invalidateOptionsMenu();
                }
            }
        });
        fabLiveChat = findViewById(R.id.fab_live_chat);
        fabLiveChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    new_popup_alertDemo(AktivasiAkunActivity.this, "Info", "Anda belum bisa menikmati layanan ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                } else {
                    DialogUtils.new_popup_bantuan(AktivasiAkunActivity.this);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kosong, menu);
        collapsedMenu = menu;
        return true;
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
        if (item.getTitle() == "Live Chat") {
            //Toast.makeText(this, "clicked add", Toast.LENGTH_SHORT).show();
            fabLiveChat.performClick();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        setResult(RESULT_OK, intent);

        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (collapsedMenu != null
                && (!appBarExpanded || collapsedMenu.size() != 1)) {
            //collapsed
            collapsedMenu.add("Live Chat")
                    .setIcon(R.drawable.ic_live_chat)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        } else {
            //expanded

        }
        return super.onPrepareOptionsMenu(collapsedMenu);
    }
}

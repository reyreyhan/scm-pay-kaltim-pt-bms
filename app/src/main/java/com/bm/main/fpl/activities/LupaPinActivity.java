package com.bm.main.fpl.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
//import android.support.design.widget.AppBarLayout;
//import android.support.design.widget.CollapsingToolbarLayout;
//import android.support.design.widget.FloatingActionButton;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.main.fpl.utils.Utils;
import com.bm.main.pos.R;
import com.bm.main.fpl.templates.showcaseview.GuideView;
import com.bm.main.fpl.utils.FormatString;
import com.bm.main.fpl.utils.MyClipboardManager;
import com.bm.main.fpl.utils.PreferenceClass;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LupaPinActivity extends BaseActivity {
    private static final String TAG = LupaPinActivity.class.getName();
    LinearLayout linHunting,linCallOnly,linSmsIsat,linSmsTsel,linSmsXl,linEmailSupport,linEmailMarketing;
    TextView textViewEmailSupport,textViewEmailMarketing;
FloatingActionButton fabLiveChat;
ImageView imageViewEmailSupport,imageViewEmailMarketing;
    private int flag;
    AppBarLayout appBarLayout;
    private Menu collapsedMenu;
    private boolean appBarExpanded = true;
    private ImageView imageViewCustomerService;
    private int heightDp;
    CollapsingToolbarLayout.LayoutParams lp;
   NestedScrollView nestedScrollViewLupaPin;
   TextView textViewPrivacyPolicy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_pin);
      //  overridePendingTransition(0,0);
        Intent intent=getIntent();
        if(intent!=null){
            flag=intent.getIntExtra("flag",0);
        }
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pusat Bantuan");
        init(1);

        textViewPrivacyPolicy=findViewById(R.id.textViewPrivacyPolicy);
        textViewPrivacyPolicy.setText(FormatString.htmlString(getString(R.string.syarat_ketentuan_dan_kebijakan_privacy_fastpay)));
        nestedScrollViewLupaPin=findViewById(R.id.nestedScrollViewLupaPin);
        imageViewEmailSupport=findViewById(R.id.imageViewEmailSupport);
        imageViewEmailMarketing=findViewById(R.id.imageViewEmailMarketing);
        textViewEmailSupport=findViewById(R.id.textViewEmailSupport);
        textViewEmailMarketing=findViewById(R.id.textViewEmailMarketing);

        fabLiveChat=findViewById(R.id.fab_live_chat);
        if(flag==0){
            fabLiveChat.setVisibility(View.GONE);
        }else{
            fabLiveChat.setVisibility(View.VISIBLE);

        }
        fabLiveChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String url = "https://support.fastpay.co.id:4430/phplive/phplive.php?d=0&onpage=livechatimagelink&title=Live+Chat+Image+Link&namachat="+ PreferenceClass.getLoggedUser().getId();
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(url));
//                startActivity(i);
//                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
//                    new_popup_alertDemo(LupaPinActivity.this, "Info", "Anda belum bisa menikmati layanan ini.\n" +
//                            "Daftar & Aktifasi sekarang juga ID Anda");
//                } else {
                    liveChat();
//                }
            }
        });
        imageViewCustomerService=findViewById(R.id.imageViewCustomerService);
        heightDp = (getResources().getDisplayMetrics().heightPixels * 2) / 7;
        Log.d(TAG, "onCreateView: " + heightDp);
        lp = (CollapsingToolbarLayout.LayoutParams) imageViewCustomerService.getLayoutParams();
//        CollapsingToolbarLayout.LayoutParams lp = (CollapsingToolbarLayout.LayoutParams) lin_header.getLayoutParams();

        lp.height = (int) heightDp;
//        lin_header.setMinimumHeight(lp.height);
        imageViewCustomerService.setMinimumHeight(lp.height);
        appBarLayout=findViewById(R.id.appBarLayout);
       // appBarLayout.setMinimumHeight(lp.height);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //  Vertical offset == 0 indicates appBar is fully expanded.
            //    Log.d(TAG, "onOffsetChanged: "+verticalOffset+" "+(Math.abs(verticalOffset)+toolbar.getHeight())+" "+lp.height);
              //  Log.d(TAG, "onOffsetChanged: "+appBarLayout.getMinimumHeight());
                float alpha = (float) Math.abs(verticalOffset) / (lp.height-toolbar.getHeight());
              //  Log.d(TAG, "onOffsetChanged: "+alpha);
                if ((Math.abs(verticalOffset)) > 150) {
                    toolbar.setBackgroundColor(Utils.getColorWithAlpha(ContextCompat.getColor(LupaPinActivity.this, R.color.colorPrimary_ppob), alpha));
                    if(alpha>=1.0){
                        toolbar.setBackground(ContextCompat.getDrawable(LupaPinActivity.this,R.drawable.toolbar));
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

        linHunting=findViewById(R.id.linHunting);
        linHunting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = "+62318535799";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });
        linCallOnly=findViewById(R.id.linCallOnly);
        linCallOnly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = "+6281385785799";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);
            }
        });
        linEmailSupport=findViewById(R.id.linEmailSupport);
linEmailSupport.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        String paramAnonymousString=textViewEmailSupport.getText().toString();

        if(new MyClipboardManager().copyToClipboard(LupaPinActivity.this,paramAnonymousString)) {
            showToast("Email Support Telah Disalin");
        }
//        sendGmail("support@fastpay.co.id","marketing@fastpay.co.id","");
//
//
//
// Intent intent = new Intent(Intent.ACTION_SENDTO);
//        intent.setType("text/plain");
//
//
//        startActivity(Intent.createChooser(intent, "Send Email"));
    }
});

        linEmailMarketing=findViewById(R.id.linEmailMarketing);
        linEmailMarketing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String paramAnonymousString=textViewEmailMarketing.getText().toString();

                if(new MyClipboardManager().copyToClipboard(LupaPinActivity.this,paramAnonymousString)) {
                    showToast("Email Marketing Telah Disalin");
                }
//        sendGmail("support@fastpay.co.id","marketing@fastpay.co.id","");
//
//
//
// Intent intent = new Intent(Intent.ACTION_SENDTO);
//        intent.setType("text/plain");
//
//
//        startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });


        linSmsIsat=findViewById(R.id.linSmsIsat);
        linSmsIsat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "085730320058";  // The number on which you want to send SMS
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
            }
        });

        linSmsTsel=findViewById(R.id.linSmsTsel);
        linSmsTsel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "081228899888";  // The number on which you want to send SMS
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
            }
        });

        linSmsXl=findViewById(R.id.linSmsXl);
        linSmsXl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "087838395999";  // The number on which you want to send SMS
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
            }
        });

        //TextView textViewPrivacyPolicy=findViewById(R.id.textViewPrivacyPolicy);
        textViewPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LupaPinActivity.this,PrivacyPolicyActivity.class);
                startActivity(intent);
            }
        });

if(PreferenceClass.getBoolean(TAG+String.valueOf(flag),false)!=true && flag==0) {

       showCaseFirst(this, "Telepon Support Hunting", "Klik untuk langsung membuka jendela Telepon", linHunting);

    mGbuilder.setGuideListener(new GuideView.GuideListener() {
        @Override
        public void onDismiss(@NonNull View view) {



                switch (view.getId()) {

                    case R.id.linHunting:

                        mGbuilder
                                .setTitle("Telepon Support Call Only")
                                .setContentText("Klik untuk langsung membuka jendela Telepon").setGravity(GuideView.Gravity.center)
                                .setDismissType(GuideView.DismissType.anywhere)
                                .setTargetView(linCallOnly)
                                .build();
                        // showCaseNext(LoginActivity.this,"Input Password Outlet","bla bla",materialEditTextPassword);

                        break;

                    case R.id.linCallOnly:
                        mGbuilder
                                .setTitle("Email Team Support")
                                .setContentText("Klik untuk menyalin Email Support").setGravity(GuideView.Gravity.center)
                                .setDismissType(GuideView.DismissType.anywhere)
                                .setTargetView(imageViewEmailSupport)
                                .build();
                        break;

                    case R.id.imageViewEmailSupport:
                        mGbuilder
                                .setTitle("Email Team Marketing")
                                .setContentText("Klik untuk menyalin Email Marketing").setGravity(GuideView.Gravity.center)
                                .setDismissType(GuideView.DismissType.anywhere)
                                .setTargetView(imageViewEmailMarketing)
                                .build();
                        break;
                    case R.id.imageViewEmailMarketing:
                        PreferenceClass.putBoolean(TAG+String.valueOf(flag), true);
                        return;
                }

            mGuideView = mGbuilder.build();
            mGuideView.show();

        }
    });
    mGuideView = mGbuilder.build();
    mGuideView.show();
}else if(PreferenceClass.getBoolean(TAG+String.valueOf(flag),false)!=true) {

        showCaseFirst(this, "Live Chat", "Klik untuk langsung membuka jendela Live Chat", fabLiveChat);

    mGbuilder.setGuideListener(new GuideView.GuideListener() {
        @Override
        public void onDismiss(@NonNull View view) {



                switch (view.getId()) {
                    case R.id.fab_live_chat:
                        mGbuilder
                                .setTitle("Telepon Support Hunting")
                                .setContentText("Klik untuk langsung membuka jendela Telepon").setGravity(GuideView.Gravity.center)
                                .setDismissType(GuideView.DismissType.anywhere)
                                .setTargetView(linHunting)
                                .build();
                        break;
                    case R.id.linHunting:

                        mGbuilder
                                .setTitle("Telepon Support Call Only")
                                .setContentText("Klik untuk langsung membuka jendela Telepon").setGravity(GuideView.Gravity.center)
                                .setDismissType(GuideView.DismissType.anywhere)
                                .setTargetView(linCallOnly)
                                .build();
                        // showCaseNext(LoginActivity.this,"Input Password Outlet","bla bla",materialEditTextPassword);

                        break;

                    case R.id.linCallOnly:
                        mGbuilder
                                .setTitle("Email Team Support")
                                .setContentText("Klik untuk menyalin Email Support").setGravity(GuideView.Gravity.center)
                                .setDismissType(GuideView.DismissType.anywhere)
                                .setTargetView(imageViewEmailSupport)
                                .build();
                        break;
                    case R.id.imageViewEmailSupport:
                        mGbuilder
                                .setTitle("Email Team Marketing")
                                .setContentText("Klik untuk menyalin Email Marketing").setGravity(GuideView.Gravity.center)
                                .setDismissType(GuideView.DismissType.anywhere)
                                .setTargetView(imageViewEmailMarketing)
                                .build();
                        break;

                    case R.id.imageViewEmailMarketing:
                        PreferenceClass.putBoolean(TAG+String.valueOf(flag), true);
                        return;
                }

            mGuideView = mGbuilder.build();
            mGuideView.show();

        }
    });
    mGuideView = mGbuilder.build();
    mGuideView.show();
}

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
        if(id == android.R.id.home){
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
        finish();
     //   overridePendingTransition(0,0);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (collapsedMenu != null
                && (!appBarExpanded || collapsedMenu.size() != 0)) {
            //collapsed
            collapsedMenu.add("Live Chat")
                    .setIcon(R.drawable.ic_live_chat)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        }
        else {
            //toolbar.setBackground(null);
            //expanded
           // collapsedMenu.removeItem(R.drawable.ic_live_chat);

        }
        return super.onPrepareOptionsMenu(collapsedMenu);
    }
}

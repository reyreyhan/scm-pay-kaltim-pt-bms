package com.bm.main.fpl.activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.main.scm.R;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.scm.SplashScreenActivity;

//import com.bm.main.fpl.utils.ExceptionHandler;

public class AnotherActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView error;
    AppCompatButton button_laporkan;
//    private PreferenceClass PreferenceClass;
    @NonNull
    private String TAG=AnotherActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_another);
//        PreferenceClass=new PreferenceClass(this);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(this, R.style.TextAppearance_Widget_ActionBar_Title);
        toolbar.setTitleTextColor(Color.WHITE);
        //      toolbar.setSubtitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_cancel);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Informasi");
        error = findViewById(R.id.error);
        error.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        error.setLongClickable(false);
        error.setHapticFeedbackEnabled(false);
       // error.setText(getIntent().getStringExtra("error")+" "+PreferenceClass.getJSONObject("pulsa_list"));
        error.setText(getIntent().getStringExtra("error"));
        button_laporkan = findViewById(R.id.button_laporkan);
        button_laporkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(button_laporkan.getText().equals("Tutup")){
                    onBackPressed();
                }else {

                 //   sendGmail("sbfapp@bm.co.id", "", error.getText().toString());
                    sendGmail("support@fastpay.co.id", "sbfapp@bm.co.id", "Yth, Team Support Fastpay\n"+PreferenceClass.getId()+" Melaporkan temuan sebagai berikut \n"+error.getText().toString());
//


//

                }
              //  sendGmail("support@fastpay.co.id", "sbfapp@bm.co.id", error.getText().toString());
//                Crashlytics.getInstance().crash(); // Force a crash


               // onBackPressed();

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                    Intent intent = new Intent(AnotherActivity.this, SplashScreenActivity.class);
                    startActivity(intent);
                    ActivityCompat.finishAffinity(AnotherActivity.this);

        }
        return true;
    }

    @Override
    public void onBackPressed()
    {

            Intent intent = new Intent(AnotherActivity.this, SplashScreenActivity.class);
            startActivity(intent);
            ActivityCompat.finishAffinity(AnotherActivity.this);

    }

    protected void sendGmail(String to, String cc, String textPesan) {
        Log.i("Send email", "");


        try {
//            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            String[] TO = {to};
            String[] CC = {cc};
            Intent emailIntent = new Intent(Intent.ACTION_SEND);

            emailIntent.setDataAndType(Uri.parse("mailto:"),"text/plain");
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
            emailIntent.putExtra(Intent.EXTRA_CC, CC);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, PreferenceClass.getId() + " melaporkan temuan");
            emailIntent.putExtra(Intent.EXTRA_TEXT, textPesan);
            emailIntent.setPackage("com.google.android.gm");
            startActivity(emailIntent);
            //   finish();
            Log.i(TAG, "Finished sending email...");
            button_laporkan.setText("Tutup");
        } catch (android.content.ActivityNotFoundException ex) {
            Log.d(TAG, "sendGmail: "+ex.toString());
            Toast.makeText(AnotherActivity.this, "Aplikasi Gmail belum Terinstall.", Toast.LENGTH_SHORT).show();
        }

    }

}

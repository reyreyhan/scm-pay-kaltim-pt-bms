package com.bm.main.fpl.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bm.main.scm.R;
import com.bm.main.fpl.templates.TextViewPlus;

public class TiketDepositActivity extends BaseActivity {
    ImageView imageViewCopyNoRek, imageViewNominal;
    TextViewPlus textViewPlusNamaBank,textViewPlusNoRek, textViewPlusNonimal,textViewPlusKeterangan;
     String type_deposit,nama_bank,norek_bank,nominal,keterangan;
TextViewPlus labelViewPlusNoRek,labelViewPlusNominal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiket_deposit);
        Intent intent=getIntent();
        if(intent!=null)
            type_deposit =intent.getStringExtra("type_deposit");
            nama_bank=intent.getStringExtra("nama_bank");
        norek_bank=intent.getStringExtra("norek_bank");
        nominal=intent.getStringExtra("nominal");
        keterangan=intent.getStringExtra("keterangan");

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Tiket Deposit");
        init(1);

        labelViewPlusNoRek=findViewById(R.id.labelViewPlusNoRek);
        labelViewPlusNominal=findViewById(R.id.labelViewPlusNominal);
        textViewPlusNamaBank = findViewById(R.id.textViewPlusNamaBank);
        textViewPlusNamaBank.setText(nama_bank);
        textViewPlusNoRek = findViewById(R.id.textViewPlusNoRek);
        imageViewCopyNoRek = findViewById(R.id.imageViewCopyNoRek);
        textViewPlusNoRek.setText(norek_bank);
        if(norek_bank.isEmpty()){
            labelViewPlusNoRek.setVisibility(View.GONE);
            textViewPlusNoRek.setVisibility(View.GONE);
            imageViewCopyNoRek.setVisibility(View.GONE);
        }

        textViewPlusNonimal = findViewById(R.id.textViewPlusNonimal);
        imageViewNominal = findViewById(R.id.imageViewNominal);
        textViewPlusNonimal.setText(nominal);
        if(nominal.isEmpty()){
            labelViewPlusNominal.setVisibility(View.GONE);
            textViewPlusNonimal.setVisibility(View.GONE);
            imageViewNominal.setVisibility(View.GONE);
        }
        textViewPlusKeterangan=findViewById(R.id.textViewPlusKeterangan);
        textViewPlusKeterangan.setText(keterangan);



        imageViewCopyNoRek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", textViewPlusNoRek.getText().toString().replaceAll("-",""));
                clipboard.setPrimaryClip(clip);
                showToast("No Rek Telah Disalin");
            }
        });

        imageViewNominal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", textViewPlusNonimal.getText().toString().replace(".","").replace("Rp ",""));
                clipboard.setPrimaryClip(clip);
                showToast("Nominal Telah Disalin");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rumah, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_right_drawer) {

            openTopDialog(false);
        } else if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {


        finish();
        //overridePendingTransition(R.anim.slide_in, R.anim.slide_out);


    }
}

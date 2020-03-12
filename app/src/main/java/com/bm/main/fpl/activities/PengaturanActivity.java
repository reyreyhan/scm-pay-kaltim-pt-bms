package com.bm.main.fpl.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.bm.main.pos.R;
import com.bm.main.fpl.constants.PreferenceKey;
import com.bm.main.fpl.templates.MaterialRippleLayout;
import com.bm.main.fpl.templates.switchbutton.SwitchButton;
import com.bm.main.fpl.utils.PreferenceClass;

public class PengaturanActivity extends BaseActivity {
    @NonNull
    private static String TAG = PengaturanActivity.class.getSimpleName();
    SwitchButton switchHarga, switchHargaPulsa, switchKomisi, switchHargaGame, switchHargaTopup, switchPromoStruk, switchKomisiProdukTomo, switchQRCodeStruk, switchVoiceAssistant;
    LinearLayout linSettingPrinter, linSettingFeeFlight;
    AppCompatButton appButtonCek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pengaturan");
        init(1);
        linSettingPrinter = findViewById(R.id.linSettingPrinter);
        linSettingFeeFlight = findViewById(R.id.linSettingFeeFlight);
        switchKomisi = findViewById(R.id.switchKomisi);
        switchHarga = findViewById(R.id.switchHarga);
        switchHargaPulsa = findViewById(R.id.switchHargaPulsa);
        switchHargaGame = findViewById(R.id.switchHargaGame);
        switchHargaTopup = findViewById(R.id.switchHargaTopup);
        switchPromoStruk = findViewById(R.id.switchPromoStruk);
        switchKomisiProdukTomo = findViewById(R.id.switchKomisiProdukTomo);
        switchQRCodeStruk = findViewById(R.id.switchQRCodeStruk);
        switchVoiceAssistant = findViewById(R.id.switchVoiceAssistant);
        appButtonCek = findViewById(R.id.appButtonCek);
        appButtonCek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PengaturanActivity.this, CekSaldoEmoneyActivity.class);
                startActivity(intent);
            }
        });

        MaterialRippleLayout.on(linSettingPrinter).rippleOverlay(true)
                .rippleAlpha(0.2f)
                //.rippleColor(0xFF585858)
                .rippleColor(R.color.colorPrimary_ppob)
                .rippleHover(true)
                .create();
        linSettingPrinter.setOnClickListener(v -> {
            Intent intent = new Intent(PengaturanActivity.this, SettingPrinterActivity.class);
            startActivity(intent);
        });

        MaterialRippleLayout.on(linSettingFeeFlight).rippleOverlay(true)
                .rippleAlpha(0.2f)
                //.rippleColor(0xFF585858)
                .rippleColor(R.color.colorPrimary_ppob)
                .rippleHover(true)
                .create();
        linSettingFeeFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PengaturanActivity.this, FeePesawatActivity.class);
                startActivity(intent);
                //overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });

        switchKomisi.setChecked(PreferenceClass.getBoolean("switchKomisi", true));
        //switchHargaPulsa.performClick();
        switchKomisi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferenceClass.putBoolean("switchKomisi", isChecked);
                //  Log.d(TAG, "onCheckedChanged: "+isChecked);
            }
        });
        switchKomisiProdukTomo.setChecked(PreferenceClass.getBoolean("switchKomisiProdukTomo", true));
        //switchHargaPulsa.performClick();
        switchKomisiProdukTomo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferenceClass.putBoolean("switchKomisiProdukTomo", isChecked);
                //  Log.d(TAG, "onCheckedChanged: "+isChecked);
            }
        });
        switchQRCodeStruk.setChecked(PreferenceClass.getBoolean("switchQRCodeStruk", true));
        //switchHargaPulsa.performClick();
        switchQRCodeStruk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferenceClass.putBoolean("switchQRCodeStruk", isChecked);
                //  Log.d(TAG, "onCheckedChanged: "+isChecked);
            }
        });
        switchVoiceAssistant.setChecked(PreferenceClass.getBoolean(PreferenceKey.isVoiceAssistant, true));
        //switchHargaPulsa.performClick();
        switchVoiceAssistant.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferenceClass.putBoolean(PreferenceKey.isVoiceAssistant, isChecked);
                //  Log.d(TAG, "onCheckedChanged: "+isChecked);
            }
        });

        switchHarga.setChecked(PreferenceClass.getBoolean("switchHarga", true));
        //switchHargaPulsa.performClick();
        switchHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "onCheckedChanged: " + isChecked);
                if (isChecked) {
                    PreferenceClass.putBoolean("switchHarga", true);
                } else {
                    PreferenceClass.putBoolean("switchHarga", false);
                }

            }
        });

        switchHargaPulsa.setChecked(PreferenceClass.getBoolean("switchHargaPulsa", true));
        //switchHargaPulsa.performClick();
        switchHargaPulsa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "onCheckedChanged: " + isChecked);
                if (isChecked) {
                    PreferenceClass.putBoolean("switchHargaPulsa", true);
                } else {
                    PreferenceClass.putBoolean("switchHargaPulsa", false);
                }

            }
        });

        switchHargaGame.setChecked(PreferenceClass.getString("switchHargaGame", "").equals("show"));
        //switchHargaPulsa.performClick();
        switchHargaGame.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    PreferenceClass.putString("switchHargaGame", "show");
                } else {
                    PreferenceClass.putString("switchHargaGame", "noshow");
                }
                // Log.d(TAG, "onCheckedChanged: "+isChecked);
            }
        });

        switchHargaTopup.setChecked(PreferenceClass.getString("switchHargaTopup", "").equals("show"));
        //switchHargaPulsa.performClick();
        switchHargaTopup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    PreferenceClass.putString("switchHargaTopup", "show");
                } else {
                    PreferenceClass.putString("switchHargaTopup", "noshow");
                }
                // Log.d(TAG, "onCheckedChanged: "+isChecked);
            }
        });
        switchPromoStruk.setChecked(PreferenceClass.getBoolean("switchPromoStruk", true));
        //switchHargaPulsa.performClick();
        switchPromoStruk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                PreferenceClass.putBoolean("switchPromoStruk", isChecked);
                // Log.d(TAG, "onCheckedChanged: "+isChecked);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        //  Log.d(TAG, "onBackPressed: "+bottom_toolbar.getVisibility());


        finish();
        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


    }
}

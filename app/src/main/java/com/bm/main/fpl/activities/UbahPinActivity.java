package com.bm.main.fpl.activities;

import android.app.PendingIntent;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.telephony.SubscriptionInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.main.fpl.utils.Device;
import com.bm.main.pos.R;
import com.bm.main.fpl.utils.FormatString;
import com.bm.main.fpl.utils.PreferenceClass;

public class UbahPinActivity extends BaseActivity {
    private static final String TAG = UbahPinActivity.class.getSimpleName();
    TextView textViewPetunjuk, textViewPetunjuk1, textViewPetunjuk2, textViewLinkLiveChat, textViewFormatSms, textViewNumberSms1, textViewNumberSms2, textViewNumberSms3;
    ImageView imageViewFormatSms, imageViewNumberSms1, imageViewNumberSms2, imageViewNumberSms3;

    private String phoneNo;
    private String message;
    @NonNull
    String SENT = "SMS_SENT";
    @NonNull
    String DELIVERED = "SMS_DELIVERED";
    private PendingIntent sentPI;
    private PendingIntent deliveredPI;
    private SubscriptionInfo fromNumber;
    LinearLayout linNumberSms1, linNumberSms2, linNumberSms3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_pin);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Ganti Password/PIN");
        init(0);
        linNumberSms1 = findViewById(R.id.linNumberSms1);
        linNumberSms2 = findViewById(R.id.linNumberSms2);
        linNumberSms3 = findViewById(R.id.linNumberSms3);

        textViewFormatSms = findViewById(R.id.textViewFormatSms);
        textViewNumberSms1 = findViewById(R.id.textViewNumberSms1);
        textViewNumberSms2 = findViewById(R.id.textViewNumberSms2);
        textViewNumberSms3 = findViewById(R.id.textViewNumberSms3);

        textViewPetunjuk = findViewById(R.id.textViewPetunjuk);
        textViewPetunjuk.setText(FormatString.htmlString(getString(R.string.petunjuk_ubah_pin)));
        textViewPetunjuk1 = findViewById(R.id.textViewPetunjuk1);
        textViewPetunjuk1.setText(FormatString.htmlString(getString(R.string.petunjuk_ubah_pin1)));
        textViewPetunjuk2 = findViewById(R.id.textViewPetunjuk2);
        textViewPetunjuk2.setText(FormatString.htmlString(getString(R.string.petunjuk_ubah_pin2)));
        textViewLinkLiveChat = findViewById(R.id.textViewLinkLiveChat);
        textViewLinkLiveChat.setText(FormatString.htmlString(getString(R.string.link_live_chat)));
        textViewLinkLiveChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Device.vibrate(UbahPinActivity.this);
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    new_popup_alertDemo(UbahPinActivity.this, "Info", "Anda belum bisa menikmati fiture ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                } else {
                    liveChat();
                }
            }
        });

        imageViewFormatSms = findViewById(R.id.imageViewFormatSms);
        imageViewFormatSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Device.vibrate(UbahPinActivity.this);
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    new_popup_alertDemo(UbahPinActivity.this, "Info", "Anda belum bisa menikmati fiture ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                } else {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", textViewFormatSms.getText().toString());
                    clipboard.setPrimaryClip(clip);
                    showToast("Format Sms Ubah Pin Telah Disalin");
                }
            }
        });

        imageViewNumberSms1 = findViewById(R.id.imageViewNumberSms1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
    }
}

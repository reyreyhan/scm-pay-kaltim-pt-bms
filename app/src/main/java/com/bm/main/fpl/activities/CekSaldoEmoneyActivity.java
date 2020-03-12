package com.bm.main.fpl.activities;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.internal.view.SupportMenu;
import androidx.appcompat.widget.SwitchCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.main.fpl.utils.Device;
import com.bm.main.pos.R;
import com.bm.main.fpl.utils.MyClipboardManager;
import com.bm.main.fpl.utils.PreferenceClass;
import com.cardnfc.lib.NfcCardReaderListenerSBF;
import com.cardnfc.lib.NfcCardReaderSBF;
import com.cardnfc.lib.bninfc.tapcashgo.tapcashgoa;
import com.google.android.gms.common.ErrorDialogFragment;
import com.reciter.lib.Announcer;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class CekSaldoEmoneyActivity extends BaseActivity implements NfcAdapter.ReaderCallback {
    static final String MIME_TEXT_PLAIN = "text/plain";
    public static int SHOW_CARD_INFO_TIME_OUT = 10000;
    public static int SHOW_ERROR_INFO_TIME_OUT = 3000;
    public static final String TAG = CekSaldoEmoneyActivity.class.getSimpleName();
    //    private AdView mAdView;
    FrameLayout mCardBackground;
    NfcCardReaderSBF mCardReader;
    NfcCardReaderListenerSBF mCardReaderListener;// = new cekSaldo();
    ImageView mImageViewPhotoKtp;
    boolean mMute = false, mUpdateSaldo = false;
    NfcAdapter mNfcAdapter;
    NfcBroacastReceiver mNfcBroadNfcBroacastReceiver;//=new NfcBroacastReceiver();
    IntentFilter mNfcBroadcastReceiverFilter;//=new IntentFilter(NfcAdapter.ACTION_ADAPTER_STATE_CHANGED);
    ImageView mNfcSettings;
    SwitchCompat mSwitch, mSwitchUpdateSaldo;
    TextView mTextViewBalanceValue;
    TextView mTextViewCardNumber;
    TextView mTextViewCardNumberValue;
    TextView mTextViewCardStatus;
    TextView mTextViewNfcStatus;
    ImageView mCard_number_value_copy;
    // ImageView imageViewUpdateSaldo;
    @Nullable
    Timer mTimer;

    PendingIntent localPendingIntent;
    @NonNull
    public static String f2284b = "192.168.143.36:57002";
    private static final String f2285g = (Environment.getExternalStorageDirectory() + "/TapCash/tapcash-host.txt");

    String branch;
    //    Vibrator mVibrator;
    @NonNull
    private final String[][] techList;//= { { NfcA.class.getName(), NfcB.class.getName(), NfcF.class.getName(), NfcV.class.getName(), IsoDep.class.getName(), MifareClassic.class.getName(), MifareUltralight.class.getName(), Ndef.class.getName() } };

    private void setShowTimer(int paramInt) {
        if (mTextViewCardNumber.getVisibility() == View.VISIBLE) {
            mCard_number_value_copy.setVisibility(View.VISIBLE);
        }
        this.mTimer = new Timer();
        this.mTimer.schedule(new timerNfcTask(), (long) paramInt);
//        this.mTimer = new Timer();
//        this.mTimer.schedule(new TimerTask()
//        {
//            public void run()
//            {
//                runOnUiThread(new Runnable()
//                {
//                    public void run()
//                    {
//                        if (Build.VERSION.SDK_INT >= 21) {
//                            mCardBackground.setBackground(getDrawable(R.drawable.tap));
//                        } else {
//                            mCardBackground.setBackgroundResource(R.drawable.tap);
//                        }
//                        mImageViewPhotoKtp.setVisibility(View.GONE);
//                        mTextViewCardNumber.setVisibility(View.GONE);
//                        if (mTextViewCardStatus != null) {
//                            mTextViewCardStatus.setText("Tempelkan kartu pada bagian belakang HP");
//                        }
//                        if (mTextViewCardNumberValue != null) {
//                            mTextViewCardNumberValue.setText(getString(R.string.default_card_number));
//                        }
//                        if (mTextViewBalanceValue != null) {
//                           mTextViewBalanceValue.setText(getString(R.string.default_balance));
//                        }
//                    }
//                });
//            }
//        }, (long)paramInt);
    }

    public CekSaldoEmoneyActivity() {
        String[][] strArr = new String[1][];
        strArr[0] = new String[]{NfcA.class.getName(), NfcB.class.getName(), NfcF.class.getName(), NfcV.class.getName(), IsoDep.class.getName(), MifareClassic.class.getName(), MifareUltralight.class.getName(), Ndef.class.getName()};
        this.techList = strArr;

        this.mNfcBroadNfcBroacastReceiver = new NfcBroacastReceiver();
//        this.mNfcBroadcastReceiverFilter = new IntentFilter(NfcAdapter.ACTION_ADAPTER_STATE_CHANGED);
        this.mNfcBroadcastReceiverFilter = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);

        Log.d(TAG, "CekSaldoEmoneyActivity: ");
    }

    // @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //   Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_saldo_emoney);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Cek Saldo E-money");
        init(1);
        //  mCardReader=NfcCardReaderSBF.getInstance(this,mCardReaderListener);
        registerReceiver(this.mNfcBroadNfcBroacastReceiver, this.mNfcBroadcastReceiverFilter);
        mCardBackground = findViewById(R.id.card_background);

        mTextViewCardNumber = findViewById(R.id.card_number);
        mTextViewCardStatus = findViewById(R.id.textview_card_status);
        mTextViewNfcStatus = findViewById(R.id.textview_nfc_status);
        mNfcSettings = findViewById(R.id.nfc_settings);
        if (mNfcSettings != null) {
            mNfcSettings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent("android.settings.NFC_SETTINGS"), 0);
                }
            });

            //  mNfcSettings.setOnClickListener(new C02682());
        }
        mImageViewPhotoKtp = findViewById(R.id.imageview_photo);
        mSwitch = findViewById(R.id.switchButton);
        mSwitch.setChecked(PreferenceClass.getBoolean(getString(R.string.preference_mute), false));
        if (mSwitch != null) {
            // mSwitch.setOnCheckedChangeListener(new C02693());
            this.mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                    //  mMute = z ^ 1;
                    Log.d(TAG, "onCheckedChanged: " + z);
                    // mMute = paramAnonymousBoolean;
                    if (z) {
                        mMute = true;
                        PreferenceClass.getBoolean("update", true);
                    } else {
                        PreferenceClass.getBoolean("update", false);
                        mMute = false;
                    }

                }
            });
        }
        mTextViewCardNumberValue = findViewById(R.id.card_number_value);
        mTextViewBalanceValue = findViewById(R.id.balance_value);
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            if (Build.VERSION.SDK_INT >= 21) {
                mCardBackground.setBackground(getDrawable(R.drawable.ic_no_nfc));
            } else {
                mCardBackground.setBackground(getResources().getDrawable(R.drawable.ic_no_nfc));
            }
            if (mTextViewCardStatus != null) {
                mTextViewCardStatus.setTextColor(SupportMenu.CATEGORY_MASK);
                mTextViewCardStatus.setText("HP tidak bisa digunakan untuk membaca kartu");
            }
            if (mTextViewNfcStatus != null) {
                mTextViewNfcStatus.setText("Perangkat tidak memiliki fiture NFC");
            }
            if (mSwitch != null) {
                mSwitch.setEnabled(false);
                mSwitch.setTextColor(ContextCompat.getColor(this, R.color.md_grey_400));
            }
        } else if (!mNfcAdapter.isEnabled()) {
            if (mTextViewNfcStatus != null) {
                mTextViewNfcStatus.setText("NFC TIDAK AKTIF");
            }
            if (mNfcSettings != null) {
                mNfcSettings.setImageResource(R.drawable.nfc_off);
            }
        } else {
            if (mTextViewNfcStatus != null) {
                mTextViewNfcStatus.setText("NFC AKTIF");
            }
            if (mNfcSettings != null) {
                mNfcSettings.setImageResource(R.drawable.nfc_on);
            }
        }
        mCardReader = new NfcCardReaderSBF(this, mCardReaderListener);
        // mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        mCard_number_value_copy = findViewById(R.id.card_number_value_copy);

        mCard_number_value_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Device.vibrate(CekSaldoEmoneyActivity.this);
                String paramAnonymousString = mTextViewCardNumber.getText().toString().replaceAll(" ", "");
                if (new MyClipboardManager().copyToClipboard(CekSaldoEmoneyActivity.this, paramAnonymousString)) {
                    showToast("No Kartu Telah Disalin");
                }
            }
        });

        try {
            File file = new File(f2285g);
            if (file.exists()) {
                PreferenceClass.putString("host_address", tapcashgoa.m5c(file));
            } else {
                tapcashgoa.m4b(file, f2284b);
                PreferenceClass.putString("host_address", f2284b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        mSwitchUpdateSaldo = findViewById(R.id.mSwitchUpdateSaldo);
        mCardReaderListener = new cekSaldo();

    }

    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.mNfcBroadNfcBroacastReceiver);
    }

    protected void onNewIntent(@NonNull Intent paramIntent) {
        Log.d(TAG, "onNewIntent: ");
        if (this.mNfcAdapter != null) {
            this.mCardReader.handleNfcIntent(paramIntent);
        }
    }

    protected void onPause() {
        super.onPause();
        if (this.mNfcAdapter != null) {
            this.mNfcAdapter.disableForegroundDispatch(this);
            this.mNfcAdapter.disableReaderMode(this);
        }
    }

    public static int READER_FLAGS =
            NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK;

    // NfcAdapter.FLAG_READER_NFC_A;
    @SuppressLint("WrongConstant")
    protected void onResume() {
        super.onResume();

        this.mMute = PreferenceClass.getBoolean(getString(R.string.preference_mute), false);
        Log.d(TAG, "onResume: " + mMute);
//                Boolean.valueOf(getSharedPreferences(getString(2131558463), 0).getBoolean(getString(2131558462), true));
        if (this.mSwitch != null) {
            this.mSwitch.setChecked(this.mMute);
        }
        //    @SuppressLint("WrongConstant")
        localPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(ErrorDialogFragment.STYLE_NORMAL), 0);
        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction(NfcAdapter.ACTION_TAG_DISCOVERED);
        localIntentFilter.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        localIntentFilter.addAction(NfcAdapter.ACTION_TECH_DISCOVERED);
        Log.d(TAG, "onResume: " + this.mNfcAdapter);
        if (this.mNfcAdapter != null) {
            mCardReader = new NfcCardReaderSBF(this, mCardReaderListener);
            this.mNfcAdapter.enableForegroundDispatch(this, localPendingIntent, new IntentFilter[]{localIntentFilter}, this.techList);
            this.mNfcAdapter.enableReaderMode(this, this, READER_FLAGS, null);
            Log.d(TAG, "onResume: ");
        }
    }

    public void onTagDiscovered(@NonNull Tag paramTag) {
        Log.d(TAG, "onTagDiscovered: " + paramTag.toString());
        runOnUiThread(new ExecuteInUI(paramTag, paramTag.getId()));
    }

    public void recite(String paramString) {
        Log.d(TAG, "recite: " + paramString);
        new Announcer(this, paramString).startAnnounce();
    }

    private class NfcBroacastReceiver extends BroadcastReceiver {

        public NfcBroacastReceiver() {
        }

        public void onReceive(Context context, @NonNull Intent intent) {
            //    Log.d(TAG, "onReceive: ");
            final String action = intent.getAction();
            Log.d(TAG, "onReceive: " + action);
//            intent = intent.getIntExtra("android.nfc.extra.ADAPTER_STATE",1);
//            if (action.equals(NfcAdapter.ACTION_ADAPTER_STATE_CHANGED)) {
//            if (context == 1) {
//                if (mTextViewNfcStatus != null) {
//                    mTextViewNfcStatus.setText("NFCBrizzi TIDAK AKTIF");
//                }
//                if (mNfcSettings != null) {
//                    mNfcSettings.setImageResource(R.drawable.nfc_off);
//                }
//            } else if (context == 3) {
//                if (mTextViewNfcStatus != null) {
//                   mTextViewNfcStatus.setText("NFCBrizzi AKTIF");
//                }
//                if (mNfcSettings != null) {
//                    mNfcSettings.setImageResource(R.drawable.nfc_on);
//                }
//            }
            if (action.equals(NfcAdapter.ACTION_ADAPTER_STATE_CHANGED)) {
                final int state = intent.getIntExtra(NfcAdapter.EXTRA_ADAPTER_STATE,
                        NfcAdapter.STATE_OFF);
                switch (state) {
                    case NfcAdapter.STATE_OFF:
                        break;
                    case NfcAdapter.STATE_TURNING_OFF:
                        if (mTextViewNfcStatus != null) {
                            mTextViewNfcStatus.setText("NFC TIDAK AKTIF");
                        }
                        if (mNfcSettings != null) {
                            mNfcSettings.setImageResource(R.drawable.nfc_off);
                        }
                        break;
                    case NfcAdapter.STATE_ON:
                        if (mTextViewNfcStatus != null) {
                            mTextViewNfcStatus.setText("NFC AKTIF");
                        }
                        if (mNfcSettings != null) {
                            mNfcSettings.setImageResource(R.drawable.nfc_on);
                        }
                        break;
                    case NfcAdapter.STATE_TURNING_ON:
                        break;
                }
            }
        }
    }
    Tag tag;

    private class ExecuteInUI implements Runnable {
        Tag mTag;
        byte[] bytes;

        public ExecuteInUI(Tag paramTag, byte[] bytes) {
            this.mTag = paramTag;
            tag = paramTag;
            this.bytes = bytes;
        }

        public void run() {
            if (mTimer != null) {
                mTimer.cancel();
                mTimer = null;
            }
            mCardReader.handleNfcTag(this.mTag, this.bytes);
        }
    }



    class cekSaldo implements NfcCardReaderListenerSBF {
        @NonNull
        Locale localeID = new Locale("in", "ID");

        cekSaldo() {
            Log.d(TAG, "cekSaldo: ");
        }

        @Override
        public void onBrizziCardFound(@NonNull String paramAnonymousString, long paramAnonymousLong) {
            DecimalFormat localDecimalFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance();
            DecimalFormatSymbols localDecimalFormatSymbols = new DecimalFormatSymbols();
            localDecimalFormatSymbols.setCurrencySymbol(getString(R.string.currency_symbol));
            localDecimalFormatSymbols.setMonetaryDecimalSeparator(',');
            localDecimalFormatSymbols.setGroupingSeparator('.');
            localDecimalFormat.setDecimalFormatSymbols(localDecimalFormatSymbols);
            if (Build.VERSION.SDK_INT >= 21) {
                mCardBackground.setBackground(getDrawable(R.drawable.brizzi));
            } else {
                mCardBackground.setBackground(getResources().getDrawable(R.drawable.brizzi));
            }
            StringBuilder paramAnonymousStringBuilder = new StringBuilder(paramAnonymousString);
            int i = paramAnonymousString.length() - 4;
            while (i > 0) {
                paramAnonymousStringBuilder.insert(i, ' ');
                i -= 4;
            }
            mImageViewPhotoKtp.setVisibility(View.GONE);
            paramAnonymousString = paramAnonymousStringBuilder.toString();
            if (mTextViewCardNumber != null) {
                mTextViewCardNumber.setText(paramAnonymousString);

                mTextViewCardNumber.setTextColor(ContextCompat.getColor(CekSaldoEmoneyActivity.this, R.color.md_white_1000));

                mTextViewCardNumber.setVisibility(View.VISIBLE);
            }
            if (mTextViewCardStatus != null) {
                mTextViewCardStatus.setText("BRI BRIZZI");
            }
            if (mTextViewCardNumberValue != null) {
                mTextViewCardNumberValue.setText(paramAnonymousString);
                PreferenceClass.putString("emoney_card_number", paramAnonymousString.replaceAll(" ", ""));
            }
            if (mTextViewBalanceValue != null) {
                mTextViewBalanceValue.setText(localDecimalFormat.format(paramAnonymousLong));
            }
            if (mMute) {
                recite(Long.toString(paramAnonymousLong));
            }
            setShowTimer(SHOW_CARD_INFO_TIME_OUT);
        }

        @Override
        public void onEKTPCardFound(Bitmap paramBitmap) {
            if (Build.VERSION.SDK_INT >= 21) {
                mCardBackground.setBackground(getDrawable(R.drawable.ektp));
            } else {
                mCardBackground.setBackground(getResources().getDrawable(R.drawable.ektp));
            }
            mImageViewPhotoKtp.setImageBitmap(paramBitmap);
            mImageViewPhotoKtp.setVisibility(View.VISIBLE);
            if (mTextViewCardStatus != null) {
                mTextViewCardStatus.setText("E-KTP");
            }
            setShowTimer(SHOW_CARD_INFO_TIME_OUT);


        }

        @Override
        public void onEMoneyCardFound(@NonNull String paramAnonymousString, long paramAnonymousLong) {
            Log.d(TAG, "onEMoneyCardFound: " + paramAnonymousString + " " + paramAnonymousLong);
            DecimalFormat localDecimalFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance();
            DecimalFormatSymbols localDecimalFormatSymbols = new DecimalFormatSymbols();
            localDecimalFormatSymbols.setCurrencySymbol(getString(R.string.currency_symbol));
            localDecimalFormatSymbols.setMonetaryDecimalSeparator(',');
            localDecimalFormatSymbols.setGroupingSeparator('.');
            localDecimalFormat.setDecimalFormatSymbols(localDecimalFormatSymbols);
            if (Build.VERSION.SDK_INT >= 21) {
                mCardBackground.setBackground(getDrawable(R.drawable.emoney));
            } else {
                mCardBackground.setBackground(getResources().getDrawable(R.drawable.emoney));
            }
            mImageViewPhotoKtp.setVisibility(View.GONE);
            StringBuilder paramAnonymousStringBuilder = new StringBuilder(paramAnonymousString);
            int i = paramAnonymousString.length() - 4;
            while (i > 0) {
                paramAnonymousStringBuilder.insert(i, ' ');
                i -= 4;
            }
            paramAnonymousString = paramAnonymousStringBuilder.toString();
            if (mTextViewCardNumber != null) {
                mTextViewCardNumber.setText(paramAnonymousString);
                mTextViewCardNumber.setTextColor(ContextCompat.getColor(CekSaldoEmoneyActivity.this, R.color.md_white_1000));
                mTextViewCardNumber.setVisibility(View.VISIBLE);
            }
            if (mTextViewCardStatus != null) {
                mTextViewCardStatus.setText("Mandiri e-money");
            }
            if (mTextViewCardNumberValue != null) {
                mTextViewCardNumberValue.setText(paramAnonymousString);
                PreferenceClass.putString("emoney_card_number", paramAnonymousString.replaceAll(" ", ""));
            }
            if (mTextViewBalanceValue != null) {
                mTextViewBalanceValue.setText(localDecimalFormat.format(paramAnonymousLong));
            }
            if (mMute) {
                recite(Long.toString(paramAnonymousLong));
            }
            setShowTimer(SHOW_CARD_INFO_TIME_OUT);

        }

        @Override
        public void onError(String paramAnonymousString) {
            Log.d(TAG, "onError: " + paramAnonymousString);
            if (Build.VERSION.SDK_INT >= 21) {
                mCardBackground.setBackground(getDrawable(R.drawable.tap_fastpay));
            } else {
                mCardBackground.setBackground(ContextCompat.getDrawable(CekSaldoEmoneyActivity.this, R.drawable.tap_fastpay));
            }
            mImageViewPhotoKtp.setVisibility(View.GONE);
            mTextViewCardNumber.setVisibility(View.GONE);
            if (mTextViewCardStatus != null) {
                mTextViewCardStatus.setText(paramAnonymousString);
            }
            if (mTextViewCardNumberValue != null) {
                mTextViewCardNumberValue.setText(getString(R.string.default_card_number));
            }
            if (mTextViewBalanceValue != null) {
                mTextViewBalanceValue.setText(getString(R.string.default_balance));
            }
            setShowTimer(SHOW_ERROR_INFO_TIME_OUT);

        }

        @Override
        public void onFlazzCardFound(@NonNull String paramAnonymousString, long paramAnonymousLong) {
            DecimalFormat localDecimalFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance();
            DecimalFormatSymbols localDecimalFormatSymbols = new DecimalFormatSymbols();
            localDecimalFormatSymbols.setCurrencySymbol(getString(R.string.currency_symbol));
            localDecimalFormatSymbols.setMonetaryDecimalSeparator(',');
            localDecimalFormatSymbols.setGroupingSeparator('.');
            localDecimalFormat.setDecimalFormatSymbols(localDecimalFormatSymbols);
            if (Build.VERSION.SDK_INT >= 21) {
                mCardBackground.setBackground(getDrawable(R.drawable.flazzcard));
            } else {
                mCardBackground.setBackground(getResources().getDrawable(R.drawable.flazzcard));
            }
            mImageViewPhotoKtp.setVisibility(View.GONE);
            StringBuilder paramAnonymousStringBuilder = new StringBuilder(paramAnonymousString);
            int i = paramAnonymousString.length() - 4;
            while (i > 0) {
                paramAnonymousStringBuilder.insert(i, ' ');
                i -= 4;
            }
            paramAnonymousString = paramAnonymousStringBuilder.toString();
            if (mTextViewCardNumber != null) {
                mTextViewCardNumber.setText(paramAnonymousString);
                mTextViewCardNumber.setVisibility(View.VISIBLE);
                mTextViewCardNumber.setTextColor(ContextCompat.getColor(CekSaldoEmoneyActivity.this, R.color.md_grey_900));
            }
            if (mTextViewCardStatus != null) {
                mTextViewCardStatus.setText("BCA Flazz");
            }
            if (mTextViewCardNumberValue != null) {
                mTextViewCardNumberValue.setText(paramAnonymousString);
                PreferenceClass.putString("emoney_card_number", paramAnonymousString.replaceAll(" ", ""));
            }
            if (mTextViewBalanceValue != null) {
                mTextViewBalanceValue.setText(localDecimalFormat.format(paramAnonymousLong));
            }
            if (mMute) {
                recite(Long.toString(paramAnonymousLong));
            }
            setShowTimer(SHOW_CARD_INFO_TIME_OUT);

        }

        @Override
        public void onTapCashCardFound(@NonNull String paramAnonymousString, long paramAnonymousLong) {
            DecimalFormat localDecimalFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance();
            DecimalFormatSymbols localDecimalFormatSymbols = new DecimalFormatSymbols();
            localDecimalFormatSymbols.setCurrencySymbol(getString(R.string.currency_symbol));
            localDecimalFormatSymbols.setMonetaryDecimalSeparator(',');
            localDecimalFormatSymbols.setGroupingSeparator('.');
            localDecimalFormat.setDecimalFormatSymbols(localDecimalFormatSymbols);
            if (Build.VERSION.SDK_INT >= 21) {
                mCardBackground.setBackground(getDrawable(R.drawable.tapcash));
            } else {
                mCardBackground.setBackground(getResources().getDrawable(R.drawable.tapcash));
            }
            mImageViewPhotoKtp.setVisibility(View.GONE);
            StringBuilder paramAnonymousStringBuilder = new StringBuilder(paramAnonymousString);
            int i = paramAnonymousString.length() - 4;
            while (i > 0) {
                paramAnonymousStringBuilder.insert(i, ' ');
                i -= 4;
            }
            paramAnonymousString = paramAnonymousStringBuilder.toString();
            if (mTextViewCardNumber != null) {
                mTextViewCardNumber.setText(paramAnonymousString);
                mTextViewCardNumber.setVisibility(View.VISIBLE);
                mTextViewCardNumber.setTextColor(ContextCompat.getColor(CekSaldoEmoneyActivity.this, R.color.md_grey_900));
            }
            if (mTextViewCardStatus != null) {
                mTextViewCardStatus.setText("BNI TapCash");
                branch = "BNI";
            }
            if (mTextViewCardNumberValue != null) {
                mTextViewCardNumberValue.setText(paramAnonymousString);
                PreferenceClass.putString("emoney_card_number", paramAnonymousString.replaceAll(" ", ""));
            }
            if (mTextViewBalanceValue != null) {
                mTextViewBalanceValue.setText(localDecimalFormat.format(paramAnonymousLong));
            }

            if (mMute) {
                recite(Long.toString(paramAnonymousLong));
            }
            setShowTimer(SHOW_CARD_INFO_TIME_OUT);
        }
    }

    class timerNfcTask extends TimerTask {

        class timerNfcTaskRun implements Runnable {
            public void run() {
                if (Build.VERSION.SDK_INT >= 21) {
                    mCardBackground.setBackground(getDrawable(R.drawable.tap_fastpay));
                } else {
                    mCardBackground.setBackground(ContextCompat.getDrawable(CekSaldoEmoneyActivity.this, R.drawable.tap_fastpay));
                }
                mImageViewPhotoKtp.setVisibility(View.GONE);
                mTextViewCardNumber.setVisibility(View.GONE);
                mCard_number_value_copy.setVisibility(View.GONE);
                if (mTextViewCardStatus != null) {
                    mTextViewCardStatus.setText("Tempelkan kartu pada bagian belakang HP");
                }
                if (mTextViewCardNumberValue != null) {
                    mTextViewCardNumberValue.setText(getString(R.string.default_card_number));
                }
                if (mTextViewBalanceValue != null) {
                    mTextViewBalanceValue.setText(getString(R.string.default_balance));
                }
            }
        }

        public void run() {
            runOnUiThread(new timerNfcTaskRun());
        }
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
        finish();
    }
}
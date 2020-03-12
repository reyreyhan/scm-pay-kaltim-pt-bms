package com.bm.main.fpl.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.widget.NestedScrollView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.ProdukCode;
import com.bm.main.fpl.constants.ProdukGroup;
import com.bm.main.fpl.constants.RequestCode;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.JsonObjectResponseCallback;
import com.bm.main.fpl.models.InqModel;
import com.bm.main.fpl.models.PayModel;
import com.bm.main.fpl.templates.TextViewPlus;
import com.bm.main.fpl.templates.htmlspanner.HtmlSpanner;
import com.bm.main.fpl.templates.htmltextview.HtmlTextView;
import com.bm.main.fpl.templates.toast.MyDynamicToast;
import com.bm.main.fpl.utils.Device;
import com.bm.main.fpl.utils.DialogUtils;
import com.bm.main.fpl.utils.FormatString;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;
import com.bm.main.materialedittext.MaterialEditText;
import com.bm.main.pos.R;
import com.google.zxing.WriterException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Method;
import java.util.UUID;

public class InqueryResultActivity extends BaseActivity implements JsonObjectResponseCallback {

    private static final String TAG = InqueryResultActivity.class.getSimpleName();
    TextViewPlus textViewPlusTitleProduk, textViewPlusKomisi;
    LinearLayout linCetak;
    InqModel inqModel;
    PayModel payModel;
    AppCompatButton appCompatButtonBayar;
    String id_pel = "", no_meter = "", periode = "", kode_area = "", nominal = "", nominal_admin = "", no_hp = "";
    private AppCompatButton appCompatButtonCetak;

    TextViewPlus textViewPlusSukses;
    AppCompatButton appCompatButtonShare;
    ImageView imageViewWa, imageViewGmail, imageViewTelegram, imageViewBbm, imageViewPdf, imageViewImage;
    private Context mContext;
    String produkCode = "", produkGroup = "";

    private LinearLayout.LayoutParams lp;
    CardView cardViewStruk;
    String produkName = "";
    FrameLayout frame_ribbon_demo;
    private String OTC;

    String bank_code = "", bank_name = "", bank_nominal = "";
    HtmlTextView textViewShowStruk;
    HtmlSpanner htmlSpanner;
    LinearLayout linStruk;

    String struk_show;
    Bitmap bitmapQRCodeShare;

    ImageView imageViewLogo, imageViewQR;
    public String strukTercetak;

    // NestedScrollView nestedScrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_inquery_result);
        mContext = this;

        Intent intent = getIntent();

        kode_area = intent.getStringExtra("kode_area");
        id_pel = intent.getStringExtra("id_pel");
        no_hp = intent.getStringExtra("no_hp");
        produkCode = intent.getStringExtra("produkCode");
        produkGroup = intent.getStringExtra("produkGroup");
        produkName = intent.getStringExtra("produkName");
        periode = intent.getStringExtra("periode");
        nominal = intent.getStringExtra("nominal");
        nominal_admin = intent.getStringExtra("nominal_admin");

        bank_code = intent.getStringExtra("bank_code");
        bank_name = intent.getStringExtra("bank_name");
        bank_nominal = intent.getStringExtra("bank_nominal");

        no_meter = intent.getStringExtra("no_met");
        inqModel = (InqModel) intent.getSerializableExtra("inq_result");

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(intent.getStringExtra("title"));
        init(0);
        bottom_toolbar = findViewById(R.id.bottom_toolbar);
        frame_ribbon_demo = findViewById(R.id.frame_ribbon_demo);

        linCetak = findViewById(R.id.linCetak);
        imageViewGmail = findViewById(R.id.imageViewGmail);
        imageViewTelegram = findViewById(R.id.imageViewTelegram);
        imageViewBbm = findViewById(R.id.imageViewBbm);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(
                mMessageReceiver,
                new IntentFilter("BROADCAST_PRINTING")
        );
        linStruk = findViewById(R.id.linStruk);
        imageViewLogo = findViewById(R.id.imageViewLogo);
        imageViewQR = findViewById(R.id.imageViewQR);

        textViewShowStruk = findViewById(R.id.textViewShowStruk);

        appCompatButtonBayar = findViewById(R.id.appCompatButtonBayar);
        appCompatButtonBayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nominalFrom = Integer.parseInt(inqModel.getNominal_inq().replace(".", ""));
                int nominal_adminFrom = Integer.parseInt(inqModel.getNominal_admin_inq().replace(".", ""));

                if (produkCode.equals(ProdukCode.TARIKTUNAI1)) {
                    promptDialog();
                } else {
                    requestPayment();
                }
            }
        });
        appCompatButtonCetak = findViewById(R.id.appCompatButtonCetak);
        appCompatButtonCetak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    new_popup_alertDemo(InqueryResultActivity.this, "Info", "Anda belum bisa menikmati fiture ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");

                } else {
                    appCompatButtonCetak.setEnabled(false);
                    appCompatButtonCetak.setClickable(false);
                    appCompatButtonCetak.setText(R.string.on_print);
                    Log.d(TAG, "onClick: false");

                    if (produkCode.equals(ProdukCode.TELKOM_TELEPON)) {
                        qrCode = kode_area + "-" + id_pel;
                    } else {
                        qrCode = id_pel;
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            cetak(mContext);
                        }
                    });
                }
            }
        });

        textViewPlusSukses = findViewById(R.id.textViewPlusSukses);
        appCompatButtonShare = findViewById(R.id.appCompatButtonShare);
        appCompatButtonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(InqueryResultActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        + ContextCompat.checkSelfPermission(InqueryResultActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(InqueryResultActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE},
                            RequestCode.ActionCode_GROUP_STORAGE);
                    return;
                }
                openShare();
            }
        });
        textViewPlusTitleProduk = findViewById(R.id.textViewPlusTitleProduk);
        textViewPlusTitleProduk.setText(intent.getStringExtra("subject"));
        textViewPlusKomisi = findViewById(R.id.textViewPlusKomisi);

        if (produkGroup.equals(ProdukGroup.GAMEONLINE)) {
            payModel = (PayModel) intent.getSerializableExtra("pay_result");
            navigationBottomCustom(bottom_toolbar, FormatString.CurencyIDR(payModel.getSaldo()));
            strukTercetak = payModel.getStruk_tercetak();
            textViewPlusSukses.setVisibility(View.VISIBLE);
            textViewPlusSukses.startAnimation(slideUp);

            textViewPlusSukses.setText("Pembelian voucher " + produkName + " Berhasil!");
            MyDynamicToast.informationMessage(InqueryResultActivity.this, R.string.sukses_terbayar, "Pembelian voucher " + produkName + " Berhasil!");
            linCetak.setVisibility(View.VISIBLE);
            appCompatButtonBayar.setVisibility(View.GONE);
            appCompatButtonCetak.setVisibility(View.VISIBLE);
            textViewPlusKomisi.setVisibility(View.GONE);
        } else {
            navigationBottomCustom(bottom_toolbar, FormatString.CurencyIDR(inqModel.getSaldo()));

            textViewPlusKomisi.setText(String.format(getString(R.string.komisi), FormatString.CurencyIDR(inqModel.getKomisi_produk())));
            if (!PreferenceClass.getBoolean("switchKomisi", true)) {
                textViewPlusKomisi.setVisibility(View.GONE);
            } else {
                textViewPlusKomisi.setVisibility(View.VISIBLE);
            }
        }

        if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo()) || PreferenceClass.getId().substring(0, 2).toString().equals("BS")) {
            frame_ribbon_demo.setVisibility(View.VISIBLE);
        } else {
            frame_ribbon_demo.setVisibility(View.GONE);
        }

        Log.d(TAG, "onCreate: " + intent.getStringExtra("struk"));
        try {
            if (produkCode.equals(ProdukCode.TELKOM_TELEPON)) {
                bitmapQRCode = FormatString.TextToImageEncode(this, kode_area + "-" + id_pel);
                bitmapQRCodeShare = FormatString.TextToImageEncode(this, kode_area + "-" + id_pel);
            } else {
                bitmapQRCode = FormatString.TextToImageEncode(this, id_pel);
                bitmapQRCodeShare = FormatString.TextToImageEncode(this, id_pel);
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }

        textViewShowStruk.setHtmlText(inqModel.getStruk_show());

        imageViewQR.setImageBitmap(bitmapQRCodeShare);
    }

    File imageStruk;

    private void openShare() {
        path = Environment.getExternalStorageDirectory().toString();
        final File dirPDF = new File(path, "/Profit/struk/pdfs");
        final File dirImage = new File(path, "/Profit/struk/img/");
        NestedScrollView.LayoutParams lp = (NestedScrollView.LayoutParams) linStruk.getLayoutParams();
        linStruk.setLayoutParams(lp);

        final FrameLayout view = (FrameLayout) View.inflate(this, R.layout.layout_action_share, null);

        DialogUtils.openBottomSheetDialog(InqueryResultActivity.this, view);

        imageStruk = saveImageBaseFile((NestedScrollView) findViewById(R.id.nestedScrollView));

        final Uri screenshotUri = FileProvider.getUriForFile(InqueryResultActivity.this, "com.bm.main.pos.fileprovider", imageStruk);
        imageViewWa = view.findViewById(R.id.imageViewWa);
        imageViewWa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Device.SharingToSocialMedia(InqueryResultActivity.this, "WA", screenshotUri);
            }
        });

        imageViewTelegram = view.findViewById(R.id.imageViewTelegram);
        imageViewTelegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Device.SharingToSocialMedia(InqueryResultActivity.this, "TELEGRAM", screenshotUri);
            }
        });

        imageViewGmail = view.findViewById(R.id.imageViewGmail);
        imageViewGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Device.SharingToSocialMedia(InqueryResultActivity.this, "GMAIL", screenshotUri);
            }
        });

        imageViewBbm = view.findViewById(R.id.imageViewBbm);
        imageViewBbm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Device.SharingToSocialMedia(InqueryResultActivity.this, "BBM", screenshotUri);
            }
        });

        imageViewPdf = view.findViewById(R.id.imageViewPdf);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            imageViewPdf.setVisibility(View.VISIBLE);
        } else {
            imageViewPdf.setVisibility(View.GONE);
        }

        imageViewPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    new_popup_alertDemo(InqueryResultActivity.this, "Info", "Anda belum bisa menikmati fiture ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                } else {
                    new_popup_alert_two_button(InqueryResultActivity.this, "Information", "File PDf Struk tersimpan di folder " + dirPDF.toString(), imageStruk, linStruk, "isPdf");
                }
            }
        });

        imageViewImage = view.findViewById(R.id.imageViewImage);
        imageViewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    new_popup_alertDemo(InqueryResultActivity.this, "Info", "Anda belum bisa menikmati fiture ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                } else {
                    new_popup_alert_two_button(InqueryResultActivity.this, "Information", "File Image Struk tersimpan di folder " + dirImage.toString(), imageStruk, linStruk, "isImage");
                }
            }
        });


    }

    BluetoothDevice bDevice;
    // Initialize da new BroadcastReceiver mInstance
    @NonNull
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, @NonNull Intent intent) {
            boolean receivedNumber = intent.getBooleanExtra("finish", false);
            boolean receivedSocket = intent.getBooleanExtra("socket", false);
            String deviceName = intent.getStringExtra("device_name");
            if (receivedNumber) {
                appCompatButtonCetak.setEnabled(true);
                appCompatButtonCetak.setClickable(true);
                appCompatButtonCetak.setText(R.string.cetak);
            } else if (!receivedSocket) {
                snackBarCustomAction(findViewById(R.id.bottom_toolbar), 0, "Tidak dapat terhubung ke printer bluetooth " + deviceName + "\n Pastikan printer bluetooth " + deviceName + "anda telah menyala", WARNING);
                bDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                IntentFilter filter = new IntentFilter();

                filter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);

                registerReceiver(mReceiver, filter);
                appCompatButtonCetak.setEnabled(true);
                appCompatButtonCetak.setClickable(true);
                appCompatButtonCetak.setText(R.string.cetak);
            }
        }
    };

    public final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, @NonNull Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED.equals(action)) {
                try {
                    UUID BPP = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
                    try {
                        socket = bDevice.createRfcommSocketToServiceRecord(BPP);
                        Method m = bDevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
                        socket = (BluetoothSocket) m.invoke(bDevice, Integer.valueOf(1));
                        socket.connect();
                        Log.d(TAG, "Connected 1 " + socket.isConnected());
                    } catch (Exception e) {
                        Log.d(TAG, "onReceive: " + e.toString());
                    }
                } catch (Exception e) {
                    Log.d(TAG, "print exception: " + e.toString());
                }
            }
        }
    };

    private void requestPayment() {
        JSONObject jsonObject = new JSONObject();
        try {
            if (produkGroup.equals(ProdukGroup.PDAM)) {
                logEventFireBase(ProdukGroup.PDAM, textViewPlusTitleProduk.getText().toString(), EventParam.EVENT_ACTION_REQUEST_PAY, EventParam.EVENT_SUCCESS, TAG);
                jsonObject = new JSONObject(BaseActivity.stringJson.requestPaymentPDAM(produkCode, id_pel, no_meter, inqModel.getReff_id_inq(), inqModel.getNominal_inq(), inqModel.getNominal_admin_inq()));
            } else if (produkGroup.equals(ProdukGroup.BPJS)) {
                logEventFireBase(ProdukGroup.BPJS, ProdukCode.ASRBPJSKS, EventParam.EVENT_ACTION_REQUEST_PAY, EventParam.EVENT_SUCCESS, TAG);
                jsonObject = new JSONObject(BaseActivity.stringJson.requestPaymentBPJSKES(id_pel, no_hp, periode, inqModel.getReff_id_inq(), inqModel.getNominal_inq(), inqModel.getNominal_admin_inq()));
            } else if (produkGroup.equals(ProdukGroup.PLN)) {
                switch (produkCode) {
                    case ProdukCode.PLNPASC:
                        logEventFireBase(ProdukGroup.PLN, ProdukCode.PLNPASC, EventParam.EVENT_ACTION_REQUEST_PAY, EventParam.EVENT_SUCCESS, TAG);
                        jsonObject = new JSONObject(BaseActivity.stringJson.requestPaymentPLN(id_pel, inqModel.getReff_id_inq(), inqModel.getNominal_inq(), inqModel.getNominal_admin_inq()));
                        break;
                    case ProdukCode.PLNPRA:
                        logEventFireBase(ProdukGroup.PLN, ProdukCode.PLNPRA + " " + inqModel.getNominal_inq(), EventParam.EVENT_ACTION_REQUEST_PAY, EventParam.EVENT_SUCCESS, TAG);
                        jsonObject = new JSONObject(BaseActivity.stringJson.requestPaymentPLNPrabayar(id_pel, inqModel.getReff_id_inq(), inqModel.getNominal_inq(), inqModel.getNominal_admin_inq()));
                        break;
                    case ProdukCode.PLNNON:
                        logEventFireBase(ProdukGroup.PLN, ProdukCode.PLNNON, EventParam.EVENT_ACTION_REQUEST_PAY, EventParam.EVENT_SUCCESS, TAG);
                        jsonObject = new JSONObject(BaseActivity.stringJson.requestPaymentPLNNontalgis(id_pel, inqModel.getReff_id_inq(), inqModel.getNominal_inq(), inqModel.getNominal_admin_inq()));
                        break;
                }
            } else if (produkCode.equals(ProdukCode.TELKOM_SPEEDY)) {
                logEventFireBase(ProdukGroup.TELKOM, ProdukCode.TELKOM_SPEEDY, EventParam.EVENT_ACTION_REQUEST_PAY, EventParam.EVENT_SUCCESS, TAG);
                jsonObject = new JSONObject(BaseActivity.stringJson.requestPaymentSpeedy(id_pel, inqModel.getReff_id_inq(), inqModel.getNominal_inq(), inqModel.getNominal_admin_inq()));
            } else if (produkCode.equals(ProdukCode.TELKOM_TELEPON)) {
                logEventFireBase(ProdukGroup.TELKOM, ProdukCode.TELKOM_TELEPON, EventParam.EVENT_ACTION_REQUEST_PAY, EventParam.EVENT_SUCCESS, TAG);
                jsonObject = new JSONObject(BaseActivity.stringJson.requestPaymentTelkom(kode_area, id_pel, inqModel.getReff_id_inq(), inqModel.getNominal_inq(), inqModel.getNominal_admin_inq()));
            } else if (produkGroup.equals(ProdukGroup.FINANCE)) {
                logEventFireBase(ProdukGroup.FINANCE, textViewPlusTitleProduk.getText().toString(), EventParam.EVENT_ACTION_REQUEST_PAY, EventParam.EVENT_SUCCESS, TAG);
                jsonObject = new JSONObject(BaseActivity.stringJson.requestPaymentCicilan(produkCode, id_pel, nominal, inqModel.getReff_id_inq(), inqModel.getNominal_inq(), inqModel.getNominal_admin_inq()));
            } else if (produkGroup.equals(ProdukGroup.HPPASCA)) {
                logEventFireBase(ProdukGroup.HPPASCA, textViewPlusTitleProduk.getText().toString(), EventParam.EVENT_ACTION_REQUEST_PAY, EventParam.EVENT_SUCCESS, TAG);
                jsonObject = new JSONObject(BaseActivity.stringJson.requestPaymentTelpPasca(produkCode, id_pel, inqModel.getReff_id_inq(), inqModel.getNominal_inq(), inqModel.getNominal_admin_inq()));
            } else if (produkGroup.equals(ProdukGroup.KARTUKREDIT)) {
                logEventFireBase(ProdukGroup.KARTUKREDIT, textViewPlusTitleProduk.getText().toString(), EventParam.EVENT_ACTION_REQUEST_PAY, EventParam.EVENT_SUCCESS, TAG);
                jsonObject = new JSONObject(BaseActivity.stringJson.requestPaymentKartuKredit(produkCode, id_pel, nominal, inqModel.getReff_id_inq(), inqModel.getNominal_inq(), inqModel.getNominal_admin_inq()));
            } else if (produkGroup.equals(ProdukGroup.TVKABEL)) {
                logEventFireBase(ProdukGroup.TVKABEL, textViewPlusTitleProduk.getText().toString(), EventParam.EVENT_ACTION_REQUEST_PAY, EventParam.EVENT_SUCCESS, TAG);
                jsonObject = new JSONObject(BaseActivity.stringJson.requestPaymentTvKabel(produkCode, id_pel, inqModel.getReff_id_inq(), inqModel.getNominal_inq(), inqModel.getNominal_admin_inq()));
            } else if (produkGroup.equals(ProdukGroup.ASURANSI)) {
                logEventFireBase(ProdukGroup.ASURANSI, textViewPlusTitleProduk.getText().toString(), EventParam.EVENT_ACTION_REQUEST_PAY, EventParam.EVENT_SUCCESS, TAG);
                jsonObject = new JSONObject(BaseActivity.stringJson.requestPaymentAsuransi(produkCode, id_pel, inqModel.getReff_id_inq(), inqModel.getNominal_inq(), inqModel.getNominal_admin_inq()));
            } else if (produkGroup.equals(ProdukGroup.PGN)) {
                logEventFireBase(ProdukGroup.PGN, ProdukCode.PGN, EventParam.EVENT_ACTION_REQUEST_PAY, EventParam.EVENT_SUCCESS, TAG);
                jsonObject = new JSONObject(BaseActivity.stringJson.requestPaymentPgn(id_pel, inqModel.getReff_id_inq(), inqModel.getNominal_inq(), inqModel.getNominal_admin_inq()));
            } else if (produkGroup.equals(ProdukGroup.PERBANKAN)) {
                switch (produkCode) {
                    case ProdukCode.SETORTUNAI:
                        logEventFireBase(ProdukGroup.PERBANKAN, ProdukCode.SETORTUNAI, EventParam.EVENT_ACTION_REQUEST_PAY, EventParam.EVENT_SUCCESS, TAG);
                        jsonObject = new JSONObject(BaseActivity.stringJson.requestPaymentSetorTunai(id_pel, inqModel.getReff_id_inq(), inqModel.getNominal_inq(), inqModel.getNominal_admin_inq()));
                        break;
                    case ProdukCode.TARIKTUNAI1:
                        logEventFireBase(ProdukGroup.PERBANKAN, ProdukCode.TARIKTUNAI1, EventParam.EVENT_ACTION_REQUEST_PAY, EventParam.EVENT_SUCCESS, TAG);
                        jsonObject = new JSONObject(BaseActivity.stringJson.requestPaymentTarikTunai(OTC, id_pel, bank_nominal, inqModel.getReff_id_inq(), inqModel.getNominal_inq(), inqModel.getNominal_admin_inq()));
                        break;
                    case ProdukCode.TRANSFERTUNAIALLBANK:
                        logEventFireBase(ProdukGroup.PERBANKAN, ProdukCode.TRANSFERTUNAIALLBANK, EventParam.EVENT_ACTION_REQUEST_PAY, EventParam.EVENT_SUCCESS, TAG);
                        jsonObject = new JSONObject(BaseActivity.stringJson.requestPaymentTransferTunaiAllBank(id_pel, bank_code, bank_name, inqModel.getReff_id_inq(), inqModel.getNominal_inq(), inqModel.getNominal_admin_inq()));
                        break;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithJSONObjectResponsePayment(this, jsonObject, ActionCode.PAY, this);
        @SuppressLint("InflateParams")
        ViewGroup parent = findViewById(R.id.contentHost);
        final FrameLayout view = (FrameLayout) View.inflate(this, R.layout.loading_bar_full_dialog, parent);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText(R.string.progress_bar_wording);
        openProgressBarDialog(this, view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rumah, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify da parent activity in AndroidManifest.xml.
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
        Intent intent = new Intent();

        setResult(RESULT_OK, intent);
        finish();
    }

    @SuppressLint("JavascriptInterface")
    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        closeProgressBarDialog();
        //  Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        Log.d(TAG, "onSuccess: " + response.toString());
        try {
            if (response.get("response_code").toString().equals(ResponseCode.SUCCESS)) {
                //  setupWebView();

                if (produkCode.equals(ProdukCode.PLNPRA)) {
                    logEventFireBase(produkGroup, textViewPlusTitleProduk.getText().toString() + " " + inqModel.getNominal_inq(), EventParam.EVENT_ACTION_RESULT_PAY, EventParam.EVENT_SUCCESS, TAG);
                } else {
                    logEventFireBase(produkGroup, textViewPlusTitleProduk.getText().toString(), EventParam.EVENT_ACTION_RESULT_PAY, EventParam.EVENT_SUCCESS, TAG);
                }
                PreferenceClass.putString("saldo", FormatString.CurencyIDR(response.get("saldo").toString()));
                String imagePath = "file:///android_asset/ic_logo.png";
                //  Bitmap bitmapQRCode = null;
                try {
                    if (produkCode.equals(ProdukCode.TELKOM_TELEPON)) {
                        bitmapQRCode = FormatString.TextToImageEncode(this, kode_area + "-" + id_pel);
                        bitmapQRCodeShare = FormatString.TextToImageEncode(this, kode_area + "-" + id_pel);
                    } else {
                        bitmapQRCode = FormatString.TextToImageEncode(this, id_pel);
                        bitmapQRCodeShare = FormatString.TextToImageEncode(this, id_pel);
                    }
                } catch (WriterException e) {
                    e.printStackTrace();
                }

                imagePathQR = BitMapToString(bitmapQRCode);
                struk_show = response.get("struk_show").toString();
                textViewShowStruk.setHtmlText(struk_show);

                imageViewQR.setImageBitmap(bitmapQRCodeShare);

                navigationBottomCustom(bottom_toolbar, FormatString.CurencyIDR(response.get("saldo").toString()));
                strukTercetak = response.get("struk_tercetak").toString().replace("<b>", ResponseCode.BoldOn + ResponseCode.UpOn).replace("</b>", ResponseCode.BoldOff + ResponseCode.UpOff);
                textViewPlusSukses.setVisibility(View.VISIBLE);
                textViewPlusSukses.startAnimation(slideUp);
                textViewPlusSukses.setText(getResources().getString(R.string.sukses_terbayar));
                String pesan = getResources().getString(R.string.sukses_terbayar);
                linCetak.setVisibility(View.VISIBLE);
                appCompatButtonBayar.setVisibility(View.GONE);
                appCompatButtonCetak.setVisibility(View.VISIBLE);
                textViewPlusKomisi.setVisibility(View.GONE);
            } else if (response.getString("response_code").equals("03")) {
                new_popup_alert_session(this, "Informasi", response.getString("response_desc"));
            } else if (response.getString("response_code").equals("06")) {
                new_popup_alert_session_deposit(this, "Informasi", response.getString("response_desc"));

            } else {
                if (produkCode.equals(ProdukCode.PLNPRA)) {
                    logEventFireBase(produkGroup, textViewPlusTitleProduk.getText().toString() + " " + inqModel.getNominal_inq(), EventParam.EVENT_ACTION_RESULT_PAY, EventParam.EVENT_NOT_SUCCESS, TAG);
                } else {
                    logEventFireBase(produkGroup, textViewPlusTitleProduk.getText().toString(), EventParam.EVENT_ACTION_RESULT_PAY, EventParam.EVENT_NOT_SUCCESS, TAG);
                }
                new_popup_alert(InqueryResultActivity.this, "Info", response.get("response_desc").toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {

        closeProgressBarDialog();

        new_popup_alert_failure_pay(InqueryResultActivity.this, responseDescription);
    }

    public String BitMapToString(@NonNull Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public void promptDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(InqueryResultActivity.this);

        ViewGroup parent = findViewById(R.id.contentHost);
        final View dialogView = View.inflate(this, R.layout.promptdialog_custom_view_otc, parent);

        // Specify alert dialog is not cancelable/not ignorable
        builder.setCancelable(false);

        // Set the custom layout as alert dialog view
        builder.setView(dialogView);

        // Get the custom alert dialog view widgets reference
        final AppCompatButton btn_positive = dialogView.findViewById(R.id.dialog_positive_btn);
        AppCompatButton btn_negative = dialogView.findViewById(R.id.dialog_negative_btn);
        final MaterialEditText editTextOTC = dialogView.findViewById(R.id.editTextOTC);
        final AlertDialog dialog = builder.create();

        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the alert dialog
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputManager != null) {
                    if (editTextOTC.isFocusable() == true) {
                        inputManager.hideSoftInputFromWindow(editTextOTC.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    }
                }


                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    new_popup_alertDemo(InqueryResultActivity.this, "Info", "Anda belum bisa menikmati fiture ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");
                } else {
                    OTC = editTextOTC.getText().toString();

                    if (editTextOTC.getText().length() == 0) {
                        editTextOTC.setError("Tidak Boleh Kosong");
                    } else {
                        dialog.dismiss();
                        requestPayment();
                    }
                }
            }
        });

        // Set negative/no button click listener
        btn_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputManager != null) {
                    if (editTextOTC.isFocusable() == true) {
                        inputManager.hideSoftInputFromWindow(editTextOTC.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    }
                }
                dialog.dismiss();
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // this.onDismiss(dialog);
                Log.d(TAG, "onDismiss: ");
            }
        });

        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == RequestCode.ActionCode_GROUP_STORAGE) {
            if ((grantResults.length > 0) && (grantResults[0] + grantResults[1])
                    == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                openShare();
            } else {
                new_popup_alert_two_buttonPermision(this, "Peringatan", R.string.content_alert_group_storage);

            }
        } else if (requestCode == RequestCode.ActionCode_READ_SMS_OTP) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                new_popup_alert_two_buttonPermision(this, "Peringatan", R.string.content_alert_read_otp);
            }
        }
    }
}

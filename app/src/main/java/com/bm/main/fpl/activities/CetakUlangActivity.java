package com.bm.main.fpl.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
//import androidx.core.content.LocalBroadcastManager;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.NestedScrollView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.text.method.LinkMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.main.fpl.templates.htmlspanner.handlers.TableHandler;
import com.bm.main.fpl.templates.htmltextview.HtmlTextView;
import com.bm.main.fpl.utils.Device;
import com.bm.main.fpl.utils.DialogUtils;
import com.bm.main.fpl.utils.FormatString;
import com.bm.main.pos.R;
import com.bm.main.fpl.constants.RequestCode;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.models.CUModel;
//import com.bm.main.fpl.templates.htmltextview.HtmlTextView;
import com.bm.main.fpl.utils.PreferenceClass;
import com.google.zxing.WriterException;


import java.io.File;
import java.lang.reflect.Method;
import java.util.UUID;

public class CetakUlangActivity extends BaseActivity {
    private static final String TAG = CetakUlangActivity.class.getSimpleName();
    private Context mContext;
    // private LinearLayout bottom_toolbar;
    // WebView webViewResult;
    private AppCompatButton appCompatButtonCetak;
    ImageView imageViewShare, imageViewWa, imageViewGmail, imageViewTelegram, imageViewBbm, imageViewPdf, imageViewImage;
    String id_transaksi;
    String id_pel = "", produk = "";
    CUModel cuModel;
    // private String saldo;
    String htmlValue = "";
    AppCompatButton appCompatButtonShare;
    private TextView textViewTitleProduk;
    private HtmlTextView textViewShowStruk;
    LinearLayout linStruk;

    String struk_show = "";

    Bitmap bitmapQRCodeCU;

    ImageView imageViewLogo, imageViewQR;

    public String strukTercetak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cetak_ulang);
        mContext = this;
        Intent intent = getIntent();

        produk = intent.getStringExtra("produk");
        id_pel = intent.getStringExtra("id_pel");

        strukTercetak = intent.getStringExtra("struk_tercetak").replace("<b>", ResponseCode.BoldOn + ResponseCode.UpOn).replace("</b>", ResponseCode.UpOff + ResponseCode.BoldOff);
        struk_show = intent.getStringExtra("struk_show");

        htmlValue = "<center>" + intent.getStringExtra("value") + "</center>";

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Cetak Ulang");
        init(1);
        try {
            bitmapQRCode = FormatString.TextToImageEncode(this, id_pel);
            bitmapQRCodeCU = FormatString.TextToImageEncode(this, id_pel);
            id_pelQRCode = id_pel;
        } catch (WriterException e) {
            e.printStackTrace();
        }

        // cuModel = (CUModel) intent.getSerializableExtra("cu_result");
        //saldo = intent.getStringExtra("saldo");
        bottom_toolbar = findViewById(R.id.bottom_toolbar);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(
                mMessageReceiver,
                new IntentFilter("BROADCAST_PRINTING")
        );

//        nestedScrollView = findViewById(R.id.nestedScrollView);
        linStruk = findViewById(R.id.linStruk);
        //   textViewMember = findViewById(R.id.textViewMember);
        imageViewLogo = findViewById(R.id.imageViewLogo);
        imageViewQR = findViewById(R.id.imageViewQR);

        textViewShowStruk = findViewById(R.id.textViewShowStruk);
//        TableHandler tableHandler=new TableHandler();
//        tableHandler.setTableWidth(nestedScrollView.getWidth());
        // textViewShowStruk.setHtmlText("<b>Bold Text</b>");
//        Spanned spanned = Html.fromHtml(intent.getStringExtra("struk_tercetak"),  Html.FROM_HTML_MODE_COMPACT);
//        textViewShowStruk.setMovementMethod(LinkMovementMethod.getInstance());

        //textViewShowStruk.setText(Html.fromHtml(, Html.FROM_HTML_MODE_COMPACT));
        appCompatButtonCetak = findViewById(R.id.appCompatButtonCetak);

        appCompatButtonCetak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                appCompatButtonCetak.setFocusableInTouchMode(false);
                appCompatButtonCetak.setClickable(false);
                appCompatButtonCetak.setEnabled(false);
                appCompatButtonCetak.setText(R.string.on_print);
                //  Log.d(TAG, "onClick: false");

                //   qrCode=id_pel;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cetak(mContext);
                    }
                });


            }
        });
//        imageViewShare = findViewById(R.id.imageViewShare);
//        imageViewShare.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                openShare();
//                //  sheet.show();
//            }
//        });
        appCompatButtonShare = findViewById(R.id.appCompatButtonShare);
        appCompatButtonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(CetakUlangActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        + ContextCompat.checkSelfPermission(CetakUlangActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    //    Log.i("Permission is require first time", "...OK...getPermission() method!..if");
                    ActivityCompat.requestPermissions(CetakUlangActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE},
                            RequestCode.ActionCode_GROUP_STORAGE);
                    return;
                }
                openShare();
                //  sheet.show();
            }
        });
//        webViewResult = findViewById(R.id.webViewResult);
//        webViewResult.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                // For final release of your app, comment the toast notification
//                //   Toast.makeText(mContext,"Long Click Disabled",Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });
//        webViewResult.setHapticFeedbackEnabled(false);
//
//
//        webViewResult.setLongClickable(false);
        textViewTitleProduk = findViewById(R.id.textViewTitleProduk);
        textViewTitleProduk.setText(produk);
//        setupWebView();

        // PreferenceClass.putString("saldo", FormatString.CurencyIDR(saldo));


//        try {
//            //setting size of qr code
//            int width =25;
//            int height = 25;
//            int smallestDimension = width < height ? width : height;
//
//            //EditText editText=(EditText)findViewById(R.id.editText) ;
//           // String qrCodeData = editText.getText().toString();
//            //setting parameters for qr code
//            String charset = "UTF-8"; // or "ISO-8859-1"
//            Map<EncodeHintType, ErrorCorrectionLevel> hintMap =new HashMap<EncodeHintType, ErrorCorrectionLevel>();
//            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
//            bitmapQRCode= CreateQRCode(id_pel, charset, hintMap, smallestDimension, smallestDimension);
//
//        } catch (Exception ex) {
//            Log.e("QrGenerate",ex.getMessage());
//        }

//        String imagePathQR = "file://" + saveImage(bitmapQRCode);
//        String imagePathQR =BitMapToString(bitmapQRCode);
//        String html = "<center><img src=\""+ imagePath + "\" width='200' height='50'></center>";
//       // String htmlQRCode = "<center><img src=\""+ imagePathQR + "\" width='50' height='50'></center>";
//        String htmlQRCode = "<center><img src=\"data:image/jpeg;base64," + imagePathQR + "\" width='50' height='50'/></center>";
        // webViewResult.clearHistory();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            WebView.enableSlowWholeDocumentDraw();
//        }


        //   webViewResult.loadDataWithBaseURL(imagePath,html+htmlValue+htmlQRCode, "text/html", "UTF-8", null);
        // Log.d(TAG, "onCreate: "+html+htmlValue+htmlQRCode);
        // imageViewLogo.setBackground(ContextCompat.getDrawable(this,R.drawable.ic_logo_color));
        //  textViewMember.setText(PreferenceClass.getUser().getNama_loket()+"\n"+PreferenceClass.getUser().getAlamat_loket()+"\nToko Modern Fastpay");
        //  BitmapDrawable drawableLeft = new BitmapDrawable(getResources(), bitmapQRCodeCU);
//                textViewShowStruk.setCompoundDrawablesWithIntrinsicBounds(null,ContextCompat.getDrawable(this,R.drawable.ic_logo_color) , null, drawableLeft);
//        textViewShowStruk.setCompoundDrawablesWithIntrinsicBounds(null, ContextCompat.getDrawable(this,R.drawable.ic_logo_color), null, drawableLeft);
        textViewShowStruk.setHtmlText(struk_show);
        //  textViewShowStruk.setMovementMethod(LinkMovementMethod.getInstance());

        imageViewQR.setImageBitmap(bitmapQRCodeCU);


//        textViewShowStruk.setHtmlText(R.raw.test);
//        textViewShowStruk.setHtmlTagHandler("htmltable");

        //   textViewShowStruk.setHtmlText(html+htmlValue+htmlQRCode);
        //webViewResult.loadUrl(html+htmlValue+htmlQRCode);

//        navigationBottomCustom(bottom_toolbar, FormatString.CurencyIDR(saldo));
        navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));
        // strukTercetak = cuModel.getStruk_tercetak();

    }

    //    private void requestCetakUlang() {
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject = new JSONObject(BaseActivity.stringJson.requestCetakTransaksi(id_transaksi));
//    } catch (JSONException e) {
//        e.printStackTrace();
//    }
//        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.CETAK_ULANG, this);
//    @SuppressLint("InflateParams") final View view = getLayoutInflater().inflate(R.layout.loading_bar_full_dialog, null);
//    TextView text = view.findViewById(R.id.textContentProgressBar);
//        text.setText(R.string.progress_bar_wording);
//    openProgressBarDialog(this, view);
//    }
    BluetoothDevice bDevice;
    // Initialize da new BroadcastReceiver mInstance
    @NonNull
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, @NonNull Intent intent) {
            // Get the received random number
            boolean receivedNumber = intent.getBooleanExtra("finish", false);
            boolean receivedSocket = intent.getBooleanExtra("socket", false);
            String deviceName = intent.getStringExtra("device_name");
            if (receivedNumber == true) {
//                appCompatButtonCetak.setFocusableInTouchMode(true);
                appCompatButtonCetak.setEnabled(true);
                appCompatButtonCetak.setClickable(true);
                appCompatButtonCetak.setText(R.string.cetak);
            } else if (receivedSocket == false) {
                snackBarCustomAction(findViewById(R.id.bottom_toolbar), 0, "Tidak dapat terhubung ke printer bluetooth " + deviceName + "\n Pastikan printer bluetooth " + deviceName + "anda telah menyala", WARNING);

                bDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                IntentFilter filter = new IntentFilter();

//        String action = "android.bleutooth.device.action.UUID";
                filter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);


                registerReceiver(mReceiver, filter);
//                appCompatButtonCetak.setFocusableInTouchMode(true);
                appCompatButtonCetak.setEnabled(true);
                appCompatButtonCetak.setClickable(true);
                appCompatButtonCetak.setText(R.string.cetak);
            }
            // Display da notification that the broadcast received
            //     Toast.makeText(context,"Received : " + receivedNumber,Toast.LENGTH_SHORT).show();
        }
    };
    //    @Override
//    protected void onDestroy() {
//
//
//        super.onDestroy();
//        unregisterReceiver(mReceiver);
//    }
//
//    @Override
//    protected void onPause() {
//        if (bluetoothAdapter != null) {
//            if (bluetoothAdapter.isDiscovering()) {
//                bluetoothAdapter.cancelDiscovery();
//            }
//        }
//
//        super.onPause();
//    }
    public final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, @NonNull Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED.equals(action)) {
                try {
                    UUID BPP = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
                    //  final BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    //bluetoothAdapter.getRemoteDevice(bDevice.getAddress());
                    // mmDevice=bluetoothAdapter.getRemoteDevice(bDevice.getAddress());
                    //  socket = createBluetoothSocket(device);
                    try {
                        socket = bDevice.createRfcommSocketToServiceRecord(BPP);
                        // socket = createBluetoothSocket(device);
//
//                    } catch (IOException e) {
//
//                        showToast("Tidak dapat melakukan koneksi awal dengan printer bluetooth"); // Unable
//                    }
                        // Method m = device.getClass().getMethod("createBond", int.class);
//                    Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[] { UUID.class });
//                    try {

                        // Method m = device.getClass().getMethod("createRfcommSocket", int.class);
                        //     Method m = device.getClass().getMethod("createInsecureRfcommSocket", int.class);

                        //socket = (BluetoothSocket) m.invoke(device, 1);
                        // socket = (BluetoothSocket) m.invoke(device, BPP);
                        Method m = bDevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
                        socket = (BluetoothSocket) m.invoke(bDevice, Integer.valueOf(1));
                        socket.connect();
                        //  cetak(CetakUlangActivity.this);
                        Log.d(TAG, "Connected 1 " + socket.isConnected());
                    } catch (Exception e) {
                        Log.d(TAG, "onReceive: " + e.toString());
                    }
                } catch (Exception e) {


                    Log.d(TAG, "print exception: " + e.toString());


//                Intent intent = new Intent(this, SettingPrinterActivity.class);
//                startActivity(intent);

                }
            }
        }
    };

    File imageStruk;

    private void openShare() {
        path = Environment.getExternalStorageDirectory().toString();
        final File dirPDF = new File(path, "/Profit/struk/pdfs");
        final File dirImage = new File(path, "/Profit/struk/img/");
        NestedScrollView.LayoutParams lp = (NestedScrollView.LayoutParams) linStruk.getLayoutParams();

        linStruk.setLayoutParams(lp);
        ViewGroup parent = findViewById(R.id.framecontentHost);
        final FrameLayout view = (FrameLayout) View.inflate(this, R.layout.layout_action_share, parent);
        DialogUtils.openBottomSheetDialog(CetakUlangActivity.this, view);
        imageStruk = saveImageBaseFile((NestedScrollView) findViewById(R.id.nestedScrollView));
        final Uri screenshotUri = FileProvider.getUriForFile(CetakUlangActivity.this, "com.bm.main.pos.fileprovider", imageStruk);
        imageViewWa = view.findViewById(R.id.imageViewWa);
        imageViewWa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Device.SharingToSocialMedia(CetakUlangActivity.this, "WA", screenshotUri);
            }
        });

        imageViewGmail = view.findViewById(R.id.imageViewGmail);
        imageViewGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Device.SharingToSocialMedia(CetakUlangActivity.this, "GMAIL", screenshotUri);
            }
        });

        imageViewTelegram = view.findViewById(R.id.imageViewTelegram);
        imageViewTelegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Device.SharingToSocialMedia(CetakUlangActivity.this, "TELEGRAM", screenshotUri);
            }
        });

        imageViewBbm = view.findViewById(R.id.imageViewBbm);
        imageViewBbm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Device.SharingToSocialMedia(CetakUlangActivity.this, "BBM", screenshotUri);
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
                new_popup_alert_two_button(CetakUlangActivity.this, "Information", "File PDf Struk tersimpan di folder " + dirPDF.toString(), imageStruk, linStruk, "isPdf");
            }
        });

        imageViewImage = view.findViewById(R.id.imageViewImage);
        imageViewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_popup_alert_two_button(CetakUlangActivity.this, "Information", "File Image Struk tersimpan di folder " + dirImage.toString(), imageStruk, linStruk, "isImage");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify da parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // navigationBottomCustom(this,"");

        finish();
        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


    }

//    @SuppressLint({"AddJavascriptInterface", "SetJavaScriptEnabled"})
//    private void setupWebView() {
//        String unencodedHtml =
//                "&lt;html&gt;&lt;body&gt;'%23' is the percent code for ‘#‘ &lt;/body&gt;&lt;/html&gt;";
//        String encodedHtml = Base64.encodeToString(unencodedHtml.getBytes(),
//                Base64.NO_PADDING);
//        webViewResult.loadData(encodedHtml, "text/html", "base64");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            webViewResult.getSettings().setSafeBrowsingEnabled(false);
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            webViewResult.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
//        } else {
//            webViewResult.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
//        }
//
//
//
//        webViewResult.getSettings().setJavaScriptEnabled(true);
//        webViewResult.clearHistory();
//        webViewResult.clearFormData();
//        // webViewResultInq.onTouchEvent(new On);
//        webViewResult.clearCache(true);
//        webViewResult.getSettings().setDefaultTextEncodingName("utf-8");
//        webViewResult.getSettings().setAppCacheEnabled(false);
//
//        webViewResult.getSettings().setTextZoom(5 * 25);
////        webViewResult.getSettings().setFixedFontFamily(getString(R.font.roboto_thin));
////        webViewResultInq.getSettings().setLoadWithOverviewMode(true);
//        webViewResult.setPadding(0, 0, 0, 0);
//
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
////            webViewResult.getSettings().setSafeBrowsingEnabled(false);
////        }
////        webViewResult.getSettings().setJavaScriptEnabled(true);
////        webViewResult.clearHistory();
////        webViewResult.clearFormData();
////       // webViewResult.clearCache(true);
////        webViewResult.getSettings().setDefaultTextEncodingName("utf-8");
////        webViewResult.getSettings().setAppCacheEnabled(false);
//////        webViewResultInq.getSettings().setLoadWithOverviewMode(true);
////        webViewResult.setPadding(0, 0, 0, 0);
//
//
//    }

    @Override
    protected void onResume() {
        super.onResume();
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
        }
    }


}

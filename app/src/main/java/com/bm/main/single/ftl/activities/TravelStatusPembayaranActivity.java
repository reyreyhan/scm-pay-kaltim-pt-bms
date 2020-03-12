package com.bm.main.single.ftl.activities;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.constants.RequestCode;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.utils.FileUtils;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.pos.R;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bm.main.single.ftl.constants.TravelPath;
import com.bm.main.single.ftl.models.PesananKapal;
import com.bm.main.single.ftl.models.PesananKereta;
import com.bm.main.single.ftl.models.PesananPesawat;
import com.bm.main.single.ftl.models.PesananWisata;
import com.bm.main.single.ftl.ship.activities.ShipStatusPayActivity;
import com.bm.main.single.ftl.utils.RequestUtilsTravel;
import com.bm.main.single.ftl.utils.utilBand;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Method;
import java.util.UUID;

public class TravelStatusPembayaranActivity extends BaseActivity implements ProgressResponseCallback {


    private static final String TAG = TravelStatusPembayaranActivity.class.getSimpleName();
    ImageView ivPembayaran;
    TextView statusTx1, statusTx2, textViewKomisi, keteranganPx1;
    boolean isSuccess = false;
    int komisi;
    private String transactionId;
    private String url_etiket;
    private String namaProduk;
    private String kode;
    private String nama;
    private String produk;
    private AppCompatButton buttonCetak;
    private String urlStruk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travel_status_pembayaran_activity);
        isSuccess = getIntent().getBooleanExtra("status", true);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(isSuccess ? "Transaksi Berhasil" : "Transaksi Gagal");
        init(0);

        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver,
                new IntentFilter("BROADCAST_PRINTING")
        );
        String rd = getIntent().getStringExtra("rd");
        urlStruk=getIntent().getStringExtra("url_struk");
        komisi = getIntent().getIntExtra("komisi", 0);
        produk = getIntent().getStringExtra("product");
        statusTx1 = findViewById(R.id.statusTx1);
        statusTx2 = findViewById(R.id.statusTx2);
        textViewKomisi = findViewById(R.id.textViewKomisi);
        keteranganPx1 = findViewById(R.id.keteranganPx1);
        ivPembayaran = findViewById(R.id.ivPembayaran);
        bottom_toolbar = findViewById(R.id.bottom_toolbar);

        if (isSuccess) {
            statusTx1.setText("SELAMAT");
//            statusTx2.setText("Transaksi Anda Berhasil");
            statusTx2.setText("Transaksi Anda " + rd);
            textViewKomisi.setText("Komisi Anda " + utilBand.formatRupiah(komisi));
            keteranganPx1.setText(R.string.info_status);
            //keteranganPx2.setText("di aplikasi Fasttravel Mobile. Dapatkan keuntungan berlimpah");
            //keteranganPx3.setText("dengan mengikuti promo Fasttravel");
            ivPembayaran.setBackgroundResource(R.drawable.ic_success);
            transactionId = getIntent().getStringExtra("transactionId");
            url_etiket = getIntent().getStringExtra("url_etiket");
            getStruk(urlStruk, 0, this);
            request_data_payment();
        } else {
            statusTx1.setText("Transaksi Gagal");
            statusTx2.setText("Saldo Anda Tidak Cukup");
            textViewKomisi.setText("");
            keteranganPx1.setText("Silahkan melakukan pengisian saldo untuk melanjutkan transaksi anda. Dapatkan keuntungan berlimpah dengan mengikuti promo Fastpay Travel");
            ivPembayaran.setBackgroundResource(R.drawable.ic_gagal);
        }

        Button buttonKembaliKeAwal = findViewById(R.id.buttonKembaliKeAwal);
        buttonKembaliKeAwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = null;
////                try {
////                intent = new Intent(TravelStatusPembayaranActivity.this, HomeActivity.class);
//                intent = new Intent();
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
////                } catch (ClassNotFoundException e) {
////                    e.printStackTrace();
////                }
                Intent intent = new Intent();
                intent.setAction(TravelActionCode.MENU_TRAVEL);
                setResult(RESULT_OK,intent);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        Button buttonLihatPromo = findViewById(R.id.buttonLihatPromo);
        buttonLihatPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = "http://blog.fastravel.co.id";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(link));
                startActivity(intent);
            }
        });

        Button buttonVoucher = findViewById(R.id.button_etiket);
        if(getIntent().getStringExtra("product").equals("HOTEL")||getIntent().getStringExtra("product").equals("WISATA")){
            buttonVoucher.setText("Download Voucher PDF");
        }
        buttonVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                String url = SavePref.getInstance(TravelStatusPembayaranActivity.this).getString("url_etiket");
                //  String url = "http://api.fastravel.co.id/app/generate_etiket?id_transaksi=" + transactionId;
                //  Log.v("INFO", url);

                //   doCekPDF(transactionId + ".pdf", url_etiket);

                if (ContextCompat.checkSelfPermission(TravelStatusPembayaranActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        + ContextCompat.checkSelfPermission(TravelStatusPembayaranActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    //    Log.i("Permission is require first time", "...OK...getPermission() method!..if");
                    ActivityCompat.requestPermissions(TravelStatusPembayaranActivity.this,
                            new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE},
                            RequestCode.ActionCode_GROUP_STORAGE);
                    return;
                }

                openEtiket();

            }
        });


         buttonCetak = findViewById(R.id.button_cetak_struk);
        buttonCetak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String transactionId = getIntent().getStringExtra("transactionId");
//                String url_struk = getIntent().getStringExtra("url_struk");
                kodeBooking=kode;
                buttonCetak.setClickable(false);
                buttonCetak.setEnabled(true);
                buttonCetak.setText(R.string.on_print);

                cetak(TravelStatusPembayaranActivity.this);
//                String url = SavePref.getInstance(TravelStatusPembayaranActivity.this).getString("url_etiket");
                //String url = "http://api.fastravel.co.id/app/generate_struk?id_transaksi=" + transactionId;
//                Log.v("INFO", url_struk);
//                getStruk(url_struk, 1, TravelStatusPembayaranActivity.this);
//
            }
        });


        RelativeLayout viewPesanTiketHotel = findViewById(R.id.viewPesanTiketHotel);
        RelativeLayout viewPesanTiketKereta = findViewById(R.id.viewPesanTiketKereta);
        RelativeLayout viewPesanTiketPesawat = findViewById(R.id.viewPesanTiketPesawat);
        RelativeLayout viewPesanTiketPelni = findViewById(R.id.viewPesanTiketPelni);
        viewPesanTiketHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = null;
////                try {
////                intent = new Intent(TravelStatusPembayaranActivity.this, StartHotelActivity.class);
//                intent = new Intent();
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
////                } catch (ClassNotFoundException e) {
////                    e.printStackTrace();
////                }
            }
        });

        viewPesanTiketPelni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(TravelActionCode.MENU_PELNI);
                setResult(RESULT_OK,intent);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


        viewPesanTiketKereta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = null;
//                try {
//                Intent intent = new Intent(TravelStatusPembayaranActivity.this, SearchTrainActivity.class);
//                Intent intent = new Intent();
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }

                Intent intent = new Intent();
                intent.setAction(TravelActionCode.MENU_KERETA);
                setResult(RESULT_OK,intent);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        viewPesanTiketPesawat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = null;
////                try {
////                intent = new Intent(TravelStatusPembayaranActivity.this, SearchFlightActivity.class);
//                intent = new Intent();
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
                Intent intent = new Intent();
                intent.setAction(TravelActionCode.MENU_PESAWAT);
                setResult(RESULT_OK,intent);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == android.R.id.home) {
//            onBackPressed();
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == RequestCode.ActionCode_GROUP_STORAGE) {
            if ((grantResults.length > 0) && (grantResults[0]+grantResults[1])
                    == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                openEtiket();
            } else {
                new_popup_alert_two_buttonPermision(this, "Peringatan", R.string.content_alert_group_storage);

            }
        }
    }

    private void openEtiket() {
//        Intent intent = new Intent(TravelStatusPembayaranActivity.this, ShowPdfActivity.class);
//        intent.putExtra("namaPdf", transactionId);
//        intent.putExtra("urlPdf", url_etiket);
//        intent.putExtra("produk", produk);
//        intent.putExtra("namaProduk", namaProduk);
//        intent.putExtra("nama", nama);
//        intent.putExtra("kode", kode);
//        startActivity(intent);
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                FileUtils.doCekPDF(transactionId + ".pdf", url_etiket);
//                //  doCekPDF(finalTransaction_id + ".pdf", finalUrl_pdf);
//                // doCekPDF(finalTransaction_id , finalUrl_pdf);
//                String path = Environment.getExternalStorageDirectory().toString();
////                                        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
//                File dir = new File(path, "/FastPay/struk/pdfs/");
//                final File file = new File(dir, transactionId + ".pdf");
//                //   final File file = new File(dir, finalTransaction_id);
//                // Intent intentShareFile = new Intent(Intent.ACTION_SEND);
//                if (file.exists()) {
////                                            intentShareFile.setType("application/pdf");
////                                            intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + file.getAbsolutePath()));
////                                            intentShareFile.putExtra(Intent.EXTRA_SUBJECT,
////                                                    "Sharing File...");
////                                            intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File...");
////                                            startActivity(Intent.createChooser(intentShareFile, "Share File"));
//                    new Handler().post(new Runnable() {
//                        @Override
//                        public void run() {
//                            //   openSendVia(view, Uri.parse("file://" + file.getAbsolutePath()), finalproduk, finalnamaProduk, finalkode, finalnama);
//                            openSendVia(findViewById(R.id.button_etiket), Uri.fromFile(file), produk, namaProduk, kode, nama, file);
//                        }
//                    });
//                } else {
////                                            showToastCustom(DaftarPesananActivity.this, 1, "File tidak ditemukan!");
//                    snackBarCustomAction(findViewById(R.id.rootLayout), 0, "File tidak ditemukan!", 1);
//                }
//            }
//        });

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FileUtils.doCekPDF(transactionId + ".pdf", url_etiket);
                //  doCekPDF(finalTransaction_id + ".pdf", finalUrl_pdf);
                // doCekPDF(finalTransaction_id , finalUrl_pdf);
                String path = Environment.getExternalStorageDirectory().toString();
//                                        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
                File dir = new File(path, "/FastPay/struk/pdfs/");
                final File file = new File(dir, transactionId + ".pdf");
                //   final File file = new File(dir, finalTransaction_id);
                // Intent intentShareFile = new Intent(Intent.ACTION_SEND);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                if (file.exists()) {
//                                            intentShareFile.setType("application/pdf");
//                                            intentShareFile.putExtra(Intent.EXTRA_STREAM,  Uri.fromFile(file));
//                                            intentShareFile.putExtra(Intent.EXTRA_SUBJECT,
//                                                    "Sharing File...");
//                                            intentShareFile.putExtra(Intent.EXTRA_TEXT, "Sharing File...");
//                                            startActivity(Intent.createChooser(intentShareFile, "Share File"));
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                        //   File file=new File(mFilePath);
////                    Uri uri = FileProvider.getUriForFile(TravelTiketPesananActivity.this, getPackageName() + ".provider", file);
//                        Uri uri =  FileProvider.getUriForFile(TravelStatusPembayaranActivity.this, "com.bm.main.fpl.fileprovider", file);
//                        intent = new Intent(Intent.ACTION_VIEW);
//                        intent.setData(uri);
//                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        startActivity(intent);
//                    } else {
//                        intent = new Intent(Intent.ACTION_VIEW);
//                        intent.setDataAndType(Uri.parse(file.getAbsolutePath()), "application/pdf");
//                        intent = Intent.createChooser(intent, "Open File");
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                    }
//                new Handler().post(new Runnable() {
//                    @Override
//                    public void run() {
//                        //   openSendVia(view, Uri.parse("file://" + file.getAbsolutePath()), finalproduk, finalnamaProduk, finalkode, finalnama);
//                        openSendVia(findViewById(R.id.action_right_pdf), Uri.fromFile(file), finalproduk, finalnamaProduk, finalkode, finalnama, file);
//                    }
//                });

                    FileUtils.openPdf(TravelStatusPembayaranActivity.this,file);

                } else {
//                                            showToastCustom(DaftarPesananActivity.this, 1, "File tidak ditemukan!");
                    snackBarCustomAction(findViewById(R.id.rootLayout), 0, "File tidak ditemukan!", 1);
                }
            }
        });

    }

    public void request_data_payment() {
        JSONObject jsonObject = new JSONObject();
        try {
            //  jsonObject.put("airline", (String) MemoryStore.get(this, "airlineCode"));
            switch (getIntent().getStringExtra("product")) {
                case "PESAWAT":
                    jsonObject.put("product", "flight");
                    break;
                case "KERETA":
                    jsonObject.put("product", "train");
                    break;
                case "WISATA":
                    jsonObject.put("product", "tour");
                    break;
                case "KAPAL":
                    jsonObject.put("product", "ship");
                    break;
                case "HOTEL":
                    jsonObject.put("product", "hotel");
                    break;
            }
            jsonObject.put("transactionId", transactionId);


            jsonObject.put("token", PreferenceClass.getToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "REQUEST payFlight: " + jsonObject);
        RequestUtilsTravel.transportWithProgressResponse(TravelStatusPembayaranActivity.this, TravelPath.DATA_PAYMENT, jsonObject,  TravelActionCode.DATA_PAYMENT,TravelStatusPembayaranActivity.this);
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
////            Promo promo = new Promo();
////            setFragment(promo);
//            menuTop.setVisibility(View.VISIBLE);
//
//        }
            //   View contentView = getLayoutInflater().inflate(R.layout.dialog_top, toolbar);
            //    contentView.setVisibility(View.VISIBLE);

            openTopDialog(false);
        } else if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onUpdate(int actionCode,long bytesRead, long totalSize, boolean done) {

    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        Log.d(TAG, "onSuccess: " + response.toString());
        try{
            JSONArray data = response.getJSONArray("data");

            switch (getIntent().getStringExtra("product")) {
                case "PESAWAT":

//            SharedPreferenceUtils.getInstance(StatusPayFlightActivty.this).putJSONArray("data",response.getJSONArray("data"));

                    //namaProduk= data.getJSONObject(0).getString();
//            new PesananPesawat(data.getJSONObject(0));

//            try {
                    // JSONArray data = response.getJSONArray("data");


                    PesananPesawat pesananPesawat = new PesananPesawat(data.getJSONObject(0));
                    produk = "Pesawat";
                    namaProduk = pesananPesawat.getNama_maskapai();
                    kode = pesananPesawat.getKode_booking();
                    kodeBooking = pesananPesawat.getKode_booking();

                    nama = pesananPesawat.getPenumpangList().get(0).getTitle() + " " + pesananPesawat.getPenumpangList().get(0).getNama();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

                    break;
                case "KERETA":
//            try {
                    //  JSONArray data = response.getJSONArray("data");


                    PesananKereta pesananKereta = new PesananKereta(data.getJSONObject(0));
                    produk = "Kereta";
                    namaProduk = pesananKereta.getNama_kereta();
                    kode = pesananKereta.getKode_booking();
                    kodeBooking = pesananKereta.getKode_booking();

                    nama = pesananKereta.getPenumpangList().get(0).getNama();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
                    break;
                case "WISATA":
//            try {
                    //   JSONArray data = response.getJSONArray("data");


                    PesananWisata pesananWisata = new PesananWisata(data.getJSONObject(0));
                    produk = "Paket Wisata";
                    namaProduk = pesananWisata.getDestination();
                    kode = pesananWisata.getKode_booking();
                    kodeBooking = pesananWisata.getKode_booking();

                    nama = pesananWisata.getNama_peserta();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
                    break;
                case "KAPAL":
//            try {
                    //   JSONArray data = response.getJSONArray("data");


                    PesananKapal pesananKapal = new PesananKapal(data.getJSONObject(0));
                    produk = "Pelni";
                    namaProduk = pesananKapal.getNama_kapal();
                    kode = pesananKapal.getKode_booking();
                    kodeBooking = pesananKapal.getKode_booking();

                    nama = pesananKapal.getPenumpangList().get(0).getNama();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
                    break;
            }
//            else if (getIntent().getStringExtra("product").equals("HOTEL")) {
////            try {
//                //     JSONArray data = response.getJSONArray("data");
//
//
//                PesananHotel pesananHotel = new PesananHotel(data.getJSONObject(0));
//                produk="Hotel";
//                namaProduk=pesananHotel.getNama_hotel();
//                kode=pesananHotel.getKode_booking();
//
//                nama=pesananHotel.getTamuList().get(0).getNama_depan()+" "+pesananHotel.getTamuList().get(0).getNama_belakang();
////            } catch (JSONException e) {
////                e.printStackTrace();
////            }
//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {
    }

    BluetoothDevice bDevice;
    @NonNull
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, @NonNull Intent intent) {
            // Get the received random number
            boolean receivedNumber = intent.getBooleanExtra("finish", false);
            boolean receivedSocket = intent.getBooleanExtra("socket", false);
            String deviceName = intent.getStringExtra("device_name" );
            if (receivedNumber == true) {
//                buttonCetak.setFocusableInTouchMode(true);
                buttonCetak.setEnabled(true);
                buttonCetak.setClickable(true);
                buttonCetak.setText(R.string.cetak_struk);
            }else
            if (receivedSocket == false) {
                snackBarCustomAction(findViewById(R.id.bottom_toolbar), 0, "Tidak dapat terhubung ke printer bluetooth " + deviceName + "\n Pastikan printer bluetooth " + deviceName + "anda telah menyala", WARNING);
                bDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                IntentFilter filter = new IntentFilter();

//        String action = "android.bleutooth.device.action.UUID";
                filter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);


                registerReceiver(mReceiver, filter);
//                buttonCetak.setFocusableInTouchMode(true);
                buttonCetak.setEnabled(true);
                buttonCetak.setClickable(true);
                buttonCetak.setText(R.string.cetak_struk);

//                Intent intentSett = new Intent(TravelStatusPembayaranActivity.this, SettingPrinterActivity.class);
//                startActivity(intentSett);
            }
            // Display da notification that the broadcast received
            //     Toast.makeText(context,"Received : " + receivedNumber,Toast.LENGTH_SHORT).show();
        }
    };
    // @Override
//    public void callbackReturn(boolean finish) {
//      //  textResult.setText("Callback function called");
//    }
    public final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, @NonNull Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "onReceive: "+action);
            if(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED.equals(action)){
                try{
                    UUID BPP = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
                    final BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    //  bluetoothAdapter.getRemoteDevice(device.getAddress());
                    //  mmDevice=bluetoothAdapter.getRemoteDevice(device.getAddress());
                    //  socket = createBluetoothSocket(device);
                    try {
                        socket = bDevice.createRfcommSocketToServiceRecord(BPP);
                        // socket = createBluetoothSocket(device);
//
//                    } catch (IOException e) {
//
//                        showToast("Tidak dapat melakukan koneksi awal dengan printer bluetooth"); // Unable
//                    }
                        //  Method m = device.getClass().getMethod("createBond", int.class);
//                    Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[] { UUID.class });
//                    try {
//
                        // Method m = device.getClass().getMethod("createRfcommSocket", int.class);
                        //     Method m = device.getClass().getMethod("createInsecureRfcommSocket", int.class);

                        // socket = (BluetoothSocket) m.invoke(device, 1);
                        // socket = (BluetoothSocket) m.invoke(device, BPP);
                        Method m = bDevice.getClass().getMethod("createRfcommSocket", new Class[] { int.class });
                        socket = (BluetoothSocket)m.invoke(bDevice, Integer.valueOf(1));
                        socket.connect();
                        // cetak(InqueryResultActivity.this);
                        Log.d(TAG, "Connected 1 " + socket.isConnected());
                    } catch (Exception e) {
                        Log.d(TAG, "onReceive: "+e.toString());
                    }
                } catch (Exception e)

                {


                    Log.d(TAG, "print exception: " + e.toString());


//                Intent intent = new Intent(this, SettingPrinterActivity.class);
//                startActivity(intent);

                }
            }
        }
    };

}

package com.bm.main.single.ftl.ship.activities;

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
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.constants.RequestCode;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.utils.FileUtils;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.pos.R;
import com.bm.main.single.ftl.activities.TravelTiketPesananActivity;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bm.main.single.ftl.constants.TravelPath;
import com.bm.main.single.ftl.models.PesananKapal;
import com.bm.main.single.ftl.utils.RequestUtilsTravel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Method;
import java.util.UUID;

public class ShipStatusPayActivity extends BaseActivity implements ProgressResponseCallback, View.OnClickListener {
    private static final String TAG = ShipStatusPayActivity.class.getSimpleName();
    String idTrx, urlStruk, urlEtiket, komisi;
    private String[] arr_nama_pdf;
    private AppCompatButton buttonCetak, buttonEtiket;
    RelativeLayout viewPesanTiketPesawat, viewPesanTiketKereta,viewPesanTiketPelni;
    AppCompatButton buttonKembaliKeAwal, buttonLihatPromo;
    private String namaProduk;
    private String kode;
    private String nama;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ship_status_pay_activity);
        Intent intent=this.getIntent();
        if(intent!=null)
            idTrx=   intent.getStringExtra("idTrx");
        urlStruk=intent.getStringExtra("urlStruk");
        urlEtiket=intent.getStringExtra("urlEtiket");
        komisi=intent.getStringExtra("komisi");
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Status Pembayaran");
        init(1);


        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver,
                new IntentFilter("BROADCAST_PRINTING")
        );
        TextView textViewKomisi= findViewById(R.id.textViewKomisiShip);
        textViewKomisi.setText("Komisi Anda "+komisi);
        bottom_toolbar = findViewById(R.id.bottom_toolbar);
        viewPesanTiketPesawat=findViewById(R.id.viewPesanTiketPesawat);
        viewPesanTiketPesawat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setAction(TravelActionCode.MENU_PESAWAT);
                setResult(RESULT_OK,intent);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });
        viewPesanTiketKereta=findViewById(R.id.viewPesanTiketKereta);
        viewPesanTiketKereta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = null;
////                try {
//                intent = new Intent(TrainStatusPayActivity.this, SearchTrainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
////                } catch (ClassNotFoundException e) {
////                    e.printStackTrace();
////                }
//
                Intent intent = new Intent();
                intent.setAction(TravelActionCode.MENU_KERETA);
                setResult(RESULT_OK,intent);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        viewPesanTiketPelni=findViewById(R.id.viewPesanTiketPelni);
        viewPesanTiketPelni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = null;
////                try {
//                intent = new Intent(TrainStatusPayActivity.this, SearchTrainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
////                } catch (ClassNotFoundException e) {
////                    e.printStackTrace();
////                }
//
                Intent intent = new Intent();
                intent.setAction(TravelActionCode.MENU_PELNI);
                setResult(RESULT_OK,intent);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        //viewPesanTiketHotel=findViewById(R.id.viewPesanTiketHotel);
//        viewPesanTiketHotel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = null;
////                try {
//                intent = new Intent(TrainStatusPayActivity.this, StartHotelActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
////                } catch (ClassNotFoundException e) {
////                    e.printStackTrace();
////                }
//            }
//        });

        buttonKembaliKeAwal=findViewById(R.id.buttonKembaliKeAwal);
        buttonKembaliKeAwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = null;
////                try {
//                intent = new Intent(TrainStatusPayActivity.this, ToursTiketingActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                }
                Intent intent = new Intent();
                intent.setAction(TravelActionCode.MENU_TRAVEL);
                setResult(RESULT_OK,intent);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        buttonLihatPromo=findViewById(R.id.buttonLihatPromo);
        buttonLihatPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = "http://blog.fastravel.co.id";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(link));
                startActivity(intent);
            }
        });

        buttonCetak= findViewById(R.id.button_cetak_struk);
        buttonCetak.setOnClickListener(this);
        buttonEtiket= findViewById(R.id.button_etiket);
        buttonEtiket.setOnClickListener(this);
//        try {
        arr_nama_pdf = urlEtiket.split("=");
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        getStruk(urlStruk, 0,this);
//
        request_data_payment();
        navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));

    }

    public void request_data_payment() {
        JSONObject jsonObject = new JSONObject();
        try {
            //  jsonObject.put("airline", (String) MemoryStore.get(this, "airlineCode"));
            jsonObject.put("product", "ship");
            jsonObject.put("transactionId", idTrx);


            jsonObject.put("token", PreferenceClass.getToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Log.d(TAG, "REQUEST payFlight: " + jsonObject);
        RequestUtilsTravel.transportWithProgressResponse(this, TravelPath.DATA_PAYMENT, jsonObject, TravelActionCode.DATA_PAYMENT,this);
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

//    public void closeTopSheet(View v) {
//        dialog.dismiss();
//    }


    public void onBackPressed() {
        // Intent intent = new Intent(TrainStatusPayActivity.this, FlightSearchActivity.class);
//        intent.putExtra("isFromPay",true);
//        startActivity(intent);
////        finish();
//        ActivityCompat.finishAffinity(TrainStatusPayActivity.this);
//        Intent intent = new Intent();
//        intent.setAction(TravelActionCode.MENU_KERETA);
        Intent intent = new Intent();
        intent.setAction(TravelActionCode.MENU_PELNI);
        setResult(RESULT_OK,intent);
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }

    @Override
    public void onClick(@NonNull View v) {
        int id=v.getId();

        if(id==R.id.button_cetak_struk){
//            buttonCetak.setFocusableInTouchMode(false);
            buttonCetak.setEnabled(false);
            buttonCetak.setClickable(false);
            buttonCetak.setText(R.string.on_print);
            cetak(this);
        }else if(id==R.id.button_etiket){

//            SharedPreferenceUtils.getInstance(StatusPayFlightActivty.this).getJSONObject("data");

            if (ContextCompat.checkSelfPermission(ShipStatusPayActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    + ContextCompat.checkSelfPermission(ShipStatusPayActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                //    Log.i("Permission is require first time", "...OK...getPermission() method!..if");
                ActivityCompat.requestPermissions(ShipStatusPayActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        RequestCode.ActionCode_GROUP_STORAGE);
                return;
            }


            openEtiket();
        }
    }
    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        Log.d(TAG, "onSuccess: " + response.toString());


        try {
//            SharedPreferenceUtils.getInstance(StatusPayFlightActivty.this).putJSONArray("data",response.getJSONArray("data"));
            JSONArray data = response.getJSONArray("data");
            //namaProduk= data.getJSONObject(0).getString();
//            new PesananKereta(data.getJSONObject(0));
            PesananKapal pesananKapal = new PesananKapal(data.getJSONObject(0));
            namaProduk = pesananKapal.getNama_kapal();
            kode = pesananKapal.getKode_booking();
            kodeBooking=pesananKapal.getKode_booking();

            nama = pesananKapal.getPenumpangList().get(0).getNama();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {
    }

    @Override
    public void onUpdate(int actionCode,long bytesRead, long totalSize, boolean done) {

    }

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
//        Intent intent = new Intent(ShipStatusPayActivity.this, ShowPdfActivity.class);
//        intent.putExtra("namaPdf", idTrx);
//        intent.putExtra("urlPdf", urlEtiket);
//        intent.putExtra("produk", "Pelni");
//        intent.putExtra("namaProduk", namaProduk);
//        intent.putExtra("nama", nama);
//        intent.putExtra("kode", kode);
//        startActivity(intent);

        String produk="Pelni";
//        intent.putExtra("namaProduk", namaProduk);
//        intent.putExtra("nama", nama);
//        intent.putExtra("kode", kode);
//        startActivity(intent);

//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                FileUtils.doCekPDF(idTrx + ".pdf", urlEtiket);
//                //  doCekPDF(finalTransaction_id + ".pdf", finalUrl_pdf);
//                // doCekPDF(finalTransaction_id , finalUrl_pdf);
//                String path = Environment.getExternalStorageDirectory().toString();
////                                        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
//                File dir = new File(path, "/FastPay/struk/pdfs/");
//                final File file = new File(dir, idTrx + ".pdf");
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
                FileUtils.doCekPDF(idTrx + ".pdf", urlEtiket);
                //  doCekPDF(finalTransaction_id + ".pdf", finalUrl_pdf);
                // doCekPDF(finalTransaction_id , finalUrl_pdf);
                String path = Environment.getExternalStorageDirectory().toString();
//                                        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
                File dir = new File(path, "/FastPay/struk/pdfs/");
                final File file = new File(dir, idTrx + ".pdf");
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
//                        Uri uri =  FileProvider.getUriForFile(ShipStatusPayActivity.this, "com.bm.main.fpl.fileprovider", file);
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

                    FileUtils.openPdf(ShipStatusPayActivity.this,file);

                } else {
//                                            showToastCustom(DaftarPesananActivity.this, 1, "File tidak ditemukan!");
                    snackBarCustomAction(findViewById(R.id.rootLayout), 0, "File tidak ditemukan!", 1);
                }
            }
        });


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

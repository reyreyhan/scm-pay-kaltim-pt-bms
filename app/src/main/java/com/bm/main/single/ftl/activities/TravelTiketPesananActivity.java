package com.bm.main.single.ftl.activities;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.constants.RequestCode;
import com.bm.main.fpl.utils.FileUtils;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.scm.R;
import com.bm.main.scm.SBFApplication;
import com.bm.main.single.ftl.models.EtiketFlightModel;
import com.bm.main.single.ftl.models.EtiketFlightModelDataPenumpang;
import com.bm.main.single.ftl.models.EtiketShipModel;
import com.bm.main.single.ftl.models.EtiketShipModelDataPenumpang;
import com.bm.main.single.ftl.models.EtiketTrainModel;
import com.bm.main.single.ftl.models.EtiketTrainModelDataPenumpang;
import com.bumptech.glide.Glide;

import java.io.File;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;

public class TravelTiketPesananActivity extends BaseActivity {
    private static final String TAG =TravelTiketPesananActivity.class.getSimpleName() ;
    String url_struk, url_pdf, id_transaksi;
    PenumpangAdapter penumpangAdapter;
    ImageView imageViewCopy;
    private String finalproduk;
    private String finalnamaProduk;
    private String finalnama;
    private String finalkode;
    private AppCompatButton btnCetak;

    EtiketFlightModel.Data pesananPesawat;
    EtiketTrainModel.Data pesananKereta;
    EtiketShipModel.Data pesananKapal;
    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travel_tiket_pesanan_activity);
        Intent intent = getIntent();
        url_struk = intent.getStringExtra("url_struk");
        url_pdf = intent.getStringExtra("url_pdf");
        id_transaksi = intent.getStringExtra("id_transaksi");

//        String data = intent.getStringExtra("data");

        String product = intent.getStringExtra("product");

        switch (product) {
            case "PESAWAT":
                pesananPesawat = intent.getParcelableExtra("data");
                break;
            case "KERETA":
                pesananKereta = intent.getParcelableExtra("data");
                break;
            case "KAPAL":
                pesananKapal = intent.getParcelableExtra("data");
                break;
        }


        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Detail Pesanan");
        toolbar.setSubtitle("No.Pesanan Fastpay Travel " + id_transaksi);
        init(0);
        bottom_toolbar = findViewById(R.id.bottom_toolbar);

        imageViewCopy = findViewById(R.id.imageViewCopy);

        final TextView tvKodeBooking = findViewById(R.id.tvKodeBooking);
        TextView tvNama = findViewById(R.id.tvNama);
        ImageView ivIcon = findViewById(R.id.ivIcon);
        TextView tvJamBerangkat = findViewById(R.id.tvJamBerangkat);
        TextView tvTanggalBerangkat = findViewById(R.id.tvTanggalBerangkat);
        TextView tvDurasi = findViewById(R.id.tvDurasi);
        TextView tvJamDatang = findViewById(R.id.tvJamDatang);
        TextView tvTanggalDatang = findViewById(R.id.tvTanggalDatang);
        TextView tvOrigin = findViewById(R.id.tvOrigin);
        TextView tvDestination = findViewById(R.id.tvDestination);

        TextView tvTextJadwal = findViewById(R.id.tvTextJadwal);
        TextView tvTextPenumpang = findViewById(R.id.tvTextPenumpang);

        View panelInfo1 = findViewById(R.id.panelInfo1);
        View panelInfo2 = findViewById(R.id.panelInfo2);
        TextView tvDuration = findViewById(R.id.tvDuration);
        TextView tvTanggal = findViewById(R.id.tvTanggal);

        LocalBroadcastManager.getInstance(this).registerReceiver(
                mMessageReceiver,
                new IntentFilter("BROADCAST_PRINTING")
        );
        RecyclerView recyclerListPenumpang = findViewById(R.id.recyclerListPenumpang);
        recyclerListPenumpang.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerListPenumpang.setLayoutManager(mLayoutManager);

        penumpangAdapter = new PenumpangAdapter(this);
        recyclerListPenumpang.setAdapter(penumpangAdapter);

         btnCetak = findViewById(R.id.btnCetak);
//        MaterialRippleLayout.on(btnCetak).rippleOverlay(true)
//                .rippleAlpha(0.2f)
//                //.rippleColor(0xFF585858)
//                .rippleColor(R.color.md_orange_200)
//                .rippleHover(true)
//                .create();
        btnCetak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showToast("Printing.....");
                kodeBooking=tvKodeBooking.getText().toString();
                btnCetak.setEnabled(false);
                btnCetak.setClickable(false);
                btnCetak.setText(R.string.on_print);
                getStruk(url_struk,1, TravelTiketPesananActivity.this);

            }
        });





        String produk="";
        String namaProduk="";

        String nama="";
        String kode="";

        imageViewCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", tvKodeBooking.getText().toString());
                clipboard.setPrimaryClip(clip);
                showToast("Kode Booking Telah Disalin");
            }
        });


//        try {
//            JSONObject jsonObject = new JSONObject(data);

        switch (product) {
            case "PESAWAT": {
                tvTextJadwal.setText("Jadwal Pesawat");
                //  PesananPesawat pesananPesawat = new PesananPesawat(jsonObject);
                //  EtiketFlightModel.Data pesananPesawat=dataFlight;
                tvKodeBooking.setText(pesananPesawat.getKode_booking());
                tvNama.setText(pesananPesawat.getNama_maskapai());
                tvDestination.setText(pesananPesawat.getDestination());
                tvOrigin.setText(pesananPesawat.getOrigin());
                tvJamBerangkat.setText(pesananPesawat.getJam_keberangkatan());
                tvJamDatang.setText(pesananPesawat.getJam_kedatangan());
                tvDurasi.setText(pesananPesawat.getDuration());

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                SimpleDateFormat odf = new SimpleDateFormat("dd MMMM yyyy", SBFApplication.config.locale);
//                try {
                // tvTanggalBerangkat.setText(odf.format(sdf.parse(pesananPesawat.getTanggal_keberangkatan())));
                tvTanggalBerangkat.setText(pesananPesawat.getTanggal_keberangkatan());
//                } catch (ParseException e) {
//                    tvTanggalBerangkat.setText("");
//                    e.printStackTrace();
//                }
                //try {
                tvTanggalDatang.setText(pesananPesawat.getTanggal_kedatangan());
//                } catch (ParseException e) {
//                    tvTanggalDatang.setText("");
//                    e.printStackTrace();
//                }

                Glide.with(TravelTiketPesananActivity.this).load(pesananPesawat.getAirlineIcon())
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into(ivIcon);

                // for(PesananPesawat.Penumpang penumpang : pesananPesawat.getPenumpangList()) {
                for (EtiketFlightModelDataPenumpang penumpang : pesananPesawat.getPenumpang()) {
                    penumpangAdapter.data.add(penumpang.getTitle() + ". " + penumpang.getNama() + "|||" + penumpang.getStatus());
                }
                penumpangAdapter.notifyDataSetChanged();
                produk = "Pesawat";
                namaProduk = pesananPesawat.getNama_maskapai();
                kode = pesananPesawat.getKode_booking();

//                nama=pesananPesawat.getPenumpangList().get(0).getTitle()+" "+pesananPesawat.getPenumpangList().get(0).getNama();
                nama = pesananPesawat.getPenumpang()[0].getTitle() + " " + pesananPesawat.getPenumpang()[0].getNama();

                break;
            }
            case "KERETA": {


                tvTextJadwal.setText("Jadwal Kereta");
                //  PesananKereta pesananKereta = new PesananKereta(jsonObject);

                tvKodeBooking.setText(pesananKereta.getKode_booking());
                tvNama.setText(pesananKereta.getNama_kereta());
                tvDestination.setText(pesananKereta.getDestination());
                tvOrigin.setText(pesananKereta.getOrigin());
                tvDurasi.setText("");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                SimpleDateFormat odf = new SimpleDateFormat("dd MMMM yyyy", SBFApplication.config.locale);
                SimpleDateFormat sdf1 = new SimpleDateFormat("HHmm");
                SimpleDateFormat odf1 = new SimpleDateFormat("HH:mm", SBFApplication.config.locale);
                try {
                    tvTanggalBerangkat.setText(odf.format(sdf.parse(pesananKereta.getTanggal_keberangkatan())));
                } catch (ParseException e) {
                    tvTanggalBerangkat.setText("");
                    e.printStackTrace();
                }
                try {
                    tvTanggalDatang.setText(odf.format(sdf.parse(pesananKereta.getTanggal_kedatangan())));
                } catch (ParseException e) {
                    tvTanggalDatang.setText("");
                    e.printStackTrace();
                }
                try {
                    tvJamBerangkat.setText(odf1.format(sdf1.parse(pesananKereta.getJam_keberangkatan())));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    tvJamDatang.setText(odf1.format(sdf1.parse(pesananKereta.getJam_kedatangan())));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                ivIcon.setVisibility(View.GONE);

                for (EtiketTrainModelDataPenumpang penumpang : pesananKereta.getPenumpang()) {
                    penumpangAdapter.data.add(penumpang.getNama() + "|||" + "-");
                }
                penumpangAdapter.notifyDataSetChanged();
                produk = "Kereta";
                namaProduk = pesananKereta.getNama_kereta();
                kode = pesananKereta.getKode_booking();

                nama = pesananKereta.getPenumpang()[0].getNama();

                break;
            }
            case "KAPAL": {
                //  JSONObject jsonObject = new JSONObject(data);

                tvTextJadwal.setText("Jadwal Kapal");
                //  PesananKapal pesananKapal = new PesananKapal(jsonObject);

                tvKodeBooking.setText(pesananKapal.getKode_booking());
                tvNama.setText(pesananKapal.getNama_kapal());
                tvDestination.setText(pesananKapal.getDestination());
                tvOrigin.setText(pesananKapal.getOrigin());
                tvJamBerangkat.setText(pesananKapal.getJam_keberangkatan());
                tvJamDatang.setText(pesananKapal.getJam_kedatangan());
                tvDurasi.setText("");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                SimpleDateFormat odf = new SimpleDateFormat("dd MMMM yyyy", SBFApplication.config.locale);
                try {
                    tvTanggalBerangkat.setText(odf.format(sdf.parse(pesananKapal.getTanggal_keberangkatan())));
                } catch (ParseException e) {
                    tvTanggalBerangkat.setText("");
                    e.printStackTrace();
                }
                try {
                    tvTanggalDatang.setText(odf.format(sdf.parse(pesananKapal.getTanggal_kedatangan())));
                } catch (ParseException e) {
                    tvTanggalDatang.setText("");
                    e.printStackTrace();
                }

                ivIcon.setVisibility(View.GONE);

                for (EtiketShipModelDataPenumpang penumpang : pesananKapal.getPenumpang()) {
                    penumpangAdapter.data.add(penumpang.getNama() + "|||" + "-");
                }
                penumpangAdapter.notifyDataSetChanged();
                produk = "Pelni";
                namaProduk = pesananKapal.getNama_kapal();
                kode = pesananKapal.getKode_booking();

                nama = pesananKapal.getPenumpang()[0].getNama();

//            }else if(product.equals("HOTEL")) {
//                tvTextJadwal.setText("Jadwal Hotel");
//                tvTextPenumpang.setText("Tamu");
//                PesananHotel pesananHotel = new PesananHotel(jsonObject);
//
//                panelInfo1.setVisibility(View.GONE);
//                panelInfo2.setVisibility(View.VISIBLE);
//
//                tvKodeBooking.setText(pesananHotel.getKode_booking());
//                tvNama.setText(pesananHotel.getNama_hotel());
//                tvDuration.setText(pesananHotel.getPax() + " Malam");
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                SimpleDateFormat odf = new SimpleDateFormat("dd MMMM yyyy", config.locale);
//                try {
//                    Date checkInDate = sdf.parse(pesananHotel.getCheck_in());
//                    Calendar calendar = Calendar.getInstance();
//                    calendar.setTime(checkInDate);
//                    calendar.add(Calendar.DAY_OF_MONTH, Integer.parseInt(pesananHotel.getPax()));
//
//                    Date checkOutDate = calendar.getTime();
//                    tvTanggal.setText(odf.format(checkInDate) + " - " + odf.format(checkOutDate));
//
//                } catch (ParseException e) {
//                    tvTanggal.setText("");
//                    e.printStackTrace();
//                }
//
//                for(PesananHotel.Tamu tamu : pesananHotel.getTamuList()) {
//                    penumpangAdapter.data.add(tamu.getNama_depan() + " " + tamu.getNama_belakang() + "|||" + "-");
//                }
//                penumpangAdapter.notifyDataSetChanged();
//                produk="Hotel";
//                namaProduk=pesananHotel.getNama_hotel();
//                kode=pesananHotel.getKode_booking();
//
//                nama=pesananHotel.getTamuList().get(0).getNama_depan()+" "+pesananHotel.getTamuList().get(0).getNama_belakang();

//            }else if(product.equals("WISATA")) {
//                tvTextJadwal.setText("Jadwal Wisata");
//                tvTextPenumpang.setText("Peserta");
//                PesananWisata pesananWisata = new PesananWisata(jsonObject);
//
//                panelInfo1.setVisibility(View.GONE);
//                panelInfo2.setVisibility(View.VISIBLE);
//
//                tvKodeBooking.setText(pesananWisata.getKode_booking());
//                tvNama.setText(pesananWisata.getDestination());
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//                SimpleDateFormat odf = new SimpleDateFormat("dd MMMM yyyy", config.locale);
//                try {
//                    Date checkInDate = sdf.parse(pesananWisata.getTanggal_mulai());
//                    Date checkOutDate = sdf.parse(pesananWisata.getTangal_selesai());
//
//                    long difference = Math.abs(checkInDate.getTime() - checkOutDate.getTime());
//                    long differenceDates = difference / (24 * 60 * 60 * 1000);
//
//                    tvTanggal.setText(odf.format(checkInDate) + " - " + odf.format(checkOutDate));
//                    tvDuration.setText(differenceDates + " Hari");
//
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//                for(PesananWisata.Wisatawan tamu : pesananWisata.getWisatawanList()) {
//                    penumpangAdapter.data.add(tamu.getNama() + "|||" + tamu.getPhone());
//                }
//                penumpangAdapter.notifyDataSetChanged();
//                produk="Paket Wisata";
//                namaProduk=pesananWisata.getDestination();
//                kode=pesananWisata.getKode_booking();
//
//                nama=pesananWisata.getNama_peserta();

                break;
            }
        }

            finalproduk=produk;
            finalnamaProduk=namaProduk;

            finalnama=nama;
            finalkode=kode;

//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.core_menu_open_ticket, menu);
        return true;
    }
//MenuItem menuItem;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        menuItem=item;
        int i = item.getItemId();
        if (i == R.id.action_right_drawer) {
            openTopDialog(false);

        } else if (i == R.id.action_right_pdf) {


           checkPermisionStorage();

        } else if (i == android.R.id.home) {
            onBackPressed();

        }
        //return super.onOptionsItemSelected(item);
        return false;
    }
    private void checkPermisionStorage(){
        if (ContextCompat.checkSelfPermission(TravelTiketPesananActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                + ContextCompat.checkSelfPermission(TravelTiketPesananActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            //    Log.i("Permission is require first time", "...OK...getPermission() method!..if");
            ActivityCompat.requestPermissions(TravelTiketPesananActivity.this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    RequestCode.ActionCode_GROUP_STORAGE);
                return;
        }


        openEtiket();
    }

private void openEtiket(){
//    Intent intent = new Intent(TravelTiketPesananActivity.this, ShowPdfActivity.class);
//    intent.putExtra("namaPdf", id_transaksi);
//    intent.putExtra("urlPdf", url_pdf);
//    intent.putExtra("produk", finalproduk);
//    intent.putExtra("namaProduk", finalnamaProduk);
//    intent.putExtra("nama", finalnama);
//    intent.putExtra("kode", finalkode);
//    startActivity(intent);
    runOnUiThread(new Runnable() {
        @Override
        public void run() {
            FileUtils.doCekPDF(id_transaksi + ".pdf", url_pdf);
            //  doCekPDF(finalTransaction_id + ".pdf", finalUrl_pdf);
            // doCekPDF(finalTransaction_id , finalUrl_pdf);
            String path = Environment.getExternalStorageDirectory().toString();
//                                        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            File dir = new File(path, "/FastPay/struk/pdfs/");
            final File file = new File(dir, id_transaksi + ".pdf");
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
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                 //   File file=new File(mFilePath);
////                    Uri uri = FileProvider.getUriForFile(TravelTiketPesananActivity.this, getPackageName() + ".provider", file);
//                    Uri uri =  FileProvider.getUriForFile(TravelTiketPesananActivity.this, "com.bm.main.fpl.fileprovider", file);
//                    intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(uri);
//                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    startActivity(intent);
//                } else {
//                    intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setDataAndType(Uri.parse(file.getAbsolutePath()), "application/pdf");
//                    intent = Intent.createChooser(intent, "Open File");
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                }

                FileUtils.openPdf(TravelTiketPesananActivity.this,file);


//                new Handler().post(new Runnable() {
//                    @Override
//                    public void run() {
//                        //   openSendVia(view, Uri.parse("file://" + file.getAbsolutePath()), finalproduk, finalnamaProduk, finalkode, finalnama);
//                        openSendVia(findViewById(R.id.action_right_pdf), Uri.fromFile(file), finalproduk, finalnamaProduk, finalkode, finalnama, file);
//                    }
//                });
            } else {
//                                            showToastCustom(DaftarPesananActivity.this, 1, "File tidak ditemukan!");
                snackBarCustomAction(findViewById(R.id.rootLayout), 0, "File tidak ditemukan!", 1);
            }
        }
    });
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

    @Override
    public void onBackPressed() {
        finish();
    }

    public class PenumpangAdapter extends RecyclerView.Adapter<PenumpangAdapter.ViewHolder> {
        Context context;
        LayoutInflater inflater;
        @NonNull
        List<String> data = new ArrayList<>();

        public PenumpangAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public PenumpangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View viewList = inflater.inflate(R.layout.list_item_penumpang, parent, false);
//            RelativeLayout viewList = (RelativeLayout)View.inflate(context,R.layout.list_item_penumpang, parent);
//            ViewGroup.LayoutParams params = new RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
//            PenumpangAdapter itemView = new PenumpangAdapter(parent.getContext());
//            itemView.setLayoutParams(params);
            return new PenumpangAdapter.ViewHolder(viewList);
        }

        @Override
        public void onBindViewHolder(@NonNull final PenumpangAdapter.ViewHolder holder, final int position) {
            StringTokenizer st = new StringTokenizer(data.get(position), "|||");
            holder.tvNama.setText(st.nextToken());
            holder.tvStatus.setText(st.nextToken());
            holder.tvNo.setText(String.valueOf(position + 1));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        @Override
        public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView tvNama, tvStatus, tvNo;


            public ViewHolder(@NonNull View view) {
                super(view);

                tvNo = view.findViewById(R.id.tvNo);
                tvNama = view.findViewById(R.id.tvNama);
                tvStatus = view.findViewById(R.id.tvStatus);
            }
        }
    }

    BluetoothDevice bDevice;
    // Initialize da new BroadcastReceiver mInstance
    @NonNull
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, @NonNull Intent intent) {
            // Get the received random number
            boolean receivedNumber = intent.getBooleanExtra("finish", false);
            boolean receivedSocket = intent.getBooleanExtra("socket", false);
            String deviceName = intent.getStringExtra("device_name" );
            if (receivedNumber == true) {
//                appCompatButtonCetak.setFocusableInTouchMode(true);
                btnCetak.setEnabled(true);
                btnCetak.setClickable(true);
                btnCetak.setText(R.string.cetak_struk);
            }else
            if (receivedSocket == false) {
                snackBarCustomAction(findViewById(R.id.bottom_toolbar), 0, "Tidak dapat terhubung ke printer bluetooth " + deviceName + "\n Pastikan printer bluetooth " + deviceName + "anda telah menyala", WARNING);
                bDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                IntentFilter filter = new IntentFilter();

//        String action = "android.bleutooth.device.action.UUID";
                filter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);


                registerReceiver(mReceiver, filter);
//                appCompatButtonCetak.setFocusableInTouchMode(true);
                btnCetak.setEnabled(true);
                btnCetak.setClickable(true);
                btnCetak.setText(R.string.cetak_struk);
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

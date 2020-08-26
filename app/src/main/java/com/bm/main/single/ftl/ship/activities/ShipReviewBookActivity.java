package com.bm.main.single.ftl.ship.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;



import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.scm.R;
import com.bm.main.scm.SBFApplication;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bm.main.single.ftl.ship.constants.ShipKeyPreference;
import com.bm.main.single.ftl.ship.constants.ShipPath;
import com.bm.main.single.ftl.ship.models.ShipFareModel;
import com.bm.main.single.ftl.ship.models.ShipModel;
import com.bm.main.single.ftl.utils.RequestUtilsTravel;
import com.bm.main.single.ftl.utils.utilBand;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ShipReviewBookActivity extends BaseActivity implements ProgressResponseCallback {
    public Context mIntstance;
    private static final String TAG = ShipReviewBookActivity.class.getSimpleName();
    int jml_penumpang_pembayaran;
    //    String bookBalance;
    String comision;
    //    String extraFee;
    String codeBooking;
    String idTransaksi;
//    String alasan, id;
//    TextView namaKapal;
//    TextView jamBerangkat;
//    TextView tglBerangkat;
//    TextView stBerangkat;
//    TextView jamTiba;
//    TextView tglTiba;
//    TextView stTiba;

    TextView textShipName, textShipKelas, textOrigin, textDestination, textTimeDept, textTimeArr, textDateDept, textDateArr;

    TextView textViewTotalHarga, textViewKomisi;//, textViewBiayaAdmin, textViewHargaTiket, textViewDiscChannel;

    AppCompatButton btn_bayar;
    //    String sJson;
    String nominalAdmin;
    String normalSales;
    //    String discount;
    private boolean isBookShip;

    TextView textViewPassagers;
    TextView textViewPassagersSeat;
    private Context context;
    private String paymentCode;
    private int totalHarga=0;
//    private String shipFare;
//    private String ship;
//    ShipFareModel shipFareModel = null;
//    ShipModel shipModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ship_review_book_activity);
        mIntstance = this;
        context = this;
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pembayaran");

        init(1);

//        Intent intent = getIntent();
//        if (intent != null)
//            shipFare = intent.getStringExtra("shipFare");
//        // Log.d(TAG, "onCreate: shipFare "+shipFare);
//        ship = intent.getStringExtra("ship");
//        // Log.d(TAG, "onCreate: ship "+ship);




        jml_penumpang_pembayaran = PreferenceClass.getInt(ShipKeyPreference.shipTotalPenumpang, 1);
        Log.d(TAG, "onCreate: jmlpenumpang pembayaran" + jml_penumpang_pembayaran);


        textViewPassagers = findViewById(R.id.textViewNamaPenumpang);
        textViewPassagersSeat = findViewById(R.id.textViewSeatPenumpang);
        textShipName = findViewById(R.id.textViewNamaKapalReviewBook);
        textShipKelas = findViewById(R.id.textViewKelasReviewBook);
        textOrigin = findViewById(R.id.textOriginReviewBook);
        textDestination = findViewById(R.id.textDestinationReviewBook);
        textTimeDept = findViewById(R.id.textDepartureTimeReviewBook);
        textTimeArr = findViewById(R.id.textArrivalTimeReviewBook);
        textDateDept = findViewById(R.id.textDepartureDateReviewBook);
        textDateArr = findViewById(R.id.textArrivalDateReviewBook);


        textViewTotalHarga = findViewById(R.id.textViewTotalHargaReviewBookShip);
        textViewKomisi = findViewById(R.id.textViewKomisiReviewBookShip);


//        textViewBiayaAdmin = findViewById(R.id.textViewBiayaAdminShip);
//        textViewHargaTiket = findViewById(R.id.textViewNormalSalesShip);
//        textViewDiscChannel = findViewById(R.id.textViewDiscountShip);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // cancelBooking();
                onBackPressed();
            }
        });


        btn_bayar = findViewById(R.id.btn_bayar);
//        btn_bayar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                lakukanPembayaran(v);
//            }
//        });

        btn_bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case DialogInterface.BUTTON_POSITIVE:
                                lakukanPembayaran();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                //SavePref.getInstance(getApplicationContext()).saveString("pricefrombook",data.getString("nominal"));
                //SavePref.getInstance(getApplicationContext()).saveString("bookcode",data.getString("bookingCode"));
                AlertDialog.Builder builder = new AlertDialog.Builder(ShipReviewBookActivity.this);
                builder.setMessage("Anda akan melakukan pembayaran PELNI kode pembayaran " + paymentCode + " dengan total pembayaran senilai "
//                        + utilBand.rupiahFormate(Integer.parseInt(PreferenceClass.getString(FlightKeyPreference.pricefrombook, ""))) + " ?")
                        +utilBand.formatRupiah(totalHarga) + " ?")
                        .setTitle("Konfirmasi")
                        .setPositiveButton("Ya", dialogClickListener)
                        .setNegativeButton("Tidak", dialogClickListener)
                        .show();
            }
        });

        getDataBooking();
    }

    @NonNull
    public synchronized ShipReviewBookActivity getIntanceShipPay() {
        return (ShipReviewBookActivity) mIntstance;
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
//        cancelBooking();
        infoBook();
    }

    private void infoBook(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == DialogInterface.BUTTON_POSITIVE) {
                    Intent intentPelni = new Intent();
                    intentPelni.setAction(TravelActionCode.MENU_PELNI);
                    setResult(RESULT_OK, intentPelni);
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        };
        //SavePref.getInstance(getApplicationContext()).saveString("pricefrombook",data.getString("nominal"));
        //SavePref.getInstance(getApplicationContext()).saveString("bookcode",data.getString("bookingCode"));
        AlertDialog.Builder builder = new AlertDialog.Builder(ShipReviewBookActivity.this);
        builder.setMessage(Html.fromHtml(String.format(getString(R.string.content_cancek_book_ship), paymentCode)))
                .setTitle("Informasi").setCancelable(false)
                .setPositiveButton("OK", dialogClickListener)

                .show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TravelActionCode.IS_FROM_PAY && resultCode == RESULT_OK) {
            //   Intent intent = new Intent(FlightReviewBookActivity.this, FlightSearchActivity.class);
            if (data != null && data.getAction() != null) {

                Log.d(TAG, "onActivityResult: " + data.getAction());
                switch (data.getAction()) {
                    case TravelActionCode.MENU_TRAVEL:
                        Intent intentTravel = new Intent();

                        intentTravel.setAction(TravelActionCode.MENU_TRAVEL);
                        setResult(RESULT_OK, intentTravel);
                        break;
                    case TravelActionCode.MENU_PESAWAT:
                        Intent intentPesawat = new Intent();
                        intentPesawat.setAction(TravelActionCode.MENU_PESAWAT);
                        setResult(RESULT_OK, intentPesawat);
                        break;
                    case TravelActionCode.MENU_KERETA:
                        Intent intentKereta = new Intent();
                        intentKereta.setAction(TravelActionCode.MENU_KERETA);
                        setResult(RESULT_OK, intentKereta);
                        break;
                    case TravelActionCode.MENU_PELNI:
                        Intent intentPelni = new Intent();
                        intentPelni.setAction(TravelActionCode.MENU_PELNI);
                        setResult(RESULT_OK, intentPelni);
                        break;
                }

            } else {
                setResult(RESULT_OK);
            }

            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }


    public void lakukanPembayaran() {

        JSONObject jsonPayment = new JSONObject();
        try {
            jsonPayment.put("transactionId", transactionId);
            jsonPayment.put("paymentCode", paymentCode);
            jsonPayment.put("simulateSuccess", false);
            jsonPayment.put("token", PreferenceClass.getToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "lakukanPembayaran: " + jsonPayment.toString());
        RequestUtilsTravel.transportWithProgressResponseBook_Pay(this, ShipPath.PAYMENT, jsonPayment, TravelActionCode.PAYMENT, this);
        ViewGroup parent = findViewById(R.id.contentHost);
        final FrameLayout view = (FrameLayout)View.inflate(this,R.layout.loading_bar_full_dialog, parent);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText("Mohon Tunggu, Permintaan Pembayaran Ke PELNI sedang di proses");
        openProgressBarDialog(ShipReviewBookActivity.this, view);


//        Intent intent = new Intent(ShipReviewBookActivity.this, ShipStatusPayActivity.class);
//
//        startActivityForResult(intent, TravelActionCode.IS_FROM_PAY);


    }

    @Override
    public void onSuccess(final int actionCode, @NonNull final JSONObject response) {
        Log.d(TAG, "responrc: " + response.toString());
        closeProgressBarDialog();
        try {
            if (response.getString("rc").equals(ResponseCode.SUCCESS)) {

                if (actionCode == TravelActionCode.PAYMENT) {
                    JSONObject data = response.getJSONObject("data");
                    String idTrx = data.getString("transaction_id");
                    String urlStruk = data.getString("url_struk");
                    String urlEtiket = data.getString("url_etiket");
                     Intent intent = new Intent(ShipReviewBookActivity.this, ShipStatusPayActivity.class);
//                    Intent intent = new Intent();

                    intent.putExtra("idTrx", idTrx);
                    intent.putExtra("urlStruk", urlStruk);
                    intent.putExtra("urlEtiket", urlEtiket);
//                            intent.putExtra("textViewKomisi",textKomisi.getText());
                    intent.putExtra("komisi", utilBand.formatRupiah(Integer.parseInt(comision)).replace(",00", ""));
                    startActivityForResult(intent, TravelActionCode.IS_FROM_PAY);


                }

            }else if(response.getString("rc").equals("06") && response.getString("rd").startsWith("SALDO")){


                new_popup_alert_session_deposit(this, "Informasi", response.getString("rd"));
            } else {
                // showToastCustom(ShipReviewBookActivity.this, 2, response.getString("rd"));
                snackBarCustomAction(findViewById(R.id.rootLayout), 0, response.getString("rd"), 2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {
        closeProgressBarDialog();
    }

    @Override
    public void onUpdate(int actionCode,long bytesRead, long totalSize, boolean done) {

    }

    @NonNull
    static String shipSeat = "";
    static String bookingCode = "", transactionId = "";

    public void getDataBooking() {
        final JSONObject response = PreferenceClass.getJSONObject(ShipKeyPreference.bookResult);
        Log.d(TAG, "getDataBooking: " + response.toString());
//        closeProgressBarDialog();


//        try {
//            if (response.getString("rc").equals("00")) {

        // Log.d(TAG, "getDataBooking: " + response.toString());

        try {
            JSONObject dataBooking = response.getJSONObject("data");

            codeBooking = dataBooking.getString("bookingCode");
            paymentCode = dataBooking.getString("paymentCode");
            idTransaksi = dataBooking.getString("transactionId");

            //  JSONArray seats = dataBooking.getJSONArray("seats");

            Log.d(TAG, "getDataBooking: id trx book " + idTransaksi);
            normalSales = dataBooking.getString("normalSales").equals("") ? "0" : dataBooking.getString("normalSales");
            //  discount = dataBooking.getString("discount").equals("") ? "0" : dataBooking.getString("discount");
            //  extraFee = dataBooking.getString("extraFee").equals("") ? "0" : dataBooking.getString("extraFee");
            nominalAdmin = dataBooking.getString("nominal_admin").equals("") ? "0" : dataBooking.getString("nominal_admin");
            //  bookBalance = dataBooking.getString("bookBalance").equals("") ? "0" : dataBooking.getString("bookBalance");
           // comision = dataBooking.getString("komisi").equals("") ? "0" : dataBooking.getString("komisi");
            comision = dataBooking.optString("komisi","0");//.equals("") ? "0" : dataBooking.getString("komisi");

//                JSONObject jsonShipFare = new JSONObject(PreferenceClass.getJSONObject(ShipKeyPreference.shipFare));
//                JSONObject jsonShip = new JSONObject(PreferenceClass.getJSONObject(ShipKeyPreference.ship));
            ShipFareModel shipFareModel = new ShipFareModel(PreferenceClass.getJSONObject(ShipKeyPreference.shipFare));
            ShipModel   shipModel = new ShipModel(PreferenceClass.getJSONObject(ShipKeyPreference.ship));


            String kelas = shipFareModel.getCLASS();
            String subKelas = shipFareModel.getSUBCLASS();

            String shipName = shipModel.getSHIP_NAME();
            String shipNumber = shipModel.getSHIP_NO();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", SBFApplication.config.locale);
            SimpleDateFormat odf = new SimpleDateFormat("EEEE,dd MMMM yyyy", SBFApplication.config.locale);

            String arvTime = shipModel.getARV_TIME();
            String arvDate = "";//=dataSchedule.getARV_DATE();
            //  viewHolder.textViewArvTime.setText(arvTime.substring(0, 2) + ":" + arvTime.substring(2,4));

            String depTime = shipModel.getDEP_TIME();
            String depDate = "";// = dataSchedule.getDEP_DATE();
//        viewHolder.textViewDepTime.setText(depTime.substring(0, 2) + ":" + depTime.substring(2,4));
//        Log.d(TAG, "refreshHeader: "+depDate+" "+ depTime);

            try {
                arvDate = odf.format(sdf.parse(shipModel.getARV_DATE()));
                depDate = odf.format(sdf.parse(shipModel.getDEP_DATE()));

            } catch (ParseException e) {
                e.printStackTrace();
            }

            int biayaAdmin = Integer.parseInt(nominalAdmin) * jml_penumpang_pembayaran;
//            int diskon = Integer.parseInt(discount);
             totalHarga = (Integer.parseInt(normalSales) + biayaAdmin);


//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", config.locale);
            // SimpleDateFormat sdf = new SimpleDateFormat("EEEE,dd MMM yyyy", config.locale);
//            textViewHargaTiket.setText(utilBand.formatRupiah(Integer.parseInt(normalSales)).replace(",00", ""));
            textViewKomisi.setText(utilBand.formatRupiah(Integer.parseInt(comision)).replace(",00", ""));
//            textViewBiayaAdmin.setText(utilBand.formatRupiah(biayaAdmin).replace(",00", ""));
//            textViewDiscChannel.setText(utilBand.formatRupiah(diskon).replace(",00", ""));
            textViewTotalHarga.setText(utilBand.formatRupiah(totalHarga).replace(",00", ""));
//            textShipName.setText(PreferenceClass.getString(ShipKeyPreference.shipName, "") + " " + PreferenceClass.getString(ShipKeyPreference.classes, ""));
//            jamBerangkat.setText(PreferenceClass.getString(ShipKeyPreference.departureTime, ""));
//            tglBerangkat.setText(sdf.format(dateDept));
//            stBerangkat.setText(PreferenceClass.getString(ShipKeyPreference.pelabuhanNamaAsal, "") + " (" + PreferenceClass.getString(ShipKeyPreference.stationKodeAsal, "") + ")");
//            jamTiba.setText(PreferenceClass.getString(ShipKeyPreference.arrivalTime, ""));
//            tglTiba.setText(sdf.format(dateArr));
//            stTiba.setText(PreferenceClass.getString(ShipKeyPreference.pelabuhanNamaTujuan, "") + " (" + PreferenceClass.getString(ShipKeyPreference.stationKodeTujuan, "") + ")");

            textOrigin.setText(PreferenceClass.getString(ShipKeyPreference.pelabuhanNamaAsal, ""));
            textDestination.setText(PreferenceClass.getString(ShipKeyPreference.pelabuhanNamaTujuan, ""));

            textShipName.setText(shipName + " (" + shipNumber + ")");
            textShipKelas.setText(kelas + "/ " + subKelas);

            textTimeDept.setText(depTime.substring(0, 2) + ":" + depTime.substring(2, 4));
            textTimeArr.setText(arvTime.substring(0, 2) + ":" + arvTime.substring(2, 4));
            textDateDept.setText(depDate);
            textDateArr.setText(arvDate);


            JSONArray penumpangarrayAdult = PreferenceClass.getJSONArray(ShipKeyPreference.shipPassengerAdult);
            ArrayList<String> passagers = new ArrayList<>();
            if (penumpangarrayAdult.length() > 0) {

                for (int i = 0; i < penumpangarrayAdult.length(); i++) {


                    passagers.add(penumpangarrayAdult.getJSONObject(i).getString("name"));

                }
            }


            JSONArray penumpangarrayInfant = PreferenceClass.getJSONArray(ShipKeyPreference.shipPassengerInfant);
            //   ArrayList<String> passagers = new ArrayList<>();
            if (penumpangarrayInfant.length() > 0) {
                for (int i = 0; i < penumpangarrayInfant.length(); i++) {


                    passagers.add(penumpangarrayInfant.getJSONObject(i).getString("name"));

                }
            }


            textViewPassagers.setText(passagers.toString().replace("[", "").replace("]", "").replace(", ", "\n"));


//            String[] arr = new String[penumpangarray.length()];
//            Log.d(TAG, "panjangarraypenumpang: " + penumpangarray.length());

            JSONArray seatArray = dataBooking.getJSONArray("seats");

            shipSeat = seatArray.toString();
            bookingCode = codeBooking;
            transactionId = idTransaksi;

            ArrayList<String> passagersSeat = new ArrayList<>();
            for (int i = 0; i < seatArray.length(); i++) {//1
                JSONArray arrSeat = seatArray.optJSONArray(i);
//
                String seatJObj1 = arrSeat.optString(0).equals("N/A")?"N/A":arrSeat.optString(0)+"/";
                String seatJObj2 = arrSeat.optString(1).equals("N/A")?"":arrSeat.optString(1)+"/";
                String seatJObj3 = arrSeat.optString(2).equals("N/A")?"":arrSeat.optString(2);
//                String kolomKursi = arrSeat.optString(3);
//                if ((!seatJObj1.isEmpty() || !seatJObj1.equals("N/A")) && (!seatJObj2.isEmpty() || !seatJObj2.equals("N/A")) && (!seatJObj3.isEmpty() || !seatJObj3.equals("N/A"))) {
                //
//                 if(penumpangarrayAdult.length()>0){
//                passagersSeat.add("No. Kabin : " + seatJObj1 + "/" + seatJObj2 + "-" + seatJObj3);
                passagersSeat.add("No. Kabin : " + seatJObj1+ seatJObj2+seatJObj3);
                //
            }

            textViewPassagersSeat.setText(passagersSeat.toString().replace("[", "").replace("]", "").replace(", ", "\n"));


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

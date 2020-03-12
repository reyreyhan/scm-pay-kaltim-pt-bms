package com.bm.main.single.ftl.train.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;



import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.materialedittext.MaterialEditText;
import com.bm.main.pos.R;
import com.bm.main.pos.SBFApplication;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bm.main.single.ftl.train.constants.TrainKeyPreference;
import com.bm.main.single.ftl.train.constants.TrainPath;
import com.bm.main.single.ftl.utils.RequestUtilsTravel;
import com.bm.main.single.ftl.utils.utilBand;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TrainReviewBookActivity extends BaseActivity implements ProgressResponseCallback {

    // public  TrainReviewBookActivity mIntstance;
    public Context mIntstance;
    private static final String TAG = TrainReviewBookActivity.class.getSimpleName();
    int jml_penumpang_pembayaran;
    String bookBalance;
    String comision;
    String extraFee;
    String codeBooking;
    String timeLimit;
    String idTransaksi;
    String alasan, id;
    TextView namaKereta;
    TextView jamBerangkat;
    TextView tglBerangkat;
    TextView stBerangkat;
    TextView jamTiba;
    TextView tglTiba;
    TextView stTiba;
    TextView textViewTotalHarga, textViewKomisi, textViewBiayaAdmin, textViewHargaTiket, textViewDiscChannel;

    AppCompatButton BtnGantiKursi, btn_bayar;
    //    String sJson;
    String nominalAdmin;
    String normalSales;
    String discount;
    private boolean isBookTrain;

    TextView textViewPassagers;
    TextView textViewPassagersSeat;
    private Context context;
    private int totalHarga=0;

    TextView textBookingCode, textLimit;

    //    JSONObject response = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_review_book_activity);
        mIntstance = this;
        context = this;
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pembayaran");

        init(1);


        jml_penumpang_pembayaran = PreferenceClass.getInt(TrainKeyPreference.jmlpenumpang, 1);
        Log.d(TAG, "onCreate: jmlpenumpang pembayaran" + jml_penumpang_pembayaran);



        textBookingCode = findViewById(R.id.textKodeBookTrain);
        textLimit = findViewById(R.id.textBookLimitTrain);

        textViewPassagers = findViewById(R.id.textViewNamaPenumpang);
        textViewPassagersSeat = findViewById(R.id.textViewSeatPenumpang);
        namaKereta = findViewById(R.id.namaKereta_pembayaran);
        jamBerangkat = findViewById(R.id.textDepartureTimeReviewBook);
        tglBerangkat = findViewById(R.id.textDepartureDateReviewBook);
        stBerangkat = findViewById(R.id.textOriginReviewBook);
        jamTiba = findViewById(R.id.textArrivalTimeReviewBook);
        tglTiba = findViewById(R.id.textArrivalDateReviewBook);
        stTiba = findViewById(R.id.textDestinationReviewBook);


        textViewTotalHarga = findViewById(R.id.textViewTotalHargaTrain);
        textViewKomisi = findViewById(R.id.textViewKomisiTrain);
        textViewBiayaAdmin = findViewById(R.id.textViewBiayaAdminTrain);
        textViewHargaTiket = findViewById(R.id.textViewNormalSalesTrain);
        textViewDiscChannel = findViewById(R.id.textViewDiscountTrain);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // cancelBooking();
                onBackPressed();
            }
        });


        BtnGantiKursi = findViewById(R.id.btnGantiKursi);
        BtnGantiKursi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gantiKursi(v);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(TrainReviewBookActivity.this);
                builder.setMessage("Anda akan melakukan pembayaran KAI kode booking " + codeBooking + " dengan total pembayaran senilai "
//                        + utilBand.rupiahFormate(Integer.parseInt(PreferenceClass.getString(FlightKeyPreference.pricefrombook, ""))) + " ?")
                        +utilBand.formatRupiah(totalHarga) + " ?")
                        .setTitle("Konfirmasi")
                        .setPositiveButton("Ya", dialogClickListener)
                        .setNegativeButton("Tidak", dialogClickListener)
                        .show();
            }
        });
         getDataBooking();

        // attachKeyboardListeners();
    }

    @NonNull
    public synchronized TrainReviewBookActivity getIntanceTrainPay() {
        return (TrainReviewBookActivity) mIntstance;
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
        promptDialogCancel();
    }


    public void gantiKursi(View v) {
        Intent gantikursi = new Intent(TrainReviewBookActivity.this, TrainChangeSeatActivity.class);

        gantikursi.putExtra("trainSeat", trainSeat);
        gantikursi.putExtra("bookingCode", bookingCode);
        gantikursi.putExtra("transactionID", transactionId);
        startActivityForResult(gantikursi, TravelActionCode.GANTIKURSI);
    }

    public void lakukanPembayaran() {

        JSONObject jsonPayment = new JSONObject();

        try {
            jsonPayment.put("productCode", "TKAI");
            jsonPayment.put("bookingCode", codeBooking);
            jsonPayment.put("transactionId", idTransaksi);
            jsonPayment.put("nominal", Integer.parseInt(normalSales));
            jsonPayment.put("nominal_admin", Integer.parseInt(nominalAdmin));
            jsonPayment.put("pay_type", "TUNAI");
            //jsonPayment.put("extraFee", Integer.parseInt(extraFee));
            jsonPayment.put("discount", Integer.parseInt(discount));
            jsonPayment.put("simulateSuccess", false);// true simulator : false real
            jsonPayment.put("token", PreferenceClass.getToken());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "lakukanPembayaran: " + jsonPayment.toString());
        RequestUtilsTravel.transportWithProgressResponseBook_Pay(this, TrainPath.PAYMENT,jsonPayment, TravelActionCode.PAYMENT,this);
        ViewGroup parent = findViewById(R.id.contentHost);
        final FrameLayout view = (FrameLayout)View.inflate(this,R.layout.loading_bar_full_dialog, parent);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText("Mohon Tunggu, Permintaan Pembayaran Ke KAI sedang di proses");
        openProgressBarDialog(TrainReviewBookActivity.this, view);



//        Intent intent = new Intent(TrainReviewBookActivity.this, TrainStatusPayActivity.class);
//
//        startActivityForResult(intent, TravelActionCode.IS_FROM_PAY);


    }

//    public void cancelBooking() {
//
//
//        LayoutInflater layoutInflater = LayoutInflater.from(this);
//        View promptView = layoutInflater.inflate(R.layout.train_user_input_dialog_box, null);
//        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
//        alert.setView(promptView);
//        final TextInputEditText input = promptView.findViewById(R.id.userInputDialog);
//        input.requestFocus();
//        input.setTextColor(Color.BLACK);
//        final InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        alert.setPositiveButton("IYA", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                alasan = input.getText().toString().trim();
//                // Do something with value!
//                if (alasan.equals("")) {
//                    //Toast.makeText(TrainReviewBookActivity.this, "Mohon Memberikan Alasan", Toast.LENGTH_SHORT).show();
//                    //showToastCustom(TrainReviewBookActivity.this, 2,"Mohon memberikan alasan");
//                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rootLayout),
//                            "Mohon memberikan alasan", Snackbar.LENGTH_LONG);
//
//                    mySnackbar.show();
//
//                } else {
//                    if (inputManager != null) {
//                        if (input.isFocusable() == true) {
//                            inputManager.hideSoftInputFromWindow(input.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
//                        }
//                    }
//                    //   Log.d(TAG, "closeKeyboard: " + inputManager);
//
////                    JSONObject cancelbooking = new JSONObject();
////                    try {
////                        cancelbooking.put("productCode", "TKAI");
////                        cancelbooking.put("bookingCode", codeBooking);
////                        cancelbooking.put("transactionId", idTransaksi);
////                        cancelbooking.put("reason", alasan);
////                        cancelbooking.put("token", PreferenceClass.getToken());
////
////
////                    } catch (JSONException e) {
////                        e.printStackTrace();
////                    }
////                    RequestUtilsTravel.transportWithProgressResponse(TrainReviewBookActivity.this,TrainPath.CANCEL, cancelbooking, TravelActionCode.CANCELBOOKING,TrainReviewBookActivity.this);
////                    Log.d(TAG, "onClick: " + cancelbooking);
//
//                    setResult(Activity.RESULT_OK);
//
//                    finish();
//                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                }
//
//            }
//
//        });
//
//        alert.setNegativeButton("TIDAK",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        if (inputManager != null) {
//                            if (input.isFocusable() == true) {
//                                inputManager.hideSoftInputFromWindow(input.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
//                            }
//                        }
//                    }
//                });
//        AlertDialog alert1 = alert.create();
//        alert1.show();
//
////Intent intent=new Intent();
////intent.setAction(TravelActionCode.MENU_KERETA);
//
//
//    }


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
                    Intent intent = new Intent(TrainReviewBookActivity.this, TrainStatusPayActivity.class);
//                    Intent intent = new Intent();

                    intent.putExtra("idTrx", idTrx);
                    intent.putExtra("urlStruk", urlStruk);
                    intent.putExtra("urlEtiket", urlEtiket);
//                            intent.putExtra("textViewKomisi",textKomisi.getText());
                    intent.putExtra("komisi", textViewKomisi.getText());
                    startActivityForResult(intent, TravelActionCode.IS_FROM_PAY);


                } else if (actionCode == TravelActionCode.CANCELBOOKING) {

//                    Intent i = new Intent(TrainReviewBookActivity.this, TrainSearchActivity.class);
//                    startActivity(i);
//                    ActivityCompat.finishAffinity(TrainReviewBookActivity.this);


                    Log.d(TAG, "Batal: " + response.toString());
//                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.rootLayout),
//                            "Pesanan anda telah berhasil dibatalkan", Snackbar.LENGTH_LONG);
//                    mySnackbar.show();
                    // showToastCustom(TrainReviewBookActivity.this, 3,"Pembatalan Berhasil");

                    setResult(Activity.RESULT_OK);

                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }

            }else if(response.getString("rc").equals("06") && response.getString("rd").startsWith("SALDO")){


                new_popup_alert_session_deposit(this, "Informasi", response.getString("rd"));
            } else {
                // showToastCustom(TrainReviewBookActivity.this, 2, response.getString("rd"));
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

    static String trainSeat = "";
    static String bookingCode = "", transactionId = "";

    public void getDataBooking() {
        final JSONObject response = PreferenceClass.getJSONObject(TrainKeyPreference.bookResult);
        Log.d(TAG, "getDataBooking: " + response.toString());
//        closeProgressBarDialog();


//        try {
//            if (response.getString("rc").equals("00")) {

        Log.d(TAG, "getDataBooking: " + response.toString());

        try {
            JSONObject dataBooking = response.getJSONObject("data");

            codeBooking = dataBooking.getString("bookingCode");
            timeLimit = dataBooking.getString("timeLimit");

            idTransaksi = dataBooking.getString("transactionId");
            Log.d(TAG, "getDataBooking: id trx book " + idTransaksi);
            normalSales = dataBooking.getString("normalSales").equals("") ? "0" : dataBooking.getString("normalSales");
            discount = dataBooking.getString("discount").equals("") ? "0" : dataBooking.getString("discount");
            extraFee = dataBooking.getString("extraFee").equals("") ? "0" : dataBooking.getString("extraFee");
            nominalAdmin = dataBooking.getString("nominalAdmin").equals("") ? "0" : dataBooking.getString("nominalAdmin");
            bookBalance = dataBooking.getString("bookBalance").equals("") ? "0" : dataBooking.getString("bookBalance");
            comision = dataBooking.getString("komisi").equals("") ? "0" : dataBooking.getString("komisi");


            SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy", SBFApplication.config.locale);
            Date dateDept = null;
            try {
                dateDept = fmt.parse(PreferenceClass.getString(TrainKeyPreference.depDateTrain, ""));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date dateArr = null;
            try {
                dateArr = fmt.parse(PreferenceClass.getString(TrainKeyPreference.arrDateTrain, ""));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            int biayaAdmin = Integer.parseInt(nominalAdmin);
            int diskon = Integer.parseInt(discount);
             totalHarga = (Integer.parseInt(normalSales) + biayaAdmin) + diskon;


//            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", config.locale);
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE,dd MMM yyyy", SBFApplication.config.locale);
            textViewHargaTiket.setText(utilBand.formatRupiah(Integer.parseInt(normalSales)).replace(",00", ""));
            textViewKomisi.setText(utilBand.formatRupiah(Integer.parseInt(comision)).replace(",00", ""));
            textViewBiayaAdmin.setText(utilBand.formatRupiah(biayaAdmin).replace(",00", ""));
            textViewDiscChannel.setText(utilBand.formatRupiah(diskon).replace(",00", ""));
            textViewTotalHarga.setText(utilBand.formatRupiah(totalHarga).replace(",00", ""));
            namaKereta.setText(PreferenceClass.getString(TrainKeyPreference.trainName, "") + " " + PreferenceClass.getString(TrainKeyPreference.classes, ""));
            jamBerangkat.setText(PreferenceClass.getString(TrainKeyPreference.departureTime, ""));
            tglBerangkat.setText(sdf.format(dateDept));
            stBerangkat.setText(PreferenceClass.getString(TrainKeyPreference.stationNamaAsal, "") + " (" + PreferenceClass.getString(TrainKeyPreference.stationKodeAsal, "") + ")");
            jamTiba.setText(PreferenceClass.getString(TrainKeyPreference.arrivalTime, ""));
            tglTiba.setText(sdf.format(dateArr));
            stTiba.setText(PreferenceClass.getString(TrainKeyPreference.stationNamaTujuan, "") + " (" + PreferenceClass.getString(TrainKeyPreference.stationKodeTujuan, "") + ")");


            textBookingCode.setText(codeBooking);
            textLimit.setText(timeLimit);


            JSONArray penumpangarray = dataBooking.getJSONArray("passengers");
            ArrayList<String> passagers = new ArrayList<>();
            for (int i = 0; i < penumpangarray.length(); i++) {


                passagers.add(penumpangarray.getString(i));

            }
            textViewPassagers.setText(passagers.toString().replace("[", "").replace("]", "").replace(", ", "\n"));


            String[] arr = new String[penumpangarray.length()];
            Log.d(TAG, "panjangarraypenumpang: " + penumpangarray.length());

            JSONArray seatArray = dataBooking.getJSONArray("seats");

            trainSeat = seatArray.toString();
            bookingCode = codeBooking;
            transactionId = idTransaksi;

            ArrayList<String> passagersSeat = new ArrayList<>();
            for (int i = 0; i < seatArray.length(); i++) {//1
                JSONArray arrSeat = seatArray.optJSONArray(i);

                String kelas = arrSeat.optString(0);
                String gerbong = arrSeat.optString(1);
                String barisKursi = arrSeat.optString(2);
                String kolomKursi = arrSeat.optString(3);
                if (!kelas.isEmpty() && !gerbong.isEmpty() && !barisKursi.isEmpty() && !kolomKursi.isEmpty()) {

                    passagersSeat.add(kelas + " " + gerbong + " /kursi " + barisKursi + kolomKursi);
                }

            }

            textViewPassagersSeat.setText(passagersSeat.toString().replace("[", "").replace("]", "").replace(", ", "\n"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

//            } else {
//               // showToastCustom(TrainReviewBookActivity.this, 2, response.getString("rd").toString());
//                snackBarCustomAction(findViewById(R.id.rootLayout), 0, response.getString("rd"), 2);
//                finish();
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: " + requestCode + " " + resultCode + " " + data + " ");
        if (requestCode == TravelActionCode.GANTIKURSI && resultCode == RESULT_OK) {

            String result = data.getStringExtra("result");
            idTransaksi = data.getStringExtra("id_trx_ganti_kurisi");
            Log.d(TAG, "onActivityResult: id trx ganti " + idTransaksi);
            try {
                JSONArray seatArray = new JSONArray(result);
//
                trainSeat = result;
                ArrayList<String> passagersSeat = new ArrayList<>();
                for (int i = 0; i < seatArray.length(); i++) {//1
                    JSONArray arrSeat = seatArray.optJSONArray(i);

                    // for (int j = 0; j < arrSeat.length(); j++) {
                    String kelas = arrSeat.optString(0);
                    String gerbong = arrSeat.optString(1);
                    String barisKursi = arrSeat.optString(2);
                    String kolomKursi = arrSeat.optString(3);
                    if (!kelas.isEmpty() && !gerbong.isEmpty() && !barisKursi.isEmpty() && !kolomKursi.isEmpty()) {

                        passagersSeat.add(kelas + " " + gerbong + " /kursi " + barisKursi + kolomKursi);
                    }

                }

                textViewPassagersSeat.setText(passagersSeat.toString().replace("[", "").replace("]", "").replace(", ", "\n"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (requestCode == TravelActionCode.IS_FROM_PAY && resultCode == RESULT_OK) {

            //   Intent intent = new Intent(FlightReviewBookActivity.this, FlightSearchActivity.class);
            if (data != null && data.getAction() != null) {
                Intent intent = new Intent();
                Log.d(TAG, "onActivityResult: " + data.getAction());
                switch (data.getAction()) {
                    case TravelActionCode.MENU_TRAVEL:

                        intent.setAction(TravelActionCode.MENU_TRAVEL);
                        break;
                    case TravelActionCode.MENU_PESAWAT:

                        intent.setAction(TravelActionCode.MENU_PESAWAT);
                        break;
                    case TravelActionCode.MENU_KERETA:

                        intent.setAction(TravelActionCode.MENU_KERETA);
                        break;
                    case TravelActionCode.MENU_PELNI:

                        intent.setAction(TravelActionCode.MENU_PELNI);
                        break;
                }
                setResult(RESULT_OK, intent);
            } else {
                setResult(RESULT_OK);
            }

            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }

    }

//    public void getFailureBook() {
//        closeProgressBarDialog();
////        showToastCustom(TrainReviewBookActivity.this, 1, "Ada Masalah dengan server, silahkan coba kembali");
//        snackBarCustomAction(findViewById(R.id.rootLayout), 0,"Ada Masalah dengan server, silahkan coba kembali", 1);
//    }

    public void promptDialogCancel() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(TrainReviewBookActivity.this);

        ViewGroup parent = findViewById(R.id.contentHost);
        final View dialogView = View.inflate(this, R.layout.promptdialog_custom_view_cancelbook, parent);

        // Specify alert dialog is not cancelable/not ignorable
        builder.setCancelable(false);

        // Set the custom layout as alert dialog view
        builder.setView(dialogView);

        final CardView cardViewCancelBookAwal=dialogView.findViewById(R.id.cardViewCancelBookAwal);
        final CardView cardViewCancelBookNanti=dialogView.findViewById(R.id.cardViewCancelBookNanti);
//        final CardView cardViewCancelBook=dialogView.findViewById(R.id.cardViewCancelBook);
        // Get the custom alert dialog view widgets reference

        final TextView editTextContentNanti=dialogView.findViewById(R.id.editTextContentNanti);

        final AppCompatButton dialog_nanti_btn = dialogView.findViewById(R.id.dialog_nanti_btn);
        final AppCompatButton dialog_ya_btn = dialogView.findViewById(R.id.dialog_ya_btn);
        final AppCompatButton dialog_tidak_btn = dialogView.findViewById(R.id.dialog_tidak_btn);
        final AppCompatButton dialog_ok_btn = dialogView.findViewById(R.id.dialog_ok_btn);

//        final AppCompatButton dialog_kirim_btn = dialogView.findViewById(R.id.dialog_kirim_btn);
//        AppCompatButton dialog_batal_btn = dialogView.findViewById(R.id.dialog_batal_btn);
        final MaterialEditText editTexAlasan = dialogView.findViewById(R.id.editTextAlasan);


        // Create the alert dialog
        final AlertDialog dialog = builder.create();
        //  final MaterialEditText finalEt_name = et_name;
        // Set positive/yes button click listener

        dialog_ya_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dismiss the alert dialog
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputManager != null) {
                    if (editTexAlasan.isFocusable() == true) {
                        inputManager.hideSoftInputFromWindow(editTexAlasan.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    }
                }


                alasan = editTexAlasan.getText().toString();

                if (editTexAlasan.getText().length() == 0) {
                    editTexAlasan.setError("Mohon memberikan alasan");
                } else {

                    dialog.dismiss();

                    requestCancelBooking();
                }


            }
        });

//        dialog_ya_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cardViewCancelBook.setVisibility(View.GONE);
//                cardViewCancelBookAwal.setVisibility(View.GONE);
//            }
//        });

        // Set negative/no button click listener
//        dialog_batal_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Dismiss/cancel the alert dialog
//                //dialog.cancel();
//                //  closeKeyboardDialog(AkunSayaActivity.this,dialogView);
//
//                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                if (inputManager != null) {
//                    if (editTexAlasan.isFocusable() == true) {
//                        inputManager.hideSoftInputFromWindow(editTexAlasan.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
//                    }
//                }
////                if (finalEt_name.getText().length() == 0) {
////                    finalEt_name.setError("Tidak Boleh Kosong");
////                } else {
//                //dialog.dismiss();
//                cardViewCancelBook.setVisibility(View.GONE);
//                cardViewCancelBookAwal.setVisibility(View.VISIBLE);
//
////                }
//
//                //  Toast.makeText(getApplication(),
//                //          "No button clicked", Toast.LENGTH_SHORT).show();
//            }
//        });


        dialog_tidak_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog_nanti_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardViewCancelBookAwal.setVisibility(View.GONE);
                cardViewCancelBookNanti.setVisibility(View.VISIBLE);
                editTextContentNanti.setText(Html.fromHtml(String.format(getString(R.string.content_cancek_book), bookingCode)));


            }
        });

        dialog_ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                setResult(Activity.RESULT_OK);

                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
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

    private void requestCancelBooking() {
        JSONObject cancelbooking = new JSONObject();
        try {
            cancelbooking.put("productCode", "TKAI");
            cancelbooking.put("bookingCode", codeBooking);
            cancelbooking.put("transactionId", idTransaksi);
            cancelbooking.put("reason", alasan);
            cancelbooking.put("token", PreferenceClass.getToken());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtilsTravel.transportWithProgressResponse(TrainReviewBookActivity.this, TrainPath.CANCEL, cancelbooking, TravelActionCode.CANCELBOOKING, TrainReviewBookActivity.this);
        ViewGroup parent = findViewById(R.id.contentHost);
        final FrameLayout view = (FrameLayout)View.inflate(this,R.layout.loading_bar_full_dialog, parent);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText("Mohon Tunggu, Permintaan Pembatalan Ke KAI sedang di proses");
        openProgressBarDialog(TrainReviewBookActivity.this, view);
    }
}

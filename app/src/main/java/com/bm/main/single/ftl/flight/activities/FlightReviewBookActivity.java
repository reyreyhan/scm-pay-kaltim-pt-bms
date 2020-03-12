package com.bm.main.single.ftl.flight.activities;

//import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.widget.AppCompatButton;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;



import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.templates.SmartTextView;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.pos.R;
import com.bm.main.pos.SBFApplication;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bm.main.single.ftl.constants.TravelPath;
import com.bm.main.single.ftl.flight.constants.FlightKeyPreference;
import com.bm.main.single.ftl.flight.constants.FlightPath;
import com.bm.main.single.ftl.flight.models.FlightDataModelClasses;
import com.bm.main.single.ftl.flight.models.FlightListBaggageModel;
import com.bm.main.single.ftl.utils.FormatUtil;
import com.bm.main.single.ftl.utils.MemoryStore;
import com.bm.main.single.ftl.utils.RequestUtilsTravel;
import com.bm.main.single.ftl.utils.utilBand;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FlightReviewBookActivity extends BaseActivity implements ProgressResponseCallback, View.OnClickListener {
    private static final String TAG = FlightReviewBookActivity.class.getSimpleName();
    @Nullable
    String nominal="0";
    @Nullable
    String passengers;
    @Nullable
    String details;
    @Nullable
    String bookingCode;
    @Nullable
    String paymentCode;
    @Nullable
    String flightCode1;
    @Nullable
    String departureTime1;
    @Nullable
    String arrivalTime1;
    @Nullable
    String reservationDate;
    @Nullable
    String timeLimit;
    @Nullable
    String flightInfoGo;
    @Nullable
    String comission;
    @NonNull
    String komisi="0";
    @Nullable
    String nominalAdmin="0";
    @Nullable
    String transactionId;

    AppCompatButton btnPay;
    TextView textBookingCode, textLimit, textTimeDept, textTimeArr, textDateDep, textDateArr, textOrigin, textDestination, textTotal, textKomisi;
    TextView textAirLine;
    int countAdult;
    int countChild;
    int countInfant;

    TextView textPassagerNameAdult;//, textAdult2, textAdult3, textAdult4, textAdult5, textAdult6, textAdult7;
    TextView textPassagerNameChild;//, textChild2, textChild3, textChild4, textChild5, textChild6;
    TextView textPassagerNameInfant;//, textInfant2, textInfant3, textInfant4;
    @Nullable
    ArrayList passengerList;
    @Nullable
    HashMap adultPassangerList;
    @Nullable
    HashMap childPassangerList;
    @Nullable
    HashMap infantPassangerList;
    // PasswordEditText textPin;
    TextView textViewHargaTiket;
    TextView textViewBiayaAdmin;
    TextView textDetailTransit;
    FlightListBaggageModel flightListBaggageModel;

//    HashMap adultBaggageList;
//    HashMap childBaggageList;
@Nullable
Typeface typefaceRoboto;
    @Nullable
    private FlightDataModelClasses dataDetail;
    RelativeLayout relInfoBagasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_review_book_activity);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pembayaran");
        init(1);
        Bundle b = getIntent().getExtras();
        if (b != null)
            passengers = b.getString("passengers");

            details = b.getString("details");
            bookingCode = b.getString("bookingCode");
            paymentCode = b.getString("paymentCode");
            flightCode1 = b.getString("flightCode1");
            departureTime1 = b.getString("departureTime1");
            arrivalTime1 = b.getString("arrivalTime1");
            reservationDate = b.getString("reservationDate");
            timeLimit = b.getString("timeLimit");
            flightInfoGo = b.getString("flightInfoGo");
            nominal = b.getString("nominal");
            comission = b.getString("comission");
            komisi = b.getString("komisi").length()>0 ?   b.getString("komisi"):"0";
            nominalAdmin = b.getString("nominalAdmin");
            transactionId = b.getString("transactionId");
            passengerList = b.getStringArrayList("passengerList");
            adultPassangerList = (HashMap) b.getSerializable("adultPassangerList");
            childPassangerList = (HashMap) b.getSerializable("childPassangerList");
            infantPassangerList = (HashMap) b.getSerializable("infantPassangerList");



        typefaceRoboto = ResourcesCompat.getFont(this, R.font.roboto);
        dataDetail = (FlightDataModelClasses) MemoryStore.get("dataOnClick");
        new Thread(new Runnable() {
            @Override
            public void run() {
                request_list_baggage();
            }
        }).start();


        countAdult = PreferenceClass.getInt(FlightKeyPreference.countAdultFlight, 1);
        countChild = PreferenceClass.getInt(FlightKeyPreference.countChildFlight, 0);
        countInfant = PreferenceClass.getInt(FlightKeyPreference.countInfantFlight, 0);
        textBookingCode = findViewById(R.id.textKodeBookFlight);
        textLimit = findViewById(R.id.textBookLimitFlight);
        textAirLine = findViewById(R.id.textAirlineName);
        textTimeDept = findViewById(R.id.textTimeDeptPay);
        textTimeArr = findViewById(R.id.textTimeArrPay);
        textDateDep = findViewById(R.id.textDateDeptPay);
        textDateArr = findViewById(R.id.textDateArrPay);
        textOrigin = findViewById(R.id.textOriginReview);
        textDestination = findViewById(R.id.textDestinationReview);
        textTotal = findViewById(R.id.textTotalPay);
        textKomisi = findViewById(R.id.textKomisiPay);
        textViewHargaTiket = findViewById(R.id.textHargaTiketPay);
        textViewBiayaAdmin = findViewById(R.id.textBiayaAdminPay);
        textPassagerNameAdult = findViewById(R.id.textPenumpangDewasa);
        textPassagerNameChild = findViewById(R.id.textPenumpangAnak);
        textPassagerNameInfant = findViewById(R.id.textPenumpangBayi);
        relInfoBagasi = findViewById(R.id.relInfoBagasi);
//        textPin =  findViewById(R.id.pin);
        btnPay = findViewById(R.id.btn_pay_flight);
//        MaterialRippleLayout.on(btnPay).rippleOverlay(true)
//                .rippleAlpha(0.2f)
//                .rippleColor(R.color.md_orange_200)
//                .rippleHover(true)
//                .create();

        textBookingCode.setText(bookingCode);
        textLimit.setText(timeLimit);
        textAirLine.setText(PreferenceClass.getString(FlightKeyPreference.airlineName, "") + " " + PreferenceClass.getString(FlightKeyPreference.flightCode, ""));
        textTimeDept.setText(departureTime1);
        textTimeArr.setText(arrivalTime1);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        Date dateDept = null;
        try {
            dateDept = fmt.parse(PreferenceClass.getString(FlightKeyPreference.departureDate, ""));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date dateArr = null;
        try {
            dateArr = fmt.parse(PreferenceClass.getString(FlightKeyPreference.arrivalDate, ""));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE,dd MMM yyyy", SBFApplication.config.locale);
        String deptDate = sdf.format(dateDept);
        String arrDate = sdf.format(dateArr);
        textDateDep.setText(deptDate);
        textDateArr.setText(arrDate);
        textDetailTransit = findViewById(R.id.textDetailTransitReview);
        textDetailTransit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(FlightReviewBookActivity.this, FlightDetailActivity.class);
                startActivity(intent1);
            }
        });
        textOrigin.setText(PreferenceClass.getString(FlightKeyPreference.airportNamaAsal, ""));
        textDestination.setText(PreferenceClass.getString(FlightKeyPreference.airportNamaTujuan, ""));
        final int totalHarga = Integer.parseInt(nominal) + Integer.parseInt(nominalAdmin);
        textTotal.setText(utilBand.formatRupiah(totalHarga));
        textKomisi.setText(utilBand.formatRupiah(Integer.parseInt(komisi)));
        textViewHargaTiket.setText(utilBand.formatRupiah(Integer.parseInt(nominal)));
        textViewBiayaAdmin.setText(utilBand.formatRupiah(Integer.parseInt(nominalAdmin)));
        Map<String, String> sortedAdult = FormatUtil.sortByKeys(adultPassangerList);
        ArrayList<String> namaDewasa = new ArrayList<>();
        for (String key : sortedAdult.keySet()) {
            String value = sortedAdult.get(key);
            namaDewasa.add(value);
        }

        textPassagerNameAdult.setText(namaDewasa.toString().replace("[", "").replace("]", "").replace(", ", "\n"));
        if (childPassangerList.size() > 0) {
            textPassagerNameChild.setVisibility(View.VISIBLE);
            Map<String, String> sortedChild = FormatUtil.sortByKeys(childPassangerList);
            ArrayList<String> namaAnak = new ArrayList<>();
            for (String key : sortedChild.keySet()) {
                String value = sortedChild.get(key);
                namaAnak.add(value);
                //  Toast.makeText(ctx, "Key: "+key+" Value: "+value, Toast.LENGTH_LONG).show();
            }
            textPassagerNameChild.setText(namaAnak.toString().replace("[", "").replace("]", "").replace(", ", "\n"));

        }
        if (infantPassangerList.size() > 0) {
            textPassagerNameInfant.setVisibility(View.VISIBLE);
            Map<String, String> sortedInfant = FormatUtil.sortByKeys(infantPassangerList);
            ArrayList<String> namaBayi = new ArrayList<>();
            for (String key : sortedInfant.keySet()) {
                String value = sortedInfant.get(key);
                namaBayi.add(value);
            }
            textPassagerNameInfant.setText(namaBayi.toString().replace("[", "").replace("]", "").replace(", ", "\n"));
        }



        //btnPay.setOnClickListener(this);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case DialogInterface.BUTTON_POSITIVE:
                                click_pay_flight();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                //SavePref.getInstance(getApplicationContext()).saveString("pricefrombook",data.getString("nominal"));
                //SavePref.getInstance(getApplicationContext()).saveString("bookcode",data.getString("bookingCode"));
                AlertDialog.Builder builder = new AlertDialog.Builder(FlightReviewBookActivity.this);
                builder.setMessage("Anda akan melakukan pembayaran "+PreferenceClass.getString(FlightKeyPreference.airlineName, "") + " " + PreferenceClass.getString(FlightKeyPreference.flightCode, "")+" kode booking " + PreferenceClass.getString(FlightKeyPreference.bookcode, "")
                        + " dengan rincian pembayaran \n Harga Tiket   : "+utilBand.formatRupiah(Integer.parseInt(nominal))
                        +" \n Setting Admin : "+utilBand.formatRupiah(Integer.parseInt(nominalAdmin))
                        + " \n Total Harga   : "+utilBand.formatRupiah(totalHarga) + " ?")
//                        + utilBand.rupiahFormate(Integer.parseInt(PreferenceClass.getString(FlightKeyPreference.pricefrombook, ""))) + " ?")

                       // +utilBand.formatRupiah(totalHarga) + " ?")
                        .setTitle("Konfirmasi")
                        .setPositiveButton("Ya", dialogClickListener)
                        .setNegativeButton("Tidak", dialogClickListener)
                        .show();
            }
        });

    }


   // Html.fromHtml(String.format(getString(R.string.content_cancek_book), bookingCode))

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rumah, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_right_drawer) {
            openTopDialog(true);
        } else if (id == android.R.id.home) {
            onBackPressed();

        }
        return super.onOptionsItemSelected(item);
    }

//    public void closeTopSheet(View view) {
//        dialog.dismiss();
//    }


    public void onBackPressed() {
//        Intent intent = new Intent(FlightReviewBookActivity.this, FlightSearchActivity.class);
//        intent.putExtra("isFromPay", true);
//        startActivity(intent);
//        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        //ActivityCompat.finishAffinity(FlightReviewBookActivity.this);
//        Intent i = new Intent();
//        i.putExtra(TravelKeyPreference.IS_FROM_PAY, true);

       // Intent intent = new Intent(FlightReviewBookActivity.this, FlightSearchActivity.class);

        infoBook();
    }

    private void infoBook(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == DialogInterface.BUTTON_POSITIVE) {
                    setResult(RESULT_OK);
                    finish();
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        };
        //SavePref.getInstance(getApplicationContext()).saveString("pricefrombook",data.getString("nominal"));
        //SavePref.getInstance(getApplicationContext()).saveString("bookcode",data.getString("bookingCode"));
        AlertDialog.Builder builder = new AlertDialog.Builder(FlightReviewBookActivity.this);
        builder.setMessage(Html.fromHtml(String.format(getString(R.string.content_cancek_book), bookingCode)))
                .setTitle("Informasi").setCancelable(false)
                .setPositiveButton("OK", dialogClickListener)

                .show();
    }



    private void request_list_baggage() {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("id_transaksi", transactionId);
            jsonObject.put("cid", PreferenceClass.getString(TravelActionCode.CID,"0"));

            jsonObject.put("token", PreferenceClass.getToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Log.d(TAG, "REQUEST payFlight: " + jsonObject);
        RequestUtilsTravel.transportWithProgressResponse(this, TravelPath.LIST_BAGGAGE, jsonObject, TravelActionCode.LIST_BAGGAGE, this);
    }

    public void click_pay_flight() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("airline", PreferenceClass.getString(FlightKeyPreference.airlineCode, ""));
            jsonObject.put("transactionId", transactionId);
            jsonObject.put("bookingCode", bookingCode);
            jsonObject.put("paymentCode", paymentCode);
            jsonObject.put("simulateSuccess", false);// true pay simulator : false real
            jsonObject.put("cid", PreferenceClass.getString(TravelActionCode.CID,"0"));
            jsonObject.put("token", PreferenceClass.getToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Log.d(TAG, "REQUEST payFlight: " + jsonObject);
        RequestUtilsTravel.transportWithProgressResponseBook_Pay(this, FlightPath.PAYMENT, jsonObject, TravelActionCode.PAYMENT, this);
        ViewGroup parent = findViewById(R.id.contentHost);
        final FrameLayout view = (FrameLayout)View.inflate(this,R.layout.loading_bar_full_dialog, parent);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText("Mohon Tunggu, Permintaan Pembayaran sedang di proses");
        openProgressBarDialog(FlightReviewBookActivity.this, view);
    }



    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        Log.d(TAG, "onSuccess: " + response.toString());

        if (actionCode == TravelActionCode.LIST_BAGGAGE) {
            flightListBaggageModel = gson.fromJson(response.toString(), FlightListBaggageModel.class);
            if (flightListBaggageModel.getRc().equals(ResponseCode.SUCCESS)) {
                LinearLayout mLinDetail = findViewById(R.id.linDetail);
                mLinDetail.removeAllViews();
                System.out.println("===> "+flightListBaggageModel.getData().length);
//                for (int j = 0; j < dataDetail.getDetailTitle().length(); j++) {
                int x=0;
                boolean isVisible=false;
                    for (int i = 0; i < flightListBaggageModel.getData().length; i++) {


                      //  int z=x;
                        LinearLayout childLayoutPengantar = new LinearLayout(this);
                        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        linearParams.setMargins(0, 10, 0, 10);
                        childLayoutPengantar.setLayoutParams(linearParams);
                        childLayoutPengantar.setWeightSum(2);


                        TextView contentTextView = new TextView(this);
                        contentTextView.setVisibility(View.GONE);
                        SmartTextView contentDetailTextView = new SmartTextView(this);
                        contentDetailTextView.setVisibility(View.GONE);
                        String[] baggageKeys = flightListBaggageModel.getData()[i].getBaggage_key().split("\\|");
                        String weight = flightListBaggageModel.getData()[i].getWeight() + " Kg";
                        String price = utilBand.formatRupiah(flightListBaggageModel.getData()[i].getPrice());
                        String info = baggageKeys[1] + " " + weight + " " + price;
                        String[] route=baggageKeys[1].split("-");
                        String asal=route[0];
                        String tujuan=route[1];
                       // if(dataDetail.getDetailTitle().length()==1) {
                        if(asal.equals(PreferenceClass.getString(FlightKeyPreference.airportKodeAsal, ""))){
                            isVisible=true;
                            contentTextView.setVisibility(View.VISIBLE);
                            contentDetailTextView.setVisibility(View.VISIBLE);
                            x=x+1;
                            System.out.println("masuk "+x);
                           // x=+1;
                           // x=+1;
                           // z=x;
                           // for (int j = i; j < i-route[0].length(); j++) {
                                contentTextView.setText(x+".");
                          //  }

                            contentTextView.setTextSize(14);
                            contentTextView.setTextColor(ContextCompat.getColor(this, R.color.md_black_1000));
                            contentTextView.setLineSpacing(0, 1.5f);
                            contentTextView.setGravity(Gravity.START | Gravity.CENTER);
                            contentTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
                            contentTextView.setTextColor(ContextCompat.getColor(this, R.color.md_black_1000));



                            contentDetailTextView.setText(info);
                            contentDetailTextView.setTextSize(14);
                            contentDetailTextView.setLineSpacing(0, 1.5f);
                            contentDetailTextView.setGravity(Gravity.START);
                            contentDetailTextView.setTextColor(ContextCompat.getColor(this, R.color.md_black_1000));
                            contentDetailTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
                            contentDetailTextView.setJustified(true);

                            contentTextView.setLayoutParams(new TableLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT, 1.9f));
                            contentDetailTextView.setLayoutParams(new TableLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT, 0.1f));
                          //  z=-1;
                        }else{
                           // x=+1;
                            contentTextView.setVisibility(View.VISIBLE);
                            contentDetailTextView.setVisibility(View.VISIBLE);


                           // x=x;
//                            contentTextView.setText((z) + ".");
                           if(isVisible){
                               System.out.println("ga masuk "+(x-1));
                               contentTextView.setText("");
                           }else{
                               x=x+1;
                               System.out.println("ga masuk "+(x));
                               contentTextView.setText(x+".");
                           }


                            contentTextView.setTextSize(14);
                            contentTextView.setTextColor(ContextCompat.getColor(this, R.color.md_black_1000));
                            contentTextView.setLineSpacing(0, 1.5f);
                            contentTextView.setGravity(Gravity.START | Gravity.CENTER);
                            contentTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
                            contentTextView.setTextColor(ContextCompat.getColor(this, R.color.md_black_1000));

                            contentDetailTextView.setText("(Transit) "+info);
                            contentDetailTextView.setTextSize(14);
                            contentDetailTextView.setLineSpacing(0, 1.5f);
                            contentDetailTextView.setGravity(Gravity.START);
                            contentDetailTextView.setTextColor(ContextCompat.getColor(this, R.color.md_black_1000));
                            contentDetailTextView.setTypeface(typefaceRoboto, Typeface.NORMAL);
                            contentDetailTextView.setJustified(true);

                            contentTextView.setLayoutParams(new TableLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT, 1.9f));

                            contentDetailTextView.setLayoutParams(new TableLayout.LayoutParams(
                                    LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT, 0.1f));
                          //  z=-1;
                        }






                        //TextJustification.justify(contentDetailTextView);






                        childLayoutPengantar.addView(contentDetailTextView, 0);
                        childLayoutPengantar.addView(contentTextView, 0);
//                linearParams.height=contentDetailTextView.getLineHeight()*2;
                        mLinDetail.addView(childLayoutPengantar);
                    }
//                }

            }else{
                relInfoBagasi.setVisibility(View.GONE);
            }
        } else {
            closeProgressBarDialog();
            try {
                String rc = response.getString("rc");
                final String rd = response.getString("rd");
                if (rc.equals("00")) {
                    JSONObject data = response.getJSONObject("data");
                    String idTrx = data.getString("transaction_id");
                    String urlStruk = data.getString("url_struk");
                    String urlEtiket = data.getString("url_etiket");
                    Intent intent = new Intent(FlightReviewBookActivity.this, FlightStatusPayActivity.class);
                    intent.putExtra("idTrx", idTrx);
                    intent.putExtra("urlStruk", urlStruk);
                    intent.putExtra("urlEtiket", urlEtiket);
                    intent.putExtra("komisi", textKomisi.getText());
                    startActivityForResult(intent, TravelActionCode.IS_FROM_PAY);
//                    finish();
                }else if(rc.equals("06") && rd.startsWith("SALDO")){


                    new_popup_alert_session_deposit(this, "Informasi", rd);

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //   showToastCustom(FlightReviewBookActivity.this, 2, rd);
                            snackBarCustomAction(findViewById(R.id.rootLayout), 0, rd, 2);
                        }
                    });
                }
                Log.d(TAG, "onSuccess: " + response.toString());
            } catch (@NonNull final JSONException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast(e.toString());
                    }
                });
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       if(requestCode==TravelActionCode.IS_FROM_PAY && resultCode==RESULT_OK) {
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

    @Override
    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {
        closeProgressBarDialog();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //  showToastCustom(FlightReviewBookActivity.this, 1, "Ada Masalah Dengan server, Silahkan Coba Kembali");
                snackBarCustomAction(findViewById(R.id.rootLayout), 0, "Ada Masalah Dengan server, Silahkan Coba Kembali", 1);
            }
        });
    }

    @Override
    public void onUpdate(int actionCode,long bytesRead, long totalSize, boolean done) {

    }

    @Override
    public void onClick(@NonNull View v) {
        int id = v.getId();
        if (id == R.id.btn_pay_flight) {
            click_pay_flight();

        }

    }
}

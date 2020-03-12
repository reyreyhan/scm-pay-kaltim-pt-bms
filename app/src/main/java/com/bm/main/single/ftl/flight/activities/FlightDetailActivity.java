package com.bm.main.single.ftl.flight.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;


import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.pos.R;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bm.main.single.ftl.flight.adapters.FlightDetailAdapter;
import com.bm.main.single.ftl.flight.constants.FlightKeyPreference;
import com.bm.main.single.ftl.flight.models.FlightDataModelClasses;
import com.bm.main.single.ftl.flight.models.FlightDetailModel;
import com.bm.main.single.ftl.utils.MemoryStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FlightDetailActivity extends BaseActivity {
    private static final String TAG = FlightDetailActivity.class.getSimpleName();
    RecyclerView recyclerFlightDetail;
    FlightDetailAdapter flightDetailAdapter;
    @NonNull
    ArrayList<FlightDetailModel> data = new ArrayList<>();
    FlightDetailModel flightDetailModel;
    private boolean isFromSchedule = false;
    String seat[];
    AppCompatButton buttonLanjut, buttonTutup;
    String departureTime, arrivalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_detail_activity);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Detail Perjalanan");
        init(1);
        Intent intent = getIntent();
        recyclerFlightDetail = findViewById(R.id.recyclerFlightDetail);
        if (intent != null) {
            isFromSchedule = intent.getBooleanExtra("isFromSchedule", false);
            seat = intent.getStringArrayExtra("seat");
            departureTime = intent.getStringExtra("departureTime");
            arrivalTime = intent.getStringExtra("arrivalTime");
        }


        JSONArray detailTitleArr = PreferenceClass.getJSONArray(FlightKeyPreference.detailTitle);

        int count = detailTitleArr.length();
        boolean isTrasit;
//        String[]transitTimeArr=new String[count];
        isTrasit = count != 1;
        int x = 0;
        for (int i = 0; i < detailTitleArr.length(); i++) {
            flightDetailModel = new FlightDetailModel();
            try {
                JSONObject data = detailTitleArr.getJSONObject(i);



//                switch (data.getString("flightCode").substring(0, 2)) {
//                    case "SL":
//                        Log.d(TAG, "getSchedule: SL");
//                        flightDetailModel.setFlightName("Thai Lion Air");
//                      //  flightDetailModel.setFlightCode("SL");
//                        flightDetailModel.setFlightIcon("http://static.scash.bz/fastravel/asset/maskapai/TPSL.png");
//                        break;
//                    case "OD":
//                        Log.d(TAG, "getSchedule: OD");
//                        flightDetailModel.setFlightName("Malindo Air");
//                     //   flightDetailModel.setFlightCode("OD");
//                        flightDetailModel.setFlightIcon("http://static.scash.bz/fastravel/asset/maskapai/TPOD.png");
//                        break;
//                    case "ID":
//                        flightDetailModel.setFlightName("Batik Air");
//                      //  flightDetailModel.setFlightCode("ID");
//                        flightDetailModel.setFlightIcon("http://static.scash.bz/fastravel/asset/maskapai/TPID.png");
//                        break;
//                    case "IW":
//                      //  flightDetailModel.setFlightCode("IW");
//                        flightDetailModel.setFlightName("Wings Air");
//                        flightDetailModel.setFlightIcon("http://static.scash.bz/fastravel/asset/maskapai/TPIW.png");
//                        break;
//                    case "JT":
//                     //   flightDetailModel.setFlightCode("JT");
//                        flightDetailModel.setFlightName("Lion Air");
//                        flightDetailModel.setFlightIcon("http://static.scash.bz/fastravel/asset/maskapai/TPJT.png");
//                        break;
//                    default:

                        flightDetailModel.setFlightIcon(data.getString("flightIcon"));
                        flightDetailModel.setFlightName(data.getString("flightName"));
                        flightDetailModel.setFlightCode(data.getString("flightCode"));

//                        break;
//                }

                flightDetailModel.setOrigin(data.getString("origin"));
                flightDetailModel.setOriginName(data.getString("originName"));
                flightDetailModel.setDestination(data.getString("destination"));
                flightDetailModel.setDestinationName(data.getString("destinationName"));
                flightDetailModel.setDurationDetail(data.getString("durationDetail"));
                flightDetailModel.setTransitTime(data.getString("transitTime"));

                flightDetailModel.setArrival(data.getString("arrival"));
                flightDetailModel.setDepart(data.getString("depart"));
                // flightDetailModel.setTransit(isTrasit);
                if (isTrasit == true) {
                    if (x < count - 1) {
                        flightDetailModel.setInitTransit("Transit");
                     //   Log.d(TAG, "onCreate: "+data.getString("flightCode"));
//                        flightDetailModel.setTransitTime(transitTimeArr[i+1]);
                    } else {
                        flightDetailModel.setInitTransit("Tiba");

                    }
                } else {
                    flightDetailModel.setInitTransit("Tiba");
                }

                //   Log.d(TAG, "onCreate: istransit "+isTrasit);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            x++;
            data.add(flightDetailModel);

            // Log.d(TAG, "onCreate detail title: "+detailTitleArr.get);
        }

        buttonLanjut = findViewById(R.id.buttonLanjut);

        buttonLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPrice();
            }
        });
        buttonTutup = findViewById(R.id.buttonTutup);
        buttonTutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        flightDetailAdapter = new FlightDetailAdapter(this, data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerFlightDetail.setHasFixedSize(false);
        recyclerFlightDetail.setLayoutManager(linearLayoutManager);
        recyclerFlightDetail.setAdapter(flightDetailAdapter);
        if (isFromSchedule) {
            buttonLanjut.setVisibility(View.VISIBLE);
        }
    }

    private void checkPrice() {
        FlightDataModelClasses data = (FlightDataModelClasses) MemoryStore.get("dataOnClick");
        ;
        JSONObject jsonObject = new JSONObject();

        JSONArray jsonArray = new JSONArray();
        for (String a : seat) {
            jsonArray.put(a);
        }
        PreferenceClass.putString(FlightKeyPreference.airlineCode, data.getAirlineCode());
        PreferenceClass.putJSONArray(FlightKeyPreference.seat_flights, jsonArray);
        PreferenceClass.putString(FlightKeyPreference.airlineName, data.getAirlineName());
        PreferenceClass.putString(FlightKeyPreference.flightCode, data.getFlightCode());
        PreferenceClass.putString(FlightKeyPreference.departureDate, data.getDepartureDate());
        PreferenceClass.putString(FlightKeyPreference.arrivalDate, data.getArrivalDate());
        PreferenceClass.putString(FlightKeyPreference.departureTime, departureTime);
        PreferenceClass.putString(FlightKeyPreference.arrivalTime, arrivalTime);
        PreferenceClass.putInt(FlightKeyPreference.OneWayPriceSchedule, data.getPrice());
        PreferenceClass.putBoolean(FlightKeyPreference.isTransit, data.isTransit());
        PreferenceClass.putInt(FlightKeyPreference.isInternational, data.getIsInternational());
        PreferenceClass.putJSONArray(FlightKeyPreference.detailTitle, data.getDetailTitle());
        try {
            jsonObject.put("airline", data.getAirlineCode());
            jsonObject.put("departure", PreferenceClass.getString(FlightKeyPreference.airportKodeAsal, ""));
            jsonObject.put("arrival", PreferenceClass.getString(FlightKeyPreference.airportKodeTujuan, ""));
            jsonObject.put("departureDate", PreferenceClass.getString(FlightKeyPreference.departureDateFlight, ""));
            jsonObject.put("returnDate", PreferenceClass.getString(FlightKeyPreference.returnDateFlight, ""));
            jsonObject.put("adult", PreferenceClass.getInt(FlightKeyPreference.countAdultFlight, 1));
            jsonObject.put("child", PreferenceClass.getInt(FlightKeyPreference.countChildFlight, 0));
            jsonObject.put("infant", PreferenceClass.getInt(FlightKeyPreference.countInfantFlight, 0));
            jsonObject.put("seats", jsonArray);
            jsonObject.put("cid", PreferenceClass.getString(TravelActionCode.CID,"0"));
            jsonObject.put("token", PreferenceClass.getToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject jsonObjectBg = new JSONObject();
        try {

            jsonObjectBg.put("airline", data.getAirlineCode());
            jsonObjectBg.put("departure", data.getDeparture());
            jsonObjectBg.put("arrival", data.getArrival());
            jsonObjectBg.put("maskapai", "TP" + data.getFlightCode().substring(0, 2));
            jsonObjectBg.put("cid", PreferenceClass.getString(TravelActionCode.CID,"0"));
            jsonObjectBg.put("token", PreferenceClass.getToken());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(FlightDetailActivity.this, FlightBookActivity.class);
        intent.putExtra("reqJsonFare", jsonObject.toString()); //Put your id to your next Intent
        //intent.putExtra("data", String.valueOf(data)); //Put your id to your next Intent
        //  MemoryStore.set("dataOnClick",null);
        // MemoryStore.set("dataOnClick", data);
        intent.putExtra("seat", seat); //Put your id to your next Intent

//        intent.putExtra("kodePenerbangan", "TP"+data.getFlightCode().substring(0,2)); //Put your id to your next Intent
        intent.putExtra("isFare", true); //Put your id to your next Intent
        startActivityForResult(intent, TravelActionCode.IS_FROM_PAY);
        //  finish();
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
//        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == TravelActionCode.IS_FROM_PAY) {

//                if (isStillRunning) {
//                    RequestUtilsTravel.cancelTravel();
//                }
            //  Log.d(TAG, "onActivityResult: "+requestCode);
            //  Intent intent = new Intent(FlightBookActivity.this, FlightSearchActivity.class);


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
                    case TravelActionCode.MENU_PELNI:

                        intent.setAction(TravelActionCode.MENU_PELNI);
                        break;
                }
                setResult(RESULT_OK, intent);
            } else {
                setResult(RESULT_OK);
            }

           onBackPressed();
        }
    }
}
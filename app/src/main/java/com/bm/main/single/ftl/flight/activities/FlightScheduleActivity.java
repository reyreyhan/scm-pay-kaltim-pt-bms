package com.bm.main.single.ftl.flight.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;


import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.ProdukGroup;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.ClickListener;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.listener.RecyclerTouchListener;
import com.bm.main.fpl.templates.AutoScaleTextView;
import com.bm.main.fpl.templates.shimmer.ShimmerFrameLayout;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.shimmer;
import com.bm.main.pos.R;
import com.bm.main.single.ftl.activities.TravelTanggalActivity;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bm.main.single.ftl.flight.adapters.FlightScheduleOneWayAdapter;
import com.bm.main.single.ftl.flight.constants.FlightKeyPreference;
import com.bm.main.single.ftl.flight.constants.FlightPath;
import com.bm.main.single.ftl.flight.models.ConfigFlightModel;
import com.bm.main.single.ftl.flight.models.FlightAirlinesModel;
import com.bm.main.single.ftl.flight.models.FlightAirlinesPriceModel;
import com.bm.main.single.ftl.flight.models.FlightDataModelClasses;
import com.bm.main.single.ftl.flight.models.FlightDetailModel;
import com.bm.main.single.ftl.utils.MemoryStore;
import com.bm.main.single.ftl.utils.RequestUtilsTravel;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.bm.main.fpl.utils.DialogUtils.closeBootomSheetDialog;
import static com.bm.main.fpl.utils.DialogUtils.openBottomSheetDialog;

public class FlightScheduleActivity extends BaseActivity implements FlightScheduleOneWayAdapter.OnClickListener, ProgressResponseCallback, View.OnClickListener {
//public class FlightScheduleActivity extends BaseActivity implements  ProgressResponseCallback, View.OnClickListener {
    private static final String TAG = FlightScheduleActivity.class.getSimpleName();
   // public static FlightScheduleActivity flightScheduleActivityInstance;
    public ProgressBar progressBar;
    private RelativeLayout emptyView;
    private ConfigFlightModel configAirportModel;
    public int checked = 0;
    private ShimmerFrameLayout mShimmerViewContainer;
    RecyclerView recyclerView;
    public FlightScheduleOneWayAdapter adapter;


    @NonNull
    public ArrayList<FlightDataModelClasses> list = new ArrayList<>();
    @NonNull
    public ArrayList<FlightAirlinesModel> listAirlines = new ArrayList<>();
    @NonNull
    public ArrayList<FlightAirlinesPriceModel> listAirlinesPrice = new ArrayList<>();
    //    FlightDataModel flightDataModel;
//    private ArrayList<FlightClassesModel.Classes> listClasses= new ArrayList<>();
    private int countFlight;
    private boolean isFilter;
    private boolean show = false;
    private LinearLayout layout_sort;
    private LinearLayout layout_filter;
    private TextView textViewSort;
    public ImageView image_view_filter_check;
    private ImageView image_view_sort_check;

    private TextView textSorterLowPrice;
    private ImageView imageViewSorterLowPrice;
    private TextView textSorterEarlyDept;
    private ImageView imageViewSorterEarlyDept;
    private TextView textSorterShortDuration;
    private ImageView imageViewSorterShortDuration;
    private AppCompatButton btn_Tutup;
    private AutoScaleTextView textViewAsal, textViewTujuan;
    private AutoScaleTextView textTgl_Pax;
    private int pax;
    private TextView textViewfilter;
    SeekBar seek_barprogressFlightPergi;

    @Nullable
    String asal, tujuan, depDate;
    @SuppressLint("StaticFieldLeak")
    static FlightScheduleActivity flightScheduleActivityInstance;

    public static synchronized FlightScheduleActivity getInstanceFlightSchedule() {
        return flightScheduleActivityInstance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_activity_schedule);
        flightScheduleActivityInstance = this;
        //context = this.getApplicationContext();
//        toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitle("Jadwal Pesawat");
        init(0);
        emptyView = findViewById(R.id.empty_view_flight);
        progressBar = findViewById(R.id.progressFlightPergi);
        seek_barprogressFlightPergi= findViewById(R.id.seek_barprogressFlightPergi);
        image_view_filter_check = findViewById(R.id.image_view_filter_check);
        image_view_filter_check.setVisibility(View.GONE);
        image_view_sort_check = findViewById(R.id.image_view_sort_check);
        image_view_sort_check.setVisibility(View.GONE);
        layout_sort = findViewById(R.id.layout_sort);
        layout_filter = findViewById(R.id.layout_filter);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        shimmer.selectPreset(0, mShimmerViewContainer);

        recyclerView = findViewById(R.id.recylerList);
        Intent intent = this.getIntent();
        if (intent != null) {
            show = intent.getBooleanExtra("cari", false);
            if (show) {
                if (mShimmerViewContainer.getVisibility() == View.GONE) {
                    mShimmerViewContainer.setVisibility(View.VISIBLE);
                    mShimmerViewContainer.startShimmerAnimation();

                }
//                progressBar.setProgress(0);
//                progressBar.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        searchingFlight();
                    }
                });
            }
            //  Log.d(TAG, "onCreate: "+intent.getIntExtra(TravelActionCode.IS_FROM_PAY,0));
        }


        asal = PreferenceClass.getString(FlightKeyPreference.airportNamaAsal, "");
        String arrAsal[] = asal.split("\\(");
        tujuan = PreferenceClass.getString(FlightKeyPreference.airportNamaTujuan, "");
        String arrTujuan[] = tujuan.split("\\(");
        depDate = (String) PreferenceClass.getString(FlightKeyPreference.departureDateShowFlight, "");
        int adult = PreferenceClass.getInt(FlightKeyPreference.countAdultFlight, 0);
        int child = PreferenceClass.getInt(FlightKeyPreference.countChildFlight, 0);
        int infant = PreferenceClass.getInt(FlightKeyPreference.countInfantFlight, 0);
        pax = adult + child + infant;
        toolbar = findViewById(R.id.toolbar);
        init(0);
        textViewAsal = findViewById(R.id.asal);
        textViewAsal.setText(arrAsal[0]);
        textViewTujuan = findViewById(R.id.tujuan);
        textViewTujuan.setText(arrTujuan[0]);
        textTgl_Pax = findViewById(R.id.textViewToolbarSubTitle);
        textTgl_Pax.setText(depDate + " - " + pax + " org");
        textViewfilter = findViewById(R.id.text_view_filter);
        textViewSort = findViewById(R.id.text_view_sort);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new FlightScheduleOneWayAdapter(this, list, this);


//        adapter = new FlightScheduleOneWayAdapter(this, list);
        recyclerView.setAdapter(adapter);


//        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
//                recyclerView, new ClickListener() {
//            @Override
//            public void onClick(final View view,  int position) {
////                Toast.makeText(FlightScheduleActivity.this, "Single Click on Image :"+position,
////                        Toast.LENGTH_SHORT).show();
//                final FlightDataModelClasses data = list.get(position);
//                final RelativeLayout relDetail=view.findViewById(R.id.relDetail);
//                final TextView departureTime=view.findViewById(R.id.departureTime);
//                final TextView arrivalTime=view.findViewById(R.id.arrivalTime);
//                final AppCompatButton btnPrice=view.findViewById(R.id.airlinesCheckPrice);
//
//                final String[] seat = new String[data.getClassArr().length()];
//
//                for (int i = 0; i < data.getClassArr().length(); i++) {
//                    try {
//                        seat[i] = data.getClassArr().getJSONArray(i).getJSONObject(0).getString("seat").toString();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                LinearLayout linMainTouch=view.findViewById(R.id.linMainTouch);
//                relDetail.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //Toast.makeText(FlightScheduleActivity.this, "Single Click on Detail :"+position+" "+data.getFlightCode(),
//                        //       Toast.LENGTH_SHORT).show();
//                        onClickDetail(data.getDetailTitle(),relDetail.getTag().toString(),data, seat, departureTime.getText().toString(), arrivalTime.getText().toString());
//                    }
//                });
//                linMainTouch.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                        Toast.makeText(FlightScheduleActivity.this, "Single Click on ticketView :"+position,
////                                Toast.LENGTH_SHORT).show();
//                        onClickCheckPrice(data, seat,departureTime.getText().toString(), arrivalTime.getText().toString());
//                    }
//                });
//                btnPrice.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                        Toast.makeText(FlightScheduleActivity.this, "Single Click on ticketView :"+position,
////                                Toast.LENGTH_SHORT).show();
//                        onClickCheckPrice(data, seat,departureTime.getText().toString(), arrivalTime.getText().toString());
//                    }
//                });
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));

    }

    private void searchingFlight() {
        logEventFireBase(ProdukGroup.PESAWAT,ProdukGroup.PESAWAT, FirebaseAnalytics.Event.SEARCH,EventParam.EVENT_SUCCESS,TAG);

//        Random r = new Random();
//        int rand = r.nextInt(10000000);
        long cid=System.currentTimeMillis();
        Log.d(TAG, "searchingFlight CID: "+cid);
        PreferenceClass.putString(TravelActionCode.CID,String.valueOf(cid));
        list.clear();
        progressz = 0;
        countFlight = 0;
        isFilter = false;
        // adapter.checked(1);
        checked = 1;
        //listClasses.clear();
//       JSONObject jsonObject= PreferenceClass.getJSONObject(FlightKeyPreference.dataConfigFlight);
//        configAirportModel=jsonObject.
        if (image_view_filter_check.getVisibility() == View.VISIBLE) {
            image_view_filter_check.setVisibility(View.GONE);
        }
        if (image_view_sort_check.getVisibility() == View.VISIBLE) {
            image_view_sort_check.setVisibility(View.GONE);
        }

        boolean isLowestPrice;
        if(PreferenceClass.getString(FlightKeyPreference.searchChoicePrice,"low").equals("low")){
            isLowestPrice=true;
        }else{
            isLowestPrice=false;
        }

        configAirportModel = gson.fromJson(PreferenceClass.getJSONObject(FlightKeyPreference.dataConfigFlight).toString(), ConfigFlightModel.class);
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < configAirportModel.getConfigurations().size(); i++) {
          //  Log.d(TAG, "searchingFlight: "+configAirportModel.getConfigurations().get(i).getAirline()+" "+configAirportModel.getConfigurations().get(i).getIsActive());
            if (configAirportModel.getConfigurations().get(i).getIsActive().equals("1")) {
                try {
                    ConfigFlightModel.Configurations airlinescode = configAirportModel.getConfigurations().get(i);//String("airline");
                    jsonObject.put("airline", airlinescode.getAirline());
//                jsonObject.put("airline","TPJT");
                    jsonObject.put("departure", PreferenceClass.getString(FlightKeyPreference.airportKodeAsal, ""));
                    jsonObject.put("arrival", PreferenceClass.getString(FlightKeyPreference.airportKodeTujuan, ""));
                    jsonObject.put("departureDate", PreferenceClass.getString(FlightKeyPreference.departureDateFlight, ""));
                    jsonObject.put("returnDate", PreferenceClass.getString(FlightKeyPreference.returnDateFlight, ""));
                    jsonObject.put("adult", String.valueOf(PreferenceClass.getInt(FlightKeyPreference.countAdultFlight, 0)));
                    jsonObject.put("child", String.valueOf(PreferenceClass.getInt(FlightKeyPreference.countChildFlight, 0)));
                    jsonObject.put("infant", String.valueOf(PreferenceClass.getInt(FlightKeyPreference.countInfantFlight, 0)));
                    jsonObject.put("isLowestPrice", isLowestPrice);
                    jsonObject.put("token", PreferenceClass.getToken());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "REQUEST searchingFlight: " + jsonObject);
                isStillRunning = true;
                seek_barprogressFlightPergi.setProgress(0);
                seek_barprogressFlightPergi.setVisibility(View.VISIBLE);
//            RequestUtilsTravel.transportWithProgressResponse("flight/search", jsonObject, new ProgressResponseHandler(FlightScheduleActivity.this,this, ActionCode.SCHEDULE_ONEWAY));

                RequestUtilsTravel.transportWithProgressResponse(FlightScheduleActivity.this, FlightPath.SEARCH, jsonObject, TravelActionCode.SEARCH, this);
            }

        }
    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        super.onSuccess(actionCode, response);
        Log.d(TAG, "onSuccess: " + response.toString());


        try {
            if (actionCode == TravelActionCode.SEARCH) {
                if (response.getString("rc").equals(ResponseCode.SUCCESS)) {


                    progressz = progressz + 1;
                    showProgress(progressz);
                    getSchedule(response);


                } else {

                    progressz = progressz + 1;
                    showProgress(progressz);

                }
            } else {
                JSONObject oParent = response.getJSONObject("data");
                Intent intent = null;//new Intent(FlightScheduleActivity.this, BookFlightActivity.class);
                Bundle b = new Bundle();
                b.putString("departureTime", oParent.getString("departureTime"));
                b.putString("arrivalTime", oParent.getString("arrivalTime"));
                b.putString("price", oParent.getString("price"));
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
            }
        } catch (JSONException e) {
            progressz = progressz + 1;
            showProgress(progressz);
        }
        isStillRunning = false;

    }

    @Override
    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {
        super.onFailure(actionCode, responseCode, responseDescription, throwable);

        isStillRunning = false;
        if (actionCode == TravelActionCode.SEARCH) {
            progressz = progressz + 1;
            showProgress(progressz);
        }
    }

    // boolean check;
//    @Override
//    public void onClickCheckPrice(FlightDataModelClasses data, String[] seat, boolean check, String departureTime, String arrivalTime) {

//    }
    @Override
    public void onClickCheckPrice(@NonNull FlightDataModelClasses data, @NonNull String[] seat,boolean check,  String departureTime, String arrivalTime) {
        Log.d(TAG, "onClickCheckPrice: " +data.getIsInternational()+"  "+data);
        //   this.check = check;
        logEventFireBase(ProdukGroup.PESAWAT, clearListFromDuplicateAirlines(listAirlines).get(0).getAirLineNama(), String.valueOf(data.getPrice()),PreferenceClass.getString(FlightKeyPreference.airportKodeAsal, ""),data.getFlightCode(),PreferenceClass.getString(FlightKeyPreference.airportKodeTujuan, "") ,PreferenceClass.getString(FlightKeyPreference.departureDateFlight, ""),String.valueOf(data.isTransit()),EventParam.EVENT_ACTION_REQUEST_FARE, EventParam.EVENT_SUCCESS, TAG);

//        HashMap<String, String> eventMap = new HashMap<>();
//        eventMap.put("upline", materialEditTextIdUplineReg.getEditableText().toString());
//        eventMap.put("is_register", "1");
//        eventMap.put("paket", paketName);
//        eventMap.put("email", materialEditTextEmailReg.getEditableText().toString());
//        eventMap.put("nama", materialEditTextNamaReg.getEditableText().toString());
//        eventMap.put("no_handphone", materialEditTextNoHpReg.getEditableText().toString());
//        eventMap.put("kabupaten", materialEditTextKotaReg.getEditableText().toString());
//        eventMap.put("propinsi", materialEditTextPropinsiReg.getEditableText().toString());
//
//
//        SBFApplication.sendEvent(FirebaseAnalytics.Event.VIEW_ITEM, eventMap);


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
            jsonObjectBg.put("token", PreferenceClass.getToken());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(FlightScheduleActivity.this, FlightBookActivity.class);
        intent.putExtra("reqJsonFare", jsonObject.toString()); //Put your id to your next Intent
        //intent.putExtra("data", String.valueOf(data)); //Put your id to your next Intent
        //  MemoryStore.set("dataOnClick",null);
        MemoryStore.set("dataOnClick", data);
        intent.putExtra("seat", seat); //Put your id to your next Intent

//        intent.putExtra("kodePenerbangan", "TP"+data.getFlightCode().substring(0,2)); //Put your id to your next Intent
        intent.putExtra("isFare", true); //Put your id to your next Intent
        startActivityForResult(intent, TravelActionCode.IS_FROM_PAY);

    }



    @Override
    public void onClickDetail(@NonNull JSONArray detailTitle, String tag, FlightDataModelClasses data, String[] seat, String departureTime, String arrivalTime) {
//        if(!next) {
        MemoryStore.set("dataOnClick", data);

        PreferenceClass.putJSONArray(FlightKeyPreference.detailTitle, detailTitle);
        Intent intent1 = new Intent(FlightScheduleActivity.this, FlightDetailActivity.class);
        intent1.putExtra("isFromSchedule", true);
        intent1.putExtra("seat", seat);
        intent1.putExtra("departureTime", departureTime); //Put your id to your next Intent
        intent1.putExtra("arrivalTime", arrivalTime); //Put your id to your next Intent
        startActivity(intent1);
//        }else{
//           // onClickCheckPrice(data,seat, true, departureTime,  arrivalTime);
//        }
    }


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

    public void onBackPressed() {
        if (isStillRunning) {
            RequestUtilsTravel.cancelTravel();
        }
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @NonNull
    public ArrayList<Integer> priceList = new ArrayList();

    @NonNull
    public ArrayList<FlightDataModelClasses> realList = new ArrayList<>();

    public void getSchedule(@NonNull JSONObject response) {
        try {
            JSONArray flightScheduleJson = response.getJSONArray("data");
            //  Log.d(TAG, "getSchedule: MD5"+md5());
            FlightDataModelClasses flightDataModelClasses;
            FlightAirlinesModel airLinesModel;
            FlightAirlinesPriceModel airLinesPriceModel;
            FlightDetailModel flightDetailModel;
            if (flightScheduleJson.length() > 0) {
                if (mShimmerViewContainer.getVisibility() == View.VISIBLE) {
                    mShimmerViewContainer.setVisibility(View.GONE);
                    mShimmerViewContainer.stopShimmerAnimation();

                }
                if(emptyView.getVisibility()==View.VISIBLE) {
                    emptyView.setVisibility(View.GONE);
                }
                if(recyclerView.getVisibility()==View.GONE) {
                    recyclerView.setVisibility(View.VISIBLE);
                }
//                layout_filter.setFocusableInTouchMode(true);
                layout_filter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openFilter(v);
                    }
                });
//                layout_sort.setFocusableInTouchMode(true);
                layout_sort.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openSorter(v);
                    }
                });
                countFlight += flightScheduleJson.length();

                //Log.d(TAG, "getSchedule: " + countFlight);
                for (int i = 0; i < flightScheduleJson.length(); i++) {
                    JSONObject data = flightScheduleJson.getJSONObject(i);
                    String title = data.getString("title");
                    String arrDuration[] = data.getString("duration").split("j");
                    String jam;
                    String mnt;
                    if (arrDuration[0].length() == 1) {
                        jam = "0" + arrDuration[0];
                    } else {
                        jam = arrDuration[0];
                    }
                    if (arrDuration[1].length() == 2) {
                        mnt = "0" + arrDuration[1];
                    } else {
                        mnt = arrDuration[1];
                    }
                    String duration = jam + "j" + mnt;
                    String dur = jam + mnt.substring(0, 2);
                    int durationInt = Integer.parseInt(dur);
                    String cityTransit = data.getString("cityTransit");
                    boolean isTransit = data.getBoolean("isTransit");
                    String departureDate = data.getString("departureDate");
                    String arrivalDate = data.getString("arrivalDate");
                    String airlineIcon = data.getString("airlineIcon");
                    String airlineName = data.getString("airlineName");
                    String airlineCode = data.getString("airlineCode");
                    JSONArray flightDetailJson = data.getJSONArray("detailTitle");
                    JSONArray flightClassesJson = data.getJSONArray("classes");
                    JSONArray jsonArrayMergeClasses = new JSONArray();
                    flightDataModelClasses = new FlightDataModelClasses();
                    for (int j = 0; j < flightClassesJson.length(); j++) {
                        JSONArray flightSubClasses = flightClassesJson.getJSONArray(j);
                        for (int x = 0; x < flightSubClasses.length(); x++) {
                            jsonArrayMergeClasses.put(flightSubClasses.getJSONObject(x));
                            System.out.println("getSchedule subclasses: " + jsonArrayMergeClasses);
                        }
                    }
//                    int count = flightDetailJson.length();
//                    boolean isTrasit;
//                    isTrasit = count != 1;
//                    int x = 0;
//                    for (int ii = 0; ii < flightDetailJson.length(); ii++) {
//                        flightDetailModel = new FlightDetailModel();
//                        try {
//                          //  JSONObject dataD = flightDetailJson.getJSONObject(ii);
//
//
//
//                            switch (data.getString("flightCode").substring(0, 2)) {
//                                case "SL":
//                                    Log.d(TAG, "getSchedule: SL");
//                                    flightDetailModel.setFlightName("Thai Lion Air");
//                                    flightDetailModel.setFlightCode("SL");
//                                    flightDetailModel.setFlightIcon("http://static.scash.bz/fastravel/asset/maskapai/TPSL.png");
//                                    break;
//                                case "OD":
//                                    Log.d(TAG, "getSchedule: OD");
//                                    flightDetailModel.setFlightName("Malindo Air");
//                                    flightDetailModel.setFlightCode("OD");
//                                    flightDetailModel.setFlightIcon("http://static.scash.bz/fastravel/asset/maskapai/TPOD.png");
//                                    break;
//                                case "ID":
//                                    flightDetailModel.setFlightName("Batik Air");
//                                    flightDetailModel.setFlightCode("ID");
//                                    flightDetailModel.setFlightIcon("http://static.scash.bz/fastravel/asset/maskapai/TPID.png");
//                                    break;
//                                case "IW":
//                                    flightDetailModel.setFlightCode("IW");
//                                    flightDetailModel.setFlightName("Wings Air");
//                                    flightDetailModel.setFlightIcon("http://static.scash.bz/fastravel/asset/maskapai/TPIW.png");
//                                    break;
//                                case "JT":
//                                    flightDetailModel.setFlightCode("JT");
//                                    flightDetailModel.setFlightName("Lion Air");
//                                    flightDetailModel.setFlightIcon("http://static.scash.bz/fastravel/asset/maskapai/TPJT.png");
//                                    break;
//                                default:
//
//                                    flightDetailModel.setFlightIcon(data.getString("flightIcon"));
//                                    flightDetailModel.setFlightName(data.getString("flightName"));
//                                    flightDetailModel.setFlightCode(data.getString("flightCode"));
//
//                                    break;
//                            }
//
//                            flightDetailModel.setOrigin(data.getString("origin"));
//                            flightDetailModel.setOriginName(data.getString("originName"));
//                            flightDetailModel.setDestination(data.getString("destination"));
//                            flightDetailModel.setDestinationName(data.getString("destinationName"));
//                            flightDetailModel.setDurationDetail(data.getString("durationDetail"));
//                            flightDetailModel.setTransitTime(data.getString("transitTime"));
//                            flightDetailModel.setArrival(data.getString("arrival"));
//                            flightDetailModel.setDepart(data.getString("depart"));
//                            // flightDetailModel.setTransit(isTrasit);
//                            if (isTrasit == true) {
//                                if (x < count - 1) {
//                                    flightDetailModel.setInitTransit("Transit");
//                                } else {
//                                    flightDetailModel.setInitTransit("Tiba");
//                                }
//                            } else {
//                                flightDetailModel.setInitTransit("Tiba");
//                            }
//
//                            //   Log.d(TAG, "onCreate: istransit "+isTrasit);
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        x++;
//                      //  dataD.add(flightDetailModel);
//
//                        // Log.d(TAG, "onCreate detail title: "+detailTitleArr.get);
//                    }

                    int price = 0;
                    String flightCode;
                    for (int k = 0; k < jsonArrayMergeClasses.length(); k++) {
                        int avail = jsonArrayMergeClasses.getJSONObject(k).getInt("availability");
                        String seatKey = jsonArrayMergeClasses.getJSONObject(k).getString("seatKey");
                        String seat = jsonArrayMergeClasses.getJSONObject(k).getString("seat");
                        String classes = jsonArrayMergeClasses.getJSONObject(k).getString("class");
                        price = price + jsonArrayMergeClasses.getJSONObject(k).getInt("price");
                        String depTime = jsonArrayMergeClasses.getJSONObject(0).getString("depatureTime");
                        String arrTime = jsonArrayMergeClasses.getJSONObject(jsonArrayMergeClasses.length() - 1).getString("arrivalTime");
                        flightCode = jsonArrayMergeClasses.getJSONObject(k).getString("flightCode");
                        String departure = jsonArrayMergeClasses.getJSONObject(k).getString("departure");
                        String arrival = jsonArrayMergeClasses.getJSONObject(k).getString("arrival");
                        String departureTimeZoneText = jsonArrayMergeClasses.getJSONObject(k).getString("departureTimeZoneText");
                        String departureName = jsonArrayMergeClasses.getJSONObject(k).getString("departureName");
                        String arrivalName = jsonArrayMergeClasses.getJSONObject(k).getString("arrivalName");
                        String arrivalTimeZoneText = jsonArrayMergeClasses.getJSONObject(k).getString("arrivalTimeZoneText");
                        int isInternasional = jsonArrayMergeClasses.getJSONObject(k).getInt("isInternational");
                        Log.d(TAG, "getSchedule: " + flightCode + " " + flightDetailJson);
                        flightDataModelClasses.setDetailTitle(flightDetailJson);
                        flightDataModelClasses.setTitle(title);
                        flightDataModelClasses.setAirlineCode(airlineCode);
                        switch (flightCode.substring(0, 2)) {
                            case "SL":
                                Log.d(TAG, "getSchedule: SL 2");
                                //    flightDataModelClasses.setAirlineCode("TPID");
                                flightDataModelClasses.setAirlineName("Thai Lion Air");
                                flightDataModelClasses.setAirlineIcon("http://static.scash.bz/fastravel/asset/maskapai/TPSL.png");
                                break;
                            case "OD":
                                Log.d(TAG, "getSchedule: OD 2");
                                //    flightDataModelClasses.setAirlineCode("TPID");
                                flightDataModelClasses.setAirlineName("Malindo Air");
                                flightDataModelClasses.setAirlineIcon("http://static.scash.bz/fastravel/asset/maskapai/TPOD.png");
                                break;
                            case "ID":
                                //    flightDataModelClasses.setAirlineCode("TPID");
                                flightDataModelClasses.setAirlineName("Batik Air");
                                flightDataModelClasses.setAirlineIcon("http://static.scash.bz/fastravel/asset/maskapai/TPID.png");
                                break;
                            case "IW":
                                //   flightDataModelClasses.setAirlineCode("TPIW");
                                flightDataModelClasses.setAirlineName("Wings Air");
                                flightDataModelClasses.setAirlineIcon("http://static.scash.bz/fastravel/asset/maskapai/TPIW.png");
                                break;
                            case "JT":
                                //   flightDataModelClasses.setAirlineCode("TPJT");
                                flightDataModelClasses.setAirlineName("Lion Air");
                                flightDataModelClasses.setAirlineIcon("http://static.scash.bz/fastravel/asset/maskapai/TPJT.png");
                                break;
                            default:
                                //
                                flightDataModelClasses.setAirlineName(airlineName);
                                flightDataModelClasses.setAirlineIcon(airlineIcon);
                                break;
                        }
                        flightDataModelClasses.setIsTransit(isTransit);
                        flightDataModelClasses.setIsInternational(isInternasional);
                        flightDataModelClasses.setCityTransit(cityTransit);
                        flightDataModelClasses.setAvailability(avail);
                        flightDataModelClasses.setSeatKey(seatKey);
                        flightDataModelClasses.setClasseses(classes);
                        flightDataModelClasses.setPrice(price);
                        airLinesModel = new FlightAirlinesModel();
                        airLinesPriceModel = new FlightAirlinesPriceModel();
                        airLinesPriceModel.setAirLinesCodeModel(airlineCode);
                        if (price > 0) {
                            priceList.add(price);
                            flightDataModelClasses.setPriceTemp(price);
                            airLinesPriceModel.setAirLinesPriceModel(price);
                            airLinesModel.setAirLinePrice(price);
                        } else {
                            flightDataModelClasses.setPriceTemp(999999999);
                            airLinesPriceModel.setAirLinesPriceModel(999999999);
                            airLinesModel.setAirLinePrice(999999999);
                        }
                        listAirlinesPrice.add(airLinesPriceModel);
                        flightDataModelClasses.setDepartureTime(depTime);
                        flightDataModelClasses.setArrivalTime(arrTime);
                        flightDataModelClasses.setFlightCode(flightCode);
                        flightDataModelClasses.setDeparture(departure);
                        flightDataModelClasses.setDepartureName(departureName);
                        flightDataModelClasses.setDepartureDate(departureDate);
                        flightDataModelClasses.setArrival(arrival);
                        flightDataModelClasses.setArrivalName(arrivalName);
                        flightDataModelClasses.setArrivalDate(arrivalDate);
                        flightDataModelClasses.setDepartureTimeZoneText(departureTimeZoneText);
                        flightDataModelClasses.setArrivalTimeZoneText(arrivalTimeZoneText);
                        flightDataModelClasses.setDuration(duration);
                        flightDataModelClasses.setDurationInt(durationInt);
                        flightDataModelClasses.setSeat(seat);
                        flightDataModelClasses.setClassArr(flightClassesJson);
//                        airLinesModel.setAirLineCode(airlineCode);
                        //  airLinesModel.setAirLineCode(flightDataModelClasses.getAirlineCode());
//                        airLinesModel.setAirLineNama(airlineName);
                        // airLinesModel.setAirLineNama(flightDataModelClasses.getAirlineName());
//                        airLinesModel.setAirLineIcon(airlineIcon);
                        //  airLinesModel.setAirLineIcon(flightDataModelClasses.getAirlineIcon());

                        switch (flightCode.substring(0, 2)) {
                            case "SL":
                                Log.d(TAG, "getSchedule: SL 1");
                                airLinesModel.setAirLineNama("Thai Lion Air");
                                airLinesModel.setAirLineCode("SL");
                                airLinesModel.setAirLineIcon("http://static.scash.bz/fastravel/asset/maskapai/TPSL.png");
                                break;
                            case "OD":
                                Log.d(TAG, "getSchedule: OD 1");
                                airLinesModel.setAirLineNama("Malindo Air");
                                airLinesModel.setAirLineCode("OD");
                                airLinesModel.setAirLineIcon("http://static.scash.bz/fastravel/asset/maskapai/TPOD.png");
                                break;
                            case "ID":
                                airLinesModel.setAirLineNama("Batik Air");
                                airLinesModel.setAirLineCode("ID");
                                airLinesModel.setAirLineIcon("http://static.scash.bz/fastravel/asset/maskapai/TPID.png");
                                break;
                            case "IW":
                                airLinesModel.setAirLineCode("IW");
                                airLinesModel.setAirLineNama("Wings Air");
                                airLinesModel.setAirLineIcon("http://static.scash.bz/fastravel/asset/maskapai/TPIW.png");
                                break;
                            case "JT":
                                airLinesModel.setAirLineCode("JT");
                                airLinesModel.setAirLineNama("Lion Air");
                                airLinesModel.setAirLineIcon("http://static.scash.bz/fastravel/asset/maskapai/TPJT.png");
                                break;
                            default:
                                airLinesModel.setAirLineCode(airlineCode.substring(2, 4));
                                airLinesModel.setAirLineNama(airlineName);
                                airLinesModel.setAirLineIcon(airlineIcon);
                                break;
                        }

                        airLinesModel.setChecked(false);
                        listAirlines.add(airLinesModel);
                        list.add(flightDataModelClasses);
                        realList.add(flightDataModelClasses);

                        //Set<String> s = new HashSet<String>(airlineName);

                    }

                    //Log.d(TAG, "getSchedule: count " + countFlight);
                    if (checked == 2) {
                        Collections.sort(list, waktuBerangkatTerpagi);
                        adapter.updateList(clearListFromDuplicatePrice(list));
                        adapter.checked(2);
                    } else if (checked == 3) {
                        Collections.sort(list, durasi);
                        adapter.updateList(clearListFromDuplicatePrice(list));
                        adapter.checked(3);
//                        adapter.updateList(list);
                    } else if (checked == 1) {
                        Collections.sort(list, hargaTermurah);
                        adapter.updateList(clearListFromDuplicatePrice(list));
                        adapter.checked(1);
                    }
                    //int count = Collections.frequency(listAirlines, airlineName);
//                    findDuplicates
//                    if(count==1){
                        Log.d(TAG, "getSchedule: "+ clearListFromDuplicateAirlines(listAirlines));
                        logEventFireBase(ProdukGroup.PESAWAT, clearListFromDuplicateAirlines(listAirlines).get(0).getAirLineNama(), FirebaseAnalytics.Event.VIEW_SEARCH_RESULTS, EventParam.EVENT_SUCCESS, TAG);
//                    }
                }


                MemoryStore.set("schedule_list_one_way", (clearListFromDuplicatePrice(list)));
                MemoryStore.set("airlines_list", listAirlines);
                MemoryStore.set("airlinesPrice_list", listAirlinesPrice);
                String formatString = String.format(getString(R.string.filter_header), countFlight, "Rp 255.000");
                PreferenceClass.putString("updateCountFilter", formatString);

            }
        } catch (JSONException e) {
            e.printStackTrace();
            //Log.d(TAG, "getSchedule: " + e.toString());
        }
    }

    @NonNull
    private ArrayList<FlightAirlinesModel> clearListFromDuplicateFirstName(@NonNull ArrayList<FlightAirlinesModel> list1) {
        Map<String, FlightAirlinesModel> cleanMap = new LinkedHashMap<String, FlightAirlinesModel>();
        for (int i = 0; i < list1.size(); i++) {
            cleanMap.put(list1.get(i).getAirLineNama(), list1.get(i));
        }
        ArrayList<FlightAirlinesModel> list = new ArrayList<FlightAirlinesModel>(cleanMap.values());
        return list;
    }

    @NonNull
    private ArrayList<FlightAirlinesPriceModel> clearListFromDuplicatePrice_(@NonNull ArrayList<FlightAirlinesPriceModel> list1) {
        Map<String, FlightAirlinesPriceModel> cleanMap = new LinkedHashMap<>();
        for (int i = 0; i < list1.size(); i++) {
            //  cleanMap.put(list1.get(i).getFlightCode(), list1.get(i));
            cleanMap.put(String.valueOf(list1.get(i).getAirLinesPriceModel()), list1.get(i));
        }
        ArrayList<FlightAirlinesPriceModel> list = new ArrayList<>(cleanMap.values());
        return list;
    }

    @NonNull
    private ArrayList<FlightDataModelClasses> clearListFromDuplicatePrice(@NonNull ArrayList<FlightDataModelClasses> list1) {
        Map<String, FlightDataModelClasses> cleanMap = new LinkedHashMap<>();
        for (int i = 0; i < list1.size(); i++) {
            //  cleanMap.put(list1.get(i).getFlightCode(), list1.get(i));
            cleanMap.put(list1.get(i).getSeat(), list1.get(i));
        }
        ArrayList<FlightDataModelClasses> list = new ArrayList<>(cleanMap.values());
        return list;
    }

    @NonNull
    private ArrayList<FlightAirlinesModel> clearListFromDuplicateAirlines(@NonNull ArrayList<FlightAirlinesModel> list1) {
        Map<String, FlightAirlinesModel> cleanMap = new LinkedHashMap<>();
        for (int i = 0; i < list1.size(); i++) {
            //  cleanMap.put(list1.get(i).getFlightCode(), list1.get(i));
            cleanMap.put(list1.get(i).getAirLineNama(), list1.get(i));
        }
        ArrayList<FlightAirlinesModel> list = new ArrayList<>(cleanMap.values());
        return list;
    }

    @NonNull
    private ArrayList<FlightDataModelClasses> clearListFromDuplicateDurasi(@NonNull ArrayList<FlightDataModelClasses> list1) {
        Map<String, FlightDataModelClasses> cleanMap = new LinkedHashMap<>();
        for (int i = 0; i < list1.size(); i++) {
            // if(list1.get(i).getDepartureTime().equals(list1.get(i).getDepartureTime())) {
            cleanMap.put(list1.get(i).getDuration(), list1.get(i));
            // }
        }
        return new ArrayList<>(cleanMap.values());
    }

    @NonNull
    private ArrayList<FlightDataModelClasses> clearListFromDuplicateTerpagi(@NonNull ArrayList<FlightDataModelClasses> list1) {
        Map<String, FlightDataModelClasses> cleanMap = new LinkedHashMap<>();
        for (int i = 0; i < list1.size(); i++) {
//            cleanMap.put(list1.get(i).getDepartureTime(), list1.get(i));
            try {
                cleanMap.put(list1.get(i).getDetailTitle().getJSONObject(0).getString("depart"), list1.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>(cleanMap.values());
    }

    @NonNull
    public Comparator<FlightDataModelClasses> waktuBerangkatTerpagi = new Comparator<FlightDataModelClasses>() {
        public int compare(@NonNull FlightDataModelClasses app1, @NonNull FlightDataModelClasses app2) {
            String stringName1 = "", stringName2 = "";


            try {
                stringName1 = app1.getDetailTitle().getJSONObject(0).getString("depart").replace(":", "");
                stringName2 = app2.getDetailTitle().getJSONObject(0).getString("depart").replace(":", "");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                Log.d(TAG, "compare waktuBerangkatTerpagi: " + stringName1 + " " + stringName2 + " " + app1.getDetailTitle().getJSONObject(0).getString("flightCode"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return stringName1.compareTo(stringName2);
//            Log.d(TAG, "compare waktuBerangkatTerpagi: " + stringName1 + " " + stringName2);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    return Integer.compare(Integer.parseInt(stringName1), Integer.parseInt(stringName2));
//                } else {
//                    return ((Integer) Integer.parseInt(stringName1)).compareTo(Integer.parseInt(stringName2));
//                }

        }
    };

    @NonNull
    public Comparator<FlightDataModelClasses> hargaTermurah = new Comparator<FlightDataModelClasses>() {
        public int compare(@NonNull FlightDataModelClasses app1, @NonNull FlightDataModelClasses app2) {
            String stringName1 = String.valueOf(app1.getPriceTemp());
            String stringName2 = String.valueOf(app2.getPriceTemp());
            Log.d(TAG, "compare hargaTermurah: " + stringName1 + " " + stringName2);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                return Integer.compare(Integer.parseInt(stringName1), Integer.parseInt(stringName2));
            } else {
                return ((Integer) Integer.parseInt(stringName1)).compareTo(Integer.parseInt(stringName2));
            }
        }

    };

    @NonNull
    public Comparator<FlightDataModelClasses> durasi = new Comparator<FlightDataModelClasses>() {
        public int compare(@NonNull FlightDataModelClasses app1, @NonNull FlightDataModelClasses app2) {
//            String stringName1 = app1.getDuration() + app1.getDepartureTime() + app1.getArrivalTime();
//             String stringName1 = app1.getDurationInt();
////            String stringName2 = app2.getDuration() + app2.getDepartureTime() + app2.getArrivalTime();
//             String stringName2 = app2.getDurationInt();
////            Log.d(TAG, "compare durasi: " + stringName1 + " " + stringName2);
////            return stringName1.compareTo(stringName2);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                return Integer.compare(Integer.parseInt(stringName1), Integer.parseInt(stringName2));
//            }else{
//                return ((Integer)Integer.parseInt(stringName1)).compareTo(Integer.parseInt(stringName2));
//            }
            String stringName1 = "", stringName2 = "";


            //try {
                stringName1 = app1.getDuration().replace("j", "").replace("m", "");
              //  stringName1 = app1.getDetailTitle().getJSONObject(0).getString("duration").replace("j", "").replace("m", "");
              //  stringName2 = app2.getDetailTitle().getJSONObject(0).getString("duration").replace("j", "").replace("m", "");
                stringName2 = app2.getDuration().replace("j", "").replace("m", "");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

//            try {
//                Log.d(TAG, "compare durasi: " + stringName1 + " " + stringName2 + " " + app1.getDetailTitle().getJSONObject(0).getString("flightCode"));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            return stringName1.compareTo(stringName2);
        }
    };

    boolean isStillRunning = false;
    int progressz = 0;

    public void showProgress(final int value) {
        //Log.d(TAG, "showProgress: " + progressz + " " + code.size());
        progressz = value;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                progressBar.setProgress((100 * value) / PreferenceClass.getJSONObject(FlightKeyPreference.dataConfigFlight).length());
                seek_barprogressFlightPergi.setProgress((100 * value) / configAirportModel.getConfigurations().size());
//                progressBar.setProgress((100 * value) / code.length());
                textViewfilter.setTextColor(ContextCompat.getColor(FlightScheduleActivity.this, R.color.md_white_1000));
                textViewfilter.setEnabled(false);
                textViewSort.setTextColor(ContextCompat.getColor(FlightScheduleActivity.this, R.color.md_white_1000));
                textViewSort.setEnabled(false);
                if (seek_barprogressFlightPergi.getProgress() == 100) {
                    seek_barprogressFlightPergi.setVisibility(View.GONE);
                    if (list.size() > 0) {

                        textViewfilter.setTextColor(ContextCompat.getColor(FlightScheduleActivity.this, R.color.colorPrimary));
                        textViewfilter.setEnabled(true);
                        textViewSort.setTextColor(ContextCompat.getColor(FlightScheduleActivity.this, R.color.colorPrimary));
                        textViewSort.setEnabled(true);

                    } else {
                        if (mShimmerViewContainer.getVisibility() == View.VISIBLE) {
                            mShimmerViewContainer.setVisibility(View.GONE);
                            mShimmerViewContainer.stopShimmerAnimation();

                        }
                        emptyView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        textViewfilter.setTextColor(ContextCompat.getColor(FlightScheduleActivity.this, R.color.md_white_1000));
                        textViewfilter.setEnabled(false);
                        textViewSort.setTextColor(ContextCompat.getColor(FlightScheduleActivity.this, R.color.md_white_1000));
                        textViewSort.setEnabled(false);
                    }
                    isStillRunning = false;
                }
            }
        });

    }

    public void openCalendar(View v) {
        Intent intent = new Intent(FlightScheduleActivity.this, TravelTanggalActivity.class);
        intent.putExtra("initTanggal", "pergi");
        intent.putExtra("initValue", PreferenceClass.getString(FlightKeyPreference.departureDateFlight,""));
        startActivityForResult(intent, 1);
        overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
    }

    public void openSorter(View v) {
        final View view = View.inflate(this,R.layout.core_sorter, null);
        textSorterLowPrice = view.findViewById(R.id.textSorterLowPrice);
        imageViewSorterLowPrice = view.findViewById(R.id.imageViewSorterLowPrice);
        textSorterLowPrice.setOnClickListener(this);

        textSorterEarlyDept = view.findViewById(R.id.textSorterEarlyDept);
        imageViewSorterEarlyDept = view.findViewById(R.id.imageViewSorterEarlyDept);
        textSorterEarlyDept.setOnClickListener(this);

        textSorterShortDuration = view.findViewById(R.id.textSorterShortDuration);
        imageViewSorterShortDuration = view.findViewById(R.id.imageViewSorterShortDuration);
        textSorterShortDuration.setOnClickListener(this);

        btn_Tutup = view.findViewById(R.id.btn_tutup);
        btn_Tutup.setOnClickListener(this);

        if (checked == 1) {
            textSorterLowPrice.setTextColor(getResources().getColor(R.color.md_blue_800));
            textSorterEarlyDept.setTextColor(getResources().getColor(R.color.md_grey_600));
            textSorterShortDuration.setTextColor(getResources().getColor(R.color.md_grey_600));
            imageViewSorterLowPrice.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_checkmark));

            imageViewSorterEarlyDept.setBackground(null);

            imageViewSorterShortDuration.setBackground(null);

        } else if (checked == 2) {
            textSorterLowPrice.setTextColor(getResources().getColor(R.color.md_grey_600));
            textSorterEarlyDept.setTextColor(getResources().getColor(R.color.md_blue_800));
            textSorterShortDuration.setTextColor(getResources().getColor(R.color.md_grey_600));
            imageViewSorterLowPrice.setBackground(null);

            imageViewSorterEarlyDept.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_checkmark));

            imageViewSorterShortDuration.setBackground(null);

        } else if (checked == 3) {
            textSorterLowPrice.setTextColor(getResources().getColor(R.color.md_grey_600));
            textSorterEarlyDept.setTextColor(getResources().getColor(R.color.md_grey_600));
            textSorterShortDuration.setTextColor(getResources().getColor(R.color.md_blue_800));

            imageViewSorterLowPrice.setBackground(null);


            imageViewSorterEarlyDept.setBackground(null);


            imageViewSorterShortDuration.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_checkmark));

        }
        openBottomSheetDialog(FlightScheduleActivity.this, view);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
//        Log.d(TAG, "onActivityResult: "+requestCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            depDate = data.getStringExtra("dateShow");
            PreferenceClass.putString(FlightKeyPreference.departureDateFlight, data.getStringExtra("date"));
            PreferenceClass.putString(FlightKeyPreference.returnDateFlight, data.getStringExtra("date"));
            textTgl_Pax = findViewById(R.id.textViewToolbarSubTitle);
            textTgl_Pax.setText(depDate + " - " + pax + " org");
            if (isStillRunning) {
                RequestUtilsTravel.cancelTravel();
            }
            if (mShimmerViewContainer.getVisibility() == View.GONE) {
                mShimmerViewContainer.setVisibility(View.VISIBLE);
                mShimmerViewContainer.startShimmerAnimation();

            }
//            progressBar.setProgress(0);
//            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    searchingFlight();
                }
            });
        } else if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            isFilter = data.getBooleanExtra("isFilter", true);
            if (isFilter) {
                image_view_filter_check.setVisibility(View.VISIBLE);
                isFilter = true;
            } else {
                image_view_filter_check.setVisibility(View.GONE);
                isFilter = false;
            }
        } else if (requestCode == TravelActionCode.IS_FROM_PAY && resultCode == RESULT_OK) {
            //   Intent intent = new Intent(FlightReviewBookActivity.this, FlightSearchActivity.class);

            if (data != null) {
                Log.d(TAG, "onActivityResult: " + data.getAction());
                show = data.getBooleanExtra("cari", false);
                if (show) {
                    if (mShimmerViewContainer.getVisibility() == View.GONE) {
                        mShimmerViewContainer.setVisibility(View.VISIBLE);
                        mShimmerViewContainer.startShimmerAnimation();

                    }
//                progressBar.setProgress(0);
//                progressBar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            searchingFlight();
                        }
                    });
                } else {

                    if (data.getAction() != null) {
                        Intent intent = new Intent();
//                        String action = data.getAction();
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
                        finish();
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    }
//                    else{
//                        finish();
//                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                    }
                }
            } else {
//onBackPressed();
                setResult(RESULT_OK);
                onBackPressed();
//                finish();
//                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        }


    }

    @Override
    public void onClick(@NonNull View view) {
        int id = view.getId();
        if (id == R.id.btn_tutup) {
            closeBootomSheetDialog();
        } else if (id == R.id.textSorterLowPrice) {
            checked = 1;
            image_view_sort_check.setVisibility(View.GONE);
            textSorterLowPrice.setTextColor(getResources().getColor(R.color.md_blue_800));

            imageViewSorterLowPrice.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_checkmark));


            imageViewSorterEarlyDept.setBackground(null);


            imageViewSorterShortDuration.setBackground(null);
            if (isFilter) {
                // Collections.sort(adapter.mDisplayListFilter, hargaTermurah);
//                adapter.getFilterList();
                Collections.sort(adapter.mDisplayListFilter, hargaTermurah);
                adapter.updateList(clearListFromDuplicatePrice(adapter.mDisplayListFilter));

            } else {
                Collections.sort(list, hargaTermurah);
                adapter.updateList(clearListFromDuplicatePrice(list));
            }
            adapter.checked(1);
            closeBootomSheetDialog();
        } else if (id == R.id.textSorterEarlyDept) {
            checked = 2;
            image_view_sort_check.setVisibility(View.VISIBLE);
            textSorterEarlyDept.setTextColor(getResources().getColor(R.color.md_blue_800));

            imageViewSorterLowPrice.setBackground(null);


            imageViewSorterEarlyDept.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_checkmark));


            imageViewSorterShortDuration.setBackground(null);
            if (isFilter) {
                Collections.sort(adapter.mDisplayListFilter, waktuBerangkatTerpagi);
                adapter.updateList(clearListFromDuplicatePrice(adapter.mDisplayListFilter));

                // adapter.getFilterList();
            } else {
                Collections.sort(list, waktuBerangkatTerpagi);
                adapter.updateList(clearListFromDuplicatePrice(list));
////                adapter.updateList(list);
            }
            adapter.checked(2);
            closeBootomSheetDialog();
        } else if (id == R.id.textSorterShortDuration) {
            checked = 3;
            image_view_sort_check.setVisibility(View.VISIBLE);
            textSorterShortDuration.setTextColor(getResources().getColor(R.color.md_blue_800));

            imageViewSorterLowPrice.setBackground(null);


            imageViewSorterEarlyDept.setBackground(null);


            imageViewSorterShortDuration.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_checkmark));

//            Collections.sort(list, durasi);
//            adapter.updateList(clearListFromDuplicatePrice(list));
            if (isFilter) {
                Collections.sort(adapter.mDisplayListFilter, durasi);
//                adapter.updateList(clearListFromDuplicatePrice(adapter.mDisplayListFilter));
                adapter.updateList(clearListFromDuplicateDurasi(adapter.mDisplayListFilter));

                // adapter.getFilterList();
            } else {
                Collections.sort(list, durasi);
                adapter.updateList(clearListFromDuplicateDurasi(list));
////                adapter.updateList(list);
            }
            closeBootomSheetDialog();
        }
    }

    public void openFilter(View v) {
        Intent intent = new Intent(FlightScheduleActivity.this, FlightFilterActivity.class);
        intent.putExtra("isFilter", isFilter);
        startActivityForResult(intent, 2);
        overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
    }


}

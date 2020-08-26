package com.bm.main.single.ftl.flight.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;



import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.ProdukGroup;
import com.bm.main.fpl.templates.MaterialNumberPicker;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.materialedittext.MaterialEditText;
import com.bm.main.scm.R;
import com.bm.main.scm.SBFApplication;
import com.bm.main.single.ftl.activities.TravelTanggalActivity;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bm.main.single.ftl.flight.constants.FlightKeyPreference;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.SimpleDateFormat;

import static com.bm.main.fpl.utils.DialogUtils.closeBootomSheetDialog;
import static com.bm.main.fpl.utils.DialogUtils.openBottomSheetDialog;

public class FlightSearchRevActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = FlightSearchRevActivity.class.getSimpleName();
    BottomSheetDialog bottomSheetDialog;
//    SwitchButton switchPP;
    RelativeLayout relTanggalBerangkat,  linPenumpang, linKotaAsal, linKotaTujuan;
    LinearLayout linTanggal;
    ImageView btnExchange, imageKotaAsal, imageKotaTujuan, imageViewTanggalBerangkat;
    MaterialEditText textKotaAsal, textKotaTujuan, textTanggalBerangkat,  textJumlahPenumpang;
    MaterialNumberPicker pickerAdult, pickerChild, pickerInfant;
    AppCompatButton btnPilih, btnBatal;
    AppCompatButton btnCari;
    FrameLayout toastContainer;
    int pickerAdultValue = 1;
    int pickerChildValue = 0;
    int pickerInfantValue = 0;
    public String origin, destination, depDate, returnDate;
    boolean pp;
    View.OnClickListener mOnClickListener;
    int airportCount = 0;
    int nasionalityCount =  0;
    int configCount = 0;
    Context context;
    boolean isFromPay = false;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_search_rev_activity);
        overridePendingTransition(0, 0);
        logEventFireBase(ProdukGroup.PESAWAT,ProdukGroup.PESAWAT, EventParam.EVENT_ACTION_VISIT,EventParam.EVENT_SUCCESS,TAG);
        intent = getIntent();
        if (intent != null)
            isFromPay = intent.getBooleanExtra("isFromPay", true);

        initView();


        init(0);


//        airportCount = SavePref.getInstance(getApplicationContext()).getInt("airportCount");
//        nasionalityCount = SavePref.getInstance(getApplicationContext()).getInt("nasionalityCount");
//        configCount = SavePref.getInstance(getApplicationContext()).getInt("configCount");
//        //Log.d(TAG, "onCreate count: " + airportCount + " " + nasionalityCount + " " + configCount);
//
//        MaterialRippleLayout.on(btnCari).rippleOverlay(true)
//                .rippleAlpha(0.2f)
//                .rippleColor(R.color.md_orange_200)
//                .rippleHover(true)
//                .create();
//        btnCari.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Handler handler = new Handler(Looper.getMainLooper());
//                handler.post(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        MemoryStore.set(FlightSearchRevActivity.this, "countAdultFlight", pickerAdultValue);
//                        MemoryStore.set(FlightSearchRevActivity.this, "countChildFlight", pickerChildValue);
//                        MemoryStore.set(FlightSearchRevActivity.this, "countInfantFlight", pickerInfantValue);
//                        Intent intent = new Intent(FlightSearchRevActivity.this, ScheduleFlightActivity.class);
//                        intent.putExtra("cari", true);
//                        startActivity(intent);
//                    }
//                });
//            }
//        });

        String kotaAsal = PreferenceClass.getString(FlightKeyPreference.airportKotaAsal, "");
        String kodeAsal = PreferenceClass.getString(FlightKeyPreference.airportKodeAsal, "");
        String bandaraAsal = PreferenceClass.getString(FlightKeyPreference.airportNamaAsal, "");

        if (kotaAsal.equals("")) {
            kotaAsal = "Juanda (SUB)";
            kodeAsal = "SUB";
            bandaraAsal = "JUANDA";
            PreferenceClass.putString(FlightKeyPreference.airportKotaAsal, kotaAsal);
            PreferenceClass.putString(FlightKeyPreference.airportKodeAsal, kodeAsal);
            PreferenceClass.putString(FlightKeyPreference.airportNamaAsal, bandaraAsal);

        }

        //Log.d(TAG, "onCreate: " + kotaAsal);
        textKotaAsal.setText(kotaAsal);
        origin = kodeAsal;
        String kotaTujuan = PreferenceClass.getString(FlightKeyPreference.airportKotaTujuan, "");
        String kodeTujuan = PreferenceClass.getString(FlightKeyPreference.airportKodeTujuan, "");
        String bandaraTujuan = PreferenceClass.getString(FlightKeyPreference.airportNamaTujuan, "");
        if (kotaTujuan.equals("")) {
            kotaTujuan = "Soekarno - Hatta (CGK)";
            kodeTujuan = "CGK";
            bandaraTujuan = "Soekarno - Hatta";
            PreferenceClass.putString(FlightKeyPreference.airportKotaTujuan, kotaTujuan);
            PreferenceClass.putString(FlightKeyPreference.airportKodeTujuan, kodeTujuan);
            PreferenceClass.putString(FlightKeyPreference.airportNamaTujuan, bandaraTujuan);

        }
        textKotaTujuan.setText(kotaTujuan);
        destination = kodeTujuan;
        LocalDate currentDate = LocalDate.now();
//        Date now = new Date();
        LocalDate max = LocalDate.of(currentDate.getYear(),currentDate.getMonthValue(),currentDate.getDayOfMonth()).plusDays(1);
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", config.locale);
//        SimpleDateFormat formatterShow = new SimpleDateFormat("EEEE, dd MMMM yyyy", config.locale);
        SimpleDateFormat formatterShowDate = new SimpleDateFormat("dd", SBFApplication.config.locale);
        SimpleDateFormat formatterShowMonth = new SimpleDateFormat("MMMM", SBFApplication.config.locale);
        SimpleDateFormat formatterShowDayYear = new SimpleDateFormat("EEEE, yyyy", SBFApplication.config.locale);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd",SBFApplication.config.locale);
        DateTimeFormatter formatterShow = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy",SBFApplication.config.locale);
        String showDateNow = formatterShow.format(max);

//        String showDateNowDate = formatterShowDate.format(now);
//        String showDateNowMonth = formatterShowMonth.format(now);
//        String showDateNowDayYear = formatterShowDayYear.format(now);

        String dateNow = formatter.format(max);
        textTanggalBerangkat.setText(showDateNow);
       // textTanggalPulang.setText(showDateNow);
        depDate = dateNow;
        returnDate = dateNow;

        PreferenceClass.putString(FlightKeyPreference.departureDateFlight, depDate);
        PreferenceClass.putString(FlightKeyPreference.departureDateShowFlight, showDateNow);
        PreferenceClass.putString(FlightKeyPreference.returnDateFlight, depDate);
        PreferenceClass.putString(FlightKeyPreference.returnDateShowFlight, showDateNow);

//        PreferenceClass.putString(FlightKeyPreference.departureDateFlight, dateNow);
//        PreferenceClass.putString(FlightKeyPreference.departureDateShowFlight, showDateNow);
//        PreferenceClass.putString(FlightKeyPreference.returnDateFlight, dateNow);
//        PreferenceClass.putString(FlightKeyPreference.returnDateShowFlight, showDateNow);
//        switchPP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                relTanggalPulang.setVisibility(isChecked ? View.VISIBLE : View.GONE);
//                pp = isChecked;
//            }
//        });
//        // dialog = new TopSheetDialog(this);
////        bottomSheetDialog = new BottomSheetDialog(this);
////        bottomSheetDialog.setContentView(R.layout.three_passager);
//        // toastContainer = bottomSheetDialog.findViewById(R.id.toast_container);
//        mOnClickListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        };
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Cari Tiket Pesawat");

      //  switchPP = findViewById(R.id.switchPP);
        btnExchange = findViewById(R.id.btnExchange);
        btnExchange.setOnClickListener(this);
        linTanggal = findViewById(R.id.linTanggal);
        relTanggalBerangkat = findViewById(R.id.relTanggalBerangkat);
        relTanggalBerangkat.setOnClickListener(this);
        imageViewTanggalBerangkat = findViewById(R.id.imageViewTanggalBerangkat);
        imageViewTanggalBerangkat.setOnClickListener(this);
//        imageViewTanggalPulang = findViewById(R.id.imageViewTanggalPulang);
//        imageViewTanggalPulang.setOnClickListener(this);
//        relTanggalPulang = findViewById(R.id.relTanggalPulang);
//        relTanggalPulang.setOnClickListener(this);
        linKotaAsal = findViewById(R.id.linPenumpang);
        linKotaTujuan = findViewById(R.id.linKotaAsal);
        linPenumpang = findViewById(R.id.linKotaTujuan);
        linPenumpang.setOnClickListener(this);
        imageKotaAsal = findViewById(R.id.imageKotaAsal);
        imageKotaAsal.setOnClickListener(this);
        imageKotaTujuan = findViewById(R.id.imageKotaTujuan);
        imageKotaTujuan.setOnClickListener(this);
        textKotaAsal = findViewById(R.id.textKotaAsal);
        textKotaAsal.setOnClickListener(this);
        textKotaTujuan = findViewById(R.id.textKotaTujuan);
        textKotaTujuan.setOnClickListener(this);
        textTanggalBerangkat = findViewById(R.id.textTanggalBerangkat);
        textTanggalBerangkat.setOnClickListener(this);
//        textTanggalPulang = findViewById(R.id.textTanggalPulang);
//        textTanggalPulang.setOnClickListener(this);
        textJumlahPenumpang = findViewById(R.id.textJumlahPenumpang);
        textJumlahPenumpang.setOnClickListener(this);
        btnCari = findViewById(R.id.buttonCari_Flight);
        btnCari.setOnClickListener(this);

        PreferenceClass.putInt(FlightKeyPreference.countAdultFlight, pickerAdultValue);
        PreferenceClass.putInt(FlightKeyPreference.countChildFlight, pickerChildValue);
        PreferenceClass.putInt(FlightKeyPreference.countInfantFlight, pickerInfantValue);

    }
//    private void RequestNationality() {
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("token", SavePref.getInstance(getApplicationContext()).getString("token"));
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        //Log.d(TAG, "RequestNationality: " + jsonObject);
//        RequestUtils.transportWithJSONObjectResponse("app/nationality", jsonObject, new JsonObjectResponseHandler(this, ActionCode.NATIONALITY));
//    }

//    private void RequestConfiguration() {
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("token", SavePref.getInstance(getApplicationContext()).getString("token"));
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        //Log.d(TAG, "RequestConfiguration: " + jsonObject);
//        RequestUtils.transportWithJSONObjectResponse("flight/configuration", jsonObject, new JsonObjectResponseHandler(this, ActionCode.CONFIGURATION));
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rumah, menu);
//        new Handler().post(new Runnable() {
//            @Override
//            public void run() {
//
//
//                if (PreferenceClass.getInt(TAG, 0) != 1) {
//                    showCaseFirst(FlightSearchRevActivity.this, "", getResources().getString(R.string.content_flight_airport_asal), textKotaAsal);
//
//                    mGbuilder.setGuideListener(new GuideView.GuideListener() {
//                        @Override
//                        public void onDismiss(@NonNull View view) {
//                            switch (view.getId()) {
//                                case R.id.textKotaAsal:
//                                    mGbuilder
//                                            .setTitle("")
//                                            .setContentText(getResources().getString(R.string.content_flight_tukar_airport)).setGravity(GuideView.Gravity.center)
//                                            .setDismissType(GuideView.DismissType.anywhere)
//                                            .setTargetView(btnExchange)
//                                            .setCircleView(true)
//                                            .build();
//                                    break;
//                                case R.id.btnExchange:
//                                    mGbuilder
//                                            .setTitle("")
//                                            .setContentText(getResources().getString(R.string.content_flight_airport_tujuan)).setGravity(GuideView.Gravity.center)
//                                            .setDismissType(GuideView.DismissType.anywhere)
//                                            .setTargetView(textKotaTujuan)
//                                            .setCornerRadius(5)
//                                            .setCircleView(false)
//                                            .build();
//                                    break;
//
//                                case R.id.textKotaTujuan:
//                                    mGbuilder
//                                            .setTitle("")
//                                            .setContentText(getResources().getString(R.string.content_flight_tanggal_berangkat)).setGravity(GuideView.Gravity.center)
//                                            .setDismissType(GuideView.DismissType.anywhere)
//                                            .setTargetView(textTanggalBerangkat)
//                                            .setCornerRadius(10)
//                                            .setCircleView(false)
//                                            .build();
//                                    break;
//
//                                case R.id.textTanggalBerangkat:
//                                    mGbuilder
//                                            .setTitle("")
//                                            .setContentText(getResources().getString(R.string.content_flight_jumlah_penumpang)).setGravity(GuideView.Gravity.center)
//                                            .setDismissType(GuideView.DismissType.anywhere)
//                                            .setTargetView(textJumlahPenumpang)
//                                            .setCornerRadius(10)
//                                            .setCircleView(false)
//                                            .build();
//                                    break;
//
////                                case R.id.linMainJumlahPenumpang:
////                                    mGbuilder
////                                            .setTitle("")
////                                            .setContentText(getResources().getString(R.string.content_flight_cari_pilih_harga)).setGravity(GuideView.Gravity.center)
////                                            .setDismissType(GuideView.DismissType.anywhere)
////                                            .setTargetView(linTampilkanHarga)
////                                            .setCornerRadius(10)
////                                            .setCircleView(false)
////                                            .build();
////                                    break;
//
//                                case R.id.textJumlahPenumpang:
//                                    PreferenceClass.putInt(TAG, 1);
//                                    return;
//                            }
//                            mGuideView = mGbuilder.build();
//                            mGuideView.show();
//
//                        }
//                    });
//                    mGuideView = mGbuilder.build();
//                    mGuideView.show();
//                }
//            }
//        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_right_drawer) {

            openTopDialog(false);
//        } else if (id == R.id.action_right_scanner) {
//
//            openScanner(FlightSearchActivity.this);
        } else if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


//    int progressx = 0;

//    @Override
//    public void onSuccess(final int actionCode, final JSONObject response) {
////        runOnUiThread(new Runnable() {
////            @Override
////            public void run() {
//        try {
//            if (response.getString("rc").equals("00")) {
//                if (actionCode == ActionCode.AIRPORT) {
//                    Log.d(TAG, "onSuccess AIRPORT: " + response.toString());
//                    try {
//                        List<FlightAirportModel> result = new ArrayList<>();
//                        JSONArray airPortJson = response.getJSONArray("data");
//                        PreferenceStore.putJSONArray("route_list_data",airPortJson);
//                        SavePref.getInstance(getApplicationContext()).saveInt("airportCount", airPortJson.length());
//                        FlightAirportModel airportModel;
//                        for (int i = 0; i < airPortJson.length(); i++) {
//                            JSONObject airPort = airPortJson.getJSONObject(i);
//                            airportModel = new FlightAirportModel(airPort.getString("code"), airPort.getString("name"), airPort.getString("group"));
//                            result.add(airportModel);
//                        }
//                        MemoryStore.set(FlightSearchRevActivity.this, "route_list", result);
//                        // PreferenceStore.putJSONArray("dataAirport",airPortJson);
//
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//                else if (actionCode == ActionCode.SCHEDULE_ONEWAY) {
//                    progressx = progressx + 1;
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            getInstancex().showProgress(progressx);
//                            getInstancex().getSchedule(response);
//                        }
//                    });
//                }
//                else if (actionCode == ActionCode.CONFIGURATION) {
//                    //Log.d(TAG, "onSuccess CONFIGURATION: " + response.toString());
//                    JSONObject oParent = response.getJSONObject("data");
//                    PreferenceStore.putJSONObject("dataConfigFlight",response.getJSONObject("data"));
//                    JSONArray jsonArrayConfigFlight = oParent.getJSONArray("configurations");
//                    List<ConfigFlightModel> listConfigFlightModel = new ArrayList<>();
//                    ConfigFlightModel configFlightModel;
//                    for (int i = 0; i < jsonArrayConfigFlight.length(); i++) {
//                        JSONObject jsonObjectConfFlight = jsonArrayConfigFlight.getJSONObject(i);
//                        //Log.d(TAG, "onSuccess: " + jsonObjectConfFlight.getString("airline"));
//                        configFlightModel = new ConfigFlightModel();
//                        configFlightModel.setAirline(jsonObjectConfFlight.getString("airline"));
//                        configFlightModel.setIsActive(jsonObjectConfFlight.getString("isActive"));
//                        configFlightModel.setIsFirstName(jsonObjectConfFlight.getString("isFirstName"));
//                        configFlightModel.setIsLastName(jsonObjectConfFlight.getString("isLastName"));
//                        configFlightModel.setIsTitle(jsonObjectConfFlight.getString("isTitle"));
//                        configFlightModel.setIsPhone(jsonObjectConfFlight.getString("isPhone"));
//                        configFlightModel.setIsMobilePhone(jsonObjectConfFlight.getString("isMobilePhone"));
//                        configFlightModel.setIsBirthDay(jsonObjectConfFlight.getString("isBirthDay"));
//                        configFlightModel.setIsIdentityNumber(jsonObjectConfFlight.getString("isIdentityNumber"));
//                        configFlightModel.setIsNationality(jsonObjectConfFlight.getString("isNationality"));
//                        configFlightModel.setIsAddress(jsonObjectConfFlight.getString("isAddress"));
//                        configFlightModel.setIsEmail(jsonObjectConfFlight.getString("isEmail"));
//                        configFlightModel.setIsPostalCode(jsonObjectConfFlight.getString("isPostalCode"));
//                        listConfigFlightModel.add(configFlightModel);
//                    }
//                    MemoryStore.set(FlightSearchRevActivity.this, "configFlight", listConfigFlightModel);
//                    SavePref.getInstance(getApplicationContext()).saveString("configFlight", listConfigFlightModel.toString());
//                    PreferenceStore.putJSONArray("configFlight",jsonArrayConfigFlight);
//                }
//                else if (actionCode == ActionCode.NATIONALITY) {
//                    //Log.d(TAG, "onSuccess NASIONALITY: " + response.toString());
//                    JSONObject oParent = response.getJSONObject("data");
//                    JSONArray jsonArrayNasionalityFlight = oParent.getJSONArray("nationality");
//                    List<NasionalityModel> listNasionalityModel = new ArrayList<>();
//                    NasionalityModel nasinonalityModel;
//                    SavePref.getInstance(getApplicationContext()).saveInt("nasionalityCount", jsonArrayNasionalityFlight.length());
//                    for (int i = 0; i < jsonArrayNasionalityFlight.length(); i++) {
//                        JSONObject jsonObjectNasionalityFlight = jsonArrayNasionalityFlight.getJSONObject(i);
//                        nasinonalityModel = new NasionalityModel();
//                        nasinonalityModel.setCountry_id(jsonObjectNasionalityFlight.getString("country_id"));
//                        nasinonalityModel.setCountry_name(jsonObjectNasionalityFlight.getString("country_name"));
//                        nasinonalityModel.setCountry_areacode(jsonObjectNasionalityFlight.getString("country_areacode"));
//                        listNasionalityModel.add(nasinonalityModel);
//                    }
//                    MemoryStore.set(FlightSearchRevActivity.this, "nationality", listNasionalityModel);
//                    PreferenceStore.putJSONArray("configFlight",jsonArrayNasionalityFlight);
//                }
//            }
//            else {
//                if (actionCode == ActionCode.SCHEDULE_ONEWAY) {
//                    progressx = progressx + 1;
//                    ScheduleFlightActivity.getInstancex().showProgress(progressx);
//                }
//            }
//        } catch (JSONException e) {
//            if (actionCode == ActionCode.SCHEDULE_ONEWAY) {
//                progressx = progressx + 1;
//                getInstancex().showProgress(progressx);
//            }
//            showToast(e.toString());
//        }
////            }
////        });
//    }
//
//    @Override
//    public void onFailure(final int actionCode, final String responseCode, final String responseDescription, Throwable throwable) {
////        runOnUiThread(new Runnable() {
////            @Override
////            public void run() {
//        //Log.d(TAG, "onFailure: " + actionCode + " " + responseCode + " " + responseDescription);
//        final String rd = responseDescription;
//        if (actionCode == ActionCode.SCHEDULE_ONEWAY) {
//            progressx = progressx + 1;
//            getInstancex().showProgress(progressx);
//        }
////            }
////        });
//    }

//    @Override
//    public void onUpdate(long bytesRead, long contentLength, boolean done) {
//        /*System.out.println(bytesRead);
//        System.out.println(contentLength);
//        System.out.println(done);
//        System.out.format("%d%% done search\n", (100 * bytesRead) / contentLength);*/
//    }

    @Override
    public void onBackPressed() {
//        if (isFromPay == true) {
//            Intent intent;
////            try {
//            intent = new Intent(FlightSearchRevActivity.this, HomeActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
////            } catch (ClassNotFoundException e) {
////                e.printStackTrace();
////            }
//        }
        finish();
        overridePendingTransition(0,0);
    }

    @Override
    public void onClick(@NonNull View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.textKotaAsal:
                // TODO 19/03/29
                //  requestCode = 1;
                intent = new Intent(FlightSearchRevActivity.this, FlightAirPortActivity.class);
                intent.putExtra("initAirport", "asal");
                startActivityForResult(intent, TravelActionCode.LIST_ORIGIN_AIRPORT);
                overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
                break;
            case R.id.btnExchange:
                // TODO 19/03/29
                switchAirport(v);
                break;
            case R.id.textKotaTujuan:
                // TODO 19/03/29
                //requestCode = 2;
                intent = new Intent(FlightSearchRevActivity.this, FlightAirPortActivity.class);
                intent.putExtra("initAirport", "tujuan");
                startActivityForResult(intent, TravelActionCode.LIST_DESTINATION_AIRPORT);
                overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
                break;
            case R.id.textTanggalBerangkat:
                // TODO 19/03/29
                //  requestCode = 3;
                intent = new Intent(FlightSearchRevActivity.this, TravelTanggalActivity.class);
                intent.putExtra("initTanggal", "pergi");
                intent.putExtra("initValue", PreferenceClass.getString(FlightKeyPreference.departureDateFlight,""));

                startActivityForResult(intent, TravelActionCode.LIST_TANGGAL_BERANGKAT);
                overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
                break;
            case R.id.textJumlahPenumpang:
                // TODO 19/03/29
                openBottomSheet(v);
                break;
            case R.id.buttonCari_Flight:
                // TODO 19/03/29
//                int selectedId = radioHargaGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
//                radioHargaButton = (RadioButton) findViewById(selectedId);

//                PreferenceClass.putString(FlightKeyPreference.searchChoicePrice,radioHargaButton.getTag().toString());

                PreferenceClass.putString(FlightKeyPreference.departureDateFlight, depDate);
                PreferenceClass.putString(FlightKeyPreference.departureDateShowFlight, String.valueOf(textTanggalBerangkat.getText()));
                PreferenceClass.putString(FlightKeyPreference.returnDateFlight, depDate);
                PreferenceClass.putString(FlightKeyPreference.returnDateShowFlight, String.valueOf(textTanggalBerangkat.getText()));

                intent = new Intent(FlightSearchRevActivity.this, FlightScheduleActivity.class);
                intent.putExtra("cari", true);
                startActivityForResult(intent, TravelActionCode.IS_FROM_PAY);
                //startActivity(intent);
                break;
            default:
                break;
        }
    }

    public void openBottomSheet(View v) {
        final FrameLayout view = (FrameLayout)View.inflate(this,R.layout.travel_three_passager, null);
        pickerAdult = view.findViewById(R.id.jumlahDewasa);
        pickerChild = view.findViewById(R.id.jumlahAnak);
        pickerInfant = view.findViewById(R.id.jumlahBayi);
        pickerAdult.setValue(pickerAdultValue);
        pickerChild.setValue(pickerChildValue);
        pickerChild.setMaxValue(7-pickerAdultValue);
        pickerInfant.setValue(pickerInfantValue);
        pickerInfant.setMaxValue(pickerAdultValue);
        btnBatal = view.findViewById(R.id.btn_batal);
        btnPilih = view.findViewById(R.id.btn_pilih);
        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeBootomSheetDialog();
            }
        });
        btnPilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeBootomSheetDialog();
                pickerAdultValue = pickerAdult.getValue();
                pickerChildValue = pickerChild.getValue();
                pickerInfantValue = pickerInfant.getValue();
                PreferenceClass.putInt(FlightKeyPreference.countAdultFlight, pickerAdultValue);
                PreferenceClass.putInt(FlightKeyPreference.countChildFlight, pickerChildValue);
                PreferenceClass.putInt(FlightKeyPreference.countInfantFlight, pickerInfantValue);
//                mTextViewJumlahDewasa.setText(String.valueOf(pickerAdult.getValue()));
//                mTextViewJumlahAnak.setText(String.valueOf(pickerChild.getValue()));
//                mTextViewJumlahBayi.setText(String.valueOf(pickerInfant.getValue()));


                if (pickerChild.getValue() == 0 && pickerInfant.getValue() == 0) {
                    textJumlahPenumpang.setText(pickerAdult.getValue() + " Dewasa");
                }
                else if (pickerInfant.getValue() == 0) {
                    textJumlahPenumpang.setText(pickerAdult.getValue() + " Dewasa, " + pickerChild.getValue() + " Anak");
                }
                else if (pickerChild.getValue() == 0) {
                    textJumlahPenumpang.setText(pickerAdult.getValue() + " Dewasa, " + pickerInfant.getValue() + " Bayi");
                }
                else {
                    textJumlahPenumpang.setText(pickerAdult.getValue() + " Dewasa, " + pickerChild.getValue() + " Anak," + pickerInfant.getValue() + " Bayi");
                }
            }
        });
        pickerAdult.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //Log.d(TAG, "onScrollStateChange: " + oldVal + " " + newVal);
                pickerInfant.setMaxValue(newVal);
                pickerChild.setMaxValue(7 - newVal);

//                if (pickerInfant.getValue() > newVal) {
//                    snackBarCustomAction(view, R.string.penumpang_bayi_picker, String.valueOf(R.string.action_tutup), ALERT);
//                    pickerInfant.setValue(newVal);
//                } else
//                    if ((newVal + pickerChild.getValue() > 7)) {
//                    snackBarCustomAction(view, R.string.penumpang_max, String.valueOf(R.string.action_tutup), ALERT);
//                    pickerAdult.setValue(oldVal);
//                }
            }
        });

        pickerChild.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                pickerAdult.setMaxValue(7 - newVal);
                //Log.d(TAG, "onScrollStateChange: " + oldVal + " " + newVal);
//                if ((pickerAdult.getValue() + pickerInfant.getValue()+newVal > 7)) {
//                    snackBarCustomAction(view, R.string.penumpang_max, String.valueOf(R.string.action_tutup), ALERT);
//                    pickerChild.setValue(oldVal);
//                }
            }
        });

        pickerInfant.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                //Log.d(TAG, "onScrollStateChange: " + oldVal + " " + newVal);
                //if (newVal > pickerAdult.getValue()) {
//                if ((pickerAdult.getValue() +pickerChild.getValue() +newVal > 7)) {
//                    snackBarCustomAction(view, R.string.penumpang_bayi_picker, String.valueOf(R.string.action_tutup), ALERT);
//                    pickerInfant.setValue(oldVal);
//                }
            }
        });

        openBottomSheetDialog(this, view);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TravelActionCode.LIST_ORIGIN_AIRPORT && resultCode == Activity.RESULT_OK) {
            if (PreferenceClass.getString(FlightKeyPreference.airportKodeTujuan, "").equals(data.getStringExtra("airportKode"))) {
                snackBarCustomAction(findViewById(R.id.rootLayout), R.string.flight_content_same_airport, "", 1);
                textKotaAsal.setBackgroundResource(R.drawable.shape_card_error);
                textKotaAsal.setAnimation(animShake);
                textKotaAsal.startAnimation(animShake);
                getar();
            } else {

                textKotaAsal.setText(data.getStringExtra("airportNama")+" ("+data.getStringExtra("airportKode")+")");
                //mTextViewKodeNamaAirportAsal.setText(data.getStringExtra("airportKota"));
                origin = data.getStringExtra("airportKode");
                PreferenceClass.putString(FlightKeyPreference.airportNamaAsal, data.getStringExtra("airportNama"));
                PreferenceClass.putString(FlightKeyPreference.airportKotaAsal, data.getStringExtra("airportKota"));
                PreferenceClass.putString(FlightKeyPreference.airportKodeAsal, data.getStringExtra("airportKode"));
                PreferenceClass.putString(FlightKeyPreference.airportGroupAsal, data.getStringExtra("airportGroup"));
            }
        } else if (requestCode == TravelActionCode.LIST_DESTINATION_AIRPORT && resultCode == Activity.RESULT_OK) {
            if (PreferenceClass.getString(FlightKeyPreference.airportKodeAsal, "").equals(data.getStringExtra("airportKode"))) {
                snackBarCustomAction(findViewById(R.id.rootLayout), R.string.flight_content_same_airport, "", 1);
                textKotaTujuan.setBackgroundResource(R.drawable.shape_card_error);
                textKotaTujuan.setAnimation(animShake);
                textKotaTujuan.startAnimation(animShake);
                getar();
            } else {

                textKotaTujuan.setText(data.getStringExtra("airportNama")+" ("+data.getStringExtra("airportKode")+")");
              //  mTextViewKodeNamaAirportTujuan.setText(data.getStringExtra("airportKota"));
                destination = data.getStringExtra("airportKode");
                PreferenceClass.putString(FlightKeyPreference.airportNamaTujuan, data.getStringExtra("airportNama"));
                PreferenceClass.putString(FlightKeyPreference.airportKotaTujuan, data.getStringExtra("airportKota"));
                PreferenceClass.putString(FlightKeyPreference.airportKodeTujuan, data.getStringExtra("airportKode"));
                PreferenceClass.putString(FlightKeyPreference.airportGroupTujuan, data.getStringExtra("airportGroup"));
            }
        } else if (requestCode == TravelActionCode.LIST_TANGGAL_BERANGKAT && resultCode == Activity.RESULT_OK) {
            //textTanggalBerangkat.setText(data.getStringExtra("dateShow"));
            textTanggalBerangkat.setText(data.getStringExtra("dateShow"));
//            mTextViewBulanBerangkat.setText(data.getStringExtra("dateShowMonth"));
//            mTextViewHariTahunBerangkat.setText(data.getStringExtra("dateShowDayYear"));


            depDate = data.getStringExtra("date");

            returnDate = data.getStringExtra("date");

            PreferenceClass.putString(FlightKeyPreference.departureDateFlight, depDate);
            PreferenceClass.putString(FlightKeyPreference.departureDateShowFlight, data.getStringExtra("dateShow"));
            PreferenceClass.putString(FlightKeyPreference.returnDateFlight, depDate);
            PreferenceClass.putString(FlightKeyPreference.returnDateShowFlight, data.getStringExtra("dateShow"));


        } else if (requestCode == TravelActionCode.IS_FROM_PAY && resultCode == Activity.RESULT_OK) {
            if(data!=null && data.getAction()!=null) {
                Log.d(TAG, "onActivityResult: " + data.getAction());
                // String action = data.getAction();
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
                setResult(RESULT_OK,intent);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }

        }
    }

    public void switchAirport(View v) {
//        if (!textKotaAsal.getText().toString().equals("") || !textKotaTujuan.getText().toString().equals("")) {
//            PreferenceClass.putString( "airportKotaTujuan", kotaTujuan);
//            PreferenceClass.putString("airportKodeTujuan", kodeTujuan);
//            PreferenceClass.putString("airportNamaTujuan", bandaraTujuan);
//

        String originCode = PreferenceClass.getString(FlightKeyPreference.airportKodeAsal, "");
        // String originAirport = PreferenceClass.getString( "airportNamaAsal","");

        String destinationCode = PreferenceClass.getString(FlightKeyPreference.airportKodeTujuan, "");
         //String destinationAirport = PreferenceClass.getString( "airportNamaTujuan","");


        String originAirport = textKotaAsal.getText().toString();
        String originName = PreferenceClass.getString(FlightKeyPreference.airportKotaAsal,"");
        String originGroup = PreferenceClass.getString(FlightKeyPreference.airportGroupAsal,"Domestik");


        String destinationAirport = textKotaTujuan.getText().toString();
        String destinationName = PreferenceClass.getString(FlightKeyPreference.airportKotaTujuan,"");
        String destinationGroup = PreferenceClass.getString(FlightKeyPreference.airportGroupTujuan,"Domestik");

        textKotaAsal.setText(destinationAirport);
      //  mTextViewKodeNamaAirportAsal.setText(destinationName);

        textKotaTujuan.setText(originAirport);
        //mTextViewKodeNamaAirportTujuan.setText(originName);


        origin = destinationCode;
        destination = originCode;

        PreferenceClass.putString(FlightKeyPreference.airportKodeAsal, destinationCode);
        PreferenceClass.putString(FlightKeyPreference.airportKotaAsal, destinationName);
        PreferenceClass.putString(FlightKeyPreference.airportNamaAsal, destinationAirport);
        PreferenceClass.putString(FlightKeyPreference.airportGroupAsal, destinationGroup);

        PreferenceClass.putString(FlightKeyPreference.airportKodeTujuan, originCode);
        PreferenceClass.putString(FlightKeyPreference.airportKotaTujuan, originName);
        PreferenceClass.putString(FlightKeyPreference.airportNamaTujuan, originAirport);
        PreferenceClass.putString(FlightKeyPreference.airportGroupTujuan, originGroup);
//        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        //Log.d(TAG, "onPostResume: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
//        (new Thread() {
//            @Override
//            public void run() {
//                RequestAirport();
//            }
//        }).start();
//        (new Thread() {
//            @Override
//            public void run() {
//                RequestNationality();
//            }
//        }).start();
//        (new Thread() {
//            @Override
//            public void run() {
//                RequestConfiguration();
//            }
//        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Log.d(TAG, "onDestroy: ");
    }
}

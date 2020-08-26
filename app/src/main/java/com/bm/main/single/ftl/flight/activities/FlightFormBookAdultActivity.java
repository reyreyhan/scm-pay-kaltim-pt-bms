package com.bm.main.single.ftl.flight.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.templates.CustomFontCheckBox;
import com.bm.main.fpl.templates.clearableedittext.ClearableEditText;
import com.bm.main.fpl.utils.FormatString;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.Validate;
import com.bm.main.materialedittext.MaterialEditText;
import com.bm.main.scm.R;
import com.bm.main.scm.SBFApplication;
import com.bm.main.single.ftl.adapters.ListCountryAdapter;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bm.main.single.ftl.flight.constants.FlightKeyPreference;
import com.bm.main.single.ftl.flight.models.ConfigFlightModel;
import com.bm.main.single.ftl.flight.models.FlightFormBaggageModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

//import com.bm.main.single.ftl.utils.MemoryStore;

public class FlightFormBookAdultActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener,
        AdapterView.OnItemClickListener {
    private static final String TAG = FlightFormBookAdultActivity.class.getSimpleName();
    TextView titleHeader, actBatal;
    MaterialEditText adultTitle,  adultId, adultBorn, adultMobilePhone;

    ClearableEditText adultName,adultEmail;
    String sAdultTitle, sAdultName, sAdultId, sAdultBorn, sAdultMobilePhone, sAdultEmail;
    String sAdultNationality, sAdultNoPassport, sAdultIssuingCountry, sAdultExpirationDate;
    String titleTemp;
    static String dateBorn;
    static String dateExpired;
    AppCompatButton btnSelesai;
    private ListPopupWindow lpw;
    private ListPopupWindow lpw1;
    private ListPopupWindow lpw2;
    private ListPopupWindow lpw3;
    private ListPopupWindow lpwCountry;
    private String[] list;
    private String[] listTitle;
//    private List[Country] listCountry;
//    private List[] listCodeCountry;


//    private String[] listBaggage1;
//    private String[] listTitleBaggage1;
//    private String[] listWeightBaggage1;
//
//    private String[] listBaggage2;
//    private String[] listTitleBaggage2;
//    private String[] listWeightBaggage2;
//
//    private String[] listBaggage3;
//    private String[] listTitleBaggage3;
//    private String[] listWeightBaggage3;

    boolean isNew = false;
    //    List<ConfigFlightModel> resultConf = new ArrayList<>();
    private ConfigFlightModel configFlightModel;
    @Nullable
    private String isPhone;
    @Nullable
    private String isMobilePhone;
    @Nullable
    private String isBirthDay;
    @Nullable
    private String isIdentityNumber;
    @Nullable
    private String isNationality;
    @Nullable
    private String isAddress;
    @Nullable
    private String isEmail;
    @Nullable
    private String isPostalCode;
    private boolean isFirstPassenger;

    RecyclerView form_baggage_recycler_view;
//
//    FlightFormAdultBaggageAdapter flightBaggageAdapter;
//    ArrayList<FlightFormBaggageModel> data = new ArrayList<>();


    MaterialEditText textBaggageAdult1, textBaggageAdult2, textBaggageAdult3;
    private String titleBaggageTemp1, titleBaggageTemp2, titleBaggageTemp3;
    // String sAdultTitleBaggage1, sAdultTitleBaggage2, sAdultTitleBaggage3;
    private CustomFontCheckBox checkboxBeliBagasi;
    LinearLayout linListBaggage, linMainBaggage1, linMainBaggage2, linMainBaggage3;
    TextView textViewTitleBaggage1, textViewTitleBaggage2, textViewTitleBaggage3;

    ImageView imageViewBaggage1, imageViewBaggage2, imageViewBaggage3;


    int positionBaggageCheck;
    int isBuyAdult = 0;


    MaterialEditText adultNationality, adultIssuingCountry, adultExpirationDate;
    ClearableEditText adultNoPassport;
    private ListCountryAdapter adapter;
    static String nasionalityKode;
    static String issuingNasionalityKode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_form_book_adult_activity);
        Intent intent = this.getIntent();
        if (intent != null)
            isNew = intent.getBooleanExtra(FlightKeyPreference.isNewFlight, false);
        isFirstPassenger = intent.getBooleanExtra("isFirstPassenger", false);
        sAdultTitle = intent.getStringExtra("adultTitle");
        titleTemp = intent.getStringExtra("adultTitleTemp");
        sAdultName = intent.getStringExtra("adultName");
        sAdultId = intent.getStringExtra("adultId");
        sAdultBorn = intent.getStringExtra("adultBornShow");
        dateBorn = intent.getStringExtra("adultBorn");
        sAdultMobilePhone = intent.getStringExtra("adultMobilePhone");
        sAdultEmail = intent.getStringExtra("adultEmail");

        sAdultNationality=intent.getStringExtra("adultNationality");
        nasionalityKode=intent.getStringExtra("adultNationalityKode");
        sAdultNoPassport=intent.getStringExtra("adultNoPassport");
        sAdultIssuingCountry=intent.getStringExtra("adultIssuingCountry");
        issuingNasionalityKode=intent.getStringExtra("adultIssuingCountryKode");
        sAdultExpirationDate=intent.getStringExtra("adultExpiredShow");
        dateExpired = intent.getStringExtra("adultExpired");


        isBuyAdult = intent.getIntExtra("adultCheckboxBeliBagasi", 0);
        positionBaggageCheck = intent.getIntExtra("adultTitleBaggagePosition", 0);
//            sAdultTitleBaggage1 = intent.getStringExtra("adultTitleBaggage1");
        titleBaggageTemp1 = intent.getStringExtra("titleBaggageTemp1");
//            sAdultTitleBaggage2 = intent.getStringExtra("adultTitleBaggage2");
        titleBaggageTemp2 = intent.getStringExtra("titleBaggageTemp2");
//            sAdultTitleBaggage3 = intent.getStringExtra("adultTitleBaggage3");
        titleBaggageTemp3 = intent.getStringExtra("titleBaggageTemp3");


        Log.d(TAG, "onCreate: isinternasional " + PreferenceClass.getInt(FlightKeyPreference.isInternational, 0));

        form_baggage_recycler_view = findViewById(R.id.form_baggage_recycler_view);
        toolbar = findViewById(R.id.toolbarForm);
        titleHeader = toolbar.findViewById(R.id.titleToolbar);
        adultTitle = findViewById(R.id.textTitleAdult);
        adultName = findViewById(R.id.textNameAdult);
//        adultName.setShowClearButton(true);

        adultId = findViewById(R.id.textIDAdult);
        adultBorn = findViewById(R.id.textBornAdult);
        adultBorn.setOnClickListener(this);
        adultMobilePhone = findViewById(R.id.textMobileAdult);
        adultMobilePhone.setHint(Html.fromHtml(getString(R.string.hint_no_handphone_passanger)));
        adultMobilePhone.setFloatingLabelText(Html.fromHtml(getString(R.string.hint_no_handphone)));
        adultEmail = findViewById(R.id.textEmailAdult);

        adultNationality = findViewById(R.id.textNationalityAdult);
        adultNationality.setOnClickListener(this);
        adultNoPassport = findViewById(R.id.textNoPassportAdult);
        adultIssuingCountry = findViewById(R.id.textIssuingCountryAdult);
        adultIssuingCountry.setOnClickListener(this);
        adultExpirationDate = findViewById(R.id.textExpirationDateAdult);
        adultExpirationDate .setOnClickListener(this);


        textBaggageAdult1 = findViewById(R.id.textBaggageAdult1);
        textBaggageAdult1.setOnTouchListener(this);
        textBaggageAdult2 = findViewById(R.id.textBaggageAdult2);
        textBaggageAdult2.setOnTouchListener(this);
        textBaggageAdult3 = findViewById(R.id.textBaggageAdult3);
        textBaggageAdult3.setOnTouchListener(this);

        actBatal = toolbar.findViewById(R.id.actionToolbar);
        titleHeader.setText("DATA PENUMPANG DEWASA");
        adultTitle.setOnTouchListener(this);
        InputFilter[] filter = new InputFilter[2];
        filter[0] = new InputFilter.AllCaps();
        filter[1] = new InputFilter.LengthFilter(100);
        adultName.setFilters(filter);
        actBatal.setText("Batal");
        actBatal.setOnClickListener(this);
        btnSelesai = findViewById(R.id.btn_selesai);
//        MaterialRippleLayout.on(btnSelesai).rippleOverlay(true)
//                .rippleAlpha(0.2f)
//                .rippleColor(R.color.md_orange_200)
//                .rippleHover(true)
//                .create();
        btnSelesai.setOnClickListener(this);
        adultName.setOnEditorActionListener(new DoneOnEditorActionListener());
        list = new String[]{"Tuan", "Nyonya", "Nona"};
        listTitle = new String[]{"Tn", "Ny", "Nona"};
        lpw = new ListPopupWindow(this);
        lpw.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, list));
        lpw.setAnchorView(adultTitle);
        lpw.setModal(true);
        lpw.setOnItemClickListener(this);


        linListBaggage = findViewById(R.id.linListBaggage);
        linMainBaggage1 = findViewById(R.id.linMainBaggage1);
        linMainBaggage2 = findViewById(R.id.linMainBaggage2);
        linMainBaggage3 = findViewById(R.id.linMainBaggage3);

        textViewTitleBaggage1 = findViewById(R.id.textViewTitleBaggage1);
        textViewTitleBaggage2 = findViewById(R.id.textViewTitleBaggage2);
        textViewTitleBaggage3 = findViewById(R.id.textViewTitleBaggage3);

        imageViewBaggage1 = findViewById(R.id.imageViewBaggage1);
        imageViewBaggage2 = findViewById(R.id.imageViewBaggage2);
        imageViewBaggage3 = findViewById(R.id.imageViewBaggage3);


        checkboxBeliBagasi = findViewById(R.id.checkboxBeliBagasi);
        checkboxBeliBagasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {
                if (((CheckBox) view).isChecked()) {
                    Log.d(TAG, "onClick: true ");
                    isBuyAdult = 1;
                    linListBaggage.setVisibility(View.VISIBLE);
                    closeKeyboard(FlightFormBookAdultActivity.this);
                } else {
                    Log.d(TAG, "onClick: false");
                    isBuyAdult = 0;
                    linListBaggage.setVisibility(View.GONE);
                    clearBaggage();

                }
            }
        });


//        listBaggage1 = showBaggage1(); //new String[]{"Tuan", "Nyonya", "Nona"};
//        listTitleBaggage1 = showTitleBaggage1();//new String[]{"Tn", "Ny", "Nona"};
//        listWeightBaggage1=showWeightBaggage1();
        lpw1 = new ListPopupWindow(this);
        lpw1.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, showBaggage1()));
        lpw1.setAnchorView(textBaggageAdult1);
        lpw1.setModal(true);
        lpw1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick cb1: " + position);
                // jsonObject1 = new JSONObject();
//                 jsonObject2 = new JSONObject();
//                 jsonObject3 = new JSONObject();

                positionBaggageCheck = position;
                String item = showBaggage1()[position];
                String itemTemp = showTitleBaggage1()[position];
                String weightTemp = showWeightBaggage1()[position];
                String kodeMaskapai = PreferenceClass.getString(FlightKeyPreference.baggageFlightNo1, "");
                textBaggageAdult1.setText(item);
                titleBaggageTemp1 = itemTemp;
                // baggage1="{\"kode_maskapai\":\"TP\"" + kodeMaskapai.substring(0, 2)+"\",\"baggage_key\":\"" +itemTemp+ "\",\"weight\":\"" +Integer.parseInt(weightTemp)+ "\"}";
                baggage1 = "{kode_maskapai:TP" + kodeMaskapai.substring(0, 2) + ",baggage_key:" + itemTemp + ",weight:" + Integer.parseInt(weightTemp) + "}";

                try {
                    jsonObject1 = new JSONObject(baggage1);
//                    jsonObject1.put("kode_maskapai", "TP" + kodeMaskapai.substring(0, 2));
//                    jsonObject1.put("baggage_key", itemTemp);
//                    jsonObject1.put("weight", Integer.parseInt(weightTemp));
                    Log.d(TAG, "onClickFormBaggage 0: " + jsonObject1.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (showBaggage2().length > 0) {
                    String item2 = showBaggage2()[position];
                    String itemTemp2 = showTitleBaggage2()[position];
                    String weightTemp2 = showWeightBaggage2()[position];
                    String kodeMaskapai2 = PreferenceClass.getString(FlightKeyPreference.baggageFlightNo2, "");
                    textBaggageAdult2.setText(item2);
                    titleBaggageTemp2 = itemTemp2;
                    baggage2 = "{kode_maskapai:TP" + kodeMaskapai2.substring(0, 2) + ",baggage_key:" + itemTemp2 + ",weight:" + Integer.parseInt(weightTemp2) + "}";
                    try {
                        jsonObject2 = new JSONObject(baggage2);
//                        jsonObject2.put("kode_maskapai", "TP" + kodeMaskapai2.substring(0, 2));
//                        jsonObject2.put("baggage_key", itemTemp2);
//                        jsonObject2.put("weight", Integer.parseInt(weightTemp2));
                        Log.d(TAG, "onClickFormBaggage 1: " + jsonObject2.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (showBaggage3().length > 0) {
                    String item3 = showBaggage3()[position];
                    String itemTemp3 = showTitleBaggage3()[position];
                    String weightTemp3 = showWeightBaggage3()[position];
                    String kodeMaskapai3 = PreferenceClass.getString(FlightKeyPreference.baggageFlightNo3, "");
                    textBaggageAdult3.setText(item3);
                    titleBaggageTemp3 = itemTemp3;
                    baggage3 = "{\"kode_maskapai\":\"TP\"" + kodeMaskapai3.substring(0, 2) + "\",\"baggage_key\":\"" + itemTemp3 + "\",\"weight\":\"" + Integer.parseInt(weightTemp3) + "\"}";
                    try {
                        jsonObject3 = new JSONObject(baggage3);
//                        jsonObject3.put("kode_maskapai", "TP" + kodeMaskapai3.substring(0, 2));
//                        jsonObject3.put("baggage_key", itemTemp3);
//                        jsonObject3.put("weight", Integer.parseInt(weightTemp3));
                        Log.d(TAG, "onClickFormBaggage 2: " + jsonObject3.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                lpw1.dismiss();
            }
        });


//        listBaggage2 = showBaggage2(); //new String[]{"Tuan", "Nyonya", "Nona"};
//        listTitleBaggage2 = showTitleBaggage2();// new String[]{"Tn", "Ny", "Nona"};
//        listWeightBaggage2=showWeightBaggage2();
        lpw2 = new ListPopupWindow(this);
        lpw2.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, showBaggage2()));
        lpw2.setAnchorView(textBaggageAdult2);

        lpw2.setModal(true);
        lpw2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                jsonObject1 = new JSONObject();
//                jsonObject2 = new JSONObject();
//                jsonObject3 = new JSONObject();
                positionBaggageCheck = position;
                String item = showBaggage2()[position];
                String itemTemp = showTitleBaggage2()[position];
                String weightTemp = showWeightBaggage2()[position];
                String kodeMaskapai = PreferenceClass.getString(FlightKeyPreference.baggageFlightNo2, "");
                textBaggageAdult2.setText(item);
                titleBaggageTemp2 = itemTemp;
                baggage2 = "{\"kode_maskapai\":\"TP\"" + kodeMaskapai.substring(0, 2) + "\",\"baggage_key\":\"" + itemTemp + "\",\"weight\":\"" + Integer.parseInt(weightTemp) + "\"}";
                try {
                    jsonObject2 = new JSONObject(baggage2);
//                    jsonObject2.put("kode_maskapai", "TP" + kodeMaskapai.substring(0, 2));
//                    jsonObject2.put("baggage_key", itemTemp);
//                    jsonObject2.put("weight", Integer.parseInt(weightTemp));
                    Log.d(TAG, "onClickFormBaggage 1: " + jsonObject2.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (showBaggage1().length > 0) {
                    String item1 = showBaggage1()[position];
                    String itemTemp1 = showTitleBaggage1()[position];
                    String weightTemp1 = showWeightBaggage1()[position];
                    String kodeMaskapai1 = PreferenceClass.getString(FlightKeyPreference.baggageFlightNo1, "");
                    textBaggageAdult1.setText(item1);
                    titleBaggageTemp1 = itemTemp1;
                    baggage1 = "{\"kode_maskapai\":\"TP\"" + kodeMaskapai1.substring(0, 2) + "\",\"baggage_key\":\"" + itemTemp1 + "\",\"weight\":\"" + Integer.parseInt(weightTemp1) + "\"}";
                    try {
                        jsonObject1 = new JSONObject(baggage1);
//                        jsonObject1.put("kode_maskapai", "TP" + kodeMaskapai1.substring(0, 2));
//                        jsonObject1.put("baggage_key", itemTemp1);
//                        jsonObject1.put("weight", Integer.parseInt(weightTemp1));
                        Log.d(TAG, "onClickFormBaggage 0: " + jsonObject1.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (showBaggage3().length > 0) {
                    String item3 = showBaggage3()[position];
                    String itemTemp3 = showTitleBaggage3()[position];
                    String weightTemp3 = showWeightBaggage3()[position];
                    String kodeMaskapai3 = PreferenceClass.getString(FlightKeyPreference.baggageFlightNo3, "");
                    textBaggageAdult3.setText(item3);
                    titleBaggageTemp3 = itemTemp3;
                    baggage3 = "{\"kode_maskapai\":\"TP\"" + kodeMaskapai3.substring(0, 2) + "\",\"baggage_key\":\"" + itemTemp3 + "\",\"weight\":\"" + Integer.parseInt(weightTemp3) + "\"}";
                    try {
                        jsonObject3 = new JSONObject(baggage3);
//                        jsonObject3.put("kode_maskapai", "TP" + kodeMaskapai3.substring(0, 2));
//                        jsonObject3.put("baggage_key", itemTemp3);
//                        jsonObject3.put("weight", Integer.parseInt(weightTemp3));
                        Log.d(TAG, "onClickFormBaggage 2: " + jsonObject3.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                lpw2.dismiss();
            }
        });


//        listBaggage3 = showBaggage3(); //new String[]{"Tuan", "Nyonya", "Nona"};
//        listTitleBaggage3 = showTitleBaggage3();// new String[]{"Tn", "Ny", "Nona"};
//        listWeightBaggage3=showWeightBaggage3();
        lpw3 = new ListPopupWindow(this);
        lpw3.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, showBaggage3()));
        lpw3.setAnchorView(textBaggageAdult3);

        lpw3.setModal(true);
        lpw3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                jsonObject1 = new JSONObject();
//                jsonObject2 = new JSONObject();
                //  jsonObject3 = new JSONObject();
                positionBaggageCheck = position;
                String item = showBaggage3()[position];
                String itemTemp = showTitleBaggage3()[position];
                String weightTemp = showWeightBaggage3()[position];
                String kodeMaskapai = PreferenceClass.getString(FlightKeyPreference.baggageFlightNo3, "");
                textBaggageAdult3.setText(item);
                titleBaggageTemp3 = itemTemp;
                baggage3 = "{\"kode_maskapai\":\"TP\"" + kodeMaskapai.substring(0, 2) + "\",\"baggage_key\":\"" + itemTemp + "\",\"weight\":\"" + Integer.parseInt(weightTemp) + "\"}";
                try {
                    jsonObject3 = new JSONObject(baggage3);
//                    jsonObject3.put("kode_maskapai", "TP" + kodeMaskapai.substring(0, 2));
//                    jsonObject3.put("baggage_key", itemTemp);
//                    jsonObject3.put("weight", Integer.parseInt(weightTemp));
                    Log.d(TAG, "onClickFormBaggage 2: " + jsonObject3.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (showBaggage2().length > 0) {
                    String item2 = showBaggage2()[position];
                    String itemTemp2 = showTitleBaggage2()[position];
                    String weightTemp2 = showWeightBaggage2()[position];
                    String kodeMaskapai2 = PreferenceClass.getString(FlightKeyPreference.baggageFlightNo2, "");
                    textBaggageAdult2.setText(item2);
                    titleBaggageTemp2 = itemTemp2;
                    baggage2 = "{\"kode_maskapai\":\"TP\"" + kodeMaskapai2.substring(0, 2) + "\",\"baggage_key\":\"" + itemTemp2 + "\",\"weight\":\"" + Integer.parseInt(weightTemp2) + "\"}";
                    try {
                        jsonObject2 = new JSONObject(baggage2);
//                        jsonObject2.put("kode_maskapai", "TP" + kodeMaskapai2.substring(0, 2));
//                        jsonObject2.put("baggage_key", itemTemp2);
//                        jsonObject2.put("weight", Integer.parseInt(weightTemp2));
                        Log.d(TAG, "onClickFormBaggage 1: " + jsonObject2.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (showBaggage1().length > 0) {
                    String item1 = showBaggage1()[position];
                    String itemTemp1 = showTitleBaggage1()[position];
                    String weightTemp1 = showWeightBaggage1()[position];
                    String kodeMaskapai1 = PreferenceClass.getString(FlightKeyPreference.baggageFlightNo1, "");
                    textBaggageAdult1.setText(item1);
                    titleBaggageTemp1 = itemTemp1;
                    baggage1 = "{\"kode_maskapai\":\"TP\"" + kodeMaskapai1.substring(0, 2) + "\",\"baggage_key\":\"" + itemTemp1 + "\",\"weight\":\"" + Integer.parseInt(weightTemp1) + "\"}";
                    try {
                        jsonObject1 = new JSONObject(baggage1);
//                        jsonObject1.put("kode_maskapai", "TP" + kodeMaskapai1.substring(0, 2));
//                        jsonObject1.put("baggage_key", itemTemp1);
//                        jsonObject1.put("weight", Integer.parseInt(weightTemp1));
                        Log.d(TAG, "onClickFormBaggage 0: " + jsonObject1.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                lpw3.dismiss();
            }
        });

        //data = new ArrayList<>();

//        try {
//            showListBaggage();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


//        form_baggage_recycler_view.setHasFixedSize(true);
//
//        //adapter = new FlightSubBaggageAdapter(this, allSectionDataPassagerModels);
//        flightBaggageAdapter = new FlightFormAdultBaggageAdapter(this, data, this);
//
//        form_baggage_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//
////        my_recycler_view.setAdapter(adapter);
//        form_baggage_recycler_view.setAdapter(flightBaggageAdapter);
        isConfig();
        clearAll(isNew);


        Context context;
//        lpwCountry=new ListPopupWindow(this);

//        Collections.sort(countries, new CountryComparator());
//
//        adapter = new ListCountryAdapter(FlightFormBookAdultActivity.this, R.layout.list_country, countries,FlightFormBookAdultActivity.this);
//        textNationalityAdult.setAdapter(adapter);
//
//
//        textNationalityAdult.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.d(TAG, "onTextChanged: "+s.toString());
//                adapter.notifyDataSetChanged();
//                adapter.getFilter().filter(s.toString());
//                adapter = new ListCountryAdapter(FlightFormBookAdultActivity.this, R.layout.list_country, adapter.filterData,FlightFormBookAdultActivity.this);
//                textNationalityAdult.setAdapter(adapter);
//
//
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });


//        textNationalityAdult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Object item = parent.getItemAtPosition(position);
//                if (item instanceof Country) {
//                    Country studentInfo = (Country) item;
//                    // do something with the studentInfo object
//                }
//            }
//        });


//        listCountry = new String[countries.size()];
//        for (Country country : countries) {
////            System.out.println(country);
//            listCountry.
//        }
//        listCodeCounty = new String[]{"Tn", "Ny", "Nona"};
//        lpwCountry = new ListPopupWindow(this);
//        lpwCountry.setAdapter(new ArrayAdapter<>(this,
//                android.R.layout.simple_gallery_item, countries));
//        lpwCountry.setAnchorView(textNationalityAdult);
//        lpwCountry.setModal(true);
//        lpwCountry.setOnItemClickListener(this);


//
//
//        for (Country country : countries) {
//            System.out.println(country);
//        }


//        if (0 != RecyclerView.NO_POSITION) {
//            if (flightBaggageAdapter.itemListDataAdapter.listener != null) {
//                //  flightBaggageAdapter.itemListDataAdapter.clearSelections();
        //        flightBaggageAdapter.itemListDataAdapter.toggleSelection(0);
//
//                // flightBaggageAdapter.itemListDataAdapter.listener.onClickBaggage(new SingleItemBaggageModel("0", "0", "No Baggage"), flightBaggageDetailModel);
////                    nominal = 500000;
////                    materialEditTextNominal.setText(String.valueOf(nominal));
//            }
//        }
        // flightBaggageAdapter.listener.onClickFormBaggage(flightBaggageDetailModel,0);

//        form_baggage_recycler_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView adapterView, View view, int position, long l) {
//                listAdapter.setCheckBox(position);
//            }
//        });
//        ArrayList singleSectionItems = data.get(0).getSectionDataPassagerModels();
////        flightForm_adult_sectionBaggageAdapter = new FlightForm_Adult_SectionBaggageAdapter(context, singleSectionItems);
//
//        FlightFormSectionListDataPassagerAdapter itemListDataAdapter = new FlightFormSectionListDataPassagerAdapter(this, singleSectionItems,  0);
//        FlightFormSectionListDataPassagerAdapter.SingleItemRowHolder x = new FlightFormSectionListDataPassagerAdapter();
//x.linMainListBaggage.setSelected(itemListDataAdapter.selectedItems.get(0,true));
//        itemListDataAdapter.setSelected(0,1);
        //  itemListDataAdapter.listener.onClickBaggage(flightBaggageDetailModel,0,0);
        int countBaggage = PreferenceClass.getInt(FlightKeyPreference.countBaggage, 0);

        if (countBaggage > 0) {
//            checkboxBeliBagasi.setVisibility(View.VISIBLE);//sementara
            if (countBaggage == 1) {
                linMainBaggage1.setVisibility(View.VISIBLE);
                Glide.with(this).load(PreferenceClass.getString(FlightKeyPreference.baggageFlightIcon1, "")).into(imageViewBaggage1);
                textViewTitleBaggage1.setText(PreferenceClass.getString(FlightKeyPreference.baggageRoute1, "") + " " + PreferenceClass.getString(FlightKeyPreference.baggageFlightNo1, ""));
            } else if (countBaggage == 2) {
                Glide.with(this).load(PreferenceClass.getString(FlightKeyPreference.baggageFlightIcon1, "")).into(imageViewBaggage1);
                Glide.with(this).load(PreferenceClass.getString(FlightKeyPreference.baggageFlightIcon2, "")).into(imageViewBaggage2);
                textViewTitleBaggage1.setText(PreferenceClass.getString(FlightKeyPreference.baggageRoute1, "") + " " + PreferenceClass.getString(FlightKeyPreference.baggageFlightNo1, ""));
                textViewTitleBaggage2.setText(PreferenceClass.getString(FlightKeyPreference.baggageRoute2, "") + " " + PreferenceClass.getString(FlightKeyPreference.baggageFlightNo2, ""));
                linMainBaggage1.setVisibility(View.VISIBLE);
                linMainBaggage2.setVisibility(View.VISIBLE);
            } else if (countBaggage == 3) {
                Glide.with(this).load(PreferenceClass.getString(FlightKeyPreference.baggageFlightIcon1, "")).into(imageViewBaggage1);
                Glide.with(this).load(PreferenceClass.getString(FlightKeyPreference.baggageFlightIcon2, "")).into(imageViewBaggage2);
                Glide.with(this).load(PreferenceClass.getString(FlightKeyPreference.baggageFlightIcon3, "")).into(imageViewBaggage3);

                textViewTitleBaggage1.setText(PreferenceClass.getString(FlightKeyPreference.baggageRoute1, "") + " " + PreferenceClass.getString(FlightKeyPreference.baggageFlightNo1, ""));
                textViewTitleBaggage2.setText(PreferenceClass.getString(FlightKeyPreference.baggageRoute2, "") + " " + PreferenceClass.getString(FlightKeyPreference.baggageFlightNo2, ""));
                textViewTitleBaggage3.setText(PreferenceClass.getString(FlightKeyPreference.baggageRoute3, "") + " " + PreferenceClass.getString(FlightKeyPreference.baggageFlightNo3, ""));
                linMainBaggage1.setVisibility(View.VISIBLE);
                linMainBaggage2.setVisibility(View.VISIBLE);
                linMainBaggage3.setVisibility(View.VISIBLE);
            }
        } else {
            checkboxBeliBagasi.setVisibility(View.GONE);
        }
        Log.d(TAG, "onCreate 1: " + PreferenceClass.getString(FlightKeyPreference.baggageFlightNo1, ""));
        Log.d(TAG, "onCreate 2: " + PreferenceClass.getString(FlightKeyPreference.baggageFlightNo2, ""));
    }

    JSONObject jsonObject1;
    JSONObject jsonObject2;
    JSONObject jsonObject3;

    String baggage1;
    String baggage2;
    String baggage3;
    FlightFormBaggageModel flightBaggageDetailModel;
//    private void showListBaggage() {
//        JSONArray detailTitleArr = PreferenceClass.getJSONArray(FlightKeyPreference.detailTitle);
//        List<FlightBaggageModel.Data> itemList = (List<FlightBaggageModel.Data>) MemoryStore.get(FlightKeyPreference.baggage);
//        String codeBaggage = "";
//
//        for (int i = 0; i < detailTitleArr.length(); i++) {
//             flightBaggageDetailModel = new FlightFormBaggageModel();
//            Log.d("BAGGAGE", "showData: " + i);
//
//            try {
//                JSONObject data = detailTitleArr.getJSONObject(i);
//
//                flightBaggageDetailModel.setFlightIcon(data.getString("flightIcon"));
//                flightBaggageDetailModel.setFlightName(data.getString("flightName"));
//                flightBaggageDetailModel.setFlightCode(data.getString("flightCode"));
//                flightBaggageDetailModel.setOrigin(data.getString("origin"));
//                flightBaggageDetailModel.setOriginName(data.getString("originName"));
//                flightBaggageDetailModel.setDestination(data.getString("destination"));
//                flightBaggageDetailModel.setDestinationName(data.getString("destinationName"));
//                flightBaggageDetailModel.setDurationDetail(data.getString("durationDetail"));
//                flightBaggageDetailModel.setTransitTime(data.getString("transitTime"));
//                flightBaggageDetailModel.setArrival(data.getString("arrival"));
//                flightBaggageDetailModel.setDepart(data.getString("depart"));
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
////            ArrayList<FlightFormBaggageModel> allSectionDataPassagerModels = new ArrayList<>();
////            for (String adult : adultPassanger) {
////                ArrayList<SectionDataPassagerModel> allSectionDataPassagerModels = new ArrayList<>();
//            //  jsonArrayAdults.put(adult);
//
//            //  SectionDataPassagerModel dm = new SectionDataPassagerModel();
//
////                String[] arrChild = adult.split(";");
//            //  dm.setHeaderTitle(arrChild[1] + " " + arrChild[2] + " " + arrChild[3]);
////                Log.d("BAGGAGE", "showData: adultPassanger "+arrChild[1] + " " + arrChild[2] + " " + arrChild[3]);
//            if (flightBaggageDetailModel.getFlightCode().substring(0, 2).equals("IW") || flightBaggageDetailModel.getFlightCode().substring(0, 2).equals("JT")) {
//                ArrayList<SingleItemBaggageModel> singleItem = new ArrayList<>();
//                singleItem.add(new SingleItemBaggageModel("0", "0", "No Baggage",flightBaggageDetailModel.getFlightCode(),true));
//                for (int j = 0; j < itemList.size(); j++) {
//                    String[] arrA = itemList.get(j).getBaggage_key().split("\\|");
//                    Log.d("BAGGAGE", "showData->: " + arrA[1]);
//                    try {
//                        if (arrA[1].equals(detailTitleArr.getJSONObject(i).getString("origin") + "-" + detailTitleArr.getJSONObject(i).getString("destination"))) {
//
//                            singleItem.add(new SingleItemBaggageModel(itemList.get(j).getPrice(), itemList.get(j).getWeight(), itemList.get(j).getBaggage_key(),flightBaggageDetailModel.getFlightCode(),false));
//                            Log.d("BAGGAGE", itemList.get(j).getPrice() + " " + itemList.get(j).getWeight() + " " + itemList.get(j).getBaggage_key()+" "+flightBaggageDetailModel.getFlightCode());
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//                // dm.setAllItemsInSection(singleItem);
//
////                allSectionDataPassagerModels.add(flightBaggageDetailModel);
//
//                flightBaggageDetailModel.setSectionDataPassagerModels(singleItem);
//                data.add(flightBaggageDetailModel);
//             //   flightBaggageAdapter.itemListDataAdapter.linMainListBaggage.setSelected(flightBaggageAdapter.itemListDataAdapter.selectedItems.get(0, true));
//
//            }
//
//
//        }
//       // flightBaggageAdapter.itemListDataAdapter.toggleSelection(0);
////        if (0 != RecyclerView.NO_POSITION) {
////            if (flightBaggageAdapter.listener != null) {
//////                clearSelections();
//////                toggleSelection(0);
////                flightBaggageAdapter.itemListDataAdapter.toggleSelection(0);
////
//////                Log.d("FormBaggage", "onClick: "+0+" "+position);
//////                Log.d("FormBaggage", "onClick: "+holder.linMainListBaggage.getTag(R.id.baggage_posisi));
//////                // Log.d("FormBaggage", "onClick: "+holder.linMainListBaggage.getLayoutPosition());
//////                        listener.onClickBaggage(singleItem,holder,adapterPos,flightFormBaggageModel);
////                flightBaggageAdapter.itemListDataAdapter.listener.onClickBaggage(flightBaggageDetailModel,0);
////                flightBaggageAdapter.itemListDataAdapter.listener.onClickBaggage(flightBaggageDetailModel,1);
////            }
////        }
//
//    }

    @NonNull
    private String[] showBaggage1() {

        JSONObject jsonObject = PreferenceClass.getJSONObject(FlightKeyPreference.baggage1);

        String key;
        String[] list1 = new String[0];
        try {

            JSONArray jsonArray = jsonObject.getJSONArray("data");
            list1 = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                String[] arrA = jsonArray.getJSONObject(i).get("baggage_key").toString().split("\\|");
                list1[i] = jsonArray.getJSONObject(i).get("weight") + "kg - Rp." + FormatString.CurencyIDR(jsonArray.getJSONObject(i).get("price").toString());
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list1;

    }

    @NonNull
    private String[] showTitleBaggage1() {

        JSONObject jsonObject = PreferenceClass.getJSONObject(FlightKeyPreference.baggage1);

        String key;
        String[] list1 = new String[0];

        try {

            JSONArray jsonArray = jsonObject.getJSONArray("data");
            list1 = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                // String[] arrA = jsonArray.getJSONObject(i).get("baggage_key").toString().split("\\|");
                list1[i] = jsonArray.getJSONObject(i).get("baggage_key").toString();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


//
        return list1;

    }

    @NonNull
    private String[] showWeightBaggage1() {

        JSONObject jsonObject = PreferenceClass.getJSONObject(FlightKeyPreference.baggage1);

        String key;
        String[] list1 = new String[0];

        try {

            JSONArray jsonArray = jsonObject.getJSONArray("data");
            list1 = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                // String[] arrA = jsonArray.getJSONObject(i).get("baggage_key").toString().split("\\|");
                list1[i] = jsonArray.getJSONObject(i).get("weight").toString();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


//
        return list1;

    }

    @NonNull
    private String[] showBaggage2() {

        JSONObject jsonObject = PreferenceClass.getJSONObject(FlightKeyPreference.baggage2);

        String key;
        String[] list2 = new String[0];
        try {

            JSONArray jsonArray = jsonObject.getJSONArray("data");
            list2 = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                String[] arrA = jsonArray.getJSONObject(i).get("baggage_key").toString().split("\\|");
                list2[i] = jsonArray.getJSONObject(i).get("weight") + "kg - Rp." + FormatString.CurencyIDR(jsonArray.getJSONObject(i).get("price").toString());
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list2;
    }

    @NonNull
    private String[] showTitleBaggage2() {

        JSONObject jsonObject = PreferenceClass.getJSONObject(FlightKeyPreference.baggage2);

        String key;
        String[] list2 = new String[0];
        try {

            JSONArray jsonArray = jsonObject.getJSONArray("data");
            list2 = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                // String[] arrA = jsonArray.getJSONObject(i).get("baggage_key").toString().split("\\|");
                list2[i] = jsonArray.getJSONObject(i).get("baggage_key").toString();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


//
        return list2;

    }

    @NonNull
    private String[] showWeightBaggage2() {

        JSONObject jsonObject = PreferenceClass.getJSONObject(FlightKeyPreference.baggage2);

        String key;
        String[] list2 = new String[0];
        try {

            JSONArray jsonArray = jsonObject.getJSONArray("data");
            list2 = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                // String[] arrA = jsonArray.getJSONObject(i).get("baggage_key").toString().split("\\|");
                list2[i] = jsonArray.getJSONObject(i).get("weight").toString();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


//
        return list2;

    }

    @NonNull
    private String[] showBaggage3() {

        JSONObject jsonObject = PreferenceClass.getJSONObject(FlightKeyPreference.baggage3);

        String key;
        String[] list3 = new String[0];
        try {

            JSONArray jsonArray = jsonObject.getJSONArray("data");
            list3 = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                String[] arrA = jsonArray.getJSONObject(i).get("baggage_key").toString().split("\\|");
                list3[i] = jsonArray.getJSONObject(i).get("weight") + "kg - Rp." + FormatString.CurencyIDR(jsonArray.getJSONObject(i).get("price").toString());
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list3;
    }

    @NonNull
    private String[] showTitleBaggage3() {

        JSONObject jsonObject = PreferenceClass.getJSONObject(FlightKeyPreference.baggage3);

        String key;
        String[] list2 = new String[0];
        try {

            JSONArray jsonArray = jsonObject.getJSONArray("data");
            list2 = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                // String[] arrA = jsonArray.getJSONObject(i).get("baggage_key").toString().split("\\|");
                list2[i] = jsonArray.getJSONObject(i).get("baggage_key").toString();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


//
        return list2;

    }

    @NonNull
    private String[] showWeightBaggage3() {

        JSONObject jsonObject = PreferenceClass.getJSONObject(FlightKeyPreference.baggage3);

        String key;
        String[] list2 = new String[0];
        try {

            JSONArray jsonArray = jsonObject.getJSONArray("data");
            list2 = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                // String[] arrA = jsonArray.getJSONObject(i).get("baggage_key").toString().split("\\|");
                list2[i] = jsonArray.getJSONObject(i).get("weight").toString();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


//
        return list2;

    }


//    private String[] showBaggage() {
//        JSONArray detailTitleArr = PreferenceClass.getJSONArray(FlightKeyPreference.detailTitle);
//        List<FlightBaggageModel.Data> itemList = (List<FlightBaggageModel.Data>) MemoryStore.get(FlightKeyPreference.baggage);
//        String[] list = new String[itemList.size()];
//        for (int i = 0; i < detailTitleArr.length(); i++) {
//            flightBaggageDetailModel = new FlightFormBaggageModel();
//            Log.d("BAGGAGE", "showData: " + i);
//
//            try {
//                JSONObject data = detailTitleArr.getJSONObject(i);
//
//                flightBaggageDetailModel.setFlightIcon(data.getString("flightIcon"));
//                flightBaggageDetailModel.setFlightName(data.getString("flightName"));
//                flightBaggageDetailModel.setFlightCode(data.getString("flightCode"));
//                flightBaggageDetailModel.setOrigin(data.getString("origin"));
//                flightBaggageDetailModel.setOriginName(data.getString("originName"));
//                flightBaggageDetailModel.setDestination(data.getString("destination"));
//                flightBaggageDetailModel.setDestinationName(data.getString("destinationName"));
//                flightBaggageDetailModel.setDurationDetail(data.getString("durationDetail"));
//                flightBaggageDetailModel.setTransitTime(data.getString("transitTime"));
//                flightBaggageDetailModel.setArrival(data.getString("arrival"));
//                flightBaggageDetailModel.setDepart(data.getString("depart"));
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//
//            if (flightBaggageDetailModel.getFlightCode().substring(0, 2).equals("IW") || flightBaggageDetailModel.getFlightCode().substring(0, 2).equals("JT")) {
//                ArrayList<SingleItemBaggageModel> singleItem = new ArrayList<>();
//                singleItem.add(new SingleItemBaggageModel("0", "0", "No Baggage", flightBaggageDetailModel.getFlightCode(), true));
//
//                list[0] = "0 kg Rp 0";
//                for (int j = 0; j < itemList.size(); j++) {
//                    String[] arrA = itemList.get(j).getBaggage_key().split("\\|");
//                    Log.d("BAGGAGE", "showData->: " + arrA[1]);
//                    try {
//                        if (arrA[1].equals(detailTitleArr.getJSONObject(i).getString("origin") + "-" + detailTitleArr.getJSONObject(i).getString("destination"))) {
//
//                            singleItem.add(new SingleItemBaggageModel(itemList.get(j).getPrice(), itemList.get(j).getWeight(), itemList.get(j).getBaggage_key(), flightBaggageDetailModel.getFlightCode(), false));
//                            Log.d("BAGGAGE", itemList.get(j).getPrice() + " " + itemList.get(j).getWeight() + " " + itemList.get(j).getBaggage_key() + " " + flightBaggageDetailModel.getFlightCode());
//                            list[j] = itemList.get(j).getWeight() + " " + itemList.get(j).getPrice();
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//
////                flightBaggageDetailModel.setSectionDataPassagerModels(singleItem);
////                data.add(flightBaggageDetailModel);
//
//            }
//
//
//        }
//
//        return list;
//    }

    private void clearAll(boolean isNew) {
        Log.d(TAG, "clearAll: " + isNew+" "+ PreferenceClass.getString(FlightKeyPreference.airportGroupAsal, "Domestik").equals("Domestik") +" "+ PreferenceClass.getString(FlightKeyPreference.airportGroupTujuan, "Domestik").equals("Domestik"));
        if (isNew) {
            if (isIdentityNumber.equals("0")) {
                adultId.setVisibility(View.GONE);
            } else {
                adultId.setVisibility(View.VISIBLE);
            }
            if (isBirthDay.equals("0")) {
                adultBorn.setVisibility(View.GONE);
            } else {
                adultBorn.setVisibility(View.VISIBLE);
            }
            if (PreferenceClass.getInt(FlightKeyPreference.isInternational, 0) == 0 ) {
                if (PreferenceClass.getString(FlightKeyPreference.airportGroupAsal, "Domestik").equals("Domestik") && PreferenceClass.getString(FlightKeyPreference.airportGroupTujuan, "Domestik").equals("Domestik")) {
                    adultNationality.setVisibility(View.GONE);
                    adultNoPassport.setVisibility(View.GONE);
                    adultIssuingCountry.setVisibility(View.GONE);
                    adultExpirationDate.setVisibility(View.GONE);
                }else if (PreferenceClass.getString(FlightKeyPreference.airportGroupAsal, "Domestik").equals("Internasional")&& PreferenceClass.getString(FlightKeyPreference.airportGroupTujuan, "Domestik").equals("Domestik")) {
                    adultNationality.setVisibility(View.VISIBLE);
                    adultBorn.setVisibility(View.VISIBLE);
                    adultNoPassport.setVisibility(View.VISIBLE);
                    adultIssuingCountry.setVisibility(View.VISIBLE);
                    adultExpirationDate.setVisibility(View.VISIBLE);
                }else  if (PreferenceClass.getString(FlightKeyPreference.airportGroupAsal, "Domestik").equals("Domestik")&& PreferenceClass.getString(FlightKeyPreference.airportGroupTujuan, "Domestik").equals("Internasional")) {
                    adultNationality.setVisibility(View.VISIBLE);
                    adultBorn.setVisibility(View.VISIBLE);
                    adultNoPassport.setVisibility(View.VISIBLE);
                    adultIssuingCountry.setVisibility(View.VISIBLE);
                    adultExpirationDate.setVisibility(View.VISIBLE);
                }
            }else{
                adultNationality.setVisibility(View.VISIBLE);
                adultBorn.setVisibility(View.VISIBLE);
                adultNoPassport.setVisibility(View.VISIBLE);
                adultIssuingCountry.setVisibility(View.VISIBLE);
                adultExpirationDate.setVisibility(View.VISIBLE);
            }
//
//            if (PreferenceClass.getString(FlightKeyPreference.airportGroupAsal, "Domestik").equals("Domestik")){
//                adultNationality.setVisibility(View.GONE);
//                adultNoPassport.setVisibility(View.GONE);
//                adultIssuingCountry.setVisibility(View.GONE);
//                adultExpirationDate.setVisibility(View.GONE);
//            } else {
//                adultNationality.setVisibility(View.VISIBLE);
//                adultBorn.setVisibility(View.VISIBLE);
//                adultNoPassport.setVisibility(View.VISIBLE);
//                adultIssuingCountry.setVisibility(View.VISIBLE);
//                adultExpirationDate.setVisibility(View.VISIBLE);
//            }
//            if(PreferenceClass.getString(FlightKeyPreference.airportGroupTujuan, "Domestik").equals("Domestik")) {
//                adultNationality.setVisibility(View.GONE);
//                adultNoPassport.setVisibility(View.GONE);
//                adultIssuingCountry.setVisibility(View.GONE);
//                adultExpirationDate.setVisibility(View.GONE);
//            } else {
//                adultNationality.setVisibility(View.VISIBLE);
//                adultBorn.setVisibility(View.VISIBLE);
//                adultNoPassport.setVisibility(View.VISIBLE);
//                adultIssuingCountry.setVisibility(View.VISIBLE);
//                adultExpirationDate.setVisibility(View.VISIBLE);
//            }

//            if (PreferenceClass.getString(FlightKeyPreference.airportGroupAsal, "").equals("Domestik") || PreferenceClass.getString(FlightKeyPreference.airportGroupTujuan, "").equals("Domestik")) {
//                adultNationality.setVisibility(View.GONE);
//                adultNoPassport.setVisibility(View.GONE);
//                adultIssuingCountry.setVisibility(View.GONE);
//                adultExpirationDate.setVisibility(View.GONE);
//            } else {
//                adultNationality.setVisibility(View.VISIBLE);
//                adultBorn.setVisibility(View.VISIBLE);
//                adultNoPassport.setVisibility(View.VISIBLE);
//                adultIssuingCountry.setVisibility(View.VISIBLE);
//                adultExpirationDate.setVisibility(View.VISIBLE);
//            }


            if (!isFirstPassenger || isEmail.equals("0")) {
                adultEmail.setVisibility(View.GONE);
            }

            if (!isFirstPassenger || isMobilePhone.equals("0")) {
                adultMobilePhone.setVisibility(View.GONE);
            }
            adultTitle.setText("");
            adultName.setText("");
            adultId.setText("");
            adultBorn.setText("");
            dateBorn="";
            adultEmail.setText("");
            adultMobilePhone.setText("");
            adultNationality.setText("");
            adultNoPassport.setText("");
            adultIssuingCountry.setText("");
            adultExpirationDate.setText("");
            dateExpired="";


            checkboxBeliBagasi.setChecked(false);
            textBaggageAdult1.setText("");
            textBaggageAdult2.setText("");
            textBaggageAdult3.setText("");

        } else {
            adultTitle.setText(sAdultTitle);
            adultName.setText(sAdultName);
            if (isIdentityNumber.equals("0")) {
                adultId.setVisibility(View.GONE);
            } else {
                adultId.setVisibility(View.VISIBLE);
            }
            adultId.setText(sAdultId);
            if (isBirthDay.equals("0")) {
                adultBorn.setVisibility(View.GONE);
            } else {
                adultBorn.setVisibility(View.VISIBLE);
            }
            adultBorn.setText(sAdultBorn);
            if (!isFirstPassenger || isEmail.equals("0")) {
                adultEmail.setVisibility(View.GONE);
            }
            if (!isFirstPassenger || isMobilePhone.equals("0")) {
                adultMobilePhone.setVisibility(View.GONE);
            }

            if (PreferenceClass.getInt(FlightKeyPreference.isInternational, 0) == 0) {
                adultNationality.setVisibility(View.GONE);
                adultNoPassport.setVisibility(View.GONE);
                adultIssuingCountry.setVisibility(View.GONE);
                adultExpirationDate.setVisibility(View.GONE);

            } else {
                adultNationality.setVisibility(View.VISIBLE);
                adultBorn.setVisibility(View.VISIBLE);
                adultNoPassport.setVisibility(View.VISIBLE);
                adultIssuingCountry.setVisibility(View.VISIBLE);
                adultExpirationDate.setVisibility(View.VISIBLE);
            }

         if (PreferenceClass.getString(FlightKeyPreference.airportGroupAsal, "Domestik").equals("Domestik")){
            adultNationality.setVisibility(View.GONE);
            adultNoPassport.setVisibility(View.GONE);
            adultIssuingCountry.setVisibility(View.GONE);
            adultExpirationDate.setVisibility(View.GONE);
         } else {
             adultNationality.setVisibility(View.VISIBLE);
             adultBorn.setVisibility(View.VISIBLE);
             adultNoPassport.setVisibility(View.VISIBLE);
             adultIssuingCountry.setVisibility(View.VISIBLE);
             adultExpirationDate.setVisibility(View.VISIBLE);
         }
         if(PreferenceClass.getString(FlightKeyPreference.airportGroupTujuan, "Domestik").equals("Domestik")) {
            adultNationality.setVisibility(View.GONE);
            adultNoPassport.setVisibility(View.GONE);
            adultIssuingCountry.setVisibility(View.GONE);
            adultExpirationDate.setVisibility(View.GONE);
         } else {
             adultNationality.setVisibility(View.VISIBLE);
             adultBorn.setVisibility(View.VISIBLE);
             adultNoPassport.setVisibility(View.VISIBLE);
             adultIssuingCountry.setVisibility(View.VISIBLE);
             adultExpirationDate.setVisibility(View.VISIBLE);
         }



adultNationality.setText(sAdultNationality);
            String flag = "https://www.countryflags.io/" + nasionalityKode + "/shiny/64.png";
            Glide.with(this).asBitmap().load(flag).encodeFormat(Bitmap.CompressFormat.PNG).encodeQuality(50).override(30, 30).diskCacheStrategy(DiskCacheStrategy.NONE).into(new SimpleTarget<Bitmap>() {


                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
    super.onLoadFailed(errorDrawable);
                }

                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> glideAnimation) {
                    Drawable drawable = new BitmapDrawable(getResources(), resource);
                    int h = drawable.getIntrinsicHeight();
                    int w = drawable.getIntrinsicWidth();
                    drawable.setBounds(0, 0, w, h);
                    adultNationality.setCompoundDrawables(drawable, null, null, null);
                }
            });

            adultIssuingCountry.setText(sAdultIssuingCountry);
            String flagIssuing = "https://www.countryflags.io/" + issuingNasionalityKode + "/shiny/64.png";
            Glide.with(this).asBitmap().load(flagIssuing).encodeFormat(Bitmap.CompressFormat.PNG).encodeQuality(50).override(30, 30).diskCacheStrategy(DiskCacheStrategy.NONE).into(new SimpleTarget<Bitmap>() {


                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
    super.onLoadFailed(errorDrawable);
                }

                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> glideAnimation) {
                    Drawable drawable = new BitmapDrawable(getResources(), resource);
                    int h = drawable.getIntrinsicHeight();
                    int w = drawable.getIntrinsicWidth();
                    drawable.setBounds(0, 0, w, h);
                    adultIssuingCountry.setCompoundDrawables(drawable, null, null, null);
                }
            });

            adultExpirationDate.setText(sAdultExpirationDate);
            adultNoPassport.setText(sAdultNoPassport);


            adultEmail.setText(sAdultEmail);
            adultMobilePhone.setText(sAdultMobilePhone);

//            flightBaggageAdapter.
            if (isBuyAdult == 1) {
                checkboxBeliBagasi.setChecked(true);
                linListBaggage.setVisibility(View.VISIBLE);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    checkboxBeliBagasi.performContextClick();
//                }else{
//                    checkboxBeliBagasi.performClick();
//                }
//                textBaggageAdult1.setText(sAdultTitleBaggage1);
//                textBaggageAdult2.setText(sAdultTitleBaggage2);
//                textBaggageAdult3.setText(sAdultTitleBaggage3);
                String item = showBaggage1()[positionBaggageCheck];
                String itemTemp = showTitleBaggage1()[positionBaggageCheck];
                textBaggageAdult1.setText(item);
                titleBaggageTemp1 = itemTemp;

                String weightTemp = showWeightBaggage1()[positionBaggageCheck];
                String kodeMaskapai = PreferenceClass.getString(FlightKeyPreference.baggageFlightNo1, "");

                // baggage1="{\"kode_maskapai\":\"TP\"" + kodeMaskapai.substring(0, 2)+"\",\"baggage_key\":\"" +itemTemp+ "\",\"weight\":\"" +Integer.parseInt(weightTemp)+ "\"}";
                baggage1 = "{kode_maskapai:TP" + kodeMaskapai.substring(0, 2) + ",baggage_key:" + itemTemp + ",weight:" + Integer.parseInt(weightTemp) + "}";

                try {
                    jsonObject1 = new JSONObject(baggage1);
//                    jsonObject1.put("kode_maskapai", "TP" + kodeMaskapai.substring(0, 2));
//                    jsonObject1.put("baggage_key", itemTemp);
//                    jsonObject1.put("weight", Integer.parseInt(weightTemp));
                    Log.d(TAG, "onClickFormBaggage 0: " + jsonObject1.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (showBaggage2().length > 0) {
                    String item2 = showBaggage2()[positionBaggageCheck];
                    String itemTemp2 = showTitleBaggage2()[positionBaggageCheck];
                    textBaggageAdult2.setText(item2);
                    titleBaggageTemp2 = itemTemp2;

                    String weightTemp2 = showWeightBaggage2()[positionBaggageCheck];
                    String kodeMaskapai2 = PreferenceClass.getString(FlightKeyPreference.baggageFlightNo2, "");

                    baggage2 = "{kode_maskapai:TP" + kodeMaskapai2.substring(0, 2) + ",baggage_key:" + itemTemp2 + ",weight:" + Integer.parseInt(weightTemp2) + "}";
                    try {
                        jsonObject2 = new JSONObject(baggage2);
//                        jsonObject2.put("kode_maskapai", "TP" + kodeMaskapai2.substring(0, 2));
//                        jsonObject2.put("baggage_key", itemTemp2);
//                        jsonObject2.put("weight", Integer.parseInt(weightTemp2));
                        Log.d(TAG, "onClickFormBaggage 1: " + jsonObject2.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                if (showBaggage3().length > 0) {
                    String item3 = showBaggage3()[positionBaggageCheck];
                    String itemTemp3 = showTitleBaggage3()[positionBaggageCheck];
                    textBaggageAdult3.setText(item3);
                    titleBaggageTemp3 = itemTemp3;

                    String weightTemp3 = showWeightBaggage3()[positionBaggageCheck];
                    String kodeMaskapai3 = PreferenceClass.getString(FlightKeyPreference.baggageFlightNo3, "");

                    baggage3 = "{\"kode_maskapai\":\"TP\"" + kodeMaskapai3.substring(0, 2) + "\",\"baggage_key\":\"" + itemTemp3 + "\",\"weight\":\"" + Integer.parseInt(weightTemp3) + "\"}";
                    try {
                        jsonObject3 = new JSONObject(baggage3);
//                        jsonObject3.put("kode_maskapai", "TP" + kodeMaskapai3.substring(0, 2));
//                        jsonObject3.put("baggage_key", itemTemp3);
//                        jsonObject3.put("weight", Integer.parseInt(weightTemp3));
                        Log.d(TAG, "onClickFormBaggage 2: " + jsonObject3.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    private void clearBaggage() {
        if (showBaggage1().length > 0) {
            if (textBaggageAdult1.getEditableText().length() > 0) {
                textBaggageAdult1.setText("");


            }
        }

        if (showBaggage2().length > 0) {
            if (textBaggageAdult2.getEditableText().length() > 0) {
                textBaggageAdult2.setText("");

            }
        }


        if (showBaggage3().length > 0) {
            if (textBaggageAdult3.getEditableText().length() > 0) {
                textBaggageAdult3.setText("");

            }
        }
    }

    private void isConfig() {

        configFlightModel = gson.fromJson(PreferenceClass.getJSONObject(FlightKeyPreference.dataConfigFlight).toString(), ConfigFlightModel.class);
        //   JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < configFlightModel.getConfigurations().size(); i++) {
            ConfigFlightModel.Configurations jsonObject = configFlightModel.getConfigurations().get(i);
            Log.d(TAG, "isConfig: " + PreferenceClass.getString(FlightKeyPreference.airlineCode, ""));
            if (jsonObject.getAirline().equals(PreferenceClass.getString(FlightKeyPreference.airlineCode, ""))) {
                isPhone = jsonObject.getIsPhone();
                isMobilePhone = jsonObject.getIsMobilePhone();
                isBirthDay = jsonObject.getIsBirthDay();
                isIdentityNumber = jsonObject.getIsIdentityNumber();
                isNationality = jsonObject.getIsNationality();
                isAddress = jsonObject.getIsAddress();
                isEmail = jsonObject.getIsEmail();
                isPostalCode = jsonObject.getIsPostalCode();
                Log.d(TAG, "isConfig: " + jsonObject.getAirline() + " " + PreferenceClass.getString(FlightKeyPreference.airlineCode, "") + " " + jsonObject.getIsBirthDay());
                break;
            }
        }
//        resultConf = (List<ConfigFlightModel>) MemoryStore.get( "configFlight");
//        for (int i = 0; i < resultConf.size(); i++) {
//            if (resultConf.get(i).getAirline().equals(MemoryStore.get( "airlineCode"))) {
//                isPhone = resultConf.get(i).getIsPhone();
//                isMobilePhone = resultConf.get(i).getIsMobilePhone();
//                isBirthDay = resultConf.get(i).getIsBirthDay();
//                isIdentityNumber = resultConf.get(i).getIsIdentityNumber();
//                isNationality = resultConf.get(i).getIsNationality();
//                isAddress = resultConf.get(i).getIsAddress();
//                isEmail = resultConf.get(i).getIsEmail();
//                isPostalCode = resultConf.get(i).getIsPostalCode();
//                //Log.d(TAG, "isConfig: " + resultConf.get(i).getAirline() + " " + MemoryStore.get(FormBookAdultActivity.this, "airlineCode") + " " + resultConf.get(i).getIsBirthDay());
//                break;
//            }
//        }
    }

    @Override
    public void onClick(@NonNull View v) {
        int id = v.getId();
        if (id == R.id.actionToolbar) {
            onBackPressed();
        } else if (id == R.id.btn_selesai) {

            selesai();
        } else if (id == R.id.textBornAdult) {
            openCalendar();
        } else if (id == R.id.textExpirationDateAdult) {
            openCalendarExpired();
        } else if (id == R.id.textNationalityAdult) {
            Intent intent = new Intent(this, FlightCountryActivity.class);
            startActivityForResult(intent, TravelActionCode.NASIONALITY);
        } else if (id == R.id.textIssuingCountryAdult) {
            Intent intent = new Intent(this, FlightCountryActivity.class);
            startActivityForResult(intent, TravelActionCode.ISSUING_NASIONALITY);
        }
    }

    @Override
    public void onBackPressed() {
        closeKeyboard(this);

        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = list[position];
        String itemTemp = listTitle[position];
        adultTitle.setText(item);
        titleTemp = itemTemp;
        lpw.dismiss();
    }

    @Override
    public boolean onTouch(@NonNull View v, @NonNull MotionEvent event) {
        int id = v.getId();
        if (id == R.id.textTitleAdult) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                lpw.show();
                return true;
            }
        } else if (id == R.id.textBaggageAdult1) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                lpw1.show();
                return true;
            }
        } else if (id == R.id.textBaggageAdult2) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                lpw2.show();
                return true;
            }
        } else if (id == R.id.textBaggageAdult3) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                lpw3.show();
                return true;
            }
        }
        return false;
    }

    private void openCalendar() {

            (new DatePickerFragment()).show(getFragmentManager(), String.valueOf(AlertDialog.THEME_HOLO_LIGHT));
    }
 private void openCalendarExpired() {
        (new ExpiredDatePickerFragment()).show(getFragmentManager(), String.valueOf(AlertDialog.THEME_HOLO_LIGHT));
    }

//    @Override
//    public void onClickCountry(Country produk) {
//        Log.d(TAG, "onClickCountry: "+produk.getCode());
//    }


//    @Override
//    public void onClickFormBaggage(SingleItemBaggageModel baggage, FlightFormSectionListDataPassagerAdapter.SingleItemRowHolder holder, int adapterPos, FlightFormBaggageModel flightFormBaggageModel) {
//    public void onClickFormBaggage(FlightFormBaggageModel flightFormBaggageModel, int row, int col) {
//        // Log.d(TAG, "onClickFormBaggage: " + holder.radioButtonBaggage.getTag(R.id.radioGroupKey) + " " + adapterPos);
//        //  Log.d(TAG, "onClickFormBaggage: " + holder.radioButtonBaggage.getTag(R.id.radioGroupKey) + " " + position);
//
////       if(holder.linMainListBaggage.getTag(R.id.baggage_posisi).equals(baggage.getBaggage_key())){
////
////       }
//
////        if (row == 0) {
//////            Log.d(TAG, "onClickFormBaggage: 1");
////            // JSONObject jsonObject=new JSONObject();
////            try {
////                jsonObject1.put("kode_maskapai", "TP" + flightFormBaggageModel.getFlightCode().substring(0, 2));
////                jsonObject1.put("baggage_key", flightFormBaggageModel.getSectionDataPassagerModels().get(row).getBaggage_key());
////                jsonObject1.put("weight", flightFormBaggageModel.getSectionDataPassagerModels().get(row).getWeight());
////                Log.d(TAG, "onClickFormBaggage 0: " + jsonObject1.toString());
////            } catch (JSONException e) {
////                e.printStackTrace();
////            }
////        } else if (row == 1) {
//////            Log.d(TAG, "onClickFormBaggage: 2");
//////            JSONObject jsonObject=new JSONObject();
////            try {
////                jsonObject2.put("kode_maskapai", "TP" + flightFormBaggageModel.getFlightCode().substring(0, 2));
////                jsonObject2.put("baggage_key", flightFormBaggageModel.getSectionDataPassagerModels().get(row).getBaggage_key());
////                jsonObject2.put("weight", flightFormBaggageModel.getSectionDataPassagerModels().get(row).getWeight());
////                Log.d(TAG, "onClickFormBaggage 1: " + jsonObject2.toString());
////            } catch (JSONException e) {
////                e.printStackTrace();
////            }
////        } else if (row == 2) {
//////            Log.d(TAG, "onClickFormBaggage: 3");
//////            JSONObject jsonObject=new JSONObject();
////            try {
////                jsonObject3.put("kode_maskapai", "TP" + flightFormBaggageModel.getFlightCode().substring(0, 2));
////                jsonObject3.put("baggage_key", flightFormBaggageModel.getSectionDataPassagerModels().get(row).getBaggage_key());
////                jsonObject3.put("weight", flightFormBaggageModel.getSectionDataPassagerModels().get(row).getWeight());
////                Log.d(TAG, "onClickFormBaggage 2: " + jsonObject3.toString());
////            } catch (JSONException e) {
////                e.printStackTrace();
////            }
////        }
//
//
//    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @NonNull
        SimpleDateFormat formatterFlight = new SimpleDateFormat("MM/dd/yyyy", SBFApplication.config.locale);
        @NonNull
        SimpleDateFormat formatterBornShow = new SimpleDateFormat("dd MMMM yyyy", SBFApplication.config.locale);

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance(SBFApplication.config.locale);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dpd = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
            calendar.add(Calendar.YEAR, -12);
            dpd.getDatePicker().setMaxDate(calendar.getTimeInMillis());
            return dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            TextView tv = getActivity().findViewById(R.id.textBornAdult);
            Calendar cal = Calendar.getInstance(SBFApplication.config.locale);
            cal.setTimeInMillis(0);
            cal.set(year, month, day, 0, 0, 0);
            Date chosenDate = cal.getTime();
            String formattedDateShow = formatterBornShow.format(chosenDate);
            String formattedDate = formatterFlight.format(chosenDate);
            tv.setText(formattedDateShow);
            dateBorn = formattedDate;
        }
    }
    public static class ExpiredDatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @NonNull
        SimpleDateFormat formatterFlight = new SimpleDateFormat("MM/dd/yyyy", new Locale("id"));
        @NonNull
        SimpleDateFormat formatterBornShow = new SimpleDateFormat("dd MMMM yyyy", new Locale("id"));

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance(new Locale("id"));
//            int year = calendar.get(Calendar.YEAR);
//            int month = calendar.get(Calendar.MONTH);
//            int day = calendar.get(Calendar.DAY_OF_MONTH);
            String[] departureDateFlight=PreferenceClass.getString(FlightKeyPreference.departureDateFlight,"").split("-");
            int year=Integer.parseInt(departureDateFlight[0]);
            int month=Integer.parseInt(departureDateFlight[1]);
            int day=Integer.parseInt(departureDateFlight[2]);

            calendar.add(Calendar.DAY_OF_MONTH, day);
            calendar.add(Calendar.MONTH, month);
            calendar.add(Calendar.YEAR, year);
            calendar.set(year, month, day, 0, 0, 0);
            Log.d(TAG, "onCreateDialog: "+year+" "+month+" "+day);
            DatePickerDialog dpd = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
//            calendar.add(Calendar.MONTH, month);
            calendar.add(Calendar.MONTH, +6);
            dpd.getDatePicker().setMinDate(calendar.getTimeInMillis());
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                dpd.setTitle("Tanggal Lahir Dewasa");//Prevent Date picker from creating extra Title.!
            }
            return dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            TextView tv = getActivity().findViewById(R.id.textExpirationDateAdult);
            Calendar cal = Calendar.getInstance(new Locale("id"));
            cal.setTimeInMillis(0);
            cal.set(year, month, day, 0, 0, 0);
            Date chosenDate = cal.getTime();
            String formattedDateShow = formatterBornShow.format(chosenDate);
            String formattedDate = formatterFlight.format(chosenDate);
            tv.setText(formattedDateShow);
            dateExpired = formattedDate;
        }
    }

    private void selesai() {
        closeKeyboard(this);
        if (!Validate.checkEmptyEditText(adultTitle, "Tidak Boleh Kosong")) {
            adultTitle.setAnimation(animShake);
            adultTitle.startAnimation(animShake);
            getar();
            return;
        }
        if (!Validate.checkEmptyEditText(adultName, "Tidak Boleh Kosong")) {
            adultName.setAnimation(animShake);
            adultName.startAnimation(animShake);
            getar();
            return;
        }
        if (adultId.getVisibility() == View.VISIBLE) {
            if (!Validate.checkEmptyEditText(adultId, "Tidak Boleh Kosong")) {
                adultId.setAnimation(animShake);
                adultId.startAnimation(animShake);
                getar();
                return;
            }
        }
        if (adultBorn.getVisibility() == View.VISIBLE) {
            if (!Validate.checkEmptyEditText(adultBorn, "Tidak Boleh Kosong")) {
                adultBorn.setAnimation(animShake);
                adultBorn.startAnimation(animShake);
                getar();
                return;
            }
        }


        if (adultNationality.getVisibility() == View.VISIBLE) {
            if(!Validate.checkEmptyEditText(adultNationality, "Tidak Boleh Kosong")) {
                adultNationality.setAnimation(animShake);
                adultNationality.startAnimation(animShake);
                getar();
                return;
            }
        }

        if (adultNoPassport.getVisibility() == View.VISIBLE) {
            if(!Validate.checkEmptyEditText(adultNoPassport, "Tidak Boleh Kosong")) {
                adultNoPassport.setAnimation(animShake);
                adultNoPassport.startAnimation(animShake);
                getar();
                return;
            }else if(!adultNoPassport.isCharactersCountValid()){
                adultNoPassport.setAnimation(animShake);
                adultNoPassport.startAnimation(animShake);
                adultNoPassport.setError("Nomor Passport kurang dari minimun");
                getar();
                return;
            }
        }
        if (adultIssuingCountry.getVisibility() == View.VISIBLE) {
            if(!Validate.checkEmptyEditText(adultIssuingCountry, "Tidak Boleh Kosong")) {
                adultIssuingCountry.setAnimation(animShake);
                adultIssuingCountry.startAnimation(animShake);
                getar();
                return;
            }
        }
        if (adultExpirationDate.getVisibility() == View.VISIBLE) {
            if(!Validate.checkEmptyEditText(adultExpirationDate, "Tidak Boleh Kosong")) {
                adultExpirationDate.setAnimation(animShake);
                adultExpirationDate.startAnimation(animShake);
                getar();
                return;
            }
        }

        if (adultEmail.getVisibility() == View.VISIBLE) {
            if (!Validate.checkValidEmailEditText(adultEmail, "Format Email Salah")) {
                adultEmail.setAnimation(animShake);
                adultEmail.startAnimation(animShake);
                getar();
                return;
            } else if (!Validate.checkEmptyEditText(adultEmail, "Tidak Boleh Kosong")) {
                adultEmail.setAnimation(animShake);
                adultEmail.startAnimation(animShake);
                getar();
                return;
            }
        }

        if (adultMobilePhone.getVisibility() == View.VISIBLE) {
            if (!Validate.checkEmptyEditText(adultMobilePhone, "Tidak Boleh Kosong")) {
                adultMobilePhone.setAnimation(animShake);
                adultMobilePhone.startAnimation(animShake);
                getar();
                return;
            } else if (!adultMobilePhone.getText().toString().substring(0, 2).equalsIgnoreCase("08")) {
                adultMobilePhone.setError("No Handphone Anda Tidak Valid");
                adultMobilePhone.setAnimation(animShake);
                adultMobilePhone.startAnimation(animShake);
                getar();
                return;
            } else if (adultMobilePhone.getText().toString().length() < 8) {
                adultMobilePhone.setError("No Handphone harus terdiri dari 8-16 angka");
                adultMobilePhone.setAnimation(animShake);
                adultMobilePhone.startAnimation(animShake);
                getar();
                return;
            }
        }



        JSONArray baggage = new JSONArray();
        ArrayList<String> arrBaggage = new ArrayList<>();
        if (checkboxBeliBagasi.isChecked()) {
            if (showBaggage1().length > 0) {
                if (!Validate.checkEmptyEditText(textBaggageAdult1, "Tidak Boleh Kosong")) {
                    textBaggageAdult1.setAnimation(animShake);
                    textBaggageAdult1.startAnimation(animShake);
                    getar();
                    return;
                }
                baggage.put(jsonObject1);
                arrBaggage.add(baggage1);

            }


            if (showBaggage2().length > 0) {
                if (!Validate.checkEmptyEditText(textBaggageAdult2, "Tidak Boleh Kosong")) {
                    textBaggageAdult2.setAnimation(animShake);
                    textBaggageAdult2.startAnimation(animShake);
                    getar();
                    return;
                }

                baggage.put(jsonObject2);
                arrBaggage.add(baggage2);

            }


            if (showBaggage3().length > 0) {
                if (!Validate.checkEmptyEditText(textBaggageAdult3, "Tidak Boleh Kosong")) {
                    textBaggageAdult3.setAnimation(animShake);
                    textBaggageAdult3.startAnimation(animShake);
                    getar();
                    return;
                }

                baggage.put(jsonObject3);
                arrBaggage.add(baggage3);
            }
        }


        Intent i = new Intent();
        i.putExtra("adultTitle", adultTitle.getText().toString());
        i.putExtra("adultTitleTemp", titleTemp);
        i.putExtra("adultName", adultName.getText().toString().replaceAll("[']", "`"));
        i.putExtra("adultId", adultId.getText().toString());
        i.putExtra("adultEmail", adultEmail.getText().toString());
        i.putExtra("adultMobilePhone", adultMobilePhone.getText().toString());
        i.putExtra("adultBornShow", adultBorn.getText().toString());
        i.putExtra("adultBorn", dateBorn);

        i.putExtra("adultNoPassport", adultNoPassport.getText().toString());
        i.putExtra("adultNationality", adultNationality.getText().toString());
        i.putExtra("adultNationalityKode", nasionalityKode);
        i.putExtra("adultIssuingCountry", adultIssuingCountry.getText().toString());
        i.putExtra("adultIssuingCountryKode", issuingNasionalityKode);
        i.putExtra("adultExpiredShow", adultExpirationDate.getText().toString());
        i.putExtra("adultExpired", dateExpired);


        i.putExtra("adultCheckboxBeliBagasi", isBuyAdult);


//        i.putExtra("adultTitleBaggage1", textBaggageAdult1.getEditableText());
//        i.putExtra("adultTitleBaggage2", textBaggageAdult2.getEditableText());
//        i.putExtra("adultTitleBaggage3", textBaggageAdult3.getEditableText());

        i.putExtra("adultTitleBaggageTemp1", titleBaggageTemp1);
        i.putExtra("adultTitleBaggageTemp2", titleBaggageTemp2);
        i.putExtra("adultTitleBaggageTemp3", titleBaggageTemp3);
        i.putExtra("adultTitleBaggagePosition", positionBaggageCheck);
        i.putExtra("adultBaggage", baggage.toString());
        i.putExtra("adultBaggageArr", arrBaggage);

        i.putExtra(FlightKeyPreference.isNewFlight, isNew);
        i.putExtra("status", "adult");
        setResult(Activity.RESULT_OK, i);
        finish();
    }


    class DoneOnEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(@NonNull TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                btnSelesai.performClick();
            }
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == TravelActionCode.NASIONALITY) {
            adultNationality.setText(data.getStringExtra("countryNama"));
            nasionalityKode = data.getStringExtra("countryKode");
            String flag = "https://www.countryflags.io/" + nasionalityKode + "/shiny/64.png";
            Glide.with(this).asBitmap().load(flag).encodeFormat(Bitmap.CompressFormat.PNG).encodeQuality(50).override(30, 30).diskCacheStrategy(DiskCacheStrategy.NONE).into(new SimpleTarget<Bitmap>() {


                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
    super.onLoadFailed(errorDrawable);
                }

                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> glideAnimation) {
                    Drawable drawable = new BitmapDrawable(getResources(), resource);
                    int h = drawable.getIntrinsicHeight();
                    int w = drawable.getIntrinsicWidth();
                    drawable.setBounds(0, 0, w, h);
                    adultNationality.setCompoundDrawables(drawable, null, null, null);
                }
            });

        } else if (resultCode == Activity.RESULT_OK && requestCode == TravelActionCode.ISSUING_NASIONALITY) {
            adultIssuingCountry.setText(data.getStringExtra("countryNama"));
            issuingNasionalityKode = data.getStringExtra("countryKode");
            String flag = "https://www.countryflags.io/" + issuingNasionalityKode + "/shiny/64.png";
            Glide.with(this).asBitmap().load(flag).encodeFormat(Bitmap.CompressFormat.PNG).encodeQuality(50).override(30, 30).diskCacheStrategy(DiskCacheStrategy.NONE).into(new SimpleTarget<Bitmap>() {


                @Override
                public void onLoadFailed(@Nullable Drawable errorDrawable) {
    super.onLoadFailed(errorDrawable);
                }

                @Override
                public void onResourceReady(Bitmap resource, Transition<? super Bitmap> glideAnimation) {
                    Drawable drawable = new BitmapDrawable(getResources(), resource);
                    int h = drawable.getIntrinsicHeight();
                    int w = drawable.getIntrinsicWidth();
                    drawable.setBounds(0, 0, w, h);
                    adultIssuingCountry.setCompoundDrawables(drawable, null, null, null);
                }
            });
        }


    }
}

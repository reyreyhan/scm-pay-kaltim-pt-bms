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
import com.bm.main.pos.R;
import com.bm.main.pos.SBFApplication;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bm.main.single.ftl.flight.constants.FlightKeyPreference;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
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

public class FlightFormBookChildActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener,
        AdapterView.OnItemClickListener {
    private static final String TAG = FlightFormBookChildActivity.class.getSimpleName();
    TextView titleHeader, actBatal;
    MaterialEditText childTitle, childBorn;
    ClearableEditText childName;
    String sChildTitle, sChildName, sChildBorn;
    String sChildNationality, sChildNoPassport, sChildIssuingCountry, sChildExpirationDate;
    String titleTemp;
    static String dateBorn;
    static String dateExpired;
    AppCompatButton btnSelesai;
    private ListPopupWindow lpw;
    private ListPopupWindow lpw1;
    private ListPopupWindow lpw2;
    private ListPopupWindow lpw3;
    private String[] list;
    private String[] listTitle;

//    private String[] listBaggage1;
//    private String[] listTitleBaggage1;
//    private String[] listWeightBaggage1;
//
//
//    private String[] listBaggage2;
//    private String[] listTitleBaggage2;
//    private String[] listWeightBaggage2;
//
//    private String[] listBaggage3;
//    private String[] listTitleBaggage3;
//    private String[] listWeightBaggage3;

    boolean isNew=false;
    //    private String sChildTitleBaggage1,sChildTitleBaggage2,sChildTitleBaggage3;
    private String titleBaggageTemp1, titleBaggageTemp2, titleBaggageTemp3;

    MaterialEditText textBaggageChild1, textBaggageChild2, textBaggageChild3;


    private CustomFontCheckBox checkboxBeliBagasi;
    LinearLayout linListBaggage, linMainBaggage1, linMainBaggage2, linMainBaggage3;
    TextView textViewTitleBaggage1, textViewTitleBaggage2, textViewTitleBaggage3;

    ImageView imageViewBaggage1, imageViewBaggage2, imageViewBaggage3;

    int positionBaggageCheck;
    int isBuyChild =0;// "false";


    MaterialEditText childNationality,childIssuingCountry, childExpirationDate;
    ClearableEditText childNoPassport;
   // private ListCountryAdapter adapter;
    static String nasionalityKode;
    static String issuingNasionalityKode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_form_book_child_activity);
        Intent intent = this.getIntent();
        if (intent != null)
            isNew = intent.getBooleanExtra(FlightKeyPreference.isNewFlight, false);
            sChildTitle = intent.getStringExtra("childTitle");
            titleTemp = intent.getStringExtra("childTitleTemp");
            sChildName = intent.getStringExtra("childName");
            sChildBorn = intent.getStringExtra("childBornShow");
            dateBorn = intent.getStringExtra("childBorn");


        sChildNationality=intent.getStringExtra("childNationality");
        nasionalityKode=intent.getStringExtra("childNationalityKode");
        sChildNoPassport=intent.getStringExtra("childNoPassport");
        sChildIssuingCountry=intent.getStringExtra("childIssuingCountry");
        issuingNasionalityKode=intent.getStringExtra("childIssuingCountryKode");
        sChildExpirationDate=intent.getStringExtra("childExpiredShow");
        dateExpired = intent.getStringExtra("childExpired");

            isBuyChild = intent.getIntExtra("childCheckboxBeliBagasi",0);
            positionBaggageCheck = intent.getIntExtra("childTitleBaggagePosition", 0);
//            sChildTitleBaggage1 = intent.getStringExtra("childTitleBaggage1");
            titleBaggageTemp1 = intent.getStringExtra("titleBaggageTemp1");
//            sChildTitleBaggage2 = intent.getStringExtra("childTitleBaggage2");
            titleBaggageTemp2 = intent.getStringExtra("titleBaggageTemp2");
//            sChildTitleBaggage3 = intent.getStringExtra("childTitleBaggage3");
            titleBaggageTemp3 = intent.getStringExtra("titleBaggageTemp3");




        toolbar = findViewById(R.id.toolbarForm);
        titleHeader = toolbar.findViewById(R.id.titleToolbar);
        childTitle = findViewById(R.id.textTitleChild);
        childName = findViewById(R.id.textNameChild);
        childBorn = findViewById(R.id.textBornChild);
        childBorn.setOnClickListener(this);

        childNationality = findViewById(R.id.textNationalityChild);
        childNationality.setOnClickListener(this);
        childNoPassport = findViewById(R.id.textNoPassportChild);
        childIssuingCountry = findViewById(R.id.textIssuingCountryChild);
        childIssuingCountry.setOnClickListener(this);
        childExpirationDate = findViewById(R.id.textExpirationDateChild);
        childExpirationDate .setOnClickListener(this);


        textBaggageChild1 = findViewById(R.id.textBaggageChild1);
        textBaggageChild1.setOnTouchListener(this);
        textBaggageChild2 = findViewById(R.id.textBaggageChild2);
        textBaggageChild2.setOnTouchListener(this);
        textBaggageChild3 = findViewById(R.id.textBaggageChild3);
        textBaggageChild3.setOnTouchListener(this);


        actBatal = toolbar.findViewById(R.id.actionToolbar);
        titleHeader.setText("DATA PENUMPANG ANAK");
        childTitle.setOnTouchListener(this);
        childName.setOnEditorActionListener(new NextOnEditorActionListener());
        InputFilter[] filter = new InputFilter[2];
        filter[0] = new InputFilter.AllCaps();
        filter[1] = new InputFilter.LengthFilter(100);
        childName.setFilters(filter);
        actBatal.setText("Batal");
        actBatal.setOnClickListener(this);
        btnSelesai = findViewById(R.id.btn_selesai);
//        MaterialRippleLayout.on(btnSelesai).rippleOverlay(true)
//                .rippleAlpha(0.2f)
//                .rippleColor(R.color.md_orange_200)
//                .rippleHover(true)
//                .create();
        btnSelesai.setOnClickListener(this);
        list = new String[]{"Tuan", "Nona"};
        listTitle = new String[]{"Tn", "Nona"};
        lpw = new ListPopupWindow(this);
        lpw.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, list));
        lpw.setAnchorView(childTitle);
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
                    isBuyChild =1;// "true";
                    linListBaggage.setVisibility(View.VISIBLE);
                    closeKeyboard(FlightFormBookChildActivity.this);
                } else {
                    Log.d(TAG, "onClick: false");
                    isBuyChild = 0;//"false";
                    linListBaggage.setVisibility(View.GONE);
                    clearBaggage();

                }
            }
        });


//        listBaggage1 = showBaggage1(); //new String[]{"Tuan", "Nyonya", "Nona"};
//        listTitleBaggage1 = showTitleBaggage1();//new String[]{"Tn", "Ny", "Nona"};
//        listWeightBaggage1 = showWeightBaggage1();

        lpw1 = new ListPopupWindow(this);
        lpw1.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, showBaggage1()));
        lpw1.setAnchorView(textBaggageChild1);
        lpw1.setModal(true);
        lpw1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick cb1: " + position);
//                jsonObject1 = new JSONObject();
//                jsonObject2 = new JSONObject();
//                jsonObject3 = new JSONObject();
                positionBaggageCheck = position;
                String item = showBaggage1()[position];
                String itemTemp = showTitleBaggage1()[position];
                String weightTemp = showWeightBaggage1()[position];
                String kodeMaskapai = "TP" + PreferenceClass.getString(FlightKeyPreference.baggageFlightNo1, "").substring(0, 2);
                textBaggageChild1.setText(item);
                titleBaggageTemp1 = itemTemp;
                baggage1 = "{\"kode_maskapai\":\"" + kodeMaskapai + "\",\"baggage_key\":\"" + itemTemp + "\",\"weight\":\"" + Integer.parseInt(weightTemp) + "\"}";
                try {
                    jsonObject1 = new JSONObject(baggage1);
//                    jsonObject1.put("kode_maskapai", kodeMaskapai);
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
                    String kodeMaskapai2 = "TP" + PreferenceClass.getString(FlightKeyPreference.baggageFlightNo2, "").substring(0, 2);
                    textBaggageChild2.setText(item2);
                    titleBaggageTemp2 = itemTemp2;
                    baggage2 = "{\"kode_maskapai\":\"" + kodeMaskapai2 + "\",\"baggage_key\":\"" + itemTemp2 + "\",\"weight\":\"" + Integer.parseInt(weightTemp2) + "\"}";
                    try {
                        jsonObject2 = new JSONObject(baggage2);
//                        jsonObject2.put("kode_maskapai", kodeMaskapai2);
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
                    String kodeMaskapai3 = "TP" + PreferenceClass.getString(FlightKeyPreference.baggageFlightNo3, "").substring(0, 2);
                    textBaggageChild3.setText(item3);
                    titleBaggageTemp3 = itemTemp3;
                    baggage3 = "{\"kode_maskapai\":\"" + kodeMaskapai3 + "\",\"baggage_key\":\"" + itemTemp3 + "\",\"weight\":\"" + Integer.parseInt(weightTemp3) + "\"}";
                    try {
                        jsonObject3 = new JSONObject(baggage3);
//                        jsonObject3.put("kode_maskapai", kodeMaskapai3);
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
//        listWeightBaggage2 = showWeightBaggage2();
        lpw2 = new ListPopupWindow(this);
        lpw2.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, showBaggage2()));
        lpw2.setAnchorView(textBaggageChild2);

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
                String kodeMaskapai = "TP" + PreferenceClass.getString(FlightKeyPreference.baggageFlightNo2, "").substring(0, 2);
                textBaggageChild2.setText(item);
                titleBaggageTemp2 = itemTemp;
                baggage2 = "{\"kode_maskapai\":\"" + kodeMaskapai + "\",\"baggage_key\":\"" + itemTemp + "\",\"weight\":\"" + Integer.parseInt(weightTemp) + "\"}";
                try {
                    jsonObject2 = new JSONObject(baggage2);
//                    jsonObject2.put("kode_maskapai", kodeMaskapai);
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
                    String kodeMaskapai1 = "TP" + PreferenceClass.getString(FlightKeyPreference.baggageFlightNo1, "").substring(0, 2);
                    textBaggageChild1.setText(item1);
                    titleBaggageTemp1 = itemTemp1;
                    baggage1 = "{\"kode_maskapai\":\"" + kodeMaskapai1 + "\",\"baggage_key\":\"" + itemTemp1 + "\",\"weight\":\"" + Integer.parseInt(weightTemp1) + "\"}";
                    try {
                        jsonObject1 = new JSONObject(baggage1);
//                        jsonObject1.put("kode_maskapai", kodeMaskapai1);
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
                    String kodeMaskapai3 = "TP" + PreferenceClass.getString(FlightKeyPreference.baggageFlightNo3, "").substring(0, 2);
                    textBaggageChild3.setText(item3);
                    titleBaggageTemp3 = itemTemp3;
                    baggage3 = "{\"kode_maskapai\":\"" + kodeMaskapai3 + "\",\"baggage_key\":\"" + itemTemp3 + "\",\"weight\":\"" + Integer.parseInt(weightTemp3) + "\"}";
                    try {
                        jsonObject3 = new JSONObject(baggage3);
//                        jsonObject3.put("kode_maskapai", kodeMaskapai3);
//                        jsonObject3.put("baggage_key", itemTemp3);
//                        jsonObject3.put("weight", Integer.parseInt(weightTemp3));
                        Log.d(TAG, "onClickFormBaggage 3: " + jsonObject3.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                lpw2.dismiss();
            }
        });


//        listBaggage3 = showBaggage3(); //new String[]{"Tuan", "Nyonya", "Nona"};
//        listTitleBaggage3 = showTitleBaggage3();// new String[]{"Tn", "Ny", "Nona"};
//        listWeightBaggage3 = showWeightBaggage3();
        lpw3 = new ListPopupWindow(this);
        lpw3.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, showBaggage3()));
        lpw3.setAnchorView(textBaggageChild3);

        lpw3.setModal(true);
        lpw3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                jsonObject1 = new JSONObject();
//                jsonObject2 = new JSONObject();
//                jsonObject3 = new JSONObject();
                positionBaggageCheck = position;
                String item = showBaggage3()[position];
                String itemTemp = showTitleBaggage3()[position];
                String weightTemp = showWeightBaggage3()[position];
                String kodeMaskapai = "TP" + PreferenceClass.getString(FlightKeyPreference.baggageFlightNo3, "").substring(0, 2);
                textBaggageChild3.setText(item);
                titleBaggageTemp3 = itemTemp;
                baggage3 = "{\"kode_maskapai\":\"" + kodeMaskapai + "\",\"baggage_key\":\"" + itemTemp + "\",\"weight\":\"" + Integer.parseInt(weightTemp) + "\"}";
                try {
                    jsonObject3 = new JSONObject(baggage3);
//                    jsonObject3.put("kode_maskapai", kodeMaskapai);
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
                    String kodeMaskapai2 = "TP" + PreferenceClass.getString(FlightKeyPreference.baggageFlightNo2, "").substring(0, 2);
                    textBaggageChild2.setText(item2);
                    titleBaggageTemp2 = itemTemp2;
                    baggage2 = "{\"kode_maskapai\":\"" + kodeMaskapai2 + "\",\"baggage_key\":\"" + itemTemp2 + "\",\"weight\":\"" + Integer.parseInt(weightTemp2) + "\"}";
                    try {
                        jsonObject2 = new JSONObject(baggage2);
//                        jsonObject2.put("kode_maskapai", kodeMaskapai2);
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
                    String kodeMaskapai1 = "TP" + PreferenceClass.getString(FlightKeyPreference.baggageFlightNo1, "").substring(0, 2);
                    textBaggageChild1.setText(item1);
                    titleBaggageTemp1 = itemTemp1;
                    baggage1 = "{\"kode_maskapai\":\"" + kodeMaskapai1 + "\",\"baggage_key\":\"" + itemTemp1 + "\",\"weight\":\"" + Integer.parseInt(weightTemp1) + "\"}";
                    try {
                        jsonObject1 = new JSONObject(baggage1);
//                        jsonObject1.put("kode_maskapai", kodeMaskapai1);
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


        clearAll(isNew);


        int countBaggage = PreferenceClass.getInt(FlightKeyPreference.countBaggage, 0);

        if (countBaggage > 0) {
//            checkboxBeliBagasi.setVisibility(View.VISIBLE);// sementara
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
    }

    String baggage1;
    String baggage2;
    String baggage3;

    JSONObject jsonObject1;
    JSONObject jsonObject2;
    JSONObject jsonObject3;

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
//            if (jsonArray.length() > 0) {
                list2 = new String[jsonArray.length()];

                for (int i = 0; i < jsonArray.length(); i++) {
                    // String[] arrA = jsonArray.getJSONObject(i).get("baggage_key").toString().split("\\|");
                    list2[i] = jsonArray.getJSONObject(i).get("baggage_key").toString();
                }
//            }

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
        String[] list3 = new String[0];

        try {

            JSONArray jsonArray = jsonObject.getJSONArray("data");
            list3 = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                // String[] arrA = jsonArray.getJSONObject(i).get("baggage_key").toString().split("\\|");
                list3[i] = jsonArray.getJSONObject(i).get("weight").toString();
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


//
        return list3;

    }

    public void clearAll(boolean isNew) {
        if (isNew) {
            childTitle.setText("");
            childName.setText("");
            childBorn.setText("");
            dateBorn="";

            if (PreferenceClass.getInt(FlightKeyPreference.isInternational, 0) == 0) {
                childNationality.setVisibility(View.GONE);
                childNoPassport.setVisibility(View.GONE);
                childIssuingCountry.setVisibility(View.GONE);
                childExpirationDate.setVisibility(View.GONE);
            } else {
                childNationality.setVisibility(View.VISIBLE);
                childBorn.setVisibility(View.VISIBLE);
                childNoPassport.setVisibility(View.VISIBLE);
                childIssuingCountry.setVisibility(View.VISIBLE);
                childExpirationDate.setVisibility(View.VISIBLE);
            }

            childNationality.setText("");
            childNoPassport.setText("");
            childIssuingCountry.setText("");
            childExpirationDate.setText("");
            dateExpired="";
            
            checkboxBeliBagasi.setChecked(false);
            textBaggageChild1.setText("");
            textBaggageChild2.setText("");
            textBaggageChild3.setText("");
        } else {
            childTitle.setText(sChildTitle);
            childName.setText(sChildName);
            childBorn.setText(sChildBorn);

            if (PreferenceClass.getInt(FlightKeyPreference.isInternational, 0) == 0) {
                childNationality.setVisibility(View.GONE);
                childNoPassport.setVisibility(View.GONE);
                childIssuingCountry.setVisibility(View.GONE);
                childExpirationDate.setVisibility(View.GONE);
            } else {
                childNationality.setVisibility(View.VISIBLE);
                childBorn.setVisibility(View.VISIBLE);
                childNoPassport.setVisibility(View.VISIBLE);
                childIssuingCountry.setVisibility(View.VISIBLE);
                childExpirationDate.setVisibility(View.VISIBLE);
            }
            childNationality.setText(sChildNationality);
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
                    childNationality.setCompoundDrawables(drawable, null, null, null);
                }
            });

            childIssuingCountry.setText(sChildIssuingCountry);
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
                    childIssuingCountry.setCompoundDrawables(drawable, null, null, null);
                }
            });

            childExpirationDate.setText(sChildExpirationDate);
            childNoPassport.setText(sChildNoPassport);
            
            
            
            if (isBuyChild==1) {
//                checkboxBeliBagasi.performClick();
//                textBaggageChild1.setText(sChildTitleBaggage1);
//                textBaggageChild2.setText(sChildTitleBaggage2);
//                textBaggageChild3.setText(sChildTitleBaggage3);
                checkboxBeliBagasi.setChecked(true);
                linListBaggage.setVisibility(View.VISIBLE);
                String item = showBaggage1()[positionBaggageCheck];
                String itemTemp = showTitleBaggage1()[positionBaggageCheck];
                textBaggageChild1.setText(item);
                titleBaggageTemp1 = itemTemp;
                String weightTemp = showWeightBaggage1()[positionBaggageCheck];
                String kodeMaskapai=PreferenceClass.getString(FlightKeyPreference.baggageFlightNo1, "");

                // baggage1="{\"kode_maskapai\":\"TP\"" + kodeMaskapai.substring(0, 2)+"\",\"baggage_key\":\"" +itemTemp+ "\",\"weight\":\"" +Integer.parseInt(weightTemp)+ "\"}";
                baggage1="{kode_maskapai:TP" + kodeMaskapai.substring(0, 2)+",baggage_key:" +itemTemp+ ",weight:" +Integer.parseInt(weightTemp)+ "}";

                try {
                    jsonObject1=new JSONObject(baggage1);
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
                    textBaggageChild2.setText(item2);
                    titleBaggageTemp2 = itemTemp2;
                    String weightTemp2 = showWeightBaggage2()[positionBaggageCheck];
                    String kodeMaskapai2=PreferenceClass.getString(FlightKeyPreference.baggageFlightNo2, "");

                    baggage2="{kode_maskapai:TP" + kodeMaskapai2.substring(0, 2)+",baggage_key:" +itemTemp2+ ",weight:" +Integer.parseInt(weightTemp2)+ "}";
                    try {
                        jsonObject2=new JSONObject(baggage2);
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
                    textBaggageChild3.setText(item3);
                    titleBaggageTemp3 = itemTemp3;
                    String weightTemp3 = showWeightBaggage3()[positionBaggageCheck];
                    String kodeMaskapai3=PreferenceClass.getString(FlightKeyPreference.baggageFlightNo3, "");

                    baggage3="{\"kode_maskapai\":\"TP\"" + kodeMaskapai3.substring(0, 2)+"\",\"baggage_key\":\"" +itemTemp3+ "\",\"weight\":\"" +Integer.parseInt(weightTemp3)+ "\"}";
                    try {
                        jsonObject3=new JSONObject(baggage3);
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
            if (textBaggageChild1.getEditableText().length() > 0) {
                textBaggageChild1.setText("");


            }
        }

        if (showBaggage2().length > 0) {
            if (textBaggageChild2.getEditableText().length() > 0) {
                textBaggageChild2.setText("");

            }
        }


        if (showBaggage3().length > 0) {
            if (textBaggageChild3.getEditableText().length() > 0) {
                textBaggageChild3.setText("");

            }
        }
    }

    @Override
    public void onClick(@NonNull View v) {
        int id = v.getId();
        if (id == R.id.actionToolbar) {
            onBackPressed();
        } else if (id == R.id.btn_selesai) {

            selesai();
        } else if (id == R.id.textBornChild) {
            openCalendar();

        } else if (id == R.id.textExpirationDateChild) {
            openCalendarExpired();
        } else if (id == R.id.textNationalityChild) {
            Intent intent = new Intent(this, FlightCountryActivity.class);
            startActivityForResult(intent, TravelActionCode.NASIONALITY);
        } else if (id == R.id.textIssuingCountryChild) {
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
        childTitle.setText(item);
        titleTemp = itemTemp;
        lpw.dismiss();
    }

    @Override
    public boolean onTouch(@NonNull View v, @NonNull MotionEvent event) {
        int id = v.getId();
        if (id == R.id.textTitleChild) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                lpw.show();
                return true;
            }
        } else if (id == R.id.textBaggageChild1) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                lpw1.show();
                return true;
            }
        } else if (id == R.id.textBaggageChild2) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                lpw2.show();
                return true;
            }
        } else if (id == R.id.textBaggageChild3) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                lpw3.show();
                return true;
            }
        }
        return false;
    }

    private void openCalendar() {
        (new DatePickerFragment()).show(getFragmentManager(), String.valueOf(R.style.SBFTheme));
    }
    private void openCalendarExpired() {
        (new ExpiredDatePickerFragment()).show(getFragmentManager(), String.valueOf(R.style.SBFTheme));
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        private static final String TAG = DatePickerFragment.class.getSimpleName();
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
//            calendar.add(Calendar.YEAR, -2);
            calendar.add(Calendar.MONTH, -23);
            calendar.add(Calendar.DAY_OF_MONTH, -5);
            dpd.getDatePicker().setMaxDate(calendar.getTimeInMillis());
            calendar.add(Calendar.YEAR, -10);
            calendar.add(Calendar.DAY_OF_MONTH, 1);

            dpd.getDatePicker().setMinDate(calendar.getTimeInMillis());
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                dpd.setTitle("Tanggal Lahir Anak");//Prevent Date picker from creating extra Title.!
            }
            return dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            TextView tv = getActivity().findViewById(R.id.textBornChild);
            Calendar cal = Calendar.getInstance(SBFApplication.config.locale);
            cal.setTimeInMillis(0);
            cal.set(year, month, day, 0, 0, 0);
            Date chosenDate = cal.getTime();
            String formattedDateShow = formatterBornShow.format(chosenDate);
            String formattedDate = formatterFlight.format(chosenDate);
            //Log.d(TAG, "onDateSet: " + chosenDate);
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
            return dpd;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            TextView tv = getActivity().findViewById(R.id.textExpirationDateChild);
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
        if (!Validate.checkEmptyEditText(childTitle, "Tidak Boleh Kosong")) {
            childTitle.setAnimation(animShake);
            childTitle.startAnimation(animShake);
            getar();
            return;
        }
        if (!Validate.checkEmptyEditText(childName, "Tidak Boleh Kosong")) {
            childName.setAnimation(animShake);
            childName.startAnimation(animShake);
            getar();
            return;
        }
        if (!Validate.checkEmptyEditText(childBorn, "Tidak Boleh Kosong")) {
            childBorn.setAnimation(animShake);
            childBorn.startAnimation(animShake);
            getar();
            return;
        }


        if (childNationality.getVisibility() == View.VISIBLE) {
            if(!Validate.checkEmptyEditText(childNationality, "Tidak Boleh Kosong")) {
                childNationality.setAnimation(animShake);
                childNationality.startAnimation(animShake);
                getar();
                return;
            }
        }

        if (childNoPassport.getVisibility() == View.VISIBLE) {
            if(!Validate.checkEmptyEditText(childNoPassport, "Tidak Boleh Kosong")) {
                childNoPassport.setAnimation(animShake);
                childNoPassport.startAnimation(animShake);
                getar();
                return;
            }else if(!childNoPassport.isCharactersCountValid()){
                childNoPassport.setAnimation(animShake);
                childNoPassport.startAnimation(animShake);
                childNoPassport.setError("Nomor Passport kurang dari minimun");
                getar();
                return;
            }
        }
        if (childIssuingCountry.getVisibility() == View.VISIBLE) {
            if(!Validate.checkEmptyEditText(childIssuingCountry, "Tidak Boleh Kosong")) {
                childIssuingCountry.setAnimation(animShake);
                childIssuingCountry.startAnimation(animShake);
                getar();
                return;
            }
        }
        if (childExpirationDate.getVisibility() == View.VISIBLE) {
            if(!Validate.checkEmptyEditText(childExpirationDate, "Tidak Boleh Kosong")) {
                childExpirationDate.setAnimation(animShake);
                childExpirationDate.startAnimation(animShake);
                getar();
                return;
            }
        }
        
        ArrayList<String> arrBaggage = new ArrayList<>();
        JSONArray baggage = new JSONArray();
        if (checkboxBeliBagasi.isChecked()) {
            if (showBaggage1().length > 0) {
                if (!Validate.checkEmptyEditText(textBaggageChild1, "Tidak Boleh Kosong")) {
                    textBaggageChild1.setAnimation(animShake);
                    textBaggageChild1.startAnimation(animShake);
                    getar();
                    return;
                }
                baggage.put(jsonObject1);
                arrBaggage.add(baggage1);
            }

            if (showBaggage2().length > 0) {
                if (!Validate.checkEmptyEditText(textBaggageChild2, "Tidak Boleh Kosong")) {
                    textBaggageChild2.setAnimation(animShake);
                    textBaggageChild2.startAnimation(animShake);
                    getar();
                    return;
                }
                baggage.put(jsonObject2);
                arrBaggage.add(baggage2);
            }


            if (showBaggage3().length > 0) {
                if (!Validate.checkEmptyEditText(textBaggageChild3, "Tidak Boleh Kosong")) {
                    textBaggageChild3.setAnimation(animShake);
                    textBaggageChild3.startAnimation(animShake);
                    getar();
                    return;
                }
                baggage.put(jsonObject3);
                arrBaggage.add(baggage3);
            }
        }


        Intent i = new Intent();
        i.putExtra("childTitle", childTitle.getText().toString());
        i.putExtra("childTitleTemp", titleTemp);
        i.putExtra("childName", childName.getText().toString().replaceAll("[']", "`"));
        i.putExtra("childBornShow", childBorn.getText().toString());
        i.putExtra("childBorn", dateBorn);

        i.putExtra("childNoPassport", childNoPassport.getText().toString());
        i.putExtra("childNationality", childNationality.getText().toString());
        i.putExtra("childNationalityKode", nasionalityKode);
        i.putExtra("childIssuingCountry", childIssuingCountry.getText().toString());
        i.putExtra("childIssuingCountryKode", issuingNasionalityKode);
        i.putExtra("childExpiredShow", childExpirationDate.getText().toString());
        i.putExtra("childExpired", dateExpired);

        i.putExtra("childCheckboxBeliBagasi", isBuyChild);


//        i.putExtra("childTitleBaggage1", textBaggageChild1.getEditableText());
//        i.putExtra("childTitleBaggage2", textBaggageChild2.getEditableText());
//        i.putExtra("childTitleBaggage3", textBaggageChild3.getEditableText());

        i.putExtra("childTitleBaggageTemp1", titleBaggageTemp1);
        i.putExtra("childTitleBaggageTemp2", titleBaggageTemp2);
        i.putExtra("childTitleBaggageTemp3", titleBaggageTemp3);
        i.putExtra("childTitleBaggagePosition", positionBaggageCheck);
        i.putExtra("childBaggage", baggage.toString());
        i.putExtra("childBaggageArr", arrBaggage);


        i.putExtra("status", "child");
        i.putExtra(FlightKeyPreference.isNewFlight, isNew);
        setResult(Activity.RESULT_OK, i);
        finish();
    }

    class NextOnEditorActionListener implements TextView.OnEditorActionListener {
        @Override
        public boolean onEditorAction(@NonNull TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                openCalendar();
            }
            return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == TravelActionCode.NASIONALITY) {
            childNationality.setText(data.getStringExtra("countryNama"));
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
                    childNationality.setCompoundDrawables(drawable, null, null, null);
                }
            });

        } else if (resultCode == Activity.RESULT_OK && requestCode == TravelActionCode.ISSUING_NASIONALITY) {
            childIssuingCountry.setText(data.getStringExtra("countryNama"));
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
                    childIssuingCountry.setCompoundDrawables(drawable, null, null, null);
                }
            });
        }


    }
}


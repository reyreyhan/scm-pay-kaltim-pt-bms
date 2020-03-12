package com.bm.main.single.ftl.train.activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;



import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.templates.ExpandablePanel;
import com.bm.main.fpl.templates.MaterialRippleLayout;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.materialedittext.MaterialEditText;
import com.bm.main.pos.R;
import com.bm.main.pos.SBFApplication;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bm.main.single.ftl.train.constants.TrainKeyPreference;
import com.bm.main.single.ftl.train.constants.TrainPath;
import com.bm.main.single.ftl.utils.RequestUtilsTravel;
import com.bm.main.single.ftl.utils.utilBand;
import com.crowdfire.cfalertdialog.CFAlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TrainBookActivity extends BaseActivity implements ProgressResponseCallback, View.OnClickListener {
    private static final String TAG = TrainBookActivity.class.getSimpleName();

    TextView textTrainNo, textTotal, textOrigin, textDestination, textTimeDept, textTimeArr, textDateDept, textDateArr;

    TextView noUrutAdult1, noUrutAdult2, noUrutAdult3, noUrutAdult4;
    TextView textContentAdult1, textContentAdult2, textContentAdult3, textContentAdult4;
    TextView textActionAdult1, textActionAdult2, textActionAdult3, textActionAdult4;

    ImageView imageActionAdult1, imageActionAdult2, imageActionAdult3, imageActionAdult4;


    TextView noUrutInfant1, noUrutInfant2, noUrutInfant3, noUrutInfant4;
    TextView textContentInfant1, textContentInfant2, textContentInfant3, textContentInfant4;
    TextView textActionInfant1, textActionInfant2, textActionInfant3, textActionInfant4;
    ImageView imageActionInfant1, imageActionInfant2, imageActionInfant3, imageActionInfant4;

    RelativeLayout cardPassangerAdult1, cardPassangerAdult2, cardPassangerAdult3, cardPassangerAdult4;

    RelativeLayout cardPassangerInfant1, cardPassangerInfant2, cardPassangerInfant3, cardPassangerInfant4;

    AppCompatButton btnPesan;
    @NonNull
    String price = "0", timeArr, timeDept, deptDate, arrDate;
    int countAdult = 1, countChild = 0, countInfant = 0;
//    String[] adultPassengger;
//    String[] childPassengger;
//    String[] infantPassengger;
    MaterialEditText textHpKontak, textEmailKontak;
    public TrainBookActivity instance;
    String sJson = "";
    ScrollView scrollMainBook;
    RelativeLayout loadingPage;
//    FrameLayout frameBottom;


    LinearLayout rlPassangerAdultTrain1, rlPassangerAdultTrain2, rlPassangerAdultTrain3, rlPassangerAdultTrain4;
    LinearLayout rlPassangerInfantTrain1, rlPassangerInfantTrain2, rlPassangerInfantTrain3, rlPassangerInfantTrain4;


    ImageView imageViewExpandPassangerAdultTrain1, imageViewExpandPassangerAdultTrain2, imageViewExpandPassangerAdultTrain3, imageViewExpandPassangerAdultTrain4;


    ImageView imageViewExpandPassangerInfantTrain1, imageViewExpandPassangerInfantTrain2, imageViewExpandPassangerInfantTrain3, imageViewExpandPassangerInfantTrain4;

    ExpandablePanel panelPassangerAdultTrain1Detail, panelPassangerAdultTrain2Detail, panelPassangerAdultTrain3Detail, panelPassangerAdultTrain4Detail;

    ExpandablePanel panelPassangerInfantTrain1Detail, panelPassangerInfantTrain2Detail, panelPassangerInfantTrain3Detail, panelPassangerInfantTrain4Detail;


    TextView textViewNoIdentitasAdultTrain1, textViewNoIdentitasAdultTrain2, textViewNoIdentitasAdultTrain3, textViewNoIdentitasAdultTrain4;

    TextView textViewMobileAdultTrain1, textViewMobileAdultTrain2, textViewMobileAdultTrain3, textViewMobileAdultTrain4;

    TextView textViewTanggalLahirInfantTrain1, textViewTanggalLahirInfantTrain2, textViewTanggalLahirInfantTrain3, textViewTanggalLahirInfantTrain4;
//    private TrainDataModel dataSchedule;

    Context context;


    LinearLayout linContentAdultTrain1,linContentAdultTrain2,linContentAdultTrain3,linContentAdultTrain4;
    LinearLayout linContentInfantTrain1,linContentInfantTrain2,linContentInfantTrain3,linContentInfantTrain4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_book_activity);
        instance = this;
        context = this;
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Lengkapi Data");
        init(0);
        scrollMainBook = findViewById(R.id.scrollMainBook);
//        frameBottom = findViewById(R.id.frameBottomTrain);
        loadingPage = findViewById(R.id.loading_view_train);
        Intent intent = this.getIntent();
        //    Bundle b = getIntent().getExtras();

        if (intent != null)
            loadingPage.setVisibility(View.VISIBLE);
        scrollMainBook.setVisibility(View.GONE);
    //    frameBottom.setVisibility(View.GONE);
        sJson = intent.getStringExtra("reqJsonFare");

//            price = b.getString("price");
//        timeArr = b.getString("arrivalTime");
//        timeDept = b.getString("departureTime");

        //  dataSchedule= (TrainDataModel) MemoryStore.get(TrainKeyPreference.onClickSchedule);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(sJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "onCreate: " + jsonObject);
        RequestUtilsTravel.transportWithProgressResponse(this, TrainPath.FARE, jsonObject, TravelActionCode.FARE, this);

        textTrainNo = findViewById(R.id.textCodeTrain);
        textTotal = findViewById(R.id.textPrice);
        textOrigin = findViewById(R.id.textOrigin);
        textDestination = findViewById(R.id.textDestination);
        textTimeDept = findViewById(R.id.jamBerangkatTrain);
        textTimeArr = findViewById(R.id.jamTibaTrain);
        textDateDept = findViewById(R.id.textDepartureDate);
        textDateArr = findViewById(R.id.textArrivalDate);


        rlPassangerAdultTrain1 = findViewById(R.id.rlPassangerAdultTrain1);
        cardPassangerAdult1 = findViewById(R.id.cardPassangerAdultTrain1);

        noUrutAdult1 = findViewById(R.id.textUrutAdultTrain1);
        textContentAdult1 = findViewById(R.id.textContentAdultTrain1);
        textActionAdult1 = findViewById(R.id.textActionAdultTrain1);
        imageActionAdult1 = findViewById(R.id.imageActionAdultTrain1);
        linContentAdultTrain1 = findViewById(R.id.linContentAdultTrain1);
        linContentAdultTrain1.setOnClickListener(this);
//        textActionAdult1.setOnClickListener(this);
//        imageActionAdult1.setOnClickListener(this);

        imageViewExpandPassangerAdultTrain1 = findViewById(R.id.imageViewExpandPassangerAdultTrain1);
        imageViewExpandPassangerAdultTrain1.setBackground(ContextCompat.getDrawable(TrainBookActivity.this, R.drawable.ic_adult));
//        imageViewExpandPassangerAdult1.setVisibility(View.GONE);
        panelPassangerAdultTrain1Detail = findViewById(R.id.panelPassangerAdultTrain1Detail);

        panelPassangerAdultTrain1Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            public void onCollapse(View handle, View content) {
                ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                imageView.setBackground(ContextCompat.getDrawable(TrainBookActivity.this, R.drawable.ic_expand));
            }

            public void onExpand(View handle, View content) {
                ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                imageView.setBackground(ContextCompat.getDrawable(TrainBookActivity.this, R.drawable.ic_collapse));
            }
        });

        rlPassangerAdultTrain2 = findViewById(R.id.rlPassangerAdultTrain2);
        cardPassangerAdult2 = findViewById(R.id.cardPassangerAdultTrain2);

        noUrutAdult2 = findViewById(R.id.textUrutAdultTrain2);
        textContentAdult2 = findViewById(R.id.textContentAdultTrain2);
        textActionAdult2 = findViewById(R.id.textActionAdultTrain2);
        imageActionAdult2 = findViewById(R.id.imageActionAdultTrain2);
        linContentAdultTrain2 = findViewById(R.id.linContentAdultTrain2);
        linContentAdultTrain2.setOnClickListener(this);
//        textActionAdult2.setOnClickListener(this);
//
//        imageActionAdult2.setOnClickListener(this);

        imageViewExpandPassangerAdultTrain2 = findViewById(R.id.imageViewExpandPassangerAdultTrain2);
        imageViewExpandPassangerAdultTrain2.setBackground(ContextCompat.getDrawable(TrainBookActivity.this, R.drawable.ic_adult));
//        imageViewExpandPassangerAdult1.setVisibility(View.GONE);
        panelPassangerAdultTrain2Detail = findViewById(R.id.panelPassangerAdultTrain2Detail);

        panelPassangerAdultTrain2Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            public void onCollapse(View handle, View content) {
                ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                imageView.setBackground(ContextCompat.getDrawable(TrainBookActivity.this, R.drawable.ic_expand));
            }

            public void onExpand(View handle, View content) {
                ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                imageView.setBackground(ContextCompat.getDrawable(TrainBookActivity.this, R.drawable.ic_collapse));
            }
        });


        rlPassangerAdultTrain3 = findViewById(R.id.rlPassangerAdultTrain3);
        cardPassangerAdult3 = findViewById(R.id.cardPassangerAdultTrain3);

        noUrutAdult3 = findViewById(R.id.textUrutAdultTrain3);
        textContentAdult3 = findViewById(R.id.textContentAdultTrain3);
        textActionAdult3 = findViewById(R.id.textActionAdultTrain3);
        imageActionAdult3 = findViewById(R.id.imageActionAdultTrain3);
        linContentAdultTrain3 = findViewById(R.id.linContentAdultTrain3);
        linContentAdultTrain3.setOnClickListener(this);
//        textActionAdult3.setOnClickListener(this);
//        imageActionAdult3.setOnClickListener(this);

        imageViewExpandPassangerAdultTrain3 = findViewById(R.id.imageViewExpandPassangerAdultTrain3);
        imageViewExpandPassangerAdultTrain3.setBackground(ContextCompat.getDrawable(TrainBookActivity.this, R.drawable.ic_adult));
//        imageViewExpandPassangerAdult1.setVisibility(View.GONE);
        panelPassangerAdultTrain3Detail = findViewById(R.id.panelPassangerAdultTrain3Detail);

        panelPassangerAdultTrain3Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            public void onCollapse(View handle, View content) {
                ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                imageView.setBackground(ContextCompat.getDrawable(TrainBookActivity.this, R.drawable.ic_expand));
            }

            public void onExpand(View handle, View content) {
                ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                imageView.setBackground(ContextCompat.getDrawable(TrainBookActivity.this, R.drawable.ic_collapse));
            }
        });


        rlPassangerAdultTrain4 = findViewById(R.id.rlPassangerAdultTrain4);
        cardPassangerAdult4 = findViewById(R.id.cardPassangerAdultTrain4);

        noUrutAdult4 = findViewById(R.id.textUrutAdultTrain4);
        textContentAdult4 = findViewById(R.id.textContentAdultTrain4);
        textActionAdult4 = findViewById(R.id.textActionAdultTrain4);
        imageActionAdult4 = findViewById(R.id.imageActionAdultTrain4);
        linContentAdultTrain4 = findViewById(R.id.linContentAdultTrain4);
        linContentAdultTrain4.setOnClickListener(this);
//        textActionAdult4.setOnClickListener(this);
//        imageActionAdult4.setOnClickListener(this);

        imageViewExpandPassangerAdultTrain4 = findViewById(R.id.imageViewExpandPassangerAdultTrain4);
        imageViewExpandPassangerAdultTrain4.setBackground(ContextCompat.getDrawable(TrainBookActivity.this, R.drawable.ic_adult));
//        imageViewExpandPassangerAdult1.setVisibility(View.GONE);
        panelPassangerAdultTrain4Detail = findViewById(R.id.panelPassangerAdultTrain4Detail);

        panelPassangerAdultTrain4Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            public void onCollapse(View handle, View content) {
                ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                imageView.setBackground(ContextCompat.getDrawable(TrainBookActivity.this, R.drawable.ic_expand));
            }

            public void onExpand(View handle, View content) {
                ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                imageView.setBackground(ContextCompat.getDrawable(TrainBookActivity.this, R.drawable.ic_collapse));
            }
        });

        rlPassangerInfantTrain1 = findViewById(R.id.rlPassangerInfantTrain1);
        cardPassangerInfant1 = findViewById(R.id.cardPassangerInfantTrain1);

        noUrutInfant1 = findViewById(R.id.textUrutInfantTrain1);
        textContentInfant1 = findViewById(R.id.textContentInfantTrain1);
        textActionInfant1 = findViewById(R.id.textActionInfantTrain1);
        imageActionInfant1 = findViewById(R.id.imageActionInfantTrain1);
        linContentInfantTrain1 = findViewById(R.id.linContentInfantTrain1);
        linContentInfantTrain1.setOnClickListener(this);
//        textActionInfant1.setOnClickListener(this);
//        imageActionInfant1.setOnClickListener(this);

        imageViewExpandPassangerInfantTrain1 = findViewById(R.id.imageViewExpandPassangerInfantTrain1);
        imageViewExpandPassangerInfantTrain1.setBackground(ContextCompat.getDrawable(TrainBookActivity.this, R.drawable.ic_baby));
//        imageViewExpandPassangerAdult1.setVisibility(View.GONE);
        panelPassangerInfantTrain1Detail = findViewById(R.id.panelPassangerInfantTrain1Detail);

        panelPassangerInfantTrain1Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            public void onCollapse(View handle, View content) {
                ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                imageView.setBackground(ContextCompat.getDrawable(TrainBookActivity.this, R.drawable.ic_expand));
            }

            public void onExpand(View handle, View content) {
                ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                imageView.setBackground(ContextCompat.getDrawable(TrainBookActivity.this, R.drawable.ic_collapse));
            }
        });


        rlPassangerInfantTrain2 = findViewById(R.id.rlPassangerInfantTrain2);
        cardPassangerInfant2 = findViewById(R.id.cardPassangerInfantTrain2);

        noUrutInfant2 = findViewById(R.id.textUrutInfantTrain2);
        textContentInfant2 = findViewById(R.id.textContentInfantTrain2);
        textActionInfant2 = findViewById(R.id.textActionInfantTrain2);
        imageActionInfant2 = findViewById(R.id.imageActionInfantTrain2);
        linContentInfantTrain2 = findViewById(R.id.linContentInfantTrain2);
        linContentInfantTrain2.setOnClickListener(this);
//        textActionInfant2.setOnClickListener(this);
//        imageActionInfant2.setOnClickListener(this);

        imageViewExpandPassangerInfantTrain2 = findViewById(R.id.imageViewExpandPassangerInfantTrain2);
        imageViewExpandPassangerInfantTrain2.setBackground(ContextCompat.getDrawable(TrainBookActivity.this, R.drawable.ic_baby));
//        imageViewExpandPassangerAdult1.setVisibility(View.GONE);
        panelPassangerInfantTrain2Detail = findViewById(R.id.panelPassangerInfantTrain2Detail);

        panelPassangerInfantTrain2Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            public void onCollapse(View handle, View content) {
                ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                imageView.setBackground(ContextCompat.getDrawable(TrainBookActivity.this, R.drawable.ic_expand));
            }

            public void onExpand(View handle, View content) {
                ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                imageView.setBackground(ContextCompat.getDrawable(TrainBookActivity.this, R.drawable.ic_collapse));
            }
        });


        rlPassangerInfantTrain3 = findViewById(R.id.rlPassangerInfantTrain3);
        cardPassangerInfant3 = findViewById(R.id.cardPassangerInfantTrain3);
//        cardPassangerInfant3.setOnClickListener(this);
        noUrutInfant3 = findViewById(R.id.textUrutInfantTrain3);
        textContentInfant3 = findViewById(R.id.textContentInfantTrain3);
        textActionInfant3 = findViewById(R.id.textActionInfantTrain3);
        imageActionInfant3 = findViewById(R.id.imageActionInfantTrain3);
        linContentInfantTrain3 = findViewById(R.id.linContentInfantTrain3);
        linContentInfantTrain3.setOnClickListener(this);
//        textActionInfant3.setOnClickListener(this);
//        imageActionInfant3.setOnClickListener(this);

        imageViewExpandPassangerInfantTrain3 = findViewById(R.id.imageViewExpandPassangerInfantTrain3);
        imageViewExpandPassangerInfantTrain3.setBackground(ContextCompat.getDrawable(TrainBookActivity.this, R.drawable.ic_baby));
//        imageViewExpandPassangerAdult1.setVisibility(View.GONE);
        panelPassangerInfantTrain3Detail = findViewById(R.id.panelPassangerInfantTrain3Detail);

        panelPassangerInfantTrain3Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            public void onCollapse(View handle, View content) {
                ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                imageView.setBackground(ContextCompat.getDrawable(TrainBookActivity.this, R.drawable.ic_expand));
            }

            public void onExpand(View handle, View content) {
                ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                imageView.setBackground(ContextCompat.getDrawable(TrainBookActivity.this, R.drawable.ic_collapse));
            }
        });


        rlPassangerInfantTrain4 = findViewById(R.id.rlPassangerInfantTrain4);
        cardPassangerInfant4 = findViewById(R.id.cardPassangerInfantTrain4);
//        cardPassangerInfant4.setOnClickListener(this);
        noUrutInfant4 = findViewById(R.id.textUrutInfantTrain4);
        textContentInfant4 = findViewById(R.id.textContentInfantTrain4);
        textActionInfant4 = findViewById(R.id.textActionInfantTrain4);
        imageActionInfant4 = findViewById(R.id.imageActionInfantTrain4);
        linContentInfantTrain4 = findViewById(R.id.linContentInfantTrain4);
        linContentInfantTrain4.setOnClickListener(this);
//        textActionInfant4.setOnClickListener(this);
//        imageActionInfant4.setOnClickListener(this);

        imageViewExpandPassangerInfantTrain4 = findViewById(R.id.imageViewExpandPassangerInfantTrain4);
        imageViewExpandPassangerInfantTrain4.setBackground(ContextCompat.getDrawable(TrainBookActivity.this, R.drawable.ic_baby));
//        imageViewExpandPassangerAdult1.setVisibility(View.GONE);
        panelPassangerInfantTrain4Detail = findViewById(R.id.panelPassangerInfantTrain4Detail);

        panelPassangerInfantTrain4Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            public void onCollapse(View handle, View content) {
                ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                imageView.setBackground(ContextCompat.getDrawable(TrainBookActivity.this, R.drawable.ic_expand));
            }

            public void onExpand(View handle, View content) {
                ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                imageView.setBackground(ContextCompat.getDrawable(TrainBookActivity.this, R.drawable.ic_collapse));
            }
        });

        textHpKontak = findViewById(R.id.textHpKontak);
        textHpKontak.setText(PreferenceClass.getUser().getNotelp_pemilik().replace("+62", "0"));
        textEmailKontak = findViewById(R.id.textEmailKontak);
        textEmailKontak.setText(PreferenceClass.getUser().getEmail_pemilik());

        btnPesan = findViewById(R.id.btn_pesan);
        MaterialRippleLayout.on(btnPesan).rippleOverlay(true)
                .rippleAlpha(0.2f)
                //.rippleColor(0xFF585858)
                .rippleColor(R.color.md_orange_200)
                .rippleHover(true)
                .create();
        btnPesan.setOnClickListener(this);

        //textOrigin.setText(PreferenceClass.getString(TrainKeyPreference.stationNamaAsal, ""));
        textOrigin.setText(PreferenceClass.getString(TrainKeyPreference.stationNamaAsal, "") + " (" + PreferenceClass.getString(TrainKeyPreference.stationKodeAsal, "") + ")");
//        textDestination.setText(PreferenceClass.getString(TrainKeyPreference.stationNamaTujuan, ""));
        textDestination.setText(PreferenceClass.getString(TrainKeyPreference.stationNamaTujuan, "") + " (" + PreferenceClass.getString(TrainKeyPreference.stationKodeTujuan, "") + ")");


        textTrainNo.setText(PreferenceClass.getString(TrainKeyPreference.trainName, "") + " " + PreferenceClass.getString(TrainKeyPreference.classes, ""));

        textTimeDept.setText(PreferenceClass.getString(TrainKeyPreference.departureTime, ""));
        textTimeArr.setText(PreferenceClass.getString(TrainKeyPreference.arrivalTime, ""));
        //  textTimeDept.setText(timeDept);
        //  textTimeArr.setText(timeArr);


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

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE,dd MMM yyyy", SBFApplication.config.locale);
        deptDate = sdf.format(dateDept);
        arrDate = sdf.format(dateArr);

        textDateDept.setText(deptDate);
        textDateArr.setText(arrDate);

//        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
//        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
//        formatRp.setCurrencySymbol("Rp. ");
//        formatRp.setGroupingSeparator('.');
//        kursIndonesia.setDecimalFormatSymbols(formatRp);
        textTotal.setText(utilBand.formatRupiah(PreferenceClass.getInt(TrainKeyPreference.priceAdult, 0)));


        countAdult = PreferenceClass.getInt(TrainKeyPreference.countAdultTrain, 1);
        countChild = PreferenceClass.getInt(TrainKeyPreference.countChildTrain, 0);
        countInfant = PreferenceClass.getInt(TrainKeyPreference.countInfantTrain, 0);

        Log.d(TAG, "onCreate: " + countAdult + " " + countInfant);
//        adultPassengger = new String[countAdult];
//        childPassengger = new String[countChild];
//        infantPassengger = new String[countInfant];

        if (countAdult == 2) {

            cardPassangerAdult2.setVisibility(View.VISIBLE);

        } else if (countAdult == 3) {
            cardPassangerAdult2.setVisibility(View.VISIBLE);
            cardPassangerAdult3.setVisibility(View.VISIBLE);
        } else if (countAdult == 4) {
            cardPassangerAdult2.setVisibility(View.VISIBLE);
            cardPassangerAdult3.setVisibility(View.VISIBLE);
            cardPassangerAdult4.setVisibility(View.VISIBLE);
        }


        if (countInfant == 1) {
            noUrutInfant1.setText(String.valueOf(countAdult + 1));
            cardPassangerInfant1.setVisibility(View.VISIBLE);
        } else if (countInfant == 2) {
            noUrutInfant1.setText(String.valueOf(countAdult + 1));
            noUrutInfant2.setText(String.valueOf(countAdult + 2));
            cardPassangerInfant1.setVisibility(View.VISIBLE);
            cardPassangerInfant2.setVisibility(View.VISIBLE);

        } else if (countInfant == 3) {
            noUrutInfant1.setText(String.valueOf(countAdult + 1));
            noUrutInfant2.setText(String.valueOf(countAdult + 2));
            noUrutInfant3.setText(String.valueOf(countAdult + 3));
            cardPassangerInfant1.setVisibility(View.VISIBLE);
            cardPassangerInfant2.setVisibility(View.VISIBLE);
            cardPassangerAdult3.setVisibility(View.VISIBLE);
        } else if (countInfant == 4) {
            noUrutInfant1.setText(String.valueOf(countAdult + 1));
            noUrutInfant2.setText(String.valueOf(countAdult + 2));
            noUrutInfant3.setText(String.valueOf(countAdult + 3));
            noUrutInfant4.setText(String.valueOf(countAdult + 4));
            cardPassangerInfant1.setVisibility(View.VISIBLE);
            cardPassangerInfant2.setVisibility(View.VISIBLE);
            cardPassangerInfant3.setVisibility(View.VISIBLE);
            cardPassangerInfant4.setVisibility(View.VISIBLE);
        }


        textViewNoIdentitasAdultTrain1 = findViewById(R.id.textViewNoIdentitasAdultTrain1);
        textViewNoIdentitasAdultTrain2 = findViewById(R.id.textViewNoIdentitasAdultTrain2);
        textViewNoIdentitasAdultTrain3 = findViewById(R.id.textViewNoIdentitasAdultTrain3);
        textViewNoIdentitasAdultTrain4 = findViewById(R.id.textViewNoIdentitasAdultTrain4);

        textViewMobileAdultTrain1 = findViewById(R.id.textViewMobileAdultTrain1);
        textViewMobileAdultTrain2 = findViewById(R.id.textViewMobileAdultTrain2);
        textViewMobileAdultTrain3 = findViewById(R.id.textViewMobileAdultTrain3);
        textViewMobileAdultTrain4 = findViewById(R.id.textViewMobileAdultTrain4);

        textViewTanggalLahirInfantTrain1 = findViewById(R.id.textViewTanggalLahirInfantTrain1);
        textViewTanggalLahirInfantTrain2 = findViewById(R.id.textViewTanggalLahirInfantTrain2);
        textViewTanggalLahirInfantTrain3 = findViewById(R.id.textViewTanggalLahirInfantTrain3);
        textViewTanggalLahirInfantTrain4 = findViewById(R.id.textViewTanggalLahirInfantTrain4);

    }


//    public synchronized TrainBookActivity getInstancex() {
//        return instance;
//    }

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

            openTopDialog(true);
        } else if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

//    public void closeTopSheet(View v) {
//        dialog.dismiss();
//    }


    public void onBackPressed() {
        if (isStillRunning) {
            RequestUtilsTravel.cancelTravel();
        }
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void openFormAdultTrain(@NonNull View v) {
        int id = v.getId();
        Intent intent = new Intent(TrainBookActivity.this, TrainFormBookAdultActivity.class);


        String nameAdult;
        String idAdult;
        String phoneAdult;
        String bornAdult;
        String bornShowAdult;
       // if (id == R.id.imageActionAdultTrain1) {
        if (id == R.id.linContentAdultTrain1) {
            if (textActionAdult1.getVisibility() == View.GONE) {
                intent.putExtra(TrainKeyPreference.isNewTrain, true);
                startActivityForResult(intent, TravelActionCode.ADULT1);
            }else{
                intent.putExtra(TrainKeyPreference.isNewTrain, false);
                //    titleAdult= (String) textContentAdult1.getTag(R.id.titleAdult1);
                //    titleTempAdult=(String)textContentAdult1.getTag(R.id.titleTempAdult1);
                nameAdult = (String) textContentAdult1.getTag(R.id.nameAdult1);
                idAdult = (String) textContentAdult1.getTag(R.id.idAdult1);
                phoneAdult = (String) textContentAdult1.getTag(R.id.phoneAdult1);
                bornAdult = (String) textContentAdult1.getTag(R.id.bornAdult1);
                bornShowAdult = (String) textContentAdult1.getTag(R.id.bornShowAdult1);

                //    intent.putExtra("titleAdult", titleAdult);
                //    intent.putExtra("titleTempAdult", titleTempAdult);
                intent.putExtra("nameAdult", nameAdult);
                intent.putExtra("idAdult", idAdult);
                intent.putExtra("phoneAdult", phoneAdult);
                intent.putExtra("bornAdult", bornAdult);
                intent.putExtra("bornShowAdult", bornShowAdult);


                startActivityForResult(intent, TravelActionCode.ADULT1);


            }
//        } else if (id == R.id.textActionAdultTrain1) {
//
//        } else if (id == R.id.imageActionAdultTrain2) {
        } else if (id == R.id.linContentAdultTrain2) {
            if (textActionAdult2.getVisibility() == View.GONE) {
                intent.putExtra(TrainKeyPreference.isNewTrain, true);
                startActivityForResult(intent, TravelActionCode.ADULT2);
            }else{
                intent.putExtra(TrainKeyPreference.isNewTrain, false);
                //  titleAdult= (String) textContentAdult2.getTag(R.id.titleAdult2);
                //  titleTempAdult=(String)textContentAdult2.getTag(R.id.titleTempAdult2);
                nameAdult = (String) textContentAdult2.getTag(R.id.nameAdult2);
                idAdult = (String) textContentAdult2.getTag(R.id.idAdult2);
                phoneAdult = (String) textContentAdult2.getTag(R.id.phoneAdult2);
                bornAdult = (String) textContentAdult2.getTag(R.id.bornAdult2);
                bornShowAdult = (String) textContentAdult2.getTag(R.id.bornShowAdult2);

                //  intent.putExtra("titleAdult", titleAdult);
                //  intent.putExtra("titleTempAdult", titleTempAdult);
                intent.putExtra("nameAdult", nameAdult);
                intent.putExtra("idAdult", idAdult);
                intent.putExtra("phoneAdult", phoneAdult);
                intent.putExtra("bornAdult", bornAdult);
                intent.putExtra("bornShowAdult", bornShowAdult);
                startActivityForResult(intent, TravelActionCode.ADULT2);
            }
//        } else if (id == R.id.textActionAdultTrain2) {


        } else if (id == R.id.linContentAdultTrain3) {
            if (textActionAdult3.getVisibility() == View.GONE) {
                intent.putExtra(TrainKeyPreference.isNewTrain, true);
                startActivityForResult(intent, TravelActionCode.ADULT3);
            }else{
                intent.putExtra(TrainKeyPreference.isNewTrain, false);
                // titleAdult= (String) textContentAdult3.getTag(R.id.titleAdult3);
                //  titleTempAdult=(String)textContentAdult3.getTag(R.id.titleTempAdult3);
                nameAdult = (String) textContentAdult3.getTag(R.id.nameAdult3);
                idAdult = (String) textContentAdult3.getTag(R.id.idAdult3);
                phoneAdult = (String) textContentAdult3.getTag(R.id.phoneAdult3);
                bornAdult = (String) textContentAdult3.getTag(R.id.bornAdult3);
                bornShowAdult = (String) textContentAdult3.getTag(R.id.bornShowAdult3);

                //    intent.putExtra("titleAdult", titleAdult);
                //    intent.putExtra("titleTempAdult", titleTempAdult);
                intent.putExtra("nameAdult", nameAdult);
                intent.putExtra("idAdult", idAdult);
                intent.putExtra("phoneAdult", phoneAdult);
                intent.putExtra("bornAdult", bornAdult);
                intent.putExtra("bornShowAdult", bornShowAdult);
                startActivityForResult(intent, TravelActionCode.ADULT3);

            }
//        } else if (id == R.id.textActionAdultTrain3) {

//        } else if (id == R.id.imageActionAdultTrain4) {
        } else if (id == R.id.linContentAdultTrain4) {
            if (textActionAdult4.getVisibility() == View.GONE) {
                intent.putExtra(TrainKeyPreference.isNewTrain, true);
                startActivityForResult(intent, TravelActionCode.ADULT4);
            }else{
                intent.putExtra(TrainKeyPreference.isNewTrain, false);
                //    titleAdult= (String) textContentAdult4.getTag(R.id.titleAdult4);
                //    titleTempAdult=(String)textContentAdult4.getTag(R.id.titleTempAdult4);
                nameAdult = (String) textContentAdult4.getTag(R.id.nameAdult4);
                idAdult = (String) textContentAdult4.getTag(R.id.idAdult4);
                phoneAdult = (String) textContentAdult4.getTag(R.id.phoneAdult4);
                bornAdult = (String) textContentAdult4.getTag(R.id.bornAdult4);
                bornShowAdult = (String) textContentAdult4.getTag(R.id.bornShowAdult4);

                //  intent.putExtra("titleAdult", titleAdult);
                //  intent.putExtra("titleTempAdult", titleTempAdult);
                intent.putExtra("nameAdult", nameAdult);
                intent.putExtra("idAdult", idAdult);
                intent.putExtra("phoneAdult", phoneAdult);
                intent.putExtra("bornAdult", bornAdult);
                intent.putExtra("bornShowAdult", bornShowAdult);
                startActivityForResult(intent, TravelActionCode.ADULT4);
            }
//        } else if (id == R.id.textActionAdultTrain4) {

        }
        //  overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }


    public void openFormInfantTrain(@NonNull View v) {
        int id = v.getId();
        Intent intent = new Intent(TrainBookActivity.this, TrainFormBookInfantActivity.class);


        String nameInfant;
        String idInfant;
        String phoneInfant;
        String bornInfant;
        String bornShowInfant;
        if (id == R.id.linContentInfantTrain1) {
            if (textActionInfant1.getVisibility() == View.GONE) {
                intent.putExtra(TrainKeyPreference.isNewTrain, true);
                startActivityForResult(intent, TravelActionCode.INFANT1);
            }else{
                intent.putExtra(TrainKeyPreference.isNewTrain, false);
                //    titleInfant= (String) textContentInfant1.getTag(R.id.titleInfant1);
                //    titleTempInfant=(String)textContentInfant1.getTag(R.id.titleTempInfant1);
                nameInfant = (String) textContentInfant1.getTag(R.id.nameInfant1);

                bornInfant = (String) textContentInfant1.getTag(R.id.bornInfant1);
                bornShowInfant = (String) textContentInfant1.getTag(R.id.bornShowInfant1);

                //   intent.putExtra("titleInfant", titleInfant);
                //   intent.putExtra("titleTempInfant", titleTempInfant);
                intent.putExtra("nameInfant", nameInfant);

                intent.putExtra("bornInfant", bornInfant);
                intent.putExtra("bornShowInfant", bornShowInfant);
                startActivityForResult(intent, TravelActionCode.INFANT1);

            }
//        } else if (id == R.id.textActionInfantTrain1) {


//        } else if (id == R.id.imageActionInfantTrain2) {
        } else if (id == R.id.linContentInfantTrain2) {
            if (textActionInfant2.getVisibility() == View.GONE) {
                intent.putExtra(TrainKeyPreference.isNewTrain, true);
                startActivityForResult(intent, TravelActionCode.INFANT2);
            }else{
                intent.putExtra(TrainKeyPreference.isNewTrain, false);
                //   titleInfant= (String) textContentInfant2.getTag(R.id.titleInfant2);
                //   titleTempInfant=(String)textContentInfant2.getTag(R.id.titleTempInfant2);
                nameInfant = (String) textContentInfant2.getTag(R.id.nameInfant2);

                bornInfant = (String) textContentInfant2.getTag(R.id.bornInfant2);
                bornShowInfant = (String) textContentInfant2.getTag(R.id.bornShowInfant2);

                //   intent.putExtra("titleInfant", titleInfant);
                //   intent.putExtra("titleTempInfant", titleTempInfant);
                intent.putExtra("nameInfant", nameInfant);

                intent.putExtra("bornInfant", bornInfant);
                intent.putExtra("bornShowInfant", bornShowInfant);

                startActivityForResult(intent, TravelActionCode.INFANT2);

            }
//        } else if (id == R.id.textActionInfantTrain2) {

//        } else if (id == R.id.imageActionInfantTrain3) {
        } else if (id == R.id.linContentInfantTrain3) {
            if (textActionInfant3.getVisibility() == View.GONE) {
                intent.putExtra(TrainKeyPreference.isNewTrain, true);
                startActivityForResult(intent, TravelActionCode.INFANT3);
            }else{
                intent.putExtra(TrainKeyPreference.isNewTrain, false);
                //    titleInfant= (String) textContentInfant3.getTag(R.id.titleInfant3);
                //    titleTempInfant=(String)textContentInfant3.getTag(R.id.titleTempInfant3);
                nameInfant = (String) textContentInfant3.getTag(R.id.nameInfant3);

                bornInfant = (String) textContentInfant3.getTag(R.id.bornInfant3);
                bornShowInfant = (String) textContentInfant3.getTag(R.id.bornShowInfant3);

                //   intent.putExtra("titleInfant", titleInfant);
                //   intent.putExtra("titleTempInfant", titleTempInfant);
                intent.putExtra("nameInfant", nameInfant);

                intent.putExtra("bornInfant", bornInfant);
                intent.putExtra("bornShowInfant", bornShowInfant);
                startActivityForResult(intent, TravelActionCode.INFANT3);

            }
//        } else if (id == R.id.textActionInfantTrain3) {

//        } else if (id == R.id.imageActionInfantTrain4) {
        } else if (id == R.id.linContentInfantTrain4) {
            if (textActionInfant4.getVisibility() == View.GONE) {
                intent.putExtra(TrainKeyPreference.isNewTrain, true);
                startActivityForResult(intent, TravelActionCode.INFANT4);
            }else{
                intent.putExtra(TrainKeyPreference.isNewTrain, false);
                //   titleInfant= (String) textContentInfant4.getTag(R.id.titleInfant4);
                //   titleTempInfant=(String)textContentInfant4.getTag(R.id.titleTempInfant4);
                nameInfant = (String) textContentInfant4.getTag(R.id.nameInfant4);

                bornInfant = (String) textContentInfant4.getTag(R.id.bornInfant4);
                bornShowInfant = (String) textContentInfant4.getTag(R.id.bornShowInfant4);

                //  intent.putExtra("titleInfant", titleInfant);
                //  intent.putExtra("titleTempInfant", titleTempInfant);
                intent.putExtra("nameInfant", nameInfant);

                intent.putExtra("bornInfant", bornInfant);
                intent.putExtra("bornShowInfant", bornShowInfant);
                startActivityForResult(intent, TravelActionCode.INFANT4);
            }
//        } else if (id == R.id.textActionInfantTrain4) {


        }

        //  overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }


    //    public static ArrayList<String> passengersList = new ArrayList<String>();
    //  public ArrayList<String> passengersList = new ArrayList();
    @NonNull
    ArrayList<String> listNameAdult = new ArrayList<String>();
    @NonNull
    ArrayList<String> listBirthdateAdult = new ArrayList<String>();
    @NonNull
    ArrayList<String> listIdAdult = new ArrayList<String>();
    @NonNull
    ArrayList<String> listPhoneAdult = new ArrayList<String>();

    @NonNull
    public ArrayList<String> listNameInfant = new ArrayList<String>();
    @NonNull
    ArrayList<String> listBirthdateInfant = new ArrayList<String>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: " +requestCode+" "+resultCode+" "+ data+" ");

        //  String initTitle ;
        String namePassager;
        String fullName[];
        String firstName;
        String lastName;
        String noId;
        String bornDate;
        String phone;


        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == TravelActionCode.IS_FROM_PAY) {

//                if (isStillRunning) {
//                    RequestUtilsTravel.cancelTravel();
//                }
              //  Log.d(TAG, "onActivityResult: "+requestCode);
                //  Intent intent = new Intent(FlightBookActivity.this, FlightSearchActivity.class);


               if(data!=null&&data.getAction()!=null) {
                   Intent intent = new Intent();
                   Log.d(TAG, "onActivityResult: "+data.getAction());

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
               }else{
                   setResult(RESULT_OK);
               }

                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }else {
                if (data.getStringExtra("status").equals("adult")) {// ADULT
                    if (requestCode == TravelActionCode.ADULT1) {// adult 1
                        imageViewExpandPassangerAdultTrain1.performClick();
                       // panelPassangerAdultTrain1Detail.setBackgroundResource(R.drawable.shape_card_primary);
                        cardPassangerAdult1.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
                        if (!data.getBooleanExtra(TrainKeyPreference.isNewTrain, false)) {
                            //if (passengersList.size() > 0) {
                            //           passengersList.remove(countAdult-1);
                            listNameAdult.remove(0);
                            listBirthdateAdult.remove(0);
                            listIdAdult.remove(0);
                            listPhoneAdult.remove(0);
                            // }
                        }
                        //   Log.d(TAG, "onActivityResult adult 1: " + MemoryStore.get(this, "adultBorn") + " " + data.getStringExtra("adultBorn"));
                        textContentAdult1.setText(data.getStringExtra("adultName"));

                        // textContentAdult1.setTag(R.id.titleAdult1,data.getStringExtra("adultTitle"));
                        // textContentAdult1.setTag(R.id.titleTempAdult1,data.getStringExtra("adultTitleTemp"));
                        textContentAdult1.setTag(R.id.nameAdult1, data.getStringExtra("adultName"));
                        textContentAdult1.setTag(R.id.idAdult1, data.getStringExtra("adultId"));
                        textContentAdult1.setTag(R.id.phoneAdult1, data.getStringExtra("adultPhone"));
                        textContentAdult1.setTag(R.id.bornAdult1, data.getStringExtra("adultBorn"));
                        textContentAdult1.setTag(R.id.bornShowAdult1, data.getStringExtra("adultBornShow"));
//                    MemoryStore.set(BookTrainActivity.this, "adultTitle", data.getStringExtra("adultTitle"));
//                    MemoryStore.set(BookTrainActivity.this, "adultTitleTemp", data.getStringExtra("adultTitleTemp"));
//                    MemoryStore.set(BookTrainActivity.this, "adultName", data.getStringExtra("adultName"));
//                    MemoryStore.set(BookTrainActivity.this, "adultId", data.getStringExtra("adultId"));
//                    MemoryStore.set(BookTrainActivity.this, "adultPhone", data.getStringExtra("adultPhone"));
//                    MemoryStore.set(BookTrainActivity.this, "adultBornShow", data.getStringExtra("adultBornShow"));
//                    MemoryStore.set(BookTrainActivity.this, "adultBorn", data.getStringExtra("adultBorn"));
//                    if (data.getStringExtra("adultTitleTemp").equals("Tn")) {
//                        initTitle = "MR";
//                    } else if (data.getStringExtra("adultTitleTemp").equals("Ny")) {
//                        initTitle = "MRS";
//                    } else if (data.getStringExtra("adultTitleTemp").equals("Nona")) {
//                        initTitle = "MISS";
//                    }
                        textViewNoIdentitasAdultTrain1.setText(data.getStringExtra("adultId"));
                        textViewMobileAdultTrain1.setText(data.getStringExtra("adultPhone"));

                        namePassager = data.getStringExtra("adultName");
//                    fullName = data.getStringExtra("adultName").split(" ");
//                    firstName = fullName[0];
//                    if (fullName.length > 1) {
//
//                        lastName = fullName[1];
//                    } else {
//                        lastName = fullName[0];
//                    }
                        noId = data.getStringExtra("adultId");
                        phone = data.getStringExtra("adultPhone");
                        bornDate = data.getStringExtra("adultBorn");


//                    passengersList.add(data.getStringExtra("adultTitleTemp") + ". " + firstName + " " + lastName);
                        //  passengersList.add(countAdult-1,data.getStringExtra("adultTitleTemp") + ". " + namePassager);
                        //  Log.d(TAG, "onActivityResult: ADT;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";" + noId + ";::623187762;::6285655141006;;;;;KTP;ID");
                        //       adultPassengger[0] = namePassager + ";" + bornDate + ";" + noId + ";::623187762;::"+phone+";;;;;KTP;"+noId;
//                    listNameAdult.add(firstName + " " + lastName);
                        listNameAdult.add(0, namePassager);
                        listBirthdateAdult.add(0, bornDate);
                        listIdAdult.add(0, noId);
                        listPhoneAdult.add(0, phone);

//                    HashMap<String,String> adult = new HashMap<>() ;
//
//                        adult.put("name", firstName + " " + lastName);
//                        adult.put("birthdate", bornDate);
//                        adult.put("phone", "6285655141006");
//                        adult.put("idNumber", noId);
//
//                    adultPassengger[0]+=adult;

                        textActionAdult1.setVisibility(View.VISIBLE);
                        imageActionAdult1.setVisibility(View.GONE);

                    } else if (requestCode == TravelActionCode.ADULT2) {// adult 2
                        imageViewExpandPassangerAdultTrain2.performClick();
//                        panelPassangerAdultTrain2Detail.setBackgroundResource(R.drawable.shape_card_primary);
                        cardPassangerAdult2.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
                        if (!data.getBooleanExtra(TrainKeyPreference.isNewTrain, false)) {
                            // if (passengersList.size() > 0) {
                            //         passengersList.remove(1);
                            //  }
                            //         passengersList.remove(countAdult);
                            listNameAdult.remove(1);
                            listBirthdateAdult.remove(1);
                            listIdAdult.remove(1);
                            listPhoneAdult.remove(1);

                        }
                        //     Log.d(TAG, "onActivityResult adult 2: " + MemoryStore.get(this, "adultBorn") + " " + data.getStringExtra("adultBorn"));
                        textContentAdult2.setText(data.getStringExtra("adultName"));

                        //  textContentAdult2.setTag(R.id.titleAdult2,data.getStringExtra("adultTitle"));
                        //  textContentAdult2.setTag(R.id.titleTempAdult2,data.getStringExtra("adultTitleTemp"));
                        textContentAdult2.setTag(R.id.nameAdult2, data.getStringExtra("adultName"));
                        textContentAdult2.setTag(R.id.idAdult2, data.getStringExtra("adultId"));
                        textContentAdult2.setTag(R.id.phoneAdult2, data.getStringExtra("adultPhone"));
                        textContentAdult2.setTag(R.id.bornAdult2, data.getStringExtra("adultBorn"));
                        textContentAdult2.setTag(R.id.bornShowAdult2, data.getStringExtra("adultBornShow"));

//                    MemoryStore.set(BookTrainActivity.this, "adultTitle", data.getStringExtra("adultTitle"));
//                    MemoryStore.set(BookTrainActivity.this, "adultTitleTemp", data.getStringExtra("adultTitleTemp"));
//                    MemoryStore.set(BookTrainActivity.this, "adultName", data.getStringExtra("adultName"));
//                    MemoryStore.set(BookTrainActivity.this, "adultId", data.getStringExtra("adultId"));
//                    MemoryStore.set(BookTrainActivity.this, "adultPhone", data.getStringExtra("adultPhone"));
//                    MemoryStore.set(BookTrainActivity.this, "adultBornShow", data.getStringExtra("adultBornShow"));
//                    MemoryStore.set(BookTrainActivity.this, "adultBorn", data.getStringExtra("adultBorn"));
//                    if (data.getStringExtra("adultTitleTemp").equals("Tn")) {
//                        initTitle = "MR";
//                    } else if (data.getStringExtra("adultTitleTemp").equals("Ny")) {
//                        initTitle = "MRS";
//                    } else if (data.getStringExtra("adultTitleTemp").equals("Nona")) {
//                        initTitle = "MISS";
//                    }

                        textViewNoIdentitasAdultTrain2.setText(data.getStringExtra("adultId"));
                        textViewMobileAdultTrain2.setText(data.getStringExtra("adultPhone"));
                        namePassager = data.getStringExtra("adultName");
//                    fullName = data.getStringExtra("adultName").split(" ");
//                    firstName = fullName[0];
//                    if (fullName.length > 1) {
//
//                        lastName = fullName[1];
//                    } else {
//                        lastName = fullName[0];
//                    }
                        noId = data.getStringExtra("adultId");
                        phone = data.getStringExtra("adultPhone");
                        bornDate = data.getStringExtra("adultBorn");

                        //     passengersList.add(countAdult,data.getStringExtra("adultTitleTemp") + ". " +namePassager);
                        //    Log.d(TAG, "onActivityResult: ADT;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";" + noId + ";::623187762;::6285655141006;;;;;KTP;ID");
                        //   adultPassengger[1] = initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";" + noId + ";::623187762;::6285655141006;;;;;KTP;ID";
                        listNameAdult.add(1, namePassager);
                        listBirthdateAdult.add(1, bornDate);
                        listIdAdult.add(1, noId);
                        listPhoneAdult.add(1, phone);
//                    HashMap<String,String> adult = new HashMap<>() ;
//
//                    adult.put("name", firstName + " " + lastName);
//                    adult.put("birthdate", bornDate);
//                    adult.put("phone", "6285655141006");
//                    adult.put("idNumber", noId);
//
//                    adultPassengger[1]+=adult;
                        textActionAdult2.setVisibility(View.VISIBLE);
                        imageActionAdult2.setVisibility(View.GONE);
                        //   Log.d(TAG, "onActivityResult: " + passengersList.toString());
                    } else if (requestCode == TravelActionCode.ADULT3) {// adult 3
                        imageViewExpandPassangerAdultTrain3.performClick();
//                        panelPassangerAdultTrain3Detail.setBackgroundResource(R.drawable.shape_card_primary);
                        cardPassangerAdult3.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
                        if (!data.getBooleanExtra(TrainKeyPreference.isNewTrain, false)) {
                            // if (passengersList.size() > 0) {
                            //         passengersList.remove(1);
                            //  }
                            //     passengersList.remove(countAdult+1);
                            listNameAdult.remove(2);
                            listBirthdateAdult.remove(2);
                            listIdAdult.remove(2);
                            listPhoneAdult.remove(2);
                        }
                        //     Log.d(TAG, "onActivityResult adult 3: " + MemoryStore.get(this, "adultBorn") + " " + data.getStringExtra("adultBorn"));
                        textContentAdult3.setText(data.getStringExtra("adultName"));

                        // textContentAdult3.setTag(R.id.titleAdult3,data.getStringExtra("adultTitle"));
                        //  textContentAdult3.setTag(R.id.titleTempAdult3,data.getStringExtra("adultTitleTemp"));
                        textContentAdult3.setTag(R.id.nameAdult3, data.getStringExtra("adultName"));
                        textContentAdult3.setTag(R.id.idAdult3, data.getStringExtra("adultId"));
                        textContentAdult3.setTag(R.id.phoneAdult3, data.getStringExtra("adultPhone"));
                        textContentAdult3.setTag(R.id.bornAdult3, data.getStringExtra("adultBorn"));
                        textContentAdult3.setTag(R.id.bornShowAdult3, data.getStringExtra("adultBornShow"));
//                    MemoryStore.set(BookTrainActivity.this, "adultTitle", data.getStringExtra("adultTitle"));
//                    MemoryStore.set(BookTrainActivity.this, "adultTitleTemp", data.getStringExtra("adultTitleTemp"));
//                    MemoryStore.set(BookTrainActivity.this, "adultName", data.getStringExtra("adultName"));
//                    MemoryStore.set(BookTrainActivity.this, "adultId", data.getStringExtra("adultId"));
//                    MemoryStore.set(BookTrainActivity.this, "adultPhone", data.getStringExtra("adultPhone"));
//                    MemoryStore.set(BookTrainActivity.this, "adultBornShow", data.getStringExtra("adultBornShow"));
//                    MemoryStore.set(BookTrainActivity.this, "adultBorn", data.getStringExtra("adultBorn"));
//                    if (data.getStringExtra("adultTitleTemp").equals("Tn")) {
//                        initTitle = "MR";
//                    } else if (data.getStringExtra("adultTitleTemp").equals("Ny")) {
//                        initTitle = "MRS";
//                    } else if (data.getStringExtra("adultTitleTemp").equals("Nona")) {
//                        initTitle = "MISS";
//                    }
                        textViewNoIdentitasAdultTrain3.setText(data.getStringExtra("adultId"));
                        textViewMobileAdultTrain3.setText(data.getStringExtra("adultPhone"));
                        namePassager = data.getStringExtra("adultName");
//                    fullName = data.getStringExtra("adultName").split(" ");
//                    firstName = fullName[0];
//                    if (fullName.length > 1) {
//
//                        lastName = fullName[1];
//                    } else {
//                        lastName = fullName[0];
//                    }
                        noId = data.getStringExtra("adultId");
                        phone = data.getStringExtra("adultPhone");
                        bornDate = data.getStringExtra("adultBorn");


                        //   passengersList.add(countAdult+1,data.getStringExtra("adultTitleTemp") + ". " + namePassager);
                        //     Log.d(TAG, "onActivityResult: ADT;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";" + noId + ";::623187762;::6285655141006;;;;;KTP;ID");
//                    adultPassengger[2] = "ADT;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";" + noId + ";::623187762;::6285655141006;;;;;KTP;ID";
                        listNameAdult.add(2, namePassager);
                        listBirthdateAdult.add(2, bornDate);
                        listIdAdult.add(2, noId);
                        listPhoneAdult.add(2, phone);
//                    HashMap<String,String> adult = new HashMap<>() ;
//
//                    adult.put("name", firstName + " " + lastName);
//                    adult.put("birthdate", bornDate);
//                    adult.put("phone", "6285655141006");
//                    adult.put("idNumber", noId);
//
//                    adultPassengger[2]+=adult;

                        textActionAdult3.setVisibility(View.VISIBLE);
                        imageActionAdult3.setVisibility(View.GONE);
                        //  Log.d(TAG, "onActivityResult: " + passengersList.toString());
                    } else if (requestCode == TravelActionCode.ADULT4) {// adult 4
                        imageViewExpandPassangerAdultTrain4.performClick();
//                        panelPassangerAdultTrain4Detail.setBackgroundResource(R.drawable.shape_card_primary);
                        cardPassangerAdult4.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
                        if (!data.getBooleanExtra(TrainKeyPreference.isNewTrain, false)) {
                            // if (passengersList.size() > 0) {
                            //         passengersList.remove(1);
                            //  }
                            //      passengersList.remove(countAdult+2);
                            listNameAdult.remove(3);
                            listBirthdateAdult.remove(3);
                            listIdAdult.remove(3);
                            listPhoneAdult.remove(3);
                        }
                        //       Log.d(TAG, "onActivityResult adult 4: " + MemoryStore.get(this, "adultBorn") + " " + data.getStringExtra("adultBorn"));
                        textContentAdult4.setText(data.getStringExtra("adultName"));

                        //  textContentAdult4.setTag(R.id.titleAdult4,data.getStringExtra("adultTitle"));
                        //  textContentAdult4.setTag(R.id.titleTempAdult4,data.getStringExtra("adultTitleTemp"));
                        textContentAdult4.setTag(R.id.nameAdult4, data.getStringExtra("adultName"));
                        textContentAdult4.setTag(R.id.idAdult4, data.getStringExtra("adultId"));
                        textContentAdult4.setTag(R.id.phoneAdult4, data.getStringExtra("adultPhone"));
                        textContentAdult4.setTag(R.id.bornAdult4, data.getStringExtra("adultBorn"));
                        textContentAdult4.setTag(R.id.bornShowAdult4, data.getStringExtra("adultBornShow"));

//                    MemoryStore.set(BookTrainActivity.this, "adultTitle", data.getStringExtra("adultTitle"));
//                    MemoryStore.set(BookTrainActivity.this, "adultTitleTemp", data.getStringExtra("adultTitleTemp"));
//                    MemoryStore.set(BookTrainActivity.this, "adultName", data.getStringExtra("adultName"));
//                    MemoryStore.set(BookTrainActivity.this, "adultId", data.getStringExtra("adultId"));
//                    MemoryStore.set(BookTrainActivity.this, "adultPhone", data.getStringExtra("adultPhone"));
//                    MemoryStore.set(BookTrainActivity.this, "adultBornShow", data.getStringExtra("adultBornShow"));
//                    MemoryStore.set(BookTrainActivity.this, "adultBorn", data.getStringExtra("adultBorn"));
//                    if (data.getStringExtra("adultTitleTemp").equals("Tn")) {
//                        initTitle = "MR";
//                    } else if (data.getStringExtra("adultTitleTemp").equals("Ny")) {
//                        initTitle = "MRS";
//                    } else if (data.getStringExtra("adultTitleTemp").equals("Nona")) {
//                        initTitle = "MISS";
//                    }
                        textViewNoIdentitasAdultTrain4.setText(data.getStringExtra("adultId"));
                        textViewMobileAdultTrain4.setText(data.getStringExtra("adultPhone"));
                        namePassager = data.getStringExtra("adultName");
//                    fullName = data.getStringExtra("adultName").split(" ");
//                    firstName = fullName[0];
//                    if (fullName.length > 1) {
//
//                        lastName = fullName[1];
//                    } else {
//                        lastName = fullName[0];
//                    }
                        noId = data.getStringExtra("adultId");
                        phone = data.getStringExtra("adultPhone");
                        bornDate = data.getStringExtra("adultBorn");


                        //     passengersList.add(countAdult+2,data.getStringExtra("adultTitleTemp") + ". " + namePassager);
                        //   Log.d(TAG, "onActivityResult: ADT;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";" + noId + ";::623187762;::6285655141006;;;;;KTP;ID");
                        // adultPassengger[3] = "ADT;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";" + noId + ";::623187762;::6285655141006;;;;;KTP;ID";
                        listNameAdult.add(3, namePassager);
                        listBirthdateAdult.add(3, bornDate);
                        listIdAdult.add(3, noId);
                        listPhoneAdult.add(3, phone);
//                    HashMap<String,String> adult = new HashMap<>() ;
//
//                    adult.put("name", firstName + " " + lastName);
//                    adult.put("birthdate", bornDate);
//                    adult.put("phone", "6285655141006");
//                    adult.put("idNumber", noId);
//
//                    adultPassengger[3]+=adult;

                        textActionAdult4.setVisibility(View.VISIBLE);
                        imageActionAdult4.setVisibility(View.GONE);
                        //    Log.d(TAG, "onActivityResult: " + passengersList.toString());
                    }

                } else if (data.getStringExtra("status").equals("infant")) {// INFANT
                    imageViewExpandPassangerInfantTrain1.performClick();
//                    panelPassangerInfantTrain1Detail.setBackgroundResource(R.drawable.shape_card_primary);
                    cardPassangerInfant1.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
                    if (requestCode == TravelActionCode.INFANT1) {// infant 1
                        if (!data.getBooleanExtra(TrainKeyPreference.isNewTrain, false)) {
                            //if (passengersList.size() > 0) {
                            //  passengersList.remove(0);
                            // }
                            listNameInfant.remove(0);
                            listBirthdateInfant.remove(0);
                        }
                        //    Log.d(TAG, "onActivityResult infant 1: " + MemoryStore.get(this, "infantBorn") + " " + data.getStringExtra("infantBorn"));
                        textContentInfant1.setText(data.getStringExtra("infantName"));

                        // textContentInfant1.setTag(R.id.titleInfant1,data.getStringExtra("infantTitle"));
                        // textContentInfant1.setTag(R.id.titleTempInfant1,data.getStringExtra("infantTitleTemp"));
                        textContentInfant1.setTag(R.id.nameInfant1, data.getStringExtra("infantName"));
                        textContentInfant1.setTag(R.id.bornInfant1, data.getStringExtra("infantBorn"));
                        textContentInfant1.setTag(R.id.bornShowInfant1, data.getStringExtra("infantBornShow"));

//                    MemoryStore.set(BookTrainActivity.this, "infantTitle", data.getStringExtra("infantTitle"));
//                    MemoryStore.set(BookTrainActivity.this, "infantTitleTemp", data.getStringExtra("infantTitleTemp"));
//                    MemoryStore.set(BookTrainActivity.this, "infantName", data.getStringExtra("infantName"));
//                    //     MemoryStore.set(BookTrainActivity.this, "infantId", data.getStringExtra("infantId"));
//                    MemoryStore.set(BookTrainActivity.this, "infantBornShow", data.getStringExtra("infantBornShow"));
//                    MemoryStore.set(BookTrainActivity.this, "infantBorn", data.getStringExtra("infantBorn"));
//                    if (data.getStringExtra("infantTitleTemp").equals("Tn")) {
//                        initTitle = "MSTR";
//                        //  } else if (data.getStringExtra("infantTitleTemp").equals("Ny")) {
//                        //      initTitle = "MRS";
//                    } else if (data.getStringExtra("infantTitleTemp").equals("Nona")) {
//                        initTitle = "MISS";
//                    }
                        textViewTanggalLahirInfantTrain1.setText(data.getStringExtra("infantBornShow"));
                        namePassager = data.getStringExtra("infantName");
                        //fullName = data.getStringExtra("infantName").split(" ");
//                    firstName = fullName[0];
//                    if (fullName.length > 1) {
//
//                        lastName = fullName[1];
//                    } else {
//                        lastName = fullName[0];
//                    }
                        //  noId = data.getStringExtra("infantId");
                        bornDate = data.getStringExtra("infantBorn");

                        //    passengersList.add(0,data.getStringExtra("infantTitleTemp") + ". " + namePassager);
                        //     Log.d(TAG, "onActivityResult: CHD;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1");
                        // infantPassengger[0] = "CHD;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1";
                        listNameInfant.add(0, namePassager);
                        listBirthdateInfant.add(0, bornDate);

//                    HashMap<String,String> infant = new HashMap<>() ;
//
//                    infant.put("name", firstName + " " + lastName);
//                    infant.put("birthdate", bornDate);
//
//
//                    infantPassengger[0]+=infant;
                        textActionInfant1.setVisibility(View.VISIBLE);
                        imageActionInfant1.setVisibility(View.GONE);
                    } else if (requestCode == TravelActionCode.INFANT2) {// infant 2
                        imageViewExpandPassangerInfantTrain2.performClick();
//                        panelPassangerInfantTrain2Detail.setBackgroundResource(R.drawable.shape_card_primary);
                        cardPassangerInfant2.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
                        if (!data.getBooleanExtra(TrainKeyPreference.isNewTrain, false)) {
                            //if (passengersList.size() > 0) {
                            //  passengersList.remove(0);
                            // }
                            listNameInfant.remove(1);
                            listBirthdateInfant.remove(1);
                        }
                        //   Log.d(TAG, "onActivityResult infant 2: " + MemoryStore.get(this, "infantBorn") + " " + data.getStringExtra("infantBorn"));
                        textContentInfant2.setText(data.getStringExtra("infantName"));
                        // textContentInfant2.setTag(R.id.titleInfant2,data.getStringExtra("infantTitle"));
                        //textContentInfant2.setTag(R.id.titleTempInfant2,data.getStringExtra("infantTitleTemp"));
                        textContentInfant2.setTag(R.id.nameInfant2, data.getStringExtra("infantName"));
                        textContentInfant2.setTag(R.id.bornInfant2, data.getStringExtra("infantBorn"));
                        textContentInfant2.setTag(R.id.bornShowInfant2, data.getStringExtra("infantBornShow"));

//                    MemoryStore.set(BookTrainActivity.this, "infantTitle", data.getStringExtra("infantTitle"));
//                    MemoryStore.set(BookTrainActivity.this, "infantTitleTemp", data.getStringExtra("infantTitleTemp"));
//                    MemoryStore.set(BookTrainActivity.this, "infantName", data.getStringExtra("infantName"));
//                    //     MemoryStore.set(BookTrainActivity.this, "infantId", data.getStringExtra("infantId"));
//                    MemoryStore.set(BookTrainActivity.this, "infantBornShow", data.getStringExtra("infantBornShow"));
//                    MemoryStore.set(BookTrainActivity.this, "infantBorn", data.getStringExtra("infantBorn"));
//                    if (data.getStringExtra("infantTitleTemp").equals("Tn")) {
//                        initTitle = "MSTR";
//                        //  } else if (data.getStringExtra("infantTitleTemp").equals("Ny")) {
//                        //      initTitle = "MRS";
//                    } else if (data.getStringExtra("infantTitleTemp").equals("Nona")) {
//                        initTitle = "MISS";
//                    }
                        textViewTanggalLahirInfantTrain2.setText(data.getStringExtra("infantBornShow"));
                        namePassager = data.getStringExtra("infantName");
                        fullName = data.getStringExtra("infantName").split(" ");
//                    firstName = fullName[0];
//                    if (fullName.length > 1) {
//
//                        lastName = fullName[1];
//                    } else {
//                        lastName = fullName[0];
//                    }
                        //  noId = data.getStringExtra("infantId");
                        bornDate = data.getStringExtra("infantBorn");

                        //      passengersList.add(data.getStringExtra("infantTitleTemp") + ". " + firstName + " " + lastName);
                        //   Log.d(TAG, "onActivityResult: CHD;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1");
                        // infantPassengger[1] = "CHD;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1";
                        listNameInfant.add(1, namePassager);
                        listBirthdateInfant.add(1, bornDate);
//                    HashMap<String,String> infant = new HashMap<>() ;
//
//                    infant.put("name", firstName + " " + lastName);
//                    infant.put("birthdate", bornDate);
//
//
//                    infantPassengger[1]+=infant;

                        textActionInfant2.setVisibility(View.VISIBLE);
                        imageActionInfant2.setVisibility(View.GONE);
                    } else if (requestCode == TravelActionCode.INFANT3) {// infant 3
                        imageViewExpandPassangerInfantTrain3.performClick();
//                        panelPassangerInfantTrain3Detail.setBackgroundResource(R.drawable.shape_card_primary);
                        cardPassangerInfant3.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
                        if (!data.getBooleanExtra(TrainKeyPreference.isNewTrain, false)) {
                            //if (passengersList.size() > 0) {
                            //  passengersList.remove(0);
                            // }
                            listNameInfant.remove(2);
                            listBirthdateInfant.remove(2);
                        }
//                    Log.d(TAG, "onActivityResult infant 3: " + MemoryStore.get(this, "infantBorn") + " " + data.getStringExtra("infantBorn"));
                        textContentInfant3.setText(data.getStringExtra("infantName"));
//                    textContentInfant3.setTag(R.id.titleInfant3,data.getStringExtra("infantTitle"));
//                    textContentInfant3.setTag(R.id.titleTempInfant3,data.getStringExtra("infantTitleTemp"));
                        textContentInfant3.setTag(R.id.nameInfant3, data.getStringExtra("infantName"));
                        textContentInfant3.setTag(R.id.bornInfant3, data.getStringExtra("infantBorn"));
                        textContentInfant3.setTag(R.id.bornShowInfant3, data.getStringExtra("infantBornShow"));

//                    MemoryStore.set(BookTrainActivity.this, "infantTitle", data.getStringExtra("infantTitle"));
//                    MemoryStore.set(BookTrainActivity.this, "infantTitleTemp", data.getStringExtra("infantTitleTemp"));
//                    MemoryStore.set(BookTrainActivity.this, "infantName", data.getStringExtra("infantName"));
//                    //     MemoryStore.set(BookTrainActivity.this, "infantId", data.getStringExtra("infantId"));
//                    MemoryStore.set(BookTrainActivity.this, "infantBornShow", data.getStringExtra("infantBornShow"));
//                    MemoryStore.set(BookTrainActivity.this, "infantBorn", data.getStringExtra("infantBorn"));
//                    if (data.getStringExtra("infantTitleTemp").equals("Tn")) {
//                        initTitle = "MSTR";
//                        //  } else if (data.getStringExtra("infantTitleTemp").equals("Ny")) {
//                        //      initTitle = "MRS";
//                    } else if (data.getStringExtra("infantTitleTemp").equals("Nona")) {
//                        initTitle = "MISS";
//                    }
                        textViewTanggalLahirInfantTrain3.setText(data.getStringExtra("infantBornShow"));
                        namePassager = data.getStringExtra("infantName");
//                    fullName = data.getStringExtra("infantName").split(" ");
//                    firstName = fullName[0];
//                    if (fullName.length > 1) {
//
//                        lastName = fullName[1];
//                    } else {
//                        lastName = fullName[0];
//                    }
                        //  noId = data.getStringExtra("infantId");
                        bornDate = data.getStringExtra("infantBorn");

                        // passengersList.add(data.getStringExtra("infantTitleTemp") + ". " + firstName + " " + lastName);
                        //     Log.d(TAG, "onActivityResult: CHD;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1");
                        // infantPassengger[2] = "CHD;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1";
                        listNameInfant.add(2, namePassager);
                        listBirthdateInfant.add(2, bornDate);
//                    HashMap<String,String> infant = new HashMap<>() ;
//
//                    infant.put("name", firstName + " " + lastName);
//                    infant.put("birthdate", bornDate);
//
//
//                    infantPassengger[2]+=infant;

                        textActionInfant3.setVisibility(View.VISIBLE);
                        imageActionInfant3.setVisibility(View.GONE);
                    } else if (requestCode == TravelActionCode.INFANT4) {// infant 4
                        imageViewExpandPassangerInfantTrain4.performClick();
//                        panelPassangerInfantTrain4Detail.setBackgroundResource(R.drawable.shape_card_primary);
                        cardPassangerInfant4.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
                        if (!data.getBooleanExtra(TrainKeyPreference.isNewTrain, false)) {
                            //if (passengersList.size() > 0) {
                            //  passengersList.remove(0);
                            // }
                            listNameInfant.remove(3);
                            listBirthdateInfant.remove(3);
                        }
                        //    Log.d(TAG, "onActivityResult infant 4: " + MemoryStore.get(this, "infantBorn") + " " + data.getStringExtra("infantBorn"));
                        textContentInfant4.setText(data.getStringExtra("infantName"));
//                    textContentInfant4.setTag(R.id.titleInfant4,data.getStringExtra("infantTitle"));
//                    textContentInfant4.setTag(R.id.titleTempInfant4,data.getStringExtra("infantTitleTemp"));
                        textContentInfant4.setTag(R.id.nameInfant4, data.getStringExtra("infantName"));
                        textContentInfant4.setTag(R.id.bornInfant4, data.getStringExtra("infantBorn"));
                        textContentInfant4.setTag(R.id.bornShowInfant4, data.getStringExtra("infantBornShow"));

//                    MemoryStore.set(BookTrainActivity.this, "infantTitle", data.getStringExtra("infantTitle"));
//                    MemoryStore.set(BookTrainActivity.this, "infantTitleTemp", data.getStringExtra("infantTitleTemp"));
//                    MemoryStore.set(BookTrainActivity.this, "infantName", data.getStringExtra("infantName"));
//                    //     MemoryStore.set(BookTrainActivity.this, "infantId", data.getStringExtra("infantId"));
//                    MemoryStore.set(BookTrainActivity.this, "infantBornShow", data.getStringExtra("infantBornShow"));
//                    MemoryStore.set(BookTrainActivity.this, "infantBorn", data.getStringExtra("infantBorn"));
//                    if (data.getStringExtra("infantTitleTemp").equals("Tn")) {
//                        initTitle = "MSTR";
//                        //  } else if (data.getStringExtra("infantTitleTemp").equals("Ny")) {
//                        //      initTitle = "MRS";
//                    } else if (data.getStringExtra("infantTitleTemp").equals("Nona")) {
//                        initTitle = "MISS";
//                    }
                        textViewTanggalLahirInfantTrain4.setText(data.getStringExtra("infantBornShow"));
                        namePassager = data.getStringExtra("infantName");
//                    fullName = data.getStringExtra("infantName").split(" ");
//                    firstName = fullName[0];
//                    if (fullName.length > 1) {
//
//                        lastName = fullName[1];
//                    } else {
//                        lastName = fullName[0];
//                    }
                        //  noId = data.getStringExtra("infantId");
                        bornDate = data.getStringExtra("infantBorn");

                        //   passengersList.add(data.getStringExtra("infantTitleTemp") + ". " + firstName + " " + lastName);
                        //  Log.d(TAG, "onActivityResult: CHD;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1");
                        //infantPassengger[3] = "CHD;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1";

                        listNameInfant.add(3, namePassager);
                        listBirthdateInfant.add(3, bornDate);
//                    HashMap<String,String> infant = new HashMap<>() ;
//
//                    infant.put("name", firstName + " " + lastName);
//                    infant.put("birthdate", bornDate);
//
//
//                    infantPassengger[3]+=infant;
                        textActionInfant4.setVisibility(View.VISIBLE);
                        imageActionInfant4.setVisibility(View.GONE);
                    }
                }
            }

        }
    }

    boolean isStillRunning = false;

    public void click_pesan_train() {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObjectPassengers = new JSONObject();
        // JSONArray jsonArray = (JSONArray) MemoryStore.get(BookTrainActivity.this, "seat_flights");
        JSONArray jsonArrayAdults = new JSONArray();
        JSONArray jsonArrayChilds = new JSONArray();
        JSONArray jsonArrayInfants = new JSONArray();


        Date now = new Date();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", SBFApplication.config.locale);


        String dateNow = formatter.format(now).toString();
//        for (String adult : adultPassengger) {
//            jsonArrayAdults.put(adult);
//        }
//        for (String child : childPassengger) {
//            jsonArrayChilds.put(child);
//        }
//
//        for (String infant : infantPassengger) {
//            jsonArrayInfants.put(infant);
//        }

//        for (String a : (String[])MemoryStore.get(BookTrainActivity.this,"seat_flights")) {
//            jsonArray.put(a);
//        }
        try {
            jsonObject.put("hp_kontak", textHpKontak.getText());
            jsonObject.put("email_kontak", textEmailKontak.getText());
            jsonObject.put("productCode", "TKAI");
            jsonObject.put("origin", PreferenceClass.getString(TrainKeyPreference.stationKodeAsal, ""));
            jsonObject.put("destination", PreferenceClass.getString(TrainKeyPreference.stationKodeTujuan, ""));
            jsonObject.put("date", PreferenceClass.getString(TrainKeyPreference.departureDateTrain, ""));
            // jsonObject.put("returnDate", MemoryStore.get(this, "retrunDateTrain"));
            jsonObject.put("trainNumber", PreferenceClass.getString(TrainKeyPreference.trainNumber, ""));
            jsonObject.put("grade", PreferenceClass.getString(TrainKeyPreference.grade, ""));
            jsonObject.put("class", PreferenceClass.getString(TrainKeyPreference.classes, ""));
            jsonObject.put("adult", PreferenceClass.getInt(TrainKeyPreference.countAdultTrain, 1));
            jsonObject.put("child", PreferenceClass.getInt(TrainKeyPreference.countChildTrain, 0));
            jsonObject.put("infant", PreferenceClass.getInt(TrainKeyPreference.countInfantTrain, 0));
            jsonObject.put("trainName", PreferenceClass.getString(TrainKeyPreference.trainName, ""));

            jsonObject.put("departureStation", PreferenceClass.getString(TrainKeyPreference.stationNamaAsal, ""));
            jsonObject.put("departureTime", PreferenceClass.getString(TrainKeyPreference.departureTime, ""));
//            jsonObject.put("arrivalStation", MemoryStore.get(this, "destinationStation"));
            jsonObject.put("arrivalStation", PreferenceClass.getString(TrainKeyPreference.stationNamaTujuan, ""));
            jsonObject.put("arrivalTime", PreferenceClass.getString(TrainKeyPreference.arrivalTime, ""));
            jsonObject.put("priceAdult", PreferenceClass.getInt(TrainKeyPreference.priceAdult, 0));
            jsonObject.put("priceChild", "-");
            jsonObject.put("priceInfant", "-");

//            jsonObject.put("adult", MemoryStore.get(this, "countAdultTrain"));
//            jsonObject.put("child", MemoryStore.get(this, "countChildTrain"));
//            jsonObject.put("infant", MemoryStore.get(this, "countInfantTrain"));

            // jsonObjectPassengers.put("adults", jsonArrayAdults);

            String []adultPassengger = new String[countAdult];
            String []childPassengger = new String[countChild];
            String []infantPassengger = new String[countInfant];

            JSONObject adult;
            for (int i = 0; i < adultPassengger.length; i++) {
                adult = new JSONObject();
                try {
                    adult.put("name", listNameAdult.get(i));
//                    adult.put("birthdate", listBirthdateAdult.get(i));
                    adult.put("birthdate", dateNow);
                    adult.put("phone", listPhoneAdult.get(i));
                    adult.put("idNumber", listIdAdult.get(i));
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                jsonArrayAdults.put(adult);
            }


            JSONObject child = new JSONObject();
            jsonArrayChilds.put(child);
//            if (childPassengger.length > 0) {
//                jsonObjectPassengers.put("children", jsonArrayChilds);
//            }


            JSONObject infant;
            for (int i = 0; i < infantPassengger.length; i++) {
                infant = new JSONObject();
                try {
                    infant.put("name", listNameInfant.get(i));
                    infant.put("birthdate", listBirthdateInfant.get(i));

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                jsonArrayInfants.put(infant);
            }

//            if (infantPassengger.length > 0) {
//                jsonObjectPassengers.put("infants", jsonArrayInfants);
//            }
            jsonObjectPassengers.put("adults", jsonArrayAdults);
            jsonObjectPassengers.put("children", jsonArrayChilds);
            jsonObjectPassengers.put("infants", jsonArrayInfants);

            jsonObject.put("passengers", jsonObjectPassengers);

            jsonObject.put("token", PreferenceClass.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
        isStillRunning = true;
        Log.d(TAG, "REQUEST book Train: " + jsonObject);
        RequestUtilsTravel.transportWithProgressResponseBook_Pay(this, TrainPath.BOOK, jsonObject, TravelActionCode.BOOK, this);
        final FrameLayout view = (FrameLayout)View.inflate(this,R.layout.loading_bar_full_dialog, null);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText("Mohon Tunggu, Permintaan Booking Ke KAI Sedang Diproses");
        openProgressBarDialog(TrainBookActivity.this, view);


    }

    @Override
    public void onSuccess(int actionCode, @NonNull final JSONObject response) {
        try {

            if (actionCode == TravelActionCode.FARE) {
                Log.d(TAG, "onSuccess TRAIN_FARE: " + response.toString());
                String rc = response.getString("rc").toString();
                final String rd = response.getString("rd").toString();
                //  try {
                if (rc.equals(ResponseCode.SUCCESS)) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
                    loadingPage.setVisibility(View.GONE);
                    scrollMainBook.setVisibility(View.VISIBLE);
                //    frameBottom.setVisibility(View.VISIBLE);
                    JSONObject oParent = null;
                    try {
                        oParent = response.getJSONObject("data");
                        PreferenceClass.putString(TrainKeyPreference.nominalAdmin, oParent.getString("nominalAdmin"));
//                                MemoryStore.set(this, "trainNumber", trainNumber);
//                                MemoryStore.set(this, "grade", grade);
//                                MemoryStore.set(this, "kelas", classes);
//                                MemoryStore.set(this, "trainName", trainName);
//                                MemoryStore.set(this, "departureTime", departureTime);
//                                MemoryStore.set(this, "arrivalTime", arrivalTime);
//                                MemoryStore.set(this, "priceAdult", priceAdult);
//                                textTimeDept.setText((String)MemoryStore.get(BookTrainActivity.this, "departureTime"));
//                                textTimeArr.setText((String)MemoryStore.get(BookTrainActivity.this, "arrivalTime"));
//                                textTotal.setText(utilBand.formatRupiah((int)MemoryStore.get(BookTrainActivity.this,"priceAdult")));
                        //   textDateDept.setText(deptDate);
                        //  textDateArr.setText(arrDate);
                    } catch (JSONException e) {
                        //  e.printStackTrace();
                        showToast(e.toString());
                        finish();
                    }


//                        }
//                    });
                }else  if (rc.equals("56")) {

                    ViewGroup parent = findViewById(R.id.contentHost);
                    View view = View.inflate(context, R.layout.dialog_header_response_layout, parent);
                    final CFAlertDialog.Builder  builder = new CFAlertDialog.Builder(context,R.style.CFDialog);
                    builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
                            .setBackgroundColor(ContextCompat.getColor(context, R.color.black_overlay_dark))
                            .setTitle("Informasi")
                            .setMessage(rd)
                            .setTextGravity(Gravity.CENTER)
                            // .setHeaderView(R.layout.dialog_header_layout)
                            .setHeaderView(view)
                            .setCancelable(false);
                    builder.addButton("Tutup", -1, ContextCompat.getColor(this, R.color.md_red_500), CFAlertDialog.CFAlertActionStyle.POSITIVE, getButtonGravity(3), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(@NonNull DialogInterface dialog, int which) {
                            // Toast.makeText(BaseActivity.this, "Neutral", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                            Intent i = new Intent();
                            i.putExtra("cari", true);

                            setResult(Activity.RESULT_OK, i);
                            finish();
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


                        }

                    });
//                    builder.onDismissListener(new DialogInterface.OnDismissListener() {
//                        @Override
//                        public void onDismiss(DialogInterface dialog) {
//
//                        }
//                    });
//


                    // if (context instanceof AppCompatActivity && !((AppCompatActivity) context).isFinishing()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (context instanceof AppCompatActivity && !((AppCompatActivity) context).isFinishing()) {
                                builder.show();
                            }
                        }
                    });

                } else {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                    showToastCustom(BookTrainActivity.this, 2, rd);
                    snackBarCustomAction(findViewById(R.id.rootLayout), 0, rd, 2);
                    finish();

//                        }
//                    });
                }
//            } catch (JSONException e) {
//                showToast(e.toString());
//            }

            } else if (actionCode == TravelActionCode.BOOK) {
                isStillRunning = false;
                closeProgressBarDialog();
//
//                Log.d(TAG, "onSuccess: " + response.toString());
//
                String rc = response.getString("rc").toString();
                final String rd = response.getString("rd").toString();
                if (rc.equals("00")) {

//                    PembayaranTrainActivity.mIntstance.getDataBooking(response);

//                    JSONObject data = response.getJSONObject("data");
//
//                    String transactionId = data.getString("transactionId");
//                    String bookingCode = data.getString("bookingCode");
//                    String[] passenggers = (String[]) data.get("passengers");
//                    String[] seat = (String[]) data.get("seats");
//
//
//                    String normalSales = data.getString("normalSales");
//                    String extraFee = data.getString("extraFee");
//                    String bookBalance = data.getString("bookBalance");
//                    String nominalAdmin = data.getString("nominalAdmin");
//                    int discount = data.getInt("discount");


                    //   Intent intent = new Intent(BookTrainActivity.this, PembayaranTrainActivity.class);
                    //   Bundle b = new Bundle();
////                    b.putString("passengers", passengger);
//
//                    b.putString("bookingCode", bookingCode);
//
//
//                    b.putString("normalSales", normalSales);
//                    b.putString("extraFee", extraFee);
//                    b.putString("nominalAdmin", nominalAdmin);
//                    b.putString("bookBalance", bookBalance);
//                    b.putInt("discount", discount);
//                    b.putString("transactionId", transactionId);
//
//                    b.putStringArrayList("passengerList", passengersList);
//                    b.putStringArray("adultpassengerList", adultPassengger);
//                    b.putStringArray("childpassengerList", childPassengger);
//                    b.putStringArray("infantpassengerList", infantPassengger);
//
//                    b.putStringArray("passenggers",passenggers);
                    //  b.putStringArrayList("listNameInfant",listNameInfant);
                    //intent.putExtras(b);
                    //intent.putExtra("resJsonBooking", response.toString()); //Put your id to your next Intent
                    //  startActivity(intent);
                    // finish();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        ((TrainReviewBookActivity)context).getIntanceTrainPay().getDataBooking(response);
//
//
//                    }
//                });
                    Intent intent = new Intent(TrainBookActivity.this, TrainReviewBookActivity.class);
//        Intent intent=new Intent();
//                    Bundle b = new Bundle();
//                    b.putString("passengers", response);
                    PreferenceClass.remove(TrainKeyPreference.bookResult);
                    PreferenceClass.putJSONObject(TrainKeyPreference.bookResult, response);
//                    intent.putExtra("bookTrain",true);
                    // intent.putExtras(b);
                    startActivityForResult(intent, TravelActionCode.IS_FROM_PAY);

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            snackBarCustomAction(findViewById(R.id.rootLayout), 0, rd, 2);
                        }
                    });
                }

            }
        } catch (JSONException e) {
            showToast(e.toString());
            if (actionCode == TravelActionCode.BOOK) {
                closeProgressBarDialog();
            } else {
                // closeProgressBarDialog();
                finish();
            }

        }
    }

    @Override
    public void onFailure(final int actionCode, @NonNull final String responseCode, String responseDescription, @NonNull Throwable throwable) {

        Log.d(TAG, "onFailure: " + actionCode + " " + responseDescription + " " + throwable.toString());
        if (actionCode == TravelActionCode.BOOK) {
            isStillRunning = false;
            closeProgressBarDialog();
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//            if (actionCode == TravelActionCode.BOOK) {
            if (responseCode.equals("504")) {
                snackBarCustomAction(findViewById(R.id.rootLayout), 0, "Silahkan cek di menu pesanan Anda", 3);
                //  showToastCustom(BookTrainActivity.this, 3, "Silahkan cek di menu pesanan Anda");
//                }
            } else {
                //  ((TrainReviewBookActivity)instance.getApplicationContext()).getIntanceTrainPay().getFailureBook();
                snackBarCustomAction(findViewById(R.id.rootLayout), 0, "Ada Masalah dengan server, silahkan coba kembali", 1);
            }
//                }
//            });
        } else {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//            showToastCustom(BookTrainActivity.this, 1, "Ada Masalah dengan server, silahkan coba kembali");
            snackBarCustomAction(findViewById(R.id.rootLayout), 0, "Ada Masalah dengan server, silahkan coba kembali", 1);
//                }
//            });
            //  closeProgressBarDialog();
//            finish();
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    PembayaranTrainActivity.getIntanceTrainPay().getFailruBooking(response);
//
//
//                }
//            });
        }

        Log.d(TAG, "onFailure: " + responseDescription + " " + throwable);


    }

    @Override
    public void onUpdate(int actionCode,long bytesRead, long totalSize, boolean done) {

    }

    @Override
    public void onClick(@NonNull View v) {
        int id = v.getId();
        String tag = v.getTag().toString();
        if (tag.equals("adult")) {
            openFormAdultTrain(v);

        } else if (tag.equals("infant")) {
            openFormInfantTrain(v);
        } else if (id == R.id.btn_pesan) {



            if (textContentAdult1.getTag(R.id.nameAdult1) == null) {
                snackBarCustomAction(findViewById(R.id.rootLayout), R.string.penumpang_dewasa_1_kosong, "", 1);
               // rlPassangerAdultTrain1.setBackgroundResource(R.drawable.shape_card_error);
                cardPassangerAdult1.setBackgroundResource(R.drawable.shape_card_error);
                cardPassangerAdult1.setAnimation(animShake);
                cardPassangerAdult1.startAnimation(animShake);
                getar();
                return;
            }

            if (cardPassangerAdult2.getVisibility() == View.VISIBLE) {
                if (textContentAdult2.getTag(R.id.nameAdult2) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.penumpang_dewasa_2_kosong, "", 1);
                    cardPassangerAdult2.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerAdult2.setAnimation(animShake);
                    cardPassangerAdult2.startAnimation(animShake);
                    getar();
                    return;
                }
            }

            if (cardPassangerAdult3.getVisibility() == View.VISIBLE) {
                if (textContentAdult3.getTag(R.id.nameAdult3) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.penumpang_dewasa_3_kosong, "", 1);
                    cardPassangerAdult3.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerAdult3.setAnimation(animShake);
                    cardPassangerAdult3.startAnimation(animShake);
                    getar();
                    return;
                }
            }

            if (cardPassangerAdult4.getVisibility() == View.VISIBLE) {
                if (textContentAdult4.getTag(R.id.nameAdult4) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.penumpang_dewasa_4_kosong, "", 1);
                    cardPassangerAdult4.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerAdult4.setAnimation(animShake);
                    cardPassangerAdult4.startAnimation(animShake);
                    getar();
                    return;
                }
            }

            if (cardPassangerInfant1.getVisibility() == View.VISIBLE) {
                if (textContentInfant1.getTag(R.id.nameInfant1) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.penumpang_bayi_1_kosong, "", 1);
                    cardPassangerInfant1.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerInfant1.setAnimation(animShake);
                    cardPassangerInfant1.startAnimation(animShake);
                    getar();
                    return;
                }
            }

            if (cardPassangerInfant2.getVisibility() == View.VISIBLE) {
                if (textContentInfant2.getTag(R.id.nameInfant2) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.penumpang_bayi_2_kosong, "", 2);
                    cardPassangerInfant2.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerInfant2.setAnimation(animShake);
                    cardPassangerInfant2.startAnimation(animShake);
                    getar();
                    return;
                }
            }

            if (cardPassangerInfant3.getVisibility() == View.VISIBLE) {
                if (textContentInfant3.getTag(R.id.nameInfant3) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.penumpang_bayi_3_kosong, "", 3);
                    cardPassangerInfant3.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerInfant3.setAnimation(animShake);
                    cardPassangerInfant3.startAnimation(animShake);
                    getar();
                    return;
                }
            }

            if (cardPassangerInfant4.getVisibility() == View.VISIBLE) {
                if (textContentInfant4.getTag(R.id.nameInfant4) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.penumpang_bayi_4_kosong, "", 4);
                    cardPassangerInfant4.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerInfant4.setAnimation(animShake);
                    cardPassangerInfant4.startAnimation(animShake);
                    getar();
                    return;
                }
            }

            if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                new_popup_alertDemo(this, "Info", "Anda belum bisa menikmati fiture ini.\n" +
                        "Daftar & Aktifasi sekarang juga ID Anda");

            } else {

                click_pesan_train();
            }





//            Intent intent = new Intent(TrainBookActivity.this, TrainReviewBookActivity.class);
//
//            PreferenceClass.remove(TrainKeyPreference.bookResult);
//
//            startActivityForResult(intent, TravelActionCode.IS_FROM_PAY);

        }


    }
}

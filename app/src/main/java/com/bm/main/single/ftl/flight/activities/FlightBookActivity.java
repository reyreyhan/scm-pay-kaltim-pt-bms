package com.bm.main.single.ftl.flight.activities;

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
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.ProdukGroup;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.templates.AutoResizeTextView;
import com.bm.main.fpl.templates.ExpandablePanel;
import com.bm.main.fpl.templates.MaterialRippleLayout;
import com.bm.main.fpl.templates.indicators.AVLoadingIndicatorView;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.materialedittext.MaterialEditText;
import com.bm.main.pos.R;
import com.bm.main.pos.SBFApplication;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bm.main.single.ftl.flight.constants.FlightKeyPreference;
import com.bm.main.single.ftl.flight.constants.FlightPath;
import com.bm.main.single.ftl.flight.models.FlightBaggageModel;
import com.bm.main.single.ftl.flight.models.FlightDataModelClasses;
import com.bm.main.single.ftl.utils.MemoryStore;
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
import java.util.HashMap;
import java.util.List;
//import java.util.Locale;

public class FlightBookActivity extends BaseActivity implements ProgressResponseCallback, View.OnClickListener {
    private static final String TAG = FlightBookActivity.class.getSimpleName();
    TextView textOrigin, textDestination, textTimeDept, textTimeArr, textDateDept, textDateArr;
    TextView textAirlineNo;
    AutoResizeTextView textTotal;

    TextView noUrutAdultFlight1, noUrutAdultFlight2, noUrutAdultFlight3, noUrutAdultFlight4, noUrutAdultFlight5, noUrutAdultFlight6, noUrutAdultFlight7;
    public TextView textContentAdultFlight1, textContentAdultFlight2, textContentAdultFlight3, textContentAdultFlight4, textContentAdultFlight5, textContentAdultFlight6, textContentAdultFlight7;
    TextView textActionAdultFlight1, textActionAdultFlight2, textActionAdultFlight3, textActionAdultFlight4, textActionAdultFlight5, textActionAdultFlight6, textActionAdultFlight7;
    ImageView imageActionAdultFlight1, imageActionAdultFlight2, imageActionAdultFlight3, imageActionAdultFlight4, imageActionAdultFlight5, imageActionAdultFlight6, imageActionAdultFlight7;

    TextView noUrutChildFlight1, noUrutChildFlight2, noUrutChildFlight3, noUrutChildFlight4, noUrutChildFlight5, noUrutChildFlight6,noUrutChildFlight7;
    public TextView textContentChildFlight1, textContentChildFlight2, textContentChildFlight3, textContentChildFlight4, textContentChildFlight5, textContentChildFlight6,textContentChildFlight7;
    TextView textActionChildFlight1, textActionChildFlight2, textActionChildFlight3, textActionChildFlight4, textActionChildFlight5, textActionChildFlight6,textActionChildFlight7;
    ImageView imageActionChildFlight1, imageActionChildFlight2, imageActionChildFlight3, imageActionChildFlight4, imageActionChildFlight5, imageActionChildFlight6,imageActionChildFlight7;

    TextView noUrutInfantFlight1, noUrutInfantFlight2, noUrutInfantFlight3, noUrutInfantFlight4,noUrutInfantFlight5,noUrutInfantFlight6,noUrutInfantFlight7;
    public TextView textContentInfantFlight1, textContentInfantFlight2, textContentInfantFlight3, textContentInfantFlight4,textContentInfantFlight5,textContentInfantFlight6,textContentInfantFlight7;
    TextView textActionInfantFlight1, textActionInfantFlight2, textActionInfantFlight3, textActionInfantFlight4,textActionInfantFlight5,textActionInfantFlight6,textActionInfantFlight7;
    ImageView imageActionInfantFlight1, imageActionInfantFlight2, imageActionInfantFlight3, imageActionInfantFlight4,imageActionInfantFlight5,imageActionInfantFlight6,imageActionInfantFlight7;

    RelativeLayout cardPassangerAdultFlight1, cardPassangerAdultFlight2, cardPassangerAdultFlight3, cardPassangerAdultFlight4, cardPassangerAdultFlight5, cardPassangerAdultFlight6, cardPassangerAdultFlight7;
    RelativeLayout cardPassangerChildFlight1, cardPassangerChildFlight2, cardPassangerChildFlight3, cardPassangerChildFlight4, cardPassangerChildFlight5, cardPassangerChildFlight6,cardPassangerChildFlight7;
    RelativeLayout cardPassangerInfantFlight1, cardPassangerInfantFlight2, cardPassangerInfantFlight3, cardPassangerInfantFlight4,cardPassangerInfantFlight5,cardPassangerInfantFlight6,cardPassangerInfantFlight7;
    AppCompatButton btnPesan;
    int price = 0;
    @Nullable
    String timeArr, timeDep, depDate, arrDate;
    int countAdult = 1, countChild = 0, countInfant = 0;
    String[] adultPassanger;
    String[] childPassanger;
    String[] infantPassanger;

    String[] adultBaggage;
    String[] childBaggage;
    MaterialEditText textHpKontak, textEmailKontak;
    //public static FlightBookActivity instance;
    String sJson;
    ScrollView scrollMainBook;
    RelativeLayout loadingPage;
//    RelativeLayout frameBottomFlight;
    String bagageFromResponse = "20";
    boolean isFare = false;
    public AVLoadingIndicatorView avi;
    LinearLayout rlPassangerAdultFlight1, rlPassangerAdultFlight2, rlPassangerAdultFlight3, rlPassangerAdultFlight4, rlPassangerAdultFlight5, rlPassangerAdultFlight6, rlPassangerAdultFlight7;
    LinearLayout rlPassangerChildFlight1, rlPassangerChildFlight2, rlPassangerChildFlight3, rlPassangerChildFlight4, rlPassangerChildFlight5, rlPassangerChildFlight6,rlPassangerChildFlight7;
    LinearLayout rlPassangerInfantFlight1, rlPassangerInfantFlight2, rlPassangerInfantFlight3, rlPassangerInfantFlight4,rlPassangerInfantFlight5,rlPassangerInfantFlight6,rlPassangerInfantFlight7;
    TextView textDetailTransit;

    ImageView imageViewExpandPassangerAdultFlight1, imageViewExpandPassangerAdultFlight2, imageViewExpandPassangerAdultFlight3, imageViewExpandPassangerAdultFlight4, imageViewExpandPassangerAdultFlight5, imageViewExpandPassangerAdultFlight6, imageViewExpandPassangerAdultFlight7;

    ImageView imageViewExpandPassangerChildFlight1, imageViewExpandPassangerChildFlight2, imageViewExpandPassangerChildFlight3, imageViewExpandPassangerChildFlight4, imageViewExpandPassangerChildFlight5, imageViewExpandPassangerChildFlight6,imageViewExpandPassangerChildFlight7;

    ImageView imageViewExpandPassangerInfantFlight1, imageViewExpandPassangerInfantFlight2, imageViewExpandPassangerInfantFlight3, imageViewExpandPassangerInfantFlight4,imageViewExpandPassangerInfantFlight5,imageViewExpandPassangerInfantFlight6,imageViewExpandPassangerInfantFlight7;


    ExpandablePanel panelPassangerAdultFlight1Detail, panelPassangerAdultFlight2Detail, panelPassangerAdultFlight3Detail, panelPassangerAdultFlight4Detail, panelPassangerAdultFlight5Detail, panelPassangerAdultFlight6Detail, panelPassangerAdultFlight7Detail;


    ExpandablePanel panelPassangerChildFlight1Detail, panelPassangerChildFlight2Detail, panelPassangerChildFlight3Detail, panelPassangerChildFlight4Detail, panelPassangerChildFlight5Detail, panelPassangerChildFlight6Detail,panelPassangerChildFlight7Detail;

    ExpandablePanel panelPassangerInfantFlight1Detail, panelPassangerInfantFlight2Detail, panelPassangerInfantFlight3Detail, panelPassangerInfantFlight4Detail,panelPassangerInfantFlight5Detail,panelPassangerInfantFlight6Detail,panelPassangerInfantFlight7Detail;

    TextView textViewNoIdentitasAdult1, textViewNoIdentitasAdult2, textViewNoIdentitasAdult3, textViewNoIdentitasAdult4, textViewNoIdentitasAdult5, textViewNoIdentitasAdult6, textViewNoIdentitasAdult7;

    TextView textViewTanggalLahirAdult1, textViewTanggalLahirAdult2, textViewTanggalLahirAdult3, textViewTanggalLahirAdult4, textViewTanggalLahirAdult5, textViewTanggalLahirAdult6, textViewTanggalLahirAdult7;
    TextView textViewMobileAdult1;
    //    , textViewMobileAdult2, textViewMobileAdult3, textViewMobileAdult4, textViewMobileAdult5, textViewMobileAdult6, textViewMobileAdult7;
    TextView textViewEmailAdult1;
    // , textViewEmailAdult2, textViewEmailAdult3, textViewEmailAdult4, textViewEmailAdult5, textViewEmailAdult6, textViewEmailAdult7;
    TextView textViewTanggalLahirChild1, textViewTanggalLahirChild2, textViewTanggalLahirChild3, textViewTanggalLahirChild4, textViewTanggalLahirChild5, textViewTanggalLahirChild6,textViewTanggalLahirChild7;
    TextView textViewTanggalLahirInfant1, textViewTanggalLahirInfant2, textViewTanggalLahirInfant3, textViewTanggalLahirInfant4,textViewTanggalLahirInfant5,textViewTanggalLahirInfant6,textViewTanggalLahirInfant7;
    LinearLayout linTanggalLahirAdult1, linTanggalLahirAdult2, linTanggalLahirAdult3, linTanggalLahirAdult4, linTanggalLahirAdult5, linTanggalLahirAdult6, linTanggalLahirAdult7;
    LinearLayout linNoIdentitasAdult1, linNoIdentitasAdult2, linNoIdentitasAdult3, linNoIdentitasAdult4, linNoIdentitasAdult5, linNoIdentitasAdult6, linNoIdentitasAdult7;

    LinearLayout linEmailAdult1, linMobileAdult1;

    int countBaggage = 0;
    @Nullable
    FlightDataModelClasses data;
    @NonNull
    List<FlightBaggageModel.Data> itemList = new ArrayList<>();

    Context context;


    LinearLayout linContentAdultFlight1,linContentAdultFlight2,linContentAdultFlight3,linContentAdultFlight4,linContentAdultFlight5,linContentAdultFlight6,linContentAdultFlight7;
    LinearLayout linContentChildFlight1,linContentChildFlight2,linContentChildFlight3,linContentChildFlight4,linContentChildFlight5,linContentChildFlight6,linContentChildFlight7;
    LinearLayout linContentInfantFlight1,linContentInfantFlight2,linContentInfantFlight3,linContentInfantFlight4,linContentInfantFlight5,linContentInfantFlight6,linContentInfantFlight7;
   
   LinearLayout lin_contentPassportChild1,lin_contentPassportChild2,lin_contentPassportChild3,lin_contentPassportChild4,lin_contentPassportChild5,lin_contentPassportChild6,lin_contentPassportChild7;
   LinearLayout lin_contentPassportInfant1,lin_contentPassportInfant2,lin_contentPassportInfant3,lin_contentPassportInfant4,lin_contentPassportInfant5,lin_contentPassportInfant6,lin_contentPassportInfant7;
     TextView textViewNoPassportInfantFlight1,textViewNoPassportInfantFlight2,textViewNoPassportInfantFlight3,textViewNoPassportInfantFlight4,textViewNoPassportInfantFlight5,textViewNoPassportInfantFlight6,textViewNoPassportInfantFlight7;
     TextView textViewNoPassportChildFlight1,textViewNoPassportChildFlight2,textViewNoPassportChildFlight3,textViewNoPassportChildFlight4,textViewNoPassportChildFlight5,textViewNoPassportChildFlight6,textViewNoPassportChildFlight7;
     TextView labelViewNoIdentitasAdultFlight1,labelViewNoIdentitasAdultFlight2,labelViewNoIdentitasAdultFlight3,labelViewNoIdentitasAdultFlight4,labelViewNoIdentitasAdultFlight5,labelViewNoIdentitasAdultFlight6,labelViewNoIdentitasAdultFlight7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_book_activity);
        context=this;
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Lengkapi Data");
        init(0);
        scrollMainBook = findViewById(R.id.scrollMainBook);
//        frameBottomFlight = findViewById(R.id.frameBottomFlight);
        loadingPage = findViewById(R.id.loading_view_flight);
        final Intent intent = this.getIntent();
        if (intent != null) {
            loadingPage.setVisibility(View.VISIBLE);
            scrollMainBook.setVisibility(View.GONE);
       //     frameBottomFlight.setVisibility(View.GONE);
            sJson = intent.getStringExtra("reqJsonFare");
            isFare = intent.getBooleanExtra("isFare", true);
        }
        data = (FlightDataModelClasses) MemoryStore.get("dataOnClick");
        itemList.clear();


       // Log.d(TAG, "onCreate: get all country "+ Locale.getISOCountries());

//        PreferenceClass.remove(FlightKeyPreference.baggage);
//        PreferenceClass.remove(FlightKeyPreference.baggage1);
//        PreferenceClass.remove(FlightKeyPreference.baggage2);
//        PreferenceClass.remove(FlightKeyPreference.baggage3);

        textDetailTransit = findViewById(R.id.textDetailTransit);
        textDetailTransit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(FlightBookActivity.this, FlightDetailActivity.class);
                startActivity(intent1);
            }
        });
        avi = findViewById(R.id.avi_flight_book);
        textAirlineNo = findViewById(R.id.textCodeFlight);
        textTotal = findViewById(R.id.textPrice);
        price = PreferenceClass.getInt(FlightKeyPreference.OneWayPriceSchedule, 0);
        avi.setVisibility(View.VISIBLE);
        textTotal.setText("sedang memperbaharui harga");
        textOrigin = findViewById(R.id.textOrigin);
        textDestination = findViewById(R.id.textDestination);
        textTimeDept = findViewById(R.id.jamBerangkatFlight);
        textTimeArr = findViewById(R.id.jamTibaFlight);
        textDateDept = findViewById(R.id.textDepartureDate);
        textDateArr = findViewById(R.id.textArrivalDate);

        countAdult = PreferenceClass.getInt(FlightKeyPreference.countAdultFlight, 1);
        countChild = PreferenceClass.getInt(FlightKeyPreference.countChildFlight, 0);
        countInfant = PreferenceClass.getInt(FlightKeyPreference.countInfantFlight, 0);

        if (1 <= countAdult) {
            rlPassangerAdultFlight1 = findViewById(R.id.rlPassangerAdultFlight1);
            cardPassangerAdultFlight1 = findViewById(R.id.cardPassangerAdultFlight1);
//            cardPassangerAdultFlight1.setOnClickListener(this);
            noUrutAdultFlight1 = findViewById(R.id.textUrutAdultFlight1);
            textContentAdultFlight1 = findViewById(R.id.textContentAdultFlight1);
            textActionAdultFlight1 = findViewById(R.id.textActionAdultFlight1);
            imageActionAdultFlight1 = findViewById(R.id.imageActionAdultFlight1);
            linContentAdultFlight1 = findViewById(R.id.linContentAdultFlight1);
            linContentAdultFlight1.setOnClickListener(this);
//            textActionAdultFlight1.setOnClickListener(this);
//            imageActionAdultFlight1.setOnClickListener(this);
            imageViewExpandPassangerAdultFlight1 = findViewById(R.id.imageViewExpandPassangerAdultFlight1);
            imageViewExpandPassangerAdultFlight1.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_adult));
//        imageViewExpandPassangerAdultFlight1.setVisibility(View.GONE);
            panelPassangerAdultFlight1Detail = findViewById(R.id.panelPassangerAdultFlight1Detail);

            panelPassangerAdultFlight1Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
                public void onCollapse(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
////                btn.setText("More");
//imageView.performClick();
                   // imageViewExpandPassangerAdultFlight1.setBackground(null);
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_expand));
                }

                public void onExpand(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                   // imageView.performClick();
                  //  imageViewExpandPassangerAdultFlight1.setBackground(null);
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_collapse));
                }
            });

        }
        if (2 <= countAdult) {
            rlPassangerAdultFlight2 = findViewById(R.id.rlPassangerAdultFlight2);
            cardPassangerAdultFlight2 = findViewById(R.id.cardPassangerAdultFlight2);
//            cardPassangerAdultFlight2.setOnClickListener(this);
            noUrutAdultFlight2 = findViewById(R.id.textUrutAdultFlight2);
            textContentAdultFlight2 = findViewById(R.id.textContentAdultFlight2);
            textActionAdultFlight2 = findViewById(R.id.textActionAdultFlight2);
            imageActionAdultFlight2 = findViewById(R.id.imageActionAdultFlight2);
            linContentAdultFlight2 = findViewById(R.id.linContentAdultFlight2);
            linContentAdultFlight2.setOnClickListener(this);
           // linContentAdultFlight1.setOnClickListener(this);
//            textActionAdultFlight2.setOnClickListener(this);
//            imageActionAdultFlight2.setOnClickListener(this);
            imageViewExpandPassangerAdultFlight2 = findViewById(R.id.imageViewExpandPassangerAdultFlight2);
            imageViewExpandPassangerAdultFlight2.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_adult));
            panelPassangerAdultFlight2Detail = findViewById(R.id.panelPassangerAdultFlight2Detail);

            panelPassangerAdultFlight2Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
                public void onCollapse(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_expand));
                }

                public void onExpand(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_collapse));
                }
            });
        }
        if (3 <= countAdult) {
            rlPassangerAdultFlight3 = findViewById(R.id.rlPassangerAdultFlight3);
            cardPassangerAdultFlight3 = findViewById(R.id.cardPassangerAdultFlight3);
//            cardPassangerAdultFlight3.setOnClickListener(this);
            noUrutAdultFlight3 = findViewById(R.id.textUrutAdultFlight3);
            textContentAdultFlight3 = findViewById(R.id.textContentAdultFlight3);
            textActionAdultFlight3 = findViewById(R.id.textActionAdultFlight3);
            imageActionAdultFlight3 = findViewById(R.id.imageActionAdultFlight3);
//            textActionAdultFlight3.setOnClickListener(this);
//            imageActionAdultFlight3.setOnClickListener(this);
            linContentAdultFlight3 = findViewById(R.id.linContentAdultFlight3);
            linContentAdultFlight3 .setOnClickListener(this);
            imageViewExpandPassangerAdultFlight3 = findViewById(R.id.imageViewExpandPassangerAdultFlight3);
            imageViewExpandPassangerAdultFlight3.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_adult));
            panelPassangerAdultFlight3Detail = findViewById(R.id.panelPassangerAdultFlight3Detail);

            panelPassangerAdultFlight3Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
                public void onCollapse(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_expand));
                }

                public void onExpand(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_collapse));
                }
            });
        }
        if (4 <= countAdult) {
            rlPassangerAdultFlight4 = findViewById(R.id.rlPassangerAdultFlight4);
            cardPassangerAdultFlight4 = findViewById(R.id.cardPassangerAdultFlight4);
//            cardPassangerAdultFlight4.setOnClickListener(this);
            noUrutAdultFlight4 = findViewById(R.id.textUrutAdultFlight4);
            textContentAdultFlight4 = findViewById(R.id.textContentAdultFlight4);
            textActionAdultFlight4 = findViewById(R.id.textActionAdultFlight4);
            imageActionAdultFlight4 = findViewById(R.id.imageActionAdultFlight4);
//            textActionAdultFlight4.setOnClickListener(this);
//            imageActionAdultFlight4.setOnClickListener(this);
            linContentAdultFlight4 = findViewById(R.id.linContentAdultFlight4);
            linContentAdultFlight4 .setOnClickListener(this);

            imageViewExpandPassangerAdultFlight4 = findViewById(R.id.imageViewExpandPassangerAdultFlight4);
            imageViewExpandPassangerAdultFlight4.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_adult));
            panelPassangerAdultFlight4Detail = findViewById(R.id.panelPassangerAdultFlight4Detail);

            panelPassangerAdultFlight4Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
                public void onCollapse(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_expand));
                }

                public void onExpand(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_collapse));
                }
            });

        }
        if (5 <= countAdult) {
            rlPassangerAdultFlight5 = findViewById(R.id.rlPassangerAdultFlight5);
            cardPassangerAdultFlight5 = findViewById(R.id.cardPassangerAdultFlight5);
//            cardPassangerAdultFlight5.setOnClickListener(this);
            noUrutAdultFlight5 = findViewById(R.id.textUrutAdultFlight5);
            textContentAdultFlight5 = findViewById(R.id.textContentAdultFlight5);
            textActionAdultFlight5 = findViewById(R.id.textActionAdultFlight5);
            imageActionAdultFlight5 = findViewById(R.id.imageActionAdultFlight5);
//            textActionAdultFlight5.setOnClickListener(this);
//            imageActionAdultFlight5.setOnClickListener(this);
            linContentAdultFlight5 = findViewById(R.id.linContentAdultFlight5);
            linContentAdultFlight5 .setOnClickListener(this);
            imageViewExpandPassangerAdultFlight5 = findViewById(R.id.imageViewExpandPassangerAdultFlight5);
            imageViewExpandPassangerAdultFlight5.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_adult));
            panelPassangerAdultFlight5Detail = findViewById(R.id.panelPassangerAdultFlight5Detail);

            panelPassangerAdultFlight5Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
                public void onCollapse(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_expand));
                }

                public void onExpand(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_collapse));
                }
            });
        }
        if (6 <= countAdult) {
            rlPassangerAdultFlight6 = findViewById(R.id.rlPassangerAdultFlight6);
            cardPassangerAdultFlight6 = findViewById(R.id.cardPassangerAdultFlight6);
//            cardPassangerAdultFlight6.setOnClickListener(this);
            noUrutAdultFlight6 = findViewById(R.id.textUrutAdultFlight6);
            textContentAdultFlight6 = findViewById(R.id.textContentAdultFlight6);
            textActionAdultFlight6 = findViewById(R.id.textActionAdultFlight6);
            imageActionAdultFlight6 = findViewById(R.id.imageActionAdultFlight6);
//            textActionAdultFlight6.setOnClickListener(this);
//            imageActionAdultFlight6.setOnClickListener(this);
            linContentAdultFlight6 = findViewById(R.id.linContentAdultFlight6);
            linContentAdultFlight6 .setOnClickListener(this);
            imageViewExpandPassangerAdultFlight6 = findViewById(R.id.imageViewExpandPassangerAdultFlight6);
            imageViewExpandPassangerAdultFlight6.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_adult));
            panelPassangerAdultFlight6Detail = findViewById(R.id.panelPassangerAdultFlight6Detail);

            panelPassangerAdultFlight6Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
                public void onCollapse(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_expand));
                }

                public void onExpand(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_collapse));
                }
            });
        }
        if (7 <= countAdult) {
            rlPassangerAdultFlight7 = findViewById(R.id.rlPassangerAdultFlight7);
            cardPassangerAdultFlight7 = findViewById(R.id.cardPassangerAdultFlight7);
//            cardPassangerAdultFlight7.setOnClickListener(this);
            noUrutAdultFlight7 = findViewById(R.id.textUrutAdultFlight7);
            textContentAdultFlight7 = findViewById(R.id.textContentAdultFlight7);
            textActionAdultFlight7 = findViewById(R.id.textActionAdultFlight7);
            imageActionAdultFlight7 = findViewById(R.id.imageActionAdultFlight7);
//            textActionAdultFlight7.setOnClickListener(this);
//            imageActionAdultFlight7.setOnClickListener(this);
            linContentAdultFlight7 = findViewById(R.id.linContentAdultFlight7);
            linContentAdultFlight7.setOnClickListener(this);
            imageViewExpandPassangerAdultFlight7 = findViewById(R.id.imageViewExpandPassangerAdultFlight7);
            imageViewExpandPassangerAdultFlight7.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_adult));
            panelPassangerAdultFlight7Detail = findViewById(R.id.panelPassangerAdultFlight7Detail);

            panelPassangerAdultFlight7Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
                public void onCollapse(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_expand));
                }

                public void onExpand(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_collapse));
                }
            });
        }

        if (1 <= countChild) {
            rlPassangerChildFlight1 = findViewById(R.id.rlPassangerChildFlight1);
            cardPassangerChildFlight1 = findViewById(R.id.cardPassangerChildFlight1);
//            cardPassangerChildFlight1.setOnClickListener(this);
            noUrutChildFlight1 = findViewById(R.id.textUrutChildFlight1);
            textContentChildFlight1 = findViewById(R.id.textContentChildFlight1);
            textActionChildFlight1 = findViewById(R.id.textActionChildFlight1);
            imageActionChildFlight1 = findViewById(R.id.imageActionChildFlight1);
//            textActionChildFlight1.setOnClickListener(this);
//            imageActionChildFlight1.setOnClickListener(this);
            linContentChildFlight1 = findViewById(R.id.linContentChildFlight1);
            linContentChildFlight1.setOnClickListener(this);
            imageViewExpandPassangerChildFlight1 = findViewById(R.id.imageViewExpandPassangerChildFlight1);
            imageViewExpandPassangerChildFlight1.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_child));
            panelPassangerChildFlight1Detail = findViewById(R.id.panelPassangerChildFlight1Detail);

            panelPassangerChildFlight1Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
                public void onCollapse(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_expand));
                }

                public void onExpand(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_collapse));
                }
            });

        }
        if (2 <= countChild) {
            rlPassangerChildFlight2 = findViewById(R.id.rlPassangerChildFlight2);
            cardPassangerChildFlight2 = findViewById(R.id.cardPassangerChildFlight2);
//            cardPassangerChildFlight2.setOnClickListener(this);
            noUrutChildFlight2 = findViewById(R.id.textUrutChildFlight2);
            textContentChildFlight2 = findViewById(R.id.textContentChildFlight2);
            textActionChildFlight2 = findViewById(R.id.textActionChildFlight2);
            imageActionChildFlight2 = findViewById(R.id.imageActionChildFlight2);
//            textActionChildFlight2.setOnClickListener(this);
//            imageActionChildFlight2.setOnClickListener(this);
            linContentChildFlight2 = findViewById(R.id.linContentChildFlight2);
            linContentChildFlight2 .setOnClickListener(this);
            imageViewExpandPassangerChildFlight2 = findViewById(R.id.imageViewExpandPassangerChildFlight2);
            imageViewExpandPassangerChildFlight2.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_child));
            panelPassangerChildFlight2Detail = findViewById(R.id.panelPassangerChildFlight2Detail);

            panelPassangerChildFlight2Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
                public void onCollapse(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_expand));
                }

                public void onExpand(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_collapse));
                }
            });
        }
        if (3 <= countChild) {
            rlPassangerChildFlight3 = findViewById(R.id.rlPassangerChildFlight3);
            cardPassangerChildFlight3 = findViewById(R.id.cardPassangerChildFlight3);
//            cardPassangerChildFlight3.setOnClickListener(this);
            noUrutChildFlight3 = findViewById(R.id.textUrutChildFlight3);
            textContentChildFlight3 = findViewById(R.id.textContentChildFlight3);
            textActionChildFlight3 = findViewById(R.id.textActionChildFlight3);
            imageActionChildFlight3 = findViewById(R.id.imageActionChildFlight3);
//            textActionChildFlight3.setOnClickListener(this);
//            imageActionChildFlight3.setOnClickListener(this);
            linContentChildFlight3 = findViewById(R.id.linContentChildFlight3);
            linContentChildFlight3 .setOnClickListener(this);
            imageViewExpandPassangerChildFlight3 = findViewById(R.id.imageViewExpandPassangerChildFlight3);
            imageViewExpandPassangerChildFlight3.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_child));
            panelPassangerChildFlight3Detail = findViewById(R.id.panelPassangerChildFlight3Detail);

            panelPassangerChildFlight3Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
                public void onCollapse(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_expand));
                }

                public void onExpand(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_collapse));
                }
            });
        }
        if (4 <= countChild) {
            rlPassangerChildFlight4 = findViewById(R.id.rlPassangerChildFlight4);
            cardPassangerChildFlight4 = findViewById(R.id.cardPassangerChildFlight4);
//            cardPassangerChildFlight4.setOnClickListener(this);
            noUrutChildFlight4 = findViewById(R.id.textUrutChildFlight4);
            textContentChildFlight4 = findViewById(R.id.textContentChildFlight4);
            textActionChildFlight4 = findViewById(R.id.textActionChildFlight4);
            imageActionChildFlight4 = findViewById(R.id.imageActionChildFlight4);
//            textActionChildFlight4.setOnClickListener(this);
//            imageActionChildFlight4.setOnClickListener(this);
            linContentChildFlight4 = findViewById(R.id.linContentChildFlight4);
            linContentChildFlight4 .setOnClickListener(this);
            imageViewExpandPassangerChildFlight4 = findViewById(R.id.imageViewExpandPassangerChildFlight4);
            imageViewExpandPassangerChildFlight4.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_child));
            panelPassangerChildFlight4Detail = findViewById(R.id.panelPassangerChildFlight4Detail);

            panelPassangerChildFlight4Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
                public void onCollapse(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_expand));
                }

                public void onExpand(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_collapse));
                }
            });

        }
        if (5 <= countChild) {
            rlPassangerChildFlight5 = findViewById(R.id.rlPassangerChildFlight5);
            cardPassangerChildFlight5 = findViewById(R.id.cardPassangerChildFlight5);
//            cardPassangerChildFlight5.setOnClickListener(this);
            noUrutChildFlight5 = findViewById(R.id.textUrutChildFlight5);
            textContentChildFlight5 = findViewById(R.id.textContentChildFlight5);
            textActionChildFlight5 = findViewById(R.id.textActionChildFlight5);
            imageActionChildFlight5 = findViewById(R.id.imageActionChildFlight5);
//            textActionChildFlight5.setOnClickListener(this);
//            imageActionChildFlight5.setOnClickListener(this);
            linContentChildFlight5 = findViewById(R.id.linContentChildFlight5);
            linContentChildFlight5.setOnClickListener(this);
            imageViewExpandPassangerChildFlight5 = findViewById(R.id.imageViewExpandPassangerChildFlight5);
            imageViewExpandPassangerChildFlight5.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_child));
            panelPassangerChildFlight5Detail = findViewById(R.id.panelPassangerChildFlight5Detail);

            panelPassangerChildFlight5Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
                public void onCollapse(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_expand));
                }

                public void onExpand(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_collapse));
                }
            });
        }
        if (6 <= countChild) {
            rlPassangerChildFlight6 = findViewById(R.id.rlPassangerChildFlight6);
            cardPassangerChildFlight6 = findViewById(R.id.cardPassangerChildFlight6);
//            cardPassangerChildFlight6.setOnClickListener(this);
            noUrutChildFlight6 = findViewById(R.id.textUrutChildFlight6);
            textContentChildFlight6 = findViewById(R.id.textContentChildFlight6);
            textActionChildFlight6 = findViewById(R.id.textActionChildFlight6);
            imageActionChildFlight6 = findViewById(R.id.imageActionChildFlight6);
//            textActionChildFlight6.setOnClickListener(this);
//            imageActionChildFlight6.setOnClickListener(this);
            linContentChildFlight6 = findViewById(R.id.linContentChildFlight6);
            linContentChildFlight6 .setOnClickListener(this);
            imageViewExpandPassangerChildFlight6 = findViewById(R.id.imageViewExpandPassangerChildFlight6);
            imageViewExpandPassangerChildFlight6.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_child));
            panelPassangerChildFlight6Detail = findViewById(R.id.panelPassangerChildFlight6Detail);

            panelPassangerChildFlight6Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
                public void onCollapse(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_expand));
                }

                public void onExpand(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_collapse));
                }
            });

        }
        if (7 <= countChild) {
            rlPassangerChildFlight7 = findViewById(R.id.rlPassangerChildFlight7);
            cardPassangerChildFlight7 = findViewById(R.id.cardPassangerChildFlight7);
//            cardPassangerChildFlight7.setOnClickListener(this);
            noUrutChildFlight7 = findViewById(R.id.textUrutChildFlight7);
            textContentChildFlight7 = findViewById(R.id.textContentChildFlight7);
            textActionChildFlight7 = findViewById(R.id.textActionChildFlight7);
            imageActionChildFlight7 = findViewById(R.id.imageActionChildFlight7);
//            textActionChildFlight7.setOnClickListener(this);
//            imageActionChildFlight7.setOnClickListener(this);
            linContentChildFlight7 = findViewById(R.id.linContentChildFlight7);
            linContentChildFlight7 .setOnClickListener(this);
            imageViewExpandPassangerChildFlight7 = findViewById(R.id.imageViewExpandPassangerChildFlight7);
            imageViewExpandPassangerChildFlight7.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_child));
            panelPassangerChildFlight7Detail = findViewById(R.id.panelPassangerChildFlight7Detail);

            panelPassangerChildFlight7Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
                public void onCollapse(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_expand));
                }

                public void onExpand(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_collapse));
                }
            });

        }

        if (1 <= countInfant) {
            rlPassangerInfantFlight1 = findViewById(R.id.rlPassangerInfantFlight1);
            cardPassangerInfantFlight1 = findViewById(R.id.cardPassangerInfantFlight1);
//            cardPassangerInfantFlight1.setOnClickListener(this);
            noUrutInfantFlight1 = findViewById(R.id.textUrutInfantFlight1);
            textContentInfantFlight1 = findViewById(R.id.textContentInfantFlight1);
            textActionInfantFlight1 = findViewById(R.id.textActionInfantFlight1);
            imageActionInfantFlight1 = findViewById(R.id.imageActionInfantFlight1);
//            textActionInfantFlight1.setOnClickListener(this);
//            imageActionInfantFlight1.setOnClickListener(this);
            linContentInfantFlight1 = findViewById(R.id.linContentInfantFlight1);
            linContentInfantFlight1 .setOnClickListener(this);
            imageViewExpandPassangerInfantFlight1 = findViewById(R.id.imageViewExpandPassangerInfantFlight1);
            imageViewExpandPassangerInfantFlight1.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_baby));
            panelPassangerInfantFlight1Detail = findViewById(R.id.panelPassangerInfantFlight1Detail);

            panelPassangerInfantFlight1Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
                public void onCollapse(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_expand));
                }

                public void onExpand(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_collapse));
                }
            });
        }
        if (2 <= countInfant) {
            rlPassangerInfantFlight2 = findViewById(R.id.rlPassangerInfantFlight2);
            cardPassangerInfantFlight2 = findViewById(R.id.cardPassangerInfantFlight2);
//            cardPassangerInfantFlight2.setOnClickListener(this);
            noUrutInfantFlight2 = findViewById(R.id.textUrutInfantFlight2);
            textContentInfantFlight2 = findViewById(R.id.textContentInfantFlight2);
            textActionInfantFlight2 = findViewById(R.id.textActionInfantFlight2);
            imageActionInfantFlight2 = findViewById(R.id.imageActionInfantFlight2);
//            textActionInfantFlight2.setOnClickListener(this);
//            imageActionInfantFlight2.setOnClickListener(this);
            linContentInfantFlight2 = findViewById(R.id.linContentInfantFlight2);
            linContentInfantFlight2.setOnClickListener(this);
            imageViewExpandPassangerInfantFlight2 = findViewById(R.id.imageViewExpandPassangerInfantFlight2);
            imageViewExpandPassangerInfantFlight2.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_baby));
            panelPassangerInfantFlight2Detail = findViewById(R.id.panelPassangerInfantFlight2Detail);

            panelPassangerInfantFlight2Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
                public void onCollapse(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_expand));
                }

                public void onExpand(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_collapse));
                }
            });
        }
        if (3 <= countInfant) {
            rlPassangerInfantFlight3 = findViewById(R.id.rlPassangerInfantFlight3);
            cardPassangerInfantFlight3 = findViewById(R.id.cardPassangerInfantFlight3);
//            cardPassangerInfantFlight3.setOnClickListener(this);
            noUrutInfantFlight3 = findViewById(R.id.textUrutInfantFlight3);
            textContentInfantFlight3 = findViewById(R.id.textContentInfantFlight3);
            textActionInfantFlight3 = findViewById(R.id.textActionInfantFlight3);
            imageActionInfantFlight3 = findViewById(R.id.imageActionInfantFlight3);
//            textActionInfantFlight3.setOnClickListener(this);
//            imageActionInfantFlight3.setOnClickListener(this);
            linContentInfantFlight3 = findViewById(R.id.linContentInfantFlight3);
            linContentInfantFlight3.setOnClickListener(this);
            imageViewExpandPassangerInfantFlight3 = findViewById(R.id.imageViewExpandPassangerInfantFlight3);
            imageViewExpandPassangerInfantFlight3.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_baby));
            panelPassangerInfantFlight3Detail = findViewById(R.id.panelPassangerInfantFlight3Detail);

            panelPassangerInfantFlight3Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
                public void onCollapse(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_expand));
                }

                public void onExpand(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_collapse));
                }
            });
        }
        if (4 <= countInfant) {
            rlPassangerInfantFlight4 = findViewById(R.id.rlPassangerInfantFlight4);
            cardPassangerInfantFlight4 = findViewById(R.id.cardPassangerInfantFlight4);
//            cardPassangerInfantFlight4.setOnClickListener(this);
            noUrutInfantFlight4 = findViewById(R.id.textUrutInfantFlight4);
            textContentInfantFlight4 = findViewById(R.id.textContentInfantFlight4);
            textActionInfantFlight4 = findViewById(R.id.textActionInfantFlight4);
            imageActionInfantFlight4 = findViewById(R.id.imageActionInfantFlight4);
//            textActionInfantFlight4.setOnClickListener(this);
//            imageActionInfantFlight4.setOnClickListener(this);
            linContentInfantFlight4 = findViewById(R.id.linContentInfantFlight4);
            linContentInfantFlight4.setOnClickListener(this);
            imageViewExpandPassangerInfantFlight4 = findViewById(R.id.imageViewExpandPassangerInfantFlight4);
            imageViewExpandPassangerInfantFlight4.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_baby));
            panelPassangerInfantFlight4Detail = findViewById(R.id.panelPassangerInfantFlight4Detail);

            panelPassangerInfantFlight4Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
                public void onCollapse(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_expand));
                }

                public void onExpand(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_collapse));
                }
            });
        }

        if (5 <= countInfant) {
            rlPassangerInfantFlight5 = findViewById(R.id.rlPassangerInfantFlight5);
            cardPassangerInfantFlight5 = findViewById(R.id.cardPassangerInfantFlight5);
//            cardPassangerInfantFlight5.setOnClickListener(this);
            noUrutInfantFlight5 = findViewById(R.id.textUrutInfantFlight5);
            textContentInfantFlight5 = findViewById(R.id.textContentInfantFlight5);
            textActionInfantFlight5 = findViewById(R.id.textActionInfantFlight5);
            imageActionInfantFlight5 = findViewById(R.id.imageActionInfantFlight5);
//            textActionInfantFlight5.setOnClickListener(this);
//            imageActionInfantFlight5.setOnClickListener(this);
            linContentInfantFlight5 = findViewById(R.id.linContentInfantFlight5);
            linContentInfantFlight5.setOnClickListener(this);
            imageViewExpandPassangerInfantFlight5 = findViewById(R.id.imageViewExpandPassangerInfantFlight5);
            imageViewExpandPassangerInfantFlight5.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_baby));
            panelPassangerInfantFlight5Detail = findViewById(R.id.panelPassangerInfantFlight5Detail);

            panelPassangerInfantFlight5Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
                public void onCollapse(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_expand));
                }

                public void onExpand(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_collapse));
                }
            });
        }

        if (6 <= countInfant) {
            rlPassangerInfantFlight6 = findViewById(R.id.rlPassangerInfantFlight6);
            cardPassangerInfantFlight6 = findViewById(R.id.cardPassangerInfantFlight6);
//            cardPassangerInfantFlight6.setOnClickListener(this);
            noUrutInfantFlight6 = findViewById(R.id.textUrutInfantFlight6);
            textContentInfantFlight6 = findViewById(R.id.textContentInfantFlight6);
            textActionInfantFlight6 = findViewById(R.id.textActionInfantFlight6);
            imageActionInfantFlight6 = findViewById(R.id.imageActionInfantFlight6);
//            textActionInfantFlight6.setOnClickListener(this);
//            imageActionInfantFlight6.setOnClickListener(this);
            linContentInfantFlight6 = findViewById(R.id.linContentInfantFlight6);
            linContentInfantFlight6.setOnClickListener(this);
            imageViewExpandPassangerInfantFlight6 = findViewById(R.id.imageViewExpandPassangerInfantFlight6);
            imageViewExpandPassangerInfantFlight6.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_baby));
            panelPassangerInfantFlight6Detail = findViewById(R.id.panelPassangerInfantFlight6Detail);

            panelPassangerInfantFlight6Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
                public void onCollapse(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_expand));
                }

                public void onExpand(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_collapse));
                }
            });
        }

        if (7 <= countInfant) {
            rlPassangerInfantFlight7 = findViewById(R.id.rlPassangerInfantFlight7);
            cardPassangerInfantFlight7 = findViewById(R.id.cardPassangerInfantFlight7);
//            cardPassangerInfantFlight7.setOnClickListener(this);
            noUrutInfantFlight7 = findViewById(R.id.textUrutInfantFlight7);
            textContentInfantFlight7 = findViewById(R.id.textContentInfantFlight7);
            textActionInfantFlight7 = findViewById(R.id.textActionInfantFlight7);
            imageActionInfantFlight7 = findViewById(R.id.imageActionInfantFlight7);
//            textActionInfantFlight7.setOnClickListener(this);
//            imageActionInfantFlight7.setOnClickListener(this);
            linContentInfantFlight7 = findViewById(R.id.linContentInfantFlight7);
            linContentInfantFlight7.setOnClickListener(this);
            imageViewExpandPassangerInfantFlight7 = findViewById(R.id.imageViewExpandPassangerInfantFlight7);
            imageViewExpandPassangerInfantFlight7.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_baby));
            panelPassangerInfantFlight7Detail = findViewById(R.id.panelPassangerInfantFlight7Detail);

            panelPassangerInfantFlight7Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
                public void onCollapse(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_expand));
                }

                public void onExpand(View handle, View content) {
                    ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                    imageView.setBackground(ContextCompat.getDrawable(FlightBookActivity.this, R.drawable.ic_collapse));
                }
            });
        }
        
        
        textHpKontak = findViewById(R.id.textHpKontak);
        textHpKontak.setText(PreferenceClass.getUser().getNotelp_pemilik().replace("+62", "0"));
        textEmailKontak = findViewById(R.id.textEmailKontak);
        textEmailKontak.setText(PreferenceClass.getUser().getEmail_pemilik());

        btnPesan = findViewById(R.id.btn_pesan);
//        MaterialRippleLayout.on(btnPesan).rippleOverlay(true)
//                .rippleAlpha(0.2f).rippleColor(R.color.md_orange_200)
//                .rippleHover(true).create();
        btnPesan.setOnClickListener(this);


//        JSONArray detailTitleArr= PreferenceClass.getJSONArray(FlightKeyPreference.detailTitle);
//        JSONObject datax = null;
        StringBuilder nameCode = new StringBuilder();
        String bandaraAsal=PreferenceClass.getString(FlightKeyPreference.airportNamaAsal, "");
        String bandaraTujuan=PreferenceClass.getString(FlightKeyPreference.airportNamaTujuan, "");
        String waktuAsal=PreferenceClass.getString(FlightKeyPreference.departureTime, "");
        String waktuTujuan=PreferenceClass.getString(FlightKeyPreference.arrivalTime, "");

//        String hari[]=new String[data.getDetailTitle().length()];
        for (int i = 0; i < data.getDetailTitle().length(); i++) {
           // final JSONObject jsonObjectBg = new JSONObject();

            try {
                JSONObject object = data.getDetailTitle().getJSONObject(i);
                nameCode.append(object.getString("flightName") + " " + object.getString("flightCode")+"\n");
                bandaraAsal=data.getDetailTitle().getJSONObject(0).getString("originName");

                waktuAsal=data.getDetailTitle().getJSONObject(0).getString("depart");
                bandaraTujuan=data.getDetailTitle().getJSONObject(data.getDetailTitle().length()-1).getString("destinationName");
                waktuTujuan=data.getDetailTitle().getJSONObject(data.getDetailTitle().length()-1).getString("arrival");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
//        textOrigin.setText(PreferenceClass.getString(FlightKeyPreference.airportNamaAsal, ""));
        textOrigin.setText(bandaraAsal);
        textDestination.setText(bandaraTujuan);
        textAirlineNo.setText(nameCode.toString());
//        flightDetailModel.setFlightIcon(data.getString("flightIcon"));
//        flightDetailModel.setFlightName(data.getString("flightName"));
//        flightDetailModel.setFlightCode(data.getString("flightCode"));
      //  textAirlineNo.setText(PreferenceClass.getString(FlightKeyPreference.airlineName, "") + " " + PreferenceClass.getString(FlightKeyPreference.flightCode, ""));

        timeDep = waktuAsal;//PreferenceClass.getString(FlightKeyPreference.departureTime, "");
        timeArr = waktuTujuan;//PreferenceClass.getString(FlightKeyPreference.arrivalTime, "");

        textTimeDept.setText(timeDep);
        textTimeArr.setText(timeArr);

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd", SBFApplication.config.locale);
        Date dateDept = null;
        try {
//            dateDept = fmt.parse(PreferenceClass.getString(FlightKeyPreference.departureDateFlight, ""));
            dateDept = fmt.parse(data.getDepartureDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date dateArr = null;
        try {
//            dateArr = fmt.parse(PreferenceClass.getString(FlightKeyPreference.arrivalDate, ""));
            dateArr = fmt.parse(data.getArrivalDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMM yyyy", SBFApplication.config.locale);
        depDate = sdf.format(dateDept);
        arrDate = sdf.format(dateArr);
        textDateDept.setText(depDate);
        textDateArr.setText(arrDate);

        Log.d(TAG, "onCreate: " + countAdult + " " + countChild + " " + countInfant);

        adultPassanger = new String[countAdult];
        adultBaggage = new String[countAdult];
        childPassanger = new String[countChild];
        childBaggage = new String[countChild];
        infantPassanger = new String[countInfant];

        for (int a = 2; a <= countAdult; a++) {
            switch (a) {
                case 2:
                    cardPassangerAdultFlight2.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    cardPassangerAdultFlight3.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    cardPassangerAdultFlight4.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    cardPassangerAdultFlight5.setVisibility(View.VISIBLE);
                    break;
                case 6:
                    cardPassangerAdultFlight6.setVisibility(View.VISIBLE);
                    break;
                case 7:
                    cardPassangerAdultFlight7.setVisibility(View.VISIBLE);
                    break;
            }
        }

        for (int c = 1; c <= countChild; c++) {
            switch (c) {
                case 1:
                    noUrutChildFlight1.setText(String.valueOf(countAdult + 1));
                    cardPassangerChildFlight1.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    noUrutChildFlight2.setText(String.valueOf(countAdult + 2));
                    cardPassangerChildFlight2.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    noUrutChildFlight3.setText(String.valueOf(countAdult + 3));
                    cardPassangerChildFlight3.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    noUrutChildFlight4.setText(String.valueOf(countAdult + 4));
                    cardPassangerChildFlight4.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    noUrutChildFlight5.setText(String.valueOf(countAdult + 5));
                    cardPassangerChildFlight5.setVisibility(View.VISIBLE);
                    break;
                case 6:
                    noUrutChildFlight6.setText(String.valueOf(countAdult + 6));
                    cardPassangerChildFlight6.setVisibility(View.VISIBLE);
                    break;
                case 7:
                    noUrutChildFlight7.setText(String.valueOf(countAdult + 7));
                    cardPassangerChildFlight7.setVisibility(View.VISIBLE);
                    break;
            }
        }

        for (int i = 1; i <= countInfant; i++) {
            switch (i) {
                case 1:
                    noUrutInfantFlight1.setText(String.valueOf(countAdult + countChild + 1));
                    cardPassangerInfantFlight1.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    noUrutInfantFlight2.setText(String.valueOf(countAdult + countChild + 2));
                    cardPassangerInfantFlight2.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    noUrutInfantFlight3.setText(String.valueOf(countAdult + countChild + 3));
                    cardPassangerInfantFlight3.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    noUrutInfantFlight4.setText(String.valueOf(countAdult + countChild + 4));
                    cardPassangerInfantFlight4.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    noUrutInfantFlight5.setText(String.valueOf(countAdult + countChild + 5));
                    cardPassangerInfantFlight5.setVisibility(View.VISIBLE);
                    break;
                case 6:
                    noUrutInfantFlight6.setText(String.valueOf(countAdult + countChild + 6));
                    cardPassangerInfantFlight6.setVisibility(View.VISIBLE);
                    break;
                case 7:
                    noUrutInfantFlight7.setText(String.valueOf(countAdult + countChild + 7));
                    cardPassangerInfantFlight7.setVisibility(View.VISIBLE);
                    break;    

            }
        }
//        PreferenceClass.remove(FlightKeyPreference.baggage1);
//        PreferenceClass.remove(FlightKeyPreference.baggage2);
//        PreferenceClass.remove(FlightKeyPreference.baggage3);
        PreferenceClass.remove(FlightKeyPreference.namaPenumpangAdult1);
        PreferenceClass.remove(FlightKeyPreference.namaPenumpangAdult2);
        PreferenceClass.remove(FlightKeyPreference.namaPenumpangAdult3);
        PreferenceClass.remove(FlightKeyPreference.namaPenumpangAdult4);
        PreferenceClass.remove(FlightKeyPreference.namaPenumpangAdult5);
        PreferenceClass.remove(FlightKeyPreference.namaPenumpangAdult6);
        PreferenceClass.remove(FlightKeyPreference.namaPenumpangAdult7);
       // PreferenceClass.remove(FlightKeyPreference.namaPenumpangInfant1);


        PreferenceClass.remove(FlightKeyPreference.baggageAdult1);
        PreferenceClass.remove(FlightKeyPreference.baggageAdult2);
        PreferenceClass.remove(FlightKeyPreference.baggageAdult3);
        PreferenceClass.remove(FlightKeyPreference.baggageAdult4);
        PreferenceClass.remove(FlightKeyPreference.baggageAdult5);
        PreferenceClass.remove(FlightKeyPreference.baggageAdult6);
        PreferenceClass.remove(FlightKeyPreference.baggageAdult7);
        PreferenceClass.remove(FlightKeyPreference.baggageChild1);
        PreferenceClass.remove(FlightKeyPreference.baggageChild2);
        PreferenceClass.remove(FlightKeyPreference.baggageChild3);
        PreferenceClass.remove(FlightKeyPreference.baggageChild4);
        PreferenceClass.remove(FlightKeyPreference.baggageChild5);
        PreferenceClass.remove(FlightKeyPreference.baggageChild6);
        PreferenceClass.remove(FlightKeyPreference.baggageChild7);
        /*
         * onCreate langsung action fare in background
         * */

        if (isFare) {
            loadingPage.setVisibility(View.GONE);
            scrollMainBook.setVisibility(View.VISIBLE);
          //  frameBottomFlight.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(sJson);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //Log.d(TAG, "onCreate: " + jsonObject);
                    RequestUtilsTravel.transportWithProgressResponse(FlightBookActivity.this, FlightPath.FARE, jsonObject, TravelActionCode.FARE, FlightBookActivity.this);
                }
            }).start();
        }
        PreferenceClass.putInt(FlightKeyPreference.countBaggage, 0);
        for (int i = 0; i < data.getDetailTitle().length(); i++) {
            final JSONObject jsonObjectBg = new JSONObject();

            try {
                final JSONObject object = data.getDetailTitle().getJSONObject(i);
                if (object.getString("flightCode").substring(0, 2).equals("IW") || object.getString("flightCode").substring(0, 2).equals("JT")) {
                    final String route = object.getString("origin") + "-" + object.getString("destination");
                    final String icon = object.getString("flightIcon");
//                    if(check(stringArrCode,object.getString("flightCode").substring(0, 2))){

//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                    countBaggage++;

                            try {
                                callBaggageList(jsonObjectBg, object, countBaggage, object.getString("flightCode"), route, icon);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }}).start();
//                        }
//                    }, 2000);


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        linTanggalLahirAdult1 = findViewById(R.id.linTanggalLahirAdultFlight1);
        linTanggalLahirAdult2 = findViewById(R.id.linTanggalLahirAdultFlight2);
        linTanggalLahirAdult3 = findViewById(R.id.linTanggalLahirAdultFlight3);
        linTanggalLahirAdult4 = findViewById(R.id.linTanggalLahirAdultFlight4);
        linTanggalLahirAdult5 = findViewById(R.id.linTanggalLahirAdultFlight5);
        linTanggalLahirAdult6 = findViewById(R.id.linTanggalLahirAdultFlight6);
        linTanggalLahirAdult7 = findViewById(R.id.linTanggalLahirAdultFlight7);


        linNoIdentitasAdult1 = findViewById(R.id.linNoIdentitasAdultFlight1);
        linNoIdentitasAdult2 = findViewById(R.id.linNoIdentitasAdultFlight2);
        linNoIdentitasAdult3 = findViewById(R.id.linNoIdentitasAdultFlight3);
        linNoIdentitasAdult4 = findViewById(R.id.linNoIdentitasAdultFlight4);
        linNoIdentitasAdult5 = findViewById(R.id.linNoIdentitasAdultFlight5);
        linNoIdentitasAdult6 = findViewById(R.id.linNoIdentitasAdultFlight6);
        linNoIdentitasAdult7 = findViewById(R.id.linNoIdentitasAdultFlight7);


        textViewNoIdentitasAdult1 = findViewById(R.id.textViewNoIdentitasAdultFlight1);
        textViewNoIdentitasAdult2 = findViewById(R.id.textViewNoIdentitasAdultFlight2);
        textViewNoIdentitasAdult3 = findViewById(R.id.textViewNoIdentitasAdultFlight3);
        textViewNoIdentitasAdult4 = findViewById(R.id.textViewNoIdentitasAdultFlight4);
        textViewNoIdentitasAdult5 = findViewById(R.id.textViewNoIdentitasAdultFlight5);
        textViewNoIdentitasAdult6 = findViewById(R.id.textViewNoIdentitasAdultFlight6);
        textViewNoIdentitasAdult7 = findViewById(R.id.textViewNoIdentitasAdultFlight7);


        textViewTanggalLahirAdult1 = findViewById(R.id.textViewTanggalLahirAdultFlight1);
        textViewTanggalLahirAdult2 = findViewById(R.id.textViewTanggalLahirAdultFlight2);
        textViewTanggalLahirAdult3 = findViewById(R.id.textViewTanggalLahirAdultFlight3);
        textViewTanggalLahirAdult4 = findViewById(R.id.textViewTanggalLahirAdultFlight4);
        textViewTanggalLahirAdult5 = findViewById(R.id.textViewTanggalLahirAdultFlight5);
        textViewTanggalLahirAdult6 = findViewById(R.id.textViewTanggalLahirAdultFlight6);
        textViewTanggalLahirAdult7 = findViewById(R.id.textViewTanggalLahirAdultFlight7);


        textViewMobileAdult1 = findViewById(R.id.textViewMobileAdultFlight1);

        textViewEmailAdult1 = findViewById(R.id.textViewEmailAdultFlight1);


        textViewTanggalLahirChild1 = findViewById(R.id.textViewTanggalLahirChildFlight1);
        textViewTanggalLahirChild2 = findViewById(R.id.textViewTanggalLahirChildFlight2);
        textViewTanggalLahirChild3 = findViewById(R.id.textViewTanggalLahirChildFlight3);
        textViewTanggalLahirChild4 = findViewById(R.id.textViewTanggalLahirChildFlight4);
        textViewTanggalLahirChild5 = findViewById(R.id.textViewTanggalLahirChildFlight5);
        textViewTanggalLahirChild6 = findViewById(R.id.textViewTanggalLahirChildFlight6);
        textViewTanggalLahirChild7 = findViewById(R.id.textViewTanggalLahirChildFlight7);


        textViewTanggalLahirInfant1 = findViewById(R.id.textViewTanggalLahirInfantFlight1);
        textViewTanggalLahirInfant2 = findViewById(R.id.textViewTanggalLahirInfantFlight2);
        textViewTanggalLahirInfant3 = findViewById(R.id.textViewTanggalLahirInfantFlight3);
        textViewTanggalLahirInfant4 = findViewById(R.id.textViewTanggalLahirInfantFlight4);
        textViewTanggalLahirInfant5 = findViewById(R.id.textViewTanggalLahirInfantFlight5);
        textViewTanggalLahirInfant6 = findViewById(R.id.textViewTanggalLahirInfantFlight6);
        textViewTanggalLahirInfant7 = findViewById(R.id.textViewTanggalLahirInfantFlight7);


        lin_contentPassportChild1 = findViewById(R.id.lin_contentPassportChild1);
        lin_contentPassportChild1 = findViewById(R.id.lin_contentPassportChild2);
        lin_contentPassportChild1 = findViewById(R.id.lin_contentPassportChild3);
        lin_contentPassportChild1 = findViewById(R.id.lin_contentPassportChild4);
        lin_contentPassportChild1 = findViewById(R.id.lin_contentPassportChild5);
        lin_contentPassportChild1 = findViewById(R.id.lin_contentPassportChild6);
        lin_contentPassportChild1 = findViewById(R.id.lin_contentPassportChild7);


        lin_contentPassportInfant1 = findViewById(R.id.lin_contentPassportInfant1);
        lin_contentPassportInfant2 = findViewById(R.id.lin_contentPassportInfant2);
        lin_contentPassportInfant3 = findViewById(R.id.lin_contentPassportInfant3);
        lin_contentPassportInfant4 = findViewById(R.id.lin_contentPassportInfant4);
        lin_contentPassportInfant5 = findViewById(R.id.lin_contentPassportInfant5);
        lin_contentPassportInfant6 = findViewById(R.id.lin_contentPassportInfant6);
        lin_contentPassportInfant7 = findViewById(R.id.lin_contentPassportInfant7);

        textViewNoPassportInfantFlight1 = findViewById(R.id.textViewNoPassportInfantFlight1);
        textViewNoPassportInfantFlight2 = findViewById(R.id.textViewNoPassportInfantFlight2);
        textViewNoPassportInfantFlight3 = findViewById(R.id.textViewNoPassportInfantFlight3);
        textViewNoPassportInfantFlight4 = findViewById(R.id.textViewNoPassportInfantFlight4);
        textViewNoPassportInfantFlight5 = findViewById(R.id.textViewNoPassportInfantFlight5);
        textViewNoPassportInfantFlight6 = findViewById(R.id.textViewNoPassportInfantFlight6);
        textViewNoPassportInfantFlight7 = findViewById(R.id.textViewNoPassportInfantFlight7);


        textViewNoPassportChildFlight1 = findViewById(R.id.textViewNoPassportChildFlight1);
        textViewNoPassportChildFlight2 = findViewById(R.id.textViewNoPassportChildFlight2);
        textViewNoPassportChildFlight3 = findViewById(R.id.textViewNoPassportChildFlight3);
        textViewNoPassportChildFlight4 = findViewById(R.id.textViewNoPassportChildFlight4);
        textViewNoPassportChildFlight5 = findViewById(R.id.textViewNoPassportChildFlight5);
        textViewNoPassportChildFlight6 = findViewById(R.id.textViewNoPassportChildFlight6);
        textViewNoPassportChildFlight7 = findViewById(R.id.textViewNoPassportChildFlight7);

        labelViewNoIdentitasAdultFlight1 = findViewById(R.id.labelViewNoIdentitasAdultFlight1);
        labelViewNoIdentitasAdultFlight2 = findViewById(R.id.labelViewNoIdentitasAdultFlight2);
        labelViewNoIdentitasAdultFlight3 = findViewById(R.id.labelViewNoIdentitasAdultFlight3);
        labelViewNoIdentitasAdultFlight4 = findViewById(R.id.labelViewNoIdentitasAdultFlight4);
        labelViewNoIdentitasAdultFlight5 = findViewById(R.id.labelViewNoIdentitasAdultFlight5);
        labelViewNoIdentitasAdultFlight6 = findViewById(R.id.labelViewNoIdentitasAdultFlight6);
        labelViewNoIdentitasAdultFlight7 = findViewById(R.id.labelViewNoIdentitasAdultFlight7);


        linEmailAdult1 = findViewById(R.id.linEmailAdultFlight1);
        linMobileAdult1 = findViewById(R.id.linMobileAdultFlight1);
    }

    private void callBaggageList(@NonNull JSONObject jsonObjectBg, @NonNull JSONObject object, int i, String flightCode, String route, String icon) {
        try {
            jsonObjectBg.put("airline", data.getAirlineCode());
            jsonObjectBg.put("departure", object.getString("origin"));
            jsonObjectBg.put("arrival", object.getString("destination"));
            jsonObjectBg.put("maskapai", "TP" + object.getString("flightCode").substring(0, 2));
            jsonObjectBg.put("cid", PreferenceClass.getString(TravelActionCode.CID,"0"));
            jsonObjectBg.put("token", PreferenceClass.getToken());

            if (i == 1) {
                PreferenceClass.putString(FlightKeyPreference.baggageFlightIcon1, icon);
                PreferenceClass.putString(FlightKeyPreference.baggageFlightNo1, flightCode);
                PreferenceClass.putString(FlightKeyPreference.baggageRoute1, route);
                PreferenceClass.putInt(FlightKeyPreference.countBaggage, 1);
                RequestUtilsTravel.transportWithProgressResponse(FlightBookActivity.this, FlightPath.BAGAGE, jsonObjectBg, TravelActionCode.BAGGAGE1, FlightBookActivity.this);
            } else if (i == 2) {
                PreferenceClass.putString(FlightKeyPreference.baggageFlightIcon2, icon);
                PreferenceClass.putString(FlightKeyPreference.baggageFlightNo2, flightCode);
                PreferenceClass.putString(FlightKeyPreference.baggageRoute2, route);
                PreferenceClass.putInt(FlightKeyPreference.countBaggage, 2);
                RequestUtilsTravel.transportWithProgressResponse(FlightBookActivity.this, FlightPath.BAGAGE, jsonObjectBg, TravelActionCode.BAGGAGE2, FlightBookActivity.this);
            } else if (i == 3) {
                PreferenceClass.putString(FlightKeyPreference.baggageFlightIcon3, icon);
                PreferenceClass.putString(FlightKeyPreference.baggageFlightNo3, flightCode);
                PreferenceClass.putString(FlightKeyPreference.baggageRoute3, route);
                PreferenceClass.putInt(FlightKeyPreference.countBaggage, 3);
                RequestUtilsTravel.transportWithProgressResponse(FlightBookActivity.this, FlightPath.BAGAGE, jsonObjectBg, TravelActionCode.BAGGAGE3, FlightBookActivity.this);

            }
            Log.d(TAG, "onCreate: " + jsonObjectBg);
        } catch (JSONException jsone) {
            jsone.printStackTrace();
        }
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

//    public void closeTopSheet(View view) {
//        dialog.dismiss();
//    }

    public void onBackPressed() {
        if (isStillRunning) {
            RequestUtilsTravel.cancelTravel();
        }
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void openFormAdult(@NonNull View v) {
        Intent intent = new Intent(FlightBookActivity.this, FlightFormBookAdultActivity.class);
//        if (v.getId() == R.id.imageActionAdultFlight1) {
        if (v.getId() == R.id.linContentAdultFlight1) {
            if (textActionAdultFlight1.getVisibility() == View.GONE) {
                intent.putExtra(FlightKeyPreference.isNewFlight, true);
                intent.putExtra("isFirstPassenger", true);
                startActivityForResult(intent, TravelActionCode.ADULT1);
            }else{
                intent.putExtra(FlightKeyPreference.isNewFlight, false);
                intent.putExtra("isFirstPassenger", true);
                intent.putExtra("adultTitle", (String) textContentAdultFlight1.getTag(R.id.titleAdult1));
                intent.putExtra("adultTitleTemp", (String) textContentAdultFlight1.getTag(R.id.titleTempAdult1));
                intent.putExtra("adultName", (String) textContentAdultFlight1.getTag(R.id.nameAdult1));
                intent.putExtra("adultId", (String) textContentAdultFlight1.getTag(R.id.idAdult1));
                intent.putExtra("adultEmail", (String) textContentAdultFlight1.getTag(R.id.emailAdult1));
                intent.putExtra("adultMobilePhone", (String) textContentAdultFlight1.getTag(R.id.mobilePhoneAdult1));
                intent.putExtra("adultBorn", (String) textContentAdultFlight1.getTag(R.id.bornAdult1));
                intent.putExtra("adultBornShow", (String) textContentAdultFlight1.getTag(R.id.bornShowAdult1));

                intent.putExtra("adultNoPassport", (String) textContentAdultFlight1.getTag(R.id.noPassportAdult1));
                intent.putExtra("adultNationality", (String) textContentAdultFlight1.getTag(R.id.nasinatilyAdult1));
                intent.putExtra("adultNationalityKode",(String) textContentAdultFlight1.getTag(R.id.nasinatilyKodeAdult1));
                intent.putExtra("adultIssuingCountry", (String) textContentAdultFlight1.getTag(R.id.issuingCountryAdult1));
                intent.putExtra("adultIssuingCountryKode", (String) textContentAdultFlight1.getTag(R.id.issuingCountryKodeAdult1));

                intent.putExtra("adultExpired", (String) textContentAdultFlight1.getTag(R.id.expiredAdult1));
                intent.putExtra("adultExpiredShow", (String) textContentAdultFlight1.getTag(R.id.expiredShowAdult1));



                intent.putExtra("adultCheckboxBeliBagasi", (int) textContentAdultFlight1.getTag(R.id.isBuyAdultBaggage1));
                intent.putExtra("adultTitleBaggagePosition", (int) textContentAdultFlight1.getTag(R.id.titleAdultBaggagePosition1));
//            intent.putExtra("adultTitleBaggage1", (String) textContentAdultFlight1.getTag(R.id.titleAdultBaggage1_1));
                intent.putExtra("titleBaggageTemp1", (String) textContentAdultFlight1.getTag(R.id.titleTempAdultBaggage1_1));
//            intent.putExtra("adultTitleBaggage2", (String) textContentAdultFlight1.getTag(R.id.titleAdultBaggage1_2));
                intent.putExtra("titleBaggageTemp2", (String) textContentAdultFlight1.getTag(R.id.titleTempAdultBaggage1_2));
//            intent.putExtra("adultTitleBaggage3", (String) textContentAdultFlight1.getTag(R.id.titleAdultBaggage1_3));
                intent.putExtra("titleBaggageTemp3", (String) textContentAdultFlight1.getTag(R.id.titleTempAdultBaggage1_3));

                startActivityForResult(intent, TravelActionCode.ADULT1);
            }
//        } else if (v.getId() == R.id.imageActionAdultFlight2) {
        } else if (v.getId() == R.id.linContentAdultFlight2) {
            if (textActionAdultFlight2.getVisibility() == View.GONE) {
                intent.putExtra(FlightKeyPreference.isNewFlight, true);
                startActivityForResult(intent, TravelActionCode.ADULT2);
            }else{
                intent.putExtra(FlightKeyPreference.isNewFlight, false);
                intent.putExtra("adultTitle", (String) textContentAdultFlight2.getTag(R.id.titleAdult2));
                intent.putExtra("adultTitleTemp", (String) textContentAdultFlight2.getTag(R.id.titleTempAdult2));
                intent.putExtra("adultName", (String) textContentAdultFlight2.getTag(R.id.nameAdult2));
                intent.putExtra("adultId", (String) textContentAdultFlight2.getTag(R.id.idAdult2));
                intent.putExtra("adultEmail", (String) textContentAdultFlight2.getTag(R.id.emailAdult2));
                intent.putExtra("adultMobilePhone", (String) textContentAdultFlight2.getTag(R.id.mobilePhoneAdult2));
                intent.putExtra("adultBorn", (String) textContentAdultFlight2.getTag(R.id.bornAdult2));
                intent.putExtra("adultBornShow", (String) textContentAdultFlight2.getTag(R.id.bornShowAdult2));

                intent.putExtra("adultNoPassport", (String) textContentAdultFlight2.getTag(R.id.noPassportAdult2));
                intent.putExtra("adultNationality", (String) textContentAdultFlight2.getTag(R.id.nasinatilyAdult2));
                intent.putExtra("adultNationalityKode",(String) textContentAdultFlight2.getTag(R.id.nasinatilyKodeAdult2));
                intent.putExtra("adultIssuingCountry", (String) textContentAdultFlight2.getTag(R.id.issuingCountryAdult2));
                intent.putExtra("adultIssuingCountryKode", (String) textContentAdultFlight2.getTag(R.id.issuingCountryKodeAdult2));

                intent.putExtra("adultExpired", (String) textContentAdultFlight2.getTag(R.id.expiredAdult2));
                intent.putExtra("adultExpiredShow", (String) textContentAdultFlight2.getTag(R.id.expiredShowAdult2));


                intent.putExtra("adultCheckboxBeliBagasi", (int) textContentAdultFlight2.getTag(R.id.isBuyAdultBaggage2));
                intent.putExtra("adultTitleBaggagePosition", (int) textContentAdultFlight2.getTag(R.id.titleAdultBaggagePosition2));
//            intent.putExtra("adultTitleBaggage1", (String) textContentAdultFlight2.getTag(R.id.titleAdultBaggage2_1));
                intent.putExtra("titleBaggageTemp1", (String) textContentAdultFlight2.getTag(R.id.titleTempAdultBaggage2_1));
//            intent.putExtra("adultTitleBaggage2", (String) textContentAdultFlight2.getTag(R.id.titleAdultBaggage2_2));
                intent.putExtra("titleBaggageTemp2", (String) textContentAdultFlight2.getTag(R.id.titleTempAdultBaggage2_2));
//            intent.putExtra("adultTitleBaggage3", (String) textContentAdultFlight2.getTag(R.id.titleAdultBaggage2_3));
                intent.putExtra("titleBaggageTemp3", (String) textContentAdultFlight2.getTag(R.id.titleTempAdultBaggage2_3));

                startActivityForResult(intent, TravelActionCode.ADULT2);
            }
//        } else if (v.getId() == R.id.imageActionAdultFlight3) {
        } else if (v.getId() == R.id.linContentAdultFlight3) {
            if (textActionAdultFlight3.getVisibility() == View.GONE) {
                intent.putExtra(FlightKeyPreference.isNewFlight, true);
                startActivityForResult(intent, TravelActionCode.ADULT3);
            }else{
                intent.putExtra(FlightKeyPreference.isNewFlight, false);
                intent.putExtra("adultTitle", (String) textContentAdultFlight3.getTag(R.id.titleAdult3));
                intent.putExtra("adultTitleTemp", (String) textContentAdultFlight3.getTag(R.id.titleTempAdult3));
                intent.putExtra("adultName", (String) textContentAdultFlight3.getTag(R.id.nameAdult3));
                intent.putExtra("adultId", (String) textContentAdultFlight3.getTag(R.id.idAdult3));
                intent.putExtra("adultEmail", (String) textContentAdultFlight3.getTag(R.id.emailAdult3));
                intent.putExtra("adultMobilePhone", (String) textContentAdultFlight3.getTag(R.id.mobilePhoneAdult3));
                intent.putExtra("adultBorn", (String) textContentAdultFlight3.getTag(R.id.bornAdult3));
                intent.putExtra("adultBornShow", (String) textContentAdultFlight3.getTag(R.id.bornShowAdult3));

                intent.putExtra("adultNoPassport", (String) textContentAdultFlight3.getTag(R.id.noPassportAdult3));
                intent.putExtra("adultNationality", (String) textContentAdultFlight3.getTag(R.id.nasinatilyAdult3));
                intent.putExtra("adultNationalityKode",(String) textContentAdultFlight3.getTag(R.id.nasinatilyKodeAdult3));
                intent.putExtra("adultIssuingCountry", (String) textContentAdultFlight3.getTag(R.id.issuingCountryAdult3));
                intent.putExtra("adultIssuingCountryKode", (String) textContentAdultFlight3.getTag(R.id.issuingCountryKodeAdult3));

                intent.putExtra("adultExpired", (String) textContentAdultFlight3.getTag(R.id.expiredAdult3));
                intent.putExtra("adultExpiredShow", (String) textContentAdultFlight3.getTag(R.id.expiredShowAdult3));


                intent.putExtra("adultCheckboxBeliBagasi", (int) textContentAdultFlight3.getTag(R.id.isBuyAdultBaggage3));
                intent.putExtra("adultTitleBaggagePosition", (int) textContentAdultFlight3.getTag(R.id.titleAdultBaggagePosition3));
//            intent.putExtra("adultTitleBaggage1", (String) textContentAdultFlight3.getTag(R.id.titleAdultBaggage3_1));
                intent.putExtra("titleBaggageTemp1", (String) textContentAdultFlight3.getTag(R.id.titleTempAdultBaggage3_1));
//            intent.putExtra("adultTitleBaggage2", (String) textContentAdultFlight3.getTag(R.id.titleAdultBaggage3_2));
                intent.putExtra("titleBaggageTemp2", (String) textContentAdultFlight3.getTag(R.id.titleTempAdultBaggage3_2));
//            intent.putExtra("adultTitleBaggage3", (String) textContentAdultFlight3.getTag(R.id.titleAdultBaggage3_3));
                intent.putExtra("titleBaggageTemp3", (String) textContentAdultFlight3.getTag(R.id.titleTempAdultBaggage3_3));
                startActivityForResult(intent, TravelActionCode.ADULT3);
            }
//        } else if (v.getId() == R.id.imageActionAdultFlight4) {
        } else if (v.getId() == R.id.linContentAdultFlight4) {
            if (textActionAdultFlight4.getVisibility() == View.GONE) {
                intent.putExtra(FlightKeyPreference.isNewFlight, true);
                startActivityForResult(intent, TravelActionCode.ADULT4);
            }else{
                intent.putExtra(FlightKeyPreference.isNewFlight, false);
                intent.putExtra("adultTitle", (String) textContentAdultFlight4.getTag(R.id.titleAdult4));
                intent.putExtra("adultTitleTemp", (String) textContentAdultFlight4.getTag(R.id.titleTempAdult4));
                intent.putExtra("adultName", (String) textContentAdultFlight4.getTag(R.id.nameAdult4));
                intent.putExtra("adultId", (String) textContentAdultFlight4.getTag(R.id.idAdult4));
                intent.putExtra("adultEmail", (String) textContentAdultFlight4.getTag(R.id.emailAdult4));
                intent.putExtra("adultMobilePhone", (String) textContentAdultFlight4.getTag(R.id.mobilePhoneAdult4));
                intent.putExtra("adultBorn", (String) textContentAdultFlight4.getTag(R.id.bornAdult4));
                intent.putExtra("adultBornShow", (String) textContentAdultFlight4.getTag(R.id.bornShowAdult4));

                intent.putExtra("adultNoPassport", (String) textContentAdultFlight4.getTag(R.id.noPassportAdult4));
                intent.putExtra("adultNationality", (String) textContentAdultFlight4.getTag(R.id.nasinatilyAdult4));
                intent.putExtra("adultNationalityKode",(String) textContentAdultFlight4.getTag(R.id.nasinatilyKodeAdult4));
                intent.putExtra("adultIssuingCountry", (String) textContentAdultFlight4.getTag(R.id.issuingCountryAdult4));
                intent.putExtra("adultIssuingCountryKode", (String) textContentAdultFlight4.getTag(R.id.issuingCountryKodeAdult4));

                intent.putExtra("adultExpired", (String) textContentAdultFlight4.getTag(R.id.expiredAdult4));
                intent.putExtra("adultExpiredShow", (String) textContentAdultFlight4.getTag(R.id.expiredShowAdult4));


                intent.putExtra("adultCheckboxBeliBagasi", (int) textContentAdultFlight4.getTag(R.id.isBuyAdultBaggage4));
                intent.putExtra("adultTitleBaggagePosition", (int) textContentAdultFlight4.getTag(R.id.titleAdultBaggagePosition4));
//            intent.putExtra("adultTitleBaggage1", (String) textContentAdultFlight4.getTag(R.id.titleAdultBaggage4_1));
                intent.putExtra("titleBaggageTemp1", (String) textContentAdultFlight4.getTag(R.id.titleTempAdultBaggage4_1));
//            intent.putExtra("adultTitleBaggage2", (String) textContentAdultFlight4.getTag(R.id.titleAdultBaggage4_2));
                intent.putExtra("titleBaggageTemp2", (String) textContentAdultFlight4.getTag(R.id.titleTempAdultBaggage4_2));
//            intent.putExtra("adultTitleBaggage3", (String) textContentAdultFlight4.getTag(R.id.titleAdultBaggage4_3));
                intent.putExtra("titleBaggageTemp3", (String) textContentAdultFlight4.getTag(R.id.titleTempAdultBaggage4_3));
                startActivityForResult(intent, TravelActionCode.ADULT4);
            }
//        } else if (v.getId() == R.id.imageActionAdultFlight5) {
        } else if (v.getId() == R.id.linContentAdultFlight5) {
            if (textActionAdultFlight5.getVisibility() == View.GONE) {
                intent.putExtra(FlightKeyPreference.isNewFlight, true);
                startActivityForResult(intent, TravelActionCode.ADULT5);
            }else{
                intent.putExtra(FlightKeyPreference.isNewFlight, false);
                intent.putExtra("adultTitle", (String) textContentAdultFlight5.getTag(R.id.titleAdult5));
                intent.putExtra("adultTitleTemp", (String) textContentAdultFlight5.getTag(R.id.titleTempAdult5));
                intent.putExtra("adultName", (String) textContentAdultFlight5.getTag(R.id.nameAdult5));
                intent.putExtra("adultId", (String) textContentAdultFlight5.getTag(R.id.idAdult5));
                intent.putExtra("adultEmail", (String) textContentAdultFlight5.getTag(R.id.emailAdult5));
                intent.putExtra("adultMobilePhone", (String) textContentAdultFlight5.getTag(R.id.mobilePhoneAdult5));
                intent.putExtra("adultBorn", (String) textContentAdultFlight5.getTag(R.id.bornAdult5));
                intent.putExtra("adultBornShow", (String) textContentAdultFlight5.getTag(R.id.bornShowAdult5));

                intent.putExtra("adultNoPassport", (String) textContentAdultFlight5.getTag(R.id.noPassportAdult5));
                intent.putExtra("adultNationality", (String) textContentAdultFlight5.getTag(R.id.nasinatilyAdult5));
                intent.putExtra("adultNationalityKode",(String) textContentAdultFlight5.getTag(R.id.nasinatilyKodeAdult5));
                intent.putExtra("adultIssuingCountry", (String) textContentAdultFlight5.getTag(R.id.issuingCountryAdult5));
                intent.putExtra("adultIssuingCountryKode", (String) textContentAdultFlight5.getTag(R.id.issuingCountryKodeAdult5));

                intent.putExtra("adultExpired", (String) textContentAdultFlight5.getTag(R.id.expiredAdult5));
                intent.putExtra("adultExpiredShow", (String) textContentAdultFlight5.getTag(R.id.expiredShowAdult5));

                intent.putExtra("adultCheckboxBeliBagasi", (int) textContentAdultFlight5.getTag(R.id.isBuyAdultBaggage5));
                intent.putExtra("adultTitleBaggagePosition", (int) textContentAdultFlight5.getTag(R.id.titleAdultBaggagePosition5));
//            intent.putExtra("adultTitleBaggage1", (String) textContentAdultFlight5.getTag(R.id.titleAdultBaggage5_1));
                intent.putExtra("titleBaggageTemp1", (String) textContentAdultFlight5.getTag(R.id.titleTempAdultBaggage5_1));
//            intent.putExtra("adultTitleBaggage2", (String) textContentAdultFlight5.getTag(R.id.titleAdultBaggage5_2));
                intent.putExtra("titleBaggageTemp2", (String) textContentAdultFlight5.getTag(R.id.titleTempAdultBaggage5_2));
//            intent.putExtra("adultTitleBaggage3", (String) textContentAdultFlight5.getTag(R.id.titleAdultBaggage5_3));
                intent.putExtra("titleBaggageTemp3", (String) textContentAdultFlight5.getTag(R.id.titleTempAdultBaggage5_3));
                startActivityForResult(intent, TravelActionCode.ADULT5);
            }
//        } else if (v.getId() == R.id.imageActionAdultFlight6) {
        } else if (v.getId() == R.id.linContentAdultFlight6) {
            if (textActionAdultFlight6.getVisibility() == View.GONE) {
                intent.putExtra(FlightKeyPreference.isNewFlight, true);
                startActivityForResult(intent, TravelActionCode.ADULT6);
            }else{
                intent.putExtra(FlightKeyPreference.isNewFlight, false);
                intent.putExtra("adultTitle", (String) textContentAdultFlight6.getTag(R.id.titleAdult6));
                intent.putExtra("adultTitleTemp", (String) textContentAdultFlight6.getTag(R.id.titleTempAdult6));
                intent.putExtra("adultName", (String) textContentAdultFlight6.getTag(R.id.nameAdult6));
                intent.putExtra("adultId", (String) textContentAdultFlight6.getTag(R.id.idAdult6));
                intent.putExtra("adultEmail", (String) textContentAdultFlight6.getTag(R.id.emailAdult6));
                intent.putExtra("adultMobilePhone", (String) textContentAdultFlight6.getTag(R.id.mobilePhoneAdult6));
                intent.putExtra("adultBorn", (String) textContentAdultFlight6.getTag(R.id.bornAdult6));
                intent.putExtra("adultBornShow", (String) textContentAdultFlight6.getTag(R.id.bornShowAdult6));

                intent.putExtra("adultNoPassport", (String) textContentAdultFlight6.getTag(R.id.noPassportAdult6));
                intent.putExtra("adultNationality", (String) textContentAdultFlight6.getTag(R.id.nasinatilyAdult6));
                intent.putExtra("adultNationalityKode",(String) textContentAdultFlight6.getTag(R.id.nasinatilyKodeAdult6));
                intent.putExtra("adultIssuingCountry", (String) textContentAdultFlight6.getTag(R.id.issuingCountryAdult6));
                intent.putExtra("adultIssuingCountryKode", (String) textContentAdultFlight6.getTag(R.id.issuingCountryKodeAdult6));

                intent.putExtra("adultExpired", (String) textContentAdultFlight6.getTag(R.id.expiredAdult6));
                intent.putExtra("adultExpiredShow", (String) textContentAdultFlight6.getTag(R.id.expiredShowAdult6));

                intent.putExtra("adultCheckboxBeliBagasi", (int) textContentAdultFlight6.getTag(R.id.isBuyAdultBaggage6));
                intent.putExtra("adultTitleBaggagePosition", (int) textContentAdultFlight6.getTag(R.id.titleAdultBaggagePosition6));
//            intent.putExtra("adultTitleBaggage1", (String) textContentAdultFlight6.getTag(R.id.titleAdultBaggage6_1));
                intent.putExtra("titleBaggageTemp1", (String) textContentAdultFlight6.getTag(R.id.titleTempAdultBaggage6_1));
//            intent.putExtra("adultTitleBaggage2", (String) textContentAdultFlight6.getTag(R.id.titleAdultBaggage6_2));
                intent.putExtra("titleBaggageTemp2", (String) textContentAdultFlight6.getTag(R.id.titleTempAdultBaggage6_2));
//            intent.putExtra("adultTitleBaggage3", (String) textContentAdultFlight6.getTag(R.id.titleAdultBaggage6_3));
                intent.putExtra("titleBaggageTemp3", (String) textContentAdultFlight6.getTag(R.id.titleTempAdultBaggage6_3));
                startActivityForResult(intent, TravelActionCode.ADULT6);
            }
//        } else if (v.getId() == R.id.imageActionAdultFlight7) {
        } else if (v.getId() == R.id.linContentAdultFlight7) {
            if (textActionAdultFlight7.getVisibility() == View.GONE) {
                intent.putExtra(FlightKeyPreference.isNewFlight, true);
                startActivityForResult(intent, TravelActionCode.ADULT7);
            }else{
                intent.putExtra(FlightKeyPreference.isNewFlight, false);
                intent.putExtra("adultTitle", (String) textContentAdultFlight7.getTag(R.id.titleAdult7));
                intent.putExtra("adultTitleTemp", (String) textContentAdultFlight7.getTag(R.id.titleTempAdult7));
                intent.putExtra("adultName", (String) textContentAdultFlight7.getTag(R.id.nameAdult7));
                intent.putExtra("adultId", (String) textContentAdultFlight7.getTag(R.id.idAdult7));
                intent.putExtra("adultEmail", (String) textContentAdultFlight7.getTag(R.id.emailAdult7));
                intent.putExtra("adultMobilePhone", (String) textContentAdultFlight7.getTag(R.id.mobilePhoneAdult7));
                intent.putExtra("adultBorn", (String) textContentAdultFlight7.getTag(R.id.bornAdult7));
                intent.putExtra("adultBornShow", (String) textContentAdultFlight7.getTag(R.id.bornShowAdult7));

                intent.putExtra("adultNoPassport", (String) textContentAdultFlight7.getTag(R.id.noPassportAdult7));
                intent.putExtra("adultNationality", (String) textContentAdultFlight7.getTag(R.id.nasinatilyAdult7));
                intent.putExtra("adultNationalityKode",(String) textContentAdultFlight7.getTag(R.id.nasinatilyKodeAdult7));
                intent.putExtra("adultIssuingCountry", (String) textContentAdultFlight7.getTag(R.id.issuingCountryAdult7));
                intent.putExtra("adultIssuingCountryKode", (String) textContentAdultFlight7.getTag(R.id.issuingCountryKodeAdult7));

                intent.putExtra("adultExpired", (String) textContentAdultFlight7.getTag(R.id.expiredAdult7));
                intent.putExtra("adultExpiredShow", (String) textContentAdultFlight7.getTag(R.id.expiredShowAdult7));

                intent.putExtra("adultCheckboxBeliBagasi", (int) textContentAdultFlight7.getTag(R.id.isBuyAdultBaggage7));
                intent.putExtra("adultTitleBaggagePosition", (int) textContentAdultFlight7.getTag(R.id.titleAdultBaggagePosition7));
//            intent.putExtra("adultTitleBaggage1", (String) textContentAdultFlight7.getTag(R.id.titleAdultBaggage7_1));
                intent.putExtra("titleBaggageTemp1", (String) textContentAdultFlight7.getTag(R.id.titleTempAdultBaggage7_1));
//            intent.putExtra("adultTitleBaggage2", (String) textContentAdultFlight7.getTag(R.id.titleAdultBaggage7_2));
                intent.putExtra("titleBaggageTemp2", (String) textContentAdultFlight7.getTag(R.id.titleTempAdultBaggage7_2));
//            intent.putExtra("adultTitleBaggage3", (String) textContentAdultFlight7.getTag(R.id.titleAdultBaggage7_3));
                intent.putExtra("titleBaggageTemp3", (String) textContentAdultFlight7.getTag(R.id.titleTempAdultBaggage7_3));
                startActivityForResult(intent, TravelActionCode.ADULT7);
            }
//        } else if (v.getId() == R.id.textActionAdultFlight1) {


//        } else if (v.getId() == R.id.textActionAdultFlight2) {

//        } else if (v.getId() == R.id.textActionAdultFlight3) {

//        } else if (v.getId() == R.id.textActionAdultFlight4) {

//        } else if (v.getId() == R.id.textActionAdultFlight5) {

//        } else if (v.getId() == R.id.textActionAdultFlight6) {

//        } else if (v.getId() == R.id.textActionAdultFlight7) {

        }

    }

    public void openFormChild(@NonNull View v) {
        Intent intent = new Intent(FlightBookActivity.this, FlightFormBookChildActivity.class);
//        if (v.getId() == R.id.imageActionChildFlight1) {
        if (v.getId() == R.id.linContentChildFlight1) {
            if (textActionChildFlight1.getVisibility() == View.GONE) {
                intent.putExtra(FlightKeyPreference.isNewFlight, true);
                startActivityForResult(intent, TravelActionCode.CHILD1);
            }else{
                intent.putExtra(FlightKeyPreference.isNewFlight, false);
                intent.putExtra("childTitle", (String) textContentChildFlight1.getTag(R.id.titleChild1));
                intent.putExtra("childTitleTemp", (String) textContentChildFlight1.getTag(R.id.titleTempChild1));
                intent.putExtra("childName", (String) textContentChildFlight1.getTag(R.id.nameChild1));
                intent.putExtra("childBorn", (String) textContentChildFlight1.getTag(R.id.bornChild1));
                intent.putExtra("childBornShow", (String) textContentChildFlight1.getTag(R.id.bornShowChild1));

                intent.putExtra("childNoPassport", (String) textContentChildFlight1.getTag(R.id.noPassportChild1));
                intent.putExtra("childNationality", (String) textContentChildFlight1.getTag(R.id.nasinatilyChild1));
                intent.putExtra("childNationalityKode",(String) textContentChildFlight1.getTag(R.id.nasinatilyKodeChild1));
                intent.putExtra("childIssuingCountry", (String) textContentChildFlight1.getTag(R.id.issuingCountryChild1));
                intent.putExtra("childIssuingCountryKode", (String) textContentChildFlight1.getTag(R.id.issuingCountryKodeChild1));

                intent.putExtra("childExpired", (String) textContentChildFlight1.getTag(R.id.expiredChild1));
                intent.putExtra("childExpiredShow", (String) textContentChildFlight1.getTag(R.id.expiredShowChild1));



                intent.putExtra("childCheckboxBeliBagasi", (int) textContentChildFlight1.getTag(R.id.isBuyChildBaggage1));
                intent.putExtra("childTitleBaggagePosition", (int) textContentChildFlight1.getTag(R.id.titleChildBaggagePosition1));
//            intent.putExtra("childTitleBaggage1", (String) textContentChildFlight1.getTag(R.id.titleChildBaggage1_1));
                intent.putExtra("titleBaggageTemp1", (String) textContentChildFlight1.getTag(R.id.titleTempChildBaggage1_1));
//            intent.putExtra("childTitleBaggage2", (String) textContentAdultFlight1.getTag(R.id.titleChildBaggage1_2));
                intent.putExtra("titleBaggageTemp2", (String) textContentAdultFlight1.getTag(R.id.titleTempChildBaggage1_2));
//            intent.putExtra("childTitleBaggage3", (String) textContentAdultFlight1.getTag(R.id.titleChildBaggage1_3));
                intent.putExtra("titleBaggageTemp3", (String) textContentAdultFlight1.getTag(R.id.titleTempChildBaggage1_3));
                startActivityForResult(intent, TravelActionCode.CHILD1);
            }
//        } else if (v.getId() == R.id.imageActionChildFlight2) {
        } else if (v.getId() == R.id.linContentChildFlight2) {
            if (textActionChildFlight2.getVisibility() == View.GONE) {
                intent.putExtra(FlightKeyPreference.isNewFlight, true);
                startActivityForResult(intent, TravelActionCode.CHILD2);
            }else{
                intent.putExtra(FlightKeyPreference.isNewFlight, false);
                intent.putExtra("childTitle", (String) textContentChildFlight2.getTag(R.id.titleChild2));
                intent.putExtra("childTitleTemp", (String) textContentChildFlight2.getTag(R.id.titleTempChild2));
                intent.putExtra("childName", (String) textContentChildFlight2.getTag(R.id.nameChild2));
                intent.putExtra("childBorn", (String) textContentChildFlight2.getTag(R.id.bornChild2));
                intent.putExtra("childBornShow", (String) textContentChildFlight2.getTag(R.id.bornShowChild2));

                intent.putExtra("childNoPassport", (String) textContentChildFlight2.getTag(R.id.noPassportChild2));
                intent.putExtra("childNationality", (String) textContentChildFlight2.getTag(R.id.nasinatilyChild2));
                intent.putExtra("childNationalityKode",(String) textContentChildFlight2.getTag(R.id.nasinatilyKodeChild2));
                intent.putExtra("childIssuingCountry", (String) textContentChildFlight2.getTag(R.id.issuingCountryChild2));
                intent.putExtra("childIssuingCountryKode", (String) textContentChildFlight2.getTag(R.id.issuingCountryKodeChild2));

                intent.putExtra("childExpired", (String) textContentChildFlight2.getTag(R.id.expiredChild2));
                intent.putExtra("childExpiredShow", (String) textContentChildFlight2.getTag(R.id.expiredShowChild2));


                intent.putExtra("childCheckboxBeliBagasi", (int) textContentChildFlight2.getTag(R.id.isBuyChildBaggage2));
                intent.putExtra("childTitleBaggagePosition", (int) textContentChildFlight2.getTag(R.id.titleChildBaggagePosition2));
//            intent.putExtra("childTitleBaggage1", (String) textContentChildFlight2.getTag(R.id.titleChildBaggage2_1));
                intent.putExtra("titleBaggageTemp1", (String) textContentChildFlight2.getTag(R.id.titleTempChildBaggage2_1));
//            intent.putExtra("childTitleBaggage2", (String) textContentAdultFlight2.getTag(R.id.titleChildBaggage2_2));
                intent.putExtra("titleBaggageTemp2", (String) textContentAdultFlight2.getTag(R.id.titleTempChildBaggage2_2));
//            intent.putExtra("childTitleBaggage3", (String) textContentAdultFlight2.getTag(R.id.titleChildBaggage2_3));
                intent.putExtra("titleBaggageTemp3", (String) textContentAdultFlight2.getTag(R.id.titleTempChildBaggage2_3));
                startActivityForResult(intent, TravelActionCode.CHILD2);
            }
//        } else if (v.getId() == R.id.imageActionChildFlight3) {
        } else if (v.getId() == R.id.linContentChildFlight3) {
            if (textActionChildFlight3.getVisibility() == View.GONE) {
                intent.putExtra(FlightKeyPreference.isNewFlight, true);
                startActivityForResult(intent, TravelActionCode.CHILD3);
            }else{
                intent.putExtra(FlightKeyPreference.isNewFlight, false);
                intent.putExtra("childTitle", (String) textContentChildFlight3.getTag(R.id.titleChild3));
                intent.putExtra("childTitleTemp", (String) textContentChildFlight3.getTag(R.id.titleTempChild3));
                intent.putExtra("childName", (String) textContentChildFlight3.getTag(R.id.nameChild3));
                intent.putExtra("childBorn", (String) textContentChildFlight3.getTag(R.id.bornChild3));
                intent.putExtra("childBornShow", (String) textContentChildFlight3.getTag(R.id.bornShowChild3));

                intent.putExtra("childNoPassport", (String) textContentChildFlight3.getTag(R.id.noPassportChild3));
                intent.putExtra("childNationality", (String) textContentChildFlight3.getTag(R.id.nasinatilyChild3));
                intent.putExtra("childNationalityKode",(String) textContentChildFlight3.getTag(R.id.nasinatilyKodeChild3));
                intent.putExtra("childIssuingCountry", (String) textContentChildFlight3.getTag(R.id.issuingCountryChild3));
                intent.putExtra("childIssuingCountryKode", (String) textContentChildFlight3.getTag(R.id.issuingCountryKodeChild3));

                intent.putExtra("childExpired", (String) textContentChildFlight3.getTag(R.id.expiredChild3));
                intent.putExtra("childExpiredShow", (String) textContentChildFlight3.getTag(R.id.expiredShowChild3));


                intent.putExtra("childCheckboxBeliBagasi", (int) textContentChildFlight3.getTag(R.id.isBuyChildBaggage3));
                intent.putExtra("childTitleBaggagePosition", (int) textContentChildFlight3.getTag(R.id.titleChildBaggagePosition3));
//            intent.putExtra("childTitleBaggage1", (String) textContentChildFlight3.getTag(R.id.titleChildBaggage3_1));
                intent.putExtra("titleBaggageTemp1", (String) textContentChildFlight3.getTag(R.id.titleTempChildBaggage3_1));
//            intent.putExtra("childTitleBaggage2", (String) textContentAdultFlight3.getTag(R.id.titleChildBaggage3_2));
                intent.putExtra("titleBaggageTemp2", (String) textContentAdultFlight3.getTag(R.id.titleTempChildBaggage3_2));
//            intent.putExtra("childTitleBaggage3", (String) textContentAdultFlight3.getTag(R.id.titleChildBaggage3_3));
                intent.putExtra("titleBaggageTemp3", (String) textContentAdultFlight3.getTag(R.id.titleTempChildBaggage3_3));
                startActivityForResult(intent, TravelActionCode.CHILD3);
            }
        } else if (v.getId() == R.id.linContentChildFlight4) {
            if (textActionChildFlight4.getVisibility() == View.GONE) {
                intent.putExtra(FlightKeyPreference.isNewFlight, true);
                startActivityForResult(intent, TravelActionCode.CHILD4);
            }else{
                intent.putExtra(FlightKeyPreference.isNewFlight, false);
                intent.putExtra("childTitle", (String) textContentChildFlight4.getTag(R.id.titleChild4));
                intent.putExtra("childTitleTemp", (String) textContentChildFlight4.getTag(R.id.titleTempChild4));
                intent.putExtra("childName", (String) textContentChildFlight4.getTag(R.id.nameChild4));
                intent.putExtra("childBorn", (String) textContentChildFlight4.getTag(R.id.bornChild4));
                intent.putExtra("childBornShow", (String) textContentChildFlight4.getTag(R.id.bornShowChild4));

                intent.putExtra("childNoPassport", (String) textContentChildFlight4.getTag(R.id.noPassportChild4));
                intent.putExtra("childNationality", (String) textContentChildFlight4.getTag(R.id.nasinatilyChild4));
                intent.putExtra("childNationalityKode",(String) textContentChildFlight4.getTag(R.id.nasinatilyKodeChild4));
                intent.putExtra("childIssuingCountry", (String) textContentChildFlight4.getTag(R.id.issuingCountryChild4));
                intent.putExtra("childIssuingCountryKode", (String) textContentChildFlight4.getTag(R.id.issuingCountryKodeChild4));

                intent.putExtra("childExpired", (String) textContentChildFlight4.getTag(R.id.expiredChild4));
                intent.putExtra("childExpiredShow", (String) textContentChildFlight4.getTag(R.id.expiredShowChild4));


                intent.putExtra("childCheckboxBeliBagasi", (int) textContentChildFlight4.getTag(R.id.isBuyChildBaggage4));
                intent.putExtra("childTitleBaggagePosition", (int) textContentChildFlight4.getTag(R.id.titleChildBaggagePosition4));
//            intent.putExtra("childTitleBaggage1", (String) textContentChildFlight4.getTag(R.id.titleChildBaggage4_1));
                intent.putExtra("titleBaggageTemp1", (String) textContentChildFlight4.getTag(R.id.titleTempChildBaggage4_1));
//            intent.putExtra("childTitleBaggage2", (String) textContentAdultFlight4.getTag(R.id.titleChildBaggage4_2));
                intent.putExtra("titleBaggageTemp2", (String) textContentAdultFlight4.getTag(R.id.titleTempChildBaggage4_2));
//            intent.putExtra("childTitleBaggage3", (String) textContentAdultFlight4.getTag(R.id.titleChildBaggage4_3));
                intent.putExtra("titleBaggageTemp3", (String) textContentAdultFlight4.getTag(R.id.titleTempChildBaggage4_3));
                startActivityForResult(intent, TravelActionCode.CHILD4);
            }
//        } else if (v.getId() == R.id.imageActionChildFlight5) {
        } else if (v.getId() == R.id.linContentChildFlight5) {
            if (textActionChildFlight5.getVisibility() == View.GONE) {
                intent.putExtra(FlightKeyPreference.isNewFlight, true);
                startActivityForResult(intent, TravelActionCode.CHILD5);
            }else{
                intent.putExtra(FlightKeyPreference.isNewFlight, false);
                intent.putExtra("childTitle", (String) textContentChildFlight5.getTag(R.id.titleChild5));
                intent.putExtra("childTitleTemp", (String) textContentChildFlight5.getTag(R.id.titleTempChild5));
                intent.putExtra("childName", (String) textContentChildFlight5.getTag(R.id.nameChild5));
                intent.putExtra("childBorn", (String) textContentChildFlight5.getTag(R.id.bornChild5));
                intent.putExtra("childBornShow", (String) textContentChildFlight5.getTag(R.id.bornShowChild5));

                intent.putExtra("childNoPassport", (String) textContentChildFlight5.getTag(R.id.noPassportChild5));
                intent.putExtra("childNationality", (String) textContentChildFlight5.getTag(R.id.nasinatilyChild5));
                intent.putExtra("childNationalityKode",(String) textContentChildFlight5.getTag(R.id.nasinatilyKodeChild5));
                intent.putExtra("childIssuingCountry", (String) textContentChildFlight5.getTag(R.id.issuingCountryChild5));
                intent.putExtra("childIssuingCountryKode", (String) textContentChildFlight5.getTag(R.id.issuingCountryKodeChild5));

                intent.putExtra("childExpired", (String) textContentChildFlight5.getTag(R.id.expiredChild5));
                intent.putExtra("childExpiredShow", (String) textContentChildFlight5.getTag(R.id.expiredShowChild5));


                intent.putExtra("childCheckboxBeliBagasi", (int) textContentChildFlight5.getTag(R.id.isBuyChildBaggage5));
                intent.putExtra("childTitleBaggagePosition", (int) textContentChildFlight5.getTag(R.id.titleChildBaggagePosition5));
//            intent.putExtra("childTitleBaggage1", (String) textContentChildFlight5.getTag(R.id.titleChildBaggage5_1));
                intent.putExtra("titleBaggageTemp1", (String) textContentChildFlight5.getTag(R.id.titleTempChildBaggage5_1));
//            intent.putExtra("childTitleBaggage2", (String) textContentAdultFlight5.getTag(R.id.titleChildBaggage5_2));
                intent.putExtra("titleBaggageTemp2", (String) textContentAdultFlight5.getTag(R.id.titleTempChildBaggage5_2));
//            intent.putExtra("childTitleBaggage3", (String) textContentAdultFlight5.getTag(R.id.titleChildBaggage5_3));
                intent.putExtra("titleBaggageTemp3", (String) textContentAdultFlight5.getTag(R.id.titleTempChildBaggage5_3));
                startActivityForResult(intent, TravelActionCode.CHILD5);
            }
//        } else if (v.getId() == R.id.imageActionChildFlight6) {
        } else if (v.getId() == R.id.linContentChildFlight6) {
            if (textActionChildFlight6.getVisibility() == View.GONE) {
                intent.putExtra(FlightKeyPreference.isNewFlight, true);
                startActivityForResult(intent, TravelActionCode.CHILD6);
            }else{
                intent.putExtra(FlightKeyPreference.isNewFlight, false);
                intent.putExtra("childTitle", (String) textContentChildFlight6.getTag(R.id.titleChild6));
                intent.putExtra("childTitleTemp", (String) textContentChildFlight6.getTag(R.id.titleTempChild6));
                intent.putExtra("childName", (String) textContentChildFlight6.getTag(R.id.nameChild6));
                intent.putExtra("childBorn", (String) textContentChildFlight6.getTag(R.id.bornChild6));
                intent.putExtra("childBornShow", (String) textContentChildFlight6.getTag(R.id.bornShowChild6));

                intent.putExtra("childNoPassport", (String) textContentChildFlight6.getTag(R.id.noPassportChild6));
                intent.putExtra("childNationality", (String) textContentChildFlight6.getTag(R.id.nasinatilyChild6));
                intent.putExtra("childNationalityKode",(String) textContentChildFlight6.getTag(R.id.nasinatilyKodeChild6));
                intent.putExtra("childIssuingCountry", (String) textContentChildFlight6.getTag(R.id.issuingCountryChild6));
                intent.putExtra("childIssuingCountryKode", (String) textContentChildFlight6.getTag(R.id.issuingCountryKodeChild6));

                intent.putExtra("childExpired", (String) textContentChildFlight6.getTag(R.id.expiredChild6));
                intent.putExtra("childExpiredShow", (String) textContentChildFlight6.getTag(R.id.expiredShowChild6));


                intent.putExtra("childCheckboxBeliBagasi", (int) textContentChildFlight6.getTag(R.id.isBuyChildBaggage6));
                intent.putExtra("childTitleBaggagePosition", (int) textContentChildFlight6.getTag(R.id.titleChildBaggagePosition6));
//            intent.putExtra("childTitleBaggage1", (String) textContentChildFlight6.getTag(R.id.titleChildBaggage6_1));
                intent.putExtra("titleBaggageTemp1", (String) textContentChildFlight6.getTag(R.id.titleTempChildBaggage6_1));
//            intent.putExtra("childTitleBaggage2", (String) textContentAdultFlight6.getTag(R.id.titleChildBaggage6_2));
                intent.putExtra("titleBaggageTemp2", (String) textContentAdultFlight6.getTag(R.id.titleTempChildBaggage6_2));
//            intent.putExtra("childTitleBaggage3", (String) textContentAdultFlight6.getTag(R.id.titleChildBaggage6_3));
                intent.putExtra("titleBaggageTemp3", (String) textContentAdultFlight6.getTag(R.id.titleTempChildBaggage6_3));
                startActivityForResult(intent, TravelActionCode.CHILD6);
            }
        } else if (v.getId() == R.id.linContentChildFlight7) {
            if (textActionChildFlight7.getVisibility() == View.GONE) {
                intent.putExtra(FlightKeyPreference.isNewFlight, true);
                startActivityForResult(intent, TravelActionCode.CHILD7);
            }else{
                intent.putExtra(FlightKeyPreference.isNewFlight, false);
                intent.putExtra("childTitle", (String) textContentChildFlight7.getTag(R.id.titleChild7));
                intent.putExtra("childTitleTemp", (String) textContentChildFlight7.getTag(R.id.titleTempChild7));
                intent.putExtra("childName", (String) textContentChildFlight7.getTag(R.id.nameChild7));
                intent.putExtra("childBorn", (String) textContentChildFlight7.getTag(R.id.bornChild7));
                intent.putExtra("childBornShow", (String) textContentChildFlight7.getTag(R.id.bornShowChild7));

                intent.putExtra("childNoPassport", (String) textContentChildFlight7.getTag(R.id.noPassportChild7));
                intent.putExtra("childNationality", (String) textContentChildFlight7.getTag(R.id.nasinatilyChild7));
                intent.putExtra("childNationalityKode",(String) textContentChildFlight7.getTag(R.id.nasinatilyKodeChild7));
                intent.putExtra("childIssuingCountry", (String) textContentChildFlight7.getTag(R.id.issuingCountryChild7));
                intent.putExtra("childIssuingCountryKode", (String) textContentChildFlight7.getTag(R.id.issuingCountryKodeChild7));

                intent.putExtra("childExpired", (String) textContentChildFlight7.getTag(R.id.expiredChild7));
                intent.putExtra("childExpiredShow", (String) textContentChildFlight7.getTag(R.id.expiredShowChild7));


                intent.putExtra("childCheckboxBeliBagasi", (int) textContentChildFlight7.getTag(R.id.isBuyChildBaggage7));
                intent.putExtra("childTitleBaggagePosition", (int) textContentChildFlight7.getTag(R.id.titleChildBaggagePosition7));
//            intent.putExtra("childTitleBaggage1", (String) textContentChildFlight7.getTag(R.id.titleChildBaggage7_1));
                intent.putExtra("titleBaggageTemp1", (String) textContentChildFlight7.getTag(R.id.titleTempChildBaggage7_1));
//            intent.putExtra("childTitleBaggage2", (String) textContentAdultFlight7.getTag(R.id.titleChildBaggage7_2));
                intent.putExtra("titleBaggageTemp2", (String) textContentAdultFlight7.getTag(R.id.titleTempChildBaggage7_2));
//            intent.putExtra("childTitleBaggage3", (String) textContentAdultFlight7.getTag(R.id.titleChildBaggage7_3));
                intent.putExtra("titleBaggageTemp3", (String) textContentAdultFlight7.getTag(R.id.titleTempChildBaggage7_3));
                startActivityForResult(intent, TravelActionCode.CHILD7);
            }    

//        } else if (v.getId() == R.id.imageActionChildFlight4) {

//        } else if (v.getId() == R.id.textActionChildFlight1) {

//        } else if (v.getId() == R.id.textActionChildFlight2) {

//        } else if (v.getId() == R.id.textActionChildFlight3) {

//        } else if (v.getId() == R.id.textActionChildFlight4) {

//        } else if (v.getId() == R.id.textActionChildFlight5) {

//        } else if (v.getId() == R.id.textActionChildFlight6) {

        }

    }

    public void openFormInfant(@NonNull View v) {
        Intent intent = new Intent(FlightBookActivity.this, FlightFormBookInfantActivity.class);
//        if (v.getId() == R.id.imageActionInfantFlight1) {
        if (v.getId() == R.id.linContentInfantFlight1) {
            if (textActionInfantFlight1.getVisibility() == View.GONE) {
                intent.putExtra(FlightKeyPreference.isNewFlight, true);
                intent.putExtra("nomor", 1);
                startActivityForResult(intent, TravelActionCode.INFANT1);
            }else{
                intent.putExtra(FlightKeyPreference.isNewFlight, false);
                intent.putExtra("nomor", 1);
                intent.putExtra("infantTitle", (String) textContentInfantFlight1.getTag(R.id.titleInfant1));
                intent.putExtra("infantTitleTemp", (String) textContentInfantFlight1.getTag(R.id.titleTempInfant1));
                intent.putExtra("infantName", (String) textContentInfantFlight1.getTag(R.id.nameInfant1));
                intent.putExtra("infantBorn", (String) textContentInfantFlight1.getTag(R.id.bornInfant1));
                intent.putExtra("infantBornShow", (String) textContentInfantFlight1.getTag(R.id.bornShowInfant1));

                intent.putExtra("infantNoPassport", (String) textContentInfantFlight1.getTag(R.id.noPassportInfant1));
                intent.putExtra("infantNationality", (String) textContentInfantFlight1.getTag(R.id.nasinatilyInfant1));
                intent.putExtra("infantNationalityKode",(String) textContentInfantFlight1.getTag(R.id.nasinatilyKodeInfant1));
                intent.putExtra("infantIssuingCountry", (String) textContentInfantFlight1.getTag(R.id.issuingCountryInfant1));
                intent.putExtra("infantIssuingCountryKode", (String) textContentInfantFlight1.getTag(R.id.issuingCountryKodeInfant1));

                intent.putExtra("infantExpired", (String) textContentInfantFlight1.getTag(R.id.expiredInfant1));
                intent.putExtra("infantExpiredShow", (String) textContentInfantFlight1.getTag(R.id.expiredShowInfant1));


                startActivityForResult(intent, TravelActionCode.INFANT1);
            }
//        } else if (v.getId() == R.id.imageActionInfantFlight2) {
        } else if (v.getId() == R.id.linContentInfantFlight2) {
            if (textActionInfantFlight2.getVisibility() == View.GONE) {
                intent.putExtra(FlightKeyPreference.isNewFlight, true);
                intent.putExtra("nomor", 2);
                startActivityForResult(intent, TravelActionCode.INFANT2);
            }else{
                intent.putExtra(FlightKeyPreference.isNewFlight, false);
                intent.putExtra("nomor", 2);
                intent.putExtra("infantTitle", (String) textContentInfantFlight2.getTag(R.id.titleInfant2));
                intent.putExtra("infantTitleTemp", (String) textContentInfantFlight2.getTag(R.id.titleTempInfant2));
                intent.putExtra("infantName", (String) textContentInfantFlight2.getTag(R.id.nameInfant2));
                intent.putExtra("infantBorn", (String) textContentInfantFlight2.getTag(R.id.bornInfant2));
                intent.putExtra("infantBornShow", (String) textContentInfantFlight2.getTag(R.id.bornShowInfant2));

                intent.putExtra("infantNoPassport", (String) textContentInfantFlight2.getTag(R.id.noPassportInfant2));
                intent.putExtra("infantNationality", (String) textContentInfantFlight2.getTag(R.id.nasinatilyInfant2));
                intent.putExtra("infantNationalityKode",(String) textContentInfantFlight2.getTag(R.id.nasinatilyKodeInfant2));
                intent.putExtra("infantIssuingCountry", (String) textContentInfantFlight2.getTag(R.id.issuingCountryInfant2));
                intent.putExtra("infantIssuingCountryKode", (String) textContentInfantFlight2.getTag(R.id.issuingCountryKodeInfant2));

                intent.putExtra("infantExpired", (String) textContentInfantFlight2.getTag(R.id.expiredInfant2));
                intent.putExtra("infantExpiredShow", (String) textContentInfantFlight2.getTag(R.id.expiredShowInfant2));

                startActivityForResult(intent, TravelActionCode.INFANT2);
            }
//        } else if (v.getId() == R.id.imageActionInfantFlight3) {
        } else if (v.getId() == R.id.linContentInfantFlight3) {
            if (textActionInfantFlight3.getVisibility() == View.GONE) {
                intent.putExtra(FlightKeyPreference.isNewFlight, true);
                intent.putExtra("nomor", 3);
                startActivityForResult(intent, TravelActionCode.INFANT3);
            }else{
                intent.putExtra(FlightKeyPreference.isNewFlight, false);
                intent.putExtra("nomor", 3);
                intent.putExtra("infantTitle", (String) textContentInfantFlight3.getTag(R.id.titleInfant3));
                intent.putExtra("infantTitleTemp", (String) textContentInfantFlight3.getTag(R.id.titleTempInfant3));
                intent.putExtra("infantName", (String) textContentInfantFlight3.getTag(R.id.nameInfant3));
                intent.putExtra("infantBorn", (String) textContentInfantFlight3.getTag(R.id.bornInfant3));
                intent.putExtra("infantBornShow", (String) textContentInfantFlight3.getTag(R.id.bornShowInfant3));

                intent.putExtra("infantNoPassport", (String) textContentInfantFlight3.getTag(R.id.noPassportInfant3));
                intent.putExtra("infantNationality", (String) textContentInfantFlight3.getTag(R.id.nasinatilyInfant3));
                intent.putExtra("infantNationalityKode",(String) textContentInfantFlight3.getTag(R.id.nasinatilyKodeInfant3));
                intent.putExtra("infantIssuingCountry", (String) textContentInfantFlight3.getTag(R.id.issuingCountryInfant3));
                intent.putExtra("infantIssuingCountryKode", (String) textContentInfantFlight3.getTag(R.id.issuingCountryKodeInfant3));

                intent.putExtra("infantExpired", (String) textContentInfantFlight3.getTag(R.id.expiredInfant3));
                intent.putExtra("infantExpiredShow", (String) textContentInfantFlight3.getTag(R.id.expiredShowInfant3));

                startActivityForResult(intent, TravelActionCode.INFANT3);
            }
//        } else if (v.getId() == R.id.imageActionInfantFlight4) {
        } else if (v.getId() == R.id.linContentInfantFlight4) {
            if (textActionInfantFlight4.getVisibility() == View.GONE) {
                intent.putExtra(FlightKeyPreference.isNewFlight, true);
                intent.putExtra("nomor", 4);
                startActivityForResult(intent, TravelActionCode.INFANT4);
            }else{
                intent.putExtra(FlightKeyPreference.isNewFlight, false);
                intent.putExtra("nomor", 4);
                intent.putExtra("infantTitle", (String) textContentInfantFlight4.getTag(R.id.titleInfant4));
                intent.putExtra("infantTitleTemp", (String) textContentInfantFlight4.getTag(R.id.titleTempInfant4));
                intent.putExtra("infantName", (String) textContentInfantFlight4.getTag(R.id.nameInfant4));
                intent.putExtra("infantBorn", (String) textContentInfantFlight4.getTag(R.id.bornInfant4));
                intent.putExtra("infantBornShow", (String) textContentInfantFlight4.getTag(R.id.bornShowInfant4));

                intent.putExtra("infantNoPassport", (String) textContentInfantFlight4.getTag(R.id.noPassportInfant4));
                intent.putExtra("infantNationality", (String) textContentInfantFlight4.getTag(R.id.nasinatilyInfant4));
                intent.putExtra("infantNationalityKode",(String) textContentInfantFlight4.getTag(R.id.nasinatilyKodeInfant4));
                intent.putExtra("infantIssuingCountry", (String) textContentInfantFlight4.getTag(R.id.issuingCountryInfant4));
                intent.putExtra("infantIssuingCountryKode", (String) textContentInfantFlight4.getTag(R.id.issuingCountryKodeInfant4));

                intent.putExtra("infantExpired", (String) textContentInfantFlight4.getTag(R.id.expiredInfant4));
                intent.putExtra("infantExpiredShow", (String) textContentInfantFlight4.getTag(R.id.expiredShowInfant4));

                startActivityForResult(intent, TravelActionCode.INFANT4);
            }

        } else if (v.getId() == R.id.linContentInfantFlight5) {
            if (textActionInfantFlight5.getVisibility() == View.GONE) {
                intent.putExtra(FlightKeyPreference.isNewFlight, true);
                intent.putExtra("nomor", 5);
                startActivityForResult(intent, TravelActionCode.INFANT5);
            }else{
                intent.putExtra(FlightKeyPreference.isNewFlight, false);
                intent.putExtra("nomor", 5);
                intent.putExtra("infantTitle", (String) textContentInfantFlight5.getTag(R.id.titleInfant5));
                intent.putExtra("infantTitleTemp", (String) textContentInfantFlight5.getTag(R.id.titleTempInfant5));
                intent.putExtra("infantName", (String) textContentInfantFlight5.getTag(R.id.nameInfant5));
                intent.putExtra("infantBorn", (String) textContentInfantFlight5.getTag(R.id.bornInfant5));
                intent.putExtra("infantBornShow", (String) textContentInfantFlight5.getTag(R.id.bornShowInfant5));

                intent.putExtra("infantNoPassport", (String) textContentInfantFlight5.getTag(R.id.noPassportInfant5));
                intent.putExtra("infantNationality", (String) textContentInfantFlight5.getTag(R.id.nasinatilyInfant5));
                intent.putExtra("infantNationalityKode",(String) textContentInfantFlight5.getTag(R.id.nasinatilyKodeInfant5));
                intent.putExtra("infantIssuingCountry", (String) textContentInfantFlight5.getTag(R.id.issuingCountryInfant5));
                intent.putExtra("infantIssuingCountryKode", (String) textContentInfantFlight5.getTag(R.id.issuingCountryKodeInfant5));

                intent.putExtra("infantExpired", (String) textContentInfantFlight5.getTag(R.id.expiredInfant5));
                intent.putExtra("infantExpiredShow", (String) textContentInfantFlight5.getTag(R.id.expiredShowInfant5));

                startActivityForResult(intent, TravelActionCode.INFANT5);
            }
        } else if (v.getId() == R.id.linContentInfantFlight6) {
            if (textActionInfantFlight6.getVisibility() == View.GONE) {
                intent.putExtra(FlightKeyPreference.isNewFlight, true);
                intent.putExtra("nomor", 6);
                startActivityForResult(intent, TravelActionCode.INFANT6);
            }else{
                intent.putExtra(FlightKeyPreference.isNewFlight, false);
                intent.putExtra("nomor", 6);
                intent.putExtra("infantTitle", (String) textContentInfantFlight6.getTag(R.id.titleInfant6));
                intent.putExtra("infantTitleTemp", (String) textContentInfantFlight6.getTag(R.id.titleTempInfant6));
                intent.putExtra("infantName", (String) textContentInfantFlight6.getTag(R.id.nameInfant6));
                intent.putExtra("infantBorn", (String) textContentInfantFlight6.getTag(R.id.bornInfant6));
                intent.putExtra("infantBornShow", (String) textContentInfantFlight6.getTag(R.id.bornShowInfant6));

                intent.putExtra("infantNoPassport", (String) textContentInfantFlight6.getTag(R.id.noPassportInfant6));
                intent.putExtra("infantNationality", (String) textContentInfantFlight6.getTag(R.id.nasinatilyInfant6));
                intent.putExtra("infantNationalityKode",(String) textContentInfantFlight6.getTag(R.id.nasinatilyKodeInfant6));
                intent.putExtra("infantIssuingCountry", (String) textContentInfantFlight6.getTag(R.id.issuingCountryInfant6));
                intent.putExtra("infantIssuingCountryKode", (String) textContentInfantFlight6.getTag(R.id.issuingCountryKodeInfant6));

                intent.putExtra("infantExpired", (String) textContentInfantFlight6.getTag(R.id.expiredInfant6));
                intent.putExtra("infantExpiredShow", (String) textContentInfantFlight6.getTag(R.id.expiredShowInfant6));

                startActivityForResult(intent, TravelActionCode.INFANT6);
            }
        } else if (v.getId() == R.id.linContentInfantFlight7) {
            if (textActionInfantFlight7.getVisibility() == View.GONE) {
                intent.putExtra(FlightKeyPreference.isNewFlight, true);
                intent.putExtra("nomor", 7);
                startActivityForResult(intent, TravelActionCode.INFANT7);
            }else{
                intent.putExtra(FlightKeyPreference.isNewFlight, false);
                intent.putExtra("nomor", 7);
                intent.putExtra("infantTitle", (String) textContentInfantFlight7.getTag(R.id.titleInfant7));
                intent.putExtra("infantTitleTemp", (String) textContentInfantFlight7.getTag(R.id.titleTempInfant7));
                intent.putExtra("infantName", (String) textContentInfantFlight7.getTag(R.id.nameInfant7));
                intent.putExtra("infantBorn", (String) textContentInfantFlight7.getTag(R.id.bornInfant7));
                intent.putExtra("infantBornShow", (String) textContentInfantFlight7.getTag(R.id.bornShowInfant7));

                intent.putExtra("infantNoPassport", (String) textContentInfantFlight7.getTag(R.id.noPassportInfant7));
                intent.putExtra("infantNationality", (String) textContentInfantFlight7.getTag(R.id.nasinatilyInfant7));
                intent.putExtra("infantNationalityKode",(String) textContentInfantFlight7.getTag(R.id.nasinatilyKodeInfant7));
                intent.putExtra("infantIssuingCountry", (String) textContentInfantFlight7.getTag(R.id.issuingCountryInfant7));
                intent.putExtra("infantIssuingCountryKode", (String) textContentInfantFlight7.getTag(R.id.issuingCountryKodeInfant7));

                intent.putExtra("infantExpired", (String) textContentInfantFlight7.getTag(R.id.expiredInfant7));
                intent.putExtra("infantExpiredShow", (String) textContentInfantFlight7.getTag(R.id.expiredShowInfant7));

                startActivityForResult(intent, TravelActionCode.INFANT7);
            }    
//        } else if (v.getId() == R.id.textActionInfantFlight1) {
//
//        } else if (v.getId() == R.id.textActionInfantFlight2) {

//        } else if (v.getId() == R.id.textActionInfantFlight3) {

//        } else if (v.getId() == R.id.textActionInfantFlight4) {

        }
        //  overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

    @NonNull
    public ArrayList<String> passengersList = new ArrayList();
    @NonNull
    public HashMap adultPassengersList = new HashMap();
    @NonNull
    public HashMap childPassengersList = new HashMap();
    @NonNull
    public HashMap infantPassengersList = new HashMap();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*
        * Format Data Penumpang Pesawat:
        ADT;[titlea];[firstnamea];[lastnamea];[birthdatea];[ida/passportnoa];::;::[phone];;;;[email];KTP;[nationalitya];[passportnationalitya];[passportExpiryDatea];[passportissueddatea];[passportissuinga];[dcheckinbaggagea1]|
        CHD;[titlec];[firstnamec];[lastnamec];[birthdatec];[idc/passportnoc];;;;;;;;[nationalityc];[passportnationalityc];[passportExpiryDatec];[passportissueddatec];[passportissuingc];[dcheckinbaggagec1]|
        INF;[titlei];[firstnamei];[lastnamei];[birthdatei];[idi/passportnoi];;;;;;;;[nationalityi];[passportnationalityi];[passportExpiryDatei];[passportissueddatei];[passportissuingi]|
        **/

        Log.d("BOOK", "onActivityResult: " + data);
        String initTitle = null;
        String fullName[];
        String firstName;
        String lastName;
        String noId = "1";
        String bornDate = "";
        String phone = "623187762";
        String mobilePhone = PreferenceClass.getUser().getNotelp_pemilik().replace("+62", "0");
        String email = "";
        String nasionalityId = "ID";
        String passportNasionalityId = "ID";
        String passportExpDate = "";
        String passportIssuedDate = "";
        String passportIssuingNasionality = "";
        String airLineCode = PreferenceClass.getString(FlightKeyPreference.airlineCode, "");

        if (resultCode == RESULT_OK) {
            if (requestCode == TravelActionCode.IS_FROM_PAY) {

//                if (isStillRunning) {
//                    RequestUtilsTravel.cancelTravel();
//                }
                //  Log.d(TAG, "onActivityResult: "+requestCode);
                //  Intent intent = new Intent(FlightBookActivity.this, FlightSearchActivity.class);


                if(data!=null && data.getAction()!=null) {
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

                onBackPressed();
            }else {


                switch (data.getStringExtra("status")) {
                    case "adult":
                        if (requestCode == TravelActionCode.ADULT1) {
                            imageViewExpandPassangerAdultFlight1.performClick();
                            cardPassangerAdultFlight1.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
                            //  panelPassangerAdultFlight1Detail.setBackgroundResource(R.drawable.card_primary);
                            if (!data.getBooleanExtra(FlightKeyPreference.isNewFlight, false)) {
                                adultPassengersList.remove("1");
                                adultPassanger[0] = null;
                                PreferenceClass.remove(FlightKeyPreference.baggageAdult1);
                            }
                            textContentAdultFlight1.setText(data.getStringExtra("adultTitleTemp") + " " + data.getStringExtra("adultName"));
                            textContentAdultFlight1.setTag(R.id.titleAdult1, data.getStringExtra("adultTitle"));
                            textContentAdultFlight1.setTag(R.id.titleTempAdult1, data.getStringExtra("adultTitleTemp"));
                            textContentAdultFlight1.setTag(R.id.nameAdult1, data.getStringExtra("adultName"));
                            PreferenceClass.putString(FlightKeyPreference.namaPenumpangAdult1,data.getStringExtra("adultName"));
                            textContentAdultFlight1.setTag(R.id.idAdult1, data.getStringExtra("adultId"));
                            textContentAdultFlight1.setTag(R.id.emailAdult1, data.getStringExtra("adultEmail"));
                            textContentAdultFlight1.setTag(R.id.mobilePhoneAdult1, data.getStringExtra("adultMobilePhone"));
                            textContentAdultFlight1.setTag(R.id.bornAdult1, data.getStringExtra("adultBorn"));
                            textContentAdultFlight1.setTag(R.id.bornShowAdult1, data.getStringExtra("adultBornShow"));

                            textContentAdultFlight1.setTag(R.id.noPassportAdult1, data.getStringExtra("adultNoPassport"));
                            textContentAdultFlight1.setTag(R.id.nasinatilyAdult1, data.getStringExtra("adultNationality"));
                            textContentAdultFlight1.setTag(R.id.nasinatilyKodeAdult1, data.getStringExtra("adultNationalityKode"));
                            textContentAdultFlight1.setTag(R.id.issuingCountryAdult1, data.getStringExtra("adultIssuingCountry"));
                            textContentAdultFlight1.setTag(R.id.issuingCountryKodeAdult1, data.getStringExtra("adultIssuingCountryKode"));
                            textContentAdultFlight1.setTag(R.id.expiredAdult1, data.getStringExtra("adultExpired"));
                            textContentAdultFlight1.setTag(R.id.expiredShowAdult1, data.getStringExtra("adultExpiredShow"));

                            textContentAdultFlight1.setTag(R.id.isBuyAdultBaggage1, data.getIntExtra("adultCheckboxBeliBagasi",0));


                            textContentAdultFlight1.setTag(R.id.titleAdultBaggagePosition1, data.getIntExtra("adultTitleBaggagePosition", 0));
//                    textContentAdultFlight1.setTag(R.id.titleAdultBaggage1_1, data.getStringExtra("adultTitleBaggage1"));
                            textContentAdultFlight1.setTag(R.id.titleTempAdultBaggage1_1, data.getStringExtra("titleBaggageTemp1"));
//                    textContentAdultFlight1.setTag(R.id.titleAdultBaggage1_2, data.getStringExtra("adultTitleBaggage2"));
                            textContentAdultFlight1.setTag(R.id.titleTempAdultBaggage1_2, data.getStringExtra("titleBaggageTemp2"));
//                    textContentAdultFlight1.setTag(R.id.titleAdultBaggage1_3, data.getStringExtra("adultTitleBaggage3"));
                            textContentAdultFlight1.setTag(R.id.titleTempAdultBaggage1_3, data.getStringExtra("titleBaggageTemp3"));
                            textContentAdultFlight1.setTag(R.id.adultBaggage1, data.getStringExtra("adultBaggage"));
                            String jsonArray = data.getStringExtra("adultBaggage");

                            try {

                                JSONArray array = new JSONArray(jsonArray);
                                if (array.length() > 0) {
                                    PreferenceClass.putJSONArray(FlightKeyPreference.baggageAdult1, array);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            if (!data.getStringExtra("adultEmail").isEmpty()) {
                                linEmailAdult1.setVisibility(View.VISIBLE);
                                textViewEmailAdult1.setText(data.getStringExtra("adultEmail"));
                            }

                            if (!data.getStringExtra("adultMobilePhone").isEmpty()) {
                                linMobileAdult1.setVisibility(View.VISIBLE);
                                textViewMobileAdult1.setText(data.getStringExtra("adultMobilePhone"));
                            }


                            if (!data.getStringExtra("adultBornShow").isEmpty()) {
                                bornDate = data.getStringExtra("adultBorn");
                                linTanggalLahirAdult1.setVisibility(View.VISIBLE);
                                textViewTanggalLahirAdult1.setText(data.getStringExtra("adultBornShow"));
                            }

                            if (!data.getStringExtra("adultId").isEmpty()) {
                                noId = data.getStringExtra("adultId");
                                linNoIdentitasAdult1.setVisibility(View.VISIBLE);
                                textViewNoIdentitasAdult1.setText(data.getStringExtra("adultId"));
                            }


                            if (!data.getStringExtra("adultNoPassport").isEmpty()) {
                                noId = data.getStringExtra("adultNoPassport");
                                linNoIdentitasAdult1.setVisibility(View.VISIBLE);
                                labelViewNoIdentitasAdultFlight1.setText("No Passport");
                                textViewNoIdentitasAdult1.setText(data.getStringExtra("adultNoPassport"));

                            }
                            if (!data.getStringExtra("adultNationality").isEmpty()) {
                                passportNasionalityId = data.getStringExtra("adultNationalityKode");

                            }
                            if (!data.getStringExtra("adultIssuingCountry").isEmpty()) {
                                passportIssuingNasionality = data.getStringExtra("adultIssuingCountryKode");

                            }
                            if (!data.getStringExtra("adultExpiredShow").isEmpty()) {
                                passportExpDate = data.getStringExtra("adultExpired");

                            }

                            switch (data.getStringExtra("adultTitleTemp")) {
                                case "Tn":
                                    initTitle = "MR";
                                    break;
                                case "Ny":
                                    initTitle = "MRS";
                                    break;
                                case "Nona":
                                    initTitle = "MISS";
                                    break;
                            }
                            String inputName = data.getStringExtra("adultName");
                            fullName = inputName.split(" ");
                            firstName = fullName[0];
                            if (fullName.length >= 1) {
                                lastName = inputName.replace(firstName + " ", "");
                            } else {
                                if (PreferenceClass.getString(FlightKeyPreference.airlineCode, "").equals("TPQG")) {
                                    lastName = initTitle;
                                } else {
                                    lastName = fullName[0];
                                }
                            }
                            email = data.getStringExtra("adultEmail");
                            mobilePhone = data.getStringExtra("adultMobilePhone");
                            passengersList.add(data.getStringExtra("adultTitleTemp") + ". " + firstName + " " + lastName);
                            adultPassengersList.put("1", data.getStringExtra("adultTitleTemp") + ". " + firstName + " " + lastName);
                            Log.d(TAG, "onActivityResult: ADT;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";" + noId + ";::" + phone + ";::" + mobilePhone + ";;;;" + email + ";KTP;" + nasionalityId + ";[passportnationalitya];[passportExpiryDatea];[passportissueddatea];[passportissuinga];[dcheckinbaggagea1]");
                            if (airLineCode.equals("TPJT")&& data.getStringExtra("adultNoPassport").isEmpty()) {
                                adultPassanger[0] = "ADT;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";" + noId + ";::" + phone + ";::" + mobilePhone + ";;;;" + email + ";KTP;" + nasionalityId + ";" + passportNasionalityId + ";";

                            } else {
                                adultPassanger[0] = "ADT;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";" + noId + ";::" + phone + ";::" + mobilePhone + ";;;;" + email + ";KTP;" + nasionalityId + ";" + passportNasionalityId + ";" + passportExpDate + ";" + passportIssuedDate + ";" + passportIssuingNasionality + ";" + bagageFromResponse;
                            }
                            textActionAdultFlight1.setVisibility(View.VISIBLE);
                            imageActionAdultFlight1.setVisibility(View.GONE);
                        //    imageViewExpandPassangerAdultFlight1.performClick();
                          //  panelPassangerAdultFlight1Detail.performClick();
                        } else if (requestCode == TravelActionCode.ADULT2) {// adult 2
                            imageViewExpandPassangerAdultFlight2.performClick();
                            cardPassangerAdultFlight2.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
                            // panelPassangerAdultFlight2Detail.setBackgroundResource(R.drawable.card_primary);
                            if (!data.getBooleanExtra(FlightKeyPreference.isNewFlight, false)) {
                                adultPassengersList.remove("2");
                                adultPassanger[1] = null;
                                PreferenceClass.remove(FlightKeyPreference.baggageAdult2);
                            }
                            //     Log.d(TAG, "onActivityResult adult 2: " + MemoryStore.get(this, "adultBorn") + " " + data.getStringExtra("adultBorn"));
                            textContentAdultFlight2.setText(data.getStringExtra("adultTitleTemp") + " " + data.getStringExtra("adultName"));
                            textContentAdultFlight2.setTag(R.id.titleAdult2, data.getStringExtra("adultTitle"));
                            textContentAdultFlight2.setTag(R.id.titleTempAdult2, data.getStringExtra("adultTitleTemp"));
                            textContentAdultFlight2.setTag(R.id.nameAdult2, data.getStringExtra("adultName"));
                            PreferenceClass.putString(FlightKeyPreference.namaPenumpangAdult2,data.getStringExtra("adultName"));
                            textContentAdultFlight2.setTag(R.id.idAdult2, data.getStringExtra("adultId"));
                            textContentAdultFlight2.setTag(R.id.emailAdult2, data.getStringExtra("adultEmail"));
                            textContentAdultFlight2.setTag(R.id.mobilePhoneAdult2, data.getStringExtra("adultMobilePhone"));
                            textContentAdultFlight2.setTag(R.id.bornAdult2, data.getStringExtra("adultBorn"));
                            textContentAdultFlight2.setTag(R.id.bornShowAdult2, data.getStringExtra("adultBornShow"));

                            textContentAdultFlight2.setTag(R.id.noPassportAdult2, data.getStringExtra("adultNoPassport"));
                            textContentAdultFlight2.setTag(R.id.nasinatilyAdult2, data.getStringExtra("adultNationality"));
                            textContentAdultFlight2.setTag(R.id.nasinatilyKodeAdult2, data.getStringExtra("adultNationalityKode"));
                            textContentAdultFlight2.setTag(R.id.issuingCountryAdult2, data.getStringExtra("adultIssuingCountry"));
                            textContentAdultFlight2.setTag(R.id.issuingCountryKodeAdult2, data.getStringExtra("adultIssuingCountryKode"));
                            textContentAdultFlight2.setTag(R.id.expiredAdult2, data.getStringExtra("adultExpired"));
                            textContentAdultFlight2.setTag(R.id.expiredShowAdult2, data.getStringExtra("adultExpiredShow"));
                            
                            textContentAdultFlight2.setTag(R.id.isBuyAdultBaggage2, data.getIntExtra("adultCheckboxBeliBagasi",0));

                            textContentAdultFlight2.setTag(R.id.titleAdultBaggagePosition2, data.getIntExtra("adultTitleBaggagePosition", 0));
//                    textContentAdultFlight2.setTag(R.id.titleAdultBaggage2_1, data.getStringExtra("adultTitleBaggage1"));
                            textContentAdultFlight2.setTag(R.id.titleTempAdultBaggage2_1, data.getStringExtra("titleBaggageTemp1"));
//                    textContentAdultFlight2.setTag(R.id.titleAdultBaggage2_2, data.getStringExtra("adultTitleBaggage2"));
                            textContentAdultFlight2.setTag(R.id.titleTempAdultBaggage2_2, data.getStringExtra("titleBaggageTemp2"));
//                    textContentAdultFlight2.setTag(R.id.titleAdultBaggage2_3, data.getStringExtra("adultTitleBaggage3"));
                            textContentAdultFlight2.setTag(R.id.titleTempAdultBaggage2_3, data.getStringExtra("titleBaggageTemp3"));
//                    textViewEmailAdult2.setText(data.getStringExtra("adultEmail"));
//                    textViewMobileAdult2.setText(data.getStringExtra("adultMobilePhone"));
                            String jsonArray = data.getStringExtra("adultBaggage");

                            try {
                                JSONArray array = new JSONArray(jsonArray);
                                if (array.length() > 0) {
                                    PreferenceClass.putJSONArray(FlightKeyPreference.baggageAdult2, array);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if (!data.getStringExtra("adultBornShow").isEmpty()) {
                                bornDate = data.getStringExtra("adultBorn");
                                linTanggalLahirAdult2.setVisibility(View.VISIBLE);
                                textViewTanggalLahirAdult2.setText(data.getStringExtra("adultBornShow"));
                            }

                            if (!data.getStringExtra("adultId").isEmpty()) {
                                noId = data.getStringExtra("adultId");
                                linNoIdentitasAdult2.setVisibility(View.VISIBLE);
                                textViewNoIdentitasAdult2.setText(data.getStringExtra("adultId"));
                            }

                            if (!data.getStringExtra("adultNoPassport").isEmpty()) {
                                noId = data.getStringExtra("adultNoPassport");

                                linNoIdentitasAdult2.setVisibility(View.VISIBLE);
                                labelViewNoIdentitasAdultFlight2.setText("No Passport");
                                textViewNoIdentitasAdult2.setText(data.getStringExtra("adultNoPassport"));

                            }
                            if (!data.getStringExtra("adultNationality").isEmpty()) {
                                passportNasionalityId = data.getStringExtra("adultNationalityKode");

                            }
                            if (!data.getStringExtra("adultIssuingCountry").isEmpty()) {
                                passportIssuingNasionality = data.getStringExtra("adultIssuingCountryKode");

                            }
                            if (!data.getStringExtra("adultExpired").isEmpty()) {
                                passportExpDate = data.getStringExtra("adultExpired");

                            }
                            
                            switch (data.getStringExtra("adultTitleTemp")) {
                                case "Tn":
                                    initTitle = "MR";
                                    break;
                                case "Ny":
                                    initTitle = "MRS";
                                    break;
                                case "Nona":
                                    initTitle = "MISS";
                                    break;
                            }
                            String inputName = data.getStringExtra("adultName");
                            fullName = inputName.split(" ");
                            firstName = fullName[0];

                            if (fullName.length >= 1) {

                                //lastName = fullName[1];//
                                lastName = inputName.replace(firstName + " ", "");

                            } else {
                                if (PreferenceClass.getString(FlightKeyPreference.airlineCode, "").equals("TPQG")) {
                                    lastName = initTitle;
                                } else {
                                    lastName = fullName[0];
                                }
                            }

                            email = data.getStringExtra("adultEmail");
                            mobilePhone = data.getStringExtra("adultMobilePhone");

                            passengersList.add(data.getStringExtra("adultTitleTemp") + ". " + firstName + " " + lastName);
                            adultPassengersList.put("2", data.getStringExtra("adultTitleTemp") + ". " + firstName + " " + lastName);
                            //    Log.d(TAG, "onActivityResult: ADT;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";" + noId + ";::"+phone+";::"+mobilePhone+";;;;;KTP;"+nasionalityId+";[passportnationalitya];[passportExpiryDatea];[passportissueddatea];[passportissuinga];[dcheckinbaggagea1]");
                            if (airLineCode.equals("TPJT") && data.getStringExtra("adultNoPassport").isEmpty()) {
                                adultPassanger[1] = "ADT;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";" + noId + ";::" + phone + ";::" + mobilePhone + ";;;;" + email + ";KTP;" + nasionalityId + ";" + passportNasionalityId + ";";
                            } else {
                                adultPassanger[1] = "ADT;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";" + noId + ";::" + phone + ";::" + mobilePhone + ";;;;" + email + ";KTP;" + nasionalityId + ";" + passportNasionalityId + ";" + passportExpDate + ";" + passportIssuedDate + ";" + passportIssuingNasionality + ";" + bagageFromResponse;
                            }

                            textActionAdultFlight2.setVisibility(View.VISIBLE);
                            imageActionAdultFlight2.setVisibility(View.GONE);
                            Log.d(TAG, "onActivityResult: " + passengersList.toString());
                        } else if (requestCode == TravelActionCode.ADULT3) {
                            imageViewExpandPassangerAdultFlight3.performClick();
                            cardPassangerAdultFlight3.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
//                    panelPassangerAdultFlight3Detail.setBackgroundResource(R.drawable.card_primary);
                            if (!data.getBooleanExtra(FlightKeyPreference.isNewFlight, false)) {
                                adultPassengersList.remove("3");
                                adultPassanger[2] = null;
                                PreferenceClass.remove(FlightKeyPreference.baggageAdult3);
                            }
                            textContentAdultFlight3.setText(data.getStringExtra("adultTitleTemp") + " " + data.getStringExtra("adultName"));
                            textContentAdultFlight3.setTag(R.id.titleAdult3, data.getStringExtra("adultTitle"));
                            textContentAdultFlight3.setTag(R.id.titleTempAdult3, data.getStringExtra("adultTitleTemp"));
                            textContentAdultFlight3.setTag(R.id.nameAdult3, data.getStringExtra("adultName"));
                            PreferenceClass.putString(FlightKeyPreference.namaPenumpangAdult3,data.getStringExtra("adultName"));
                            textContentAdultFlight3.setTag(R.id.idAdult3, data.getStringExtra("adultId"));
                            textContentAdultFlight3.setTag(R.id.emailAdult3, data.getStringExtra("adultEmail"));
                            textContentAdultFlight3.setTag(R.id.mobilePhoneAdult3, data.getStringExtra("adultMobilePhone"));
                            textContentAdultFlight3.setTag(R.id.bornAdult3, data.getStringExtra("adultBorn"));
                            textContentAdultFlight3.setTag(R.id.bornShowAdult3, data.getStringExtra("adultBornShow"));

                            textContentAdultFlight3.setTag(R.id.noPassportAdult3, data.getStringExtra("adultNoPassport"));
                            textContentAdultFlight3.setTag(R.id.nasinatilyAdult3, data.getStringExtra("adultNationality"));
                            textContentAdultFlight3.setTag(R.id.nasinatilyKodeAdult3, data.getStringExtra("adultNationalityKode"));
                            textContentAdultFlight3.setTag(R.id.issuingCountryAdult3, data.getStringExtra("adultIssuingCountry"));
                            textContentAdultFlight3.setTag(R.id.issuingCountryKodeAdult3, data.getStringExtra("adultIssuingCountryKode"));
                            textContentAdultFlight3.setTag(R.id.expiredAdult3, data.getStringExtra("adultExpired"));
                            textContentAdultFlight3.setTag(R.id.expiredShowAdult3, data.getStringExtra("adultExpiredShow"));
                            
                            textContentAdultFlight3.setTag(R.id.isBuyAdultBaggage3, data.getIntExtra("adultCheckboxBeliBagasi",0));

                            textContentAdultFlight3.setTag(R.id.titleAdultBaggagePosition3, data.getIntExtra("adultTitleBaggagePosition", 0));
//                    textContentAdultFlight3.setTag(R.id.titleAdultBaggage3_1, data.getStringExtra("adultTitleBaggage1"));
                            textContentAdultFlight3.setTag(R.id.titleTempAdultBaggage3_1, data.getStringExtra("titleBaggageTemp1"));
//                    textContentAdultFlight3.setTag(R.id.titleAdultBaggage3_2, data.getStringExtra("adultTitleBaggage2"));
                            textContentAdultFlight3.setTag(R.id.titleTempAdultBaggage3_2, data.getStringExtra("titleBaggageTemp2"));
//                    textContentAdultFlight3.setTag(R.id.titleAdultBaggage3_3, data.getStringExtra("adultTitleBaggage3"));
                            textContentAdultFlight3.setTag(R.id.titleTempAdultBaggage3_3, data.getStringExtra("titleBaggageTemp3"));

                            String jsonArray = data.getStringExtra("adultBaggage");

                            try {
                                JSONArray array = new JSONArray(jsonArray);
                                if (array.length() > 0) {
                                    PreferenceClass.putJSONArray(FlightKeyPreference.baggageAdult3, array);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
//                    textViewEmailAdult3.setText(data.getStringExtra("adultEmail"));
//                    textViewMobileAdult3.setText(data.getStringExtra("adultMobilePhone"));
                            if (!data.getStringExtra("adultBornShow").isEmpty()) {
                                bornDate = data.getStringExtra("adultBorn");
                                linTanggalLahirAdult3.setVisibility(View.VISIBLE);
                                textViewTanggalLahirAdult3.setText(data.getStringExtra("adultBornShow"));
                            }

                            if (!data.getStringExtra("adultId").isEmpty()) {
                                noId = data.getStringExtra("adultId");
                                linNoIdentitasAdult3.setVisibility(View.VISIBLE);
                                textViewNoIdentitasAdult3.setText(data.getStringExtra("adultId"));
                            }

                            if (!data.getStringExtra("adultNoPassport").isEmpty()) {
                                noId = data.getStringExtra("adultNoPassport");
                                linNoIdentitasAdult3.setVisibility(View.VISIBLE);
                                labelViewNoIdentitasAdultFlight3.setText("No Passport");
                                textViewNoIdentitasAdult3.setText(data.getStringExtra("adultNoPassport"));

                            }
                            if (!data.getStringExtra("adultNationality").isEmpty()) {
                                passportNasionalityId = data.getStringExtra("adultNationalityKode");

                            }
                            if (!data.getStringExtra("adultIssuingCountry").isEmpty()) {
                                passportIssuingNasionality = data.getStringExtra("adultIssuingCountryKode");

                            }
                            if (!data.getStringExtra("adultExpired").isEmpty()) {
                                passportExpDate = data.getStringExtra("adultExpired");

                            }
                            switch (data.getStringExtra("adultTitleTemp")) {
                                case "Tn":
                                    initTitle = "MR";
                                    break;
                                case "Ny":
                                    initTitle = "MRS";
                                    break;
                                case "Nona":
                                    initTitle = "MISS";
                                    break;
                            }
                            String inputName = data.getStringExtra("adultName");
                            fullName = inputName.split(" ");
                            firstName = fullName[0];
                            if (fullName.length >= 1) {
                                lastName = inputName.replace(firstName + " ", "");
                            } else {
                                if (PreferenceClass.getString(FlightKeyPreference.airlineCode, "").equals("TPQG")) {
                                    lastName = initTitle;
                                } else {
                                    lastName = fullName[0];
                                }
                            }
                            email = data.getStringExtra("adultEmail");
                            mobilePhone = data.getStringExtra("adultMobilePhone");
                            passengersList.add(data.getStringExtra("adultTitleTemp") + ". " + firstName + " " + lastName);
                            adultPassengersList.put("3", data.getStringExtra("adultTitleTemp") + ". " + firstName + " " + lastName);
                            //Log.d(TAG, "onActivityResult: ADT;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";" + noId + ";::" + phone + ";::" + mobilePhone + ";;;;;KTP;" + nasionalityId + ";[passportnationalitya];[passportExpiryDatea];[passportissueddatea];[passportissuinga];[dcheckinbaggagea1]");
                            if (airLineCode.equals("TPJT") && data.getStringExtra("adultNoPassport").isEmpty()) {
                                adultPassanger[2] = "ADT;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";" + noId + ";::" + phone + ";::" + mobilePhone + ";;;;" + email + ";KTP;" + nasionalityId + ";" + passportNasionalityId + ";";
                            } else {
                                adultPassanger[2] = "ADT;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";" + noId + ";::" + phone + ";::" + mobilePhone + ";;;;" + email + ";KTP;" + nasionalityId + ";" + passportNasionalityId + ";" + passportExpDate + ";" + passportIssuedDate + ";" + passportIssuingNasionality + ";" + bagageFromResponse;
                            }

                            textActionAdultFlight3.setVisibility(View.VISIBLE);
                            imageActionAdultFlight3.setVisibility(View.GONE);
                            Log.d(TAG, "onActivityResult: " + passengersList.toString());
                        } else if (requestCode == TravelActionCode.ADULT4) {// adult 4
                            imageViewExpandPassangerAdultFlight4.performClick();
                            cardPassangerAdultFlight4.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
//                    panelPassangerAdultFlight4Detail.setBackgroundResource(R.drawable.card_primary);
                            if (!data.getBooleanExtra(FlightKeyPreference.isNewFlight, false)) {
                                // if (passengersList.size() > 0) {
                                //         passengersList.remove(1);
                                //  }
                                adultPassengersList.remove("4");
                                adultPassanger[3] = null;
                                PreferenceClass.remove(FlightKeyPreference.baggageAdult4);
                            }
                            // Log.d(TAG, "onActivityResult adult 4: " + MemoryStore.get(this, "adultBorn") + " " + data.getStringExtra("adultBorn"));
                            textContentAdultFlight4.setText(data.getStringExtra("adultTitleTemp") + " " + data.getStringExtra("adultName"));
                            textContentAdultFlight4.setTag(R.id.titleAdult4, data.getStringExtra("adultTitle"));
                            textContentAdultFlight4.setTag(R.id.titleTempAdult4, data.getStringExtra("adultTitleTemp"));
                            textContentAdultFlight4.setTag(R.id.nameAdult4, data.getStringExtra("adultName"));
                            PreferenceClass.putString(FlightKeyPreference.namaPenumpangAdult4,data.getStringExtra("adultName"));
                            textContentAdultFlight4.setTag(R.id.idAdult4, data.getStringExtra("adultId"));
                            textContentAdultFlight4.setTag(R.id.emailAdult4, data.getStringExtra("adultEmail"));
                            textContentAdultFlight4.setTag(R.id.mobilePhoneAdult4, data.getStringExtra("adultMobilePhone"));
                            textContentAdultFlight4.setTag(R.id.bornAdult4, data.getStringExtra("adultBorn"));
                            textContentAdultFlight4.setTag(R.id.bornShowAdult4, data.getStringExtra("adultBornShow"));

                            textContentAdultFlight4.setTag(R.id.noPassportAdult4, data.getStringExtra("adultNoPassport"));
                            textContentAdultFlight4.setTag(R.id.nasinatilyAdult4, data.getStringExtra("adultNationality"));
                            textContentAdultFlight4.setTag(R.id.nasinatilyKodeAdult4, data.getStringExtra("adultNationalityKode"));
                            textContentAdultFlight4.setTag(R.id.issuingCountryAdult4, data.getStringExtra("adultIssuingCountry"));
                            textContentAdultFlight4.setTag(R.id.issuingCountryKodeAdult4, data.getStringExtra("adultIssuingCountryKode"));
                            textContentAdultFlight4.setTag(R.id.expiredAdult4, data.getStringExtra("adultExpired"));
                            textContentAdultFlight4.setTag(R.id.expiredShowAdult4, data.getStringExtra("adultExpiredShow"));
                            
                            textContentAdultFlight4.setTag(R.id.isBuyAdultBaggage4, data.getIntExtra("adultCheckboxBeliBagasi",0));

                            textContentAdultFlight4.setTag(R.id.titleAdultBaggagePosition4, data.getIntExtra("adultTitleBaggagePosition", 0));
//                    textContentAdultFlight4.setTag(R.id.titleAdultBaggage4_1, data.getStringExtra("adultTitleBaggage1"));
                            textContentAdultFlight4.setTag(R.id.titleTempAdultBaggage4_1, data.getStringExtra("titleBaggageTemp1"));
//                    textContentAdultFlight4.setTag(R.id.titleAdultBaggage4_2, data.getStringExtra("adultTitleBaggage2"));
                            textContentAdultFlight4.setTag(R.id.titleTempAdultBaggage4_2, data.getStringExtra("titleBaggageTemp2"));
//                    textContentAdultFlight4.setTag(R.id.titleAdultBaggage4_3, data.getStringExtra("adultTitleBaggage3"));
                            textContentAdultFlight4.setTag(R.id.titleTempAdultBaggage4_3, data.getStringExtra("titleBaggageTemp3"));

                            String jsonArray = data.getStringExtra("adultBaggage");

                            try {
                                JSONArray array = new JSONArray(jsonArray);
                                if (array.length() > 0) {
                                    PreferenceClass.putJSONArray(FlightKeyPreference.baggageAdult4, array);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
//                    textViewEmailAdult4.setText(data.getStringExtra("adultEmail"));
//                    textViewMobileAdult4.setText(data.getStringExtra("adultMobilePhone"));
                            if (!data.getStringExtra("adultBornShow").isEmpty()) {
                                bornDate = data.getStringExtra("adultBorn");
                                linTanggalLahirAdult4.setVisibility(View.VISIBLE);
                                textViewTanggalLahirAdult4.setText(data.getStringExtra("adultBornShow"));
                            }

                            if (!data.getStringExtra("adultId").isEmpty()) {
                                noId = data.getStringExtra("adultId");
                                linNoIdentitasAdult4.setVisibility(View.VISIBLE);
                                textViewNoIdentitasAdult4.setText(data.getStringExtra("adultId"));
                            }

                            if (!data.getStringExtra("adultNoPassport").isEmpty()) {
                                noId = data.getStringExtra("adultNoPassport");
                                linNoIdentitasAdult4.setVisibility(View.VISIBLE);
                                labelViewNoIdentitasAdultFlight4.setText("No Passport");
                                textViewNoIdentitasAdult4.setText(data.getStringExtra("adultNoPassport"));

                            }
                            if (!data.getStringExtra("adultNationality").isEmpty()) {
                                passportNasionalityId = data.getStringExtra("adultNationalityKode");

                            }
                            if (!data.getStringExtra("adultIssuingCountry").isEmpty()) {
                                passportIssuingNasionality = data.getStringExtra("adultIssuingCountryKode");

                            }
                            if (!data.getStringExtra("adultExpired").isEmpty()) {
                                passportExpDate = data.getStringExtra("adultExpired");

                            }
                            
                            switch (data.getStringExtra("adultTitleTemp")) {
                                case "Tn":
                                    initTitle = "MR";
                                    break;
                                case "Ny":
                                    initTitle = "MRS";
                                    break;
                                case "Nona":
                                    initTitle = "MISS";
                                    break;
                            }
                            String inputName = data.getStringExtra("adultName");
                            fullName = inputName.split(" ");
                            firstName = fullName[0];
                            if (fullName.length >= 1) {
                                lastName = inputName.replace(firstName + " ", "");
                            } else {
                                if (PreferenceClass.getString(FlightKeyPreference.airlineCode, "").equals("TPQG")) {
                                    lastName = initTitle;
                                } else {
                                    lastName = fullName[0];
                                }
                            }
                            email = data.getStringExtra("adultEmail");
                            mobilePhone = data.getStringExtra("adultMobilePhone");
                            passengersList.add(data.getStringExtra("adultTitleTemp") + ". " + firstName + " " + lastName);
                            adultPassengersList.put("4", data.getStringExtra("adultTitleTemp") + ". " + firstName + " " + lastName);
                            //Log.d(TAG, "onActivityResult: ADT;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";" + noId + ";::" + phone + ";::" + mobilePhone + ";;;;;KTP;" + nasionalityId + ";[passportnationalitya];[passportExpiryDatea];[passportissueddatea];[passportissuinga];[dcheckinbaggagea1]");
                            if (airLineCode.equals("TPJT") && data.getStringExtra("adultNoPassport").isEmpty()) {
                                adultPassanger[3] = "ADT;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";" + noId + ";::" + phone + ";::" + mobilePhone + ";;;;" + email + ";KTP;" + nasionalityId + ";" + passportNasionalityId + ";";
                            } else {
                                adultPassanger[3] = "ADT;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";" + noId + ";::" + phone + ";::" + mobilePhone + ";;;;" + email + ";KTP;" + nasionalityId + ";" + passportNasionalityId + ";" + passportExpDate + ";" + passportIssuedDate + ";" + passportIssuingNasionality + ";" + bagageFromResponse;
                            }
                            textActionAdultFlight4.setVisibility(View.VISIBLE);
                            imageActionAdultFlight4.setVisibility(View.GONE);
                            //Log.d(TAG, "onActivityResult: " + passengersList.toString());
                        } else if (requestCode == TravelActionCode.ADULT5) {// adult 5
                            imageViewExpandPassangerAdultFlight5.performClick();
                            cardPassangerAdultFlight5.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
//                    panelPassangerAdultFlight5Detail.setBackgroundResource(R.drawable.card_primary);
                            if (!data.getBooleanExtra(FlightKeyPreference.isNewFlight, false)) {
                                adultPassengersList.remove("5");
                                adultPassanger[4] = null;
                                PreferenceClass.remove(FlightKeyPreference.baggageAdult5);
                            }
                            //Log.d(TAG, "onActivityResult adult 5: " + MemoryStore.get(this, "adultBorn") + " " + data.getStringExtra("adultBorn"));
                            textContentAdultFlight5.setText(data.getStringExtra("adultTitleTemp") + " " + data.getStringExtra("adultName"));
                            textContentAdultFlight5.setTag(R.id.titleAdult5, data.getStringExtra("adultTitle"));
                            textContentAdultFlight5.setTag(R.id.titleTempAdult5, data.getStringExtra("adultTitleTemp"));
                            textContentAdultFlight5.setTag(R.id.nameAdult5, data.getStringExtra("adultName"));
                            PreferenceClass.putString(FlightKeyPreference.namaPenumpangAdult5,data.getStringExtra("adultName"));
                            textContentAdultFlight5.setTag(R.id.idAdult5, data.getStringExtra("adultId"));
                            textContentAdultFlight5.setTag(R.id.emailAdult5, data.getStringExtra("adultEmail"));
                            textContentAdultFlight5.setTag(R.id.mobilePhoneAdult5, data.getStringExtra("adultMobilePhone"));
                            textContentAdultFlight5.setTag(R.id.bornAdult5, data.getStringExtra("adultBorn"));
                            textContentAdultFlight5.setTag(R.id.bornShowAdult5, data.getStringExtra("adultBornShow"));


                            textContentAdultFlight5.setTag(R.id.noPassportAdult5, data.getStringExtra("adultNoPassport"));
                            textContentAdultFlight5.setTag(R.id.nasinatilyAdult5, data.getStringExtra("adultNationality"));
                            textContentAdultFlight5.setTag(R.id.nasinatilyKodeAdult5, data.getStringExtra("adultNationalityKode"));
                            textContentAdultFlight5.setTag(R.id.issuingCountryAdult5, data.getStringExtra("adultIssuingCountry"));
                            textContentAdultFlight5.setTag(R.id.issuingCountryKodeAdult5, data.getStringExtra("adultIssuingCountryKode"));
                            textContentAdultFlight5.setTag(R.id.expiredAdult5, data.getStringExtra("adultExpired"));
                            textContentAdultFlight5.setTag(R.id.expiredShowAdult5, data.getStringExtra("adultExpiredShow"));
                            
                            textContentAdultFlight5.setTag(R.id.isBuyAdultBaggage5, data.getIntExtra("adultCheckboxBeliBagasi",0));

                            textContentAdultFlight5.setTag(R.id.titleAdultBaggagePosition5, data.getIntExtra("adultTitleBaggagePosition", 0));
//                    textContentAdultFlight5.setTag(R.id.titleAdultBaggage5_1, data.getStringExtra("adultTitleBaggage1"));
                            textContentAdultFlight5.setTag(R.id.titleTempAdultBaggage5_1, data.getStringExtra("titleBaggageTemp1"));
//                    textContentAdultFlight5.setTag(R.id.titleAdultBaggage5_2, data.getStringExtra("adultTitleBaggage2"));
                            textContentAdultFlight5.setTag(R.id.titleTempAdultBaggage5_2, data.getStringExtra("titleBaggageTemp2"));
//                    textContentAdultFlight5.setTag(R.id.titleAdultBaggage5_3, data.getStringExtra("adultTitleBaggage3"));
                            textContentAdultFlight5.setTag(R.id.titleTempAdultBaggage5_3, data.getStringExtra("titleBaggageTemp3"));

                            String jsonArray = data.getStringExtra("adultBaggage");

                            try {
                                JSONArray array = new JSONArray(jsonArray);
                                if (array.length() > 0) {
                                    PreferenceClass.putJSONArray(FlightKeyPreference.baggageAdult5, array);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
//                    textViewEmailAdult5.setText(data.getStringExtra("adultEmail"));
//                    textViewMobileAdult5.setText(data.getStringExtra("adultMobilePhone"));
                            if (!data.getStringExtra("adultBornShow").isEmpty()) {
                                bornDate = data.getStringExtra("adultBorn");
                                linTanggalLahirAdult5.setVisibility(View.VISIBLE);
                                textViewTanggalLahirAdult5.setText(data.getStringExtra("adultBornShow"));
                            }

                            if (!data.getStringExtra("adultId").isEmpty()) {
                                noId = data.getStringExtra("adultId");
                                linNoIdentitasAdult5.setVisibility(View.VISIBLE);
                                textViewNoIdentitasAdult5.setText(data.getStringExtra("adultId"));
                            }

                            if (!data.getStringExtra("adultNoPassport").isEmpty()) {
                                noId = data.getStringExtra("adultNoPassport");
                                linNoIdentitasAdult5.setVisibility(View.VISIBLE);
                                labelViewNoIdentitasAdultFlight5.setText("No Passport");
                                textViewNoIdentitasAdult5.setText(data.getStringExtra("adultNoPassport"));

                            }
                            if (!data.getStringExtra("adultNationality").isEmpty()) {
                                passportNasionalityId = data.getStringExtra("adultNationalityKode");

                            }
                            if (!data.getStringExtra("adultIssuingCountry").isEmpty()) {
                                passportIssuingNasionality = data.getStringExtra("adultIssuingCountryKode");

                            }
                            if (!data.getStringExtra("adultExpired").isEmpty()) {
                                passportExpDate = data.getStringExtra("adultExpired");

                            }
                            switch (data.getStringExtra("adultTitleTemp")) {
                                case "Tn":
                                    initTitle = "MR";
                                    break;
                                case "Ny":
                                    initTitle = "MRS";
                                    break;
                                case "Nona":
                                    initTitle = "MISS";
                                    break;
                            }
                            String inputName = data.getStringExtra("adultName");
                            fullName = inputName.split(" ");
                            firstName = fullName[0];
                            if (fullName.length >= 1) {
                                lastName = inputName.replace(firstName + " ", "");
                            } else {
                                if (PreferenceClass.getString(FlightKeyPreference.airlineCode, "").equals("TPQG")) {
                                    lastName = initTitle;
                                } else {
                                    lastName = fullName[0];
                                }
                            }
                            email = data.getStringExtra("adultEmail");
                            mobilePhone = data.getStringExtra("adultMobilePhone");
                            passengersList.add(data.getStringExtra("adultTitleTemp") + ". " + firstName + " " + lastName);
                            adultPassengersList.put("5", data.getStringExtra("adultTitleTemp") + ". " + firstName + " " + lastName);
                            //Log.d(TAG, "onActivityResult: ADT;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";" + noId + ";::" + phone + ";::" + mobilePhone + ";;;;;KTP;" + nasionalityId + ";[passportnationalitya];[passportExpiryDatea];[passportissueddatea];[passportissuinga];[dcheckinbaggagea1]");
                            if (airLineCode.equals("TPJT") && data.getStringExtra("adultNoPassport").isEmpty()) {
                                adultPassanger[4] = "ADT;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";" + noId + ";::" + phone + ";::" + mobilePhone + ";;;;" + email + ";KTP;" + nasionalityId + ";" + passportNasionalityId + ";";
                            } else {
                                adultPassanger[4] = "ADT;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";" + noId + ";::" + phone + ";::" + mobilePhone + ";;;;" + email + ";KTP;" + nasionalityId + ";" + passportNasionalityId + ";" + passportExpDate + ";" + passportIssuedDate + ";" + passportIssuingNasionality + ";" + bagageFromResponse;
                            }
                            textActionAdultFlight5.setVisibility(View.VISIBLE);
                            imageActionAdultFlight5.setVisibility(View.GONE);
                            //Log.d(TAG, "onActivityResult: " + passengersList.toString());
                        } else if (requestCode == TravelActionCode.ADULT6) {
                            imageViewExpandPassangerAdultFlight6.performClick();
                            cardPassangerAdultFlight6.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
//                    panelPassangerAdultFlight6Detail.setBackgroundResource(R.drawable.card_primary);
                            if (!data.getBooleanExtra(FlightKeyPreference.isNewFlight, false)) {
                                adultPassengersList.remove("6");
                                adultPassanger[5] = null;
                                PreferenceClass.remove(FlightKeyPreference.baggageAdult6);
                            }
                            //Log.d(TAG, "onActivityResult adult 6: " + MemoryStore.get(this, "adultBorn") + " " + data.getStringExtra("adultBorn"));
                            textContentAdultFlight6.setText(data.getStringExtra("adultTitleTemp") + " " + data.getStringExtra("adultName"));
                            textContentAdultFlight6.setTag(R.id.titleAdult6, data.getStringExtra("adultTitle"));
                            textContentAdultFlight6.setTag(R.id.titleTempAdult6, data.getStringExtra("adultTitleTemp"));
                            textContentAdultFlight6.setTag(R.id.nameAdult6, data.getStringExtra("adultName"));
                            PreferenceClass.putString(FlightKeyPreference.namaPenumpangAdult6,data.getStringExtra("adultName"));
                            textContentAdultFlight6.setTag(R.id.idAdult6, data.getStringExtra("adultId"));
                            textContentAdultFlight6.setTag(R.id.emailAdult6, data.getStringExtra("adultEmail"));
                            textContentAdultFlight6.setTag(R.id.mobilePhoneAdult6, data.getStringExtra("adultMobilePhone"));
                            textContentAdultFlight6.setTag(R.id.bornAdult6, data.getStringExtra("adultBorn"));
                            textContentAdultFlight6.setTag(R.id.bornShowAdult6, data.getStringExtra("adultBornShow"));

                            textContentAdultFlight6.setTag(R.id.noPassportAdult6, data.getStringExtra("adultNoPassport"));
                            textContentAdultFlight6.setTag(R.id.nasinatilyAdult6, data.getStringExtra("adultNationality"));
                            textContentAdultFlight6.setTag(R.id.nasinatilyKodeAdult6, data.getStringExtra("adultNationalityKode"));
                            textContentAdultFlight6.setTag(R.id.issuingCountryAdult6, data.getStringExtra("adultIssuingCountry"));
                            textContentAdultFlight6.setTag(R.id.issuingCountryKodeAdult6, data.getStringExtra("adultIssuingCountryKode"));
                            textContentAdultFlight6.setTag(R.id.expiredAdult6, data.getStringExtra("adultExpired"));
                            textContentAdultFlight6.setTag(R.id.expiredShowAdult6, data.getStringExtra("adultExpiredShow"));
                            
                            
                            textContentAdultFlight6.setTag(R.id.isBuyAdultBaggage6, data.getIntExtra("adultCheckboxBeliBagasi",0));

                            textContentAdultFlight6.setTag(R.id.titleAdultBaggagePosition6, data.getIntExtra("adultTitleBaggagePosition", 0));
//                    textContentAdultFlight6.setTag(R.id.titleAdultBaggage6_1, data.getStringExtra("adultTitleBaggage1"));
                            textContentAdultFlight6.setTag(R.id.titleTempAdultBaggage6_1, data.getStringExtra("titleBaggageTemp1"));
//                    textContentAdultFlight6.setTag(R.id.titleAdultBaggage6_2, data.getStringExtra("adultTitleBaggage2"));
                            textContentAdultFlight6.setTag(R.id.titleTempAdultBaggage6_2, data.getStringExtra("titleBaggageTemp2"));
//                    textContentAdultFlight6.setTag(R.id.titleAdultBaggage6_3, data.getStringExtra("adultTitleBaggage3"));
                            textContentAdultFlight6.setTag(R.id.titleTempAdultBaggage6_3, data.getStringExtra("titleBaggageTemp3"));

                            String jsonArray = data.getStringExtra("adultBaggage");

                            try {
                                JSONArray array = new JSONArray(jsonArray);
                                if (array.length() > 0) {
                                    PreferenceClass.putJSONArray(FlightKeyPreference.baggageAdult6, array);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
//                    textViewEmailAdult6.setText(data.getStringExtra("adultEmail"));
//                    textViewMobileAdult6.setText(data.getStringExtra("adultMobilePhone"));
                            if (!data.getStringExtra("adultBornShow").isEmpty()) {
                                bornDate = data.getStringExtra("adultBorn");
                                linTanggalLahirAdult6.setVisibility(View.VISIBLE);
                                textViewTanggalLahirAdult6.setText(data.getStringExtra("adultBornShow"));
                            }

                            if (!data.getStringExtra("adultId").isEmpty()) {
                                noId = data.getStringExtra("adultId");
                                linNoIdentitasAdult6.setVisibility(View.VISIBLE);
                                textViewNoIdentitasAdult6.setText(data.getStringExtra("adultId"));
                            }

                            if (!data.getStringExtra("adultNoPassport").isEmpty()) {
                                noId = data.getStringExtra("adultNoPassport");
                                linNoIdentitasAdult6.setVisibility(View.VISIBLE);
                                labelViewNoIdentitasAdultFlight6.setText("No Passport");
                                textViewNoIdentitasAdult6.setText(data.getStringExtra("adultNoPassport"));

                            }
                            if (!data.getStringExtra("adultNationality").isEmpty()) {
                                passportNasionalityId = data.getStringExtra("adultNationalityKode");

                            }
                            if (!data.getStringExtra("adultIssuingCountry").isEmpty()) {
                                passportIssuingNasionality = data.getStringExtra("adultIssuingCountryKode");

                            }
                            if (!data.getStringExtra("adultExpired").isEmpty()) {
                                passportExpDate = data.getStringExtra("adultExpired");

                            }
                            switch (data.getStringExtra("adultTitleTemp")) {
                                case "Tn":
                                    initTitle = "MR";
                                    break;
                                case "Ny":
                                    initTitle = "MRS";
                                    break;
                                case "Nona":
                                    initTitle = "MISS";
                                    break;
                            }
                            String inputName = data.getStringExtra("adultName");
                            fullName = inputName.split(" ");
                            firstName = fullName[0];
                            if (fullName.length >= 1) {
                                lastName = inputName.replace(firstName + " ", "");
                            } else {
                                if (PreferenceClass.getString(FlightKeyPreference.airlineCode, "").equals("TPQG")) {
                                    lastName = initTitle;
                                } else {
                                    lastName = fullName[0];
                                }
                            }
                            email = data.getStringExtra("adultEmail");
                            mobilePhone = data.getStringExtra("adultMobilePhone");
                            passengersList.add(data.getStringExtra("adultTitleTemp") + ". " + firstName + " " + lastName);
                            adultPassengersList.put("6", data.getStringExtra("adultTitleTemp") + ". " + firstName + " " + lastName);
                            //Log.d(TAG, "onActivityResult: ADT;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";" + noId + ";::" + phone + ";::" + mobilePhone + ";;;;;KTP;" + nasionalityId + ";[passportnationalitya];[passportExpiryDatea];[passportissueddatea];[passportissuinga];[dcheckinbaggagea1]");
                            if (airLineCode.equals("TPJT") && data.getStringExtra("adultNoPassport").isEmpty()) {
                                adultPassanger[5] = "ADT;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";" + noId + ";::" + phone + ";::" + mobilePhone + ";;;;" + email + ";KTP;" + nasionalityId + ";" + passportNasionalityId + ";";
                            } else {
                                adultPassanger[5] = "ADT;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";" + noId + ";::" + phone + ";::" + mobilePhone + ";;;;" + email + ";KTP;" + nasionalityId + ";" + passportNasionalityId + ";" + passportExpDate + ";" + passportIssuedDate + ";" + passportIssuingNasionality + ";" + bagageFromResponse;
                            }
                            textActionAdultFlight6.setVisibility(View.VISIBLE);
                            imageActionAdultFlight6.setVisibility(View.GONE);
                            //Log.d(TAG, "onActivityResult: " + passengersList.toString());
                        } else if (requestCode == TravelActionCode.ADULT7) {// adult 7
                            imageViewExpandPassangerAdultFlight7.performClick();
                            cardPassangerAdultFlight7.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
//                    panelPassangerAdultFlight7Detail.setBackgroundResource(R.drawable.card_primary);
                            if (!data.getBooleanExtra(FlightKeyPreference.isNewFlight, false)) {
                                adultPassengersList.remove("7");
                                adultPassanger[6] = null;
                                PreferenceClass.remove(FlightKeyPreference.baggageAdult7);
                            }
                            //Log.d(TAG, "onActivityResult adult 7: " + MemoryStore.get(this, "adultBorn") + " " + data.getStringExtra("adultBorn"));
                            textContentAdultFlight7.setText(data.getStringExtra("adultTitleTemp") + " " + data.getStringExtra("adultName"));
                            textContentAdultFlight7.setTag(R.id.titleAdult7, data.getStringExtra("adultTitle"));
                            textContentAdultFlight7.setTag(R.id.titleTempAdult7, data.getStringExtra("adultTitleTemp"));
                            textContentAdultFlight7.setTag(R.id.nameAdult7, data.getStringExtra("adultName"));
                            PreferenceClass.putString(FlightKeyPreference.namaPenumpangAdult7,data.getStringExtra("adultName"));
                            textContentAdultFlight7.setTag(R.id.idAdult7, data.getStringExtra("adultId"));
                            textContentAdultFlight7.setTag(R.id.emailAdult7, data.getStringExtra("adultEmail"));
                            textContentAdultFlight7.setTag(R.id.mobilePhoneAdult7, data.getStringExtra("adultMobilePhone"));
                            textContentAdultFlight7.setTag(R.id.bornAdult7, data.getStringExtra("adultBorn"));
                            textContentAdultFlight7.setTag(R.id.bornShowAdult7, data.getStringExtra("adultBornShow"));

                            textContentAdultFlight7.setTag(R.id.noPassportAdult7, data.getStringExtra("adultNoPassport"));
                            textContentAdultFlight7.setTag(R.id.nasinatilyAdult7, data.getStringExtra("adultNationality"));
                            textContentAdultFlight7.setTag(R.id.nasinatilyKodeAdult7, data.getStringExtra("adultNationalityKode"));
                            textContentAdultFlight7.setTag(R.id.issuingCountryAdult7, data.getStringExtra("adultIssuingCountry"));
                            textContentAdultFlight7.setTag(R.id.issuingCountryKodeAdult7, data.getStringExtra("adultIssuingCountryKode"));
                            textContentAdultFlight7.setTag(R.id.expiredAdult7, data.getStringExtra("adultExpired"));
                            textContentAdultFlight7.setTag(R.id.expiredShowAdult7, data.getStringExtra("adultExpiredShow"));
                            
                            textContentAdultFlight7.setTag(R.id.isBuyAdultBaggage7, data.getIntExtra("adultCheckboxBeliBagasi",0));

                            textContentAdultFlight7.setTag(R.id.titleAdultBaggagePosition7, data.getIntExtra("adultTitleBaggagePosition", 0));
//                    textContentAdultFlight7.setTag(R.id.titleAdultBaggage7_1, data.getStringExtra("adultTitleBaggage1"));
                            textContentAdultFlight7.setTag(R.id.titleTempAdultBaggage7_1, data.getStringExtra("titleBaggageTemp1"));
//                    textContentAdultFlight7.setTag(R.id.titleAdultBaggage7_2, data.getStringExtra("adultTitleBaggage2"));
                            textContentAdultFlight7.setTag(R.id.titleTempAdultBaggage7_2, data.getStringExtra("titleBaggageTemp2"));
//                    textContentAdultFlight7.setTag(R.id.titleAdultBaggage7_3, data.getStringExtra("adultTitleBaggage3"));
                            textContentAdultFlight7.setTag(R.id.titleTempAdultBaggage7_3, data.getStringExtra("titleBaggageTemp3"));

                            String jsonArray = data.getStringExtra("adultBaggage");

                            try {
                                JSONArray array = new JSONArray(jsonArray);
                                if (array.length() > 0) {
                                    PreferenceClass.putJSONArray(FlightKeyPreference.baggageAdult7, array);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

//                    textViewEmailAdult7.setText(data.getStringExtra("adultEmail"));
//                    textViewMobileAdult7.setText(data.getStringExtra("adultMobilePhone"));
                            if (!data.getStringExtra("adultBornShow").isEmpty()) {
                                bornDate = data.getStringExtra("adultBorn");
                                linTanggalLahirAdult7.setVisibility(View.VISIBLE);
                                textViewTanggalLahirAdult7.setText(data.getStringExtra("adultBornShow"));
                            }

                            if (!data.getStringExtra("adultId").isEmpty()) {
                                noId = data.getStringExtra("adultId");
                                linNoIdentitasAdult7.setVisibility(View.VISIBLE);
                                textViewNoIdentitasAdult7.setText(data.getStringExtra("adultId"));
                            }

                            if (!data.getStringExtra("adultNoPassport").isEmpty()) {
                                noId = data.getStringExtra("adultNoPassport");
                                linNoIdentitasAdult7.setVisibility(View.VISIBLE);
                                labelViewNoIdentitasAdultFlight7.setText("No Passport");
                                textViewNoIdentitasAdult7.setText(data.getStringExtra("adultNoPassport"));

                            }
                            if (!data.getStringExtra("adultNationality").isEmpty()) {
                                passportNasionalityId = data.getStringExtra("adultNationalityKode");

                            }
                            if (!data.getStringExtra("adultIssuingCountry").isEmpty()) {
                                passportIssuingNasionality = data.getStringExtra("adultIssuingCountryKode");

                            }
                            if (!data.getStringExtra("adultExpired").isEmpty()) {
                                passportExpDate = data.getStringExtra("adultExpired");

                            }
                            switch (data.getStringExtra("adultTitleTemp")) {
                                case "Tn":
                                    initTitle = "MR";
                                    break;
                                case "Ny":
                                    initTitle = "MRS";
                                    break;
                                case "Nona":
                                    initTitle = "MISS";
                                    break;
                            }
                            String inputName = data.getStringExtra("adultName");
                            fullName = inputName.split(" ");
                            firstName = fullName[0];
                            if (fullName.length >= 1) {
                                lastName = inputName.replace(firstName + " ", "");
                            } else {
                                if (PreferenceClass.getString(FlightKeyPreference.airlineCode, "").equals("TPQG")) {
                                    lastName = initTitle;
                                } else {
                                    lastName = fullName[0];
                                }
                            }
                            email = data.getStringExtra("adultEmail");
                            mobilePhone = data.getStringExtra("adultMobilePhone");
                            passengersList.add(data.getStringExtra("adultTitleTemp") + ". " + firstName + " " + lastName);
                            adultPassengersList.put("7", data.getStringExtra("adultTitleTemp") + ". " + firstName + " " + lastName);
                            //Log.d(TAG, "onActivityResult: ADT;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";" + noId + ";::" + phone + ";::" + mobilePhone + ";;;;;KTP;" + nasionalityId + ";[passportnationalitya];[passportExpiryDatea];[passportissueddatea];[passportissuinga];[dcheckinbaggagea1]");
                            if (airLineCode.equals("TPJT") && data.getStringExtra("adultNoPassport").isEmpty()) {
                                adultPassanger[6] = "ADT;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";" + noId + ";::" + phone + ";::" + mobilePhone + ";;;;" + email + ";KTP;" + nasionalityId + ";" + passportNasionalityId + ";";
                            } else {
                                adultPassanger[6] = "ADT;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";" + noId + ";::" + phone + ";::" + mobilePhone + ";;;;" + email + ";KTP;" + nasionalityId + ";" + passportNasionalityId + ";" + passportExpDate + ";" + passportIssuedDate + ";" + passportIssuingNasionality + ";" + bagageFromResponse;
                            }
                            textActionAdultFlight7.setVisibility(View.VISIBLE);
                            imageActionAdultFlight7.setVisibility(View.GONE);
                            //Log.d(TAG, "onActivityResult: " + passengersList.toString());
                        }
                        break;
                    case "child": // CHILD
                        if (requestCode == TravelActionCode.CHILD1) {// child 1
                            imageViewExpandPassangerChildFlight1.performClick();
                            cardPassangerChildFlight1.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
//                    panelPassangerChildFlight1Detail.setBackgroundResource(R.drawable.card_primary);
                            if (!data.getBooleanExtra(FlightKeyPreference.isNewFlight, true)) {
                                childPassengersList.remove("1");
                                childPassanger[0] = null;
                                PreferenceClass.remove(FlightKeyPreference.baggageChild1);
                            }
                            //Log.d(TAG, "onActivityResult child 1: " + MemoryStore.get(this, "childBorn") + " " + data.getStringExtra("childBorn"));
                            textContentChildFlight1.setText(data.getStringExtra("childTitleTemp") + " " + data.getStringExtra("childName"));
                            textContentChildFlight1.setTag(R.id.titleChild1, data.getStringExtra("childTitle"));
                            textContentChildFlight1.setTag(R.id.titleTempChild1, data.getStringExtra("childTitleTemp"));
                            textContentChildFlight1.setTag(R.id.nameChild1, data.getStringExtra("childName"));
                            textContentChildFlight1.setTag(R.id.bornChild1, data.getStringExtra("childBorn"));
                            textContentChildFlight1.setTag(R.id.bornShowChild1, data.getStringExtra("childBornShow"));

                            textContentChildFlight1.setTag(R.id.noPassportChild1, data.getStringExtra("childNoPassport"));
                            textContentChildFlight1.setTag(R.id.nasinatilyChild1, data.getStringExtra("childNationality"));
                            textContentChildFlight1.setTag(R.id.nasinatilyKodeChild1, data.getStringExtra("childNationalityKode"));
                            textContentChildFlight1.setTag(R.id.issuingCountryChild1, data.getStringExtra("childIssuingCountry"));
                            textContentChildFlight1.setTag(R.id.issuingCountryKodeChild1, data.getStringExtra("childIssuingCountryKode"));
                            textContentChildFlight1.setTag(R.id.expiredChild1, data.getStringExtra("childExpired"));
                            textContentChildFlight1.setTag(R.id.expiredShowChild1, data.getStringExtra("childExpiredShow"));
                            
                            textContentChildFlight1.setTag(R.id.isBuyChildBaggage1, data.getIntExtra("childCheckboxBeliBagasi",0));

                            textContentChildFlight1.setTag(R.id.titleChildBaggagePosition1, data.getIntExtra("childTitleBaggagePosition", 0));
//                    textContentChildFlight1.setTag(R.id.titleChildBaggage1_1, data.getStringExtra("childTitleBaggage1"));
                            textContentChildFlight1.setTag(R.id.titleTempChildBaggage1_1, data.getStringExtra("titleBaggageTemp1"));
//                    textContentChildFlight1.setTag(R.id.titleChildBaggage1_2, data.getStringExtra("childTitleBaggage2"));
                            textContentChildFlight1.setTag(R.id.titleTempChildBaggage1_2, data.getStringExtra("titleBaggageTemp2"));
//                    textContentChildFlight1.setTag(R.id.titleChildBaggage1_3, data.getStringExtra("childTitleBaggage3"));
                            textContentChildFlight1.setTag(R.id.titleTempChildBaggage1_3, data.getStringExtra("titleBaggageTemp3"));
                            String jsonArray = data.getStringExtra("childBaggage");

                            try {
                                JSONArray array = new JSONArray(jsonArray);
                                if (array.length() > 0) {
                                    PreferenceClass.putJSONArray(FlightKeyPreference.baggageChild1, array);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if (!data.getStringExtra("childNoPassport").isEmpty()) {
                                noId = data.getStringExtra("childNoPassport");
                                lin_contentPassportChild1.setVisibility(View.VISIBLE);
                                textViewNoPassportChildFlight1.setText(noId);

                            }
                            if (!data.getStringExtra("childNationality").isEmpty()) {
                                passportNasionalityId = data.getStringExtra("childNationalityKode");

                            }
                            if (!data.getStringExtra("childIssuingCountry").isEmpty()) {
                                passportIssuingNasionality = data.getStringExtra("childIssuingCountryKode");

                            }
                            if (!data.getStringExtra("childExpiredShow").isEmpty()) {
                                passportExpDate = data.getStringExtra("childExpired");

                            }
                            
                            textViewTanggalLahirChild1.setText(data.getStringExtra("childBornShow"));
                            if (data.getStringExtra("childTitleTemp").equals("Tn")) {
                                initTitle = "MSTR";
                            } else if (data.getStringExtra("childTitleTemp").equals("Nona")) {
                                initTitle = "MISS";
                            }
                            String inputName = data.getStringExtra("childName");
                            fullName = inputName.split(" ");
                            firstName = fullName[0];
                            if (fullName.length >= 1) {
                                lastName = inputName.replace(firstName + " ", "");
                            } else {
                                if (PreferenceClass.getString(FlightKeyPreference.airlineCode, "").equals("TPQG")) {
                                    lastName = initTitle;
                                } else {
                                    lastName = fullName[0];
                                }
                            }
                            bornDate = data.getStringExtra("childBorn");
                            passengersList.add(data.getStringExtra("childTitleTemp") + ". " + firstName + " " + lastName);
                            childPassengersList.put("1", data.getStringExtra("childTitleTemp") + ". " + firstName + " " + lastName);
                            //Log.d(TAG, "onActivityResult: CHD;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1;;;;;;;;[nationalityc];[passportnationalityc];[passportExpiryDatec];[passportissueddatec];[passportissuingc];" + bagageFromResponse);
                            if (airLineCode.equals("TPJT") && data.getStringExtra("childNoPassport").isEmpty()) {
                                childPassanger[0] = "CHD;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1;;;;;;;;" + nasionalityId + ";" + passportNasionalityId + ";";
                            } else {
                                childPassanger[0] = "CHD;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";" + noId + ";;;;;;;;" + nasionalityId + ";" + passportNasionalityId + ";" + passportExpDate + ";" + passportIssuedDate + ";" + passportIssuingNasionality + ";" + bagageFromResponse;
                            }
                            textActionChildFlight1.setVisibility(View.VISIBLE);
                            imageActionChildFlight1.setVisibility(View.GONE);
                        } else if (requestCode == TravelActionCode.CHILD2) {
                            imageViewExpandPassangerChildFlight2.performClick();
                            cardPassangerChildFlight2.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
//                    panelPassangerChildFlight2Detail.setBackgroundResource(R.drawable.card_primary);
                            if (!data.getBooleanExtra(FlightKeyPreference.isNewFlight, false)) {
                                childPassengersList.remove("2");
                                childPassanger[1] = null;
                                PreferenceClass.remove(FlightKeyPreference.baggageChild2);
                            }
                            //Log.d(TAG, "onActivityResult child 2: " + MemoryStore.get(this, "childBorn") + " " + data.getStringExtra("childBorn"));
                            textContentChildFlight2.setText(data.getStringExtra("childTitleTemp") + " " + data.getStringExtra("childName"));
                            textContentChildFlight2.setTag(R.id.titleChild2, data.getStringExtra("childTitle"));
                            textContentChildFlight2.setTag(R.id.titleTempChild2, data.getStringExtra("childTitleTemp"));
                            textContentChildFlight2.setTag(R.id.nameChild2, data.getStringExtra("childName"));
                            textContentChildFlight2.setTag(R.id.bornChild2, data.getStringExtra("childBorn"));
                            textContentChildFlight2.setTag(R.id.bornShowChild2, data.getStringExtra("childBornShow"));

                            textContentChildFlight2.setTag(R.id.noPassportChild2, data.getStringExtra("childNoPassport"));
                            textContentChildFlight2.setTag(R.id.nasinatilyChild2, data.getStringExtra("childNationality"));
                            textContentChildFlight2.setTag(R.id.nasinatilyKodeChild2, data.getStringExtra("childNationalityKode"));
                            textContentChildFlight2.setTag(R.id.issuingCountryChild2, data.getStringExtra("childIssuingCountry"));
                            textContentChildFlight2.setTag(R.id.issuingCountryKodeChild2, data.getStringExtra("childIssuingCountryKode"));
                            textContentChildFlight2.setTag(R.id.expiredChild2, data.getStringExtra("childExpired"));
                            textContentChildFlight2.setTag(R.id.expiredShowChild2, data.getStringExtra("childExpiredShow"));
                            
                            textContentChildFlight2.setTag(R.id.isBuyChildBaggage2, data.getIntExtra("childCheckboxBeliBagasi",0));
                            textContentChildFlight2.setTag(R.id.titleChildBaggagePosition1, data.getIntExtra("childTitleBaggagePosition", 0));
//                    textContentChildFlight2.setTag(R.id.titleChildBaggage2_1, data.getStringExtra("childTitleBaggage1"));
                            textContentChildFlight2.setTag(R.id.titleTempChildBaggage2_1, data.getStringExtra("titleBaggageTemp1"));
//                    textContentChildFlight2.setTag(R.id.titleChildBaggage2_2, data.getStringExtra("childTitleBaggage2"));
                            textContentChildFlight2.setTag(R.id.titleTempChildBaggage2_2, data.getStringExtra("titleBaggageTemp2"));
//                    textContentChildFlight2.setTag(R.id.titleChildBaggage2_3, data.getStringExtra("childTitleBaggage3"));
                            textContentChildFlight2.setTag(R.id.titleTempChildBaggage2_3, data.getStringExtra("titleBaggageTemp3"));
                            String jsonArray = data.getStringExtra("childBaggage");

                            try {
                                JSONArray array = new JSONArray(jsonArray);
                                if (array.length() > 0) {
                                    PreferenceClass.putJSONArray(FlightKeyPreference.baggageChild2, array);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if (!data.getStringExtra("childNoPassport").isEmpty()) {
                                noId = data.getStringExtra("childNoPassport");
                                lin_contentPassportChild2.setVisibility(View.VISIBLE);
                                textViewNoPassportChildFlight2.setText(noId);

                            }
                            if (!data.getStringExtra("childNationality").isEmpty()) {
                                passportNasionalityId = data.getStringExtra("childNationalityKode");

                            }
                            if (!data.getStringExtra("childIssuingCountry").isEmpty()) {
                                passportIssuingNasionality = data.getStringExtra("childIssuingCountryKode");

                            }
                            if (!data.getStringExtra("childExpiredShow").isEmpty()) {
                                passportExpDate = data.getStringExtra("childExpired");

                            }

                            textViewTanggalLahirChild2.setText(data.getStringExtra("childBornShow"));
                            if (data.getStringExtra("childTitleTemp").equals("Tn")) {
                                initTitle = "MSTR";
                            } else if (data.getStringExtra("childTitleTemp").equals("Nona")) {
                                initTitle = "MISS";
                            }
                            String inputName = data.getStringExtra("childName");
                            fullName = inputName.split(" ");
                            firstName = fullName[0];
                            if (fullName.length >= 1) {
                                lastName = inputName.replace(firstName + " ", "");
                            } else {
                                if (PreferenceClass.getString(FlightKeyPreference.airlineCode, "").equals("TPQG")) {
                                    lastName = initTitle;
                                } else {
                                    lastName = fullName[0];
                                }
                            }
                            bornDate = data.getStringExtra("childBorn");
                            passengersList.add(data.getStringExtra("childTitleTemp") + ". " + firstName + " " + lastName);
                            childPassengersList.put("2", data.getStringExtra("childTitleTemp") + ". " + firstName + " " + lastName);
                            //Log.d(TAG, "onActivityResult: CHD;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1;;;;;;;;[nationalityc];[passportnationalityc];[passportExpiryDatec];[passportissueddatec];[passportissuingc];" + bagageFromResponse);
                            if (airLineCode.equals("TPJT") && data.getStringExtra("childNoPassport").isEmpty()) {
                                childPassanger[1] = "CHD;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1;;;;;;;;" + nasionalityId + ";" + passportNasionalityId + ";";
                            } else {
                                childPassanger[1] = "CHD;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";"+ noId +";;;;;;;;" + nasionalityId + ";" + passportNasionalityId + ";" + passportExpDate + ";" + passportIssuedDate + ";" + passportIssuingNasionality + ";" + bagageFromResponse;
                            }
                            textActionChildFlight2.setVisibility(View.VISIBLE);
                            imageActionChildFlight2.setVisibility(View.GONE);
                        } else if (requestCode == TravelActionCode.CHILD3) {// child 3
                            imageViewExpandPassangerChildFlight3.performClick();
                            cardPassangerChildFlight3.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
//                    panelPassangerChildFlight3Detail.setBackgroundResource(R.drawable.card_primary);
                            if (!data.getBooleanExtra(FlightKeyPreference.isNewFlight, false)) {
                                childPassengersList.remove("3");
                                childPassanger[2] = null;
                                PreferenceClass.remove(FlightKeyPreference.baggageChild3);
                            }
                            //Log.d(TAG, "onActivityResult child 3: " + MemoryStore.get(this, "childBorn") + " " + data.getStringExtra("childBorn"));
                            textContentChildFlight3.setText(data.getStringExtra("childTitleTemp") + " " + data.getStringExtra("childName"));
                            textContentChildFlight3.setTag(R.id.titleChild3, data.getStringExtra("childTitle"));
                            textContentChildFlight3.setTag(R.id.titleTempChild3, data.getStringExtra("childTitleTemp"));
                            textContentChildFlight3.setTag(R.id.nameChild3, data.getStringExtra("childName"));
                            textContentChildFlight3.setTag(R.id.bornChild3, data.getStringExtra("childBorn"));
                            textContentChildFlight3.setTag(R.id.bornShowChild3, data.getStringExtra("childBornShow"));

                            textContentChildFlight3.setTag(R.id.noPassportChild3, data.getStringExtra("childNoPassport"));
                            textContentChildFlight3.setTag(R.id.nasinatilyChild3, data.getStringExtra("childNationality"));
                            textContentChildFlight3.setTag(R.id.nasinatilyKodeChild3, data.getStringExtra("childNationalityKode"));
                            textContentChildFlight3.setTag(R.id.issuingCountryChild3, data.getStringExtra("childIssuingCountry"));
                            textContentChildFlight3.setTag(R.id.issuingCountryKodeChild3, data.getStringExtra("childIssuingCountryKode"));
                            textContentChildFlight3.setTag(R.id.expiredChild3, data.getStringExtra("childExpired"));
                            textContentChildFlight3.setTag(R.id.expiredShowChild3, data.getStringExtra("childExpiredShow"));
                            
                            textContentChildFlight3.setTag(R.id.isBuyChildBaggage3, data.getIntExtra("childCheckboxBeliBagasi",0));
                            textContentChildFlight3.setTag(R.id.titleChildBaggagePosition1, data.getIntExtra("childTitleBaggagePosition", 0));
//                    textContentChildFlight3.setTag(R.id.titleChildBaggage3_1, data.getStringExtra("childTitleBaggage1"));
                            textContentChildFlight3.setTag(R.id.titleTempChildBaggage3_1, data.getStringExtra("titleBaggageTemp1"));
//                    textContentChildFlight3.setTag(R.id.titleChildBaggage3_2, data.getStringExtra("childTitleBaggage2"));
                            textContentChildFlight3.setTag(R.id.titleTempChildBaggage3_2, data.getStringExtra("titleBaggageTemp2"));
//                    textContentChildFlight3.setTag(R.id.titleChildBaggage3_3, data.getStringExtra("childTitleBaggage3"));
                            textContentChildFlight3.setTag(R.id.titleTempChildBaggage3_3, data.getStringExtra("titleBaggageTemp3"));
                            String jsonArray = data.getStringExtra("childBaggage");

                            try {
                                JSONArray array = new JSONArray(jsonArray);
                                if (array.length() > 0) {
                                    PreferenceClass.putJSONArray(FlightKeyPreference.baggageChild3, array);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (!data.getStringExtra("childNoPassport").isEmpty()) {
                                noId = data.getStringExtra("childNoPassport");
                                lin_contentPassportChild3.setVisibility(View.VISIBLE);
                                textViewNoPassportChildFlight3.setText(noId);


                            }
                            if (!data.getStringExtra("childNationality").isEmpty()) {
                                passportNasionalityId = data.getStringExtra("childNationalityKode");

                            }
                            if (!data.getStringExtra("childIssuingCountry").isEmpty()) {
                                passportIssuingNasionality = data.getStringExtra("childIssuingCountryKode");

                            }
                            if (!data.getStringExtra("childExpiredShow").isEmpty()) {
                                passportExpDate = data.getStringExtra("childExpired");

                            }


                            textViewTanggalLahirChild3.setText(data.getStringExtra("childBornShow"));
                            if (data.getStringExtra("childTitleTemp").equals("Tn")) {
                                initTitle = "MSTR";
                            } else if (data.getStringExtra("childTitleTemp").equals("Nona")) {
                                initTitle = "MISS";
                            }
                            String inputName = data.getStringExtra("childName");
                            fullName = inputName.split(" ");
                            firstName = fullName[0];
                            if (fullName.length >= 1) {
                                lastName = inputName.replace(firstName + " ", "");
                            } else {
                                if (PreferenceClass.getString(FlightKeyPreference.airlineCode, "").equals("TPQG")) {
                                    lastName = initTitle;
                                } else {
                                    lastName = fullName[0];
                                }
                            }
                            bornDate = data.getStringExtra("childBorn");
                            passengersList.add(data.getStringExtra("childTitleTemp") + ". " + firstName + " " + lastName);
                            childPassengersList.put("3", data.getStringExtra("childTitleTemp") + ". " + firstName + " " + lastName);
                            //Log.d(TAG, "onActivityResult: CHD;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1;;;;;;;;[nationalityc];[passportnationalityc];[passportExpiryDatec];[passportissueddatec];[passportissuingc];" + bagageFromResponse);
                            if (airLineCode.equals("TPJT") && data.getStringExtra("childNoPassport").isEmpty()) {
                                childPassanger[2] = "CHD;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1;;;;;;;;" + nasionalityId + ";" + passportNasionalityId + ";";
                            } else {
                                childPassanger[2] = "CHD;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";"+ noId +";;;;;;;;" + nasionalityId + ";" + passportNasionalityId + ";" + passportExpDate + ";" + passportIssuedDate + ";" + passportIssuingNasionality + ";" + bagageFromResponse;
                            }
                            textActionChildFlight3.setVisibility(View.VISIBLE);
                            imageActionChildFlight3.setVisibility(View.GONE);
                        } else if (requestCode == TravelActionCode.CHILD4) {
                            imageViewExpandPassangerChildFlight4.performClick();
                            cardPassangerChildFlight4.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
//                    panelPassangerChildFlight4Detail.setBackgroundResource(R.drawable.card_primary);
                            if (!data.getBooleanExtra(FlightKeyPreference.isNewFlight, false)) {
                                childPassengersList.remove("4");
                                childPassanger[3] = null;
                                PreferenceClass.remove(FlightKeyPreference.baggageChild4);
                            }
                            //Log.d(TAG, "onActivityResult child 4: " + MemoryStore.get(this, "childBorn") + " " + data.getStringExtra("childBorn"));
                            textContentChildFlight4.setText(data.getStringExtra("childTitleTemp") + " " + data.getStringExtra("childName"));
                            textContentChildFlight4.setTag(R.id.titleChild4, data.getStringExtra("childTitle"));
                            textContentChildFlight4.setTag(R.id.titleTempChild4, data.getStringExtra("childTitleTemp"));
                            textContentChildFlight4.setTag(R.id.nameChild4, data.getStringExtra("childName"));
                            textContentChildFlight4.setTag(R.id.bornChild4, data.getStringExtra("childBorn"));
                            textContentChildFlight4.setTag(R.id.bornShowChild4, data.getStringExtra("childBornShow"));

                            textContentChildFlight4.setTag(R.id.noPassportChild4, data.getStringExtra("childNoPassport"));
                            textContentChildFlight4.setTag(R.id.nasinatilyChild4, data.getStringExtra("childNationality"));
                            textContentChildFlight4.setTag(R.id.nasinatilyKodeChild4, data.getStringExtra("childNationalityKode"));
                            textContentChildFlight4.setTag(R.id.issuingCountryChild4, data.getStringExtra("childIssuingCountry"));
                            textContentChildFlight4.setTag(R.id.issuingCountryKodeChild4, data.getStringExtra("childIssuingCountryKode"));
                            textContentChildFlight4.setTag(R.id.expiredChild4, data.getStringExtra("childExpired"));
                            textContentChildFlight4.setTag(R.id.expiredShowChild4, data.getStringExtra("childExpiredShow"));
                            
                            textContentChildFlight4.setTag(R.id.isBuyChildBaggage4, data.getIntExtra("childCheckboxBeliBagasi",0));
                            textContentChildFlight4.setTag(R.id.titleChildBaggagePosition4, data.getIntExtra("childTitleBaggagePosition", 0));
//                    textContentChildFlight4.setTag(R.id.titleChildBaggage4_1, data.getStringExtra("childTitleBaggage1"));
                            textContentChildFlight4.setTag(R.id.titleTempChildBaggage4_1, data.getStringExtra("titleBaggageTemp1"));
//                    textContentChildFlight4.setTag(R.id.titleChildBaggage4_2, data.getStringExtra("childTitleBaggage2"));
                            textContentChildFlight4.setTag(R.id.titleTempChildBaggage4_2, data.getStringExtra("titleBaggageTemp2"));
//                    textContentChildFlight4.setTag(R.id.titleChildBaggage4_3, data.getStringExtra("childTitleBaggage3"));
                            textContentChildFlight4.setTag(R.id.titleTempChildBaggage4_3, data.getStringExtra("titleBaggageTemp3"));
                            String jsonArray = data.getStringExtra("childBaggage");

                            try {
                                JSONArray array = new JSONArray(jsonArray);
                                if (array.length() > 0) {
                                    PreferenceClass.putJSONArray(FlightKeyPreference.baggageChild4, array);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (!data.getStringExtra("childNoPassport").isEmpty()) {
                                noId = data.getStringExtra("childNoPassport");
                                lin_contentPassportChild4.setVisibility(View.VISIBLE);
                                textViewNoPassportChildFlight4.setText(noId);


                            }
                            if (!data.getStringExtra("childNationality").isEmpty()) {
                                passportNasionalityId = data.getStringExtra("childNationalityKode");

                            }
                            if (!data.getStringExtra("childIssuingCountry").isEmpty()) {
                                passportIssuingNasionality = data.getStringExtra("childIssuingCountryKode");

                            }
                            if (!data.getStringExtra("childExpiredShow").isEmpty()) {
                                passportExpDate = data.getStringExtra("childExpired");

                            }


                            textViewTanggalLahirChild4.setText(data.getStringExtra("childBornShow"));
                            if (data.getStringExtra("childTitleTemp").equals("Tn")) {
                                initTitle = "MSTR";
                            } else if (data.getStringExtra("childTitleTemp").equals("Nona")) {
                                initTitle = "MISS";
                            }
                            String inputName = data.getStringExtra("childName");
                            fullName = inputName.split(" ");
                            firstName = fullName[0];
                            if (fullName.length >= 1) {
                                lastName = inputName.replace(firstName + " ", "");
                            } else {
                                if (PreferenceClass.getString(FlightKeyPreference.airlineCode, "").equals("TPQG")) {
                                    lastName = initTitle;
                                } else {
                                    lastName = fullName[0];
                                }
                            }
                            bornDate = data.getStringExtra("childBorn");
                            passengersList.add(data.getStringExtra("childTitleTemp") + ". " + firstName + " " + lastName);
                            childPassengersList.put("4", data.getStringExtra("childTitleTemp") + ". " + firstName + " " + lastName);
                            //Log.d(TAG, "onActivityResult: CHD;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1;;;;;;;;[nationalityc];[passportnationalityc];[passportExpiryDatec];[passportissueddatec];[passportissuingc];" + bagageFromResponse);
                            if (airLineCode.equals("TPJT") && data.getStringExtra("childNoPassport").isEmpty()) {
                                childPassanger[3] = "CHD;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1;;;;;;;;" + nasionalityId + ";" + passportNasionalityId + ";";
                            } else {
                                childPassanger[3] = "CHD;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";"+ noId +";;;;;;;;" + nasionalityId + ";" + passportNasionalityId + ";" + passportExpDate + ";" + passportIssuedDate + ";" + passportIssuingNasionality + ";" + bagageFromResponse;
                            }
                            textActionChildFlight4.setVisibility(View.VISIBLE);
                            imageActionChildFlight4.setVisibility(View.GONE);
                        } else if (requestCode == TravelActionCode.CHILD5) {// child 5
                            textViewTanggalLahirChild5.setText(data.getStringExtra("childBornShow"));
                            cardPassangerChildFlight5.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
//                    panelPassangerChildFlight5Detail.setBackgroundResource(R.drawable.card_primary);
                            if (!data.getBooleanExtra(FlightKeyPreference.isNewFlight, false)) {
                                childPassengersList.remove("5");
                                childPassanger[4] = null;
                                PreferenceClass.remove(FlightKeyPreference.baggageChild5);
                            }
                            //Log.d(TAG, "onActivityResult child 5: " + MemoryStore.get(this, "childBorn") + " " + data.getStringExtra("childBorn"));
                            textContentChildFlight5.setText(data.getStringExtra("childTitleTemp") + " " + data.getStringExtra("childName"));
                            textContentChildFlight5.setTag(R.id.titleChild5, data.getStringExtra("childTitle"));
                            textContentChildFlight5.setTag(R.id.titleTempChild5, data.getStringExtra("childTitleTemp"));
                            textContentChildFlight5.setTag(R.id.nameChild5, data.getStringExtra("childName"));
                            textContentChildFlight5.setTag(R.id.bornChild5, data.getStringExtra("childBorn"));
                            textContentChildFlight5.setTag(R.id.bornShowChild5, data.getStringExtra("childBornShow"));

                            textContentChildFlight5.setTag(R.id.noPassportChild5, data.getStringExtra("childNoPassport"));
                            textContentChildFlight5.setTag(R.id.nasinatilyChild5, data.getStringExtra("childNationality"));
                            textContentChildFlight5.setTag(R.id.nasinatilyKodeChild5, data.getStringExtra("childNationalityKode"));
                            textContentChildFlight5.setTag(R.id.issuingCountryChild5, data.getStringExtra("childIssuingCountry"));
                            textContentChildFlight5.setTag(R.id.issuingCountryKodeChild5, data.getStringExtra("childIssuingCountryKode"));
                            textContentChildFlight5.setTag(R.id.expiredChild5, data.getStringExtra("childExpired"));
                            textContentChildFlight5.setTag(R.id.expiredShowChild5, data.getStringExtra("childExpiredShow"));
                            
                            textContentChildFlight5.setTag(R.id.isBuyChildBaggage5, data.getIntExtra("childCheckboxBeliBagasi",0));
                            textContentChildFlight5.setTag(R.id.titleChildBaggagePosition5, data.getIntExtra("childTitleBaggagePosition", 0));
//                    textContentChildFlight5.setTag(R.id.titleChildBaggage5_1, data.getStringExtra("childTitleBaggage1"));
                            textContentChildFlight5.setTag(R.id.titleTempChildBaggage5_1, data.getStringExtra("titleBaggageTemp1"));
//                    textContentChildFlight5.setTag(R.id.titleChildBaggage5_2, data.getStringExtra("childTitleBaggage2"));
                            textContentChildFlight5.setTag(R.id.titleTempChildBaggage5_2, data.getStringExtra("titleBaggageTemp2"));
//                    textContentChildFlight5.setTag(R.id.titleChildBaggage5_3, data.getStringExtra("childTitleBaggage3"));
                            textContentChildFlight5.setTag(R.id.titleTempChildBaggage5_3, data.getStringExtra("titleBaggageTemp3"));
                            String jsonArray = data.getStringExtra("childBaggage");

                            try {
                                JSONArray array = new JSONArray(jsonArray);
                                if (array.length() > 0) {
                                    PreferenceClass.putJSONArray(FlightKeyPreference.baggageChild5, array);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if (!data.getStringExtra("childNoPassport").isEmpty()) {
                                noId = data.getStringExtra("childNoPassport");
                                lin_contentPassportChild5.setVisibility(View.VISIBLE);
                                textViewNoPassportChildFlight5.setText(noId);


                            }
                            if (!data.getStringExtra("childNationality").isEmpty()) {
                                passportNasionalityId = data.getStringExtra("childNationalityKode");

                            }
                            if (!data.getStringExtra("childIssuingCountry").isEmpty()) {
                                passportIssuingNasionality = data.getStringExtra("childIssuingCountryKode");

                            }
                            if (!data.getStringExtra("childExpiredShow").isEmpty()) {
                                passportExpDate = data.getStringExtra("childExpired");

                            }


                            textViewTanggalLahirChild5.setText(data.getStringExtra("childBornShow"));
                            if (data.getStringExtra("childTitleTemp").equals("Tn")) {
                                initTitle = "MSTR";
                            } else if (data.getStringExtra("childTitleTemp").equals("Nona")) {
                                initTitle = "MISS";
                            }
                            String inputName = data.getStringExtra("childName");
                            fullName = inputName.split(" ");
                            firstName = fullName[0];
                            if (fullName.length >= 1) {
                                lastName = inputName.replace(firstName + " ", "");
                            } else {
                                if (PreferenceClass.getString(FlightKeyPreference.airlineCode, "").equals("TPQG")) {
                                    lastName = initTitle;
                                } else {
                                    lastName = fullName[0];
                                }
                            }
                            bornDate = data.getStringExtra("childBorn");
                            passengersList.add(data.getStringExtra("childTitleTemp") + ". " + firstName + " " + lastName);
                            childPassengersList.put("5", data.getStringExtra("childTitleTemp") + ". " + firstName + " " + lastName);
                            //Log.d(TAG, "onActivityResult: CHD;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1;;;;;;;;[nationalityc];[passportnationalityc];[passportExpiryDatec];[passportissueddatec];[passportissuingc];" + bagageFromResponse);
                            if (airLineCode.equals("TPJT") && data.getStringExtra("childNoPassport").isEmpty()) {
                                childPassanger[4] = "CHD;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1;;;;;;;;" + nasionalityId + ";" + passportNasionalityId + ";";
                            } else {
                                childPassanger[4] = "CHD;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";"+ noId +";;;;;;;;" + nasionalityId + ";" + passportNasionalityId + ";" + passportExpDate + ";" + passportIssuedDate + ";" + passportIssuingNasionality + ";" + bagageFromResponse;
                            }
                            textActionChildFlight5.setVisibility(View.VISIBLE);
                            imageActionChildFlight5.setVisibility(View.GONE);
                        } else if (requestCode == TravelActionCode.CHILD6) {
                            imageViewExpandPassangerChildFlight6.performClick();
                            cardPassangerChildFlight6.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
//                    panelPassangerChildFlight6Detail.setBackgroundResource(R.drawable.card_primary);
                            if (!data.getBooleanExtra(FlightKeyPreference.isNewFlight, false)) {
                                childPassengersList.remove("6");
                                childPassanger[5] = null;
                                PreferenceClass.remove(FlightKeyPreference.baggageChild6);
                            }
                            //Log.d(TAG, "onActivityResult child 6: " + MemoryStore.get(this, "childBorn") + " " + data.getStringExtra("childBorn"));
                            textContentChildFlight6.setText(data.getStringExtra("childTitleTemp") + " " + data.getStringExtra("childName"));
                            textContentChildFlight6.setTag(R.id.titleChild6, data.getStringExtra("childTitle"));
                            textContentChildFlight6.setTag(R.id.titleTempChild6, data.getStringExtra("childTitleTemp"));
                            textContentChildFlight6.setTag(R.id.nameChild6, data.getStringExtra("childName"));
                            textContentChildFlight6.setTag(R.id.bornChild6, data.getStringExtra("childBorn"));
                            textContentChildFlight6.setTag(R.id.bornShowChild6, data.getStringExtra("childBornShow"));

                            textContentChildFlight6.setTag(R.id.noPassportChild6, data.getStringExtra("childNoPassport"));
                            textContentChildFlight6.setTag(R.id.nasinatilyChild6, data.getStringExtra("childNationality"));
                            textContentChildFlight6.setTag(R.id.nasinatilyKodeChild6, data.getStringExtra("childNationalityKode"));
                            textContentChildFlight6.setTag(R.id.issuingCountryChild6, data.getStringExtra("childIssuingCountry"));
                            textContentChildFlight6.setTag(R.id.issuingCountryKodeChild6, data.getStringExtra("childIssuingCountryKode"));
                            textContentChildFlight6.setTag(R.id.expiredChild6, data.getStringExtra("childExpired"));
                            textContentChildFlight6.setTag(R.id.expiredShowChild6, data.getStringExtra("childExpiredShow"));
                            
                            textContentChildFlight6.setTag(R.id.isBuyChildBaggage6, data.getIntExtra("childCheckboxBeliBagasi",0));
                            textContentChildFlight6.setTag(R.id.titleChildBaggagePosition6, data.getIntExtra("childTitleBaggagePosition", 0));
//                    textContentChildFlight6.setTag(R.id.titleChildBaggage6_1, data.getStringExtra("childTitleBaggage1"));
                            textContentChildFlight6.setTag(R.id.titleTempChildBaggage6_1, data.getStringExtra("titleBaggageTemp1"));
//                    textContentChildFlight6.setTag(R.id.titleChildBaggage6_2, data.getStringExtra("childTitleBaggage2"));
                            textContentChildFlight6.setTag(R.id.titleTempChildBaggage6_2, data.getStringExtra("titleBaggageTemp2"));
//                    textContentChildFlight6.setTag(R.id.titleChildBaggage6_3, data.getStringExtra("childTitleBaggage3"));
                            textContentChildFlight6.setTag(R.id.titleTempChildBaggage6_3, data.getStringExtra("titleBaggageTemp3"));
                            String jsonArray = data.getStringExtra("childBaggage");

                            try {
                                JSONArray array = new JSONArray(jsonArray);
                                if (array.length() > 0) {
                                    PreferenceClass.putJSONArray(FlightKeyPreference.baggageChild6, array);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if (!data.getStringExtra("childNoPassport").isEmpty()) {
                                noId = data.getStringExtra("childNoPassport");
                                lin_contentPassportChild6.setVisibility(View.VISIBLE);
                                textViewNoPassportChildFlight6.setText(noId);


                            }
                            if (!data.getStringExtra("childNationality").isEmpty()) {
                                passportNasionalityId = data.getStringExtra("childNationalityKode");

                            }
                            if (!data.getStringExtra("childIssuingCountry").isEmpty()) {
                                passportIssuingNasionality = data.getStringExtra("childIssuingCountryKode");

                            }
                            if (!data.getStringExtra("childExpiredShow").isEmpty()) {
                                passportExpDate = data.getStringExtra("childExpired");

                            }


                            textViewTanggalLahirChild6.setText(data.getStringExtra("childBornShow"));
                            if (data.getStringExtra("childTitleTemp").equals("Tn")) {
                                initTitle = "MSTR";
                            } else if (data.getStringExtra("childTitleTemp").equals("Nona")) {
                                initTitle = "MISS";
                            }
                            String inputName = data.getStringExtra("childName");
                            fullName = inputName.split(" ");
                            firstName = fullName[0];
                            if (fullName.length >= 1) {
                                lastName = inputName.replace(firstName + " ", "");
                            } else {
                                if (PreferenceClass.getString(FlightKeyPreference.airlineCode, "").equals("TPQG")) {
                                    lastName = initTitle;
                                } else {
                                    lastName = fullName[0];
                                }
                            }
                            bornDate = data.getStringExtra("childBorn");
                            passengersList.add(data.getStringExtra("childTitleTemp") + ". " + firstName + " " + lastName);
                            childPassengersList.put("6", data.getStringExtra("childTitleTemp") + ". " + firstName + " " + lastName);
                            //Log.d(TAG, "onActivityResult: CHD;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1;;;;;;;;[nationalityc];[passportnationalityc];[passportExpiryDatec];[passportissueddatec];[passportissuingc];" + bagageFromResponse);
                            if (airLineCode.equals("TPJT") && data.getStringExtra("childNoPassport").isEmpty()) {
                                childPassanger[5] = "CHD;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1;;;;;;;;" + nasionalityId + ";" + passportNasionalityId + ";";
                            } else {
                                childPassanger[5] = "CHD;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";"+ noId +";;;;;;;;" + nasionalityId + ";" + passportNasionalityId + ";" + passportExpDate + ";" + passportIssuedDate + ";" + passportIssuingNasionality + ";" + bagageFromResponse;
                            }
                            textActionChildFlight6.setVisibility(View.VISIBLE);
                            imageActionChildFlight6.setVisibility(View.GONE);
                        } else if (requestCode == TravelActionCode.CHILD7) {
                            imageViewExpandPassangerChildFlight7.performClick();
                            cardPassangerChildFlight7.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
//                    panelPassangerChildFlight7Detail.setBackgroundResource(R.drawable.card_primary);
                            if (!data.getBooleanExtra(FlightKeyPreference.isNewFlight, false)) {
                                childPassengersList.remove("7");
                                childPassanger[6] = null;
                                PreferenceClass.remove(FlightKeyPreference.baggageChild7);
                            }
                            //Log.d(TAG, "onActivityResult child 7: " + MemoryStore.get(this, "childBorn") + " " + data.getStringExtra("childBorn"));
                            textContentChildFlight7.setText(data.getStringExtra("childTitleTemp") + " " + data.getStringExtra("childName"));
                            textContentChildFlight7.setTag(R.id.titleChild7, data.getStringExtra("childTitle"));
                            textContentChildFlight7.setTag(R.id.titleTempChild7, data.getStringExtra("childTitleTemp"));
                            textContentChildFlight7.setTag(R.id.nameChild7, data.getStringExtra("childName"));
                            textContentChildFlight7.setTag(R.id.bornChild7, data.getStringExtra("childBorn"));
                            textContentChildFlight7.setTag(R.id.bornShowChild7, data.getStringExtra("childBornShow"));

                            textContentChildFlight7.setTag(R.id.noPassportChild7, data.getStringExtra("childNoPassport"));
                            textContentChildFlight7.setTag(R.id.nasinatilyChild7, data.getStringExtra("childNationality"));
                            textContentChildFlight7.setTag(R.id.nasinatilyKodeChild7, data.getStringExtra("childNationalityKode"));
                            textContentChildFlight7.setTag(R.id.issuingCountryChild7, data.getStringExtra("childIssuingCountry"));
                            textContentChildFlight7.setTag(R.id.issuingCountryKodeChild7, data.getStringExtra("childIssuingCountryKode"));
                            textContentChildFlight7.setTag(R.id.expiredChild7, data.getStringExtra("childExpired"));
                            textContentChildFlight7.setTag(R.id.expiredShowChild7, data.getStringExtra("childExpiredShow"));

                            textContentChildFlight7.setTag(R.id.isBuyChildBaggage7, data.getIntExtra("childCheckboxBeliBagasi",0));
                            textContentChildFlight7.setTag(R.id.titleChildBaggagePosition7, data.getIntExtra("childTitleBaggagePosition", 0));
//                    textContentChildFlight7.setTag(R.id.titleChildBaggage7_1, data.getStringExtra("childTitleBaggage1"));
                            textContentChildFlight7.setTag(R.id.titleTempChildBaggage7_1, data.getStringExtra("titleBaggageTemp1"));
//                    textContentChildFlight7.setTag(R.id.titleChildBaggage7_2, data.getStringExtra("childTitleBaggage2"));
                            textContentChildFlight7.setTag(R.id.titleTempChildBaggage7_2, data.getStringExtra("titleBaggageTemp2"));
//                    textContentChildFlight7.setTag(R.id.titleChildBaggage7_3, data.getStringExtra("childTitleBaggage3"));
                            textContentChildFlight7.setTag(R.id.titleTempChildBaggage7_3, data.getStringExtra("titleBaggageTemp3"));
                            String jsonArray = data.getStringExtra("childBaggage");

                            try {
                                JSONArray array = new JSONArray(jsonArray);
                                if (array.length() > 0) {
                                    PreferenceClass.putJSONArray(FlightKeyPreference.baggageChild7, array);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            if (!data.getStringExtra("childNoPassport").isEmpty()) {
                                noId = data.getStringExtra("childNoPassport");
                                lin_contentPassportChild7.setVisibility(View.VISIBLE);
                                textViewNoPassportChildFlight7.setText(noId);


                            }
                            if (!data.getStringExtra("childNationality").isEmpty()) {
                                passportNasionalityId = data.getStringExtra("childNationalityKode");

                            }
                            if (!data.getStringExtra("childIssuingCountry").isEmpty()) {
                                passportIssuingNasionality = data.getStringExtra("childIssuingCountryKode");

                            }
                            if (!data.getStringExtra("childExpiredShow").isEmpty()) {
                                passportExpDate = data.getStringExtra("childExpired");

                            }


                            textViewTanggalLahirChild7.setText(data.getStringExtra("childBornShow"));
                            if (data.getStringExtra("childTitleTemp").equals("Tn")) {
                                initTitle = "MSTR";
                            } else if (data.getStringExtra("childTitleTemp").equals("Nona")) {
                                initTitle = "MISS";
                            }
                            String inputName = data.getStringExtra("childName");
                            fullName = inputName.split(" ");
                            firstName = fullName[0];
                            if (fullName.length >= 1) {
                                lastName = inputName.replace(firstName + " ", "");
                            } else {
                                if (PreferenceClass.getString(FlightKeyPreference.airlineCode, "").equals("TPQG")) {
                                    lastName = initTitle;
                                } else {
                                    lastName = fullName[0];
                                }
                            }
                            bornDate = data.getStringExtra("childBorn");
                            passengersList.add(data.getStringExtra("childTitleTemp") + ". " + firstName + " " + lastName);
                            childPassengersList.put("7", data.getStringExtra("childTitleTemp") + ". " + firstName + " " + lastName);
                            //Log.d(TAG, "onActivityResult: CHD;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1;;;;;;;;[nationalityc];[passportnationalityc];[passportExpiryDatec];[passportissueddatec];[passportissuingc];" + bagageFromResponse);
                            if (airLineCode.equals("TPJT") && data.getStringExtra("childNoPassport").isEmpty()) {
                                childPassanger[6] = "CHD;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1;;;;;;;;" + nasionalityId + ";" + passportNasionalityId + ";";
                            } else {
                                childPassanger[6] = "CHD;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";"+ noId +";;;;;;;;" + nasionalityId + ";" + passportNasionalityId + ";" + passportExpDate + ";" + passportIssuedDate + ";" + passportIssuingNasionality + ";" + bagageFromResponse;
                            }
                            textActionChildFlight7.setVisibility(View.VISIBLE);
                            imageActionChildFlight7.setVisibility(View.GONE);    
                        }
                        break;
                    case "infant":
                        if (requestCode == TravelActionCode.INFANT1) {
                            imageViewExpandPassangerInfantFlight1.performClick();// 1
                            cardPassangerInfantFlight1.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
//                    panelPassangerInfantFlight1Detail.setBackgroundResource(R.drawable.card_primary);
                            if (!data.getBooleanExtra(FlightKeyPreference.isNewFlight, false)) {
                                infantPassengersList.remove("1");
                                infantPassanger[0] = null;
                            }
                            //Log.d(TAG, "onActivityResult infant 1: " + MemoryStore.get(this, "infantBorn") + " " + data.getStringExtra("infantBorn"));
                            textContentInfantFlight1.setText(data.getStringExtra("infantTitleTemp") + " " + data.getStringExtra("infantName"));
                            textContentInfantFlight1.setTag(R.id.titleInfant1, data.getStringExtra("infantTitle"));
                            textContentInfantFlight1.setTag(R.id.titleTempInfant1, data.getStringExtra("infantTitleTemp"));
                            textContentInfantFlight1.setTag(R.id.nameInfant1, data.getStringExtra("infantName"));
                            PreferenceClass.putString(FlightKeyPreference.namaPenumpangInfant1,data.getStringExtra("infantName").replace(" ",""));
                            textContentInfantFlight1.setTag(R.id.bornInfant1, data.getStringExtra("infantBorn"));
                            textContentInfantFlight1.setTag(R.id.bornShowInfant1, data.getStringExtra("infantBornShow"));

                            textContentInfantFlight1.setTag(R.id.noPassportInfant1, data.getStringExtra("childNoPassport"));
                            textContentInfantFlight1.setTag(R.id.nasinatilyInfant1, data.getStringExtra("childNationality"));
                            textContentInfantFlight1.setTag(R.id.nasinatilyKodeInfant1, data.getStringExtra("childNationalityKode"));
                            textContentInfantFlight1.setTag(R.id.issuingCountryInfant1, data.getStringExtra("childIssuingCountry"));
                            textContentInfantFlight1.setTag(R.id.issuingCountryKodeInfant1, data.getStringExtra("childIssuingCountryKode"));
                            textContentInfantFlight1.setTag(R.id.expiredInfant1, data.getStringExtra("childExpired"));
                            textContentInfantFlight1.setTag(R.id.expiredShowInfant1, data.getStringExtra("childExpiredShow"));

                            if (!data.getStringExtra("infantNoPassport").isEmpty()) {
                                noId = data.getStringExtra("infantNoPassport");
                                lin_contentPassportInfant1.setVisibility(View.VISIBLE);
                                textViewNoPassportInfantFlight1.setText(noId);


                            }
                            if (!data.getStringExtra("infantNationality").isEmpty()) {
                                passportNasionalityId = data.getStringExtra("infantNationalityKode");

                            }
                            if (!data.getStringExtra("infantIssuingCountry").isEmpty()) {
                                passportIssuingNasionality = data.getStringExtra("infantIssuingCountryKode");

                            }
                            if (!data.getStringExtra("infantExpiredShow").isEmpty()) {
                                passportExpDate = data.getStringExtra("infantExpired");

                            }


                            textViewTanggalLahirInfant1.setText(data.getStringExtra("infantBornShow").toString());
                            if (data.getStringExtra("infantTitleTemp").equals("Tn")) {
                                initTitle = "MSTR";
                            } else if (data.getStringExtra("infantTitleTemp").equals("Nona")) {
                                initTitle = "MISS";
                            }
                            String inputName = data.getStringExtra("infantName");
                            fullName = inputName.split(" ");
                            firstName = fullName[0];
                            if (fullName.length >= 1) {
                                lastName = inputName.replace(firstName + " ", "");
                            } else {
                                if (PreferenceClass.getString(FlightKeyPreference.airlineCode, "").equals("TPQG")) {
                                    lastName = initTitle;
                                } else {
                                    lastName = fullName[0];
                                }
                            }
                            bornDate = data.getStringExtra("infantBorn");
                            passengersList.add(data.getStringExtra("infantTitleTemp") + ". " + firstName + " " + lastName);
                            infantPassengersList.put("1", data.getStringExtra("infantTitleTemp") + ". " + firstName + " " + lastName);
                            //Log.d(TAG, "onActivityResult: INF;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1");
                            if (airLineCode.equals("TPJT") && data.getStringExtra("infantNoPassport").isEmpty() ) {
                                infantPassanger[0] = "INF;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1;;;;;;;;" + nasionalityId + ";" + passportNasionalityId + ";";
                            } else {
                                infantPassanger[0] = "INF;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";"+ noId +";;;;;;;;" + nasionalityId + ";" + passportNasionalityId + ";" + passportExpDate + ";" + passportIssuedDate + ";" + passportIssuingNasionality;
                            }
                            textActionInfantFlight1.setVisibility(View.VISIBLE);
                            imageActionInfantFlight1.setVisibility(View.GONE);
                        } else if (requestCode == TravelActionCode.INFANT2) {
                            imageViewExpandPassangerInfantFlight2.performClick();// 1
                            cardPassangerInfantFlight2.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
//                    panelPassangerInfantFlight2Detail.setBackgroundResource(R.drawable.card_primary);
                            if (!data.getBooleanExtra(FlightKeyPreference.isNewFlight, false)) {
                                infantPassengersList.remove("2");
                                infantPassanger[1] = null;
                            }
                            //Log.d(TAG, "onActivityResult infant 2: " + MemoryStore.get(this, "infantBorn") + " " + data.getStringExtra("infantBorn"));
                            textContentInfantFlight2.setText(data.getStringExtra("infantTitleTemp") + " " + data.getStringExtra("infantName"));
                            textContentInfantFlight2.setTag(R.id.titleInfant2, data.getStringExtra("infantTitle"));
                            textContentInfantFlight2.setTag(R.id.titleTempInfant2, data.getStringExtra("infantTitleTemp"));
                            textContentInfantFlight2.setTag(R.id.nameInfant2, data.getStringExtra("infantName"));
                            textContentInfantFlight2.setTag(R.id.bornInfant2, data.getStringExtra("infantBorn"));
                            textContentInfantFlight2.setTag(R.id.bornShowInfant2, data.getStringExtra("infantBornShow"));


                            textContentInfantFlight2.setTag(R.id.noPassportInfant2, data.getStringExtra("infantNoPassport"));
                            textContentInfantFlight2.setTag(R.id.nasinatilyInfant2, data.getStringExtra("infantNationality"));
                            textContentInfantFlight2.setTag(R.id.nasinatilyKodeInfant2, data.getStringExtra("infantNationalityKode"));
                            textContentInfantFlight2.setTag(R.id.issuingCountryInfant2, data.getStringExtra("infantIssuingCountry"));
                            textContentInfantFlight2.setTag(R.id.issuingCountryKodeInfant2, data.getStringExtra("infantIssuingCountryKode"));
                            textContentInfantFlight2.setTag(R.id.expiredInfant2, data.getStringExtra("infantExpired"));
                            textContentInfantFlight2.setTag(R.id.expiredShowInfant2, data.getStringExtra("infantExpiredShow"));

                            if (!data.getStringExtra("infantNoPassport").isEmpty()) {
                                noId = data.getStringExtra("infantNoPassport");
                                lin_contentPassportInfant2.setVisibility(View.VISIBLE);
                                textViewNoPassportInfantFlight2.setText(noId);


                            }
                            if (!data.getStringExtra("infantNationality").isEmpty()) {
                                passportNasionalityId = data.getStringExtra("infantNationalityKode");

                            }
                            if (!data.getStringExtra("infantIssuingCountry").isEmpty()) {
                                passportIssuingNasionality = data.getStringExtra("infantIssuingCountryKode");

                            }
                            if (!data.getStringExtra("infantExpiredShow").isEmpty()) {
                                passportExpDate = data.getStringExtra("infantExpired");

                            }
                            textViewTanggalLahirInfant2.setText(data.getStringExtra("infantBornShow"));
                            if (data.getStringExtra("infantTitleTemp").equals("Tn")) {
                                initTitle = "MSTR";
                            } else if (data.getStringExtra("infantTitleTemp").equals("Nona")) {
                                initTitle = "MISS";
                            }
                            String inputName = data.getStringExtra("infantName");
                            fullName = inputName.split(" ");
                            firstName = fullName[0];
                            if (fullName.length >= 1) {
                                lastName = inputName.replace(firstName + " ", "");
                            } else {
                                if (PreferenceClass.getString(FlightKeyPreference.airlineCode, "").equals("TPQG")) {
                                    lastName = initTitle;
                                } else {
                                    lastName = fullName[0];
                                }
                            }
                            bornDate = data.getStringExtra("infantBorn");
                            passengersList.add(data.getStringExtra("infantTitleTemp") + ". " + firstName + " " + lastName);
                            infantPassengersList.put("2", data.getStringExtra("infantTitleTemp") + ". " + firstName + " " + lastName);
                            //Log.d(TAG, "onActivityResult: INF;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1");
                            if (airLineCode.equals("TPJT") && data.getStringExtra("infantNoPassport").isEmpty() ) {
                                infantPassanger[1] = "INF;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1;;;;;;;;" + nasionalityId + ";" + passportNasionalityId + ";";
                            } else {
                                infantPassanger[1] = "INF;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";"+ noId +";;;;;;;;" + nasionalityId + ";" + passportNasionalityId + ";" + passportExpDate + ";" + passportIssuedDate + ";" + passportIssuingNasionality;
                            }
                            textActionInfantFlight2.setVisibility(View.VISIBLE);
                            imageActionInfantFlight2.setVisibility(View.GONE);
                        } else if (requestCode == TravelActionCode.INFANT3) {// infant 3
                            imageViewExpandPassangerInfantFlight3.performClick();// 1
                            cardPassangerInfantFlight3.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
//                    panelPassangerInfantFlight3Detail.setBackgroundResource(R.drawable.card_primary);
                            if (!data.getBooleanExtra(FlightKeyPreference.isNewFlight, false)) {
                                infantPassengersList.remove("3");
                                infantPassanger[2] = null;
                            }
                            //Log.d(TAG, "onActivityResult infant 3: " + MemoryStore.get(this, "infantBorn") + " " + data.getStringExtra("infantBorn"));
                            textContentInfantFlight3.setText(data.getStringExtra("infantTitleTemp") + " " + data.getStringExtra("infantName"));
                            textContentInfantFlight3.setTag(R.id.titleInfant3, data.getStringExtra("infantTitle"));
                            textContentInfantFlight3.setTag(R.id.titleTempInfant3, data.getStringExtra("infantTitleTemp"));
                            textContentInfantFlight3.setTag(R.id.nameInfant3, data.getStringExtra("infantName"));
                            textContentInfantFlight3.setTag(R.id.bornInfant3, data.getStringExtra("infantBorn"));
                            textContentInfantFlight3.setTag(R.id.bornShowInfant3, data.getStringExtra("infantBornShow"));

                            textContentInfantFlight3.setTag(R.id.noPassportInfant3, data.getStringExtra("infantNoPassport"));
                            textContentInfantFlight3.setTag(R.id.nasinatilyInfant3, data.getStringExtra("infantNationality"));
                            textContentInfantFlight3.setTag(R.id.nasinatilyKodeInfant3, data.getStringExtra("infantNationalityKode"));
                            textContentInfantFlight3.setTag(R.id.issuingCountryInfant3, data.getStringExtra("infantIssuingCountry"));
                            textContentInfantFlight3.setTag(R.id.issuingCountryKodeInfant3, data.getStringExtra("infantIssuingCountryKode"));
                            textContentInfantFlight3.setTag(R.id.expiredInfant3, data.getStringExtra("infantExpired"));
                            textContentInfantFlight3.setTag(R.id.expiredShowInfant3, data.getStringExtra("infantExpiredShow"));

                            if (!data.getStringExtra("infantNoPassport").isEmpty()) {
                                noId = data.getStringExtra("infantNoPassport");
                                lin_contentPassportInfant3.setVisibility(View.VISIBLE);
                                textViewNoPassportInfantFlight3.setText(noId);

                            }
                            if (!data.getStringExtra("infantNationality").isEmpty()) {
                                passportNasionalityId = data.getStringExtra("infantNationalityKode");

                            }
                            if (!data.getStringExtra("infantIssuingCountry").isEmpty()) {
                                passportIssuingNasionality = data.getStringExtra("infantIssuingCountryKode");

                            }
                            if (!data.getStringExtra("infantExpiredShow").isEmpty()) {
                                passportExpDate = data.getStringExtra("infantExpired");

                            }

                            textViewTanggalLahirInfant3.setText(data.getStringExtra("infantBornShow"));
                            if (data.getStringExtra("infantTitleTemp").equals("Tn")) {
                                initTitle = "MSTR";
                            } else if (data.getStringExtra("infantTitleTemp").equals("Nona")) {
                                initTitle = "MISS";
                            }
                            String inputName = data.getStringExtra("infantName");
                            fullName = inputName.split(" ");
                            firstName = fullName[0];
                            if (fullName.length >= 1) {
                                lastName = inputName.replace(firstName + " ", "");
                            } else {
                                if (PreferenceClass.getString(FlightKeyPreference.airlineCode, "").equals("TPQG")) {
                                    lastName = initTitle;
                                } else {
                                    lastName = fullName[0];
                                }
                            }
                            bornDate = data.getStringExtra("infantBorn");
                            passengersList.add(data.getStringExtra("infantTitleTemp") + ". " + firstName + " " + lastName);
                            infantPassengersList.put("3", data.getStringExtra("infantTitleTemp") + ". " + firstName + " " + lastName);
                            //Log.d(TAG, "onActivityResult: INF;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1");
                            if (airLineCode.equals("TPJT") && data.getStringExtra("infantNoPassport").isEmpty() ) {
                                infantPassanger[2] = "INF;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1;;;;;;;;" + nasionalityId + ";" + passportNasionalityId + ";";
                            } else {
                                infantPassanger[2] = "INF;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";"+ noId +";;;;;;;;" + nasionalityId + ";" + passportNasionalityId + ";" + passportExpDate + ";" + passportIssuedDate + ";" + passportIssuingNasionality;
                            }
                            textActionInfantFlight3.setVisibility(View.VISIBLE);
                            imageActionInfantFlight3.setVisibility(View.GONE);
                        } else if (requestCode == TravelActionCode.INFANT4) {
                            imageViewExpandPassangerInfantFlight4.performClick();// 1
                            cardPassangerInfantFlight4.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
//                    panelPassangerInfantFlight4Detail.setBackgroundResource(R.drawable.card_primary);
                            if (!data.getBooleanExtra(FlightKeyPreference.isNewFlight, false)) {
                                infantPassengersList.remove("4");
                                infantPassanger[3] = null;
                            }
                            //Log.d(TAG, "onActivityResult infant 4: " + MemoryStore.get(this, "infantBorn") + " " + data.getStringExtra("infantBorn"));
                            textContentInfantFlight4.setText(data.getStringExtra("infantTitleTemp") + " " + data.getStringExtra("infantName"));
                            textContentInfantFlight4.setTag(R.id.titleInfant4, data.getStringExtra("infantTitle"));
                            textContentInfantFlight4.setTag(R.id.titleTempInfant4, data.getStringExtra("infantTitleTemp"));
                            textContentInfantFlight4.setTag(R.id.nameInfant4, data.getStringExtra("infantName"));
                            textContentInfantFlight4.setTag(R.id.bornInfant4, data.getStringExtra("infantBorn"));
                            textContentInfantFlight4.setTag(R.id.bornShowInfant4, data.getStringExtra("infantBornShow"));

                            textContentInfantFlight4.setTag(R.id.noPassportInfant4, data.getStringExtra("infantNoPassport"));
                            textContentInfantFlight4.setTag(R.id.nasinatilyInfant4, data.getStringExtra("infantNationality"));
                            textContentInfantFlight4.setTag(R.id.nasinatilyKodeInfant4, data.getStringExtra("infantNationalityKode"));
                            textContentInfantFlight4.setTag(R.id.issuingCountryInfant4, data.getStringExtra("infantIssuingCountry"));
                            textContentInfantFlight4.setTag(R.id.issuingCountryKodeInfant4, data.getStringExtra("infantIssuingCountryKode"));
                            textContentInfantFlight4.setTag(R.id.expiredInfant4, data.getStringExtra("infantExpired"));
                            textContentInfantFlight4.setTag(R.id.expiredShowInfant4, data.getStringExtra("infantExpiredShow"));

                            if (!data.getStringExtra("infantNoPassport").isEmpty()) {
                                noId = data.getStringExtra("infantNoPassport");
                                lin_contentPassportInfant4.setVisibility(View.VISIBLE);
                                textViewNoPassportInfantFlight4.setText(noId);

                            }
                            if (!data.getStringExtra("infantNationality").isEmpty()) {
                                passportNasionalityId = data.getStringExtra("infantNationalityKode");

                            }
                            if (!data.getStringExtra("infantIssuingCountry").isEmpty()) {
                                passportIssuingNasionality = data.getStringExtra("infantIssuingCountryKode");

                            }
                            if (!data.getStringExtra("infantExpiredShow").isEmpty()) {
                                passportExpDate = data.getStringExtra("infantExpired");

                            }

                            textViewTanggalLahirInfant4.setText(data.getStringExtra("infantBornShow"));
                            if (data.getStringExtra("infantTitleTemp").equals("Tn")) {
                                initTitle = "MSTR";
                            } else if (data.getStringExtra("infantTitleTemp").equals("Nona")) {
                                initTitle = "MISS";
                            }
                            String inputName = data.getStringExtra("infantName");
                            fullName = inputName.split(" ");
                            firstName = fullName[0];
                            if (fullName.length >= 1) {
                                lastName = inputName.replace(firstName + " ", "");
                            } else {
                                if (PreferenceClass.getString(FlightKeyPreference.airlineCode, "").equals("TPQG")) {
                                    lastName = initTitle;
                                } else {
                                    lastName = fullName[0];
                                }
                            }
                            bornDate = data.getStringExtra("infantBorn");
                            passengersList.add(data.getStringExtra("infantTitleTemp") + ". " + firstName + " " + lastName);
                            infantPassengersList.put("4", data.getStringExtra("infantTitleTemp") + ". " + firstName + " " + lastName);
                            //Log.d(TAG, "onActivityResult: INF;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1");
                            if (airLineCode.equals("TPJT") && data.getStringExtra("infantNoPassport").isEmpty() ) {
                                infantPassanger[3] = "INF;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1;;;;;;;;" + nasionalityId + ";" + passportNasionalityId + ";";
                            } else {
                                infantPassanger[3] = "INF;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";"+ noId +";;;;;;;;" + nasionalityId + ";" + passportNasionalityId + ";" + passportExpDate + ";" + passportIssuedDate + ";" + passportIssuingNasionality;
                            }
                            textActionInfantFlight4.setVisibility(View.VISIBLE);
                            imageActionInfantFlight4.setVisibility(View.GONE);
                        } else if (requestCode == TravelActionCode.INFANT5) {
                            imageViewExpandPassangerInfantFlight5.performClick();// 1
                            cardPassangerInfantFlight5.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
//                    panelPassangerInfantFlight5Detail.setBackgroundResource(R.drawable.card_primary);
                            if (!data.getBooleanExtra(FlightKeyPreference.isNewFlight, false)) {
                                infantPassengersList.remove("5");
                                infantPassanger[4] = null;
                            }
                            //Log.d(TAG, "onActivityResult infant 5: " + MemoryStore.get(this, "infantBorn") + " " + data.getStringExtra("infantBorn"));
                            textContentInfantFlight5.setText(data.getStringExtra("infantTitleTemp") + " " + data.getStringExtra("infantName"));
                            textContentInfantFlight5.setTag(R.id.titleInfant5, data.getStringExtra("infantTitle"));
                            textContentInfantFlight5.setTag(R.id.titleTempInfant5, data.getStringExtra("infantTitleTemp"));
                            textContentInfantFlight5.setTag(R.id.nameInfant5, data.getStringExtra("infantName"));
                            textContentInfantFlight5.setTag(R.id.bornInfant5, data.getStringExtra("infantBorn"));
                            textContentInfantFlight5.setTag(R.id.bornShowInfant5, data.getStringExtra("infantBornShow"));

                            textContentInfantFlight5.setTag(R.id.noPassportInfant5, data.getStringExtra("infantNoPassport"));
                            textContentInfantFlight5.setTag(R.id.nasinatilyInfant5, data.getStringExtra("infantNationality"));
                            textContentInfantFlight5.setTag(R.id.nasinatilyKodeInfant5, data.getStringExtra("infantNationalityKode"));
                            textContentInfantFlight5.setTag(R.id.issuingCountryInfant5, data.getStringExtra("infantIssuingCountry"));
                            textContentInfantFlight5.setTag(R.id.issuingCountryKodeInfant5, data.getStringExtra("infantIssuingCountryKode"));
                            textContentInfantFlight5.setTag(R.id.expiredInfant5, data.getStringExtra("infantExpired"));
                            textContentInfantFlight5.setTag(R.id.expiredShowInfant5, data.getStringExtra("infantExpiredShow"));

                            if (!data.getStringExtra("infantNoPassport").isEmpty()) {
                                noId = data.getStringExtra("infantNoPassport");
                                lin_contentPassportInfant5.setVisibility(View.VISIBLE);
                                textViewNoPassportInfantFlight5.setText(noId);

                            }
                            if (!data.getStringExtra("infantNationality").isEmpty()) {
                                passportNasionalityId = data.getStringExtra("infantNationalityKode");

                            }
                            if (!data.getStringExtra("infantIssuingCountry").isEmpty()) {
                                passportIssuingNasionality = data.getStringExtra("infantIssuingCountryKode");

                            }
                            if (!data.getStringExtra("infantExpiredShow").isEmpty()) {
                                passportExpDate = data.getStringExtra("infantExpired");

                            }

                            textViewTanggalLahirInfant5.setText(data.getStringExtra("infantBornShow"));
                            if (data.getStringExtra("infantTitleTemp").equals("Tn")) {
                                initTitle = "MSTR";
                            } else if (data.getStringExtra("infantTitleTemp").equals("Nona")) {
                                initTitle = "MISS";
                            }
                            String inputName = data.getStringExtra("infantName");
                            fullName = inputName.split(" ");
                            firstName = fullName[0];
                            if (fullName.length >= 1) {
                                lastName = inputName.replace(firstName + " ", "");
                            } else {
                                if (PreferenceClass.getString(FlightKeyPreference.airlineCode, "").equals("TPQG")) {
                                    lastName = initTitle;
                                } else {
                                    lastName = fullName[0];
                                }
                            }
                            bornDate = data.getStringExtra("infantBorn");
                            passengersList.add(data.getStringExtra("infantTitleTemp") + ". " + firstName + " " + lastName);
                            infantPassengersList.put("5", data.getStringExtra("infantTitleTemp") + ". " + firstName + " " + lastName);
                            //Log.d(TAG, "onActivityResult: INF;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1");
                            if (airLineCode.equals("TPJT") && data.getStringExtra("infantNoPassport").isEmpty() ) {
                                infantPassanger[4] = "INF;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1;;;;;;;;" + nasionalityId + ";" + passportNasionalityId + ";";
                            } else {
                                infantPassanger[4] = "INF;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";"+ noId +";;;;;;;;" + nasionalityId + ";" + passportNasionalityId + ";" + passportExpDate + ";" + passportIssuedDate + ";" + passportIssuingNasionality;
                            }
                            textActionInfantFlight5.setVisibility(View.VISIBLE);
                            imageActionInfantFlight5.setVisibility(View.GONE);
                        } else if (requestCode == TravelActionCode.INFANT6) {
                            imageViewExpandPassangerInfantFlight6.performClick();// 1
                            cardPassangerInfantFlight6.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
//                    panelPassangerInfantFlight6Detail.setBackgroundResource(R.drawable.card_primary);
                            if (!data.getBooleanExtra(FlightKeyPreference.isNewFlight, false)) {
                                infantPassengersList.remove("6");
                                infantPassanger[5] = null;
                            }
                            //Log.d(TAG, "onActivityResult infant 6: " + MemoryStore.get(this, "infantBorn") + " " + data.getStringExtra("infantBorn"));
                            textContentInfantFlight6.setText(data.getStringExtra("infantTitleTemp") + " " + data.getStringExtra("infantName"));
                            textContentInfantFlight6.setTag(R.id.titleInfant6, data.getStringExtra("infantTitle"));
                            textContentInfantFlight6.setTag(R.id.titleTempInfant6, data.getStringExtra("infantTitleTemp"));
                            textContentInfantFlight6.setTag(R.id.nameInfant6, data.getStringExtra("infantName"));
                            textContentInfantFlight6.setTag(R.id.bornInfant6, data.getStringExtra("infantBorn"));
                            textContentInfantFlight6.setTag(R.id.bornShowInfant6, data.getStringExtra("infantBornShow"));

                            textContentInfantFlight6.setTag(R.id.noPassportInfant6, data.getStringExtra("infantNoPassport"));
                            textContentInfantFlight6.setTag(R.id.nasinatilyInfant6, data.getStringExtra("infantNationality"));
                            textContentInfantFlight6.setTag(R.id.nasinatilyKodeInfant6, data.getStringExtra("infantNationalityKode"));
                            textContentInfantFlight6.setTag(R.id.issuingCountryInfant6, data.getStringExtra("infantIssuingCountry"));
                            textContentInfantFlight6.setTag(R.id.issuingCountryKodeInfant6, data.getStringExtra("infantIssuingCountryKode"));
                            textContentInfantFlight6.setTag(R.id.expiredInfant6, data.getStringExtra("infantExpired"));
                            textContentInfantFlight6.setTag(R.id.expiredShowInfant6, data.getStringExtra("infantExpiredShow"));

                            if (!data.getStringExtra("infantNoPassport").isEmpty()) {
                                noId = data.getStringExtra("infantNoPassport");
                                lin_contentPassportInfant6.setVisibility(View.VISIBLE);
                                textViewNoPassportInfantFlight6.setText(noId);

                            }
                            if (!data.getStringExtra("infantNationality").isEmpty()) {
                                passportNasionalityId = data.getStringExtra("infantNationalityKode");

                            }
                            if (!data.getStringExtra("infantIssuingCountry").isEmpty()) {
                                passportIssuingNasionality = data.getStringExtra("infantIssuingCountryKode");

                            }
                            if (!data.getStringExtra("infantExpiredShow").isEmpty()) {
                                passportExpDate = data.getStringExtra("infantExpired");

                            }

                            textViewTanggalLahirInfant6.setText(data.getStringExtra("infantBornShow"));
                            if (data.getStringExtra("infantTitleTemp").equals("Tn")) {
                                initTitle = "MSTR";
                            } else if (data.getStringExtra("infantTitleTemp").equals("Nona")) {
                                initTitle = "MISS";
                            }
                            String inputName = data.getStringExtra("infantName");
                            fullName = inputName.split(" ");
                            firstName = fullName[0];
                            if (fullName.length >= 1) {
                                lastName = inputName.replace(firstName + " ", "");
                            } else {
                                if (PreferenceClass.getString(FlightKeyPreference.airlineCode, "").equals("TPQG")) {
                                    lastName = initTitle;
                                } else {
                                    lastName = fullName[0];
                                }
                            }
                            bornDate = data.getStringExtra("infantBorn");
                            passengersList.add(data.getStringExtra("infantTitleTemp") + ". " + firstName + " " + lastName);
                            infantPassengersList.put("6", data.getStringExtra("infantTitleTemp") + ". " + firstName + " " + lastName);
                            //Log.d(TAG, "onActivityResult: INF;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1");
                            if (airLineCode.equals("TPJT") && data.getStringExtra("infantNoPassport").isEmpty() ) {
                                infantPassanger[5] = "INF;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1;;;;;;;;" + nasionalityId + ";" + passportNasionalityId + ";";
                            } else {
                                infantPassanger[5] = "INF;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";"+ noId +";;;;;;;;" + nasionalityId + ";" + passportNasionalityId + ";" + passportExpDate + ";" + passportIssuedDate + ";" + passportIssuingNasionality;
                            }
                            textActionInfantFlight6.setVisibility(View.VISIBLE);
                            imageActionInfantFlight6.setVisibility(View.GONE);
                        } else if (requestCode == TravelActionCode.INFANT7) {
                            imageViewExpandPassangerInfantFlight7.performClick();// 1
                            cardPassangerInfantFlight7.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
//                    panelPassangerInfantFlight7Detail.setBackgroundResource(R.drawable.card_primary);
                            if (!data.getBooleanExtra(FlightKeyPreference.isNewFlight, false)) {
                                infantPassengersList.remove("7");
                                infantPassanger[6] = null;
                            }
                            //Log.d(TAG, "onActivityResult infant 7: " + MemoryStore.get(this, "infantBorn") + " " + data.getStringExtra("infantBorn"));
                            textContentInfantFlight7.setText(data.getStringExtra("infantTitleTemp") + " " + data.getStringExtra("infantName"));
                            textContentInfantFlight7.setTag(R.id.titleInfant7, data.getStringExtra("infantTitle"));
                            textContentInfantFlight7.setTag(R.id.titleTempInfant7, data.getStringExtra("infantTitleTemp"));
                            textContentInfantFlight7.setTag(R.id.nameInfant7, data.getStringExtra("infantName"));
                            textContentInfantFlight7.setTag(R.id.bornInfant7, data.getStringExtra("infantBorn"));
                            textContentInfantFlight7.setTag(R.id.bornShowInfant7, data.getStringExtra("infantBornShow"));

                            textContentInfantFlight7.setTag(R.id.noPassportInfant7, data.getStringExtra("infantNoPassport"));
                            textContentInfantFlight7.setTag(R.id.nasinatilyInfant7, data.getStringExtra("infantNationality"));
                            textContentInfantFlight7.setTag(R.id.nasinatilyKodeInfant7, data.getStringExtra("infantNationalityKode"));
                            textContentInfantFlight7.setTag(R.id.issuingCountryInfant7, data.getStringExtra("infantIssuingCountry"));
                            textContentInfantFlight7.setTag(R.id.issuingCountryKodeInfant7, data.getStringExtra("infantIssuingCountryKode"));
                            textContentInfantFlight7.setTag(R.id.expiredInfant7, data.getStringExtra("infantExpired"));
                            textContentInfantFlight7.setTag(R.id.expiredShowInfant7, data.getStringExtra("infantExpiredShow"));

                            if (!data.getStringExtra("infantNoPassport").isEmpty()) {
                                noId = data.getStringExtra("infantNoPassport");
                                lin_contentPassportInfant7.setVisibility(View.VISIBLE);
                                textViewNoPassportInfantFlight7.setText(noId);

                            }
                            if (!data.getStringExtra("infantNationality").isEmpty()) {
                                passportNasionalityId = data.getStringExtra("infantNationalityKode");

                            }
                            if (!data.getStringExtra("infantIssuingCountry").isEmpty()) {
                                passportIssuingNasionality = data.getStringExtra("infantIssuingCountryKode");

                            }
                            if (!data.getStringExtra("infantExpiredShow").isEmpty()) {
                                passportExpDate = data.getStringExtra("infantExpired");

                            }

                            textViewTanggalLahirInfant7.setText(data.getStringExtra("infantBornShow"));
                            if (data.getStringExtra("infantTitleTemp").equals("Tn")) {
                                initTitle = "MSTR";
                            } else if (data.getStringExtra("infantTitleTemp").equals("Nona")) {
                                initTitle = "MISS";
                            }
                            String inputName = data.getStringExtra("infantName");
                            fullName = inputName.split(" ");
                            firstName = fullName[0];
                            if (fullName.length >= 1) {
                                lastName = inputName.replace(firstName + " ", "");
                            } else {
                                if (PreferenceClass.getString(FlightKeyPreference.airlineCode, "").equals("TPQG")) {
                                    lastName = initTitle;
                                } else {
                                    lastName = fullName[0];
                                }
                            }
                            bornDate = data.getStringExtra("infantBorn");
                            passengersList.add(data.getStringExtra("infantTitleTemp") + ". " + firstName + " " + lastName);
                            infantPassengersList.put("7", data.getStringExtra("infantTitleTemp") + ". " + firstName + " " + lastName);
                            //Log.d(TAG, "onActivityResult: INF;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1");
                            if (airLineCode.equals("TPJT") && data.getStringExtra("infantNoPassport").isEmpty() ) {
                                infantPassanger[6] = "INF;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1;;;;;;;;" + nasionalityId + ";" + passportNasionalityId + ";";
                            } else {
                                infantPassanger[6] = "INF;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";"+ noId +";;;;;;;;" + nasionalityId + ";" + passportNasionalityId + ";" + passportExpDate + ";" + passportIssuedDate + ";" + passportIssuingNasionality;
                            }
                            textActionInfantFlight7.setVisibility(View.VISIBLE);
                            imageActionInfantFlight7.setVisibility(View.GONE);
                        }
                        break;
                }


            }

        }



    }

    boolean isStillRunning = true;

    public void click_pesan_flight() {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObjectPassengers = new JSONObject();
        JSONArray jsonArray = PreferenceClass.getJSONArray(FlightKeyPreference.seat_flights);
        JSONArray jsonArrayAdults = new JSONArray();
        JSONArray jsonArrayChilds = new JSONArray();
        JSONArray jsonArrayInfants = new JSONArray();

        JSONArray jsonArrayBaggage = new JSONArray();

        for (String adult : adultPassanger) {
            jsonArrayAdults.put(adult);
        }
        for (String child : childPassanger) {
            jsonArrayChilds.put(child);
        }
        for (String infant : infantPassanger) {
            jsonArrayInfants.put(infant);
        }

        if (countAdult == 1) {
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult1).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult1));
            }
        } else if (countAdult == 2) {
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult1).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult1));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult2).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult2));
            }
        } else if (countAdult == 3) {
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult1).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult1));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult2).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult2));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult3).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult3));
            }
        } else if (countAdult == 4) {
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult1).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult1));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult2).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult2));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult3).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult3));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult4).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult4));
            }
        } else if (countAdult == 5) {
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult1).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult1));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult2).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult2));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult3).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult3));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult4).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult4));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult5).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult5));
            }
        } else if (countAdult == 6) {
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult1).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult1));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult2).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult2));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult3).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult3));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult4).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult4));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult5).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult5));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult6).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult6));
            }
        } else if (countAdult == 7) {
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult1).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult1));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult2).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult2));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult3).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult3));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult4).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult4));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult5).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult5));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult6).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult6));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult7).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageAdult7));
            }
        }


        if (countChild == 1) {
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild1).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild1));
            }
        } else if (countChild == 2) {
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild1).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild1));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild2).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild2));
            }
        } else if (countChild == 3) {
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild1).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild1));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild2).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild2));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild3).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild3));
            }
        } else if (countChild == 4) {
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild1).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild1));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild2).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild2));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild3).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild3));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild4).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild4));
            }
        } else if (countChild == 5) {
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild1).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild1));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild2).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild2));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild3).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild3));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild4).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild4));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild5).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild5));
            }
        } else if (countChild == 6) {
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild1).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild1));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild2).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild2));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild3).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild3));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild4).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild4));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild5).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild5));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild6).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild6));
            }
        } else if (countChild == 7) {
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild1).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild1));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild2).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild2));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild3).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild3));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild4).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild4));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild5).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild5));
            }
            if (PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild7).length() > 0) {
                jsonArrayBaggage.put(PreferenceClass.getJSONArray(FlightKeyPreference.baggageChild7));
            }
        }

        try {
            jsonObject.put("hp_kontak", textHpKontak.getText());
            jsonObject.put("email_kontak", textEmailKontak.getText());
            jsonObject.put("airline", PreferenceClass.getString(FlightKeyPreference.airlineCode, ""));
            jsonObject.put("departure", PreferenceClass.getString(FlightKeyPreference.airportKodeAsal, ""));
            jsonObject.put("arrival", PreferenceClass.getString(FlightKeyPreference.airportKodeTujuan, ""));
            jsonObject.put("departureDate", PreferenceClass.getString(FlightKeyPreference.departureDateFlight, ""));
            jsonObject.put("returnDate", PreferenceClass.getString(FlightKeyPreference.returnDateFlight, ""));
            jsonObject.put("adult", PreferenceClass.getInt(FlightKeyPreference.countAdultFlight, 1));
            jsonObject.put("child", PreferenceClass.getInt(FlightKeyPreference.countChildFlight, 0));
            jsonObject.put("infant", PreferenceClass.getInt(FlightKeyPreference.countInfantFlight, 0));
            jsonObject.put("flights", jsonArray);
            jsonObjectPassengers.put("adults", jsonArrayAdults);
            if (childPassanger.length > 0) {
                jsonObjectPassengers.put("children", jsonArrayChilds);
            }
            if (infantPassanger.length > 0) {
                jsonObjectPassengers.put("infants", jsonArrayInfants);
            }
            if (jsonArrayBaggage.length() > 0) {

                jsonObject.put("baggages", jsonArrayBaggage);
            }
            jsonObject.put("passengers", jsonObjectPassengers);
            jsonObject.put("cid", PreferenceClass.getString(TravelActionCode.CID,"0"));
            jsonObject.put("token", PreferenceClass.getToken());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "REQUEST book Flight: " + jsonObject);
//        Intent intent = new Intent(FlightBookActivity.this, FlightReviewBookActivity.class);
//        Bundle b = new Bundle();
////        b.putString("transactionId","1388496053");
//        b.putString("transactionId","1399819209");
//        intent.putExtras(b);
//      //  intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
//       startActivityForResult(intent,TravelActionCode.IS_FROM_PAY);
      //  finish();
        logEventFireBase(ProdukGroup.PESAWAT,  PreferenceClass.getString(FlightKeyPreference.airlineName,""), String.valueOf(price),PreferenceClass.getString(FlightKeyPreference.airportKodeAsal, ""),PreferenceClass.getString(FlightKeyPreference.airlineCode,""),PreferenceClass.getString(FlightKeyPreference.airportKodeTujuan, "") ,PreferenceClass.getString(FlightKeyPreference.departureDateFlight, ""), String.valueOf(PreferenceClass.getBoolean(FlightKeyPreference.isTransit,false)),EventParam.EVENT_ACTION_REQUEST_BOOK, EventParam.EVENT_SUCCESS, TAG);
        isStillRunning = true;
        RequestUtilsTravel.transportWithProgressResponseBook_Pay(this, FlightPath.BOOK, jsonObject, TravelActionCode.BOOK, this);
//        ViewGroup parent = findViewById(R.id.coordinated_layout_flight);
        final FrameLayout view =(FrameLayout)View.inflate(this,R.layout.loading_bar_full_dialog, null);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText("Mohon Tunggu, Permintaan Booking Ke Maskapai Sedang Diproses");
        openProgressBarDialog(FlightBookActivity.this, view);
    }

    @Override
    public void onSuccess(int actionCode, @NonNull final JSONObject response) {
        Log.d(TAG, "onSuccess: " + response.toString());
        try {
            if (actionCode == TravelActionCode.FARE) {
                //Log.d(TAG, "onSuccess FLIGHT_FARE: " + response.toString());
                String rc = response.getString("rc").toString();
                String rd = response.getString("rd").toString();
                if (rc.equals(ResponseCode.SUCCESS)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            avi.setVisibility(View.INVISIBLE);
                            JSONObject oParent;
                            try {
                                oParent = response.getJSONObject("data");
                                String deptTime = oParent.getString("departureTime").equalsIgnoreCase("") ? timeDep : oParent.getString("departureTime");
                                String arrTime = oParent.getString("arrivalTime").equalsIgnoreCase("") ? timeArr : oParent.getString("arrivalTime");
                                textTimeDept.setText(deptTime);
                                textTimeArr.setText(arrTime);
                                PreferenceClass.putString(FlightKeyPreference.departureTime, deptTime);
                                PreferenceClass.putString(FlightKeyPreference.arrivalTime, arrTime);
                                if (!oParent.getString("price").equals("0")) {
                                    textTotal.setText(utilBand.rupiahFormate(Integer.parseInt(oParent.getString("price"))));
                                    //SavePref.getInstance(getApplicationContext()).saveString(oParent.getString("pricefrombook"),"");
                                } else {
                                    textTotal.setText(utilBand.rupiahFormate(price));
//                                    MemoryStore.set(FlightBookActivity.this, "priceFromFare", price);
                                    PreferenceClass.putString(FlightKeyPreference.pricefrombook, String.valueOf(price));

                                }
                                textDateDept.setText(depDate);
                                textDateArr.setText(arrDate);
                                bagageFromResponse = oParent.getString("baggage");



                                logEventFireBase(ProdukGroup.PESAWAT,  PreferenceClass.getString(FlightKeyPreference.airlineName,""),String.valueOf(price),PreferenceClass.getString(FlightKeyPreference.airportKodeAsal, ""),PreferenceClass.getString(FlightKeyPreference.airlineCode,""),PreferenceClass.getString(FlightKeyPreference.airportKodeTujuan, "") ,PreferenceClass.getString(FlightKeyPreference.departureDateFlight, ""), String.valueOf(PreferenceClass.getBoolean(FlightKeyPreference.isTransit,false)),EventParam.EVENT_ACTION_RESULT_FARE, EventParam.EVENT_SUCCESS, TAG);

                            } catch (JSONException e) {
                                showToast(e.toString());
//                                finish();
                                onBackPressed();
                            }
                        }
                    });
                }else  if (rc.equals("11")) {

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
//                            finish();
//                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            onBackPressed();


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

//                    }}).start();
                  //  }

//
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            avi.setVisibility(View.INVISIBLE);
                            if (price > 0) {
                                textTotal.setText(utilBand.formatRupiah(price));
                            } else {
//                                textTotal.setText("-");
                                textTotal.setText(utilBand.formatRupiah(PreferenceClass.getInt(FlightKeyPreference.OneWayPriceSchedule, 0)));
                                //HARUS DI KASIH TOMBOL REFRESH PRICE
                            }
//                            MemoryStore.set(FlightBookActivity.this, "priceFromFare", price);
                            PreferenceClass.putString(FlightKeyPreference.pricefrombook, String.valueOf(price));
                        }
                    });
                }
//            } else if (actionCode == TravelActionCode.BAGAGE) {
//                FlightBaggageModel flightBaggageModel = gson.fromJson(response.toString(), FlightBaggageModel.class);
//
//
//                //  itemList.clear();
//                itemList.addAll(flightBaggageModel.getData());
//                // Collections.reverse(itemList);
//                MemoryStore.set(FlightKeyPreference.baggage, itemList);

            } else if (actionCode == TravelActionCode.BAGGAGE1) {
                PreferenceClass.remove(FlightKeyPreference.baggage1);
                //   Log.d(TAG, "onSuccess: BAGGAGE1" + response);
//                FlightBaggageModel flightBaggageModel = gson.fromJson(response.toString(), FlightBaggageModel.class);
//
//
//                //  itemList.clear();
//                itemList.addAll(flightBaggageModel.getData());
//                // Collections.reverse(itemList);
//                MemoryStore.set(FlightKeyPreference.baggage1, itemList);

                PreferenceClass.putJSONObject(FlightKeyPreference.baggage1, response);

            } else if (actionCode == TravelActionCode.BAGGAGE2) {
                PreferenceClass.remove(FlightKeyPreference.baggage2);
                //  Log.d(TAG, "onSuccess: BAGGAGE2" + response);
//                FlightBaggageModel flightBaggageModel = gson.fromJson(response.toString(), FlightBaggageModel.class);
//
//
//                //  itemList.clear();
//                itemList.addAll(flightBaggageModel.getData());
//                // Collections.reverse(itemList);
//                MemoryStore.set(FlightKeyPreference.baggage2, itemList);
                PreferenceClass.putJSONObject(FlightKeyPreference.baggage2, response);
            } else if (actionCode == TravelActionCode.BAGGAGE3) {
                PreferenceClass.remove(FlightKeyPreference.baggage3);
                //  Log.d(TAG, "onSuccess: BAGGAGE3" + response);
//                FlightBaggageModel flightBaggageModel = gson.fromJson(response.toString(), FlightBaggageModel.class);
//
//
//                //  itemList.clear();
//                itemList.addAll(flightBaggageModel.getData());
//                // Collections.reverse(itemList);
//                MemoryStore.set(FlightKeyPreference.baggage3, itemList);
                PreferenceClass.putJSONObject(FlightKeyPreference.baggage3, response);


            } else if (actionCode == TravelActionCode.BOOK) {
                isStillRunning = false;
                closeProgressBarDialog();
                //Log.d(TAG, "onSuccess: " + response.toString());
                final String rc = response.getString("rc").toString();
                final String rd = response.getString("rd").toString();
                if (rc.equals(ResponseCode.SUCCESS)) {

                    JSONObject data = response.getJSONObject("data");
//                    SharedPreferenceUtils.getInstance(FlightBookActivity.this).putJSONObject("data",data);
                    Intent intent = new Intent(FlightBookActivity.this, FlightReviewBookActivity.class);
                    Bundle b = new Bundle();
                    b.putString("passengers", data.getString("passengers"));
                    b.putString("details", data.getString("details"));
                    b.putString("bookingCode", data.getString("bookingCode"));
                    b.putString("paymentCode", data.getString("paymentCode"));
                    b.putString("flightCode1", data.getString("flightCode1"));
                    b.putString("departureTime1", PreferenceClass.getString(FlightKeyPreference.departureTime, ""));
                    b.putString("arrivalTime1", PreferenceClass.getString(FlightKeyPreference.arrivalTime, ""));
                    b.putString("reservationDate", data.getString("reservationDate"));
                    b.putString("timeLimit", data.getString("timeLimit"));
                    b.putString("flightInfoGo", data.getString("flightInfoGo"));
                    b.putString("nominal", data.getString("nominal"));
                    b.putString("comission", data.getString("comission"));
                    b.putString("komisi", data.getString("comission"));
                    b.putString("nominalAdmin", data.getString("nominalAdmin"));
                    b.putString("transactionId", data.getString("transactionId"));

                    b.putStringArrayList("passengerList", passengersList);
                    b.putSerializable("adultPassangerList", adultPassengersList);
                    b.putSerializable("childPassangerList", childPassengersList);
                    b.putSerializable("infantPassangerList", infantPassengersList);
                    b.putStringArray("adultPassanger", adultPassanger);
                    b.putStringArray("childPassanger", childPassanger);
                    b.putStringArray("infantPassanger", infantPassanger);

                    PreferenceClass.remove(FlightKeyPreference.pricefrombook);
                    PreferenceClass.putString(FlightKeyPreference.pricefrombook, data.getString("nominal"));
                    PreferenceClass.remove(FlightKeyPreference.bookcode);
                    PreferenceClass.putString(FlightKeyPreference.bookcode, data.getString("bookingCode"));

                    logEventFireBase(ProdukGroup.PESAWAT,  PreferenceClass.getString(FlightKeyPreference.airlineName,""), utilBand.rupiahFormate(price),PreferenceClass.getString(FlightKeyPreference.airportKodeAsal, ""),PreferenceClass.getString(FlightKeyPreference.airlineCode,""),PreferenceClass.getString(FlightKeyPreference.airportKodeTujuan, "") ,PreferenceClass.getString(FlightKeyPreference.departureDateFlight, ""), String.valueOf(PreferenceClass.getBoolean(FlightKeyPreference.isTransit,false)),data.getString("bookingCode"),EventParam.EVENT_ACTION_RESULT_BOOK, EventParam.EVENT_SUCCESS, TAG);

                    intent.putExtras(b);
                  //  intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                    startActivityForResult(intent, TravelActionCode.IS_FROM_PAY);
                   // finish();
                }else  if ((rc.equals("71"))||(rc.equals("73"))||(rc.equals("75"))) {

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
//                            finish();
//                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            onBackPressed();


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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Log.d(TAG, "run: " + rc + " " + rd);
                            //showToastCustom(FlightBookActivity.this, 2, rd);
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
               onBackPressed();
            }

        }
    }

    @Override
    public void onFailure(final int actionCode, @NonNull final String responseCode, String responseDescription, Throwable throwable) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
        if (actionCode == TravelActionCode.BOOK) {
            isStillRunning = false;
            closeProgressBarDialog();
            if (responseCode.equals("504")) {
                //showToastCustom(FlightBookActivity.this, 3, "Silahkan cek di menu pesanan Anda");
                snackBarCustomAction(findViewById(R.id.rootLayout), 0, "Silahkan cek di menu pesanan Anda", 3);
            }else{
                snackBarCustomAction(findViewById(R.id.rootLayout), 0, "Ada Masalah dengan server, silahkan coba kembali", 1);
                onBackPressed();
            }
        } else {


//            showToastCustom(FlightBookActivity.this, 1, "Ada Masalah dengan server, silahkan coba kembali");
            snackBarCustomAction(findViewById(R.id.rootLayout), 0, "Ada Masalah dengan server, silahkan coba kembali", 1);
            onBackPressed();
        }
//            }
//        });


//        if (actionCode == TravelActionCode.FLIGHT_BOOK) {
//            closeProgressBarDialog();
//            if(responseCode.equals("504")) {
//                showToastCustom(FlightBookActivity.this, 3, "Silahkan cek di menu pesanan Anda");
//            }
//        }


//        if (actionCode == TravelActionCode.BOOK ) {
//            closeProgressBarDialog();
//
//
//        } else {
//            finish();
//        }
        Log.d(TAG, "onFailure: " + responseDescription + " " + throwable);
    }


    @Override
    public void onClick(@NonNull View v) {
        int id = v.getId();
        String tag = v.getTag().toString();
        if (tag.equals("adult")) {
            openFormAdult(v);
        } else if (tag.equals("child")) {
            openFormChild(v);
        } else if (tag.equals("infant")) {
            openFormInfant(v);
        } else if (id == R.id.btn_pesan) {
            callPesanFlight();
        }
    }

    private void callPesanFlight() {
        if (1 <= countAdult) {
            if (textContentAdultFlight1.getTag(R.id.nameAdult1) == null) {


//                   if(snackbar.isShown()) {
//                       snackbar.dismiss();
//                   }
                snackBarCustomAction(findViewById(R.id.rootLayout), R.string.flight_penumpang_dewasa_1_kosong, "", 1);
                // textContentAdultFlight1.setError(getResources().getString(R.string.flight_penumpang_dewasa_1_kosong));
                //rlPassangerAdultFlight1.setBackgroundResource(R.drawable.shape_card_error);

//                    cardPassangerAdultFlight1.addView();
                cardPassangerAdultFlight1.setBackgroundResource(R.drawable.shape_card_error);
                cardPassangerAdultFlight1.setAnimation(animShake);
                cardPassangerAdultFlight1.startAnimation(animShake);
                getar();
                return;
            }
        }
        if (2 <= countAdult) {
            if (cardPassangerAdultFlight2.getVisibility() == View.VISIBLE) {
                if (textContentAdultFlight2.getTag(R.id.nameAdult2) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.flight_penumpang_dewasa_2_kosong, "", 1);
                    cardPassangerAdultFlight2.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerAdultFlight2.setAnimation(animShake);
                    cardPassangerAdultFlight2.startAnimation(animShake);
                    getar();
                    return;
                }
            }
        }
        if (3 <= countAdult) {
            if (cardPassangerAdultFlight3.getVisibility() == View.VISIBLE) {
                if (textContentAdultFlight3.getTag(R.id.nameAdult3) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.flight_penumpang_dewasa_3_kosong, "", 1);
                    cardPassangerAdultFlight3.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerAdultFlight3.setAnimation(animShake);
                    cardPassangerAdultFlight3.startAnimation(animShake);
                    getar();
                    return;
                }
            }
        }
        if (4 <= countAdult) {
            if (cardPassangerAdultFlight4.getVisibility() == View.VISIBLE) {
                if (textContentAdultFlight4.getTag(R.id.nameAdult4) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.flight_penumpang_dewasa_4_kosong, "", 1);
                    cardPassangerAdultFlight4.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerAdultFlight4.setAnimation(animShake);
                    cardPassangerAdultFlight4.startAnimation(animShake);
                    getar();
                    return;
                }
            }
        }
        if (5 <= countAdult) {
            if (cardPassangerAdultFlight5.getVisibility() == View.VISIBLE) {
                if (textContentAdultFlight5.getTag(R.id.nameAdult5) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.flight_penumpang_dewasa_5_kosong, "", 1);
                    cardPassangerAdultFlight5.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerAdultFlight5.setAnimation(animShake);
                    cardPassangerAdultFlight5.startAnimation(animShake);
                    getar();
                    return;
                }
            }
        }
        if (6 <= countAdult) {
            if (cardPassangerAdultFlight6.getVisibility() == View.VISIBLE) {
                if (textContentAdultFlight6.getTag(R.id.nameAdult6) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.flight_penumpang_dewasa_6_kosong, "", 1);
                    cardPassangerAdultFlight6.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerAdultFlight6.setAnimation(animShake);
                    cardPassangerAdultFlight6.startAnimation(animShake);
                    getar();
                    return;
                }
            }
        }
        if (7 <= countAdult) {
            if (cardPassangerAdultFlight7.getVisibility() == View.VISIBLE) {
                if (textContentAdultFlight7.getTag(R.id.nameAdult7) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.flight_penumpang_dewasa_7_kosong, "", 1);
                    cardPassangerAdultFlight7.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerAdultFlight7.setAnimation(animShake);
                    cardPassangerAdultFlight7.startAnimation(animShake);
                    getar();
                    return;
                }
            }
        }
        if (1 <= countChild) {
            if (cardPassangerChildFlight1.getVisibility() == View.VISIBLE) {
                if (textContentChildFlight1.getTag(R.id.nameChild1) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.flight_penumpang_anak_1_kosong, "", 1);
                    cardPassangerChildFlight1.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerChildFlight1.setAnimation(animShake);
                    cardPassangerChildFlight1.startAnimation(animShake);
                    getar();
                    return;
                }
            }
        }
        if (2 <= countChild) {
            if (cardPassangerChildFlight2.getVisibility() == View.VISIBLE) {
                if (textContentChildFlight2.getTag(R.id.nameChild2) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.flight_penumpang_anak_2_kosong, "", 2);
                    cardPassangerChildFlight2.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerChildFlight2.setAnimation(animShake);
                    cardPassangerChildFlight2.startAnimation(animShake);
                    getar();
                    return;
                }
            }
        }
        if (3 <= countChild) {
            if (cardPassangerChildFlight3.getVisibility() == View.VISIBLE) {
                if (textContentChildFlight3.getTag(R.id.nameChild3) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.flight_penumpang_anak_3_kosong, "", 3);
                    cardPassangerChildFlight3.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerChildFlight3.setAnimation(animShake);
                    cardPassangerChildFlight3.startAnimation(animShake);
                    getar();
                    return;
                }
            }
        }
        if (4 <= countChild) {
            if (cardPassangerChildFlight4.getVisibility() == View.VISIBLE) {
                if (textContentChildFlight4.getTag(R.id.nameChild4) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.flight_penumpang_anak_4_kosong, "", 4);
                    cardPassangerChildFlight4.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerChildFlight4.setAnimation(animShake);
                    cardPassangerChildFlight4.startAnimation(animShake);
                    getar();
                    return;
                }
            }
        }
        if (5 <= countChild) {
            if (cardPassangerChildFlight5.getVisibility() == View.VISIBLE) {
                if (textContentChildFlight5.getTag(R.id.nameChild5) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.flight_penumpang_anak_5_kosong, "", 5);
                    cardPassangerChildFlight5.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerChildFlight5.setAnimation(animShake);
                    cardPassangerChildFlight5.startAnimation(animShake);
                    getar();
                    return;
                }
            }
        }
        if (6 <= countChild) {
            if (cardPassangerChildFlight6.getVisibility() == View.VISIBLE) {
                if (textContentChildFlight6.getTag(R.id.nameChild6) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.flight_penumpang_anak_6_kosong, "", 6);
                    cardPassangerChildFlight6.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerChildFlight6.setAnimation(animShake);
                    cardPassangerChildFlight6.startAnimation(animShake);
                    getar();
                    return;
                }
            }
        }
        if (1 <= countInfant) {
            if (cardPassangerInfantFlight1.getVisibility() == View.VISIBLE) {
                if (textContentInfantFlight1.getTag(R.id.nameInfant1) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.flight_penumpang_bayi_1_kosong, "", 1);
                    cardPassangerInfantFlight1.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerInfantFlight1.setAnimation(animShake);
                    cardPassangerInfantFlight1.startAnimation(animShake);
                    getar();
                    return;
                }
            }
        }
        if (2 <= countInfant) {
            if (cardPassangerInfantFlight2.getVisibility() == View.VISIBLE) {
                if (textContentInfantFlight2.getTag(R.id.nameInfant2) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.flight_penumpang_bayi_2_kosong, "", 2);
                    cardPassangerInfantFlight2.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerInfantFlight2.setAnimation(animShake);
                    cardPassangerInfantFlight2.startAnimation(animShake);
                    getar();
                    return;
                }
            }
        }
        if (3 <= countInfant) {
            if (cardPassangerInfantFlight3.getVisibility() == View.VISIBLE) {
                if (textContentInfantFlight3.getTag(R.id.nameInfant3) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.flight_penumpang_bayi_3_kosong, "", 3);
                    cardPassangerInfantFlight3.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerInfantFlight3.setAnimation(animShake);
                    cardPassangerInfantFlight3.startAnimation(animShake);
                    getar();
                    return;
                }
            }
        }
        if (4 <= countInfant) {
            if (cardPassangerInfantFlight4.getVisibility() == View.VISIBLE) {
                if (textContentInfantFlight4.getTag(R.id.nameInfant4) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.flight_penumpang_bayi_4_kosong, "", 4);
                    cardPassangerInfantFlight4.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerInfantFlight4.setAnimation(animShake);
                    cardPassangerInfantFlight4.startAnimation(animShake);
                    getar();
                    return;
                }
            }
        }

        if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
            new_popup_alertDemo(this, "Info", "Anda belum bisa menikmati fiture ini.\n" +
                    "Daftar & Aktifasi sekarang juga ID Anda");

        } else {
            click_pesan_flight();
        }
    }


}

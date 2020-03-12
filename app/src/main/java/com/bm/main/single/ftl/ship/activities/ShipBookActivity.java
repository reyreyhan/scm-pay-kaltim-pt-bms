package com.bm.main.single.ftl.ship.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatButton;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.bm.main.single.ftl.ship.constants.ShipKeyPreference;
import com.bm.main.single.ftl.ship.constants.ShipPath;
import com.bm.main.single.ftl.ship.models.ShipFareModel;
import com.bm.main.single.ftl.ship.models.ShipModel;
import com.bm.main.single.ftl.utils.RequestUtilsTravel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class ShipBookActivity extends BaseActivity implements ProgressResponseCallback, View.OnClickListener {
    private static final String TAG = ShipBookActivity.class.getSimpleName();

    TextView textShipName, textShipKelas, textPriceAdult, textPriceInfant, textOrigin, textDestination, textTimeDept, textTimeArr, textDateDept, textDateArr, textRoute,textViewTotalBook;

    TextView noUrutAdult1, noUrutAdult2, noUrutAdult3, noUrutAdult4, noUrutAdult5, noUrutAdult6, noUrutAdult7, noUrutAdult8, noUrutAdult9, noUrutAdult10;
    TextView textContentAdult1, textContentAdult2, textContentAdult3, textContentAdult4, textContentAdult5, textContentAdult6, textContentAdult7, textContentAdult8, textContentAdult9, textContentAdult10;
    TextView textActionAdult1, textActionAdult2, textActionAdult3, textActionAdult4, textActionAdult5, textActionAdult6, textActionAdult7, textActionAdult8, textActionAdult9, textActionAdult10;

    ImageView imageActionAdult1, imageActionAdult2, imageActionAdult3, imageActionAdult4, imageActionAdult5, imageActionAdult6, imageActionAdult7, imageActionAdult8, imageActionAdult9, imageActionAdult10;


    TextView noUrutInfant1, noUrutInfant2, noUrutInfant3, noUrutInfant4, noUrutInfant5;
    TextView textContentInfant1, textContentInfant2, textContentInfant3, textContentInfant4, textContentInfant5;
    TextView textActionInfant1, textActionInfant2, textActionInfant3, textActionInfant4, textActionInfant5;
    ImageView imageActionInfant1, imageActionInfant2, imageActionInfant3, imageActionInfant4, imageActionInfant5;

    RelativeLayout cardPassangerAdult1, cardPassangerAdult2, cardPassangerAdult3, cardPassangerAdult4, cardPassangerAdult5, cardPassangerAdult6, cardPassangerAdult7, cardPassangerAdult8, cardPassangerAdult9, cardPassangerAdult10;

    RelativeLayout cardPassangerInfant1, cardPassangerInfant2, cardPassangerInfant3, cardPassangerInfant4, cardPassangerInfant5;

    AppCompatButton btnPesan;
   // String price = "0", timeArr, timeDept, deptDate, arrDate;
    int countAdult = 1, countInfant = 0;
    String adultPassengger[];
    //    String childPassengger[];
    String infantPassengger[];
    MaterialEditText textHpKontak, textEmailKontak;
    public ShipBookActivity instance;
    @NonNull
    String sJson = "";
//    ScrollView scrollMainBook;
//    RelativeLayout loadingPage;
//    FrameLayout frameBottom;

    LinearLayout linInfantPrice;

    LinearLayout rlPassangerAdultShip1, rlPassangerAdultShip2, rlPassangerAdultShip3, rlPassangerAdultShip4, rlPassangerAdultShip5, rlPassangerAdultShip6, rlPassangerAdultShip7, rlPassangerAdultShip8, rlPassangerAdultShip9, rlPassangerAdultShip10;
    LinearLayout rlPassangerInfantShip1, rlPassangerInfantShip2, rlPassangerInfantShip3, rlPassangerInfantShip4, rlPassangerInfantShip5;


    ImageView imageViewExpandPassangerAdultShip1, imageViewExpandPassangerAdultShip2, imageViewExpandPassangerAdultShip3, imageViewExpandPassangerAdultShip4, imageViewExpandPassangerAdultShip5, imageViewExpandPassangerAdultShip6, imageViewExpandPassangerAdultShip7, imageViewExpandPassangerAdultShip8, imageViewExpandPassangerAdultShip9, imageViewExpandPassangerAdultShip10;


    ImageView imageViewExpandPassangerInfantShip1, imageViewExpandPassangerInfantShip2, imageViewExpandPassangerInfantShip3, imageViewExpandPassangerInfantShip4, imageViewExpandPassangerInfantShip5;

    ExpandablePanel panelPassangerAdultShip1Detail, panelPassangerAdultShip2Detail, panelPassangerAdultShip3Detail, panelPassangerAdultShip4Detail, panelPassangerAdultShip5Detail, panelPassangerAdultShip6Detail, panelPassangerAdultShip7Detail, panelPassangerAdultShip8Detail, panelPassangerAdultShip9Detail, panelPassangerAdultShip10Detail;

    ExpandablePanel panelPassangerInfantShip1Detail, panelPassangerInfantShip2Detail, panelPassangerInfantShip3Detail, panelPassangerInfantShip4Detail, panelPassangerInfantShip5Detail;


    TextView textViewNoIdentitasAdultShip1, textViewNoIdentitasAdultShip2, textViewNoIdentitasAdultShip3, textViewNoIdentitasAdultShip4, textViewNoIdentitasAdultShip5, textViewNoIdentitasAdultShip6, textViewNoIdentitasAdultShip7, textViewNoIdentitasAdultShip8, textViewNoIdentitasAdultShip9, textViewNoIdentitasAdultShip10;

   // TextView textViewMobileAdultShip1, textViewMobileAdultShip2, textViewMobileAdultShip3, textViewMobileAdultShip4, textViewMobileAdultShip5, textViewMobileAdultShip6, textViewMobileAdultShip7, textViewMobileAdultShip8, textViewMobileAdultShip9, textViewMobileAdultShip10;

    TextView textViewTanggalLahirInfantShip1, textViewTanggalLahirInfantShip2, textViewTanggalLahirInfantShip3, textViewTanggalLahirInfantShip4, textViewTanggalLahirInfantShip5;
//    private ShipDataModel dataSchedule;

    Context context;

    @Nullable
    ShipFareModel shipFareModel = null;
    @Nullable
    ShipModel shipModel = null;
    public boolean isFamily = false;

    CheckBox checkboxIsFamiliy;
    private String shipFare="";
    private String ship="";


    LinearLayout linContentAdultShip1,linContentAdultShip2,linContentAdultShip3,linContentAdultShip4,linContentAdultShip5,linContentAdultShip6,linContentAdultShip7,linContentAdultShip8,linContentAdultShip9,linContentAdultShip10;
    LinearLayout linContentInfantShip1,linContentInfantShip2,linContentInfantShip3,linContentInfantShip4,linContentInfantShip5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ship_book_activity);
        context = this;
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Lengkapi Data");
        init(0);
//        scrollMainBook = findViewById(R.id.scrollMainBook);
//        frameBottom = findViewById(R.id.frameBottomShip);
      //  loadingPage = findViewById(R.id.loading_view_ship);
        Intent intent = this.getIntent();
        //    Bundle b = getIntent().getExtras();

        if (intent != null)
//            loadingPage.setVisibility(View.VISIBLE);
//        scrollMainBook.setVisibility(View.GONE);
//        frameBottom.setVisibility(View.GONE);
             shipFare = intent.getStringExtra("shipFare");

            // Log.d(TAG, "onCreate: shipFare "+shipFare);
             ship = intent.getStringExtra("ship");
            // Log.d(TAG, "onCreate: ship "+ship);

            try {
                JSONObject jsonShipFare = new JSONObject(shipFare);
                JSONObject jsonShip = new JSONObject(ship);
                shipFareModel = new ShipFareModel(jsonShipFare);
                shipModel = new ShipModel(jsonShip);
            } catch (JSONException e) {
                e.printStackTrace();
            }



        String kelas = shipFareModel.getCLASS();
        String subKelas = shipFareModel.getSUBCLASS();
        double priceAdult = shipFareModel.getFareDetailA().getTOTAL();
        double priceInfant = shipFareModel.getFareDetailI().getTOTAL();
        String shipName = shipModel.getSHIP_NAME();
        String shipNumber = shipModel.getSHIP_NO();





        String[] routes = shipModel.getROUTE().split("/");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", SBFApplication.config.locale);
        SimpleDateFormat odf = new SimpleDateFormat("EEEE,dd MMMM yyyy", SBFApplication.config.locale);
        String routeAll;
        try {
            String routeText = "";
            JSONObject obj = PreferenceClass.getJSONObject(ShipKeyPreference.portListData);

            JSONArray destinationJson = new JSONArray();
            // JSONArray destinationJson = new JSONArray(SavePref.getInstance(getApplicationContext()).getString("shipDestinationList"));
            try {

                destinationJson = obj.getJSONArray("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (String route1 : routes) {
                String route = route1;
                if (route.length() < 3)
                    continue;

                if (route.length() > 3) {
                    route = route.split("-")[1];
                }

                for (int j = 0; j < destinationJson.length(); j++) {
                    JSONObject jsonObject = destinationJson.getJSONObject(j);
                    String code = jsonObject.getString("CODE");
                    String name = jsonObject.getString("NAME");

                    if (code.equals(route)) {
                        routeText += name + " - ";
                        break;
                    }
                }
            }
            routeAll = routeText.substring(0, routeText.length() - 2);
        } catch (JSONException e) {
            e.printStackTrace();
            routeAll = "-";
        }

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
//        textViewTibaKapal.setText("Tiba " + arvDate + " Pukul " + arvTime.substring(0, 2) + ":" + arvTime.substring(2, 4));
//        textViewBerangkatKapal.setText("Berangkat " + depDate + " Pukul " + depTime.substring(0, 2) + ":" + depTime.substring(2, 4));


        textShipName = findViewById(R.id.textViewNamaKapalBook);
        textShipKelas = findViewById(R.id.textViewKelasBook);
        textPriceAdult = findViewById(R.id.textViewPriceAdultBook);
        textPriceInfant = findViewById(R.id.textViewPriceInfantBook);
        textOrigin = findViewById(R.id.textOrigin);
        textDestination = findViewById(R.id.textDestination);
        textTimeDept = findViewById(R.id.textDepartureTime);
        textTimeArr = findViewById(R.id.textArrivalTime);
        textDateDept = findViewById(R.id.textDepartureDate);
        textDateArr = findViewById(R.id.textArrivalDate);
        textViewTotalBook = findViewById(R.id.textViewTotalBook);
        textRoute = findViewById(R.id.textViewRuteBook);
        linInfantPrice = findViewById(R.id.linInfantPrice);


        rlPassangerAdultShip1 = findViewById(R.id.rlPassangerAdultShip1);
        cardPassangerAdult1 = findViewById(R.id.cardPassangerAdultShip1);

        noUrutAdult1 = findViewById(R.id.textUrutAdultShip1);
        textContentAdult1 = findViewById(R.id.textContentAdultShip1);
        textActionAdult1 = findViewById(R.id.textActionAdultShip1);
        imageActionAdult1 = findViewById(R.id.imageActionAdultShip1);
        linContentAdultShip1 = findViewById(R.id.linContentAdultShip1);
        linContentAdultShip1.setOnClickListener(this);
//        textActionAdult1.setOnClickListener(this);
//        imageActionAdult1.setOnClickListener(this);

        imageViewExpandPassangerAdultShip1 = findViewById(R.id.imageViewExpandPassangerAdultShip1);
        imageViewExpandPassangerAdultShip1.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_adult));
//        imageViewExpandPassangerAdult1.setVisibility(View.GONE);
        panelPassangerAdultShip1Detail = findViewById(R.id.panelPassangerAdultShip1Detail);

        panelPassangerAdultShip1Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            public void onCollapse(View handle, View content) {
                ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                imageView.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_expand));
            }

            public void onExpand(View handle, View content) {
                ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                imageView.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_collapse));
            }
        });

        rlPassangerAdultShip2 = findViewById(R.id.rlPassangerAdultShip2);
        cardPassangerAdult2 = findViewById(R.id.cardPassangerAdultShip2);

        noUrutAdult2 = findViewById(R.id.textUrutAdultShip2);
        textContentAdult2 = findViewById(R.id.textContentAdultShip2);
        textActionAdult2 = findViewById(R.id.textActionAdultShip2);
        imageActionAdult2 = findViewById(R.id.imageActionAdultShip2);
        linContentAdultShip2 = findViewById(R.id.linContentAdultShip2);
        linContentAdultShip2.setOnClickListener(this);
//        textActionAdult2.setOnClickListener(this);
//
//        imageActionAdult2.setOnClickListener(this);

        imageViewExpandPassangerAdultShip2 = findViewById(R.id.imageViewExpandPassangerAdultShip2);
        imageViewExpandPassangerAdultShip2.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_adult));
//        imageViewExpandPassangerAdult1.setVisibility(View.GONE);
        panelPassangerAdultShip2Detail = findViewById(R.id.panelPassangerAdultShip2Detail);

        panelPassangerAdultShip2Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            public void onCollapse(View handle, View content) {
                ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                imageView.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_expand));
            }

            public void onExpand(View handle, View content) {
                ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                imageView.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_collapse));
            }
        });


        rlPassangerAdultShip3 = findViewById(R.id.rlPassangerAdultShip3);
        cardPassangerAdult3 = findViewById(R.id.cardPassangerAdultShip3);

        noUrutAdult3 = findViewById(R.id.textUrutAdultShip3);
        textContentAdult3 = findViewById(R.id.textContentAdultShip3);
        textActionAdult3 = findViewById(R.id.textActionAdultShip3);
        imageActionAdult3 = findViewById(R.id.imageActionAdultShip3);
        linContentAdultShip3 = findViewById(R.id.linContentAdultShip3);
        linContentAdultShip3.setOnClickListener(this);
//        textActionAdult3.setOnClickListener(this);
//        imageActionAdult3.setOnClickListener(this);

        imageViewExpandPassangerAdultShip3 = findViewById(R.id.imageViewExpandPassangerAdultShip3);
        imageViewExpandPassangerAdultShip3.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_adult));
//        imageViewExpandPassangerAdult1.setVisibility(View.GONE);
        panelPassangerAdultShip3Detail = findViewById(R.id.panelPassangerAdultShip3Detail);

        panelPassangerAdultShip3Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            public void onCollapse(View handle, View content) {
                ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                imageView.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_expand));
            }

            public void onExpand(View handle, View content) {
                ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                imageView.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_collapse));
            }
        });


        rlPassangerAdultShip4 = findViewById(R.id.rlPassangerAdultShip4);
        cardPassangerAdult4 = findViewById(R.id.cardPassangerAdultShip4);

        noUrutAdult4 = findViewById(R.id.textUrutAdultShip4);
        textContentAdult4 = findViewById(R.id.textContentAdultShip4);
        textActionAdult4 = findViewById(R.id.textActionAdultShip4);
        imageActionAdult4 = findViewById(R.id.imageActionAdultShip4);
        linContentAdultShip4 = findViewById(R.id.linContentAdultShip4);
        linContentAdultShip4.setOnClickListener(this);
//        textActionAdult4.setOnClickListener(this);
//        imageActionAdult4.setOnClickListener(this);

        imageViewExpandPassangerAdultShip4 = findViewById(R.id.imageViewExpandPassangerAdultShip4);
        imageViewExpandPassangerAdultShip4.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_adult));
//        imageViewExpandPassangerAdult1.setVisibility(View.GONE);
        panelPassangerAdultShip4Detail = findViewById(R.id.panelPassangerAdultShip4Detail);

        panelPassangerAdultShip4Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            public void onCollapse(View handle, View content) {
                ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                imageView.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_expand));
            }

            public void onExpand(View handle, View content) {
                ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                imageView.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_collapse));
            }
        });


        rlPassangerAdultShip5 = findViewById(R.id.rlPassangerAdultShip5);
        cardPassangerAdult5 = findViewById(R.id.cardPassangerAdultShip5);

        noUrutAdult5 = findViewById(R.id.textUrutAdultShip5);
        textContentAdult5 = findViewById(R.id.textContentAdultShip5);
        textActionAdult5 = findViewById(R.id.textActionAdultShip5);
        imageActionAdult5 = findViewById(R.id.imageActionAdultShip5);
        linContentAdultShip5 = findViewById(R.id.linContentAdultShip5);
        linContentAdultShip5.setOnClickListener(this);
//        textActionAdult5.setOnClickListener(this);
//        imageActionAdult5.setOnClickListener(this);

        imageViewExpandPassangerAdultShip5 = findViewById(R.id.imageViewExpandPassangerAdultShip5);
        imageViewExpandPassangerAdultShip5.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_adult));
//        imageViewExpandPassangerAdult1.setVisibility(View.GONE);
        panelPassangerAdultShip5Detail = findViewById(R.id.panelPassangerAdultShip5Detail);

        panelPassangerAdultShip5Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            public void onCollapse(View handle, View content) {
                ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                imageView.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_expand));
            }

            public void onExpand(View handle, View content) {
                ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                imageView.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_collapse));
            }
        });


        rlPassangerAdultShip6 = findViewById(R.id.rlPassangerAdultShip6);
        cardPassangerAdult6 = findViewById(R.id.cardPassangerAdultShip6);

        noUrutAdult6 = findViewById(R.id.textUrutAdultShip6);
        textContentAdult6 = findViewById(R.id.textContentAdultShip6);
        textActionAdult6 = findViewById(R.id.textActionAdultShip6);
        imageActionAdult6 = findViewById(R.id.imageActionAdultShip6);
        linContentAdultShip6 = findViewById(R.id.linContentAdultShip6);
        linContentAdultShip6.setOnClickListener(this);
//        textActionAdult6.setOnClickListener(this);
//        imageActionAdult6.setOnClickListener(this);

        imageViewExpandPassangerAdultShip6 = findViewById(R.id.imageViewExpandPassangerAdultShip6);
        imageViewExpandPassangerAdultShip6.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_adult));
//        imageViewExpandPassangerAdult1.setVisibility(View.GONE);
        panelPassangerAdultShip6Detail = findViewById(R.id.panelPassangerAdultShip6Detail);

        panelPassangerAdultShip6Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            public void onCollapse(View handle, View content) {
                ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                imageView.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_expand));
            }

            public void onExpand(View handle, View content) {
                ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                imageView.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_collapse));
            }
        });


        rlPassangerAdultShip7 = findViewById(R.id.rlPassangerAdultShip7);
        cardPassangerAdult7 = findViewById(R.id.cardPassangerAdultShip7);

        noUrutAdult7 = findViewById(R.id.textUrutAdultShip7);
        textContentAdult7 = findViewById(R.id.textContentAdultShip7);
        textActionAdult7 = findViewById(R.id.textActionAdultShip7);
        imageActionAdult7 = findViewById(R.id.imageActionAdultShip7);
        linContentAdultShip7 = findViewById(R.id.linContentAdultShip7);
        linContentAdultShip7.setOnClickListener(this);
//        textActionAdult7.setOnClickListener(this);
//        imageActionAdult7.setOnClickListener(this);

        imageViewExpandPassangerAdultShip7 = findViewById(R.id.imageViewExpandPassangerAdultShip7);
        imageViewExpandPassangerAdultShip7.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_adult));
//        imageViewExpandPassangerAdult1.setVisibility(View.GONE);
        panelPassangerAdultShip7Detail = findViewById(R.id.panelPassangerAdultShip7Detail);

        panelPassangerAdultShip7Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            public void onCollapse(View handle, View content) {
                ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                imageView.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_expand));
            }

            public void onExpand(View handle, View content) {
                ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                imageView.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_collapse));
            }
        });

        rlPassangerAdultShip8 = findViewById(R.id.rlPassangerAdultShip8);
        cardPassangerAdult8 = findViewById(R.id.cardPassangerAdultShip8);

        noUrutAdult8 = findViewById(R.id.textUrutAdultShip8);
        textContentAdult8 = findViewById(R.id.textContentAdultShip8);
        textActionAdult8 = findViewById(R.id.textActionAdultShip8);
        imageActionAdult8 = findViewById(R.id.imageActionAdultShip8);
        linContentAdultShip8 = findViewById(R.id.linContentAdultShip8);
        linContentAdultShip8.setOnClickListener(this);
//        textActionAdult8.setOnClickListener(this);
//        imageActionAdult8.setOnClickListener(this);

        imageViewExpandPassangerAdultShip8 = findViewById(R.id.imageViewExpandPassangerAdultShip8);
        imageViewExpandPassangerAdultShip8.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_adult));
//        imageViewExpandPassangerAdult1.setVisibility(View.GONE);
        panelPassangerAdultShip8Detail = findViewById(R.id.panelPassangerAdultShip8Detail);

        panelPassangerAdultShip8Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            public void onCollapse(View handle, View content) {
                ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                imageView.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_expand));
            }

            public void onExpand(View handle, View content) {
                ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                imageView.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_collapse));
            }
        });


        rlPassangerAdultShip9 = findViewById(R.id.rlPassangerAdultShip9);
        cardPassangerAdult9 = findViewById(R.id.cardPassangerAdultShip9);

        noUrutAdult9 = findViewById(R.id.textUrutAdultShip9);
        textContentAdult9 = findViewById(R.id.textContentAdultShip9);
        textActionAdult9 = findViewById(R.id.textActionAdultShip9);
        imageActionAdult9 = findViewById(R.id.imageActionAdultShip9);
        linContentAdultShip9 = findViewById(R.id.linContentAdultShip9);
        linContentAdultShip9.setOnClickListener(this);
//        textActionAdult9.setOnClickListener(this);
//        imageActionAdult9.setOnClickListener(this);

        imageViewExpandPassangerAdultShip9 = findViewById(R.id.imageViewExpandPassangerAdultShip9);
        imageViewExpandPassangerAdultShip9.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_adult));
//        imageViewExpandPassangerAdult1.setVisibility(View.GONE);
        panelPassangerAdultShip9Detail = findViewById(R.id.panelPassangerAdultShip9Detail);

        panelPassangerAdultShip9Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            public void onCollapse(View handle, View content) {
                ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                imageView.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_expand));
            }

            public void onExpand(View handle, View content) {
                ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                imageView.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_collapse));
            }
        });


        rlPassangerAdultShip10 = findViewById(R.id.rlPassangerAdultShip10);
        cardPassangerAdult10 = findViewById(R.id.cardPassangerAdultShip10);

        noUrutAdult10 = findViewById(R.id.textUrutAdultShip10);
        textContentAdult10 = findViewById(R.id.textContentAdultShip10);
        textActionAdult10 = findViewById(R.id.textActionAdultShip10);
        imageActionAdult10 = findViewById(R.id.imageActionAdultShip10);
        linContentAdultShip10 = findViewById(R.id.linContentAdultShip10);
        linContentAdultShip10.setOnClickListener(this);
//        textActionAdult10.setOnClickListener(this);
//        imageActionAdult10.setOnClickListener(this);

        imageViewExpandPassangerAdultShip10 = findViewById(R.id.imageViewExpandPassangerAdultShip10);
        imageViewExpandPassangerAdultShip10.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_adult));
//        imageViewExpandPassangerAdult1.setVisibility(View.GONE);
        panelPassangerAdultShip10Detail = findViewById(R.id.panelPassangerAdultShip10Detail);

        panelPassangerAdultShip10Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            public void onCollapse(View handle, View content) {
                ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                imageView.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_expand));
            }

            public void onExpand(View handle, View content) {
                ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                imageView.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_collapse));
            }
        });


        rlPassangerInfantShip1 = findViewById(R.id.rlPassangerInfantShip1);
        cardPassangerInfant1 = findViewById(R.id.cardPassangerInfantShip1);

        noUrutInfant1 = findViewById(R.id.textUrutInfantShip1);
        textContentInfant1 = findViewById(R.id.textContentInfantShip1);
        textActionInfant1 = findViewById(R.id.textActionInfantShip1);
        imageActionInfant1 = findViewById(R.id.imageActionInfantShip1);
        linContentInfantShip1 = findViewById(R.id.linContentInfantShip1);
        linContentInfantShip1.setOnClickListener(this);
//        textActionInfant1.setOnClickListener(this);
//        imageActionInfant1.setOnClickListener(this);

        imageViewExpandPassangerInfantShip1 = findViewById(R.id.imageViewExpandPassangerInfantShip1);
        imageViewExpandPassangerInfantShip1.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_baby));
//        imageViewExpandPassangerAdult1.setVisibility(View.GONE);
        panelPassangerInfantShip1Detail = findViewById(R.id.panelPassangerInfantShip1Detail);

        panelPassangerInfantShip1Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            public void onCollapse(View handle, View content) {
                ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                imageView.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_expand));
            }

            public void onExpand(View handle, View content) {
                ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                imageView.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_collapse));
            }
        });


        rlPassangerInfantShip2 = findViewById(R.id.rlPassangerInfantShip2);
        cardPassangerInfant2 = findViewById(R.id.cardPassangerInfantShip2);

        noUrutInfant2 = findViewById(R.id.textUrutInfantShip2);
        textContentInfant2 = findViewById(R.id.textContentInfantShip2);
        textActionInfant2 = findViewById(R.id.textActionInfantShip2);
        imageActionInfant2 = findViewById(R.id.imageActionInfantShip2);
        linContentInfantShip2 = findViewById(R.id.linContentInfantShip2);
        linContentInfantShip2.setOnClickListener(this);
//        textActionInfant2.setOnClickListener(this);
//        imageActionInfant2.setOnClickListener(this);

        imageViewExpandPassangerInfantShip2 = findViewById(R.id.imageViewExpandPassangerInfantShip2);
        imageViewExpandPassangerInfantShip2.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_baby));
//        imageViewExpandPassangerAdult1.setVisibility(View.GONE);
        panelPassangerInfantShip2Detail = findViewById(R.id.panelPassangerInfantShip2Detail);

        panelPassangerInfantShip2Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            public void onCollapse(View handle, View content) {
                ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                imageView.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_expand));
            }

            public void onExpand(View handle, View content) {
                ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                imageView.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_collapse));
            }
        });


        rlPassangerInfantShip3 = findViewById(R.id.rlPassangerInfantShip3);
        cardPassangerInfant3 = findViewById(R.id.cardPassangerInfantShip3);
//        cardPassangerInfant3.setOnClickListener(this);
        noUrutInfant3 = findViewById(R.id.textUrutInfantShip3);
        textContentInfant3 = findViewById(R.id.textContentInfantShip3);
        textActionInfant3 = findViewById(R.id.textActionInfantShip3);
        imageActionInfant3 = findViewById(R.id.imageActionInfantShip3);
        linContentInfantShip3 = findViewById(R.id.linContentInfantShip3);
        linContentInfantShip3.setOnClickListener(this);
//        textActionInfant3.setOnClickListener(this);
//        imageActionInfant3.setOnClickListener(this);

        imageViewExpandPassangerInfantShip3 = findViewById(R.id.imageViewExpandPassangerInfantShip3);
        imageViewExpandPassangerInfantShip3.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_baby));
//        imageViewExpandPassangerAdult1.setVisibility(View.GONE);
        panelPassangerInfantShip3Detail = findViewById(R.id.panelPassangerInfantShip3Detail);

        panelPassangerInfantShip3Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            public void onCollapse(View handle, View content) {
                ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                imageView.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_expand));
            }

            public void onExpand(View handle, View content) {
                ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                imageView.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_collapse));
            }
        });


        rlPassangerInfantShip4 = findViewById(R.id.rlPassangerInfantShip4);
        cardPassangerInfant4 = findViewById(R.id.cardPassangerInfantShip4);
//        cardPassangerInfant4.setOnClickListener(this);
        noUrutInfant4 = findViewById(R.id.textUrutInfantShip4);
        textContentInfant4 = findViewById(R.id.textContentInfantShip4);
        textActionInfant4 = findViewById(R.id.textActionInfantShip4);
        imageActionInfant4 = findViewById(R.id.imageActionInfantShip4);
        linContentInfantShip4 = findViewById(R.id.linContentInfantShip4);
        linContentInfantShip4.setOnClickListener(this);
//        textActionInfant4.setOnClickListener(this);
//        imageActionInfant4.setOnClickListener(this);

        imageViewExpandPassangerInfantShip4 = findViewById(R.id.imageViewExpandPassangerInfantShip4);
        imageViewExpandPassangerInfantShip4.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_baby));
//        imageViewExpandPassangerAdult1.setVisibility(View.GONE);
        panelPassangerInfantShip4Detail = findViewById(R.id.panelPassangerInfantShip4Detail);

        panelPassangerInfantShip4Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            public void onCollapse(View handle, View content) {
                ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                imageView.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_expand));
            }

            public void onExpand(View handle, View content) {
                ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                imageView.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_collapse));
            }
        });

        rlPassangerInfantShip5 = findViewById(R.id.rlPassangerInfantShip5);
        cardPassangerInfant5 = findViewById(R.id.cardPassangerInfantShip5);
//        cardPassangerInfant5.setOnClickListener(this);
        noUrutInfant5 = findViewById(R.id.textUrutInfantShip5);
        textContentInfant5 = findViewById(R.id.textContentInfantShip5);
        textActionInfant5 = findViewById(R.id.textActionInfantShip5);
        imageActionInfant5 = findViewById(R.id.imageActionInfantShip5);
        linContentInfantShip5 = findViewById(R.id.linContentInfantShip5);
        linContentInfantShip5.setOnClickListener(this);
//        textActionInfant5.setOnClickListener(this);
//        imageActionInfant5.setOnClickListener(this);

        imageViewExpandPassangerInfantShip5 = findViewById(R.id.imageViewExpandPassangerInfantShip5);
        imageViewExpandPassangerInfantShip5.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_baby));
//        imageViewExpandPassangerAdult1.setVisibility(View.GONE);
        panelPassangerInfantShip5Detail = findViewById(R.id.panelPassangerInfantShip5Detail);

        panelPassangerInfantShip5Detail.setOnExpandListener(new ExpandablePanel.OnExpandListener() {
            public void onCollapse(View handle, View content) {
                ImageView imageView = (ImageView) handle;
////                btn.setText("More");
                imageView.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_expand));
            }

            public void onExpand(View handle, View content) {
                ImageView imageView = (ImageView) handle;
//                btn.setText("Less");
                imageView.setBackground(ContextCompat.getDrawable(ShipBookActivity.this, R.drawable.ic_collapse));
            }
        });


        textHpKontak = findViewById(R.id.textHpKontak);
        textHpKontak.setText(PreferenceClass.getUser().getNotelp_pemilik().replace("+62", "0"));
        textEmailKontak = findViewById(R.id.textEmailKontak);
        textEmailKontak.setText(PreferenceClass.getUser().getEmail_pemilik());

        btnPesan = findViewById(R.id.btn_pesan);
//        MaterialRippleLayout.on(btnPesan).rippleOverlay(true)
//                .rippleAlpha(0.2f)
//                //.rippleColor(0xFF585858)
//                .rippleColor(R.color.md_orange_200)
//                .rippleHover(true)
//                .create();
        btnPesan.setOnClickListener(this);

        textOrigin.setText(PreferenceClass.getString(ShipKeyPreference.pelabuhanNamaAsal, ""));
        textDestination.setText(PreferenceClass.getString(ShipKeyPreference.pelabuhanNamaTujuan, ""));

//        PreferenceClass.putString(ShipKeyPreference.shipClass,dataSchedule.getCLASS());
//        PreferenceClass.putString(ShipKeyPreference.shipSubClass,dataSchedule.getSUBCLASS());
//        PreferenceClass.putInt(ShipKeyPreference.shipPriceAdult, (int) dataSchedule.getFareDetailA().getTOTAL());
//        PreferenceClass.putInt(ShipKeyPreference.shipPriceInfant, (int) dataSchedule.getFareDetailI().getTOTAL());
//        PreferenceClass.putString(ShipKeyPreference.shipName,  dataSchedule.getShipModel().getSHIP_NAME());
//        PreferenceClass.putString(ShipKeyPreference.shipNo, dataSchedule.getShipModel().getSHIP_NO());
        textShipName.setText(shipName + " (" + shipNumber + ")");
        textShipKelas.setText(kelas + "/ " + subKelas);

        textTimeDept.setText(depTime.substring(0, 2) + ":" + depTime.substring(2, 4));
        textTimeArr.setText(arvTime.substring(0, 2) + ":" + arvTime.substring(2, 4));
        //  textTimeDept.setText(timeDept);
        //  textTimeArr.setText(timeArr);


//        SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy", config.locale);
//        Date dateDept = null;
//        try {
//            dateDept = fmt.parse(depDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        Date dateArr = null;
//        try {
//            dateArr = fmt.parse(arrDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        SimpleDateFormat sdf = new SimpleDateFormat("EEEE,dd MMM yyyy", config.locale);
//        deptDate = sdf.format(dateDept);
//        arrDate = sdf.format(dateArr);

        textDateDept.setText(depDate);
        textDateArr.setText(arvDate);
        textRoute.setText("Rute " + routeAll);

//        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
//        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
//        formatRp.setCurrencySymbol("Rp. ");
//        formatRp.setGroupingSeparator('.');
//        kursIndonesia.setDecimalFormatSymbols(formatRp);
        countAdult = PreferenceClass.getInt(ShipKeyPreference.countAdultShip, 1);
        // countChild = PreferenceClass.getInt(ShipKeyPreference.countChildShip, 0);
        countInfant = PreferenceClass.getInt(ShipKeyPreference.countInfantShip, 0);

        textPriceAdult.setText(countAdult+" X Rp. " + String.format("%,.0f", priceAdult));
        if(countInfant==0){
            linInfantPrice.setVisibility(View.GONE);
        }else {
            linInfantPrice.setVisibility(View.VISIBLE);
            textPriceInfant.setText(countInfant+" X Rp. " + String.format("%,.0f", priceInfant));
        }

        double totalA=priceAdult*countAdult;
        double totalI=priceInfant*countInfant;
        double total=totalA+totalI;

        textViewTotalBook.setText("Rp. " + String.format("%,.0f", total));




        Log.d(TAG, "onCreate: " + countAdult + " " + countInfant);
        adultPassengger = new String[countAdult];
        // childPassengger = new String[countChild];
        infantPassengger = new String[countInfant];

        if (countAdult == 2) {

            cardPassangerAdult2.setVisibility(View.VISIBLE);

        } else if (countAdult == 3) {
            cardPassangerAdult2.setVisibility(View.VISIBLE);
            cardPassangerAdult3.setVisibility(View.VISIBLE);
        } else if (countAdult == 4) {
            cardPassangerAdult2.setVisibility(View.VISIBLE);
            cardPassangerAdult3.setVisibility(View.VISIBLE);
            cardPassangerAdult4.setVisibility(View.VISIBLE);
        } else if (countAdult == 5) {
            cardPassangerAdult2.setVisibility(View.VISIBLE);
            cardPassangerAdult3.setVisibility(View.VISIBLE);
            cardPassangerAdult4.setVisibility(View.VISIBLE);
            cardPassangerAdult5.setVisibility(View.VISIBLE);
        } else if (countAdult == 6) {
            cardPassangerAdult2.setVisibility(View.VISIBLE);
            cardPassangerAdult3.setVisibility(View.VISIBLE);
            cardPassangerAdult4.setVisibility(View.VISIBLE);
            cardPassangerAdult5.setVisibility(View.VISIBLE);
            cardPassangerAdult6.setVisibility(View.VISIBLE);
        } else if (countAdult == 7) {
            cardPassangerAdult2.setVisibility(View.VISIBLE);
            cardPassangerAdult3.setVisibility(View.VISIBLE);
            cardPassangerAdult4.setVisibility(View.VISIBLE);
            cardPassangerAdult5.setVisibility(View.VISIBLE);
            cardPassangerAdult6.setVisibility(View.VISIBLE);
            cardPassangerAdult7.setVisibility(View.VISIBLE);
        } else if (countAdult == 8) {
            cardPassangerAdult2.setVisibility(View.VISIBLE);
            cardPassangerAdult3.setVisibility(View.VISIBLE);
            cardPassangerAdult4.setVisibility(View.VISIBLE);
            cardPassangerAdult5.setVisibility(View.VISIBLE);
            cardPassangerAdult6.setVisibility(View.VISIBLE);
            cardPassangerAdult7.setVisibility(View.VISIBLE);
            cardPassangerAdult8.setVisibility(View.VISIBLE);
        } else if (countAdult == 9) {
            cardPassangerAdult2.setVisibility(View.VISIBLE);
            cardPassangerAdult3.setVisibility(View.VISIBLE);
            cardPassangerAdult4.setVisibility(View.VISIBLE);
            cardPassangerAdult5.setVisibility(View.VISIBLE);
            cardPassangerAdult6.setVisibility(View.VISIBLE);
            cardPassangerAdult7.setVisibility(View.VISIBLE);
            cardPassangerAdult8.setVisibility(View.VISIBLE);
            cardPassangerAdult9.setVisibility(View.VISIBLE);
        } else if (countAdult == 10) {
            cardPassangerAdult2.setVisibility(View.VISIBLE);
            cardPassangerAdult3.setVisibility(View.VISIBLE);
            cardPassangerAdult4.setVisibility(View.VISIBLE);
            cardPassangerAdult5.setVisibility(View.VISIBLE);
            cardPassangerAdult6.setVisibility(View.VISIBLE);
            cardPassangerAdult7.setVisibility(View.VISIBLE);
            cardPassangerAdult8.setVisibility(View.VISIBLE);
            cardPassangerAdult9.setVisibility(View.VISIBLE);
            cardPassangerAdult10.setVisibility(View.VISIBLE);
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
        } else if (countInfant == 5) {
            noUrutInfant1.setText(String.valueOf(countAdult + 1));
            noUrutInfant2.setText(String.valueOf(countAdult + 2));
            noUrutInfant3.setText(String.valueOf(countAdult + 3));
            noUrutInfant4.setText(String.valueOf(countAdult + 4));
            noUrutInfant5.setText(String.valueOf(countAdult + 5));
            cardPassangerInfant1.setVisibility(View.VISIBLE);
            cardPassangerInfant2.setVisibility(View.VISIBLE);
            cardPassangerInfant3.setVisibility(View.VISIBLE);
            cardPassangerInfant4.setVisibility(View.VISIBLE);
            cardPassangerInfant5.setVisibility(View.VISIBLE);
        }


        textViewNoIdentitasAdultShip1 = findViewById(R.id.textViewNoIdentitasAdultShip1);
        textViewNoIdentitasAdultShip2 = findViewById(R.id.textViewNoIdentitasAdultShip2);
        textViewNoIdentitasAdultShip3 = findViewById(R.id.textViewNoIdentitasAdultShip3);
        textViewNoIdentitasAdultShip4 = findViewById(R.id.textViewNoIdentitasAdultShip4);
        textViewNoIdentitasAdultShip5 = findViewById(R.id.textViewNoIdentitasAdultShip5);
        textViewNoIdentitasAdultShip6 = findViewById(R.id.textViewNoIdentitasAdultShip6);
        textViewNoIdentitasAdultShip7 = findViewById(R.id.textViewNoIdentitasAdultShip7);
        textViewNoIdentitasAdultShip8 = findViewById(R.id.textViewNoIdentitasAdultShip8);
        textViewNoIdentitasAdultShip9 = findViewById(R.id.textViewNoIdentitasAdultShip9);
        textViewNoIdentitasAdultShip10 = findViewById(R.id.textViewNoIdentitasAdultShip10);

//        textViewMobileAdultShip1 = findViewById(R.id.textViewMobileAdultShip1);
//        textViewMobileAdultShip2 = findViewById(R.id.textViewMobileAdultShip2);
//        textViewMobileAdultShip3 = findViewById(R.id.textViewMobileAdultShip3);
//        textViewMobileAdultShip4 = findViewById(R.id.textViewMobileAdultShip4);
//        textViewMobileAdultShip5 = findViewById(R.id.textViewMobileAdultShip5);
//        textViewMobileAdultShip6 = findViewById(R.id.textViewMobileAdultShip6);
//        textViewMobileAdultShip7 = findViewById(R.id.textViewMobileAdultShip7);
//        textViewMobileAdultShip8 = findViewById(R.id.textViewMobileAdultShip8);
//        textViewMobileAdultShip9 = findViewById(R.id.textViewMobileAdultShip9);
//        textViewMobileAdultShip10 = findViewById(R.id.textViewMobileAdultShip10);

        textViewTanggalLahirInfantShip1 = findViewById(R.id.textViewTanggalLahirInfantShip1);
        textViewTanggalLahirInfantShip2 = findViewById(R.id.textViewTanggalLahirInfantShip2);
        textViewTanggalLahirInfantShip3 = findViewById(R.id.textViewTanggalLahirInfantShip3);
        textViewTanggalLahirInfantShip4 = findViewById(R.id.textViewTanggalLahirInfantShip4);
        textViewTanggalLahirInfantShip5 = findViewById(R.id.textViewTanggalLahirInfantShip5);
        checkboxIsFamiliy = findViewById(R.id.checkboxIsFamiliy);
        if ((countAdult+countInfant) == 1) {
            checkboxIsFamiliy.setVisibility(View.GONE);
        }

    }




    public synchronized ShipBookActivity getInstancex() {
        return instance;
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

            openTopDialog(true);
        } else if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    boolean isStillRunning = true;

    public void onBackPressed() {
        if (isStillRunning) {
            RequestUtilsTravel.cancelTravel();
        }
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onClick(@NonNull View v) {
        int id = v.getId();
        String tag = v.getTag().toString();
        if (tag.equals("adult")) {
            openFormAdultShip(v);

        } else if (tag.equals("infant")) {
            openFormInfantShip(v);
        } else if (id == R.id.btn_pesan) {


            if (textContentAdult1.getTag(R.id.nameAdult1) == null) {
                snackBarCustomAction(findViewById(R.id.rootLayout), R.string.ship_penumpang_dewasa_1_kosong, "", 1);
                cardPassangerAdult1.setBackgroundResource(R.drawable.shape_card_error);
                cardPassangerAdult1.setAnimation(animShake);
                cardPassangerAdult1.startAnimation(animShake);
                getar();
                return;
            }

            if (cardPassangerAdult2.getVisibility() == View.VISIBLE) {
                if (textContentAdult2.getTag(R.id.nameAdult2) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.ship_penumpang_dewasa_2_kosong, "", 1);
                    cardPassangerAdult2.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerAdult2.setAnimation(animShake);
                    cardPassangerAdult2.startAnimation(animShake);
                    getar();
                    return;
                }
            }

            if (cardPassangerAdult3.getVisibility() == View.VISIBLE) {
                if (textContentAdult3.getTag(R.id.nameAdult3) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.ship_penumpang_dewasa_3_kosong, "", 1);
                    cardPassangerAdult3.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerAdult3.setAnimation(animShake);
                    cardPassangerAdult3.startAnimation(animShake);
                    getar();
                    return;
                }
            }

            if (cardPassangerAdult4.getVisibility() == View.VISIBLE) {
                if (textContentAdult4.getTag(R.id.nameAdult4) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.ship_penumpang_dewasa_4_kosong, "", 1);
                    cardPassangerAdult4.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerAdult4.setAnimation(animShake);
                    cardPassangerAdult4.startAnimation(animShake);
                    getar();
                    return;
                }
            }

            if (cardPassangerAdult5.getVisibility() == View.VISIBLE) {
                if (textContentAdult5.getTag(R.id.nameAdult5) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.ship_penumpang_dewasa_5_kosong, "", 1);
                    cardPassangerAdult5.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerAdult5.setAnimation(animShake);
                    cardPassangerAdult5.startAnimation(animShake);
                    getar();
                    return;
                }
            }


            if (cardPassangerAdult6.getVisibility() == View.VISIBLE) {
                if (textContentAdult6.getTag(R.id.nameAdult6) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.ship_penumpang_dewasa_6_kosong, "", 1);
                    cardPassangerAdult6.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerAdult6.setAnimation(animShake);
                    cardPassangerAdult6.startAnimation(animShake);
                    getar();
                    return;
                }
            }

            if (cardPassangerAdult7.getVisibility() == View.VISIBLE) {
                if (textContentAdult7.getTag(R.id.nameAdult7) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.ship_penumpang_dewasa_7_kosong, "", 1);
                    cardPassangerAdult7.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerAdult7.setAnimation(animShake);
                    cardPassangerAdult7.startAnimation(animShake);
                    getar();
                    return;
                }
            }

            if (cardPassangerAdult8.getVisibility() == View.VISIBLE) {
                if (textContentAdult8.getTag(R.id.nameAdult8) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.ship_penumpang_dewasa_8_kosong, "", 1);
                    cardPassangerAdult8.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerAdult8.setAnimation(animShake);
                    cardPassangerAdult8.startAnimation(animShake);
                    getar();
                    return;
                }
            }

            if (cardPassangerAdult9.getVisibility() == View.VISIBLE) {
                if (textContentAdult9.getTag(R.id.nameAdult9) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.ship_penumpang_dewasa_9_kosong, "", 1);
                    cardPassangerAdult9.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerAdult9.setAnimation(animShake);
                    cardPassangerAdult9.startAnimation(animShake);
                    getar();
                    return;
                }
            }

            if (cardPassangerAdult10.getVisibility() == View.VISIBLE) {
                if (textContentAdult10.getTag(R.id.nameAdult10) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.ship_penumpang_dewasa_10_kosong, "", 1);
                    cardPassangerAdult10.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerAdult10.setAnimation(animShake);
                    cardPassangerAdult10.startAnimation(animShake);
                    getar();
                    return;
                }
            }


            if (cardPassangerInfant1.getVisibility() == View.VISIBLE) {
                if (textContentInfant1.getTag(R.id.nameInfant1) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.ship_penumpang_bayi_1_kosong, "", 1);
                    cardPassangerInfant1.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerInfant1.setAnimation(animShake);
                    cardPassangerInfant1.startAnimation(animShake);
                    getar();
                    return;
                }
            }

            if (cardPassangerInfant2.getVisibility() == View.VISIBLE) {
                if (textContentInfant2.getTag(R.id.nameInfant2) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.ship_penumpang_bayi_2_kosong, "", 2);
                    cardPassangerInfant2.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerInfant2.setAnimation(animShake);
                    cardPassangerInfant2.startAnimation(animShake);
                    getar();
                    return;
                }
            }

            if (cardPassangerInfant3.getVisibility() == View.VISIBLE) {
                if (textContentInfant3.getTag(R.id.nameInfant3) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.ship_penumpang_bayi_3_kosong, "", 3);
                    cardPassangerInfant3.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerInfant3.setAnimation(animShake);
                    cardPassangerInfant3.startAnimation(animShake);
                    getar();
                    return;
                }
            }

            if (cardPassangerInfant4.getVisibility() == View.VISIBLE) {
                if (textContentInfant4.getTag(R.id.nameInfant4) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.ship_penumpang_bayi_4_kosong, "", 4);
                    cardPassangerInfant4.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerInfant4.setAnimation(animShake);
                    cardPassangerInfant4.startAnimation(animShake);
                    getar();
                    return;
                }
            }

            if (cardPassangerInfant5.getVisibility() == View.VISIBLE) {
                if (textContentInfant5.getTag(R.id.nameInfant5) == null) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.ship_penumpang_bayi_5_kosong, "", 4);
                    cardPassangerInfant5.setBackgroundResource(R.drawable.shape_card_error);
                    cardPassangerInfant5.setAnimation(animShake);
                    cardPassangerInfant5.startAnimation(animShake);
                    getar();
                    return;
                }
            }
            if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                new_popup_alertDemo(this, "Info", "Anda belum bisa menikmati fiture ini.\n" +
                        "Daftar & Aktifasi sekarang juga ID Anda");

            } else {
                click_pesan_ship();
            }


        }


    }

    private void click_pesan_ship() {
        PreferenceClass.remove(ShipKeyPreference.shipPassengerAdult);
        PreferenceClass.remove(ShipKeyPreference.shipPassengerInfant);
        isFamily = checkboxIsFamiliy.isChecked();
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObjectPassengers = new JSONObject();
        // JSONArray jsonArray = (JSONArray) MemoryStore.get(BookShipActivity.this, "seat_flights");
        JSONArray jsonArrayAdults = new JSONArray();
//        JSONArray jsonArrayChilds = new JSONArray();
        JSONArray jsonArrayInfants = new JSONArray();


        Date now = new Date();

//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", config.locale);
//
//
//        String dateNow = formatter.format(now).toString();
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

//        for (String a : (String[])MemoryStore.get(BookShipActivity.this,"seat_flights")) {
//            jsonArray.put(a);
//        }
        int M = 0;
        int F = 0;
//        for(int w=0;w<listGenderAdult.size();w++){
//            if(listGenderAdult.get(w).equals("M")){
//                M+=M;
//            }
//            if(listGenderAdult.get(w).equals("F")){
//                F+=F;
//            }
//        }
        int aM = Collections.frequency(listGenderAdult, "M");
        int aF = Collections.frequency(listGenderAdult, "F");

//        for(int w=0;w<listGenderInfant.size();w++){
//            if(listGenderInfant.get(w).equals("M")){
//                M+=w;
//            }
//            if(listGenderInfant.get(w).equals("F")){
//                F+=w;
//            }
//        }
        int iM = Collections.frequency(listGenderInfant, "M");
        int iF = Collections.frequency(listGenderInfant, "F");

        M = aM + iM;
        F = aF + iF;

        try {
            jsonObject.put("origin", PreferenceClass.getString(ShipKeyPreference.pelabuhanKodeAsal, ""));
            jsonObject.put("originCall", shipModel.getORG_CALL());
            jsonObject.put("destination", PreferenceClass.getString(ShipKeyPreference.pelabuhanKodeTujuan, ""));
            jsonObject.put("destinationCall", shipModel.getDES_CALL());
            jsonObject.put("pelabuhan_asal", PreferenceClass.getString(ShipKeyPreference.pelabuhanNamaAsal, ""));
            jsonObject.put("pelabuhan_tujuan", PreferenceClass.getString(ShipKeyPreference.pelabuhanNamaTujuan, ""));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", SBFApplication.config.locale);
            SimpleDateFormat odf = new SimpleDateFormat("yyyy-MM-dd", SBFApplication.config.locale);
            jsonObject.put("departureDate", odf.format(sdf.parse(shipModel.getDEP_DATE())));
            jsonObject.put("shipName", shipModel.getSHIP_NAME());
            jsonObject.put("shipNumber", shipModel.getSHIP_NO());
            jsonObject.put("subClass", shipFareModel.getSUBCLASS());
            jsonObject.put("class", shipFareModel.getCLASS());
            jsonObject.put("male", M);
            jsonObject.put("female", F);
            jsonObject.put("harga_dewasa", String.valueOf(shipFareModel.getFareDetailA().getTOTAL()));
            jsonObject.put("harga_infant", String.valueOf(shipFareModel.getFareDetailI().getTOTAL()));

            JSONObject adults;
            for (int i = 0; i < adultPassengger.length; i++) {
                adults = new JSONObject();
                try {
                    adults.put("name", listNameAdult.get(i));
                    adults.put("birthDate", listBirthdateAdult.get(i));
//                    adults.put("birthDate", dateNow);
                    //   adult.put("phone", listPhoneAdult.get(i));
                    adults.put("identityNumber", listIdAdult.get(i));
                    adults.put("gender", listGenderAdult.get(i));
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                jsonArrayAdults.put(adults);
            }

            JSONObject infants;
            for (int i = 0; i < infantPassengger.length; i++) {
                infants = new JSONObject();
                try {
                    infants.put("name", listNameInfant.get(i));
                    infants.put("birthDate", listBirthdateInfant.get(i));
                    infants.put("gender", listGenderInfant.get(i));

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                jsonArrayInfants.put(infants);
            }

            jsonObject.put("adult", PreferenceClass.getInt(ShipKeyPreference.countAdultShip, 1));
            jsonObject.put("infant", PreferenceClass.getInt(ShipKeyPreference.countInfantShip, 0));
            jsonObject.put("isFamily", isFamily ? "Y" : "N");
            JSONObject contact = new JSONObject();
            contact.put("email", textEmailKontak.getText().toString());
            contact.put("phone", textHpKontak.getText().toString());
            jsonObject.put("contact", contact);
            JSONObject passengers = new JSONObject();
            passengers.put("adults", jsonArrayAdults);
            passengers.put("infants", jsonArrayInfants);
            jsonObject.put("passengers", passengers);

            jsonObject.put("token", PreferenceClass.getToken());
            PreferenceClass.putJSONArray(ShipKeyPreference.shipPassengerAdult,jsonArrayAdults);
            PreferenceClass.putJSONArray(ShipKeyPreference.shipPassengerInfant,jsonArrayInfants);
        } catch (Exception e) {
            e.printStackTrace();
        }
        isStillRunning = true;
        Log.d(TAG, "REQUEST book Ship: " + jsonObject);
        RequestUtilsTravel.transportWithProgressResponseBook_Pay(this, ShipPath.BOOK, jsonObject, TravelActionCode.BOOK, this);
        final FrameLayout view = (FrameLayout)View.inflate(this,R.layout.loading_bar_full_dialog, null);
        TextView text = view.findViewById(R.id.textContentProgressBar);
        text.setText("Mohon Tunggu, Permintaan Booking Ke PELNI Sedang Diproses");
        openProgressBarDialog(ShipBookActivity.this, view);



    }


    @Override
    public void onSuccess(int actionCode, @NonNull final JSONObject response) {
        if(actionCode==TravelActionCode.BOOK) {
            try {


//
                Log.d(TAG, "onSuccess: " + response.toString());
//
                String rc = response.getString("rc").toString();
                final String rd = response.getString("rd").toString();
                if (rc.equals(ResponseCode.SUCCESS)) {

                    Intent intent = new Intent(ShipBookActivity.this, ShipReviewBookActivity.class);
//        Intent intent=new Intent();
//                    Bundle b = new Bundle();
//                    b.putString("passengers", response);
                    PreferenceClass.remove(ShipKeyPreference.bookResult);
                    PreferenceClass.putJSONObject(ShipKeyPreference.bookResult, response);
//                intent.putExtra("ship", shipFareModel.getShipModel().getJsonObject().toString());
//                intent.putExtra("shipFare", shipFareModel.getJsonObject().toString());
//                    intent.putExtra("bookShip",true);
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


            } catch (JSONException e) {
                showToast(e.toString());

                // closeProgressBarDialog();


            }
            isStillRunning = false;
            closeProgressBarDialog();
        }
    }

    @Override
    public void onFailure(final int actionCode, @NonNull final String responseCode, String responseDescription, @NonNull Throwable throwable) {

        Log.d(TAG, "onFailure: " + actionCode + " " + responseDescription + " " + throwable.toString());


//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
            if (actionCode == TravelActionCode.BOOK) {
                if (responseCode.equals("504")) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), 0, "Silahkan cek di menu pesanan Anda", 3);
                    //  showToastCustom(BookShipActivity.this, 3, "Silahkan cek di menu pesanan Anda");
//                }
                } else {
                    //  ((ShipReviewBookActivity)instance.getApplicationContext()).getIntanceShipPay().getFailureBook();
                    snackBarCustomAction(findViewById(R.id.rootLayout), 0, "Ada Masalah dengan server, silahkan coba kembali", 1);
                }
//                }
//            });

                isStillRunning = false;
                closeProgressBarDialog();
                Log.d(TAG, "onFailure: " + responseDescription + " " + throwable);

            }
    }

    @Override
    public void onUpdate(int actionCode,long bytesRead, long totalSize, boolean done) {

    }


    public void openFormAdultShip(@NonNull View v) {
        int id = v.getId();
        Intent intent = new Intent(ShipBookActivity.this, ShipFormBookAdultActivity.class);


        String sexAdult;
        String sexTempAdult;
        String nameAdult;
        String idAdult;

        String bornAdult;
        String bornShowAdult;
//        if (id == R.id.imageActionAdultShip1) {
        if (id == R.id.linContentAdultShip1) {
            if (textActionAdult1.getVisibility() == View.GONE) {
                intent.putExtra(ShipKeyPreference.isNewShip, true);
                startActivityForResult(intent, TravelActionCode.ADULT1);
            }else{
                intent.putExtra(ShipKeyPreference.isNewShip, false);
                sexAdult= (String) textContentAdult1.getTag(R.id.sexAdult1);
                sexTempAdult=(String)textContentAdult1.getTag(R.id.sexTempAdult1);
                nameAdult = (String) textContentAdult1.getTag(R.id.nameAdult1);
                idAdult = (String) textContentAdult1.getTag(R.id.idAdult1);

                bornAdult = (String) textContentAdult1.getTag(R.id.bornAdult1);
                bornShowAdult = (String) textContentAdult1.getTag(R.id.bornShowAdult1);

                intent.putExtra("sexAdult", sexAdult);
                intent.putExtra("sexTempAdult", sexTempAdult);
                intent.putExtra("nameAdult", nameAdult);
                intent.putExtra("idAdult", idAdult);

                intent.putExtra("bornAdult", bornAdult);
                intent.putExtra("bornShowAdult", bornShowAdult);


                startActivityForResult(intent, TravelActionCode.ADULT1);


            }
//        } else if (id == R.id.textActionAdultShip1) {

//        } else if (id == R.id.imageActionAdultShip2) {
        } else if (id == R.id.linContentAdultShip2) {
            if (textActionAdult2.getVisibility() == View.GONE) {
                intent.putExtra(ShipKeyPreference.isNewShip, true);
                startActivityForResult(intent, TravelActionCode.ADULT2);
            }else{
                intent.putExtra(ShipKeyPreference.isNewShip, false);
                sexAdult= (String) textContentAdult2.getTag(R.id.sexAdult2);
                sexTempAdult=(String)textContentAdult2.getTag(R.id.sexTempAdult2);
                nameAdult = (String) textContentAdult2.getTag(R.id.nameAdult2);
                idAdult = (String) textContentAdult2.getTag(R.id.idAdult2);

                bornAdult = (String) textContentAdult2.getTag(R.id.bornAdult2);
                bornShowAdult = (String) textContentAdult2.getTag(R.id.bornShowAdult2);

                intent.putExtra("sexAdult", sexAdult);
                intent.putExtra("sexTempAdult", sexTempAdult);
                intent.putExtra("nameAdult", nameAdult);
                intent.putExtra("idAdult", idAdult);

                intent.putExtra("bornAdult", bornAdult);
                intent.putExtra("bornShowAdult", bornShowAdult);
                startActivityForResult(intent, TravelActionCode.ADULT2);
            }
//        } else if (id == R.id.textActionAdultShip2) {


//        } else if (id == R.id.imageActionAdultShip3) {
        } else if (id == R.id.linContentAdultShip3) {
            if (textActionAdult3.getVisibility() == View.GONE) {
                intent.putExtra(ShipKeyPreference.isNewShip, true);
                startActivityForResult(intent, TravelActionCode.ADULT3);
            }else{
                intent.putExtra(ShipKeyPreference.isNewShip, false);
                sexAdult= (String) textContentAdult3.getTag(R.id.sexAdult3);
                sexTempAdult=(String)textContentAdult3.getTag(R.id.sexTempAdult3);
                nameAdult = (String) textContentAdult3.getTag(R.id.nameAdult3);
                idAdult = (String) textContentAdult3.getTag(R.id.idAdult3);

                bornAdult = (String) textContentAdult3.getTag(R.id.bornAdult3);
                bornShowAdult = (String) textContentAdult3.getTag(R.id.bornShowAdult3);

                intent.putExtra("sexAdult", sexAdult);
                intent.putExtra("sexTempAdult", sexTempAdult);
                intent.putExtra("nameAdult", nameAdult);
                intent.putExtra("idAdult", idAdult);

                intent.putExtra("bornAdult", bornAdult);
                intent.putExtra("bornShowAdult", bornShowAdult);
                startActivityForResult(intent, TravelActionCode.ADULT3);

            }
//        } else if (id == R.id.textActionAdultShip3) {

//        } else if (id == R.id.imageActionAdultShip4) {
        } else if (id == R.id.linContentAdultShip4) {
            if (textActionAdult4.getVisibility() == View.GONE) {
                intent.putExtra(ShipKeyPreference.isNewShip, true);
                startActivityForResult(intent, TravelActionCode.ADULT4);
            }else{
                intent.putExtra(ShipKeyPreference.isNewShip, false);
                sexAdult= (String) textContentAdult4.getTag(R.id.sexAdult4);
                sexTempAdult=(String)textContentAdult4.getTag(R.id.sexTempAdult4);
                nameAdult = (String) textContentAdult4.getTag(R.id.nameAdult4);
                idAdult = (String) textContentAdult4.getTag(R.id.idAdult4);

                bornAdult = (String) textContentAdult4.getTag(R.id.bornAdult4);
                bornShowAdult = (String) textContentAdult4.getTag(R.id.bornShowAdult4);

                intent.putExtra("sexAdult", sexAdult);
                intent.putExtra("sexTempAdult", sexTempAdult);
                intent.putExtra("nameAdult", nameAdult);
                intent.putExtra("idAdult", idAdult);

                intent.putExtra("bornAdult", bornAdult);
                intent.putExtra("bornShowAdult", bornShowAdult);
                startActivityForResult(intent, TravelActionCode.ADULT4);
            }
//        } else if (id == R.id.textActionAdultShip4) {

//        } else if (id == R.id.imageActionAdultShip5) {
        } else if (id == R.id.linContentAdultShip5) {
            if (textActionAdult5.getVisibility() == View.GONE) {
                intent.putExtra(ShipKeyPreference.isNewShip, true);
                startActivityForResult(intent, TravelActionCode.ADULT5);
            }else{
                intent.putExtra(ShipKeyPreference.isNewShip, false);
                sexAdult= (String) textContentAdult5.getTag(R.id.sexAdult5);
                sexTempAdult=(String)textContentAdult5.getTag(R.id.sexTempAdult5);
                nameAdult = (String) textContentAdult5.getTag(R.id.nameAdult5);
                idAdult = (String) textContentAdult5.getTag(R.id.idAdult5);

                bornAdult = (String) textContentAdult5.getTag(R.id.bornAdult5);
                bornShowAdult = (String) textContentAdult5.getTag(R.id.bornShowAdult5);

                intent.putExtra("sexAdult", sexAdult);
                intent.putExtra("sexTempAdult", sexTempAdult);
                intent.putExtra("nameAdult", nameAdult);
                intent.putExtra("idAdult", idAdult);

                intent.putExtra("bornAdult", bornAdult);
                intent.putExtra("bornShowAdult", bornShowAdult);
                startActivityForResult(intent, TravelActionCode.ADULT5);
            }
//        } else if (id == R.id.textActionAdultShip5) {

//        } else if (id == R.id.imageActionAdultShip6) {
        } else if (id == R.id.linContentAdultShip6) {
            if (textActionAdult6.getVisibility() == View.GONE) {
                intent.putExtra(ShipKeyPreference.isNewShip, true);
                startActivityForResult(intent, TravelActionCode.ADULT6);
            }else{
                intent.putExtra(ShipKeyPreference.isNewShip, false);
                sexAdult= (String) textContentAdult6.getTag(R.id.sexAdult6);
                sexTempAdult=(String)textContentAdult6.getTag(R.id.sexTempAdult6);
                nameAdult = (String) textContentAdult6.getTag(R.id.nameAdult6);
                idAdult = (String) textContentAdult6.getTag(R.id.idAdult6);

                bornAdult = (String) textContentAdult6.getTag(R.id.bornAdult6);
                bornShowAdult = (String) textContentAdult6.getTag(R.id.bornShowAdult6);

                intent.putExtra("sexAdult", sexAdult);
                intent.putExtra("sexTempAdult", sexTempAdult);
                intent.putExtra("nameAdult", nameAdult);
                intent.putExtra("idAdult", idAdult);

                intent.putExtra("bornAdult", bornAdult);
                intent.putExtra("bornShowAdult", bornShowAdult);
                startActivityForResult(intent, TravelActionCode.ADULT6);
            }
//        } else if (id == R.id.textActionAdultShip6) {

//        } else if (id == R.id.imageActionAdultShip7) {
        } else if (id == R.id.linContentAdultShip7) {
            if (textActionAdult7.getVisibility() == View.GONE) {
                intent.putExtra(ShipKeyPreference.isNewShip, true);
                startActivityForResult(intent, TravelActionCode.ADULT7);
            }else{
                intent.putExtra(ShipKeyPreference.isNewShip, false);
                sexAdult= (String) textContentAdult7.getTag(R.id.sexAdult7);
                sexTempAdult=(String)textContentAdult7.getTag(R.id.sexTempAdult7);
                nameAdult = (String) textContentAdult7.getTag(R.id.nameAdult7);
                idAdult = (String) textContentAdult7.getTag(R.id.idAdult7);

                bornAdult = (String) textContentAdult7.getTag(R.id.bornAdult7);
                bornShowAdult = (String) textContentAdult7.getTag(R.id.bornShowAdult7);

                intent.putExtra("sexAdult", sexAdult);
                intent.putExtra("sexTempAdult", sexTempAdult);
                intent.putExtra("nameAdult", nameAdult);
                intent.putExtra("idAdult", idAdult);

                intent.putExtra("bornAdult", bornAdult);
                intent.putExtra("bornShowAdult", bornShowAdult);
                startActivityForResult(intent, TravelActionCode.ADULT7);
            }
//        } else if (id == R.id.textActionAdultShip7) {

//        } else if (id == R.id.imageActionAdultShip8) {
        } else if (id == R.id.linContentAdultShip8) {
            if (textActionAdult8.getVisibility() == View.GONE) {
                intent.putExtra(ShipKeyPreference.isNewShip, true);
                startActivityForResult(intent, TravelActionCode.ADULT8);
            }else{
                intent.putExtra(ShipKeyPreference.isNewShip, false);
                sexAdult= (String) textContentAdult8.getTag(R.id.sexAdult8);
                sexTempAdult=(String)textContentAdult8.getTag(R.id.sexTempAdult8);
                nameAdult = (String) textContentAdult8.getTag(R.id.nameAdult8);
                idAdult = (String) textContentAdult8.getTag(R.id.idAdult8);

                bornAdult = (String) textContentAdult8.getTag(R.id.bornAdult8);
                bornShowAdult = (String) textContentAdult8.getTag(R.id.bornShowAdult8);

                intent.putExtra("sexAdult", sexAdult);
                intent.putExtra("sexTempAdult", sexTempAdult);
                intent.putExtra("nameAdult", nameAdult);
                intent.putExtra("idAdult", idAdult);

                intent.putExtra("bornAdult", bornAdult);
                intent.putExtra("bornShowAdult", bornShowAdult);
                startActivityForResult(intent, TravelActionCode.ADULT8);
            }
//        } else if (id == R.id.textActionAdultShip8) {

//        } else if (id == R.id.imageActionAdultShip9) {
        } else if (id == R.id.linContentAdultShip9) {
            if (textActionAdult9.getVisibility() == View.GONE) {
                intent.putExtra(ShipKeyPreference.isNewShip, true);
                startActivityForResult(intent, TravelActionCode.ADULT9);
            }else{
                intent.putExtra(ShipKeyPreference.isNewShip, false);
                sexAdult= (String) textContentAdult9.getTag(R.id.sexAdult9);
                sexTempAdult=(String)textContentAdult9.getTag(R.id.sexTempAdult9);
                nameAdult = (String) textContentAdult9.getTag(R.id.nameAdult9);
                idAdult = (String) textContentAdult9.getTag(R.id.idAdult9);

                bornAdult = (String) textContentAdult9.getTag(R.id.bornAdult9);
                bornShowAdult = (String) textContentAdult9.getTag(R.id.bornShowAdult9);

                intent.putExtra("sexAdult", sexAdult);
                intent.putExtra("sexTempAdult", sexTempAdult);
                intent.putExtra("nameAdult", nameAdult);
                intent.putExtra("idAdult", idAdult);

                intent.putExtra("bornAdult", bornAdult);
                intent.putExtra("bornShowAdult", bornShowAdult);
                startActivityForResult(intent, TravelActionCode.ADULT9);
            }
//        } else if (id == R.id.textActionAdultShip9) {

//        } else if (id == R.id.imageActionAdultShip10) {
        } else if (id == R.id.linContentAdultShip10) {
            if (textActionAdult10.getVisibility() == View.GONE) {
                intent.putExtra(ShipKeyPreference.isNewShip, true);
                startActivityForResult(intent, TravelActionCode.ADULT10);
            }else{
                intent.putExtra(ShipKeyPreference.isNewShip, false);
                sexAdult= (String) textContentAdult10.getTag(R.id.sexAdult10);
                sexTempAdult=(String)textContentAdult10.getTag(R.id.sexTempAdult10);
                nameAdult = (String) textContentAdult10.getTag(R.id.nameAdult10);
                idAdult = (String) textContentAdult10.getTag(R.id.idAdult10);

                bornAdult = (String) textContentAdult10.getTag(R.id.bornAdult10);
                bornShowAdult = (String) textContentAdult10.getTag(R.id.bornShowAdult10);

                intent.putExtra("sexAdult", sexAdult);
                intent.putExtra("sexTempAdult", sexTempAdult);
                intent.putExtra("nameAdult", nameAdult);
                intent.putExtra("idAdult", idAdult);

                intent.putExtra("bornAdult", bornAdult);
                intent.putExtra("bornShowAdult", bornShowAdult);
                startActivityForResult(intent, TravelActionCode.ADULT10);
            }
//        } else if (id == R.id.textActionAdultShip10) {

        }
        //  overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }


    public void openFormInfantShip(@NonNull View v) {
        int id = v.getId();
        Intent intent = new Intent(ShipBookActivity.this, ShipFormBookInfantActivity.class);


        String sexInfant;
        String sexTempInfant;
        String nameInfant;

        String bornInfant;
        String bornShowInfant;
//        if (id == R.id.imageActionInfantShip1) {
        if (id == R.id.linContentInfantShip1) {
            if (textActionInfant1.getVisibility() == View.GONE) {
                intent.putExtra(ShipKeyPreference.isNewShip, true);
                startActivityForResult(intent, TravelActionCode.INFANT1);
            }else{
                intent.putExtra(ShipKeyPreference.isNewShip, false);
                sexInfant= (String) textContentInfant1.getTag(R.id.sexInfant1);
                sexTempInfant=(String)textContentInfant1.getTag(R.id.sexTempInfant1);
                nameInfant = (String) textContentInfant1.getTag(R.id.nameInfant1);

                bornInfant = (String) textContentInfant1.getTag(R.id.bornInfant1);
                bornShowInfant = (String) textContentInfant1.getTag(R.id.bornShowInfant1);

                intent.putExtra("sexInfant", sexInfant);
                intent.putExtra("sexTempInfant", sexTempInfant);
                intent.putExtra("nameInfant", nameInfant);

                intent.putExtra("bornInfant", bornInfant);
                intent.putExtra("bornShowInfant", bornShowInfant);
                startActivityForResult(intent, TravelActionCode.INFANT1);

            }
//        } else if (id == R.id.textActionInfantShip1) {


//        } else if (id == R.id.imageActionInfantShip2) {
        } else if (id == R.id.linContentInfantShip2) {
            if (textActionInfant2.getVisibility() == View.GONE) {
                intent.putExtra(ShipKeyPreference.isNewShip, true);
                startActivityForResult(intent, TravelActionCode.INFANT2);
            }else{
                intent.putExtra(ShipKeyPreference.isNewShip, false);
                sexInfant= (String) textContentInfant2.getTag(R.id.sexInfant2);
                sexTempInfant=(String)textContentInfant2.getTag(R.id.sexTempInfant2);
                nameInfant = (String) textContentInfant2.getTag(R.id.nameInfant2);

                bornInfant = (String) textContentInfant2.getTag(R.id.bornInfant2);
                bornShowInfant = (String) textContentInfant2.getTag(R.id.bornShowInfant2);

                intent.putExtra("sexInfant", sexInfant);
                intent.putExtra("sexTempInfant", sexTempInfant);
                intent.putExtra("nameInfant", nameInfant);

                intent.putExtra("bornInfant", bornInfant);
                intent.putExtra("bornShowInfant", bornShowInfant);

                startActivityForResult(intent, TravelActionCode.INFANT2);

            }
//        } else if (id == R.id.textActionInfantShip2) {

//        } else if (id == R.id.imageActionInfantShip3) {
        } else if (id == R.id.linContentInfantShip3) {
            if (textActionInfant3.getVisibility() == View.GONE) {
                intent.putExtra(ShipKeyPreference.isNewShip, true);
                startActivityForResult(intent, TravelActionCode.INFANT3);
            }else{
                intent.putExtra(ShipKeyPreference.isNewShip, false);
                sexInfant= (String) textContentInfant3.getTag(R.id.sexInfant3);
                sexTempInfant=(String)textContentInfant3.getTag(R.id.sexTempInfant3);
                nameInfant = (String) textContentInfant3.getTag(R.id.nameInfant3);

                bornInfant = (String) textContentInfant3.getTag(R.id.bornInfant3);
                bornShowInfant = (String) textContentInfant3.getTag(R.id.bornShowInfant3);

                intent.putExtra("sexInfant", sexInfant);
                intent.putExtra("sexTempInfant", sexTempInfant);
                intent.putExtra("nameInfant", nameInfant);

                intent.putExtra("bornInfant", bornInfant);
                intent.putExtra("bornShowInfant", bornShowInfant);
                startActivityForResult(intent, TravelActionCode.INFANT3);
            }
//        } else if (id == R.id.textActionInfantShip3) {


//        } else if (id == R.id.imageActionInfantShip4) {
        } else if (id == R.id.linContentInfantShip4) {
            if (textActionInfant4.getVisibility() == View.GONE) {
                intent.putExtra(ShipKeyPreference.isNewShip, true);
                startActivityForResult(intent, TravelActionCode.INFANT4);
            }else{
                intent.putExtra(ShipKeyPreference.isNewShip, false);
                sexInfant= (String) textContentInfant4.getTag(R.id.sexInfant4);
                sexTempInfant=(String)textContentInfant4.getTag(R.id.sexTempInfant4);
                nameInfant = (String) textContentInfant4.getTag(R.id.nameInfant4);

                bornInfant = (String) textContentInfant4.getTag(R.id.bornInfant4);
                bornShowInfant = (String) textContentInfant4.getTag(R.id.bornShowInfant4);

                intent.putExtra("sexInfant", sexInfant);
                intent.putExtra("sexTempInfant", sexTempInfant);
                intent.putExtra("nameInfant", nameInfant);

                intent.putExtra("bornInfant", bornInfant);
                intent.putExtra("bornShowInfant", bornShowInfant);
                startActivityForResult(intent, TravelActionCode.INFANT4);
            }
//        } else if (id == R.id.textActionInfantShip4) {


//        } else if (id == R.id.imageActionInfantShip5) {
        } else if (id == R.id.linContentInfantShip5) {
            if (textActionInfant5.getVisibility() == View.GONE) {
                intent.putExtra(ShipKeyPreference.isNewShip, true);
                startActivityForResult(intent, TravelActionCode.INFANT5);
            }else{
                intent.putExtra(ShipKeyPreference.isNewShip, false);
                sexInfant= (String) textContentInfant5.getTag(R.id.sexInfant5);
                sexTempInfant=(String)textContentInfant5.getTag(R.id.sexTempInfant5);
                nameInfant = (String) textContentInfant5.getTag(R.id.nameInfant5);

                bornInfant = (String) textContentInfant5.getTag(R.id.bornInfant5);
                bornShowInfant = (String) textContentInfant5.getTag(R.id.bornShowInfant5);

                intent.putExtra("sexInfant", sexInfant);
                intent.putExtra("sexTempInfant", sexTempInfant);
                intent.putExtra("nameInfant", nameInfant);

                intent.putExtra("bornInfant", bornInfant);
                intent.putExtra("bornShowInfant", bornShowInfant);
                startActivityForResult(intent, TravelActionCode.INFANT5);
            }
//        } else if (id == R.id.textActionInfantShip5) {


        }

        //  overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

    @NonNull
    ArrayList<String> listNameAdult = new ArrayList<>();
    @NonNull
    ArrayList<String> listBirthdateAdult = new ArrayList<>();
    @NonNull
    ArrayList<String> listIdAdult = new ArrayList<>();
    // ArrayList<String> listPhoneAdult = new ArrayList<String>();
    @NonNull
    ArrayList<String> listGenderAdult = new ArrayList<>();

    @NonNull
    public ArrayList<String> listNameInfant = new ArrayList<>();
    @NonNull
    ArrayList<String> listBirthdateInfant = new ArrayList<>();
    @NonNull
    ArrayList<String> listGenderInfant = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       // Log.d(TAG, "onActivityResult: " + data);

        //  String initTitle ;



        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == TravelActionCode.IS_FROM_PAY) {
        if (requestCode == TravelActionCode.IS_FROM_PAY) {
            Log.d(TAG, "onActivityResult: IS_FROM_PAY");
//                if (isStillRunning) {
//                    RequestUtilsTravel.cancelTravel();
//                }
                //  Log.d(TAG, "onActivityResult: "+requestCode);
                //  Intent intent = new Intent(FlightBookActivity.this, FlightSearchActivity.class);


                if(data!=null && data.getAction()!=null) {
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
                }else{
                    setResult(RESULT_OK);
                }

                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            } else {
            String namePassager;
            String sexPassanger;
            String fullName[];
            String firstName;
            String lastName;
            String noId;
            String bornDate;
                if (data.getStringExtra("status").equals("adult")) {// ADULT/DEWASA
                    if (requestCode == TravelActionCode.ADULT1) {// adult 1
                        imageViewExpandPassangerAdultShip1.performClick();
                      //  panelPassangerAdultShip1Detail.setBackgroundResource(R.drawable.shape_card_primary);
                        cardPassangerAdult1.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
                        if (!data.getBooleanExtra(ShipKeyPreference.isNewShip, false)) {
                            //if (passengersList.size() > 0) {
                            //           passengersList.remove(countAdult-1);
                            listNameAdult.remove(0);
                            listBirthdateAdult.remove(0);
                            listIdAdult.remove(0);
                            listGenderAdult.remove(0);
                            // listPhoneAdult.remove(0);
                            // }
                        }
                        //   Log.d(TAG, "onActivityResult adult 1: " + MemoryStore.get(this, "adultBorn") + " " + data.getStringExtra("adultBorn"));
                        textContentAdult1.setText(data.getStringExtra("adultName"));

                        textContentAdult1.setTag(R.id.sexAdult1, data.getStringExtra("adultSex"));
                        textContentAdult1.setTag(R.id.sexTempAdult1, data.getStringExtra("adultSexTemp"));
                        textContentAdult1.setTag(R.id.nameAdult1, data.getStringExtra("adultName"));
                        textContentAdult1.setTag(R.id.idAdult1, data.getStringExtra("adultId"));
//                    textContentAdult1.setTag(R.id.phoneAdult1, data.getStringExtra("adultPhone"));
                        textContentAdult1.setTag(R.id.bornAdult1, data.getStringExtra("adultBorn"));
                        textContentAdult1.setTag(R.id.bornShowAdult1, data.getStringExtra("adultBornShow"));

                        textViewNoIdentitasAdultShip1.setText(data.getStringExtra("adultId"));
                        //textViewMobileAdultShip1.setText(data.getStringExtra("adultPhone"));

                        namePassager = data.getStringExtra("adultName");
                        sexPassanger = data.getStringExtra("adultSexTemp");

                        noId = data.getStringExtra("adultId");
//                    phone = data.getStringExtra("adultPhone");
                        bornDate = data.getStringExtra("adultBorn");


                        listNameAdult.add(0, namePassager);
                        listBirthdateAdult.add(0, bornDate);
                        listIdAdult.add(0, noId);
                        listGenderAdult.add(0, sexPassanger);
//                    listPhoneAdult.add(0, phone);


                        textActionAdult1.setVisibility(View.VISIBLE);
                        imageActionAdult1.setVisibility(View.GONE);

                    } else if (requestCode == TravelActionCode.ADULT2) {// adult 2
                        imageViewExpandPassangerAdultShip2.performClick();
                        //panelPassangerAdultShip2Detail.setBackgroundResource(R.drawable.shape_card_primary);
                        cardPassangerAdult2.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
                        if (!data.getBooleanExtra(ShipKeyPreference.isNewShip, false)) {

                            listNameAdult.remove(1);
                            listBirthdateAdult.remove(1);
                            listIdAdult.remove(1);
                            listGenderAdult.remove(1);
//                        listPhoneAdult.remove(1);

                        }
                        //     Log.d(TAG, "onActivityResult adult 2: " + MemoryStore.get(this, "adultBorn") + " " + data.getStringExtra("adultBorn"));
                        textContentAdult2.setText(data.getStringExtra("adultName"));

                        textContentAdult2.setTag(R.id.sexAdult2, data.getStringExtra("adultSex"));
                        textContentAdult2.setTag(R.id.sexTempAdult2, data.getStringExtra("adultSexTemp"));
                        textContentAdult2.setTag(R.id.nameAdult2, data.getStringExtra("adultName"));
                        textContentAdult2.setTag(R.id.idAdult2, data.getStringExtra("adultId"));
//                    textContentAdult2.setTag(R.id.phoneAdult2, data.getStringExtra("adultPhone"));
                        textContentAdult2.setTag(R.id.bornAdult2, data.getStringExtra("adultBorn"));
                        textContentAdult2.setTag(R.id.bornShowAdult2, data.getStringExtra("adultBornShow"));


                        textViewNoIdentitasAdultShip2.setText(data.getStringExtra("adultId"));
                     //   textViewMobileAdultShip2.setText(data.getStringExtra("adultPhone"));
                        namePassager = data.getStringExtra("adultName");
                        sexPassanger = data.getStringExtra("adultSexTemp");

                        noId = data.getStringExtra("adultId");
//                    phone = data.getStringExtra("adultPhone");
                        bornDate = data.getStringExtra("adultBorn");

                        listNameAdult.add(1, namePassager);
                        listBirthdateAdult.add(1, bornDate);
                        listIdAdult.add(1, noId);
                        listGenderAdult.add(1, sexPassanger);
//                    listPhoneAdult.add(1, phone);

                        textActionAdult2.setVisibility(View.VISIBLE);
                        imageActionAdult2.setVisibility(View.GONE);
                        //   Log.d(TAG, "onActivityResult: " + passengersList.toString());
                    } else if (requestCode == TravelActionCode.ADULT3) {// adult 3
                        imageViewExpandPassangerAdultShip3.performClick();
//                        panelPassangerAdultShip3Detail.setBackgroundResource(R.drawable.shape_card_primary);
                        cardPassangerAdult3.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
                        if (!data.getBooleanExtra(ShipKeyPreference.isNewShip, false)) {

                            listNameAdult.remove(2);
                            listBirthdateAdult.remove(2);
                            listIdAdult.remove(2);
                            listGenderAdult.remove(2);
//                        listPhoneAdult.remove(2);
                        }
                        //     Log.d(TAG, "onActivityResult adult 3: " + MemoryStore.get(this, "adultBorn") + " " + data.getStringExtra("adultBorn"));
                        textContentAdult3.setText(data.getStringExtra("adultName"));

                        textContentAdult3.setTag(R.id.sexAdult3, data.getStringExtra("adultSex"));
                        textContentAdult3.setTag(R.id.sexTempAdult3, data.getStringExtra("adultSexTemp"));
                        textContentAdult3.setTag(R.id.nameAdult3, data.getStringExtra("adultName"));
                        textContentAdult3.setTag(R.id.idAdult3, data.getStringExtra("adultId"));
//                    textContentAdult3.setTag(R.id.phoneAdult3, data.getStringExtra("adultPhone"));
                        textContentAdult3.setTag(R.id.bornAdult3, data.getStringExtra("adultBorn"));
                        textContentAdult3.setTag(R.id.bornShowAdult3, data.getStringExtra("adultBornShow"));

                        textViewNoIdentitasAdultShip3.setText(data.getStringExtra("adultId"));
                        //textViewMobileAdultShip3.setText(data.getStringExtra("adultPhone"));
                        namePassager = data.getStringExtra("adultName");
                        sexPassanger = data.getStringExtra("adultSexTemp");

                        noId = data.getStringExtra("adultId");
//                    phone = data.getStringExtra("adultPhone");
                        bornDate = data.getStringExtra("adultBorn");

                        listNameAdult.add(2, namePassager);
                        listBirthdateAdult.add(2, bornDate);
                        listIdAdult.add(2, noId);
                        listGenderAdult.add(2, sexPassanger);
//                    listPhoneAdult.add(2, phone);

                        textActionAdult3.setVisibility(View.VISIBLE);
                        imageActionAdult3.setVisibility(View.GONE);
                        //  Log.d(TAG, "onActivityResult: " + passengersList.toString());
                    } else if (requestCode == TravelActionCode.ADULT4) {// adult 4
                        imageViewExpandPassangerAdultShip4.performClick();
//                        panelPassangerAdultShip4Detail.setBackgroundResource(R.drawable.shape_card_primary);
                        cardPassangerAdult4.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
                        if (!data.getBooleanExtra(ShipKeyPreference.isNewShip, false)) {

                            listNameAdult.remove(3);
                            listBirthdateAdult.remove(3);
                            listIdAdult.remove(3);
                            listGenderAdult.remove(3);
//                        listPhoneAdult.remove(3);
                        }
                        //       Log.d(TAG, "onActivityResult adult 4: " + MemoryStore.get(this, "adultBorn") + " " + data.getStringExtra("adultBorn"));
                        textContentAdult4.setText(data.getStringExtra("adultName"));

                        textContentAdult4.setTag(R.id.sexAdult4, data.getStringExtra("adultSex"));
                        textContentAdult4.setTag(R.id.sexTempAdult4, data.getStringExtra("adultSexTemp"));
                        textContentAdult4.setTag(R.id.nameAdult4, data.getStringExtra("adultName"));
                        textContentAdult4.setTag(R.id.idAdult4, data.getStringExtra("adultId"));
//                    textContentAdult4.setTag(R.id.phoneAdult4, data.getStringExtra("adultPhone"));
                        textContentAdult4.setTag(R.id.bornAdult4, data.getStringExtra("adultBorn"));
                        textContentAdult4.setTag(R.id.bornShowAdult4, data.getStringExtra("adultBornShow"));

//                 
                        textViewNoIdentitasAdultShip4.setText(data.getStringExtra("adultId"));
                        //textViewMobileAdultShip4.setText(data.getStringExtra("adultPhone"));
                        namePassager = data.getStringExtra("adultName");
                        sexPassanger = data.getStringExtra("adultSexTemp");
//           
                        noId = data.getStringExtra("adultId");
//                    phone = data.getStringExtra("adultPhone");
                        bornDate = data.getStringExtra("adultBorn");


                        listNameAdult.add(3, namePassager);
                        listBirthdateAdult.add(3, bornDate);
                        listIdAdult.add(3, noId);
                        listGenderAdult.add(3, sexPassanger);
//                    listPhoneAdult.add(3, phone);
//                 
                        textActionAdult4.setVisibility(View.VISIBLE);
                        imageActionAdult4.setVisibility(View.GONE);
                        //    Log.d(TAG, "onActivityResult: " + passengersList.toString());
                    } else if (requestCode == TravelActionCode.ADULT5) {// adult 5
                        imageViewExpandPassangerAdultShip5.performClick();
//                        panelPassangerAdultShip5Detail.setBackgroundResource(R.drawable.shape_card_primary);
                        cardPassangerAdult5.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
                        if (!data.getBooleanExtra(ShipKeyPreference.isNewShip, false)) {

                            listNameAdult.remove(4);
                            listBirthdateAdult.remove(4);
                            listIdAdult.remove(4);
                            listGenderAdult.remove(4);
//                        listPhoneAdult.remove(4);
                        }
                        //       Log.d(TAG, "onActivityResult adult 5: " + MemoryStore.get(this, "adultBorn") + " " + data.getStringExtra("adultBorn"));
                        textContentAdult5.setText(data.getStringExtra("adultName"));

                        textContentAdult5.setTag(R.id.sexAdult5, data.getStringExtra("adultSex"));
                        textContentAdult5.setTag(R.id.sexTempAdult5, data.getStringExtra("adultSexTemp"));
                        textContentAdult5.setTag(R.id.nameAdult5, data.getStringExtra("adultName"));
                        textContentAdult5.setTag(R.id.idAdult5, data.getStringExtra("adultId"));
//                    textContentAdult5.setTag(R.id.phoneAdult5, data.getStringExtra("adultPhone"));
                        textContentAdult5.setTag(R.id.bornAdult5, data.getStringExtra("adultBorn"));
                        textContentAdult5.setTag(R.id.bornShowAdult5, data.getStringExtra("adultBornShow"));

//                 
                        textViewNoIdentitasAdultShip5.setText(data.getStringExtra("adultId"));
                        //textViewMobileAdultShip5.setText(data.getStringExtra("adultPhone"));
                        namePassager = data.getStringExtra("adultName");
                        sexPassanger = data.getStringExtra("adultSexTemp");
//           
                        noId = data.getStringExtra("adultId");
//                    phone = data.getStringExtra("adultPhone");
                        bornDate = data.getStringExtra("adultBorn");


                        listNameAdult.add(4, namePassager);
                        listBirthdateAdult.add(4, bornDate);
                        listIdAdult.add(4, noId);
                        listGenderAdult.add(4, sexPassanger);
//                    listPhoneAdult.add(4, phone);
//                 
                        textActionAdult5.setVisibility(View.VISIBLE);
                        imageActionAdult5.setVisibility(View.GONE);
                    } else if (requestCode == TravelActionCode.ADULT6) {// adult 6
                        imageViewExpandPassangerAdultShip6.performClick();
//                        panelPassangerAdultShip6Detail.setBackgroundResource(R.drawable.shape_card_primary);
                        cardPassangerAdult6.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
                        if (!data.getBooleanExtra(ShipKeyPreference.isNewShip, false)) {

                            listNameAdult.remove(5);
                            listBirthdateAdult.remove(5);
                            listIdAdult.remove(5);
                            listGenderAdult.remove(5);
//                        listPhoneAdult.remove(5);
                        }
                        //       Log.d(TAG, "onActivityResult adult 6: " + MemoryStore.get(this, "adultBorn") + " " + data.getStringExtra("adultBorn"));
                        textContentAdult6.setText(data.getStringExtra("adultName"));
                        textContentAdult6.setTag(R.id.sexAdult6, data.getStringExtra("adultSex"));
                        textContentAdult6.setTag(R.id.sexTempAdult6, data.getStringExtra("adultSexTemp"));

                        textContentAdult6.setTag(R.id.nameAdult6, data.getStringExtra("adultName"));
                        textContentAdult6.setTag(R.id.idAdult6, data.getStringExtra("adultId"));
//                    textContentAdult6.setTag(R.id.phoneAdult6, data.getStringExtra("adultPhone"));
                        textContentAdult6.setTag(R.id.bornAdult6, data.getStringExtra("adultBorn"));
                        textContentAdult6.setTag(R.id.bornShowAdult6, data.getStringExtra("adultBornShow"));

//                 
                        textViewNoIdentitasAdultShip6.setText(data.getStringExtra("adultId"));
                        //textViewMobileAdultShip6.setText(data.getStringExtra("adultPhone"));
                        namePassager = data.getStringExtra("adultName");
                        sexPassanger = data.getStringExtra("adultSexTemp");
//           
                        noId = data.getStringExtra("adultId");
//                    phone = data.getStringExtra("adultPhone");
                        bornDate = data.getStringExtra("adultBorn");


                        listNameAdult.add(5, namePassager);
                        listBirthdateAdult.add(5, bornDate);
                        listIdAdult.add(5, noId);
                        listGenderAdult.add(5, sexPassanger);
//                    listPhoneAdult.add(5, phone);
//                 
                        textActionAdult6.setVisibility(View.VISIBLE);
                        imageActionAdult6.setVisibility(View.GONE);
                    } else if (requestCode == TravelActionCode.ADULT7) {// adult 7
                        imageViewExpandPassangerAdultShip7.performClick();
//                        panelPassangerAdultShip7Detail.setBackgroundResource(R.drawable.shape_card_primary);
                        cardPassangerAdult7.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
                        if (!data.getBooleanExtra(ShipKeyPreference.isNewShip, false)) {

                            listNameAdult.remove(6);
                            listBirthdateAdult.remove(6);
                            listIdAdult.remove(6);
                            listGenderAdult.remove(6);
//                        listPhoneAdult.remove(6);
                        }
                        //       Log.d(TAG, "onActivityResult adult 7: " + MemoryStore.get(this, "adultBorn") + " " + data.getStringExtra("adultBorn"));
                        textContentAdult7.setText(data.getStringExtra("adultName"));
                        textContentAdult7.setTag(R.id.sexAdult7, data.getStringExtra("adultSex"));
                        textContentAdult7.setTag(R.id.sexTempAdult7, data.getStringExtra("adultSexTemp"));

                        textContentAdult7.setTag(R.id.nameAdult7, data.getStringExtra("adultName"));
                        textContentAdult7.setTag(R.id.idAdult7, data.getStringExtra("adultId"));
//                    textContentAdult7.setTag(R.id.phoneAdult7, data.getStringExtra("adultPhone"));
                        textContentAdult7.setTag(R.id.bornAdult7, data.getStringExtra("adultBorn"));
                        textContentAdult7.setTag(R.id.bornShowAdult7, data.getStringExtra("adultBornShow"));

//                 
                        textViewNoIdentitasAdultShip7.setText(data.getStringExtra("adultId"));
                        //textViewMobileAdultShip7.setText(data.getStringExtra("adultPhone"));
                        namePassager = data.getStringExtra("adultName");
                        sexPassanger = data.getStringExtra("adultSexTemp");
//           
                        noId = data.getStringExtra("adultId");
//                    phone = data.getStringExtra("adultPhone");
                        bornDate = data.getStringExtra("adultBorn");


                        listNameAdult.add(6, namePassager);
                        listBirthdateAdult.add(6, bornDate);
                        listIdAdult.add(6, noId);
                        listGenderAdult.add(6, sexPassanger);
                        //      listPhoneAdult.add(6, phone);
//                 
                        textActionAdult7.setVisibility(View.VISIBLE);
                        imageActionAdult7.setVisibility(View.GONE);
                    } else if (requestCode == TravelActionCode.ADULT8) {// adult 8
                        imageViewExpandPassangerAdultShip8.performClick();
//                        panelPassangerAdultShip8Detail.setBackgroundResource(R.drawable.shape_card_primary);
                        cardPassangerAdult8.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
                        if (!data.getBooleanExtra(ShipKeyPreference.isNewShip, false)) {

                            listNameAdult.remove(7);
                            listBirthdateAdult.remove(7);
                            listIdAdult.remove(7);
                            listGenderAdult.remove(7);
//                        listPhoneAdult.remove(7);
                        }
                        //       Log.d(TAG, "onActivityResult adult 8: " + MemoryStore.get(this, "adultBorn") + " " + data.getStringExtra("adultBorn"));
                        textContentAdult8.setText(data.getStringExtra("adultName"));
                        textContentAdult8.setTag(R.id.sexAdult8, data.getStringExtra("adultSex"));
                        textContentAdult8.setTag(R.id.sexTempAdult8, data.getStringExtra("adultSexTemp"));

                        textContentAdult8.setTag(R.id.nameAdult8, data.getStringExtra("adultName"));
                        textContentAdult8.setTag(R.id.idAdult8, data.getStringExtra("adultId"));
//                    textContentAdult8.setTag(R.id.phoneAdult8, data.getStringExtra("adultPhone"));
                        textContentAdult8.setTag(R.id.bornAdult8, data.getStringExtra("adultBorn"));
                        textContentAdult8.setTag(R.id.bornShowAdult8, data.getStringExtra("adultBornShow"));

//                 
                        textViewNoIdentitasAdultShip8.setText(data.getStringExtra("adultId"));
                        //textViewMobileAdultShip8.setText(data.getStringExtra("adultPhone"));
                        namePassager = data.getStringExtra("adultName");
                        sexPassanger = data.getStringExtra("adultSexTemp");
//           
                        noId = data.getStringExtra("adultId");
//                    phone = data.getStringExtra("adultPhone");
                        bornDate = data.getStringExtra("adultBorn");


                        listNameAdult.add(7, namePassager);
                        listBirthdateAdult.add(7, bornDate);
                        listIdAdult.add(7, noId);
                        listGenderAdult.add(7, sexPassanger);
                        //   listPhoneAdult.add(7, phone);
//                 
                        textActionAdult8.setVisibility(View.VISIBLE);
                        imageActionAdult8.setVisibility(View.GONE);
                    } else if (requestCode == TravelActionCode.ADULT9) {// adult 9
                        imageViewExpandPassangerAdultShip9.performClick();
//                        panelPassangerAdultShip9Detail.setBackgroundResource(R.drawable.shape_card_primary);
                        cardPassangerAdult9.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
                        if (!data.getBooleanExtra(ShipKeyPreference.isNewShip, false)) {

                            listNameAdult.remove(8);
                            listBirthdateAdult.remove(8);
                            listIdAdult.remove(8);
                            listGenderAdult.remove(8);
//                        listPhoneAdult.remove(8);
                        }
                        //       Log.d(TAG, "onActivityResult adult 9: " + MemoryStore.get(this, "adultBorn") + " " + data.getStringExtra("adultBorn"));
                        textContentAdult9.setText(data.getStringExtra("adultName"));

                        textContentAdult9.setTag(R.id.sexAdult9, data.getStringExtra("adultSex"));
                        textContentAdult9.setTag(R.id.sexTempAdult9, data.getStringExtra("adultSexTemp"));
                        textContentAdult9.setTag(R.id.nameAdult9, data.getStringExtra("adultName"));
                        textContentAdult9.setTag(R.id.idAdult9, data.getStringExtra("adultId"));
//                    textContentAdult9.setTag(R.id.phoneAdult9, data.getStringExtra("adultPhone"));
                        textContentAdult9.setTag(R.id.bornAdult9, data.getStringExtra("adultBorn"));
                        textContentAdult9.setTag(R.id.bornShowAdult9, data.getStringExtra("adultBornShow"));

//                 
                        textViewNoIdentitasAdultShip9.setText(data.getStringExtra("adultId"));
                        //textViewMobileAdultShip9.setText(data.getStringExtra("adultPhone"));
                        namePassager = data.getStringExtra("adultName");
                        sexPassanger = data.getStringExtra("adultSexTemp");
//           
                        noId = data.getStringExtra("adultId");
//                    phone = data.getStringExtra("adultPhone");
                        bornDate = data.getStringExtra("adultBorn");


                        listNameAdult.add(8, namePassager);
                        listBirthdateAdult.add(8, bornDate);
                        listIdAdult.add(8, noId);
                        listGenderAdult.add(8, sexPassanger);
                        //    listPhoneAdult.add(8, phone);
//                 
                        textActionAdult9.setVisibility(View.VISIBLE);
                        imageActionAdult9.setVisibility(View.GONE);
                    } else if (requestCode == TravelActionCode.ADULT10) {// adult 10
                        imageViewExpandPassangerAdultShip10.performClick();
//                        panelPassangerAdultShip10Detail.setBackgroundResource(R.drawable.shape_card_primary);
                        cardPassangerAdult10.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
                        if (!data.getBooleanExtra(ShipKeyPreference.isNewShip, false)) {

                            listNameAdult.remove(9);
                            listBirthdateAdult.remove(9);
                            listIdAdult.remove(9);
                            listGenderAdult.remove(9);
//                        listPhoneAdult.remove(9);
                        }
                        //       Log.d(TAG, "onActivityResult adult 10: " + MemoryStore.get(this, "adultBorn") + " " + data.getStringExtra("adultBorn"));
                        textContentAdult10.setText(data.getStringExtra("adultName"));

                        textContentAdult10.setTag(R.id.sexAdult10, data.getStringExtra("adultSex"));
                        textContentAdult10.setTag(R.id.sexTempAdult10, data.getStringExtra("adultSexTemp"));
                        textContentAdult10.setTag(R.id.nameAdult10, data.getStringExtra("adultName"));
                        textContentAdult10.setTag(R.id.idAdult10, data.getStringExtra("adultId"));
//                    textContentAdult10.setTag(R.id.phoneAdult10, data.getStringExtra("adultPhone"));
                        textContentAdult10.setTag(R.id.bornAdult10, data.getStringExtra("adultBorn"));
                        textContentAdult10.setTag(R.id.bornShowAdult10, data.getStringExtra("adultBornShow"));

//                 
                        textViewNoIdentitasAdultShip10.setText(data.getStringExtra("adultId"));
                        //textViewMobileAdultShip10.setText(data.getStringExtra("adultPhone"));
                        namePassager = data.getStringExtra("adultName");
                        sexPassanger = data.getStringExtra("adultSexTemp");
//           
                        noId = data.getStringExtra("adultId");
//                    phone = data.getStringExtra("adultPhone");
                        bornDate = data.getStringExtra("adultBorn");


                        listNameAdult.add(9, namePassager);
                        listBirthdateAdult.add(9, bornDate);
                        listIdAdult.add(9, noId);
                        listGenderAdult.add(9, sexPassanger);

                        //  listPhoneAdult.add(9, phone);
//                 
                        textActionAdult10.setVisibility(View.VISIBLE);
                        imageActionAdult10.setVisibility(View.GONE);
                    }

                } else if (data.getStringExtra("status").equals("infant")) {// INFANT/BAYI
                    imageViewExpandPassangerInfantShip1.performClick();
//                    panelPassangerInfantShip1Detail.setBackgroundResource(R.drawable.shape_card_primary);
                    cardPassangerInfant1.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
                    if (requestCode == TravelActionCode.INFANT1) {// infant 1
                        if (!data.getBooleanExtra(ShipKeyPreference.isNewShip, false)) {

                            listNameInfant.remove(0);
                            listBirthdateInfant.remove(0);
                            listGenderInfant.remove(0);
                        }
                        //    Log.d(TAG, "onActivityResult infant 1: " + MemoryStore.get(this, "infantBorn") + " " + data.getStringExtra("infantBorn"));
                        textContentInfant1.setText(data.getStringExtra("infantName"));
                        textContentInfant1.setTag(R.id.sexInfant1, data.getStringExtra("infantSex"));
                        textContentInfant1.setTag(R.id.sexTempInfant1, data.getStringExtra("infantSexTemp"));
                        textContentInfant1.setTag(R.id.nameInfant1, data.getStringExtra("infantName"));
                        textContentInfant1.setTag(R.id.bornInfant1, data.getStringExtra("infantBorn"));
                        textContentInfant1.setTag(R.id.bornShowInfant1, data.getStringExtra("infantBornShow"));

//             
                        textViewTanggalLahirInfantShip1.setText(data.getStringExtra("infantBornShow"));
                        namePassager = data.getStringExtra("infantName");
                        sexPassanger = data.getStringExtra("infantSexTemp");
                        bornDate = data.getStringExtra("infantBorn");

                        listNameInfant.add(0, namePassager);
                        listBirthdateInfant.add(0, bornDate);
                        listGenderInfant.add(0, sexPassanger);
                        textActionInfant1.setVisibility(View.VISIBLE);
                        imageActionInfant1.setVisibility(View.GONE);
                    } else if (requestCode == TravelActionCode.INFANT2) {// infant 2
                        imageViewExpandPassangerInfantShip2.performClick();
//                        panelPassangerInfantShip2Detail.setBackgroundResource(R.drawable.shape_card_primary);
                        cardPassangerInfant2.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
                        if (!data.getBooleanExtra(ShipKeyPreference.isNewShip, false)) {

                            listNameInfant.remove(1);
                            listBirthdateInfant.remove(1);
                            listGenderInfant.remove(1);
                        }
                        //   Log.d(TAG, "onActivityResult infant 2: " + MemoryStore.get(this, "infantBorn") + " " + data.getStringExtra("infantBorn"));
                        textContentInfant2.setText(data.getStringExtra("infantName"));
                        textContentInfant2.setTag(R.id.sexInfant2, data.getStringExtra("infantSex"));
                        textContentInfant2.setTag(R.id.sexTempInfant2, data.getStringExtra("infantSexTemp"));
                        textContentInfant2.setTag(R.id.nameInfant2, data.getStringExtra("infantName"));
                        textContentInfant2.setTag(R.id.bornInfant2, data.getStringExtra("infantBorn"));
                        textContentInfant2.setTag(R.id.bornShowInfant2, data.getStringExtra("infantBornShow"));

                        textViewTanggalLahirInfantShip2.setText(data.getStringExtra("infantBornShow"));
                        namePassager = data.getStringExtra("infantName");
                        sexPassanger = data.getStringExtra("infantSexTemp");
//                    fullName = data.getStringExtra("infantName").split(" ");
//  
                        bornDate = data.getStringExtra("infantBorn");

                        //      passengersList.add(data.getStringExtra("infantTitleTemp") + ". " + firstName + " " + lastName);
                        //   Log.d(TAG, "onActivityResult: CHD;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1");
                        // infantPassengger[1] = "CHD;" + initTitle + ";" + firstName + ";" + lastName + ";" + bornDate + ";1";
                        listNameInfant.add(1, namePassager);
                        listBirthdateInfant.add(1, bornDate);
                        listGenderInfant.add(1, sexPassanger);

                        textActionInfant2.setVisibility(View.VISIBLE);
                        imageActionInfant2.setVisibility(View.GONE);
                    } else if (requestCode == TravelActionCode.INFANT3) {// infant 3
                        imageViewExpandPassangerInfantShip3.performClick();
//                        panelPassangerInfantShip3Detail.setBackgroundResource(R.drawable.shape_card_primary);
                        cardPassangerInfant3.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
                        if (!data.getBooleanExtra(ShipKeyPreference.isNewShip, false)) {

                            listNameInfant.remove(2);
                            listBirthdateInfant.remove(2);
                            listGenderInfant.remove(2);
                        }
//                    Log.d(TAG, "onActivityResult infant 3: " + MemoryStore.get(this, "infantBorn") + " " + data.getStringExtra("infantBorn"));
                        textContentInfant3.setText(data.getStringExtra("infantName"));
                        textContentInfant3.setTag(R.id.sexInfant3, data.getStringExtra("infantSex"));
                        textContentInfant3.setTag(R.id.sexTempInfant3, data.getStringExtra("infantSexTemp"));
                        textContentInfant3.setTag(R.id.nameInfant3, data.getStringExtra("infantName"));
                        textContentInfant3.setTag(R.id.bornInfant3, data.getStringExtra("infantBorn"));
                        textContentInfant3.setTag(R.id.bornShowInfant3, data.getStringExtra("infantBornShow"));

                        textViewTanggalLahirInfantShip3.setText(data.getStringExtra("infantBornShow"));
                        namePassager = data.getStringExtra("infantName");
                        sexPassanger = data.getStringExtra("infantSexTemp");
//         
                        bornDate = data.getStringExtra("infantBorn");
                        listNameInfant.add(2, namePassager);
                        listBirthdateInfant.add(2, bornDate);
                        listGenderInfant.add(2, sexPassanger);
//
                        textActionInfant3.setVisibility(View.VISIBLE);
                        imageActionInfant3.setVisibility(View.GONE);
                    } else if (requestCode == TravelActionCode.INFANT4) {// infant 4
                        imageViewExpandPassangerInfantShip4.performClick();
//                        panelPassangerInfantShip4Detail.setBackgroundResource(R.drawable.shape_card_primary);
                        cardPassangerInfant4.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
                        if (!data.getBooleanExtra(ShipKeyPreference.isNewShip, false)) {
                            //if (passengersList.size() > 0) {
                            //  passengersList.remove(0);
                            // }
                            listNameInfant.remove(3);
                            listBirthdateInfant.remove(3);
                            listGenderInfant.remove(3);
                        }
                        //    Log.d(TAG, "onActivityResult infant 4: " + MemoryStore.get(this, "infantBorn") + " " + data.getStringExtra("infantBorn"));
                        textContentInfant4.setText(data.getStringExtra("infantName"));
                        textContentInfant4.setTag(R.id.sexInfant4, data.getStringExtra("infantSex"));
                        textContentInfant4.setTag(R.id.sexTempInfant4, data.getStringExtra("infantSexTemp"));
                        textContentInfant4.setTag(R.id.nameInfant4, data.getStringExtra("infantName"));
                        textContentInfant4.setTag(R.id.bornInfant4, data.getStringExtra("infantBorn"));
                        textContentInfant4.setTag(R.id.bornShowInfant4, data.getStringExtra("infantBornShow"));

                        textViewTanggalLahirInfantShip4.setText(data.getStringExtra("infantBornShow"));
                        namePassager = data.getStringExtra("infantName");
                        sexPassanger = data.getStringExtra("infantSexTemp");
//                
                        bornDate = data.getStringExtra("infantBorn");


                        listNameInfant.add(3, namePassager);
                        listBirthdateInfant.add(3, bornDate);
                        listGenderInfant.add(3, sexPassanger);
//
                        textActionInfant4.setVisibility(View.VISIBLE);
                        imageActionInfant4.setVisibility(View.GONE);
                    } else if (requestCode == TravelActionCode.INFANT5) {// infant 5
                        imageViewExpandPassangerInfantShip5.performClick();
//                        panelPassangerInfantShip5Detail.setBackgroundResource(R.drawable.shape_card_primary);
                        cardPassangerInfant5.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
                        if (!data.getBooleanExtra(ShipKeyPreference.isNewShip, false)) {
                            //if (passengersList.size() > 0) {
                            //  passengersList.remove(0);
                            // }
                            listNameInfant.remove(4);
                            listBirthdateInfant.remove(4);
                            listGenderInfant.remove(4);
                        }
                        //    Log.d(TAG, "onActivityResult infant 5: " + MemoryStore.get(this, "infantBorn") + " " + data.getStringExtra("infantBorn"));
                        textContentInfant5.setText(data.getStringExtra("infantName"));
                        textContentInfant5.setTag(R.id.sexInfant5, data.getStringExtra("infantSex"));
                        textContentInfant5.setTag(R.id.sexTempInfant5, data.getStringExtra("infantSexTemp"));
                        textContentInfant5.setTag(R.id.nameInfant5, data.getStringExtra("infantName"));
                        textContentInfant5.setTag(R.id.bornInfant5, data.getStringExtra("infantBorn"));
                        textContentInfant5.setTag(R.id.bornShowInfant5, data.getStringExtra("infantBornShow"));

                        textViewTanggalLahirInfantShip5.setText(data.getStringExtra("infantBornShow"));
                        namePassager = data.getStringExtra("infantName");
                        sexPassanger = data.getStringExtra("infantSexTemp");
//                
                        bornDate = data.getStringExtra("infantBorn");


                        listNameInfant.add(4, namePassager);
                        listBirthdateInfant.add(4, bornDate);
                        listGenderInfant.add(4, sexPassanger);
//
                        textActionInfant5.setVisibility(View.VISIBLE);
                        imageActionInfant5.setVisibility(View.GONE);
                    }
                }


            }
        }

    }
}

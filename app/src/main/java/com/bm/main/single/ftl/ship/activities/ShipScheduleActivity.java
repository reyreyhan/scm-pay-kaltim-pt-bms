package com.bm.main.single.ftl.ship.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;



import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.ProdukGroup;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.templates.AutoScaleTextView;
import com.bm.main.fpl.templates.shimmer.ShimmerFrameLayout;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.Utils;
import com.bm.main.fpl.utils.shimmer;
import com.bm.main.scm.R;
import com.bm.main.scm.SBFApplication;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bm.main.single.ftl.ship.adapters.ShipScheduleAdapter;
import com.bm.main.single.ftl.ship.adapters.ShipScheduleTabsAdapter;
import com.bm.main.single.ftl.ship.constants.ShipKeyPreference;
import com.bm.main.single.ftl.ship.constants.ShipPath;
import com.bm.main.single.ftl.ship.models.ShipFareModel;
import com.bm.main.single.ftl.ship.models.ShipModel;
import com.bm.main.single.ftl.utils.RequestUtilsTravel;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class ShipScheduleActivity extends BaseActivity implements ProgressResponseCallback, ShipScheduleTabsAdapter.OnClickScheduleTabs, ShipScheduleAdapter.OnClickScheduleShip {
    private static final String TAG = ShipScheduleActivity.class.getSimpleName();
    RecyclerView recyclerViewTabs;
    RecyclerView recyclerViewSchedule;
    ShipScheduleTabsAdapter shipScheduleTabsAdapter;
    ShipScheduleAdapter shipScheduleAdapter;

//    public ImageView image_view_filter_check;
//    private ImageView image_view_sort_check;

    private RelativeLayout emptyView;
    private ShimmerFrameLayout mShimmerViewContainer;
    private ShimmerFrameLayout shimmer_view_container_tab;

//    ShipDataModel shipDataModel;

    public ShipScheduleActivity mInstance;

    boolean show = false;
    //    public ArrayList<ShipDataModel> list = new ArrayList<>();
//    public ArrayList<ShipDataModel> real_list = new ArrayList<>();
    @NonNull
    ArrayList<ShipFareModel> shipModels = new ArrayList<>();
    @NonNull
    ArrayList<String> shipNames = new ArrayList<>();
    @NonNull
    ArrayList<String> classNames = new ArrayList<>();
    @NonNull
    ArrayList<String> times = new ArrayList<>();

    @NonNull
    ArrayList<String> shipNamesSelected = new ArrayList<>();
    @NonNull
    ArrayList<String> classNamesSelected = new ArrayList<>();
    @NonNull
    ArrayList<String> timesSelected = new ArrayList<>();
    @NonNull
    ArrayList<String> dateSchedule = new ArrayList<>();
    private AutoScaleTextView textTgl_Pax;
    @Nullable
    String asal, tujuan;
//    TextView textViewNamaKapal, textViewBerangkatKapal, textViewTibaKapal, textViewRoute;
//    ImageView imageViewNext, imageViewDown;
//    RelativeLayout relRoute;

    //    LinearLayout linHeader;
    @NonNull
    String[] months = new String[]{
            "Januari", "Februari", "Maret",
            "April", "Mei", "Juni",
            "Juli", "Agustus", "September",
            "Oktober", "November", "Desember"
    };
    String description;
    int dewasa;

    int bayi;
    int pax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ship_schedule_activity);
        PreferenceClass.remove(ShipKeyPreference.shipTotalPenumpang);
        asal = PreferenceClass.getString(ShipKeyPreference.pelabuhanNamaAsal, "");
        String[] arrAsal = asal.split("\\(");
        tujuan = PreferenceClass.getString(ShipKeyPreference.pelabuhanNamaTujuan, "");
        String[] arrTujuan = tujuan.split("\\(");
//        depDate = PreferenceClass.getString(ShipKeyPreference.pela, "");
        dewasa = PreferenceClass.getInt(ShipKeyPreference.countAdultShip, 1);

        bayi = PreferenceClass.getInt(ShipKeyPreference.countInfantShip, 0);
        pax = dewasa + bayi;
        // MemoryStore.set(this, "jmlpenumpang", pax);
        PreferenceClass.putInt(ShipKeyPreference.shipTotalPenumpang, pax);


        toolbar = findViewById(R.id.toolbar);
        init(0);
        AutoScaleTextView textViewAsal = findViewById(R.id.asal);
        textViewAsal.setText(arrAsal[0]);
        AutoScaleTextView textViewTujuan = findViewById(R.id.tujuan);
        textViewTujuan.setText(arrTujuan[0]);
        textTgl_Pax = findViewById(R.id.textViewToolbarSubTitle);
        // = months[SavePref.getInt("shipMonth")] + " " + SavePref.getInt("shipYear") + ", " + SavePref.getInt("shipMan") + " Laki-dewasa, " + SavePref.getInt("shipWoman") + " Perempuan";

        if (bayi > 0 && dewasa == 0) {
            description = months[PreferenceClass.getInt(ShipKeyPreference.shipMonth, 0)] + " " + PreferenceClass.getInt(ShipKeyPreference.shipYear, 0) + ", " + bayi + " Bayi";
            textTgl_Pax.setText(description);
        } else if (dewasa > 0 && bayi == 0) {
            description = months[PreferenceClass.getInt(ShipKeyPreference.shipMonth, 0)] + " " + PreferenceClass.getInt(ShipKeyPreference.shipYear, 0) + ", " + dewasa + " Dewasa";
            textTgl_Pax.setText(description);
        } else if (bayi > 0 && dewasa > 0) {


            description = months[PreferenceClass.getInt(ShipKeyPreference.shipMonth, 0)] + " " + PreferenceClass.getInt(ShipKeyPreference.shipYear, 0) + ", " + dewasa + " Dewasa, " + bayi + " Bayi";
            Log.d(TAG, "onCreate: " + description);
            textTgl_Pax.setText(description);
        }
        // textTgl_Pax.setText(depDate + " - " + pax + " org");
        emptyView = findViewById(R.id.empty_view_train);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        shimmer_view_container_tab = findViewById(R.id.shimmer_view_container_tab);
        shimmer.selectPreset(0, mShimmerViewContainer);
        shimmer.selectPreset(0, shimmer_view_container_tab);

//        image_view_filter_check = findViewById(R.id.image_view_filter_check);
//        image_view_filter_check.setVisibility(View.GONE);
//        image_view_sort_check = findViewById(R.id.image_view_sort_check);
//        image_view_sort_check.setVisibility(View.GONE);
        recyclerViewSchedule = findViewById(R.id.recylerList);
        recyclerViewTabs = findViewById(R.id.recyclerViewTabs);
//        linHeader= findViewById(R.id.linHeader);
        Intent intent = this.getIntent();
        if (intent != null)
            show = intent.getBooleanExtra("cari", true);
        if (show) {

            //  progressBar.setProgress(0);
            //   progressBar.setVisibility(View.VISIBLE);


            if (mShimmerViewContainer.getVisibility() == View.GONE) {
                mShimmerViewContainer.setVisibility(View.VISIBLE);
                mShimmerViewContainer.startShimmerAnimation();

            }
            if (shimmer_view_container_tab.getVisibility() == View.GONE) {
                shimmer_view_container_tab.setVisibility(View.VISIBLE);
                shimmer_view_container_tab.startShimmerAnimation();

            }
//                progressBar.setProgress(0);
//                progressBar.setVisibility(View.VISIBLE);
            recyclerViewSchedule.setVisibility(View.GONE);
            recyclerViewTabs.setVisibility(View.GONE);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    searchingShip();
                }
            });
        }


        recyclerViewSchedule.setHasFixedSize(true);
//        recyclerViewTabs.setHasFixedSize(true);
//
        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
//        RecyclerView.LayoutManager mLayoutManagerTab = new LinearLayoutManager(this);
        recyclerViewSchedule.setLayoutManager(mLayoutManager);
//        recyclerViewTabs.setLayoutManager(mLayoutManagerTab);

        shipScheduleAdapter = new ShipScheduleAdapter(this, shipModels, this);
        shipScheduleTabsAdapter = new ShipScheduleTabsAdapter(this, dateSchedule, this);
//        adapterTimeFilter = new TimeFilterAdapter(this, listTimeFilter);
//        adapterKeretaFilter = new KeretaFilterAdapter(this, listKeretaFilter);
//        adapterKelasFilter = new KelasFilterAdapter(this, listKelasFilter);

        recyclerViewSchedule.setAdapter(shipScheduleAdapter);
        recyclerViewTabs.setAdapter(shipScheduleTabsAdapter);

//        textViewNamaKapal = findViewById(R.id.textViewNamaKapal);
//        textViewBerangkatKapal = findViewById(R.id.textViewBerangkatKapal);
//        textViewTibaKapal = findViewById(R.id.textViewTibaKapal);
//        textViewRoute = findViewById(R.id.textViewRoute);
////
//        imageViewNext = findViewById(R.id.imageViewNext);
//        imageViewDown = findViewById(R.id.imageViewDown);
//
//        relRoute = findViewById(R.id.relRoute);


    }


    private void searchingShip() {
        logEventFireBase(ProdukGroup.PELNI, ProdukGroup.PELNI, FirebaseAnalytics.Event.SEARCH, EventParam.EVENT_SUCCESS, TAG);
        FrameLayout loadingView =(FrameLayout) View.inflate(this,R.layout.loading_bar_full_dialog, null);
        TextView text = loadingView.findViewById(R.id.textContentProgressBar);
        text.setText("Mohon tunggu, Pencarian kapal anda sedang kami proses");
        openProgressBarDialog(this, loadingView);
        //list.clear();
//        listKelasFilter.clear();
//        listTimeFilter.clear();
//        listKeretaFilter.clear();
        // real_list.clear();
//        filteredTimeList.clear();
//        filteredKelasList.clear();
//        filteredKeretaList.clear();
//        isFilter = false;
//        isFilterKelas = false;
//        isFilterKereta = false;
//        isFilterTime = false;
//        checked = 1;
//        if (image_view_filter_check.getVisibility() == View.VISIBLE) {
//            image_view_filter_check.setVisibility(View.GONE);
//        }
//
//        if (image_view_sort_check.getVisibility() == View.VISIBLE) {
//            image_view_sort_check.setVisibility(View.GONE);
//        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("origin", PreferenceClass.getString(ShipKeyPreference.pelabuhanKodeAsal, ""));
            jsonObject.put("destination", PreferenceClass.getString(ShipKeyPreference.pelabuhanKodeTujuan, ""));
            jsonObject.put("startDate", PreferenceClass.getString(ShipKeyPreference.shipStartDate, ""));
            jsonObject.put("endDate", PreferenceClass.getString(ShipKeyPreference.shipEndDate, ""));
            jsonObject.put("token", PreferenceClass.getToken());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "cariKapal: " + jsonObject);
        RequestUtilsTravel.transportWithProgressResponse(this, ShipPath.SEARCH, jsonObject, TravelActionCode.SEARCH, this);

    }


    boolean isStillRunning = false;

    @Override
    public void onSuccess(final int actionCode, @NonNull final JSONObject response) {
        if (actionCode == TravelActionCode.AVAIL) {
            progressBar.setVisibility(View.GONE);
            try {
                if (response.getString("rc").equals(ResponseCode.SUCCESS)) {

                    JSONObject jsonObject = new JSONObject();
                    try {

                        jsonObject = response.getJSONObject("data");

                        int M = jsonObject.getInt("M");
                        int F = jsonObject.getInt("F");

                        Log.d(TAG, "onSuccess: M=" + M + " F=" + F);
                        if ((M > 0) && (F > 0)) {
                            //ada
                            viewHolder.btnPesan.setText("TERSEDIA");
                            // viewHolder.btnPesan.setTag("A");
                            shipFareModel.setTagButton("A");
                            viewHolder.btnPesan.setBackground(ContextCompat.getDrawable(ShipScheduleActivity.this, R.drawable.selector_button_green_pressed));
                        } else if ((M < 1) && (F > 0)) {
                            viewHolder.btnPesan.setText("TERSEDIA");
//                    viewHolder.btnPesan.setTag("F");
                            shipFareModel.setTagButton("F");
                            viewHolder.btnPesan.setBackground(ContextCompat.getDrawable(ShipScheduleActivity.this, R.drawable.selector_button_green_pressed));
                            viewHolder.textViewInfoAvail.setVisibility(View.VISIBLE);
                            viewHolder.textViewInfoAvail.setText("untuk penumpang wanita");
                            //F tersedia
                        } else if ((M > 0) && (F < 1)) {
                            //M tersedia
                            viewHolder.btnPesan.setText("TERSEDIA");
//                    viewHolder.btnPesan.setTag("M");
                            shipFareModel.setTagButton("M");

                            viewHolder.btnPesan.setBackground(ContextCompat.getDrawable(ShipScheduleActivity.this, R.drawable.selector_button_green_pressed));
                            viewHolder.textViewInfoAvail.setVisibility(View.VISIBLE);
                            viewHolder.textViewInfoAvail.setText("untuk penumpang pria");
                        } else {
                            //habis
                            viewHolder.btnPesan.setText("TIDAK TERSEDIA");
                            shipFareModel.setTagButton("H");
//                    viewHolder.btnPesan.setTag("H");
                            viewHolder.btnPesan.setBackground(ContextCompat.getDrawable(ShipScheduleActivity.this, R.drawable.selector_button_grey_pressed));
                            //viewHolder.btnPesan.setClickable(false);
                        }
                        shipFareModel.setSelected(!shipFareModel.isSelected());

                    } catch (Exception e) {

                    }
                } else {

                }
            } catch (Exception e) {

            }

        } else {


            // Log.d(TAG, "onSuccess: " + response.toString());
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
            closeProgressBarDialog();
            if (mShimmerViewContainer.getVisibility() == View.VISIBLE) {
                mShimmerViewContainer.setVisibility(View.GONE);
                mShimmerViewContainer.stopShimmerAnimation();

            }
            if (shimmer_view_container_tab.getVisibility() == View.VISIBLE) {
                shimmer_view_container_tab.setVisibility(View.GONE);
                shimmer_view_container_tab.stopShimmerAnimation();

            }
//        try {
//            shipDataModel = gson.fromJson(response.toString(), ShipDataModel.class);
//            // data.clear();
//            if (shipDataModel.getRc().equals(ResponseCode.SUCCESS)) {
//
//                list.clear();
//
//               // PreferenceClass.putJSONObject(ShipKeyPreference.stationListData, response);
//                list.add(shipDataModel);
//               // Collections.sort(list);
//                shipScheduleAdapter.notifyDataSetChanged();
//                shipScheduleTabsAdapter.notifyDataSetChanged();
//
//                emptyView.setVisibility(View.GONE);
//                recyclerViewSchedule.setVisibility(View.VISIBLE);
//            } else {
//                emptyView.setVisibility(View.VISIBLE);
//                recyclerViewSchedule.setVisibility(View.GONE);
////                showToastCustom(ScheduleTrainResultActivity.this, 2, response.getString("rd"));
//                snackBarCustomAction(findViewById(R.id.rootLayout), 0, response.getString("rd"), 2);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
            try {
                if (response.getString("rc").equals(ResponseCode.SUCCESS)) {

                    JSONArray jsonArray = new JSONArray();
                    try {
                        shipModels = new ArrayList<>();
                        jsonArray = response.getJSONArray("data");
//                    PreferenceClass.putJSONArray(ShipKeyPreference.dataSchedule,jsonArray);
                        if (jsonArray.length() > 0) {
                            dateSchedule.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {

                                shipModels.addAll(new ShipModel(jsonArray.getJSONObject(i)).getFARES());
                            }
                            for (ShipFareModel shipFareModel : shipModels) {
                                Log.d(TAG, "onSuccess: " + shipFareModel.getShipModel().getDEP_DATE());
                                if (!Utils.isThisDateWithin3DaysRange(shipFareModel.getShipModel().getDEP_DATE(), "yyyyMMdd")) {
                                    String shipName = shipFareModel.getShipModel().getSHIP_NAME();
                                    String className = shipFareModel.getCLASS();
                                    String dateSch = shipFareModel.getShipModel().getDEP_DATE();

                                    if (!shipNames.contains(shipName)) {
                                        shipNames.add(shipName);
                                        shipNamesSelected.add(shipName);
                                    }
                                    if (!classNames.contains(className)) {
                                        classNames.add(className);
                                        classNamesSelected.add(className);
                                    }

                                    if (!dateSchedule.contains(dateSch)) {
                                        dateSchedule.add(dateSch);
                                    }

                                    if (shipFareModel.getShipAvailabilityF().getF().equals("0") && shipFareModel.getShipAvailabilityM().getM().equals("0")) {
                                        // viewHolder.btnPesan.setText("TIDAK TERSEDIA");
                                        shipFareModel.setTagButton("H");
//        viewHolder.btnPesan.setTag(R.id.gender_ship_avail, "H");
                                        //  viewHolder.btnPesan.setBackground(ContextCompat.getDrawable(context, R.drawable.selector_button_grey_pressed));
                                    } else if (shipFareModel.getShipAvailabilityF().getF().equals("1") && shipFareModel.getShipAvailabilityM().getM().equals("0")) {
                                        shipFareModel.setTagButton("F");
                                    } else if (shipFareModel.getShipAvailabilityF().getF().equals("0") && shipFareModel.getShipAvailabilityM().getM().equals("1")) {
                                        shipFareModel.setTagButton("M");
                                    } else if (shipFareModel.getShipAvailabilityF().getF().equals("1") && shipFareModel.getShipAvailabilityM().getM().equals("1")) {
                                        shipFareModel.setTagButton("A");
                                    }
                                } else {
                                    shipFareModel.setTagButton("");
                                }
                            }

                            if (dateSchedule.size() > 0) {
                                Collections.sort(shipModels, availCompare);
                                shipScheduleAdapter.updateList(shipModels);
                                shipScheduleTabsAdapter.updateList(dateSchedule);
                                shipScheduleTabsAdapter.clearSelections();
                                shipScheduleTabsAdapter.toggleSelection(0);
                                shipScheduleAdapter.getFilter().filter(dateSchedule.get(0));

                                //  Collections.sort(shipModels);
                                // onClickScheduleTabs(dateSchedule.get(0),0);
                                // refreshHeader(0);
                                emptyView.setVisibility(View.GONE);
                                recyclerViewSchedule.setVisibility(View.VISIBLE);
//                        linHeader.setVisibility(View.VISIBLE);
                                recyclerViewTabs.setVisibility(View.VISIBLE);
                            } else {
                                emptyView.setVisibility(View.VISIBLE);
//                        linHeader.setVisibility(View.GONE);
                                recyclerViewSchedule.setVisibility(View.GONE);
                                recyclerViewTabs.setVisibility(View.GONE);
                            }
                        } else {
                            emptyView.setVisibility(View.VISIBLE);
//                        linHeader.setVisibility(View.GONE);
                            recyclerViewSchedule.setVisibility(View.GONE);
                            recyclerViewTabs.setVisibility(View.GONE);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                shipScheduleAdapter.notifyDataSetChanged();
//                shipScheduleTabsAdapter.notifyDataSetChanged();

                } else {
                    emptyView.setVisibility(View.VISIBLE);
                    recyclerViewSchedule.setVisibility(View.GONE);
//                linHeader.setVisibility(View.GONE);
                    recyclerViewTabs.setVisibility(View.GONE);
                    snackBarCustomAction(findViewById(R.id.rootLayout), 0, response.getString("rd"), 2);
////
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        isStillRunning = false;
//            }
//        });
    }


    @Override
    public void onFailure(int actionCode, String responseCode, final String
            responseDescription, @NonNull Throwable throwable) {
        Log.d(TAG, "onFailure: " + actionCode + " " + responseCode + " " + throwable.toString());
        // if (actionCode == ActionCode.FARE_TRAIN) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
        closeProgressBarDialog();
        if (mShimmerViewContainer.getVisibility() == View.VISIBLE) {
            mShimmerViewContainer.setVisibility(View.GONE);
            mShimmerViewContainer.stopShimmerAnimation();

        }
        if (shimmer_view_container_tab.getVisibility() == View.VISIBLE) {
            shimmer_view_container_tab.setVisibility(View.GONE);
            shimmer_view_container_tab.stopShimmerAnimation();

        }
        emptyView.setVisibility(View.VISIBLE);
        recyclerViewSchedule.setVisibility(View.GONE);
//        linHeader.setVisibility(View.GONE);
        recyclerViewTabs.setVisibility(View.GONE);
        isStillRunning = false;
        // showToastCustom(ScheduleTrainResultActivity.this, 1, responseDescription);
        snackBarCustomAction(findViewById(R.id.rootLayout), 0, responseDescription, 1);
//            }
//        });
        //  }

        if(actionCode==TravelActionCode.AVAIL){
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onUpdate(int actionCode, final long bytesRead, final long totalSize, final boolean done) {
//        Log.d(TAG, "run: "+done+" "+(100 * bytesRead)+"  "+totalSize+"   "+(int) ((100 * bytesRead) / totalSize));
//        if (actionCode == TravelActionCode.AVAIL) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//               //     Log.d(TAG, "run: "+done+" "+(100 * bytesRead)+"  "+totalSize+"   "+(int) ((100 * bytesRead) / totalSize));
////                progressBar.setProgress((100 * value) / PreferenceClass.getJSONObject(FlightKeyPreference.dataConfigFlight).length());
//                    progressBar.setProgress((int) ((100 * bytesRead) / totalSize));
////                progressBar.setProgress((100 * value) / code.length());
////            textViewfilter.setTextColor(ContextCompat.getColor(FlightScheduleActivity.this, R.color.md_white_1000));
////            textViewfilter.setEnabled(false);
////            textViewSort.setTextColor(ContextCompat.getColor(FlightScheduleActivity.this, R.color.md_white_1000));
////            textViewSort.setEnabled(false);
//                    if (progressBar.getProgress() == 100) {
//                        progressBar.setVisibility(View.GONE);
////                if (list.size() > 0) {
////
////                    textViewfilter.setTextColor(ContextCompat.getColor(FlightScheduleActivity.this, R.color.colorPrimary));
////                    textViewfilter.setEnabled(true);
////                    textViewSort.setTextColor(ContextCompat.getColor(FlightScheduleActivity.this, R.color.colorPrimary));
////                    textViewSort.setEnabled(true);
////
////                } else {
////                    if (mShimmerViewContainer.getVisibility() == View.VISIBLE) {
////                        mShimmerViewContainer.setVisibility(View.GONE);
////                        mShimmerViewContainer.stopShimmerAnimation();
////
////                    }
////                    emptyView.setVisibility(View.VISIBLE);
////                    recyclerView.setVisibility(View.GONE);
////                    textViewfilter.setTextColor(ContextCompat.getColor(FlightScheduleActivity.this, R.color.md_white_1000));
////                    textViewfilter.setEnabled(false);
////                    textViewSort.setTextColor(ContextCompat.getColor(FlightScheduleActivity.this, R.color.md_white_1000));
////                    textViewSort.setEnabled(false);
////                }
//                        //   isStillRunning = false;
//                    }
//                }
//            });
//        }
    }

    //    public void getFind(JSONObject response) {
//        // closeProgressBarDialog();
//
//        Log.d(TAG, "onSuccess schedule ship: " + response.toString());
//
////        if (layout_no_connection.getVisibility() == View.VISIBLE) {
////            layout_no_connection.setVisibility(View.GONE);
////        }
////        if (linMain.getVisibility() == View.GONE) {
////            linMain.setVisibility(View.VISIBLE);
////        }
//        if (mShimmerViewContainer.getVisibility() == View.VISIBLE) {
//            mShimmerViewContainer.setVisibility(View.GONE);
//            mShimmerViewContainer.stopShimmerAnimation();
//        }
//
//    }
    ShipScheduleAdapter.ViewHolder viewHolder;
    ProgressBar progressBar;
    ShipFareModel shipFareModel;

    @Override
    public void onClickScheduleShip(@NonNull ShipFareModel shipFareModel, @NonNull ShipModel shipModel, @NonNull ShipScheduleAdapter.ViewHolder viewHolder) {
        this.viewHolder = viewHolder;
        this.progressBar = viewHolder.progressBar;
        this.shipFareModel = shipFareModel;
        if (viewHolder.btnPesan.getText().equals("CEK TERSEDIA")) {

            //  shipScheduleAdapter.notifyDataSetChanged();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", SBFApplication.config.locale);
            SimpleDateFormat odf = new SimpleDateFormat("yyyy-MM-dd", SBFApplication.config.locale);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("origin", PreferenceClass.getString(ShipKeyPreference.pelabuhanKodeAsal, ""));
                jsonObject.put("originCall", shipModel.getORG_CALL());
                jsonObject.put("destination", PreferenceClass.getString(ShipKeyPreference.pelabuhanKodeTujuan, ""));
                jsonObject.put("destinationCall", shipModel.getDES_CALL());

                try {
                    jsonObject.put("departureDate", odf.format(sdf.parse(shipModel.getDEP_DATE())));
                } catch (ParseException e) {
                    e.printStackTrace();
//                    jsonObject.put("endDate",PreferenceClass.getString(ShipKeyPreference.shipEndDate,""));
                }
                jsonObject.put("shipNumber", shipModel.getSHIP_NO());
                jsonObject.put("subClass", shipFareModel.getSUBCLASS());
                jsonObject.put("male", "0");
                jsonObject.put("female", "0");
                jsonObject.put("token", PreferenceClass.getToken());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.d(TAG, "AVAIL KELAS: " + jsonObject);
            RequestUtilsTravel.transportWithProgressResponse(this, ShipPath.AVAIL, jsonObject, TravelActionCode.AVAIL, this);
          //  progressBar.setProgress(0);
            progressBar.setVisibility(View.VISIBLE);


        } else if (viewHolder.btnPesan.getText().equals("TIDAK TERSEDIA")) {

        } else if (viewHolder.btnPesan.getText().equals("TERSEDIA")) {
            Log.d(TAG, "onClickScheduleShip: " + shipFareModel.getTagButton() + " " + shipModel.getSHIP_NAME() + " " + shipModel.getSHIP_NO() + " " + shipFareModel.getCLASS() + " " + shipFareModel.getSUBCLASS());
            PreferenceClass.putString(ShipKeyPreference.avail_gender, shipFareModel.getTagButton());

            Intent intent = new Intent(ShipScheduleActivity.this, ShipBookActivity.class);

//        PreferenceClass.putString(ShipKeyPreference.shipClass,shipFareModel.getCLASS());
//        PreferenceClass.putString(ShipKeyPreference.shipSubClass,shipFareModel.getSUBCLASS());
//        PreferenceClass.putInt(ShipKeyPreference.shipPriceAdult, (int) shipFareModel.getFareDetailA().getTOTAL());
//        PreferenceClass.putInt(ShipKeyPreference.shipPriceInfant, (int) shipFareModel.getFareDetailI().getTOTAL());
//        PreferenceClass.putString(ShipKeyPreference.shipName,  shipFareModel.getShipModel().getSHIP_NAME());
//        PreferenceClass.putString(ShipKeyPreference.shipNo, shipFareModel.getShipModel().getSHIP_NO());

//        intent.putExtra("priceManShip", dataSchedule.getFareDetailA().getTOTAL());
////        intent.putExtra("priceChildShip", dataSchedule.getFareDetailC().getTOTAL());
//        intent.putExtra("priceWomanShip", dataSchedule.getFareDetailI().getTOTAL());
////                    intent.putExtra("originShip", shipModel.getJsonObject().toString());
////                    intent.putExtra("destinationShip", shipModel.getJsonObject().toString());
//        intent.putExtra("nameShip", dataSchedule.getShipModel().getSHIP_NAME().toString());
//        intent.putExtra("numberShip", dataSchedule.getShipModel().getSHIP_NO().toString());
//        intent.putExtra("classShip", dataSchedule.getCLASS().toString());
//        intent.putExtra("subClassShip", dataSchedule.getSUBCLASS().toString());
            PreferenceClass.putJSONObject(ShipKeyPreference.shipFare, shipFareModel.getJsonObject());
            PreferenceClass.putJSONObject(ShipKeyPreference.ship, shipFareModel.getShipModel().getJsonObject());
            intent.putExtra("ship", shipFareModel.getShipModel().getJsonObject().toString());
            intent.putExtra("shipFare", shipFareModel.getJsonObject().toString());
            startActivityForResult(intent, TravelActionCode.IS_FROM_PAY);
        }
    }


    @Override
    public void onClickScheduleTabs(String data, int adapterPos) {

        Log.d(TAG, "onClickScheduleTabs: " + data + " " + adapterPos);
        shipScheduleTabsAdapter.clearSelections();

        shipScheduleTabsAdapter.toggleSelection(adapterPos);
        shipScheduleAdapter.getFilter().filter(data);
        // shipScheduleAdapter.notifyItemChanged(adapterPos);
        // shipScheduleAdapter.updateList(shipModels);

        // recyclerViewSchedule.setAdapter(shipScheduleAdapter);

        //refreshHeader(adapterPos);


    }

    // private void refreshHeader(int position){
    public void onClickScheduleShipFilter(@NonNull ShipModel dataSchedule, ShipFareModel dataFareSchedule) {

//        Log.d(TAG, "refreshHeader: "+position);
//        JSONArray jsonArray= PreferenceClass.getJSONArray(ShipKeyPreference.dataSchedule);
//        ShipModel shipFareModel=shipScheduleAdapter.getData().get(position).getShipModel();
//        JSONObject jsonObjectShip;
//        try {
//            jsonObjectShip = jsonArray.getJSONObject(position);
//            Log.d(TAG, "refreshHeader: "+jsonObjectShip.getString("SHIP_NAME"));
        Log.d(TAG, "refreshHeader: " + dataSchedule.getSHIP_NAME());
        //  Collections.sort(dataFareSchedule,availCompare);
//        ShipModel shipModel = dataSchedule.getShipModel();

//        for(int i=0;i<shipFareModel.getShipModel().getFARES().size();i++){
//            Log.d(TAG, "refreshHeader: "+i+" "+shipFareModel.getShipModel().getFARES().get(i).getShipModel().getSHIP_NAME());
//        }
        //ShipModel shipModel= shipScheduleAdapter.mDisplayList.get()getFARES().get(position).getShipModel();


//        textViewNamaKapal.setText(dataSchedule.getSHIP_NAME() + " (" + dataSchedule.getSHIP_NO() + ") ");
//        relRoute.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // shipFareModel.setExpand(!shipFareModel.isExpand());
//                //notifyDataSetChanged();
//
//                if (textViewRoute.getVisibility() == View.GONE) {
//                    imageViewDown.setVisibility(View.VISIBLE);
//                    textViewRoute.setVisibility(View.VISIBLE);
//                    imageViewNext.setVisibility(View.GONE);
//                } else {
//                    imageViewDown.setVisibility(View.GONE);
//                    textViewRoute.setVisibility(View.GONE);
//                    imageViewNext.setVisibility(View.VISIBLE);
//                }
//
//            }
//        });
//
//
//        String[] routes = dataSchedule.getROUTE().split("/");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", config.locale);
//        SimpleDateFormat odf = new SimpleDateFormat("dd MMMM yyyy", config.locale);
//
//        try {
//            String routeText = "";
//            JSONObject obj = PreferenceClass.getJSONObject(ShipKeyPreference.portListData);
//
//            JSONArray destinationJson = new JSONArray();
//            // JSONArray destinationJson = new JSONArray(SavePref.getInstance(getApplicationContext()).getString("shipDestinationList"));
//            try {
//
//                destinationJson = obj.getJSONArray("data");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            for (String route1 : routes) {
//                String route = route1;
//                if (route.length() < 3)
//                    continue;
//
//                if (route.length() > 3) {
//                    route = route.split("-")[1];
//                }
//
//                for (int j = 0; j < destinationJson.length(); j++) {
//                    JSONObject jsonObject = destinationJson.getJSONObject(j);
//                    String code = jsonObject.getString("CODE");
//                    String name = jsonObject.getString("NAME");
//
//                    if (code.equals(route)) {
//                        routeText += name + " - ";
//                        break;
//                    }
//                }
//            }
//            textViewRoute.setText(routeText.substring(0, routeText.length() - 2));
//        } catch (JSONException e) {
//            e.printStackTrace();
//            textViewRoute.setText("-");
//        }
//
//        String arvTime = dataSchedule.getARV_TIME();
//        String arvDate="";//=dataSchedule.getARV_DATE();
//        //  viewHolder.textViewArvTime.setText(arvTime.substring(0, 2) + ":" + arvTime.substring(2,4));
//
//        String depTime = dataSchedule.getDEP_TIME();
//        String depDate="";// = dataSchedule.getDEP_DATE();
////        viewHolder.textViewDepTime.setText(depTime.substring(0, 2) + ":" + depTime.substring(2,4));
////        Log.d(TAG, "refreshHeader: "+depDate+" "+ depTime);
//
//        try {
//             arvDate=odf.format(sdf.parse(dataSchedule.getARV_DATE()));
//             depDate=odf.format(sdf.parse(dataSchedule.getDEP_DATE()));
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//       textViewTibaKapal.setText("Tiba " + arvDate + " Pukul " + arvTime.substring(0, 2) + ":" + arvTime.substring(2, 4));
//       textViewBerangkatKapal.setText("Berangkat " + depDate + " Pukul " + depTime.substring(0, 2) + ":" + depTime.substring(2, 4));


        //            textViewBerangkatKapal.setText("Berangkat " + odf.format(sdf.parse(depDate) + " Pukul " + depTime.substring(0, 2) + ":" + depTime.substring(2, 4)));

//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    @NonNull
    public Comparator<ShipFareModel> availCompare = new Comparator<ShipFareModel>() {
        public int compare(@NonNull ShipFareModel app1, @NonNull ShipFareModel app2) {
            String stringName1 = app1.getTagButton();
            String stringName2 = app2.getTagButton();
            Log.d(TAG, "compare button: " + stringName1 + " " + stringName2);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return stringName1.compareTo(stringName2);
//            } else {
//                return ((Integer) Integer.parseInt(stringName1)).compareTo(Integer.parseInt(stringName2));
//            }
        }

    };

    public synchronized ShipScheduleActivity getInstance() {
        return mInstance;
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

        if (isStillRunning) {
            RequestUtilsTravel.cancelTravel();
        }
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void openCalendar(View view) {
        Intent intent = new Intent(ShipScheduleActivity.this, ShipTanggalActivity.class);
//                intent.putExtra("initTanggal", "pergi");
        startActivityForResult(intent, TravelActionCode.LIST_TANGGAL_BERANGKAT);
        overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult:tglsukses" + requestCode + "" + resultCode + " " + data);

        if (requestCode == TravelActionCode.LIST_TANGGAL_BERANGKAT && resultCode == Activity.RESULT_OK) {
            int selectedMonth = data.getIntExtra("selectedMonth", 0);
            int selectedYear = data.getIntExtra("selectedYear", 0);

//            currentYear = calendar.get(Calendar.YEAR);
//            currentMonth = calendar.get(selectedMonth);
//Date bulan=calendar.get(selectedMonth);
            // SimpleDateFormat sdf = new SimpleDateFormat("MMMM", config.locale);

            Calendar calendar = Calendar.getInstance();
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DATE) + 1;
            Log.d(TAG, "onClick: " + month + " " + day);
            if (month == selectedMonth) {
                calendar.set(selectedYear, selectedMonth, day);
            } else {
                calendar.set(selectedYear, selectedMonth, 1);
            }

            Date startDate = calendar.getTime();

            calendar.set(selectedYear, selectedMonth, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

            Date endDate = calendar.getTime();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", SBFApplication.config.locale);

            PreferenceClass.putString(ShipKeyPreference.shipStartDate, sdf.format(startDate));
            PreferenceClass.putString(ShipKeyPreference.shipEndDate, sdf.format(endDate));

            if (bayi > 0 && dewasa == 0) {
                description = months[PreferenceClass.getInt(ShipKeyPreference.shipMonth, 0)] + " " + PreferenceClass.getInt(ShipKeyPreference.shipYear, 0) + ", " + bayi + " Bayi";
                textTgl_Pax.setText(description);
            } else if (dewasa > 0 && bayi == 0) {
                description = months[PreferenceClass.getInt(ShipKeyPreference.shipMonth, 0)] + " " + PreferenceClass.getInt(ShipKeyPreference.shipYear, 0) + ", " + dewasa + " Dewasa";
                textTgl_Pax.setText(description);
            } else if (bayi > 0 && dewasa > 0) {


                description = months[PreferenceClass.getInt(ShipKeyPreference.shipMonth, 0)] + " " + PreferenceClass.getInt(ShipKeyPreference.shipYear, 0) + ", " + dewasa + " Dewasa, " + bayi + " Bayi";
                Log.d(TAG, "onCreate: " + description);
                textTgl_Pax.setText(description);
            }

            if (isStillRunning) {
                RequestUtilsTravel.cancelTravel();
            }
            if (mShimmerViewContainer.getVisibility() == View.GONE) {
                mShimmerViewContainer.setVisibility(View.VISIBLE);
                mShimmerViewContainer.startShimmerAnimation();

            }
            if (shimmer_view_container_tab.getVisibility() == View.GONE) {
                shimmer_view_container_tab.setVisibility(View.VISIBLE);
                shimmer_view_container_tab.startShimmerAnimation();

            }

//            if(linHeader.getVisibility()==View.VISIBLE){
//                linHeader.setVisibility(View.GONE);
//            }
            recyclerViewTabs.setVisibility(View.GONE);
            recyclerViewSchedule.setVisibility(View.GONE);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    searchingShip();
                }
            });
        } else if (requestCode == TravelActionCode.IS_FROM_PAY && resultCode == RESULT_OK) {
            //   Intent intent = new Intent(FlightReviewBookActivity.this, FlightSearchActivity.class);
            if (data != null) {
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

            } else {

                setResult(RESULT_OK);
                onBackPressed();

            }

        }
    }
}

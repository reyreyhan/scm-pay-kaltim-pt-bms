package com.bm.main.single.ftl.train.activities;

import android.app.Activity;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;



import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.templates.AutoScaleTextView;
import com.bm.main.fpl.templates.NonScrollListView;
import com.bm.main.fpl.templates.shimmer.ShimmerFrameLayout;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.shimmer;
import com.bm.main.pos.R;
import com.bm.main.pos.SBFApplication;
import com.bm.main.single.ftl.activities.TravelTanggalActivity;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bm.main.single.ftl.train.adapters.KelasFilterAdapter;
import com.bm.main.single.ftl.train.adapters.KeretaFilterAdapter;
import com.bm.main.single.ftl.train.adapters.TimeFilterAdapter;
import com.bm.main.single.ftl.train.adapters.TrainScheduleAdapter;
import com.bm.main.single.ftl.train.constants.TrainKeyPreference;
import com.bm.main.single.ftl.train.constants.TrainPath;
import com.bm.main.single.ftl.train.models.TrainDataModel;
import com.bm.main.single.ftl.train.models.TrainFilterModel;
import com.bm.main.single.ftl.train.models.TrainKelasFilterModel;
import com.bm.main.single.ftl.train.models.TrainTimeFilterModel;
import com.bm.main.single.ftl.utils.MemoryStore;
import com.bm.main.single.ftl.utils.RequestUtilsTravel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.bm.main.fpl.utils.DialogUtils.closeBootomSheetDialog;
import static com.bm.main.fpl.utils.DialogUtils.openBottomSheetDialog;


public class TrainScheduleActivity extends BaseActivity implements View.OnClickListener, ProgressResponseCallback, TrainScheduleAdapter.OnClickSchedule {
    private static final String TAG = TrainScheduleActivity.class.getSimpleName();
    RecyclerView recyclerView;
    //RecyclerViewEmptySupport recyclerView;
    public TrainScheduleActivity mInstance;

    @NonNull
    public ArrayList<TrainDataModel> list = new ArrayList<>();
    @NonNull
    public ArrayList<TrainDataModel> real_list = new ArrayList<>();
    @NonNull
    public ArrayList<TrainTimeFilterModel> listTimeFilter = new ArrayList<>();
    @NonNull
    public ArrayList<TrainKelasFilterModel> listKelasFilter = new ArrayList<>();
    @NonNull
    public ArrayList<TrainFilterModel> listKeretaFilter = new ArrayList<>();
    public TrainScheduleAdapter adapter;
    public TimeFilterAdapter adapterTimeFilter;
    public KelasFilterAdapter adapterKelasFilter;
    public KeretaFilterAdapter adapterKeretaFilter;
    private TextView textSorterDefault;
    private ImageView imageViewSorterDefault;
    private TextView textSorterLowPrice;
    private ImageView imageViewSorterLowPrice;
    private TextView textSorterEarlyDept;
    private ImageView imageViewSorterEarlyDept;
    private TextView textSorterShortDuration;
    private ImageView imageViewSorterShortDuration;
    public ImageView image_view_filter_check;
    private ImageView image_view_sort_check;
    private Context context;

    @Nullable
    String asal, tujuan, depDate;

    private TextView textViewfilter;
    private AutoScaleTextView textTgl_Pax;
    private boolean isFilter;
    private boolean isSorting;

    private NonScrollListView list_kelas, list_kereta;
    private RelativeLayout emptyView;
    private TextView textViewSort;

    private ShimmerFrameLayout mShimmerViewContainer;
    boolean show = false;
    RelativeLayout linDefaultSorter,linLowPriceSorter,linEarlyGoSorter,linFastDurasiSorter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_schedule_activity);
        emptyView = findViewById(R.id.empty_view_train);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        shimmer.selectPreset(0, mShimmerViewContainer);

        image_view_filter_check = findViewById(R.id.image_view_filter_check);
        image_view_filter_check.setVisibility(View.GONE);
        image_view_sort_check = findViewById(R.id.image_view_sort_check);
        image_view_sort_check.setVisibility(View.GONE);
        recyclerView = findViewById(R.id.recylerList);
        Intent intent = this.getIntent();
        if (intent != null)
            show = intent.getBooleanExtra("cari", false);
        if (show) {

            //  progressBar.setProgress(0);
            //   progressBar.setVisibility(View.VISIBLE);


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
                    searchingTrain();
                }
            });
        }

        PreferenceClass.remove(TrainKeyPreference.jmlpenumpang);
        asal = PreferenceClass.getString(TrainKeyPreference.stationNamaAsal, "");
        String[] arrAsal = asal.split("\\(");
        tujuan = PreferenceClass.getString(TrainKeyPreference.stationNamaTujuan, "");
        String[] arrTujuan = tujuan.split("\\(");
        depDate = PreferenceClass.getString(TrainKeyPreference.departureDateShowTrain, "");
        int dewasa = PreferenceClass.getInt(TrainKeyPreference.countAdultTrain, 1);

        int bayi = PreferenceClass.getInt(TrainKeyPreference.countInfantTrain, 0);
        int pax = dewasa + bayi;
        // MemoryStore.set(this, "jmlpenumpang", pax);
        PreferenceClass.putInt(TrainKeyPreference.jmlpenumpang, pax);
        toolbar = findViewById(R.id.toolbar);
        init(0);
        AutoScaleTextView textViewAsal = findViewById(R.id.asal);
        textViewAsal.setText(arrAsal[0]);
        AutoScaleTextView textViewTujuan = findViewById(R.id.tujuan);
        textViewTujuan.setText(arrTujuan[0]);
        textTgl_Pax = findViewById(R.id.textViewToolbarSubTitle);
        textTgl_Pax.setText(depDate + " - " + pax + " org");


        textViewfilter = findViewById(R.id.text_view_filter);

        textViewSort = findViewById(R.id.text_view_sort);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new TrainScheduleAdapter(this, list, this);
        adapterTimeFilter = new TimeFilterAdapter(this, listTimeFilter);
        adapterKeretaFilter = new KeretaFilterAdapter(this, listKeretaFilter);
        adapterKelasFilter = new KelasFilterAdapter(this, listKelasFilter);

        recyclerView.setAdapter(adapter);
        LinearLayout layout_filter = findViewById(R.id.layout_filter);
        layout_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilter(v);
            }
        });
//                layout_sort.setFocusableInTouchMode(true);
        LinearLayout layout_sort = findViewById(R.id.layout_sort);
        layout_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSorter(v);
            }
        });
//recyclerView.setEmptyView(emptyView);

        //  dialog = new TopSheetDialog(this);


    }

    public synchronized TrainScheduleActivity getInstance() {
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

    public void openFilter(View v) {
        final FrameLayout view =(FrameLayout) View.inflate(this,R.layout.train_filter, null);

        AppCompatButton btn_Reset = view.findViewById(R.id.btn_reset);
        btn_Reset.setOnClickListener(this);
        AppCompatButton btn_Selesai = view.findViewById(R.id.btn_selesai);
        btn_Selesai.setOnClickListener(this);
        //  list_time = (NonScrollListView) view.findViewById(R.id.list_time);
        // Utility.setListViewHeightBasedOnChildren(list_time);
        list_kelas = view.findViewById(R.id.list_kelas);
        // Utility.setListViewHeightBasedOnChildren(list_kelas);
        list_kereta = view.findViewById(R.id.list_kereta);

        list_kereta.setAdapter(adapterKeretaFilter);
        list_kereta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long l) {
                //Item Selected from list
                adapterKeretaFilter.setCheckBox(position);
                //     TrainFilterModel country = (TrainFilterModel) parent.getItemAtPosition(position);
                // Toast.makeText(ScheduleTrainResultActivity.this,"Clicked on Row: " + country.getKeretaKey(),Toast.LENGTH_LONG).show();

            }
        });


        list_kelas.setAdapter(adapterKelasFilter);
        list_kelas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int position, long l) {
                //Item Selected from list
                adapterKelasFilter.setCheckBox(position);

            }
        });

        openBottomSheetDialog(this, view);
    }

    public void openCalendar(View v) {
//        TextView textViewfilter= (TextView) v.getView();

        // if(!isStillRunning) {

        Intent intent = new Intent(TrainScheduleActivity.this, TravelTanggalActivity.class);
        intent.putExtra("initTanggal", "pergi");
        intent.putExtra("initValue", PreferenceClass.getString(TrainKeyPreference.departureDateTrain,""));

        startActivityForResult(intent, 1);
        overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
        // }
//        else{
//            textViewfilter.setTextColor(ActivityCompat.getColor(this,R.color.colorPrimary));
//        }
    }

    public void openSorter(View v) {
        final View view = getLayoutInflater().inflate(R.layout.train_sorter, null);
        linDefaultSorter = view.findViewById(R.id.linDefaultSorter);
        textSorterDefault = view.findViewById(R.id.textSorterDefault);
        imageViewSorterDefault = view.findViewById(R.id.imageViewSorterDefault);
        linDefaultSorter.setOnClickListener(this);

        linLowPriceSorter = view.findViewById(R.id.linLowPriceSorter);
        textSorterLowPrice = view.findViewById(R.id.textSorterLowPrice);
        imageViewSorterLowPrice = view.findViewById(R.id.imageViewSorterLowPrice);
        linLowPriceSorter.setOnClickListener(this);

        linEarlyGoSorter = view.findViewById(R.id.linEarlyGoSorter);
        textSorterEarlyDept = view.findViewById(R.id.textSorterEarlyDept);
        imageViewSorterEarlyDept = view.findViewById(R.id.imageViewSorterEarlyDept);
        linEarlyGoSorter.setOnClickListener(this);

        linFastDurasiSorter = view.findViewById(R.id.linFastDurasiSorter);
        textSorterShortDuration = view.findViewById(R.id.textSorterShortDuration);
        imageViewSorterShortDuration = view.findViewById(R.id.imageViewSorterShortDuration);
        linFastDurasiSorter.setOnClickListener(this);
        AppCompatButton btn_Tutup = view.findViewById(R.id.btn_tutup);
        btn_Tutup.setOnClickListener(this);

        if (checked == 1) {
            textSorterDefault.setTextColor(getResources().getColor(R.color.md_blue_800));
            textSorterLowPrice.setTextColor(getResources().getColor(R.color.md_grey_600));
            //textSorterLowPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_checked, 0);
            textSorterEarlyDept.setTextColor(getResources().getColor(R.color.md_grey_600));
            //textSorterEarlyDept.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            textSorterShortDuration.setTextColor(getResources().getColor(R.color.md_grey_600));
            //textSorterShortDuration.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            imageViewSorterDefault.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_checkmark));
//            } else {
//                imageViewSorterDefault.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.ic_checkmark));
//            }

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            imageViewSorterLowPrice.setBackground(null);
//            } else {
//                imageViewSorterLowPrice.setBackgroundDrawable(null);
//            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            imageViewSorterEarlyDept.setBackground(null);
//            } else {
//                imageViewSorterEarlyDept.setBackgroundDrawable(null);
//            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            imageViewSorterShortDuration.setBackground(null);
//            } else {
//                imageViewSorterShortDuration.setBackgroundDrawable(null);
//            }
        } else
            if (checked == 2) {
            textSorterDefault.setTextColor(getResources().getColor(R.color.md_grey_600));
            textSorterLowPrice.setTextColor(getResources().getColor(R.color.md_blue_800));
            //textSorterLowPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_checked, 0);
            textSorterEarlyDept.setTextColor(getResources().getColor(R.color.md_grey_600));
            //textSorterEarlyDept.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            textSorterShortDuration.setTextColor(getResources().getColor(R.color.md_grey_600));
            //textSorterShortDuration.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            imageViewSorterDefault.setBackground(null);
//            } else {
//                imageViewSorterDefault.setBackgroundDrawable(null);
//            }

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            imageViewSorterLowPrice.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_checkmark));
//            } else {
//                imageViewSorterLowPrice.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.ic_checkmark));
//            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            imageViewSorterEarlyDept.setBackground(null);
//            } else {
//                imageViewSorterEarlyDept.setBackgroundDrawable(null);
//            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            imageViewSorterShortDuration.setBackground(null);
//            } else {
//                imageViewSorterShortDuration.setBackgroundDrawable(null);
//            }
        } else if (checked == 3) {
            textSorterDefault.setTextColor(getResources().getColor(R.color.md_grey_600));
            textSorterLowPrice.setTextColor(getResources().getColor(R.color.md_grey_600));
            // textSorterLowPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            textSorterEarlyDept.setTextColor(getResources().getColor(R.color.md_blue_800));
            //textSorterEarlyDept.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_checked, 0);
            textSorterShortDuration.setTextColor(getResources().getColor(R.color.md_grey_600));
            //textSorterShortDuration.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
           imageViewSorterDefault.setBackground(null);
//            } else {
//                imageViewSorterDefault.setBackgroundDrawable(null);
//            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            imageViewSorterLowPrice.setBackground(null);
//            } else {
//                imageViewSorterLowPrice.setBackgroundDrawable(null);
//            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            imageViewSorterEarlyDept.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_checkmark));
//            } else {
//                imageViewSorterEarlyDept.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.ic_checkmark));
//            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            imageViewSorterShortDuration.setBackground(null);
//            } else {
//                imageViewSorterShortDuration.setBackgroundDrawable(null);
//            }
        } else if (checked == 4) {
            textSorterDefault.setTextColor(getResources().getColor(R.color.md_grey_600));
            textSorterLowPrice.setTextColor(getResources().getColor(R.color.md_grey_600));
            //  textSorterLowPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            textSorterEarlyDept.setTextColor(getResources().getColor(R.color.md_grey_600));
            // textSorterEarlyDept.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            textSorterShortDuration.setTextColor(getResources().getColor(R.color.md_blue_800));
            //  textSorterShortDuration.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_checked, 0);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            imageViewSorterDefault.setBackground(null);
//            } else {
//                imageViewSorterDefault.setBackgroundDrawable(null);
//            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            imageViewSorterLowPrice.setBackground(null);
//            } else {
//                imageViewSorterLowPrice.setBackgroundDrawable(null);
//            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            imageViewSorterEarlyDept.setBackground(null);
//            } else {
//                imageViewSorterEarlyDept.setBackgroundDrawable(null);
//            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            imageViewSorterShortDuration.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_checkmark));
//            } else {
//                imageViewSorterShortDuration.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.ic_checkmark));
//            }
        }

        openBottomSheetDialog(this, view);
    }

    boolean isStillRunning = true;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: " + requestCode + "" + resultCode + " " + data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            String tglfind = data.getStringExtra("date");
            String dateShow = data.getStringExtra("dateShow");
            PreferenceClass.putString(TrainKeyPreference.departureDateTrain, tglfind);
            textTgl_Pax.setText(dateShow + " - " + "" + PreferenceClass.getInt(TrainKeyPreference.jmlpenumpang, 1) + " Orang");
            if (isStillRunning) {
                RequestUtilsTravel.cancelTravel();
            }
            if (mShimmerViewContainer.getVisibility() == View.GONE) {
                mShimmerViewContainer.setVisibility(View.VISIBLE);
                mShimmerViewContainer.startShimmerAnimation();

            }
            recyclerView.setVisibility(View.GONE);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    searchingTrain();
                }
            });
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
                            searchingTrain();
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
                        setResult(RESULT_OK,intent);
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

    private void searchingTrain() {
        FrameLayout loadingView =(FrameLayout) View.inflate(this,R.layout.loading_bar_full_dialog, null);
        TextView text = loadingView.findViewById(R.id.textContentProgressBar);
        text.setText("Mohon tunggu, Pencarian kereta anda sedang kami proses");
        openProgressBarDialog(this, loadingView);
        list.clear();
        listKelasFilter.clear();
        listTimeFilter.clear();
        listKeretaFilter.clear();
        real_list.clear();
        filteredTimeList.clear();
        filteredKelasList.clear();
        filteredKeretaList.clear();
        isFilter = false;
        isFilterKelas = false;
        isFilterKereta = false;
        isFilterTime = false;
        checked = 1;
        if (image_view_filter_check.getVisibility() == View.VISIBLE) {
            image_view_filter_check.setVisibility(View.GONE);
        }

        if (image_view_sort_check.getVisibility() == View.VISIBLE) {
            image_view_sort_check.setVisibility(View.GONE);
        }
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("productCode", "TKAI");
            jsonObject.put("origin", PreferenceClass.getString(TrainKeyPreference.stationKodeAsal, ""));
            jsonObject.put("destination", PreferenceClass.getString(TrainKeyPreference.stationKodeTujuan, ""));


            jsonObject.put("date", PreferenceClass.getString(TrainKeyPreference.departureDateTrain, ""));

            jsonObject.put("token", PreferenceClass.getToken());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "cariKereta: " + jsonObject);
        RequestUtilsTravel.transportWithProgressResponse(this, TrainPath.SEARCH, jsonObject, TravelActionCode.SEARCH, this);

    }


    int checked = 1;

    boolean isFilterTime = false;
    boolean isFilterKereta = false;
    boolean isFilterKelas = false;
    @NonNull
    String timeDepFilter = "";
    @NonNull
    String kelasFilter = "";
    @NonNull
    String keretaFilter = "";
    @NonNull
    ArrayList<String> filteredTimeList = new ArrayList<>();
    @NonNull
    ArrayList<String> filteredKeretaList = new ArrayList<>();
    @NonNull
    ArrayList<String> filteredKelasList = new ArrayList<>();

    //int countKeretaFilter=0;
    @Override
    public void onClick(@NonNull View view) {
        int id = view.getId();
        if (id == R.id.btn_tutup) {
            closeBootomSheetDialog();
        } else if (id == R.id.btn_selesai) {

            filteredTimeList.clear();
            for (int i = 0; i < adapterTimeFilter.getAllData().size(); i++) {
                TrainTimeFilterModel time = adapterTimeFilter.getAllData().get(i);

                if (time.isCheckbox()) {
                    Log.d(TAG, "onClick: " + time.getTimeKey());
                    filteredTimeList.add(time.getTimeKey());


                }
            }


            filteredKelasList.clear();
            for (int i = 0; i < adapterKelasFilter.getAllData().size(); i++) {
                TrainKelasFilterModel kelas = adapterKelasFilter.getAllData().get(i);

                if (kelas.isCheckbox()) {
                    filteredKelasList.add(kelas.getKelasKey());


                }
            }

            filteredKeretaList.clear();
            // StringBuffer responseText = new StringBuffer();
            for (int i = 0; i < adapterKeretaFilter.getAllData().size(); i++) {
                TrainFilterModel kereta = adapterKeretaFilter.getAllData().get(i);

                if (kereta.isCheckbox()) {
                    filteredKeretaList.add(kereta.getKeretaKey());


                }
            }

            if (filteredTimeList.size() > 0) {
                isFilterTime = true;
                isFilter = true;

            } else {
                isFilterTime = false;
                isFilter = false;
            }

            if (filteredKeretaList.size() > 0) {
                isFilterKereta = true;
                isFilter = true;

            } else {
                isFilterKereta = false;
                isFilter = false;
            }

            if (filteredKelasList.size() > 0) {
                isFilterKelas = true;
                isFilter = true;

            } else {
                isFilterKelas = false;
                isFilter = false;
            }


            filtered();

            Log.d(TAG, "onClick: Names: " + timeDepFilter + " " + kelasFilter + "  " + keretaFilter);

            closeBootomSheetDialog();
        } else if (id == R.id.btn_reset) {
            isFilter = false;
            isFilterKelas = false;
            isFilterKereta = false;
            isFilterTime = false;
            resetAllFilter();
            //clear all checkbox filter
        } else if (id == R.id.linDefaultSorter) {
            checked = 1;
            isSorting = false;
            image_view_sort_check.setVisibility(View.GONE);
            textSorterDefault.setTextColor(getResources().getColor(R.color.md_blue_800));
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            imageViewSorterDefault.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_checkmark));
//            } else {
//                imageViewSorterDefault.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.ic_checkmark));
//            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            imageViewSorterLowPrice.setBackground(null);
//            } else {
//                imageViewSorterLowPrice.setBackgroundDrawable(null);
//            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            imageViewSorterEarlyDept.setBackground(null);
//            } else {
//                imageViewSorterEarlyDept.setBackgroundDrawable(null);
//            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            imageViewSorterShortDuration.setBackground(null);
//            } else {
//                imageViewSorterShortDuration.setBackgroundDrawable(null);
//            }
            ArrayList<TrainDataModel> trainModelsDefault = new ArrayList<TrainDataModel>();

            if (isFilter) {
                ArrayList<TrainDataModel> arrTrainModel = (ArrayList<TrainDataModel>) MemoryStore.get(TrainKeyPreference.trainFilterModel);
                Collections.sort(arrTrainModel, urutTrain);
            } else {
                ArrayList<TrainDataModel> arrTrainModel = (ArrayList<TrainDataModel>) MemoryStore.get(TrainKeyPreference.schedule_list_train_default);
                Collections.sort(list, urutTrain);
            }


            adapter.notifyDataSetChanged();
            closeBootomSheetDialog();
        } else if (id == R.id.linLowPriceSorter) {
            checked = 2;
            isSorting = true;
            image_view_sort_check.setVisibility(View.VISIBLE);
            textSorterLowPrice.setTextColor(getResources().getColor(R.color.md_blue_800));
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //imageViewSorterDefault.setBackground(null);
//            } else {
//                imageViewSorterDefault.setBackgroundDrawable(null);
//            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            imageViewSorterLowPrice.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_checkmark));
//            } else {
//                imageViewSorterLowPrice.setBackgroundResource(R.drawable.ic_checkmark);
//            }
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            imageViewSorterEarlyDept.setBackground(null);
//            } else {
//                imageViewSorterEarlyDept.setBackgroundDrawable(null);
//            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                imageViewSorterShortDuration.setBackground(null);
            } else {
                imageViewSorterShortDuration.setBackgroundDrawable(null);
            }

            if (isFilter) {
                ArrayList<TrainDataModel> arrTrainModel = (ArrayList<TrainDataModel>) MemoryStore.get(TrainKeyPreference.trainFilterModel);
                Collections.sort(arrTrainModel, hargaTermurah);
            } else {
                Collections.sort(list, hargaTermurah);
            }
            // list= (ArrayList<TrainDataModel>) MemoryStore.get(ScheduleTrainResultActivity.this, "schedule_list_train_default");
            adapter.notifyDataSetChanged();
            closeBootomSheetDialog();
        } else if (id == R.id.linEarlyGoSorter) {
            checked = 3;
            isSorting = true;
            image_view_sort_check.setVisibility(View.VISIBLE);

            textSorterEarlyDept.setTextColor(getResources().getColor(R.color.md_blue_800));
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                imageViewSorterDefault.setBackground(null);
//            } else {
//                imageViewSorterDefault.setBackgroundDrawable(null);
//            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                imageViewSorterLowPrice.setBackground(null);
            } else {
                imageViewSorterLowPrice.setBackgroundDrawable(null);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                imageViewSorterEarlyDept.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_checkmark));
            } else {
                imageViewSorterEarlyDept.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.ic_checkmark));
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                imageViewSorterShortDuration.setBackground(null);
            } else {
                imageViewSorterShortDuration.setBackgroundDrawable(null);
            }

            if (isFilter) {
                ArrayList<TrainDataModel> arrTrainModel = (ArrayList<TrainDataModel>) MemoryStore.get(TrainKeyPreference.trainFilterModel);
                Collections.sort(arrTrainModel, waktuBerangkatTerpagi);
            } else {

                Collections.sort(list, waktuBerangkatTerpagi);
            }

            adapter.notifyDataSetChanged();
            closeBootomSheetDialog();
        } else if (id == R.id.linFastDurasiSorter) {
            checked = 4;
            isSorting = true;
            image_view_sort_check.setVisibility(View.VISIBLE);
            textSorterShortDuration.setTextColor(getResources().getColor(R.color.md_blue_800));
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                imageViewSorterDefault.setBackground(null);
//            } else {
//                imageViewSorterDefault.setBackgroundDrawable(null);
//            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                imageViewSorterLowPrice.setBackground(null);
            } else {
                imageViewSorterLowPrice.setBackgroundDrawable(null);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                imageViewSorterEarlyDept.setBackground(null);
            } else {
                imageViewSorterEarlyDept.setBackgroundDrawable(null);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                imageViewSorterShortDuration.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_checkmark));
            } else {
                imageViewSorterShortDuration.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.ic_checkmark));
            }

            if (isFilter) {
                ArrayList<TrainDataModel> arrTrainModel = (ArrayList<TrainDataModel>) MemoryStore.get(TrainKeyPreference.trainFilterModel);
                Collections.sort(arrTrainModel, durasiSort);
            } else {
                Collections.sort(list, durasiSort);
            }

            adapter.notifyDataSetChanged();
            closeBootomSheetDialog();

        }
    }

    private void resetAllFilter() {


        for (int i = 0; i < list_kereta.getChildCount(); i++) {
            CheckBox cb = list_kereta.getChildAt(i).findViewById(R.id.checkBoxKeretaFilter);

            cb.setChecked(false);
            TrainFilterModel planet = (TrainFilterModel) cb.getTag();
            // planet.toggleChecked();
            planet.setCheckbox(cb.isChecked());
        }

        for (int i = 0; i < list_kelas.getChildCount(); i++) {
            CheckBox cb = list_kelas.getChildAt(i).findViewById(R.id.checkBoxKelasFilter);

            cb.setChecked(false);
            TrainKelasFilterModel planet = (TrainKelasFilterModel) cb.getTag();
            // planet.toggleChecked();
            planet.setCheckbox(cb.isChecked());
        }


    }

    private void filtered() {
        ArrayList<TrainDataModel> trainModelsFiltered = new ArrayList<TrainDataModel>();
        //    list.clear();
        if (isFilterTime == false && isFilterKereta == false && isFilterKelas == false) {
            isFilter = false;
            image_view_filter_check.setVisibility(View.GONE);
            trainModelsFiltered = (ArrayList<TrainDataModel>) MemoryStore.get(TrainKeyPreference.schedule_list_train_default);
            if (checked == 3) {
                // image_view_filter_check.setVisibility(View.VISIBLE);
                Collections.sort(trainModelsFiltered, waktuBerangkatTerpagi);
                adapter.notifyDataSetChanged();
            } else if (checked == 4) {
                //  image_view_sort_check.setVisibility(View.VISIBLE);
                Collections.sort(trainModelsFiltered, durasiSort);
                adapter.notifyDataSetChanged();
            } else if (checked == 2) {

//                        Collections.sort(list, tersedia);
//                        Collections.sort(list, waktuBerangkatTerpagi);
//
                Collections.sort(trainModelsFiltered, hargaTermurah);
                adapter.notifyDataSetChanged();
                // MemoryStore.set(ScheduleTrainResultActivity.this, "schedule_list_train_default", list);
            }
            else if (checked == 1) {
                        Collections.sort(list, urutTrain);
                adapter.notifyDataSetChanged();
            }

            adapter.updateList(trainModelsFiltered);


        } else {

            isFilter = true;
            image_view_filter_check.setVisibility(View.VISIBLE);
            for (TrainDataModel trainModel : adapter.getData()) {
                String timeDept = trainModel.getDepartureTime();
                String kelas = trainModel.getGrade();
                String kereta = trainModel.getTrainName();
                boolean foundTime = false;
                boolean foundKelas = false;
                boolean foundKereta = false;
                if (isFilterTime) {
                    for (int i = 0; i < filteredTimeList.size(); i++) {
                        Log.d(TAG, "filtered: " + filteredTimeList.get(i));
                        if (timeDept.equals(filteredTimeList.get(i))) {
                            Log.d(TAG, "filtered inner: " + filteredTimeList.get(i));
                            foundTime = true;
                        }
                    }
                    if (!foundTime) {
                        continue;
                    }

                }

                if (isFilterKelas) {
                    for (int i = 0; i < filteredKelasList.size(); i++) {
                        if (kelas.equals(filteredKelasList.get(i))) {
                            foundKelas = true;
                        }
                    }
                    if (!foundKelas) {
                        continue;
                    }

                }

                if (isFilterKereta) {
                    for (int i = 0; i < filteredKeretaList.size(); i++) {
                        if (kereta.equals(filteredKeretaList.get(i))) {
                            foundKereta = true;
                        }
                    }
                    if (!foundKereta) {
                        continue;
                    }

                }


                trainModelsFiltered.add(trainModel);
            }
//list.add(trainModelsFiltered);
            //         adapter.notifyDataSetChanged();

            if (checked == 3) {
                // image_view_filter_check.setVisibility(View.VISIBLE);
                Collections.sort(trainModelsFiltered, waktuBerangkatTerpagi);
                adapter.notifyDataSetChanged();
            } else if (checked == 4) {
                //  image_view_sort_check.setVisibility(View.VISIBLE);
                Collections.sort(trainModelsFiltered, durasiSort);
                adapter.notifyDataSetChanged();
            } else if (checked == 2) {

//                        Collections.sort(list, tersedia);
//                        Collections.sort(list, waktuBerangkatTerpagi);
//
                Collections.sort(trainModelsFiltered, hargaTermurah);
                adapter.notifyDataSetChanged();
                // MemoryStore.set(ScheduleTrainResultActivity.this, "schedule_list_train_default", list);
            } else if (checked == 1) {
                        Collections.sort(list, urutTrain);
                adapter.notifyDataSetChanged();
            }
            MemoryStore.set(TrainKeyPreference.trainFilterModel, trainModelsFiltered);
            adapter.updateList(trainModelsFiltered);

        }
    }

    @Override
    public void onSuccess(final int actionCode, @NonNull final JSONObject response) {
        // Log.d(TAG, "onSuccess: " + response.toString());
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
        closeProgressBarDialog();
        if (mShimmerViewContainer.getVisibility() == View.VISIBLE) {
            mShimmerViewContainer.setVisibility(View.GONE);
            mShimmerViewContainer.stopShimmerAnimation();

        }
        try {
            if (response.getString("rc").equals(ResponseCode.SUCCESS)) {
//
                if (actionCode == TravelActionCode.SEARCH) {

                    getFind(response);

                }
            } else {
                emptyView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
//                showToastCustom(ScheduleTrainResultActivity.this, 2, response.getString("rd"));
                snackBarCustomAction(findViewById(R.id.rootLayout), 0, response.getString("rd"), 2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
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
        emptyView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        isStillRunning = false;
        // showToastCustom(ScheduleTrainResultActivity.this, 1, responseDescription);
        snackBarCustomAction(findViewById(R.id.rootLayout), 0, responseDescription, 1);
//            }
//        });
        //  }
    }

    @Override
    public void onUpdate(int actionCode,long bytesRead, long totalSize, boolean done) {
        Log.d(TAG, "run: "+done+" "+(100 * bytesRead)+"  "+totalSize+"   "+(int) ((100 * bytesRead) / totalSize));
    }

    public void getFind(@NonNull JSONObject response) {
        // closeProgressBarDialog();

        Log.d(TAG, "onSuccess schedule train: " + response.toString());

        try {
            JSONArray trainjson = response.getJSONArray("data");
            Log.d(TAG, "getFind: " + response.getJSONArray("data"));
            TrainDataModel trainDataModelClasses;
            TrainFilterModel trainFilterModel;

            TrainDataModel modelTrain;
            TrainKelasFilterModel modelKelas;
            int urut = 1;
            if (trainjson.length() > 0) {

                emptyView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                for (int i = 0; i < trainjson.length(); i++) {
                    JSONObject data = trainjson.getJSONObject(i);


                    JSONArray arrayinarray = data.getJSONArray("seats");
                    String kelas = null;
                    //  int ketersediaan = 0;
                    // String ketersediaankursi = null;
                    String gred;// = null;
                    int harga = 0;
                    for (int j = 0; j < arrayinarray.length(); j++) {
                        // Log.d(TAG, "getFindARRAYLENGHTH: " + arrayinarray.length());

                        String trainName = data.getString("trainName");
                        Log.d(TAG, "getFind: nama kereta " + trainName);
                        String tglkeberangkatan = data.getString("departureDate");
                        String tglkedatangan = data.getString("arrivalDate");
                        String jamkeberangkatan = data.getString("departureTime");
                        String jamkedatangan = data.getString("arrivalTime");
                        String durasi = data.getString("duration");
                        String originCode = PreferenceClass.getString(TrainKeyPreference.stationKodeAsal, "");
                        String destinationCode = PreferenceClass.getString(TrainKeyPreference.stationKodeTujuan, "");

                        String nosepur = data.getString("trainNumber");

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd", SBFApplication.config.locale);
                        Date testDate = null;
                        Date testDate1 = null;
                        try {
                            testDate = sdf.parse(tglkeberangkatan);
                            testDate1 = sdf.parse(tglkedatangan);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy", SBFApplication.config.locale);
                        String newFormat = formatter.format(testDate);
                        String newFormat1 = formatter.format(testDate1);

                        trainFilterModel = new TrainFilterModel();
                        trainFilterModel.setCheckbox(false);
                        trainFilterModel.setKeretaKey(trainName);
                        trainFilterModel.setKeretaShow(trainName);

                        listKeretaFilter.add(trainFilterModel);


                        JSONObject datakursi = arrayinarray.getJSONObject(j);
                        kelas = datakursi.getString("class");

//                        if (datakursi.getString("availability").contains("-")) {
//                            ketersediaan = 0;
//                            // Log.d(TAG, "ketersediaankursi: " + ketersediaan);
//
//                        }
//                        if (!datakursi.getString("availability").contains("-")) {
//                            ketersediaan = Integer.parseInt(datakursi.getString("availability"));
//                        }
                        gred = datakursi.getString("grade");
                        harga = Integer.parseInt(datakursi.getString("priceAdult"));

                        // Log.d(TAG, "harga: " + harga);
                        // rega.add(Integer.valueOf(datakursi.getString("priceAdult")));
                        //  Log.d(TAG, "REGA: " + rega);

                        modelTrain = new TrainDataModel();
//                        int urut=i++;
                        Log.d(TAG, "getFind urut: " + urut);
                        modelTrain.setNo_urut(urut);
                        modelTrain.setTrainName(trainName);
                        modelTrain.setDepartureDate(newFormat);
                        modelTrain.setArrivalDate(newFormat1);
                        modelTrain.setDepartureTime(jamkeberangkatan);
                        modelTrain.setArrivalTime(jamkedatangan);
                        modelTrain.setDuration(durasi);

                        modelTrain.setTrainNumber(nosepur);

                        modelTrain.setOriginCode(originCode);
                        modelTrain.setDestinationCode(destinationCode);
                        modelTrain.setClasses(kelas);

                        modelKelas = new TrainKelasFilterModel();

                        modelKelas.setKelasKey(gred);

                        switch (gred) {
                            case "E":
                                Log.d(TAG, "getFind: kelas 1 " + gred);
                                modelKelas.setCheckbox(false);
                                modelKelas.setKelasShow("Eksekutif");
                                break;
                            case "K":
                                Log.d(TAG, "getFind: kelas 2 " + gred);
                                modelKelas.setCheckbox(false);
                                modelKelas.setKelasShow("Ekonomi");
                                break;
                            case "B":
                                Log.d(TAG, "getFind: kelas 3 " + gred);
                                modelKelas.setCheckbox(false);
                                modelKelas.setKelasShow("Bisnis");
                                break;
                        }


                        listKelasFilter.add(modelKelas);


                        if (datakursi.getString("availability").equals("-")) {
                            Log.d(TAG, "getFind avail seat: " + datakursi.getString("availability"));
                            modelTrain.setAvailability(datakursi.optInt("availability", 999999));
//                            modelTrain.setAvailability(0);//coba olah data di adapyter.....14/7/17
//                        }
//                        if (!datakursi.getString("availability").equals("-")) {
                        } else {
                            Log.d(TAG, "getFind avail seat else : " + datakursi.getInt("availability"));


                            modelTrain.setAvailability(datakursi.getInt("availability"));

                        }
                        modelTrain.setGrade(gred);
                        modelTrain.setPriceAdult(harga);

                        list.add(modelTrain);
                        real_list.add(modelTrain);
                        urut++;
                    }

                    if (checked == 3) {
                        // image_view_filter_check.setVisibility(View.VISIBLE);
                        Collections.sort(list, waktuBerangkatTerpagi);
                        adapter.notifyDataSetChanged();
                    } else if (checked == 4) {
                        //  image_view_sort_check.setVisibility(View.VISIBLE);
                        Collections.sort(list, durasiSort);
                        adapter.notifyDataSetChanged();
                    } else if (checked == 2) {

//                        Collections.sort(list, tersedia);
//                        Collections.sort(list, waktuBerangkatTerpagi);
//
                        Collections.sort(list, hargaTermurah);
                        adapter.notifyDataSetChanged();
                        // MemoryStore.set(ScheduleTrainResultActivity.this, "schedule_list_train_default", list);
                    } else if (checked == 1) {
//                        Collections.sort(list, urutTrain);
                        adapter.notifyDataSetChanged();
                    }
                }

                adapter.updateList(list);

                adapterKelasFilter.updateList(clearListFromDuplicateKelas(listKelasFilter));
                adapterKeretaFilter.updateList(clearListFromDuplicateKereta(listKeretaFilter));
                MemoryStore.set(TrainKeyPreference.listKeretaFilter, listKeretaFilter);
                MemoryStore.set(TrainKeyPreference.schedule_list_train, list);
                MemoryStore.set(TrainKeyPreference.schedule_list_train_default, real_list);

            } else {
                emptyView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
//                recyclerView.setEmptyView(emptyView);
                Log.d(TAG, "getFind: empty");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Comparator for Ascending Order
    @NonNull
    public static Comparator<TrainDataModel> urutTrain = new Comparator<TrainDataModel>() {


        @Override
        public int compare(@NonNull TrainDataModel emp1, @NonNull TrainDataModel emp2) {
            return emp1.getNo_urut() - emp2.getNo_urut();
        }

//        public int compare(TrainDataModel app1, TrainDataModel app2) {
//
//            //int stringName1 = app1.getAvailability();
//          //  int stringName2 = app2.getAvailability();
//
//
//            return Comparator.comparingInt(TrainDataModel::getAvailability).reversed();
//        }
    };

    @NonNull
    public static Comparator<TrainDataModel> waktuBerangkatTerpagi = new Comparator<TrainDataModel>() {

        public int compare(@NonNull TrainDataModel app1, @NonNull TrainDataModel app2) {

            String stringName1 = app1.getDepartureTime().replace(":", "");
            String stringName2 = app2.getDepartureTime().replace(":", "");


            return stringName1.compareToIgnoreCase(stringName2);
        }
    };

    @NonNull
    public static Comparator<TrainDataModel> tersedia = new Comparator<TrainDataModel>() {


        @Override
        public int compare(@NonNull TrainDataModel emp1, @NonNull TrainDataModel emp2) {
            return emp2.getAvailability() - emp1.getAvailability();
        }

//        public int compare(TrainDataModel app1, TrainDataModel app2) {
//
//            //int stringName1 = app1.getAvailability();
//          //  int stringName2 = app2.getAvailability();
//
//
//            return Comparator.comparingInt(TrainDataModel::getAvailability).reversed();
//        }
    };


    @NonNull
    public static Comparator<TrainTimeFilterModel> waktuBerangkatTerpagiFilter = new Comparator<TrainTimeFilterModel>() {

        public int compare(@NonNull TrainTimeFilterModel app1, @NonNull TrainTimeFilterModel app2) {

            String stringName1 = app1.getTimeKey();
            String stringName2 = app2.getTimeKey();


            return stringName1.compareToIgnoreCase(stringName2);
        }
    };

    @NonNull
    public static Comparator<TrainDataModel> hargaTermurah = new Comparator<TrainDataModel>() {


        public int compare(@NonNull TrainDataModel app1, @NonNull TrainDataModel app2) {

            String stringName1 = String.valueOf(app1.getPriceAdult());
            String stringName2 = String.valueOf(app2.getPriceAdult());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                return Integer.compare(Integer.parseInt(stringName1), Integer.parseInt(stringName2));
            } else {
                return ((Integer) Integer.parseInt(stringName1)).compareTo(Integer.parseInt(stringName2));
            }
        }
    };

    @NonNull
    public static Comparator<TrainDataModel> durasiSort = new Comparator<TrainDataModel>() {


        public int compare(@NonNull TrainDataModel app1, @NonNull TrainDataModel app2) {

            String stringName1 = app1.getDuration().replace("j", "").replace("m", "");
            String stringName2 = app2.getDuration().replace("j", "").replace("m", "");

            return stringName1.compareToIgnoreCase(stringName2);
        }
    };
    boolean check;


    @NonNull
    private ArrayList<TrainFilterModel> clearListFromDuplicateKereta(@NonNull ArrayList<TrainFilterModel> list1) {

        Map<String, TrainFilterModel> cleanMap = new LinkedHashMap<String, TrainFilterModel>();
        for (int i = 0; i < list1.size(); i++) {
            cleanMap.put(list1.get(i).getKeretaKey(), list1.get(i));
            //cleanMap.put(list1.get(i).getKeretaShow(), list1.get(i));
        }
        ArrayList<TrainFilterModel> list = new ArrayList<TrainFilterModel>(cleanMap.values());
        return list;
    }

    @NonNull
    private ArrayList<TrainKelasFilterModel> clearListFromDuplicateKelas(@NonNull ArrayList<TrainKelasFilterModel> list1) {

        Map<String, TrainKelasFilterModel> cleanMap = new LinkedHashMap<String, TrainKelasFilterModel>();
        for (int i = 0; i < list1.size(); i++) {
            cleanMap.put(list1.get(i).getKelasKey(), list1.get(i));
        }
        ArrayList<TrainKelasFilterModel> list = new ArrayList<TrainKelasFilterModel>(cleanMap.values());
        return list;
    }

    @Override
    public void onClickSchedule(@NonNull TrainDataModel dataSchedule) {
        // MemoryStore.set(TrainKeyPreference.onClickSchedule,dataSchedule);
//        PreferenceClass.putString(TrainKeyPreference.classes, dataSchedule.getClasses());
        JSONObject jsonObject = new JSONObject();

        try {
            Log.d(TAG, "getFare: " + dataSchedule.getTrainNumber());
            jsonObject.put("productCode", "TKAI");

            jsonObject.put("origin", PreferenceClass.getString(TrainKeyPreference.stationKodeAsal, ""));

            jsonObject.put("destination", PreferenceClass.getString(TrainKeyPreference.stationKodeTujuan, ""));

            jsonObject.put("date", PreferenceClass.getString(TrainKeyPreference.departureDateTrain, ""));

            jsonObject.put("trainNumber", dataSchedule.getTrainNumber());
            jsonObject.put("grade", dataSchedule.getGrade());
            jsonObject.put("class", dataSchedule.getClasses());
            jsonObject.put("adult", PreferenceClass.getInt(TrainKeyPreference.countAdultTrain, 1));
            jsonObject.put("child", PreferenceClass.getInt(TrainKeyPreference.countChildTrain, 0));
            jsonObject.put("infant", PreferenceClass.getInt(TrainKeyPreference.countInfantTrain, 0));

            jsonObject.put("trainName", dataSchedule.getTrainName());

            jsonObject.put("departureStation", PreferenceClass.getString(TrainKeyPreference.stationNamaAsal, ""));
            jsonObject.put("departureTime", dataSchedule.getDepartureTime());

            jsonObject.put("arrivalStation", PreferenceClass.getString(TrainKeyPreference.stationNamaTujuan, ""));
            jsonObject.put("arrivalTime", dataSchedule.getArrivalTime());

            //use memory store to save and now get value from before activity
            jsonObject.put("token", PreferenceClass.getToken());

            Log.d(TAG, "getFare: response tvPesan diklik" + jsonObject);
            PreferenceClass.putString(TrainKeyPreference.trainNumber, dataSchedule.getTrainNumber());
            PreferenceClass.putString(TrainKeyPreference.grade, dataSchedule.getGrade());
            PreferenceClass.putString(TrainKeyPreference.classes, dataSchedule.getClasses());
            PreferenceClass.putString(TrainKeyPreference.trainName, dataSchedule.getTrainName());
            PreferenceClass.putString(TrainKeyPreference.departureTime, dataSchedule.getDepartureTime());
            PreferenceClass.putString(TrainKeyPreference.arrivalTime, dataSchedule.getArrivalTime());
            PreferenceClass.putInt(TrainKeyPreference.priceAdult, dataSchedule.getPriceAdult());
            PreferenceClass.putString(TrainKeyPreference.arrDateTrain, dataSchedule.getArrivalDate());
            PreferenceClass.putString(TrainKeyPreference.depDateTrain, dataSchedule.getDepartureDate());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(TrainScheduleActivity.this, TrainBookActivity.class);
        intent.putExtra("reqJsonFare", jsonObject.toString());
//        intent.putExtra("trainNumber", trainNumber);
//        intent.putExtra("grade", grade);
//        intent.putExtra( "kelas", classes);
//        intent.putExtra("trainName", trainName);
//        intent.putExtra("departureTime", departureTime);
//        intent.putExtra("arrivalTime", arrivalTime);
//        intent.putExtra("priceAdult", priceAdult);
//        intent.putExtra("arrDateTrain", arrivalDate);
//        intent.putExtra("depDateTrain", departureDate);
        startActivityForResult(intent, TravelActionCode.IS_FROM_PAY);

    }

    @Override
    public void onClickScheduleMsg(String msg) {

        snackBarCustomAction(findViewById(R.id.rootLayout), R.string.train_content_not_avail_seat, "", 3);
    }
}
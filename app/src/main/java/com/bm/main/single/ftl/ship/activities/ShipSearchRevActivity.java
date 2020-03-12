package com.bm.main.single.ftl.ship.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;



import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.templates.MaterialNumberPicker;
import com.bm.main.fpl.templates.showcaseview.GuideView;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.pos.R;
import com.bm.main.pos.SBFApplication;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bm.main.single.ftl.ship.constants.ShipKeyPreference;
import com.bm.main.single.ftl.ship.constants.ShipPath;
import com.bm.main.single.ftl.ship.models.ShipDestinationModel;
import com.bm.main.single.ftl.utils.RequestUtilsTravel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.bm.main.fpl.utils.DialogUtils.closeBootomSheetDialog;
import static com.bm.main.fpl.utils.DialogUtils.openBottomSheetDialog;

public class ShipSearchRevActivity extends BaseActivity implements View.OnClickListener, ProgressResponseCallback {
    private static final String TAG = ShipSearchRevActivity.class.getSimpleName();
    int selectedYear = 0;
    int selectedMonth = 0;
    int currentYear = 0;
    int currentMonth = 0;

    // TextView tvPenumpangLaki, tvPenumpangPerempuan;
    // TextView tvOrigin, tvDestination;
    // AppCompatButton btnCari;

//    TextView textViewYear;
//    GridView gridViewCalendar;

//    ImageView ivKurangPenumpangPerempuan, ivKurangPenumpangLaki;
//    ImageView ivTambahPenumpangPerempuan, ivTambahPenumpangLaki;

//    ImageButton ivSwap;

    // int jmlPenumpangLaki = 1, jmlPenumpangPerempuan = 0;
    // String destinationKode = "", destinationKey = "", originKode = "", originKey = "";

//    CalendarAdapter calendarAdapter;


    AppCompatButton btnPilih, btnBatal;
    Intent intent;
    boolean isFromPay = false;

    MaterialNumberPicker pickerAdult, pickerChild, pickerInfant;

    int pickerAdultValue = 1;
    int pickerInfantValue = 0;
//    int pickerInfantValue = 0;

    @Nullable
    String originKode, destinationKode;
//    String depDate;
//    String returnDate;

    private TextView mTextViewNamaPelabuhanAsal;
    //    private TextView mTextViewKodeNamaPelabuhanAsal;
//    private LinearLayout mLinPelabuhanAsal;
    private ImageView mImageViewTukarPelabuhan;
    private TextView mTextViewNamaPelabuhanTujuan;
    //    private TextView mTextViewKodeNamaPelabuhanTujuan;
//    private LinearLayout mLinPelabuhanTujuan;

    //    private TextView mTextViewTanggalBerangkat;
//    private TextView mTextViewBulanBerangkat;
//    private TextView mTextViewHariTahunBerangkat;
//    private LinearLayout mLinTanggalBerangkat;
//    private TextView mTextViewJumlahDewasa;
//    //    private TextView mTextViewJumlahAnak;
//    private TextView mTextViewJumlahBayi;
//    private LinearLayout mLinMainJumlahPenumpang;
    private AppCompatButton mAppButtonCariKapal;
    private RelativeLayout relativeTahun;


    //    private TextView mTextViewBulanBerangkat;
//    private TextView mTextViewTahunBerangkat;
    private TextView mTextViewJadwalBerangkat;
//    private LinearLayout mLinJadwalBerangkat;

    Calendar calendar = Calendar.getInstance();
    private ShipDestinationModel shipDestinationModel;
    private TextView textJumlahPenumpang;
//    private ImageView imageViewSearchShip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ship_search_rev_activity);
        if (intent != null)
            isFromPay = intent.getBooleanExtra("isFromPay", true);
        initView();

        init(0);

        if (PreferenceClass.getJSONObject(ShipKeyPreference.portListData).length() > 0) {
//            if (layout_no_connection.getVisibility() == View.VISIBLE) {
//                layout_no_connection.setVisibility(View.GONE);
//            }
//            if (layout_data_empty.getVisibility() == View.VISIBLE) {
//                layout_data_empty.setVisibility(View.GONE);
//            }
//            if (linMain.getVisibility() == View.GONE) {
//                linMain.setVisibility(View.VISIBLE);
//            }
//            if (mShimmerViewContainer.getVisibility() == View.VISIBLE) {
//                mShimmerViewContainer.setVisibility(View.GONE);
//                mShimmerViewContainer.stopShimmerAnimation();
//            }
            shipDestinationModel = gson.fromJson(PreferenceClass.getJSONObject(ShipKeyPreference.portListData).toString(), ShipDestinationModel.class);

//            itemList.clear();
//            itemList.addAll(shipDestinationModel.getData());
//            Collections.sort(itemList);
//            //  setData(gameModel.getData());
//            adapter.notifyDataSetChanged();
//            recyclerViewPort.setVisibility(View.VISIBLE);
        }


        RequestPort();
    }

    private void RequestPort() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", PreferenceClass.getToken());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Log.d(TAG, "RequestAirport: " + jsonObject);
        RequestUtilsTravel.transportWithProgressResponse(ShipSearchRevActivity.this, ShipPath.PORTLIST, jsonObject, TravelActionCode.PORTLIST,this);
    }
    private void initView() {

//        imageViewSearchShip=findViewById(R.id.imageViewSearchShip);
//        Glide.with(this).asBitmap().load(R.drawable.background_search_kereta).encodeFormat(Bitmap.CompressFormat.WEBP).encodeQuality(50).diskCacheStrategy(DiskCacheStrategy.NONE).into(new BitmapImageViewTarget(imageViewSearchShip) {
//            @Override
//            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> animation) {
//                // here it's similar to RequestListener, but with less information (e.g. no model available)
//                super.onResourceReady(resource, animation);
//                //viewHolder.avi.setVisibility(View.GONE);
//                imageViewSearchShip.refreshDrawableState();
//                // here you can be sure it's already set
//            }
//            // +++++ OR +++++
//            @Override protected void setResource(Bitmap resource) {
//                // this.getView().setImageDrawable(resource); is about to be called
//                super.setResource(resource);
//                // viewHolder.avi.setVisibility(View.GONE);
//                imageViewSearchShip.setScaleType(ImageView.ScaleType.FIT_XY);
//
//                // here you can be sure it's already set
//            }
//
//            @Override
//            public void onLoadFailed(@Nullable Drawable errorDrawable) {
//    super.onLoadFailed(errorDrawable);
////                viewHolder.avi.setVisibility(View.GONE);
////                viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.gameonline_default));
//            }
//        });
        mTextViewNamaPelabuhanAsal = (TextView) findViewById(R.id.textPelabuhanAsal);
        mTextViewNamaPelabuhanAsal.setOnClickListener(this);
//        mTextViewKodeNamaPelabuhanAsal = (TextView) findViewById(R.id.textViewKodeNamaPelabuhanAsal);
//        mLinPelabuhanAsal = (LinearLayout) findViewById(R.id.linPelabuhanAsal);
//        mLinPelabuhanAsal.setOnClickListener(this);
        mImageViewTukarPelabuhan =  findViewById(R.id.btnExchange);
        mImageViewTukarPelabuhan.setOnClickListener(this);
        mTextViewNamaPelabuhanTujuan = (TextView) findViewById(R.id.textPelabuhanTujuan);
        mTextViewNamaPelabuhanTujuan .setOnClickListener(this);
//        mTextViewKodeNamaPelabuhanTujuan = (TextView) findViewById(R.id.textViewKodeNamaPelabuhanTujuan);
//        mLinPelabuhanTujuan = (LinearLayout) findViewById(R.id.linPelabuhanTujuan);
//        mLinPelabuhanTujuan.setOnClickListener(this);
        // mRelImage = (RelativeLayout) findViewById(R.id.relImage);

        relativeTahun = findViewById(R.id.relativeTahun);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pelni");
//        mToolbarCollapsing = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        mTextViewTanggalBerangkat = (TextView) findViewById(R.id.textViewTanggalBerangkat);
//        mTextViewBulanBerangkat = (TextView) findViewById(R.id.textViewBulanBerangkat);
//        mTextViewHariTahunBerangkat = (TextView) findViewById(R.id.textViewHariTahunBerangkat);
//        mLinTanggalBerangkat = (LinearLayout) findViewById(R.id.linTanggalBerangkat);
//        mLinTanggalBerangkat.setOnClickListener(this);
        textJumlahPenumpang = (TextView) findViewById(R.id.textJumlahPenumpang);
        textJumlahPenumpang.setOnClickListener(this);

//        mTextViewJumlahDewasa = (TextView) findViewById(R.id.textViewJumlahDewasa);
//        mTextViewJumlahAnak = (TextView) findViewById(R.id.textViewJumlahAnak);
//        mTextViewJumlahBayi = (TextView) findViewById(R.id.textViewJumlahBayi);
//        mLinMainJumlahPenumpang = (LinearLayout) findViewById(R.id.linMainJumlahPenumpang);
//        mLinMainJumlahPenumpang.setOnClickListener(this);
        mAppButtonCariKapal = (AppCompatButton) findViewById(R.id.buttonCari_Kapal);
        mAppButtonCariKapal.setOnClickListener(this);
//        mAppBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);

        mTextViewJadwalBerangkat = (TextView) findViewById(R.id.textJadwalBerangkat);
        mTextViewJadwalBerangkat.setOnClickListener(this);
//        mTextViewBulanBerangkat = (TextView) findViewById(R.id.textViewBulanBerangkat);
//        mTextViewTahunBerangkat = (TextView) findViewById(R.id.textViewTahunBerangkat);
//        mLinJadwalBerangkat = (LinearLayout) findViewById(R.id.linJadwalBerangkat);
//        mLinJadwalBerangkat.setOnClickListener(this);

        String kodeAsal = PreferenceClass.getString(ShipKeyPreference.pelabuhanKodeAsal, "");
        String pelabuhanAsal = PreferenceClass.getString(ShipKeyPreference.pelabuhanNamaAsal, "");

        if (kodeAsal.equals("")) {
            pelabuhanAsal = "TANJUNG PRIOK (JAKARTA)";
            kodeAsal = "431";
            //pelabuhanAsal = "Surabaya Gubeng";
//            PreferenceClass.putString(ShipKeyPreference.pelabuhanKotaAsal, pelabuhanAsal);
            PreferenceClass.putString(ShipKeyPreference.pelabuhanKodeAsal, kodeAsal);
            PreferenceClass.putString(ShipKeyPreference.pelabuhanNamaAsal, pelabuhanAsal);

        }

        //Log.d(TAG, "onCreate: " + kotaAsal);
        originKode = kodeAsal;
//        mTextViewKodeNamaPelabuhanAsal.setText(kotaAsal);
        mTextViewNamaPelabuhanAsal.setText(pelabuhanAsal);

//        String kotaTujuan = PreferenceClass.getString(ShipKeyPreference.pelabuhanKotaTujuan, "");
        String kodeTujuan = PreferenceClass.getString(ShipKeyPreference.pelabuhanKodeTujuan, "");
        String pelabuhanTujuan = PreferenceClass.getString(ShipKeyPreference.pelabuhanNamaTujuan, "");
        if (kodeTujuan.equals("")) {
            //  kotaTujuan = "Jakarta";
            kodeTujuan = "563";
            pelabuhanTujuan = "TANJUNG PERAK (SURABAYA)";
//            PreferenceClass.putString(ShipKeyPreference.pelabuhanKotaTujuan, kotaTujuan);
            PreferenceClass.putString(ShipKeyPreference.pelabuhanKodeTujuan, kodeTujuan);
            PreferenceClass.putString(ShipKeyPreference.pelabuhanNamaTujuan, pelabuhanTujuan);

        }
        destinationKode = kodeTujuan;
//        mTextViewKodeNamaPelabuhanTujuan.setText(kotaTujuan);
        mTextViewNamaPelabuhanTujuan.setText(pelabuhanTujuan);

//        Date now = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", config.locale);
//        SimpleDateFormat formatterShow = new SimpleDateFormat("EEEE, dd MMM yyyy", config.locale);
//        SimpleDateFormat formatterShowDate = new SimpleDateFormat("dd", config.locale);
//        SimpleDateFormat formatterShowMonth = new SimpleDateFormat("MMMM", config.locale);
//        SimpleDateFormat formatterShowDayYear = new SimpleDateFormat("EEEE, yyyy", config.locale);
//        String showDateNow = formatterShow.format(now);
//        String showDateNowDate = formatterShowDate.format(now);
//        String showDateNowMonth = formatterShowMonth.format(now);
//        String showDateNowDayYear = formatterShowDayYear.format(now);
//
//        String dateNow = formatter.format(now);
//        mTextViewTanggalBerangkat.setText(showDateNowDate);
//        mTextViewBulanBerangkat.setText(showDateNowMonth);
//        mTextViewHariTahunBerangkat.setText(showDateNowDayYear);
//
//        depDate = dateNow;
//        returnDate = dateNow;
//
//        PreferenceClass.putString(ShipKeyPreference.departureDateTrain, dateNow);
//        PreferenceClass.putString(ShipKeyPreference.departureDateShowTrain, showDateNow);
//        PreferenceClass.putString(ShipKeyPreference.returnDateTrain, dateNow);
//        PreferenceClass.putString(ShipKeyPreference.returnDateShowTrain, showDateNow);

//        gridViewCalendar = findViewById(R.id.gridViewCalendar);
//        calendarAdapter = new CalendarAdapter(this);
//        calendarAdapter.selectedIndex = Calendar.getInstance().get(Calendar.MONTH);
//        gridViewCalendar.setAdapter(calendarAdapter);
//        gridViewCalendar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if(selectedYear == currentYear && position < (currentMonth)) {
////                    showToastCustom(StartShipActivity.this, 1, "Tidak bisa memilih bulan sebelumnya");
//                    return;
//                }
//                selectedMonth = position;
//                PreferenceClass.putInt(ShipKeyPreference.shipMonth,selectedMonth);
//                calendarAdapter.selectedIndex = position;
//                calendarAdapter.notifyDataSetChanged();
//            }
//        });
//        ImageView btnPrevYear = findViewById(R.id.btnPrevYear);
//        ImageView btnNextYear = findViewById(R.id.btnNextYear);
//        textViewYear = findViewById(R.id.textViewYear);
//
//        btnPrevYear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(selectedYear - 1 < currentYear) {
//                    return;
//                }
//                selectedYear--;
//                if(selectedYear == currentYear)
//                    selectedMonth = currentMonth;
//
//                textViewYear.setText(String.valueOf(selectedYear));
//                PreferenceClass.putInt(ShipKeyPreference.shipYear,selectedYear);
//                calendarAdapter.selectedIndex = selectedMonth;
//                calendarAdapter.notifyDataSetChanged();
//            }
//        });
//
//        btnNextYear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                selectedYear++;
//                textViewYear.setText(String.valueOf(selectedYear));
//                PreferenceClass.putInt(ShipKeyPreference.shipYear,selectedYear);
//                calendarAdapter.selectedIndex = selectedMonth;
//                calendarAdapter.notifyDataSetChanged();
//               // reloadView(true);
//            }
//        });

//        Calendar calendar = Calendar.getInstance();
        currentYear = calendar.get(Calendar.YEAR);
        currentMonth = calendar.get(Calendar.MONTH);

//        int month = calendar.get(Calendar.MONTH);
//        int day=calendar.get(Calendar.DATE)+1;

        if (selectedYear < currentYear) {
            selectedYear = currentYear;
            if (selectedMonth < currentMonth)
                selectedMonth = currentMonth;
        }
//        textViewYear.setText(String.valueOf(selectedYear));
        // mTextViewTahunBerangkat.setText(String.valueOf(selectedYear));
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", SBFApplication.config.locale);
        Date startDate = calendar.getTime();
        Log.d(TAG, "initView: "+selectedMonth);
//        PreferenceClass.putString(ShipKeyPreference.shipStartDate, sdf.format(startDate));
        // mTextViewBulanBerangkat.setText(sdf.format(startDate));
        mTextViewJadwalBerangkat.setText(sdf.format(startDate));

        PreferenceClass.putInt(ShipKeyPreference.shipMonth,selectedMonth);
        PreferenceClass.putInt(ShipKeyPreference.shipYear,selectedYear);
//        calendarAdapter.selectedIndex = selectedMonth;
//        calendarAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TravelActionCode.LIST_ORIGIN_PORT && resultCode == Activity.RESULT_OK) {
            if (PreferenceClass.getString(ShipKeyPreference.pelabuhanKodeTujuan, "").equals(data.getStringExtra("pelabuhanKode"))) {
                snackBarCustomAction(findViewById(R.id.rootLayout), R.string.ship_content_same_pelabuhan, "", 1);
                mTextViewNamaPelabuhanAsal.setBackgroundResource(R.drawable.shape_card_error);
                mTextViewNamaPelabuhanAsal.setAnimation(animShake);
                mTextViewNamaPelabuhanAsal.startAnimation(animShake);
                getar();
            } else {
                originKode = data.getStringExtra("pelabuhanKode");
                mTextViewNamaPelabuhanAsal.setText(data.getStringExtra("pelabuhanNama"));
//                mTextViewKodeNamaPelabuhanAsal.setText(data.getStringExtra("pelabuhanKota"));

                PreferenceClass.putString(ShipKeyPreference.pelabuhanNamaAsal, data.getStringExtra("pelabuhanNama"));
//                PreferenceClass.putString(ShipKeyPreference.pelabuhanKotaAsal, data.getStringExtra("pelabuhanKota"));
                PreferenceClass.putString(ShipKeyPreference.pelabuhanKodeAsal, data.getStringExtra("pelabuhanKode"));
            }
        } else if (requestCode == TravelActionCode.LIST_DESTINATION_PORT && resultCode == Activity.RESULT_OK) {
            if (PreferenceClass.getString(ShipKeyPreference.pelabuhanKodeAsal, "").equals(data.getStringExtra("pelabuhanKode"))) {
                snackBarCustomAction(findViewById(R.id.rootLayout), R.string.ship_content_same_pelabuhan, "", 1);
                mTextViewNamaPelabuhanTujuan.setBackgroundResource(R.drawable.shape_card_error);
                mTextViewNamaPelabuhanTujuan.setAnimation(animShake);
                mTextViewNamaPelabuhanTujuan.startAnimation(animShake);
                getar();
            } else {
                destinationKode = data.getStringExtra("pelabuhanKode");
                mTextViewNamaPelabuhanTujuan.setText(data.getStringExtra("pelabuhanNama"));
//                mTextViewKodeNamaPelabuhanTujuan.setText(data.getStringExtra("pelabuhanKota"));

                PreferenceClass.putString(ShipKeyPreference.pelabuhanNamaTujuan, data.getStringExtra("pelabuhanNama"));
//                PreferenceClass.putString(ShipKeyPreference.pelabuhanKotaTujuan, data.getStringExtra("pelabuhanKota"));
                PreferenceClass.putString(ShipKeyPreference.pelabuhanKodeTujuan, data.getStringExtra("pelabuhanKode"));
            }
        } else if (requestCode == TravelActionCode.LIST_TANGGAL_BERANGKAT && resultCode == Activity.RESULT_OK) {
            //textTanggalBerangkat.setText(data.getStringExtra("dateShow"));
            // mTextViewTanggalBerangkat.setText(data.getStringExtra("dateShowDate"));
            selectedMonth = data.getIntExtra("selectedMonth", 0);
            selectedYear = data.getIntExtra("selectedYear", 0);

//            currentYear = calendar.get(Calendar.YEAR);
//            currentMonth = calendar.get(selectedMonth);
//Date bulan=calendar.get(selectedMonth);
            // SimpleDateFormat sdf = new SimpleDateFormat("MMMM", config.locale);


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

            // Date endDate = calendar.getTime();

            SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", SBFApplication.config.locale);

//            mTextViewBulanBerangkat.setText(sdf.format(startDate));
            mTextViewJadwalBerangkat.setText(sdf.format(startDate));

            // mTextViewTahunBerangkat.setText(String.valueOf(selectedYear));

//            PreferenceClass.putString(TrainKeyPreference.departureDateTrain, data.getStringExtra("date"));
//            PreferenceClass.putString(TrainKeyPreference.departureDateShowTrain, data.getStringExtra("dateShow"));
//            depDate = data.getStringExtra("date");
//
//            PreferenceClass.putString(TrainKeyPreference.returnDateTrain, data.getStringExtra("date"));
//            PreferenceClass.putString(TrainKeyPreference.returnDateShowTrain, data.getStringExtra("dateShow"));
//            returnDate = data.getStringExtra("date");
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


    @Override
    public void onClick(@NonNull View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.textPelabuhanAsal:
                // TODO 19/03/29
                //  requestCode = 1;
                intent = new Intent(ShipSearchRevActivity.this, ShipPortActivity.class);
                //   intent.putExtra("initPelabuhan", "asal");
                startActivityForResult(intent, TravelActionCode.LIST_ORIGIN_PORT);
                overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
                break;
            case R.id.btnExchange:
                // TODO 19/03/29
                switchPelabuhan(v);
                break;
            case R.id.textPelabuhanTujuan:
                // TODO 19/03/29
                //requestCode = 2;
                intent = new Intent(ShipSearchRevActivity.this, ShipPortActivity.class);
                // intent.putExtra("initPelabuhan", "tujuan");
                startActivityForResult(intent, TravelActionCode.LIST_DESTINATION_PORT);
                overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
                break;
            case R.id.textJadwalBerangkat:
                // TODO 19/03/29
                //  requestCode = 3;
                intent = new Intent(ShipSearchRevActivity.this, ShipTanggalActivity.class);
//                intent.putExtra("initTanggal", "pergi");
                startActivityForResult(intent, TravelActionCode.LIST_TANGGAL_BERANGKAT);
                overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
                break;
            case R.id.textJumlahPenumpang:
                // TODO 19/03/29
                openBottomSheet(v);
                break;
            case R.id.buttonCari_Kapal:
                // TODO 19/03/29

//                if (originKode.equals(destinationKode)){
//                    snackBarCustomAction(findViewById(R.id.rootLayout), R.string.ship_content_same_pelabuhan, "", 1);
//                    mLinPelabuhanAsal.setBackgroundResource(R.drawable.card_error);
//                    mLinPelabuhanAsal.setAnimation(animShake);
//                    mLinPelabuhanAsal.startAnimation(animShake);
//                    getar();
//                    return;
//                }

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
                PreferenceClass.putInt(ShipKeyPreference.countAdultShip, pickerAdultValue);
                PreferenceClass.putInt(ShipKeyPreference.countInfantShip, pickerInfantValue);
                PreferenceClass.putString(ShipKeyPreference.shipStartDate, sdf.format(startDate));
                PreferenceClass.putString(ShipKeyPreference.shipEndDate, sdf.format(endDate));
                intent = new Intent(ShipSearchRevActivity.this, ShipScheduleActivity.class);
                intent.putExtra("cari", true);
                startActivityForResult(intent, TravelActionCode.IS_FROM_PAY);

                break;
            default:
                break;
        }
    }

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
//                    showCaseFirst(ShipSearchRevActivity.this, "", getResources().getString(R.string.content_ship_pelabuhan_asal), mTextViewNamaPelabuhanAsal);
//
//                    mGbuilder.setGuideListener(new GuideView.GuideListener() {
//                        @Override
//                        public void onDismiss(@NonNull View view) {
//                            switch (view.getId()) {
//                                case R.id.textPelabuhanAsal:
//                                    mGbuilder
//                                            .setTitle("")
//                                            .setContentText(getResources().getString(R.string.content_ship_tukar_pelabuhan)).setGravity(GuideView.Gravity.center)
//                                            .setDismissType(GuideView.DismissType.anywhere)
//                                            .setTargetView(mImageViewTukarPelabuhan)
//                                            .setCircleView(true)
//                                            .build();
//                                    break;
//                                case R.id.btnExchange:
//                                    mGbuilder
//                                            .setTitle("")
//                                            .setContentText(getResources().getString(R.string.content_ship_pelabuhan_tujuan)).setGravity(GuideView.Gravity.center)
//                                            .setDismissType(GuideView.DismissType.anywhere)
//                                            .setTargetView(mTextViewNamaPelabuhanTujuan)
//                                            .setCornerRadius(5)
//                                            .setCircleView(false)
//                                            .build();
//                                    break;
//
//                                case R.id.textPelabuhanTujuan:
////                                    mGbuilder
////                                            .setTitle("")
////                                            .setContentText(getResources().getString(R.string.content_ship_tahun_berangkat)).setGravity(GuideView.Gravity.center)
////                                            .setDismissType(GuideView.DismissType.anywhere)
////                                            .setTargetView(relativeTahun)
////                                            .setCornerRadius(10)
////                                            .setCircleView(false)
////                                            .build();
////                                    break;
//                                    mGbuilder
//                                            .setTitle("")
//                                            .setContentText(getResources().getString(R.string.content_ship_jadwal_berangkat)).setGravity(GuideView.Gravity.center)
//                                            .setDismissType(GuideView.DismissType.anywhere)
//                                            .setTargetView(mTextViewJadwalBerangkat)
//                                            .setCornerRadius(10)
//                                            .setCircleView(false)
//                                            .build();
//                                    break;
//
////                                case R.id.relativeTahun:
////                                    mGbuilder
////                                            .setTitle("")
////                                            .setContentText(getResources().getString(R.string.content_ship_bulan_berangkat)).setGravity(GuideView.Gravity.center)
////                                            .setDismissType(GuideView.DismissType.anywhere)
////                                            .setTargetView(gridViewCalendar)
////                                            .setCornerRadius(10)
////                                            .setCircleView(false)
////                                            .build();
////                                    break;
////                                case R.id.gridViewCalendar:
//                                case R.id.textJadwalBerangkat:
//                                    mGbuilder
//                                            .setTitle("")
//                                            .setContentText(getResources().getString(R.string.content_ship_jumlah_penumpang)).setGravity(GuideView.Gravity.center)
//                                            .setDismissType(GuideView.DismissType.anywhere)
//                                            .setTargetView(textJumlahPenumpang)
//                                            .setCornerRadius(10)
//                                            .setCircleView(false)
//                                            .build();
//                                    break;
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

        } else if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        finish();

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    public void openBottomSheet(View v) {
        final FrameLayout view = (FrameLayout)View.inflate(this,R.layout.travel_two_passager_ship, null);
        pickerAdult = view.findViewById(R.id.materialNumberPickerDewasa);
        // pickerChild = view.findViewById(R.id.jumlahAnak);
        pickerInfant = view.findViewById(R.id.materialNumberPickerBayi);
        pickerAdult.setValue(pickerAdultValue);
        // pickerChild.setValue(pickerChildValue);
        pickerInfant.setValue(pickerInfantValue);
        pickerInfant.setMaxValue(pickerAdultValue);
//        if (pickerAdultValue > 5) {
//            pickerInfant.setMaxValue(10 - pickerAdultValue);
//        }
        pickerInfant.setMaxValue(pickerAdultValue);
        btnBatal = view.findViewById(R.id.btn_batal);
        btnPilih = view.findViewById(R.id.btn_pilih);
        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeBootomSheetDialog();

                //     mTextViewJumlahDewasa.setText(String.valueOf(PreferenceClass.getInt(ShipKeyPreference.countAdultShip, 1)));
                //  mTextViewJumlahAnak.setText(String.valueOf(pickerChild.getValue()));
                //     mTextViewJumlahBayi.setText(String.valueOf(PreferenceClass.getInt(ShipKeyPreference.countInfantShip, 0)));
            }
        });
        btnPilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeBootomSheetDialog();
                pickerAdultValue = pickerAdult.getValue();
                //  pickerChildValue = pickerChild.getValue();
                pickerInfantValue = pickerInfant.getValue();
//                PreferenceClass.putInt(ShipKeyPreference.countAdultShip, pickerAdultValue);
//                PreferenceClass.putInt(ShipKeyPreference.countInfantShip, pickerInfantValue);
//                PreferenceClass.putInt(ShipKeyPreference.countInfantTrain, pickerInfantValue);
//                mTextViewJumlahDewasa.setText(String.valueOf(pickerAdult.getValue()));
//                //  mTextViewJumlahAnak.setText(String.valueOf(pickerChild.getValue()));
//                mTextViewJumlahBayi.setText(String.valueOf(pickerInfant.getValue()));
                if (pickerInfant.getValue() == 0) {
                    textJumlahPenumpang.setText(pickerAdult.getValue() + " Dewasa");
                }


                else {
                    textJumlahPenumpang.setText(pickerAdult.getValue() + " Dewasa," + pickerInfant.getValue() + " Bayi");
                }
            }
        });

        pickerAdult.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                pickerInfant.setMaxValue(newVal);
//                if (newVal > 5) {
//                    pickerInfant.setMaxValue(10 - newVal);
//                }
            }
        });

//        pickerAdult.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
////            @Override
////            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                //Log.d(TAG, "onScrollStateChange: " + oldVal + " " + newVal);
//               // pickerInfant.setMaxValue();
//                if (pickerInfant.getValue() > newVal) {
//                    snackBarCustomAction(view, R.string.penumpang_bayi_picker, String.valueOf(R.string.action_tutup), ALERT);
//                    pickerInfant.setValue(newVal);
//                }
//            }
//        });
//
//        pickerAdult.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                //Log.d(TAG, "onScrollStateChange: " + oldVal + " " + newVal);
//                if (pickerInfant.getValue() + newVal>10) {
//                    snackBarCustomAction(view, R.string.penumpang_ship_max, String.valueOf(R.string.action_tutup), ALERT);
////                    pickerAdult.setValue(oldVal);
//                    pickerAdult.setValue(oldVal);
//                }
//            }
//        });
//
//        pickerInfant.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                //Log.d(TAG, "onScrollStateChange: " + oldVal + " " + newVal);
//                if (pickerAdult.getValue() + newVal>10) {
//                    snackBarCustomAction(view, R.string.penumpang_ship_max, String.valueOf(R.string.action_tutup), ALERT);
////                    pickerInfant.setValue(oldVal);
//                    pickerInfant.setValue(10-pickerAdult.getValue());
//                }
//            }
//        });
//        pickerInfant.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                //Log.d(TAG, "onScrollStateChange: " + oldVal + " " + newVal);
//                if (newVal > pickerAdult.getValue()) {
////
//
//                    snackBarCustomAction(view, R.string.penumpang_bayi_picker,  String.valueOf(R.string.action_tutup), ALERT);
//
////                    pickerInfant.setValue(oldVal);
//                    pickerInfant.setValue(pickerAdult.getValue());
//
//
//                }
//            }
//        });

//        pickerChild.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                //Log.d(TAG, "onScrollStateChange: " + oldVal + " " + newVal);
//                if ((pickerAdult.getValue() + newVal > 7)) {
//                    snackBarCustomAction(view, R.string.penumpang_max, String.valueOf(R.string.action_tutup), ALERT);
//                    pickerChild.setValue(oldVal);
//                }
//            }
//        });

//        pickerInfant.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                //Log.d(TAG, "onScrollStateChange: " + oldVal + " " + newVal);
//                if (newVal > pickerAdult.getValue()) {
////
//
//                    snackBarCustomAction(view, R.string.penumpang_bayi_picker, String.valueOf(R.string.action_tutup), ALERT);
//
//                    pickerInfant.setValue(oldVal);
//
//
//                }
//            }
//        });

        openBottomSheetDialog(this, view);
    }

    public void switchPelabuhan(View v) {
//        if (!textKotaAsal.getText().toString().equals("") || !textKotaTujuan.getText().toString().equals("")) {
//            PreferenceClass.putString( "pelabuhanKotaTujuan", kotaTujuan);
//            PreferenceClass.putString("pelabuhanKodeTujuan", kodeTujuan);
//            PreferenceClass.putString("pelabuhanNamaTujuan", bandaraTujuan);
//

        String originCode = PreferenceClass.getString(ShipKeyPreference.pelabuhanKodeAsal, "");
        // String originPelabuhan = PreferenceClass.getString( "pelabuhanNamaAsal","");

        String destinationCode = PreferenceClass.getString(ShipKeyPreference.pelabuhanKodeTujuan, "");
        // String destinationPelabuhan = PreferenceClass.getString( "pelabuhanNamaTujuan","");


        String originPelabuhan = mTextViewNamaPelabuhanAsal.getText().toString();
//        String originName = mTextViewKodeNamaPelabuhanAsal.getText().toString();


        String destinationPelabuhan = mTextViewNamaPelabuhanTujuan.getText().toString();
//        String destinationName = mTextViewKodeNamaPelabuhanTujuan.getText().toString();

        mTextViewNamaPelabuhanAsal.setText(destinationPelabuhan);
//        mTextViewKodeNamaPelabuhanAsal.setText(destinationName);

        mTextViewNamaPelabuhanTujuan.setText(originPelabuhan);
//        mTextViewKodeNamaPelabuhanTujuan.setText(originName);


        originKode = destinationCode;
        destinationKode = originCode;

        PreferenceClass.putString(ShipKeyPreference.pelabuhanKodeAsal, destinationCode);
//        PreferenceClass.putString(ShipKeyPreference.pelabuhanKotaAsal, destinationName);
        PreferenceClass.putString(ShipKeyPreference.pelabuhanNamaAsal, destinationPelabuhan);


        PreferenceClass.putString(ShipKeyPreference.pelabuhanKodeTujuan, originCode);
//        PreferenceClass.putString(ShipKeyPreference.pelabuhanKotaTujuan, originName);
        PreferenceClass.putString(ShipKeyPreference.pelabuhanNamaTujuan, originPelabuhan);

//        }
    }


    public class CalendarAdapter extends BaseAdapter {
        private Context mContext;
        public int selectedIndex = 0;

        public CalendarAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return 12;
        }

        @Nullable
        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view;
            view = inflater.inflate(R.layout.ship_item_month, null);
            TextView tvMonth = view.findViewById(R.id.tvMonth);
            String[] months = new String[]{
                    "Januari", "Februari", "Maret",
                    "April", "Mei", "Juni",
                    "Juli", "Agustus", "September",
                    "Oktober", "November", "Desember"
            };

            tvMonth.setText(months[position]);

            if (selectedYear * 100 + position < currentYear * 100 + currentMonth) {
                tvMonth.setTextColor(ContextCompat.getColor(ShipSearchRevActivity.this, R.color.md_white_1000));
//                view.setBackgroundResource(R.color.md_grey_400);
                tvMonth.setBackgroundResource(R.drawable.selector_button_grey_pressed);
            } else {
                if (selectedIndex == position) {
                    tvMonth.setTextColor(ContextCompat.getColor(ShipSearchRevActivity.this, R.color.md_blue_500));
                    tvMonth.setBackgroundResource(R.drawable.selector_button_blue_white_pressed);
                    //view.setBackgroundResource(R.color.colorPrimary);
                } else {
//                    tvMonth.setTextColor(Color.BLACK);
////                    view.setBackgroundResource(R.color.md_white_1000);
                    tvMonth.setTextColor(ContextCompat.getColor(ShipSearchRevActivity.this, R.color.md_orange_500));
//                view.setBackgroundResource(R.color.md_grey_400);
                    tvMonth.setBackgroundResource(R.drawable.selector_button_orange_white_pressed_grid);
                }
            }

            return view;
        }
    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {

//        if (layout_no_connection.getVisibility() == View.VISIBLE) {
//            layout_no_connection.setVisibility(View.GONE);
//        }
//        if (linMain.getVisibility() == View.GONE) {
//            linMain.setVisibility(View.VISIBLE);
//        }
//        if (mShimmerViewContainer.getVisibility() == View.VISIBLE) {
//            mShimmerViewContainer.setVisibility(View.GONE);
//            mShimmerViewContainer.stopShimmerAnimation();
//        }

        if (actionCode == TravelActionCode.PORTLIST) {

            shipDestinationModel = gson.fromJson(response.toString(), ShipDestinationModel.class);
            // data.clear();
            if (shipDestinationModel.getRc().equals(ResponseCode.SUCCESS)) {

//                data.addAll(produkModel.getData());
//                //  setData(gameModel.getData());
//                adapter.notifyDataSetChanged();
                // stop animating Shimmer and hide the layout
                JSONObject obj = PreferenceClass.getJSONObject(ShipKeyPreference.portListData);

                JSONArray array = new JSONArray();

                try {

                    array = obj.getJSONArray("data");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (array.length() != shipDestinationModel.getData().size()) {
                    Log.d(TAG, "onSuccess: "+response.toString());
//                    itemList.clear();

                    PreferenceClass.putJSONObject(ShipKeyPreference.portListData, response);
//                    itemList.addAll(shipDestinationModel.getData());
//                    Collections.sort(itemList);
//                    adapter.notifyDataSetChanged();
                }
                //recyclerViewPort.setVisibility(View.VISIBLE);
//                if (flightAirportModel.getData().size() == 0) {
//                    layout_data_empty.setVisibility(View.VISIBLE);
//                    txtHeader.setText("Data Airport");
//                    txtSub.setText("Tidak Ditemukan");
//                    recyclerViewAirport.setVisibility(View.GONE);
//                }
            } else {
//                layout_data_empty.setVisibility(View.VISIBLE);
//                txtHeader.setText("Data Pelabuhan");
//                txtSub.setText("Tidak Ditemukan");
            }
        }

    }

    @Override
    public void onFailure(int actionCode, @NonNull String responseCode, String responseDescription, Throwable throwable) {

        if (responseCode.equals(ResponseCode.NETWORK_ERROR)) {
            if (actionCode == TravelActionCode.PORTLIST) {
                if (PreferenceClass.getJSONObject(ShipKeyPreference.portListData).length() > 0) {

                } else {
//                    mShimmerViewContainer.stopShimmerAnimation();
//                    mShimmerViewContainer.setVisibility(View.GONE);
//                    linMain.setVisibility(View.GONE);
//                    layout_data_empty.setVisibility(View.GONE);
//                    layout_no_connection.setVisibility(View.VISIBLE);
                }
            }
        }

    }
}

package com.bm.main.single.ftl.flight.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
//import android.support.design.widget.AppBarLayout;
//import android.support.design.widget.CollapsingToolbarLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.main.fpl.utils.Device;
import com.bm.main.fpl.utils.DialogUtils;
import com.bm.main.pos.R;
import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.PreferenceKey;
import com.bm.main.fpl.constants.ProdukGroup;
import com.bm.main.fpl.templates.MaterialNumberPicker;
import com.bm.main.fpl.templates.showcaseview.GuideView;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.single.ftl.activities.TravelTanggalActivity;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bm.main.single.ftl.constants.TravelKeyPreference;
import com.bm.main.single.ftl.flight.constants.FlightKeyPreference;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder;
//import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.text.SimpleDateFormat;
import java.util.Date;

//import com.bm.main.fpl.constants.ActionCode;

public class FlightSearchActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = FlightSearchActivity.class.getSimpleName();
    private View menuItemView;
    RelativeLayout relImage;
    //  LinearLayout linAirportAsal, linAirportTujuan, linTanggalBerangkat, linMainJumlahPenumpang;
    //  TextView textViewNamaAirportAsal, textViewNamaAirportTujuan, textViewKodeNamaAirportAsal, textViewKodeNamaAirportTujuan, textViewTanggalBerangkat, textViewBulanBerangkat, textViewHariTahunBerangkat, textViewJumlahDewasa, textViewJumlahAnak, textViewJumlahBayi;
    //   ImageView imageViewTukarAirport;
    AppCompatButton btnPilih, btnBatal;


    MaterialNumberPicker pickerAdult, pickerChild, pickerInfant;

    int pickerAdultValue = 1;
    int pickerChildValue = 0;
    int pickerInfantValue = 0;
    // private int requestCode;
    @Nullable
    private String origin, destination;
    private String depDate;
    private String returnDate;
//    private ImageView mImageViewSearchFlight;
    private TextView mTextViewNamaAirportAsal;
    private TextView mTextViewKodeNamaAirportAsal;
    private LinearLayout mLinAirportAsal;
    private LinearLayout linMainSearch;
    private ImageView mImageViewTukarAirport;
    private TextView mTextViewNamaAirportTujuan;
    private TextView mTextViewKodeNamaAirportTujuan;
    private LinearLayout mLinAirportTujuan;
    private RelativeLayout mRelImage;

    private CollapsingToolbarLayout mToolbarCollapsing;
    private TextView mTextViewTanggalBerangkat;
//    private TextView mTextViewBulanBerangkat;
//    private TextView mTextViewHariTahunBerangkat;
    private LinearLayout mLinTanggalBerangkat;
    private TextView mTextViewJumlahDewasa;
    private TextView mTextViewJumlahAnak;
    private TextView mTextViewJumlahBayi;
    private LinearLayout mLinMainJumlahPenumpang;
    private LinearLayout linTampilkanHarga;
    private AppCompatButton mAppButtonCariPesawat;
    private AppBarLayout mAppBarLayout;
    Intent intent;
    boolean isFromPay = false;

    ImageView imageViewSearchFlight;

    private RadioGroup radioHargaGroup;
    private RadioButton radioHargaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.flight_search_activity);
        setContentView(R.layout.flight_search_activity_edit);
        logEventFireBase(ProdukGroup.PESAWAT,ProdukGroup.PESAWAT, EventParam.EVENT_ACTION_VISIT,EventParam.EVENT_SUCCESS,TAG);
        intent = getIntent();
        if (intent != null)
            isFromPay = intent.getBooleanExtra("isFromPay", true);
        initView();
//        toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitle("Pesawat");
        init(0);

//        relImage = findViewById(R.id.relImage);
//        linAirportAsal = findViewById(R.id.linAirportAsal);
//        linAirportAsal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                requestCode = 1;
//                Intent intent = new Intent(FlightSearchActivity.this, FlightAirPortActivity.class);
//                intent.putExtra("initAirport", "asal");
//                startActivityForResult(intent, requestCode);
//                overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
//            }
//        });
//        imageViewTukarAirport = findViewById(R.id.imageViewTukarAirport);
//        imageViewTukarAirport.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                switchAirport(view);
//            }
//        });
//        linAirportTujuan = findViewById(R.id.linAirportTujuan);
//        linAirportTujuan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                requestCode = 2;
//                Intent intent = new Intent(FlightSearchActivity.this, FlightAirPortActivity.class);
//                intent.putExtra("initAirport", "tujuan");
//                startActivityForResult(intent, requestCode);
//                overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
//            }
//        });
//        linTanggalBerangkat = findViewById(R.id.linTanggalBerangkat);
//        linTanggalBerangkat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                requestCode = 3;
//                Intent intent = new Intent(FlightSearchActivity.this, TravelTanggalActivity.class);
//                intent.putExtra("initTanggal", "pergi");
//                startActivityForResult(intent, requestCode);
//                overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
//            }
//        });
//        linMainJumlahPenumpang = findViewById(R.id.linMainJumlahPenumpang);
//        linMainJumlahPenumpang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openBottomSheet(view);
//            }
//        });
        mTextViewNamaAirportAsal = findViewById(R.id.textViewNamaAirportAsal);
        mTextViewNamaAirportTujuan = findViewById(R.id.textViewNamaAirportTujuan);
        mTextViewKodeNamaAirportAsal = findViewById(R.id.textViewKodeNamaAirportAsal);
        mTextViewKodeNamaAirportTujuan = findViewById(R.id.textViewKodeNamaAirportTujuan);
        mTextViewTanggalBerangkat = findViewById(R.id.textViewTanggalBerangkat);
//        mTextViewBulanBerangkat = findViewById(R.id.textViewBulanBerangkat);
//        mTextViewHariTahunBerangkat = findViewById(R.id.textViewHariTahunBerangkat);
        mTextViewJumlahDewasa = findViewById(R.id.textViewJumlahDewasa);
        mTextViewJumlahAnak = findViewById(R.id.textViewJumlahAnak);
        mTextViewJumlahBayi = findViewById(R.id.textViewJumlahBayi);


//        appButtonCariPesawat = findViewById(R.id.appButtonCariPesawat);
//        appButtonCariPesawat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(FlightSearchActivity.this, FlightScheduleActivity.class);
//                startActivity(intent);
//            }
//        });


        String kotaAsal = PreferenceClass.getString(FlightKeyPreference.airportKotaAsal, "");
        String kodeAsal = PreferenceClass.getString(FlightKeyPreference.airportKodeAsal, "");
        String bandaraAsal = PreferenceClass.getString(FlightKeyPreference.airportNamaAsal, "");

        if (kotaAsal.equals("")) {
            kotaAsal = "Surabaya (SUB)";
            kodeAsal = "SUB";
            bandaraAsal = "JUANDA";
            PreferenceClass.putString(FlightKeyPreference.airportKotaAsal, kotaAsal);
            PreferenceClass.putString(FlightKeyPreference.airportKodeAsal, kodeAsal);
            PreferenceClass.putString(FlightKeyPreference.airportNamaAsal, bandaraAsal);

        }

        //Log.d(TAG, "onCreate: " + kotaAsal);
        mTextViewKodeNamaAirportAsal.setText(kotaAsal);
        mTextViewNamaAirportAsal.setText(bandaraAsal);
        origin = kodeAsal;
        String kotaTujuan = PreferenceClass.getString(FlightKeyPreference.airportKotaTujuan, "");
        String kodeTujuan = PreferenceClass.getString(FlightKeyPreference.airportKodeTujuan, "");
        String bandaraTujuan = PreferenceClass.getString(FlightKeyPreference.airportNamaTujuan, "");
        if (kotaTujuan.equals("")) {
            kotaTujuan = "Jakarta (CGK)";
            kodeTujuan = "CGK";
            bandaraTujuan = "Soekarno - Hatta";
            PreferenceClass.putString(FlightKeyPreference.airportKotaTujuan, kotaTujuan);
            PreferenceClass.putString(FlightKeyPreference.airportKodeTujuan, kodeTujuan);
            PreferenceClass.putString(FlightKeyPreference.airportNamaTujuan, bandaraTujuan);

        }
        mTextViewKodeNamaAirportTujuan.setText(kotaTujuan);
        mTextViewNamaAirportTujuan.setText(bandaraTujuan);
        destination = kodeTujuan;
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", config.locale);
        SimpleDateFormat formatterShow = new SimpleDateFormat("EEEE, dd MMMM yyyy", config.locale);
        SimpleDateFormat formatterShowDate = new SimpleDateFormat("dd", config.locale);
        SimpleDateFormat formatterShowMonth = new SimpleDateFormat("MMMM", config.locale);
        SimpleDateFormat formatterShowDayYear = new SimpleDateFormat("EEEE, yyyy", config.locale);

        String showDateNow = formatterShow.format(now);
//        String showDateNowDate = formatterShowDate.format(now);
//        String showDateNowMonth = formatterShowMonth.format(now);
//        String showDateNowDayYear = formatterShowDayYear.format(now);

        String dateNow = formatter.format(now);
        mTextViewTanggalBerangkat.setText(showDateNow);
//        mTextViewTanggalBerangkat.setText(showDateNowDate);
//        mTextViewBulanBerangkat.setText(showDateNowMonth);
//        mTextViewHariTahunBerangkat.setText(showDateNowDayYear);

        depDate = dateNow;
        returnDate = dateNow;

        PreferenceClass.putString(FlightKeyPreference.departureDateFlight, dateNow);
        PreferenceClass.putString(FlightKeyPreference.departureDateShowFlight, showDateNow);
        PreferenceClass.putString(FlightKeyPreference.returnDateFlight, dateNow);
        PreferenceClass.putString(FlightKeyPreference.returnDateShowFlight, showDateNow);

    }

    private void initView() {
//        mImageViewSearchFlight = (ImageView) findViewById(R.id.imageViewSearchFlight);
        CoordinatorLayout rootLayout = findViewById(R.id.rootLayout);
        imageViewSearchFlight=findViewById(R.id.imageViewSearchFlight);
        Glide.with(this).asBitmap().load(R.drawable.background_search_kereta).encodeFormat(Bitmap.CompressFormat.WEBP).encodeQuality(50).diskCacheStrategy(DiskCacheStrategy.NONE).into(new BitmapImageViewTarget(imageViewSearchFlight) {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> animation) {
                // here it's similar to RequestListener, but with less information (e.g. no model available)
                super.onResourceReady(resource, animation);
                //viewHolder.avi.setVisibility(View.GONE);
                imageViewSearchFlight.refreshDrawableState();
                // here you can be sure it's already set
            }
            // +++++ OR +++++
            @Override protected void setResource(Bitmap resource) {
                // this.getView().setImageDrawable(resource); is about to be called
                super.setResource(resource);
                // viewHolder.avi.setVisibility(View.GONE);
                imageViewSearchFlight.setScaleType(ImageView.ScaleType.FIT_XY);

                // here you can be sure it's already set
            }

            @Override
            public void onLoadFailed( Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
//                viewHolder.avi.setVisibility(View.GONE);
//                viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.gameonline_default));
            }
        });

        radioHargaGroup = (RadioGroup) findViewById(R.id.radioGroupHarga);


        mTextViewNamaAirportAsal = (TextView) findViewById(R.id.textViewNamaAirportAsal);
        mTextViewKodeNamaAirportAsal = (TextView) findViewById(R.id.textViewKodeNamaAirportAsal);
        mLinAirportAsal = (LinearLayout) findViewById(R.id.linAirportAsal);
        linMainSearch = (LinearLayout) findViewById(R.id.linMainSearch);

        mLinAirportAsal.setOnClickListener(this);
        mImageViewTukarAirport = (ImageView) findViewById(R.id.imageViewTukarAirport);
//        mImageViewTukarAirport.bringToFront();
//        mImageViewTukarAirport.invalidate();
       // linMainSearch.bringChildToFront(mImageViewTukarAirport);
        mImageViewTukarAirport.setOnClickListener(this);
        mTextViewNamaAirportTujuan = (TextView) findViewById(R.id.textViewNamaAirportTujuan);
        mTextViewKodeNamaAirportTujuan = (TextView) findViewById(R.id.textViewKodeNamaAirportTujuan);
        mLinAirportTujuan = (LinearLayout) findViewById(R.id.linAirportTujuan);
        mLinAirportTujuan.setOnClickListener(this);
        mRelImage = (RelativeLayout) findViewById(R.id.relImage);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pesawat");
        mToolbarCollapsing = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mTextViewTanggalBerangkat = (TextView) findViewById(R.id.textViewTanggalBerangkat);
//        mTextViewBulanBerangkat = (TextView) findViewById(R.id.textViewBulanBerangkat);
//        mTextViewHariTahunBerangkat = (TextView) findViewById(R.id.textViewHariTahunBerangkat);
        mLinTanggalBerangkat = (LinearLayout) findViewById(R.id.linTanggalBerangkat);
        mLinTanggalBerangkat.setOnClickListener(this);
        mTextViewJumlahDewasa = (TextView) findViewById(R.id.textViewJumlahDewasa);
        mTextViewJumlahAnak = (TextView) findViewById(R.id.textViewJumlahAnak);
        mTextViewJumlahBayi = (TextView) findViewById(R.id.textViewJumlahBayi);
        mLinMainJumlahPenumpang = (LinearLayout) findViewById(R.id.linMainJumlahPenumpang);
        linTampilkanHarga = (LinearLayout) findViewById(R.id.linTampilkanHarga);
        mLinMainJumlahPenumpang.setOnClickListener(this);
        mAppButtonCariPesawat = (AppCompatButton) findViewById(R.id.appButtonCariPesawat);
        mAppButtonCariPesawat.setOnClickListener(this);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        mImageViewTukarAirport.setTranslationX(10f);
        mImageViewTukarAirport.setTranslationY(10f);
//        mImageViewTukarAirport.bringToFront();
//        rootLayout.invalidate();
//        pickerAdultValue = pickerAdult.getValue();
//        pickerChildValue = pickerChild.getValue();
//        pickerInfantValue = pickerInfant.getValue();
        PreferenceClass.putInt(FlightKeyPreference.countAdultFlight, pickerAdultValue);
        PreferenceClass.putInt(FlightKeyPreference.countChildFlight, pickerChildValue);
        PreferenceClass.putInt(FlightKeyPreference.countInfantFlight, pickerInfantValue);
//        mTextViewJumlahDewasa.setText(String.valueOf(pickerAdult.getValue()));
//        mTextViewJumlahAnak.setText(String.valueOf(pickerChild.getValue()));
//        mTextViewJumlahBayi.setText(String.valueOf(pickerInfant.getValue()));
    }

    @Override
    public void onClick(@NonNull View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.linAirportAsal:
                // TODO 19/03/29
                //  requestCode = 1;
                intent = new Intent(FlightSearchActivity.this, FlightAirPortActivity.class);
                intent.putExtra("initAirport", "asal");
                startActivityForResult(intent, TravelActionCode.LIST_ORIGIN_AIRPORT);
                overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
                break;
            case R.id.imageViewTukarAirport:
                // TODO 19/03/29
                switchAirport(v);
                break;
            case R.id.linAirportTujuan:
                // TODO 19/03/29
                //requestCode = 2;
                intent = new Intent(FlightSearchActivity.this, FlightAirPortActivity.class);
                intent.putExtra("initAirport", "tujuan");
                startActivityForResult(intent, TravelActionCode.LIST_DESTINATION_AIRPORT);
                overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
                break;
            case R.id.linTanggalBerangkat:
                // TODO 19/03/29
                //  requestCode = 3;
                intent = new Intent(FlightSearchActivity.this, TravelTanggalActivity.class);
                intent.putExtra("initTanggal", "pergi");
                startActivityForResult(intent, TravelActionCode.LIST_TANGGAL_BERANGKAT);
                overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
                break;
            case R.id.linMainJumlahPenumpang:
                // TODO 19/03/29
                openBottomSheet(v);
                break;
            case R.id.appButtonCariPesawat:
                // TODO 19/03/29
                int selectedId = radioHargaGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioHargaButton = (RadioButton) findViewById(selectedId);

                PreferenceClass.putString(FlightKeyPreference.searchChoicePrice,radioHargaButton.getTag().toString());
                intent = new Intent(FlightSearchActivity.this, FlightScheduleActivity.class);
                intent.putExtra("cari", true);
                startActivityForResult(intent, TravelActionCode.IS_FROM_PAY);
                //startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rumah, menu);
        new Handler().post(new Runnable() {
            @Override
            public void run() {


                if (PreferenceClass.getInt(TAG, 0) != 1) {
                    showCaseFirst(FlightSearchActivity.this, "", getResources().getString(R.string.content_flight_airport_asal), mLinAirportAsal);

                    mGbuilder.setGuideListener(new GuideView.GuideListener() {
                        @Override
                        public void onDismiss(@NonNull View view) {
                            switch (view.getId()) {
                                case R.id.linAirportAsal:
                                    mGbuilder
                                            .setTitle("")
                                            .setContentText(getResources().getString(R.string.content_flight_tukar_airport)).setGravity(GuideView.Gravity.center)
                                            .setDismissType(GuideView.DismissType.anywhere)
                                            .setTargetView(mImageViewTukarAirport)
                                            .setCircleView(true)
                                            .build();
                                    break;
                                case R.id.imageViewTukarAirport:
                                    mGbuilder
                                            .setTitle("")
                                            .setContentText(getResources().getString(R.string.content_flight_airport_tujuan)).setGravity(GuideView.Gravity.center)
                                            .setDismissType(GuideView.DismissType.anywhere)
                                            .setTargetView(mLinAirportTujuan)
                                            .setCornerRadius(5)
                                            .setCircleView(false)
                                            .build();
                                    break;

                                case R.id.linAirportTujuan:
                                    mGbuilder
                                            .setTitle("")
                                            .setContentText(getResources().getString(R.string.content_flight_tanggal_berangkat)).setGravity(GuideView.Gravity.center)
                                            .setDismissType(GuideView.DismissType.anywhere)
                                            .setTargetView(mLinTanggalBerangkat)
                                            .setCornerRadius(10)
                                            .setCircleView(false)
                                            .build();
                                    break;

                                case R.id.linTanggalBerangkat:
                                    mGbuilder
                                            .setTitle("")
                                            .setContentText(getResources().getString(R.string.content_flight_jumlah_penumpang)).setGravity(GuideView.Gravity.center)
                                            .setDismissType(GuideView.DismissType.anywhere)
                                            .setTargetView(mLinMainJumlahPenumpang)
                                            .setCornerRadius(10)
                                            .setCircleView(false)
                                            .build();
                                    break;

//                                case R.id.linMainJumlahPenumpang:
//                                    mGbuilder
//                                            .setTitle("")
//                                            .setContentText(getResources().getString(R.string.content_flight_cari_pilih_harga)).setGravity(GuideView.Gravity.center)
//                                            .setDismissType(GuideView.DismissType.anywhere)
//                                            .setTargetView(linTampilkanHarga)
//                                            .setCornerRadius(10)
//                                            .setCircleView(false)
//                                            .build();
//                                    break;

                                case R.id.linMainJumlahPenumpang:
                                    PreferenceClass.putInt(TAG, 1);
                                    return;
                            }
                            mGuideView = mGbuilder.build();
                            mGuideView.show();

                        }
                    });
                    mGuideView = mGbuilder.build();
                    mGuideView.show();
                }
            }
        });
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

    @Override
    public void onBackPressed() {

//        if (isFromPay == true) {
//            //Intent intent;
////            try {
//            intent = new Intent(FlightSearchActivity.this, ToursTiketingActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
////            } catch (ClassNotFoundException e) {
////                e.printStackTrace();
////            }
//        }
        finish();
        //  overridePendingTransition(0,0);


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
                DialogUtils.closeBootomSheetDialog();
            }
        });
        btnPilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.closeBootomSheetDialog();
                pickerAdultValue = pickerAdult.getValue();
                pickerChildValue = pickerChild.getValue();
                pickerInfantValue = pickerInfant.getValue();
                PreferenceClass.putInt(FlightKeyPreference.countAdultFlight, pickerAdultValue);
                PreferenceClass.putInt(FlightKeyPreference.countChildFlight, pickerChildValue);
                PreferenceClass.putInt(FlightKeyPreference.countInfantFlight, pickerInfantValue);
                mTextViewJumlahDewasa.setText(String.valueOf(pickerAdult.getValue()));
                mTextViewJumlahAnak.setText(String.valueOf(pickerChild.getValue()));
                mTextViewJumlahBayi.setText(String.valueOf(pickerInfant.getValue()));
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
            }
        });

        DialogUtils.openBottomSheetDialog(this, view);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TravelActionCode.LIST_ORIGIN_AIRPORT && resultCode == Activity.RESULT_OK) {
            if (PreferenceClass.getString(FlightKeyPreference.airportKodeTujuan, "").equals(data.getStringExtra("airportKode"))) {
                snackBarCustomAction(findViewById(R.id.rootLayout), R.string.flight_content_same_airport, "", 1);
                mLinAirportAsal.setBackgroundResource(R.drawable.shape_card_error);
                mLinAirportAsal.setAnimation(animShake);
                mLinAirportAsal.startAnimation(animShake);
                Device.vibrate(FlightSearchActivity.this);
            } else {

                mTextViewNamaAirportAsal.setText(data.getStringExtra("airportNama"));
                mTextViewKodeNamaAirportAsal.setText(data.getStringExtra("airportKota"));
                origin = data.getStringExtra("airportKode");
                PreferenceClass.putString(FlightKeyPreference.airportNamaAsal, data.getStringExtra("airportNama"));
                PreferenceClass.putString(FlightKeyPreference.airportKotaAsal, data.getStringExtra("airportKota"));
                PreferenceClass.putString(FlightKeyPreference.airportKodeAsal, data.getStringExtra("airportKode"));
            }
        } else if (requestCode == TravelActionCode.LIST_DESTINATION_AIRPORT && resultCode == Activity.RESULT_OK) {
            if (PreferenceClass.getString(FlightKeyPreference.airportKodeAsal, "").equals(data.getStringExtra("airportKode"))) {
                snackBarCustomAction(findViewById(R.id.rootLayout), R.string.flight_content_same_airport, "", 1);
                mLinAirportTujuan.setBackgroundResource(R.drawable.shape_card_error);
                mLinAirportTujuan.setAnimation(animShake);
                mLinAirportTujuan.startAnimation(animShake);
                Device.vibrate(FlightSearchActivity.this);
            } else {

                mTextViewNamaAirportTujuan.setText(data.getStringExtra("airportNama"));
                mTextViewKodeNamaAirportTujuan.setText(data.getStringExtra("airportKota"));
                destination = data.getStringExtra("airportKode");
                PreferenceClass.putString(FlightKeyPreference.airportNamaTujuan, data.getStringExtra("airportNama"));
                PreferenceClass.putString(FlightKeyPreference.airportKotaTujuan, data.getStringExtra("airportKota"));
                PreferenceClass.putString(FlightKeyPreference.airportKodeTujuan, data.getStringExtra("airportKode"));
            }
        } else if (requestCode == TravelActionCode.LIST_TANGGAL_BERANGKAT && resultCode == Activity.RESULT_OK) {
            //textTanggalBerangkat.setText(data.getStringExtra("dateShow"));
            mTextViewTanggalBerangkat.setText(data.getStringExtra("dateShow"));
//            mTextViewBulanBerangkat.setText(data.getStringExtra("dateShowMonth"));
//            mTextViewHariTahunBerangkat.setText(data.getStringExtra("dateShowDayYear"));

            PreferenceClass.putString(FlightKeyPreference.departureDateFlight, data.getStringExtra("date"));
            PreferenceClass.putString(FlightKeyPreference.departureDateShowFlight, data.getStringExtra("dateShow"));
            depDate = data.getStringExtra("date");

            PreferenceClass.putString(FlightKeyPreference.returnDateFlight, data.getStringExtra("date"));
            PreferenceClass.putString(FlightKeyPreference.returnDateShowFlight, data.getStringExtra("dateShow"));
            returnDate = data.getStringExtra("date");
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
        // String destinationAirport = PreferenceClass.getString( "airportNamaTujuan","");


        String originAirport = mTextViewNamaAirportAsal.getText().toString();
        String originName = mTextViewKodeNamaAirportAsal.getText().toString();


        String destinationAirport = mTextViewNamaAirportTujuan.getText().toString();
        String destinationName = mTextViewKodeNamaAirportTujuan.getText().toString();

        mTextViewNamaAirportAsal.setText(destinationAirport);
        mTextViewKodeNamaAirportAsal.setText(destinationName);

        mTextViewNamaAirportTujuan.setText(originAirport);
        mTextViewKodeNamaAirportTujuan.setText(originName);


        origin = destinationCode;
        destination = originCode;

        PreferenceClass.putString(FlightKeyPreference.airportKodeAsal, destinationCode);
        PreferenceClass.putString(FlightKeyPreference.airportKotaAsal, destinationName);
        PreferenceClass.putString(FlightKeyPreference.airportNamaAsal, destinationAirport);


        PreferenceClass.putString(FlightKeyPreference.airportKodeTujuan, originCode);
        PreferenceClass.putString(FlightKeyPreference.airportKotaTujuan, originName);
        PreferenceClass.putString(FlightKeyPreference.airportNamaTujuan, originAirport);

//        }
    }

    public void turnOffToolbarScrolling() {



        //turn off scrolling
        AppBarLayout.LayoutParams toolbarLayoutParams = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        toolbarLayoutParams.setScrollFlags(0);
        toolbar.setLayoutParams(toolbarLayoutParams);

        CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
        appBarLayoutParams.setBehavior(null);
        mAppBarLayout.setLayoutParams(appBarLayoutParams);
    }

    public void turnOnToolbarScrolling() {

        //turn on scrolling
        AppBarLayout.LayoutParams toolbarLayoutParams = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        toolbarLayoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
        toolbar.setLayoutParams(toolbarLayoutParams);

        CoordinatorLayout.LayoutParams appBarLayoutParams = (CoordinatorLayout.LayoutParams) mAppBarLayout.getLayoutParams();
        appBarLayoutParams.setBehavior(new AppBarLayout.Behavior());
        mAppBarLayout.setLayoutParams(appBarLayoutParams);
    }

//    public void updateToolbarBehaviour(){
//        if (mLayoutManager.findLastCompletelyVisibleItemPosition() == items.size()-1) {
//           turnOffToolbarScrolling();
//        } else {
//            turnOnToolbarScrolling();
//        }
//    }



}

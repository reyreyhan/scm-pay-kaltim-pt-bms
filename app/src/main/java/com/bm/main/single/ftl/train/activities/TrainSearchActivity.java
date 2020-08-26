package com.bm.main.single.ftl.train.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.bm.main.fpl.utils.Device;
import com.bm.main.fpl.utils.DialogUtils;
import com.bm.main.scm.R;
import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.templates.MaterialNumberPicker;
import com.bm.main.fpl.templates.showcaseview.GuideView;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.single.ftl.activities.TravelTanggalActivity;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bm.main.single.ftl.train.constants.TrainKeyPreference;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.transition.Transition;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TrainSearchActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = TrainSearchActivity.class.getSimpleName();
    AppCompatButton btnPilih, btnBatal;
    Intent intent;
    boolean isFromPay = false;

    MaterialNumberPicker pickerAdult, pickerChild, pickerInfant;

    int pickerAdultValue = 1;
    int pickerChildValue = 0;
    int pickerInfantValue = 0;

    @Nullable
    String origin, destination;
    String depDate;
    String returnDate;

    private TextView mTextViewNamaStationAsal;
    private TextView mTextViewKodeNamaStationAsal;
    private LinearLayout mLinStationAsal;
    private ImageView mImageViewTukarStation;
    private TextView mTextViewNamaStationTujuan;
    private TextView mTextViewKodeNamaStationTujuan;
    private LinearLayout mLinStationTujuan;

    private TextView mTextViewTanggalBerangkat;
    //    private TextView mTextViewBulanBerangkat;
//    private TextView mTextViewHariTahunBerangkat;
    private LinearLayout mLinTanggalBerangkat;
    private TextView mTextViewJumlahDewasa;
    //    private TextView mTextViewJumlahAnak;
    private TextView mTextViewJumlahBayi;
    private LinearLayout mLinMainJumlahPenumpang;
    private AppCompatButton mAppButtonCariKereta;

    ImageView imageViewSearchTrain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_search_activity_edit);
        if (intent != null)
            isFromPay = intent.getBooleanExtra("isFromPay", true);
        initView();

        init(0);

//        mTextViewNamaStationAsal = findViewById(R.id.textViewNamaStationAsal);
//        mTextViewNamaStationTujuan = findViewById(R.id.textViewNamaStationTujuan);
//        mTextViewKodeNamaStationAsal = findViewById(R.id.textViewKodeNamaStationAsal);
//        mTextViewKodeNamaStationTujuan = findViewById(R.id.textViewKodeNamaStationTujuan);
//        mTextViewTanggalBerangkat = findViewById(R.id.textViewTanggalBerangkat);
//        mTextViewBulanBerangkat = findViewById(R.id.textViewBulanBerangkat);
//        mTextViewHariTahunBerangkat = findViewById(R.id.textViewHariTahunBerangkat);
//        mTextViewJumlahDewasa = findViewById(R.id.textViewJumlahDewasa);
//
//        mTextViewJumlahBayi = findViewById(R.id.textViewJumlahBayi);
    }

    private void initView() {
        //  mImageViewSearchTrain = (ImageView) findViewById(R.id.imageViewSearchTrain);
        imageViewSearchTrain = findViewById(R.id.imageViewSearchTrain);
        Glide.with(this).asBitmap().load(R.drawable.background_search_kereta).encodeFormat(Bitmap.CompressFormat.WEBP).encodeQuality(50).diskCacheStrategy(DiskCacheStrategy.NONE).into(new BitmapImageViewTarget(imageViewSearchTrain) {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> animation) {
                // here it's similar to RequestListener, but with less information (e.g. no model available)
                super.onResourceReady(resource, animation);
                //viewHolder.avi.setVisibility(View.GONE);
                imageViewSearchTrain.refreshDrawableState();
                // here you can be sure it's already set
            }

            // +++++ OR +++++
            @Override
            protected void setResource(Bitmap resource) {
                // this.getView().setImageDrawable(resource); is about to be called
                super.setResource(resource);
                // viewHolder.avi.setVisibility(View.GONE);
                imageViewSearchTrain.setScaleType(ImageView.ScaleType.FIT_XY);

                // here you can be sure it's already set
            }

            @Override
            public void onLoadFailed(Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
//                viewHolder.avi.setVisibility(View.GONE);
//                viewHolder.imageViewProduk.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.gameonline_default));
            }
        });
        mTextViewNamaStationAsal = (TextView) findViewById(R.id.textViewNamaStationAsal);
        mTextViewKodeNamaStationAsal = (TextView) findViewById(R.id.textViewKodeNamaStationAsal);
        mLinStationAsal = (LinearLayout) findViewById(R.id.linStationAsal);
        mLinStationAsal.setOnClickListener(this);
        mImageViewTukarStation = (ImageView) findViewById(R.id.imageViewTukarStation);
        mImageViewTukarStation.setOnClickListener(this);
        mTextViewNamaStationTujuan = (TextView) findViewById(R.id.textViewNamaStationTujuan);
        mTextViewKodeNamaStationTujuan = (TextView) findViewById(R.id.textViewKodeNamaStationTujuan);
        mLinStationTujuan = (LinearLayout) findViewById(R.id.linStationTujuan);
        mLinStationTujuan.setOnClickListener(this);
        // mRelImage = (RelativeLayout) findViewById(R.id.relImage);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Kereta");
//        mToolbarCollapsing = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mTextViewTanggalBerangkat = (TextView) findViewById(R.id.textViewTanggalBerangkat);
//        mTextViewBulanBerangkat = (TextView) findViewById(R.id.textViewBulanBerangkat);
//        mTextViewHariTahunBerangkat = (TextView) findViewById(R.id.textViewHariTahunBerangkat);
        mLinTanggalBerangkat = (LinearLayout) findViewById(R.id.linTanggalBerangkat);
        mLinTanggalBerangkat.setOnClickListener(this);
        mTextViewJumlahDewasa = (TextView) findViewById(R.id.textViewJumlahDewasa);
//        mTextViewJumlahAnak = (TextView) findViewById(R.id.textViewJumlahAnak);
        mTextViewJumlahBayi = (TextView) findViewById(R.id.textViewJumlahBayi);
        mLinMainJumlahPenumpang = (LinearLayout) findViewById(R.id.linMainJumlahPenumpang);
        mLinMainJumlahPenumpang.setOnClickListener(this);
        mAppButtonCariKereta = (AppCompatButton) findViewById(R.id.appButtonCariKereta);
        mAppButtonCariKereta.setOnClickListener(this);
//        mAppBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);


        String kotaAsal = PreferenceClass.getString(TrainKeyPreference.stationKotaAsal, "");
        String kodeAsal = PreferenceClass.getString(TrainKeyPreference.stationKodeAsal, "");
        String stationAsal = PreferenceClass.getString(TrainKeyPreference.stationNamaAsal, "");

        if (kotaAsal.equals("")) {
            kotaAsal = "Surabaya";
            kodeAsal = "SGU";
            stationAsal = "Surabaya Gubeng";
            PreferenceClass.putString(TrainKeyPreference.stationKotaAsal, kotaAsal);
            PreferenceClass.putString(TrainKeyPreference.stationKodeAsal, kodeAsal);
            PreferenceClass.putString(TrainKeyPreference.stationNamaAsal, stationAsal);

        }

        //Log.d(TAG, "onCreate: " + kotaAsal);
        origin = kodeAsal;
        mTextViewKodeNamaStationAsal.setText(kotaAsal);
        mTextViewNamaStationAsal.setText(stationAsal);

        String kotaTujuan = PreferenceClass.getString(TrainKeyPreference.stationKotaTujuan, "");
        String kodeTujuan = PreferenceClass.getString(TrainKeyPreference.stationKodeTujuan, "");
        String stationTujuan = PreferenceClass.getString(TrainKeyPreference.stationNamaTujuan, "");
        if (kotaTujuan.equals("")) {
            kotaTujuan = "Jakarta";
            kodeTujuan = "GMR";
            stationTujuan = "Gambir";
            PreferenceClass.putString(TrainKeyPreference.stationKotaTujuan, kotaTujuan);
            PreferenceClass.putString(TrainKeyPreference.stationKodeTujuan, kodeTujuan);
            PreferenceClass.putString(TrainKeyPreference.stationNamaTujuan, stationTujuan);

        }
        destination = kodeTujuan;
        mTextViewKodeNamaStationTujuan.setText(kotaTujuan);
        mTextViewNamaStationTujuan.setText(stationTujuan);

        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", config.locale);
        SimpleDateFormat formatterShow = new SimpleDateFormat("EEEE, dd MMMM yyyy", config.locale);
        SimpleDateFormat formatterShowDate = new SimpleDateFormat("dd", config.locale);
        SimpleDateFormat formatterShowMonth = new SimpleDateFormat("MMMM", config.locale);
        SimpleDateFormat formatterShowDayYear = new SimpleDateFormat("EEEE, yyyy", config.locale);
        String showDateNow = formatterShow.format(now);
        String showDateNowDate = formatterShowDate.format(now);
        String showDateNowMonth = formatterShowMonth.format(now);
        String showDateNowDayYear = formatterShowDayYear.format(now);

        String dateNow = formatter.format(now);
        mTextViewTanggalBerangkat.setText(showDateNow);
//        mTextViewBulanBerangkat.setText(showDateNowMonth);
//        mTextViewHariTahunBerangkat.setText(showDateNowDayYear);

        depDate = dateNow;
        returnDate = dateNow;

        PreferenceClass.putString(TrainKeyPreference.departureDateTrain, dateNow);
        PreferenceClass.putString(TrainKeyPreference.departureDateShowTrain, showDateNow);
        PreferenceClass.putString(TrainKeyPreference.returnDateTrain, dateNow);
        PreferenceClass.putString(TrainKeyPreference.returnDateShowTrain, showDateNow);

    }

    @Override
    public void onClick(@NonNull View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.linStationAsal:
                // TODO 19/03/29
                //  requestCode = 1;
                intent = new Intent(TrainSearchActivity.this, TrainStationActivity.class);
                intent.putExtra("initStation", "asal");
                startActivityForResult(intent, TravelActionCode.LIST_ORIGIN_STATION);
                overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
                break;
            case R.id.imageViewTukarStation:
                // TODO 19/03/29
                switchStation(v);
                break;
            case R.id.linStationTujuan:
                // TODO 19/03/29
                //requestCode = 2;
                intent = new Intent(TrainSearchActivity.this, TrainStationActivity.class);
                intent.putExtra("initStation", "tujuan");
                startActivityForResult(intent, TravelActionCode.LIST_DESTINATION_STATION);
                overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
                break;
            case R.id.linTanggalBerangkat:
                // TODO 19/03/29
                //  requestCode = 3;
                intent = new Intent(TrainSearchActivity.this, TravelTanggalActivity.class);
                intent.putExtra("initTanggal", "pergi");
                startActivityForResult(intent, TravelActionCode.LIST_TANGGAL_BERANGKAT);
                overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
                break;
            case R.id.linMainJumlahPenumpang:
                // TODO 19/03/29
                openBottomSheet(v);
                break;
            case R.id.appButtonCariKereta:
                // TODO 19/03/29
                PreferenceClass.putInt(TrainKeyPreference.countAdultTrain, pickerAdultValue);
                PreferenceClass.putInt(TrainKeyPreference.countChildTrain, pickerChildValue);
                PreferenceClass.putInt(TrainKeyPreference.countInfantTrain, pickerInfantValue);
                intent = new Intent(TrainSearchActivity.this, TrainScheduleActivity.class);
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
        new Handler().post(new Runnable() {
            @Override
            public void run() {


                if (PreferenceClass.getInt(TAG, 0) != 1) {
                    showCaseFirst(TrainSearchActivity.this, "", getResources().getString(R.string.content_train_station_asal), mLinStationAsal);

                    mGbuilder.setGuideListener(new GuideView.GuideListener() {
                        @Override
                        public void onDismiss(@NonNull View view) {
                            switch (view.getId()) {
                                case R.id.linStationAsal:
                                    mGbuilder
                                            .setTitle("")
                                            .setContentText(getResources().getString(R.string.content_train_tukar_station)).setGravity(GuideView.Gravity.center)
                                            .setDismissType(GuideView.DismissType.anywhere)
                                            .setTargetView(mImageViewTukarStation)
                                            .setCircleView(true)
                                            .build();
                                    break;
                                case R.id.imageViewTukarStation:
                                    mGbuilder
                                            .setTitle("")
                                            .setContentText(getResources().getString(R.string.content_train_station_tujuan)).setGravity(GuideView.Gravity.center)
                                            .setDismissType(GuideView.DismissType.anywhere)
                                            .setTargetView(mLinStationTujuan)
                                            .setCornerRadius(5)
                                            .setCircleView(false)
                                            .build();
                                    break;

                                case R.id.linStationTujuan:
                                    mGbuilder
                                            .setTitle("")
                                            .setContentText(getResources().getString(R.string.content_train_tanggal_berangkat)).setGravity(GuideView.Gravity.center)
                                            .setDismissType(GuideView.DismissType.anywhere)
                                            .setTargetView(mLinTanggalBerangkat)
                                            .setCornerRadius(10)
                                            .setCircleView(false)
                                            .build();
                                    break;

                                case R.id.linTanggalBerangkat:
                                    mGbuilder
                                            .setTitle("")
                                            .setContentText(getResources().getString(R.string.content_train_jumlah_penumpang)).setGravity(GuideView.Gravity.center)
                                            .setDismissType(GuideView.DismissType.anywhere)
                                            .setTargetView(mLinMainJumlahPenumpang)
                                            .setCornerRadius(10)
                                            .setCircleView(false)
                                            .build();
                                    break;

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

        } else if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        finish();


    }

    public void openBottomSheet(View v) {

        final FrameLayout view = (FrameLayout) View.inflate(this, R.layout.travel_two_passager, null);
        pickerAdult = view.findViewById(R.id.jumlahDewasa);
        // pickerChild = view.findViewById(R.id.jumlahAnak);
        pickerInfant = view.findViewById(R.id.jumlahBayi);
        pickerAdult.setValue(pickerAdultValue);
        // pickerChild.setValue(pickerChildValue);
        pickerInfant.setValue(pickerInfantValue);
        pickerInfant.setMaxValue(pickerAdultValue);
//        if(pickerAdultValue>1) {
//            pickerInfant.setMaxValue(4-pickerAdultValue);
//        }


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
                pickerInfantValue = pickerInfant.getValue();

                mTextViewJumlahDewasa.setText(String.valueOf(pickerAdult.getValue()));
                mTextViewJumlahBayi.setText(String.valueOf(pickerInfant.getValue()));
            }
        });

        pickerAdult.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                pickerInfant.setMaxValue(newVal);
            }
        });

        DialogUtils.openBottomSheetDialog(this, view);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: " + requestCode + " " + resultCode + " " + data + " ");
        if (requestCode == TravelActionCode.LIST_ORIGIN_STATION && resultCode == Activity.RESULT_OK) {
            if (PreferenceClass.getString(TrainKeyPreference.stationKodeTujuan, "").equals(data.getStringExtra("stationKode"))) {
                snackBarCustomAction(findViewById(R.id.rootLayout), R.string.train_content_same_station, "", 1);
                mLinStationAsal.setBackgroundResource(R.drawable.shape_card_error);
                mLinStationAsal.setAnimation(animShake);
                mLinStationAsal.startAnimation(animShake);
                Device.vibrate(this);
            } else {
                origin = data.getStringExtra("stationKode");
                mTextViewNamaStationAsal.setText(data.getStringExtra("stationNama"));
                mTextViewKodeNamaStationAsal.setText(data.getStringExtra("stationKota"));

                PreferenceClass.putString(TrainKeyPreference.stationNamaAsal, data.getStringExtra("stationNama"));
                PreferenceClass.putString(TrainKeyPreference.stationKotaAsal, data.getStringExtra("stationKota"));
                PreferenceClass.putString(TrainKeyPreference.stationKodeAsal, data.getStringExtra("stationKode"));
            }
        } else if (requestCode == TravelActionCode.LIST_DESTINATION_STATION && resultCode == Activity.RESULT_OK) {
            if (PreferenceClass.getString(TrainKeyPreference.stationKodeAsal, "").equals(data.getStringExtra("stationKode"))) {
                snackBarCustomAction(findViewById(R.id.rootLayout), R.string.train_content_same_station, "", 1);
                mLinStationTujuan.setBackgroundResource(R.drawable.shape_card_error);
                mLinStationTujuan.setAnimation(animShake);
                mLinStationTujuan.startAnimation(animShake);
                Device.vibrate(this);
            } else {
                destination = data.getStringExtra("stationKode");
                mTextViewNamaStationTujuan.setText(data.getStringExtra("stationNama"));
                mTextViewKodeNamaStationTujuan.setText(data.getStringExtra("stationKota"));

                PreferenceClass.putString(TrainKeyPreference.stationNamaTujuan, data.getStringExtra("stationNama"));
                PreferenceClass.putString(TrainKeyPreference.stationKotaTujuan, data.getStringExtra("stationKota"));
                PreferenceClass.putString(TrainKeyPreference.stationKodeTujuan, data.getStringExtra("stationKode"));
            }
        } else if (requestCode == TravelActionCode.LIST_TANGGAL_BERANGKAT && resultCode == Activity.RESULT_OK) {
            mTextViewTanggalBerangkat.setText(data.getStringExtra("dateShow"));
            PreferenceClass.putString(TrainKeyPreference.departureDateTrain, data.getStringExtra("date"));
            PreferenceClass.putString(TrainKeyPreference.departureDateShowTrain, data.getStringExtra("dateShow"));
            depDate = data.getStringExtra("date");

            PreferenceClass.putString(TrainKeyPreference.returnDateTrain, data.getStringExtra("date"));
            PreferenceClass.putString(TrainKeyPreference.returnDateShowTrain, data.getStringExtra("dateShow"));
            returnDate = data.getStringExtra("date");
        } else if (requestCode == TravelActionCode.IS_FROM_PAY && resultCode == Activity.RESULT_OK) {
            if (data != null && data.getAction() != null) {
                Log.d(TAG, "onActivityResult: " + data.getAction());
                Intent intent = new Intent();
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

        }
    }

    public void switchStation(View v) {
//        if (!textKotaAsal.getText().toString().equals("") || !textKotaTujuan.getText().toString().equals("")) {
//            PreferenceClass.putString( "stationKotaTujuan", kotaTujuan);
//            PreferenceClass.putString("stationKodeTujuan", kodeTujuan);
//            PreferenceClass.putString("stationNamaTujuan", bandaraTujuan);
//

        String originCode = PreferenceClass.getString(TrainKeyPreference.stationKodeAsal, "");
        // String originStation = PreferenceClass.getString( "stationNamaAsal","");

        String destinationCode = PreferenceClass.getString(TrainKeyPreference.stationKodeTujuan, "");
        // String destinationStation = PreferenceClass.getString( "stationNamaTujuan","");


        String originStation = mTextViewNamaStationAsal.getText().toString();
        String originName = mTextViewKodeNamaStationAsal.getText().toString();


        String destinationStation = mTextViewNamaStationTujuan.getText().toString();
        String destinationName = mTextViewKodeNamaStationTujuan.getText().toString();

        mTextViewNamaStationAsal.setText(destinationStation);
        mTextViewKodeNamaStationAsal.setText(destinationName);

        mTextViewNamaStationTujuan.setText(originStation);
        mTextViewKodeNamaStationTujuan.setText(originName);


        origin = destinationCode;
        destination = originCode;

        PreferenceClass.putString(TrainKeyPreference.stationKodeAsal, destinationCode);
        PreferenceClass.putString(TrainKeyPreference.stationKotaAsal, destinationName);
        PreferenceClass.putString(TrainKeyPreference.stationNamaAsal, destinationStation);


        PreferenceClass.putString(TrainKeyPreference.stationKodeTujuan, originCode);
        PreferenceClass.putString(TrainKeyPreference.stationKotaTujuan, originName);
        PreferenceClass.putString(TrainKeyPreference.stationNamaTujuan, originStation);

//        }
    }

}

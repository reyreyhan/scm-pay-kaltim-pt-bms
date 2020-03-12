package com.bm.main.single.ftl.train.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;

import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.constants.EventParam;
import com.bm.main.fpl.constants.ProdukGroup;
import com.bm.main.fpl.templates.MaterialNumberPicker;
import com.bm.main.fpl.templates.showcaseview.GuideView;
import com.bm.main.fpl.templates.switchbutton.SwitchButton;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.materialedittext.MaterialEditText;
import com.bm.main.pos.R;
import com.bm.main.pos.SBFApplication;
import com.bm.main.single.ftl.activities.TravelTanggalActivity;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bm.main.single.ftl.train.constants.TrainKeyPreference;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.SimpleDateFormat;

import static com.bm.main.fpl.utils.DialogUtils.closeBootomSheetDialog;
import static com.bm.main.fpl.utils.DialogUtils.openBottomSheetDialog;

public class TrainSearchRevActivity extends BaseActivity implements  View.OnClickListener {
    private static final String TAG = TrainSearchRevActivity.class.getSimpleName();
    // public Toolbar toolbar;
    //  TopSheetDialog dialog;
    BottomSheetDialog bottomSheetDialog;
    SwitchButton switchPP;
    RelativeLayout relTanggalBerangkat, linPenumpang, linKotaAsal, linKotaTujuan;
    LinearLayout linTanggal;
    private Animation animShow, animHide, animScale;
    ImageView btnExchange, imageKotaAsal, imageKotaTujuan,imageViewTanggalBerangkat;
    MaterialEditText textKotaAsal, textKotaTujuan, textTanggalBerangkat, textJumlahPenumpang;
    // MaterialNumberPicker.Builder numberPickerBuilder;
    MaterialNumberPicker pickerAdult,  pickerInfant;
    AppCompatButton btnPilih, btnBatal;
    AppCompatButton btnCari;
    FrameLayout toastContainer;
    int pickerAdultValue = 1;
    int pickerChildValue = 0;
    int pickerInfantValue = 0;
    public String origin, destination, depDate, returnDate;
    boolean pp;
    CoordinatorLayout coordinatorLayout;
    View.OnClickListener mOnClickListener;
//    Toast customToast;


//    ArrayList<TrainStationModel> airPort;
//    Static_Response staticResponse;
//    JSONArray configFlight;
    Context context;
    Intent intent;
    boolean isFromPay = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_search_rev_activity);
        overridePendingTransition(0, 0);
        logEventFireBase(ProdukGroup.KERETA,ProdukGroup.KERETA, EventParam.EVENT_ACTION_VISIT,EventParam.EVENT_SUCCESS,TAG);

        intent=getIntent();
        if (intent != null)
            isFromPay = intent.getBooleanExtra("isFromPay", true);
        initView();
        // Static_Response staticResponse = new Static_Response(this);
       
        init(0);



    
    }
    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Cari Tiket Kereta");

        btnExchange = findViewById(R.id.btnExchange);
        btnExchange.setOnClickListener(this);
        linTanggal = findViewById(R.id.linTanggal);
        relTanggalBerangkat = findViewById(R.id.relTanggalBerangkat);
        relTanggalBerangkat.setOnClickListener(this);
        imageViewTanggalBerangkat= findViewById(R.id.imageViewTanggalBerangkat);
        imageViewTanggalBerangkat.setOnClickListener(this);

        linKotaAsal = findViewById(R.id.linPenumpang);
        linKotaTujuan = findViewById(R.id.linKotaAsal);
        linPenumpang = findViewById(R.id.linKotaTujuan);
        linPenumpang.setOnClickListener(this);
        imageKotaAsal = findViewById(R.id.imageKotaAsal);
        imageKotaAsal.setOnClickListener(this);
        imageKotaTujuan = findViewById(R.id.imageKotaTujuan);
        imageKotaTujuan.setOnClickListener(this);
        textKotaAsal = findViewById(R.id.textKotaAsal);
        textKotaAsal.setOnClickListener(this);
        textKotaTujuan = findViewById(R.id.textKotaTujuan);
        textKotaTujuan.setOnClickListener(this);
        textTanggalBerangkat = findViewById(R.id.textTanggalBerangkat);
        textTanggalBerangkat.setOnClickListener(this);
        textJumlahPenumpang = findViewById(R.id.textJumlahPenumpang);
        textJumlahPenumpang.setOnClickListener(this);

        btnCari = findViewById(R.id.buttonCari_Train);
        btnCari.setOnClickListener(this);

        String kotaAsal = PreferenceClass.getString(TrainKeyPreference.stationKotaAsal, "");
        String kodeAsal = PreferenceClass.getString(TrainKeyPreference.stationKodeAsal, "");
        String stationAsal = PreferenceClass.getString(TrainKeyPreference.stationNamaAsal, "");

        if (kotaAsal.equals("")) {
            kotaAsal = "Surabaya Gubeng(SGU)";
            kodeAsal = "SGU";
            stationAsal = "Surabaya Gubeng";
            PreferenceClass.putString(TrainKeyPreference.stationKotaAsal, kotaAsal);
            PreferenceClass.putString(TrainKeyPreference.stationKodeAsal, kodeAsal);
            PreferenceClass.putString(TrainKeyPreference.stationNamaAsal, stationAsal);

        }

        //Log.d(TAG, "onCreate: " + kotaAsal);
        origin = kodeAsal;
        textKotaAsal.setText(kotaAsal);
        // mTextViewNamaStationAsal.setText(stationAsal);

        String kotaTujuan = PreferenceClass.getString(TrainKeyPreference.stationKotaTujuan, "");
        String kodeTujuan = PreferenceClass.getString(TrainKeyPreference.stationKodeTujuan, "");
        String stationTujuan = PreferenceClass.getString(TrainKeyPreference.stationNamaTujuan, "");
        if (kotaTujuan.equals("")) {
            kotaTujuan = "Gambir (GMR)";
            kodeTujuan = "GMR";
            stationTujuan = "Gambir";
            PreferenceClass.putString(TrainKeyPreference.stationKotaTujuan, kotaTujuan);
            PreferenceClass.putString(TrainKeyPreference.stationKodeTujuan, kodeTujuan);
            PreferenceClass.putString(TrainKeyPreference.stationNamaTujuan, stationTujuan);

        }
        destination = kodeTujuan;
        textKotaTujuan.setText(kotaTujuan);
        // mTextViewNamaStationTujuan.setText(stationTujuan);

        LocalDate currentDate = LocalDate.now();
////        Date now = new Date();
        LocalDate max = LocalDate.of(currentDate.getYear(),currentDate.getMonthValue(),currentDate.getDayOfMonth()).plusDays(1);
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", config.locale);
//        SimpleDateFormat formatterShow = new SimpleDateFormat("EEEE, dd MMMM yyyy", config.locale);
        SimpleDateFormat formatterShowDate = new SimpleDateFormat("dd", SBFApplication.config.locale);
        SimpleDateFormat formatterShowMonth = new SimpleDateFormat("MMMM", SBFApplication.config.locale);
        SimpleDateFormat formatterShowDayYear = new SimpleDateFormat("EEEE, yyyy", SBFApplication.config.locale);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd",SBFApplication.config.locale);
        DateTimeFormatter formatterShow = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy",SBFApplication.config.locale);

        String showDateNow = formatterShow.format(max);
//        String showDateNowDate = formatterShowDate.format(now);
//        String showDateNowMonth = formatterShowMonth.format(now);
//        String showDateNowDayYear = formatterShowDayYear.format(now);

        String dateNow = formatter.format(max);
        textTanggalBerangkat.setText(showDateNow);
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
    protected void onResume() {
        super.onResume();
//        final Thread workerThread3 =new Thread() {
//
//            @Override
//
//            public void run() {
//
//                RequestStation();
//            }
//        };
//        workerThread3.start();

    }



    @Override
    public void onClick(@NonNull View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.textKotaAsal:
                // TODO 19/03/29
                //  requestCode = 1;
                intent = new Intent(TrainSearchRevActivity.this, TrainStationActivity.class);
                intent.putExtra("initStation", "asal");
                startActivityForResult(intent, TravelActionCode.LIST_ORIGIN_STATION);
                overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
                break;
            case R.id.btnExchange:
                // TODO 19/03/29
                switchStation(v);
                break;
            case R.id.textKotaTujuan:
                // TODO 19/03/29
                //requestCode = 2;
                intent = new Intent(TrainSearchRevActivity.this, TrainStationActivity.class);
                intent.putExtra("initStation", "tujuan");
                startActivityForResult(intent, TravelActionCode.LIST_DESTINATION_STATION);
                overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
                break;
            case R.id.textTanggalBerangkat:
                // TODO 19/03/29
                //  requestCode = 3;
                intent = new Intent(TrainSearchRevActivity.this, TravelTanggalActivity.class);
                intent.putExtra("initTanggal", "pergi");
                intent.putExtra("initValue",  PreferenceClass.getString(TrainKeyPreference.departureDateTrain, ""));
                startActivityForResult(intent, TravelActionCode.LIST_TANGGAL_BERANGKAT);
                overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);
                break;
            case R.id.textJumlahPenumpang:
                // TODO 19/03/29
                openBottomSheet(v);
                break;
            case R.id.buttonCari_Train:
                // TODO 19/03/29
                PreferenceClass.putString(TrainKeyPreference.departureDateTrain, depDate);
        PreferenceClass.putString(TrainKeyPreference.departureDateShowTrain, String.valueOf(textTanggalBerangkat.getText()));
        PreferenceClass.putString(TrainKeyPreference.returnDateTrain, depDate);
        PreferenceClass.putString(TrainKeyPreference.returnDateShowTrain, String.valueOf(textTanggalBerangkat.getText()));

                PreferenceClass.putInt(TrainKeyPreference.countAdultTrain, pickerAdultValue);
                PreferenceClass.putInt(TrainKeyPreference.countChildTrain, pickerChildValue);
                PreferenceClass.putInt(TrainKeyPreference.countInfantTrain, pickerInfantValue);
                intent = new Intent(TrainSearchRevActivity.this, TrainScheduleActivity.class);
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
//                    showCaseFirst(TrainSearchRevActivity.this, "", getResources().getString(R.string.content_train_station_asal), textKotaAsal);
//
//                    mGbuilder.setGuideListener(new GuideView.GuideListener() {
//                        @Override
//                        public void onDismiss(@NonNull View view) {
//                            switch (view.getId()) {
//                                case R.id.textKotaAsal:
//                                    mGbuilder
//                                            .setTitle("")
//                                            .setContentText(getResources().getString(R.string.content_train_tukar_station)).setGravity(GuideView.Gravity.center)
//                                            .setDismissType(GuideView.DismissType.anywhere)
//                                            .setTargetView(btnExchange)
//                                            .setCircleView(true)
//                                            .build();
//                                    break;
//                                case R.id.btnExchange:
//                                    mGbuilder
//                                            .setTitle("")
//                                            .setContentText(getResources().getString(R.string.content_train_station_tujuan)).setGravity(GuideView.Gravity.center)
//                                            .setDismissType(GuideView.DismissType.anywhere)
//                                            .setTargetView(textKotaTujuan)
//                                            .setCornerRadius(5)
//                                            .setCircleView(false)
//                                            .build();
//                                    break;
//
//                                case R.id.textKotaTujuan:
//                                    mGbuilder
//                                            .setTitle("")
//                                            .setContentText(getResources().getString(R.string.content_train_tanggal_berangkat)).setGravity(GuideView.Gravity.center)
//                                            .setDismissType(GuideView.DismissType.anywhere)
//                                            .setTargetView(textTanggalBerangkat)
//                                            .setCornerRadius(10)
//                                            .setCircleView(false)
//                                            .build();
//                                    break;
//
//                                case R.id.textTanggalBerangkat:
//                                    mGbuilder
//                                            .setTitle("")
//                                            .setContentText(getResources().getString(R.string.content_train_jumlah_penumpang)).setGravity(GuideView.Gravity.center)
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


    }

    public void openBottomSheet(View v) {
        final FrameLayout view = (FrameLayout) View.inflate(this,R.layout.travel_two_passager, null);
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
                closeBootomSheetDialog();
            }
        });
        btnPilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeBootomSheetDialog();
                pickerAdultValue = pickerAdult.getValue();
                //  pickerChildValue = pickerChild.getValue();
                pickerInfantValue = pickerInfant.getValue();

               // mTextViewJumlahDewasa.setText(String.valueOf(pickerAdult.getValue()));
                //  mTextViewJumlahAnak.setText(String.valueOf(pickerChild.getValue()));
                //mTextViewJumlahBayi.setText(String.valueOf(pickerInfant.getValue()));
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
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                //Log.d(TAG, "onScrollStateChange: " + oldVal + " " + newVal);
//                if (pickerInfant.getValue() > newVal) {
//                    snackBarCustomAction(view, R.string.penumpang_bayi_picker, String.valueOf(R.string.action_tutup), ALERT);
//                    pickerInfant.setValue(newVal);
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
//                    snackBarCustomAction(view, R.string.penumpang_bayi_picker,  String.valueOf(R.string.action_tutup), ALERT);
//
////                    pickerInfant.setValue(oldVal);
//                    pickerInfant.setValue(pickerAdult.getValue());
//
//
//                }
//            }
//        });

        openBottomSheetDialog(this, view);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: " +requestCode+" "+resultCode+" "+ data+" ");
        if (requestCode == TravelActionCode.LIST_ORIGIN_STATION && resultCode == Activity.RESULT_OK) {
            if (PreferenceClass.getString(TrainKeyPreference.stationKodeTujuan, "").equals(data.getStringExtra("stationKode"))) {
                snackBarCustomAction(findViewById(R.id.rootLayout), R.string.train_content_same_station, "", 1);
                textKotaAsal.setBackgroundResource(R.drawable.shape_card_error);
                textKotaAsal.setAnimation(animShake);
                textKotaAsal.startAnimation(animShake);
                getar();
            } else {
                origin = data.getStringExtra("stationKode");
                textKotaAsal.setText(data.getStringExtra("stationNama")+" ("+data.getStringExtra("stationKode")+")");
               // mTextViewKodeNamaStationAsal.setText(data.getStringExtra("stationKota"));

                PreferenceClass.putString(TrainKeyPreference.stationNamaAsal, data.getStringExtra("stationNama"));
                PreferenceClass.putString(TrainKeyPreference.stationKotaAsal, data.getStringExtra("stationKota"));
                PreferenceClass.putString(TrainKeyPreference.stationKodeAsal, data.getStringExtra("stationKode"));
            }
        } else if (requestCode == TravelActionCode.LIST_DESTINATION_STATION && resultCode == Activity.RESULT_OK) {
            if (PreferenceClass.getString(TrainKeyPreference.stationKodeAsal, "").equals(data.getStringExtra("stationKode"))) {
                snackBarCustomAction(findViewById(R.id.rootLayout), R.string.train_content_same_station, "", 1);
                textKotaTujuan.setBackgroundResource(R.drawable.shape_card_error);
                textKotaTujuan.setAnimation(animShake);
                textKotaTujuan.startAnimation(animShake);
                getar();
            } else {
                destination = data.getStringExtra("stationKode");
                textKotaTujuan.setText(data.getStringExtra("stationNama")+" ("+data.getStringExtra("stationKode")+")" );
               // mTextViewKodeNamaStationTujuan.setText(data.getStringExtra("stationKota"));

                PreferenceClass.putString(TrainKeyPreference.stationNamaTujuan, data.getStringExtra("stationNama"));
                PreferenceClass.putString(TrainKeyPreference.stationKotaTujuan, data.getStringExtra("stationKota"));
                PreferenceClass.putString(TrainKeyPreference.stationKodeTujuan, data.getStringExtra("stationKode"));
            }
        } else if (requestCode == TravelActionCode.LIST_TANGGAL_BERANGKAT && resultCode == Activity.RESULT_OK) {
            //textTanggalBerangkat.setText(data.getStringExtra("dateShow"));
            textTanggalBerangkat.setText(data.getStringExtra("dateShow"));
//            mTextViewBulanBerangkat.setText(data.getStringExtra("dateShowMonth"));
//            mTextViewHariTahunBerangkat.setText(data.getStringExtra("dateShowDayYear"));


            depDate = data.getStringExtra("date");
            returnDate = data.getStringExtra("date");
            PreferenceClass.putString(TrainKeyPreference.departureDateTrain, data.getStringExtra("date"));
            PreferenceClass.putString(TrainKeyPreference.departureDateShowTrain, data.getStringExtra("dateShow"));
            PreferenceClass.putString(TrainKeyPreference.returnDateTrain, data.getStringExtra("date"));
            PreferenceClass.putString(TrainKeyPreference.returnDateShowTrain, data.getStringExtra("dateShow"));

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


        String originStation = textKotaAsal.getText().toString();
        String originName =PreferenceClass.getString(TrainKeyPreference.stationKotaAsal,"");


        String destinationStation = textKotaTujuan.getText().toString();
        String destinationName =PreferenceClass.getString(TrainKeyPreference.stationKotaTujuan,"");

        textKotaAsal.setText(destinationStation);
       // mTextViewKodeNamaStationAsal.setText(destinationName);

        textKotaTujuan.setText(originStation);
        //mTextViewKodeNamaStationTujuan.setText(originName);


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

package com.bm.main.single.ftl.flight.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;



import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.pos.R;
import com.bm.main.pos.SBFApplication;
import com.bm.main.single.ftl.flight.constants.FlightKeyPreference;
import com.bm.main.single.ftl.flight.models.FlightDataModelClasses;
import com.bm.main.single.ftl.utils.MemoryStore;
import com.bm.main.single.ftl.utils.utilBand;

import com.bm.main.fpl.templates.rangeseekbar.RangeSeekBar;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class FlightFilterActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = FlightFilterActivity.class.getSimpleName();
    public TextView textHeaderFilter;
    private RangeSeekBar seekBarPrice;
    private TextView textViewLowPrice;
    private TextView textViewHighPrice;
    private TextView textViewSeekBarLowPrice;
    private TextView textViewSeekBarHighPrice;
    private int lowPrice;
    private int highPrice;
    private Number lowPriceLabel;
    private Number highPriceLabel;
    private Number lowPriceReset;
    private Number highPriceReset;

    private AppCompatButton btn_pilih;
    private CheckBox checkJam1, checkJam2, checkJam3, checkJam4;
    private boolean bCheckJam1 = false, bCheckJam2 = false, bCheckJam3 = false, bCheckJam4 = false;

    private Calendar fromTime1, fromTime2, fromTime3, fromTime4;
    private Calendar toTime1, toTime2, toTime3, toTime4;
    private Calendar currentTime;

    boolean isFilteredPrice = false;
    boolean isFilteredDateDept = false;
    public boolean isFilteredAirline = false;
    private int lowPriceOri;
    private int highPriceOri;
    private int countPrice = 0;
    @NonNull
    ArrayList<FlightDataModelClasses> flightModels = new ArrayList<>();
    private CardView cardViewMaskapai;
    public TextView textViewHasilFilterAirlines;
    public String formatStringFilter;
    boolean isFilter;
    private ImageView imageViewMaskapai;
    private Context context;

    FlightScheduleActivity scheduleFlightActivity;

    FlightAirlinesFilterActivity flightAirlinesFilterActivity;

    public static FlightFilterActivity flightFilterActivityInstance;

    public static synchronized FlightFilterActivity getInstanceFilter() {
        return flightFilterActivityInstance;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_filter_activity);
        context = this;
        flightFilterActivityInstance = this;
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Filter");
        init(1);
        Intent intent = getIntent();
        if (intent != null) {
            isFilter = intent.getBooleanExtra("isFilter", false);
//            if(isFilter==false){
//                reset();
//            }
        }
        textHeaderFilter = findViewById(R.id.textHeaderFilter);
        btn_pilih = findViewById(R.id.btn_pilih);
        btn_pilih.setOnClickListener(this);
        textViewHasilFilterAirlines = findViewById(R.id.textViewHasilFilterAirlines);
        imageViewMaskapai = findViewById(R.id.imageViewMaskapai);
        textViewLowPrice = findViewById(R.id.textViewLowPrice);
        textViewHighPrice = findViewById(R.id.textViewHighPrice);
        textViewSeekBarLowPrice = findViewById(R.id.textViewSeekBarLowPrice);
        textViewSeekBarHighPrice = findViewById(R.id.textViewSeekBarHighPrice);
        seekBarPrice = findViewById(R.id.seekBarPrice);
        checkJam1 = findViewById(R.id.checkboxJam1);
        cardViewMaskapai = findViewById(R.id.cardViewMaskapai);
        cardViewMaskapai.setOnClickListener(this);
        checkJam1.setOnClickListener(this);
        checkJam2 = findViewById(R.id.checkboxJam2);
        checkJam2.setOnClickListener(this);
        checkJam3 = findViewById(R.id.checkboxJam3);
        checkJam3.setOnClickListener(this);
        checkJam4 = findViewById(R.id.checkboxJam4);
        checkJam4.setOnClickListener(this);


        scheduleFlightActivity = FlightScheduleActivity.getInstanceFlightSchedule();
        flightAirlinesFilterActivity = FlightAirlinesFilterActivity.getIntanceAirlineFilter();

        flightModels = (ArrayList<FlightDataModelClasses>) MemoryStore.get("schedule_list_one_way");
        // JSONArray flightScheduleJson = PreferenceClass.getJSONArray("data");


        lowPrice = 0;
        highPrice = 0;
        if (flightModels.get(0).getPriceTemp() != 999999999) {
            lowPrice = flightModels.get(0).getPriceTemp();
            lowPriceLabel = flightModels.get(0).getPriceTemp();
            lowPriceReset = flightModels.get(0).getPriceTemp();
            lowPriceOri = flightModels.get(0).getPriceTemp();
        }

        for (int i = 0; i < flightModels.size(); i++) {
            Log.d(TAG, "onCreate: " + flightModels.get(i).getPriceTemp());
            highPriceOri = flightModels.get(i).getPriceTemp();
            if (flightModels.get(i).getPriceTemp() != 999999999) {
                highPriceLabel = flightModels.get(i).getPriceTemp();
                highPrice = flightModels.get(i).getPriceTemp();
                highPriceReset = flightModels.get(i).getPriceTemp();
            }

        }


        if (isFilter) {

            checkJam1.setChecked(PreferenceClass.getBoolean("checkJam1", false));
            checkJam2.setChecked(PreferenceClass.getBoolean("checkJam2", false));
            checkJam3.setChecked(PreferenceClass.getBoolean("checkJam3", false));
            checkJam4.setChecked(PreferenceClass.getBoolean("checkJam4", false));

            lowPrice = PreferenceClass.getInt("lowPrice", 0);
            highPrice = PreferenceClass.getInt("highPrice", 0);
         //   if (highPriceLabel.intValue() != highPriceOri) {
            if (((lowPriceLabel.intValue() == (int) lowPrice) && (highPriceLabel.intValue() == (int) highPrice))) {
                isFilteredPrice = true;
            }else{
                isFilteredPrice = false;
            }
            textViewHasilFilterAirlines.setText(PreferenceClass.getString(FlightKeyPreference.hasilFilterAirlines, ""));
            // }
            if (PreferenceClass.getString(FlightKeyPreference.hasilFilterAirlines, "").equals("Bebas")) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                imageViewMaskapai.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_flight_free));
//                } else {
//                    imageViewMaskapai.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.ic_flight_free));
//                }
                isFilteredAirline = false;
            } else {
//

                imageViewMaskapai.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_flight_choice));
                isFilteredAirline = true;
                codeAirline = (ArrayList<String>) MemoryStore.get(FlightKeyPreference.resultFilterAirlinesCode);
                namaAirline = (ArrayList<String>) MemoryStore.get(FlightKeyPreference.resultFilterAirlinesName);
            }
        } else {

//                imageViewMaskapai.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_flight_free));
//            PreferenceClass.putString(FlightKeyPreference.hasilFilterAirlines, "Bebas");
//            textViewHasilFilterAirlines.setText("Bebas");
//            isFilteredAirline = false;
//            PreferenceClass.putBoolean(FlightKeyPreference.isFilteredAirline, false);
//           // textViewHasilFilterAirlines.setText(PreferenceClass.getString(FlightKeyPreference.hasilFilterAirlines,""));
//            checkJam1.setChecked(bCheckJam1);
//            checkJam2.setChecked(bCheckJam2);
//            checkJam3.setChecked(bCheckJam3);
//            checkJam4.setChecked(bCheckJam4);
            reset();
        }


        //flightDataModelClasses = new FlightDataModelClasses();


        countPrice = flightModels.size();

        Log.d(TAG, "run: min price " + lowPrice);
        Log.d(TAG, "run: max price " + highPrice);

        textViewSeekBarLowPrice.setText(utilBand.formatRupiah(lowPriceLabel.intValue()).replace(",00", ""));
        textViewSeekBarHighPrice.setText(utilBand.formatRupiah(highPriceLabel.intValue()).replace(",00", ""));
        seekBarPrice.setRangeValues(lowPriceLabel, highPriceLabel);
        seekBarPrice.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Number minValue, Number maxValue) {
                textViewLowPrice.setText(utilBand.formatRupiah((int) minValue).replace(",00", ""));
                textViewHighPrice.setText(utilBand.formatRupiah((int) maxValue).replace(",00", ""));

//                isFilteredPrice = !((lowPrice == (int) minValue) && (highPrice == (int) maxValue));
                if (((lowPriceLabel.intValue() == (int) minValue) && (highPriceLabel.intValue() == (int) maxValue))) {
                    isFilteredPrice = false;
                    lowPrice = (int) minValue;
                    highPrice = highPriceOri;
                } else {
                    isFilteredPrice = true;
                    //if((int)minValue>0) {
                    lowPrice = (int) minValue;
                    // }
                    highPrice = (int) maxValue;

                    PreferenceClass.putInt("lowPrice", lowPrice);
                    PreferenceClass.putInt("highPrice", highPrice);

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        filtered();
                    }
                });

            }


        });

        textViewLowPrice.setText(utilBand.formatRupiah(lowPrice).replace(",00", ""));
        textViewHighPrice.setText(utilBand.formatRupiah(highPrice).replace(",00", ""));
        seekBarPrice.setSelectedMinValue(lowPrice);
        seekBarPrice.setSelectedMaxValue(highPrice);
        isFilterVoid();
    }

    private void isFilterVoid() {
//        textViewLowPrice.setText(utilBand.formatRupiah(lowPrice).replace(",00", ""));
//        textViewHighPrice.setText(utilBand.formatRupiah(highPrice).replace(",00", ""));
//        seekBarPrice.setSelectedMinValue(lowPrice);
//        seekBarPrice.setSelectedMaxValue(highPrice);
        scheduleFlightActivity = FlightScheduleActivity.getInstanceFlightSchedule();
        if (isFilter) {
            countPrice = scheduleFlightActivity.adapter.mDisplayListFilter.size();
            int lowPriceFilter = 0;
            String formatStringButton;
            if (countPrice > 0) {
                if (scheduleFlightActivity.adapter.mDisplayListFilter.get(0).getPriceTemp() != 999999999) {
                    lowPriceFilter = scheduleFlightActivity.adapter.mDisplayListFilter.get(0).getPriceTemp();
                }
                formatStringFilter = String.format(getString(R.string.filter_header), countPrice, utilBand.formatRupiah(lowPriceFilter).replace(",00", ""));
                formatStringButton = String.format(getString(R.string.filter_button), countPrice);


            } else {
                formatStringFilter = "Penerbangan Tidak Ditemukan";
                formatStringButton = String.format(getString(R.string.filter_button), countPrice);

            }
            btn_pilih.setText(formatStringButton);
            textHeaderFilter.setText(formatStringFilter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.core_menu_reset, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // int id = item.getItemId();
        int i = item.getItemId();
        if (i == android.R.id.home) {
            // stop();
//            handler.removeCallbacks(r);

            onBackPressed();
            return true;
        } else if (i == R.id.action_right_reset) {

            reset();

            return true;

        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    private void reset() {
        isFilter = false;
//        isFilteredDateDept = false;
        isFilteredPrice = false;
        isFilteredAirline = false;
        PreferenceClass.putBoolean(FlightKeyPreference.isFilteredAirline, false);
        bCheckJam1 = false;
        bCheckJam2 = false;
        bCheckJam3 = false;
        bCheckJam4 = false;

        PreferenceClass.putBoolean("checkJam1", false);
        PreferenceClass.putBoolean("checkJam2", false);
        PreferenceClass.putBoolean("checkJam3", false);
        PreferenceClass.putBoolean("checkJam4", false);

        //filtered();
        // Collections.sort(flightModels, hargaTermurah);
        flightModels = (ArrayList<FlightDataModelClasses>) MemoryStore.get("schedule_list_one_way");
        scheduleFlightActivity.adapter.updateList(flightModels);
        countPrice = flightModels.size();
        String formatString = String.format(getString(R.string.filter_header), countPrice, utilBand.formatRupiah(lowPrice).replace(",00", ""));
        String formatStringButton = String.format(getString(R.string.filter_button), countPrice);
        btn_pilih.setText(formatStringButton);
        textHeaderFilter.setText(formatString);
        PreferenceClass.putString(FlightKeyPreference.hasilFilterAirlines, "Bebas");
        textViewHasilFilterAirlines.setText("Bebas");

        textViewSeekBarLowPrice.setText(utilBand.formatRupiah(lowPriceReset.intValue()).replace(",00", ""));
        textViewSeekBarHighPrice.setText(utilBand.formatRupiah(highPriceReset.intValue()).replace(",00", ""));
        seekBarPrice.setRangeValues(lowPriceReset, highPriceReset);
        textViewLowPrice.setText(utilBand.formatRupiah(lowPriceReset.intValue()).replace(",00", ""));
        textViewHighPrice.setText(utilBand.formatRupiah(highPriceReset.intValue()).replace(",00", ""));
        seekBarPrice.setSelectedMinValue(lowPriceReset);
        seekBarPrice.setSelectedMaxValue(highPriceReset);
        PreferenceClass.putInt("lowPrice", lowPriceReset.intValue());
        PreferenceClass.putInt("highPrice", highPriceReset.intValue());
        checkJam1.setChecked(bCheckJam1);
        checkJam2.setChecked(bCheckJam2);
        checkJam3.setChecked(bCheckJam3);
        checkJam4.setChecked(bCheckJam4);


        imageViewMaskapai.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_flight_free));

        Log.d(TAG, "onOptionsItemSelected: " + flightModels.size());
        if (!textViewHasilFilterAirlines.getText().equals("Bebas")) {
//            flightAirlinesFilterActivity=FlightAirlinesFilterActivity.getIntanceAirlineFilter();
//
//            flightAirlinesFilterActivity.resetAll(false);
        }
        MemoryStore.set(FlightKeyPreference.resultFilterAirlinesCode, new ArrayList<>());
        MemoryStore.set(FlightKeyPreference.resultFilterAirlinesName, new ArrayList<>());
    }

    public void onBackPressed() {

        Intent i = new Intent();
        i.putExtra("isFilter", isFilter);

        setResult(Activity.RESULT_OK, i);
        finish();
    }

    @Override
    public void onClick(@NonNull View v) {

        int id = v.getId();
//        if (id == R.id.checkboxJam1) {
//            bCheckJam1 = checkJam1.isChecked();
//            PreferenceClass.putBoolean("checkJam1", bCheckJam1);
//            filtered();
//
//        } else if (id == R.id.checkboxJam2) {
//            bCheckJam2 = checkJam2.isChecked();
//            PreferenceClass.putBoolean("checkJam2", bCheckJam2);
//            filtered();
//        } else if (id == R.id.checkboxJam3) {
//            bCheckJam3 = checkJam3.isChecked();
//            PreferenceClass.putBoolean("checkJam3", bCheckJam3);
//            filtered();
//        } else if (id == R.id.checkboxJam4) {
//            bCheckJam4 = checkJam4.isChecked();
//            PreferenceClass.putBoolean("checkJam4", bCheckJam4);
//            filtered();
//        } else
        if (id == R.id.cardViewMaskapai) {
            Intent intent = new Intent(FlightFilterActivity.this, FlightAirlinesFilterActivity.class);
            isFilteredAirline = PreferenceClass.getBoolean(FlightKeyPreference.isFilteredAirline, false);
            // intent.putExtra("isFilteredAirline", isFilteredAirline);
            //  intent.putExtra("hasilFilterAirlines", textViewHasilFilterAirlines.getText());
            startActivityForResult(intent, 100);
        } else {

            Intent i = new Intent();
            i.putExtra("isFilter", isFilter);
            //i.putExtra()
            setResult(Activity.RESULT_OK, i);
            finish();
        }
    }

    @Nullable
    public ArrayList<String> codeAirline = new ArrayList<>();
    @Nullable
    public ArrayList<String> namaAirline = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 100) {
                isFilteredAirline = PreferenceClass.getBoolean(FlightKeyPreference.isFilteredAirline, false);
//                isFilteredAirline = data.getBooleanExtra("isFilteredAirline",true);
                codeAirline = data.getStringArrayListExtra("resultFilterAirlinesCode");
                namaAirline = data.getStringArrayListExtra("resultFilterAirlinesName");

                Log.d(TAG, "onActivityResult: " + isFilteredAirline + " " + namaAirline.toString() + " " + codeAirline);


                if (isFilteredAirline) {
                    // textViewHasilFilterAirlines.setText(Arrays.asList(namaAirline).toString());
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    imageViewMaskapai.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_flight_choice));
//                    } else {
//                        imageViewMaskapai.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.ic_flight_choice));
//                    }
                } else {
                    PreferenceClass.putString(FlightKeyPreference.hasilFilterAirlines, "Bebas");
                    textViewHasilFilterAirlines.setText("Bebas");
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    imageViewMaskapai.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_flight_free));
//                    } else {
//                        imageViewMaskapai.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.ic_flight_free));
//                    }

                }
                filtered();
                //  isFilterVoid();
            }
        }


    }

    public void filtered() {
        scheduleFlightActivity = FlightScheduleActivity.getInstanceFlightSchedule();
//        boolean groupCode=false;
        String specialNameGroup = "";
//        String[] times1 = checkJam1.getText().toString().trim().split("-");
//        String[] from1 = times1[0].trim().split(":");
//        String[] until1 = times1[1].trim().split(":");
//
//        String[] times2 = checkJam2.getText().toString().trim().split("-");
//        String[] from2 = times2[0].trim().split(":");
//        String[] until2 = times2[1].trim().split(":");
//
//        String[] times3 = checkJam3.getText().toString().trim().split("-");
//        String[] from3 = times3[0].trim().split(":");
//        String[] until3 = times3[1].trim().split(":");
//
//        String[] times4 = checkJam4.getText().toString().trim().split("-");
//        String[] from4 = times4[0].trim().split(":");
//        String[] until4 = times4[1].trim().split(":");
//
//        fromTime1 = Calendar.getInstance(config.locale);
//        fromTime1.set(Calendar.HOUR_OF_DAY, Integer.valueOf(from1[0]));
//        fromTime1.set(Calendar.MINUTE, Integer.valueOf(from1[1]));
//
//        toTime1 = Calendar.getInstance(config.locale);
//        toTime1.set(Calendar.HOUR_OF_DAY, Integer.valueOf(until1[0]));
//        toTime1.set(Calendar.MINUTE, Integer.valueOf(until1[1]));
//
//
//        fromTime2 = Calendar.getInstance(config.locale);
//        fromTime2.set(Calendar.HOUR_OF_DAY, Integer.valueOf(from2[0]));
//        fromTime2.set(Calendar.MINUTE, Integer.valueOf(from2[1]));
//
//        toTime2 = Calendar.getInstance(config.locale);
//        toTime2.set(Calendar.HOUR_OF_DAY, Integer.valueOf(until2[0]));
//        toTime2.set(Calendar.MINUTE, Integer.valueOf(until2[1]));
//
//
//        fromTime3 = Calendar.getInstance(config.locale);
//        fromTime3.set(Calendar.HOUR_OF_DAY, Integer.valueOf(from3[0]));
//        fromTime3.set(Calendar.MINUTE, Integer.valueOf(from3[1]));
//
//        toTime3 = Calendar.getInstance(config.locale);
//        toTime3.set(Calendar.HOUR_OF_DAY, Integer.valueOf(until3[0]));
//        toTime3.set(Calendar.MINUTE, Integer.valueOf(until3[1]));
//
//
//        fromTime4 = Calendar.getInstance(config.locale);
//        fromTime4.set(Calendar.HOUR_OF_DAY, Integer.valueOf(from4[0]));
//        fromTime4.set(Calendar.MINUTE, Integer.valueOf(from4[1]));
//
//        toTime4 = Calendar.getInstance(config.locale);
//        toTime4.set(Calendar.HOUR_OF_DAY, Integer.valueOf(until4[0]));
//        toTime4.set(Calendar.MINUTE, Integer.valueOf(until4[1]));


        ArrayList<FlightDataModelClasses> flightModelsFiltered = new ArrayList<>();
        //if (((bCheckJam1 == false) && (bCheckJam2 == false) && (bCheckJam3 == false) && (bCheckJam4 == false)) &&
        if ((!isFilteredAirline) && (!isFilteredPrice)) {
            // flightModelsFiltered=flightModels;
            flightModelsFiltered = (ArrayList<FlightDataModelClasses>) MemoryStore.get("schedule_list_one_way");
            countPrice = flightModelsFiltered.size();
            isFilter = false;

            // if(scheduleFlightActivity.checked==1){
            scheduleFlightActivity.adapter.updateList(clearListFromDuplicatePrice(flightModelsFiltered));
//            }else  if(scheduleFlightActivity.checked==2){
//                scheduleFlightActivity.adapter.updateList(clearListFromDuplicateTerpagi(flightModelsFiltered));
//            }else  if(scheduleFlightActivity.checked==3){//durasi
            // scheduleFlightActivity.adapter.updateList(clearListFromDuplicateDurasi(flightModelsFiltered));
//            }
        } else {
            flightModels = (ArrayList<FlightDataModelClasses>) MemoryStore.get("schedule_list_one_way");
            Log.d(TAG, "filtered: airline" + isFilteredAirline+" isFilteredPrice"+isFilteredPrice);
//            Log.d(TAG, "filtered: price" + isFilteredPrice);
            countPrice = 0;
            isFilter = true;
            for (FlightDataModelClasses flightModel : flightModels) {
                //Log.d(TAG, "filtered: "+flightModel.);
                float flightPrice = flightModel.getPriceTemp();
//                Log.d(TAG, "filtered: price "+flightModel.getPriceTemp());
//                String flightModelAirlineCode = flightModel.getFlightCode();//AirlineCode();
                String flightModelAirlineCode = flightModel.getAirlineCode();

                String[] arrFlightTimeDept = flightModel.getDepartureTime().split(":");
                String hourModel = arrFlightTimeDept[0];
                String minuteModel = arrFlightTimeDept[1];
                currentTime = Calendar.getInstance(SBFApplication.config.locale);
                currentTime.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hourModel));
                currentTime.set(Calendar.MINUTE, Integer.valueOf(minuteModel));
                boolean found = false;
                boolean foundPrice = false;
//                boolean foundTime = false;

                if (isFilteredPrice && !isFilteredAirline) {
                    if (flightPrice >= lowPrice && flightPrice <= highPrice) {
                        //try {
                        //specialNameGroup = Integer.toString(flightModel.getPriceTemp());
                     //   specialNameGroup = lowPrice + "-" + highPrice;
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                        //  flightModelsFiltered.add(flightModel);
                        //  Log.d(TAG, "filtered: price " + flightPrice+" "+flightModel.getAirlineName()+" "+flightModel.getDepartureTime()+" "+flightModelsFiltered.size());
                        foundPrice = true;
                        //  scheduleFlightActivity.adapter.checked(1);

                        //    scheduleFlightActivity.adapter.getFilter().filter(specialNameGroup);
                        countPrice++;
                    }
                    if (!foundPrice) {
                        continue;
                    }
                    flightModelsFiltered.add(flightModel);
                   // break;
                }


//                if (!isFilteredDateDept) {
//                    isFilteredDateDept = bCheckJam1 == true || bCheckJam2 == true || bCheckJam3 == true || bCheckJam4 == true;
//                }
////
//                if (isFilteredDateDept == true) {
//
//
//                    if (bCheckJam1 == true) {
//                        if ((currentTime.before(fromTime1) || currentTime.after(toTime1))) {
////                            flightModelsFiltered.add(flightModel);
//                            continue;
//                            // foundTime = true;
//                        }
//                    }
//                    if (bCheckJam2 == true) {
//                        if ((currentTime.before(fromTime2) || currentTime.after(toTime2))) {
////                            flightModelsFiltered.add(flightModel);
//                            continue;
//                            //foundTime = true;
//                        }
//                    }
//                    if (bCheckJam3 == true) {
//                        if ((currentTime.before(fromTime3) || currentTime.after(toTime3))) {
////                            flightModelsFiltered.add(flightModel);
//                            continue;
//                            //foundTime = true;
//                        }
//                    }
//                    if (bCheckJam4 == true) {
//                        if ((currentTime.before(fromTime4) || currentTime.after(toTime4))) {
//                            //  flightModelsFiltered.add(flightModel);
//                            continue;
//                            //  foundTime = true;
//                        }
//                    }
////
//                }

                if (isFilteredAirline && !isFilteredPrice) {
                    textViewHasilFilterAirlines.setText(namaAirline.toString().replace("[", "").replace("]", ""));
                    PreferenceClass.putString(FlightKeyPreference.hasilFilterAirlines, namaAirline.toString().replace("[", "").replace("]", ""));
                    Log.d(TAG, "filtered size: " + flightModel.getFlightCode().length());//getAirlineCode());
                    for (int i = 0; i < codeAirline.size(); i++) {

                      //  Log.d(TAG, "filtered: " + codeAirline.get(i).substring(0, 2) + " " + flightModel.getFlightCode()+" "+flightModel.getPriceTemp());//getAirlineCode());

                        try {
                            Log.d(TAG, "filtered xxxx: isFilteredAirline "+isFilteredAirline+ "  isFilteredPrice " + isFilteredPrice +"  "+ codeAirline.get(i).substring(0, 2) + " " +flightModel.getDetailTitle().getJSONObject(0).getString("flightCode").substring(0, 2)+" "+flightModel.getPriceTemp());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

//                        if (flightModelAirlineCode.equals(codeAirline.get(i))) {
//                            found = true;
//
//                        }
                        try {
                            if (codeAirline.get(i).substring(0, 2).equals(flightModel.getDetailTitle().getJSONObject(0).getString("flightCode").substring(0, 2))) {
//                                if (flightPrice >= 0 && flightPrice <= highPrice) {

                                  //  foundPrice = true;
                                    countPrice++;
                                    found = true;

//                                }
//                                if (!foundPrice) {
//                                    continue;
//                                }
//                                specialNameGroup=namaAirline.get(i);
//                                countPrice++;
//                  //              groupCode=true;
//                                found = true;
//                                scheduleFlightActivity.adapter.checked(1);
//
//                                scheduleFlightActivity.adapter.getFilter().filter(specialNameGroup);
                                // break;
                                //                        if (codeAirline.get(i).equals("ID")) {
                                //                            //  case "ID":
                                ////                            flightModel.setAirlineCode("TPJT");
                                ////                            flightModel.setAirlineName("Batik Air");
                                ////                            flightModel.setAirlineIcon("http://static.scash.bz/fastravel/asset/maskapai/TPID.png");
                                //                            specialNameGroup="Batik Air";
                                //                            groupCode=true;
                                //                            found = true;
                                //                            //     break;
                                //                        }else if(codeAirline.get(i).equals("IW")) {
                                //                            //  case "IW":
                                ////                            flightModel.setAirlineCode("TPJT");
                                ////                            flightModel.setAirlineName("Wings Air");
                                ////                            flightModel.setAirlineIcon("http://static.scash.bz/fastravel/asset/maskapai/TPIW.png");
                                //                            specialNameGroup="Wings Air";
                                //                            groupCode=true;
                                //                            found = true;
                                //                        }else if(codeAirline.get(i).equals("JT")) {
                                //                            //  case "IW":
                                ////                            flightModel.setAirlineCode("TPJT");
                                ////                            flightModel.setAirlineName("Wings Air");
                                ////                            flightModel.setAirlineIcon("http://static.scash.bz/fastravel/asset/maskapai/TPIW.png");
                                //                            specialNameGroup="Lion Air";
                                //                            groupCode=true;
                                //                            found = true;
                                //                            //    break;
                                // default:
                            }
//                            else if (flightModelAirlineCode.equals(codeAirline.get(i))) {
////                                if (flightPrice >= 0 && flightPrice <= highPrice) {
//
//                                  //  foundPrice = true;
//                                    countPrice++;
//                                    found = true;
//
////                                }
////                                if (!foundPrice) {
////                                    continue;
////                                }
//                                //                            flightModel.setAirlineCode(flightModelAirlineCode);
//                                //                            flightModel.setAirlineName(flightModels.);
//                                //                            flightModel.setAirlineIcon(airlineIcon);
////                                specialNameGroup = flightModel.getAirlineName();
////                                //      groupCode=false;
////                                found = true;
//////                                scheduleFlightActivity.adapter.checked(1);
//////
//////                                scheduleFlightActivity.adapter.getFilter().filter(specialNameGroup);
////                                countPrice++;
//                                //   break;
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    if (!found) {
                        continue;
                    }

                    flightModelsFiltered.add(flightModel);
                    //  scheduleFlightActivity.adapter.checked(1);

                    //  scheduleFlightActivity.adapter.getFilter().filter(specialNameGroup);
//break;
                }

                if (isFilteredAirline && isFilteredPrice) {

                    String harga = Integer.toString(flightModel.getPriceTemp());

                    textViewHasilFilterAirlines.setText(namaAirline.toString().replace("[", "").replace("]", ""));
                    PreferenceClass.putString(FlightKeyPreference.hasilFilterAirlines, namaAirline.toString().replace("[", "").replace("]", ""));
                    Log.d(TAG, "filtered size: " + flightModel.getFlightCode().length());//getAirlineCode());
                    for (int i = 0; i < codeAirline.size(); i++) {

                        Log.d(TAG, "filtered: " + codeAirline.get(i) + " " + flightModel.getFlightCode());//getAirlineCode());

                        try {
                            Log.d(TAG, "filtered xxxx: " + flightModel.getDetailTitle().getJSONObject(0).getString("flightCode").substring(0, 2));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            if (codeAirline.get(i).substring(0, 2).equals(flightModel.getDetailTitle().getJSONObject(0).getString("flightCode").substring(0, 2))) {
                                if (flightPrice >= lowPrice && flightPrice <= highPrice) {
                                    //try {
                                    // specialNameGroup = namaAirline.get(i)+"-"+harga;
                                    specialNameGroup = namaAirline.get(i) + "-" + lowPrice + "-" + highPrice;
//
                                    foundPrice = true;


                                    countPrice++;

                                    found = true;
                                    //    scheduleFlightActivity.adapter.checked(1);

                                    //  scheduleFlightActivity.adapter.getFilter().filter(specialNameGroup);
                                }
                                if (!foundPrice) {
                                    continue;
                                }

                            }
//                            else if (flightModelAirlineCode.equals(codeAirline.get(i))) {
//
//                                // countPrice++;
//                                if (flightPrice >= lowPrice && flightPrice <= highPrice) {
//                                    //try {
////                                    specialNameGroup = flightModel.getAirlineName()+"-"+harga;
//                                    specialNameGroup = flightModel.getAirlineName() + "-" + lowPrice + "-" + highPrice;
////
//                                    foundPrice = true;
//
//
//                                    countPrice++;
//
//                                    found = true;
//                                    //     scheduleFlightActivity.adapter.checked(1);
//
//                                    //  scheduleFlightActivity.adapter.getFilter().filter(specialNameGroup);
//                                }
//                                if (!foundPrice) {
//                                    continue;
//                                }
//
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    if (!found) {
                        continue;
                    }
                    flightModelsFiltered.add(flightModel);
                    //break;
                }


            }
            // Collections.sort(flightModelsFiltered, hargaTermurah);
            // Log.d(TAG, "filtered: final "+specialNameGroup);
            if (scheduleFlightActivity.checked == 1) {
                Collections.sort(flightModelsFiltered, scheduleFlightActivity.hargaTermurah);
                scheduleFlightActivity.adapter.updateList(clearListFromDuplicatePrice(flightModelsFiltered));

//                if(groupCode==true){
                scheduleFlightActivity.adapter.checked(1);
//
//                    scheduleFlightActivity.adapter.getFilter().filter(specialNameGroup);
//                }
                //  scheduleFlightActivity.adapter.notifyDataSetChanged();

            } else if (scheduleFlightActivity.checked == 2) {
                Collections.sort(flightModelsFiltered, scheduleFlightActivity.waktuBerangkatTerpagi);
                scheduleFlightActivity.adapter.updateList(clearListFromDuplicatePrice(flightModelsFiltered));
////                if(groupCode==true){
                scheduleFlightActivity.adapter.checked(2);
//                    scheduleFlightActivity.adapter.getFilter().filter(specialNameGroup);
////                }
//                scheduleFlightActivity.adapter.notifyDataSetChanged();
            } else if (scheduleFlightActivity.checked == 3) {//durasi
                Collections.sort(flightModelsFiltered, scheduleFlightActivity.waktuBerangkatTerpagi);
                scheduleFlightActivity.adapter.updateList(clearListFromDuplicatePrice(flightModelsFiltered));
//                if(groupCode==true){
                scheduleFlightActivity.adapter.checked(3);
//                    scheduleFlightActivity.adapter.getFilter().filter(specialNameGroup);
//                }
                // scheduleFlightActivity.adapter.notifyDataSetChanged();
            }
            //  Log.d(TAG, "filtered end: " +  scheduleFlightActivity.adapter.mDisplayListFilter.size());
            // countPrice =  scheduleFlightActivity.adapter.mDisplayListFilter.size();
        }

        // int countx = new ArrayList<FlightDataModelClasses>().size();


        Log.d(TAG, "filtered: count show" + countPrice);
        int lowPriceFilter = 0;
        String formatStringButton;
        if (countPrice > 0) {
            if (scheduleFlightActivity.adapter.mDisplayListFilter.get(0).getPriceTemp() != 999999999) {
                lowPriceFilter = scheduleFlightActivity.adapter.mDisplayListFilter.get(0).getPriceTemp();
//                try {
//                    Log.d(TAG, "filtered: > 0"+scheduleFlightActivity.adapter.mDisplayListFilter.get(0).getDetailTitle().getJSONObject(0).getString("flightCode").length());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
            formatStringFilter = String.format(getString(R.string.filter_header), countPrice, utilBand.formatRupiah(lowPriceFilter).replace(",00", ""));
            formatStringButton = String.format(getString(R.string.filter_button), countPrice);


        } else {
            formatStringFilter = "Penerbangan Tidak Ditemukan";
            formatStringButton = String.format(getString(R.string.filter_button), countPrice);

        }
        btn_pilih.setText(formatStringButton);
        textHeaderFilter.setText(formatStringFilter);
//        if ( ((FlightAirlinesFilterActivity)context).active == true) {
//            ((FlightAirlinesFilterActivity)context).textHeaderFilter.setText(formatStringFilter);
//        }


    }

    @NonNull
    private ArrayList<FlightDataModelClasses> clearListFromDuplicateFirstName(@NonNull ArrayList<FlightDataModelClasses> list1) {
        Map<String, FlightDataModelClasses> cleanMap = new LinkedHashMap<>();
        for (int i = 0; i < list1.size(); i++) {
            cleanMap.put(list1.get(i).getFlightCode(), list1.get(i));
        }
        ArrayList<FlightDataModelClasses> list = new ArrayList<>(cleanMap.values());
        return list;
    }

    @NonNull
    private ArrayList<FlightDataModelClasses> clearListFromDuplicatePrice(@NonNull ArrayList<FlightDataModelClasses> list1) {

        Map<String, FlightDataModelClasses> cleanMap = new LinkedHashMap<>();
        for (int i = 0; i < list1.size(); i++) {
            //  cleanMap.put(list1.get(i).getFlightCode(), list1.get(i));
            cleanMap.put(list1.get(i).getSeat(), list1.get(i));
        }
        ArrayList<FlightDataModelClasses> list = new ArrayList<>(cleanMap.values());
        return list;
    }

    @NonNull
    private ArrayList<FlightDataModelClasses> clearListFromDuplicateDurasi(@NonNull ArrayList<FlightDataModelClasses> list1) {

        Map<String, FlightDataModelClasses> cleanMap = new LinkedHashMap<String, FlightDataModelClasses>();
        for (int i = 0; i < list1.size(); i++) {
            cleanMap.put(list1.get(i).getDuration(), list1.get(i));
        }
        ArrayList<FlightDataModelClasses> list = new ArrayList<FlightDataModelClasses>(cleanMap.values());
        return list;
    }

    @NonNull
    private ArrayList<FlightDataModelClasses> clearListFromDuplicateTerpagi(@NonNull ArrayList<FlightDataModelClasses> list1) {

        Map<String, FlightDataModelClasses> cleanMap = new LinkedHashMap<String, FlightDataModelClasses>();
        for (int i = 0; i < list1.size(); i++) {
            cleanMap.put(list1.get(i).getDepartureTime(), list1.get(i));
        }
        ArrayList<FlightDataModelClasses> list = new ArrayList<FlightDataModelClasses>(cleanMap.values());
        return list;
    }
}

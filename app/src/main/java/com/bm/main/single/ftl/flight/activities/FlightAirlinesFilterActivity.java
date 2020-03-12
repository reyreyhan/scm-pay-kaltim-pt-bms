package com.bm.main.single.ftl.flight.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.templates.switchbutton.SwitchButton;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.pos.R;
import com.bm.main.single.ftl.flight.adapters.FlightAirlinesAdapter;
import com.bm.main.single.ftl.flight.constants.FlightKeyPreference;
import com.bm.main.single.ftl.flight.models.FlightAirlinesModel;
import com.bm.main.single.ftl.utils.MemoryStore;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class FlightAirlinesFilterActivity extends BaseActivity {
    private static final String TAG =FlightAirlinesFilterActivity.class.getSimpleName() ;
    public ListView mainListView;
    public FlightAirlinesAdapter listAdapter;
    private AppCompatButton btnSimpan;
    @NonNull
    private ArrayList<FlightAirlinesModel> airlinesModel = new ArrayList<>();
    public SwitchButton switchPilihSemua;
    TextView title, reset, batal;
    boolean checkAll = false;
    // public TextView textHeaderFilter;
    // public static AirlinesFilterActivity mInstance;
//    static boolean active = false;
    boolean isFilteredAirline;
    Context context;
    public static FlightAirlinesFilterActivity flightAirlinesFilterActivityInstance;
    private String hasilFilterAirlines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flight_activity_airlines_filter);
        context = this;
        flightAirlinesFilterActivityInstance = this;
        toolbar = findViewById(R.id.toolbar);
//        Intent intent = getIntent();
//        if (intent != null)
//            isFilteredAirline = intent.getStringExtra("isFilteredAirline");

        title = toolbar.findViewById(R.id.titleToolbar);
        reset = toolbar.findViewById(R.id.actionToolbarReset);
        title.setText("Maskapai");
        //  textHeaderFilter = findViewById(R.id.textHeaderFilter);
        isFilteredAirline = PreferenceClass.getBoolean(FlightKeyPreference.isFilteredAirline, false);
        switchPilihSemua = findViewById(R.id.switchSemua);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAll == true) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            switchPilihSemua.performClick();

                        }
                    }, 0001);
                }
            }
        });
//        batal = toolbar.findViewById(R.id.actionToolbarBatal);
//        batal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
        btnSimpan = findViewById(R.id.btn_simpanFilter);
        mainListView = findViewById(R.id.list_airlines);


        switchPilihSemua.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkAll = isChecked;
//if(isChecked==true){
                // switchPilihSemua.setChecked(isChecked);
////}
                resetAll(isChecked);
            }
        });
        airlinesModel = (ArrayList<FlightAirlinesModel>) MemoryStore.get("airlines_list");
        listAdapter = new FlightAirlinesAdapter(FlightAirlinesFilterActivity.this, clearListFromDuplicateFirstName(airlinesModel));
        mainListView.setAdapter(listAdapter);
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int position, long l) {
                listAdapter.setCheckBox(position);
            }
        });
        btnSimpan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Simpan(view);
                    }
                });
            }
        });
//        String header=((FlightFilterActivity)context).formatStringFilter;
//        textHeaderFilter.setText(header);
        if (!PreferenceClass.getBoolean(FlightKeyPreference.isFilteredAirline, false)) {
            Log.d(TAG, "onCreate: masuk reset");
            for (int i = 0; i < clearListFromDuplicateFirstName(airlinesModel).size(); i++) {
//
           //     CheckBox cb = mainListView.getChildAt(i).findViewById(R.id.checkBoxAirlineFilter);
                boolean x = clearListFromDuplicateFirstName(airlinesModel).get(i).isChecked();
//                FlightAirlinesModel airlines = (FlightAirlinesModel) cb.getTag();
//                airlines.setChecked(cb.isChecked());
                Log.d(TAG, "onCreate cek: "+x+" "+i);
                if(x){
                    listAdapter.setCheckBox(i);
                }
//                if(cb.isChecked()) {
//
//                    listAdapter.setCheckBox(i);
////                    FlightAirlinesModel planet = (FlightAirlinesModel) cb.getTag();
////                    planet.toggleChecked();
////                    planet.setChecked(false);
//                }
//
            }
//            mainListView.setAdapter(listAdapter);

          //  airlinesModel = (ArrayList<FlightAirlinesModel>) MemoryStore.get("airlines_list");
//            listAdapter = new FlightAirlinesAdapter(FlightAirlinesFilterActivity.this, clearListFromDuplicateFirstName(listAdapter.getAllData()));
//            mainListView.setAdapter(listAdapter);
           // listAdapter.setCheckBox(2);
//            listAdapter.notifyDataSetChanged();
        }
    }

    public static synchronized FlightAirlinesFilterActivity getIntanceAirlineFilter() {
        return flightAirlinesFilterActivityInstance;
    }

    public void resetAll(boolean isChecked) {

        for (int i = 0; i < mainListView.getChildCount(); i++) {

            CheckBox cb = mainListView.getChildAt(i).findViewById(R.id.checkBoxAirlineFilter);
            cb.setChecked(isChecked);

            FlightAirlinesModel planet = (FlightAirlinesModel) cb.getTag();
            planet.toggleChecked();
            planet.setChecked(cb.isChecked());

        }
    }

    @NonNull
    public ArrayList<String> codeAirline = new ArrayList<>();
    @NonNull
    public ArrayList<String> namaAirline = new ArrayList<>();

    public void Simpan(View v) {
        codeAirline.clear();
        namaAirline.clear();
        for (int i = 0; i < listAdapter.getAllData().size(); i++) {
            FlightAirlinesModel planet = listAdapter.getAllData().get(i);
            if (planet.isChecked()) {
                codeAirline.add(planet.getAirLineCode());
                namaAirline.add(planet.getAirLineNama());
            }
        }

        isFilteredAirline = namaAirline.size() > 0;
        //Log.d(TAG, "Simpan: "+isFilteredAirline);
        PreferenceClass.putBoolean(FlightKeyPreference.isFilteredAirline, isFilteredAirline);
        MemoryStore.set(FlightKeyPreference.resultFilterAirlinesCode, codeAirline);
        MemoryStore.set(FlightKeyPreference.resultFilterAirlinesName, namaAirline);
        Intent i = new Intent();
        i.putExtra("resultFilterAirlinesCode", codeAirline);
        i.putExtra("resultFilterAirlinesName", namaAirline);
        setResult(Activity.RESULT_OK, i);
        finish();
    }

    @NonNull
    private ArrayList<FlightAirlinesModel> clearListFromDuplicateFirstName(@NonNull ArrayList<FlightAirlinesModel> list1) {
        Map<String, FlightAirlinesModel> cleanMap = new LinkedHashMap<String, FlightAirlinesModel>();
        for (int i = 0; i < list1.size(); i++) {
            cleanMap.put(list1.get(i).getAirLineNama(), list1.get(i));
        }
        ArrayList<FlightAirlinesModel> list = new ArrayList<FlightAirlinesModel>(cleanMap.values());
        return list;
    }

    @Override
    public void onBackPressed() {
        Log.e("INFO", "TEST");
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        active = true;
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        active = false;
//    }
}

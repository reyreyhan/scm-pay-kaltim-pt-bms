package com.bm.main.fpl.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.bm.main.scm.R;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.models.SaldoModel;
import com.bm.main.fpl.staggeredgridApp.MenuToursModel;
import com.bm.main.fpl.staggeredgridApp.SolventRecyclerViewToursAdapter;
import com.bm.main.fpl.utils.FormatString;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.single.ftl.activities.TravelPesananActivity;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bm.main.single.ftl.flight.activities.FlightSearchActivity;
import com.bm.main.single.ftl.flight.constants.FlightKeyPreference;
import com.bm.main.single.ftl.flight.constants.FlightPath;
import com.bm.main.single.ftl.flight.models.ConfigFlightModel;
import com.bm.main.single.ftl.flight.models.SettingFlightModel;
import com.bm.main.single.ftl.models.BaseObject;
import com.bm.main.single.ftl.ship.activities.ShipSearchActivity;
import com.bm.main.single.ftl.train.activities.TrainSearchActivity;
import com.bm.main.single.ftl.utils.RequestUtilsTravel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ToursTiketingActivity extends BaseActivity implements ProgressResponseCallback, SolventRecyclerViewToursAdapter.OnClickMenuTravel {

    private static final String TAG = ToursTiketingActivity.class.getSimpleName();
    private RecyclerView recycler_menu_tours;
    @NonNull
    private int[] menuIcons = {
            R.drawable.ic_menu_pesawat,//0
            R.drawable.ic_menu_kereta,//1
            R.drawable.ic_menu_pelni,//5
            R.drawable.ic_menu_pesanan//7
    };
    @NonNull
    private String[] menuTitle = {
            "Pesawat",
            "Kereta",
            "Pelni",
            "Pesanan Saya",
    };

    private ConfigFlightModel configFlightModel;
    private SettingFlightModel settingFlightModel;
    Intent intent;
    boolean isFromPay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tours_tiketing);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Tiket & Travel");
        bottom_toolbar = findViewById(R.id.bottom_toolbar);
        init(0);
        recycler_menu_tours = findViewById(R.id.recycler_menu_tours);
        setMenu();
        RequestConfiguration();
        navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));
    }

    protected void onResume() {
        Log.d(TAG, "onResume: ");
        navigationBottomCustom(bottom_toolbar, PreferenceClass.getString("saldo", "0"));
        super.onResume();
    }

    private void RequestConfiguration() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", PreferenceClass.getToken());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestUtilsTravel.transportWithProgressResponse(ToursTiketingActivity.this, FlightPath.CONFIGURATION, jsonObject, TravelActionCode.CONFIGURATION, this);
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

    private void setMenu() {
        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        recycler_menu_tours.setLayoutManager(mLayoutManager);
        List<MenuToursModel> menuList = new ArrayList<>();
        for (int i = 0; i < menuIcons.length; i++) {
            MenuToursModel menuToursModel = new MenuToursModel(menuTitle[i], menuIcons[i]);
            menuList.add(menuToursModel);
        }

        SolventRecyclerViewToursAdapter mAdapter = new SolventRecyclerViewToursAdapter(this, menuList, this);
        recycler_menu_tours.setAdapter(mAdapter);
    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        if (actionCode == TravelActionCode.CONFIGURATION) {
            BaseObject baseObject = gson.fromJson(response.toString(), BaseObject.class);
            if (baseObject.getRc().equals(ResponseCode.SUCCESS)) {
                Log.d(TAG, "onSuccess: " + response.toString());
                JSONObject data = new JSONObject();
                try {
                    data = response.getJSONObject("data");
                    configFlightModel = gson.fromJson(response.getJSONObject("data").toString(), ConfigFlightModel.class);
                    settingFlightModel = gson.fromJson(response.getJSONObject("data").toString(), SettingFlightModel.class);

                    JSONObject obj = PreferenceClass.getJSONObject(FlightKeyPreference.dataConfigFlight);
                    JSONObject objSettings = PreferenceClass.getJSONObject(FlightKeyPreference.dataSettingsFlight);
                    JSONArray array = new JSONArray();
                    JSONArray arraySettings = new JSONArray();
                    try {
                        array = obj.getJSONArray("configurations");
                        arraySettings = objSettings.getJSONArray("settings");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "onSuccess: " + array.length());
                    if (array.length() != configFlightModel.getConfigurations().size()) {
                        PreferenceClass.putJSONObject(FlightKeyPreference.dataConfigFlight, data);

                    }

                    if (arraySettings.length() != settingFlightModel.getSettings().size()) {
                        PreferenceClass.putJSONObject(FlightKeyPreference.dataSettingsFlight, data);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        } else if (actionCode == ActionCode.CEK_SALDO) {
            Log.d(TAG, "onSuccess: " + response.toString());
            SaldoModel saldoModel = gson.fromJson(response.toString(), SaldoModel.class);
            if (saldoModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                PreferenceClass.putString("saldo", FormatString.CurencyIDR(saldoModel.getResponse_value()));
                navigationBottomCustom((RelativeLayout) findViewById(R.id.bottom_toolbar), PreferenceClass.getString("saldo", "0"));
            }

        }
    }

    @Override
    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {
    }

    @Override
    public void onClickMenuTravel(int position) {
        switch (position) {
            case 0:
                intent = new Intent(ToursTiketingActivity.this, FlightSearchActivity.class);
                break;

            case 1:
                intent = new Intent(ToursTiketingActivity.this, TrainSearchActivity.class);
                break;

            case 2:
                intent = new Intent(ToursTiketingActivity.this, ShipSearchActivity.class);
                break;

            case 3:
                if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
                    new_popup_alertDemo(this, "Info", "Anda belum bisa menikmati fiture ini.\n" +
                            "Daftar & Aktifasi sekarang juga ID Anda");

                } else {
                    intent = new Intent(ToursTiketingActivity.this, TravelPesananActivity.class);
                }
                break;
            default:
                break;
        }

        startActivityForResult(intent, TravelActionCode.IS_FROM_PAY);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TravelActionCode.IS_FROM_PAY && resultCode == RESULT_OK) {
            if (data != null && data.getAction() != null) {
                switch (data.getAction()) {
                    case TravelActionCode.MENU_PESAWAT:
                        onClickMenuTravel(0);
                        break;
                    case TravelActionCode.MENU_KERETA:
                        onClickMenuTravel(1);
                        break;
                    case TravelActionCode.MENU_PELNI:
                        onClickMenuTravel(2);
                        break;
                    case TravelActionCode.MENU_PESANAN:
                        onClickMenuTravel(3);
                        break;
                }
            }

        }
    }

    public void togglePesanan(View view) {
        //. call pesanan saya
        if (PreferenceClass.getId().equals(PreferenceClass.getIdDemo())) {
            new_popup_alertDemo(this, "Info", "Anda belum bisa menikmati fiture ini.\n" +
                    "Daftar & Aktifasi sekarang juga ID Anda");

        } else {
            Intent intent = new Intent(ToursTiketingActivity.this, TravelPesananActivity.class);
            startActivity(intent);
        }
    }
}
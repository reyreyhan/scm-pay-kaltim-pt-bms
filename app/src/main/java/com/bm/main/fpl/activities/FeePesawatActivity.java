package com.bm.main.fpl.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;

import com.bm.main.pos.R;
import com.bm.main.fpl.adapters.Fee_List_FlightAdapter;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.handlers.JsonObjectResponseHandler;
import com.bm.main.fpl.interfaces.JsonObjectResponseCallback;
import com.bm.main.fpl.models.FeeFlightModel;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.materialedittext.MaterialEditText;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bm.main.single.ftl.constants.TravelPath;
import com.bm.main.single.ftl.flight.constants.FlightPath;
import com.bm.main.single.ftl.models.BaseObject;
import com.bm.main.single.ftl.utils.MemoryStore;
import com.bm.main.single.ftl.utils.RequestUtilsTravel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class FeePesawatActivity extends BaseActivity implements JsonObjectResponseCallback {

    private static final String TAG = FeePesawatActivity.class.getSimpleName();
    List<FeeFlightModel> list = new ArrayList<>();
    RecyclerView recyclerView;
    Fee_List_FlightAdapter adapter;
    RecyclerView.LayoutManager mLayoutManager;
    AppCompatButton btn_simpan;

    private boolean firstLoad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_pesawat);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Setting Fee Pesawat");
        init(0);
        recyclerView = findViewById(R.id.recylerList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new Fee_List_FlightAdapter(this, list);
        recyclerView.setAdapter(adapter);
        RequestConfiguration();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    private void RequestConfiguration() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", PreferenceClass.getToken());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtilsTravel.transportWithJSONObjectResponse(this, FlightPath.CONFIGURATION, jsonObject, TravelActionCode.CONFIGURATION, this);
    }

    @Override
    public void onSuccess(int actionCode, final JSONObject response) {
        Log.d(TAG, "onSuccess: " + response.toString());
        try {
            if (response.getString("rc").equals("00")) {
                if (actionCode == TravelActionCode.CONFIGURATION) {
                    //Log.d(TAG, "onSuccess: " + response.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getFee(response);
                        }
                    });
                } else if (actionCode == TravelActionCode.SETFEEFLIGHT) {
                    //Log.d(TAG, "onSuccess: " + response.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RequestConfiguration();
                        }
                    });
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //   showToastCustom(FeePesawatActivity.this, 2, response.getString("rd"));
                            snackBarCustomAction(findViewById(R.id.rootLayout), 0, response.getString("rd"), 2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (JSONException e) {
            showToast(e.toString());
        }
    }

    private void getFee(JSONObject response) {
        list.clear();
        BaseObject baseObject = gson.fromJson(response.toString(), BaseObject.class);
        try {
            if (baseObject.getRc().equals(ResponseCode.SUCCESS)) {
                JSONObject oParent = response.getJSONObject("data");
                JSONArray settingFlight = oParent.getJSONArray("settings");
                FeeFlightModel setFee;
                for (int i = 0; i < settingFlight.length(); i++) {
                    JSONObject fee = settingFlight.getJSONObject(i);
                    setFee = new FeeFlightModel();
                    setFee.setCustomAdmin(fee.getString("customAdmin"));
                    setFee.setAirline(fee.getString("airline"));
                    setFee.setAirlineName(fee.getString("airlineName"));
                    setFee.setIcon(fee.getString("icon"));
                    setFee.setNewsUrl(fee.getString("newsUrl"));
                    list.add(setFee);
                }
                adapter.notifyDataSetChanged();
                MemoryStore.set("feeFlight", list);

                if (!firstLoad) {
                    snackBarCustomAction(findViewById(R.id.rootLayout), 0, "Setting Fee Pesawat Berhasil", 1);
                } else {
                    firstLoad = false;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {
        final String rd = responseDescription;
        runOnUiThread(() -> snackBarCustomAction(findViewById(R.id.rootLayout), 0, rd, 2));
    }

    public void getRequestFromAdapter(MaterialEditText textFee) {
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObjectFeesContaint = new JSONObject();
        try {
            jsonObjectFeesContaint.put(textFee.getTag(R.id.idfee).toString(), Integer.parseInt(textFee.getText().toString().trim().replace("Rp ", "").replace(".", "")));
            jsonObject.put("fees", jsonObjectFeesContaint);
            jsonObject.put("token", PreferenceClass.getToken());
        } catch (JSONException e) {
            Timber.e(e);
        }
        RequestUtilsTravel.transportWithJSONObjectResponse(this, TravelPath.FLIGHT_FEE, jsonObject, TravelActionCode.SETFEEFLIGHT, this);
    }
}

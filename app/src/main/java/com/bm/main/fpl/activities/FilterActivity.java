package com.bm.main.fpl.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
//import androidx.appcompat.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import com.bm.main.pos.R;
import com.bm.main.fpl.adapters.FilterTypeAdapter;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.Filter;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.models.NotifTypeModel;
import com.bm.main.fpl.utils.RequestUtils;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends BaseActivity implements View.OnClickListener, ProgressResponseCallback, FilterTypeAdapter.RadicoClickedListener {
    private static final String TAG = FilterActivity.class.getSimpleName();

    RadioButton radio_semua;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recycle_filter;
    List<NotifTypeModel.Response_value> typeList;
    NotifTypeModel.Response_value typeModel;
    NotifTypeModel notifTypeModel;
    FilterTypeAdapter adapter;
    String type = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_filter_ppob);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Notifikasi");
        init(0);
        radio_semua = findViewById(R.id.radio_semua);
        recycle_filter = findViewById(R.id.recycler_filter);
        Intent intent = getIntent();
        if (intent.getStringExtra("type").equalsIgnoreCase("0")) {
            radio_semua.setChecked(true);
            type = intent.getStringExtra("type");
        } else {
            radio_semua.setChecked(false);
            type = intent.getStringExtra("type");
        }


        //    setToolbar();
        requestNotificationList();
    }

    public void onRadioButtonClicked(@NonNull View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        if (view.getId() == R.id.radio_semua && checked) {
            Intent intent = new Intent();
            intent.putExtra("type", Filter.TYPE_SEMUA);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private void setMenu(@NonNull List<NotifTypeModel.Response_value> list) {
        linearLayoutManager = new LinearLayoutManager(this);
        recycle_filter.setLayoutManager(linearLayoutManager);
        typeList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            typeModel = new NotifTypeModel.Response_value(
                    list.get(i).getId_type(),
                    list.get(i).getNama_type(),
                    list.get(i).getUrl_icon());
            typeList.add(typeModel);
        }
        // Initialize da new mInstance of RecyclerView Adapter mInstance
        adapter = new FilterTypeAdapter(type, typeList, this, this);

        // Set the adapter for RecyclerView
        recycle_filter.setAdapter(adapter);
    }
    //   @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_filter, menu);
//        //return true;
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify da parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//        if (id == R.id.action_right_filter){
//            onBackPressed();
//        }else
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

//    public void setToolbar() {
//        toolbar.setTitleTextColor(FormatString.setColor(this,R.color.md_white_1000));
//        toolbar.setNavigationIcon(R.drawable.ic_back);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Notifikasi");
//    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        Log.d(TAG, "onSuccess: " + response.toString());
//        try {
//            if (response.getString("response_code").equals("03")) {
//                if (mProgressDialog != null && mProgressDialog.isShowing()) {
//                    closeProgressBarDialog();
//                }
//                new_popup_alert_session(this, "Informasi", response.getString("response_desc"));
//            } else {
//                Gson gson = new GsonBuilder().create();
                if (actionCode == ActionCode.LIST_NOTIF_KATEGORI) {

                    notifTypeModel = gson.fromJson(response.toString(), NotifTypeModel.class);
                    typeList = notifTypeModel.getResponse_value();
                    setMenu(typeList);
                }
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    closeProgressBarDialog();
                }
//            }
//        } catch (JSONException jsone) {
//            Log.d(TAG, "JSONException: " + jsone.toString());
//        }
    }

    @Override
    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {
        closeProgressBarDialog();
    }

    private void requestNotificationList() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestListKategoryNotif());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.LIST_NOTIF_KATEGORI, this);
// call progress bar disini
/*        final View view = getLayoutInflater().inflate(R.layout.loading_bar_full_dialog, null);
        TextView text = (TextView) view.findViewById(R.id.textContentProgressBar);
        text.setText("Mohon Tunggu, sedang di proses");
        openProgressBarDialog(FilterActivity.this, view);*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void radioClicked(String type) {
        radio_semua.setChecked(false);
        Intent intent = new Intent();
        intent.putExtra("type", type);
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    protected void onDestroy() {
        Glide.get(this).clearMemory();
        super.onDestroy();
    }
}

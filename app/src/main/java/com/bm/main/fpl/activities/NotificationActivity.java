package com.bm.main.fpl.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
//import androidx.appcompat.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.main.scm.R;
import com.bm.main.fpl.adapters.NotificationAdapter;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.constants.Filter;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.models.NotificationModel;
import com.bm.main.fpl.templates.DividerItemDecoration;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.fpl.utils.RequestUtils;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotificationActivity extends BaseActivity implements ProgressResponseCallback, View.OnClickListener, NotificationAdapter.OnClickNotification {
    private static final String TAG = NotificationActivity.class.getSimpleName();
    // Toolbar toolbar;
    RecyclerView recycler_notif;
    //    List<NotificationModel.Response_value> dataFiltered = new ArrayList<>();
    @NonNull
    ArrayList<NotificationModel.Response_value> data = new ArrayList<>();
    NotificationModel notificationModel;
    String choosenFilter = "0";
    LinearLayoutManager linearLayoutManager;
    LinearLayout layout_no_notification;
    NotificationAdapter notificationAdapter;

    // View empty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Notifikasi");
        init(0);
        Log.d(TAG, "onCreate: " + PreferenceClass.getJSONObject("notif_home").toString());
        recycler_notif = findViewById(R.id.recycler_notif);
        layout_no_notification = findViewById(R.id.layout_no_notification);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler_notif.setHasFixedSize(false);
        recycler_notif.setLayoutManager(linearLayoutManager);
        recycler_notif.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        notificationAdapter = new NotificationAdapter(data, this, this);
        recycler_notif.setAdapter(notificationAdapter);
//        empty=findViewById(R.id.layout_data_empty);
//        TextView textHeader=empty.findViewById(R.id.txtHeader);
//        TextView textSubHeader=empty.findViewById(R.id.txtSub);

//        recycler_notif.setEmptyView(empty);
        // requestNotificationHome();
        notificationModel = gson.fromJson(PreferenceClass.getJSONObject("notif_home").toString(), NotificationModel.class);

        data.clear();
        if (notificationModel.getResponse_code().equals(ResponseCode.SUCCESS)) {

            if (notificationModel.getResponse_value().size() > 0) {
                data.addAll(notificationModel.getResponse_value());
                notificationAdapter.notifyDataSetChanged();
                //setData(notificationModel.getData());


                recycler_notif.setVisibility(View.VISIBLE);
                layout_no_notification.setVisibility(View.GONE);
            } else {
                //  textHeader.setText("Pesan Notifikasi");
                //   textSubHeader.setText("Tidak Ada");
                recycler_notif.setVisibility(View.VISIBLE);
                layout_no_notification.setVisibility(View.GONE);
                requestNotificationHome();
            }
        } else {
//            textHeader.setText("Pesan Notifikasi");
//            textSubHeader.setText("Tidak Di temukan");
            recycler_notif.setVisibility(View.GONE);
            layout_no_notification.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        //return true;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify da parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_right_filter) {

            Intent toFilter = new Intent(NotificationActivity.this, FilterActivity.class);
            toFilter.putExtra("type", choosenFilter);
            startActivityForResult(toFilter, Filter.REQ_FILTER);
        } else if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

//    public void setData(List<NotificationModel.Response_value> data) {
////        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
////        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
////        recycler_notif.setHasFixedSize(false);
////        recycler_notif.setLayoutManager(linearLayoutManager);
////        recycler_notif.setAdapter(new NotificationAdapter(data,this,this));
//this.data=data;
//    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        closeProgressBarDialog();
//        try {
//            if (response.getString("response_code").equals("03")) {
//                new_popup_alert_session(this, "Informasi", response.getString("response_desc"));
//            } else {

//                Gson gson = new GsonBuilder().create();
                if (actionCode == ActionCode.LIST_NOTIF_HOME) {
                    Log.d(TAG, "onSuccess: " + response.toString());
                    notificationModel = gson.fromJson(response.toString(), NotificationModel.class);

                    data.clear();
                    if (notificationModel.getResponse_code().equals(ResponseCode.SUCCESS)) {
                        if (notificationModel.getResponse_value().size() > 0) {
                            data.addAll(notificationModel.getResponse_value());
                            notificationAdapter.notifyDataSetChanged();
                            //setData(notificationModel.getData());
                            recycler_notif.setVisibility(View.VISIBLE);
                            layout_no_notification.setVisibility(View.GONE);
                        } else {
                            recycler_notif.setVisibility(View.GONE);
                            layout_no_notification.setVisibility(View.VISIBLE);
                        }
                    } else {

                        recycler_notif.setVisibility(View.GONE);
                        layout_no_notification.setVisibility(View.VISIBLE);

                    }
                }


//            }
//        } catch (JSONException jsone) {
//            Log.d(TAG, "JSONException: " + jsone.toString());
//        }
    }

    @Override
    public void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable) {
        data.clear();
        closeProgressBarDialog();
    }

    private void requestNotificationHome() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringJson.requestListNotifHome());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithProgressResponse(this, jsonObject, ActionCode.LIST_NOTIF_HOME, this);
// call progress bar disini
        ViewGroup parent = findViewById(R.id.contentHost);
        final FrameLayout view =(FrameLayout) View.inflate(this,R.layout.loading_bar_full_dialog, parent);
        TextView text = (TextView) view.findViewById(R.id.textContentProgressBar);
        text.setText("Mohon Tunggu, sedang di proses");
        openProgressBarDialog(NotificationActivity.this, view);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClickNotif(NotificationModel.Response_value notif) {
        Intent toNotificationDetail = new Intent(NotificationActivity.this, NotificationDetail.class);
        toNotificationDetail.putExtra("notif", notif);
        startActivity(toNotificationDetail);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Filter.REQ_FILTER) {
            if (resultCode == RESULT_OK) {
                String type = data.getStringExtra("type");
                //  Log.d(TAG, "onActivityResult: "+type);
                choosenFilter = type;
                if (choosenFilter.equalsIgnoreCase("0")) {
                    // setData(this.data);
                    notificationAdapter.getFilter().filter("");
                } else {
//                    dataFiltered.clear();
                    //  this.data.clear();
//                    for (int i = 0; i < notificationModel.getData().size(); i++) {
//                        if (choosenFilter.equalsIgnoreCase(notificationModel.getData().get(i).getId_tipe_notif())) {
//                            //data.add(notificationModel.getData().get(i));
//                            notificationAdapter.getFilter().filter(dataFiltered);
//                        }
//                    }
                    //setData(dataFiltered);
                    notificationAdapter.getFilter().filter(choosenFilter);

                }
                // notificationAdapter.notifyDataSetChanged();
//                Log.d(TAG, "onActivityResult: "+notificationAdapter.getItemCount());
//                if(notificationAdapter.isFlag==1){
//                    recycler_notif.setVisibility(View.VISIBLE);
//                    empty.setVisibility(View.GONE);
//                }else{
//                    recycler_notif.setVisibility(View.GONE);
//                    empty.setVisibility(View.VISIBLE);
//                }
                //  recycler_notif.setAdapter(notificationAdapter);

                // Toast.makeText(this, "you choose type = " + type, Toast.LENGTH_SHORT).show();
                //filter adapter notif bedasarkan type, lalu notify data change pada adapter
            } else {
                // Toast.makeText(this, "you choose default type = " + 0, Toast.LENGTH_SHORT).show();
                //panggil ulang api notif disini
            }
        }
    }

    @Override
    protected void onDestroy() {
        Glide.get(this).clearMemory();
        super.onDestroy();
    }
}

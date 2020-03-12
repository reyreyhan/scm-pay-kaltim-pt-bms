package com.bm.main.single.ftl.activities;

import androidx.appcompat.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bm.main.fpl.activities.BaseActivity;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.pos.R;
import com.bm.main.pos.SBFApplication;
import com.bm.main.single.ftl.constants.TravelActionCode;
import com.bm.main.single.ftl.constants.TravelPath;
import com.bm.main.single.ftl.utils.RequestUtilsTravel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class TravelDetailPesananActivity extends BaseActivity implements ProgressResponseCallback {

    private static final String TAG = TravelDetailPesananActivity.class.getSimpleName();
    @NonNull
    DataPemesanAdapter dataPemesanAdapter = new DataPemesanAdapter(this);
    String transaction_id, product;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.travel_detail_pesanan_activity);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pembayaran");
        init(0);
        TextView tvTime = findViewById(R.id.tvTime);
        TextView tvPrice = findViewById(R.id.tvPrice);
        TextView tvModul = findViewById(R.id.tvModul);
        AppCompatButton btnBayar = findViewById(R.id.btnBayar);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(dataPemesanAdapter);
        String data = getIntent().getStringExtra("data");
        float price;
        try {
            JSONObject jsonObject = new JSONObject(data);
            price = Float.parseFloat(jsonObject.getString("price"));
            String timeLimit = jsonObject.getString("time_limit");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", SBFApplication.config.locale);
            SimpleDateFormat formatterShow = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm:ss", SBFApplication.config.locale);
            String showDateNow = "";
            Date date;
            try {
                date = formatter.parse(timeLimit);
                showDateNow = formatterShow.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            tvTime.setText("Pembayaran Expired pada :\r\n" + showDateNow);
            tvPrice.setText("Rp. " + String.format("%,.0f", price));
            tvModul.setText("Tiket " + jsonObject.getString("product"));
            final JSONObject dataJson = jsonObject.getJSONObject("data");
            JSONArray base = dataJson.getJSONArray("base");
            JSONArray additional_data = dataJson.getJSONArray("additional_data");
            for (int i = 0; i < base.length(); i++) {
                JSONObject baseItem = base.getJSONObject(i);
                String key = baseItem.getString("key");
                String value = baseItem.getString("value");
                try {
                    dataPemesanAdapter.baseData.add(key + "|||" + value);
                }catch (Exception e){

                }
            }
            for (int i = 0; i < additional_data.length(); i++) {
                JSONObject additionalItem = additional_data.getJSONObject(i);
                String key = additionalItem.getString("key");
                String value = additionalItem.getString("value").equals("")?"-":additionalItem.getString("value");
                try {
                    dataPemesanAdapter.additionalData.add(key + "|||" + value);
                }catch (Exception e){

                }
            }
            dataPemesanAdapter.notifyDataSetChanged();
            transaction_id = jsonObject.getString("transaction_id");
            product = jsonObject.getString("product");
            final float finalPrice = price;
            btnBayar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    JSONObject jsonObject = new JSONObject();
                                    try {
                                        jsonObject.put("transaction_id", transaction_id);
                                        jsonObject.put("product", product);
                                        jsonObject.put("token", PreferenceClass.getToken());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    //Log.d("INFO", "REQUEST : " + jsonObject);
                                    FrameLayout view =(FrameLayout) View.inflate(TravelDetailPesananActivity.this,R.layout.loading_bar_full_dialog, null);
                                    TextView text = view.findViewById(R.id.textContentProgressBar);
                                    text.setText("Mohon Tunggu ...");
                                    openProgressBarDialog(TravelDetailPesananActivity.this, view);
                                    //Log.d(TAG, "onClick: "+jsonObject);
                                    RequestUtilsTravel.transportWithProgressResponseBook_Pay(TravelDetailPesananActivity.this, TravelPath.GLOBAL_PAYMENT, jsonObject, TravelActionCode.GLOBAL_PAYMENT,TravelDetailPesananActivity.this);
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(TravelDetailPesananActivity.this);
                    builder.setMessage("Anda akan melakukan pembayaran dengan total pembayaran senilai Rp " + String.format(SBFApplication.config.locale, "%,.0f", finalPrice) + " ?")
                            .setTitle("Konfirmasi")
                            .setPositiveButton("Ya", dialogClickListener)
                            .setNegativeButton("Tidak", dialogClickListener)
                            .show();

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        }
        else if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onSuccess(int actionCode, @NonNull final JSONObject response)  {
        closeProgressBarDialog();
        //Log.d(TAG, "onSuccess: "+response.toString());
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
        try {
            if (response.getString("rc").equals("00")) {
                JSONObject jsonObject = response.getJSONObject("data");
                //Log.d(TAG, "run: " + response.toString());
                Intent intent = new Intent(TravelDetailPesananActivity.this, TravelStatusPembayaranActivity.class);
                intent.putExtra("status", true);
                intent.putExtra("komisi", jsonObject.getInt("komisi"));
                intent.putExtra("transactionId", jsonObject.getString("transaction_id"));
                intent.putExtra("product", product);
                intent.putExtra("url_etiket", jsonObject.getString("url_etiket"));
                intent.putExtra("url_struk", jsonObject.getString("url_struk"));
                intent.putExtra("rd", response.getString("rd"));
                startActivityForResult(intent, TravelActionCode.IS_FROM_PAY);
            }else if(response.getString("rc").equals("06") && response.getString("rd").startsWith("SALDO")){


                new_popup_alert_session_deposit(this, "Informasi", response.getString("rd"));
            }
            else {
//                showToastCustom(DetailPesananActivity.this, 2, response.getString("rd"));
                snackBarCustomAction(findViewById(R.id.rootLayout),0,response.getString("rd"),2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//            }
//        });
    }

    @Override
    public void onFailure(int actionCode, String responseCode, final String responseDescription, Throwable throwable) {
        closeProgressBarDialog();
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
       // showToastCustom(DetailPesananActivity.this, 1, responseDescription);
       snackBarCustomAction(findViewById(R.id.rootLayout),0,responseDescription,1);
//            }
//        });
        Intent intent = new Intent(this, TravelStatusPembayaranActivity.class);
        intent.putExtra("status", false);
        startActivity(intent);
    }

    @Override
    public void onUpdate(int actionCode,long bytesRead, long totalSize, boolean done) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: " + requestCode + " " + resultCode + " " + data + " ");
        if (requestCode == TravelActionCode.IS_FROM_PAY && resultCode == RESULT_OK) {

            //   Intent intent = new Intent(FlightReviewBookActivity.this, FlightSearchActivity.class);
            if (data != null && data.getAction() != null) {
                Intent intent = new Intent();
                Log.d(TAG, "onActivityResult: " + data.getAction());
                switch (data.getAction()) {
                    case TravelActionCode.MENU_TRAVEL:

                        intent.setAction(TravelActionCode.MENU_TRAVEL);
                        break;
                    case TravelActionCode.MENU_PESAWAT:

                        intent.setAction(TravelActionCode.MENU_PESAWAT);
                        break;
                    case TravelActionCode.MENU_PELNI:

                        intent.setAction(TravelActionCode.MENU_PELNI);
                        break;
                    case TravelActionCode.MENU_KERETA:

                        intent.setAction(TravelActionCode.MENU_KERETA);
                        break;
                }
                setResult(RESULT_OK, intent);
            } else {
                setResult(RESULT_OK);
            }

            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }

    }


    public class DataPemesanAdapter extends BaseAdapter {
        @NonNull
        public List<String> additionalData = new ArrayList<>();
        @NonNull
        public List<String> baseData = new ArrayList<>();
        private Context mContext;

        public DataPemesanAdapter(Context c) {
            mContext = c;
        }

        @Override
        public int getCount() {
            return additionalData.size() + baseData.size() + 2;
        }

        @Nullable
        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        class ViewHolder {
            protected CheckBox checkbox;
        }

        @Override
        public View getView(final int i, View v, ViewGroup viewGroup) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            if (i == 0 || i == additionalData.size() + 1) {
                LinearLayout view = (LinearLayout)View.inflate(mContext,R.layout.detail_pesanan_header, null);
                TextView tvHeader = view.findViewById(R.id.tvHeader);
                if (i == 0)
                    tvHeader.setText("Detail Pesanan");
                else
                    tvHeader.setText("Detail Pemesan");
                return view;
            }
            else {
                String data;
                if (i - 1 < additionalData.size()) {
                    data = additionalData.get(i - 1);
                }
                else {
                    data = baseData.get(i - additionalData.size() - 2);
                }
                LinearLayout view = (LinearLayout)View.inflate(mContext,R.layout.detail_pesanan_item, null);
                TextView tvKey = view.findViewById(R.id.tvKey);
                TextView tvValue = view.findViewById(R.id.tvValue);
                StringTokenizer st = new StringTokenizer(data, "|||");
                tvKey.setText(st.nextToken());
                try {
                    tvValue.setText(st.nextToken());
                }catch (Exception e){
                    tvValue.setText("-");
                }
                return view;
            }
        }
    }
}

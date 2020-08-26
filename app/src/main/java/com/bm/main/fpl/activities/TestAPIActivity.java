package com.bm.main.fpl.activities;

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bm.main.scm.R;
import com.bm.main.fpl.constants.ActionCode;
import com.bm.main.fpl.interfaces.JsonObjectResponseCallback;
import com.bm.main.fpl.utils.RequestUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

//import com.bm.main.fpl.utils.SavePref;

public class TestAPIActivity extends BaseActivity implements JsonObjectResponseCallback,View.OnClickListener {//pastika semua activity extends BaseActivity dan implements ResponseCallback
    private static final String TAG =TestAPIActivity.class.getSimpleName() ;
    private TextView resultTextView;
    private Button getApiBtn,postApiBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_api);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Test API");
        init(0);
        resultTextView  = findViewById(R.id.resultTextView);
        getApiBtn       = findViewById(R.id.getApiBtn);
        postApiBtn      = findViewById(R.id.postApiBtn);

        getApiBtn.setOnClickListener( this);
        postApiBtn.setOnClickListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rumah, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_right_drawer) {
////            Promo promo = new Promo();
////            setFragment(promo);
//            menuTop.setVisibility(View.VISIBLE);
//
//        }
            //   View contentView = getLayoutInflater().inflate(R.layout.dialog_top, toolbar);
            //    contentView.setVisibility(View.VISIBLE);

            openTopDialog(false);
        } else if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccess(int actionCode, @NonNull JSONObject response) {
        if(actionCode==ActionCode.SLIDE_LOGIN) {
            Log.d(TAG, "onSuccess: " + response.toString());
        }
        // cuma sementara //
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(response.toString());
        String prettyJsonString = gson.toJson(je);
        //
        resultTextView.setText(prettyJsonString);

        // close progress bar disini
        closeProgressBarDialog();
    }

    @Override
    public void onFailure(int actionCode, String responseCode, @NonNull String responseDescription, Throwable throwable) {
        if(actionCode==ActionCode.SLIDE_LOGIN) {
            Log.d(TAG, "onFailure: " + actionCode + " " + responseCode + " " + responseDescription);
        }



        resultTextView.setText(responseDescription.toString());
        // close progress bar disini
        closeProgressBarDialog();
    }

    @Override
    public void onClick(@NonNull View view) {
        int viewId=view.getId();
        if(viewId==R.id.postApiBtn){
            //requestLogin();
            requestSliderLogin();
        }

    }

    private void requestSliderLogin() {


        JSONObject jsonObject=new JSONObject() ;
        try {
//           jsonObject.put("token", SavePref.getInstance(TestAPIActivity.this).getString( "token"));
            jsonObject = new JSONObject(stringJson.requestSliderLogin());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithJSONObjectResponse(this, jsonObject, ActionCode.SLIDE_LOGIN,this);

// call progress bar disini
        final View view = getLayoutInflater().inflate(R.layout.loading_bar_full_dialog, null);
        TextView text = (TextView) view.findViewById(R.id.textContentProgressBar);
        text.setText("Mohon Tunggu, sedang di proses");
        openProgressBarDialog(TestAPIActivity.this, view);
    }

    private void requestLogin() {


        JSONObject jsonObject=new JSONObject() ;
        try {
//           jsonObject.put("token", SavePref.getInstance(TestAPIActivity.this).getString( "token"));
            jsonObject = new JSONObject(stringJson.requestSignOn("idnya","pinya","keynya",""));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestUtils.transportWithJSONObjectResponse(TestAPIActivity.this, jsonObject, ActionCode.LOGIN,this);

// call progress bar disini
        final View view = getLayoutInflater().inflate(R.layout.loading_bar_full_dialog, null);
        TextView text = (TextView) view.findViewById(R.id.textContentProgressBar);
        text.setText("Mohon Tunggu, sedang di proses");
        openProgressBarDialog(TestAPIActivity.this, view);
    }
}

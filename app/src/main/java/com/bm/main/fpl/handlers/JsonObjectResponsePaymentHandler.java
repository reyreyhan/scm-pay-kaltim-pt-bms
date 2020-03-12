package com.bm.main.fpl.handlers;

import android.content.res.Resources;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bm.main.pos.R;
import com.bm.main.fpl.constants.FCMConstants;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.JsonObjectResponseCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class JsonObjectResponsePaymentHandler implements Callback {
    private AppCompatActivity context;
    private JsonObjectResponseCallback callback;
    private int actionCode;
    final Resources res;
    private Handler mHandler;
    public JsonObjectResponsePaymentHandler(@NonNull AppCompatActivity context, JsonObjectResponseCallback callback, int actionCode) {
        this.context = context;
        this.callback = callback;
        this.actionCode = actionCode;
        this.res = context.getResources();
//        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onFailure(Call call, final IOException e) {

        context.runOnUiThread(new Runnable() {
//        mHandler.post(new Runnable() {
            @Override
            public void run() {
                FCMConstants.isStillRunningRequest=false;
               // callback.onFailure(actionCode, ResponseCode.NETWORK_ERROR, e.getMessage(), e);
                String pesan = res.getString(
                        R.string.failure_message_payment);
                callback.onFailure(actionCode, ResponseCode.NETWORK_ERROR, pesan, e);
            }
        });
    }

    @Override
    public void onResponse(Call call, @NonNull Response response) throws IOException {
        try {
           // Log.d("onResponse",response.code()+" "+response.networkResponse().code());
            if (response.isSuccessful()) {

                final JSONObject responseJson = new JSONObject(response.body().string());

//                BaseActivity.log(responseJson.toString());

//            final JSONObject data = !responseJson.isNull("data") ? responseJson.getJSONObject("data") : null;

                // final String rc = responseJson.getString("response_code");
                // final String rd = responseJson.getString("response_desc");

//            if(responseJson.getString("response_code").equals("00"))
//            {
                context.runOnUiThread(new Runnable() {
//                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        FCMConstants.isStillRunningRequest=false;
                        callback.onSuccess(actionCode, responseJson);
                    }
                });
//            }
//            else
//            {
//                context.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        callback.onFailure(actionCode, rc, rd, null);
//                    }
//                });
//            }
            } else {
                context.runOnUiThread(new Runnable() {
//                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        FCMConstants.isStillRunningRequest=false;
                        String pesan = res.getString(
                                R.string.failure_message_payment);
                        callback.onFailure(actionCode, ResponseCode.RESPONSE_UNSUCCESSFUL, pesan, new Throwable().fillInStackTrace());
                    }
                });

            }
        } catch (@NonNull final JSONException e) {
            context.runOnUiThread(new Runnable() {
//            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    FCMConstants.isStillRunningRequest=false;
                    String pesan = res.getString(
                            R.string.failure_message_payment);
                    callback.onFailure(actionCode, ResponseCode.RESPONSE_PARSE_ERROR, pesan, e);
                }
            });
        }
    }
}

package com.bm.main.fpl.handlers;

import android.content.res.Resources;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bm.main.scm.R;
import com.bm.main.fpl.constants.FCMConstants;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.BodyObjectResponseCallback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BodyObjectResponseHandler implements Callback
{
    private AppCompatActivity context;
    private BodyObjectResponseCallback callback;
    private int actionCode;
    final Resources res;

    public BodyObjectResponseHandler(@NonNull AppCompatActivity context, BodyObjectResponseCallback callback, int actionCode)
    {
        this.context = context;
        this.callback = callback;
        this.actionCode = actionCode;
        this.res = context.getResources();
    }

    @Override
    public void onFailure(Call call, final IOException e)
    {
       // callback.onFailure(actionCode, "99", e.getMessage(), e);
        context.runOnUiThread(new Runnable() {
            //        mHandler.post(new Runnable() {
            @Override
            public void run() {
                FCMConstants.isStillRunningRequest=false;
                // callback.onFailure(actionCode, ResponseCode.NETWORK_ERROR, e.getMessage(), e);
                String pesan = res.getString(
                        R.string.failure_message);
                callback.onFailure(actionCode, ResponseCode.NETWORK_ERROR, pesan, e);
            }
        });
    }

    @Override
    public void onResponse(Call call, @NonNull Response response) throws IOException
    {
        //String jsonData = response.body().string();
        if(response.isSuccessful())
        {
          //  Log.d("body", "onResponse: "+response.body().string());
//                String responseJson = Html.fromHtml(response.body().string()).toString();
           final String responseJson = response.body().string();

           // callback.onSuccess(actionCode, responseJson);
            context.runOnUiThread(new Runnable() {
                //                    mHandler.post(new Runnable() {
                @Override
                public void run() {
                    FCMConstants.isStillRunningRequest=false;
                    callback.onSuccess(actionCode, responseJson);
                }
            });
        }
        else
        {
            //callback.onFailure(actionCode, "97", response.message(), null);
            context.runOnUiThread(new Runnable() {
                //                mHandler.post(new Runnable() {
                @Override
                public void run() {
                    FCMConstants.isStillRunningRequest=false;
                    String pesan = res.getString(
                            R.string.failure_message, ResponseCode.RESPONSE_UNSUCCESSFUL);
                    callback.onFailure(actionCode, ResponseCode.RESPONSE_UNSUCCESSFUL, pesan, new Throwable().fillInStackTrace());
                }
            });
        }
    }


}
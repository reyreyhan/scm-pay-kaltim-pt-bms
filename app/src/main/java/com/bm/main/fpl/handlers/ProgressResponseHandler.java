package com.bm.main.fpl.handlers;

import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bm.main.fpl.constants.FCMConstants;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.ProgressResponseCallback;
import com.bm.main.scm.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ProgressResponseHandler implements Callback {
    private AppCompatActivity context;
    private ProgressResponseCallback callback;
    private int actionCode;
    final Resources res;

    public ProgressResponseHandler(@NonNull AppCompatActivity context, ProgressResponseCallback callback, int actionCode) {
        this.context = context;
        this.callback = callback;
        this.actionCode = actionCode;
        this.res = context.getResources();
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        context.runOnUiThread(() -> {
            FCMConstants.isStillRunningRequest = false;
            String pesan = res.getString(
                    R.string.failure_message, ResponseCode.NETWORK_ERROR);
            callback.onFailure(actionCode, ResponseCode.NETWORK_ERROR, pesan, e);
        });
    }

    @Override
    public void onResponse(Call call, @NonNull final Response response) throws IOException {
        try {
            if (response.isSuccessful()) {
                final JSONObject responseJson = new JSONObject(response.body().string());
                context.runOnUiThread(() -> {
                    FCMConstants.isStillRunningRequest = false;
                    callback.onSuccess(actionCode, responseJson);

                });
            } else {
                response.body().close();
                context.runOnUiThread(() -> {
                    FCMConstants.isStillRunningRequest = false;

                    String pesan = res.getString(
                            R.string.failure_message, ResponseCode.RESPONSE_UNSUCCESSFUL);
                    callback.onFailure(actionCode, ResponseCode.RESPONSE_UNSUCCESSFUL, pesan, new Throwable().fillInStackTrace());
                });
            }
        } catch (@NonNull final JSONException e) {
            response.body().close();
            context.runOnUiThread(() -> {
                FCMConstants.isStillRunningRequest = false;
                response.body();
                String pesan = res.getString(R.string.failure_message, ResponseCode.RESPONSE_PARSE_ERROR);
                callback.onFailure(actionCode, ResponseCode.RESPONSE_PARSE_ERROR, pesan, e);
            });
        }
    }

    public void onUpdate(long bytesRead, long totalSize, boolean done) {
        callback.onUpdate(actionCode, bytesRead, totalSize, done);
    }
}
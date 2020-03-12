package com.bm.main.fpl.handlers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.JsonArrayResponseCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

//import com.fastravel.android.core.AppContext;

public class JsonArrayResponseHandler implements Callback
{
    private AppCompatActivity context;
    private JsonArrayResponseCallback callback;
    private int actionCode;

    public JsonArrayResponseHandler(AppCompatActivity context, JsonArrayResponseCallback callback, int actionCode)
    {
        this.context = context;
        this.callback = callback;
        this.actionCode = actionCode;
    }

    @Override
    public void onFailure(Call call, final IOException e)
    {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                callback.onFailure(actionCode, ResponseCode.NETWORK_ERROR, e.getMessage(), e);
            }
        });
    }

    @Override
    public void onResponse(Call call, @NonNull final Response response) throws IOException
    {
        try
        {
            if(response.isSuccessful())
            {
                JSONObject responseJson = new JSONObject(response.body().string());

              //  BaseActivity.log(responseJson.toString());

                final JSONArray data = !responseJson.isNull("data") ? responseJson.getJSONArray("data") : null;
                final String rc = responseJson.getString("rc");
                final String rd = responseJson.getString("rd");

            //    BaseActivity.log(responseJson.toString());

                if(responseJson.getString("rc").equals("00"))
                {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(actionCode, data);
                        }
                    });
                }
                else
                {
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailure(actionCode, rc, rd, null);
                        }
                    });
                }
            }
            else
            {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFailure(actionCode, ResponseCode.RESPONSE_UNSUCCESSFUL, response.message(), null);
                    }
                });
            }
        }
        catch (@NonNull final JSONException e)
        {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    callback.onFailure(actionCode, ResponseCode.RESPONSE_PARSE_ERROR, e.getMessage(), e);
                }
            });
        }
    }
}
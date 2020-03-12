package com.bm.main.fpl.handlers;

import androidx.annotation.NonNull;

import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.interfaces.PlainTextResponseCallback;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PlainTextResponseHandler implements Callback
{
    private PlainTextResponseCallback callback;
    private int actionCode;

    public PlainTextResponseHandler(PlainTextResponseCallback callback, int actionCode)
    {
        this.callback = callback;
        this.actionCode = actionCode;
    }

    @Override
    public void onFailure(Call call, IOException e)
    {
        callback.onFailure(actionCode, ResponseCode.NETWORK_ERROR, e.getMessage(), e);
    }

    @Override
    public void onResponse(Call call, @NonNull Response response) throws IOException
    {
        if(response.isSuccessful())
        {
            callback.onSuccess(actionCode, response.body().string());
        }
        else
        {
            callback.onFailure(actionCode, ResponseCode.RESPONSE_UNSUCCESSFUL, response.message(), null);
        }
    }
}
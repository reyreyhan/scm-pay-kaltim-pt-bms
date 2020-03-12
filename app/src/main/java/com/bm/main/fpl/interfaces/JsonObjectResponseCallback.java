package com.bm.main.fpl.interfaces;

import org.json.JSONObject;

public interface JsonObjectResponseCallback
{
    void onSuccess(int actionCode, JSONObject response);
    void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable);
}

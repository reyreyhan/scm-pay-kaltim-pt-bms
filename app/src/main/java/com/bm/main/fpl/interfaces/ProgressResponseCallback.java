package com.bm.main.fpl.interfaces;

import org.json.JSONObject;

public interface ProgressResponseCallback
{
     void onSuccess(int actionCode, JSONObject response);
     void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable);
     void onUpdate(int actionCode,long bytesRead,long totalSize,boolean done);
}

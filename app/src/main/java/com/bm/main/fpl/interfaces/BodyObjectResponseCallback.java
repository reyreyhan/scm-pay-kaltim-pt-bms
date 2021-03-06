package com.bm.main.fpl.interfaces;

public interface BodyObjectResponseCallback
{
    void onSuccess(int actionCode, String response);
    void onFailure(int actionCode, String responseCode, String responseDescription, Throwable throwable);
}

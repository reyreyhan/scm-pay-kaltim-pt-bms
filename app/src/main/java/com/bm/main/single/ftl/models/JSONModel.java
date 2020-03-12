package com.bm.main.single.ftl.models;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rizabudiprasetya on 6/27/17.
 */

public class JSONModel {


    String getStringValue(@NonNull JSONObject obj, String key) throws JSONException {
        if(obj.has(key)) {
            return obj.getString(key);
        }

        return "";
    }

    int getIntValue(@NonNull JSONObject obj, String key) throws JSONException {
        if(obj.has(key)) {
            return obj.getInt(key);
        }

        return 0;
    }

    double getDoubleValue(@NonNull JSONObject obj, String key) throws JSONException {
        if(obj.has(key)) {
            return obj.getDouble(key);
        }

        return 0;
    }

    boolean getBooleanValue(@NonNull JSONObject obj, String key) throws JSONException {
        if(obj.has(key)) {
            return obj.getBoolean(key);
        }

        return false;
    }
}

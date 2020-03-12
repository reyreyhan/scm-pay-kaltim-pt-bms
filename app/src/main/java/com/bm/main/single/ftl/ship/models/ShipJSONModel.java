package com.bm.main.single.ftl.ship.models;

import androidx.annotation.NonNull;

import com.bm.main.single.ftl.models.BaseObject;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rizabudiprasetya on 6/27/17.
 */

public class ShipJSONModel extends BaseObject {


    String getStringValue(@NonNull JSONObject obj, String key) throws JSONException {
        if(obj.has(key)) {
            return obj.getString(key);
        }

        return "";
    }
    String getStringValue(@NonNull String obj, String key) throws JSONException {
        if(obj.equals(key)) {
            return key;
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

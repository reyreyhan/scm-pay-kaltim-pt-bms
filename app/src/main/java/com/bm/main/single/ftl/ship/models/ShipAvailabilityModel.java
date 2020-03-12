package com.bm.main.single.ftl.ship.models;

import androidx.annotation.NonNull;

import org.json.JSONException;

/**
 * Created by rizabudiprasetya on 7/30/17.
 */

public class ShipAvailabilityModel extends ShipJSONModel {
    private String KEY;
    private String M;
    private String F;

    public ShipAvailabilityModel(@NonNull String key, String jsonObject) throws JSONException {
        this.KEY = key;
if(key.equals("M")){
    setM(jsonObject);
}else if(key.equals("F")){
    setF(jsonObject);
}
//        this.M = getStringValue(jsonObject, "M");
//        this.F = getStringValue(jsonObject, "F");

    }



    public String getKEY() {
        return KEY;
    }

    public void setKEY(String KEY) {

        this.KEY = KEY;
    }

    public String getM() {
        return M;
    }

    public void setM(String m) {
        M = m;
    }

    public String getF() {
        return F;
    }

    public void setF(String f) {
        F = f;
    }
}

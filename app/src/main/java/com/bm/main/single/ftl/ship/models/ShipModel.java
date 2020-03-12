package com.bm.main.single.ftl.ship.models;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by rizabudiprasetya on 7/27/17.
 */

public class ShipModel extends ShipJSONModel implements Comparable<ShipModel>{
    private String ORG_CALL, DEP_DATE, SHIP_NAME, SHIP_NO, ARV_TIME, ROUTE, ARV_DATE, DEP_TIME, DES_CALL;
    private ArrayList<ShipFareModel> FARES;

    private JSONObject jsonObject;

    public ShipModel(@NonNull JSONObject jsonObject) throws JSONException {
        this.ORG_CALL = getStringValue(jsonObject, "ORG_CALL");
        this.DEP_DATE = getStringValue(jsonObject, "DEP_DATE");
        this.SHIP_NAME = getStringValue(jsonObject, "SHIP_NAME");
        this.SHIP_NO = getStringValue(jsonObject, "SHIP_NO");
        this.ARV_TIME = getStringValue(jsonObject, "ARV_TIME");
        this.ROUTE = getStringValue(jsonObject, "ROUTE");
        this.ARV_DATE = getStringValue(jsonObject, "ARV_DATE");
        this.DEP_TIME = getStringValue(jsonObject, "DEP_TIME");
        this.DES_CALL = getStringValue(jsonObject, "DES_CALL");

        this.jsonObject = jsonObject;

        FARES = new ArrayList<>();
        JSONArray fares = jsonObject.getJSONArray("fares");
        for(int i = 0; i < fares.length(); i++) {
            ShipFareModel shipFareModel = new ShipFareModel(fares.getJSONObject(i));
            shipFareModel.setShipModel(this);
            FARES.add(shipFareModel);
        }
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public ArrayList<ShipFareModel> getFARES() {
        return FARES;
    }

    public void setFARES(ArrayList<ShipFareModel> FARES) {
        this.FARES = FARES;
    }

    public String getORG_CALL() {
        return ORG_CALL;
    }

    public void setORG_CALL(String ORG_CALL) {
        this.ORG_CALL = ORG_CALL;
    }

    public String getDEP_DATE() {
        return DEP_DATE;
    }

    public void setDEP_DATE(String DEP_DATE) {
        this.DEP_DATE = DEP_DATE;
    }

    public String getSHIP_NAME() {
        return SHIP_NAME;
    }

    public void setSHIP_NAME(String SHIP_NAME) {
        this.SHIP_NAME = SHIP_NAME;
    }

    public String getSHIP_NO() {
        return SHIP_NO;
    }

    public void setSHIP_NO(String SHIP_NO) {
        this.SHIP_NO = SHIP_NO;
    }

    public String getARV_TIME() {
        return ARV_TIME;
    }

    public void setARV_TIME(String ARV_TIME) {
        this.ARV_TIME = ARV_TIME;
    }

    public String getROUTE() {
        return ROUTE;
    }

    public void setROUTE(String ROUTE) {
        this.ROUTE = ROUTE;
    }

    public String getARV_DATE() {
        return ARV_DATE;
    }

    public void setARV_DATE(String ARV_DATE) {
        this.ARV_DATE = ARV_DATE;
    }

    public String getDEP_TIME() {
        return DEP_TIME;
    }

    public void setDEP_TIME(String DEP_TIME) {
        this.DEP_TIME = DEP_TIME;
    }

    public String getDES_CALL() {
        return DES_CALL;
    }

    public void setDES_CALL(String DES_CALL) {
        this.DES_CALL = DES_CALL;
    }

    @Override
    public int compareTo(@NonNull ShipModel o) {
        return (getDEP_DATE()+getDEP_TIME()).compareTo(o.getDEP_DATE()+o.getDEP_TIME());
    }
}

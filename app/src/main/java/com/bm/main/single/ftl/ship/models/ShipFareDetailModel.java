package com.bm.main.single.ftl.ship.models;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rizabudiprasetya on 7/30/17.
 */

public class ShipFareDetailModel extends ShipJSONModel {
    private String KEY;
    private Double ARV_PORT_TRANSPORT_FEE, PORT_PASS, INSURANCE, FARE, TOTAL, DEP_PORT_TRANSPORT_FEE;

    public ShipFareDetailModel(String key, @NonNull JSONObject jsonObject) throws JSONException {
        this.KEY = key;

        this.ARV_PORT_TRANSPORT_FEE = getDoubleValue(jsonObject, "ARV_PORT_TRANSPORT_FEE");
        this.PORT_PASS = getDoubleValue(jsonObject, "PORT_PASS");
        this.INSURANCE = getDoubleValue(jsonObject, "INSURANCE");
        this.FARE = getDoubleValue(jsonObject, "FARE");
        this.TOTAL = getDoubleValue(jsonObject, "TOTAL");
        this.DEP_PORT_TRANSPORT_FEE = getDoubleValue(jsonObject, "DEP_PORT_TRANSPORT_FEE");
    }

    public String getKEY() {
        return KEY;
    }

    public void setKEY(String KEY) {
        this.KEY = KEY;
    }

    public double getARV_PORT_TRANSPORT_FEE() {
        return ARV_PORT_TRANSPORT_FEE;
    }

    public void setARV_PORT_TRANSPORT_FEE(double ARV_PORT_TRANSPORT_FEE) {
        this.ARV_PORT_TRANSPORT_FEE = ARV_PORT_TRANSPORT_FEE;
    }

    public double getPORT_PASS() {
        return PORT_PASS;
    }

    public void setPORT_PASS(double PORT_PASS) {
        this.PORT_PASS = PORT_PASS;
    }

    public double getINSURANCE() {
        return INSURANCE;
    }

    public void setINSURANCE(double INSURANCE) {
        this.INSURANCE = INSURANCE;
    }

    public double getFARE() {
        return FARE;
    }

    public void setFARE(double FARE) {
        this.FARE = FARE;
    }

    public double getTOTAL() {
        return TOTAL;
    }

    public void setTOTAL(double TOTAL) {
        this.TOTAL = TOTAL;
    }

    public double getDEP_PORT_TRANSPORT_FEE() {
        return DEP_PORT_TRANSPORT_FEE;
    }

    public void setDEP_PORT_TRANSPORT_FEE(double DEP_PORT_TRANSPORT_FEE) {
        this.DEP_PORT_TRANSPORT_FEE = DEP_PORT_TRANSPORT_FEE;
    }
}

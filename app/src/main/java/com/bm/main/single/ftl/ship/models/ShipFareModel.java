package com.bm.main.single.ftl.ship.models;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rizabudiprasetya on 7/30/17.
 */

public class ShipFareModel extends ShipJSONModel implements Comparable<ShipFareModel>{
    private String SUBCLASS, AVAILABILITY, CLASS;
    private ShipFareDetailModel fareDetailA, fareDetailC, fareDetailI;
    private ShipAvailabilityModel shipAvailabilityM,shipAvailabilityF;

    private ShipModel shipModel;
    private boolean isExpand = false;
    private boolean isSelected=false;
    private String tagButton;


    private JSONObject jsonObject;

    public boolean isExpand() {
        return isExpand;
    }
    public boolean isSelected() {
        return isSelected;
    }

    public String getTagButton() {
        return tagButton;
    }

    public void setTagButton(String tagButton) {
        this.tagButton = tagButton;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }
    public void setSelected(boolean select) {
        isSelected = select;
    }

    public ShipFareModel(@NonNull JSONObject jsonObject) throws JSONException {
        this.SUBCLASS = getStringValue(jsonObject, "SUBCLASS");
        this.AVAILABILITY = getStringValue(jsonObject, "AVAILABILITY");
        this.CLASS = getStringValue(jsonObject, "CLASS");

        shipAvailabilityM = new ShipAvailabilityModel("M", jsonObject.getJSONObject("AVAILABILITY").getString("M"));
        shipAvailabilityF = new ShipAvailabilityModel("F", jsonObject.getJSONObject("AVAILABILITY").getString("F"));

        fareDetailA = new ShipFareDetailModel("A", jsonObject.getJSONObject("FARE_DETAIL").getJSONObject("A"));
        fareDetailC = new ShipFareDetailModel("C", jsonObject.getJSONObject("FARE_DETAIL").getJSONObject("C"));
        fareDetailI = new ShipFareDetailModel("I", jsonObject.getJSONObject("FARE_DETAIL").getJSONObject("I"));

        this.jsonObject = jsonObject;
    }

    public void setFareDetailI(ShipFareDetailModel fareDetailI) {
        this.fareDetailI = fareDetailI;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public ShipModel getShipModel() {
        return shipModel;
    }

    public void setShipModel(ShipModel shipModel) {
        this.shipModel = shipModel;
    }

    public String getSUBCLASS() {
        return SUBCLASS;
    }

    public void setSUBCLASS(String SUBCLASS) {
        this.SUBCLASS = SUBCLASS;
    }

    public String getAVAILABILITY() {
        return AVAILABILITY;
    }

    public void setAVAILABILITY(String AVAILABILITY) {
        this.AVAILABILITY = AVAILABILITY;
    }

    public String getCLASS() {
        return CLASS;
    }

    public void setCLASS(String CLASS) {
        this.CLASS = CLASS;
    }

    public ShipFareDetailModel getFareDetailA() {
        return fareDetailA;
    }

    public void setFareDetailA(ShipFareDetailModel fareDetailA) {
        this.fareDetailA = fareDetailA;
    }

    public ShipFareDetailModel getFareDetailI() {
        return fareDetailI;
    }

    public void setFareDetailB(ShipFareDetailModel fareDetailI) {
        this.fareDetailI = fareDetailI;
    }

    public ShipFareDetailModel getFareDetailC() {
        return fareDetailC;
    }

    public void setFareDetailC(ShipFareDetailModel fareDetailC) {
        this.fareDetailC = fareDetailC;
    }

    public ShipAvailabilityModel getShipAvailabilityM() {
        return shipAvailabilityM;
    }

    public void setShipAvailabilityM(ShipAvailabilityModel shipAvailabilityM) {
        this.shipAvailabilityM = shipAvailabilityM;
    }

    public ShipAvailabilityModel getShipAvailabilityF() {
        return shipAvailabilityF;
    }

    public void setShipAvailabilityF(ShipAvailabilityModel shipAvailabilityF) {
        this.shipAvailabilityF = shipAvailabilityF;
    }

    @Override
    public int compareTo(@NonNull ShipFareModel o) {
        return getTagButton().compareTo(o.getTagButton());
    }
}

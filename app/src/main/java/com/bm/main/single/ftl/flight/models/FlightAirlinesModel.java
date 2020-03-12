package com.bm.main.single.ftl.flight.models;

import java.io.Serializable;

/**
 * Created by sarifhidayat on 8/25/17.
 */

public class FlightAirlinesModel implements Serializable {

    boolean checked;
    private String airLineCode;
    private String airLineNama;
    private String airLineIcon;
    private int airLinePrice;

    public FlightAirlinesModel() {

    }

    public void toggleChecked() {
        checked = !checked;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getAirLineCode() {
        return airLineCode;
    }

    public void setAirLineCode(String airLineCode) {
        this.airLineCode = airLineCode;
    }

    public String getAirLineNama() {
        return airLineNama;
    }

    public void setAirLineNama(String airLineNama) {
        this.airLineNama = airLineNama;
    }

    public String getAirLineIcon() {
        return airLineIcon;
    }

    public void setAirLineIcon(String airLineIcon) {
        this.airLineIcon = airLineIcon;
    }

    public int getAirLinePrice() {
        return airLinePrice;
    }

    public void setAirLinePrice(int airLinePrice) {
        this.airLinePrice = airLinePrice;
    }
}

package com.bm.main.single.ftl.flight.models;

/**
 * Created by sarifhidayat on 9/4/17.
 */

public class FlightAirlinesPriceModel {

    private String airLinesCodeModel;
    private int airLinesPriceModel;

    public FlightAirlinesPriceModel(){

    }

    public String getAirLinesCodeModel() {
        return airLinesCodeModel;
    }

    public void setAirLinesCodeModel(String airLinesCodeModel) {
        this.airLinesCodeModel = airLinesCodeModel;
    }

    public int getAirLinesPriceModel() {
        return airLinesPriceModel;
    }

    public void setAirLinesPriceModel(int airLinesPrice) {
        this.airLinesPriceModel = airLinesPrice;
    }
}

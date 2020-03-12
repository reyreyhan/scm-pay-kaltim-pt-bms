package com.bm.main.single.ftl.flight.models;

import androidx.annotation.NonNull;

/**
 * Created by pratap.kesaboyina on 01-12-2015.
 */
public class SingleItemBaggageModel implements Comparable<SingleItemBaggageModel> {


    boolean checked;
    private String price;
    private String weight;
    private String baggage_key;
    private String flight_code;


    public SingleItemBaggageModel() {
    }

    public SingleItemBaggageModel(String price, String weight,String baggage_key,String flight_code,boolean checked) {
        this.price =price;
        this.weight = weight;
        this.baggage_key = baggage_key;
        this.flight_code = flight_code;
        this.checked =  checked;
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


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBaggage_key() {
        return baggage_key;
    }

    public void setBaggage_key(String baggage_key) {
        this.baggage_key = baggage_key;
    }

    @Override
    public int compareTo(@NonNull SingleItemBaggageModel o) {
        return getBaggage_key().compareTo(o.getBaggage_key());
    }

    public String getFlight_code() {
        return flight_code;
    }

    public void setFlight_code(String flight_code) {
        this.flight_code = flight_code;
    }
}

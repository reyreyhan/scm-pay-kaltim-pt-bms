package com.bm.main.single.ftl.flight.models;

public class FlightBaggageBookModel {
    private String kode_maskapai;
    private int weight;
    private String baggage_key;

    public String getKode_maskapai() {
        return this.kode_maskapai;
    }

    public void setKode_maskapai(String kode_maskapai) {
        this.kode_maskapai = kode_maskapai;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getBaggage_key() {
        return this.baggage_key;
    }

    public void setBaggage_key(String baggage_key) {
        this.baggage_key = baggage_key;
    }
}

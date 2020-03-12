package com.bm.main.single.ftl.train.models;

/**
 * Created by sarifhidayat on 9/14/17.
 */

public class TrainKelasFilterModel {
    private String kelasKey;
    private String kelasShow;
    boolean checkbox;
  public TrainKelasFilterModel(){

  }

    public String getKelasKey() {
        return kelasKey;
    }

    public void setKelasKey(String kelasKey) {
        this.kelasKey = kelasKey;
    }

    public String getKelasShow() {
        return kelasShow;
    }

    public void setKelasShow(String kelasShow) {
        this.kelasShow = kelasShow;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }
}

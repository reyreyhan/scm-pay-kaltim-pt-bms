package com.bm.main.single.ftl.train.models;

/**
 * Created by sarifhidayat on 9/14/17.
 */

public class TrainFilterModel {
    private String keretaKey;
    private String keretaShow;
    boolean checkbox;
  public TrainFilterModel(){

  }

    public String getKeretaKey() {
        return keretaKey;
    }

    public void setKeretaKey(String keretaKey) {
        this.keretaKey = keretaKey;
    }

    public String getKeretaShow() {
        return keretaShow;
    }

    public void setKeretaShow(String keretaShow) {
        this.keretaShow = keretaShow;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }
}

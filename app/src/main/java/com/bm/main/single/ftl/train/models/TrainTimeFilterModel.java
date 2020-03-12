package com.bm.main.single.ftl.train.models;

import java.io.Serializable;

/**
 * Created by sarifhidayat on 9/14/17.
 */

public class TrainTimeFilterModel implements Serializable{
    private String timeKey;
    private String timeShow;
    private String kelas;
    private String grade;
    boolean checkbox;
  public TrainTimeFilterModel(){

  }

    public String getTimeKey() {
        return timeKey;
    }

    public void setTimeKey(String timeKey) {
        this.timeKey = timeKey;
    }

    public String getTimeShow() {
        return timeShow;
    }

    public void setTimeShow(String timeShow) {
        this.timeShow = timeShow;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}

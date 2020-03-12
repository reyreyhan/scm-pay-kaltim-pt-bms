package com.bm.main.fpl.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by papahnakal on 10/11/17.
 */

public class KeyRequest extends BaseObject{
    @SerializedName("start_date")
    private String start_date;
    @SerializedName("end_date")
    private String end_date;
    @SerializedName("day_limit")
    private int day_limit;

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getDay_limit() {
        return day_limit;
    }

    public void setDay_limit(int day_limit) {
        this.day_limit = day_limit;
    }
}

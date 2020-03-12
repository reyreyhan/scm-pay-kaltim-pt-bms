package com.bm.main.single.ftl.models;

import com.google.gson.annotations.SerializedName;


public class BaseObject {
    @SerializedName("rc")
    private String rc;
    @SerializedName("rd")
    private String rd;
 @SerializedName("mid")
    private String mid;

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public String getRd() {
        return rd;
    }

    public void setRd(String rd) {
        this.rd = rd;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
}

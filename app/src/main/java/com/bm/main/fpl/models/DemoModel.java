package com.bm.main.fpl.models;

import com.google.gson.annotations.SerializedName;

public class DemoModel extends BaseObject  {




        @SerializedName("id_outlet_demo")
        private String id_outlet_demo;
        @SerializedName("pin_outlet_demo")
        private String pin_outlet_demo;
    @SerializedName("key_outlet_demo")
    private String key_outlet_demo;

    @SerializedName("edukasi_login")
    private String edukasi_login;

    public String getId_outlet_demo() {
        return id_outlet_demo;
    }

    public void setId_outlet_demo(String id_outlet_demo) {
        this.id_outlet_demo = id_outlet_demo;
    }

    public String getPin_outlet_demo() {
        return pin_outlet_demo;
    }

    public void setPin_outlet_demo(String pin_outlet_demo) {
        this.pin_outlet_demo = pin_outlet_demo;
    }

    public String getKey_outlet_demo() {
        return key_outlet_demo;
    }

    public void setKey_outlet_demo(String key_outlet_demo) {
        this.key_outlet_demo = key_outlet_demo;
    }

    public String getEdukasi_login() {
        return edukasi_login;
    }

    public void setEdukasi_login(String edukasi_login) {
        this.edukasi_login = edukasi_login;
    }

}

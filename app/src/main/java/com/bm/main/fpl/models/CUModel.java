package com.bm.main.fpl.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by papahnakal on 10/11/17.
 */

public class CUModel extends BaseObject implements Serializable{

    @SerializedName("reff_id_pay")
    private String reff_id_pay;
    @SerializedName("saldo")
    private String saldo;
    @SerializedName("struk_tercetak")
    private String struk_tercetak;
    @SerializedName("struk_show")
    private String struk_show;


    public String getReff_id_pay() {
        return reff_id_pay;
    }

    public void setReff_id_pay(String reff_id_pay) {
        this.reff_id_pay = reff_id_pay;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getStruk_tercetak() {
        return struk_tercetak;
    }

    public void setStruk_tercetak(String struk_tercetak) {
        this.struk_tercetak = struk_tercetak;
    }

    public String getStruk_show() {
        return struk_show;
    }

    public void setStruk_show(String struk_show) {
        this.struk_show = struk_show;
    }
}

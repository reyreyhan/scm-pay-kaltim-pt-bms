package com.bm.main.fpl.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by papahnakal on 10/11/17.
 */

public class InqModel extends BaseObject implements Serializable{

    @SerializedName("reff_id_inq")
    private String reff_id_inq;
    @SerializedName("nominal_inq")
    private String nominal_inq;
    @SerializedName("nominal_admin_inq")
    private String nominal_admin_inq;
    @SerializedName("saldo")
    private String saldo;
    @SerializedName("komisi_produk")
    private String komisi_produk;
    @SerializedName("struk_show")
    private String struk_show;



    public String getReff_id_inq() {
        return reff_id_inq;
    }

    public void setReff_id_inq(String reff_id_inq) {
        this.reff_id_inq = reff_id_inq;
    }

    public String getNominal_inq() {
        return nominal_inq;
    }

    public void setNominal_inq(String nominal_inq) {
        this.nominal_inq = nominal_inq;
    }

    public String getNominal_admin_inq() {
        return nominal_admin_inq;
    }

    public void setNominal_admin_inq(String nominal_admin_inq) {
        this.nominal_admin_inq = nominal_admin_inq;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getKomisi_produk() {
        return komisi_produk;
    }

    public void setKomisi_produk(String komisi_produk) {
        this.komisi_produk = komisi_produk;
    }

    public String getStruk_show() {
        return struk_show;
    }

    public void setStruk_show(String struk_show) {
        this.struk_show = struk_show;
    }
}

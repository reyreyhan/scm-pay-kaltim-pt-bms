
package com.bm.main.fpl.models;


import com.google.gson.annotations.SerializedName;


public class AjakBisnisModel extends BaseObject {

    @SerializedName("keterangan_ajak_bisnis")
    private String keteranganAjakBisnis;


    public String getKeteranganAjakBisnis() {
        return keteranganAjakBisnis;
    }

    public void setKeteranganAjakBisnis(String keteranganAjakBisnis) {
        this.keteranganAjakBisnis = keteranganAjakBisnis;
    }


}

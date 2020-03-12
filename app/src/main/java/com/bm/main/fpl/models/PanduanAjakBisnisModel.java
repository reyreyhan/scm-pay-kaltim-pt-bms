
package com.bm.main.fpl.models;


import com.google.gson.annotations.SerializedName;


public class PanduanAjakBisnisModel extends BaseObject {

    @SerializedName("header_panduan")
    private String headerPanduan;
    @SerializedName("keterangan_panduan")
    private String keteranganPanduan;

    @SerializedName("selangkapnya_panduan")
    private String selangkapnyaPanduan;
    @SerializedName("basic_value")
    private String basic_value;
    @SerializedName("pro_value")
    private String pro_value;
    @SerializedName("enterprise_value")
    private String enterprise_value;


    @SerializedName("youtube_panduan")
    private String youtubePanduan;

    public String getHeaderPanduan() {
        return headerPanduan;
    }

    public void setHeaderPanduan(String headerPanduan) {
        this.headerPanduan = headerPanduan;
    }

    public String getKeteranganPanduan() {
        return keteranganPanduan;
    }

    public void setKeteranganPanduan(String keteranganPanduan) {
        this.keteranganPanduan = keteranganPanduan;
    }


    public String getSelangkapnyaPanduan() {
        return selangkapnyaPanduan;
    }

    public void setSelangkapnyaPanduan(String selangkapnyaPanduan) {
        this.selangkapnyaPanduan = selangkapnyaPanduan;
    }

    public String getBasic_value() {
        return basic_value;
    }

    public void setBasic_value(String basic_value) {
        this.basic_value = basic_value;
    }

    public String getPro_value() {
        return pro_value;
    }

    public void setPro_value(String pro_value) {
        this.pro_value = pro_value;
    }

    public String getEnterprise_value() {
        return enterprise_value;
    }

    public void setEnterprise_value(String enterprise_value) {
        this.enterprise_value = enterprise_value;
    }

    public String getYoutubePanduan() {
        return youtubePanduan;
    }

    public void setYoutubePanduan(String youtubePanduan) {
        this.youtubePanduan = youtubePanduan;
    }

}

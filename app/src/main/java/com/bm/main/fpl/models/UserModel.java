package com.bm.main.fpl.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by papahnakal on 09/11/17.
 */

public class UserModel extends BaseObject{

    @SerializedName("session_id")
    private String session_id;
    @SerializedName("expired_time")
    private String expired_time;
    @SerializedName("id_outlet")
    private String id_outlet;
    @SerializedName("saldo")
    private String saldo;
    @SerializedName("alias")
    private String alias;
    @SerializedName("rating")
    private String rating;
    @SerializedName("nama_pemilik")
    private String nama_pemilik;
    @SerializedName("notelp_pemilik")
    private String notelp_pemilik;

    @SerializedName("alamat_pemilik")
    private String alamat_pemilik;
    @SerializedName("email_pemilik")
    private String email_pemilik;
    @SerializedName("nama_loket")
    private String nama_loket;
    @SerializedName("alamat_loket")
    private String alamat_loket;
    @SerializedName("no_whatsapp_loket")
    private String no_whatsapp_loket;
    @SerializedName("notelp_loket")
    private String notelp_loket;

    @SerializedName("nama_logo")
    private String nama_logo;
    @SerializedName("cek_sum_logo")
    private String cek_sum_logo;
    @SerializedName("link_afiliasi")
    private String link_afiliasi;
    @SerializedName("web_report")
    private String web_report;
    @SerializedName("keagenan")
    private String keagenan;
    @SerializedName("perbankan")
    private String perbankan;
    @SerializedName("belanja")
    private String belanja;
    @SerializedName("bayar")
    private String bayar;
    @SerializedName("lion_parcel")
    private String lion_parcel;

    @SerializedName("pinjaman_dana")
    private String pinjaman_dana;
    @SerializedName("tunaiku")
    private String tunaiku;


    @SerializedName("fastravel")
    private String fastravel;

    @SerializedName("edukasi")
    private String edukasi;
    @SerializedName("upgrade")
    private String upgrade;
    @SerializedName("affiliasi")
    private String affiliasi;


    @SerializedName("id_upline")
    private String id_upline;

    @SerializedName("nama_upline")
    private String nama_upline;
    @SerializedName("notlp_upline")
    private String notlp_upline;

    @SerializedName("content_awal_otp")
    private String content_awal_otp;


    @SerializedName("isi_content_otp")
    private String isi_content_otp;
    @SerializedName("grosir")
    private String grosir;
    @SerializedName("isCoverGrosir")
    private String isCoverGrosir;

    @SerializedName("keyPos")
    private String keyPos;
//    @SerializedName("list_produk")
////    private String[] list_produk;
//    private XList_produk[] list_produk;



    public UserModel(String session_id) {
        this.session_id = session_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getExpired_time() {
        return expired_time;
    }

    public void setExpired_time(String expired_time) {
        this.expired_time = expired_time;
    }

    public String getId_outlet() {
        return id_outlet;
    }

    public void setId_outlet(String id_outlet) {
        this.id_outlet = id_outlet;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getNama_pemilik() {
        return nama_pemilik;
    }

    public void setNama_pemilik(String nama_pemilik) {
        this.nama_pemilik = nama_pemilik;
    }

    public String getNotelp_pemilik() {
        return notelp_pemilik;
    }

    public void setNotelp_pemilik(String notelp_pemilik) {
        this.notelp_pemilik = notelp_pemilik;
    }


    public String getAlamat_pemilik() {
        return alamat_pemilik;
    }

    public void setAlamat_pemilik(String alamat_pemilik) {
        this.alamat_pemilik = alamat_pemilik;
    }

    public String getEmail_pemilik() {
        return email_pemilik;
    }

    public void setEmail_pemilik(String email_pemilik) {
        this.email_pemilik = email_pemilik;
    }

    public String getNama_loket() {
        return nama_loket;
    }

    public void setNama_loket(String nama_loket) {
        this.nama_loket = nama_loket;
    }

    public String getAlamat_loket() {
        return alamat_loket;
    }

    public void setAlamat_loket(String alamat_loket) {
        this.alamat_loket = alamat_loket;
    }

    public String getNo_whatsapp_loket() {
        return no_whatsapp_loket;
    }

    public void setNo_whatsapp_loket(String no_whatsapp_loket) {
        this.no_whatsapp_loket = no_whatsapp_loket;
    }

    public String getNotelp_loket() {
        return notelp_loket;
    }

    public void setNotelp_loket(String notelp_loket) {
        this.notelp_loket = notelp_loket;
    }

    public String getNama_logo() {
        return nama_logo;
    }

    public void setNama_logo(String nama_logo) {
        this.nama_logo = nama_logo;
    }

    public String getCek_sum_logo() {
        return cek_sum_logo;
    }

    public void setCek_sum_logo(String cek_sum_logo) {
        this.cek_sum_logo = cek_sum_logo;
    }

    public String getLink_afiliasi() {
        return link_afiliasi;
    }

    public void setLink_afiliasi(String link_afiliasi) {
        this.link_afiliasi = link_afiliasi;
    }

    public String getWeb_report() {
        return web_report;
    }

    public void setWeb_report(String web_report) {
        this.web_report = web_report;
    }

    public String getKeagenan() {
        return keagenan;
    }

    public void setKeagenan(String keagenan) {
        this.keagenan = keagenan;
    }

    public String getPerbankan() {
        return perbankan;
    }

    public void setPerbankan(String perbankan) {
        this.perbankan = perbankan;
    }

    public String getBelanja() {
        return belanja;
    }

    public void setBelanja(String belanja) {
        this.belanja = belanja;
    }

    public String getBayar() {
        return bayar;
    }

    public void setBayar(String bayar) {
        this.bayar = bayar;
    }

    public String getLion_parcel() {
        return lion_parcel;
    }

    public void setLion_parcel(String lion_parcel) {
        this.lion_parcel = lion_parcel;
    }

    public String getFastravel() {
        return fastravel;
    }

    public void setFastravel(String fastravel) {
        this.fastravel = fastravel;
    }

    public String getEdukasi() {
        return edukasi;
    }

    public void setEdukasi(String edukasi) {
        this.edukasi = edukasi;
    }

    public String getUpgrade() {
        return upgrade;
    }

    public void setUpgrade(String upgrade) {
        this.upgrade = upgrade;
    }

    public String getAffiliasi() {
        return affiliasi;
    }

    public void setAffiliasi(String affiliasi) {
        this.affiliasi = affiliasi;
    }


    public String getId_upline() {
        return id_upline;
    }

    public void setId_upline(String id_upline) {
        this.id_upline = id_upline;
    }

    public String getNama_upline() {
        return nama_upline;
    }

    public void setNama_upline(String nama_upline) {
        this.nama_upline = nama_upline;
    }

    public String getNotlp_upline() {
        return notlp_upline;
    }

    public void setNotlp_upline(String notlp_upline) {
        this.notlp_upline = notlp_upline;
    }

    public String getPinjaman_dana() {
        return pinjaman_dana;
    }

    public void setPinjaman_dana(String pinjaman_dana) {
        this.pinjaman_dana = pinjaman_dana;
    }

    public String getContent_awal_otp() {
        return content_awal_otp;
    }

    public void setContent_awal_otp(String content_awal_otp) {
        this.content_awal_otp = content_awal_otp;
    }

    public String getIsi_content_otp() {
        return isi_content_otp;
    }

    public void setIsi_content_otp(String isi_content_otp) {
        this.isi_content_otp = isi_content_otp;
    }

    public String getGrosir() {
        return grosir;
    }

    public void setGrosir(String grosir) {
        this.grosir = grosir;
    }

    public String getIsCoverGrosir() {
        return isCoverGrosir;
    }

    public void setIsCoverGrosir(String isCoverGrosir) {
        this.isCoverGrosir = isCoverGrosir;
    }

    public String getTunaiku() {
        return tunaiku;
    }

    public void setTunaiku(String tunaiku) {
        this.tunaiku = tunaiku;
    }

    public String getKeyPos() {
        return keyPos;
    }

    public void setKeyPos(String keyPos) {
        this.keyPos = keyPos;
    }

    //    public XList_produk[] getList_produk() {
//        return this.list_produk;
//    }
//
//    public void setList_produk(XList_produk[] list_produk) {
//        this.list_produk = list_produk;
//    }
}

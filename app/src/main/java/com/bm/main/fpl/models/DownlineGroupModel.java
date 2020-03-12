package com.bm.main.fpl.models;

import java.util.ArrayList;

public class DownlineGroupModel {
    private String nomor_whatsapp_outlet;
    private String nama_pemilik;
    private String notelp_pemilik;
    private String jumlah_downline;
    private String id_outlet;
    private ArrayList<DownlineChildModel> level2;

    public String getNomor_whatsapp_outlet() {
        return this.nomor_whatsapp_outlet;
    }

    public void setNomor_whatsapp_outlet(String nomor_whatsapp_outlet) {
        this.nomor_whatsapp_outlet = nomor_whatsapp_outlet;
    }

    public String getNama_pemilik() {
        return this.nama_pemilik;
    }

    public void setNama_pemilik(String nama_pemilik) {
        this.nama_pemilik = nama_pemilik;
    }

    public String getNotelp_pemilik() {
        return this.notelp_pemilik;
    }

    public void setNotelp_pemilik(String notelp_pemilik) {
        this.notelp_pemilik = notelp_pemilik;
    }

    public String getJumlah_downline() {
        return this.jumlah_downline;
    }

    public void setJumlah_downline(String jumlah_downline) {
        this.jumlah_downline = jumlah_downline;
    }

    public String getId_outlet() {
        return this.id_outlet;
    }

    public void setId_outlet(String id_outlet) {
        this.id_outlet = id_outlet;
    }

    public ArrayList<DownlineChildModel> getItems() {
        return level2;
    }

    public void setItems(ArrayList<DownlineChildModel> level2) {
        this.level2 = level2;
    }
}

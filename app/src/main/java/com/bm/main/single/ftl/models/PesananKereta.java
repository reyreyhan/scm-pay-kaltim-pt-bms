package com.bm.main.single.ftl.models;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rizabudiprasetya on 8/12/17.
 */

public class PesananKereta extends JSONModel {
    private String id_transaksi, kode_booking, origin, destination, tanggal_keberangkatan, hari_keberangkatan, jam_keberangkatan,
            tanggal_kedatangan, hari_kedatangan, jam_kedatangan, kode_kereta, nama_kereta, url_etiket, url_struk, classes,komisi,expiredDate;
    private List<Penumpang> penumpangList;
    private JSONObject jsonObject;

    public PesananKereta(@NonNull JSONObject jsonObject) throws JSONException {
        this.id_transaksi = getStringValue(jsonObject, "id_transaksi");
        this.kode_booking = getStringValue(jsonObject, "kode_booking");
        this.origin = getStringValue(jsonObject, "origin");
        this.destination = getStringValue(jsonObject, "destination");
        this.tanggal_keberangkatan = getStringValue(jsonObject, "tanggal_keberangkatan");
        this.hari_keberangkatan = getStringValue(jsonObject, "hari_keberangkatan");
        this.jam_keberangkatan = getStringValue(jsonObject, "jam_keberangkatan");
        this.tanggal_kedatangan = getStringValue(jsonObject, "tanggal_kedatangan");
        this.hari_kedatangan = getStringValue(jsonObject, "hari_kedatangan");
        this.jam_kedatangan = getStringValue(jsonObject, "jam_kedatangan");
        this.kode_kereta = getStringValue(jsonObject, "kode_kereta");
        this.nama_kereta = getStringValue(jsonObject, "nama_kereta");
        this.url_etiket = getStringValue(jsonObject, "url_etiket");
        this.url_struk = getStringValue(jsonObject, "url_struk");
        this.classes = getStringValue(jsonObject, "classes");
        this.komisi = getStringValue(jsonObject, "komisi");
        this.expiredDate = getStringValue(jsonObject, "expiredDate");

        this.url_etiket = this.url_etiket.replace("=>", ":");
        this.url_struk = this.url_struk.replace("=>", ":");


        this.penumpangList = new ArrayList<>();
        JSONArray penumpang = jsonObject.getJSONArray("penumpang");
        for(int i = 0; i < penumpang.length(); i++) {
            penumpangList.add(new Penumpang(penumpang.getJSONObject(i)));
        }

        this.jsonObject = jsonObject;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public class Penumpang extends JSONModel {
        public String nama;

        public Penumpang(@NonNull JSONObject jsonObject) throws JSONException {
            this.nama = getStringValue(jsonObject, "nama");
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }
    }

    public String getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public String getKode_booking() {
        return kode_booking;
    }

    public void setKode_booking(String kode_booking) {
        this.kode_booking = kode_booking;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTanggal_keberangkatan() {
        return tanggal_keberangkatan;
    }

    public void setTanggal_keberangkatan(String tanggal_keberangkatan) {
        this.tanggal_keberangkatan = tanggal_keberangkatan;
    }

    public String getHari_keberangkatan() {
        return hari_keberangkatan;
    }

    public void setHari_keberangkatan(String hari_keberangkatan) {
        this.hari_keberangkatan = hari_keberangkatan;
    }

    public String getJam_keberangkatan() {
        return jam_keberangkatan;
    }

    public void setJam_keberangkatan(String jam_keberangkatan) {
        this.jam_keberangkatan = jam_keberangkatan;
    }

    public String getTanggal_kedatangan() {
        return tanggal_kedatangan;
    }

    public void setTanggal_kedatangan(String tanggal_kedatangan) {
        this.tanggal_kedatangan = tanggal_kedatangan;
    }

    public String getHari_kedatangan() {
        return hari_kedatangan;
    }

    public void setHari_kedatangan(String hari_kedatangan) {
        this.hari_kedatangan = hari_kedatangan;
    }

    public String getJam_kedatangan() {
        return jam_kedatangan;
    }

    public void setJam_kedatangan(String jam_kedatangan) {
        this.jam_kedatangan = jam_kedatangan;
    }

    public String getKode_kereta() {
        return kode_kereta;
    }

    public void setKode_kereta(String kode_kereta) {
        this.kode_kereta = kode_kereta;
    }

    public String getNama_kereta() {
        return nama_kereta;
    }

    public void setNama_kereta(String nama_kereta) {
        this.nama_kereta = nama_kereta;
    }

    public String getUrl_etiket() {
        return url_etiket;
    }

    public void setUrl_etiket(String url_etiket) {
        this.url_etiket = url_etiket;
    }

    public String getUrl_struk() {
        return url_struk;
    }

    public void setUrl_struk(String url_struk) {
        this.url_struk = url_struk;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public List<Penumpang> getPenumpangList() {
        return penumpangList;
    }

    public void setPenumpangList(List<Penumpang> penumpangList) {
        this.penumpangList = penumpangList;
    }

    public String getKomisi() {
        return komisi;
    }

    public void setKomisi(String komisi) {
        this.komisi = komisi;
    }
    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }
}
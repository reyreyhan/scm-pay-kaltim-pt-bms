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

public class PesananPesawat extends JSONModel {
    private String id_transaksi, kode_booking, origin, destination, tanggal_keberangkatan, hari_keberangkatan, jam_keberangkatan,
            tanggal_kedatangan, hari_kedatangan, jam_kedatangan, kode_maskapai, nama_maskapai, url_etiket, url_struk, airlineIcon, subClass, nominal, nominal_admin, duration,expiredDate;
    int komisi;
    private List<Penumpang> penumpangList;
    private JSONObject jsonObject;

    public PesananPesawat(@NonNull JSONObject jsonObject) throws JSONException {
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
        this.kode_maskapai = getStringValue(jsonObject, "kode_maskapai");
        this.expiredDate = getStringValue(jsonObject, "expiredDate");
        String str = getStringValue(jsonObject, "nama_maskapai");
        String[] strArray = str.split(" ");
        StringBuilder builder = new StringBuilder();
        for (String s : strArray) {
            String cap = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
            builder.append(cap + " ");
        }

        if (getStringValue(jsonObject, "kode_maskapai").substring(0, 2).equals("ID")) {
            this.nama_maskapai = "Batik Air";
            this.airlineIcon = "http://static.scash.bz/fastravel/asset/maskapai/TPID.png";
        }else  if (getStringValue(jsonObject, "kode_maskapai").substring(0, 2).equals("IW")) {
            this.nama_maskapai = "Wings Air";
            this.airlineIcon = "http://static.scash.bz/fastravel/asset/maskapai/TPIW.png";
        }else{
            this.nama_maskapai = builder.toString();//getStringValue(jsonObject, "nama_maskapai");
            this.airlineIcon = getStringValue(jsonObject, "airlineIcon");
        }


        this.url_etiket = getStringValue(jsonObject, "url_etiket");
        this.url_struk = getStringValue(jsonObject, "url_struk");

        this.subClass = getStringValue(jsonObject, "subClass");
        this.nominal = getStringValue(jsonObject, "nominal");
        this.nominal_admin = getStringValue(jsonObject, "nominal_admin");
        this.komisi = getIntValue(jsonObject, "komisi");
        this.duration = getStringValue(jsonObject, "duration");

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
        public String status, nama, title;

        public Penumpang(@NonNull JSONObject jsonObject) throws JSONException {
            this.status = getStringValue(jsonObject, "status");
            this.nama = getStringValue(jsonObject, "nama");
            this.title = getStringValue(jsonObject, "title");
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }


    public String getHari_keberangkatan() {
        return hari_keberangkatan;
    }

    public void setHari_keberangkatan(String hari_keberangkatan) {
        this.hari_keberangkatan = hari_keberangkatan;
    }

    public List<Penumpang> getPenumpangList() {
        return penumpangList;
    }

    public void setPenumpangList(List<Penumpang> penumpangList) {
        this.penumpangList = penumpangList;
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

    public String getKode_maskapai() {
        return kode_maskapai;
    }

    public void setKode_maskapai(String kode_maskapai) {
        this.kode_maskapai = kode_maskapai;
    }

    public String getNama_maskapai() {
        return nama_maskapai;
    }

    public void setNama_maskapai(String nama_maskapai) {
        this.nama_maskapai = nama_maskapai;
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

    public void setUrl_struk(String url_struck) {
        this.url_struk = url_struck;
    }

    public String getAirlineIcon() {
        return airlineIcon;
    }

    public void setAirlineIcon(String airlineIcon) {
        this.airlineIcon = airlineIcon;
    }

    public String getSubClass() {
        return subClass;
    }

    public void setSubClass(String subClass) {
        this.subClass = subClass;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getNominal_admin() {
        return nominal_admin;
    }

    public void setNominal_admin(String nominal_admin) {
        this.nominal_admin = nominal_admin;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getKomisi() {
        return komisi;
    }

    public void setKomisi(int komisi) {
        this.komisi = komisi;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }
}

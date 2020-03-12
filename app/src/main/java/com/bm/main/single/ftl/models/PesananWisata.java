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

public class PesananWisata extends JSONModel {
    private String id_transaksi, kode_booking, destination, tanggal_mulai, tangal_selesai, nama_peserta, jumlah_peserta, harga, url_image, url_etiket, url_struk,komisi,expiredDate;
    private JSONObject jsonObject;
    private List<PesananWisata.Wisatawan> wisatawanList;

    public PesananWisata(@NonNull JSONObject jsonObject) throws JSONException {
        this.id_transaksi = getStringValue(jsonObject, "id_transaksi");
        this.kode_booking = getStringValue(jsonObject, "kode_booking");
        this.destination = getStringValue(jsonObject, "destination");
        this.tanggal_mulai = getStringValue(jsonObject, "tanggal_mulai");
        this.tangal_selesai = getStringValue(jsonObject, "tangal_selesai");
        this.nama_peserta = getStringValue(jsonObject, "nama_peserta");
        this.jumlah_peserta = getStringValue(jsonObject, "jumlah peserta");
        this.harga = getStringValue(jsonObject, "harga");
        this.url_etiket = getStringValue(jsonObject, "url_etiket");
        this.url_struk = getStringValue(jsonObject, "url_struk");
        this.url_image = getStringValue(jsonObject, "url_image");
        this.komisi = getStringValue(jsonObject, "komisi");
        this.expiredDate = getStringValue(jsonObject, "expiredDate");
        this.url_etiket = this.url_etiket.replace("=>", ":");
        this.url_struk = this.url_struk.replace("=>", ":");

        this.wisatawanList = new ArrayList<>();
        JSONArray wisatawan = jsonObject.getJSONArray("wisatawan");
        for(int i = 0; i < wisatawan.length(); i++) {
            wisatawanList.add(new PesananWisata.Wisatawan(wisatawan.getJSONObject(i)));
        }

        this.jsonObject = jsonObject;

        this.jsonObject = jsonObject;
    }

    public class Wisatawan extends JSONModel {
        public String nama, phone;

        public Wisatawan(@NonNull JSONObject jsonObject) throws JSONException {
            this.nama = getStringValue(jsonObject, "nama");
            this.phone = getStringValue(jsonObject, "phone");
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    public List<Wisatawan> getWisatawanList() {
        return wisatawanList;
    }

    public void setWisatawanList(List<Wisatawan> wisatawanList) {
        this.wisatawanList = wisatawanList;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
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

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTanggal_mulai() {
        return tanggal_mulai;
    }

    public void setTanggal_mulai(String tanggal_mulai) {
        this.tanggal_mulai = tanggal_mulai;
    }

    public String getTangal_selesai() {
        return tangal_selesai;
    }

    public void setTangal_selesai(String tangal_selesai) {
        this.tangal_selesai = tangal_selesai;
    }

    public String getNama_peserta() {
        return nama_peserta;
    }

    public void setNama_peserta(String nama_peserta) {
        this.nama_peserta = nama_peserta;
    }

    public String getJumlah_peserta() {
        return jumlah_peserta;
    }

    public void setJumlah_peserta(String jumlah_peserta) {
        this.jumlah_peserta = jumlah_peserta;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
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

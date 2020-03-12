package com.bm.main.fpl.models;

public class XList_produk implements java.io.Serializable {
    private static final long serialVersionUID = -5809608523882416914L;
    private String komisi_outlet;
    private String foto;
    private String nama;
    private int harga;
    private String url;

    public XList_produk(){

    }

    public String getKomisi_outlet() {
        return this.komisi_outlet;
    }

    public void setKomisi_outlet(String komisi_outlet) {
        this.komisi_outlet = komisi_outlet;
    }

    public String getFoto() {
        return this.foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNama() {
        return this.nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getHarga() {
        return this.harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

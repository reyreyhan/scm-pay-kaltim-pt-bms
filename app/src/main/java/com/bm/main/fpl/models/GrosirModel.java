package com.bm.main.fpl.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarifhidayat on 10/10/18.
 **/
public class GrosirModel {


    @SerializedName("foto")
    private String foto;
    @SerializedName("nama")
    private String nama;
    //        @SerializedName("komisi_outlet")
//        private String komisi_outlet;
    @SerializedName("harga")
    private String harga;
    @SerializedName("harga_coret")
    private String harga_coret;
    @SerializedName("url")
    private String url;
    @SerializedName("is_cover")
    private String is_cover;



    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

//        public String getKomisi_outlet() {
//            return komisi_outlet;
//        }
//
//        public void setKomisi_outlet(String komisi_outlet) {
//            this.komisi_outlet = komisi_outlet;
//        }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getHarga_coret() {
        return harga_coret;
    }

    public void setHarga_coret(String harga_coret) {
        this.harga_coret = harga_coret;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIs_cover() {
        return is_cover;
    }

    public void setIs_cover(String is_cover) {
        this.is_cover = is_cover;
    }


    private static int lastContactId = 0;

    @NonNull
    public static List<GrosirModel> createProductsList(int numProducts, int offset, @NonNull ArrayList<ProdukFMCGModel.Response_value> produkFMCGModel) {
        List<GrosirModel> products = new ArrayList<GrosirModel>();

        for (int i = 1; i <= numProducts; i++) {

            GrosirModel grosirModel=new GrosirModel();
            grosirModel.setNama(produkFMCGModel.get(i).getNama());
            grosirModel.setFoto(produkFMCGModel.get(i).getFoto());
            grosirModel.setHarga(produkFMCGModel.get(i).getHarga());
            grosirModel.setHarga_coret(produkFMCGModel.get(i).getHarga_coret());
            grosirModel.setIs_cover(produkFMCGModel.get(i).getIs_cover());
            grosirModel.setUrl(produkFMCGModel.get(i).getUrl());
            products.add(grosirModel);

//            contacts.add(new Contact("Person " + ++lastContactId + " offset: " + offset, i <= numContacts / 2));
        }

        return products;
    }
}

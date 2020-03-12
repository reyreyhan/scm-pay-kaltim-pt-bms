package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProdukTomoModel extends BaseObject implements Parcelable{

    @SerializedName("response_value")
    private ArrayList<ProdukTomoModel.Response_value> response_value;
    private ProdukTomoModel(Parcel in) {
    }

    public static final Creator<ProdukTomoModel> CREATOR = new Creator<ProdukTomoModel>() {
        @NonNull
        @Override
        public ProdukTomoModel createFromParcel(Parcel in) {
            return new ProdukTomoModel(in);
        }

        @NonNull
        @Override
        public ProdukTomoModel[] newArray(int size) {
            return new ProdukTomoModel[size];
        }
    };

    public ArrayList<ProdukTomoModel.Response_value> getResponse_value() {
        return response_value;
    }

    public void setResponse_value(ArrayList<ProdukTomoModel.Response_value> response_value) {
        this.response_value = response_value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public static class Response_value implements Parcelable {

        @Nullable
        @SerializedName("foto")
        private String foto;
        @Nullable
        @SerializedName("nama")
        private String nama;
        @Nullable
        @SerializedName("komisi_outlet")
        private String komisi_outlet;
        @Nullable
        @SerializedName("harga")
        private String harga;
        @Nullable
        @SerializedName("harga_coret")
        private String harga_coret;
        @Nullable
        @SerializedName("url")
        private String url;





        protected Response_value(@NonNull Parcel in) {

            foto = in.readString();
            nama = in.readString();
            harga = in.readString();
            harga_coret = in.readString();
            komisi_outlet = in.readString();
            url = in.readString();


        }

        public static final Creator<ProdukTomoModel.Response_value> CREATOR = new Creator<ProdukTomoModel.Response_value>() {
            @NonNull
            @Override
            public ProdukTomoModel.Response_value createFromParcel(@NonNull Parcel in) {
                return new ProdukTomoModel.Response_value(in);
            }

            @NonNull
            @Override
            public ProdukTomoModel.Response_value[] newArray(int size) {
                return new ProdukTomoModel.Response_value[size];
            }
        };

        @Nullable
        public String getFoto() {
            return foto;
        }

        public void setFoto(String foto) {
            this.foto = foto;
        }

        @Nullable
        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        @Nullable
        public String getKomisi_outlet() {
            return komisi_outlet;
        }

        public void setKomisi_outlet(String komisi_outlet) {
            this.komisi_outlet = komisi_outlet;
        }

        @Nullable
        public String getHarga() {
            return harga;
        }

        public void setHarga(String harga) {
            this.harga = harga;
        }

        @Nullable
        public String getHarga_coret() {
            return harga_coret;
        }

        public void setHarga_coret(String harga_coret) {
            this.harga_coret = harga_coret;
        }

        @Nullable
        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {

            dest.writeString(this.foto);
            dest.writeString(this.nama);
            dest.writeString(this.harga);
            dest.writeString(this.harga_coret);
            dest.writeString(this.komisi_outlet);
            dest.writeString(this.url);

        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}

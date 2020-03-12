package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProdukFMCGModel extends BaseObject implements Parcelable{

    @SerializedName("response_value")
    private ArrayList<ProdukFMCGModel.Response_value> response_value;
    private ProdukFMCGModel(Parcel in) {
    }

    public static final Creator<ProdukFMCGModel> CREATOR = new Creator<ProdukFMCGModel>() {
        @NonNull
        @Override
        public ProdukFMCGModel createFromParcel(Parcel in) {
            return new ProdukFMCGModel(in);
        }

        @NonNull
        @Override
        public ProdukFMCGModel[] newArray(int size) {
            return new ProdukFMCGModel[size];
        }
    };

    public ArrayList<ProdukFMCGModel.Response_value> getResponse_value() {
        return response_value;
    }

    public void setResponse_value(ArrayList<ProdukFMCGModel.Response_value> response_value) {
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
//        @SerializedName("komisi_outlet")
//        private String komisi_outlet;
        @Nullable
        @SerializedName("harga")
        private String harga;
        @Nullable
        @SerializedName("harga_coret")
        private String harga_coret;
        @Nullable
        @SerializedName("url")
        private String url;
        @Nullable
        @SerializedName("is_cover")
        private String is_cover;





        protected Response_value(@NonNull Parcel in) {

            foto = in.readString();
            nama = in.readString();
            harga = in.readString();
            harga_coret = in.readString();
     //       komisi_outlet = in.readString();
            url = in.readString();
            is_cover = in.readString();


        }

        public static final Creator<ProdukFMCGModel.Response_value> CREATOR = new Creator<ProdukFMCGModel.Response_value>() {
            @NonNull
            @Override
            public ProdukFMCGModel.Response_value createFromParcel(@NonNull Parcel in) {
                return new ProdukFMCGModel.Response_value(in);
            }

            @NonNull
            @Override
            public ProdukFMCGModel.Response_value[] newArray(int size) {
                return new ProdukFMCGModel.Response_value[size];
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

//        public String getKomisi_outlet() {
//            return komisi_outlet;
//        }
//
//        public void setKomisi_outlet(String komisi_outlet) {
//            this.komisi_outlet = komisi_outlet;
//        }

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

        @Nullable
        public String getIs_cover() {
            return is_cover;
        }

        public void setIs_cover(String is_cover) {
            this.is_cover = is_cover;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {

            dest.writeString(this.foto);
            dest.writeString(this.nama);
            dest.writeString(this.harga);
            dest.writeString(this.harga_coret);
          //  dest.writeString(this.komisi_outlet);
            dest.writeString(this.url);
            dest.writeString(this.is_cover);

        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}

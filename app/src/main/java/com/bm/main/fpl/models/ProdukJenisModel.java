package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProdukJenisModel extends BaseObject implements Parcelable {

    @SerializedName("response_value")
    private ArrayList<Response_value> response_value;
    private ProdukJenisModel(Parcel in) {
    }

    public static final Creator<ProdukJenisModel> CREATOR = new Creator<ProdukJenisModel>() {
        @NonNull
        @Override
        public ProdukJenisModel createFromParcel(Parcel in) {
            return new ProdukJenisModel(in);
        }

        @NonNull
        @Override
        public ProdukJenisModel[] newArray(int size) {
            return new ProdukJenisModel[size];
        }
    };

    public ArrayList<ProdukJenisModel.Response_value> getResponse_value() {
        return response_value;
    }

    public void setResponse_value(ArrayList<ProdukJenisModel.Response_value> response_value) {
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
        @SerializedName("jenis_produk")
        private String jenis_produk;
        @Nullable
        @SerializedName("product_url")
        private String product_url;
        @Nullable
        @SerializedName("is_gangguan")
        private String is_gangguan;
        @Nullable
        @SerializedName("is_promo")
        private String is_promo;
        @Nullable
        @SerializedName("product_code")
        private String product_code;
        @Nullable
        @SerializedName("product_name")
        private String product_name;



        protected Response_value(@NonNull Parcel in) {
            jenis_produk = in.readString();
            product_url = in.readString();
            is_gangguan = in.readString();
            is_promo = in.readString();
            product_code = in.readString();
            product_name = in.readString();

        }

        public static final Creator<ProdukJenisModel.Response_value> CREATOR = new Creator<ProdukJenisModel.Response_value>() {
            @NonNull
            @Override
            public ProdukJenisModel.Response_value createFromParcel(@NonNull Parcel in) {
                return new ProdukJenisModel.Response_value(in);
            }

            @NonNull
            @Override
            public ProdukJenisModel.Response_value[] newArray(int size) {
                return new ProdukJenisModel.Response_value[size];
            }
        };


        @Nullable
        public String getJenis_produk() {
            return jenis_produk;
        }

        public void setJenis_produk(String jenis_produk) {
            this.jenis_produk = jenis_produk;
        }

        @Nullable
        public String getProduct_url() {
            return this.product_url;
        }

        public void setProduct_url(String product_url) {
            this.product_url = product_url;
        }

        @Nullable
        public String getIs_gangguan() {
            return this.is_gangguan;
        }

        public void setIs_gangguan(String is_gangguan) {
            this.is_gangguan = is_gangguan;
        }

        @Nullable
        public String getIs_promo() {
            return is_promo;
        }

        public void setIs_promo(String is_promo) {
            this.is_promo = is_promo;
        }

        @Nullable
        public String getProduct_code() {
            return this.product_code;
        }

        public void setProduct_code(String product_code) {
            this.product_code = product_code;
        }

        @Nullable
        public String getProduct_name() {
            return this.product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(this.jenis_produk);
            dest.writeString(this.product_url);
            dest.writeString(this.is_gangguan);
            dest.writeString(this.is_promo);
            dest.writeString(this.product_code);
            dest.writeString(this.product_name);
        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}

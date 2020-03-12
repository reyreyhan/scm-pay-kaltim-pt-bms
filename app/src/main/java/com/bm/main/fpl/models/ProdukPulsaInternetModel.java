package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProdukPulsaInternetModel extends BaseObject implements Parcelable {

    @SerializedName("response_value")
    private ArrayList<Response_value> response_value;
    private ProdukPulsaInternetModel(Parcel in) {
    }

    public static final Creator<ProdukPulsaInternetModel> CREATOR = new Creator<ProdukPulsaInternetModel>() {
        @NonNull
        @Override
        public ProdukPulsaInternetModel createFromParcel(Parcel in) {
            return new ProdukPulsaInternetModel(in);
        }

        @NonNull
        @Override
        public ProdukPulsaInternetModel[] newArray(int size) {
            return new ProdukPulsaInternetModel[size];
        }
    };

    public ArrayList<ProdukPulsaInternetModel.Response_value> getResponse_value() {
        return response_value;
    }

    public void setResponse_value(ArrayList<ProdukPulsaInternetModel.Response_value> response_value) {
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
        @SerializedName("product_url")
        private String product_url;
        @Nullable
        @SerializedName("is_gangguan")
        private String is_gangguan;
        @Nullable
        @SerializedName("type")
        private String type;
        @Nullable
        @SerializedName("product_code")
        private String product_code;
        @Nullable
        @SerializedName("product_name")
        private String product_name;



        protected Response_value(@NonNull Parcel in) {
            product_url = in.readString();
            is_gangguan = in.readString();
            type = in.readString();
            product_code = in.readString();
            product_name = in.readString();

        }

        public static final Creator<ProdukPulsaInternetModel.Response_value> CREATOR = new Creator<ProdukPulsaInternetModel.Response_value>() {
            @NonNull
            @Override
            public ProdukPulsaInternetModel.Response_value createFromParcel(@NonNull Parcel in) {
                return new ProdukPulsaInternetModel.Response_value(in);
            }

            @NonNull
            @Override
            public ProdukPulsaInternetModel.Response_value[] newArray(int size) {
                return new ProdukPulsaInternetModel.Response_value[size];
            }
        };



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
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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
            dest.writeString(this.product_url);
            dest.writeString(this.is_gangguan);
            dest.writeString(this.type);
            dest.writeString(this.product_code);
            dest.writeString(this.product_name);
        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}

package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AdditionalMenuModel implements Parcelable {

    @SerializedName("response_value")
    private ArrayList<Response_value> response_value;
    private AdditionalMenuModel(Parcel in) {
    }

    public static final Creator<AdditionalMenuModel> CREATOR = new Creator<AdditionalMenuModel>() {
        @NonNull
        @Override
        public AdditionalMenuModel createFromParcel(Parcel in) {
            return new AdditionalMenuModel(in);
        }

        @NonNull
        @Override
        public AdditionalMenuModel[] newArray(int size) {
            return new AdditionalMenuModel[size];
        }
    };

    public ArrayList<Response_value> getResponse_value() {
        return response_value;
    }

    public void setResponse_value(ArrayList<Response_value> response_value) {
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
        @SerializedName("product_image_url")
        private String product_image_url;
        @Nullable
        @SerializedName("is_gangguan")
        private String is_gangguan;
        @Nullable
        @SerializedName("is_promo")
        private String is_promo;
        @Nullable
        @SerializedName("is_new")
        private String is_new;
        @Nullable
        @SerializedName("is_hot")
        private String is_hot;
        @Nullable
        @SerializedName("is_coming_soon")
        private String is_coming_soon;

        @SerializedName("is_use_id_outlet")
        private String is_use_id_outlet;

        @Nullable
        @SerializedName("product_name")
        private String product_name;



        protected Response_value(@NonNull Parcel in) {
            product_url = in.readString();
            product_image_url = in.readString();
            is_gangguan = in.readString();
            is_promo = in.readString();
            is_new = in.readString();
            is_hot = in.readString();
            is_use_id_outlet= in.readString();
            is_coming_soon= in.readString();
           // product_code = in.readString();
            product_name = in.readString();

        }

        public static final Creator<Response_value> CREATOR = new Creator<Response_value>() {
            @NonNull
            @Override
            public AdditionalMenuModel.Response_value createFromParcel(@NonNull Parcel in) {
                return new AdditionalMenuModel.Response_value(in);
            }

            @NonNull
            @Override
            public AdditionalMenuModel.Response_value[] newArray(int size) {
                return new AdditionalMenuModel.Response_value[size];
            }
        };


        @Nullable
        public String getProduct_image_url() {
            return product_image_url;
        }

        public void setProduct_image_url(@Nullable String product_image_url) {
            this.product_image_url = product_image_url;
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

        public String getIs_use_id_outlet() {
            return is_use_id_outlet;
        }

        public void setIs_use_id_outlet(String is_use_id_outlet) {
            this.is_use_id_outlet = is_use_id_outlet;
        }

        @Nullable
        public String getIs_coming_soon() {
            return is_coming_soon;
        }

        public void setIs_coming_soon(@Nullable String is_coming_soon) {
            this.is_coming_soon = is_coming_soon;
        }

        @Nullable
        public String getIs_new() {
            return is_new;
        }

        public void setIs_new(@Nullable String is_new) {
            this.is_new = is_new;
        }

        @Nullable
        public String getIs_hot() {
            return is_hot;
        }

        public void setIs_hot(@Nullable String is_hot) {
            this.is_hot = is_hot;
        }
        //        @Nullable
//        public String getProduct_code() {
//            return this.product_code;
//        }
//
//        public void setProduct_code(String product_code) {
//            this.product_code = product_code;
//        }

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
            dest.writeString(this.product_image_url);
            dest.writeString(this.is_gangguan);
            dest.writeString(this.is_promo);
            dest.writeString(this.is_new);
            dest.writeString(this.is_hot);
            dest.writeString(this.is_use_id_outlet);
            dest.writeString(this.is_coming_soon);
        //    dest.writeString(this.product_code);
            dest.writeString(this.product_name);
        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}

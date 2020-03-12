package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MerchantModel extends BaseObject implements Parcelable {

    @SerializedName("response_value")
    private ArrayList<Response_value> response_value;
    private MerchantModel(Parcel in) {
    }

    public static final Creator<MerchantModel> CREATOR = new Creator<MerchantModel>() {
        @NonNull
        @Override
        public MerchantModel createFromParcel(Parcel in) {
            return new MerchantModel(in);
        }

        @NonNull
        @Override
        public MerchantModel[] newArray(int size) {
            return new MerchantModel[size];
        }
    };

    public ArrayList<MerchantModel.Response_value> getResponse_value() {
        return response_value;
    }

    public void setResponse_value(ArrayList<MerchantModel.Response_value> response_value) {
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
        @SerializedName("merchant_name")
        private String merchant_name;
        @Nullable
        @SerializedName("merchant_code")
        private String merchant_code;




        protected Response_value(@NonNull Parcel in) {
            product_url = in.readString();

            merchant_code = in.readString();
            merchant_name = in.readString();


        }

        public static final Creator<MerchantModel.Response_value> CREATOR = new Creator<MerchantModel.Response_value>() {
            @NonNull
            @Override
            public MerchantModel.Response_value createFromParcel(@NonNull Parcel in) {
                return new MerchantModel.Response_value(in);
            }

            @NonNull
            @Override
            public MerchantModel.Response_value[] newArray(int size) {
                return new MerchantModel.Response_value[size];
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
        public String getMerchant_name() {
            return merchant_name;
        }

        public void setMerchant_name(String merchant_name) {
            this.merchant_name = merchant_name;
        }

        @Nullable
        public String getMerchant_code() {
            return merchant_code;
        }

        public void setMerchant_code(String merchant_code) {
            this.merchant_code = merchant_code;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(this.product_url);

            dest.writeString(this.merchant_code);
            dest.writeString(this.merchant_name);

        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}

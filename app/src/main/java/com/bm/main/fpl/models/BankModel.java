package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BankModel extends BaseObject implements Parcelable {

    @SerializedName("response_value")
    private ArrayList<Response_value> response_value;
    private BankModel(Parcel in) {
    }

    public static final Creator<BankModel> CREATOR = new Creator<BankModel>() {
        @NonNull
        @Override
        public BankModel createFromParcel(Parcel in) {
            return new BankModel(in);
        }

        @NonNull
        @Override
        public BankModel[] newArray(int size) {
            return new BankModel[size];
        }
    };

    public ArrayList<BankModel.Response_value> getResponse_value() {
        return response_value;
    }

    public void setResponse_value(ArrayList<BankModel.Response_value> response_value) {
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
        @SerializedName("bank_code")
        private String bank_code;
        @Nullable
        @SerializedName("bank_name")
        private String bank_name;




        protected Response_value(@NonNull Parcel in) {
            product_url = in.readString();

            bank_code = in.readString();
            bank_name = in.readString();


        }

        public static final Creator<BankModel.Response_value> CREATOR = new Creator<BankModel.Response_value>() {
            @NonNull
            @Override
            public BankModel.Response_value createFromParcel(@NonNull Parcel in) {
                return new BankModel.Response_value(in);
            }

            @NonNull
            @Override
            public BankModel.Response_value[] newArray(int size) {
                return new BankModel.Response_value[size];
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
        public String getBank_code() {
            return bank_code;
        }

        public void setBank_code(String bank_code) {
            this.bank_code = bank_code;
        }

        @Nullable
        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }



        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(this.product_url);

            dest.writeString(this.bank_code);
            dest.writeString(this.bank_name);

        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}

package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListBankModel implements Parcelable {

    @SerializedName("response_value")
    private ArrayList<Response_value> response_value;

    private ListBankModel(Parcel in) {
    }

    public static final Creator<ListBankModel> CREATOR = new Creator<ListBankModel>() {
        @NonNull
        @Override
        public ListBankModel createFromParcel(Parcel in) {
            return new ListBankModel(in);
        }

        @NonNull
        @Override
        public ListBankModel[] newArray(int size) {
            return new ListBankModel[size];
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
        @SerializedName("nama_bank")
        private String nama_bank;


        protected Response_value(@NonNull Parcel in) {
            nama_bank = in.readString();


        }

        public static final Creator<Response_value> CREATOR = new Creator<Response_value>() {
            @NonNull
            @Override
            public ListBankModel.Response_value createFromParcel(@NonNull Parcel in) {
                return new ListBankModel.Response_value(in);
            }

            @NonNull
            @Override
            public ListBankModel.Response_value[] newArray(int size) {
                return new ListBankModel.Response_value[size];
            }
        };


        @Nullable
        public String getNama_bank() {
            return nama_bank;
        }

        public void setNama_bank(@Nullable String nama_bank) {
            this.nama_bank = nama_bank;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(this.nama_bank);


        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}

package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TiketHistoryModel extends BaseObject implements Parcelable {

    @SerializedName("response_value")
    private ArrayList<Response_value> response_value;
    private TiketHistoryModel(Parcel in) {
    }

    public static final Creator<TiketHistoryModel> CREATOR = new Creator<TiketHistoryModel>() {
        @NonNull
        @Override
        public TiketHistoryModel createFromParcel(Parcel in) {
            return new TiketHistoryModel(in);
        }

        @NonNull
        @Override
        public TiketHistoryModel[] newArray(int size) {
            return new TiketHistoryModel[size];
        }
    };

    public ArrayList<TiketHistoryModel.Response_value> getResponse_value() {
        return response_value;
    }

    public void setResponse_value(ArrayList<TiketHistoryModel.Response_value> response_value) {
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
        @SerializedName("bank")
        private String bank;
        @Nullable
        @SerializedName("nominal")
        private String nominal;
        @Nullable
        @SerializedName("status")
        private String status;
        @Nullable
        @SerializedName("keterangan")
        private String keterangan;




        protected Response_value(@NonNull Parcel in) {
            bank = in.readString();
            nominal = in.readString();
            status = in.readString();
            keterangan = in.readString();


        }

        public static final Creator<TiketHistoryModel.Response_value> CREATOR = new Creator<TiketHistoryModel.Response_value>() {
            @NonNull
            @Override
            public TiketHistoryModel.Response_value createFromParcel(@NonNull Parcel in) {
                return new TiketHistoryModel.Response_value(in);
            }

            @NonNull
            @Override
            public TiketHistoryModel.Response_value[] newArray(int size) {
                return new TiketHistoryModel.Response_value[size];
            }
        };

        @Nullable
        public String getBank() {
            return bank;
        }

        public void setBank(String bank) {
            this.bank = bank;
        }

        @Nullable
        public String getNominal() {
            return nominal;
        }

        public void setNominal(String nominal) {
            this.nominal = nominal;
        }

        @Nullable
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Nullable
        public String getKeterangan() {
            return keterangan;
        }

        public void setKeterangan(String keterangan) {
            this.keterangan = keterangan;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(this.bank);
            dest.writeString(this.nominal);
            dest.writeString(this.status);
            dest.writeString(this.keterangan);

        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}

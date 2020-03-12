package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class KodePosModel extends BaseObject implements Parcelable {

    @SerializedName("response_value")
    private ArrayList<Response_value> response_value;
    private KodePosModel(Parcel in) {
    }

    public static final Creator<KodePosModel> CREATOR = new Creator<KodePosModel>() {
        @NonNull
        @Override
        public KodePosModel createFromParcel(Parcel in) {
            return new KodePosModel(in);
        }

        @NonNull
        @Override
        public KodePosModel[] newArray(int size) {
            return new KodePosModel[size];
        }
    };

    public ArrayList<KodePosModel.Response_value> getResponse_value() {
        return response_value;
    }

    public void setResponse_value(ArrayList<KodePosModel.Response_value> response_value) {
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
        @SerializedName("kode_pos")
        private String kode_pos;
        @Nullable
        @SerializedName("detail")
        private String detail;





        protected Response_value(@NonNull Parcel in) {

            kode_pos = in.readString();
            detail = in.readString();


        }

        public static final Creator<KodePosModel.Response_value> CREATOR = new Creator<KodePosModel.Response_value>() {
            @NonNull
            @Override
            public KodePosModel.Response_value createFromParcel(@NonNull Parcel in) {
                return new KodePosModel.Response_value(in);
            }

            @NonNull
            @Override
            public KodePosModel.Response_value[] newArray(int size) {
                return new KodePosModel.Response_value[size];
            }
        };

        @Nullable
        public String getKode_pos() {
            return kode_pos;
        }

        public void setKode_pos(@Nullable String kode_pos) {
            this.kode_pos = kode_pos;
        }

        @Nullable
        public String getDetail() {
            return detail;
        }

        public void setDetail(@Nullable String detail) {
            this.detail = detail;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {

            dest.writeString(this.kode_pos);
            dest.writeString(this.detail);

        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}

package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class KecamatanModel extends BaseObject implements Parcelable {

    @SerializedName("response_value")
    private ArrayList<Response_value> response_value;
    private KecamatanModel(Parcel in) {
    }

    public static final Creator<KecamatanModel> CREATOR = new Creator<KecamatanModel>() {
        @NonNull
        @Override
        public KecamatanModel createFromParcel(Parcel in) {
            return new KecamatanModel(in);
        }

        @NonNull
        @Override
        public KecamatanModel[] newArray(int size) {
            return new KecamatanModel[size];
        }
    };

    public ArrayList<KecamatanModel.Response_value> getResponse_value() {
        return response_value;
    }

    public void setResponse_value(ArrayList<KecamatanModel.Response_value> response_value) {
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
        @SerializedName("kecamatan_code")
        private String kecamatan_code;
        @Nullable
        @SerializedName("kecamatan_name")
        private String kecamatan_name;





        protected Response_value(@NonNull Parcel in) {

            kecamatan_code = in.readString();
            kecamatan_name = in.readString();


        }

        public static final Creator<KecamatanModel.Response_value> CREATOR = new Creator<KecamatanModel.Response_value>() {
            @NonNull
            @Override
            public KecamatanModel.Response_value createFromParcel(@NonNull Parcel in) {
                return new KecamatanModel.Response_value(in);
            }

            @NonNull
            @Override
            public KecamatanModel.Response_value[] newArray(int size) {
                return new KecamatanModel.Response_value[size];
            }
        };

        @Nullable
        public String getKecamatan_code() {
            return kecamatan_code;
        }

        public void setKecamatan_code(@Nullable String kecamatan_code) {
            this.kecamatan_code = kecamatan_code;
        }

        @Nullable
        public String getKecamatan_name() {
            return kecamatan_name;
        }

        public void setKecamatan_name(@Nullable String kecamatan_name) {
            this.kecamatan_name = kecamatan_name;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {

            dest.writeString(this.kecamatan_code);
            dest.writeString(this.kecamatan_name);

        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}

package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class KabupatenModel extends BaseObject implements Parcelable {

    @SerializedName("response_value")
    private ArrayList<Response_value> response_value;
    private KabupatenModel(Parcel in) {
    }

    public static final Creator<KabupatenModel> CREATOR = new Creator<KabupatenModel>() {
        @NonNull
        @Override
        public KabupatenModel createFromParcel(Parcel in) {
            return new KabupatenModel(in);
        }

        @NonNull
        @Override
        public KabupatenModel[] newArray(int size) {
            return new KabupatenModel[size];
        }
    };

    public ArrayList<KabupatenModel.Response_value> getResponse_value() {
        return response_value;
    }

    public void setResponse_value(ArrayList<KabupatenModel.Response_value> response_value) {
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
        @SerializedName("city_code")
        private String city_code;
        @Nullable
        @SerializedName("city_name")
        private String city_name;





        protected Response_value(@NonNull Parcel in) {

            city_code = in.readString();
            city_name = in.readString();


        }

        public static final Creator<KabupatenModel.Response_value> CREATOR = new Creator<KabupatenModel.Response_value>() {
            @NonNull
            @Override
            public KabupatenModel.Response_value createFromParcel(@NonNull Parcel in) {
                return new KabupatenModel.Response_value(in);
            }

            @NonNull
            @Override
            public KabupatenModel.Response_value[] newArray(int size) {
                return new KabupatenModel.Response_value[size];
            }
        };

        @Nullable
        public String getCity_code() {
            return city_code;
        }

        public void setCity_code(String city_code) {
            this.city_code = city_code;
        }

        @Nullable
        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {

            dest.writeString(this.city_code);
            dest.writeString(this.city_name);

        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}

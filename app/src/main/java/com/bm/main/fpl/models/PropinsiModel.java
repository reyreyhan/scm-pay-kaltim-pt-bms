package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PropinsiModel extends BaseObject implements Parcelable {

    @SerializedName("response_value")
    private ArrayList<Response_value> response_value;
    private PropinsiModel(Parcel in) {
    }

    public static final Creator<PropinsiModel> CREATOR = new Creator<PropinsiModel>() {
        @NonNull
        @Override
        public PropinsiModel createFromParcel(Parcel in) {
            return new PropinsiModel(in);
        }

        @NonNull
        @Override
        public PropinsiModel[] newArray(int size) {
            return new PropinsiModel[size];
        }
    };

    public ArrayList<PropinsiModel.Response_value> getResponse_value() {
        return response_value;
    }

    public void setResponse_value(ArrayList<PropinsiModel.Response_value> response_value) {
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
        @SerializedName("prop_code")
        private String prop_code;
        @Nullable
        @SerializedName("prop_name")
        private String prop_name;





        protected Response_value(@NonNull Parcel in) {

            prop_code = in.readString();
            prop_name = in.readString();


        }

        public static final Creator<PropinsiModel.Response_value> CREATOR = new Creator<PropinsiModel.Response_value>() {
            @NonNull
            @Override
            public PropinsiModel.Response_value createFromParcel(@NonNull Parcel in) {
                return new PropinsiModel.Response_value(in);
            }

            @NonNull
            @Override
            public PropinsiModel.Response_value[] newArray(int size) {
                return new PropinsiModel.Response_value[size];
            }
        };

        @Nullable
        public String getProp_code() {
            return prop_code;
        }

        public void setProp_code(String prop_code) {
            this.prop_code = prop_code;
        }

        @Nullable
        public String getProp_name() {
            return prop_name;
        }

        public void setProp_name(String prop_name) {
            this.prop_name = prop_name;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {

            dest.writeString(this.prop_code);
            dest.writeString(this.prop_name);

        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}

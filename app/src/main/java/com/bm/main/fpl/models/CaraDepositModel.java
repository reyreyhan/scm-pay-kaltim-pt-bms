package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CaraDepositModel extends BaseObject implements Parcelable {

    @SerializedName("response_value")
    private ArrayList<Response_value> response_value;

    private CaraDepositModel(Parcel in) {
    }

    public static final Creator<CaraDepositModel> CREATOR = new Creator<CaraDepositModel>() {
        @NonNull
        @Override
        public CaraDepositModel createFromParcel(Parcel in) {
            return new CaraDepositModel(in);
        }

        @NonNull
        @Override
        public CaraDepositModel[] newArray(int size) {
            return new CaraDepositModel[size];
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
        @SerializedName("desc_number")
        private String desc_number;
        @Nullable
        @SerializedName("desc_header")
        private String desc_header;
        @Nullable
        @SerializedName("desc_content")
        private String desc_content;


        protected Response_value(@NonNull Parcel in) {
            desc_number = in.readString();

            desc_header = in.readString();
            desc_content = in.readString();


        }

        public static final Creator<Response_value> CREATOR = new Creator<Response_value>() {
            @NonNull
            @Override
            public CaraDepositModel.Response_value createFromParcel(@NonNull Parcel in) {
                return new CaraDepositModel.Response_value(in);
            }

            @NonNull
            @Override
            public CaraDepositModel.Response_value[] newArray(int size) {
                return new CaraDepositModel.Response_value[size];
            }
        };


        @Nullable
        public String getDesc_number() {
            return desc_number;
        }

        public void setDesc_number(@Nullable String desc_number) {
            this.desc_number = desc_number;
        }

        @Nullable
        public String getDesc_header() {
            return desc_header;
        }

        public void setDesc_header(@Nullable String desc_header) {
            this.desc_header = desc_header;
        }

        @Nullable
        public String getDesc_content() {
            return desc_content;
        }

        public void setDesc_content(@Nullable String desc_content) {
            this.desc_content = desc_content;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(this.desc_number);

            dest.writeString(this.desc_header);
            dest.writeString(this.desc_content);

        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}

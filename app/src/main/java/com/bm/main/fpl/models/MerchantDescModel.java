package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MerchantDescModel extends BaseObject implements Parcelable  {

    @SerializedName("note")
    private String note;

    @SerializedName("response_value")
    private ArrayList<Response_value> response_value;
    private MerchantDescModel(Parcel in) {
    }

    public static final Creator<MerchantDescModel> CREATOR = new Creator<MerchantDescModel>() {
        @NonNull
        @Override
        public MerchantDescModel createFromParcel(Parcel in) {
            return new MerchantDescModel(in);
        }

        @NonNull
        @Override
        public MerchantDescModel[] newArray(int size) {
            return new MerchantDescModel[size];
        }
    };

    public ArrayList<MerchantDescModel.Response_value> getResponse_value() {
        return response_value;
    }

    public void setResponse_value(ArrayList<MerchantDescModel.Response_value> response_value) {
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
        @SerializedName("desc_name")
        private String desc_name;





        protected Response_value(@NonNull Parcel in) {


            desc_number = in.readString();
            desc_name = in.readString();


        }

        public static final Creator<MerchantDescModel.Response_value> CREATOR = new Creator<MerchantDescModel.Response_value>() {
            @NonNull
            @Override
            public MerchantDescModel.Response_value createFromParcel(@NonNull Parcel in) {
                return new MerchantDescModel.Response_value(in);
            }

            @NonNull
            @Override
            public MerchantDescModel.Response_value[] newArray(int size) {
                return new MerchantDescModel.Response_value[size];
            }
        };

        @Nullable
        public String getDesc_number() {
            return desc_number;
        }

        public void setDesc_number(String desc_number) {
            this.desc_number = desc_number;
        }

        @Nullable
        public String getDesc_name() {
            return desc_name;
        }

        public void setDesc_name(String desc_name) {
            this.desc_name = desc_name;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {


            dest.writeString(this.desc_number);
            dest.writeString(this.desc_name);

        }

        @Override
        public int describeContents() {
            return 0;
        }
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

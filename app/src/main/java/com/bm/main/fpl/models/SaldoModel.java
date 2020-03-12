package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class SaldoModel extends BaseObject implements Parcelable {

    @SerializedName("response_value")
    private String response_value;
    private SaldoModel(Parcel in) {
    }

    public static final Creator<SaldoModel> CREATOR = new Creator<SaldoModel>() {
        @NonNull
        @Override
        public SaldoModel createFromParcel(Parcel in) {
            return new SaldoModel(in);
        }

        @NonNull
        @Override
        public SaldoModel[] newArray(int size) {
            return new SaldoModel[size];
        }
    };

    public String getResponse_value() {
        return response_value;
    }

    public void setResponse_value(String response_value) {
        this.response_value = response_value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.response_value);
    }


}

package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BankVaNewModelResponse_valueVa_descSteper_value implements Parcelable {
    public static final Creator<BankVaNewModelResponse_valueVa_descSteper_value> CREATOR = new Creator<BankVaNewModelResponse_valueVa_descSteper_value>() {
        @NonNull
        @Override
        public BankVaNewModelResponse_valueVa_descSteper_value createFromParcel(@NonNull Parcel source) {
            BankVaNewModelResponse_valueVa_descSteper_value var = new BankVaNewModelResponse_valueVa_descSteper_value();
            var.desc_name = source.readString();
            var.desc_number = source.readString();
            return var;
        }

        @NonNull
        @Override
        public BankVaNewModelResponse_valueVa_descSteper_value[] newArray(int size) {
            return new BankVaNewModelResponse_valueVa_descSteper_value[size];
        }
    };
    @Nullable
    private String desc_name;
    @Nullable
    private String desc_number;

    @Nullable
    public String getDesc_name() {
        return this.desc_name;
    }

    public void setDesc_name(String desc_name) {
        this.desc_name = desc_name;
    }

    @Nullable
    public String getDesc_number() {
        return this.desc_number;
    }

    public void setDesc_number(String desc_number) {
        this.desc_number = desc_number;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.desc_name);
        dest.writeString(this.desc_number);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

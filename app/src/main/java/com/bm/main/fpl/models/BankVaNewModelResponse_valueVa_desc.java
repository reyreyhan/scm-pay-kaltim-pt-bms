package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BankVaNewModelResponse_valueVa_desc implements Parcelable {
    public static final Creator<BankVaNewModelResponse_valueVa_desc> CREATOR = new Creator<BankVaNewModelResponse_valueVa_desc>() {
        @NonNull
        @Override
        public BankVaNewModelResponse_valueVa_desc createFromParcel(@NonNull Parcel source) {
            BankVaNewModelResponse_valueVa_desc var = new BankVaNewModelResponse_valueVa_desc();
            var.note = source.readString();
            var.steper_value = source.createTypedArray(BankVaNewModelResponse_valueVa_descSteper_value.CREATOR);
            var.type = source.readString();
            var.bank_name = source.readString();
            return var;
        }

        @NonNull
        @Override
        public BankVaNewModelResponse_valueVa_desc[] newArray(int size) {
            return new BankVaNewModelResponse_valueVa_desc[size];
        }
    };
    @Nullable
    private String note;
    @Nullable
    private BankVaNewModelResponse_valueVa_descSteper_value[] steper_value;
    @Nullable
    private String type;
    @Nullable
    private String bank_name;

    @Nullable
    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Nullable
    public BankVaNewModelResponse_valueVa_descSteper_value[] getSteper_value() {
        return this.steper_value;
    }

    public void setSteper_value(BankVaNewModelResponse_valueVa_descSteper_value[] steper_value) {
        this.steper_value = steper_value;
    }

    @Nullable
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Nullable
    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.note);
        dest.writeTypedArray(this.steper_value, flags);
        dest.writeString(this.type);
        dest.writeString(this.bank_name);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

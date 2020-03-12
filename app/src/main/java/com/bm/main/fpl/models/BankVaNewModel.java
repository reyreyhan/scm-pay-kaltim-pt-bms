package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BankVaNewModel extends BaseObject  implements Parcelable {


    public static final Creator<BankVaNewModel> CREATOR = new Creator<BankVaNewModel>() {
        @NonNull
        @Override
        public BankVaNewModel createFromParcel(@NonNull Parcel source) {
            BankVaNewModel var = new BankVaNewModel();
            var.response_value = source.createTypedArray(BankVaNewModelResponse_value.CREATOR);
            return var;
        }

        @NonNull
        @Override
        public BankVaNewModel[] newArray(int size) {
            return new BankVaNewModel[size];
        }
    };
    @Nullable
    private BankVaNewModelResponse_value[] response_value;

    @Nullable
    public BankVaNewModelResponse_value[] getResponse_value() {
        return this.response_value;
    }

    public void setResponse_value(BankVaNewModelResponse_value[] response_value) {
        this.response_value = response_value;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeTypedArray(this.response_value, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

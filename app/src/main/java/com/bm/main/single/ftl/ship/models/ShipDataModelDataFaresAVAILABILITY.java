package com.bm.main.single.ftl.ship.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ShipDataModelDataFaresAVAILABILITY implements Parcelable {
    public static final Creator<ShipDataModelDataFaresAVAILABILITY> CREATOR = new Creator<ShipDataModelDataFaresAVAILABILITY>() {
        @NonNull
        @Override
        public ShipDataModelDataFaresAVAILABILITY createFromParcel(@NonNull Parcel source) {
            ShipDataModelDataFaresAVAILABILITY var = new ShipDataModelDataFaresAVAILABILITY();
            var.F = source.readString();
            var.M = source.readString();
            return var;
        }

        @NonNull
        @Override
        public ShipDataModelDataFaresAVAILABILITY[] newArray(int size) {
            return new ShipDataModelDataFaresAVAILABILITY[size];
        }
    };
    @Nullable
    private String F;
    @Nullable
    private String M;

    @Nullable
    public String getF() {
        return this.F;
    }

    public void setF(String F) {
        this.F = F;
    }

    @Nullable
    public String getM() {
        return this.M;
    }

    public void setM(String M) {
        this.M = M;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.F);
        dest.writeString(this.M);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

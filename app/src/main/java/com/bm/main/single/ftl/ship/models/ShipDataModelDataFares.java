package com.bm.main.single.ftl.ship.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ShipDataModelDataFares implements Parcelable {
    public static final Creator<ShipDataModelDataFares> CREATOR = new Creator<ShipDataModelDataFares>() {
        @NonNull
        @Override
        public ShipDataModelDataFares createFromParcel(@NonNull Parcel source) {
            ShipDataModelDataFares var = new ShipDataModelDataFares();
            var.AVAILABILITY = source.readParcelable(ShipDataModelDataFaresAVAILABILITY.class.getClassLoader());
            var.FARE_DETAIL = source.readParcelable(ShipDataModelDataFaresFARE_DETAIL.class.getClassLoader());
            var.CLASS = source.readString();
            var.SUBCLASS = source.readString();
            return var;
        }

        @NonNull
        @Override
        public ShipDataModelDataFares[] newArray(int size) {
            return new ShipDataModelDataFares[size];
        }
    };
    @Nullable
    private ShipDataModelDataFaresAVAILABILITY AVAILABILITY;
    @Nullable
    private ShipDataModelDataFaresFARE_DETAIL FARE_DETAIL;
    @Nullable
    private String CLASS;
    @Nullable
    private String SUBCLASS;

    @Nullable
    public ShipDataModelDataFaresAVAILABILITY getAVAILABILITY() {
        return this.AVAILABILITY;
    }

    public void setAVAILABILITY(ShipDataModelDataFaresAVAILABILITY AVAILABILITY) {
        this.AVAILABILITY = AVAILABILITY;
    }

    @Nullable
    public ShipDataModelDataFaresFARE_DETAIL getFARE_DETAIL() {
        return this.FARE_DETAIL;
    }

    public void setFARE_DETAIL(ShipDataModelDataFaresFARE_DETAIL FARE_DETAIL) {
        this.FARE_DETAIL = FARE_DETAIL;
    }

    @Nullable
    public String getCLASS() {
        return this.CLASS;
    }

    public void setCLASS(String CLASS) {
        this.CLASS = CLASS;
    }

    @Nullable
    public String getSUBCLASS() {
        return this.SUBCLASS;
    }

    public void setSUBCLASS(String SUBCLASS) {
        this.SUBCLASS = SUBCLASS;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(this.AVAILABILITY, flags);
        dest.writeParcelable(this.FARE_DETAIL, flags);
        dest.writeString(this.CLASS);
        dest.writeString(this.SUBCLASS);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

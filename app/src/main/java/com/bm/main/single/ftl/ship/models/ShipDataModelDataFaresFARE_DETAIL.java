package com.bm.main.single.ftl.ship.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ShipDataModelDataFaresFARE_DETAIL implements Parcelable {
    public static final Creator<ShipDataModelDataFaresFARE_DETAIL> CREATOR = new Creator<ShipDataModelDataFaresFARE_DETAIL>() {
        @NonNull
        @Override
        public ShipDataModelDataFaresFARE_DETAIL createFromParcel(@NonNull Parcel source) {
            ShipDataModelDataFaresFARE_DETAIL var = new ShipDataModelDataFaresFARE_DETAIL();
            var.A = source.readParcelable(ShipDataModelDataFaresFARE_DETAILA.class.getClassLoader());
            var.C = source.readParcelable(ShipDataModelDataFaresFARE_DETAILC.class.getClassLoader());
            var.I = source.readParcelable(ShipDataModelDataFaresFARE_DETAILI.class.getClassLoader());
            return var;
        }

        @NonNull
        @Override
        public ShipDataModelDataFaresFARE_DETAIL[] newArray(int size) {
            return new ShipDataModelDataFaresFARE_DETAIL[size];
        }
    };
    @Nullable
    private ShipDataModelDataFaresFARE_DETAILA A;
    @Nullable
    private ShipDataModelDataFaresFARE_DETAILC C;
    @Nullable
    private ShipDataModelDataFaresFARE_DETAILI I;

    @Nullable
    public ShipDataModelDataFaresFARE_DETAILA getA() {
        return this.A;
    }

    public void setA(ShipDataModelDataFaresFARE_DETAILA A) {
        this.A = A;
    }

    @Nullable
    public ShipDataModelDataFaresFARE_DETAILC getC() {
        return this.C;
    }

    public void setC(ShipDataModelDataFaresFARE_DETAILC C) {
        this.C = C;
    }

    @Nullable
    public ShipDataModelDataFaresFARE_DETAILI getI() {
        return this.I;
    }

    public void setI(ShipDataModelDataFaresFARE_DETAILI I) {
        this.I = I;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(this.A, flags);
        dest.writeParcelable(this.C, flags);
        dest.writeParcelable(this.I, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

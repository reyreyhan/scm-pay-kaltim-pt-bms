package com.bm.main.single.ftl.ship.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ShipDataModelDataFaresFARE_DETAILC implements Parcelable {
    public static final Creator<ShipDataModelDataFaresFARE_DETAILC> CREATOR = new Creator<ShipDataModelDataFaresFARE_DETAILC>() {
        @NonNull
        @Override
        public ShipDataModelDataFaresFARE_DETAILC createFromParcel(@NonNull Parcel source) {
            ShipDataModelDataFaresFARE_DETAILC var = new ShipDataModelDataFaresFARE_DETAILC();
            var.TOTAL = source.readString();
            var.ARV_PORT_TRANSPORT_FEE = source.readString();
            var.INSURANCE = source.readString();
            var.PORT_PASS = source.readString();
            var.DEP_PORT_TRANSPORT_FEE = source.readString();
            var.FARE = source.readString();
            return var;
        }

        @NonNull
        @Override
        public ShipDataModelDataFaresFARE_DETAILC[] newArray(int size) {
            return new ShipDataModelDataFaresFARE_DETAILC[size];
        }
    };
    @Nullable
    private String TOTAL;
    @Nullable
    private String ARV_PORT_TRANSPORT_FEE;
    @Nullable
    private String INSURANCE;
    @Nullable
    private String PORT_PASS;
    @Nullable
    private String DEP_PORT_TRANSPORT_FEE;
    @Nullable
    private String FARE;

    @Nullable
    public String getTOTAL() {
        return this.TOTAL;
    }

    public void setTOTAL(String TOTAL) {
        this.TOTAL = TOTAL;
    }

    @Nullable
    public String getARV_PORT_TRANSPORT_FEE() {
        return this.ARV_PORT_TRANSPORT_FEE;
    }

    public void setARV_PORT_TRANSPORT_FEE(String ARV_PORT_TRANSPORT_FEE) {
        this.ARV_PORT_TRANSPORT_FEE = ARV_PORT_TRANSPORT_FEE;
    }

    @Nullable
    public String getINSURANCE() {
        return this.INSURANCE;
    }

    public void setINSURANCE(String INSURANCE) {
        this.INSURANCE = INSURANCE;
    }

    @Nullable
    public String getPORT_PASS() {
        return this.PORT_PASS;
    }

    public void setPORT_PASS(String PORT_PASS) {
        this.PORT_PASS = PORT_PASS;
    }

    @Nullable
    public String getDEP_PORT_TRANSPORT_FEE() {
        return this.DEP_PORT_TRANSPORT_FEE;
    }

    public void setDEP_PORT_TRANSPORT_FEE(String DEP_PORT_TRANSPORT_FEE) {
        this.DEP_PORT_TRANSPORT_FEE = DEP_PORT_TRANSPORT_FEE;
    }

    @Nullable
    public String getFARE() {
        return this.FARE;
    }

    public void setFARE(String FARE) {
        this.FARE = FARE;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.TOTAL);
        dest.writeString(this.ARV_PORT_TRANSPORT_FEE);
        dest.writeString(this.INSURANCE);
        dest.writeString(this.PORT_PASS);
        dest.writeString(this.DEP_PORT_TRANSPORT_FEE);
        dest.writeString(this.FARE);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

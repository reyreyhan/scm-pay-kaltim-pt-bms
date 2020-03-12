package com.bm.main.single.ftl.ship.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ShipDataModelData implements Parcelable,Comparable<ShipDataModelData> {
    public static final Creator<ShipDataModelData> CREATOR = new Creator<ShipDataModelData>() {
        @NonNull
        @Override
        public ShipDataModelData createFromParcel(@NonNull Parcel source) {
            ShipDataModelData var = new ShipDataModelData();
            var.SHIP_NAME = source.readString();
            var.ROUTE = source.readString();
            var.DES_CALL = source.readString();
            var.DEP_DATE = source.readString();
            var.ORG_CALL = source.readString();
            var.SHIP_NO = source.readString();
            var.ARV_TIME = source.readString();
            var.fares = source.createTypedArray(ShipDataModelDataFares.CREATOR);
            var.ARV_DATE = source.readString();
            var.DEP_TIME = source.readString();
            return var;
        }

        @NonNull
        @Override
        public ShipDataModelData[] newArray(int size) {
            return new ShipDataModelData[size];
        }
    };
    @Nullable
    private String SHIP_NAME;
    @Nullable
    private String ROUTE;
    @Nullable
    private String DES_CALL;
    @Nullable
    private String DEP_DATE;
    @Nullable
    private String ORG_CALL;
    @Nullable
    private String SHIP_NO;
    @Nullable
    private String ARV_TIME;
    @Nullable
    private ShipDataModelDataFares[] fares;
    @Nullable
    private String ARV_DATE;
    @Nullable
    private String DEP_TIME;

    @Nullable
    public String getSHIP_NAME() {
        return this.SHIP_NAME;
    }

    public void setSHIP_NAME(String SHIP_NAME) {
        this.SHIP_NAME = SHIP_NAME;
    }

    @Nullable
    public String getROUTE() {
        return this.ROUTE;
    }

    public void setROUTE(String ROUTE) {
        this.ROUTE = ROUTE;
    }

    @Nullable
    public String getDES_CALL() {
        return this.DES_CALL;
    }

    public void setDES_CALL(String DES_CALL) {
        this.DES_CALL = DES_CALL;
    }

    @Nullable
    public String getDEP_DATE() {
        return this.DEP_DATE;
    }

    public void setDEP_DATE(String DEP_DATE) {
        this.DEP_DATE = DEP_DATE;
    }

    @Nullable
    public String getORG_CALL() {
        return this.ORG_CALL;
    }

    public void setORG_CALL(String ORG_CALL) {
        this.ORG_CALL = ORG_CALL;
    }

    @Nullable
    public String getSHIP_NO() {
        return this.SHIP_NO;
    }

    public void setSHIP_NO(String SHIP_NO) {
        this.SHIP_NO = SHIP_NO;
    }

    @Nullable
    public String getARV_TIME() {
        return this.ARV_TIME;
    }

    public void setARV_TIME(String ARV_TIME) {
        this.ARV_TIME = ARV_TIME;
    }

    @Nullable
    public ShipDataModelDataFares[] getFares() {
        return this.fares;
    }

    public void setFares(ShipDataModelDataFares[] fares) {
        this.fares = fares;
    }

    @Nullable
    public String getARV_DATE() {
        return this.ARV_DATE;
    }

    public void setARV_DATE(String ARV_DATE) {
        this.ARV_DATE = ARV_DATE;
    }

    @Nullable
    public String getDEP_TIME() {
        return this.DEP_TIME;
    }

    public void setDEP_TIME(String DEP_TIME) {
        this.DEP_TIME = DEP_TIME;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.SHIP_NAME);
        dest.writeString(this.ROUTE);
        dest.writeString(this.DES_CALL);
        dest.writeString(this.DEP_DATE);
        dest.writeString(this.ORG_CALL);
        dest.writeString(this.SHIP_NO);
        dest.writeString(this.ARV_TIME);
        dest.writeTypedArray(this.fares, flags);
        dest.writeString(this.ARV_DATE);
        dest.writeString(this.DEP_TIME);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public int compareTo(@NonNull ShipDataModelData o) {
        return (getDEP_DATE()+getDEP_TIME()).compareTo(o.getDEP_DATE()+o.getDEP_TIME());
    }
}

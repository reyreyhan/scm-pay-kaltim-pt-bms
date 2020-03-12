package com.bm.main.single.ftl.flight.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class FlightListBaggageModelData implements Parcelable {
    public static final Creator<FlightListBaggageModelData> CREATOR = new Creator<FlightListBaggageModelData>() {
        @NonNull
        @Override
        public FlightListBaggageModelData createFromParcel(@NonNull Parcel source) {
            FlightListBaggageModelData var = new FlightListBaggageModelData();
            var.price = source.readInt();
            var.maskapai = source.readString();
            var.weight = source.readInt();
            var.baggage_key = source.readString();
            return var;
        }

        @NonNull
        @Override
        public FlightListBaggageModelData[] newArray(int size) {
            return new FlightListBaggageModelData[size];
        }
    };
    private int price;
    @Nullable
    private String maskapai;
    private int weight;
    @Nullable
    private String baggage_key;

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Nullable
    public String getMaskapai() {
        return this.maskapai;
    }

    public void setMaskapai(String maskapai) {
        this.maskapai = maskapai;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Nullable
    public String getBaggage_key() {
        return this.baggage_key;
    }

    public void setBaggage_key(String baggage_key) {
        this.baggage_key = baggage_key;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(this.price);
        dest.writeString(this.maskapai);
        dest.writeInt(this.weight);
        dest.writeString(this.baggage_key);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

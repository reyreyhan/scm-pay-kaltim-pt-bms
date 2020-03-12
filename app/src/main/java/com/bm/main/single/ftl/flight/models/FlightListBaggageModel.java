package com.bm.main.single.ftl.flight.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bm.main.single.ftl.models.BaseObject;

public class FlightListBaggageModel extends BaseObject implements Parcelable {
    public static final Creator<FlightListBaggageModel> CREATOR = new Creator<FlightListBaggageModel>() {
        @NonNull
        @Override
        public FlightListBaggageModel createFromParcel(@NonNull Parcel source) {
            FlightListBaggageModel var = new FlightListBaggageModel();
            var.data = source.createTypedArray(FlightListBaggageModelData.CREATOR);
            return var;
        }

        @NonNull
        @Override
        public FlightListBaggageModel[] newArray(int size) {
            return new FlightListBaggageModel[size];
        }
    };
    @Nullable
    private FlightListBaggageModelData[] data;

    @Nullable
    public FlightListBaggageModelData[] getData() {
        return this.data;
    }

    public void setData(FlightListBaggageModelData[] data) {
        this.data = data;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeTypedArray(this.data, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

package com.bm.main.single.ftl.flight.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bm.main.single.ftl.models.BaseObject;

public class FlightResponseBookModel extends BaseObject implements Parcelable {
    public static final Creator<FlightResponseBookModel> CREATOR = new Creator<FlightResponseBookModel>() {
        @NonNull
        @Override
        public FlightResponseBookModel createFromParcel(@NonNull Parcel source) {
            FlightResponseBookModel var = new FlightResponseBookModel();
            var.data = source.readParcelable(FlightResponseBookModelData.class.getClassLoader());
            return var;
        }

        @NonNull
        @Override
        public FlightResponseBookModel[] newArray(int size) {
            return new FlightResponseBookModel[size];
        }
    };
    @Nullable
    private FlightResponseBookModelData data;

    @Nullable
    public FlightResponseBookModelData getData() {
        return this.data;
    }

    public void setData(FlightResponseBookModelData data) {
        this.data = data;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(this.data, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

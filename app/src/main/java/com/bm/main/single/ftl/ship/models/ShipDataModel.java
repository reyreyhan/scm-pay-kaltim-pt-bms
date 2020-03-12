package com.bm.main.single.ftl.ship.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bm.main.single.ftl.models.BaseObject;

public class ShipDataModel extends BaseObject implements Parcelable {
    public static final Creator<ShipDataModel> CREATOR = new Creator<ShipDataModel>() {
        @NonNull
        @Override
        public ShipDataModel createFromParcel(@NonNull Parcel source) {
            ShipDataModel var = new ShipDataModel();

            var.data = source.createTypedArray(ShipDataModelData.CREATOR);

            return var;
        }

        @NonNull
        @Override
        public ShipDataModel[] newArray(int size) {
            return new ShipDataModel[size];
        }
    };

    @Nullable
    private ShipDataModelData[] data;




    @Nullable
    public ShipDataModelData[] getData() {
        return this.data;
    }

    public void setData(ShipDataModelData[] data) {
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

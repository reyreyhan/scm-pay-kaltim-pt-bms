package com.bm.main.single.ftl.ship.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bm.main.single.ftl.models.BaseObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rizabudiprasetya on 6/24/17.
 */

public class ShipDestinationModel extends BaseObject implements Parcelable {
    @SerializedName("data")
    private List<Data> data;
    private ShipDestinationModel(Parcel in) {
    }

    public static final Creator<ShipDestinationModel> CREATOR = new Creator<ShipDestinationModel>() {
        @NonNull
        @Override
        public ShipDestinationModel createFromParcel(Parcel in) {
            return new ShipDestinationModel(in);
        }

        @NonNull
        @Override
        public ShipDestinationModel[] newArray(int size) {
            return new ShipDestinationModel[size];
        }
    };

    public List<ShipDestinationModel.Data> getData() {
        return data;
    }

    public void setData(List<ShipDestinationModel.Data> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }




    public static class Data implements Parcelable,Comparable<Data> {

        @Nullable
        @SerializedName("CODE")
        private String code;
        @Nullable
        @SerializedName("NAME")
        private String nama;





        protected Data(@NonNull Parcel in) {
            code = in.readString();
            nama = in.readString();


        }

        public static final Creator<ShipDestinationModel.Data> CREATOR = new Creator<ShipDestinationModel.Data>() {
            @NonNull
            @Override
            public ShipDestinationModel.Data createFromParcel(@NonNull Parcel in) {
                return new ShipDestinationModel.Data(in);
            }

            @NonNull
            @Override
            public ShipDestinationModel.Data[] newArray(int size) {
                return new ShipDestinationModel.Data[size];
            }

        };


        @Nullable
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @Nullable
        public String getNama() {
            return nama;
        }

        public void setNama(String name) {
            this.nama = name;
        }


        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(this.code);
            dest.writeString(this.nama);


        }

        @Override
        public int describeContents() {
            return 0;
        }


        @Override
        public int compareTo(@NonNull Data o) {
            return getNama().compareTo(o.getNama());
        }
    }

}

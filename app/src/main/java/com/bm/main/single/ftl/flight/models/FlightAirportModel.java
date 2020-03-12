package com.bm.main.single.ftl.flight.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bm.main.single.ftl.models.BaseObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FlightAirportModel extends BaseObject  implements Parcelable {
    @SerializedName("data")
    private List<Data> data;
    private FlightAirportModel(Parcel in) {
    }

    public static final Creator<FlightAirportModel> CREATOR = new Creator<FlightAirportModel>() {
        @NonNull
        @Override
        public FlightAirportModel createFromParcel(Parcel in) {
            return new FlightAirportModel(in);
        }

        @NonNull
        @Override
        public FlightAirportModel[] newArray(int size) {
            return new FlightAirportModel[size];
        }
    };

    public List<FlightAirportModel.Data> getData() {
        return data;
    }

    public void setData(List<FlightAirportModel.Data> data) {
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
        @SerializedName("code")
        private String code;
        @Nullable
        @SerializedName("name")
        private String nama;
        @Nullable
        @SerializedName("bandara")
        private String bandara;
        @Nullable
        @SerializedName("group")
        private String group;




        protected Data(@NonNull Parcel in) {
            code = in.readString();
            nama = in.readString();
            bandara= in.readString();
            group = in.readString();

        }

        public static final Creator<FlightAirportModel.Data> CREATOR = new Creator<FlightAirportModel.Data>() {
            @NonNull
            @Override
            public FlightAirportModel.Data createFromParcel(@NonNull Parcel in) {
                return new FlightAirportModel.Data(in);
            }

            @NonNull
            @Override
            public FlightAirportModel.Data[] newArray(int size) {
                return new FlightAirportModel.Data[size];
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

        @Nullable
        public String getBandara() {
            return bandara;
        }

        public void setBandara(String bandara) {
            this.bandara = bandara;
        }

        @Nullable
        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(this.code);
            dest.writeString(this.nama);
            dest.writeString(this.bandara);
            dest.writeString(this.group);

        }

        @Override
        public int describeContents() {
            return 0;
        }


        @Override
        public int compareTo(@NonNull Data o) {
            return getGroup().compareTo(o.getGroup());
        }
    }


}

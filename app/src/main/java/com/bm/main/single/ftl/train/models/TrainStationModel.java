package com.bm.main.single.ftl.train.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bm.main.single.ftl.models.BaseObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrainStationModel extends BaseObject  implements Parcelable {
    @SerializedName("data")
    private List<Data> data;
    private TrainStationModel(Parcel in) {
    }

    public static final Creator<TrainStationModel> CREATOR = new Creator<TrainStationModel>() {
        @NonNull
        @Override
        public TrainStationModel createFromParcel(Parcel in) {
            return new TrainStationModel(in);
        }

        @NonNull
        @Override
        public TrainStationModel[] newArray(int size) {
            return new TrainStationModel[size];
        }
    };

    public List<TrainStationModel.Data> getData() {
        return data;
    }

    public void setData(List<TrainStationModel.Data> data) {
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
        @SerializedName("id_stasiun")
        private String id_stasiun;
        @Nullable
        @SerializedName("nama_stasiun")
        private String nama_stasiun;
        @Nullable
        @SerializedName("nama_kota")
        private String nama_kota;
        @SerializedName("is_active")
        private int is_active;




        protected Data(@NonNull Parcel in) {
            id_stasiun = in.readString();
            nama_stasiun = in.readString();
            nama_kota= in.readString();
            is_active = in.readInt();

        }

        public static final Creator<TrainStationModel.Data> CREATOR = new Creator<TrainStationModel.Data>() {
            @NonNull
            @Override
            public TrainStationModel.Data createFromParcel(@NonNull Parcel in) {
                return new TrainStationModel.Data(in);
            }

            @NonNull
            @Override
            public TrainStationModel.Data[] newArray(int size) {
                return new TrainStationModel.Data[size];
            }

        };

        @Nullable
        public String getId_stasiun() {
            return id_stasiun;
        }

        public void setId_stasiun(String id_stasiun) {
            this.id_stasiun = id_stasiun;
        }

        @Nullable
        public String getNama_stasiun() {
            return nama_stasiun;
        }

        public void setNama_stasiun(String nama_stasiun) {
            this.nama_stasiun = nama_stasiun;
        }

        @Nullable
        public String getNama_kota() {
            return nama_kota;
        }

        public void setNama_kota(String nama_kota) {
            this.nama_kota = nama_kota;
        }

        public int getIs_active() {
            return is_active;
        }

        public void setIs_active(int is_active) {
            this.is_active = is_active;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(this.id_stasiun);
            dest.writeString(this.nama_stasiun);
            dest.writeString(this.nama_kota);
            dest.writeInt(this.is_active);

        }

        @Override
        public int describeContents() {
            return 0;
        }


        @Override
        public int compareTo(@NonNull Data o) {
            return getNama_kota().compareTo(o.getNama_kota());
        }
    }


}

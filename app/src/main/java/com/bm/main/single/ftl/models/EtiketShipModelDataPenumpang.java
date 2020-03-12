package com.bm.main.single.ftl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class EtiketShipModelDataPenumpang implements Parcelable {

    public static final Creator<EtiketShipModelDataPenumpang> CREATOR = new Creator<EtiketShipModelDataPenumpang>() {
        @NonNull
        @Override
        public EtiketShipModelDataPenumpang createFromParcel(@NonNull Parcel source) {
            EtiketShipModelDataPenumpang var = new EtiketShipModelDataPenumpang();
            var.nama = source.readString();

//            var.title = source.readString();
//            var.status = source.readString();

            return var;
        }

        @NonNull
        @Override
        public EtiketShipModelDataPenumpang[] newArray(int size) {
            return new EtiketShipModelDataPenumpang[size];
        }
    };


    private static final long serialVersionUID = 175814590831088551L;
    @Nullable
    @SerializedName("nama")
    private String nama;
//    private String title;
//    private String status;

    public String getNama() {
        return this.nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

//    public String getTitle() {
//        return this.title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getStatus() {
//        return this.status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }


    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.nama);

//        dest.writeString(this.title);
//        dest.writeString(this.status);

    }

    @Override
    public int describeContents() {
        return 0;
    }
}

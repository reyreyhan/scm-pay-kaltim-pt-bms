package com.bm.main.single.ftl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class EtiketTrainModelDataPenumpang implements Parcelable {

    public static final Creator<EtiketTrainModelDataPenumpang> CREATOR = new Creator<EtiketTrainModelDataPenumpang>() {
        @NonNull
        @Override
        public EtiketTrainModelDataPenumpang createFromParcel(@NonNull Parcel source) {
            EtiketTrainModelDataPenumpang var = new EtiketTrainModelDataPenumpang();
            var.nama = source.readString();

//            var.title = source.readString();
//            var.status = source.readString();

            return var;
        }

        @NonNull
        @Override
        public EtiketTrainModelDataPenumpang[] newArray(int size) {
            return new EtiketTrainModelDataPenumpang[size];
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

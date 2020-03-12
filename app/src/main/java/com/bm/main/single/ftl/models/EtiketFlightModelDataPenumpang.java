package com.bm.main.single.ftl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class EtiketFlightModelDataPenumpang  implements Parcelable {

    public static final Creator<EtiketFlightModelDataPenumpang> CREATOR = new Creator<EtiketFlightModelDataPenumpang>() {
        @NonNull
        @Override
        public EtiketFlightModelDataPenumpang createFromParcel(@NonNull Parcel source) {
            EtiketFlightModelDataPenumpang var = new EtiketFlightModelDataPenumpang();
            var.nama = source.readString();

            var.title = source.readString();
            var.status = source.readString();

            return var;
        }

        @NonNull
        @Override
        public EtiketFlightModelDataPenumpang[] newArray(int size) {
            return new EtiketFlightModelDataPenumpang[size];
        }
    };


    private static final long serialVersionUID = 175814590831088551L;
    @Nullable
    @SerializedName("nama")
    private String nama;
    @Nullable
    @SerializedName("title")
    private String title;
    @Nullable
    @SerializedName("status")
    private String status;

    public String getNama() {
        return this.nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.nama);

        dest.writeString(this.title);
        dest.writeString(this.status);

    }

    @Override
    public int describeContents() {
        return 0;
    }
}

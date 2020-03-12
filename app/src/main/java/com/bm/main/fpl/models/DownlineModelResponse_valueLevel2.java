package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DownlineModelResponse_valueLevel2 implements Parcelable {
    public static final Creator<DownlineModelResponse_valueLevel2> CREATOR = new Creator<DownlineModelResponse_valueLevel2>() {
        @NonNull
        @Override
        public DownlineModelResponse_valueLevel2 createFromParcel(@NonNull Parcel source) {
            DownlineModelResponse_valueLevel2 var = new DownlineModelResponse_valueLevel2();
            var.nomor_whatsapp_outlet2 = source.readString();
            var.nama_pemilik2 = source.readString();
            var.notelp_pemilik2 = source.readString();
            var.jumlah_downline2 = source.readString();
            var.id_outlet2 = source.readString();
            return var;
        }

        @NonNull
        @Override
        public DownlineModelResponse_valueLevel2[] newArray(int size) {
            return new DownlineModelResponse_valueLevel2[size];
        }
    };
    @Nullable
    private String nomor_whatsapp_outlet2;
    @Nullable
    private String nama_pemilik2;
    @Nullable
    private String notelp_pemilik2;
    @Nullable
    private String jumlah_downline2;
    @Nullable
    private String id_outlet2;

    @Nullable
    public String getNomor_whatsapp_outlet2() {
        return nomor_whatsapp_outlet2;
    }

    public void setNomor_whatsapp_outlet2(String nomor_whatsapp_outlet2) {
        this.nomor_whatsapp_outlet2 = nomor_whatsapp_outlet2;
    }

    @Nullable
    public String getNama_pemilik2() {
        return nama_pemilik2;
    }

    public void setNama_pemilik2(String nama_pemilik2) {
        this.nama_pemilik2 = nama_pemilik2;
    }

    @Nullable
    public String getNotelp_pemilik2() {
        return notelp_pemilik2;
    }

    public void setNotelp_pemilik2(String notelp_pemilik2) {
        this.notelp_pemilik2 = notelp_pemilik2;
    }

    @Nullable
    public String getJumlah_downline2() {
        return jumlah_downline2;
    }

    public void setJumlah_downline2(String jumlah_downline2) {
        this.jumlah_downline2 = jumlah_downline2;
    }

    @Nullable
    public String getId_outlet2() {
        return id_outlet2;
    }

    public void setId_outlet2(String id_outlet2) {
        this.id_outlet2 = id_outlet2;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.nomor_whatsapp_outlet2);
        dest.writeString(this.nama_pemilik2);
        dest.writeString(this.notelp_pemilik2);
        dest.writeString(this.jumlah_downline2);
        dest.writeString(this.id_outlet2);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

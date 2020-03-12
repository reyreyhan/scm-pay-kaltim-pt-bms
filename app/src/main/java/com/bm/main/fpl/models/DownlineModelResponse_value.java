package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DownlineModelResponse_value implements Parcelable {
    public static final Creator<DownlineModelResponse_value> CREATOR = new Creator<DownlineModelResponse_value>() {
        @NonNull
        @Override
        public DownlineModelResponse_value createFromParcel(@NonNull Parcel source) {
            DownlineModelResponse_value var = new DownlineModelResponse_value();
            var.nomor_whatsapp_outlet = source.readString();
            var.nama_pemilik = source.readString();
            var.notelp_pemilik = source.readString();
            var.jumlah_downline = source.readString();
            var.id_outlet = source.readString();
            var.level2 = source.createTypedArray(DownlineModelResponse_valueLevel2.CREATOR);
            return var;
        }

        @NonNull
        @Override
        public DownlineModelResponse_value[] newArray(int size) {
            return new DownlineModelResponse_value[size];
        }
    };
    @Nullable
    private String nomor_whatsapp_outlet;
    @Nullable
    private String nama_pemilik;
    @Nullable
    private String notelp_pemilik;
    @Nullable
    private String jumlah_downline;
    @Nullable
    private String id_outlet;
    @Nullable
    private DownlineModelResponse_valueLevel2[] level2;

    @Nullable
    public String getNomor_whatsapp_outlet() {
        return this.nomor_whatsapp_outlet;
    }

    public void setNomor_whatsapp_outlet(String nomor_whatsapp_outlet) {
        this.nomor_whatsapp_outlet = nomor_whatsapp_outlet;
    }

    @Nullable
    public String getNama_pemilik() {
        return this.nama_pemilik;
    }

    public void setNama_pemilik(String nama_pemilik) {
        this.nama_pemilik = nama_pemilik;
    }

    @Nullable
    public String getNotelp_pemilik() {
        return this.notelp_pemilik;
    }

    public void setNotelp_pemilik(String notelp_pemilik) {
        this.notelp_pemilik = notelp_pemilik;
    }

    @Nullable
    public String getJumlah_downline() {
        return this.jumlah_downline;
    }

    public void setJumlah_downline(String jumlah_downline) {
        this.jumlah_downline = jumlah_downline;
    }

    @Nullable
    public String getId_outlet() {
        return this.id_outlet;
    }

    public void setId_outlet(String id_outlet) {
        this.id_outlet = id_outlet;
    }

    @Nullable
    public DownlineModelResponse_valueLevel2[] getLevel2() {
        return this.level2;
    }

    public void setLevel2(DownlineModelResponse_valueLevel2[] level2) {
        this.level2 = level2;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.nomor_whatsapp_outlet);
        dest.writeString(this.nama_pemilik);
        dest.writeString(this.notelp_pemilik);
        dest.writeString(this.jumlah_downline);
        dest.writeString(this.id_outlet);
        dest.writeTypedArray(this.level2, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

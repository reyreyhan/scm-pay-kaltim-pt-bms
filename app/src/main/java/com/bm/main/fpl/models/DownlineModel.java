package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DownlineModel extends BaseObject  implements Parcelable {

    @SerializedName("response_value")
    private ArrayList<Response_value> response_value;


    private DownlineModel(Parcel in) {
    }

    public static final Creator<DownlineModel> CREATOR = new Creator<DownlineModel>() {
        @NonNull
        @Override
        public DownlineModel createFromParcel(Parcel in) {
            return new DownlineModel(in);
        }

        @NonNull
        @Override
        public DownlineModel[] newArray(int size) {
            return new DownlineModel[size];
        }
    };

    public ArrayList<DownlineModel.Response_value> getResponse_value() {
        return response_value;
    }

    public void setResponse_value(ArrayList<DownlineModel.Response_value> response_value) {
        this.response_value = response_value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public static class Response_value implements Parcelable {

        @Nullable
        @SerializedName("nama_pemilik")
        private String nama_pemilik;

        @Nullable
        @SerializedName("id_outlet")
        private String id_outlet;

        @Nullable
        @SerializedName("notelp_pemilik")
        private String notelp_pemilik;

        @Nullable
        @SerializedName("nomor_whatsapp_outlet")
        private String nomor_whatsapp_outlet;

        @Nullable
        @SerializedName("jumlah_downline")
        private String jumlah_downline;


        @Nullable
        @SerializedName("level2")
        private DownlineModelResponse_valueLevel2[] level2;



        protected Response_value(@NonNull Parcel in) {
            nama_pemilik = in.readString();
            level2 = in.createTypedArray(DownlineModelResponse_valueLevel2.CREATOR);
            id_outlet = in.readString();
            notelp_pemilik = in.readString();
            nomor_whatsapp_outlet = in.readString();
            jumlah_downline = in.readString();




        }

        public static final Creator<DownlineModel.Response_value> CREATOR = new Creator<DownlineModel.Response_value>() {
            @NonNull
            @Override
            public DownlineModel.Response_value createFromParcel(@NonNull Parcel in) {
                return new DownlineModel.Response_value(in);
            }

            @NonNull
            @Override
            public DownlineModel.Response_value[] newArray(int size) {
                return new DownlineModel.Response_value[size];
            }
        };


        @Nullable
        public String getNama_pemilik() {
            return nama_pemilik;
        }

        public void setNama_pemilik(String nama_pemilik) {
            this.nama_pemilik = nama_pemilik;
        }

        @Nullable
        public String getId_outlet() {
            return id_outlet;
        }

        public void setId_outlet(String id_outlet) {
            this.id_outlet = id_outlet;
        }

        @Nullable
        public String getNotelp_pemilik() {
            return notelp_pemilik;
        }

        public void setNotelp_pemilik(String notelp_pemilik) {
            this.notelp_pemilik = notelp_pemilik;
        }

        @Nullable
        public String getNomor_whatsapp_outlet() {
            return nomor_whatsapp_outlet;
        }

        public void setNomor_whatsapp_outlet(String nomor_whatsapp_outlet) {
            this.nomor_whatsapp_outlet = nomor_whatsapp_outlet;
        }

        @Nullable
        public String getJumlah_downline() {
            return jumlah_downline;
        }

        public void setJumlah_downline(String jumlah_downline) {
            this.jumlah_downline = jumlah_downline;
        }

        @Nullable
        public DownlineModelResponse_valueLevel2[] getLevel2() {
            return level2;
        }



        public void setLevel2(DownlineModelResponse_valueLevel2[] level2) {
            this.level2 = level2;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(this.nama_pemilik);

            dest.writeString(this.id_outlet);
            dest.writeString(this.notelp_pemilik);
            dest.writeString(this.nomor_whatsapp_outlet);
            dest.writeString(this.jumlah_downline);

            dest.writeTypedArray(this.level2, flags);

        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}

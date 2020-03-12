package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LaporanKomisiModel extends BaseObject implements Parcelable {

    @SerializedName("response_value")
    private ArrayList<Response_value> response_value;

    @SerializedName("response_cetak")
    private String response_cetak;
    private LaporanKomisiModel(Parcel in) {
    }

    public static final Creator<LaporanKomisiModel> CREATOR = new Creator<LaporanKomisiModel>() {
        @NonNull
        @Override
        public LaporanKomisiModel createFromParcel(Parcel in) {
            return new LaporanKomisiModel(in);
        }

        @NonNull
        @Override
        public LaporanKomisiModel[] newArray(int size) {
            return new LaporanKomisiModel[size];
        }
    };

    public ArrayList<LaporanKomisiModel.Response_value> getResponse_value() {
        return response_value;
    }

    public void setResponse_value(ArrayList<LaporanKomisiModel.Response_value> response_value) {
        this.response_value = response_value;
    }

    public String getResponse_cetak() {
        return response_cetak;
    }

    public void setResponse_cetak(String response_cetak) {
        this.response_cetak = response_cetak;
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
        @SerializedName("id_transaksi")
        private String id_transaksi;

        @Nullable
        @SerializedName("keterangan")
        private String keterangan;

        @Nullable
        @SerializedName("jumlah")
        private String jumlah;
        @Nullable
        @SerializedName("status")
        private String status;
        @Nullable
        @SerializedName("initstatus")
        private String initstatus;




        protected Response_value(@NonNull Parcel in) {
            id_transaksi = in.readString();
            keterangan = in.readString();
            jumlah = in.readString();
            status = in.readString();
            initstatus = in.readString();




        }

        public static final Creator<LaporanKomisiModel.Response_value> CREATOR = new Creator<LaporanKomisiModel.Response_value>() {
            @NonNull
            @Override
            public LaporanKomisiModel.Response_value createFromParcel(@NonNull Parcel in) {
                return new LaporanKomisiModel.Response_value(in);
            }

            @NonNull
            @Override
            public LaporanKomisiModel.Response_value[] newArray(int size) {
                return new LaporanKomisiModel.Response_value[size];
            }
        };

        @Nullable
        public String getId_transaksi() {
            return id_transaksi;
        }

        public void setId_transaksi(String id_transaksi) {
            this.id_transaksi = id_transaksi;
        }

        @Nullable
        public String getKeterangan() {
            return keterangan;
        }

        public void setKeterangan(String keterangan) {
            this.keterangan = keterangan;
        }

        @Nullable
        public String getJumlah() {
            return jumlah;
        }

        public void setJumlah(String jumlah) {
            this.jumlah = jumlah;
        }

        @Nullable
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Nullable
        public String getInitstatus() {
            return initstatus;
        }

        public void setInitstatus(String initstatus) {
            this.initstatus = initstatus;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(this.id_transaksi);
            dest.writeString(this.keterangan);
            dest.writeString(this.jumlah);
            dest.writeString(this.status);
            dest.writeString(this.initstatus);



        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}

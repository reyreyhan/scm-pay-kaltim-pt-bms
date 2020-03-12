package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LaporanMutasiModel extends BaseObject implements Parcelable {

    @SerializedName("response_value")
    private ArrayList<Response_value> response_value;

    @SerializedName("response_cetak")
    private String response_cetak;
    private LaporanMutasiModel(Parcel in) {
    }

    public static final Creator<LaporanMutasiModel> CREATOR = new Creator<LaporanMutasiModel>() {
        @NonNull
        @Override
        public LaporanMutasiModel createFromParcel(Parcel in) {
            return new LaporanMutasiModel(in);
        }

        @NonNull
        @Override
        public LaporanMutasiModel[] newArray(int size) {
            return new LaporanMutasiModel[size];
        }
    };

    public ArrayList<LaporanMutasiModel.Response_value> getResponse_value() {
        return response_value;
    }

    public void setResponse_value(ArrayList<LaporanMutasiModel.Response_value> response_value) {
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
        @SerializedName("id_mutasi")
        private String id_mutasi;
        @Nullable
        @SerializedName("tanggal_mutasi_toshow")
        private String tanggal_mutasi_toshow;
        @Nullable
        @SerializedName("debet")
        private String debet;
        @Nullable
        @SerializedName("kredit")
        private String kredit;
        @Nullable
        @SerializedName("keterangan")
        private String keterangan;
        @Nullable
        @SerializedName("current_balance")
        private String current_balance;



        protected Response_value(@NonNull Parcel in) {
            id_mutasi = in.readString();
            tanggal_mutasi_toshow = in.readString();
            debet = in.readString();
            kredit = in.readString();
            keterangan = in.readString();
            current_balance = in.readString();



        }

        public static final Creator<LaporanMutasiModel.Response_value> CREATOR = new Creator<LaporanMutasiModel.Response_value>() {
            @NonNull
            @Override
            public LaporanMutasiModel.Response_value createFromParcel(@NonNull Parcel in) {
                return new LaporanMutasiModel.Response_value(in);
            }

            @NonNull
            @Override
            public LaporanMutasiModel.Response_value[] newArray(int size) {
                return new LaporanMutasiModel.Response_value[size];
            }
        };

        @Nullable
        public String getId_mutasi() {
            return id_mutasi;
        }

        public void setId_mutasi(String id_mutasi) {
            this.id_mutasi = id_mutasi;
        }

        @Nullable
        public String getTanggal_mutasi_toshow() {
            return tanggal_mutasi_toshow;
        }

        public void setTanggal_mutasi_toshow(String tanggal_mutasi_toshow) {
            this.tanggal_mutasi_toshow = tanggal_mutasi_toshow;
        }

        @Nullable
        public String getDebet() {
            return debet;
        }

        public void setDebet(String debet) {
            this.debet = debet;
        }

        @Nullable
        public String getKredit() {
            return kredit;
        }

        public void setKredit(String kredit) {
            this.kredit = kredit;
        }

        @Nullable
        public String getKeterangan() {
            return keterangan;
        }

        public void setKeterangan(String keterangan) {
            this.keterangan = keterangan;
        }

        @Nullable
        public String getCurrent_balance() {
            return current_balance;
        }

        public void setCurrent_balance(String current_balance) {
            this.current_balance = current_balance;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(this.id_mutasi);
            dest.writeString(this.tanggal_mutasi_toshow);
            dest.writeString(this.debet);
            dest.writeString(this.kredit);
            dest.writeString(this.keterangan);
            dest.writeString(this.current_balance);


        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}

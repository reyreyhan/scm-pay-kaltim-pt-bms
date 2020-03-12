package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LaporanTransaksiModel extends BaseObject implements Parcelable {

    @SerializedName("response_value")
    private ArrayList<Response_value> response_value;
    private LaporanTransaksiModel(Parcel in) {
    }

    public static final Creator<LaporanTransaksiModel> CREATOR = new Creator<LaporanTransaksiModel>() {
        @NonNull
        @Override
        public LaporanTransaksiModel createFromParcel(Parcel in) {
            return new LaporanTransaksiModel(in);
        }

        @NonNull
        @Override
        public LaporanTransaksiModel[] newArray(int size) {
            return new LaporanTransaksiModel[size];
        }
    };

    public ArrayList<LaporanTransaksiModel.Response_value> getResponse_value() {
        return response_value;
    }

    public void setResponse_value(ArrayList<LaporanTransaksiModel.Response_value> response_value) {
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
        @SerializedName("id_transaksi")
        private String id_transaksi;
        @Nullable
        @SerializedName("tanggal_trx")
        private String tanggal_trx;
        @Nullable
        @SerializedName("produk")
        private String produk;
        @Nullable
        @SerializedName("keterangan")
        private String keterangan;
        @Nullable
        @SerializedName("status_transaksi")
        private String status_transaksi;
        @Nullable
        @SerializedName("is_pulsa")
        private String is_pulsa;
        @Nullable
        @SerializedName("nominal")
        private String nominal;
        @Nullable
        @SerializedName("info")
        private String info;
        @Nullable
        @SerializedName("sn")
        private String sn;



        protected Response_value(@NonNull Parcel in) {
            id_transaksi = in.readString();
            tanggal_trx = in.readString();
            produk = in.readString();
            keterangan = in.readString();
            status_transaksi = in.readString();
            is_pulsa = in.readString();
            nominal = in.readString();
            info = in.readString();
            sn = in.readString();


        }

        public static final Creator<LaporanTransaksiModel.Response_value> CREATOR = new Creator<LaporanTransaksiModel.Response_value>() {
            @NonNull
            @Override
            public LaporanTransaksiModel.Response_value createFromParcel(@NonNull Parcel in) {
                return new LaporanTransaksiModel.Response_value(in);
            }

            @NonNull
            @Override
            public LaporanTransaksiModel.Response_value[] newArray(int size) {
                return new LaporanTransaksiModel.Response_value[size];
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
        public String getTanggal_trx() {
            return tanggal_trx;
        }

        public void setTanggal_trx(String tanggal_trx) {
            this.tanggal_trx = tanggal_trx;
        }

        @Nullable
        public String getProduk() {
            return produk;
        }

        public void setProduk(String produk) {
            this.produk = produk;
        }

        @Nullable
        public String getKeterangan() {
            return keterangan;
        }

        public void setKeterangan(String keterangan) {
            this.keterangan = keterangan;
        }

        @Nullable
        public String getStatus_transaksi() {
            return status_transaksi;
        }

        public void setStatus_transaksi(String status_transaksi) {
            this.status_transaksi = status_transaksi;
        }

        @Nullable
        public String getIs_pulsa() {
            return is_pulsa;
        }

        public void setIs_pulsa(String is_pulsa) {
            this.is_pulsa = is_pulsa;
        }

        @Nullable
        public String getNominal() {
            return nominal;
        }

        public void setNominal(String nominal) {
            this.nominal = nominal;
        }

        @Nullable
        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        @Nullable
        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(this.id_transaksi);
            dest.writeString(this.tanggal_trx);
            dest.writeString(this.produk);
            dest.writeString(this.keterangan);
            dest.writeString(this.status_transaksi);
            dest.writeString(this.is_pulsa);
            dest.writeString(this.nominal);
            dest.writeString(this.info);
            dest.writeString(this.sn);

        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}

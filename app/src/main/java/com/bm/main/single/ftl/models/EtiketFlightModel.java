package com.bm.main.single.ftl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EtiketFlightModel extends BaseObject implements Parcelable {

    @SerializedName("data")
    private ArrayList<Data> data;

    private EtiketFlightModel(Parcel in) {
    }

    public static final Creator<EtiketFlightModel> CREATOR = new Creator<EtiketFlightModel>() {
        @NonNull
        @Override
        public EtiketFlightModel createFromParcel(Parcel in) {
            return new EtiketFlightModel(in);
        }

        @NonNull
        @Override
        public EtiketFlightModel[] newArray(int size) {
            return new EtiketFlightModel[size];
        }
    };

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<EtiketFlightModel.Data> data) {
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
        @SerializedName("tanggal_keberangkatan")
        private String tanggal_keberangkatan;
        @Nullable
        @SerializedName("tanggal_kedatangan")
        private String tanggal_kedatangan;
        @Nullable
        @SerializedName("url_struk")
        private String url_struk;
        @Nullable
        @SerializedName("airlineIcon")
        private String airlineIcon;
        @Nullable
        @SerializedName("subClass")
        private String subClass;
        @Nullable
        @SerializedName("origin")
        private String origin;
        @Nullable
        @SerializedName("destination")
        private String destination;
        @Nullable
        @SerializedName("id_transaksi")
        private int id_transaksi;
        @Nullable
        @SerializedName("penumpang")
        private EtiketFlightModelDataPenumpang[] penumpang;
        @Nullable
        @SerializedName("jam_kedatangan")
        private String jam_kedatangan;
        @Nullable
        @SerializedName("duration")
        private String duration;
        @Nullable
        @SerializedName("nominal")
        private String nominal;
        @Nullable
        @SerializedName("url_etiket")
        private String url_etiket;
        @Nullable
        @SerializedName("kode_maskapai")
        private String kode_maskapai;
        @Nullable
        @SerializedName("hari_keberangkatan")
        private String hari_keberangkatan;
        @Nullable
        @SerializedName("hari_kedatangan")
        private String hari_kedatangan;
        @Nullable
        @SerializedName("nama_maskapai")
        private String nama_maskapai;
        @Nullable
        @SerializedName("kode_booking")
        private String kode_booking;
        @Nullable
        @SerializedName("nominal_admin")
        private String nominal_admin;
        @Nullable
        @SerializedName("jam_keberangkatan")
        private String jam_keberangkatan;
        
        protected Data(@NonNull Parcel in) {
             tanggal_keberangkatan= in.readString();
             tanggal_kedatangan= in.readString();
             url_struk= in.readString();
           //  url_struk=url_struk.replace("=>", ":");
             airlineIcon= in.readString();
             subClass= in.readString();
             origin= in.readString();
             destination= in.readString();
             id_transaksi= in.readInt();
            penumpang=in.createTypedArray(EtiketFlightModelDataPenumpang.CREATOR);
             jam_kedatangan= in.readString();
             duration= in.readString();
             nominal= in.readString();
         //   this.url_etiket = this.url_etiket.replace("=>", ":");
             url_etiket= in.readString();
          //  url_etiket=url_etiket.replace("=>", ":");
             kode_maskapai= in.readString();
             hari_keberangkatan= in.readString();
             hari_kedatangan= in.readString();
             nama_maskapai= in.readString();
          //  String str = getStringValue(jsonObject, "nama_maskapai");

             kode_booking= in.readString();
             nominal_admin= in.readString();
             jam_keberangkatan= in.readString();
        }


        public static final Creator<EtiketFlightModel.Data> CREATOR = new Creator<EtiketFlightModel.Data>() {
            @NonNull
            @Override
            public EtiketFlightModel.Data createFromParcel(@NonNull Parcel in) {
                return new EtiketFlightModel.Data(in);
            }

            @NonNull
            @Override
            public EtiketFlightModel.Data[] newArray(int size) {
                return new EtiketFlightModel.Data[size];
            }

        };
        public String getTanggal_keberangkatan() {
            return this.tanggal_keberangkatan;
        }

        public void setTanggal_keberangkatan(String tanggal_keberangkatan) {
            this.tanggal_keberangkatan = tanggal_keberangkatan;
        }

        public String getTanggal_kedatangan() {
            return this.tanggal_kedatangan;
        }

        public void setTanggal_kedatangan(String tanggal_kedatangan) {
            this.tanggal_kedatangan = tanggal_kedatangan;
        }

        public String getUrl_struk() {
            return this.url_struk;
        }

        public void setUrl_struk(String url_struk) {
            this.url_struk = url_struk;
        }

        public String getAirlineIcon() {
            return this.airlineIcon;
        }

        public void setAirlineIcon(String airlineIcon) {
            this.airlineIcon = airlineIcon;
        }

        public String getSubClass() {
            return this.subClass;
        }

        public void setSubClass(String subClass) {
            this.subClass = subClass;
        }

        public String getOrigin() {
            return this.origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getDestination() {
            return this.destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public int getId_transaksi() {
            return this.id_transaksi;
        }

        public void setId_transaksi(int id_transaksi) {
            this.id_transaksi = id_transaksi;
        }

        public EtiketFlightModelDataPenumpang[] getPenumpang() {
            return this.penumpang;
        }

        public void setPenumpang(EtiketFlightModelDataPenumpang[] penumpang) {
            this.penumpang = penumpang;
        }

        public String getJam_kedatangan() {
            return this.jam_kedatangan;
        }

        public void setJam_kedatangan(String jam_kedatangan) {
            this.jam_kedatangan = jam_kedatangan;
        }

        public String getDuration() {
            return this.duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getNominal() {
            return this.nominal;
        }

        public void setNominal(String nominal) {
            this.nominal = nominal;
        }

        public String getUrl_etiket() {
            return this.url_etiket;
        }

        public void setUrl_etiket(String url_etiket) {
            this.url_etiket = url_etiket;
        }

        public String getKode_maskapai() {
            return this.kode_maskapai;
        }

        public void setKode_maskapai(String kode_maskapai) {
            this.kode_maskapai = kode_maskapai;
        }

        public String getHari_keberangkatan() {
            return this.hari_keberangkatan;
        }

        public void setHari_keberangkatan(String hari_keberangkatan) {
            this.hari_keberangkatan = hari_keberangkatan;
        }

        public String getHari_kedatangan() {
            return this.hari_kedatangan;
        }

        public void setHari_kedatangan(String hari_kedatangan) {
            this.hari_kedatangan = hari_kedatangan;
        }

        public String getNama_maskapai() {
            return this.nama_maskapai;
        }

        public void setNama_maskapai(String nama_maskapai) {
            this.nama_maskapai = nama_maskapai;
        }

        public String getKode_booking() {
            return this.kode_booking;
        }

        public void setKode_booking(String kode_booking) {
            this.kode_booking = kode_booking;
        }

        public String getNominal_admin() {
            return this.nominal_admin;
        }

        public void setNominal_admin(String nominal_admin) {
            this.nominal_admin = nominal_admin;
        }

        public String getJam_keberangkatan() {
            return this.jam_keberangkatan;
        }

        public void setJam_keberangkatan(String jam_keberangkatan) {
            this.jam_keberangkatan = jam_keberangkatan;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(this.tanggal_keberangkatan);

            dest.writeString(this.tanggal_kedatangan);
            dest.writeString(this.url_struk);
            dest.writeString(this.airlineIcon);
            dest.writeString(this.subClass);
            dest.writeString(this.origin);
            dest.writeString(this.destination);
            dest.writeInt(this.id_transaksi);
            dest.writeTypedArray(this.penumpang, flags);
            dest.writeString(this.jam_kedatangan);
            dest.writeString(this.duration);
            dest.writeString(this.nominal);
            dest.writeString(this.url_etiket);
            dest.writeString(this.kode_maskapai);
            dest.writeString(this.hari_keberangkatan);
            dest.writeString(this.hari_kedatangan);
            dest.writeString(this.nama_maskapai);
            dest.writeString(this.kode_booking);
            dest.writeString(this.nominal_admin);
            dest.writeString(this.jam_keberangkatan);

        }

        @Override
        public int describeContents() {
            return 0;
        }
        @Override
        public int compareTo(@NonNull Data o) {
            return getTanggal_keberangkatan().compareTo(o.getTanggal_keberangkatan());
        }
    }


  
}

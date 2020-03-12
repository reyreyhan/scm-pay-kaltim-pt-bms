package com.bm.main.single.ftl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EtiketTrainModel extends BaseObject implements Parcelable {

    @SerializedName("data")
    private ArrayList<Data> data;

    private EtiketTrainModel(Parcel in) {
    }

    public static final Creator<EtiketTrainModel> CREATOR = new Creator<EtiketTrainModel>() {
        @NonNull
        @Override
        public EtiketTrainModel createFromParcel(Parcel in) {
            return new EtiketTrainModel(in);
        }

        @NonNull
        @Override
        public EtiketTrainModel[] newArray(int size) {
            return new EtiketTrainModel[size];
        }
    };

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<EtiketTrainModel.Data> data) {
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
        @SerializedName("penumpang")
        private EtiketTrainModelDataPenumpang[] penumpang;
        @Nullable
        @SerializedName("id_transaksi")
        private int id_transaksi;
        @Nullable
        @SerializedName("kode_booking")
        private String kode_booking;
        @Nullable
        @SerializedName("origin")
        private String origin;
        @Nullable
        @SerializedName("destination")
        private String destination;
        @Nullable
        @SerializedName("tanggal_keberangkatan")
        private String tanggal_keberangkatan;
        @Nullable
        @SerializedName("hari_keberangkatan")
        private String hari_keberangkatan;
        @Nullable
        @SerializedName("jam_keberangkatan")
        private String jam_keberangkatan;
        @Nullable
        @SerializedName("tanggal_kedatangan")
        private String tanggal_kedatangan;
        @Nullable
        @SerializedName("hari_kedatangan")
        private String hari_kedatangan;
        @Nullable
        @SerializedName("jam_kedatangan")
        private String jam_kedatangan;
        @Nullable
        @SerializedName("kode_kereta")
        private String kode_kereta;
        @Nullable
        @SerializedName("nama_kereta")
        private String nama_kereta;
        @Nullable
        @SerializedName("url_etiket")
        private String url_etiket;
        @Nullable
        @SerializedName("url_struk")
        private String url_struk;
        @Nullable
        @SerializedName("classes")
        private String classes;
        @Nullable
        @SerializedName("komisi")
        private String komisi;
        @Nullable
        @SerializedName("expiredDate")
        private String expiredDate;


        protected Data(@NonNull Parcel in) {
             tanggal_keberangkatan= in.readString();
             tanggal_kedatangan= in.readString();
             url_struk= in.readString();
           //  url_struk=url_struk.replace("=>", ":");
             //airlineIcon= in.readString();
             classes= in.readString();
             origin= in.readString();
             destination= in.readString();
             id_transaksi= in.readInt();
            penumpang=in.createTypedArray(EtiketTrainModelDataPenumpang.CREATOR);
             jam_kedatangan= in.readString();
//             duration= in.readString();
//             nominal= in.readString();
         //   this.url_etiket = this.url_etiket.replace("=>", ":");
             url_etiket= in.readString();
          //  url_etiket=url_etiket.replace("=>", ":");
             kode_kereta= in.readString();
             hari_keberangkatan= in.readString();
             hari_kedatangan= in.readString();
             nama_kereta= in.readString();
          //  String str = getStringValue(jsonObject, "nama_maskapai");

             kode_booking= in.readString();
//             nominal_admin= in.readString();
             jam_keberangkatan= in.readString();
             expiredDate= in.readString();
        }


        public static final Creator<EtiketTrainModel.Data> CREATOR = new Creator<EtiketTrainModel.Data>() {
            @NonNull
            @Override
            public EtiketTrainModel.Data createFromParcel(@NonNull Parcel in) {
                return new EtiketTrainModel.Data(in);
            }

            @NonNull
            @Override
            public EtiketTrainModel.Data[] newArray(int size) {
                return new EtiketTrainModel.Data[size];
            }

        };
        public int getId_transaksi() {
            return id_transaksi;
        }

        public void setId_transaksi(int id_transaksi) {
            this.id_transaksi = id_transaksi;
        }

        public String getKode_booking() {
            return kode_booking;
        }

        public void setKode_booking(String kode_booking) {
            this.kode_booking = kode_booking;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public String getTanggal_keberangkatan() {
            return tanggal_keberangkatan;
        }

        public void setTanggal_keberangkatan(String tanggal_keberangkatan) {
            this.tanggal_keberangkatan = tanggal_keberangkatan;
        }

        public String getHari_keberangkatan() {
            return hari_keberangkatan;
        }

        public void setHari_keberangkatan(String hari_keberangkatan) {
            this.hari_keberangkatan = hari_keberangkatan;
        }

        public String getJam_keberangkatan() {
            return jam_keberangkatan;
        }

        public void setJam_keberangkatan(String jam_keberangkatan) {
            this.jam_keberangkatan = jam_keberangkatan;
        }

        public String getTanggal_kedatangan() {
            return tanggal_kedatangan;
        }

        public void setTanggal_kedatangan(String tanggal_kedatangan) {
            this.tanggal_kedatangan = tanggal_kedatangan;
        }

        public String getHari_kedatangan() {
            return hari_kedatangan;
        }

        public void setHari_kedatangan(String hari_kedatangan) {
            this.hari_kedatangan = hari_kedatangan;
        }

        public String getJam_kedatangan() {
            return jam_kedatangan;
        }

        public void setJam_kedatangan(String jam_kedatangan) {
            this.jam_kedatangan = jam_kedatangan;
        }

        public String getKode_kereta() {
            return kode_kereta;
        }

        public void setKode_kereta(String kode_kereta) {
            this.kode_kereta = kode_kereta;
        }

        public String getNama_kereta() {
            return nama_kereta;
        }

        public void setNama_kereta(String nama_kereta) {
            this.nama_kereta = nama_kereta;
        }

        public String getUrl_etiket() {
            return url_etiket;
        }

        public void setUrl_etiket(String url_etiket) {
            this.url_etiket = url_etiket;
        }

        public String getUrl_struk() {
            return url_struk;
        }

        public void setUrl_struk(String url_struk) {
            this.url_struk = url_struk;
        }

        public String getClasses() {
            return classes;
        }

        public void setClasses(String classes) {
            this.classes = classes;
        }

        public EtiketTrainModelDataPenumpang[] getPenumpang() {
            return this.penumpang;
        }

        public void setPenumpang(EtiketTrainModelDataPenumpang[] penumpang) {
            this.penumpang = penumpang;
        }

        public String getKomisi() {
            return komisi;
        }

        public void setKomisi(String komisi) {
            this.komisi = komisi;
        }
        public String getExpiredDate() {
            return expiredDate;
        }

        public void setExpiredDate(String expiredDate) {
            this.expiredDate = expiredDate;
        }
        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(this.tanggal_keberangkatan);

            dest.writeString(this.tanggal_kedatangan);
            dest.writeString(this.url_struk);
//            dest.writeString(this.airlineIcon);
            dest.writeString(this.classes);
            dest.writeString(this.origin);
            dest.writeString(this.destination);
            dest.writeInt(this.id_transaksi);
            dest.writeTypedArray(this.penumpang, flags);
            dest.writeString(this.jam_kedatangan);
//            dest.writeString(this.duration);
//            dest.writeString(this.nominal);
            dest.writeString(this.url_etiket);
            dest.writeString(this.kode_kereta);
            dest.writeString(this.hari_keberangkatan);
            dest.writeString(this.hari_kedatangan);
            dest.writeString(this.nama_kereta);
            dest.writeString(this.kode_booking);
//            dest.writeString(this.nominal_admin);
            dest.writeString(this.jam_keberangkatan);
            dest.writeString(this.expiredDate);

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

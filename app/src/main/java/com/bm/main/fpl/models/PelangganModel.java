package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by papahnakal on 13/11/17.
 */

public class PelangganModel extends BaseObject implements Parcelable{

    @SerializedName("response_value")
    private List<Response_value> response_value;


    protected PelangganModel(Parcel in) {
    }

    public static final Creator<PelangganModel> CREATOR = new Creator<PelangganModel>() {
        @NonNull
        @Override
        public PelangganModel createFromParcel(Parcel in) {
            return new PelangganModel(in);
        }

        @NonNull
        @Override
        public PelangganModel[] newArray(int size) {
            return new PelangganModel[size];
        }
    };

    public List<Response_value> getResponse_value() {
        return response_value;
    }

    public void setResponse_value(List<Response_value> response_value) {
        this.response_value = response_value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public static class Response_value implements Parcelable{
        @Nullable
        @SerializedName("id_pelanggan_1")
        private String id_pelanggan_1;
        @Nullable
        @SerializedName("id_pelanggan_2")
        private String id_pelanggan_2;
        @Nullable
        @SerializedName("nama_pelanggan")
        private String nama_pelanggan;
        @Nullable
        @SerializedName("additional_data")
        private String additional_data;
        @Nullable
        @SerializedName("id_history")
        private String id_history;



        protected Response_value(@NonNull Parcel in) {
            id_pelanggan_1 = in.readString();
            id_pelanggan_2 = in.readString();
            nama_pelanggan = in.readString();
            additional_data = in.readString();
            id_history = in.readString();


        }

        public static final Creator<Response_value> CREATOR = new Creator<Response_value>() {
            @NonNull
            @Override
            public Response_value createFromParcel(@NonNull Parcel in) {
                return new Response_value(in);
            }

            @NonNull
            @Override
            public Response_value[] newArray(int size) {
                return new Response_value[size];
            }
        };

        @Nullable
        public String getId_pelanggan_1() {
            return id_pelanggan_1;
        }

        public void setId_pelanggan_1(String id_pelanggan_1) {
            this.id_pelanggan_1 = id_pelanggan_1;
        }

        @Nullable
        public String getId_pelanggan_2() {
            return id_pelanggan_2;
        }

        public void setId_pelanggan_2(String id_pelanggan_2) {
            this.id_pelanggan_2 = id_pelanggan_2;
        }

        @Nullable
        public String getNama_pelanggan() {
            return nama_pelanggan;
        }

        public void setNama_pelanggan(String nama_pelanggan) {
            this.nama_pelanggan = nama_pelanggan;
        }

        @Nullable
        public String getAdditional_data() {
            return additional_data;
        }

        public void setAdditional_data(String additional_data) {
            this.additional_data = additional_data;
        }

        @Nullable
        public String getId_history() {
            return id_history;
        }

        public void setId_history(String id_history) {
            this.id_history = id_history;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(id_pelanggan_1);
            dest.writeString(id_pelanggan_2);
            dest.writeString(nama_pelanggan);
            dest.writeString(additional_data);
            dest.writeString(id_history);


        }
    }
}

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

public class SaranKategoriModel extends BaseObject implements Parcelable{

    @SerializedName("response_value")
    private List<Response_value> response_value;


    protected SaranKategoriModel(Parcel in) {
    }

    public static final Creator<SaranKategoriModel> CREATOR = new Creator<SaranKategoriModel>() {
        @NonNull
        @Override
        public SaranKategoriModel createFromParcel(Parcel in) {
            return new SaranKategoriModel(in);
        }

        @NonNull
        @Override
        public SaranKategoriModel[] newArray(int size) {
            return new SaranKategoriModel[size];
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
        @SerializedName("id_kategori")
        private String id_kategori;
        @Nullable
        @SerializedName("nama_kategori")
        private String nama_kategori;


        protected Response_value(@NonNull Parcel in) {
            id_kategori = in.readString();
            nama_kategori = in.readString();

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
        public String getId_kategori() {
            return id_kategori;
        }

        public void setId_kategori(String id_kategori) {
            this.id_kategori = id_kategori;
        }

        @Nullable
        public String getNama_kategori() {
            return nama_kategori;
        }

        public void setNama_kategori(String nama_kategori) {
            this.nama_kategori = nama_kategori;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(id_kategori);
            dest.writeString(nama_kategori);

        }
    }
}

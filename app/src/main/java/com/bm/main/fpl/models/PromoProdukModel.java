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

public class PromoProdukModel extends BaseObject implements Parcelable{

    @SerializedName("response_value")
    private List<Response_value> response_value;


    protected PromoProdukModel(Parcel in) {
    }

    public static final Creator<PromoProdukModel> CREATOR = new Creator<PromoProdukModel>() {
        @NonNull
        @Override
        public PromoProdukModel createFromParcel(Parcel in) {
            return new PromoProdukModel(in);
        }

        @NonNull
        @Override
        public PromoProdukModel[] newArray(int size) {
            return new PromoProdukModel[size];
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
        @SerializedName("id_promo")
        private String id_promo;
        @Nullable
        @SerializedName("judul")
        private String judul;
        @Nullable
        @SerializedName("produk")
        private String produk;
        @Nullable
        @SerializedName("content")
        private String content;
        @Nullable
        @SerializedName("url_gambar")
        private String url_gambar;
        @Nullable
        @SerializedName("url_gambar_landscape")
        private String url_gambar_landscape;
        @Nullable
        @SerializedName("target_link")
        private String target_link;


        protected Response_value(@NonNull Parcel in) {
            id_promo = in.readString();
            judul = in.readString();
            produk = in.readString();
            content = in.readString();
            url_gambar = in.readString();
            url_gambar_landscape = in.readString();
            target_link = in.readString();

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
        public String getId_promo() {
            return id_promo;
        }

        public void setId_promo(String id_promo) {
            this.id_promo = id_promo;
        }

        @Nullable
        public String getJudul() {
            return judul;
        }

        public void setJudul(String judul) {
            this.judul = judul;
        }

        @Nullable
        public String getProduk() {
            return produk;
        }

        public void setProduk(String produk) {
            this.produk = produk;
        }

        @Nullable
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Nullable
        public String getUrl_gambar() {
            return url_gambar;
        }

        public void setUrl_gambar(String url_gambar) {
            this.url_gambar = url_gambar;
        }

        @Nullable
        public String getTarget_link() {
            return target_link;
        }

        public void setTarget_link(String target_link) {
            this.target_link = target_link;
        }

        @Nullable
        public String getUrl_gambar_landscape() {
            return url_gambar_landscape;
        }

        public void setUrl_gambar_landscape(String url_gambar_landscape) {
            this.url_gambar_landscape = url_gambar_landscape;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(id_promo);
            dest.writeString(judul);
            dest.writeString(produk);
            dest.writeString(content);
            dest.writeString(url_gambar);
            dest.writeString(url_gambar_landscape);
            dest.writeString(target_link);

        }
    }
}

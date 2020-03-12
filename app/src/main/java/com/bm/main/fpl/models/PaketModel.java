package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PaketModel extends BaseObject implements Parcelable {

    @SerializedName("response_value")
    private ArrayList<Response_value> response_value;
    private PaketModel(Parcel in) {
    }

    public static final Creator<PaketModel> CREATOR = new Creator<PaketModel>() {
        @NonNull
        @Override
        public PaketModel createFromParcel(Parcel in) {
            return new PaketModel(in);
        }

        @NonNull
        @Override
        public PaketModel[] newArray(int size) {
            return new PaketModel[size];
        }
    };

    public ArrayList<PaketModel.Response_value> getResponse_value() {
        return response_value;
    }

    public void setResponse_value(ArrayList<PaketModel.Response_value> response_value) {
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
        @SerializedName("tipe_loket_code")
        private String tipe_loket_code;
        @Nullable
        @SerializedName("tipe_loket_alias")
        private String tipe_loket_alias;
        @Nullable
        @SerializedName("tipe_loket_harga")
        private String tipe_loket_harga;
        @Nullable
        @SerializedName("tipe_loket_url")
        private String tipe_loket_url;
        @Nullable
        @SerializedName("tipe_loket_name")
        private String tipe_loket_name;
        @Nullable
        @SerializedName("tipe_loket_description")
        private String tipe_loket_description;
        @Nullable
        @SerializedName("tipe_loket_detail")
        private PaketModelModelResponse_valueTipe_loket_detail[] tipe_loket_detail;
 @Nullable
 @SerializedName("tipe_loket_detail_footer")
        private PaketModelModelResponse_valueTipe_loket_footer[] tipe_loket_detail_footer;


        @Nullable
        public PaketModelModelResponse_valueTipe_loket_detail[] getTipe_loket_detail() {
            return tipe_loket_detail;
        }

        public void setTipe_loket_detail(PaketModelModelResponse_valueTipe_loket_detail[] tipe_loket_detail) {
            this.tipe_loket_detail = tipe_loket_detail;
        }


        @Nullable
        public PaketModelModelResponse_valueTipe_loket_footer[] getTipe_loket_detail_footer() {
            return tipe_loket_detail_footer;
        }

        public void setTipe_loket_detail_footer(PaketModelModelResponse_valueTipe_loket_footer[] tipe_loket_detail_footer) {
            this.tipe_loket_detail_footer = tipe_loket_detail_footer;
        }

        protected Response_value(@NonNull Parcel in) {
            tipe_loket_code = in.readString();

            tipe_loket_alias = in.readString();
            tipe_loket_harga = in.readString();
            tipe_loket_url = in.readString();
            tipe_loket_name = in.readString();
            tipe_loket_description = in.readString();
            tipe_loket_detail = in.createTypedArray(PaketModelModelResponse_valueTipe_loket_detail.CREATOR);
            tipe_loket_detail_footer = in.createTypedArray(PaketModelModelResponse_valueTipe_loket_footer.CREATOR);

        }

        public static final Creator<PaketModel.Response_value> CREATOR = new Creator<PaketModel.Response_value>() {
            @NonNull
            @Override
            public PaketModel.Response_value createFromParcel(@NonNull Parcel in) {
                return new PaketModel.Response_value(in);
            }

            @NonNull
            @Override
            public PaketModel.Response_value[] newArray(int size) {
                return new PaketModel.Response_value[size];
            }
        };


        @Nullable
        public String getTipe_loket_code() {
            return tipe_loket_code;
        }

        public void setTipe_loket_code(String tipe_loket_code) {
            this.tipe_loket_code = tipe_loket_code;
        }

        @Nullable
        public String getTipe_loket_alias() {
            return tipe_loket_alias;
        }

        public void setTipe_loket_alias(String tipe_loket_alias) {
            this.tipe_loket_alias = tipe_loket_alias;
        }

        @Nullable
        public String getTipe_loket_harga() {
            return tipe_loket_harga;
        }

        public void setTipe_loket_harga(String tipe_loket_harga) {
            this.tipe_loket_harga = tipe_loket_harga;
        }

        @Nullable
        public String getTipe_loket_url() {
            return tipe_loket_url;
        }

        public void setTipe_loket_url(String tipe_loket_url) {
            this.tipe_loket_url = tipe_loket_url;
        }

        @Nullable
        public String getTipe_loket_name() {
            return tipe_loket_name;
        }

        public void setTipe_loket_name(String tipe_loket_name) {
            this.tipe_loket_name = tipe_loket_name;
        }

        @Nullable
        public String getTipe_loket_description() {
            return tipe_loket_description;
        }

        public void setTipe_loket_description(String tipe_loket_description) {
            this.tipe_loket_description = tipe_loket_description;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(this.tipe_loket_code);

            dest.writeString(this.tipe_loket_alias);
            dest.writeString(this.tipe_loket_harga);
            dest.writeString(this.tipe_loket_url);
            dest.writeString(this.tipe_loket_name);
            dest.writeString(this.tipe_loket_description);
            dest.writeTypedArray(this.tipe_loket_detail, flags);
            dest.writeTypedArray(this.tipe_loket_detail_footer, flags);

        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}

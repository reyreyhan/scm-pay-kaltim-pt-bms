package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProviderModel extends BaseObject implements Parcelable {

    @SerializedName("response_value")
    private ArrayList<Response_value> response_value;
    private ProviderModel(Parcel in) {
    }

    public static final Creator<ProviderModel> CREATOR = new Creator<ProviderModel>() {
        @NonNull
        @Override
        public ProviderModel createFromParcel(Parcel in) {
            return new ProviderModel(in);
        }

        @NonNull
        @Override
        public ProviderModel[] newArray(int size) {
            return new ProviderModel[size];
        }
    };

    public ArrayList<ProviderModel.Response_value> getResponse_value() {
        return response_value;
    }

    public void setResponse_value(ArrayList<ProviderModel.Response_value> response_value) {
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
        @SerializedName("provider_url")
        private String provider_url;
        @Nullable
        @SerializedName("is_gangguan")
        private String is_gangguan;
        @Nullable
        @SerializedName("is_promo")
        private String is_promo;
        @Nullable
        @SerializedName("provider_code")
        private String provider_code;
        @Nullable
        @SerializedName("provider_name")
        private String provider_name;



        protected Response_value(@NonNull Parcel in) {
            provider_url = in.readString();
            is_gangguan = in.readString();
            is_promo = in.readString();
            provider_code = in.readString();
            provider_name = in.readString();

        }

        public static final Creator<ProviderModel.Response_value> CREATOR = new Creator<ProviderModel.Response_value>() {
            @NonNull
            @Override
            public ProviderModel.Response_value createFromParcel(@NonNull Parcel in) {
                return new ProviderModel.Response_value(in);
            }

            @NonNull
            @Override
            public ProviderModel.Response_value[] newArray(int size) {
                return new ProviderModel.Response_value[size];
            }
        };

        @Nullable
        public String getProvider_url() {
            return provider_url;
        }

        public void setProvider_url(String provider_url) {
            this.provider_url = provider_url;
        }

        @Nullable
        public String getIs_gangguan() {
            return is_gangguan;
        }

        public void setIs_gangguan(String is_gangguan) {
            this.is_gangguan = is_gangguan;
        }

        @Nullable
        public String getIs_promo() {
            return is_promo;
        }

        public void setIs_promo(String is_promo) {
            this.is_promo = is_promo;
        }

        @Nullable
        public String getProvider_code() {
            return provider_code;
        }

        public void setProvider_code(String provider_code) {
            this.provider_code = provider_code;
        }

        @Nullable
        public String getProvider_name() {
            return provider_name;
        }

        public void setProvider_name(String provider_name) {
            this.provider_name = provider_name;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(this.provider_url);
            dest.writeString(this.is_gangguan);
            dest.writeString(this.is_promo);
            dest.writeString(this.provider_code);
            dest.writeString(this.provider_name);
        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}

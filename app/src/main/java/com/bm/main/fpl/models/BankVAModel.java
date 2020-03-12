package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BankVAModel extends BaseObject implements Parcelable {

    @SerializedName("response_value")
    private ArrayList<Response_value> response_value;


    private BankVAModel(Parcel in) {
    }

    public static final Creator<BankVAModel> CREATOR = new Creator<BankVAModel>() {
        @NonNull
        @Override
        public BankVAModel createFromParcel(Parcel in) {
            return new BankVAModel(in);
        }

        @NonNull
        @Override
        public BankVAModel[] newArray(int size) {
            return new BankVAModel[size];
        }
    };

    public ArrayList<BankVAModel.Response_value> getResponse_value() {
        return response_value;
    }

    public void setResponse_value(ArrayList<BankVAModel.Response_value> response_value) {
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
        @SerializedName("product_url")
        private String product_url;
        @Nullable
        @SerializedName("va_bank_code")
        private String va_bank_code;
        @Nullable
        @SerializedName("va_bank_name")
        private String va_bank_name;
        @Nullable
        @SerializedName("va_bank_type")
        private String va_bank_type;
        @Nullable
        @SerializedName("va_header")
        private String va_header;
        @Nullable
        @SerializedName("va_no")
        private String va_no;
        @Nullable
        @SerializedName("va_desc")
        private BankVaNewModelResponse_valueVa_desc[] va_desc;



        protected Response_value(@NonNull Parcel in) {
            product_url = in.readString();
            va_desc = in.createTypedArray(BankVaNewModelResponse_valueVa_desc.CREATOR);
            va_bank_code = in.readString();
            va_bank_name = in.readString();
            va_bank_type = in.readString();
            va_header = in.readString();
            va_no = in.readString();



        }

        public static final Creator<BankVAModel.Response_value> CREATOR = new Creator<BankVAModel.Response_value>() {
            @NonNull
            @Override
            public BankVAModel.Response_value createFromParcel(@NonNull Parcel in) {
                return new BankVAModel.Response_value(in);
            }

            @NonNull
            @Override
            public BankVAModel.Response_value[] newArray(int size) {
                return new BankVAModel.Response_value[size];
            }
        };



        @Nullable
        public String getProduct_url() {
            return this.product_url;
        }

        public void setProduct_url(String product_url) {
            this.product_url = product_url;
        }

        @Nullable
        public String getVa_bank_code() {
            return va_bank_code;
        }

        public void setVa_bank_code(String va_bank_code) {
            this.va_bank_code = va_bank_code;
        }

        @Nullable
        public String getVa_bank_name() {
            return va_bank_name;
        }

        public void setVa_bank_name(String va_bank_name) {
            this.va_bank_name = va_bank_name;
        }

        @Nullable
        public String getVa_bank_type() {
            return va_bank_type;
        }

        public void setVa_bank_type(String va_bank_type) {
            this.va_bank_type = va_bank_type;
        }

        @Nullable
        public String getVa_header() {
            return va_header;
        }

        public void setVa_header(String va_header) {
            this.va_header = va_header;
        }

        @Nullable
        public String getVa_no() {
            return va_no;
        }

        public void setVa_no(String va_no) {
            this.va_no = va_no;
        }

        @Nullable
        public BankVaNewModelResponse_valueVa_desc[] getVa_desc() {
            return this.va_desc;
        }

        public void setVa_desc(BankVaNewModelResponse_valueVa_desc[] va_desc) {
            this.va_desc = va_desc;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(this.product_url);

            dest.writeString(this.va_bank_code);
            dest.writeString(this.va_bank_name);
            dest.writeString(this.va_bank_type);
            dest.writeString(this.va_header);
            dest.writeString(this.va_no);
            dest.writeTypedArray(this.va_desc, flags);

        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}

package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BankVaNewModelResponse_value implements Parcelable {

    public static final Creator<BankVaNewModelResponse_value> CREATOR = new Creator<BankVaNewModelResponse_value>() {
        @NonNull
        @Override
        public BankVaNewModelResponse_value createFromParcel(@NonNull Parcel source) {
            BankVaNewModelResponse_value var = new BankVaNewModelResponse_value();
            var.va_bank_code = source.readString();
            var.product_url = source.readString();
            var.va_header = source.readString();
            var.va_no = source.readString();
            var.va_desc = source.createTypedArray(BankVaNewModelResponse_valueVa_desc.CREATOR);
            var.va_bank_name = source.readString();
            var.va_bank_type = source.readString();
            return var;
        }

        @NonNull
        @Override
        public BankVaNewModelResponse_value[] newArray(int size) {
            return new BankVaNewModelResponse_value[size];
        }
    };
    @Nullable
    private String va_bank_code;
    @Nullable
    private String product_url;
    @Nullable
    private String va_header;
    @Nullable
    private String va_no;
    @Nullable
    private BankVaNewModelResponse_valueVa_desc[] va_desc;
    @Nullable
    private String va_bank_name;
    @Nullable
    private String va_bank_type;

    @Nullable
    public String getVa_bank_code() {
        return this.va_bank_code;
    }

    public void setVa_bank_code(String va_bank_code) {
        this.va_bank_code = va_bank_code;
    }

    @Nullable
    public String getProduct_url() {
        return this.product_url;
    }

    public void setProduct_url(String product_url) {
        this.product_url = product_url;
    }

    @Nullable
    public String getVa_header() {
        return this.va_header;
    }

    public void setVa_header(String va_header) {
        this.va_header = va_header;
    }

    @Nullable
    public String getVa_no() {
        return this.va_no;
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

    @Nullable
    public String getVa_bank_name() {
        return this.va_bank_name;
    }

    public void setVa_bank_name(String va_bank_name) {
        this.va_bank_name = va_bank_name;
    }

    @Nullable
    public String getVa_bank_type() {
        return this.va_bank_type;
    }

    public void setVa_bank_type(String va_bank_type) {
        this.va_bank_type = va_bank_type;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.va_bank_code);
        dest.writeString(this.product_url);
        dest.writeString(this.va_header);
        dest.writeString(this.va_no);
        dest.writeTypedArray(this.va_desc, flags);
        dest.writeString(this.va_bank_name);
        dest.writeString(this.va_bank_type);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}

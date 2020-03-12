package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PaketModelModelResponse_valueTipe_loket_detail implements Parcelable {

    public static final Creator<PaketModelModelResponse_valueTipe_loket_detail> CREATOR = new Creator<PaketModelModelResponse_valueTipe_loket_detail>() {
        @NonNull
        @Override
        public PaketModelModelResponse_valueTipe_loket_detail createFromParcel(@NonNull Parcel source) {
            PaketModelModelResponse_valueTipe_loket_detail var = new PaketModelModelResponse_valueTipe_loket_detail();
            var.avail = source.readString();

            var.desc_name = source.readString();

            return var;
        }

        @NonNull
        @Override
        public PaketModelModelResponse_valueTipe_loket_detail[] newArray(int size) {
            return new PaketModelModelResponse_valueTipe_loket_detail[size];
        }
    };
    @Nullable
    private String avail;

    @Nullable
    private String desc_name;


    @Nullable
    public String getAvail() {
        return this.avail;
    }

    public void setAvail(String avail) {
        this.avail = avail;
    }



    @Nullable
    public String getDesc_name() {
        return this.desc_name;
    }

    public void setDesc_name(String desc_name) {
        this.desc_name = desc_name;
    }


    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.avail);

        dest.writeString(this.desc_name);

    }

    @Override
    public int describeContents() {
        return 0;
    }
}

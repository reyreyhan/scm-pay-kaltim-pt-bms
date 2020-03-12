package com.bm.main.fpl.models;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PaketModelModelResponse_valueTipe_loket_footer implements Parcelable {

    public static final Creator<PaketModelModelResponse_valueTipe_loket_footer> CREATOR = new Creator<PaketModelModelResponse_valueTipe_loket_footer>() {
        @NonNull
        @Override
        public PaketModelModelResponse_valueTipe_loket_footer createFromParcel(@NonNull Parcel source) {
            PaketModelModelResponse_valueTipe_loket_footer var = new PaketModelModelResponse_valueTipe_loket_footer();
            var.avail = source.readString();
            var.type_footer = source.readString();

            var.desc_name_footer = source.readString();

            return var;
        }

        @NonNull
        @Override
        public PaketModelModelResponse_valueTipe_loket_footer[] newArray(int size) {
            return new PaketModelModelResponse_valueTipe_loket_footer[size];
        }
    };
    @Nullable
    private String avail;

    @Nullable
    private String desc_name_footer;
    @Nullable
    private String type_footer;


    @Nullable
    public String getAvail() {
        return this.avail;
    }

    public void setAvail(String avail) {
        this.avail = avail;
    }


    @Nullable
    public String getDesc_name_footer() {
        return desc_name_footer;
    }

    public void setDesc_name_footer(String desc_name_footer) {
        this.desc_name_footer = desc_name_footer;
    }

    @Nullable
    public String getType_footer() {
        return type_footer;
    }

    public void setType_footer(String type_footer) {
        this.type_footer = type_footer;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.avail);
        dest.writeString(this.type_footer);

        dest.writeString(this.desc_name_footer);

    }

    @Override
    public int describeContents() {
        return 0;
    }
}

package com.cardnfc.lib.bninfc.tapcashgo.transit;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class Trip implements Parcelable {
    public static final Creator<Trip> CREATOR = new Trip_();

    static class Trip_ implements Creator<Trip> {
        Trip_() {
        }

        @Nullable
        public  Trip createFromParcel(Parcel parcel) {
            return trip(parcel);
        }

        @NonNull
        public  Trip[] newArray(int i) {
            return trips(i);
        }

        @Nullable
        public Trip trip(Parcel parcel) {
            return null;
        }

        @NonNull
        public Trip[] trips(int i) {
            return new Trip[i];
        }
    }

    public enum trip_enum {
        DEDUCT,
        FEE,
        REFUND,
        CARDACTIVATION,
        TOPUPUPDATE,
        OTHER,
        BANNED
    }

    public abstract long Trip_a();

    public abstract String Trip_b();

    public abstract String Trip_c();

    public abstract String Trip_d();

    public abstract trip_enum Trip_e();

    public abstract boolean Trip_f();
}

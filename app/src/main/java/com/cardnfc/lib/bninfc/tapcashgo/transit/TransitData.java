package com.cardnfc.lib.bninfc.tapcashgo.transit;

import android.os.Parcelable;

public abstract class TransitData implements Parcelable {
    public abstract String TransitDatastring_b();

    public abstract byte TransitDatabyte_c();

    public abstract byte TransitDatabyte_d();

    public abstract String TransitDatastring_e();

    public abstract String TransitDatastring_f();

    public abstract Trip[] trips();

    public final int describeContents() {
        return 0;
    }
}

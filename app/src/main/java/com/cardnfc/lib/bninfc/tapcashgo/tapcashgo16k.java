package com.cardnfc.lib.bninfc.tapcashgo;

import androidx.annotation.NonNull;

public class tapcashgo16k extends tapcashgo7a {
    public static final Object tapcashgo16k_objecte = new Object();
    public static final Object tapcashgo16k_objectf = new Object();
    private String f47b;
    private String f48c;
    private Object f49d;

    public tapcashgo16k(String str, String str2, Object obj) {
        this.f47b = str;
        this.f48c = str2;
        this.f49d = obj;
    }


    public boolean equals(Object r5) {

        throw new UnsupportedOperationException("Method not decompiled: a.c.a.k.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        return (this.f47b == null ? 0 : this.f47b.hashCode()) ^ this.f48c.hashCode();
    }

    @NonNull
    public String toString() {
        return this.f49d != null ? this.f49d.toString() : null;
    }

    public String tapcashgo16k_stringb() {
        return this.f47b;
    }

    public String tapcashgo16k_stringc() {
        return this.f48c;
    }
}

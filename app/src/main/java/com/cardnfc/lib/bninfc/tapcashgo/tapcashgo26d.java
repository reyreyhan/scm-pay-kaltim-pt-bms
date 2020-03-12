package com.cardnfc.lib.bninfc.tapcashgo;

import java.io.IOException;

public class tapcashgo26d extends tapcashgo23b {
    protected final String f85a;
    protected final int f86b;
    protected final String f87c;
    private tapcashgo25c f88j;

    public tapcashgo26d(String str, int i, String str2, int i2) {
        super("https://" + str + ":" + i + str2, i2);
        this.f85a = str;
        this.f86b = i;
        this.f87c = str2;
    }

    public tapcashgointerface24e mo27a() throws IOException {
        if (this.f88j != null) {
            return this.f88j;
        }
        this.f88j = new tapcashgo25c(this.f78d, this.f85a, this.f86b, this.f87c, this.f80f);
        return this.f88j;
    }
}

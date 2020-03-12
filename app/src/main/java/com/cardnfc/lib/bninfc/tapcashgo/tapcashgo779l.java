package com.cardnfc.lib.bninfc.tapcashgo;

import androidx.annotation.NonNull;

public class tapcashgo779l extends Exception {
    private String[] a;
    private String b;

    @NonNull
    public String getMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : this.a) {
            stringBuilder.append("\n  ");
            stringBuilder.append(str.replace("android.nfc.tech.", ""));
        }
        return String.format("Identifier: %s\n\nTechnologies:%s", new Object[]{this.b, stringBuilder.toString()});
    }
}

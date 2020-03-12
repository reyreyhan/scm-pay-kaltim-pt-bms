package com.cardnfc.lib.bninfc.tapcashgo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.Vector;
//import p000a.p008d.p009a.tapcashgointerface30a;
//import p000a.p008d.p009a.tapcashgointerface32c;

public class tapcashgo35a extends tapcashgo34b {
    @Nullable
    String a;
    String b;
    private Vector vector;
    private tapcashgo34b tapcashgo34b;
    private Vector vector1;

    public void m267a() {
    }

    public tapcashgo35a tapcashgo35a(String str, String str2) {
        return this.tapcashgo34b == null ? super.tapcashgo35a(str, str2) : this.tapcashgo34b.tapcashgo35a(str, str2);
    }

    public int tapcashgo35a_voidb() {
        return this.vector == null ? 0 : this.vector.size();
    }

    public String tapcashgo35a_stringa(int i) {
        return ((String[]) this.vector.elementAt(i))[0];
    }

    public String tapcashgo35a_stringb(int i) {
        return ((String[]) this.vector.elementAt(i))[1];
    }

    public String tapcashgo35a_stringc(int i) {
        return ((String[]) this.vector.elementAt(i))[2];
    }

    public String tapcashgo35a_stringc() {
        return this.b;
    }

    @Nullable
    public String tapcashgo35a_stringd() {
        return this.a;
    }

    public String tapcashgo35a_stringd(int i) {
        return ((String[]) this.vector1.elementAt(i))[0];
    }

    public String tapcashgo35a_stringe(int i) {
        return ((String[]) this.vector1.elementAt(i))[1];
    }

    public void tapcashgo34b_voida(@NonNull tapcashgointerface30a tapcashgointerface30A) {
        int a;
        for (a = tapcashgointerface30A.tapcashgointerface30a_inta(tapcashgointerface30A.tapcashgointerface30a_inta() - 1); a < tapcashgointerface30A.tapcashgointerface30a_inta(tapcashgointerface30A.tapcashgointerface30a_inta()); a++) {
            tapcashgo35a_voidb(tapcashgointerface30A.tapcashgointerface30a_stringb(a), tapcashgointerface30A.tapcashgointerface30a_stringc(a));
        }
        for (a = 0; a < tapcashgointerface30A.getAttributeCount(); a++) {
            tapcashgo35a_voida(tapcashgointerface30A.tapcashgointerface30a_stringe(a), tapcashgointerface30A.getAttributeName(a), tapcashgointerface30A.getAttributeValue(a));
        }
        m267a();
        if (tapcashgointerface30A.tapcashgointerface30a_booleang()) {
            tapcashgointerface30A.tapcashgointerface30a_intj();
        } else {
            tapcashgointerface30A.tapcashgointerface30a_intj();
            super.tapcashgo34b_voida(tapcashgointerface30A);
            if (tapcashgo34b_inte() == 0) {
                tapcashgo34b_voida(7, (Object) "");
            }
        }
        tapcashgointerface30A.tapcashgointerface30a_voida(3, tapcashgo35a_stringd(), tapcashgo35a_stringc());
        tapcashgointerface30A.tapcashgointerface30a_intj();
    }

    public void tapcashgo35a_voida(@Nullable String str, String str2, @Nullable String str3) {
        if (this.vector == null) {
            this.vector = new Vector();
        }
        if (str == null) {
            str = "";
        }
        int size = this.vector.size() - 1;
        while (size >= 0) {
            String[] strArr = (String[]) this.vector.elementAt(size);
            if (!strArr[0].equals(str) || !strArr[1].equals(str2)) {
                size--;
            } else if (str3 == null) {
                this.vector.removeElementAt(size);
                return;
            } else {
                strArr[2] = str3;
                return;
            }
        }
        this.vector.addElement(new String[]{str, str2, str3});
    }

    public void tapcashgo35a_voidb(String str, String str2) {
        if (this.vector1 == null) {
            this.vector1 = new Vector();
        }
        this.vector1.addElement(new String[]{str, str2});
    }

    void tapcashgo35a_voida(tapcashgo34b tapcashgo34B) {
        this.tapcashgo34b = tapcashgo34B;
    }

    public void tapcashgo34b_voida(@NonNull tapcashgointerface32c tapcashgointerface32C) throws IOException {
        int i;
        int i2 = 0;
        if (this.vector1 != null) {
            for (i = 0; i < this.vector1.size(); i++) {
                tapcashgointerface32C.tapcashgointerface32c_voida(tapcashgo35a_stringd(i), tapcashgo35a_stringe(i));
            }
        }
        tapcashgointerface32C.tapcashgointerface32c_b(tapcashgo35a_stringd(), tapcashgo35a_stringc());
        i = tapcashgo35a_voidb();
        while (i2 < i) {
            tapcashgointerface32C.tapcashgointerface32c_a(tapcashgo35a_stringa(i2), tapcashgo35a_stringb(i2), tapcashgo35a_stringc(i2));
            i2++;
        }
        tapcashgo34b_voidb(tapcashgointerface32C);
        tapcashgointerface32C.tapcashgointerface32c_c(tapcashgo35a_stringd(), tapcashgo35a_stringc());
    }
}

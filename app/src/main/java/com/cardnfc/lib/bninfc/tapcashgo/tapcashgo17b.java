package com.cardnfc.lib.bninfc.tapcashgo;

//import p000a.p008d.p009a.tapcashgointerface30a;
//import p000a.p008d.p009a.tapcashgointerface32c;
//import p000a.p010c.p012b.tapcashgo34b;
//import p000a.p010c.p012b.tapcashgo35a;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;

public class tapcashgo17b {
    @Nullable
    public Object tapcashgo17b_objecta;
    public Object tapcashgo17b_objectb;
    public tapcashgo35a[] tapcashgo35ac;
    public tapcashgo35a[] tapcashgo35ad;
    public String tapcashgo17b_stringe;
    public int tapcashgo17b_intf;
    public String tapcashgo17b_stringg;
    public String tapcashgo17b_stringh;
    public String tapcashgo17b_stringi;
    public String tapcashgo17b_stringj;

    public static boolean tapcashgo17b_booleana(@Nullable String str) {
        if (str == null) {
            return false;
        }
        String toLowerCase = str.trim().toLowerCase();
        if (toLowerCase.equals("1") || toLowerCase.equals("true")) {
            return true;
        }
        return false;
    }

    public tapcashgo17b(int i) {
        this.tapcashgo17b_intf = i;
        if (i == 100) {
            this.tapcashgo17b_stringi = "http://www.w3.org/1999/XMLSchema-mInstance";
            this.tapcashgo17b_stringj = "http://www.w3.org/1999/XMLSchema";
        } else {
            this.tapcashgo17b_stringi = "http://www.w3.org/2001/XMLSchema-mInstance";
            this.tapcashgo17b_stringj = "http://www.w3.org/2001/XMLSchema";
        }
        if (i < 120) {
            this.tapcashgo17b_stringh = "http://schemas.xmlsoap.org/soap/encoding/";
            this.tapcashgo17b_stringg = "http://schemas.xmlsoap.org/soap/envelope/";
            return;
        }
        this.tapcashgo17b_stringh = "http://www.w3.org/2003/05/soap-encoding";
        this.tapcashgo17b_stringg = "http://www.w3.org/2003/05/soap-envelope";
    }

    public void tapcashgo17b_voida(@NonNull tapcashgointerface30a tapcashgointerface30A) {
        tapcashgointerface30A.tapcashgointerface30a_intk();
        tapcashgointerface30A.tapcashgointerface30a_voida(2, this.tapcashgo17b_stringg, "Envelope");
        this.tapcashgo17b_stringe = tapcashgointerface30A.getAttributeValue(this.tapcashgo17b_stringg, "encodingStyle");
        tapcashgointerface30A.tapcashgointerface30a_intk();
        if (tapcashgointerface30A.tapcashgointerface30a_inth() == 2 && tapcashgointerface30A.tapcashgointerface30a_stringe().equals(this.tapcashgo17b_stringg) && tapcashgointerface30A.tapcashgointerface30a_strngf().equals("Header")) {
            tapcashgo17b_voidb(tapcashgointerface30A);
            tapcashgointerface30A.tapcashgointerface30a_voida(3, this.tapcashgo17b_stringg, "Header");
            tapcashgointerface30A.tapcashgointerface30a_intk();
        }
        tapcashgointerface30A.tapcashgointerface30a_voida(2, this.tapcashgo17b_stringg, "Body");
        this.tapcashgo17b_stringe = tapcashgointerface30A.getAttributeValue(this.tapcashgo17b_stringg, "encodingStyle");
        tapcashgo17b_voidc(tapcashgointerface30A);
        tapcashgointerface30A.tapcashgointerface30a_voida(3, this.tapcashgo17b_stringg, "Body");
        tapcashgointerface30A.tapcashgointerface30a_intk();
        tapcashgointerface30A.tapcashgointerface30a_voida(3, this.tapcashgo17b_stringg, "Envelope");
    }

    public void tapcashgo17b_voidb(@NonNull tapcashgointerface30a tapcashgointerface30A) {
        int i;
        int i2 = 0;
        tapcashgointerface30A.tapcashgointerface30a_intk();
        tapcashgo34b tapcashgo34B = new tapcashgo34b();
        tapcashgo34B.tapcashgo34b_voida(tapcashgointerface30A);
        int i3 = 0;
        for (i = 0; i < tapcashgo34B.tapcashgo34b_inte(); i++) {
            if (tapcashgo34B.tapcashgo35a(i) != null) {
                i3++;
            }
        }
        this.tapcashgo35ac = new tapcashgo35a[i3];
        i = 0;
        while (i2 < tapcashgo34B.tapcashgo34b_inte()) {
            tapcashgo35a g = tapcashgo34B.tapcashgo35a(i2);
            if (g != null) {
                i3 = i + 1;
                this.tapcashgo35ac[i] = g;
                i = i3;
            }
            i2++;
        }
    }

    public void tapcashgo17b_voidc(@NonNull tapcashgointerface30a tapcashgointerface30A) {
        tapcashgointerface30A.tapcashgointerface30a_intk();
        if (tapcashgointerface30A.tapcashgointerface30a_inth() == 2 && tapcashgointerface30A.tapcashgointerface30a_stringe().equals(this.tapcashgo17b_stringg) && tapcashgointerface30A.tapcashgointerface30a_strngf().equals("Fault")) {
            tapcashgo28c tapcashgo28C;
            if (this.tapcashgo17b_intf < 120) {
                tapcashgo28C = new tapcashgo28c(this.tapcashgo17b_intf);
            } else {
                tapcashgo28C = new tapcashgo29d(this.tapcashgo17b_intf);
            }
            tapcashgo28C.tapcashgo28c_voida(tapcashgointerface30A);
            this.tapcashgo17b_objecta = tapcashgo28C;
            return;
        }
        tapcashgo34b tapcashgo34B = this.tapcashgo17b_objecta instanceof tapcashgo34b ? (tapcashgo34b) this.tapcashgo17b_objecta : new tapcashgo34b();
        tapcashgo34B.tapcashgo34b_voida(tapcashgointerface30A);
        this.tapcashgo17b_objecta = tapcashgo34B;
    }

    public void tapcashgo17b_voida(@NonNull tapcashgointerface32c tapcashgointerface32C) throws IOException {
        tapcashgointerface32C.tapcashgointerface32c_voida("i", this.tapcashgo17b_stringi);
        tapcashgointerface32C.tapcashgointerface32c_voida("d", this.tapcashgo17b_stringj);
        tapcashgointerface32C.tapcashgointerface32c_voida("c", this.tapcashgo17b_stringh);
        tapcashgointerface32C.tapcashgointerface32c_voida("v", this.tapcashgo17b_stringg);
        tapcashgointerface32C.tapcashgointerface32c_b(this.tapcashgo17b_stringg, "Envelope");
        tapcashgointerface32C.tapcashgointerface32c_b(this.tapcashgo17b_stringg, "Header");
        tapcashgo17b_voidb(tapcashgointerface32C);
        tapcashgointerface32C.tapcashgointerface32c_c(this.tapcashgo17b_stringg, "Header");
        tapcashgointerface32C.tapcashgointerface32c_b(this.tapcashgo17b_stringg, "Body");
        tapcashgo17b_voidc(tapcashgointerface32C);
        tapcashgointerface32C.tapcashgointerface32c_c(this.tapcashgo17b_stringg, "Body");
        tapcashgointerface32C.tapcashgointerface32c_c(this.tapcashgo17b_stringg, "Envelope");
    }

    public void tapcashgo17b_voidb(tapcashgointerface32c tapcashgointerface32C) throws IOException {
        if (this.tapcashgo35ad != null) {
            for (tapcashgo35a a : this.tapcashgo35ad) {
                a.tapcashgo34b_voida(tapcashgointerface32C);
            }
        }
    }

    public void tapcashgo17b_voidc(@NonNull tapcashgointerface32c tapcashgointerface32C) throws IOException {
        if (this.tapcashgo17b_stringe != null) {
            tapcashgointerface32C.tapcashgointerface32c_a(this.tapcashgo17b_stringg, "encodingStyle", this.tapcashgo17b_stringe);
        }
        ((tapcashgo34b) this.tapcashgo17b_objectb).tapcashgo34b_voida(tapcashgointerface32C);
    }

    public void tapcashgo17b_voida(Object obj) {
        this.tapcashgo17b_objectb = obj;
    }
}

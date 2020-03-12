package com.cardnfc.lib.bninfc.tapcashgo;

//import android.support.v7.p022a.C0503a.C0502j;
//import p000a.p005b.tapcashgo17b;
//import p000a.p008d.p009a.tapcashgointerface30a;
//import p000a.p008d.p009a.tapcashgointerface32c;

import androidx.annotation.NonNull;

import java.io.IOException;

class tapcashgo11c implements tapcashgointerface10h {
    tapcashgo11c() {
    }

    public Object tapcashgointerface10h_objecta(@NonNull tapcashgointerface30a tapcashgointerface30A, String str, @NonNull String str2, tapcashgo8i tapcashgo8I) {
        String l = tapcashgointerface30A.tapcashgointerface30a_stringl();
        switch (str2.charAt(0)) {
            case 98:
                return new Boolean(tapcashgo17b.tapcashgo17b_booleana(l));
            case 105:
                return new Integer(Integer.parseInt(l));
            case 108:
                return new Long(Long.parseLong(l));
            case 's':
                return l;
            default:
                throw new RuntimeException();
        }
    }

    public void tapcashgointerface10h_7a(@NonNull tapcashgointerface32c tapcashgointerface32C, Object obj) throws IOException {
        int a;
        int i;
        tapcashgo9b tapcashgo9B;
        String c;
        String b;
        String obj2;
        if (obj instanceof tapcashgo7a) {
            tapcashgo7a tapcashgo7A = (tapcashgo7a) obj;
            a = tapcashgo7A.tapcashgo6e_1a();
            for (i = 0; i < a; i++) {
                tapcashgo9B = new tapcashgo9b();
                tapcashgo7A.tapcashgo6e_2a(i, tapcashgo9B);
                try {
                    tapcashgo7A.tapcashgo6e_3b(i, tapcashgo9B);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (tapcashgo9B.tapcashgo8i_objectd() != null) {
                    c = tapcashgo9B.tapcashgo8i_stringc();
                    b = tapcashgo9B.tapcashgo8i_stringb();
                    if (tapcashgo9B.tapcashgo8i_objectd() != null) {
                        obj2 = tapcashgo9B.tapcashgo8i_objectd().toString();
                    } else {
                        obj2 = "";
                    }
                    tapcashgointerface32C.tapcashgointerface32c_a(c, b, obj2);
                }
            }
        } else if (obj instanceof tapcashgo6e) {
            tapcashgo6e tapcashgo6E = (tapcashgo6e) obj;
            a = tapcashgo6E.tapcashgo6e_1a();
            for (i = 0; i < a; i++) {
                tapcashgo9B = new tapcashgo9b();
                tapcashgo6E.tapcashgo6e_2a(i, tapcashgo9B);
                try {
                    tapcashgo6E.tapcashgo6e_3b(i, tapcashgo9B);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                if (tapcashgo9B.tapcashgo8i_objectd() != null) {
                    c = tapcashgo9B.tapcashgo8i_stringc();
                    b = tapcashgo9B.tapcashgo8i_stringb();
                    if (tapcashgo9B.tapcashgo8i_objectd() != null) {
                        obj2 = tapcashgo9B.tapcashgo8i_objectd().toString();
                    } else {
                        obj2 = "";
                    }
                    tapcashgointerface32C.tapcashgointerface32c_a(c, b, obj2);
                }
            }
        }
        if (obj instanceof tapcashgo19m) {
            ((tapcashgo19m) obj).tapcashgo19m_voida(tapcashgointerface32C);
        } else {
            tapcashgointerface32C.tapcashgointerface32c_d(obj.toString());
        }
    }

    public void tapcashgointerface10h_6a(@NonNull tapcashgo18l tapcashgo18L) {
        tapcashgo18L.tapcashgo18l_voida(tapcashgo18L.tapcashgo17b_stringj, "int", tapcashgo8i.tapcashgo8i_c, (tapcashgointerface10h) this);
        tapcashgo18L.tapcashgo18l_voida(tapcashgo18L.tapcashgo17b_stringj, "long", tapcashgo8i.tapcashgo8i_d, (tapcashgointerface10h) this);
        tapcashgo18L.tapcashgo18l_voida(tapcashgo18L.tapcashgo17b_stringj, "string", tapcashgo8i.tapcashgo8i_b, (tapcashgointerface10h) this);
        tapcashgo18L.tapcashgo18l_voida(tapcashgo18L.tapcashgo17b_stringj, "boolean", tapcashgo8i.tapcashgo8i_e, (tapcashgointerface10h) this);
    }
}

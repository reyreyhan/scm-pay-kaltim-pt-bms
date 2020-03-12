package com.cardnfc.lib.bninfc.tapcashgo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.Vector;
//import p000a.p008d.p009a.tapcashgointerface30a;
//import p000a.p008d.p009a.tapcashgointerface32c;

public class tapcashgo34b {
    protected Vector tapcashgo34b_vectorf;
    protected StringBuffer tapcashgo34b_stringbufg;

    public void tapcashgo34b_voida(int i, int i2, @Nullable Object obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        if (this.tapcashgo34b_vectorf == null) {
            this.tapcashgo34b_vectorf = new Vector();
            this.tapcashgo34b_stringbufg = new StringBuffer();
        }
        if (i2 == 2) {
            if (obj instanceof tapcashgo35a) {
                ((tapcashgo35a) obj).tapcashgo35a_voida(this);
            } else {
                throw new RuntimeException("Element obj expected)");
            }
        } else if (!(obj instanceof String)) {
            throw new RuntimeException("String expected");
        }
        this.tapcashgo34b_vectorf.insertElementAt(obj, i);
        this.tapcashgo34b_stringbufg.insert(i, (char) i2);
    }

    public void tapcashgo34b_voida(int i, Object obj) {
        tapcashgo34b_voida(tapcashgo34b_inte(), i, obj);
    }

    public tapcashgo35a tapcashgo35a(@Nullable String str, String str2) {
        tapcashgo35a tapcashgo35A = new tapcashgo35a();
        if (str == null) {
            str = "";
        }
        tapcashgo35A.a = str;
        tapcashgo35A.b = str2;
        return tapcashgo35A;
    }

    public Object tapcashgo34b_objectf(int i) {
        return this.tapcashgo34b_vectorf.elementAt(i);
    }

    public int tapcashgo34b_inte() {
        return this.tapcashgo34b_vectorf == null ? 0 : this.tapcashgo34b_vectorf.size();
    }

    @Nullable
    public tapcashgo35a tapcashgo35a(int i) {
        Object f = tapcashgo34b_objectf(i);
        return f instanceof tapcashgo35a ? (tapcashgo35a) f : null;
    }

    @Nullable
    public tapcashgo35a tapcashgo35a_c(String str, @NonNull String str2) {
        int a = tapcashgo34b_inta(str, str2, 0);
        int a2 = tapcashgo34b_inta(str, str2, a + 1);
        if (a != -1 && a2 == -1) {
            return tapcashgo35a(a);
        }
        throw new RuntimeException("Element {" + str + "}" + str2 + (a == -1 ? " not found in " : " more than once in ") + this);
    }

    @Nullable
    public String tapcashgo34b_stringh(int i) {
        return tapcashgo34b_booleanj(i) ? (String) tapcashgo34b_objectf(i) : null;
    }

    public int tapcashgo34b_inti(int i) {
        return this.tapcashgo34b_stringbufg.charAt(i);
    }

    public int tapcashgo34b_inta(@Nullable String str, @NonNull String str2, int i) {
        int e = tapcashgo34b_inte();
        int i2 = i;
        while (i2 < e) {
            tapcashgo35a g = tapcashgo35a(i2);
            if (g != null && str2.equals(g.tapcashgo35a_stringc()) && (str == null || str.equals(g.tapcashgo35a_stringd()))) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    public boolean tapcashgo34b_booleanj(int i) {
        int i2 = tapcashgo34b_inti(i);
        return i2 == 4 || i2 == 7 || i2 == 5;
    }

    public void tapcashgo34b_voida(@NonNull tapcashgointerface30a tapcashgointerface30A) {
        Object obj = null;
        do {
            int h = tapcashgointerface30A.tapcashgointerface30a_inth();
            switch (h) {
                case 1:
                case 3:
                    obj = 1;
                    continue;
                case 2:
                    tapcashgo35a a = tapcashgo35a(tapcashgointerface30A.tapcashgointerface30a_stringe(), tapcashgointerface30A.tapcashgointerface30a_strngf());
                    tapcashgo34b_voida(2, a);
                    a.tapcashgo34b_voida(tapcashgointerface30A);
                    continue;
                default:
                    if (tapcashgointerface30A.tapcashgointerface30a_stringd() != null) {
                        if (h == 6) {
                            h = 4;
                        }
                        tapcashgo34b_voida(h, tapcashgointerface30A.tapcashgointerface30a_stringd());
                    } else if (h == 6 && tapcashgointerface30A.tapcashgointerface30a_strngf() != null) {
                        tapcashgo34b_voida(6, tapcashgointerface30A.tapcashgointerface30a_strngf());
                    }
                    tapcashgointerface30A.tapcashgointerface30a_intj();
                    continue;
            }
        } while (obj == null);
    }

    public void tapcashgo34b_voida(@NonNull tapcashgointerface32c tapcashgointerface32C) throws IOException {
        tapcashgo34b_voidb(tapcashgointerface32C);
        tapcashgointerface32C.tapcashgointerface32c_voidb();
    }

    public void tapcashgo34b_voidb(@NonNull tapcashgointerface32c tapcashgointerface32C) throws IOException {
        if (this.tapcashgo34b_vectorf != null) {
            int size = this.tapcashgo34b_vectorf.size();
            for (int i = 0; i < size; i++) {
                int i2 = tapcashgo34b_inti(i);
                Object elementAt = this.tapcashgo34b_vectorf.elementAt(i);
                switch (i2) {
                    case 2:
                        ((tapcashgo35a) elementAt).tapcashgo34b_voida(tapcashgointerface32C);
                        break;
                    case 4:
                        tapcashgointerface32C.tapcashgointerface32c_d((String) elementAt);
                        break;
                    case 5:
                        tapcashgointerface32C.tapcashgointerface32c_voide((String) elementAt);
                        break;
                    case 6:
                        tapcashgointerface32C.tapcashgointerface32c_voidb((String) elementAt);
                        break;
                    case 7:
                        tapcashgointerface32C.tapcashgointerface32c_voidc((String) elementAt);
                        break;
                    case 8:
                        tapcashgointerface32C.tapcashgointerface32c_voidg((String) elementAt);
                        break;
                    case 9:
                        tapcashgointerface32C.tapcashgointerface32c_voidf((String) elementAt);
                        break;
                    case 10:
                        tapcashgointerface32C.tapcashgointerface32c_voida((String) elementAt);
                        break;
                    default:
                        throw new RuntimeException("Illegal type: " + i2);
                }
            }
        }
    }
}

package com.cardnfc.lib.bninfc.tapcashgo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;
//import a.p005b.tapcashgo17b;
//import a.p005b.tapcashgo28c;
//import a.p005b.tapcashgo29d;
//import a.p008d.p009a.tapcashgointerface30a;
//import a.p008d.p009a.tapcashgointerface32c;
//import a.p008d.p009a.C0036b;

public class tapcashgo18l extends tapcashgo17b {
    static final tapcashgointerface10h tapcashgointerface10h = new tapcashgo11c();
    @NonNull
    public Hashtable f61l = new Hashtable();
    public boolean f62m;
    public boolean f63n;
    public boolean f64o;
    public boolean f65p;
    @NonNull
    protected Hashtable f66q = new Hashtable();
    @NonNull
    protected Hashtable f67r = new Hashtable();
    protected boolean f68s = true;
    @NonNull
    Hashtable f69t = new Hashtable();
    Vector f70u;

    public tapcashgo18l(int i) {
       // super(this);
         super(i);
//        super();
        tapcashgo18l_voida(this.tapcashgo17b_stringh, "Array", tapcashgo8i.tapcashgo8i_f);
        tapcashgointerface10h.tapcashgointerface10h_6a(this);
    }

    public void tapcashgo18l_voida(boolean z) {
        this.f68s = z;
    }

    public void tapcashgo17b_voidc(@NonNull tapcashgointerface30a tapcashgointerface30A) {
        this.tapcashgo17b_objecta = null;
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
        while (tapcashgointerface30A.tapcashgointerface30a_inth() == 2) {
            String attributeValue = tapcashgointerface30A.getAttributeValue(this.tapcashgo17b_stringh, "rootView");
            Object a = tapcashgo18l_objecta(tapcashgointerface30A, null, -1, tapcashgointerface30A.tapcashgointerface30a_stringe(), tapcashgointerface30A.tapcashgointerface30a_strngf(), tapcashgo8i.tapcashgo8i);
            if ("1".equals(attributeValue) || this.tapcashgo17b_objecta == null) {
                this.tapcashgo17b_objecta = a;
            }
            tapcashgointerface30A.tapcashgointerface30a_intk();
        }
    }

    protected void tapcashgo18l_voida(@NonNull tapcashgointerface30a tapcashgointerface30A, @NonNull tapcashgo15j tapcashgo15J) {
        for (int i = 0; i < tapcashgointerface30A.getAttributeCount(); i++) {
            tapcashgo15J.tapcashgo7a_voida_(tapcashgointerface30A.getAttributeName(i), tapcashgointerface30A.getAttributeValue(i));
        }
        tapcashgo18l_voida(tapcashgointerface30A, (tapcashgointerface14g) tapcashgo15J);
    }

    protected void tapcashgo18l_voida(@NonNull tapcashgointerface30a tapcashgointerface30A, tapcashgointerface14g tapcashgointerface14G) {
        int k;
        k = tapcashgointerface30A.tapcashgointerface30a_intk();
        while (k != 3) {
            String f = tapcashgointerface30A.tapcashgointerface30a_strngf();
            if (this.f62m && (tapcashgointerface14G instanceof tapcashgo15j)) {
                ((tapcashgo15j) tapcashgointerface14G).tapcashgo15j(tapcashgointerface30A.tapcashgointerface30a_strngf(), tapcashgo18l_objecta(tapcashgointerface30A, tapcashgointerface14G, tapcashgointerface14G.tapcashgointerface14g_intb(), ((tapcashgo15j) tapcashgointerface14G).tapcashgo15j_stringd(), f, tapcashgo8i.tapcashgo8i));
            } else {
                tapcashgo8i tapcashgo8I = new tapcashgo8i();
                int b = tapcashgointerface14G.tapcashgointerface14g_intb();
                Object obj = null;
                for (int i = 0; i < b && obj == null; i++) {
                    tapcashgo8I.tapcashgo8i_37a();
                    tapcashgointerface14G.tapcashgointerface14g_voida(i, this.f61l, tapcashgo8I);
                    if ((f.equals(tapcashgo8I.tapcashgo8i_stringh) && tapcashgo8I.tapcashgo8i_stringi == null) || (f.equals(tapcashgo8I.tapcashgo8i_stringh) && tapcashgointerface30A.tapcashgointerface30a_stringe().equals(tapcashgo8I.tapcashgo8i_stringi))) {
                        tapcashgointerface14G.tapcashgointerface14g_voida(i, tapcashgo18l_objecta(tapcashgointerface30A, tapcashgointerface14G, i, null, null, tapcashgo8I));
                        obj = 1;
                    }
                }
                if (obj == null) {
                    if (this.f65p) {
                        while (true) {
                            if (tapcashgointerface30A.tapcashgointerface30a_inti() == 3 && f.equals(tapcashgointerface30A.tapcashgointerface30a_strngf())) {
                            }
                        }
                       // k = tapcashgointerface30A.tapcashgointerface30a_intk();
                    } else {
                        throw new RuntimeException("Unknown Property: " + f);
                    }
                } else if (tapcashgointerface14G instanceof tapcashgo6e) {
                    tapcashgo6e tapcashgo6E = (tapcashgo6e) tapcashgointerface14G;
                    int attributeCount = tapcashgointerface30A.getAttributeCount();
                    for (int i2 = 0; i2 < attributeCount; i2++) {
                        tapcashgo9b tapcashgo9B = new tapcashgo9b();
                        tapcashgo9B.tapcashgo8i_voida(tapcashgointerface30A.getAttributeName(i2));
                        tapcashgo9B.tapcashgo8i_voidb_(tapcashgointerface30A.getAttributeValue(i2));
                        tapcashgo9B.tapcashgo8i_voidb(tapcashgointerface30A.tapcashgointerface30a_stringe(i2));
                        tapcashgo9B.tapcashgo8i_voida_(tapcashgointerface30A.tapcashgointerface30a_stringd(i2));
                        tapcashgo6E.tapcashgo6e_4b(tapcashgo9B);
                    }
                }
            }
            k = tapcashgointerface30A.tapcashgointerface30a_intk();
        }
        tapcashgointerface30A.tapcashgointerface30a_voida(3, null, null);
    }

    @Nullable
    protected Object tapcashgo18l_objecta(@NonNull tapcashgointerface30a tapcashgointerface30A, String str, String str2) {
        String str3;
        Object obj = null;
       // obj;

        int i = 0;
        String f = tapcashgointerface30A.tapcashgointerface30a_strngf();
        String e = tapcashgointerface30A.tapcashgointerface30a_stringe();
        Vector vector = new Vector();
        for (int i2 = 0; i2 < tapcashgointerface30A.getAttributeCount(); i2++) {
            tapcashgo9b tapcashgo9B = new tapcashgo9b();
            tapcashgo9B.tapcashgo8i_voida(tapcashgointerface30A.getAttributeName(i2));
            tapcashgo9B.tapcashgo8i_voidb_(tapcashgointerface30A.getAttributeValue(i2));
            tapcashgo9B.tapcashgo8i_voidb(tapcashgointerface30A.tapcashgointerface30a_stringe(i2));
            tapcashgo9B.tapcashgo8i_voida_(tapcashgointerface30A.tapcashgointerface30a_stringd(i2));
            vector.addElement(tapcashgo9B);
        }
        tapcashgointerface30A.tapcashgointerface30a_inti();
        int i3;
        if (tapcashgointerface30A.tapcashgointerface30a_inth() == 4) {
            String d = tapcashgointerface30A.tapcashgointerface30a_stringd();
            tapcashgo16k tapcashgo16K = new tapcashgo16k(str, str2, d);
            for (i3 = 0; i3 < vector.size(); i3++) {
                tapcashgo16K.tapcashgo7a_voida___((tapcashgo9b) vector.elementAt(i3));
            }
            tapcashgointerface30A.tapcashgointerface30a_inti();
            str3 = d;
             obj = tapcashgo16K;
        } else if (tapcashgointerface30A.tapcashgointerface30a_inth() == 3) {
            tapcashgo15j tapcashgo15J = new tapcashgo15j(str, str2);
            for (i3 = 0; i3 < vector.size(); i3++) {
                tapcashgo15J.tapcashgo7a_voida___((tapcashgo9b) vector.elementAt(i3));
            }
            str3 = null;
            tapcashgo15j tapcashgo15J2 = tapcashgo15J;
        } else {
            str3 = null;
            obj = null;
        }
        if (tapcashgointerface30A.tapcashgointerface30a_inth() == 2) {
            if (str3 == null || str3.trim().length() == 0) {
                tapcashgo15j objx = new tapcashgo15j(str, str2);
                while (i < vector.size()) {
                    objx.tapcashgo7a_voida___((tapcashgo9b) vector.elementAt(i));
                    i++;
                }
                while (tapcashgointerface30A.tapcashgointerface30a_inth() != 3) {
                    objx.tapcashgo15j(tapcashgointerface30A.tapcashgointerface30a_stringe(), tapcashgointerface30A.tapcashgointerface30a_strngf(), tapcashgo18l_objecta(tapcashgointerface30A, objx, objx.tapcashgo15j_intb(), null, null, tapcashgo8i.tapcashgo8i));
                    tapcashgointerface30A.tapcashgointerface30a_intk();
                }
            } else {
                throw new RuntimeException("Malformed input: Mixed content");
            }
        }
        tapcashgointerface30A.tapcashgointerface30a_voida(3, e, f);
        return obj;
    }

    private int tapcashgo18l_inta(@Nullable String str, int i, int i2) {
        if (str != null) {
            try {
                if (str.length() - i >= 3) {
                    i2 = Integer.parseInt(str.substring(i + 1, str.length() - 1));
                }
            } catch (Exception e) {
            }
        }
        return i2;
    }

    protected void tapcashgo18l_voida(@NonNull tapcashgointerface30a tapcashgointerface30A, @NonNull Vector vector, @Nullable tapcashgo8i tapcashgo8I) {
        String substring;
        String a;
        int i;
        int i2;
        tapcashgo8i tapcashgo8I2;
        int size = vector.size();
        String attributeValue = tapcashgointerface30A.getAttributeValue(this.tapcashgo17b_stringh, "arrayType");
        if (attributeValue != null) {
            size = attributeValue.indexOf(58);
            int indexOf = attributeValue.indexOf("[", size);
            substring = attributeValue.substring(size + 1, indexOf);
            a = tapcashgointerface30A.tapcashgointerface30a_stringa(size == -1 ? "" : attributeValue.substring(0, size));
            size = tapcashgo18l_inta(attributeValue, indexOf, -1);
            if (size != -1) {
                vector.setSize(size);
                i = 0;
                i2 = size;
            } else {
                i = 1;
                i2 = size;
            }
        } else {
            i = 1;
            substring = null;
            a = null;
            i2 = size;
        }
        if (tapcashgo8I == null) {
            tapcashgo8I2 = tapcashgo8i.tapcashgo8i;
        } else {
            tapcashgo8I2 = tapcashgo8I;
        }
        tapcashgointerface30A.tapcashgointerface30a_intk();
        size = tapcashgo18l_inta(tapcashgointerface30A.getAttributeValue(this.tapcashgo17b_stringh, "offset"), 0, 0);
        while (tapcashgointerface30A.tapcashgointerface30a_inth() != 3) {
            int indexOf = tapcashgo18l_inta(tapcashgointerface30A.getAttributeValue(this.tapcashgo17b_stringh, "position"), 0, size);
            if (i != 0 && indexOf >= i2) {
                i2 = indexOf + 1;
                vector.setSize(i2);
            }
            int i3 = i2;
            vector.setElementAt(tapcashgo18l_objecta(tapcashgointerface30A, vector, indexOf, a, substring, tapcashgo8I2), indexOf);
            size = indexOf + 1;
            tapcashgointerface30A.tapcashgointerface30a_intk();
            i2 = i3;
        }
        tapcashgointerface30A.tapcashgointerface30a_voida(3, null, null);
    }

    @NonNull
    protected String tapcashgo18l_stringb(@NonNull String str) {
        return str.substring(1);
    }

    @Nullable
    public Object tapcashgo18l_objecta(@NonNull tapcashgointerface30a tapcashgointerface30A, @Nullable Object obj, int i, String str, String str2, @NonNull tapcashgo8i tapcashgo8I) {
        Object a;
        String f = tapcashgointerface30A.tapcashgointerface30a_strngf();
        String attributeValue = tapcashgointerface30A.getAttributeValue(null, "href");
        if (attributeValue == null) {
            attributeValue = tapcashgointerface30A.getAttributeValue(this.tapcashgo17b_stringi, "nil");
            String attributeValue2 = tapcashgointerface30A.getAttributeValue(null, "id");
            if (attributeValue == null) {
                attributeValue = tapcashgointerface30A.getAttributeValue(this.tapcashgo17b_stringi, "null");
            }
            if (attributeValue == null || !tapcashgo17b.tapcashgo17b_booleana(attributeValue)) {
                String attributeValue3 = tapcashgointerface30A.getAttributeValue(this.tapcashgo17b_stringi, "type");
                if (attributeValue3 != null) {
                    int indexOf = attributeValue3.indexOf(58);
                    str2 = attributeValue3.substring(indexOf + 1);
                    str = tapcashgointerface30A.tapcashgointerface30a_stringa(indexOf == -1 ? "" : attributeValue3.substring(0, indexOf));
                } else if (str2 == null && str == null) {
                    if (tapcashgointerface30A.getAttributeValue(this.tapcashgo17b_stringh, "arrayType") != null) {
                        str = this.tapcashgo17b_stringh;
                        str2 = "Array";
                    } else {
                        Object[] a2 = tapcashgo18l_obejcta(tapcashgo8I.tapcashgo8i_objectl, null);
                        str2 = (String) a2[1];
                        str = (String) a2[0];
                    }
                }
                if (attributeValue3 == null) {
                    this.f62m = true;
                }
                a = tapcashgo18l_objecta(tapcashgointerface30A, str, str2, tapcashgo8I);
                if (a == null) {
                    a = tapcashgo18l_objecta(tapcashgointerface30A, str, str2);
                }
            } else {
                tapcashgointerface30A.tapcashgointerface30a_intk();
                tapcashgointerface30A.tapcashgointerface30a_voida(3, null, f);
                a = null;
            }
            if (attributeValue2 != null) {
                tapcashgo18l_voida(attributeValue2, a);
            }
        } else if (obj == null) {
            throw new RuntimeException("href at rootView level?!?");
        } else {
            String b = tapcashgo18l_stringb(attributeValue);
            a = this.f69t.get(b);
            if (a == null || (a instanceof tapcashgo12d)) {
                tapcashgo12d tapcashgo12D = new tapcashgo12d();
                tapcashgo12D.tapcashgo12d = (tapcashgo12d) a;
                tapcashgo12D.tapcashgo12d_objectb = obj;
                tapcashgo12D.tapcashgo12d_intc = i;
                this.f69t.put(b, tapcashgo12D);
                a = null;
            }
            tapcashgointerface30A.tapcashgointerface30a_intk();
            tapcashgointerface30A.tapcashgointerface30a_voida(3, null, f);
        }
        tapcashgointerface30A.tapcashgointerface30a_voida(3, null, f);
        return a;
    }

    protected void tapcashgo18l_voida(String str, Object obj) {
        Object obj2 = this.f69t.get(str);
        if (obj2 instanceof tapcashgo12d) {
            tapcashgo12d tapcashgo12D = (tapcashgo12d) obj2;
            while (true) {
                if (tapcashgo12D.tapcashgo12d_objectb instanceof tapcashgointerface14g) {
                    ((tapcashgointerface14g) tapcashgo12D.tapcashgo12d_objectb).tapcashgointerface14g_voida(tapcashgo12D.tapcashgo12d_intc, obj);
                } else {
                    ((Vector) tapcashgo12D.tapcashgo12d_objectb).setElementAt(obj, tapcashgo12D.tapcashgo12d_intc);
                }
                tapcashgo12d tapcashgo12D2 = tapcashgo12D.tapcashgo12d;
                if (tapcashgo12D2 == null) {
                    break;
                }
                tapcashgo12D = tapcashgo12D2;
            }
        } else if (obj2 != null) {
            throw new RuntimeException("double ID");
        }
        this.f69t.put(str, obj);
    }

    @Nullable
    public Object tapcashgo18l_objecta(@NonNull tapcashgointerface30a tapcashgointerface30A, String str, String str2, @NonNull tapcashgo8i tapcashgo8I) {
        Class<tapcashgo15j> cls = (Class<tapcashgo15j>) this.f66q.get(new tapcashgo16k(str, str2, null));
        if (cls == null) {
            return null;
        }
        Object e;
        if (cls == tapcashgo15j.class) {
            e = new tapcashgo15j(str, str2);
        } else {
            try {
                e = cls.newInstance();
            } catch (Exception e2) {
                throw new RuntimeException(e2.toString());
            }
        }
        if (e instanceof tapcashgo6e) {
            tapcashgo6e tapcashgo6E = (tapcashgo6e) e;
            int attributeCount = tapcashgointerface30A.getAttributeCount();
            for (int i = 0; i < attributeCount; i++) {
                tapcashgo9b tapcashgo9B = new tapcashgo9b();
                tapcashgo9B.tapcashgo8i_voida(tapcashgointerface30A.getAttributeName(i));
                tapcashgo9B.tapcashgo8i_voidb_(tapcashgointerface30A.getAttributeValue(i));
                tapcashgo9B.tapcashgo8i_voidb(tapcashgointerface30A.tapcashgointerface30a_stringe(i));
                tapcashgo9B.tapcashgo8i_voida_(tapcashgointerface30A.tapcashgointerface30a_stringd(i));
                tapcashgo6E.tapcashgo6e_4b(tapcashgo9B);
            }
        }
        if (e instanceof tapcashgo15j) {
            tapcashgo18l_voida(tapcashgointerface30A, (tapcashgo15j) e);
            return e;
        } else if (e instanceof tapcashgointerface14g) {
            if (e instanceof tapcashgointerface13f) {
                ((tapcashgointerface13f) e).tapcashgointerface13f_voida(tapcashgointerface30A.tapcashgointerface30a_stringd() != null ? tapcashgointerface30A.tapcashgointerface30a_stringd() : "");
            }
            tapcashgo18l_voida(tapcashgointerface30A, (tapcashgointerface14g) e);
            return e;
        } else if (e instanceof Vector) {
            tapcashgo18l_voida(tapcashgointerface30A, (Vector) e, tapcashgo8I.tapcashgo8i1);
            return e;
        } else {
            throw new RuntimeException("no deserializer for " + e.getClass());
        }
    }

    @Nullable
    public Object[] tapcashgo18l_obejcta(@Nullable Object obj, Object obj2) {
        Class cls;
        if (obj != null) {
            cls = (Class) obj;
        } else if ((obj2 instanceof tapcashgo15j) || (obj2 instanceof tapcashgo16k)) {
            cls = (Class) obj2;
        } else {
            cls = obj2.getClass();
        }
        if (obj instanceof tapcashgo15j) {
            tapcashgo15j tapcashgo15J = (tapcashgo15j) obj;
            return new Object[]{tapcashgo15J.tapcashgo15j_stringd(), tapcashgo15J.tapcashgo15j_stringc(), null, null};
        } else if (obj instanceof tapcashgo16k) {
            tapcashgo16k tapcashgo16K = (tapcashgo16k) obj;
            return new Object[]{tapcashgo16K.tapcashgo16k_stringb(), tapcashgo16K.tapcashgo16k_stringc(), null, tapcashgointerface10h};
        } else {
            if ((cls instanceof Class) && cls != tapcashgo8i.tapcashgo8i_a) {
                Object[] objArr = (Object[]) this.f67r.get(cls.getName());
                if (objArr != null) {
                    return objArr;
                }
            }
            return new Object[]{this.tapcashgo17b_stringj, "anyType", null, null};
        }
    }

    public void tapcashgo18l_voida(String str, String str2, @NonNull Class cls, @Nullable tapcashgointerface10h tapcashgointerface10H) {
        Object obj;
        Hashtable hashtable = this.f66q;
        tapcashgo16k tapcashgo16K = new tapcashgo16k(str, str2, null);
        if (tapcashgointerface10H == null) {
            obj = cls;
        } else {
           // tapcashgointerface10h c0010h2 = tapcashgointerface10H;
            obj = tapcashgointerface10H;
        }
        hashtable.put(tapcashgo16K, obj);
        this.f67r.put(cls.getName(), new Object[]{str, str2, null, tapcashgointerface10H});
    }

    public void tapcashgo18l_voida(String str, String str2, @NonNull Class cls) {
        tapcashgo18l_voida(str, str2, cls, null);
    }

    public void tapcashgo17b_voidc(@NonNull tapcashgointerface32c tapcashgointerface32C) throws IOException {
        if (this.tapcashgo17b_objectb != null) {
            this.f70u = new Vector();
            this.f70u.addElement(this.tapcashgo17b_objectb);
            Object[] a = tapcashgo18l_obejcta(null, this.tapcashgo17b_objectb);
            tapcashgointerface32C.tapcashgointerface32c_b(this.f64o ? "" : (String) a[0], (String) a[1]);
            if (this.f64o) {
                tapcashgointerface32C.tapcashgointerface32c_a(null, "xmlns", (String) a[0]);
            }
            if (this.f68s) {
                tapcashgointerface32C.tapcashgointerface32c_a(null, "id", a[2] == null ? "o0" : (String) a[2]);
                tapcashgointerface32C.tapcashgointerface32c_a(this.tapcashgo17b_stringh, "rootView", "1");
            }
            tapcashgo18l_voida(tapcashgointerface32C, this.tapcashgo17b_objectb, null, a[3]);
            tapcashgointerface32C.tapcashgointerface32c_c(this.f64o ? "" : (String) a[0], (String) a[1]);
        }
    }

    private void tapcashgo18l_voida(@NonNull tapcashgointerface32c tapcashgointerface32C, @NonNull tapcashgo6e tapcashgo6E) throws IOException {
        int a = tapcashgo6E.tapcashgo6e_1a();
        for (int i = 0; i < a; i++) {
            tapcashgo9b tapcashgo9B = new tapcashgo9b();
            tapcashgo6E.tapcashgo6e_2a(i, tapcashgo9B);
            tapcashgo6E.tapcashgo6e_3b(i, tapcashgo9B);
            if (tapcashgo9B.tapcashgo8i_objectd() != null) {
                tapcashgointerface32C.tapcashgointerface32c_a(tapcashgo9B.tapcashgo8i_stringc(), tapcashgo9B.tapcashgo8i_stringb(), tapcashgo9B.tapcashgo8i_objectd().toString());
            }
        }
    }

    public void tapcashgo18l_voida(@NonNull tapcashgointerface32c tapcashgointerface32C, tapcashgointerface14g tapcashgointerface14G) throws IOException {
        if (tapcashgointerface14G instanceof tapcashgo6e) {
            tapcashgo18l_voida(tapcashgointerface32C, (tapcashgo6e) tapcashgointerface14G);
        }
        tapcashgo18l_voida(tapcashgointerface32C, (ArrayList) tapcashgointerface14G);
    }

    public void tapcashgo18l_voidb(@NonNull tapcashgointerface32c tapcashgointerface32C, tapcashgointerface14g tapcashgointerface14G) throws IOException {
        if (tapcashgointerface14G instanceof tapcashgo6e) {
            tapcashgo18l_voida(tapcashgointerface32C, (tapcashgo6e) tapcashgointerface14G);
        }
        tapcashgo18l_voidc(tapcashgointerface32C, tapcashgointerface14G);
    }

    public void tapcashgo18l_voidc(@NonNull tapcashgointerface32c tapcashgointerface32C, @NonNull tapcashgointerface14g tapcashgointerface14G) throws IOException {
        int b = tapcashgointerface14G.tapcashgointerface14g_intb();
        tapcashgo8i tapcashgo8I = new tapcashgo8i();
        for (int i = 0; i < b; i++) {
            Object b_ = tapcashgointerface14G.b_(i);
            tapcashgointerface14G.tapcashgointerface14g_voida(i, this.f61l, tapcashgo8I);
            if (b_ instanceof tapcashgo15j) {
                String str;
                String str2;
               // b_ = b_;
                Object[] a = tapcashgo18l_obejcta(null, b_);
                String str3 = (String) a[0];
                str3 = (String) a[1];
                if (tapcashgo8I.tapcashgo8i_stringh == null || tapcashgo8I.tapcashgo8i_stringh.length() <= 0) {
                    str = (String) a[1];
                } else {
                    str = tapcashgo8I.tapcashgo8i_stringh;
                }
                if (tapcashgo8I.tapcashgo8i_stringi == null || tapcashgo8I.tapcashgo8i_stringi.length() <= 0) {
                    str2 = (String) a[0];
                } else {
                    str2 = tapcashgo8I.tapcashgo8i_stringi;
                }
                tapcashgointerface32C.tapcashgointerface32c_b(str2, str);
                if (!this.f62m) {
                    tapcashgointerface32C.tapcashgointerface32c_a(this.tapcashgo17b_stringi, "type", tapcashgointerface32C.tapcashgointerface32c_stringa(str2, true) + ":" + str3);
                }
                tapcashgo18l_voidb(tapcashgointerface32C, (tapcashgointerface14g) b_);
                tapcashgointerface32C.tapcashgointerface32c_c(str2, str);
            } else if ((tapcashgo8I.tapcashgo8i_stringj & 1) == 0) {
                Object b_2 = tapcashgointerface14G.b_(i);
                if (!((b_ == null && this.f63n) || b_2 == tapcashgo16k.tapcashgo16k_objecte)) {
                    tapcashgointerface32C.tapcashgointerface32c_b(tapcashgo8I.tapcashgo8i_stringi, tapcashgo8I.tapcashgo8i_stringh);
                    tapcashgo18l_voida(tapcashgointerface32C, b_2, tapcashgo8I);
                    tapcashgointerface32C.tapcashgointerface32c_c(tapcashgo8I.tapcashgo8i_stringi, tapcashgo8I.tapcashgo8i_stringh);
                }
            }
        }
        tapcashgo18l_voidd(tapcashgointerface32C, tapcashgointerface14G);
    }

    private void tapcashgo18l_voidd(@NonNull tapcashgointerface32c tapcashgointerface32C, tapcashgointerface14g tapcashgointerface14G) throws IOException {
        if (tapcashgointerface14G instanceof tapcashgointerface13f) {
            Object b_ = ((tapcashgointerface13f) tapcashgointerface14G).b_();
            if (b_ == null) {
                return;
            }
            if (b_ instanceof tapcashgo19m) {
                ((tapcashgo19m) b_).tapcashgo19m_voida(tapcashgointerface32C);
            } else {
                tapcashgointerface32C.tapcashgointerface32c_voide(b_.toString());
            }
        }
    }

    protected void tapcashgo18l_voida(@NonNull tapcashgointerface32c tapcashgointerface32C, @Nullable Object obj, @NonNull tapcashgo8i tapcashgo8I) throws IOException {
        if (obj == null || obj == tapcashgo16k.tapcashgo16k_objectf) {
            String str;
            String str2 = this.tapcashgo17b_stringi;
            if (this.tapcashgo17b_intf >= 120) {
                str = "nil";
            } else {
                str = "null";
            }
            tapcashgointerface32C.tapcashgointerface32c_a(str2, str, "true");
            return;
        }
        Object[] a = tapcashgo18l_obejcta(null, obj);
        if (tapcashgo8I.tapcashgo8i_booleanm || a[2] != null) {
            int indexOf = this.f70u.indexOf(obj);
            if (indexOf == -1) {
                indexOf = this.f70u.size();
                this.f70u.addElement(obj);
            }
            tapcashgointerface32C.tapcashgointerface32c_a(null, "href", a[2] == null ? "#o" + indexOf : "#" + a[2]);
            return;
        }
        if (!(this.f62m && obj.getClass() == tapcashgo8I.tapcashgo8i_objectl)) {
            tapcashgointerface32C.tapcashgointerface32c_a(this.tapcashgo17b_stringi, "type", tapcashgointerface32C.tapcashgointerface32c_stringa((String) a[0], true) + ":" + a[1]);
        }
        tapcashgo18l_voida(tapcashgointerface32C, obj, tapcashgo8I, a[3]);
    }

    protected void tapcashgo18l_voida(@NonNull tapcashgointerface32c tapcashgointerface32C, Object obj, @NonNull tapcashgo8i tapcashgo8I, @Nullable Object obj2) throws IOException {
        if (obj2 != null) {
            ((tapcashgointerface10h) obj2).tapcashgointerface10h_7a(tapcashgointerface32C, obj);
        } else if ((obj instanceof tapcashgointerface14g) || obj == tapcashgo16k.tapcashgo16k_objectf || obj == tapcashgo16k.tapcashgo16k_objecte) {
            if (obj instanceof ArrayList) {
                tapcashgo18l_voida(tapcashgointerface32C, (tapcashgointerface14g) obj);
            } else {
                tapcashgo18l_voidb(tapcashgointerface32C, (tapcashgointerface14g) obj);
            }
        } else if (obj instanceof tapcashgo6e) {
            tapcashgo18l_voida(tapcashgointerface32C, (tapcashgo6e) obj);
        } else if (obj instanceof Vector) {
            tapcashgo18l_voida_(tapcashgointerface32C, (Vector) obj, tapcashgo8I.tapcashgo8i1);
        } else {
            throw new RuntimeException("Cannot serialize: " + obj);
        }
    }

    protected void tapcashgo18l_voida(@NonNull tapcashgointerface32c tapcashgointerface32C, @NonNull ArrayList arrayList) throws IOException {
        tapcashgointerface14g tapcashgointerface14G = (tapcashgointerface14g) arrayList;
        int size = arrayList.size();
        tapcashgo8i tapcashgo8I = new tapcashgo8i();
        for (int i = 0; i < size; i++) {
            Object b_ = tapcashgointerface14G.b_(i);
            tapcashgointerface14G.tapcashgointerface14g_voida(i, this.f61l, tapcashgo8I);
            if (b_ instanceof tapcashgo15j) {
                String str;
                String str2;
              //  b_ = b_;
                Object[] a = tapcashgo18l_obejcta(null, b_);
                String str3 = (String) a[0];
                str3 = (String) a[1];
                if (tapcashgo8I.tapcashgo8i_stringh == null || tapcashgo8I.tapcashgo8i_stringh.length() <= 0) {
                    str = (String) a[1];
                } else {
                    str = tapcashgo8I.tapcashgo8i_stringh;
                }
                if (tapcashgo8I.tapcashgo8i_stringi == null || tapcashgo8I.tapcashgo8i_stringi.length() <= 0) {
                    str2 = (String) a[0];
                } else {
                    str2 = tapcashgo8I.tapcashgo8i_stringi;
                }
                tapcashgointerface32C.tapcashgointerface32c_b(str2, str);
                if (!this.f62m) {
                    tapcashgointerface32C.tapcashgointerface32c_a(this.tapcashgo17b_stringi, "type", tapcashgointerface32C.tapcashgointerface32c_stringa(str2, true) + ":" + str3);
                }
                tapcashgo18l_voidb(tapcashgointerface32C, (tapcashgointerface14g) b_);
                tapcashgointerface32C.tapcashgointerface32c_c(str2, str);
            } else if ((tapcashgo8I.tapcashgo8i_stringj & 1) == 0) {
                Object b_2 = tapcashgointerface14G.b_(i);
                if (!((b_ == null && this.f63n) || b_2 == tapcashgo16k.tapcashgo16k_objecte)) {
                    tapcashgointerface32C.tapcashgointerface32c_b(tapcashgo8I.tapcashgo8i_stringi, tapcashgo8I.tapcashgo8i_stringh);
                    tapcashgo18l_voida(tapcashgointerface32C, b_2, tapcashgo8I);
                    tapcashgointerface32C.tapcashgointerface32c_c(tapcashgo8I.tapcashgo8i_stringi, tapcashgo8I.tapcashgo8i_stringh);
                }
            }
        }
        tapcashgo18l_voidd(tapcashgointerface32C, tapcashgointerface14G);
    }

    protected void tapcashgo18l_voida_(@NonNull tapcashgointerface32c tapcashgointerface32C, @NonNull Vector vector, @Nullable tapcashgo8i tapcashgo8I) throws IOException {
        String str;
        String str2 = "item";
        if (tapcashgo8I == null) {
            tapcashgo8I = tapcashgo8i.tapcashgo8i;
            str = str2;
            str2 = null;
        } else if (!(tapcashgo8I instanceof tapcashgo8i) || tapcashgo8I.tapcashgo8i_stringh == null) {
            str = str2;
            str2 = null;
        } else {
            str = tapcashgo8I.tapcashgo8i_stringh;
            str2 = tapcashgo8I.tapcashgo8i_stringi;
        }
        int size = vector.size();
        Object[] a = tapcashgo18l_obejcta(tapcashgo8I.tapcashgo8i_objectl, null);
        if (!this.f62m) {
            tapcashgointerface32C.tapcashgointerface32c_a(this.tapcashgo17b_stringh, "arrayType", tapcashgointerface32C.tapcashgointerface32c_stringa((String) a[0], false) + ":" + a[1] + "[" + size + "]");
        } else if (str2 == null) {
            str2 = (String) a[0];
        }
        boolean z = false;
        for (int i = 0; i < size; i++) {
            if (vector.elementAt(i) == null) {
                z = true;
            } else {
                tapcashgointerface32C.tapcashgointerface32c_b(str2, str);
                if (z) {
                    tapcashgointerface32C.tapcashgointerface32c_a(this.tapcashgo17b_stringh, "position", "[" + i + "]");
                    z = false;
                }
                tapcashgo18l_voida(tapcashgointerface32C, vector.elementAt(i), tapcashgo8I);
                tapcashgointerface32C.tapcashgointerface32c_c(str2, str);
            }
        }
    }
}

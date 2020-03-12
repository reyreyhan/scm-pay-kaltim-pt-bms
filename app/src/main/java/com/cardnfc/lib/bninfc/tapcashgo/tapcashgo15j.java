package com.cardnfc.lib.bninfc.tapcashgo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Hashtable;
import java.util.Vector;

public class tapcashgo15j extends tapcashgo7a {//implements tapcashgointerface13f, tapcashgointerface14g {
    private String f41b;
    private String f42c;
    private Vector f43d;
    private Object f44e;

    public tapcashgo15j() {
        this("", "");
    }

    public tapcashgo15j(String str, String str2) {
        this.f43d = new Vector();
        this.f41b = str;
        this.f42c = str2;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof tapcashgo15j)) {
            return false;
        }
        tapcashgo15j tapcashgo15J = (tapcashgo15j) obj;
        if (!this.f42c.equals(tapcashgo15J.f42c) || !this.f41b.equals(tapcashgo15J.f41b)) {
            return false;
        }
        int size = this.f43d.size();
        if (size != tapcashgo15J.f43d.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!tapcashgo15J.tapcashgo15j_booleana(this.f43d.elementAt(i), i)) {
                return false;
            }
        }
        return tapcashgo7a_booleana_((tapcashgo7a) tapcashgo15J);
    }

    public boolean tapcashgo15j_booleana(Object obj, int i) {
        if (i >= tapcashgo15j_intb()) {
            return false;
        }
        Object elementAt = this.f43d.elementAt(i);
        if ((obj instanceof tapcashgo8i) && (elementAt instanceof tapcashgo8i)) {
            tapcashgo8i tapcashgo8I = (tapcashgo8i) obj;
            tapcashgo8i tapcashgo8I2 = (tapcashgo8i) elementAt;
            boolean z = tapcashgo8I.tapcashgo8i_stringb().equals(tapcashgo8I2.tapcashgo8i_stringb()) && tapcashgo8I.tapcashgo8i_objectd().equals(tapcashgo8I2.tapcashgo8i_objectd());
            return z;
        } else if ((obj instanceof tapcashgo15j) && (elementAt instanceof tapcashgo15j)) {
            return ((tapcashgo15j) obj).equals((tapcashgo15j) elementAt);
        } else {
            return false;
        }
    }

    public String tapcashgo15j_stringc() {
        return this.f42c;
    }

    public String tapcashgo15j_stringd() {
        return this.f41b;
    }

    @Nullable
    public Object b_(int i) {
        Object elementAt = this.f43d.elementAt(i);
        if (elementAt instanceof tapcashgo8i) {
            return ((tapcashgo8i) elementAt).tapcashgo8i_objectd();
        }
        return (tapcashgo15j) elementAt;
    }

    public int tapcashgo15j_intb() {
        return this.f43d.size();
    }

    public void tapcashgo15j_voida(int i, Hashtable hashtable, @NonNull tapcashgo8i tapcashgo8I) {
        tapcashgo15j_voida(i, tapcashgo8I);
    }

    public void tapcashgo15j_voida(int i, @NonNull tapcashgo8i tapcashgo8I) {
        Object elementAt = this.f43d.elementAt(i);
        if (elementAt instanceof tapcashgo8i) {
            tapcashgo8i tapcashgo8I2 = (tapcashgo8i) elementAt;
            tapcashgo8I.tapcashgo8i_stringh = tapcashgo8I2.tapcashgo8i_stringh;
            tapcashgo8I.tapcashgo8i_stringi = tapcashgo8I2.tapcashgo8i_stringi;
            tapcashgo8I.tapcashgo8i_stringj = tapcashgo8I2.tapcashgo8i_stringj;
            tapcashgo8I.tapcashgo8i_objectl = tapcashgo8I2.tapcashgo8i_objectl;
            tapcashgo8I.tapcashgo8i1 = tapcashgo8I2.tapcashgo8i1;
            tapcashgo8I.tapcashgo8i_objectk = tapcashgo8I2.tapcashgo8i_objectk;
            tapcashgo8I.tapcashgo8i_booleanm = tapcashgo8I2.tapcashgo8i_booleanm;
            return;
        }
        tapcashgo8I.tapcashgo8i_stringh = null;
        tapcashgo8I.tapcashgo8i_stringi = null;
        tapcashgo8I.tapcashgo8i_stringj = 0;
        tapcashgo8I.tapcashgo8i_objectl = null;
        tapcashgo8I.tapcashgo8i1 = null;
        tapcashgo8I.tapcashgo8i_objectk = elementAt;
        tapcashgo8I.tapcashgo8i_booleanm = false;
    }

    @NonNull
    public tapcashgo15j tapcashgo15j() {
        int i = 0;
        tapcashgo15j tapcashgo15J = new tapcashgo15j(this.f41b, this.f42c);
        for (int i2 = 0; i2 < this.f43d.size(); i2++) {
            Object elementAt = this.f43d.elementAt(i2);
            if (elementAt instanceof tapcashgo8i) {
                tapcashgo15J.tapcashgo15j((tapcashgo8i) ((tapcashgo8i) this.f43d.elementAt(i2)).clone());
            } else if (elementAt instanceof tapcashgo15j) {
                tapcashgo15J.tapcashgo15j(((tapcashgo15j) elementAt).tapcashgo15j());
            }
        }
        while (i < tapcashgo6e_1a()) {
            tapcashgo9b tapcashgo9B = new tapcashgo9b();
            tapcashgo6e_2a(i, tapcashgo9B);
            tapcashgo15J.tapcashgo7a_voida___(tapcashgo9B);
            i++;
        }
        return tapcashgo15J;
    }

    public void tapcashgo15j_voida(int i, Object obj) {
        Object elementAt = this.f43d.elementAt(i);
        if (elementAt instanceof tapcashgo8i) {
            ((tapcashgo8i) elementAt).tapcashgo8i_voidb_(obj);
        }
    }

    @NonNull
    public tapcashgo15j tapcashgo15j(String str, @Nullable Object obj) {
        tapcashgo8i tapcashgo8I = new tapcashgo8i();
        tapcashgo8I.tapcashgo8i_stringh = str;
        tapcashgo8I.tapcashgo8i_objectl = obj == null ? tapcashgo8i.tapcashgo8i_a : obj.getClass();
        tapcashgo8I.tapcashgo8i_objectk = obj;
        return tapcashgo15j(tapcashgo8I);
    }

    @NonNull
    public tapcashgo15j tapcashgo15j(String str, String str2, @Nullable Object obj) {
        tapcashgo8i tapcashgo8I = new tapcashgo8i();
        tapcashgo8I.tapcashgo8i_stringh = str2;
        tapcashgo8I.tapcashgo8i_stringi = str;
        tapcashgo8I.tapcashgo8i_objectl = obj == null ? tapcashgo8i.tapcashgo8i_a : obj.getClass();
        tapcashgo8I.tapcashgo8i_objectk = obj;
        return tapcashgo15j(tapcashgo8I);
    }

    @NonNull
    public tapcashgo15j tapcashgo15j(tapcashgo8i tapcashgo8I) {
        this.f43d.addElement(tapcashgo8I);
        return this;
    }

    @NonNull
    public tapcashgo15j tapcashgo15j(tapcashgo15j tapcashgo15J) {
        this.f43d.addElement(tapcashgo15J);
        return this;
    }

    @NonNull
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("" + this.f42c + "{");
        for (int i = 0; i < tapcashgo15j_intb(); i++) {
            Object elementAt = this.f43d.elementAt(i);
            if (elementAt instanceof tapcashgo8i) {
                stringBuffer.append("").append(((tapcashgo8i) elementAt).tapcashgo8i_stringb()).append("=").append(b_(i)).append("; ");
            } else {
                stringBuffer.append(((tapcashgo15j) elementAt).toString());
            }
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    public Object b_() {
        return this.f44e;
    }

    public void tapcashgo15j_voida(Object obj) {
        this.f44e = obj;
    }
}

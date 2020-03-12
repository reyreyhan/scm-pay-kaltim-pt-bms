package com.cardnfc.lib.bninfc.tapcashgo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Vector;

public class tapcashgo7a implements tapcashgo6e {
    @NonNull
    protected Vector tapcashgo7a_vectora = new Vector();

    public void tapcashgo6e_2a(int i, @NonNull tapcashgo9b tapcashgo9B) {
        tapcashgo9b tapcashgo9B2 = (tapcashgo9b) this.tapcashgo7a_vectora.elementAt(i);
        tapcashgo9B.tapcashgo8i_stringh = tapcashgo9B2.tapcashgo8i_stringh;
        tapcashgo9B.tapcashgo8i_stringi = tapcashgo9B2.tapcashgo8i_stringi;
        tapcashgo9B.tapcashgo8i_stringj = tapcashgo9B2.tapcashgo8i_stringj;
        tapcashgo9B.tapcashgo8i_objectl = tapcashgo9B2.tapcashgo8i_objectl;
        tapcashgo9B.tapcashgo8i1 = tapcashgo9B2.tapcashgo8i1;
        tapcashgo9B.tapcashgo8i_objectk = tapcashgo9B2.tapcashgo8i_objectd();
    }

    public Object tapcashgo7a_objecta(int i) {
        return ((tapcashgo9b) this.tapcashgo7a_vectora.elementAt(i)).tapcashgo8i_objectd();
    }

    public boolean tapcashgo7a_booleana(@NonNull String str) {
        if (tapcashgo7a_intc(str) != null) {
            return true;
        }
        return false;
    }

    @Nullable
    public Object tapcashgo7a_objectb(@NonNull String str) {
        Integer c = tapcashgo7a_intc(str);
        if (c != null) {
            return tapcashgo7a_objecta(c.intValue());
        }
        return null;
    }

    @Nullable
    private Integer tapcashgo7a_intc(@NonNull String str) {
        for (int i = 0; i < this.tapcashgo7a_vectora.size(); i++) {
            if (str.equals(((tapcashgo9b) this.tapcashgo7a_vectora.elementAt(i)).tapcashgo8i_stringb())) {
                return new Integer(i);
            }
        }
        return null;
    }

    public int tapcashgo6e_1a() {
        return this.tapcashgo7a_vectora.size();
    }

    protected boolean tapcashgo7a_booleana_(@NonNull tapcashgo7a tapcashgo7A) {
        int a = tapcashgo6e_1a();
        if (a != tapcashgo7A.tapcashgo6e_1a()) {
            return false;
        }
        for (int i = 0; i < a; i++) {
            tapcashgo9b tapcashgo9B = (tapcashgo9b) this.tapcashgo7a_vectora.elementAt(i);
            Object d = tapcashgo9B.tapcashgo8i_objectd();
            if (!tapcashgo7A.tapcashgo7a_booleana(tapcashgo9B.tapcashgo8i_stringb())) {
                return false;
            }
            if (!d.equals(tapcashgo7A.tapcashgo7a_objectb(tapcashgo9B.tapcashgo8i_stringb()))) {
                return false;
            }
        }
        return true;
    }

    public void tapcashgo7a_voida_(String str, Object obj) {
        tapcashgo7a_voida__(null, str, obj);
    }

    public void tapcashgo7a_voida__(String str, String str2, @Nullable Object obj) {
        tapcashgo9b tapcashgo9B = new tapcashgo9b();
        tapcashgo9B.tapcashgo8i_stringh = str2;
        tapcashgo9B.tapcashgo8i_stringi = str;
        tapcashgo9B.tapcashgo8i_objectl = obj == null ? tapcashgo8i.tapcashgo8i_a : obj.getClass();
        tapcashgo9B.tapcashgo8i_objectk = obj;
        tapcashgo7a_voida___(tapcashgo9B);
    }

    public void tapcashgo7a_voida___(tapcashgo9b tapcashgo9B) {
        this.tapcashgo7a_vectora.addElement(tapcashgo9B);
    }

    public void tapcashgo6e_4b(tapcashgo9b tapcashgo9B) {
    }

    public void tapcashgo6e_3b(int i, tapcashgo9b tapcashgo9B) {
    }
}

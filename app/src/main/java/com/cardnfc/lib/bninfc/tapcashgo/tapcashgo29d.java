package com.cardnfc.lib.bninfc.tapcashgo;

//import p000a.p008d.p009a.tapcashgointerface30a;
//import p000a.p010c.p012b.tapcashgo34b;

import androidx.annotation.NonNull;

public class tapcashgo29d extends tapcashgo28c {
    public tapcashgo34b f95f;
    public tapcashgo34b f96g;
    public tapcashgo34b f97h;
    public tapcashgo34b f98i;
    public tapcashgo34b f99j;

    public tapcashgo29d() {
        this.f94e = 120;
    }

    public tapcashgo29d(int i) {
        this.f94e = i;
    }

    public void tapcashgo28c_voida(@NonNull tapcashgointerface30a tapcashgointerface30A) {
        tapcashgo29d_voidb(tapcashgointerface30A);
        this.f90a = this.f95f.tapcashgo35a_c("http://www.w3.org/2003/05/soap-envelope", "Value").tapcashgo34b_stringh(0);
        this.f91b = this.f96g.tapcashgo35a_c("http://www.w3.org/2003/05/soap-envelope", "Text").tapcashgo34b_stringh(0);
        this.f93d = this.f99j;
        this.f92c = null;
    }

    private void tapcashgo29d_voidb(@NonNull tapcashgointerface30a tapcashgointerface30A) {
        tapcashgointerface30A.tapcashgointerface30a_voida(2, "http://www.w3.org/2003/05/soap-envelope", "Fault");
        while (tapcashgointerface30A.tapcashgointerface30a_intk() == 2) {
            String f = tapcashgointerface30A.tapcashgointerface30a_strngf();
            String e = tapcashgointerface30A.tapcashgointerface30a_stringe();
            tapcashgointerface30A.tapcashgointerface30a_intk();
            if (f.toLowerCase().equals("Code".toLowerCase())) {
                this.f95f = new tapcashgo34b();
                this.f95f.tapcashgo34b_voida(tapcashgointerface30A);
            } else if (f.toLowerCase().equals("Reason".toLowerCase())) {
                this.f96g = new tapcashgo34b();
                this.f96g.tapcashgo34b_voida(tapcashgointerface30A);
            } else if (f.toLowerCase().equals("Node".toLowerCase())) {
                this.f97h = new tapcashgo34b();
                this.f97h.tapcashgo34b_voida(tapcashgointerface30A);
            } else if (f.toLowerCase().equals("Role".toLowerCase())) {
                this.f98i = new tapcashgo34b();
                this.f98i.tapcashgo34b_voida(tapcashgointerface30A);
            } else if (f.toLowerCase().equals("Detail".toLowerCase())) {
                this.f99j = new tapcashgo34b();
                this.f99j.tapcashgo34b_voida(tapcashgointerface30A);
            } else {
                throw new RuntimeException("unexpected tag:" + f);
            }
            tapcashgointerface30A.tapcashgointerface30a_voida(3, e, f);
        }
        tapcashgointerface30A.tapcashgointerface30a_voida(3, "http://www.w3.org/2003/05/soap-envelope", "Fault");
        tapcashgointerface30A.tapcashgointerface30a_intk();
    }

    public String getMessage() {
        return this.f96g.tapcashgo35a_c("http://www.w3.org/2003/05/soap-envelope", "Text").tapcashgo34b_stringh(0);
    }

    @NonNull
    public String toString() {
        return "Code: " + this.f95f.tapcashgo35a_c("http://www.w3.org/2003/05/soap-envelope", "Value").tapcashgo34b_stringh(0) + ", Reason: " + this.f96g.tapcashgo35a_c("http://www.w3.org/2003/05/soap-envelope", "Text").tapcashgo34b_stringh(0);
    }
}

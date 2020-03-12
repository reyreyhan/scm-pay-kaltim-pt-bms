package com.cardnfc.lib.bninfc.tapcashgo;

//import android.support.v7.appcompat.p022a.C0503a.C0502j;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
//import p000a.p008d.p009a.tapcashgointerface30a;
//import p000a.p010c.p012b.tapcashgo34b;

public class tapcashgo28c extends IOException {
    public String f90a;
    public String f91b;
    @Nullable
    public String f92c;
    public tapcashgo34b f93d;
    public int f94e;

    public tapcashgo28c() {
        this.f94e =110;// C0502j.AppCompatTheme_ratingBarStyleSmall;
    }

    public tapcashgo28c(int i) {
        this.f94e = i;
    }

    public void tapcashgo28c_voida(@NonNull tapcashgointerface30a tapcashgointerface30A) {
        tapcashgointerface30A.tapcashgointerface30a_voida(2, "http://schemas.xmlsoap.org/soap/envelope/", "Fault");
        while (tapcashgointerface30A.tapcashgointerface30a_intk() == 2) {
            String f = tapcashgointerface30A.tapcashgointerface30a_strngf();
            if (f.equals("detail")) {
                this.f93d = new tapcashgo34b();
                this.f93d.tapcashgo34b_voida(tapcashgointerface30A);
                if (tapcashgointerface30A.tapcashgointerface30a_stringe().equals("http://schemas.xmlsoap.org/soap/envelope/") && tapcashgointerface30A.tapcashgointerface30a_strngf().equals("Fault")) {
                    break;
                }
            }
            if (f.equals("faultcode")) {
                this.f90a = tapcashgointerface30A.tapcashgointerface30a_stringl();
            } else if (f.equals("faultstring")) {
                this.f91b = tapcashgointerface30A.tapcashgointerface30a_stringl();
            } else if (f.equals("faultactor")) {
                this.f92c = tapcashgointerface30A.tapcashgointerface30a_stringl();
            } else {
                throw new RuntimeException("unexpected tag:" + f);
            }
            tapcashgointerface30A.tapcashgointerface30a_voida(3, null, f);
        }
        tapcashgointerface30A.tapcashgointerface30a_voida(3, "http://schemas.xmlsoap.org/soap/envelope/", "Fault");
        tapcashgointerface30A.tapcashgointerface30a_intk();
    }

    public String getMessage() {
        return this.f91b;
    }

    @NonNull
    public String toString() {
        return "SoapFault - faultcode: '" + this.f90a + "' faultstring: '" + this.f91b + "' faultactor: '" + this.f92c + "' detail: " + this.f93d;
    }
}

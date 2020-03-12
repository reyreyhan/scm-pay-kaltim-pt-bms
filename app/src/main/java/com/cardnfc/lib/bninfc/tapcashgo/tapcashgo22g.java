package com.cardnfc.lib.bninfc.tapcashgo;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.util.HashMap;
import java.util.List;
//import p000a.p005b.tapcashgo17b;
//import p000a.p008d.p009a.tapcashgointerface30a;
//import p000a.p008d.p009a.tapcashgointerface32c;
//import p000a.p010c.p011a.tapcashgo31a;
//import p000a.p010c.p011a.tapcashgo33b;

public abstract class tapcashgo22g {
    @NonNull
    private String f75a = "";
    private int f76b = 262144;
    @NonNull
    private HashMap f77c = new HashMap();
    protected Proxy f78d;
    protected String f79e;
    protected int f80f = 20000;
    public boolean f81g;
    public String f82h;
    public String f83i;

    public abstract List tapcashgo22g_lista(String str, tapcashgo17b tapcashgo17B, List list);

    public tapcashgo22g(String str, int i) {
        this.f79e = str;
        this.f80f = i;
    }

    protected void tapcashgo22g_voida(@NonNull tapcashgo17b tapcashgo17B, @NonNull InputStream inputStream) {
        tapcashgointerface30a c0031a = (tapcashgointerface30a) new tapcashgo31a();
        c0031a.tapcashgointerface30a_voida("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
        c0031a.tapcashgointerface30a_voida(inputStream, null);
        tapcashgo17B.tapcashgo17b_voida(c0031a);
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    protected byte[] m108a(tapcashgo17b c0017b, String str) {
//        OutputStream byteArrayOutputStream = new ByteArrayOutputStream(this.f76b);
//        byteArrayOutputStream.write(this.f75a.getBytes());
//        tapcashgointerface32c c0033b = new tapcashgo33b();
//        c0033b.tapcashgointerface32c_voida(byteArrayOutputStream, str);
//        for (String str2 : this.f77c.keySet()) {
//            c0033b.tapcashgointerface32c_voida(str2, (String) this.f77c.get(str2));
//        }
//        c0017b.tapcashgo17b_voida(c0033b);
//        c0033b.tapcashgointerface32c_voidb();
//        byteArrayOutputStream.write(13);
//        byteArrayOutputStream.write(10);
//        byteArrayOutputStream.flush();
//        return byteArrayOutputStream.toByteArray();
//    }

    public void tapcashgo22g_voida(String str, tapcashgo17b tapcashgo17B) {
        tapcashgo22g_lista(str, tapcashgo17B, null);
    }
}

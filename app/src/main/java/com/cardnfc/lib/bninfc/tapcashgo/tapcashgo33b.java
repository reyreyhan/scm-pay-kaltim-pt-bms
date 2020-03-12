package com.cardnfc.lib.bninfc.tapcashgo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
//import p000a.p008d.p009a.tapcashgointerface32c;

public class tapcashgo33b implements tapcashgointerface32c {
    private Writer writer;
    private boolean b;
    private int c;
    private int d;
    @NonNull
    private String[] e = new String[12];
    @NonNull
    private int[] f = new int[4];
    @NonNull
    private String[] arrg = new String[8];
    private boolean[] arrh = new boolean[4];
    private boolean i;
    @Nullable
    private String j;

    private final void m231a(boolean z) throws IOException {
        if (this.b) {
            boolean[] obj;
            this.d++;
            this.b = false;
            if (this.arrh.length <= this.d) {
                obj = new boolean[(this.d + 4)];
                System.arraycopy(this.arrh, 0, obj, 0, this.d);
                this.arrh = obj;
            }
            this.arrh[this.d] = this.arrh[this.d - 1];
            int i = this.f[this.d - 1];
            while (i < this.f[this.d]) {
                this.writer.write(32);
                this.writer.write("xmlns");
                if (!"".equals(this.arrg[i * 2])) {
                    this.writer.write(58);
                    this.writer.write(this.arrg[i * 2]);
                } else if ("".equals(tapcashgo33b_stringc()) && !"".equals(this.arrg[(i * 2) + 1])) {
                    throw new IllegalStateException("Cannot set default namespace for elements in no namespace");
                }
                this.writer.write("=\"");
                tapcashgo33b_voida(this.arrg[(i * 2) + 1], 34);
                this.writer.write(34);
                i++;
            }
            if (this.f.length <= this.d + 1) {
               int[] obji = new int[(this.d + 8)];
                System.arraycopy(this.f, 0, obji, 0, this.d + 1);
                this.f = obji;
            }
            this.f[this.d + 1] = this.f[this.d];
            this.writer.write(z ? " />" : ">");
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void tapcashgo33b_voida(String r6, int r7) {

        throw new UnsupportedOperationException("Method not decompiled: a.c.a.c.a(java.lang.String, int):void");
    }

    public void tapcashgointerface32c_voida(String str) throws IOException {
        this.writer.write("<!DOCTYPE");
        this.writer.write(str);
        this.writer.write(">");
    }

    public void tapcashgointerface32c_voida() throws IOException {
        while (this.d > 0) {
            tapcashgointerface32c_c(this.e[(this.d * 3) - 3], this.e[(this.d * 3) - 1]);
        }
        tapcashgointerface32c_voidb();
    }

    public void tapcashgointerface32c_voidb(String str) throws IOException {
        m231a(false);
        this.writer.write(38);
        this.writer.write(str);
        this.writer.write(59);
    }

    @Nullable
    public String tapcashgointerface32c_stringa(String str, boolean z) throws IOException {
        return m229a(str, false, z);
    }

    @Nullable
    private final String m229a(String str, boolean z, boolean z2) throws IOException {
        String str2;
        int i = (this.f[this.d + 1] * 2) - 2;
        while (i >= 0) {
            if (this.arrg[i + 1].equals(str) && (z || !this.arrg[i].equals(""))) {
                String str3 = this.arrg[i];
                for (int i2 = i + 2; i2 < this.f[this.d + 1] * 2; i2++) {
                    if (this.arrg[i2].equals(str3)) {
                        str2 = null;
                        break;
                    }
                }
                str2 = str3;
                if (str2 != null) {
                    return str2;
                }
            }
            i -= 2;
        }
        if (!z2) {
            return null;
        }
        if ("".equals(str)) {
            str2 = "";
        } else {
            do {
                StringBuilder append = new StringBuilder().append("n");
                int i3 = this.c;
                this.c = i3 + 1;
                str2 = append.append(i3).toString();
                for (i3 = (this.f[this.d + 1] * 2) - 2; i3 >= 0; i3 -= 2) {
                    if (str2.equals(this.arrg[i3])) {
                        str2 = null;
                        continue;
                    }
                }
            } while (str2 == null);
        }
        boolean z3 = this.b;
        this.b = false;
        tapcashgointerface32c_voida(str2, str);
        this.b = z3;
        return str2;
    }

    public void tapcashgointerface32c_voidc(String str) throws IOException {
        tapcashgointerface32c_d(str);
    }

    public void tapcashgointerface32c_voida(@Nullable String str, @Nullable String str2) throws IOException {
        m231a(false);
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "";
        }
        if (!str.equals(m229a(str2, true, false))) {
            int[] iArr = this.f;
            int i = this.d + 1;
            int i2 = iArr[i];
            iArr[i] = i2 + 1;
            int i3 = i2 << 1;
            if (this.arrg.length < i3 + 1) {
                String[] obj = new String[(this.arrg.length + 16)];
                System.arraycopy(this.arrg, 0, obj, 0, i3);
                this.arrg = obj;
            }
            i2 = i3 + 1;
            this.arrg[i3] = str;
            this.arrg[i2] = str2;
        }
    }

    public void tapcashgo33b_voida(Writer writer) {
        this.writer = writer;
        this.f[0] = 2;
        this.f[1] = 2;
        this.arrg[0] = "";
        this.arrg[1] = "";
        this.arrg[2] = "xml";
        this.arrg[3] = "http://www.w3.org/XML/1998/namespace";
        this.b = false;
        this.c = 0;
        this.d = 0;
        this.i = false;
    }

    public void tapcashgointerface32c_voida(@Nullable OutputStream outputStream, @Nullable String str) throws UnsupportedEncodingException {
        if (outputStream == null) {
            throw new IllegalArgumentException();
        }
        tapcashgo33b_voida(str == null ? new OutputStreamWriter(outputStream) : new OutputStreamWriter(outputStream, str));
        this.j = str;
        if (str != null && str.toLowerCase().startsWith("utf")) {
            this.i = true;
        }
    }

    public void tapcashgointerface32c_voida(@Nullable String str, @Nullable Boolean bool) throws IOException {
        this.writer.write("<?xml version='1.0' ");
        if (str != null) {
            this.j = str;
            if (str.toLowerCase().startsWith("utf")) {
                this.i = true;
            }
        }
        if (this.j != null) {
            this.writer.write("encoding='");
            this.writer.write(this.j);
            this.writer.write("' ");
        }
        if (bool != null) {
            this.writer.write("standalone='");
            this.writer.write(bool.booleanValue() ? "yes" : "no");
            this.writer.write("' ");
        }
        this.writer.write("?>");
    }

    @NonNull
    public tapcashgointerface32c tapcashgointerface32c_b(@Nullable String str, String str2) throws IOException {
        m231a(false);
        if (this.arrh[this.d]) {
            this.writer.write("\r\n");
            for (int i = 0; i < this.d; i++) {
                this.writer.write("  ");
            }
        }
        int i2 = this.d * 3;
        if (this.e.length < i2 + 3) {
            String[] obj = new String[(this.e.length + 12)];
            System.arraycopy(this.e, 0, obj, 0, i2);
            this.e = obj;
        }
        String a = str == null ? "" : m229a(str, true, true);
        if ("".equals(str)) {
            int i3 = this.f[this.d];
            while (i3 < this.f[this.d + 1]) {
                if (!"".equals(this.arrg[i3 * 2]) || "".equals(this.arrg[(i3 * 2) + 1])) {
                    i3++;
                } else {
                    throw new IllegalStateException("Cannot set default namespace for elements in no namespace");
                }
            }
        }
        int i4 = i2 + 1;
        this.e[i2] = str;
        i2 = i4 + 1;
        this.e[i4] = a;
        this.e[i2] = str2;
        this.writer.write(60);
        if (!"".equals(a)) {
            this.writer.write(a);
            this.writer.write(58);
        }
        this.writer.write(str2);
        this.b = true;
        return this;
    }

    @NonNull
    public tapcashgointerface32c tapcashgointerface32c_a(@Nullable String str, String str2, @NonNull String str3) throws IOException {
        if (this.b) {
            String str4;
            if (str == null) {
                str = "";
            }
            if ("".equals(str)) {
                str4 = "";
            } else {
                str4 = m229a(str, false, true);
            }
            this.writer.write(32);
            if (!"".equals(str4)) {
                this.writer.write(str4);
                this.writer.write(58);
            }
            this.writer.write(str2);
            this.writer.write(61);
            int i = str3.indexOf(34) == -1 ? 34 : 39;
            this.writer.write(i);
            tapcashgo33b_voida(str3, i);
            this.writer.write(i);
            return this;
        }
        throw new IllegalStateException("illegal position for attribute");
    }

    public void tapcashgointerface32c_voidb() throws IOException {
        m231a(false);
        this.writer.flush();
    }

    @NonNull
    public tapcashgointerface32c tapcashgointerface32c_c(@Nullable String str, String str2) throws IOException {
        if (!this.b) {
            this.d--;
        }
        if ((str != null || this.e[this.d * 3] == null) && ((str == null || str.equals(this.e[this.d * 3])) && this.e[(this.d * 3) + 2].equals(str2))) {
            if (this.b) {
                m231a(true);
                this.d--;
            } else {
                if (this.arrh[this.d + 1]) {
                    this.writer.write("\r\n");
                    for (int i = 0; i < this.d; i++) {
                        this.writer.write("  ");
                    }
                }
                this.writer.write("</");
                String str3 = this.e[(this.d * 3) + 1];
                if (!"".equals(str3)) {
                    this.writer.write(str3);
                    this.writer.write(58);
                }
                this.writer.write(str2);
                this.writer.write(62);
            }
            this.f[this.d + 1] = this.f[this.d];
            return this;
        }
        throw new IllegalArgumentException("</{" + str + "}" + str2 + "> does not match start");
    }

    @Nullable
    public String tapcashgo33b_stringc() {
        return tapcashgo33b_intd() == 0 ? null : this.e[(tapcashgo33b_intd() * 3) - 3];
    }

    public int tapcashgo33b_intd() {
        return this.b ? this.d + 1 : this.d;
    }

    @NonNull
    public tapcashgointerface32c tapcashgointerface32c_d(String str) throws IOException {
        m231a(false);
        this.arrh[this.d] = false;
        tapcashgo33b_voida(str, -1);
        return this;
    }

    public void tapcashgointerface32c_voide(String str) throws IOException {
        m231a(false);
        this.writer.write("<![CDATA[");
        this.writer.write(str);
        this.writer.write("]]>");
    }

    public void tapcashgointerface32c_voidf(String str) throws IOException {
        m231a(false);
        this.writer.write("<!--");
        this.writer.write(str);
        this.writer.write("-->");
    }

    public void tapcashgointerface32c_voidg(String str) throws IOException {
        m231a(false);
        this.writer.write("<?");
        this.writer.write(str);
        this.writer.write("?>");
    }
}

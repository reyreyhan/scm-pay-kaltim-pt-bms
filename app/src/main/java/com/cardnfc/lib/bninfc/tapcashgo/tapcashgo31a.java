package com.cardnfc.lib.bninfc.tapcashgo;

//import android.support.v7.p022a.C0503a.C0502j;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.Reader;
import java.util.Hashtable;
//import p000a.p008d.p009a.tapcashgointerface30a;
//import p000a.p008d.p009a.C0036b;

public class tapcashgo31a {//implements tapcashgointerface30a {
    private int A;
    @NonNull
    private String[] arrB = new String[16];
    private String C;
    @NonNull
    private int[] arrD = new int[2];
    private int E;
    private boolean F;
    private boolean G;
    private boolean H;
    private Object b;
    @Nullable
    private String c;
    @Nullable
    private Boolean d;
    private boolean e;
    private boolean f;
    private Hashtable hashtable;
    private int h;
    @NonNull
    private String[] arri = new String[16];
    @NonNull
    private String[] arrj = new String[8];
    @NonNull
    private int[] arrk = new int[4];
    @Nullable
    private Reader reader;
    @Nullable
    private String m;
    private char[] n;
    private int o;
    private int p;
    private int q;
    private int r;
    @NonNull
    private char[] s = new char[128];
    private int t;
    private int u;
    private boolean v;
    @Nullable
    private String w;
    private String x;
    @Nullable
    private String y;
    private boolean z;

    public tapcashgo31a() {
        int i = 128;
        if (Runtime.getRuntime().freeMemory() >= 1048576) {
            i = 8192;
        }
        this.n = new char[i];
    }

    private final boolean m174a(@NonNull String str, boolean z, String str2) {
        if (!str.startsWith("http://xmlpull.org/v1/doc/")) {
            return false;
        }
        if (z) {
            return str.substring(42).equals(str2);
        }
        return str.substring(40).equals(str2);
    }

//    private final boolean m183m() {
//        Object substring;
//        String str;
//        int i = 0;
//        boolean z = false;
//        while (i < (this.A << 2)) {
//             str = this.arrB[i + 2];
//            int indexOf = str.indexOf(58);
//            if (indexOf != -1) {
//                substring = str.substring(0, indexOf);
//                str = str.substring(indexOf + 1);
//            } else if (str.equals("xmlns")) {
//                String str2 = str;
//                str = null;
//                substring = str2;
//            } else {
//                i += 4;
//            }
//            if (str.equals("xmlns")) {
//                int[] iArr = this.arrk;
//                indexOf = this.h;
//                int i2 = iArr[indexOf];
//                iArr[indexOf] = i2 + 1;
//                int i3 = i2 << 1;
//                this.arrj = m175a(this.arrj, i3 + 2);
//                this.arrj[i3] = str;
//                this.arrj[i3 + 1] = this.arrB[i + 3];
//                if (str != null && this.arrB[i + 3].equals("")) {
//                    m176b("illegal empty namespace");
//                }
//                Object obj = this.arrB;
//                int i4 = i + 4;
//                Object obj2 = this.arrB;
//                i2 = this.A - 1;
//                this.A = i2;
//                System.arraycopy(obj, i4, obj2, i, (i2 << 2) - i);
//                i -= 4;
//            } else {
//                z = true;
//            }
//            i += 4;
//        }
//        if (z) {
//            i = (this.A << 2) - 4;
//            while (i >= 0) {
//                str = this.arrB[i + 2];
//                i = str.indexOf(58);
//                if (i != 0 || this.a) {
//                    if (i != -1) {
//                        String substring2 = str.substring(0, i);
//                        substring = str.substring(i + 1);
//                        str = tapcashgointerface30a_stringa(substring2);
//                        if (str != null || this.a) {
//                            this.arrB[i] = str;
//                            this.arrB[i + 1] = substring2;
//                            this.arrB[i + 2] = str;
//                        } else {
//                            throw new RuntimeException("Undefined Prefix: " + substring2 + " in " + this);
//                        }
//                    }
//                    i -= 4;
//                } else {
//                    throw new RuntimeException("illegal attribute airLineName: " + str + " at " + this);
//                }
//            }
//        }
//        i = this.y.indexOf(58);
//        if (i == 0) {
//            m176b("illegal tag airLineName: " + this.y);
//        }
//        if (i != -1) {
//            this.x = this.y.substring(0, i);
//            this.y = this.y.substring(i + 1);
//        }
//        this.w = tapcashgointerface30a_stringa(this.x);
//        if (this.w == null) {
//            if (this.x != null) {
//                m176b("undefined prefix: " + this.x);
//            }
//            this.w = "";
//        }
//        return z;
//    }

//    private final String[] m175a(String[] strArr, int i) {
//        if (strArr.length >= i) {
//            return strArr;
//        }
//        String[] obj = new String[(i + 16)];
//        System.arraycopy(strArr, 0, obj, 0, strArr.length);
//        return obj;
//    }

//    private final void m176b(String str) {
//        if (!this.a) {
//            m178c(str);
//        } else if (this.C == null) {
//            this.C = "ERR: " + str;
//        }
//    }

//    private final void m178c(String str) {
//        if (str.length() >= 100) {
//            str = str.substring(0, 100) + "\n";
//        }
//        throw new C0036b(str, this, null);
//    }

//    private final void m184n() {
//        boolean z = false;
//        if (this.reader == null) {
//            m178c("No Input specified");
//        }
//        if (this.u == 3) {
//            this.h--;
//        }
//        do {
//            this.A = -1;
//            if (!this.z) {
//                if (this.C == null) {
//                    this.x = null;
//                    this.y = null;
//                    this.w = null;
//                    this.u = m186p();
//                    switch (this.u) {
//                        case 1:
//                            return;
//                        case 2:
//                            m179c(false);
//                            return;
//                        case 3:
//                            m185o();
//                            return;
//                        case 4:
//                            if (!this.H) {
//                                z = true;
//                            }
//                            m173a(60, z);
//                            if (this.h == 0 && this.v) {
//                                this.u = 7;
//                                return;
//                            }
//                            return;
//                        case 6:
//                            m187q();
//                            return;
//                        default:
//                            this.u = m171a(this.H);
//                            break;
//                    }
//                }
//                int i;
//                while (i < this.C.length()) {
//                    m181g(this.C.charAt(i));
//                    i++;
//                }
//                this.C = null;
//                this.u = 9;
//                return;
//            }
//            this.z = false;
//            this.u = 3;
//            return;
//        } while (this.u == 998);
//    }

//    private final int m171a(boolean z) {
//        String str;
//        boolean z2;
//        String str2 = "";
//        m188r();
//        int r = m188r();
//        if (r == 63) {
//            if ((m182h(0) == 120 || m182h(0) == 88) && (m182h(1) == C0502j.AppCompatTheme_ratingBarStyleIndicator || m182h(1) == 77)) {
//                if (z) {
//                    m181g(m182h(0));
//                    m181g(m182h(1));
//                }
//                m188r();
//                m188r();
//                if ((m182h(0) == C0502j.AppCompatTheme_ratingBarStyle || m182h(0) == 76) && m182h(1) <= 32) {
//                    if (this.q != 1 || this.r > 4) {
//                        m176b("PI must not start with xml");
//                    }
//                    m179c(true);
//                    if (this.A < 1 || !"version".equals(this.arrB[2])) {
//                        m176b("version expected");
//                    }
//                    this.c = this.arrB[3];
//                    if (1 >= this.A || !"encoding".equals(this.arrB[6])) {
//                        r = 1;
//                    } else {
//                        this.m = this.arrB[7];
//                        r = 2;
//                    }
//                    if (r < this.A && "standalone".equals(this.arrB[(r * 4) + 2])) {
//                        String str3 = this.arrB[(r * 4) + 3];
//                        if ("yes".equals(str3)) {
//                            this.d = new Boolean(true);
//                        } else if ("no".equals(str3)) {
//                            this.d = new Boolean(false);
//                        } else {
//                            m176b("illegal standalone value: " + str3);
//                        }
//                        r++;
//                    }
//                    if (r != this.A) {
//                        m176b("illegal xmldecl");
//                    }
//                    this.v = true;
//                    this.t = 0;
//                    return 998;
//                }
//            }
//            r = 8;
//            str = str2;
//            z2 = true;
//        } else if (r != 33) {
//            m176b("illegal: <" + r);
//            return 9;
//        } else if (m182h(0) == 45) {
//            z2 = true;
//            str = "--";
//            r = 9;
//        } else if (m182h(0) == 91) {
//            r = 5;
//            str = "[CDATA[";
//            z2 = true;
//            z = true;
//        } else {
//            r = 10;
//            str = "DOCTYPE";
//            z2 = true;
//        }
//        for (int i = 0; i < str.length(); i++) {
//            m172a(str.charAt(i));
//        }
//        if (r == 10) {
//            m177b(z);
//        } else {
//            boolean z3 = false;
//            while (true) {
//                boolean r2 = m188r();
//                if (r2) {
//                    m176b("Unexpected EOF");
//                    return 9;
//                }
//                if (z) {
//                    m181g(r2);
//                }
//                if ((z2 || r2 == z2) && m182h(0) == z2 && m182h(1) == 62) {
//                    break;
//                }
//                z3 = r2;
//            }
//            if (z2 && r6 && !this.a) {
//                m176b("illegal comment delimiter: --->");
//            }
//            m188r();
//            m188r();
//            if (z && !z2) {
//                this.t--;
//            }
//        }
//        return r;
//    }

//    private final void m177b(boolean z) {
//        Object obj = null;
//        int i = 1;
//        while (true) {
//            int r = m188r();
//            switch (r) {
//                case -1:
//                    m176b("Unexpected EOF");
//                    return;
//                case C0502j.AppCompatTheme_actionModePopupWindowStyle /*39*/:
//                    if (obj != null) {
//                        obj = null;
//                        break;
//                    } else {
//                        int i2 = 1;
//                        break;
//                    }
//                case C0502j.AppCompatTheme_toolbarNavigationButtonStyle /*60*/:
//                    if (obj == null) {
//                        i++;
//                        break;
//                    }
//                    break;
//                case C0502j.AppCompatTheme_popupWindowStyle /*62*/:
//                    if (obj == null) {
//                        i--;
//                        if (i == 0) {
//                            return;
//                        }
//                    }
//                    break;
//            }
//            if (z) {
//                m181g(r);
//            }
//        }
//    }
//
//    private final void m185o() {
//        m188r();
//        m188r();
//        this.y = m189s();
//        m190t();
//        m172a('>');
//        int i = (this.h - 1) << 2;
//        if (this.h == 0) {
//            m176b("element stack empty");
//            this.u = 9;
//        } else if (!this.a) {
//            if (!this.y.equals(this.arri[i + 3])) {
//                m176b("expected: /" + this.arri[i + 3] + " read: " + this.y);
//            }
//            this.w = this.arri[i];
//            this.x = this.arri[i + 1];
//            this.y = this.arri[i + 2];
//        }
//    }
//
//    private final int m186p() {
//        switch (m182h(0)) {
//            case -1:
//                return 1;
//            case C0502j.AppCompatTheme_actionModeWebSearchDrawable /*38*/:
//                return 6;
//            case C0502j.AppCompatTheme_toolbarNavigationButtonStyle /*60*/:
//                switch (m182h(1)) {
//                    case C0502j.AppCompatTheme_actionModeCopyDrawable /*33*/:
//                    case C0502j.AppCompatTheme_editTextColor /*63*/:
//                        return 999;
//                    case C0502j.AppCompatTheme_dropdownListPreferredItemHeight /*47*/:
//                        return 3;
//                    default:
//                        return 2;
//                }
//            default:
//                return 4;
//        }
//    }

    @NonNull
    private final String m180f(int i) {
        return new String(this.s, i, this.t - i);
    }

//    private final void m181g(int i) {
//        this.v = (i <= 32 ? 1 : 0) & this.v;
//        if (this.t == this.s.length) {
//            Object obj = new char[(((this.t * 4) / 3) + 4)];
//            System.arraycopy(this.s, 0, obj, 0, this.t);
//            this.s = obj;
//        }
//        char[] cArr = this.s;
//        int i2 = this.t;
//        this.t = i2 + 1;
//        cArr[i2] = (char) i;
//    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void m179c(boolean r11) {
        /*
        r10 = this;
        r9 = 61;
        r1 = 32;
        r8 = 1;
        r7 = 62;
        r6 = 0;
        if (r11 != 0) goto L_0x000d;
    L_0x000a:
        r10.m188r();
    L_0x000d:
        r0 = r10.m189s();
        r10.y = r0;
        r10.A = r6;
    L_0x0015:
        r10.m190t();
        r0 = r10.m182h(r6);
        if (r11 == 0) goto L_0x0029;
    L_0x001e:
        r2 = 63;
        if (r0 != r2) goto L_0x009d;
    L_0x0022:
        r10.m188r();
        r10.m172a(r7);
    L_0x0028:
        return;
    L_0x0029:
        r2 = 47;
        if (r0 != r2) goto L_0x0095;
    L_0x002d:
        r10.z = r8;
        r10.m188r();
        r10.m190t();
        r10.m172a(r7);
    L_0x0038:
        r0 = r10.h;
        r1 = r0 + 1;
        r10.h = r1;
        r0 = r0 << 2;
        r1 = r10.arri;
        r2 = r0 + 4;
        r1 = r10.m175a(r1, r2);
        r10.arri = r1;
        r1 = r10.arri;
        r2 = r0 + 3;
        r3 = r10.y;
        r1[r2] = r3;
        r1 = r10.h;
        r2 = r10.arrk;
        r2 = r2.length;
        if (r1 < r2) goto L_0x0069;
    L_0x0059:
        r1 = r10.h;
        r1 = r1 + 4;
        r1 = new int[r1];
        r2 = r10.arrk;
        r3 = r10.arrk;
        r3 = r3.length;
        java.lang.System.arraycopy(r2, r6, r1, r6, r3);
        r10.arrk = r1;
    L_0x0069:
        r1 = r10.arrk;
        r2 = r10.h;
        r3 = r10.arrk;
        r4 = r10.h;
        r4 = r4 + -1;
        r3 = r3[r4];
        r1[r2] = r3;
        r1 = r10.e;
        if (r1 == 0) goto L_0x013c;
    L_0x007b:
        r10.m183m();
    L_0x007e:
        r1 = r10.arri;
        r2 = r10.w;
        r1[r0] = r2;
        r1 = r10.arri;
        r2 = r0 + 1;
        r3 = r10.x;
        r1[r2] = r3;
        r1 = r10.arri;
        r0 = r0 + 2;
        r2 = r10.y;
        r1[r0] = r2;
        goto L_0x0028;
    L_0x0095:
        if (r0 != r7) goto L_0x009d;
    L_0x0097:
        if (r11 != 0) goto L_0x009d;
    L_0x0099:
        r10.m188r();
        goto L_0x0038;
    L_0x009d:
        r2 = -1;
        if (r0 != r2) goto L_0x00a6;
    L_0x00a0:
        r0 = "Unexpected EOF";
        r10.m176b(r0);
        goto L_0x0028;
    L_0x00a6:
        r0 = r10.m189s();
        r2 = r0.length();
        if (r2 != 0) goto L_0x00b6;
    L_0x00b0:
        r0 = "attr airLineName expected";
        r10.m176b(r0);
        goto L_0x0038;
    L_0x00b6:
        r2 = r10.A;
        r3 = r2 + 1;
        r10.A = r3;
        r2 = r2 << 2;
        r3 = r10.arrB;
        r4 = r2 + 4;
        r3 = r10.m175a(r3, r4);
        r10.arrB = r3;
        r3 = r10.arrB;
        r4 = r2 + 1;
        r5 = "";
        r3[r2] = r5;
        r2 = r10.arrB;
        r3 = r4 + 1;
        r5 = 0;
        r2[r4] = r5;
        r2 = r10.arrB;
        r4 = r3 + 1;
        r2[r3] = r0;
        r10.m190t();
        r2 = r10.m182h(r6);
        if (r2 == r9) goto L_0x0106;
    L_0x00e6:
        r2 = r10.a;
        if (r2 != 0) goto L_0x0100;
    L_0x00ea:
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "Attr.value missing a. ";
        r2 = r2.append(r3);
        r2 = r2.append(r0);
        r2 = r2.toString();
        r10.m176b(r2);
    L_0x0100:
        r2 = r10.arrB;
        r2[r4] = r0;
        goto L_0x0015;
    L_0x0106:
        r10.m172a(r9);
        r10.m190t();
        r0 = r10.m182h(r6);
        r2 = 39;
        if (r0 == r2) goto L_0x0138;
    L_0x0114:
        r2 = 34;
        if (r0 == r2) goto L_0x0138;
    L_0x0118:
        r0 = r10.a;
        if (r0 != 0) goto L_0x0121;
    L_0x011c:
        r0 = "attr value delimiter missing!";
        r10.m176b(r0);
    L_0x0121:
        r0 = r1;
    L_0x0122:
        r2 = r10.t;
        r10.m173a(r0, r8);
        r3 = r10.arrB;
        r5 = r10.m180f(r2);
        r3[r4] = r5;
        r10.t = r2;
        if (r0 == r1) goto L_0x0015;
    L_0x0133:
        r10.m188r();
        goto L_0x0015;
    L_0x0138:
        r10.m188r();
        goto L_0x0122;
    L_0x013c:
        r1 = "";
        r10.w = r1;
        goto L_0x007e;
        */
        throw new UnsupportedOperationException("Method not decompiled: a.c.a.a.c(boolean):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void m187q() {
        /*
        r6 = this;
        r5 = 35;
        r1 = 1;
        r2 = 0;
        r0 = r6.m188r();
        r6.m181g(r0);
        r0 = r6.t;
    L_0x000d:
        r3 = r6.m182h(r2);
        r4 = 59;
        if (r3 != r4) goto L_0x0048;
    L_0x0015:
        r6.m188r();
        r3 = r6.m180f(r0);
        r0 = r0 + -1;
        r6.t = r0;
        r0 = r6.H;
        if (r0 == 0) goto L_0x002b;
    L_0x0024:
        r0 = r6.u;
        r4 = 6;
        if (r0 != r4) goto L_0x002b;
    L_0x0029:
        r6.y = r3;
    L_0x002b:
        r0 = r3.charAt(r2);
        if (r0 != r5) goto L_0x00a8;
    L_0x0031:
        r0 = r3.charAt(r1);
        r2 = 120; // 0x78 float:1.68E-43 double:5.93E-322;
        if (r0 != r2) goto L_0x009f;
    L_0x0039:
        r0 = 2;
        r0 = r3.substring(r0);
        r1 = 16;
        r0 = java.lang.Integer.parseInt(r0, r1);
    L_0x0044:
        r6.m181g(r0);
    L_0x0047:
        return;
    L_0x0048:
        r4 = 128; // 0x80 float:1.794E-43 double:6.32E-322;
        if (r3 >= r4) goto L_0x0096;
    L_0x004c:
        r4 = 48;
        if (r3 < r4) goto L_0x0054;
    L_0x0050:
        r4 = 57;
        if (r3 <= r4) goto L_0x0096;
    L_0x0054:
        r4 = 97;
        if (r3 < r4) goto L_0x005c;
    L_0x0058:
        r4 = 122; // 0x7a float:1.71E-43 double:6.03E-322;
        if (r3 <= r4) goto L_0x0096;
    L_0x005c:
        r4 = 65;
        if (r3 < r4) goto L_0x0064;
    L_0x0060:
        r4 = 90;
        if (r3 <= r4) goto L_0x0096;
    L_0x0064:
        r4 = 95;
        if (r3 == r4) goto L_0x0096;
    L_0x0068:
        r4 = 45;
        if (r3 == r4) goto L_0x0096;
    L_0x006c:
        if (r3 == r5) goto L_0x0096;
    L_0x006e:
        r1 = r6.a;
        if (r1 != 0) goto L_0x0077;
    L_0x0072:
        r1 = "unterminated entity ref";
        r6.m176b(r1);
    L_0x0077:
        r1 = java.lang.System.out;
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "broken entitiy: ";
        r2 = r2.append(r3);
        r0 = r0 + -1;
        r0 = r6.m180f(r0);
        r0 = r2.append(r0);
        r0 = r0.toString();
        r1.println(r0);
        goto L_0x0047;
    L_0x0096:
        r3 = r6.m188r();
        r6.m181g(r3);
        goto L_0x000d;
    L_0x009f:
        r0 = r3.substring(r1);
        r0 = java.lang.Integer.parseInt(r0);
        goto L_0x0044;
    L_0x00a8:
        r0 = r6.hashtable;
        r0 = r0.get(r3);
        r0 = (java.lang.String) r0;
        if (r0 != 0) goto L_0x00da;
    L_0x00b2:
        r6.G = r1;
        r1 = r6.G;
        if (r1 == 0) goto L_0x00dc;
    L_0x00b8:
        r0 = r6.H;
        if (r0 != 0) goto L_0x0047;
    L_0x00bc:
        r0 = new java.lang.StringBuilder;
        r0.<init>();
        r1 = "unresolved: &";
        r0 = r0.append(r1);
        r0 = r0.append(r3);
        r1 = ";";
        r0 = r0.append(r1);
        r0 = r0.toString();
        r6.m176b(r0);
        goto L_0x0047;
    L_0x00da:
        r1 = r2;
        goto L_0x00b2;
    L_0x00dc:
        r1 = r0.length();
        if (r2 >= r1) goto L_0x0047;
    L_0x00e2:
        r1 = r0.charAt(r2);
        r6.m181g(r1);
        r2 = r2 + 1;
        goto L_0x00dc;
        */
        throw new UnsupportedOperationException("Method not decompiled: a.c.a.a.q():void");
    }

//    private final void m173a(int i, boolean z) {
//        int h = m182h(0);
//        int i2 = 0;
//        while (h != -1 && h != i) {
//            if (i != 32 || (h > 32 && h != 62)) {
//                if (h == 38) {
//                    if (z) {
//                        m187q();
//                    } else {
//                        return;
//                    }
//                } else if (h == 10 && this.u == 2) {
//                    m188r();
//                    m181g(32);
//                } else {
//                    m181g(m188r());
//                }
//                if (h == 62 && i2 >= 2 && i != 93) {
//                    m176b("Illegal: ]]>");
//                }
//                if (h == 93) {
//                    i2++;
//                } else {
//                    i2 = 0;
//                }
//                h = m182h(0);
//            } else {
//                return;
//            }
//        }
//    }
//
//    private final void m172a(char c) {
//        char r = m188r();
//        if (r != c) {
//            m176b("expected: '" + c + "' actual: '" + ((char) r) + "'");
//        }
//    }

    private final int m188r() throws IOException {
        int h;
        if (this.E == 0) {
            h = m182h(0);
        } else {
            h = this.arrD[0];
            this.arrD[0] = this.arrD[1];
        }
        this.E--;
        this.r++;
        if (h == 10) {
            this.q++;
            this.r = 1;
        }
        return h;
    }

    private final int m182h(int i) throws IOException {
        while (i >= this.E) {
            int read;
            if (this.n.length <= 1) {
                read = this.reader.read();
            } else if (this.o < this.p) {
                char[] cArr = this.n;
                int i2 = this.o;
                this.o = i2 + 1;
                read = cArr[i2];
            } else {
                this.p = this.reader.read(this.n, 0, this.n.length);
                if (this.p <= 0) {
                    read = -1;
                } else {
                    read = this.n[0];
                }
                this.o = 1;
            }
            int[] iArr;
            int i2;
            if (read == 13) {
                this.F = true;
                iArr = this.arrD;
                i2 = this.E;
                this.E = i2 + 1;
                iArr[i2] = 10;
            } else {
                if (read != 10) {
                    int[] iArr2 = this.arrD;
                    int i3 = this.E;
                    this.E = i3 + 1;
                    iArr2[i3] = read;
                } else if (!this.F) {
                    iArr = this.arrD;
                    i2 = this.E;
                    this.E = i2 + 1;
                    iArr[i2] = 10;
                }
                this.F = false;
            }
        }
        return this.arrD[i];
    }

//    private final String m189s() {
//        int i = this.t;
//        int h = m182h(0);
//        if ((h < 97 || h > 122) && !((h >= 65 && h <= 90) || h == 95 || h == 58 || h >= 192 || this.a)) {
//            m176b("airLineName expected");
//        }
//        while (true) {
//            m181g(m188r());
//            h = m182h(0);
//            if ((h < 97 || h > 122) && ((h < 65 || h > 90) && !((h >= 48 && h <= 57) || h == 95 || h == 45 || h == 58 || h == 46 || h >= 183))) {
//                String a = m180f(i);
//                this.t = i;
//                return a;
//            }
//        }
//    }
//
//    private final void m190t() {
//        while (true) {
//            int h = m182h(0);
//            if (h <= 32 && h != -1) {
//                m188r();
//            } else {
//                return;
//            }
//        }
//    }

    public void m196a(@Nullable Reader reader) {
        this.reader = reader;
        this.q = 1;
        this.r = 0;
        this.u = 0;
        this.y = null;
        this.w = null;
        this.z = false;
        this.A = -1;
        this.m = null;
        this.c = null;
        this.d = null;
        if (reader != null) {
            this.o = 0;
            this.p = 0;
            this.E = 0;
            this.h = 0;
            this.hashtable = new Hashtable();
            this.hashtable.put("amp", "&");
            this.hashtable.put("apos", "'");
            this.hashtable.put("gt", ">");
            this.hashtable.put("lt", "<");
            this.hashtable.put("quot", "\"");
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void mo35a(java.io.InputStream r7, String r8) {
        /*
        r6 = this;
        r5 = -1;
        r0 = 0;
        r6.o = r0;
        r6.p = r0;
        if (r7 != 0) goto L_0x000e;
    L_0x0008:
        r0 = new java.lang.IllegalArgumentException;
        r0.<init>();
        throw r0;
    L_0x000e:
        if (r8 != 0) goto L_0x015a;
    L_0x0010:
        r1 = r0;
    L_0x0011:
        r0 = r6.p;	 Catch:{ Exception -> 0x006d }
        r2 = 4;
        if (r0 >= r2) goto L_0x001c;
    L_0x0016:
        r2 = r7.read();	 Catch:{ Exception -> 0x006d }
        if (r2 != r5) goto L_0x0057;
    L_0x001c:
        r0 = r6.p;	 Catch:{ Exception -> 0x006d }
        r2 = 4;
        if (r0 != r2) goto L_0x015a;
    L_0x0021:
        switch(r1) {
            case -131072: goto L_0x008b;
            case 60: goto L_0x0091;
            case 65279: goto L_0x0067;
            case 3932223: goto L_0x00ab;
            case 1006632960: goto L_0x009e;
            case 1006649088: goto L_0x00bf;
            case 1010792557: goto L_0x00d4;
            default: goto L_0x0024;
        };	 Catch:{ Exception -> 0x006d }
    L_0x0024:
        r0 = r8;
    L_0x0025:
        r2 = -65536; // 0xffffffffffff0000 float:NaN double:NaN;
        r2 = r2 & r1;
        r3 = -16842752; // 0xfffffffffeff0000 float:-1.6947657E38 double:NaN;
        if (r2 != r3) goto L_0x0121;
    L_0x002c:
        r0 = "UTF-16BE";
        r1 = r6.n;	 Catch:{ Exception -> 0x006d }
        r2 = 0;
        r3 = r6.n;	 Catch:{ Exception -> 0x006d }
        r4 = 2;
        r3 = r3[r4];	 Catch:{ Exception -> 0x006d }
        r3 = r3 << 8;
        r4 = r6.n;	 Catch:{ Exception -> 0x006d }
        r5 = 3;
        r4 = r4[r5];	 Catch:{ Exception -> 0x006d }
        r3 = r3 | r4;
        r3 = (char) r3;	 Catch:{ Exception -> 0x006d }
        r1[r2] = r3;	 Catch:{ Exception -> 0x006d }
        r1 = 1;
        r6.p = r1;	 Catch:{ Exception -> 0x006d }
    L_0x0044:
        if (r0 != 0) goto L_0x0048;
    L_0x0046:
        r0 = "UTF-8";
    L_0x0048:
        r1 = r6.p;	 Catch:{ Exception -> 0x006d }
        r2 = new java.io.InputStreamReader;	 Catch:{ Exception -> 0x006d }
        r2.<init>(r7, r0);	 Catch:{ Exception -> 0x006d }
        r6.m196a(r2);	 Catch:{ Exception -> 0x006d }
        r6.m = r8;	 Catch:{ Exception -> 0x006d }
        r6.p = r1;	 Catch:{ Exception -> 0x006d }
        return;
    L_0x0057:
        r0 = r1 << 8;
        r0 = r0 | r2;
        r1 = r6.n;	 Catch:{ Exception -> 0x006d }
        r3 = r6.p;	 Catch:{ Exception -> 0x006d }
        r4 = r3 + 1;
        r6.p = r4;	 Catch:{ Exception -> 0x006d }
        r2 = (char) r2;	 Catch:{ Exception -> 0x006d }
        r1[r3] = r2;	 Catch:{ Exception -> 0x006d }
        r1 = r0;
        goto L_0x0011;
    L_0x0067:
        r0 = "UTF-32BE";
        r1 = 0;
        r6.p = r1;	 Catch:{ Exception -> 0x006d }
        goto L_0x0044;
    L_0x006d:
        r0 = move-exception;
        r1 = new a.d.a.c;
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "Invalid stream or encoding: ";
        r2 = r2.append(r3);
        r3 = r0.toString();
        r2 = r2.append(r3);
        r2 = r2.toString();
        r1.<init>(r2, r6, r0);
        throw r1;
    L_0x008b:
        r0 = "UTF-32LE";
        r1 = 0;
        r6.p = r1;	 Catch:{ Exception -> 0x006d }
        goto L_0x0044;
    L_0x0091:
        r0 = "UTF-32BE";
        r1 = r6.n;	 Catch:{ Exception -> 0x006d }
        r2 = 0;
        r3 = 60;
        r1[r2] = r3;	 Catch:{ Exception -> 0x006d }
        r1 = 1;
        r6.p = r1;	 Catch:{ Exception -> 0x006d }
        goto L_0x0044;
    L_0x009e:
        r0 = "UTF-32LE";
        r1 = r6.n;	 Catch:{ Exception -> 0x006d }
        r2 = 0;
        r3 = 60;
        r1[r2] = r3;	 Catch:{ Exception -> 0x006d }
        r1 = 1;
        r6.p = r1;	 Catch:{ Exception -> 0x006d }
        goto L_0x0044;
    L_0x00ab:
        r0 = "UTF-16BE";
        r1 = r6.n;	 Catch:{ Exception -> 0x006d }
        r2 = 0;
        r3 = 60;
        r1[r2] = r3;	 Catch:{ Exception -> 0x006d }
        r1 = r6.n;	 Catch:{ Exception -> 0x006d }
        r2 = 1;
        r3 = 63;
        r1[r2] = r3;	 Catch:{ Exception -> 0x006d }
        r1 = 2;
        r6.p = r1;	 Catch:{ Exception -> 0x006d }
        goto L_0x0044;
    L_0x00bf:
        r0 = "UTF-16LE";
        r1 = r6.n;	 Catch:{ Exception -> 0x006d }
        r2 = 0;
        r3 = 60;
        r1[r2] = r3;	 Catch:{ Exception -> 0x006d }
        r1 = r6.n;	 Catch:{ Exception -> 0x006d }
        r2 = 1;
        r3 = 63;
        r1[r2] = r3;	 Catch:{ Exception -> 0x006d }
        r1 = 2;
        r6.p = r1;	 Catch:{ Exception -> 0x006d }
        goto L_0x0044;
    L_0x00d4:
        r0 = r7.read();	 Catch:{ Exception -> 0x006d }
        if (r0 != r5) goto L_0x00dd;
    L_0x00da:
        r0 = r8;
        goto L_0x0025;
    L_0x00dd:
        r2 = r6.n;	 Catch:{ Exception -> 0x006d }
        r3 = r6.p;	 Catch:{ Exception -> 0x006d }
        r4 = r3 + 1;
        r6.p = r4;	 Catch:{ Exception -> 0x006d }
        r4 = (char) r0;	 Catch:{ Exception -> 0x006d }
        r2[r3] = r4;	 Catch:{ Exception -> 0x006d }
        r2 = 62;
        if (r0 != r2) goto L_0x00d4;
    L_0x00ec:
        r2 = new java.lang.String;	 Catch:{ Exception -> 0x006d }
        r0 = r6.n;	 Catch:{ Exception -> 0x006d }
        r3 = 0;
        r4 = r6.p;	 Catch:{ Exception -> 0x006d }
        r2.<init>(r0, r3, r4);	 Catch:{ Exception -> 0x006d }
        r0 = "encoding";
        r0 = r2.indexOf(r0);	 Catch:{ Exception -> 0x006d }
        if (r0 == r5) goto L_0x0024;
    L_0x00fe:
        r3 = r2.charAt(r0);	 Catch:{ Exception -> 0x006d }
        r4 = 34;
        if (r3 == r4) goto L_0x0111;
    L_0x0106:
        r3 = r2.charAt(r0);	 Catch:{ Exception -> 0x006d }
        r4 = 39;
        if (r3 == r4) goto L_0x0111;
    L_0x010e:
        r0 = r0 + 1;
        goto L_0x00fe;
    L_0x0111:
        r3 = r0 + 1;
        r0 = r2.charAt(r0);	 Catch:{ Exception -> 0x006d }
        r0 = r2.indexOf(r0, r3);	 Catch:{ Exception -> 0x006d }
        r0 = r2.substring(r3, r0);	 Catch:{ Exception -> 0x006d }
        goto L_0x0025;
    L_0x0121:
        r2 = -65536; // 0xffffffffffff0000 float:NaN double:NaN;
        r2 = r2 & r1;
        r3 = -131072; // 0xfffffffffffe0000 float:NaN double:NaN;
        if (r2 != r3) goto L_0x0142;
    L_0x0128:
        r0 = "UTF-16LE";
        r1 = r6.n;	 Catch:{ Exception -> 0x006d }
        r2 = 0;
        r3 = r6.n;	 Catch:{ Exception -> 0x006d }
        r4 = 3;
        r3 = r3[r4];	 Catch:{ Exception -> 0x006d }
        r3 = r3 << 8;
        r4 = r6.n;	 Catch:{ Exception -> 0x006d }
        r5 = 2;
        r4 = r4[r5];	 Catch:{ Exception -> 0x006d }
        r3 = r3 | r4;
        r3 = (char) r3;	 Catch:{ Exception -> 0x006d }
        r1[r2] = r3;	 Catch:{ Exception -> 0x006d }
        r1 = 1;
        r6.p = r1;	 Catch:{ Exception -> 0x006d }
        goto L_0x0044;
    L_0x0142:
        r1 = r1 & -256;
        r2 = -272908544; // 0xffffffffefbbbf00 float:-1.162092E29 double:NaN;
        if (r1 != r2) goto L_0x0044;
    L_0x0149:
        r0 = "UTF-8";
        r1 = r6.n;	 Catch:{ Exception -> 0x006d }
        r2 = 0;
        r3 = r6.n;	 Catch:{ Exception -> 0x006d }
        r4 = 3;
        r3 = r3[r4];	 Catch:{ Exception -> 0x006d }
        r1[r2] = r3;	 Catch:{ Exception -> 0x006d }
        r1 = 1;
        r6.p = r1;	 Catch:{ Exception -> 0x006d }
        goto L_0x0044;
    L_0x015a:
        r0 = r8;
        goto L_0x0044;
        */
        throw new UnsupportedOperationException("Method not decompiled: a.c.a.a.a(java.io.InputStream, java.lang.String):void");
    }

    public int mo32a(int i) {
        if (i <= this.h) {
            return this.arrk[i];
        }
        throw new IndexOutOfBoundsException();
    }

    public String mo38b(int i) {
        return this.arrj[i << 1];
    }

    public String mo40c(int i) {
        return this.arrj[(i << 1) + 1];
    }

    @Nullable
    public String mo33a(@Nullable String str) {
        if ("xml".equals(str)) {
            return "http://www.w3.org/XML/1998/namespace";
        }
        if ("xmlns".equals(str)) {
            return "http://www.w3.org/2000/xmlns/";
        }
        for (int a = (mo32a(this.h) << 1) - 2; a >= 0; a -= 2) {
            if (str == null) {
                if (this.arrj[a] == null) {
                    return this.arrj[a + 1];
                }
            } else if (str.equals(this.arrj[a])) {
                return this.arrj[a + 1];
            }
        }
        return null;
    }

    public int mo31a() {
        return this.h;
    }

//    public String getPositionDescription() {
//        StringBuffer stringBuffer = new StringBuffer(this.u < a.length ? a[this.u] : "unknown");
//        stringBuffer.append(' ');
//        if (this.u == 2 || this.u == 3) {
//            if (this.z) {
//                stringBuffer.append("(empty) ");
//            }
//            stringBuffer.append('<');
//            if (this.u == 3) {
//                stringBuffer.append('/');
//            }
//            if (this.x != null) {
//                stringBuffer.append("{" + this.w + "}" + this.x + ":");
//            }
//            stringBuffer.append(this.y);
//            int i = this.A << 2;
//            for (int i2 = 0; i2 < i; i2 += 4) {
//                stringBuffer.append(' ');
//                if (this.arrB[i2 + 1] != null) {
//                    stringBuffer.append("{" + this.arrB[i2] + "}" + this.arrB[i2 + 1] + ":");
//                }
//                stringBuffer.append(this.arrB[i2 + 2] + "='" + this.arrB[i2 + 3] + "'");
//            }
//            stringBuffer.append('>');
//        } else if (this.u != 7) {
//            if (this.u != 4) {
//                stringBuffer.append(tapcashgointerface30a_stringd());
//            } else if (this.v) {
//                stringBuffer.append("(whitespace)");
//            } else {
//                String d = tapcashgointerface30a_stringd();
//                if (d.length() > 16) {
//                    d = d.substring(0, 16) + "...";
//                }
//                stringBuffer.append(d);
//            }
//        }
//        stringBuffer.append("@" + this.q + ":" + this.r);
//        if (this.c != null) {
//            stringBuffer.append(" in ");
//            stringBuffer.append(this.c);
//        } else if (this.reader != null) {
//            stringBuffer.append(" in ");
//            stringBuffer.append(this.reader.toString());
//        }
//        return stringBuffer.toString();
//    }

    public int mo37b() {
        return this.q;
    }

    public int mo39c() {
        return this.r;
    }

    @Nullable
    public String mo41d() {
        return (this.u < 4 || (this.u == 6 && this.G)) ? null : m180f(0);
    }

    @Nullable
    public String mo43e() {
        return this.w;
    }

    @Nullable
    public String mo45f() {
        return this.y;
    }

//    public boolean tapcashgointerface30a_booleang() {
//        if (this.u != 2) {
//            m178c("Wrong event type");
//        }
//        return this.z;
//    }

    public int getAttributeCount() {
        return this.A;
    }

    @NonNull
    public String mo42d(int i) {
        return "CDATA";
    }

    public String mo44e(int i) {
        if (i < this.A) {
            return this.arrB[i << 2];
        }
        throw new IndexOutOfBoundsException();
    }

    public String getAttributeName(int i) {
        if (i < this.A) {
            return this.arrB[(i << 2) + 2];
        }
        throw new IndexOutOfBoundsException();
    }

    public String getAttributeValue(int i) {
        if (i < this.A) {
            return this.arrB[(i << 2) + 3];
        }
        throw new IndexOutOfBoundsException();
    }

    @Nullable
    public String getAttributeValue(@Nullable String str, String str2) {
        int i = (this.A << 2) - 4;
        while (i >= 0) {
            if (this.arrB[i + 2].equals(str2) && (str == null || this.arrB[i].equals(str))) {
                return this.arrB[i + 3];
            }
            i -= 4;
        }
        return null;
    }

    public int mo52h() {
        return this.u;
    }

//    public int tapcashgointerface30a_inti() {
//        this.t = 0;
//        this.v = true;
//        int i = 9999;
//        this.H = false;
//        while (true) {
//            m184n();
//            if (this.u < i) {
//                i = this.u;
//            }
//            if (i > 6 || (i >= 4 && m186p() >= 4)) {
//            }
//        }
//        this.u = i;
//        if (this.u > 4) {
//            this.u = 4;
//        }
//        return this.u;
//    }
//
//    public int tapcashgointerface30a_intj() {
//        this.v = true;
//        this.t = 0;
//        this.H = true;
//        m184n();
//        return this.u;
//    }
//
//    public int tapcashgointerface30a_intk() {
//        tapcashgointerface30a_inti();
//        if (this.u == 4 && this.v) {
//            tapcashgointerface30a_inti();
//        }
//        if (!(this.u == 3 || this.u == 2)) {
//            m178c("unexpected type");
//        }
//        return this.u;
//    }
//
//    public void tapcashgointerface30a_voida(int i, String str, String str2) {
//        if (i != this.u || ((str != null && !str.equals(tapcashgointerface30a_stringe())) || (str2 != null && !str2.equals(tapcashgointerface30a_strngf())))) {
//            m178c("expected: " + a[i] + " {" + str + "}" + str2);
//        }
//    }
//
//    public String tapcashgointerface30a_stringl() {
//        String d;
//        if (this.u != 2) {
//            m178c("precondition: START_TAG");
//        }
//        tapcashgointerface30a_inti();
//        if (this.u == 4) {
//            d = tapcashgointerface30a_stringd();
//            tapcashgointerface30a_inti();
//        } else {
//            d = "";
//        }
//        if (this.u != 3) {
//            m178c("END_TAG expected");
//        }
//        return d;
//    }
//
//    public void tapcashgointerface30a_voida(String str, boolean z) {
//        if ("http://xmlpull.org/v1/doc/features.html#process-namespaces".equals(str)) {
//            this.e = z;
//        } else if (m174a(str, false, "relaxed")) {
//            this.a = z;
//        } else {
//            m178c("unsupported feature: " + str);
//        }
//    }
}

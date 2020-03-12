package com.cardnfc.lib.bninfc.tapcashgo.card.tapcash;

import android.nfc.tech.IsoDep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;

import com.cardnfc.lib.bninfc.tapcashgo.tapcashgo773f;
import com.cardnfc.lib.bninfc.tapcashgo.tapcashgo783m;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

//import android.support.v7.p022a.C0503a.C0502j;

public class tapcashparseb {
    private static final byte[] f2355a = new byte[]{(byte) -1, (byte) -1};
    private byte f2356b = (byte) 3;
    private IsoDep f2357c;

    public tapcashparseb(IsoDep isoDep) {
        this.f2357c = isoDep;
    }

    @NonNull
    public TAPCASHPurse m4107a() throws Exception {
        try {
            int b = m4102b();
            byte[] a = m4100a((byte) 50, this.f2356b, (byte) 0, (byte) 0, null);
            if (a != null) {
                return new TAPCASHPurse(b, a);
            }
            return new TAPCASHPurse(b, "No purse found");
        } catch (Throwable e) {
            Throwable th = e;
            Log.w("TAPCASHProtocol", "Error reading purse " + this.f2356b, th);
            return new TAPCASHPurse(this.f2356b, th.getMessage());
        }
    }


    @NonNull
    public TAPCASHHistory m4106a(int r11) {
        /*
        r10 = this;
        r9 = 15;
        r8 = 0;
        r7 = 0;
        r1 = 50;
        r2 = r10.f2356b;	 Catch:{ a -> 0x0092, TagLostException -> 0x00ba }
        r3 = 0;
        r4 = 1;
        r0 = 2;
        r5 = new byte[r0];	 Catch:{ a -> 0x0092, TagLostException -> 0x00ba }
        r0 = 0;
        r6 = 0;
        r5[r0] = r6;	 Catch:{ a -> 0x0092, TagLostException -> 0x00ba }
        r6 = 1;
        if (r11 != 0) goto L_0x005f;
    L_0x0014:
        r0 = 16;
    L_0x0016:
        r0 = (byte) r0;	 Catch:{ a -> 0x0092, TagLostException -> 0x00ba }
        r5[r6] = r0;	 Catch:{ a -> 0x0092, TagLostException -> 0x00ba }
        r0 = r10;
        r6 = r0.m4100a(r1, r2, r3, r4, r5);	 Catch:{ a -> 0x0092, TagLostException -> 0x00ba }
        if (r6 == 0) goto L_0x00c3;
    L_0x0020:
        if (r11 <= r9) goto L_0x0086;
    L_0x0022:
        r7 = 0;
        r1 = 50;
        r2 = r10.f2356b;	 Catch:{ a -> 0x0067, TagLostException -> 0x00ba }
        r3 = 0;
        r4 = 1;
        r0 = 2;
        r5 = new byte[r0];	 Catch:{ a -> 0x0067, TagLostException -> 0x00ba }
        r0 = 0;
        r9 = 15;
        r5[r0] = r9;	 Catch:{ a -> 0x0067, TagLostException -> 0x00ba }
        r0 = 1;
        r9 = r11 + -15;
        r9 = r9 * 16;
        r9 = (byte) r9;	 Catch:{ a -> 0x0067, TagLostException -> 0x00ba }
        r5[r0] = r9;	 Catch:{ a -> 0x0067, TagLostException -> 0x00ba }
        r0 = r10;
        r0 = r0.m4100a(r1, r2, r3, r4, r5);	 Catch:{ a -> 0x0067, TagLostException -> 0x00ba }
        r1 = r0;
    L_0x003f:
        r2 = r6.length;	 Catch:{ a -> 0x0092, TagLostException -> 0x00ba }
        if (r1 == 0) goto L_0x0084;
    L_0x0042:
        r0 = r1.length;	 Catch:{ a -> 0x0092, TagLostException -> 0x00ba }
    L_0x0043:
        r0 = r0 + r2;
        r0 = new byte[r0];	 Catch:{ a -> 0x0092, TagLostException -> 0x00ba }
        r2 = 0;
        r3 = 0;
        r4 = r6.length;	 Catch:{ a -> 0x0092, TagLostException -> 0x00ba }
        java.lang.System.arraycopy(r6, r2, r0, r3, r4);	 Catch:{ a -> 0x0092, TagLostException -> 0x00ba }
        if (r1 == 0) goto L_0x0054;
    L_0x004e:
        r2 = 0;
        r3 = r6.length;	 Catch:{ a -> 0x0092, TagLostException -> 0x00ba }
        r4 = r1.length;	 Catch:{ a -> 0x0092, TagLostException -> 0x00ba }
        java.lang.System.arraycopy(r1, r2, r0, r3, r4);	 Catch:{ a -> 0x0092, TagLostException -> 0x00ba }
    L_0x0054:
        r1 = r0;
    L_0x0055:
        if (r1 == 0) goto L_0x0088;
    L_0x0057:
        r0 = new id.co.bni.tapcashgo.card.tapcash.TAPCASHHistory;	 Catch:{ a -> 0x0092, TagLostException -> 0x00ba }
        r2 = r10.f2356b;	 Catch:{ a -> 0x0092, TagLostException -> 0x00ba }
        r0.<init>(r2, r1);	 Catch:{ a -> 0x0092, TagLostException -> 0x00ba }
    L_0x005e:
        return r0;
    L_0x005f:
        if (r11 > r9) goto L_0x0064;
    L_0x0061:
        r0 = r11 * 16;
        goto L_0x0016;
    L_0x0064:
        r0 = 240; // 0xf0 float:3.36E-43 double:1.186E-321;
        goto L_0x0016;
    L_0x0067:
        r0 = move-exception;
        r1 = "TAPCASHProtocol";
        r2 = new java.lang.StringBuilder;	 Catch:{ a -> 0x0092, TagLostException -> 0x00ba }
        r2.<init>();	 Catch:{ a -> 0x0092, TagLostException -> 0x00ba }
        r3 = "Error reading 2nd purse history ";
        r2 = r2.append(r3);	 Catch:{ a -> 0x0092, TagLostException -> 0x00ba }
        r3 = r10.f2356b;	 Catch:{ a -> 0x0092, TagLostException -> 0x00ba }
        r2 = r2.append(r3);	 Catch:{ a -> 0x0092, TagLostException -> 0x00ba }
        r2 = r2.toString();	 Catch:{ a -> 0x0092, TagLostException -> 0x00ba }
        android.util.Log.w(r1, r2, r0);	 Catch:{ a -> 0x0092, TagLostException -> 0x00ba }
        r1 = r7;
        goto L_0x003f;
    L_0x0084:
        r0 = r8;
        goto L_0x0043;
    L_0x0086:
        r1 = r6;
        goto L_0x0055;
    L_0x0088:
        r0 = new id.co.bni.tapcashgo.card.tapcash.TAPCASHHistory;	 Catch:{ a -> 0x0092, TagLostException -> 0x00ba }
        r1 = r10.f2356b;	 Catch:{ a -> 0x0092, TagLostException -> 0x00ba }
        r2 = "No history found";
        r0.<init>(r1, r2);	 Catch:{ a -> 0x0092, TagLostException -> 0x00ba }
        goto L_0x005e;
    L_0x0092:
        r0 = move-exception;
        r1 = r0;
        r0 = "TAPCASHProtocol";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "Error reading purse history ";
        r2 = r2.append(r3);
        r3 = r10.f2356b;
        r2 = r2.append(r3);
        r2 = r2.toString();
        android.util.Log.w(r0, r2, r1);
        r0 = new id.co.bni.tapcashgo.card.tapcash.TAPCASHHistory;
        r2 = r10.f2356b;
        r1 = r1.getMessage();
        r0.<init>(r2, r1);
        goto L_0x005e;
    L_0x00ba:
        r0 = move-exception;
        r0 = new java.lang.Exception;
        r1 = "Pembacaan tidak sempurna. Mohon ulangi.";
        r0.<init>(r1);
        throw r0;
    L_0x00c3:
        r1 = r7;
        goto L_0x0055;
        */
        throw new UnsupportedOperationException("Method not decompiled: id.co.bni.tapcashgo.card.tapcash.c.a(int):id.co.bni.tapcashgo.card.tapcash.TAPCASHHistory");
    }

    private byte[] m4100a(byte b, byte b2, byte b3, byte b4, byte[] bArr) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] transceive = this.f2357c.transceive(m4103b(b, b2, b3, b4, bArr));
        if (transceive[transceive.length - 2] != (byte) -112) {
            throw new Exception("Kartu tidak dikenal / \nBukan kartu BNI. \n(Card response failed)");
        }
        byteArrayOutputStream.write(transceive, 0, transceive.length - 2);
        byte b5 = transceive[transceive.length - 1];
        if (b5 == (byte) 0) {
            return byteArrayOutputStream.toByteArray();
        }
        if (b5 == (byte) -99) {
            throw new tapcashparsea("Permission denied");
        }
        throw new tapcashparsea("Unknown status code: " + Integer.toHexString(b5 & 255));
    }

    private byte[] m4103b(byte b, byte b2, byte b3, byte b4, @Nullable byte[] bArr) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(-112);
        byteArrayOutputStream.write(b);
        byteArrayOutputStream.write(b2);
        byteArrayOutputStream.write(b3);
        byteArrayOutputStream.write(b4);
        if (bArr != null) {
            byteArrayOutputStream.write(bArr);
        }
        return byteArrayOutputStream.toByteArray();
    }

    private int m4102b() throws Exception {
        byte[] transceive = this.f2357c.transceive(m4104b("A000424E49100001"));
        byte b = transceive[transceive.length - 1];
        byte[] transceive2 = this.f2357c.transceive(m4104b("A000424E49999999"));
        byte b2 = transceive2[transceive2.length - 1];
        if (b != (byte) 0) {
            throw new Exception("Kartu tidak dikenal / \nBukan kartu BNI.");
        } else if (b2 == (byte) 0) {
            return 41;
        } else {
            return 42;
        }
    }

    private byte[] m4104b(@NonNull String str) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(0);
        byteArrayOutputStream.write(-92);
        byteArrayOutputStream.write(4);
        byteArrayOutputStream.write(0);
        byteArrayOutputStream.write(8);
        byteArrayOutputStream.write(tapcashgo783m.tapcashgo783m_b(str));
        return byteArrayOutputStream.toByteArray();
    }

    private byte[] m4101a(byte[] bArr) throws IOException, tapcashparsea {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] transceive = this.f2357c.transceive(bArr);
        byteArrayOutputStream.write(transceive, 0, transceive.length - 2);
        byte b = transceive[transceive.length - 1];
        byte b2 = transceive[transceive.length - 2];
        byte b3 = transceive[transceive.length - 1];
        if (b2 == (byte) -112 && b3 == (byte) 0) {
            return byteArrayOutputStream.toByteArray();
        }
        if (b == (byte) -99) {
            throw new tapcashparsea("Permission denied");
        }
        throw new tapcashparsea("Unknown status code: " + Integer.toHexString(b & 255));
    }

    @NonNull
    private byte[] m4105b(@NonNull byte[] bArr, @NonNull byte[] bArr2, @NonNull byte[] bArr3) {
        byte[] obj = new byte[92];
        System.arraycopy(tapcashgo783m.tapcashgo783m_b("0001"), 0, obj, 0, 2);
        System.arraycopy(bArr, 8, obj, 2, 8);
        System.arraycopy(bArr, 16, obj, 10, 8);
        System.arraycopy(bArr2, 0, obj, 18, 8);
        System.arraycopy(bArr3, 0, obj, 26, 8);
        System.arraycopy(tapcashgo783m.tapcashgo783m_b("00000000"), 0, obj, 34, 4);
        System.arraycopy(bArr, 1, obj, 38, 1);
        System.arraycopy(bArr, 2, obj, 39, 3);
        System.arraycopy(bArr, 28, obj, 42, 4);
        System.arraycopy(bArr, 32, obj, 46, 8);
        System.arraycopy(bArr, 42, obj, 54, 4);
        System.arraycopy(bArr, 46, obj, 58, 16);
        System.arraycopy(bArr, 63, obj, 74, 1);
        System.arraycopy(bArr, 94, obj, 75, 1);
        System.arraycopy(bArr, 95, obj, 76, 8);
       // System.arraycopy(bArr, C0502j.AppCompatTheme_buttonStyleSmall, obj, 84, 8);
        return obj;
    }

    public byte[] m4110a(@NonNull byte[] bArr, @NonNull byte[] bArr2, @NonNull byte[] bArr3) {
        byte[] bArr4 = new byte[43];
        System.arraycopy(tapcashgo783m.tapcashgo783m_b("9036140125"), 0, bArr4, 0, 5);
        System.arraycopy(tapcashgo783m.tapcashgo783m_b("031402"), 0, bArr4, 5, 3);
        bArr4[8] = tapcashgo783m.tapcashgo783m_b("14")[0];
        bArr4[9] = tapcashgo783m.tapcashgo783m_b("03")[0];
        System.arraycopy(bArr2, 0, bArr4, 10, 8);
        System.arraycopy(bArr, 0, bArr4, 18, 16);
        System.arraycopy(bArr3, 0, bArr4, 34, 8);
        bArr4[42] = (byte) 24;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byte[] transceive = this.f2357c.transceive(bArr4);
            Log.d("UPDATE^BALANCE", "APDU update >> " + tapcashgo783m.tapcashgo783m_b(bArr4));
            Log.d("UPDATE^BALANCE", "APDU update << " + tapcashgo783m.tapcashgo783m_b(transceive));
            byteArrayOutputStream.write(transceive, transceive.length - 2, 2);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            return f2355a;
        }
    }

    public void m4108a(String str) throws Exception {
        try {
            byte[] r1 = new byte[8];
            System.arraycopy(m4101a(tapcashgo783m.tapcashgo783m_b("0084000008")), 0, r1, 0, 8);
            Log.d("UPDATE^BALANCE", "CRN : " + tapcashgo783m.tapcashgo783m_b(r1) + "\n");
            byte[] bArr = new byte[8];
            new Random().nextBytes(bArr);
            Log.d("UPDATE^BALANCE", "RRN : " + tapcashgo783m.tapcashgo783m_b(bArr) + "\n");
            byte[] bArr2 = new byte[16];
            System.arraycopy(tapcashgo783m.tapcashgo783m_b("903203000A"), 0, bArr2, 0, 5);
            bArr2[5] = (byte) 18;
            bArr2[6] = (byte) 1;
            System.arraycopy(bArr, 0, bArr2, 7, 8);
            bArr2 = m4101a(bArr2);
            String c = tapcashgo783m.tapcashgo783m_c(bArr2, 8, 8);
            String d = tapcashgo783m.tapcashgo783m_d(bArr2, 2, 3);
            int a = tapcashgo783m.tapcashgo783m_a(bArr2, 2, 3);
            int a2 = tapcashgo783m.tapcashgo783m_a(bArr2, 78, 3);
            Log.d("UPDATE^BALANCE", "Card Number          : " + c + "\n");
            Log.d("UPDATE^BALANCE", "Purse Balance Before : " + d + "\n");
            Log.d("UPDATE^BALANCE", "Max Balance          : " + a2 + "\n");
            bArr2 = m4105b(bArr2, bArr, r1);
            Log.d("UPDATE^BALANCE", "Request Update to host : " + tapcashgo783m.tapcashgo783m_b(bArr2));
            String b = tapcashgo783m.tapcashgo783m_b(bArr2);
            Log.i("cardatareq", b);
            tapcashgo773f tapcashgo773F = new tapcashgo773f();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("cardData", b);
            jSONObject.put("versionCodeApp", String.valueOf(6));
            JSONObject jSONObject2 = new JSONObject(tapcashgo773F.tapcashgo773f_stringa(jSONObject, "UpdateBalance"));
            JSONObject jSONObject3;
            if (jSONObject2.getString("responseCode").equals("OK")) {
                jSONObject3 = jSONObject2.getJSONObject("OKContent");
                String string = jSONObject3.getString("criptogram");
                b = jSONObject3.getString("amount");
                byte[] obj = new byte[8];
                byte[] obj2 = new byte[16];
                System.arraycopy(tapcashgo783m.tapcashgo783m_b(string), 8, obj, 0, 8);
                System.arraycopy(tapcashgo783m.tapcashgo783m_b(string), 16, obj2, 0, 16);
                int parseInt = Integer.parseInt(b);
                String a3 = tapcashgo783m.tapcashgo783m_a(parseInt);
                if (a + parseInt > a2) {
                    m4109a("FAILED - Max Balance Reached", "", c, "Max Balance  : " + tapcashgo783m.tapcashgo783m_a(a2) + "\n\n", d, a3, d);
                }
                byte[] a4 = m4110a(obj2, bArr, obj);
                if (a4[0] == (byte) -112 && a4[1] == (byte) 0) {
                    m4109a("00 - SUCCESS", "", c, "", d, a3, tapcashgo783m.tapcashgo783m_a(a + parseInt));
                    return;
                }
                bArr2 = tapcashgo783m.tapcashgo783m_b("9032030000");
                try {
                    m4101a(bArr2);
                    m4101a(bArr2);
                    bArr2 = m4101a(bArr2);
                } catch (Exception e) {
                    m4109a("", "Transaksi sedang diproses silakan cek info kartu anda", c, "", d, a3, "-");
                    bArr2 = null;
                }
                string = tapcashgo783m.tapcashgo783m_c(bArr2, 8, 8);
                a2 = tapcashgo783m.tapcashgo783m_a(bArr2, 2, 3);
                String d2 = tapcashgo783m.tapcashgo783m_d(bArr2, 2, 3);
                if (!c.equals(string)) {
                    m4109a("CARD FAILED", "", c, "New Card No. : " + string + "\n", "-", "-", "-");
                } else if (a2 > a) {
                    m4109a("00 - SUCCESS", "", c, "", d, a3, d2);
                } else if (a4 != f2355a) {
                    m4109a("CREDIT FAILED - REVERSED", "", c, "", d, a3, d);
                } else {
                    m4109a("UPDATE FAILED - REVERSED", "", c, "", d, a3, d2);
                }
            } else if (jSONObject2.getString("responseCode").equals("FAIL")) {
                jSONObject3 = jSONObject2.getJSONObject("FAILContent");
                String optString = jSONObject3.optString("soaFailCode");
                if (jSONObject3.optString("appFailCode").equalsIgnoreCase("MBANK67")) {
                    m4109a("", jSONObject3.optString("appFailDescription") + "\n\n", c, "", d, "-", d);
                }
                if (optString.equals("14")) {
                    m4109a(optString + " - No Data to Update", "Silakan lakukan transfer dana/pending topup melalui ATM, EDC, SMS Banking atau Internet Banking terlebih dahulu.\n\n\n", c, "", d, "-", d);
                } else if (optString.equals("76")) {
                    m4109a(optString + " - Invalid Card", "", c, "", d, "-", d);
                } else {
                    m4109a(optString + " - Failed to Update", "", c, "", d, "-", d);
                }
            }
        } catch (Throwable e2) {
            Log.w("TAPCASHProtocol", "Error json exception ", e2);
        }
    }

    public void m4109a(String str, String str2, String str3, String str4, String str5, String str6, String str7) throws Exception {
        throw new Exception("    .: UPDATE BNI TAPCASH :.    \n\n\n" + str + "\n\n\n" + str2 + "Card No.     : " + str3 + "\n" + str4 + "Prev Balance : " + str5 + "\n" + "Amount       : " + str6 + "\n" + "Curr Balance : " + str7 + "\n");
    }
}

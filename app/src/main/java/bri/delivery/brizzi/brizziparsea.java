package bri.delivery.brizzi;

import android.nfc.tech.IsoDep;
import androidx.annotation.NonNull;

import org.simalliance.openmobileapi.util.ISO7816;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class brizziparsea {
    private static final byte[] APDU_a = new byte[]{(byte) -112, (byte) 90, (byte) 0, (byte) 0, (byte) 3, (byte) 1, (byte) 0, (byte) 0, (byte) 0};
    private static final byte[] APDU_b = new byte[]{(byte) -112, (byte) 90, (byte) 0, (byte) 0, (byte) 3, (byte) 2, (byte) 0, (byte) 0, (byte) 0};
    private static final byte[] APDU_c = new byte[]{(byte) -112, (byte) 90, (byte) 0, (byte) 0, (byte) 3, (byte) 3, (byte) 0, (byte) 0, (byte) 0};
    private static final byte[] APDU_d = new byte[]{(byte) -112, (byte) -67, (byte) 0, (byte) 0, (byte) 7, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 23, (byte) 0, (byte) 0, (byte) 0};
    private static final byte[] APDU_e = new byte[]{(byte) -112, (byte) -67, (byte) 0, (byte) 0, (byte) 7, (byte) 1, (byte) 0, (byte) 0, (byte) 0, ISO7816.INS_VERIFY_20, (byte) 0, (byte) 0, (byte) 0};
    private static final byte[] APDU_h = new byte[]{(byte) -112, (byte) 108, (byte) 0, (byte) 0, (byte) 1, (byte) 0, (byte) 0};
    private static final byte[] APDU_i = new byte[]{(byte) -112, (byte) -67, (byte) 0, (byte) 0, (byte) 7, (byte) 3, (byte) 0, (byte) 0, (byte) 0, (byte) 7, (byte) 0, (byte) 0, (byte) 0};
    private static final byte[] f35f = new byte[]{(byte) -112, (byte) 10, (byte) 0, (byte) 0, (byte) 1, (byte) 0, (byte) 0};
    private static final byte[] f36g = new byte[]{(byte) -112, (byte) 10, (byte) 0, (byte) 0, (byte) 1, (byte) 1, (byte) 0};
    private static final byte[] f37j = new byte[]{(byte) -112, (byte) -69, (byte) 0, (byte) 0, (byte) 7, (byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0};
    private IsoDep isoDep;
    private kuBrizzi f38l;
    private String f39m;
    private String f40n;
    private String f41o;
    private String f42p;
    private String f43q;
    private String f44r;
    private String f45s;
    private String f46t;
    private String f47u;
    @NonNull
    private String f48v = "";
    private Integer f49w;

    public brizziparsea(IsoDep isoDep) {
        this.isoDep = isoDep;
        this.f38l = new kuBrizzi();
    }

    @NonNull
    public static byte[] brizziparsea_bytea(@NonNull String str) {
        int length = str.length();
        byte[] bArr = new byte[(length / 2)];
        int i = 0;
        while (i < length - 1) {
            int i2 = i + 2;
            bArr[i / 2] = (byte) (Integer.parseInt(str.substring(i, i2), 16) & 255);
            i = i2;
        }
        return bArr;
    }

    @NonNull
    private String brizziparsea_stringc(@NonNull String str) {
        if (str.equals("EB")) {
            return "Payment";
        }
        if (str.equals("EC")) {
            return "Topup Online";
        }
        if (str.equals("ED")) {
            return "Void";
        }
        if (str.equals("EF")) {
            return "Aktivasi Deposit";
        }
        if (str.equals("5F")) {
            return "Reaktivasi";
        }
        return "Lainnya";
    }

    @NonNull
    private String brizziparsea_stringd(@NonNull String str) {
        String str2 = "";
        int i = 0;
        while (i < str.length()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str2);
            int i2 = i + 2;
            stringBuilder.append((char) Integer.parseInt(str.substring(i, i2), 16));
            i = i2;
            str2 = stringBuilder.toString();
        }
        return str2;
    }

    @NonNull
    public Boolean brizziparsea_booleana() {
        IOException iOException;
        Boolean valueOf = Boolean.valueOf(false);
        try {
            brizziparsea_stringb(this.isoDep.transceive(APDU_a));
            String b = brizziparsea_stringb(this.isoDep.transceive(APDU_d));
            if (b.substring(b.length() - 4, b.length()).equalsIgnoreCase("9100")) {
                Boolean valueOf2 = Boolean.valueOf(true);
                try {
                    this.f39m = b.substring(6, 22);
                    this.f41o = b.substring(22, 28);
                    this.f46t = b.substring(34, 38);
                    this.f43q = brizziparsea_stringb(this.isoDep.transceive(APDU_e)).substring(6, 10);
                    valueOf = valueOf2;
                } catch (IOException e) {
                    iOException = e;
                    valueOf = valueOf2;
                    iOException.printStackTrace();
                    return valueOf;
                }
            }
            return valueOf;
        } catch (IOException e2) {
            iOException = e2;
            iOException.printStackTrace();
            return valueOf;
        }
    }

    public void brizziparsea_bytea(@NonNull byte[] bArr) {
        this.f40n = brizziparsea_stringb(bArr);
    }

    public Boolean brizziparsea_booleanb() {
        Boolean valueOf = Boolean.valueOf(false);
        try {
            Boolean valueOf2;
            brizziparsea_stringb(this.isoDep.transceive(APDU_c));
            String b = brizziparsea_stringb(this.isoDep.transceive(f35f));
            if (b.substring(b.length() - 4, b.length()).equalsIgnoreCase("91AF")) {
                this.f45s = b.substring(0, b.length() - 4);
                kuBrizzi kuBrizziVar = this.f38l;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.f39m);
                stringBuilder.append(this.f40n);
                stringBuilder.append("FF");
                kuBrizziVar.kuBrizzi_voida(stringBuilder.toString(), "0000030080000000", this.f45s);
                kuBrizziVar = this.f38l;
                IsoDep isoDep = this.isoDep;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("90AF000010");
                stringBuilder2.append(this.f38l.kuBrizzi_stringa().substring(0, 32));
                stringBuilder2.append("00");
                String a = kuBrizziVar.kuBrizzi_stringa(isoDep.transceive(brizziparsea.brizziparsea_bytea(stringBuilder2.toString())));
                if (a.substring(a.length() - 4, a.length()).equalsIgnoreCase("9100")) {
                    valueOf2 = Boolean.valueOf(true);
                    return valueOf2;
                }
            }
            valueOf2 = valueOf;
            return valueOf2;
        } catch (IOException e) {
            e.printStackTrace();
            return valueOf;
        }
    }

    @NonNull
    public String brizziparsea_stringb(@NonNull String str) {
        String str2 = "";
        for (int i = 0; i < str.length(); i += 2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str2);
            stringBuilder.append(str.substring((str.length() - i) - 2, str.length() - i));
            str2 = stringBuilder.toString();
        }
        return str2.toUpperCase();
    }

    @NonNull
    public String brizziparsea_stringb(@NonNull byte[] bArr) {
        String str = "";
        for (byte b : bArr) {
            StringBuilder stringBuilder;
            String toHexString = Integer.toHexString(b & 255);
            if (toHexString.length() == 1) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("0");
                stringBuilder.append(toHexString);
                toHexString = stringBuilder.toString();
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(toHexString);
            str = stringBuilder.toString();
        }
        return str.toUpperCase();
    }

    public String brizziparsea_stringc() {
        return this.f39m;
    }

    @NonNull
    public String brizziparsea_stringd() {
        if (this.f43q.equals("6161")) {
            return brizziparsea_booleang().booleanValue() ? "PASSIVE" : "ACTIVE";
        } else {
            if (!this.f43q.equals("636C")) {
                if (!this.f43q.equals("434C")) {
                    return "";
                }
            }
            return "CLOSED";
        }
    }

    @NonNull
    public String brizziparsea_stringe() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.f41o.substring(0, 2));
        stringBuilder.append("/");
        stringBuilder.append(this.f41o.substring(2, 4));
        stringBuilder.append("/");
        stringBuilder.append(this.f41o.substring(4, 6));
        return stringBuilder.toString();
    }

    public String brizziparsea_stringf() {
        return this.f46t;
    }

    @NonNull
    public Boolean brizziparsea_booleang() {
        Boolean valueOf = Boolean.TRUE;
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");
        while (true) {
            try {

               // break;
                return this.f42p.equals("000000") ? Boolean.valueOf(false) : (date.getTime() - simpleDateFormat.parse(this.f42p).getTime()) / 86400000 < 365 ? Boolean.valueOf(false) : valueOf;
            } catch (ParseException e) {
                e.printStackTrace();
            }
           // break;
        }

       // return valueOf;
    }

    public String brizziparsea_stringh() {
        try {
            this.f42p = brizziparsea_stringb(this.isoDep.transceive(APDU_i)).substring(0, 6);
            String b = brizziparsea_stringb(this.isoDep.transceive(APDU_h));
            this.f47u = b.substring(0, 6);
            this.f49w = Integer.valueOf(Integer.parseInt(brizziparsea_stringb(b.substring(0, 8)), 16));
            this.f44r = String.format("%,.2f", new Object[]{this.f49w});
            return this.f44r;
        } catch (IOException e) {
            e.printStackTrace();
            return this.f44r;
        }
    }
}

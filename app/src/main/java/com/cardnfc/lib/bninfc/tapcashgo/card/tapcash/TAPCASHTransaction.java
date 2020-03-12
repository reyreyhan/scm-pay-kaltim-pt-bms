package com.cardnfc.lib.bninfc.tapcashgo.card.tapcash;


import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.measurement.AppMeasurement.Param;

import org.simalliance.openmobileapi.util.ISO7816;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TAPCASHTransaction implements Parcelable {
    public static final Creator<TAPCASHTransaction> CREATOR = new TAPCASHTransaction_();
    private final byte a;
    private final int b;
    private final int c;
    private final String d;

    static class TAPCASHTransaction_ implements Creator<TAPCASHTransaction> {
        TAPCASHTransaction_() {
        }

        @Nullable
        public TAPCASHTransaction createFromParcel(@NonNull Parcel parcel) {
            return tapcashTransaction(parcel);
        }

        @NonNull
        public TAPCASHTransaction[] newArray(int i) {
            return tapcashTransactions(i);
        }

        @Nullable
        public TAPCASHTransaction tapcashTransaction(@NonNull Parcel parcel) {
            return new TAPCASHTransaction(parcel.readByte(), parcel.readInt(), parcel.readInt(), parcel.readString());
        }

        @NonNull
        public TAPCASHTransaction[] tapcashTransactions(int i) {
            return new TAPCASHTransaction[i];
        }
    }

    public enum TAPCASHTransactionenum {
        PURCHASE,
        BLACKLIST,
        ATM_TOP_UP,
        CASH_TOP_UP,
        STATEMENT_FEE,
        CARD_UPDATE,
        GRACE_PERIODE_FEE,
        DISABLE_ATU,
        DISABLE_PURSE,
        ATM_REFUND,
        CASH_REFUND,
        REFUND_DISABLE_PURSE,
        OTHERS,
        UNKNOWN
    }

    public int describeContents() {
        return 0;
    }

    public TAPCASHTransaction(byte[] paramArrayOfByte) {
        this.a = paramArrayOfByte[0];
       // int i = (((bArr[1] << 16) & 16711680) | ((bArr[2] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | (bArr[3] & 255);
        int j = 0xFF0000 & paramArrayOfByte[1] << 16 | paramArrayOfByte[2] << 8 & 0xFF00 | paramArrayOfByte[3] & 0xFF;
//        if ((bArr[1] & 128) != 0) {
//            i |= ViewCompat.MEASURED_STATE_MASK;
//        }
        int i = j;
        if ((paramArrayOfByte[1] & 0x80) != 0) {
            i = j | 0xFF000000;
        }
        this.b = i;
       // this.c = ((((((bArr[4] << 24) & ViewCompat.MEASURED_STATE_MASK) | ((bArr[5] << 16) & 16711680)) | ((bArr[6] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK)) | (bArr[7] & 255)) + 788950800) - 57600;
        this.c = ((paramArrayOfByte[4] << 24 & 0xFF000000 | 0xFF0000 & paramArrayOfByte[5] << 16 | paramArrayOfByte[6] << 8 & 0xFF00 | paramArrayOfByte[7] & 0xFF) + 788950800 - 57600);
        byte[] bArr2 = new byte[9];
        for (i = 0; i < 8; i++) {
            bArr2[i] = paramArrayOfByte[i + 8];
        }
        bArr2[8] = (byte) 0;
        this.d = new String(bArr2);
    }

    public TAPCASHTransaction(byte b, int i, int i2, String str) {
        this.a = b;
        this.b = i;
        this.c = i2;
        this.d = str;
    }

    @NonNull
    public TAPCASHTransactionenum tapcashTransactionenum() {
        if (this.a == (byte) 1) {
            return TAPCASHTransactionenum.PURCHASE;
        }
        if (this.a == (byte) 2) {
            return TAPCASHTransactionenum.BLACKLIST;
        }
        if (this.a == (byte) 3) {
            return TAPCASHTransactionenum.ATM_TOP_UP;
        }
        if (this.a == (byte) 4) {
            return TAPCASHTransactionenum.CASH_TOP_UP;
        }
        if (this.a == (byte) 5) {
            return TAPCASHTransactionenum.STATEMENT_FEE;
        }
        if (this.a == (byte) 6) {
            return TAPCASHTransactionenum.CARD_UPDATE;
        }
        if (this.a == (byte) 7) {
            return TAPCASHTransactionenum.GRACE_PERIODE_FEE;
        }
        if (this.a == (byte) 8) {
            return TAPCASHTransactionenum.DISABLE_ATU;
        }
        if (this.a == (byte) 9) {
            return TAPCASHTransactionenum.DISABLE_PURSE;
        }
        if (this.a == (byte) 10) {
            return TAPCASHTransactionenum.ATM_REFUND;
        }
        if (this.a == ISO7816.INS_VERIFY_20) {
            return TAPCASHTransactionenum.CASH_REFUND;
        }
        if (this.a == ISO7816.INS_MANAGE_SECURITY_ENVIRONMENT) {
            return TAPCASHTransactionenum.REFUND_DISABLE_PURSE;
        }
        if (this.a == (byte) -16) {
            return TAPCASHTransactionenum.OTHERS;
        }
        return TAPCASHTransactionenum.UNKNOWN;
    }

    public int b() {
        return this.b;
    }

    public int c() {
        return this.c;
    }

    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeByte(this.a);
        parcel.writeInt(this.b);
        parcel.writeInt(this.c);
        parcel.writeString(this.d);
    }

    @NonNull
    public static TAPCASHTransaction tapcashTransaction(@NonNull Element element) {
        return new TAPCASHTransaction(Byte.parseByte(element.getAttribute(Param.TYPE)), Integer.parseInt(element.getAttribute("amount")), Integer.parseInt(element.getAttribute("date")), element.getAttribute("user-data"));
    }

    public Element TAPCASHTransactionElement(@NonNull Document document) {
        Element documentx = document.createElement("transaction");
        documentx.setAttribute(Param.TYPE, Byte.toString(this.a));
        documentx.setAttribute("amount", Integer.toString(this.b));
        documentx.setAttribute("date", Integer.toString(this.c));
        documentx.setAttribute("user-data", this.d);
        return documentx;
    }
}
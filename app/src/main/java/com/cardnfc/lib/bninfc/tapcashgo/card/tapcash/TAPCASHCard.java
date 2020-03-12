package com.cardnfc.lib.bninfc.tapcashgo.card.tapcash;


import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cardnfc.lib.bninfc.tapcashgo.card.Card;
import com.cardnfc.lib.bninfc.tapcashgo.transit.TapCashTransitData;
import com.cardnfc.lib.bninfc.tapcashgo.transit.TransitData;
import com.cardnfc.lib.bninfc.tapcashgo.transit.tapcashgo789a;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Date;

public class TAPCASHCard extends Card {
    public static final Creator<TAPCASHCard> CREATOR = new TAPCASHCardModel();
    private TAPCASHPurse[] tapcashPurses;
    private TAPCASHHistory[] tapcashHistories;

    static class TAPCASHCardModel implements Creator<TAPCASHCard> {
        TAPCASHCardModel() {
        }

        @NonNull
        public  TAPCASHCard createFromParcel(@NonNull Parcel parcel) {
            return tapcashCard(parcel);
        }

        @NonNull
        public TAPCASHCard[] newArray(int i) {
            return tapcashCards(i);
        }

        @NonNull
        public TAPCASHCard tapcashCard(@NonNull Parcel parcel) {
            int i = 0;
            byte[] bArr = new byte[parcel.readInt()];
            parcel.readByteArray(bArr);
            Date date = new Date(parcel.readLong());
            TAPCASHPurse[] tAPCASHPurseArr = new TAPCASHPurse[parcel.readInt()];
            for (int i2 = 0; i2 < tAPCASHPurseArr.length; i2++) {
                tAPCASHPurseArr[i2] = (TAPCASHPurse) parcel.readParcelable(TAPCASHPurse.class.getClassLoader());
            }
            TAPCASHHistory[] tAPCASHHistoryArr = new TAPCASHHistory[parcel.readInt()];
            while (i < tAPCASHHistoryArr.length) {
                tAPCASHHistoryArr[i] = (TAPCASHHistory) parcel.readParcelable(TAPCASHHistory.class.getClassLoader());
                i++;
            }
            return new TAPCASHCard(bArr, date, tAPCASHPurseArr, tAPCASHHistoryArr);
        }

        @NonNull
        TAPCASHCard[] tapcashCards(int i) {
            return new TAPCASHCard[i];
        }
    }

    @Nullable
    public static TAPCASHCard tapcashCard(@NonNull Tag tag, boolean z, String str) throws Exception {
        IsoDep isoDep = IsoDep.get(tag);
        try {
            isoDep.connect();
            TAPCASHPurse[] tAPCASHPurseArr = new TAPCASHPurse[1];
            TAPCASHHistory[] tAPCASHHistoryArr = new TAPCASHHistory[1];
            try {
                tapcashparseb tapcashparseb = new tapcashparseb(isoDep);
                if (z) {
                    tapcashparseb.m4108a(str);
                }
                tAPCASHPurseArr[0] = tapcashparseb.m4107a();
                if (tAPCASHPurseArr[0].TAPCASHPurse_l()) {
                    tAPCASHHistoryArr[0] = tapcashparseb.m4106a(Integer.parseInt(Byte.toString(tAPCASHPurseArr[0].TAPCASHPurse_i())));
                } else {
                    tAPCASHHistoryArr[0] = new TAPCASHHistory(0, (byte[]) null);
                }
                if (isoDep.isConnected()) {
                    isoDep.close();
                }
                return new TAPCASHCard(tag.getId(), new Date(), tAPCASHPurseArr, tAPCASHHistoryArr);
            } catch (Throwable th) {
                if (isoDep.isConnected()) {
                    isoDep.close();
                }
            }
        } catch (NullPointerException e) {
            throw new Exception("Kartu tidak dikenal / \nBukan kartu BNI.");
           // return null;
        }

        return null;
    }

    private TAPCASHCard(byte[] bArr, Date date, TAPCASHPurse[] tAPCASHPurseArr, TAPCASHHistory[] tAPCASHHistoryArr) {
        super(bArr, date);
        this.tapcashPurses = tAPCASHPurseArr;
        this.tapcashHistories = tAPCASHHistoryArr;
    }

//    public Cardenum_a mo712a() {
//        return Cardenum_a.TAPCASH;
//    }

//    public tapcashgo789a mo713d() {
//        if (TapCashTransitData.tapcashgo789a_b((Card) this)) {
//            return TapCashTransitData.tapcashgo789a_b(this);
//        }
//        return null;
//    }

    @Nullable
    public TransitData transitData() {
        if (TapCashTransitData.a((Card) this)) {
            return new TapCashTransitData((Card) this);
        }
        return null;
    }

    public TAPCASHPurse TAPCASHPurse_a(int i) {
        return this.tapcashPurses[i];
    }

    public TAPCASHHistory TAPCASHPurse_b(int i) {
        return this.tapcashHistories[i];
    }

    public void writeToParcel(@NonNull Parcel parcel, int i) {
        int i2 = 0;
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.tapcashPurses.length);
        for (Parcelable writeParcelable : this.tapcashPurses) {
            parcel.writeParcelable(writeParcelable, i);
        }
        parcel.writeInt(this.tapcashHistories.length);
        while (i2 < this.tapcashHistories.length) {
            parcel.writeParcelable(this.tapcashHistories[i2], i);
            i2++;
        }
    }

    @NonNull
    public static TAPCASHCard tapcashCard(byte[] bArr, Date date, @NonNull Element element) {
        int i = 0;
        NodeList elementsByTagName = ((Element) element.getElementsByTagName("purses").item(0)).getElementsByTagName("purse");
        TAPCASHPurse[] tAPCASHPurseArr = new TAPCASHPurse[elementsByTagName.getLength()];
        for (int i2 = 0; i2 < elementsByTagName.getLength(); i2++) {
            tAPCASHPurseArr[i2] = TAPCASHPurse.m4078a((Element) elementsByTagName.item(i2));
        }
        NodeList elementsByTagName2 = ((Element) element.getElementsByTagName("histories").item(0)).getElementsByTagName("history");
        TAPCASHHistory[] tAPCASHHistoryArr = new TAPCASHHistory[elementsByTagName2.getLength()];
        while (i < elementsByTagName2.getLength()) {
            tAPCASHHistoryArr[i] = TAPCASHHistory.m4071a((Element) elementsByTagName2.item(i));
            i++;
        }
        return new TAPCASHCard(bArr, date, tAPCASHPurseArr, tAPCASHHistoryArr);
    }

    @NonNull
    @Override
    public Cardenum_a cardenum_a() {
        return Cardenum_a.TAPCASH;
    }

    @Nullable
    @Override
    public tapcashgo789a tapcashgo789a() {
        if (TapCashTransitData.a((Card) this)) {
            return TapCashTransitData.tapcashgo789a_b(this);
        }
        return null;
    }

    public Element Cardelement_f() {
        int i = 0;
        Element f = super.Cardelement_f();
        Document ownerDocument = f.getOwnerDocument();
        Node createElement = ownerDocument.createElement("purses");
        Node createElement2 = ownerDocument.createElement("histories");
        for (TAPCASHPurse a : this.tapcashPurses) {
            createElement.appendChild(a.m4080a(ownerDocument));
        }
        f.appendChild(createElement);
        TAPCASHHistory[] tAPCASHHistoryArr = this.tapcashHistories;
        int length = tAPCASHHistoryArr.length;
        while (i < length) {
            createElement2.appendChild(tAPCASHHistoryArr[i].m4072a(ownerDocument));
            i++;
        }
        f.appendChild(createElement2);
        return f;
    }
}
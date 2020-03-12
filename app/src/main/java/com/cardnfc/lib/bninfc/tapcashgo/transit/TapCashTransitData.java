package com.cardnfc.lib.bninfc.tapcashgo.transit;

import android.annotation.SuppressLint;
import android.os.Parcel;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cardnfc.lib.bninfc.tapcashgo.card.Card;
import com.cardnfc.lib.bninfc.tapcashgo.card.tapcash.TAPCASHCard;
import com.cardnfc.lib.bninfc.tapcashgo.card.tapcash.TAPCASHTransaction;
import com.cardnfc.lib.bninfc.tapcashgo.card.tapcash.TAPCASHTransaction.TAPCASHTransactionenum;
import com.cardnfc.lib.bninfc.tapcashgo.tapcashgo783m;

import java.text.DateFormat;
import java.text.NumberFormat;

//import android.support.v7.p022a.C0503a.C0502j;

@SuppressLint("ParcelCreator")
public class TapCashTransitData extends TransitData {
    @NonNull
    public Creator<TapCashTransitData> transitData_ = new TransitData_(this);
    @Nullable
    private String b;
    private double c;
    private TapCashTrip[] tapCashTrips;
    private byte e;
    private byte f;
    private int g;

    class TransitData_ implements Creator<TapCashTransitData> {
        final  TapCashTransitData tapCashTransitData;

        TransitData_(TapCashTransitData tapCashTransitData) {
            this.tapCashTransitData = tapCashTransitData;
        }

        @NonNull
        public  TapCashTransitData createFromParcel(@NonNull Parcel parcel) {
            return tapCashTransitData(parcel);
        }

        @NonNull
        public  TapCashTransitData[] newArray(int i) {
            return m4153a(i);
        }

        @NonNull
        public TapCashTransitData tapCashTransitData(@NonNull Parcel parcel) {
            return new TapCashTransitData(parcel);
        }

        @NonNull
        public TapCashTransitData[] m4153a(int i) {
            return new TapCashTransitData[i];
        }
    }

    public static class TapCashTrip extends Trip {
        @NonNull
        public static Creator<TapCashTrip> tapCashTrip_ = new TapCashTrip_();
        @Nullable
        private final TAPCASHTransaction tapcashTransaction;
        @Nullable
        private final String c;

        static class TapCashTrip_ implements Creator<TapCashTrip> {
            TapCashTrip_() {
            }

            @NonNull
            public  TapCashTrip createFromParcel(@NonNull Parcel parcel) {
                return tapCashTrip(parcel);
            }

            @NonNull
            public  TapCashTrip[] newArray(int i) {
                return tapCashTrips(i);
            }

            @NonNull
            public TapCashTrip tapCashTrip(@NonNull Parcel parcel) {
                return new TapCashTrip(parcel);
            }

            @NonNull
            public TapCashTrip[] tapCashTrips(int i) {
                return new TapCashTrip[i];
            }
        }

        public TapCashTrip(TAPCASHTransaction tAPCASHTransaction, String str) {
            this.tapcashTransaction = tAPCASHTransaction;
            this.c = str;
        }

        public TapCashTrip(@NonNull Parcel parcel) {
            this.tapcashTransaction = (TAPCASHTransaction) parcel.readParcelable(TAPCASHTransaction.class.getClassLoader());
            this.c = parcel.readString();
        }

        public long Trip_a() {
            return (long) this.tapcashTransaction.c();
        }

        @NonNull
        public String Trip_b() {
            if (this.tapcashTransaction.tapcashTransactionenum() == TAPCASHTransactionenum.PURCHASE) {
                return "Purchase";
            }
            if (this.tapcashTransaction.tapcashTransactionenum() == TAPCASHTransactionenum.BLACKLIST) {
                return "Credit";
            }
            if (this.tapcashTransaction.tapcashTransactionenum() == TAPCASHTransactionenum.ATM_TOP_UP) {
                return "Direct Top Up";
            }
            if (this.tapcashTransaction.tapcashTransactionenum() == TAPCASHTransactionenum.CASH_TOP_UP) {
                return "Direct Top Up";
            }
            if (this.tapcashTransaction.tapcashTransactionenum() == TAPCASHTransactionenum.STATEMENT_FEE) {
                return "Statement Fee";
            }
            if (this.tapcashTransaction.tapcashTransactionenum() == TAPCASHTransactionenum.CARD_UPDATE) {
                return "Update Balance";
            }
            if (this.tapcashTransaction.tapcashTransactionenum() == TAPCASHTransactionenum.GRACE_PERIODE_FEE) {
                return "Grace Period Fee";
            }
            if (this.tapcashTransaction.tapcashTransactionenum() == TAPCASHTransactionenum.DISABLE_ATU) {
                return "Disable ATU";
            }
            if (this.tapcashTransaction.tapcashTransactionenum() == TAPCASHTransactionenum.DISABLE_PURSE) {
                return "Disable Purse";
            }
            if (this.tapcashTransaction.tapcashTransactionenum() == TAPCASHTransactionenum.ATM_REFUND || this.tapcashTransaction.tapcashTransactionenum() == TAPCASHTransactionenum.CASH_REFUND || this.tapcashTransaction.tapcashTransactionenum() == TAPCASHTransactionenum.REFUND_DISABLE_PURSE) {
                return "Refund";
            }
            if (this.tapcashTransaction.tapcashTransactionenum() == TAPCASHTransactionenum.OTHERS) {
                switch (this.tapcashTransaction.b()) {
                    case 259:
                        return "Change MAX Purse";
                    case 1026:
                        return "Change Expiry";
                    case 2049:
                        return "Change ATU Interval";
                    case 2307:
                        return "Change ATU Amount";
                    case 6145:
                        return "ATU Re-Installment";
                    case 9218:
                        return "Change ATU Expiry";
                    case 65536:
                        return "Enable Purse";
                    case 66562:
                        return "Card Activation";
                    case 131072:
                        return "ATU Activation";
                }
            }
            return "Unknown";
        }

        @NonNull
        public String Trip_c() {
            if (this.tapcashTransaction.tapcashTransactionenum() == TAPCASHTransactionenum.REFUND_DISABLE_PURSE) {
                return "Disable Purse";
            }
            return "";
        }

        @NonNull
        public String Trip_d() {
            if (this.tapcashTransaction.tapcashTransactionenum() == TAPCASHTransactionenum.OTHERS || this.tapcashTransaction.tapcashTransactionenum() == TAPCASHTransactionenum.DISABLE_ATU || this.tapcashTransaction.tapcashTransactionenum() == TAPCASHTransactionenum.DISABLE_PURSE || this.tapcashTransaction.tapcashTransactionenum() == TAPCASHTransactionenum.REFUND_DISABLE_PURSE) {
                return "";
            }
            NumberFormat instance = NumberFormat.getInstance();
            int i = -this.tapcashTransaction.b();
            if (i < 0) {
                return "Rp " + instance.format((long) (-i));
            }
            return "Rp " + instance.format((long) i);
        }

        @NonNull
        public trip_enum Trip_e() {
            if (this.tapcashTransaction.tapcashTransactionenum() == TAPCASHTransactionenum.CARD_UPDATE || this.tapcashTransaction.tapcashTransactionenum() == TAPCASHTransactionenum.BLACKLIST || this.tapcashTransaction.tapcashTransactionenum() == TAPCASHTransactionenum.CASH_TOP_UP || this.tapcashTransaction.tapcashTransactionenum() == TAPCASHTransactionenum.ATM_TOP_UP) {
                return trip_enum.TOPUPUPDATE;
            }
            if (this.tapcashTransaction.tapcashTransactionenum() == TAPCASHTransactionenum.PURCHASE) {
                return trip_enum.DEDUCT;
            }
            if (this.tapcashTransaction.tapcashTransactionenum() == TAPCASHTransactionenum.GRACE_PERIODE_FEE || this.tapcashTransaction.tapcashTransactionenum() == TAPCASHTransactionenum.STATEMENT_FEE) {
                return trip_enum.FEE;
            }
            if (this.tapcashTransaction.tapcashTransactionenum() == TAPCASHTransactionenum.DISABLE_PURSE || this.tapcashTransaction.tapcashTransactionenum() == TAPCASHTransactionenum.DISABLE_ATU || this.tapcashTransaction.tapcashTransactionenum() == TAPCASHTransactionenum.REFUND_DISABLE_PURSE) {
                return trip_enum.BANNED;
            }
            if (this.tapcashTransaction.tapcashTransactionenum() == TAPCASHTransactionenum.CASH_REFUND || this.tapcashTransaction.tapcashTransactionenum() == TAPCASHTransactionenum.ATM_REFUND) {
                return trip_enum.REFUND;
            }
            if (this.tapcashTransaction.tapcashTransactionenum() == TAPCASHTransactionenum.OTHERS && this.tapcashTransaction.b() == 66562) {
                return trip_enum.CARDACTIVATION;
            }
            return trip_enum.OTHER;
        }

        public boolean Trip_f() {
            return true;
        }

        public void writeToParcel(@NonNull Parcel parcel, int i) {
            parcel.writeParcelable(this.tapcashTransaction, i);
        }

        public int describeContents() {
            return 0;
        }
    }

    @NonNull
    private static String a(String str) {
//        switch (Integer.parseInt(str.substring(0, 3))) {
//            case Resources.getSystem().AppCompatTheme_seekBarStyle /*111*/:
//                return "TapCash";
//            default:
                return "TapCash";
       // }
    }

    public static boolean a(Card card) {
        if (!(card instanceof TAPCASHCard)) {
            return false;
        }
        TAPCASHCard tAPCASHCard = (TAPCASHCard) card;
        if (tAPCASHCard.TAPCASHPurse_b(0) == null || !tAPCASHCard.TAPCASHPurse_b(0).m4074b() || tAPCASHCard.TAPCASHPurse_a(0) == null || !tAPCASHCard.TAPCASHPurse_a(0).TAPCASHPurse_l()) {
            return false;
        }
        return true;
    }

    @NonNull
    public static tapcashgo789a tapcashgo789a_b(@NonNull Card card) {
        String a = tapcashgo783m.tapcashgo783m_a(((TAPCASHCard) card).TAPCASHPurse_a(0).TAPCASHPurse_e(), "<Error>");
        return new tapcashgo789a(a(a), a);
    }

    public TapCashTransitData(@NonNull Parcel parcel) {
        this.b = parcel.readString();
        this.c = parcel.readDouble();
        this.e = parcel.readByte();
        this.f = parcel.readByte();
        this.g = parcel.readInt();
        this.tapCashTrips = new TapCashTrip[parcel.readInt()];
        parcel.readTypedArray(this.tapCashTrips, TapCashTrip.tapCashTrip_);
    }

    public TapCashTransitData(Card card) {
        TAPCASHCard tAPCASHCard = (TAPCASHCard) card;
        this.b = tapcashgo783m.tapcashgo783m_a(tAPCASHCard.TAPCASHPurse_a(0).TAPCASHPurse_e(), "<Error>");
        this.c = (double) tAPCASHCard.TAPCASHPurse_a(0).TAPCASHPurse_c();
        this.e = tAPCASHCard.TAPCASHPurse_a(0).TAPCASHPurse_i();
        this.f = tAPCASHCard.TAPCASHPurse_a(0).TAPCASHPurse_b();
        this.g = tAPCASHCard.TAPCASHPurse_a(0).TAPCASHPurse_g();
        this.tapCashTrips = tapCashTrips(tAPCASHCard);
    }

    @NonNull
    public String a() {
        return a(this.b);
    }

    @NonNull
    public String TransitDatastring_b() {
        return "Rp " + NumberFormat.getInstance().format(this.c);
    }

    public byte TransitDatabyte_c() {
        return this.e;
    }

    public byte TransitDatabyte_d() {
        return this.f;
    }

    @NonNull
    public String TransitDatastring_e() {
        return "Valid Thru  " + DateFormat.getDateInstance(2).format(Long.valueOf(((long) this.g) * 1000));
    }

    @NonNull
    public String TransitDatastring_f() {
        return this.b.substring(0, 4) + " " + this.b.substring(4, 8) + " " + this.b.substring(8, 12) + " " + this.b.substring(12, 16);
    }

    public Trip[] trips() {
        return this.tapCashTrips;
    }

    @NonNull
    private TapCashTrip[] tapCashTrips(@NonNull TAPCASHCard tAPCASHCard) {
        int i = 0;
        TAPCASHTransaction[] a = tAPCASHCard.TAPCASHPurse_b(0).m4073a();
        if (a == null) {
            return new TapCashTrip[0];
        }
        TapCashTrip[] tapCashTripArr = new TapCashTrip[a.length];
        while (i < tapCashTripArr.length) {
            tapCashTripArr[i] = new TapCashTrip(a[i], a());
            i++;
        }
        return tapCashTripArr;
    }

    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(this.b);
        parcel.writeDouble(this.c);
        parcel.writeByte(this.e);
        parcel.writeByte(this.f);
        parcel.writeInt(this.g);
        parcel.writeInt(this.tapCashTrips.length);
        parcel.writeTypedArray(this.tapCashTrips, i);
    }
}

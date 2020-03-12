package com.cardnfc.lib.bninfc.tapcashgo.card.tapcash;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cardnfc.lib.bninfc.tapcashgo.tapcashgo783m;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TAPCASHPurse
  implements Parcelable
{
  public static final Parcelable.Creator<TAPCASHPurse> CREATOR = new TAPCASHPurse_();
  private final int a;
  private final byte b;
  private final byte c;
  private final int d;
  private final int e;
  @Nullable
  private final byte[] f;
  @Nullable
  private final byte[] g;
  private final int h;
  private final int i;
  private final int j;
  @Nullable
  private final byte[] k;
  private final byte l;
  private final byte m;
  private final int n;
  private final int o;
  @Nullable
  private final TAPCASHTransaction tapcashTransaction;
  @Nullable
  private final byte[] q;
  private final byte r;
  private final boolean s;
  private final String t;
  
  public TAPCASHPurse(int paramInt1, byte paramByte1, byte paramByte2, int paramInt2, int paramInt3, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt4, int paramInt5, int paramInt6, byte[] paramArrayOfByte3, byte paramByte3, byte paramByte4, int paramInt7, int paramInt8, TAPCASHTransaction paramTAPCASHTransaction, byte[] paramArrayOfByte4, byte paramByte5)
  {
    this.a = paramInt1;
    this.b = paramByte1;
    this.c = paramByte2;
    this.d = paramInt2;
    this.e = paramInt3;
    this.f = paramArrayOfByte1;
    this.g = paramArrayOfByte2;
    this.h = paramInt4;
    this.i = paramInt5;
    this.j = paramInt6;
    this.k = paramArrayOfByte3;
    this.l = paramByte3;
    this.m = paramByte4;
    this.n = paramInt7;
    this.o = paramInt8;
    this.tapcashTransaction = paramTAPCASHTransaction;
    this.q = paramArrayOfByte4;
    this.r = paramByte5;
    this.s = true;
    this.t = "";
  }
  
  public TAPCASHPurse(int paramInt, String paramString)
  {
    this.a = paramInt;
    this.b = 0;
    this.c = 0;
    this.d = 0;
    this.e = 0;
    this.f = null;
    this.g = null;
    this.h = 0;
    this.i = 0;
    this.j = 0;
    this.k = null;
    this.l = 0;
    this.m = 0;
    this.n = 0;
    this.o = 0;
    this.tapcashTransaction = null;
    this.q = null;
    this.r = 0;
    this.s = false;
    this.t = paramString;
  }
  
  public TAPCASHPurse(int paramInt, @Nullable byte[] paramArrayOfByte)
  {
    int i = 0;
    if (paramArrayOfByte == null)
    {
      paramArrayOfByte = new byte[''];
      this.s = false;
      this.t = "";
    }
    else
    {
      this.s = true;
      this.t = "";
    }
    this.a = paramInt;
    this.b = paramArrayOfByte[0];
    this.c = paramArrayOfByte[1];
    int j = paramArrayOfByte[2] << 16 & 0xFF0000 | paramArrayOfByte[3] << 8 & 0xFF00 | paramArrayOfByte[4] & 0xFF;
    paramInt = j;
    if ((paramArrayOfByte[2] & 0x80) != 0) {
      paramInt = j | 0xFF000000;
    }
    this.d = paramInt;
    j = paramArrayOfByte[5] << 16 & 0xFF0000 | paramArrayOfByte[6] << 8 & 0xFF00 | paramArrayOfByte[7] & 0xFF;
    paramInt = j;
    if ((paramArrayOfByte[5] & 0x80) != 0) {
      paramInt = j | 0xFF000000;
    }
    this.e = paramInt;
    this.f = new byte[8];
    paramInt = 0;
    while (paramInt < this.f.length)
    {
      this.f[paramInt] = paramArrayOfByte[(paramInt + 8)];
      paramInt += 1;
    }
    this.g = new byte[8];
    paramInt = 0;
    while (paramInt < this.g.length)
    {
      this.g[paramInt] = paramArrayOfByte[(paramInt + 16)];
      paramInt += 1;
    }
    this.h = ((paramArrayOfByte[24] << 8 & 0xFF00 | paramArrayOfByte[25] << 0 & 0xFF) * 86400 + 788947200);
    this.i = (788947200 + 86400 * (paramArrayOfByte[26] << 8 & 0xFF00 | paramArrayOfByte[27] << 0 & 0xFF));
    this.j = (paramArrayOfByte[28] << 24 & 0xFF000000 | paramArrayOfByte[29] << 16 & 0xFF0000 | paramArrayOfByte[30] << 8 & 0xFF00 | paramArrayOfByte[31] << 0 & 0xFF);
    this.k = new byte[8];
    paramInt = 0;
    while (paramInt < 8)
    {
      this.k[paramInt] = paramArrayOfByte[(paramInt + 32)];
      paramInt += 1;
    }
    this.l = paramArrayOfByte[40];
    this.m = paramArrayOfByte[71];
    this.n = (paramArrayOfByte[41] & 0xFF);
    this.o = (paramArrayOfByte[42] << 24 & 0xFF000000 | paramArrayOfByte[43] << 16 & 0xFF0000 | paramArrayOfByte[44] << 8 & 0xFF00 | paramArrayOfByte[45] << 0 & 0xFF);
    byte[] arrayOfByte = new byte[16];
    paramInt = 0;
    while (paramInt < arrayOfByte.length)
    {
      arrayOfByte[paramInt] = paramArrayOfByte[(paramInt + 46)];
      paramInt += 1;
    }
    this.tapcashTransaction = new TAPCASHTransaction(arrayOfByte);
    this.q = new byte[this.n];
    paramInt = i;
    while (paramInt < this.q.length)
    {
      this.q[paramInt] = paramArrayOfByte[(paramInt + 62)];
      paramInt += 1;
    }
    this.r = paramArrayOfByte[(this.n + 62)];
  }
  
  @NonNull
  public static TAPCASHPurse m4078a(@NonNull Element paramElement)
  {
    int i = Integer.parseInt(paramElement.getAttribute("id"));
    if (paramElement.getAttribute("valid").equals("false")) {
      return new TAPCASHPurse(i, paramElement.getAttribute("error"));
    }
    return new TAPCASHPurse(i, Byte.parseByte(paramElement.getAttribute("cepas-version")), Byte.parseByte(paramElement.getAttribute("purse-status")), Integer.parseInt(paramElement.getAttribute("purse-balance")), Integer.parseInt(paramElement.getAttribute("auto-load-amount")), tapcashgo783m.tapcashgo783m_a(paramElement.getAttribute("can")), tapcashgo783m.tapcashgo783m_a(paramElement.getAttribute("csn")), Integer.parseInt(paramElement.getAttribute("purse-expiry-date")), Integer.parseInt(paramElement.getAttribute("purse-creation-date")), Integer.parseInt(paramElement.getAttribute("last-credit-transaction-trp")), tapcashgo783m.tapcashgo783m_a(paramElement.getAttribute("last-credit-transaction-header")), Byte.parseByte(paramElement.getAttribute("logfile-record-count")), Byte.parseByte(paramElement.getAttribute("key-set")), Integer.parseInt(paramElement.getAttribute("issuer-data-length")), Integer.parseInt(paramElement.getAttribute("last-transaction-trp")), TAPCASHTransaction.tapcashTransaction((Element)paramElement.getElementsByTagName("transaction").item(0)), tapcashgo783m.tapcashgo783m_a(paramElement.getAttribute("issuer-specific-data")), Byte.parseByte(paramElement.getAttribute("last-transaction-debit-options")));
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public int m4079a()
  {
    return this.a;
  }
  
  public Element m4080a(@NonNull Document paramDocument)
  {
    Element localElement = paramDocument.createElement("purse");
    if (this.s)
    {
      localElement.setAttribute("valid", "true");
      localElement.setAttribute("id", Integer.toString(this.a));
      localElement.setAttribute("cepas-version", Byte.toString(this.b));
      localElement.setAttribute("purse-status", Byte.toString(this.c));
      localElement.setAttribute("purse-balance", Integer.toString(this.d));
      localElement.setAttribute("auto-load-amount", Integer.toString(this.e));
      localElement.setAttribute("can", tapcashgo783m.tapcashgo783m_a(this.f));
      localElement.setAttribute("csn", tapcashgo783m.tapcashgo783m_a(this.g));
      localElement.setAttribute("purse-creation-date", Integer.toString(this.i));
      localElement.setAttribute("purse-expiry-date", Integer.toString(this.h));
      localElement.setAttribute("last-credit-transaction-trp", Integer.toString(this.j));
      localElement.setAttribute("last-credit-transaction-header", tapcashgo783m.tapcashgo783m_a(this.k));
      localElement.setAttribute("logfile-record-count", Byte.toString(this.l));
      localElement.setAttribute("key-set", Byte.toString(this.m));
      localElement.setAttribute("issuer-data-length", Integer.toString(this.n));
      localElement.setAttribute("last-transaction-trp", Integer.toString(this.o));
      localElement.setAttribute("issuer-specific-data", tapcashgo783m.tapcashgo783m_a(this.q));
      localElement.setAttribute("last-transaction-debit-options", Byte.toString(this.r));
      localElement.appendChild(this.tapcashTransaction.TAPCASHTransactionElement(paramDocument));
      return localElement;
    }
    localElement.setAttribute("id", Integer.toString(this.a));
    localElement.setAttribute("valid", "false");
    localElement.setAttribute("error", TAPCASHPurse_m());
    return localElement;
  }
  
  public byte TAPCASHPurse_b()
  {
    return this.c;
  }
  
  public int TAPCASHPurse_c()
  {
    return this.d;
  }
  
  public int TAPCASHPurse_d()
  {
    return this.e;
  }
  
  @Nullable
  public byte[] TAPCASHPurse_e()
  {
    return this.f;
  }
  
  @Nullable
  public byte[] TAPCASHPurse_f()
  {
    return this.g;
  }
  
  public int TAPCASHPurse_g()
  {
    return this.h;
  }
  
  public int TAPCASHPurse_h()
  {
    return this.i;
  }
  
  public byte TAPCASHPurse_i()
  {
    return this.l;
  }
  
  public byte TAPCASHPurse_j()
  {
    return this.m;
  }
  
  public int TAPCASHPurse_k()
  {
    return this.o;
  }
  
  public boolean TAPCASHPurse_l()
  {
    return this.s;
  }
  
  public String TAPCASHPurse_m()
  {
    return this.t;
  }
  
  public void writeToParcel(@NonNull Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.a);
    if (this.s)
    {
      paramParcel.writeInt(1);
      paramParcel.writeByte(this.b);
      paramParcel.writeByte(this.c);
      paramParcel.writeInt(this.d);
      paramParcel.writeInt(this.e);
      paramParcel.writeInt(this.f.length);
      paramParcel.writeByteArray(this.f);
      paramParcel.writeInt(this.g.length);
      paramParcel.writeByteArray(this.g);
      paramParcel.writeInt(this.h);
      paramParcel.writeInt(this.i);
      paramParcel.writeInt(this.j);
      paramParcel.writeInt(this.k.length);
      paramParcel.writeByteArray(this.k);
      paramParcel.writeByte(this.l);
      paramParcel.writeByte(this.m);
      paramParcel.writeInt(this.n);
      paramParcel.writeInt(this.o);
      paramParcel.writeParcelable(this.tapcashTransaction, paramInt);
      paramParcel.writeInt(this.q.length);
      paramParcel.writeByteArray(this.q);
      paramParcel.writeByte(this.r);
      return;
    }
    paramParcel.writeInt(0);
    paramParcel.writeString(this.t);
  }
  
  static class TAPCASHPurse_
    implements Parcelable.Creator<TAPCASHPurse>
  {
    @Nullable
    public TAPCASHPurse createFromParcel(@NonNull Parcel paramParcel)
    {
      return m4076a(paramParcel);
    }
    
    @Nullable
    public TAPCASHPurse m4076a(@NonNull Parcel paramParcel)
    {
      int i = paramParcel.readInt();
      if (paramParcel.readInt() == 0) {
        return new TAPCASHPurse(i, paramParcel.readString());
      }
      byte b1 = paramParcel.readByte();
      byte b2 = paramParcel.readByte();
      int j = paramParcel.readInt();
      int k = paramParcel.readInt();
      byte[] arrayOfByte1 = new byte[paramParcel.readInt()];
      paramParcel.readByteArray(arrayOfByte1);
      byte[] arrayOfByte2 = new byte[paramParcel.readInt()];
      paramParcel.readByteArray(arrayOfByte2);
      int m = paramParcel.readInt();
      int n = paramParcel.readInt();
      int i1 = paramParcel.readInt();
      byte[] arrayOfByte3 = new byte[paramParcel.readInt()];
      paramParcel.readByteArray(arrayOfByte3);
      byte b3 = paramParcel.readByte();
      byte b4 = paramParcel.readByte();
      int i2 = paramParcel.readInt();
      int i3 = paramParcel.readInt();
      TAPCASHTransaction localTAPCASHTransaction = (TAPCASHTransaction)paramParcel.readParcelable(TAPCASHTransaction.class.getClassLoader());
      byte[] arrayOfByte4 = new byte[paramParcel.readInt()];
      paramParcel.readByteArray(arrayOfByte4);
      return new TAPCASHPurse(i, b1, b2, j, k, arrayOfByte1, arrayOfByte2, m, n, i1, arrayOfByte3, b3, b4, i2, i3, localTAPCASHTransaction, arrayOfByte4, paramParcel.readByte());
    }
    
    @NonNull
    public TAPCASHPurse[] m4077a(int paramInt)
    {
      return new TAPCASHPurse[paramInt];
    }
    
    @NonNull
    public TAPCASHPurse[] newArray(int paramInt)
    {
      return m4077a(paramInt);
    }
  }
}
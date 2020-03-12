package com.cardnfc.lib.bninfc.tapcashgo.card.tapcash;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class TAPCASHHistory
  implements Parcelable
{
  public static final Parcelable.Creator<TAPCASHHistory> CREATOR = new C07631();
  @NonNull
  private static TAPCASHTransaction[] f2311a = new TAPCASHTransaction[0];
  private int f2312b;
  private TAPCASHTransaction[] f2313c;
  private final boolean f2314d;
  private final String f2315e;
  
  public TAPCASHHistory(int paramInt, String paramString)
  {
    this.f2312b = paramInt;
    this.f2315e = paramString;
    this.f2314d = false;
  }
  
  public TAPCASHHistory(int paramInt, @Nullable byte[] paramArrayOfByte)
  {
    this.f2312b = paramInt;
    if (paramArrayOfByte != null)
    {
      this.f2314d = true;
      this.f2315e = "";
      this.f2313c = new TAPCASHTransaction[paramArrayOfByte.length / 16];
      paramInt = 0;
      while (paramInt < paramArrayOfByte.length)
      {
        byte[] arrayOfByte = new byte[16];
        int i = 0;
        while (i < arrayOfByte.length)
        {
          arrayOfByte[i] = paramArrayOfByte[(paramInt + i)];
          i += 1;
        }
        this.f2313c[(paramInt / arrayOfByte.length)] = new TAPCASHTransaction(arrayOfByte);
        paramInt += 16;
      }
      return;
    }
    this.f2314d = false;
    this.f2315e = "";
    this.f2313c = f2311a;
  }
  
  public TAPCASHHistory(int paramInt, TAPCASHTransaction[] paramArrayOfTAPCASHTransaction)
  {
    this.f2313c = paramArrayOfTAPCASHTransaction;
    this.f2312b = paramInt;
    this.f2314d = true;
    this.f2315e = "";
  }
  
  @NonNull
  public static TAPCASHHistory m4071a(@NonNull Element paramElement)
  {
//    int j = Integer.parseInt(paramElement.getAttribute("id"));
//    if (paramElement.getAttribute("valid").equals("false")) {
//      return new TAPCASHHistory(j, paramElement.getAttribute("error"));
//    }
//    paramElement = paramElement.getElementsByTagName("transaction");
//    TAPCASHTransaction[] arrayOfTAPCASHTransaction = new TAPCASHTransaction[paramElement.getLength()];
//    int i = 0;
//    while (i < paramElement.getLength())
//    {
//      arrayOfTAPCASHTransaction[i] = TAPCASHTransaction.tapcashTransaction((Element)paramElement.item(i));
//      i += 1;
//    }
//    return new TAPCASHHistory(j, arrayOfTAPCASHTransaction);
    int parseInt = Integer.parseInt(paramElement.getAttribute("id"));
    if (paramElement.getAttribute("valid").equals("false")) {
      return new TAPCASHHistory(parseInt, paramElement.getAttribute("error"));
    }
    NodeList elementx = paramElement.getElementsByTagName("transaction");
    TAPCASHTransaction[] tAPCASHTransactionArr = new TAPCASHTransaction[elementx.getLength()];
    for (int i = 0; i < elementx.getLength(); i++) {
      tAPCASHTransactionArr[i] = TAPCASHTransaction.tapcashTransaction((Element) elementx.item(i));
    }
    return new TAPCASHHistory(parseInt, tAPCASHTransactionArr);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public Element m4072a(@NonNull Document paramDocument)
  {
    Element localElement = paramDocument.createElement("history");
    localElement.setAttribute("id", Integer.toString(this.f2312b));
    if (this.f2314d)
    {
      localElement.setAttribute("valid", "true");
      TAPCASHTransaction[] arrayOfTAPCASHTransaction = this.f2313c;
      int j = arrayOfTAPCASHTransaction.length;
      int i = 0;
      while (i < j)
      {
        localElement.appendChild(arrayOfTAPCASHTransaction[i].TAPCASHTransactionElement(paramDocument));
        i += 1;
      }
    }
    localElement.setAttribute("valid", "false");
    localElement.setAttribute("error", m4075c());
    return localElement;
  }
  
  public TAPCASHTransaction[] m4073a()
  {
    return this.f2313c;
  }
  
  public boolean m4074b()
  {
    return this.f2314d;
  }
  
  public String m4075c()
  {
    return this.f2315e;
  }
  
  public void writeToParcel(@NonNull Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.f2312b);
    boolean bool = this.f2314d;
    int i = 0;
    if (bool)
    {
      paramParcel.writeInt(1);
      paramParcel.writeInt(this.f2313c.length);
      while (i < this.f2313c.length)
      {
        paramParcel.writeParcelable(this.f2313c[i], paramInt);
        i += 1;
      }
      return;
    }
    paramParcel.writeInt(0);
    paramParcel.writeString(this.f2315e);
  }
  
  static class C07631
    implements Parcelable.Creator<TAPCASHHistory>
  {
    @Nullable
    public TAPCASHHistory createFromParcel(@NonNull Parcel paramParcel)
    {
      return m4069a(paramParcel);
    }
    
    @Nullable
    public TAPCASHHistory m4069a(@NonNull Parcel paramParcel)
    {
      int j = paramParcel.readInt();
      if (paramParcel.readInt() != 1) {
        return new TAPCASHHistory(j, paramParcel.readString());
      }
      TAPCASHTransaction[] arrayOfTAPCASHTransaction = new TAPCASHTransaction[paramParcel.readInt()];
      int i = 0;
      while (i < arrayOfTAPCASHTransaction.length)
      {
        arrayOfTAPCASHTransaction[i] = ((TAPCASHTransaction)paramParcel.readParcelable(TAPCASHTransaction.class.getClassLoader()));
        i += 1;
      }
      return new TAPCASHHistory(j, arrayOfTAPCASHTransaction);
    }
    
    @NonNull
    public TAPCASHHistory[] m4070a(int paramInt)
    {
      return new TAPCASHHistory[paramInt];
    }
    
    @NonNull
    public TAPCASHHistory[] newArray(int paramInt)
    {
      return m4070a(paramInt);
    }
  }
}
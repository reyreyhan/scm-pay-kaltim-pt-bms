package com.cardnfc.lib.bninfc.tapcashgo.card;

import android.nfc.Tag;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cardnfc.lib.bninfc.tapcashgo.card.tapcash.TAPCASHCard;
import com.cardnfc.lib.bninfc.tapcashgo.transit.tapcashgo789a;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Date;

import javax.xml.parsers.DocumentBuilderFactory;

public abstract class Card
  implements Parcelable
{
  private byte[] Cardbyte_a;
  private Date Carddate_b;
  
  protected Card(byte[] paramArrayOfByte, Date paramDate)
  {
    this.Cardbyte_a = paramArrayOfByte;
    this.Carddate_b = paramDate;
  }
  
  @Nullable
  public static Card card(String paramString)
  {
    return null;
  }
  
  @Nullable
  public static Card card(byte[] bArr, @NonNull Tag tag, boolean z, String str) throws Exception {
    if (tapcashgo2a.tapcashgo2aboolean_b(tag.getTechList(), "android.nfc.tech.NfcA")) {
      return TAPCASHCard.tapcashCard(tag, z, str);
    }
    throw new Exception("Kartu tidak dikenal / \nBukan kartu BNI.");
  }
  
  public final int describeContents()
  {
    return 0;
  }
  
  public byte[] Cardbyte_b()
  {
    return this.Cardbyte_a;
  }
  
  public Date Carddate_c()
  {
    return this.Carddate_b;
  }
  
  @NonNull
  public abstract Cardenum_a cardenum_a();
  
  @Nullable
  public abstract tapcashgo789a tapcashgo789a();
  
  public Element Cardelement_f()
  {
    try
    {
      Object localObject = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
      ((Document)localObject).appendChild(((Document)localObject).createElement("card"));
      localObject = ((Document)localObject).getDocumentElement();
      return (Element)localObject;
    }
    catch (Throwable localThrowable)
    {
      throw new RuntimeException(localThrowable);
    }
  }
  
  public void writeToParcel(@NonNull Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.Cardbyte_a.length);
    paramParcel.writeByteArray(this.Cardbyte_a);
    paramParcel.writeLong(this.Carddate_b.getTime());
  }
  
  public static enum Cardenum_a
  {
    TAPCASH(0);
    
    private int b;
    
    private Cardenum_a(int paramInt)
    {
      this.b = paramInt;
    }
    
    public int Cardint_a()
    {
      return this.b;
    }
    
    @NonNull
    public String toString()
    {
      if (this.b != 0) {
        return "Unknown";
      }
      return "TAPCASH";
    }
  }
}
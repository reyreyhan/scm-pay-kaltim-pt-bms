package com.cardnfc.lib.bninfc.tapcashgo.transit;

import androidx.annotation.NonNull;

public class tapcashgo789a
{
  private final String a;
  private final String b;
  
  public tapcashgo789a(String paramString1, String paramString2)
  {
    this.a = paramString1;
    this.b = paramString2;
  }
  
  @NonNull
  public String tapcashgo789a_a()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.b.substring(0, 4));
    localStringBuilder.append(" ");
    localStringBuilder.append(this.b.substring(4, 8));
    localStringBuilder.append(" ");
    localStringBuilder.append(this.b.substring(8, 12));
    localStringBuilder.append(" ");
    localStringBuilder.append(this.b.substring(12, 16));
    return localStringBuilder.toString();
  }
}
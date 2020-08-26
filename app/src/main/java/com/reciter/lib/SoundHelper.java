package com.reciter.lib;

import androidx.annotation.NonNull;
import android.util.Log;

import com.bm.main.scm.R;

public class SoundHelper
{
  private static final String TAG = "mandiri";
  @NonNull
  private static String[] nomina = { "", "satu", "dua", "tiga", "empat", "lima", "enam", "tujuh", "delapan", "sembilan", "sepuluh", "sebelas" };
  
  public static int getRawId(@NonNull String paramString)

  {

    Log.d(TAG, "getRawId: "+paramString+" "+paramString.hashCode());
    int i = -1;
    switch (paramString.hashCode())
    {
    default: 
      break;
    case 1983538801: 
      if (paramString.equals("seratus")) {
        i = 14;
      }
      break;
    case 1982279880: 
      if (paramString.equals("sepuluh")) {
        i = 10;
      }
      break;
    case 1968873321: 
      if (paramString.equals("sebelas")) {
        i = 11;
      }
      break;
    case 1550339879: 
      if (paramString.equals("delapan")) {
        i = 8;
      }
      break;
    case 1217601655: 
      if (paramString.equals("sembilan")) {
        i = 9;
      }
      break;
    case 110719580: 
      if (paramString.equals("tujuh")) {
        i = 7;
      }
      break;
    case 108286339: 
      if (paramString.equals("ratus")) {
        i = 15;
      }
      break;
    case 107027418: 
      if (paramString.equals("puluh")) {
        i = 13;
      }
      break;
    case 96633595: 
      if (paramString.equals("empat")) {
        i = 4;
      }
      break;
    case 93620859: 
      if (paramString.equals("belas")) {
        i = 12;
      }
      break;
    case 3559951: 
      if (paramString.equals("tiga")) {
        i = 3;
      }
      break;
    case 3522895: 
      if (paramString.equals("satu")) {
        i = 1;
      }
      break;
    case 3500234: 
      if (paramString.equals("ribu")) {
        i = 17;
      }
      break;
    case 3321809: 
      if (paramString.equals("lima")) {
        i = 5;
      }
      break;
    case 3273976: 
      if (paramString.equals("juta")) {
        i = 18;
      }
      break;
    case 3117717: 
      if (paramString.equals("enam")) {
        i = 6;
      }
      break;
    case 109259: 
      if (paramString.equals("nol")) {
        i = 0;
      }
      break;
    case 99824: 
      if (paramString.equals("dua")) {
        i = 2;
      }
      break;
    case -905839076: 
      if (paramString.equals("seribu")) {
        i = 16;
      }
      break;
    case -919751517: 
      if (paramString.equals("rupiah")) {
        i = 19;
      }
      break;
    }

    switch (i)
    {
    default:
      return R.raw.nol;
    case 19: 
      return R.raw.rupiah;
    case 18: 
      return R.raw.juta;
    case 17: 
      return R.raw.ribu;
    case 16: 
      return R.raw.seribu;
    case 15: 
      return R.raw.ratus;
    case 14: 
      return R.raw.seratus;
    case 13: 
      return R.raw.puluh;
    case 12: 
      return R.raw.belas;
    case 11: 
      return R.raw.sebelas;
    case 10: 
      return R.raw.sepuluh;
    case 9: 
      return R.raw.sembilan;
    case 8: 
      return R.raw.delapan;
    case 7: 
      return R.raw.tujuh;
    case 6: 
      return R.raw.enam;
    case 5: 
      return R.raw.lima;
    case 4: 
      return R.raw.empat;
    case 3: 
      return R.raw.tiga;
    case 2: 
      return R.raw.dua;
    case 1: 
      return R.raw.satu;
    }

  }
  
  @NonNull
  public static String[] setSoundStack(@NonNull String paramString)
  {
    if (paramString.equals("0")) {
      paramString = "nol";
    } else {
      paramString = terbilang(Integer.valueOf(paramString)).replace("  ", " ").trim();
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append(" rupiah");
    return localStringBuilder.toString().split(" ");
  }
  
  @NonNull
  private static String terbilang(int paramInt)
  {
    paramInt = Math.abs(paramInt);
    StringBuilder localStringBuilder;
    if (paramInt < 12)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(" ");
      localStringBuilder.append(new String[] { "", "satu", "dua", "tiga", "empat", "lima", "enam", "tujuh", "delapan", "sembilan", "sepuluh", "sebelas" }[paramInt]);
      return localStringBuilder.toString();
    }
    if (paramInt < 20)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(String.valueOf(terbilang(paramInt - 10)));
      localStringBuilder.append(" belas");
      return localStringBuilder.toString();
    }
    if (paramInt < 100)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(String.valueOf(terbilang(paramInt / 10)));
      localStringBuilder.append(" puluh");
      localStringBuilder.append(terbilang(paramInt % 10));
      return localStringBuilder.toString();
    }
    if (paramInt < 200)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(" seratus");
      localStringBuilder.append(terbilang(paramInt - 100));
      return localStringBuilder.toString();
    }
    if (paramInt < 1000)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(String.valueOf(terbilang(paramInt / 100)));
      localStringBuilder.append(" ratus");
      localStringBuilder.append(terbilang(paramInt % 100));
      return localStringBuilder.toString();
    }
    if (paramInt < 2000)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(" seribu");
      localStringBuilder.append(terbilang(paramInt - 1000));
      return localStringBuilder.toString();
    }
    if (paramInt < 1000000)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(String.valueOf(terbilang(paramInt / 1000)));
      localStringBuilder.append(" ribu");
      localStringBuilder.append(terbilang(paramInt % 1000));
      return localStringBuilder.toString();
    }
    if (paramInt < 1000000000)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(String.valueOf(terbilang(paramInt / 1000000)));
      localStringBuilder.append(" juta");
      localStringBuilder.append(terbilang(paramInt % 1000000));
      return localStringBuilder.toString();
    }
    return "";
  }
}


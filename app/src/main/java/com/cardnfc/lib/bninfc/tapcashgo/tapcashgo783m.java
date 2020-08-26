package com.cardnfc.lib.bninfc.tapcashgo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import com.bm.main.scm.R;

import org.w3c.dom.Node;

import java.io.StringWriter;
import java.text.NumberFormat;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class tapcashgo783m
{
  @NonNull
  public static String tapcashgo783m_a(@NonNull byte[] paramArrayOfByte)
  {
    String str = "";
    int i = 0;
    int j = paramArrayOfByte.length;
    while (i < j)
    {
      int k = paramArrayOfByte[i];
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(str);
      localStringBuilder.append(Integer.toString((k & 0xFF) + 256, 16).substring(1));
      str = localStringBuilder.toString();
      i += 1;
    }
    return str;
  }
  
  public static String tapcashgo783m_a(@NonNull byte[] paramArrayOfByte, String paramString)
  {
    try
    {
//      paramArrayOfByte = tapcashgo783m_a(paramArrayOfByte);
//      return paramArrayOfByte;
      return tapcashgo783m_a(paramArrayOfByte);
    }
    catch (Exception e)
    {
     // for (;;) {}
      return paramString;
    }

  }

  public static void tapcashgo783m_a(@NonNull final AppCompatActivity activity, @NonNull NfcAdapter nfcAdapter) {
    if (nfcAdapter.isEnabled()) {
      return;
    }
    new AlertDialog.Builder(activity).setTitle(R.string.nfc_off_error).setMessage(R.string.turn_on_nfc).setCancelable(true).setNegativeButton("Tutup", new DialogInterface.OnClickListener()
    {
      public void onClick(@NonNull DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.dismiss();
      }
    }).setNeutralButton("Setting", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        activity.startActivity(new Intent("android.settings.WIRELESS_SETTINGS"));
      }
    }).show();
  }

  @NonNull
  public static byte[] tapcashgo783m_a(@NonNull String paramString)
  {
//    if (paramString.length() % 2 != 0)
//    {
//      StringBuilder localObject = new StringBuilder();
//      ((StringBuilder)localObject).append("Bad input string: ");
//      ((StringBuilder)localObject).append(paramString);
//      throw new IllegalArgumentException(((StringBuilder)localObject).toString());
//    }
//    int j = paramString.length();
//    Object localObject = new byte[j / 2];
//    int i = 0;
//    while (i < j)
//    {
//      localObject[(i / 2)] = ((byte)((Character.digit(paramString.charAt(i), 16) << 4) + Character.digit(paramString.charAt(i + 1), 16)));
//      i += 2;
//    }
//    return (byte[])localObject;
    if (paramString.length() % 2 != 0) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append("Bad input string: ");
      stringBuilder.append(paramString);
      throw new IllegalArgumentException(stringBuilder.toString());
    }
    int length = paramString.length();
    byte[] bArr = new byte[(length / 2)];
    for (int i = 0; i < length; i += 2) {
      bArr[i / 2] = (byte) ((Character.digit(paramString.charAt(i), 16) << 4) + Character.digit(paramString.charAt(i + 1), 16));
    }
    return bArr;
  }

  @NonNull
  public static String tapcashgo783m_a(Node paramNode)
  {
    DOMSource paramNodeS = new DOMSource(paramNode);
    StringWriter localStringWriter = new StringWriter();
    StreamResult localStreamResult = new StreamResult(localStringWriter);
    Transformer localTransformer = null;
    try {
      localTransformer = TransformerFactory.newInstance().newTransformer();
    } catch (TransformerConfigurationException e) {
      e.printStackTrace();
    }
    localTransformer.setOutputProperty("indent", "yes");
    localTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
    localTransformer.setURIResolver(null);
    try {
      localTransformer.transform(paramNodeS, localStreamResult);
    } catch (TransformerException e) {
      e.printStackTrace();
    }
    return localStringWriter.getBuffer().toString();
  }
  public static String tapcashgo783m_a(@NonNull Throwable th) {
    String localizedMessage = th.getLocalizedMessage();
    if (localizedMessage == null) {
      localizedMessage = th.getMessage();
    }
    if (localizedMessage == null) {
      localizedMessage = th.toString();
    }
    if (th.getCause() == null) {
      return localizedMessage;
    }
    String localizedMessage2 = th.getCause().getLocalizedMessage();
    if (localizedMessage2 == null) {
      localizedMessage2 = th.getCause().getMessage();
    }
    if (localizedMessage2 == null) {
      localizedMessage2 = th.getCause().toString();
    }
    if (localizedMessage2 != null) {
      return localizedMessage + ": " + localizedMessage2;
    }
    return localizedMessage;
  }
  public static void tapcashgo783m_b(@NonNull final AppCompatActivity activity, @NonNull Exception exception) {
    try {
      Log.e(activity.getClass().getName(), tapcashgo783m.tapcashgo783m_a((Throwable) exception));
      exception.printStackTrace();
      new AlertDialog.Builder(activity).setMessage(tapcashgo783m.tapcashgo783m_a((Throwable) exception)).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialogInterface, int i) {
          activity.finish();
        }
      }).show();
    } catch (WindowManager.BadTokenException e) {
    }
  }

    @NonNull
    public static byte[] tapcashgo783m_b(@NonNull String str) {
        if (str.length() % 2 > 0) {
            str = "0" + str;
        }
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int parseInt = Integer.parseInt(str.substring(i * 2, (i * 2) + 2), 16);
            if (parseInt < 0) {
                parseInt *= -1;
            }
            bArr[i] = new Integer(parseInt).byteValue();
        }
        return bArr;
    }
    @NonNull
    public static String tapcashgo783m_b(@NonNull byte[] bArr) {
//        StringBuilder stringBuilder = new StringBuilder(32);
//        for (int i : bArr) {
//            int i2;
//            if (i2 < 0) {
//                i2 += 256;
//            }
//            String toHexString = Integer.toHexString(i2);
//            if (i2 < 16) {
//                stringBuilder.append("0");
//            }
//            stringBuilder.append(toHexString);
//        }
//        return stringBuilder.toString().toUpperCase();
        StringBuilder localStringBuilder = new StringBuilder(32);
        int i = 0;
        while (i < bArr.length)
        {
            int k = bArr[i];
            int j = k;
            if (k < 0) {
                j = k + 256;
            }
            String str = Integer.toHexString(j);
            if (j < 16) {
                localStringBuilder.append("0");
            }
            localStringBuilder.append(str);
            i += 1;
        }
        return localStringBuilder.toString().toUpperCase();
    }

    @NonNull
    public static String tapcashgo783m_c(@Nullable byte[] bArr, int i, int i2) {
        if (bArr == null) {
            return "";
        }
        if (i + i2 > bArr.length) {
            return "";
        }
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return tapcashgo783m_b(bArr2);
    }
    @NonNull
    public static String tapcashgo783m_a(int i) {
        return "Rp " + NumberFormat.getInstance().format((long) i);
    }

    @NonNull
    public static String tapcashgo783m_d(byte[] bArr, int i, int i2) {
        return tapcashgo783m_a(Integer.parseInt(tapcashgo783m.tapcashgo783m_c(bArr, i, i2), 16));
    }

    public static int tapcashgo783m_a(@NonNull byte[] bArr, int i, int i2) {
        return (int) tapcashgo783m.tapcashgo783m_b(bArr, i, i2);
    }

    public static long tapcashgo783m_b(@NonNull byte[] bArr, int i, int i2) {
        if (bArr.length < i2) {
            throw new IllegalArgumentException("length must be less than or equal to c.length");
        }
        long j = 0;
        for (int i3 = 0; i3 < i2; i3++) {
            j += (long) ((bArr[i3 + i] & 255) << (((i2 - 1) - i3) * 8));
        }
        return j;
    }

}
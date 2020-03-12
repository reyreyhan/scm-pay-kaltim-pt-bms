package bri.delivery.brizzi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.PrintStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class kuBrizzi {
    private String f50a;
    private String f51b;
    private String f52c;
    private String f53d;
    @NonNull
    private String f54e = "";
    @NonNull
    private String f55f = "";

    @NonNull
    private String m76b() {
        return "0000000000000000";
    }

    @NonNull
    public static byte[] kuBrizzi_bytea(@NonNull String str) {
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
    private String kuBrizzi_stringb(@NonNull String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str.substring(2, 16));
        stringBuilder.append(str.substring(0, 2));
        return stringBuilder.toString();
    }

    @NonNull
    private String kuBrizzi_stringc() {
        try {
            return kuBrizzi_stringa(kuBrizzi_bytea(kuBrizzi_bytea("8DC0DC40FE1DC582CF7099E2AACFBC10"), "C152153D5807784C721A433B5B59636D", m76b())).substring(0, 32);
        } catch (Throwable e) {
            Logger.getLogger(kuBrizzi.class.getName()).log(Level.SEVERE, null, e);
            return "";
        }
    }

    @NonNull
    private String kuBrizzi_stringd() {
        try {
            return kuBrizzi_stringa(kuBrizzi_bytea(kuBrizzi_bytea("3C37029CA595FE4E7E62FCB2F7909B2C"), "C152153D5807784C721A433B5B59636D", m76b())).substring(0, 32);
        } catch (Throwable e) {
            Logger.getLogger(kuBrizzi.class.getName()).log(Level.SEVERE, null, e);
            return "";
        }
    }

    private void kuBrizzi_voide() {
        try {
            this.f51b = kuBrizzi_stringa(kuBrizzi_byteb(this.f50a, kuBrizzi_stringc(), m76b())).substring(0, 32);
        } catch (Throwable e) {
            Logger.getLogger(kuBrizzi.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void kuBrizzi_voidf() {
        try {
            this.f54e = kuBrizzi_stringa(kuBrizzi_byteb(this.f51b, kuBrizzi_stringd(), this.f52c));
        } catch (Throwable e) {
            Logger.getLogger(kuBrizzi.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @NonNull
    public String kuBrizzi_stringa() {
        try {
            String b = kuBrizzi_stringb(kuBrizzi_stringa(kuBrizzi_bytea(kuBrizzi_bytea(kuBrizzi_stringa(kuBrizzi_bytec(this.f53d, this.f54e))), kuBrizzi_bytea(m76b()))));
            String a = kuBrizzi_stringa(kuBrizzi_bytec(kuBrizzi_stringa(kuBrizzi_bytea(kuBrizzi_bytea("1122334455667788"), kuBrizzi_bytea(this.f53d))), this.f54e));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(a);
            stringBuilder.append(kuBrizzi_stringa(kuBrizzi_bytec(kuBrizzi_stringa(kuBrizzi_bytea(kuBrizzi_bytea(b), kuBrizzi_bytea(a))), this.f54e)));
            return stringBuilder.toString();
        } catch (Throwable e) {
            Logger.getLogger(kuBrizzi.class.getName()).log(Level.SEVERE, null, e);
            return "";
        }
    }

    @NonNull
    public String kuBrizzi_stringa(@NonNull byte[] bArr) {
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

    public void kuBrizzi_voida(String str, String str2, String str3) {
        this.f50a = str;
        this.f52c = str2;
        this.f53d = str3;
        kuBrizzi_voide();
        kuBrizzi_voidf();
    }

    @Nullable
    public byte[] kuBrizzi_bytea(@NonNull String paramString1, @NonNull String paramString2) {
        SecretKeySpec paramString22 = new SecretKeySpec(kuBrizzi_bytea(paramString2), "DES");
        try
        {
            Cipher localCipher = Cipher.getInstance("DES/ECB/Nopadding");
            localCipher.init(1, paramString22);
            byte[] paramString12 = localCipher.doFinal(kuBrizzi_bytea(paramString1));
            return paramString12;
        }
        catch (InvalidKeyException ike)
        {
            return null;
        }
        catch (NoSuchAlgorithmException nae)
        {
            return null;
        }
        catch (NoSuchPaddingException nspe)
        {
            return null;
        }
        catch (BadPaddingException bpe)
        {
            return null;
        }
        catch (IllegalBlockSizeException ibse) {}
        return null;
    }

    @Nullable
    public byte[] kuBrizzi_bytea(byte[] paramArrayOfByte, @NonNull String paramString1, String paramString2) {
        Object localObject;
        byte[] paramString11;
        if (paramString1.length() == 48)
        {
            paramString11 = kuBrizzi_bytea(paramString1);
        }
        else if (paramString1.length() == 32)
        {
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append(paramString1);
            ((StringBuilder)localObject).append(paramString1.substring(0, 16));
            paramString11 = kuBrizzi_bytea(((StringBuilder)localObject).toString());
        }
        else if (paramString1.length() == 16)
        {
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append(paramString1);
            ((StringBuilder)localObject).append(paramString1);
            ((StringBuilder)localObject).append(paramString1);
            paramString11 = kuBrizzi_bytea(((StringBuilder)localObject).toString());
        }
        else
        {
            paramString11 = kuBrizzi_bytea("00000000000000000000000000000000");
        }
        SecretKeySpec paramString12 = new SecretKeySpec(paramString11, "DESede");
        IvParameterSpec paramString22 = new IvParameterSpec(kuBrizzi_bytea(paramString1));
        try
        {
            localObject = Cipher.getInstance("DESede/CBC/Nopadding");
            ((Cipher)localObject).init(2, paramString12, paramString22);
            paramArrayOfByte = ((Cipher)localObject).doFinal(paramArrayOfByte);
            return paramArrayOfByte;
        }
        catch (NoSuchPaddingException nspe)
        {
            return null;
        }
        catch (NoSuchAlgorithmException nsae)
        {
            return null;
        }
        catch (InvalidAlgorithmParameterException iape)
        {
            return null;
        }
        catch (InvalidKeyException ike)
        {
            return null;
        }
        catch (BadPaddingException bpe)
        {
            return null;
        }
        catch (IllegalBlockSizeException ibse) {
            return null;
        }

        }

    @NonNull
    public byte[] kuBrizzi_bytea(@NonNull byte[] bArr, @NonNull byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length];
        if (bArr2.length == 0) {
            throw new IllegalArgumentException("empty security key");
        }
        int i = 0;
        int i2 = i;
        while (i < bArr.length) {
            bArr3[i] = (byte) (bArr[i] ^ bArr2[i2]);
            i2++;
            if (i2 >= bArr2.length) {
                i2 = 0;
            }
            i++;
        }
        PrintStream bArrPrintStream = System.out;
        StringBuilder bArr2StringBUilder = new StringBuilder();
        bArr2StringBUilder.append("xorOut : ");
        bArr2StringBUilder.append(kuBrizzi_stringa(bArr3));
        bArrPrintStream.println(bArr2.toString());
        return bArr3;
    }

    @NonNull
    public byte[] kuBrizzi_byteb(java.lang.String r4, java.lang.String r5) {

        throw new UnsupportedOperationException("Method not decompiled: bri.delivery.brizzi.kuBrizzi.c(java.lang.String, java.lang.String):byte[]");
    }

    @Nullable
    public byte[] kuBrizzi_byteb(@NonNull String paramString1, @NonNull String paramString2, @NonNull String paramString3)
        {
            StringBuilder localObject;
            byte[] paramString22;
            if (paramString2.length() == 48)
            {
                paramString22 = kuBrizzi_bytea(paramString2);
            }
            else if (paramString2.length() == 32)
            {
                localObject = new StringBuilder();
                ((StringBuilder)localObject).append(paramString2);
                ((StringBuilder)localObject).append(paramString2.substring(0, 16));
                paramString22 = kuBrizzi_bytea(((StringBuilder)localObject).toString());
            }
            else if (paramString2.length() == 16)
            {
                localObject = new StringBuilder();
                ((StringBuilder)localObject).append(paramString2);
                ((StringBuilder)localObject).append(paramString2);
                ((StringBuilder)localObject).append(paramString2);
                paramString22 = kuBrizzi_bytea(((StringBuilder)localObject).toString());
            }
            else
            {
                paramString22 = kuBrizzi_bytea("00000000000000000000000000000000");
            }
            SecretKeySpec paramString23 = new SecretKeySpec(paramString22, "DESede");
            IvParameterSpec paramString32 = new IvParameterSpec(kuBrizzi_bytea(paramString3));
            try
            {
                Cipher localObjectc = Cipher.getInstance("DESede/CBC/Nopadding");
                ((Cipher)localObjectc).init(1, paramString23, paramString32);
                byte[] paramString12 = ((Cipher) localObjectc).doFinal(kuBrizzi_bytea(paramString1));
                return paramString12;
            }
            catch (InvalidKeyException ie)
            {
                return null;
            }
            catch (NoSuchAlgorithmException nsae)
            {
                return null;
            }
            catch (NoSuchPaddingException nspe)
            {
                return null;
            }
            catch (BadPaddingException bpe)
            {
                return null;
            }
            catch (IllegalBlockSizeException ibe)
            {
                return null;
            }
            catch (InvalidAlgorithmParameterException iape) {
                return null;
            }
        }

    @NonNull
    public byte[] kuBrizzi_bytec(String str, @NonNull String str2) {
        String substring = str2.substring(0, 16);
        String str3 = "";
        try {
            return kuBrizzi_bytea(kuBrizzi_stringa(kuBrizzi_byteb(kuBrizzi_stringa(kuBrizzi_bytea(kuBrizzi_stringa(kuBrizzi_byteb(str, substring)), str2.substring(16, 32))), substring)));
        } catch (Exception str4) {
            Logger.getLogger(kuBrizzi.class.getName()).log(Level.SEVERE, null, str4);
            return kuBrizzi_bytea(str3);
        }
    }
}

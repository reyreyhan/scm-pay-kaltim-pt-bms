package com.cardnfc.lib.bninfc.tapcashgo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public final class tapcashgo752a {
    @Nullable
    private static IvParameterSpec ivParameterSpec = null;
    @Nullable
    private static SecretKeySpec secretKeySpec = null;
    @Nullable
    private static Cipher chiCipher = null;
    @NonNull
    private static String d = "ehjtnMiGhNhoxRuUzfBOXw==";
    @NonNull
    private static String e = "PSCIQGfoZidjEuWtJAdn1JGYzKDonk9YblI0uv96O8s=";

    public static String tapcashgo752a_stringa(@NonNull String str) throws GeneralSecurityException {
        try {
            byte[] decode = Base64.decode(d, 2);
            byte[] decode2 = Base64.decode(e, 2);
            ivParameterSpec = new IvParameterSpec(decode);
            secretKeySpec = new SecretKeySpec(decode2, "AES");
            chiCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            chiCipher.init(1, secretKeySpec, ivParameterSpec);
            return Base64.encodeToString(chiCipher.doFinal(str.getBytes("UTF-8")), 2);
        } catch (UnsupportedEncodingException e) {
            throw new GeneralSecurityException(e.getMessage());
        }
    }

    @NonNull
    public static String tapcashgo752a_stringb(String str) throws GeneralSecurityException {
        try {
            byte[] decode = Base64.decode(str, 2);
            byte[] decode2 = Base64.decode(d, 2);
            byte[] decode3 = Base64.decode(e, 2);
            ivParameterSpec = new IvParameterSpec(decode2);
            secretKeySpec = new SecretKeySpec(decode3, "AES");
            chiCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            chiCipher.init(2, secretKeySpec, ivParameterSpec);
            return new String(chiCipher.doFinal(decode), "UTF-8");
        } catch (Throwable e) {
            throw new GeneralSecurityException(e);
        }
    }
}

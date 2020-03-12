package com.cardnfc.lib.bninfc.tapcashgo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class tapcashgo1b {
    public static final char tapcashgo1b_a = File.separatorChar;
    @NonNull
    private static final String b;

    static {
        Writer stringWriter = new StringWriter(4);
        new PrintWriter(stringWriter).println();
        b = stringWriter.toString();
    }

    public static void tapcashgo1b_a(@Nullable InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
            }
        }
    }

    public static void tapcashgo1b_a(@Nullable OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
            }
        }
    }

    @NonNull
    public static String tapcashgo1b_stringa(InputStream inputStream, String str) {
        Writer stringWriter = new StringWriter();
        tapcashgo1b.tapcashgo1b_voida(inputStream, stringWriter, str);
        return stringWriter.toString();
    }

    public static void tapcashgo1b_voida(@Nullable String str, @NonNull OutputStream outputStream) {
        if (str != null) {
            try {
                outputStream.write(str.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void tapcashgo1b_voida(@Nullable String str, @NonNull OutputStream outputStream, @Nullable String str2) {
        if (str == null) {
            return;
        }
        if (str2 == null) {
            tapcashgo1b.tapcashgo1b_voida(str, outputStream);
        } else {
            try {
                outputStream.write(str.getBytes(str2));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void tapcashgo1b_voida(InputStream inputStream, @NonNull Writer writer) {
        tapcashgo1b.tapcashgo1b_inta(new InputStreamReader(inputStream), writer);
    }

    public static void tapcashgo1b_voida(InputStream inputStream, @NonNull Writer writer, @Nullable String str) {
        if (str == null) {
            tapcashgo1b.tapcashgo1b_voida(inputStream, writer);
        } else {
            try {
                tapcashgo1b.tapcashgo1b_inta(new InputStreamReader(inputStream, str), writer);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public static int tapcashgo1b_inta(@NonNull Reader reader, @NonNull Writer writer) {
        long b = tapcashgo1b.m14b(reader, writer);
        if (b > 2147483647L) {
            return -1;
        }
        return (int) b;
    }

    public static long m14b(@NonNull Reader reader, @NonNull Writer writer) {
        char[] cArr = new char[4096];
        long j = 0;
        while (true) {
            int read = 0;
            try {
                read = reader.read(cArr);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (-1 == read) {
                return j;
            }
            try {
                writer.write(cArr, 0, read);
            } catch (IOException e) {
                e.printStackTrace();
            }
            j += (long) read;
        }
    }
}

package com.cardnfc.lib.bninfc.tapcashgo;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class tapcashgoa {
    public static final File[] f0a = new File[0];

    @NonNull
    public static FileInputStream m0a(@NonNull File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException(new StringBuffer().append("File '").append(file).append("' does not exist").toString());
        } else if (file.isDirectory()) {
            throw new IOException(new StringBuffer().append("File '").append(file).append("' exists but is a directory").toString());
        } else if (file.canRead()) {
            return new FileInputStream(file);
        } else {
            throw new IOException(new StringBuffer().append("File '").append(file).append("' cannot be read").toString());
        }
    }

    @NonNull
    public static FileOutputStream m3b(@NonNull File file) throws IOException {
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (!(parentFile == null || parentFile.exists() || parentFile.mkdirs())) {
                throw new IOException(new StringBuffer().append("File '").append(file).append("' could not be created").toString());
            }
        } else if (file.isDirectory()) {
            throw new IOException(new StringBuffer().append("File '").append(file).append("' exists but is a directory").toString());
        } else if (!file.canWrite()) {
            throw new IOException(new StringBuffer().append("File '").append(file).append("' cannot be written to").toString());
        }
        return new FileOutputStream(file);
    }

    @NonNull
    public static String m1a(@NonNull File file, String str) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = tapcashgoa.m0a(file);
            String a = tapcashgo1b.tapcashgo1b_stringa(inputStream, str);
            return a;
        } finally {
            tapcashgo1b.tapcashgo1b_a(inputStream);
        }
    }

    @NonNull
    public static String m5c(@NonNull File file) throws IOException {
        return tapcashgoa.m1a(file, null);
    }

    public static void m2a(@NonNull File file, String str, String str2) throws IOException {
        OutputStream outputStream = null;
        try {
            outputStream = tapcashgoa.m3b(file);
            tapcashgo1b.tapcashgo1b_voida(str, outputStream, str2);
        } finally {
            tapcashgo1b.tapcashgo1b_a(outputStream);
        }
    }

    public static void m4b(@NonNull File file, String str) throws IOException {
        tapcashgoa.m2a(file, str, null);
    }
}

package com.cardnfc.lib.bninfc.tapcashgo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.GZIPInputStream;
//import p000a.p005b.tapcashgo17b;

public class tapcashgo23b extends tapcashgo22g {
    public tapcashgo23b(String str, int i) {
        super(str, i);
    }

    public void tapcashgo22g_voida(String str, tapcashgo17b tapcashgo17B) {
        tapcashgo22g_lista(str, tapcashgo17B, null);
    }

    @NonNull
    public List tapcashgo22g_lista(String str, tapcashgo17b tapcashgo17B, List list) {
        return tapcashgo23b_lista(str, tapcashgo17B, list, null);
    }


    @NonNull
    public List tapcashgo23b_lista(String r14, tapcashgo17b r15, List r16, File r17) {

        throw new UnsupportedOperationException("Method not decompiled: a.c.c.c.a(java.lang.String, a.c.c, java.util.List, java.io.File):java.util.List");
    }

//    protected void m116a(byte[] bArr, tapcashgointerface24e c0024e, tapcashgo17b c0017b) {
//        c0024e.tapcashgointerface24e_voida("Content-Length", "" + bArr.length);
//        c0024e.tapcashgointerface24e_voida(bArr.length);
//        OutputStream d = c0024e.tapcashgointerface24e_outputstreamd();
//        d.write(bArr, 0, bArr.length);
//        d.flush();
//        d.close();
//    }

    protected void tapcashgo23b_voida(@NonNull tapcashgo17b tapcashgo17B, @NonNull InputStream inputStream, List list) {
        tapcashgo22g_voida(tapcashgo17B, inputStream);
    }

    @NonNull
    private InputStream inputStream(@NonNull InputStream inputStream, int i, @Nullable File file) {
        OutputStream fileOutputStream = null;
        byte[] toByteArray;
        if (file != null) {
            try {
                fileOutputStream = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            if (i <= 0) {
                i = 262144;
            }
            fileOutputStream = new ByteArrayOutputStream(i);
        }
        byte[] bArr = new byte[256];
        while (true) {
            int read = 0;
            try {
                read = inputStream.read(bArr, 0, 256);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (read == -1) {
                break;
            }
            try {
                fileOutputStream.write(bArr, 0, read);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fileOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fileOutputStream instanceof ByteArrayOutputStream) {
            toByteArray = ((ByteArrayOutputStream) fileOutputStream).toByteArray();
        } else {
            toByteArray = bArr;
        }
        this.f83i = new String(toByteArray);
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (file != null) {
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return new ByteArrayInputStream(toByteArray);
    }

    private InputStream inputStreama(InputStream inputStream) {
        try {
            return (GZIPInputStream) inputStream;
        } catch (ClassCastException e) {
            try {
                return new GZIPInputStream(inputStream);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return inputStream;
    }

   // public tapcashgointerface24e mo27a() {
//        return new C0027f(this.d, this.e, this.a);
//    }
}

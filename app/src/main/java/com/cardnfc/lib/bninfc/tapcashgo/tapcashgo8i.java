package com.cardnfc.lib.bninfc.tapcashgo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Vector;

public class tapcashgo8i implements Serializable {
    public static final Class tapcashgo8i_a = new Object().getClass();
    public static final Class tapcashgo8i_b = "".getClass();
    public static final Class tapcashgo8i_c = new Integer(0).getClass();
    public static final Class tapcashgo8i_d = new Long(0).getClass();
    public static final Class tapcashgo8i_e = new Boolean(true).getClass();
    public static final Class tapcashgo8i_f = new Vector().getClass();
    public static final tapcashgo8i tapcashgo8i = new tapcashgo8i();
    @Nullable
    public String tapcashgo8i_stringh;
    @Nullable
    public String tapcashgo8i_stringi;
    public int tapcashgo8i_stringj;
    @Nullable
    protected Object tapcashgo8i_objectk;
    @Nullable
    public Object tapcashgo8i_objectl = tapcashgo8i_a;
    public boolean tapcashgo8i_booleanm;
    @Nullable
    public tapcashgo8i tapcashgo8i1;

    public void tapcashgo8i_37a() {
        this.tapcashgo8i_objectl = tapcashgo8i_a;
        this.tapcashgo8i_stringj = 0;
        this.tapcashgo8i_stringh = null;
        this.tapcashgo8i_stringi = null;
    }

    @Nullable
    public String tapcashgo8i_stringb() {
        return this.tapcashgo8i_stringh;
    }

    public void tapcashgo8i_voida(String str) {
        this.tapcashgo8i_stringh = str;
    }

    @Nullable
    public String tapcashgo8i_stringc() {
        return this.tapcashgo8i_stringi;
    }

    public void tapcashgo8i_voidb(String str) {
        this.tapcashgo8i_stringi = str;
    }

    public void tapcashgo8i_voida_(Object obj) {
        this.tapcashgo8i_objectl = obj;
    }

    @Nullable
    public Object tapcashgo8i_objectd() {
        return this.tapcashgo8i_objectk;
    }

    public void tapcashgo8i_voidb_(Object obj) {
        this.tapcashgo8i_objectk = obj;
    }

    @NonNull
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.tapcashgo8i_stringh);
        stringBuffer.append(" : ");
        if (this.tapcashgo8i_objectk != null) {
            stringBuffer.append(this.tapcashgo8i_objectk);
        } else {
            stringBuffer.append("(not set)");
        }
        return stringBuffer.toString();
    }

    @Nullable
    public Object clone() {
//        Object obj = null;
//        try {
//            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
//            objectOutputStream.writeObject(this);
//            objectOutputStream.flush();
//            objectOutputStream.close();
//            obj = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())).readObject();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NotSerializableException e2) {
//            e2.printStackTrace();
//        } catch (IOException e3) {
//            e3.printStackTrace();
//        }
//        return obj;
        try
        {
            Object localObject = new ByteArrayOutputStream();
            ObjectOutputStream localObjectOutputStream = new ObjectOutputStream((OutputStream)localObject);
            localObjectOutputStream.writeObject(this);
            localObjectOutputStream.flush();
            localObjectOutputStream.close();
            localObject = new ObjectInputStream(new ByteArrayInputStream(((ByteArrayOutputStream)localObject).toByteArray())).readObject();
            return localObject;
        }
        catch (ClassNotFoundException localClassNotFoundException)
        {
            localClassNotFoundException.printStackTrace();
            return null;
        }
        catch (NotSerializableException localNotSerializableException)
        {
            localNotSerializableException.printStackTrace();
            return null;
        }
        catch (IOException localIOException)
        {
            localIOException.printStackTrace();
        }
        return null;
    }
}

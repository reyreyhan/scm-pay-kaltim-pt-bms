package com.cardnfc.lib.bninfc.tapcashgo.card;

import androidx.annotation.Nullable;

public class tapcashgo2a {
    public static final Object[] a = new Object[0];
    public static final Class<?>[] b = new Class[0];
    public static final String[] c = new String[0];
    public static final long[] d = new long[0];
    public static final Long[] e = new Long[0];
    public static final int[] f = new int[0];
    public static final Integer[] g = new Integer[0];
    public static final short[] h = new short[0];
    public static final Short[] i = new Short[0];
    public static final byte[] j = new byte[0];
    public static final Byte[] k = new Byte[0];
    public static final double[] l = new double[0];
    public static final Double[] m = new Double[0];
    public static final float[] n = new float[0];
    public static final Float[] o = new Float[0];
    public static final boolean[] p = new boolean[0];
    public static final Boolean[] q = new Boolean[0];
    public static final char[] r = new char[0];
    public static final Character[] s = new Character[0];

    private static int tapcashgo2aint_a(Object[] objArr, Object obj) {
        return tapcashgo2a_a(objArr, obj, 0);
    }

    private static int tapcashgo2a_a(@Nullable Object[] objArr, @Nullable Object obj, int i) {
        if (objArr == null) {
            return -1;
        }
        int i2;
        if (i < 0) {
            i2 = 0;
        } else {
            i2 = i;
        }
        if (obj == null) {
            while (i2 < objArr.length) {
                if (objArr[i2] == null) {
                    return i2;
                }
                i2++;
            }
        } else if (objArr.getClass().getComponentType().isInstance(obj)) {
            while (i2 < objArr.length) {
                if (obj.equals(objArr[i2])) {
                    return i2;
                }
                i2++;
            }
        }
        return -1;
    }

    static boolean tapcashgo2aboolean_b(Object[] objArr, Object obj) {
        return tapcashgo2aint_a(objArr, obj) != -1;
    }
}

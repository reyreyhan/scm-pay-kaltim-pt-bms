package bri.delivery.brizzi;

import org.simalliance.openmobileapi.util.ISO7816;

public class NFCBrizzi {
    private static final byte[] APDU_a = new byte[]{(byte) -112, (byte) 90, (byte) 0, (byte) 0, (byte) 3, (byte) 1, (byte) 0, (byte) 0, (byte) 0};
    private static final byte[] APDU_b = new byte[]{(byte) -112, (byte) 90, (byte) 0, (byte) 0, (byte) 3, (byte) 2, (byte) 0, (byte) 0, (byte) 0};
    private static final byte[] APDU_c = new byte[]{(byte) -112, (byte) 90, (byte) 0, (byte) 0, (byte) 3, (byte) 3, (byte) 0, (byte) 0, (byte) 0};
    private static final byte[] APDU_d = new byte[]{(byte) -112, (byte) -67, (byte) 0, (byte) 0, (byte) 7, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 23, (byte) 0, (byte) 0, (byte) 0};
    private static final byte[] APDU_e = new byte[]{(byte) -112, (byte) -67, (byte) 0, (byte) 0, (byte) 7, (byte) 1, (byte) 0, (byte) 0, (byte) 0, ISO7816.INS_VERIFY_20, (byte) 0, (byte) 0, (byte) 0};
    private static final byte[] APDU_h = new byte[]{(byte) -112, (byte) 108, (byte) 0, (byte) 0, (byte) 1, (byte) 0, (byte) 0};
    private static final byte[] APDU_i = new byte[]{(byte) -112, (byte) -67, (byte) 0, (byte) 0, (byte) 7, (byte) 3, (byte) 0, (byte) 0, (byte) 0, (byte) 7, (byte) 0, (byte) 0, (byte) 0};
    private static final byte[] f32f = new byte[]{(byte) -112, (byte) 10, (byte) 0, (byte) 0, (byte) 1, (byte) 0, (byte) 0};
    private static final byte[] f33g = new byte[]{(byte) -112, (byte) 10, (byte) 0, (byte) 0, (byte) 1, (byte) 1, (byte) 0};
    private static final byte[] f34j = new byte[]{(byte) -112, (byte) -69, (byte) 0, (byte) 0, (byte) 7, (byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0};
}

package com.bm.main.fpl.interfaces;

import com.google.android.gms.vision.barcode.Barcode;

/**
 * Created by Sarif Hidayat on 17/03/2017.
 */

public interface BarcodeListener {
    void onResultBarcode(Barcode barcode);
//    void messageReceivedKey(String messageText);
//
//    void messageReceivedDeposit(String messageText);
//    void messageReceivedGantiPin(String messageText);
//    void messageReceivedOtp(String messageText);
}

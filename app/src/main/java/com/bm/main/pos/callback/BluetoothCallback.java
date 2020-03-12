package com.bm.main.pos.callback;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;

public interface BluetoothCallback {
    void onPowerOn(Intent intent);
    void onPowerOff(Intent intent);
    void onConnected(BluetoothSocket socket, int taskType, BluetoothDevice device);
    void onError(String msg);
}

package com.bm.main.pos.utils;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;

import com.bm.main.pos.callback.BluetoothCallback;

public class BluetoothConnectTask extends AsyncTask<BluetoothDevice, Integer, BluetoothSocket> {

    private BluetoothCallback callback;
    private int typeTask;
    private String msg;
    private BluetoothDevice device;

    public BluetoothConnectTask(BluetoothCallback callback, int typeTask, String msg) {
        this.callback = callback;
        this.typeTask = typeTask;
        this.msg = msg;
    }

    @Override
    protected BluetoothSocket doInBackground(BluetoothDevice... bluetoothDevices) {
        device = bluetoothDevices[0];
        return BluetoothUtil.connectDevice(device);
    }

    @Override
    // Once the image is downloaded, associates it to the imageView
    protected void onPostExecute(BluetoothSocket socket) {
        if (socket != null) {
            callback.onConnected(socket, typeTask, device);
        } else {
            callback.onError(msg);
        }
    }
}

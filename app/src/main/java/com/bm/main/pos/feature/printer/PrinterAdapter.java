package com.bm.main.pos.feature.printer;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bm.main.pos.R;

public class PrinterAdapter extends ArrayAdapter<BluetoothDevice> {


    private int selected = -1;
    private BluetoothDevice selectedDevice = null;

    public PrinterAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final BluetoothDevice device = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_bluetooth_device, parent, false);
        }

        TextView tvDeviceName = convertView.findViewById(R.id.tv_device_name);

        String name = "";
        if(device.getName() != null){
            name = device.getName();
        }

        tvDeviceName.setText(name);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback != null){
                    callback.onClick(device);
                }
            }
        });

        return convertView;
    }

    public int getSelected() {
        return selected;
    }

    public Callback callback;
    public interface Callback{
        void onClick(BluetoothDevice device);
    }
}

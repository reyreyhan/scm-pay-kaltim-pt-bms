package com.bm.main.fpl.activities;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bm.main.fpl.utils.PreferenceClass;
import com.bm.main.pos.R;
import com.bm.main.fpl.adapters.DeviceListAdapter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.UUID;

import timber.log.Timber;

//import android.support.v7.widget.Toolbar;

public class DeviceListActivity extends BaseActivity implements DeviceListAdapter.OnPairButtonClickListener {

    private static final String TAG = DeviceListActivity.class.getSimpleName();
    // Return Intent extra
    @NonNull
    static String EXTRA_DEVICE_ADDRESS = "device_address";
    Button scanButton;
    //    private ListView mListView;
    private RecyclerView mListView;
    private DeviceListAdapter mAdapter;
    private ArrayList<BluetoothDevice> mDeviceList;
    FrameLayout emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_device_list);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        toolbar.setTitle("Daftar Perangkat");
        toolbar.setSubtitle("Printer Bluetooth");
        init(1);
//        mDeviceList.clear();
        // View empty = findViewById(R.id.emptyView);
        TextView header = findViewById(R.id.txtHeader);
        TextView subHeader = findViewById(R.id.txtSub);
        header.setText("Perangkat Bluetooth Printer");
        subHeader.setText("Tidak Ditemukan");

        Intent intent = getIntent();

        emptyView = findViewById(R.id.emptyView);

        if (intent != null)
            mDeviceList = intent.getParcelableArrayListExtra("device.list");

        mListView = findViewById(R.id.lv_paired);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        //  mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mListView.setLayoutManager(mLayoutManager);

        mAdapter = new DeviceListAdapter(this, mDeviceList, this);

        //mAdapter.setData(mDeviceList);

//        mAdapter.setListener(new DeviceListAdapter.OnPairButtonClickListener() {
//            @Override
//            public void onPairButtonClick(int position) {
//                BluetoothDevice device = mDeviceList.get(position);
//
//                if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
//                    unpairDevice(device);
//                } else {
//                    showToast("Pairing...");
//
//                    pairDevice(device);
//                }
//            }
//        });

        mListView.setAdapter(mAdapter);
        if (mDeviceList.size() > 0) {

        } else {

        }
        // mListView.setEmptyView(empty);

        registerReceiver(mPairReceiver, new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED));
    }


    @Override
    public void onDestroy() {
        unregisterReceiver(mPairReceiver);

        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent newIntent = new Intent(DeviceListActivity.this, SettingPrinterActivity.class);

                newIntent.putParcelableArrayListExtra("device.list", mDeviceList);

                startActivity(newIntent);
                finish();
        }
        return true;
    }
//	private void showToast(String message) {
//		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//	}

//    private void pairDevice(BluetoothDevice device) {
//        try {
//            Method method = device.getClass().getMethod("createBond", (Class[]) null);
//            method.invoke(device, (Object[]) null);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    private void unpairDevice(@NonNull BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("removeBond", (Class[]) null);
            method.invoke(device, (Object[]) null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final BroadcastReceiver mPairReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, @NonNull Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                Log.d(TAG, "onReceive: " + action);
                final int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
                final int prevState = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.ERROR);
                final String uuidx = intent.getStringExtra(BluetoothDevice.EXTRA_UUID);
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Log.d(TAG, "onReceive: "+device.getUuids().toString());
                if (state == BluetoothDevice.BOND_BONDED && prevState == BluetoothDevice.BOND_BONDING) {

                    // ParcelUuid uuidExtra = intent.getParcelableExtra(BluetoothDevice.EXTRA_UUID);
                    // UUID uuid = uuidExtra.getUuid();
                    showToast("Paired " + device.getName() + " " + device.getAddress());
                    //  showToast("Paired UUID"+String.valueOf(device.getUuids()));
                    //	 UUID uuid = device.getUuids()[0].getUuid();
                    // SavePref.getInstance(DeviceListActivity.this).saveString("macAddr",device.getAddress());
                    // SavePref.getInstance(DeviceListActivity.this).saveString("uuid",uuid.toString());
                    // new SavePref().saveString(DeviceListActivity.this,"uuidBT", String.valueOf(device.getUuids()[0].getUuid()));
                    //PreferenceClass.saveMacAddrPrinter(device.getAddress());
                    Intent newIntent = new Intent(DeviceListActivity.this, SettingPrinterActivity.class);

                    newIntent.putParcelableArrayListExtra("device.list", mDeviceList);
                    // newIntent.putExtra("uuid", uuidExtra.getUuid());
                    // ParcelUuid uuidExtra =intent.getParcelableExtra("android.bluetooth.device.extra.UUID");

                    startActivity(newIntent);

                    finish();

                } else if (state == BluetoothDevice.BOND_NONE && prevState == BluetoothDevice.BOND_BONDED) {
                    showToast("Unpaired");
//                    if(PreferenceClass.getMacAddrPrinter().equals(device.getAddress())){
//                        PreferenceClass.saveMacAddrPrinter("");
//                    }
                    Intent newIntent = new Intent(DeviceListActivity.this, SettingPrinterActivity.class);
                    startActivity(newIntent);
                    finish();
                }

                mAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DeviceListActivity.this, SettingPrinterActivity.class);
        startActivity(intent);
        finish();
    }

    public void pairDevice(@NonNull BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("createBond", (Class[]) null);
            method.invoke(device, (Object[]) null);
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    @Override
    public void onPairButtonClick(@NonNull BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
            unpairDevice(bluetoothDevice);
        } else {
            showToast("Pairing...");

            pairDevice(bluetoothDevice);
        }
    }

    public void onChoiceButtonClick(@NonNull BluetoothDevice bluetoothDevice) {
        Log.d(TAG, "onChoiceButtonClick: " + bluetoothDevice.getAddress());

        try {
            UUID BPP = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
            // final BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            // bluetoothAdapter.getRemoteDevice(bDevice.getAddress());
            mmDevice = bluetoothAdapter.getRemoteDevice(bluetoothDevice.getAddress());

            try {
                socket = bluetoothDevice.createRfcommSocketToServiceRecord(BPP);
                Method m = bluetoothDevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
                socket = (BluetoothSocket) m.invoke(bluetoothDevice, Integer.valueOf(1));

                socket.connect();
                // cetak(SettingPrinterActivity.this);
                Log.d(TAG, "Connected 1 " + socket.isConnected());
                PreferenceClass.saveMacAddrPrinter(bluetoothDevice.getAddress());
                Intent newIntent = new Intent(DeviceListActivity.this, SettingPrinterActivity.class);

                newIntent.putParcelableArrayListExtra("device.list", mDeviceList);
                // newIntent.putExtra("uuid", uuidExtra.getUuid());
                // ParcelUuid uuidExtra =intent.getParcelableExtra("android.bluetooth.device.extra.UUID");

                startActivity(newIntent);

                finish();
            } catch (Exception e) {
                Log.d(TAG, "onReceive: " + e.toString());
            }
        } catch (Exception e) {


            Log.d(TAG, "print exception: " + e.toString());


//                Intent intent = new Intent(this, SettingPrinterActivity.class);
//                startActivity(intent);

        }

    }
}

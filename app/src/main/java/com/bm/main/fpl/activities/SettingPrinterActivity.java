package com.bm.main.fpl.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.main.fpl.utils.FormatString;
import com.bm.main.scm.R;
import com.bm.main.fpl.constants.ResponseCode;
import com.bm.main.fpl.templates.RandomTextView;
import com.bm.main.fpl.utils.PreferenceClass;
import com.google.zxing.WriterException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SettingPrinterActivity extends BaseActivity {
    private static final String TAG = SettingPrinterActivity.class.getSimpleName();
    TextView mStatusTv;
    //     AppCompatButton mActivateBtn,
    AppCompatButton mPairedBtn, mScanBtn, mTestPrintBtn, mStopBtn;
    public SwitchCompat switchBluetooth;
    RandomTextView randomTextView;
    LinearLayout linScanner, linMain;
    BluetoothAdapter mBluetoothAdapter;
    @NonNull
    ArrayList<BluetoothDevice> mDeviceList = new ArrayList<>();
    TextView textViewSwitch;
    Context mContext;
    boolean isBluetooth;
    boolean bottom;
    public String strukTercetak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_printer);
        Intent intent = getIntent();

        mContext = this;
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Setting Printer");
        init(1);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(
                mMessageReceiver,
                new IntentFilter("BROADCAST_PRINTING")
        );
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        linScanner = findViewById(R.id.linScanner);
        linMain = findViewById(R.id.linMain);
        textViewSwitch = findViewById(R.id.textViewSwitch);
        randomTextView = findViewById(R.id.random_textview);
        switchBluetooth = findViewById(R.id.switchBluetooth);

        mStatusTv = findViewById(R.id.tv_status);
        // mActivateBtn = findViewById(R.id.btn_enable);
        mPairedBtn = findViewById(R.id.btn_view_paired);
        mScanBtn = findViewById(R.id.btn_scan);
        mTestPrintBtn = findViewById(R.id.btn_test_print);

        mStopBtn = findViewById(R.id.button_batal_scanner);
        mStopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBluetoothAdapter.cancelDiscovery();
                linScanner.setVisibility(View.GONE);
                linMain.setVisibility(View.VISIBLE);
            }
        });

        if (mBluetoothAdapter == null) {
            showUnsupported();
        } else {
            mPairedBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

                    if (pairedDevices == null || pairedDevices.size() == 0) {
                        showToast("No Paired Devices Found");
                    } else {
                        ArrayList<BluetoothDevice> list = new ArrayList<>();
                        list.clear();
                        // BluetoothDevice printer = null;
                        for (BluetoothDevice device : pairedDevices) {

                            list.add(device);
                        }

                        Intent intent = new Intent(SettingPrinterActivity.this, DeviceListActivity.class);

                        intent.putParcelableArrayListExtra("device.list", list);

                        startActivity(intent);
                        //   mBluetoothAdapter.cancelDiscovery();
                        finish();
                    }
                }
            });

            mScanBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Log.d(TAG, "onClick: scan");
                    mBluetoothAdapter.startDiscovery();
                }
            });

            if (mBluetoothAdapter.isEnabled()) {
                switchBluetooth.setChecked(true);
                showEnabled();
            } else {
                switchBluetooth.setChecked(false);
                showDisabled();
            }

            switchBluetooth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        // The toggle is enabled
                        Log.d(TAG, "onCheckedChanged: " + isChecked);
                        if (mBluetoothAdapter.isEnabled()) {

                        } else {
                            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//
                            startActivityForResult(intent, 1000);
                        }

                    } else {
                        Log.d(TAG, "onCheckedChanged: " + isChecked);
                        // The toggle is disabled
                        if (mBluetoothAdapter.isEnabled()) {
                            mBluetoothAdapter.disable();

                            showDisabled();
                        }
                        showDisabled();
                    }
                }
            });

            mScanBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    mBluetoothAdapter.startDiscovery();
                }
            });
            String normal = "Test Print Sukses";
            String bold_height = ResponseCode.BoldOn + ResponseCode.UpOn + normal + ResponseCode.UpOff + ResponseCode.BoldOff;
            String italic = ResponseCode.ItalicOn + normal + ResponseCode.ItalicOff;
            String undeline = ResponseCode.UnderlineOn + normal + ResponseCode.UnderlineOff;
            final String barcode = ResponseCode.CenterOn + ResponseCode.BarcodeOn + "12345678fastpay" + ResponseCode.BarcodeOff + ResponseCode.CenterOff;
            strukTercetak = normal + "\n";
            mTestPrintBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    mTestPrintBtn.setFocusableInTouchMode(false);
                    mTestPrintBtn.setEnabled(false);
                    mTestPrintBtn.setClickable(false);
                    mTestPrintBtn.setText(R.string.on_print);

                    qrCode = "12345678fastpay";

                    try {
                        id_pelQRCode = qrCode;
                        bitmapQRCode = FormatString.TextToImageEncode(v.getContext(), qrCode);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }

                    cetak(mContext);
                }
            });
        }

        IntentFilter filter = new IntentFilter();

        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_UUID);
        filter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);

        registerReceiver(mReceiver, filter);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {


        finish();
        //overridePendingTransition(R.anim.popup_show, R.anim.popup_hide);


    }

    @Override
    public void onDestroy() {


        super.onDestroy();
        if (mBluetoothAdapter != null) {
            if (mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }
        }
        unregisterReceiver(mReceiver);
    }

    @Override
    public void onPause() {
        if (mBluetoothAdapter != null) {
            if (mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }
        }

        super.onPause();
    }


    private void showEnabled() {
        mStatusTv.setText("Bluetooth is On");
        mStatusTv.setTextColor(Color.BLUE);

        // mActivateBtn.setText("Disable");
        // mActivateBtn.setEnabled(true);
        textViewSwitch.setText("Enable");
//        switchBluetooth.setEnabled(true);
        //  switchBluetooth.setChecked(true);
//        View view = switchBluetooth.findViewById(R.id.switchBluetooth);
//        view.performClick();
        PreferenceClass.putBoolean("isBluetooth", true);
        mPairedBtn.setEnabled(true);
        mScanBtn.setEnabled(true);
    }

    private void showDisabled() {
        mStatusTv.setText("Bluetooth is Off");
        mStatusTv.setTextColor(Color.RED);

//        mActivateBtn.setText("Enable");
//        mActivateBtn.setEnabled(true);
        textViewSwitch.setText("Disable");
//        switchBluetooth.setEnabled(true);
        //  switchBluetooth.setChecked(false);
        //  View view = switchBluetooth.findViewById(R.id.switchBluetooth);
        //  view.performClick();
        PreferenceClass.putBoolean("isBluetooth", false);
        mPairedBtn.setEnabled(false);
        mScanBtn.setEnabled(false);
    }

    private void showUnsupported() {
        mStatusTv.setText("Bluetooth is unsupported by this device");

        // mActivateBtn.setText("Enable");
        // mActivateBtn.setEnabled(false);
        textViewSwitch.setText("Disable");
        switchBluetooth.setChecked(false);
        PreferenceClass.putBoolean("isBluetooth", false);
        mPairedBtn.setEnabled(false);
        mScanBtn.setEnabled(false);
    }

    int i = 0;
    public final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, @NonNull Intent intent) {
            //    	showToast("broadcast main"+ intent.getAction());
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);

                if (state == BluetoothAdapter.STATE_ON) {
                    showToast("Enabled");
                    switchBluetooth.setChecked(true);
                    showEnabled();
                } else {
                    showDisabled();
                    switchBluetooth.setChecked(false);
                    showToast("Disabled");
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                mDeviceList.clear();

//				mProgressDlg.show();

                // main_dialog.show();
                linScanner.setVisibility(View.VISIBLE);
                linMain.setVisibility(View.GONE);
                // rippleBackground.startRippleAnimation();


            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {

                //  main_dialog.dismiss();
                linScanner.setVisibility(View.GONE);
                linMain.setVisibility(View.VISIBLE);

                // rippleBackground.stopRippleAnimation();
                //  animatorSet.end();
                Intent newIntent = new Intent(SettingPrinterActivity.this, DeviceListActivity.class);
                HashSet h = new HashSet(mDeviceList);
                mDeviceList.clear();
                mDeviceList.addAll(h);
                newIntent.putParcelableArrayListExtra("device.list", mDeviceList);

                startActivity(newIntent);
                finish();
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                final BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Log.d(TAG, "onReceive: " + device.getBluetoothClass().getDeviceClass() + " " + device.getName() + " " + (device.getBluetoothClass().getMajorDeviceClass() == BluetoothClass.Device.Major.IMAGING) + " " + device.getBluetoothClass().hasService(262144)+" "+(device.getBluetoothClass().getMajorDeviceClass() == BluetoothClass.Device.Major.HEALTH));
                System.out.println("foundDevice: " + Arrays.toString(device.getUuids()) + " " + device.getName() + " " + device.fetchUuidsWithSdp());
//                if (device.getUuids() != null) {
                mDeviceList.add(device);


                i++;
                SettingPrinterActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        foundDevice(i, device);


                    }
                });
//                }

                // showToast("Found device " + device.getName() + " " + device.getAddress());
            } else if (BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED.equals(action)) {
                // bluetoothAdapter.cancelDiscovery();
                try {
                    UUID BPP = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
                    // final BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                    // bluetoothAdapter.getRemoteDevice(bDevice.getAddress());
                    mmDevice = bluetoothAdapter.getRemoteDevice(bDevice.getAddress());
                    //  socket = createBluetoothSocket(device);
//                    try {

                    // socket = createBluetoothSocket(device);
//
//                    } catch (IOException e) {
//
//            //            showToast("Tidak dapat melakukan koneksi awal dengan printer bluetooth"); // Unable/
//                    }

                    try {
                        socket = bDevice.createRfcommSocketToServiceRecord(BPP);
                        Method m = bDevice.getClass().getMethod("createRfcommSocket", new Class[]{int.class});
                        socket = (BluetoothSocket) m.invoke(bDevice, Integer.valueOf(1));
                        // socket = device.createRfcommSocketToServiceRecord(BPP);
                        // socket = createBluetoothSocket(device);
//
//                    } catch (IOException e) {
//
//                        showToast("Tidak dapat melakukan koneksi awal dengan printer bluetooth"); // Unable
//                    }
                        // Method m = device.getClass().getMethod("createBond", int.class);
//                    Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[] { UUID.class });
//                    try {

                        // Method m = device.getClass().getMethod("createRfcommSocket", int.class);
                        //     Method m = device.getClass().getMethod("createInsecureRfcommSocket", int.class);

                        //   socket = (BluetoothSocket) m.invoke(device, 1);
                        // socket = (BluetoothSocket) m.invoke(device, BPP);

                        socket.connect();
                        // cetak(SettingPrinterActivity.this);
                        Log.d(TAG, "Connected 1 " + socket.isConnected());
                    } catch (Exception e) {
                        Log.d(TAG, "onReceive: " + e.toString());
                    }
                } catch (Exception e) {


                    Log.d(TAG, "print exception: " + e.toString());


//                Intent intent = new Intent(this, SettingPrinterActivity.class);
//                startActivity(intent);

                }
            }
//            else if (BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE.equals(action)) {
//                boolean isDiscoverable = resultCode > 0;
//                int discoverableDuration = resultCode;
//                if (isDiscoverable) {

//                final UUID uuid = UUID.fromString("a60f35f0-b93a-11de-8a39-08002009c666");
//                final String airLineName = "bluetoothserver";
//
//
//
//                 acceptThread = new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            BluetoothServerSocket   btserver = bluetoothAdapter.listenUsingRfcommWithServiceRecord(airLineName, uuid);
//                            socket = btserver.accept();
//
//                        } catch (IOException e) {
//
//                            Log.d("BLUETOOTH", e.getMessage());
//                            acceptThread.start();
//                        }
//                    }
//                });


//            }
//            else if(BluetoothDevice.ACTION_UUID.equals(action)) {
//                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                Parcelable[] uuidExtra = intent.getParcelableArrayExtra(BluetoothDevice.EXTRA_UUID);
//                for (Parcelable parcelable : uuidExtra) {
//                    System.out.println("\n  Device: " + device.getName() + ", " + device + ", Service: " + parcelable.toString());
//                }
//            }
        }


    };
    Thread acceptThread;

    private void foundDevice(int i, @NonNull BluetoothDevice device) {

        randomTextView.addKeyWord(device.getName() + "\n" + device.getAddress());


        randomTextView.show();
    }

    BluetoothDevice bDevice;
    //Initialize da new BroadcastReceiver mInstance
    @NonNull
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, @NonNull Intent intent) {
            // Get the received random number
            boolean receivedNumber = intent.getBooleanExtra("finish", false);
            boolean receivedSocket = intent.getBooleanExtra("socket", false);
            String deviceName = intent.getStringExtra("device_name");
            if (receivedNumber == true) {
//                mTestPrintBtn.setFocusableInTouchMode(true);
                mTestPrintBtn.setEnabled(true);
                mTestPrintBtn.setClickable(true);
                mTestPrintBtn.setText("Test Print");
            } else if (receivedSocket == false) {
                snackBarCustomAction(findViewById(R.id.btn_test_print), 0, "Tidak dapat terhubung ke printer bluetooth " + deviceName + "\n Pastikan printer bluetooth " + deviceName + "anda telah menyala", WARNING);
                IntentFilter filter = new IntentFilter();
                bDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.d(TAG, "onReceive: " + bDevice.getName());
//        String action = "android.bleutooth.device.action.UUID";
                filter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);


                registerReceiver(mReceiver, filter);
//                mTestPrintBtn.setFocusableInTouchMode(true);
                mTestPrintBtn.setEnabled(true);
                mTestPrintBtn.setClickable(true);
                mTestPrintBtn.setText("Test Print");
            }
            // Display da notification that the broadcast received
            //     Toast.makeText(context,"Received : " + receivedNumber,Toast.LENGTH_SHORT).show();
        }
    };

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//    }
}

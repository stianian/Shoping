package com.example.myapplication1.ui;


import android.app.Fragment;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;


import com.example.myapplication1.service.BluetoothLeService;
import com.example.myapplication1.Constant;
import com.example.myapplication1.sql.HistoryActivity;
import com.example.myapplication1.Login.LoginActivity;
import com.example.myapplication1.R;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



import static android.content.Context.BIND_AUTO_CREATE;


public class HomeFragment extends Fragment {
    @ViewInject(R.id.img_head)//事件注入中的头像
    private CircleImageView mImgHead;

    private TextView btnLogin;
    private TextView btnhistory;
    private TextView btnsecure;
    private Button export, open;



    private String[] strings1 = {"生鲜1", "生鲜2", "生鲜3", "生鲜4"};
    private String[] strings2 = {"生鲜1"};

    private String[][] kucunNUmber1 = {strings1, strings2};
    /*===============MY BLE VARS====================*/
    public static String HEART_RATE_MEASUREMENT = "0000ffe1-0000-1000-8000-00805f9b34fb";
    public static String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    ;
    public static String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    public static String EXTRAS_DEVICE_RSSI = "RSSI";
    private boolean mConnected = false;
    private String status = "disconnected";
    private String mDeviceName;
    private String mDeviceAddress;
    private String mRssi;
    private static BluetoothLeService mBluetoothLeService;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();
    private static BluetoothGattCharacteristic target_chara = null;
    /*===============END MY BLE VARS====================*/

//    private String filePath = "/storage/emulated/0/AndroidExcelDemo";

    private String filePath = Environment.getExternalStorageDirectory() + "/family_bill";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        btnLogin = (TextView) view.findViewById(R.id.txt_username);
//        btnsecure = (TextView) view.findViewById(R.id.secure);
        btnhistory = (TextView) view.findViewById(R.id.txt_my_orders);
        //warning=(TextView)view.findViewById(R.id.warning);
//        export = (Button) view.findViewById(R.id.excel);
//        open = (Button) view.findViewById(R.id.excel_open);

        initViews(view);






        return view;
    }
    private void initViews(View view) {
//        open.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Todo:export data to excel.
//                openDir(getContext());
//
//            }
//        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
////        export.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                //Todo:export data to excel.
//////                exportExcel(getContext());
////
//
//
//
//            }
//        });
//
        btnhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HistoryActivity.class);
                startActivity(intent);
            }
        });
//
//
//        btnsecure.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Constant.bleConnectFlag) {
//
//                    if (Constant.bleFirstConnectFlag) {
//                        Constant.bleFirstConnectFlag = false;
//                        mDeviceName = Constant.mDeviceName;
//                        mDeviceAddress = Constant.mDeviceAddress;
//                        mRssi = Constant.mRssi;
//                        // bindService(getActivity(), mServiceConnection, BIND_AUTO_CREATE);
//                        getContext().registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
//                        if (mBluetoothLeService != null) {
//
//                            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
//                            Log.d("Later", "Connect request result=" + result);
//                        }
//                        try {
//                            Intent service = new Intent(getActivity(), BluetoothLeService.class);
//                            getContext().bindService(service, mServiceConnection, BIND_AUTO_CREATE);
//                            Log.d("Later", "bind ok ");
//                        } catch (Exception e) {
//                            Log.d("Later", "bind err: " + e.toString());
//                        }
//
//
//                    }
//                } else {
//                    Toast.makeText(getContext(), "connect ble first!", Toast.LENGTH_SHORT).show();
//                }
//                if (Constant.bleConnectFlag) {
//
//                    if (Constant.bleFirstConnectFlag) {
//                        Constant.bleFirstConnectFlag = false;
//                        mDeviceName = Constant.mDeviceName;
//                        mDeviceAddress = Constant.mDeviceAddress;
//                        mRssi = Constant.mRssi;
//                        // bindService(getActivity(), mServiceConnection, BIND_AUTO_CREATE);
//                        getContext().registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
//                        if (mBluetoothLeService != null) {
//
//                            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
//                            Log.d("Later", "Connect request result=" + result);
//                        }
//                        try {
//                            Intent service = new Intent(getActivity(), BluetoothLeService.class);
//                            getContext().bindService(service, mServiceConnection, BIND_AUTO_CREATE);
//                            Log.d("Later", "bind ok ");
//                        } catch (Exception e) {
//                            Log.d("Later", "bind err: " + e.toString());
//                        }
//
//
//                    }
//                } else {
//                    Toast.makeText(getContext(), "connect ble first!", Toast.LENGTH_SHORT).show();
//                }
//
//
//                new Thread().start();
//
//            }
//        });
    }

    //=========================MY BLE FUNCTIONS==================//
    private Handler mhandler = new Handler();
    private Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    String state = msg.getData().getString("connect_state");
                    //connect_state.setText(state);

                    break;
                }

            }
            super.handleMessage(msg);
        }

    };

    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName,
                                       IBinder service) {

            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service)
                    .getService();
            if (!mBluetoothLeService.initialize()) {
//                    finish();
            }
            // Automatically connects to the device upon successful start-up
            // initialization.
            try {
                mBluetoothLeService.connect(mDeviceAddress);
            } catch (Exception e) {
            }


        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }

    };

    /**
     *
     */
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action))//Gatt���ӳɹ�
            {
                mConnected = true;
                status = "connected";
//                    updateConnectionState(status);
                // System.out.println("BroadcastReceiver :" + "device connected");

            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action))//Gatt����ʧ��
            {
                mConnected = false;
                status = "disconnected";
//                    updateConnectionState(status);
                // System.out.println("BroadcastReceiver :" + "device disconnected");

            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action))//����GATT������
            {

                // Show all the supported services and characteristics on the
                // user interface.

                displayGattServices(mBluetoothLeService.getSupportedGattServices());
                // System.out.println("BroadcastReceiver :" + "device SERVICES_DISCOVERED");
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action))//��Ч����
            {
//                    displayData(intent.getExtras().getString(
//                            BluetoothLeService.EXTRA_DATA));
                //  System.out.println("BroadcastReceiver onData:" + intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
            }
        }
    };


    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter
                .addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }


    /**
     * @param
     * @return void
     * @throws
     * @Title: displayGattServices
     * @Description:
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void displayGattServices(List<BluetoothGattService> gattServices) {

        if (gattServices == null)
            return;
        String uuid = null;
        String unknownServiceString = "unknown_service";
        String unknownCharaString = "unknown_characteristic";

        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();

        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData = new ArrayList<ArrayList<HashMap<String, String>>>();

        mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();

        // Loops through available GATT Services.
        for (BluetoothGattService gattService : gattServices) {
            HashMap<String, String> currentServiceData = new HashMap<String, String>();
            uuid = gattService.getUuid().toString();
            gattServiceData.add(currentServiceData);
            //System.out.println("Service uuid:" + uuid);
            ArrayList<HashMap<String, String>> gattCharacteristicGroupData = new ArrayList<HashMap<String, String>>();
            List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
            ArrayList<BluetoothGattCharacteristic> charas = new ArrayList<BluetoothGattCharacteristic>();
            // Loops through available Characteristics.
            for (final BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                charas.add(gattCharacteristic);
                HashMap<String, String> currentCharaData = new HashMap<String, String>();
                uuid = gattCharacteristic.getUuid().toString();
                if (gattCharacteristic.getUuid().toString()
                        .equals(HEART_RATE_MEASUREMENT)) {
                    mhandler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            mBluetoothLeService
                                    .readCharacteristic(gattCharacteristic);
                        }
                    }, 200);
                    mBluetoothLeService.setCharacteristicNotification(gattCharacteristic, true);
                    target_chara = gattCharacteristic;
                    // mBluetoothLeService.writeCharacteristic(gattCharacteristic);
                }
                List<BluetoothGattDescriptor> descriptors = gattCharacteristic
                        .getDescriptors();
                for (BluetoothGattDescriptor descriptor : descriptors) {
                    System.out.println("---descriptor UUID:"
                            + descriptor.getUuid());
                    mBluetoothLeService.getCharacteristicDescriptor(descriptor);
                    // mBluetoothLeService.setCharacteristicNotification(gattCharacteristic,
                    // true);
                }

                gattCharacteristicGroupData.add(currentCharaData);
            }
            mGattCharacteristics.add(charas);
            gattCharacteristicData.add(gattCharacteristicGroupData);

        }

    }

    /**
     *
     **/
//    public int[] dataSeparate(int len)
//    {
//        int[] lens = new int[2];
//        lens[0]=len/20;
//        lens[1]=len-20*lens[0];
//        return lens;
//    }

    //=================END MY BLE FUNCTIONS==========================//

    public class Thread extends java.lang.Thread {
        @Override
        public void run() {

            String strings = "\u0001\u0002\u0003\u0004";

            try {
                target_chara.setValue(strings);
                mBluetoothLeService.writeCharacteristic(target_chara);
            } catch (Exception e) {
                Log.d("Later", "Send: " + e.toString());
            }


        }

    }


    private void openDir(Context context) {

        File file = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setDataAndType(Uri.fromFile(file), "file/*");
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getContext(), "没有正确打开文件管理器", Toast.LENGTH_SHORT).show();
        }
    }









}





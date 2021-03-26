package com.example.myapplication1;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BlueScanActivity extends AppCompatActivity implements View.OnClickListener {
    private Button scan_btn;
    BluetoothAdapter mBluetoothAdapter;
    private ArrayList<Integer> rssis;
    LeDeviceListAdapter mleDeviceListAdapter;
    Context context;

    ListView lv;
    private boolean mScanning;
    private boolean scan_flag;
    private Handler mHandler;
    int REQUEST_ENABLE_BT = 1;
    private static final long SCAN_PERIOD = 10000;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_scan);
        init();
        init_ble();
        scan_flag=true;
        mleDeviceListAdapter=new LeDeviceListAdapter();
        lv.setAdapter(mleDeviceListAdapter);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final BluetoothDevice device = mleDeviceListAdapter.getDevice(position);
                if (device == null)
                    return true;
//                final Intent intent = new Intent(BlueScanActivity.this, BleActivity.class);
//                intent.putExtra(BleActivity.EXTRAS_DEVICE_NAME,
//                        device.getName());
//                intent.putExtra(BleActivity.EXTRAS_DEVICE_ADDRESS,
//                        device.getAddress());
//                intent.putExtra(BleActivity.EXTRAS_DEVICE_RSSI,
//                        rssis.get(position).toString());
                Constant.mDeviceName=device.getName();
                Constant.mDeviceAddress=device.getAddress();
                Constant.mRssi=rssis.get(position).toString();
                Constant.bleConnectFlag=true;

                if (mScanning) {
                    mBluetoothAdapter.stopLeScan((BluetoothAdapter.LeScanCallback) mleDeviceListAdapter);

                    mScanning = true;
                    Toast.makeText(context,"请等待扫描完成",Toast.LENGTH_LONG).show();
                }
//                try {
//                    startActivity(intent);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                return true;

            }

        });

       // Toast.makeText(context,"连接成功",Toast.LENGTH_LONG).show();


    }

    private void init(){
        scan_btn=(Button)this.findViewById(R.id.scan_dev_btn);
        scan_btn.setOnClickListener(this);
        lv=(ListView)this.findViewById(R.id.lv);
        mHandler=new Handler();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void init_ble(){
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_ACTIVITIES_ON_SECONDARY_DISPLAYS)){
            Toast.makeText(this,"不支持ble",Toast.LENGTH_SHORT).show();
            finish();
        }
        final BluetoothManager bluetoothManager =(BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter=bluetoothManager.getAdapter();
        if(mBluetoothAdapter==null||!mBluetoothAdapter.isEnabled()){
            Intent enableBtIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }




    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onClick(View view){
        if (scan_flag)
        {
            mleDeviceListAdapter = new LeDeviceListAdapter();
            lv.setAdapter(mleDeviceListAdapter);
            scanLeDevice(true);
        } else
        {

            scanLeDevice(false);
            scan_btn.setText("扫描设备");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void scanLeDevice(final boolean enable)
    {
        if (enable)
        {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable()
            {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
                @Override
                public void run()
                {
                    mScanning = false;
                    scan_flag = true;
                    scan_btn.setText("扫描设备");
                  //  Toast.makeText(context,"扫描完成",Toast.LENGTH_SHORT).show();
                    Log.i("SCAN", "stop.....................");
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD);

            Log.i("SCAN", "begin.....................");
            mScanning = true;
            scan_flag = false;
            scan_btn.setText("停止扫描");
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else
        {
            Log.i("Stop", "stoping................");
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
            scan_flag = true;
        }

    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback()
    {

        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi,
                             byte[] scanRecord)
        {
            // TODO Auto-generated method stub

            runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {

                    mleDeviceListAdapter.addDevice(device, rssi);
                    mleDeviceListAdapter.notifyDataSetChanged();
                }
            });

            System.out.println("Address:" + device.getAddress());
            System.out.println("Name:" + device.getName());
            System.out.println("rssi:" + rssi);

        }
    };


    private class LeDeviceListAdapter extends BaseAdapter {
        private ArrayList<BluetoothDevice> mLeDevices;

        private LayoutInflater mInflator;

        public LeDeviceListAdapter()
        {
            super();
            rssis = new ArrayList<Integer>();
            mLeDevices = new ArrayList<BluetoothDevice>();
            mInflator = getLayoutInflater();
        }

        public void addDevice(BluetoothDevice device, int rssi)
        {
            if (!mLeDevices.contains(device))
            {
                mLeDevices.add(device);
                rssis.add(rssi);
            }
        }

        public BluetoothDevice getDevice(int position)
        {
            return mLeDevices.get(position);
        }

        public void clear()
        {
            mLeDevices.clear();
            rssis.clear();
        }

        @Override
        public int getCount()
        {
            return mLeDevices.size();
        }

        @Override
        public Object getItem(int i)
        {
            return mLeDevices.get(i);
        }

        @Override
        public long getItemId(int i)
        {
            return i;
        }

        /**
         * ��дgetview
         *
         * **/
        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {

            // General ListView optimization code.
            view = mInflator.inflate(R.layout.item_blue_list, null);
            TextView deviceAddress = (TextView) view
                    .findViewById(R.id.tv_deviceAddr);
            TextView deviceName = (TextView) view
                    .findViewById(R.id.tv_deviceName);
            TextView rssi = (TextView) view.findViewById(R.id.tv_rssi);

            BluetoothDevice device = mLeDevices.get(i);
            deviceAddress.setText(device.getAddress());
            deviceName.setText(device.getName());
            rssi.setText("" + rssis.get(i));

            return view;
        }
    }

}

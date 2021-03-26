package com.example.myapplication1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.myapplication1.utils.ExcelUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressLint("SimpleDateFormat")
public class PrintActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mFoodEdt;
    private EditText mArticlesEdt;
    private EditText mTrafficEdt;
    private EditText mTravelEdt;
    private EditText mClothesEdt;
    private EditText mDoctorEdt;
    private EditText mRenQingEdt;
    private EditText mBabyEdt;
    private EditText mLiveEdt;
    private EditText mOtherEdt;
    private EditText mRemarkEdt;
    private Button mSaveBtn;
    private File file;
    private String[] title = {  "食物支出", "日用品项", "交通话费", "旅游出行", "穿着支出", "医疗保健", "人情客往", "宝宝专项", "房租水电", "其它支出", "备注说明" };
    private String[] saveData;
    private DBHelper mDbHelper;
    private ArrayList<ArrayList<String>>bill2List;

    ///////////////////////
    ///
    ///   存储权限
    ///
    //////////////////////
    String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS};
    List<String> mPermissionList = new ArrayList<>();

    // private ImageView welcomeImg = null;
    private static final int PERMISSION_REQUEST = 1;
    // 检查权限

    private void checkPermission() {
        mPermissionList.clear();

        //判断哪些权限未授予
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        /**
         * 判断是否为空
         */
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了

        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(PrintActivity.this, permissions, PERMISSION_REQUEST);
        }
    }

    /**
     * 响应授权
     * 这里不管用户是否拒绝，都进入首页，不再重复申请权限
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST:
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkPermission();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);
        findViewsById();
        mDbHelper = new DBHelper(this);
        mDbHelper.open();
        bill2List=new ArrayList<ArrayList<String>>();
    }

    private void findViewsById() {
        mFoodEdt = (EditText) findViewById(R.id.family_bill_food_edt);
        mArticlesEdt = (EditText) findViewById(R.id.family_bill_articles_edt);
        mTrafficEdt = (EditText) findViewById(R.id.family_bill_traffic_edt);
        mTravelEdt = (EditText) findViewById(R.id.family_bill_travel_edt);
        mClothesEdt = (EditText) findViewById(R.id.family_bill_clothes_edt);
        mDoctorEdt = (EditText) findViewById(R.id.family_bill_doctor_edt);
        mRenQingEdt = (EditText) findViewById(R.id.family_bill_laiwang_edt);
        mBabyEdt = (EditText) findViewById(R.id.family_bill_baby_edt);
        mLiveEdt = (EditText) findViewById(R.id.family_bill_live_edt);
        mOtherEdt = (EditText) findViewById(R.id.family_bill_other_edt);
        mRemarkEdt = (EditText) findViewById(R.id.family_bill_remark_edt);
        mSaveBtn = (Button) findViewById(R.id.family_bill_save);
        mSaveBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.family_bill_save) {
//            saveData = new String[] { new SimpleDateFormat("yyyy-MM-dd").format(new Date()), mFoodEdt.getText().toString().trim(), mArticlesEdt.getText().toString().trim(), mTrafficEdt.getText().toString().trim(), mTravelEdt.getText().toString().trim(), mClothesEdt.getText().toString().trim(), mDoctorEdt.getText().toString().trim(), mRenQingEdt.getText().toString().trim(), mBabyEdt.getText().toString().trim(), mLiveEdt.getText().toString().trim(), mOtherEdt.getText().toString().trim(), mRemarkEdt.getText().toString().trim() };

            saveData = new String[] { mFoodEdt.getText().toString().trim(), mArticlesEdt.getText().toString().trim(), mTrafficEdt.getText().toString().trim(), mTravelEdt.getText().toString().trim(), mClothesEdt.getText().toString().trim(), mDoctorEdt.getText().toString().trim(), mRenQingEdt.getText().toString().trim(), mBabyEdt.getText().toString().trim(), mLiveEdt.getText().toString().trim(), mOtherEdt.getText().toString().trim(), mRemarkEdt.getText().toString().trim() };
            if (canSave(saveData)) {
            ContentValues values = new ContentValues();
//            values.put("time", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                values.put("food", mFoodEdt.getText().toString());
                values.put("use", mArticlesEdt.getText().toString());
                values.put("traffic", mTrafficEdt.getText().toString());
                values.put("travel", mTravelEdt.getText().toString());
                values.put("clothes", mClothesEdt.getText().toString());
                values.put("doctor", mDoctorEdt.getText().toString());
                values.put("laiwang", mRenQingEdt.getText().toString());
                values.put("baby", mBabyEdt.getText().toString());
                values.put("live", mLiveEdt.getText().toString());
                values.put("other", mOtherEdt.getText().toString());
                values.put("remark", mRemarkEdt.getText().toString());


//            values.put("food", 1);
//            values.put("use", 2);
//            values.put("traffic", 3);
//            values.put("travel", 4);
//            values.put("clothes", 2);
//            values.put("doctor", 1);
//            values.put("laiwang", 2);
//            values.put("baby", 3);
//            values.put("live", 1);
//            values.put("other",2);
//            values.put("remark", 3);

                long insert = mDbHelper.insert("family_bill", values);
            initData();
                if (insert > 0) {

                }
        }
            else {
                Toast.makeText(this, "请填写任意一项内容", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    public void initData() {
        file = new File(getSDPath() + "/Family");
        makeDir(file);
        ExcelUtils.initExcel(file.toString() + "/bill.xls", title);
        ExcelUtils.writeObjListToExcel(getBillData(), getSDPath() + "/Family/bill.xls", this);
    }

    private ArrayList<ArrayList<String>> getBillData() {
        Cursor mCrusor = mDbHelper.exeSql("select * from family_bill");
        while (mCrusor.moveToNext()) {
            ArrayList<String> beanList=new ArrayList<String>();
            beanList.add(mCrusor.getString(1));
            beanList.add(mCrusor.getString(2));
            beanList.add(mCrusor.getString(3));
            beanList.add(mCrusor.getString(4));
            beanList.add(mCrusor.getString(5));
            beanList.add(mCrusor.getString(6));
            beanList.add(mCrusor.getString(7));
            beanList.add(mCrusor.getString(8));
            beanList.add(mCrusor.getString(9));
            beanList.add(mCrusor.getString(10));
            beanList.add(mCrusor.getString(11));
            beanList.add(mCrusor.getString(12));
            bill2List.add(beanList);
        }
        mCrusor.close();
        return bill2List;
    }

    public static void makeDir(File dir) {
        if (!dir.getParentFile().exists()) {
            makeDir(dir.getParentFile());
        }
        dir.mkdir();
    }

    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        String dir = sdDir.toString();
        return dir;

    }

    private boolean canSave(String[] data) {
        boolean isOk = false;
        for (int i = 0; i < data.length; i++) {
            if (i > 0 && i < data.length) {
                if (!TextUtils.isEmpty(data[i])) {
                    isOk = true;
                }
            }
        }
        return isOk;
    }
}

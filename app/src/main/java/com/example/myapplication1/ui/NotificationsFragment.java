package com.example.myapplication1.ui;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import com.example.myapplication1.Constant;
import com.example.myapplication1.MainActivity;
import com.example.myapplication1.R;
import com.example.myapplication1.adapter.MyListViewAdapter1;
import com.example.myapplication1.adapter.MyListViewAdapter2;
import com.example.myapplication1.http.HttpHelper;
import com.example.myapplication1.utils.JSONUtil;
import org.greenrobot.eventbus.EventBus;


public class NotificationsFragment extends Fragment {

    private static final int NOTIFICATION_SERVICE = 0;
    HttpHelper httpMethed=new HttpHelper();
    private int selectIndex=0;

    private static final String[] mMenus = {"饮品", "水果"};
    private String[] strs1={"可乐","雪碧","冰红茶","柠檬"};
    private String[] strs2={"苹果"};
    private String[] strings1={"生鲜1","生鲜2","生鲜3","生鲜4"};
    private String[] strings2={"生鲜1"};
    private String[] strings3={"生鲜1","生鲜2","生鲜3","生鲜4"};
    private String[] strings4={"生鲜1"};
    private String[][] allData={strs1,strs2 };
    private ListView mListView1,mListView2;
    private MyListViewAdapter1 adapter1;
    private MyListViewAdapter2 adapter2;
    String url1 , url2;
    private String[][] kucunNUmber={strings1,strings2};
    private String[][] warning={strings3,strings4};
    private Button order;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view= inflater.inflate(R.layout.fragment_notifications,container,false);
        mListView1= (ListView)view. findViewById(R.id.list_item_1);
        mListView2= (ListView)view. findViewById(R.id.list_item_2);
        order=(Button) view.findViewById(R.id.btn_add);
        url1 = Constant.Host + Constant.DeviceID + "/datastreams/" ;
        url2=Constant.Host+ Constant.DeviceID1 + "/datastreams/" ;    //"humidity";
        initView();
        new Thread().start();
        new Thread1().start();
//        createNotificition();
        return view;
    }

    private void initView() {


            adapter1=new MyListViewAdapter1(mMenus,getContext(),selectIndex);
            adapter2=new MyListViewAdapter2(allData, getContext(),selectIndex,kucunNUmber,warning);



            EventBus.getDefault().post(kucunNUmber);



            mListView1.setAdapter(adapter1);
            mListView2.setAdapter(adapter2);

            mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectIndex=position;
                //把下标传过去，然后刷新adapter
                adapter1.setIndex(position);
                adapter1.notifyDataSetChanged();
                //当点击某个item的时候让这个item自动滑动到listview的顶部(下面item够多，如果点击的是最后一个就不能到达顶部了)
                mListView1.smoothScrollToPositionFromTop(position,0);

             adapter2.setIndex(position);
             mListView2.setAdapter(adapter2);

            }
        });

       mListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // ToastUtils.showToast(NotificationsFragment.this,allData[selectIndex][position]);


            }
       });



    }
//    private void updateOne(int position) {
//        /**第一个可见的位置**/
//        int firstVisiblePosition = mListView2.getFirstVisiblePosition();
//        /**最后一个可见的位置**/
//        int lastVisiblePosition = mListView2.getLastVisiblePosition();
//
//        /**在看见范围内才更新，不可见的滑动后自动会调用getView方法更新**/
//        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
//            /**获取指定位置view对象**/
//            View view = mListView2.getChildAt(position - firstVisiblePosition);
//            adapter2.setIndex(position);
//        // mListView2.setAdapter(adapter2);
//
//        }
//    }

    public class Thread1 extends java.lang.Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    try {
                        Message msg=new Message();
                        msg.what=1;
                        Bundle bundle=new Bundle();
                        String msg1 = httpMethed.doGet(url1, Constant.ApiKey);
                        final String[] Newdata = JSONUtil.Json2(msg1);
                        String dataAll=Newdata[0];
                        for(int i=0;i<Newdata.length-1;i++)
                            dataAll=dataAll+","+Newdata[i+1];
                        bundle.putString("num",dataAll);

                        msg.setData(bundle);
                        handler.sendMessage(msg);
                        Log.d("Later", "ok");
                    } catch (Exception e) {
                    }

                    Thread.sleep(5000);
                } catch (InterruptedException e) {

                }

            }

        }

    }



    public class Thread extends java.lang.Thread{
        @Override
        public void run() {
            while (true) {
                try {
                    try {
                        Message sendmsg=new Message();
                        sendmsg.what=1;
                        Bundle bundle=new Bundle();
                        String msg1 = httpMethed.doGet(url2, Constant.ApiKey1);

                        final String[] Newdata = JSONUtil.Json1(msg1);
                        String[] str=new String[5];
                        for(int i=0;i<5;i++){
                            if(Newdata[i].equals("1")){
                                str[i]="需要";
                            }else if(Newdata[i].equals("0")){
                                str[i]="不需要";
                            }
                        }




                        String dataAll=str[0];
                        for(int i=0;i<str.length-1;i++)
                            dataAll=dataAll+","+str[i+1];
                        bundle.putString("warning",dataAll );
                        sendmsg.setData(bundle);
                        handler1.sendMessage(sendmsg);
                        Log.d("Later", "ok");

//                        String[] str1=new String[5];
//                        if(Newdata[0].equals("0")){
//                            str1[0]="可乐补货";
//                        }else if(Newdata[1].equals("0")){
//                            str1[1]="雪碧补货";
//                        }else if(Newdata[2].equals("0")){
//                            str1[2]="冰红茶补货";
//                        }else if(Newdata[3].equals("0")){
//                            str1[3]="柠檬水补货";
//                        }else if(Newdata[4].equals("0")){
//                            str1[4]="苹果补货";
//                        }
//                        String dataAll1=str1[0];
//                        for(int i=0;i<str1.length-1;i++)
//                            dataAll1=dataAll1+","+str1[i+1];
//
                        for(int i=0;i<5;i++){
                            if(Newdata[i].equals("1")){
                                NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

                                // 构建 Notification
                                Notification.Builder builder = new Notification.Builder(getContext());
                                builder.setContentTitle("缺货预警")
                                        .setSmallIcon(R.drawable.ic_launcher_background)
                                        .setContentText("有商品需要补货");

                                    // 兼容  API 26，Android 8.0
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                                    // 第三个参数表示通知的重要程度，默认则只在通知栏闪烁一下
                                    NotificationChannel notificationChannel = new NotificationChannel("AppTestNotificationId", "AppTestNotificationName", NotificationManager.IMPORTANCE_DEFAULT);
                                    // 注册通道，注册后除非卸载再安装否则不改变
                                    notificationManager.createNotificationChannel(notificationChannel);
                                    builder.setChannelId("AppTestNotificationId");
                                }
                                // 发出通知
                                notificationManager.notify(1, builder.build());
                            }
                        }


                    } catch (Exception e) {
                    }

                    Thread.sleep(5000);
                } catch (InterruptedException e) {

                }
                Log.d("Later", "thread not try");
            }
        }

    }

    Handler handler1 =new Handler(){
        public void handleMessage(Message sendmsg){
            if(sendmsg.what==1){


                String str=sendmsg.getData().getString("warning");
                String data[]=str.split(",");
                for(int i=0;i<data.length;i++)
                    warning[i/4][i%4]=data[i];
                 initView();

            }
        }
    };






    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            if(msg.what==1){
                // ArrayList arrayList=msg.getData().getParcelableArrayList("msg");

                String str=msg.getData().getString("num");
                String data[]=str.split(",");
                for(int i=0;i<data.length;i++){
                    kucunNUmber[i/4][i%4]=data[i];
                }

                initView();

            }
        }
    };



//    private void createNotificition(){
//        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
//
//// 构建 Notification
//        Notification.Builder builder = new Notification.Builder(getContext());
//        builder.setContentTitle("缺货预警")
//                .setSmallIcon(R.drawable.ic_launcher_background)
//                .setContentText("warning[0][1]");
//
//// 兼容  API 26，Android 8.0
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            // 第三个参数表示通知的重要程度，默认则只在通知栏闪烁一下
//            NotificationChannel notificationChannel = new NotificationChannel("AppTestNotificationId", "AppTestNotificationName", NotificationManager.IMPORTANCE_DEFAULT);
//            // 注册通道，注册后除非卸载再安装否则不改变
//            notificationManager.createNotificationChannel(notificationChannel);
//            builder.setChannelId("AppTestNotificationId");
//        }
//// 发出通知
//        notificationManager.notify(1, builder.build());
//    }


//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void createNotificationChannel(String channel_id, String channel_name, String channel_desc, int importance, String group_id){
//        NotificationManager manager = (NotificationManager) getActivity().getSystemService(String.valueOf(NOTIFICATION_SERVICE));
//        //配置通知渠道id,渠道名称（用户可以看到），渠道优先级
//        NotificationChannel mChannel = new NotificationChannel(channel_id, channel_name,importance);
//        //配置通知渠道的描述
//        mChannel.setDescription(channel_desc);
//        //配置通知出现时的闪灯（如果 android 设备支持的话）
//        mChannel.enableLights(true);
//        mChannel.setLightColor(Color.RED);
//        //配置通知出现时的震动（如果 android 设备支持的话）
//        mChannel.enableVibration(true);
//        mChannel.setVibrationPattern(new long[]{100, 200, 100, 200});
//        //配置渠道组
//        if(group_id!=null){
//            mChannel.setGroup(group_id);//设置渠道组
//        }
//        //在NotificationManager中创建该通知渠道
//        manager.createNotificationChannel(mChannel);
//    }


}

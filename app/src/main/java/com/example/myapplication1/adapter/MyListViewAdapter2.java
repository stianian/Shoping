package com.example.myapplication1.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication1.R;
import com.example.myapplication1.sql.BlackDao;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by a on 2020/5/3.
 */
public class MyListViewAdapter2 extends BaseAdapter {
    private  String[][] allData;
    private  Context context;
    private  int selectIndex;
    private BlackDao blackDao;
    private String[][] kucunnumber;
    private int temp;
    private String[][] warning;
    private int value;





    private String[] price1={"1","2","3","4"};
    private String[] price2={"100"};
    private String[][] priceAll={price1, price2};

    private int[] picture1={R.drawable.pictiure1,R.drawable.picture2,R.drawable.pictiure1,R.drawable.picture2};
    private int[] picture2={R.drawable.pictiure1};
    private int[][] pictureAll={picture1,picture2};



    /**
     * 这部分用来传参数，从NotifiFragment中参数传过来
     * @param allData
     * @param context
     * @param selectIndex
     * @param kucunnumber
     */
    /**
     *因为用了dialog 所有原先Context context改为AppCompatActivity context，同样其他相应地方改好
     */
    public MyListViewAdapter2(String[][] allData, Context context, int selectIndex, String[][] kucunnumber,String[][] warning) {
        this.allData=allData;
        this.context=context;
        this.selectIndex=selectIndex;
        this.kucunnumber=kucunnumber;
        this.warning=warning;



    }

/**
    public ContactAdapter(Context context,int selectIndex,int position){
        this.context= (AppCompatActivity) context;
        this.selectIndex=selectIndex;
        this.position=position;
        mOnClickListener=(View.OnClickListener)context;
    }

    public interface OnClickListener{
        public void setSelsectedNum(int num);
    }

    mOnClickListener.setSelectedNum(getSelextSi);
*/
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        blackDao = BlackDao.getInstance(context);
        ViewHolder vh=null;


        if(convertView==null){
            convertView=View.inflate(context, R.layout.item_listview_2,null);

            vh=new ViewHolder();
            vh.tv= (TextView) convertView.findViewById(R.id.textview);
            vh.kucun=(TextView)convertView.findViewById(R.id.kucun);

           vh.newwarning=(TextView)convertView.findViewById(R.id.warning);
            vh.imageView=(ImageView)convertView.findViewById(R.id.image);

            vh.btn=(Button)convertView.findViewById(R.id.btn_ok) ;
            convertView.setTag(vh);
        }else {
            vh= (ViewHolder) convertView.getTag();
        }








        vh.tv.setText(allData[selectIndex][position]);
        vh.kucun.setText(kucunnumber[selectIndex][position]);
       vh.newwarning.setText(warning[selectIndex][position]);

        vh.imageView.setImageResource(pictureAll[selectIndex][position]);

        vh.btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                temp=Integer.valueOf(kucunnumber[selectIndex][position]);
                show(selectIndex,position,temp);
            }
        });

        return convertView;
    }







    public void setIndex(int index){ selectIndex=index; }

    public final class ViewHolder{
        public TextView tv;
        private TextView kucun,newwarning;
        public Button btn;
        private ImageView imageView;
    }


    @Override
    public int getCount() {
        return allData[selectIndex].length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }




    public void show(int selectIndex,int position,int temp){
        int temp1=Integer.valueOf(kucunnumber[selectIndex][position]);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH.mm.ss");

        Date date = new Date();
        String s = simpleDateFormat.format(date);
        String[] strs1={"可乐","雪碧","冰红茶","柠檬"};
        String[] strs2={"苹果"};
        String[][] number={strs1,strs2};
        blackDao.addBlackNum(number[selectIndex][position], temp,s);//数据加入数据库

    }



}

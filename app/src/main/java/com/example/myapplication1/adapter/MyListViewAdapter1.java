package com.example.myapplication1.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication1.R;


/**
 * Created by a on 2016/5/12.
 */
public class MyListViewAdapter1 extends BaseAdapter{

    private final String[] mMenus;
    private final Context context;
    private int selectIndex;

    public MyListViewAdapter1(String[] mMenus, Context context, int selectIndex){
        this.mMenus=mMenus;
        this.context=context;
        this.selectIndex=selectIndex;
    }
    @Override
    public int getCount() {
        return mMenus.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.item_listview_1,null);
            vh=new ViewHolder();
            vh.tv= (TextView) convertView.findViewById(R.id.textview);
            convertView.setTag(vh);
        }else {
            vh= (ViewHolder) convertView.getTag();
        }

        LinearLayout.LayoutParams selectParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
       //左上右下
        selectParams.setMargins(1,1,0,0);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(1,1,1,0);

        if(position==selectIndex){
            vh.tv.setBackgroundColor(Color.parseColor("#eb4f38"));
            vh.tv.setLayoutParams(selectParams);
        }else {
            vh.tv.setBackgroundColor(Color.parseColor("#dedede"));
            vh.tv.setLayoutParams(params);
        }


        vh.tv.setText(mMenus[position]);
        return convertView;
    }

    public void setIndex(int index){
        selectIndex = index;
    }

    class ViewHolder{
        TextView tv;
    }
}

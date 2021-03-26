package com.example.myapplication1.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 解析Json数组
 * @param
 * {"errno":0,"data":{"update_at":"2019-04-07 08:14:23","unit":"","id":"shidu","unit_symbol":"","current_value":111},"error":"succ"}


 */
public class JSON {
    public static String[] parseJSONWithJSONObject(String JsonData) {
        String[] Newdata=new String[2];
        try
        {
            String data=null;
            String current_value=null;
            JSONArray jsonArray1 = new JSONArray("[" + JsonData + "]");
            for (int i=0; i < jsonArray1.length(); i++)    {
                JSONObject jsonObject = jsonArray1.getJSONObject(i);
                data = jsonObject.getString("data");
            }
            JSONArray jsonArray2 = new JSONArray("[" + data + "]");
            for (int i=0; i < jsonArray2.length(); i++)    {
                JSONObject jsonObject = jsonArray2.getJSONObject(i);
                current_value = jsonObject.getString("current_value");
                String update_at = jsonObject.getString("update_at");
                Newdata[0]=current_value;
                Newdata[0]= Newdata[0] .replaceAll(" ","");   //只能在这改
                Log.d("Later", "replace:" + Newdata[0]);
                Newdata[1]=update_at;   //时间戳
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return Newdata;
    }
}

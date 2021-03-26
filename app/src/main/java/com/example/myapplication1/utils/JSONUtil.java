package com.example.myapplication1.utils;

import android.util.Log;

import com.example.myapplication1.Constant;
import com.example.myapplication1.http.HttpHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class JSONUtil {


    public static String[] Json(String JsonData){

        String[] Weight=new String[5];

        try{
            String data=null;
            String current_value=null;
            JSONArray jsonArray1=new JSONArray("[" + JsonData + "]");
            for (int i=0; i < jsonArray1.length(); i++)    {
                JSONObject jsonObject = jsonArray1.getJSONObject(i);
                data = jsonObject.getString("data");
            }
            JSONArray jsonArray=new JSONArray(data );
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject1=(JSONObject) jsonArray.get(i);
                current_value=jsonObject1.getString("current_value");
                Weight[i]=current_value;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Weight;
    }



    public static String[] Json1(String JsonData){

        String[] Weight=new String[5];

        try{
            String data=null;
            String current_value=null;
            JSONArray jsonArray1=new JSONArray("[" + JsonData + "]");
            for (int i=0; i < jsonArray1.length(); i++)    {
                JSONObject jsonObject = jsonArray1.getJSONObject(i);
                data = jsonObject.getString("data");
            }
            JSONArray jsonArray=new JSONArray(data );
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject1=(JSONObject) jsonArray.get(i);
                String id=jsonObject1.getString(("id"));
                if(id.equals("alarm1")){
                    Weight[4] = jsonObject1.getString("current_value");
                }else if ( id.equals("alarm2")) {

                    Weight[1] = jsonObject1.getString("current_value"); /* 雪碧 */
                } else if ( id.equals("alarm3")) {

                    Weight[2] = jsonObject1.getString("current_value"); /* 冰红茶 */
                } else if ( id.equals("alarm4")) {

                    Weight[3] = jsonObject1.getString("current_value"); /* 柠檬 */
                } else if ( id.equals("alarm5")) {

                    Weight[0] = jsonObject1.getString("current_value"); /* 苹果 */
                }
//                current_value=jsonObject1.getString("current_value");
//                Weight[i]=current_value;
            }
            return Weight;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }




    public static String[] Json2(String JsonData){

        String[] Weight;

        try{
            String data=null;
            String current_value=null;
            JSONArray jsonArray1=new JSONArray("[" + JsonData + "]");
            for (int i=0; i < jsonArray1.length(); i++)    {
                JSONObject jsonObject = jsonArray1.getJSONObject(i);
                data = jsonObject.getString("data");
            }
            JSONArray jsonArray=new JSONArray(data );
            if ( jsonArray.length() < 5 ) {

                return null;
            }
            Weight = new String[5];
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject1=(JSONObject) jsonArray.get(i);
                String id = jsonObject1.getString("id");
                if ( id.equals("fresh1wh") ) {

                    Weight[0] = jsonObject1.getString("current_value"); /* 可乐 */
                } else if ( id.equals("fresh2wh")) {

                    Weight[1] = jsonObject1.getString("current_value"); /* 雪碧 */
                } else if ( id.equals("fresh3wh")) {

                    Weight[2] = jsonObject1.getString("current_value"); /* 冰红茶 */
                } else if ( id.equals("fresh4wh")) {

                    Weight[3] = jsonObject1.getString("current_value"); /* 柠檬 */
                } else if ( id.equals("flnum")) {

                    Weight[4] = jsonObject1.getString("current_value"); /* 苹果 */
                }

//                current_value=jsonObject1.getString("current_value");
//
//                Weight[i]=current_value;

            }
            Log.d("Debug", Weight == null ? "NULL" : Weight[0]);
            return Weight;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }



}

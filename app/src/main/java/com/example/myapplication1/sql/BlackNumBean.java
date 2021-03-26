package com.example.myapplication1.sql;

/**
 * Created by Administrator on 2017/6/29.
 */
public class BlackNumBean {
    public String number;
    public int mode;
    public String totalnum;

    public BlackNumBean() {
        super();
        // TODO Auto-generated constructor stub
    }

    public BlackNumBean(String number, int mode,String totalnum ) {
        super();
        this.number = number;
        this.mode = mode;
        this.totalnum=totalnum;
    }

    @Override
    public String toString() {

        return "BlackNumBean [number=" + number + ", mode=" + mode +  ",total="+  totalnum +  "]";
    }
}

package com.example.myapplication1.utils;

/**
 * @author dmrfcoder
 * @date 2019/2/14
 */

public class DemoBean {
    private String name;
    private int num;
    private int price;

    public DemoBean(String name, int num, int price) {
        this.name = name;
        this.num = num;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int price) {
        this.num = num;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}

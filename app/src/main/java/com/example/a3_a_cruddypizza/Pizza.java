package com.example.a3_a_cruddypizza;

import java.io.Serializable;

public class Pizza  implements Serializable {
    private int id, size, topping1, topping2, topping3;

    public Pizza(int size, int topping1, int topping2, int topping3) {
        this.size = size;
        this.topping1 = topping1;
        this.topping2 = topping2;
        this.topping3 = topping3;
    }


    public int getID(){return id;}
    public void setId(int id){this.id = id;}
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTopping1() {
        return topping1;
    }

    public void setTopping1(int topping1) {
        this.topping1 = topping1;
    }

    public int getTopping2() {
        return topping2;
    }

    public void setTopping2(int topping2) {
        this.topping2 = topping2;
    }

    public int getTopping3() {
        return topping3;
    }

    public void setTopping3(int topping3) {
        this.topping3 = topping3;
    }
}

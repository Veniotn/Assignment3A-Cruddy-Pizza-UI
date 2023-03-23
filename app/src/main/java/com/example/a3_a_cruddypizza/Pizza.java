package com.example.a3_a_cruddypizza;

public class Pizza {
    String size, topping1, topping2, topping3;

    public Pizza(String size, String topping1, String topping2, String topping3) {
        this.size = size;
        this.topping1 = topping1;
        this.topping2 = topping2;
        this.topping3 = topping3;
    }


    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTopping1() {
        return topping1;
    }

    public void setTopping1(String topping1) {
        this.topping1 = topping1;
    }

    public String getTopping2() {
        return topping2;
    }

    public void setTopping2(String topping2) {
        this.topping2 = topping2;
    }

    public String getTopping3() {
        return topping3;
    }

    public void setTopping3(String topping3) {
        this.topping3 = topping3;
    }
}

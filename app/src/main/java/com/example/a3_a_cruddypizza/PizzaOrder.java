package com.example.a3_a_cruddypizza;

import java.time.LocalDate;

public class PizzaOrder {

    private   int       orderID;
    private   LocalDate orderDate;
    private   Pizza     Pizza;


    public PizzaOrder(LocalDate orderDate, int orderID) {
        this.orderDate = orderDate;
        this.orderID = orderID;
    }


    public int getOrderID() {
        return orderID;
    }


    public LocalDate getOrderDate() {
        return orderDate;
    }

    public Pizza getPizza() {
        return Pizza;
    }

}

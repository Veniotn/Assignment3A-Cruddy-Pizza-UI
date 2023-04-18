package com.example.a3_a_cruddypizza;

import java.io.Serializable;
import java.time.LocalDate;

public class PizzaOrder implements Serializable {

    private   final int     orderID;
    private   final String  orderDate;
    private   Pizza         pizza;


    public PizzaOrder(String orderDate, int orderID, Pizza pizza) {
        this.orderDate = orderDate;
        this.orderID = orderID;
        this.pizza =  pizza;
    }


    public int getOrderID() {
        return orderID;
    }


    public String getOrderDate() {
        return orderDate;
    }

    public Pizza getPizza() {return pizza;}

    public void setPizza(Pizza pizza){this.pizza = pizza;}

}

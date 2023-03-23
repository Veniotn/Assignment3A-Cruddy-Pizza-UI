package com.example.a3_a_cruddypizza;

import java.time.LocalDate;
import java.util.ArrayList;

public class Customer {
    private String firstName, lastName, login;
    private int totalOrders;
    private ArrayList<PizzaOrder> pizzaOrders;

    public Customer(String firstName, String lastName, String login) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.totalOrders = 0;
        this.pizzaOrders = new ArrayList<PizzaOrder>();
    }

    public void addPizzaOrder() {
        //Add one to the total orders to be used as the order ID and make a new order.
        this.totalOrders++;
        this.pizzaOrders.add(new PizzaOrder(LocalDate.now(), totalOrders));
    }
}

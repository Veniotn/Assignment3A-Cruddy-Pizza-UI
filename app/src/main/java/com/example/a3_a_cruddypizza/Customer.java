package com.example.a3_a_cruddypizza;

import android.content.Context;
import android.database.Cursor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Customer implements Serializable {

    private String firstName, lastName, login;
    private int totalOrders, customerID;
    private ArrayList<PizzaOrder> pizzaOrders;
    private DBAdapter dbConnection;

    public Customer(String firstName, String lastName, String login) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.totalOrders = 0;
        this.pizzaOrders = new ArrayList<PizzaOrder>();
    }

    public Customer(String login){
        this.firstName = login;
        this.lastName = login;
        this.login   = login;
        this.totalOrders = 0;
        this.pizzaOrders = new ArrayList<PizzaOrder>();
    }


    public int getCustomerID(){return customerID;}
    public void setCustomerID(int customerID){
        this.customerID = customerID;
    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void addOrder() {
        this.totalOrders++;
    }

    public ArrayList<PizzaOrder> getPizzaOrders() {
        return pizzaOrders;
    }

    public void setLogin(Cursor queryResult) {

    }
}

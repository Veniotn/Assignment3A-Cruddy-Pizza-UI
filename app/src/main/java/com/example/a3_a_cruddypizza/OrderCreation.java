package com.example.a3_a_cruddypizza;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class OrderCreation extends BasicActivity {

    private TextView headerText, selectSizePrompt, toppingOnePrompt, toppingTwoPrompt, toppingThreePrompt;
    private Spinner sizeSpinner, toppingOneSpinner, toppingTwoSpinner, toppingThreeSpinner;
    private Button confirmOrderButton, changeLanguageButton, backButton;
    private Intent orderConfirmationScreen, mainMenu;



    enum index {

        CHANGE_LANGUAGE_BUTTON,
        BACK_BUTTON,
        HEADER_TEXT,
        SIZE_PROMPT,
        TOPPING_ONE_PROMPT,
        TOPPING_TWO_PROMPT,
        TOPPING_THREE_PROMPT,
        CONFIRM_BUTTON
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_creation);

        customer = getIntent().getSerializableExtra("customer", Customer.class);

        headerText         = findViewById(R.id.orderCreationTopTextView);
        selectSizePrompt   = findViewById(R.id.sizePromptTextView);
        toppingOnePrompt   = findViewById(R.id.toppingOnePromptTextView);
        toppingTwoPrompt   = findViewById(R.id.toppingTwoPromptTextView);
        toppingThreePrompt = findViewById(R.id.toppingThreePromptTextView);


        changeLanguageButton = findViewById(R.id.orderHistoryChangeLanguageButton);
        backButton           = findViewById(R.id.backButton);
        confirmOrderButton   = findViewById(R.id.confirmOrderButton);

        changeLanguageButton.setOnClickListener(buttonClicked);
        backButton.setOnClickListener(buttonClicked);
        confirmOrderButton.setOnClickListener(buttonClicked);

        sizeSpinner         = findViewById(R.id.sizeSpinner);
        toppingOneSpinner   = findViewById(R.id.toppingOneSpinner);
        toppingTwoSpinner   = findViewById(R.id.toppingTwoSpinner);
        toppingThreeSpinner = findViewById(R.id.toppingThreeSpinner);


        preferences = new SharedPreferenceHelper(this);


        orderConfirmationScreen = new Intent(getApplicationContext(), OrderConfirmation.class);
        mainMenu                = new Intent(getApplicationContext(), MainMenu.class);

        dbConnection = new DBAdapter(this);



        updateLanguage();
    }


    public View.OnClickListener buttonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.orderHistoryChangeLanguageButton:
                    preferences.onUpdate();
                    updateLanguage();
                    break;
                case R.id.backButton:
                    mainMenu.putExtra("customer", customer);
                    startActivity(mainMenu);
                    break;
                case R.id.confirmOrderButton:
                    addOrder();
                    startActivity(orderConfirmationScreen);
                    break;
            }
        }
    };

    @Override
    protected void updateLanguage() {
        String[] textArray  = preferences.isFrench() ? getResources().getStringArray(R.array.orderCreationFrench)
                : getResources().getStringArray(R.array.orderCreationEnglish);
        ArrayList<String> textOptions = new ArrayList<>(Arrays.asList(textArray));

        changeLanguageButton.setText(textOptions.get(index.CHANGE_LANGUAGE_BUTTON.ordinal()));
        backButton.setText(textOptions.get(index.BACK_BUTTON.ordinal()));
        headerText.setText(textOptions.get(index.HEADER_TEXT.ordinal()));
        selectSizePrompt.setText(textOptions.get(index.SIZE_PROMPT.ordinal()));
        toppingOnePrompt.setText(textOptions.get(index.TOPPING_ONE_PROMPT.ordinal()));
        toppingTwoPrompt.setText(textOptions.get(index.TOPPING_TWO_PROMPT.ordinal()));
        toppingThreePrompt.setText(textOptions.get(index.TOPPING_THREE_PROMPT.ordinal()));
        confirmOrderButton.setText(textOptions.get(index.CONFIRM_BUTTON.ordinal()));

        //reuse the same array and arraylist for the spinner text
        textArray = preferences.isFrench() ? getResources().getStringArray(R.array.sizeOptionsFrench)
                                           : getResources().getStringArray(R.array.sizeOptionsEnglish);


        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, textArray);
        sizeSpinner.setAdapter(spinnerAdapter);
        spinnerAdapter.notifyDataSetChanged();


        textArray = preferences.isFrench() ? getResources().getStringArray(R.array.toppingsFrench)
                                           : getResources().getStringArray(R.array.toppingsEnglish);
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, textArray);
        toppingOneSpinner.setAdapter(spinnerAdapter);
        toppingTwoSpinner.setAdapter(spinnerAdapter);
        toppingThreeSpinner.setAdapter(spinnerAdapter);
        spinnerAdapter.notifyDataSetChanged();



    }

    public void addOrder(){
        //get their index in the spinner arrays
        int size, top1, top2, top3;

        size = sizeSpinner.getSelectedItemPosition();
        top1 = toppingOneSpinner.getSelectedItemPosition();
        top2 = toppingTwoSpinner.getSelectedItemPosition();
        top3 = toppingThreeSpinner.getSelectedItemPosition();

        dbConnection.open();
        //attempt to insert pizza into db
        insertID = dbConnection.insertPizza(size, top1, top2, top3);
        dbConnection.close();

        if (insertID <= 0){
            //insertion failed
            headerText.setText(R.string.confirmOrderTextFR);
        }

        //get current date
        Date currentDate = new Date();
        DateFormat dateFormat = DateFormat.getDateInstance();
        //convert to string
        String orderDate = dateFormat.format(currentDate);

        //attempt to insert order into order table
        dbConnection.open();
        insertID = dbConnection.insertOrder(orderDate, customer.getCustomerID(), insertID);
        dbConnection.close();
        if (insertID <= 0){
            //error
            headerText.setText(R.string.confirmOrderTextEN);
        }
        //get the pizzaID


        //create a new pizza order and add it to the customers order list
        // then pass the pizza to the order confirmation screen.
        customer.getPizzaOrders().add(new PizzaOrder(orderDate, (int)insertID,
                                      new Pizza(size, top1, top2, top3)));
        PizzaOrder pizzaOrder = customer.getPizzaOrders().get(customer.getTotalOrders());
        orderConfirmationScreen.putExtra("pizzaOrder", pizzaOrder);
        orderConfirmationScreen.putExtra("customer", customer);

        //add one to the customers total orders.
        customer.addOrder();
    }

}
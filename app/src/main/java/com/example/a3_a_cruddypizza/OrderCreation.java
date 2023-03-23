package com.example.a3_a_cruddypizza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import java.time.LocalDate;
import java.util.ArrayList;

public class OrderCreation extends AppCompatActivity {

    ArrayList<PizzaOrder> orders = new ArrayList<PizzaOrder>();

    Spinner spinner;

    Button confirmOrderButton;

    Intent orderConfirmationScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_creation);


        spinner = findViewById(R.id.sizeSpinner);
        spinner.setPrompt("test");


        confirmOrderButton = findViewById(R.id.confirmOrderButton);
        confirmOrderButton.setOnClickListener(buttonClicked);



        orderConfirmationScreen = new Intent(getApplicationContext(), OrderConfirmation.class);


        LocalDate currentDate = LocalDate.now();


        orders.add(new PizzaOrder(currentDate))
    }


    public View.OnClickListener buttonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(orderConfirmationScreen);
        }
    };
}
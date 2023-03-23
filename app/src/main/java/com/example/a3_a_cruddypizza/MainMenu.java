package com.example.a3_a_cruddypizza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {

    Button createOrderButton, viewOrderHistoryButton, changeLanguageButton,
    logoutButton;

    TextView welcomeUserTextView;

    Intent orderCreation, orderHistory, startScreen;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        changeLanguageButton = findViewById(R.id.changeLanguageButton);
        changeLanguageButton.setOnClickListener(buttonClicked);

        welcomeUserTextView = findViewById(R.id.welcomeUserTextView);

        createOrderButton = findViewById(R.id.createOrderButton);
        createOrderButton.setOnClickListener(buttonClicked);


        viewOrderHistoryButton = findViewById(R.id.viewOrderHistoryButton);
        viewOrderHistoryButton.setOnClickListener(buttonClicked);

        logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(buttonClicked);


        orderCreation = new Intent(getApplicationContext(), OrderCreation.class);
        orderHistory  = new Intent(getApplicationContext(), OrderHistory.class);
        startScreen   = new Intent(getApplicationContext(), MainActivity.class);



        Customer customer = new Customer("nick", "veniot", "rat");
        orderHistory.pute
    }



    public View.OnClickListener buttonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.createOrderButton:
                    startActivity(orderCreation);
                    break;
                case R.id.viewOrderHistoryButton:
                    startActivity(orderHistory);
                    break;
                case R.id.changeLanguageButton:
                    break;
                case R.id.logoutButton:
                    startActivity(startScreen);
                default:
                    break;

            }
        }
    };
}
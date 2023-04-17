package com.example.a3_a_cruddypizza;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainMenu extends BasicActivity {

    private Button createOrderButton, viewOrderHistoryButton, changeLanguageButton,
    logoutButton;

    private TextView welcomeUserTextView;

    private Intent orderCreation, orderHistory, startScreen;

    private enum index{
        CHANGE_LANGUAGE_BUTTON,
        WELCOME_USER_TEXTVIEW,
        CREATE_ORDER_BUTTON,
        VIEW_ORDER_HISTORY_BUTTON,
        LOGOUT_BUTTON
    }


    Pizza pizza;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //setup activity
        customer = getIntent().getSerializableExtra("customer", Customer.class);

        welcomeUserTextView = findViewById(R.id.welcomeUserTextView);

        changeLanguageButton = findViewById(R.id.orderHistoryChangeLanguageButton);
        changeLanguageButton.setOnClickListener(buttonClicked);
        createOrderButton = findViewById(R.id.createOrderButton);
        createOrderButton.setOnClickListener(buttonClicked);
        viewOrderHistoryButton = findViewById(R.id.viewOrderHistoryButton);
        viewOrderHistoryButton.setOnClickListener(buttonClicked);
        logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(buttonClicked);


        orderCreation = new Intent(getApplicationContext(), OrderCreation.class);
        orderHistory  = new Intent(getApplicationContext(), OrderHistory.class);
        startScreen   = new Intent(getApplicationContext(), MainActivity.class);

        preferences = new SharedPreferenceHelper(this);


        updateLanguage();
    }



    public View.OnClickListener buttonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.createOrderButton:
                    orderCreation.putExtra("Customer", customer);
                    startActivity(orderCreation);
                    break;
                case R.id.viewOrderHistoryButton:
                    orderHistory.putExtra("Customer", customer);
                    startActivity(orderHistory);
                    break;
                case R.id.orderHistoryChangeLanguageButton:
                    preferences.onUpdate();
                    updateLanguage();
                    break;
                case R.id.logoutButton:
                    startScreen.putExtra("Customer", customer);
                    startActivity(startScreen);
                default:
                    break;

            }
        }
    };


    @Override
    protected void updateLanguage() {
        String[] array = preferences.isFrench() ? getResources().getStringArray(R.array.mainMenuFrench)
                                                : getResources().getStringArray(R.array.mainMenuEnglish);
        ArrayList<String> textOptions = new ArrayList<>(Arrays.asList(array));


        changeLanguageButton.setText(textOptions.get(index.CHANGE_LANGUAGE_BUTTON.ordinal()));
        welcomeUserTextView.setText(textOptions.get(index.WELCOME_USER_TEXTVIEW.ordinal()));
        createOrderButton.setText(textOptions.get(index.CREATE_ORDER_BUTTON.ordinal()));
        viewOrderHistoryButton.setText(textOptions.get(index.VIEW_ORDER_HISTORY_BUTTON.ordinal()));
        logoutButton.setText(textOptions.get(index.LOGOUT_BUTTON.ordinal()));
    }
}
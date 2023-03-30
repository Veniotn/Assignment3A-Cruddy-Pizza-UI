package com.example.a3_a_cruddypizza;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class OrderConfirmation extends BasicActivity {

    Button changeLanguageButton, mainMenuButton;
    TextView headerText, orderIdPrompt, orderDatePrompt, pizzaSizePrompt, toppingsPrompt,
                         orderIdText,   orderDateText,   pizzaSizeText,   toppingsText;

    Intent mainMenu;
    SharedPreferenceHelper preferences;

    enum index{
        CHANGE_LANGUAGE,
        HEADER_TEXT,
        ORDERID_PROMPT,
        ORDER_DATE_PROMPT,
        PIZZA_SIZE_PROMPT,
        TOPPINGS_PROMPT,
        MAIN_MENU_BUTTON
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        changeLanguageButton = findViewById(R.id.changeLanguageButton);
        changeLanguageButton.setOnClickListener(buttonClicked);

        mainMenuButton = findViewById(R.id.mainMenuButton);
        mainMenuButton.setOnClickListener(buttonClicked);

        headerText = findViewById(R.id.headerTextView);
        orderIdPrompt = findViewById(R.id.orderIDPrompt);
        orderIdText = findViewById(R.id.orderIDText);
        orderDatePrompt = findViewById(R.id.orderDatePrompt);
        orderDateText  = findViewById(R.id.orderDateText);
        pizzaSizePrompt = findViewById(R.id.pizzaSizePrompt);
        pizzaSizeText  = findViewById(R.id.pizzaSizeText);
        toppingsPrompt = findViewById(R.id.toppingsPrompt);
        toppingsText     = findViewById(R.id.toppingsText);

        mainMenu = new Intent(getApplicationContext(), MainMenu.class);
        preferences = new SharedPreferenceHelper(this);


        updateLanguage();
    }

    View.OnClickListener buttonClicked  = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.changeLanguageButton:
                    preferences.onUpdate();
                    updateLanguage();
                    break;
                case R.id.mainMenuButton:
                    startActivity(mainMenu);
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void updateLanguage() {
        String [] array = preferences.isFrench() ? getResources().getStringArray(R.array.orderConfirmationFrench)
                                                 : getResources().getStringArray(R.array.orderConfirmationEnglish);
        ArrayList<String> textOptions = new ArrayList<>(Arrays.asList(array));

            changeLanguageButton.setText(textOptions.get(index.CHANGE_LANGUAGE.ordinal()));
            headerText.setText(textOptions.get(index.HEADER_TEXT.ordinal()));
            orderIdPrompt.setText(textOptions.get(index.ORDERID_PROMPT.ordinal()));
            orderDatePrompt.setText(textOptions.get(index.ORDER_DATE_PROMPT.ordinal()));
            pizzaSizePrompt.setText(textOptions.get(index.PIZZA_SIZE_PROMPT.ordinal()));
            toppingsPrompt.setText(textOptions.get(index.TOPPINGS_PROMPT.ordinal()));
            mainMenuButton.setText(textOptions.get(index.MAIN_MENU_BUTTON.ordinal()));
    }
}
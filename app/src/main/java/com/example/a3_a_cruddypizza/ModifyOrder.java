package com.example.a3_a_cruddypizza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class ModifyOrder extends BasicActivity{

    private TextView modifyOrderHeader, sizePrompt, toppingOnePrompt, toppingTwoPrompt,toppingThreePrompt;
    private Spinner sizeSpinner, toppingOneSpinner,toppingTwoSpinner, toppingThreeSpinner;
    private Button confirmButton, backButton, changeLanguageButton;

    private Intent orderHistory;
    private SharedPreferenceHelper preferences;

    private Pizza pizza;


    private enum index{
        BACK_BUTTON,
        LANGUAGE_BUTTON,
        HEADER_TEXT,
        SIZE_PROMPT,
        TOP_ONE_PROMPT,
        TOP_TWO_PROMPT,
        TOP_THREE_PROMPT,
        CONFIRM_TEXT
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_order);

        dbConnection = new DBAdapter(this);

        customer = getIntent().getSerializableExtra("customer", Customer.class);
        pizza = getIntent().getSerializableExtra("pizza", Pizza.class);

        modifyOrderHeader = findViewById(R.id.editOrderHeaderText);
        sizePrompt        = findViewById(R.id.editSizePrompt);
        toppingOnePrompt  = findViewById(R.id.editTopping1Prompt);
        toppingTwoPrompt  = findViewById(R.id.editTopping2Prompt);
        toppingThreePrompt = findViewById(R.id.editTopping3Prompt);

        sizeSpinner = findViewById(R.id.editSizeSpinner);
        toppingOneSpinner = findViewById(R.id.editTopping1Spinner);
        toppingTwoSpinner = findViewById(R.id.editTopping2Spinner);
        toppingThreeSpinner = findViewById(R.id.editTopping3Spinner);

        confirmButton = findViewById(R.id.confirmEditButton);
        confirmButton.setOnClickListener(buttonClicked);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(buttonClicked);

        changeLanguageButton = findViewById(R.id.changeLanguageButton);
        changeLanguageButton.setOnClickListener(buttonClicked);

        orderHistory = new Intent(getApplicationContext(), OrderHistory.class);


        preferences  = new SharedPreferenceHelper(this);



        updateLanguage();
    }


    public View.OnClickListener buttonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.changeLanguageButton:
                    preferences.onUpdate();
                    updateLanguage();
                    break;
                case R.id.backButton:
                    orderHistory.putExtra("customer", customer);
                    startActivity(orderHistory);
                    break;
                case R.id.confirmEditButton:
                    modifyPizza();
                    orderHistory.putExtra("customer", customer);
                    startActivity(orderHistory);
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void updateLanguage() {
        String[] array = preferences.isFrench() ? getResources().getStringArray(R.array.modifyOrderFrench)
                                                : getResources().getStringArray(R.array.modifyOrderEnglish);
        ArrayList<String> textOptions  = new ArrayList<>(Arrays.asList(array));

        backButton.setText(textOptions.get(index.BACK_BUTTON.ordinal()));
        changeLanguageButton.setText(textOptions.get(index.LANGUAGE_BUTTON.ordinal()));
        modifyOrderHeader.setText(textOptions.get(index.HEADER_TEXT.ordinal()));
        sizePrompt.setText(textOptions.get(index.SIZE_PROMPT.ordinal()));
        toppingOnePrompt.setText(textOptions.get(index.TOP_ONE_PROMPT.ordinal()));
        toppingTwoPrompt.setText(textOptions.get(index.TOP_TWO_PROMPT.ordinal()));
        toppingThreePrompt.setText(textOptions.get(index.TOP_THREE_PROMPT.ordinal()));
        confirmButton.setText(textOptions.get(index.CONFIRM_TEXT.ordinal()));


        //reuse the same routine for the spinner objects but add the resources to the spinner
        // adapter and use the pizza properties to set the spinners default selection.
        array = preferences.isFrench() ? getResources().getStringArray(R.array.sizeOptionsFrench)
                                       : getResources().getStringArray(R.array.sizeOptionsEnglish);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                                                  android.R.layout.simple_spinner_item, array);
        sizeSpinner.setAdapter(spinnerAdapter);
        sizeSpinner.setSelection(pizza.getSize());
        spinnerAdapter.notifyDataSetChanged();


        array = preferences.isFrench() ? getResources().getStringArray(R.array.toppingsFrench)
                                       : getResources().getStringArray(R.array.toppingsEnglish);
        spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, array);

        toppingOneSpinner.setAdapter(spinnerAdapter);
        toppingOneSpinner.setSelection(pizza.getTopping1());
        toppingTwoSpinner.setAdapter(spinnerAdapter);
        toppingTwoSpinner.setSelection(pizza.getTopping2());
        toppingThreeSpinner.setAdapter(spinnerAdapter);
        toppingThreeSpinner.setSelection(pizza.getTopping3());
        //commit the changes and update the ui
        spinnerAdapter.notifyDataSetChanged();

    }

    public void modifyPizza(){
        int id, size, top1, top2, top3;
        //get values from spinners
        id   = pizza.getID();
        size = sizeSpinner.getSelectedItemPosition();
        top1 = toppingOneSpinner.getSelectedItemPosition();
        top2 = toppingTwoSpinner.getSelectedItemPosition();
        top3 = toppingThreeSpinner.getSelectedItemPosition();

        //update db and then update the pizza object
        dbConnection.open();
        if (dbConnection.updatePizza(id, size, top1,top2,top3)){
            dbConnection.close();
            pizza.setSize(size);
            pizza.setTopping1(top1);
            pizza.setTopping2(top2);
            pizza.setTopping3(top3);

            //return to the order history page
            orderHistory.putExtra("pizza", pizza);
            startActivity(orderHistory);
        }
    }


}
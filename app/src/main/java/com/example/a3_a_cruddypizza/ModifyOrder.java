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

    private Pizza.PizzaUpdated pizzaUpdatedListener;

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

        pizza = getIntent().getSerializableExtra("pizza", Pizza.class);

        //setup update pizza listener which will allow us to reflect the changes made to the pizza.
        pizzaUpdatedListener =  (Pizza.PizzaUpdated) getParent();

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
                default:
                    modifyPizza();
                    startActivity(orderHistory);
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


        array = preferences.isFrench() ? getResources().getStringArray(R.array.sizeOptionsFrench)
                                       : getResources().getStringArray(R.array.sizeOptionsEnglish);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                                                  android.R.layout.simple_spinner_item, array);
        sizeSpinner.setAdapter(spinnerAdapter);
        spinnerAdapter.notifyDataSetChanged();

        array = preferences.isFrench() ? getResources().getStringArray(R.array.toppingsFrench)
                                       : getResources().getStringArray(R.array.toppingsEnglish);
        spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, array);
        toppingOneSpinner.setAdapter(spinnerAdapter);
        toppingTwoSpinner.setAdapter(spinnerAdapter);
        toppingThreeSpinner.setAdapter(spinnerAdapter);
        spinnerAdapter.notifyDataSetChanged();

    }

    public void modifyPizza(){

        //update the properties of the pizza
        pizza.setSize(sizeSpinner.getSelectedItem().toString());
        pizza.setTopping1(toppingOneSpinner.getSelectedItem().toString());
        pizza.setTopping2(toppingTwoSpinner.getSelectedItem().toString());
        pizza.setTopping3(toppingThreeSpinner.getSelectedItem().toString());
//        commit the changes to the old pizza
        if (pizzaUpdatedListener != null){
            pizzaUpdatedListener.updatePizza(pizza);
        }
    }


}
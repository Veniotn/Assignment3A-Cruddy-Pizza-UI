package com.example.a3_a_cruddypizza;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class AccountCreation extends BasicActivity {

    private Button backButton, changeLanguageButton, createAccountButton;
    private TextView headerText, firstNamePrompt, lastNamePrompt, loginPrompt;
    private EditText firstNameEditText, lastNameEditText, loginEditText;
    private Intent loginScreen;
    enum index{
        BACK_BUTTON,
        LANGUAGE_BUTTON,
        HEADER_TEXT,
        FIRST_NAME_TEXT,
        LAST_NAME_TEXT,
        LOGIN_TEXT,
        CREATE_ACCOUNT_BUTTON
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);

        //buttons
        backButton            = findViewById(R.id.backButton);
        changeLanguageButton  = findViewById(R.id.orderHistoryChangeLanguageButton);
        createAccountButton   = findViewById(R.id.createAccountButton);

        //button listeners
        backButton.setOnClickListener(buttonClicked);
        changeLanguageButton.setOnClickListener(buttonClicked);
        createAccountButton.setOnClickListener(buttonClicked);

        //TextView
        headerText      = findViewById(R.id.headerTextView);
        firstNamePrompt = findViewById(R.id.firstNamePromptTextView);
        lastNamePrompt  = findViewById(R.id.lastNamePromptTextView);
        loginPrompt     = findViewById(R.id.loginPromptTextView);

        //edit text
        firstNameEditText = findViewById(R.id.firstnameEditText);
        lastNameEditText  = findViewById(R.id.lastnameEditText);
        loginEditText     = findViewById(R.id.loginEditText);


        //intents / preferences
        loginScreen = new Intent(getApplicationContext(), MainActivity.class);
        preferences = new SharedPreferenceHelper(this);

        //connect db
        dbConnection = new DBAdapter(this);

        updateLanguage();
    }



    public View.OnClickListener buttonClicked = new View.OnClickListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.backButton:
                    startActivity(loginScreen);
                    break;
                case R.id.orderHistoryChangeLanguageButton:
                    preferences.onUpdate();
                    updateLanguage();
                    break;
                case R.id.createAccountButton:
                    //if login is unique and customer has been inserted return to login.
                    if(createAccount()){
                        startActivity(loginScreen);
                    }
                    break;
                default:
                    break;

            }

        }
    };

    @Override
    protected void updateLanguage() {

        //get either french or english text based off preference variable
        String[] array =  preferences.isFrench() ? getResources().getStringArray(R.array.accountCreationFrench)
                                                 : getResources().getStringArray(R.array.accountCreationEnglish);
        ArrayList<String> textOptions = new ArrayList<>(Arrays.asList(array));


        //update UI
        backButton.setText(textOptions.get(index.BACK_BUTTON.ordinal()));
        changeLanguageButton.setText(textOptions.get(index.LANGUAGE_BUTTON.ordinal()));
        headerText.setText(textOptions.get(index.HEADER_TEXT.ordinal()));
        firstNamePrompt.setText(textOptions.get(index.FIRST_NAME_TEXT.ordinal()));
        lastNamePrompt.setText(textOptions.get(index.LAST_NAME_TEXT.ordinal()));
        loginPrompt.setText(textOptions.get(index.LOGIN_TEXT.ordinal()));
        createAccountButton.setText(textOptions.get(index.CREATE_ACCOUNT_BUTTON.ordinal()));
    }


    private boolean createAccount(){
        String firstName, lastName, login, invalidLoginText;

        //get prompt text according to preferences.
        invalidLoginText = preferences.isFrench() ? getResources().getString(R.string.loginExistsFR)
                                                  : getResources().getString(R.string.loginExistsEN);

        //grab info from edit texts
        firstName = firstNameEditText.getText().toString();
        lastName  = lastNameEditText.getText().toString();
        login     = loginEditText.getText().toString();

        //check that the login is unique, if a result is returned the login is not unique.
        dbConnection.open();
        queryResult = dbConnection.getCustomer(login);
        if (queryResult.moveToFirst()){
            dbConnection.close();
            headerText.setText(invalidLoginText);

            return false;
        }

        dbConnection.close();
        //attempt to insert customer into db, if greater than 0 insert was successful
        dbConnection.open();
        insertID = dbConnection.insertCustomer(firstName,lastName,login);
        dbConnection.close();
        if (insertID > 0){
            //add customer to login screen and to the db
            customer = new Customer(firstName,lastName,login);
            loginScreen.putExtra("customer", customer);

            return true;
        }

        return false;
    }
}
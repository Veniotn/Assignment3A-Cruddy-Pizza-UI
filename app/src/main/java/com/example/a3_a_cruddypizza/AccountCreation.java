package com.example.a3_a_cruddypizza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class AccountCreation extends BasicActivity {

    Button backButton, changeLanguageButton, createAccountButton;
    TextView headerText, firstNamePrompt, lastNamePrompt, loginPrompt;
    EditText firstNameEditText, lastNameEditText, loginEditText;
    Intent loginScreen;
    SharedPreferenceHelper preferences;
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
        changeLanguageButton  = findViewById(R.id.changeLanguageButton);
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

        updateLanguage();
    }



    public View.OnClickListener buttonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.backButton:
                    startActivity(loginScreen);
                    break;
                case R.id.changeLanguageButton:
                    preferences.onUpdate();
                    updateLanguage();
                    break;
                case R.id.createAccountButton:
                    startActivity(loginScreen);
                    System.out.println("");
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
}
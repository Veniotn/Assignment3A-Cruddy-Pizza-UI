package com.example.a3_a_cruddypizza;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends BasicActivity {

    Button loginButton, createAccountButton, changeLanguageButton;

    TextView welcomeTextView, copyrightTextView;
    EditText loginEditText;


    SharedPreferenceHelper preferences;

    Intent mainMenu, accountCreation;

    enum index{
        CHANGE_LANGUAGE_BUTTON,
        WELCOME_TEXTVIEW,
        LOGIN_TEXTVIEW,
        LOGIN_BUTTON,
        CREATE_ACCOUNT_BUTTON,
        COPYRIGHT_TEXT
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeLanguageButton = findViewById(R.id.changeLanguageButton);
        changeLanguageButton.setOnClickListener(buttonClicked);

        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(buttonClicked);

        createAccountButton = findViewById(R.id.createAccountButton);
        createAccountButton.setOnClickListener(buttonClicked);

        welcomeTextView   = findViewById(R.id.welcomeUserTextView);
        copyrightTextView = findViewById(R.id.bottomTextView);
        loginEditText     = findViewById(R.id.loginEditText);


        mainMenu          = new Intent(getApplicationContext(), MainMenu.class);
        accountCreation   = new Intent(getApplicationContext(), AccountCreation.class);

        preferences       = new SharedPreferenceHelper(this);


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
                case R.id.loginButton:
                    startActivity(mainMenu);
                    break;
                case R.id.createAccountButton:
                    startActivity(accountCreation);
                    break;
                default:
                    break;

            }
        }
    };


    public void updateLanguage(){
        //
        String[] textArray  = preferences.isFrench() ? getResources().getStringArray(R.array.loginScreenFrench)
                : getResources().getStringArray(R.array.loginScreenEnglish);
        ArrayList<String> textOptions = new ArrayList<>(Arrays.asList(textArray));

        changeLanguageButton.setText(textOptions.get(index.CHANGE_LANGUAGE_BUTTON.ordinal()));
        welcomeTextView.setText(textOptions.get(index.WELCOME_TEXTVIEW.ordinal()));
        loginEditText.setText(textOptions.get(index.LOGIN_TEXTVIEW.ordinal()));
        loginButton.setText(textOptions.get(index.LOGIN_BUTTON.ordinal()));
        createAccountButton.setText(textOptions.get(index.CREATE_ACCOUNT_BUTTON.ordinal()));
        copyrightTextView.setText(textOptions.get(index.COPYRIGHT_TEXT.ordinal()));
    }
}
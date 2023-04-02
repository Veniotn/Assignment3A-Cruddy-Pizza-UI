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

    //Ui
    private Button loginButton, createAccountButton, changeLanguageButton;
    private TextView welcomeTextView, copyrightTextView;
    private EditText loginEditText;

    //activities
    private Intent mainMenu, accountCreation;

    //used for text options when updating language
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

        //give everything functionality
        customer =  getIntent().getSerializableExtra("Customer", Customer.class);

        changeLanguageButton = findViewById(R.id.orderHistoryChangeLanguageButton);
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

        //When you first open the app the customer will be null, once we create an account
        // and return to the login screen, add the customer to the main menu
        // (WILL BE DB).

        if (customer!=null) {
            mainMenu.putExtra("Customer", customer);
        }

        //update the gui based on preferences.
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
                case R.id.loginButton:
                    validateLogin();
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
        //create a string array of the screens resources based off of the preferences .isFrench boolean.
        String[] textArray  = preferences.isFrench() ? getResources().getStringArray(R.array.loginScreenFrench)
                : getResources().getStringArray(R.array.loginScreenEnglish);
        //convert it to an arraylist
        ArrayList<String> textOptions = new ArrayList<>(Arrays.asList(textArray));

        //update the gui
        changeLanguageButton.setText(textOptions.get(index.CHANGE_LANGUAGE_BUTTON.ordinal()));
        welcomeTextView.setText(textOptions.get(index.WELCOME_TEXTVIEW.ordinal()));
        loginEditText.setText(textOptions.get(index.LOGIN_TEXTVIEW.ordinal()));
        loginButton.setText(textOptions.get(index.LOGIN_BUTTON.ordinal()));
        createAccountButton.setText(textOptions.get(index.CREATE_ACCOUNT_BUTTON.ordinal()));
        copyrightTextView.setText(textOptions.get(index.COPYRIGHT_TEXT.ordinal()));

    }


    public void validateLogin(){
        customer = new Customer("","","");//Used for testing

        if (customer == null ){
            //if theres no customer object prompt them to make a account
            welcomeTextView.setText(preferences.isFrench() ? R.string.createAccountScreenTextFR
                                                           : R.string.createAccountScreenTextEN);
        }
        else if (!loginEditText.getText().toString().equals(customer.getLogin())){

            //if the customer object has been created but the login is incorrect,
            // prompt the user.
            welcomeTextView.setText(preferences.isFrench() ? R.string.incorrectLoginFR
                                                           : R.string.incorrectLoginEN);
        }
        else {

        }

        //this wil, be in the else in the future, here now for testing.
        mainMenu.putExtra("Customer", customer);
        startActivity(mainMenu);

    }
}
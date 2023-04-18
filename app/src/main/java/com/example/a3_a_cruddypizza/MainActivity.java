package com.example.a3_a_cruddypizza;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import android.database.*;

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

        //check for customer from client-side
        customer = getIntent().getSerializableExtra("customer", Customer.class);




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

        //attempt to load db file
        loadDB();
        dbConnection = new DBAdapter(this);


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


    //the query is checking for a valid response before calling on the index so it will be there
    @SuppressLint("Range")
    public void validateLogin(){
        //check db for customer
        dbConnection.open();
        queryResult = dbConnection.getCustomer(loginEditText.getText().toString());
        dbConnection.close();

        if (queryResult.moveToFirst()){
            //if login is correct continue
            if (customer == null){
                //if customer object hasn't been passed from create account create one
                customer = new Customer(queryResult.getString(queryResult.getColumnIndex(DBAdapter.CUSTOMER_LOGIN)));
            }

            //set customerID
            dbConnection.open();
            queryResult = dbConnection.getCustomerID(customer.getLogin());
            dbConnection.close();
            //get the customerID column value as an integer.
            int customerID = Integer.parseInt(queryResult.getString(queryResult.getColumnIndex(DBAdapter.CUSTOMER_ID_PK)));
            customer.setCustomerID(customerID);

            mainMenu.putExtra("customer", customer);
            startActivity(mainMenu);
        }
        else {
            //incorrect login.
            welcomeTextView.setText(preferences.isFrench() ? R.string.incorrectLoginFR
                    : R.string.incorrectLoginEN);
        }
    }


    public void loadDB(){
        try{
            //get path of db file.
            String destPath = "/data/data/" + getPackageName() + "/database/CruddyPizzaDB";

            File dbFile = new File(destPath);

            //if app doesn't already have the db file, open it and write it to the dest path
            if (!dbFile.exists()){
                copyDB(getAssets().open(dbConnection.DB_NAME), new FileOutputStream(destPath));
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }



    public void copyDB(InputStream inputStream, OutputStream outputStream) throws IOException {
        //copy 1k bytes at a time
        byte[] buffer = new byte[1024];
        int length;
        //while the length of the string returned from .read is greater than 0
        while((length = inputStream.read(buffer)) > 0)
        {
            outputStream.write(buffer,0,length);
        }
        inputStream.close();
        outputStream.close();
    }//end method CopyDB
}
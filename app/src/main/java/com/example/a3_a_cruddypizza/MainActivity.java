package com.example.a3_a_cruddypizza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button loginButton, createAccountButton, changeLanguageButton;

    TextView welcomeTextView;
    EditText loginEditText;


    Intent mainMenu, accountCreation;

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

        welcomeTextView = findViewById(R.id.welcomeUserTextView);
        loginEditText = findViewById(R.id.loginEditText);


        mainMenu = new Intent(getApplicationContext(), MainMenu.class);
        accountCreation = new Intent(getApplicationContext(), AccountCreation.class);







    }


    public View.OnClickListener buttonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.changeLanguageButton:
                    break;
                case R.id.loginButton:
                    if ()
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
}
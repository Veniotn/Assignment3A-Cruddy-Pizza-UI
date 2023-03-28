package com.example.a3_a_cruddypizza;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Arrays;

public class SharedPreferenceHelper {



    private static final String PREFERENCE_NAME = "ApplicationPreferences";
    private SharedPreferences preferences;
    private boolean isFrench;

    public SharedPreferenceHelper(Context context){
        preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        isFrench    = false;
    }


    public boolean isFrench() {
        return isFrench;
    }

    public void setFrench(boolean french) {
        isFrench = french;
    }



    //methods
    public void changeLanguage(Context context){


        switch (context.getClass().getSimpleName()){
            case "MainActivity":
                break;
            case "MainMenu":
                break;
            case "OrderCreation":



                break;


        }


        if (isFrench){
            text =

        }
    }
}

package com.example.a3_a_cruddypizza;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Arrays;

public class SharedPreferenceHelper {

    private static final String LANGUAGE_KEY = "ApplicationPreferences";
    private SharedPreferences preferences;
    private boolean isFrench;
    private SharedPreferences.Editor editor;

    public SharedPreferenceHelper(Context context){
        preferences = context.getSharedPreferences(LANGUAGE_KEY, Context.MODE_PRIVATE);
        isFrench    = preferences.getBoolean(LANGUAGE_KEY, false);
    }


    public boolean isFrench() {
        return isFrench;
    }

    public void setFrench(boolean french) {
        isFrench = french;
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    public void setPreferences(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    //methods
    public void onUpdate(){
        //flip the boolean
        isFrench = !isFrench;
        editor = preferences.edit();
        editor.putBoolean(LANGUAGE_KEY, isFrench);
        editor.apply();
    }

}

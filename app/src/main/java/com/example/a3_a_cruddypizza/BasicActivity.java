package com.example.a3_a_cruddypizza;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

abstract class BasicActivity extends AppCompatActivity {


    //Since every activity has its own UI elements and arrays to draw from, we will make
    //each activity their own updateLanguage method.
    protected abstract void updateLanguage();
}

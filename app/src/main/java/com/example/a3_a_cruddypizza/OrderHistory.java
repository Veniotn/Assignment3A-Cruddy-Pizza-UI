package com.example.a3_a_cruddypizza;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class OrderHistory extends BasicActivity implements RecyclerViewInterface, Pizza.PizzaUpdated {

    private Button backButton, changeLanguageButton;
    private TextView headerText;
    private RecyclerView orderHistory;
    private OrderHistory_RecyclerAdapter recyclerAdapter;

    private Intent mainMenu, modifyOrder;




    private enum  index{
        BACK_BUTTON,
        LANGUAGE_BUTTON,
        HEADER_TEXT
    }


    ArrayList<PizzaOrder> orders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        customer = getIntent().getSerializableExtra("Customer", Customer.class);

        backButton = findViewById(R.id.backButton);
        changeLanguageButton = findViewById(R.id.orderHistoryChangeLanguageButton);

        headerText = findViewById(R.id.orderHistoryTextView);

        backButton.setOnClickListener(buttonClicked);
        changeLanguageButton.setOnClickListener(buttonClicked);

        mainMenu = new Intent(getApplicationContext(), MainMenu.class);
        modifyOrder = new Intent(getApplicationContext(), ModifyOrder.class);

        preferences = new SharedPreferenceHelper(this);



        orderHistory = findViewById(R.id.orderHistoryRecyclerView);

        fillOrderView();

        recyclerAdapter = new OrderHistory_RecyclerAdapter(orders, this, this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getApplicationContext());

        orderHistory.setLayoutManager(manager);
        orderHistory.setAdapter(recyclerAdapter);



        updateLanguage();
    }


    View.OnClickListener buttonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.backButton:
                    mainMenu.putExtra("Customer", customer);
                    startActivity(mainMenu);
                    break;
                case R.id.orderHistoryChangeLanguageButton:
                    preferences.onUpdate();
                    updateLanguage();
                    break;
            }

        }
    };

    @Override
    public void orderClicked(int position) {
        modifyOrder.putExtra("pizza", orders.get(position).getPizza());
        startActivity(modifyOrder);

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void updateLanguage() {
        String [] array = preferences.isFrench() ? getResources().getStringArray(R.array.orderHistoryFrench)
                                                 : getResources().getStringArray(R.array.orderHistoryEnglish);
        ArrayList<String> textOptions = new ArrayList<>(Arrays.asList(array));

        backButton.setText(textOptions.get(index.BACK_BUTTON.ordinal()));
        changeLanguageButton.setText(textOptions.get(index.LANGUAGE_BUTTON.ordinal()));
        headerText.setText(textOptions.get(index.HEADER_TEXT.ordinal()));

        //update recycler
        recyclerAdapter.notifyDataSetChanged();


    }

    @Override
    public void updatePizza(Pizza pizza) {

        //find the index of the current pizza in the old list
        int orderIndex = orders.indexOf(pizza);

        //update the pizza;
        orders.get(orderIndex).setPizza(pizza);

    }




    private void fillOrderView(){
        if (customer!=null){
            orders = customer.getPizzaOrders();
        }

        //all issues will be fixed when adding DB
        //set listeners on all pizza orders
//        for (PizzaOrder order : orders){
//            order.getPizza().setListener(this);
//        }
//
    }
}
package com.example.a3_a_cruddypizza;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

//https://www.youtube.com/watch?v=Mc0XT58A1Z4 Recycler view tutorial
public class OrderHistory_RecyclerAdapter extends RecyclerView.Adapter<OrderHistory_RecyclerAdapter.MyViewHolder> implements Serializable {
private final RecyclerViewInterface recyclerViewInterface;

    private Context context;
    private SharedPreferenceHelper preferences;
    private DBAdapter dbConnection;
    private RecyclerView recyclerView;
    ItemTouchHelper swipeToDelete;

    private ArrayList<PizzaOrder> orders;

    private enum index {
       ORDER_ID_PROMPT,
       SIZE_PROMPT,
       ORDER_DATE_PROMPT,
    }



    public OrderHistory_RecyclerAdapter(RecyclerView recyclerView, ArrayList<PizzaOrder> orders, Context context,
                                        RecyclerViewInterface recyclerViewInterface){
        this.context = context;
        this.orders = orders;
        this.recyclerViewInterface = recyclerViewInterface;
        this.dbConnection = new DBAdapter(context);
        this.recyclerView = recyclerView;

        //set the swipe listener
        swipeToDelete = new ItemTouchHelper(new SwipeToDelete(dbConnection, this));
        swipeToDelete.attachToRecyclerView(this.recyclerView);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements Serializable {

        Button changeLanguageButton;

        TextView orderIDPrompt, sizePrompt, orderDatePrompt,
                orderIDText,   sizeText,   orderDate;

        public MyViewHolder(View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            orderIDPrompt        = itemView.findViewById(R.id.orderIDPrompt);
            orderIDText          = itemView.findViewById(R.id.orderIDText);
            sizePrompt           = itemView.findViewById(R.id.pizzaSizePrompt);
            sizeText             = itemView.findViewById(R.id.pizzaSizeText);
            orderDatePrompt      = itemView.findViewById(R.id.orderDatePrompt);
            orderDate            = itemView.findViewById(R.id.orderDateText);
            changeLanguageButton = itemView.findViewById(R.id.orderHistoryChangeLanguageButton);

            //responds when user presses on order
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null){

                        //get the item that was clicked
                        int position = getAdapterPosition();

                        //if a item is found fire the onclick method in the order class
                        if (position != RecyclerView.NO_POSITION){
                        recyclerViewInterface.orderClicked(position);
                        }
                    }
                }
            });

        }
    }


    @NonNull
    @Override
    public OrderHistory_RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        preferences = new SharedPreferenceHelper(context);

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        return new MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistory_RecyclerAdapter.MyViewHolder holder, int position) {
        String [] array = preferences.isFrench() ? context.getResources().getStringArray(R.array.orderDetailsFR)
                                                 : context.getResources().getStringArray(R.array.orderDetailsEN);
        ArrayList<String> textOptions = new ArrayList<>(Arrays.asList(array));


        //Fill recycler card
        holder.orderIDPrompt.setText(textOptions.get(index.ORDER_ID_PROMPT.ordinal()));
        holder.orderIDText.setText(String.valueOf(orders.get(position).getOrderID()));
        holder.sizePrompt.setText(textOptions.get(index.SIZE_PROMPT.ordinal()));
        holder.orderDatePrompt.setText(textOptions.get(index.ORDER_DATE_PROMPT.ordinal()));
        holder.orderDate.setText(orders.get(position).getOrderDate());

       //get pizza size
        array = preferences.isFrench() ? context.getResources().getStringArray(R.array.sizeOptionsFrench)
                                       : context.getResources().getStringArray(R.array.sizeOptionsEnglish);
        textOptions = new ArrayList<>(Arrays.asList(array));
        holder.sizeText.setText(textOptions.get(orders.get(position).getPizza().getSize()));

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void onItemSwipe(int position){
        dbConnection.open();
        if (dbConnection.deleteOrder(orders.get(position).getOrderID())) {
            dbConnection.close();
            //if card position is not null alter and update the view.
            Objects.requireNonNull(recyclerView.getAdapter()).notifyItemRemoved(position);
            Toast.makeText(context.getApplicationContext(), preferences.isFrench() ?
                           R.string.orderDeletedFR : R.string.orderDeletedEN, Toast.LENGTH_SHORT).show();
        }

        dbConnection.close();


    }







}

package com.example.a3_a_cruddypizza;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

//https://www.youtube.com/watch?v=Mc0XT58A1Z4 Recycler view tutorial
public class OrderHistory_RecyclerAdapter extends RecyclerView.Adapter<OrderHistory_RecyclerAdapter.MyViewHolder> {

    Context context;
    SharedPreferenceHelper preferences;

    ArrayList<PizzaOrder> orders;

    enum index {
       ORDER_ID_PROMPT,
       SIZE_PROMPT,
       ORDER_DATE_PROMPT,
    }



    public OrderHistory_RecyclerAdapter(ArrayList<PizzaOrder> orders, Context context){
        this.context = context;
        this.orders = orders;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView orderIDPrompt, sizePrompt, orderDatePrompt,
                orderIDText,   sizeText,   orderDate;

        public MyViewHolder(View itemView) {
            super(itemView);

            orderIDPrompt   = (TextView)  itemView.findViewById(R.id.orderIDPrompt);
            orderIDText     = (TextView)  itemView.findViewById(R.id.orderIDText);
            sizePrompt      = (TextView)  itemView.findViewById(R.id.pizzaSizePrompt);
            sizeText        = (TextView)  itemView.findViewById(R.id.pizzaSizeText);
            orderDatePrompt = (TextView)  itemView.findViewById(R.id.orderDatePrompt);
            orderDate       = (TextView)  itemView.findViewById(R.id.orderDateText);
        }
    }


    @NonNull
    @Override
    public OrderHistory_RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        preferences = new SharedPreferenceHelper(context);

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistory_RecyclerAdapter.MyViewHolder holder, int position) {
        String [] array;
        ArrayList<String> textOptions;
        if (preferences.isFrench()){
            array =  context.getResources().getStringArray(R.array.orderDetailsFR);
            textOptions = new ArrayList<>(Arrays.asList(array));
        }
        else {
            array = context.getResources().getStringArray(R.array.orderDetailsEN);
            textOptions = new ArrayList<>(Arrays.asList(array));
        }


        //Assign values to the view we created in the recycler view row layout file
        holder.orderIDPrompt.setText(textOptions.get(index.ORDER_ID_PROMPT.ordinal()));
        holder.orderIDText.setText(String.valueOf(orders.get(position).getOrderID()));

        holder.sizePrompt.setText(textOptions.get(index.SIZE_PROMPT.ordinal()));
        holder.orderDatePrompt.setText(textOptions.get(index.ORDER_DATE_PROMPT.ordinal()));
        holder.orderDate.setText(orders.get(position).getOrderDate().toString());

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }







}

package com.example.a3_a_cruddypizza;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//https://www.youtube.com/watch?v=Mc0XT58A1Z4 Recycler view tutorial
public class OrderHistory_RecyclerAdapter extends RecyclerView.Adapter<OrderHistory_RecyclerAdapter.MyViewHolder> {





    @NonNull
    @Override
    public OrderHistory_RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistory_RecyclerAdapter.MyViewHolder holder, int position) {

        //Assign values to the view we created in teh recycler view row layout file

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView orderIDPrompt, sizePrompt, orderDatePrompt,
                orderIDText,   sizeText,   orderDate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            orderIDPrompt   = itemView.findViewById(R.id.orderIDLabel);
            orderIDText     = itemView.findViewById(R.id.orderIDText);
            sizePrompt      = itemView.findViewById(R.id.pizzaSizePrompt);
            sizeText        = itemView.findViewById(R.id.pizzaSizeText);
            orderDatePrompt = itemView.findViewById(R.id.orderDatePrompt);
            orderDate       = itemView.findViewById(R.id.orderDateText);
        }
    }
}

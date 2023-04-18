package com.example.a3_a_cruddypizza;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeToDelete extends ItemTouchHelper.Callback {
    private final DBAdapter dbAdapter;
    private final OrderHistory_RecyclerAdapter recyclerAdapter;

    public SwipeToDelete(DBAdapter dbAdapter, OrderHistory_RecyclerAdapter recyclerAdapter){
        this.dbAdapter = dbAdapter;
        this.recyclerAdapter = recyclerAdapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        // Do nothing
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //call the swipe method in the recycler adapter to delete the order
       recyclerAdapter.onItemSwipe(viewHolder.getAdapterPosition());
    }

}

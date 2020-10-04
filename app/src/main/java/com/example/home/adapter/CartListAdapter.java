package com.example.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.home.Models.Cart;
import com.example.home.R;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.MyViewHolder>
{
    private Context context;
    private ArrayList<Cart> list;

    //constructor
    public CartListAdapter(Context context, ArrayList<Cart> list)
    {
        //store context and the list to be displayed
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //set the viewHolder with the cart_item layout
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        //check whether the item list is not null and the size of the list is less than or equal to the item position
        if(list != null && list.size() > position)
        {
            //get the cart object from the list
            final Cart item = list.get(position);

            //set views in the item UI

            //set total
            holder.total.setText("" +item.getQty() * item.getPrice());
            //set Quantity
            holder.qty.setText("" + item.getQty());
            //set price
            holder.price.setText("" + item.getPrice());
            //set name
            holder.name.setText(item.getName());
        }
    }

    @Override
    public int getItemCount()
    {
        //return the size of the list
        if(list != null)
            return list.size();
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        //create view objects
        TextView name,price,qty,total;

        public MyViewHolder(@NonNull View itemView)
        {
            //init views
            super(itemView);

            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            qty = itemView.findViewById(R.id.qty);
            total = itemView.findViewById(R.id.total);


        }
    }
}

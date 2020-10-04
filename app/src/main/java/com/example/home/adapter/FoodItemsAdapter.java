package com.example.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.home.Models.Cart;
import com.example.home.Models.FoodItem;
import com.example.home.R;
import com.example.home.config.SharedPrefManager;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FoodItemsAdapter extends RecyclerView.Adapter<FoodItemsAdapter.MyViewHolder>
{
    private Context context;
    private ArrayList<FoodItem> list;
    private DatabaseReference reference;

    //constructor
    public FoodItemsAdapter(Context context, ArrayList<FoodItem> list)
    {
        //store context and the list to be displayed
        this.context = context;
        this.list = list;

        //init firebase database instances
        reference = FirebaseDatabase.getInstance().getReference()
                .child("Users")
                .child(String.valueOf(SharedPrefManager.getLoginUserId(context))).child("Cart");
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //set the viewHolder with the item_layout layout
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        //check whether the item list is not null and the size of the list is less than or equal to the item position
        if(list != null && list.size() > position)
        {
            //get the food item object from the list
            final FoodItem item = list.get(position);

            //load the food image with the item link
            Glide.with(context).load(item.getImage()).error(R.drawable.juice).placeholder(R.drawable.juice).into(holder.imageView);
            //set food name
            holder.name.setText(item.getName());
            //set food price
            holder.price.setText(item.getPrice() + " LKR");
            //set number of Quantity
            holder.qty.setText("" + item.getQty());

            //if plus button is pressed
                    holder.plus_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    //increase that item Quantity by 1
                    item.setQty(item.getQty() + 1);
                    notifyDataSetChanged();
                }
            });

            //if subtract item is pressed
            holder.substract_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    //check whether the item Quantity is greater than to 1
                    if(item.getQty() > 1)
                    {
                        //if greater than to 1
                        //reduce 1 from the item Quantity
                        item.setQty(item.getQty() - 1);
                        notifyDataSetChanged();
                    }
                }
            });

            //if add to cart button is pressed
            holder.add_to_cart_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {

                    //create new cart object
                    Cart cart = new Cart();
                    //set the name
                    cart.setName(item.getName());
                    //set price
                    cart.setPrice(item.getPrice());
                    //set item Quantity
                    cart.setQty(item.getQty());

                    //generate unique id for the cart
                    long child = System.currentTimeMillis();

                    //add the cart item to the cart
                   reference.child(String.valueOf(child)).setValue(cart, new DatabaseReference.CompletionListener() {
                       @Override
                       public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference)
                       {
                           //if an error occurred
                            if(databaseError != null)
                            {
                                //show toast
                                Toast.makeText(context, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            //is everything is okay
                            else
                            {
                                //show success toast
                                Toast.makeText(context, "Added to cart!", Toast.LENGTH_SHORT).show();
                            }
                       }
                   });

                }
            });

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
        ImageView imageView;
        TextView name,qty,price;
        Button add_to_cart_btn;
        ImageView substract_btn;
        ImageView plus_btn;

        public MyViewHolder(@NonNull View itemView)
        {
            //init views
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            qty = itemView.findViewById(R.id.qty);
            price = itemView.findViewById(R.id.price);
            add_to_cart_btn = itemView.findViewById(R.id.add_to_cart_btn);
            substract_btn = itemView.findViewById(R.id.substract_btn);
            plus_btn = itemView.findViewById(R.id.plus_btn);


        }
    }
}

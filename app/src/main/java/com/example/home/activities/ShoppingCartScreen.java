package com.example.home.activities;

<<<<<<< HEAD
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
=======
import android.app.AlertDialog;
>>>>>>> origin/master
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

<<<<<<< HEAD
import com.example.home.Models.Cart;
import com.example.home.Models.FoodItem;
import com.example.home.R;
import com.example.home.adapter.CartListAdapter;
import com.example.home.config.SharedPrefManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
=======
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.home.Models.Cart;
import com.example.home.R;
import com.example.home.adapter.CartListAdapter;
import com.example.home.config.SharedPrefManager;
>>>>>>> origin/master
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
<<<<<<< HEAD
import java.util.List;
=======
>>>>>>> origin/master

public class ShoppingCartScreen extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ProgressDialog dialog;
    private long id;
    private RecyclerView cart_list;
    private CartListAdapter adapter;
    private ArrayList<Cart> list = new ArrayList<>();

    private double total = 0;
    private TextView total_goods;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart_screen);


        //get the currently logged user id from sharedPreferences
        id = SharedPrefManager.getLoginUserId(ShoppingCartScreen.this);

        //init progress dialog
        dialog = new ProgressDialog(ShoppingCartScreen.this);
        dialog.setMessage("Syncing..");
        dialog.setCancelable(false);

        //set firebase database instances
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Users").child(String.valueOf(id)).child("Cart");

        //init UI elements to code
        total_goods = findViewById(R.id.total_goods);
        cart_list = findViewById(R.id.cart_list);
        adapter = new CartListAdapter(ShoppingCartScreen.this,list);
        cart_list.setAdapter(adapter);
        cart_list.setLayoutManager(new LinearLayoutManager(ShoppingCartScreen.this));

        //get cart list from database
        getCartList();

    }

    private void getCartList()
    {

        //clear the existing cart list first
        //otherwise duplicate items may appear
        list.clear();
        dialog.show();

        //calling the method to fetch all items in the cart
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                //if there are any items in the cart
                if(dataSnapshot.exists())
                {
                    //running a for each loop
                    for(DataSnapshot dss : dataSnapshot.getChildren())
                    {
                        //get a cart
                        Cart cart = dss.getValue(Cart.class);
                        //calculate the total price to be paid
                        //get each item price and qty
                        //then multiply them to get the total of one item
                        //then add that value to the existing total value
                        total = total + (cart.getPrice() * cart.getQty());
                        //set the total text in the UI
                        total_goods.setText("Total - " + total + " LKR");
                        //add item to the cart list
                        list.add(cart);
                        //notify adapter, that a new item has been added
                        adapter.notifyDataSetChanged();
                    }


                }
                //if there are no any items in the cart
                else
                {
                    //no items found toast will appear
                    Toast.makeText(ShoppingCartScreen.this, "No items were found! Please add some", Toast.LENGTH_SHORT).show();
                    //remove previous items from the list
                    adapter.notifyDataSetChanged();
                    //set the total text to empty
                    total_goods.setText("");
                    total = 0;
                }

                dialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                dialog.dismiss();
            }
        });
    }

    public void EmptyCart(View view)
    {
        //this method will be triggered when the empty cart button is pressed
        //if the total is 0
        //no items in the cart
        if(total <= 0)
        {
            //so no need of go to checkout
            //just show the error toast
            Toast.makeText(this, "No items in cart to clear", Toast.LENGTH_SHORT).show();
            return;
        }

        //if the total is greater than to 0
        //which means there are some items in the cart

        //show dialog to get the confirmation
        AlertDialog.Builder builder1 = new AlertDialog.Builder(ShoppingCartScreen.this);
        builder1.setTitle("Are you sure you want to proceed?");
        builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                //if the user press "Yes"
                dialog.show();

                //the item will be deleted from the database
                reference.removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference)
                    {
                        //if an error occurred while deleting
                        if(databaseError != null)
                        {
                            Toast.makeText(ShoppingCartScreen.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        //if everything is okay
                        else
                        {
                            Toast.makeText(ShoppingCartScreen.this, "Cart was cleared!", Toast.LENGTH_SHORT).show();
                            total = 0;
                            //load the cart list back again
                            getCartList();
                        }
                        dialog.dismiss();
                    }
                });
            }
        });
        builder1.setNegativeButton("No", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {

            }
        });
        builder1.show();



    }

    public void UpdateCart(View view)
    {

    }

    public void Checkout(View view)
    {
        //this method will be called when the UpdateCart button is pressed
        //again check the total price to be paid first
        //if it is less than or equal to 0
        //no need to go to check out screen
        if(total <= 0)
        {
            //show error toast
            Toast.makeText(this, "You have no items to checkout. Please add some items to cart and try again..", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //navigate to the Checkout Screen
            Intent intent = new Intent(ShoppingCartScreen.this,CheckoutScreen.class);
            intent.putExtra("Total",total);
            startActivity(intent);
        }

    }
}
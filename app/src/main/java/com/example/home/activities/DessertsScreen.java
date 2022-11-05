package com.example.home.activities;

<<<<<<< HEAD
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

=======
>>>>>>> origin/master
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

<<<<<<< HEAD
=======
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

>>>>>>> origin/master
import com.example.home.Models.FoodItem;
import com.example.home.R;
import com.example.home.adapter.FoodItemsAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DessertsScreen extends AppCompatActivity {

    private RecyclerView recycler_view;
    private FoodItemsAdapter adapter;
    private ProgressDialog dialog;
    private DatabaseReference reference;
    private ArrayList<FoodItem> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desserts_screen);


        //init views
        recycler_view = findViewById(R.id.recycler_view);
        dialog = new ProgressDialog(DessertsScreen.this);
        dialog.setMessage("Syncing..");
        dialog.setCancelable(false);


        //set the firebase database path
        reference = FirebaseDatabase.getInstance().getReference("Foods").child("Desserts");


        //set the recyclerview adapter and layout manager
        adapter = new FoodItemsAdapter(DessertsScreen.this,list);
        recycler_view.setLayoutManager(new LinearLayoutManager(DessertsScreen.this));
        recycler_view.setAdapter(adapter);

        //get all the desserts from the database
        getAllItems();


    }
    public void ShowCart(View view)
    {
        //will be called as soon as the show cart button is pressed
        //this will be navigated to the cart screen
        Intent intent = new Intent(DessertsScreen.this,ShoppingCartScreen.class);
        startActivity(intent);
    }
    private void getAllItems()
    {
        //show the progress dialog
        dialog.show();

        reference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                //check whether the database path is exists
                if(dataSnapshot.exists())
                {
                    //if the paths exists
                    //the desserts will be fetched
                    for(DataSnapshot dss : dataSnapshot.getChildren())
                    {
                        FoodItem item = dss.getValue(FoodItem.class);
                        //adding the desserts to the list
                        list.add(item);
                        //notify the adapter, a new item has been added
                        adapter.notifyDataSetChanged();
                    }
                    dialog.dismiss();
                }
                else
                {
                    //if the path not exists
                    //the list will be empty
                    //and the toast will be displayed
                    dialog.dismiss();
                    Toast.makeText(DessertsScreen.this, "Empty List", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                if(databaseError != null)
                {
                    //if there was an error in the database, this toast will be shown
                    Toast.makeText(DessertsScreen.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

    }
}
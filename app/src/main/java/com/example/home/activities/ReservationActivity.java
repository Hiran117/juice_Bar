package com.example.home.activities;

<<<<<<< HEAD
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

=======
>>>>>>> origin/master
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

<<<<<<< HEAD
import com.example.home.Models.Delivery;
=======
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

>>>>>>> origin/master
import com.example.home.Models.Reservation;
import com.example.home.R;
import com.example.home.adapter.ReservationAdapter;
import com.example.home.config.SharedPrefManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReservationActivity extends AppCompatActivity {

    private RecyclerView rv_payments;
    private DatabaseReference reference;
    private ProgressDialog dialog;
    private ArrayList<Reservation> list;
    private ReservationAdapter adapter;

    private static final int REQUEST_CODE = 999;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
        {
            //the list has been updated
            //reload reservations from the database
            getAllReservations();
        }
        if(requestCode == REQUEST_CODE && resultCode == 1500)
        {
            //something went wrong
            Toast.makeText(this, "We could not update that item!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        //init views
        rv_payments = findViewById(R.id.rv_payments);

        //get current user id
        long id = SharedPrefManager.getLoginUserId(ReservationActivity.this);

        //set database reservation path of the user
        reference = FirebaseDatabase.getInstance().getReference("Users").child(String.valueOf(id));

        //init the progress dialog
        dialog = new ProgressDialog(ReservationActivity.this);
        dialog.setMessage("Syncing..");
        dialog.setCancelable(false);

        //create new arrayList
        list = new ArrayList<>();
        //create the list adapter
        adapter = new ReservationAdapter(ReservationActivity.this,list);
        //set the adapter
        rv_payments.setAdapter(adapter);
        //set the layout manager
        rv_payments.setLayoutManager(new LinearLayoutManager(ReservationActivity.this));

        //load all the reservations from the database
        getAllReservations();
    }

    private void getAllReservations()
    {
        //clear the list first
        list.clear();
        //show the progress dialog
        dialog.show();
        //get reservation list from the database
        reference.child("Reservations").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                //check whether reservations exists or not already
                if(dataSnapshot.exists())
                {
                    //fetch the reservations
                    for(DataSnapshot dss : dataSnapshot.getChildren())
                    {
                        Reservation delivery = dss.getValue(Reservation.class);
                        //add it to the list
                        list.add(delivery);
                        //notify the adapter, the list has been updated
                        adapter.notifyDataSetChanged();
                    }

                }
                else
                {
                    //no reservations were found previously
                    Toast.makeText(ReservationActivity.this, "No previous reservations were found!", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                }
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void deleteItem(final long reservationId)
    {
        //delete reservation from the database

        //show the confirmation dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(ReservationActivity.this);
        builder.setMessage("Are you sure you want to delete this item?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                //if the user press "Yes"
                dialog.show();

                //the reservation will be deleted
                reference.child("Reservations").child(String.valueOf(reservationId)).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference)
                    {
                        if(databaseError != null)
                        {
                            //if an error occurred, the error will be shown
                            Toast.makeText(ReservationActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            //successfully deleted the reservation
                            Toast.makeText(ReservationActivity.this, "Deleted successfully!", Toast.LENGTH_SHORT).show();
                            getAllReservations();
                        }
                        dialog.dismiss();
                    }
                });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                //if user press "No" button
                //no action will be taken
            }
        });
        //show the dialog
        builder.show();

    }
    public void editItem(long reservationId)
    {
        //edit the reservation item
        Intent intent = new Intent(ReservationActivity.this,UpdateReservation.class);
        //pass the reservationId to the intent object
        intent.putExtra("reservationId",reservationId);
        //start activity
        startActivityForResult(intent,REQUEST_CODE);
    }
}
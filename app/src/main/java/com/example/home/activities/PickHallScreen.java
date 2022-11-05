package com.example.home.activities;

<<<<<<< HEAD
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

=======
>>>>>>> origin/master
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

<<<<<<< HEAD
=======
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

>>>>>>> origin/master
import com.example.home.Models.Reservation;
import com.example.home.R;
import com.example.home.config.SharedPrefManager;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PickHallScreen extends AppCompatActivity {

    private RadioButton hallA,hallB;
    private EditText phone;
    private  String date,time,size;

    private DatabaseReference reference;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_hall_screen);


        //fetched date,time,size from the intent object
        Intent intent = getIntent();
        //set the fetched values in the respective variables
        date = intent.getStringExtra("Date");
        time = intent.getStringExtra("Time");
        size = intent.getStringExtra("Size");

        //init views
        hallA = findViewById(R.id.hallA);
        hallB = findViewById(R.id.hallB);
        phone = findViewById(R.id.phone);


        //setting up a check changed listener for the hallA radio button
        hallA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                //if checked
                if(b)
                {
                    //hallB will be unchecked
                    hallB.setChecked(false);
                }
            }
        });

        //setting up a check changed listener for the hallB radio button
        hallB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                //if checked
                if(b)
                {
                    //hallA will be unchecked
                    hallA.setChecked(false);
                }
            }
        });


        //init the progress dialog
        dialog = new ProgressDialog(PickHallScreen.this);
        dialog.setMessage("Reserving..");
        dialog.setCancelable(false);

        //get the logged users id from the shared preferences
        long id = SharedPrefManager.getLoginUserId(PickHallScreen.this);
        //set the currently logged users path in the database
        reference = FirebaseDatabase.getInstance().getReference("Users").child(String.valueOf(id)).child("Reservations");


    }

    public void OnConfirmClicked(View view)
    {
        //if the confirm button clicks
        //validate user inputs
        if(!hallA.isChecked() && !hallB.isChecked())
        {
            Toast.makeText(this, "Please select a Hall", Toast.LENGTH_SHORT).show();
            return;
        }
        //get the phone number from user inputs
        String phoneT = phone.getText().toString().trim();

        //check the phone number will empty or not
        if(phoneT.equals(""))
        {
            Toast.makeText(this, "Please enter a valid contact number", Toast.LENGTH_SHORT).show();
            return;
        }

        dialog.show();
        //generate a new id for the reservation
        long id = System.currentTimeMillis();
        //create new reservation object
        final Reservation reservation = new Reservation();
        //set date
        reservation.setDate(date);
        //set phone number
        reservation.setPhone(phoneT);
        //set party size
        reservation.setSize(size);
        //set time
        reservation.setTime(time);
        //set the reservation id
        reservation.setId(id);

        //if hallA is checked by the user
        if(hallA.isChecked())
        {
            //set the hall type as Hall A
            reservation.setHall("Hall A");
        }
        else
        {
            //set the hall type as Hall B
            reservation.setHall("Hall B");
        }


        //add the new reservation to the database
        reference.child(String.valueOf(id)).setValue(reservation, new DatabaseReference.CompletionListener()
        {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference)
            {
                if(databaseError != null)
                {
                    //if an error occurred, the error will be shown
                    Toast.makeText(PickHallScreen.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //the reservation has successfully stored in the database
                    Toast.makeText(PickHallScreen.this, "Reservation Successful!", Toast.LENGTH_SHORT).show();

                    //go tp the ReservationConfirmScreen
                    Intent intent = new Intent(PickHallScreen.this,ReservationConfirmScreen.class);
                    //set the date,time,size,hall,phone to the intent object
                    intent.putExtra("Date",reservation.getDate());
                    intent.putExtra("Time",reservation.getTime());
                    intent.putExtra("Size",reservation.getSize());
                    intent.putExtra("Hall",reservation.getHall());
                    intent.putExtra("Phone",reservation.getPhone());
                    //start the new activity
                    startActivity(intent);
                }

                dialog.dismiss();
            }
        });


    }
}
package com.example.home.activities;

<<<<<<< HEAD
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

=======
>>>>>>> origin/master
import android.app.Activity;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateReservation extends AppCompatActivity {

    private DatabaseReference reference;
    private ProgressDialog dialog;

    private EditText date;
    private EditText time;
    private RadioButton Small;
    private RadioButton Medium;
    private RadioButton Large;

    private RadioButton hallA;
    private RadioButton hallB;
    private EditText phone;

    private  long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_reservation);


        //init progress dialog
        dialog = new ProgressDialog(UpdateReservation.this);
        dialog.setMessage("Syncing..");
        dialog.setCancelable(false);

        //init views
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        Small = findViewById(R.id.Small);
        Medium = findViewById(R.id.Medium);
        Large = findViewById(R.id.Large);

        hallA = findViewById(R.id.hallA);
        hallB = findViewById(R.id.hallB);
        phone = findViewById(R.id.phone);


        //set checkChangedListener for hallA radio button
        hallA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if(b)
                {
                    //if hallA is checked, unchecked hallB
                    hallB.setChecked(false);
                }
            }
        });
        //set checkChangedListener for hallB radio button
        hallB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if(b)
                {
                    //if hallB is checked, unchecked hallA
                    hallA.setChecked(false);
                }
            }
        });

        //get the intent object
        Intent intent = getIntent();
        //access the reservationId from the intent object
        id = intent.getLongExtra("reservationId",-99);
        //if the id = -99, no reservationId has received
        if(id == -99)
        {
            //so just finish the activity
            setResult(1500);
            finish();
        }
        //else there is a valid reservationId
        else
        {
            //get the user id
            long userId = SharedPrefManager.getLoginUserId(UpdateReservation.this);
            //set the Firebase Database instances with user id
            reference = FirebaseDatabase.getInstance().getReference("Users").child(String.valueOf(userId)).child("Reservations");
            //load reservations by the fetched id
            loadReservation(id);
        }
    }

    private void loadReservation(long id)
    {
        //show dialog
        dialog.show();
        //call the method to fetch the reservation
        reference.child(String.valueOf(id)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                //if the reservation exists
                if(dataSnapshot.exists())
                {
                    //get the reservation object
                    Reservation reservation = dataSnapshot.getValue(Reservation.class);
                    //make sure it is not null
                    if(reservation != null)
                    {
                        //the get the values from the reservation object
                        //and set them in the UI

                        //set date
                        if(reservation.getDate() != null)
                        {
                            date.setText(reservation.getDate());
                        }
                        //set time
                        if(reservation.getTime() != null)
                        {
                            time.setText(reservation.getTime());
                        }
                        //set phone number
                        if(reservation.getPhone() != null)
                        {
                            phone.setText(reservation.getPhone());
                        }
                        //set hall
                        if(reservation.getHall() != null)
                        {
                            //if Hall A
                            if(reservation.getHall().equals("Hall A"))
                            {
                                //set hallA checked
                                hallA.setChecked(true);
                                //set hallB unchecked
                                hallB.setChecked(false);
                            }
                            //if Hall B
                            if(reservation.getHall().equals("Hall B"))
                            {
                                //set hallA unchecked
                                hallA.setChecked(false);
                                //set hallB checked
                                hallB.setChecked(true);
                            }
                        }
                        //get the reservation size
                        if(reservation.getSize() != null)
                        {
                            //if small
                            if(reservation.getSize().equals("Small(1-10 People)"))
                            {
                                //set small checked
                                Small.setChecked(true);
                                //uncheck medium
                                Medium.setChecked(false);
                                //uncheck large
                                Large.setChecked(false);
                            }
                            //if medium
                            if(reservation.getSize().equals("Medium(10-20 People)"))
                            {
                                //set small unchecked
                                Small.setChecked(false);
                                //set medium checked
                                Medium.setChecked(true);
                                //set large unchecked
                                Large.setChecked(false);
                            }
                            //if large
                            if(reservation.getSize().equals("Large(more than 20 People)"))
                            {
                                //set small unchecked
                                Small.setChecked(false);
                                //set medium unchecked
                                Medium.setChecked(false);
                                //set large checked
                                Large.setChecked(true);
                            }
                        }
                    }


                }
                else
                {
                    //not found the reservation with that id
                    Toast.makeText(UpdateReservation.this, "No reservation Found!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                //if something went wrong
                Toast.makeText(UpdateReservation.this, "" +databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    public void UpdateItem(View view)
    {
        //when update button is pressed

        //get the date and time
        String dataT = date.getText().toString().trim();
        String timeT = time.getText().toString().trim();

        //validate date and time
        if(dataT.equals("") || timeT.equals(""))
        {
            Toast.makeText(this, "Please fill required fields first!", Toast.LENGTH_SHORT).show();
            return;
        }
        //validate size of the reservation
        if(!Small.isChecked() && !Medium.isChecked() && !Large.isChecked())
        {
            Toast.makeText(this, "Please select a Party Size", Toast.LENGTH_SHORT).show();
            return;
        }
        //validate hall type
        if(!hallA.isChecked() && !hallB.isChecked())
        {
            Toast.makeText(this, "Please select a Hall", Toast.LENGTH_SHORT).show();
            return;
        }
        //get phone number
        String phoneT = phone.getText().toString().trim();

        //validate phone number
        if(phoneT.equals(""))
        {
            Toast.makeText(this, "Please enter a valid contact number", Toast.LENGTH_SHORT).show();
            return;
        }

        //if everything is okay, show the progress dialog
        dialog.show();

        //create new reservation obejct
        final Reservation reservation = new Reservation();
        //set date
        reservation.setDate(dataT);
        //set phone number
        reservation.setPhone(phoneT);

        //set reservation size
        //if small
        if(Small.isChecked())
        {
            reservation.setSize("Small(1-10 People)");

        }
        //if medium
        else if(Medium.isChecked())
        {
            reservation.setSize("Medium(10-20 People)");
        }
        //if large
        else
        {
            reservation.setSize("Large(more than 20 People)");
        }

        //set hall
        //if hall A
        if(hallA.isChecked())
        {
            reservation.setHall("Hall A");
        }
        //if hall B
        else
        {
            reservation.setHall("Hall B");
        }

        //st time
        reservation.setTime(timeT);
        //set reservation id
        reservation.setId(id);

        //call the method to update
        reference.child(String.valueOf(id)).setValue(reservation, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference)
            {
                    //if an error occurred while updating
                    if(databaseError != null)
                    {
                        //show error toast
                        Toast.makeText(UpdateReservation.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    //if everything is okay
                    else
                    {
                       //show success toast
                        Toast.makeText(UpdateReservation.this, "Item was successfully Updated!", Toast.LENGTH_SHORT).show();
                        //go back to reservations screen
                        setResult(Activity.RESULT_OK);
                        //finish the UpdateReservation Activity
                        finish();

                    }
                    //hide progress dialog
                    dialog.dismiss();
            }
        });


    }
}
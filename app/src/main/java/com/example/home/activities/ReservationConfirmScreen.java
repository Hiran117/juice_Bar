package com.example.home.activities;

<<<<<<< HEAD
import androidx.appcompat.app.AppCompatActivity;

=======
>>>>>>> origin/master
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

<<<<<<< HEAD
=======
import androidx.appcompat.app.AppCompatActivity;

>>>>>>> origin/master
import com.example.home.R;

public class ReservationConfirmScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_confirm_screen);


        //get the intent object
        Intent intent = getIntent();

        //access the values from the intent object
        //and store them in variables
        String dateT = intent.getStringExtra("Date");
        String timeT = intent.getStringExtra("Time");
        String sizeT = intent.getStringExtra("Size");
        String hallT = intent.getStringExtra("Hall");
        String phoneT = intent.getStringExtra("Phone");

        //init views
        TextView date = findViewById(R.id.date);
        TextView time = findViewById(R.id.time);
        TextView size = findViewById(R.id.size);
        TextView hall = findViewById(R.id.hall);
        TextView phone = findViewById(R.id.phone);

        //set the fetched values into the UI
        date.setText("Date - " + dateT);
        time.setText("Time - " + timeT);
        size.setText("Party Size - " + sizeT);
        hall.setText("Hall - " + hallT);
        phone.setText("Contact Number - " + phoneT);
    }
    public void OnHomeButtonClicked(View view)
    {
        //this method will be triggered when the home button is pressed
        Intent intent = new Intent(ReservationConfirmScreen.this,HomeScreen.class);
        startActivity(intent);
        finish();
    }
}
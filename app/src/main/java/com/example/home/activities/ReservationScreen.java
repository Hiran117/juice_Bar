package com.example.home.activities;

<<<<<<< HEAD
import androidx.appcompat.app.AppCompatActivity;

=======
>>>>>>> origin/master
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

<<<<<<< HEAD
=======
import androidx.appcompat.app.AppCompatActivity;

>>>>>>> origin/master
import com.example.home.R;

public class ReservationScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_screen);
    }

    public void GoToChooseAHallScreen(View view)
    {
        //this method will be triggered when the Choose A Hll button is pressed

        //init views
        EditText date = findViewById(R.id.date);
        EditText time = findViewById(R.id.time);
        RadioButton Small = findViewById(R.id.Small);
        RadioButton Medium = findViewById(R.id.Medium);
        RadioButton Large = findViewById(R.id.Large);

        //get the date and time values
        String dataT = date.getText().toString().trim();
        String timeT = time.getText().toString().trim();

        //check whether the date and time in not empty
        if(dataT.equals("") || timeT.equals(""))
        {
            Toast.makeText(this, "Please fill required fields first!", Toast.LENGTH_SHORT).show();
            return;
        }
        //validate party size
        if(!Small.isChecked() && !Medium.isChecked() && !Large.isChecked())
        {
            Toast.makeText(this, "Please select a Party Size", Toast.LENGTH_SHORT).show();
            return;
        }

        //create a new intent object
<<<<<<< HEAD
        Intent intent = new Intent(ReservationScreen.this,PickHallScreen.class);
=======
        Intent intent = new Intent(ReservationScreen.this, PickHallScreen.class);
>>>>>>> origin/master
        //set the date and time in the intent object
        intent.putExtra("Date",dataT);
        intent.putExtra("Time",timeT);

        //set the party size according to the radio button that checked
        if(Small.isChecked())
        {
            //if the party size is small
            intent.putExtra("Size","Small(1-10 People)");
        }
        else if(Medium.isChecked())
        {
            //if the party size is medium
            intent.putExtra("Size","Medium(10-20 People)");
        }
        else
        {
            //if the party size is large
            intent.putExtra("Size","Large(more than 20 People)");
        }

        startActivity(intent);
    }
}
package com.example.home.activities;

<<<<<<< HEAD
import androidx.appcompat.app.AppCompatActivity;

=======
>>>>>>> origin/master
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

<<<<<<< HEAD
import com.example.home.Models.Delivery;
=======
import androidx.appcompat.app.AppCompatActivity;

>>>>>>> origin/master
import com.example.home.R;

import java.util.Date;

public class DeliveryInformationScreen extends AppCompatActivity {

    private Double total,discount,discountPercentage,subTotal,deliveryCharges,grandTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_information_screen);


        //catching the values sent by the previous activity through the intent object
        Intent intent = getIntent();
        //store them in the variables
        total = intent.getDoubleExtra("GoodPrice",0);
        discount = intent.getDoubleExtra("Discount",0);
        discountPercentage =  intent.getDoubleExtra("DiscountPercentage",0);
        subTotal = intent.getDoubleExtra("SubTotal",0);
        deliveryCharges = intent.getDoubleExtra("DeliveryCharges",0);
        grandTotal = intent.getDoubleExtra("GrandTotal",0);



    }

    public void OnSaveAndContinueClicked(View view)
    {
        //this method will be triggered when the user press save and continue button

        //init views
        EditText full_name_tv = findViewById(R.id.full_name_tv);
        EditText address_tv = findViewById(R.id.address_tv);
        EditText phone_tv = findViewById(R.id.phone_tv);
        EditText email_tv = findViewById(R.id.email_tv);
        EditText date_tv = findViewById(R.id.date_tv);
        EditText time_tv = findViewById(R.id.time_tv);
        RadioButton now = findViewById(R.id.now);
        RadioButton later = findViewById(R.id.later);


        //get the user inputs
        String fullName = full_name_tv.getText().toString().trim();
        String address = address_tv.getText().toString().trim();
        String phone = phone_tv.getText().toString().trim();
        String email = email_tv.getText().toString().trim();
        String date = date_tv.getText().toString().trim();
        String time = time_tv.getText().toString().trim();

        boolean isNow = now.isChecked();
        boolean isLater = later.isChecked();

        //validate inputs
        if(fullName.equals("") || address.equals("") || phone.equals("") || email.equals(""))
        {
            Toast.makeText(this, "Please fill required fields first!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!isNow && !isLater)
        {
            Toast.makeText(this, "Please set a delivery time method", Toast.LENGTH_SHORT).show();
            return;
        }

        //validate email address
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return;
        }


        //send the fetched values to the next activity through the intent object
        Intent intent = new Intent(DeliveryInformationScreen.this,PaymentMethodScreen.class);
        intent.putExtra("GoodPrice",total);
        intent.putExtra("Discount",discount);
        intent.putExtra("DiscountPercentage",discountPercentage);
        intent.putExtra("SubTotal",subTotal);
        intent.putExtra("DeliveryCharges",deliveryCharges);
        intent.putExtra("GrandTotal",grandTotal);
        intent.putExtra("FullName",fullName);
        intent.putExtra("Address",address);
        intent.putExtra("Phone",phone);

        //if is now is checked
        if(isNow)
        {
            //get the system time
            Date d = java.util.Calendar.getInstance().getTime();
            intent.putExtra("Time",d.toString());
            //start the next activity
            startActivity(intent);

        }
        else if(isLater)
        {
            //validate date and time input fields
            if(date.equals("") || time.equals(""))
            {
                Toast.makeText(this, "Please set a delivery date and time", Toast.LENGTH_SHORT).show();
                return;
            }
            else
            {
                //set the date and time to the input object
                intent.putExtra("Time",date + " " + time);
                //start the next activity
                startActivity(intent);
            }
        }
    }
}
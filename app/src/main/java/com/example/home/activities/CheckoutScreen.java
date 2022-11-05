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

public class CheckoutScreen extends AppCompatActivity
{
    private  double total,discountPercentage,discount,subTotal,deliveryCharges,grandTotal;
    private TextView good_price;
    private TextView discount_tv;
    private TextView sub_total_tv;
    private TextView delivery_charge_tv;
    private TextView grand_total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_screen);


        //get the total from the intent object
        Intent intent = getIntent();
        //store the total in the total variable
        total = intent.getDoubleExtra("Total",0);
        //set the delivery charge as 100 for all
        deliveryCharges = 100;

        //this selection is to calculate the discount based on the total
        if(total <= 1000)
        {
            //if the total is less than or equal to 1000, the discount percentage will be 5%
            discountPercentage = 5;
        }
        else if(total <= 2000)
        {
            //if the total is less than or equal to 2000, the discount percentage will be 8%
            discountPercentage = 8;
        }
        else if(total <= 5000)
        {
            //if the total is less than or equal to 5000, the discount percentage will be 10%
            discountPercentage = 10;
        }
        else
        {
            //if the total is greater than to 5000, the discount percentage will be 20%
            discountPercentage = 20;
        }

        //find the discount based on the discount percentage calculated earlier
        discount = (total / 100) * discountPercentage;
        //find the actual amount to be paid after reducing the discount
        subTotal = total - discount;
        //find the actual amount to be paid after adding the delivery charges.
        grandTotal = subTotal + deliveryCharges;


        //init views
        good_price = findViewById(R.id.good_price);
        discount_tv = findViewById(R.id.discount_tv);
        sub_total_tv = findViewById(R.id.sub_total_tv);
        delivery_charge_tv = findViewById(R.id.delivery_charge_tv);
        grand_total = findViewById(R.id.grand_total);


        //set the calculated values in the UI
        good_price.setText("Value of Purchased Goods - Rs " + total);
        discount_tv.setText("Discount - Rs " + discount);
        sub_total_tv.setText("Sub Total - Rs " + subTotal);
        delivery_charge_tv.setText("Delivery Charge - Rs " + deliveryCharges);
        grand_total.setText("Grand Total - Rs " + grandTotal);

    }
    public void OnConfirmClicked(View view)
    {
        //this method will be triggered when the user press the confirm button

<<<<<<< HEAD
        Intent intent = new Intent(CheckoutScreen.this,DeliveryInformationScreen.class);
=======
        Intent intent = new Intent(CheckoutScreen.this, DeliveryInformationScreen.class);
>>>>>>> origin/master
        //adding all the calculated values to the intent object to get access in the DeliveryInformationScreen
        intent.putExtra("GoodPrice",total);
        intent.putExtra("Discount",discount);
        intent.putExtra("DiscountPercentage",discountPercentage);
        intent.putExtra("SubTotal",subTotal);
        intent.putExtra("DeliveryCharges",deliveryCharges);
        intent.putExtra("GrandTotal",grandTotal);
        startActivity(intent);
    }
    public void OnCancelClicked(View view)
    {
        //this method will be triggered when the user press the cancel button
        finish();
    }
}
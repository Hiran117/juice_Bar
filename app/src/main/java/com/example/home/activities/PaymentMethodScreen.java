package com.example.home.activities;

<<<<<<< HEAD
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

=======
>>>>>>> origin/master
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

=======
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

>>>>>>> origin/master
import com.example.home.Models.Card;
import com.example.home.Models.Delivery;
import com.example.home.R;
import com.example.home.config.SharedPrefManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PaymentMethodScreen extends AppCompatActivity {

    private double goodPrice,discount,discountPercentage,subTotal,deliveryCharges,grandTotal;
    private String fullName,address,phone,time;

    private DatabaseReference reference;
    private ProgressDialog dialog;

    private EditText card_name,card_number,expiry_date,security_code;
    private RadioButton credit,debit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method_screen);


        //init progress dialog
        dialog = new ProgressDialog(PaymentMethodScreen.this);
        dialog.setMessage("Syncing..");
        dialog.setCancelable(false);

        //init views
        card_name = findViewById(R.id.card_name);
        card_number = findViewById(R.id.card_number);
        expiry_date = findViewById(R.id.expiry_date);
        security_code = findViewById(R.id.security_code);
        credit = findViewById(R.id.credit);
        debit = findViewById(R.id.debit);


        //get the intent object
        Intent intent = getIntent();

        //get the values passed through the intent object
        //and store them in the respective variables
        goodPrice = intent.getDoubleExtra("GoodPrice",0);
        discount = intent.getDoubleExtra("Discount",0);
        discountPercentage = intent.getDoubleExtra("DiscountPercentage",0);
        subTotal = intent.getDoubleExtra("SubTotal",0);
        deliveryCharges = intent.getDoubleExtra("DeliveryCharges",0);
        grandTotal = intent.getDoubleExtra("GrandTotal",0);
        fullName = intent.getStringExtra("FullName");
        address = intent.getStringExtra("Address");
        phone = intent.getStringExtra("Phone");
        time = intent.getStringExtra("Time");

        //get the currently logged user id
        long id = SharedPrefManager.getLoginUserId(PaymentMethodScreen.this);
        //set the currently logged users database path in the firebase
        reference = FirebaseDatabase.getInstance().getReference("Users").child(String.valueOf(id));

        //load the users card details if exists in the database
        loadCardDetailsFromDatabase();
    }

    private void loadCardDetailsFromDatabase()
    {
        //show progress dialog
        dialog.show();
        reference.child("CardDetails").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                //check whether the user has a card previously added
                if(dataSnapshot.exists())
                {
                    //if exists
                    //the card details will be fetched
                    //and shown in the UI
                    Card card = dataSnapshot.getValue(Card.class);

                    //check whether the card name is not null
                    if(card != null && card.getCardName() != null)
                    {
                        //set the card name in the UI
                        card_name.setText(card.getCardName());
                    }
                    //check whether the card number is not null
                    if(card != null && card.getCardNo() != null)
                    {
                        //set the card number in the UI
                        card_number.setText(card.getCardNo());
                    }
                    //check whether the expiry date is not null
                    if(card != null && card.getExpiryDate() != null)
                    {
                        //set the expiry date in the UI
                        expiry_date.setText(card.getExpiryDate());
                    }
                    //check whether the security code is not null
                    if(card != null && card.getSecurityCode() != null)
                    {
                        //set the security code in the UI
                        security_code.setText(card.getSecurityCode());
                    }

                    //check whether the card is not null
                    if(card != null)
                    {
                        //if the card type is credit
                        if(card.isCredit())
                        {
                            //credit radio button will be active
                            //debit radio button will be inactive
                            credit.setChecked(true);
                            debit.setChecked(false);
                        }
                        //if the card type is debit
                        else if(card.isDebit())
                        {
                            //debit radio button will be active
                            //credit radio button will be inactive
                            credit.setChecked(false);
                            debit.setChecked(true);
                        }
                    }

                }
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                if(databaseError != null)
                {
                    //if an error occurred, the error toast will be shown
                    Toast.makeText(PaymentMethodScreen.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
    }
    public void OnUpdateClicked(View view)
    {

        //this method will be triggered when the user clicks the Update button

        //get the user inputs
        String cardName = card_name.getText().toString().trim();
        String cardNo = card_number.getText().toString().trim();
        String exDate = expiry_date.getText().toString();
        String code = security_code.getText().toString().trim();
        boolean isCredit = credit.isChecked();
        boolean isDebit = debit.isChecked();


        //validate user inputs
        if(cardName.equals("") || cardNo.equals("") || exDate.equals("") || code.equals(""))
        {
            Toast.makeText(this, "Please fill out required fields first!", Toast.LENGTH_SHORT).show();
            return;
        }
        //validate card type
        if(!isCredit && !isDebit)
        {
            Toast.makeText(this, "Please select a card type!", Toast.LENGTH_SHORT).show();
            return;
        }

        //show progress dialog
        dialog.show();

        //create a new card
        Card card = new Card();
        //set the card name
        card.setCardName(cardName);
        //set the card no
        card.setCardNo(cardNo);
        //set the expiry date
        card.setExpiryDate(exDate);
        //set security code
        card.setSecurityCode(code);
        //set the card type
        if(isCredit)
        {
            //if credit
            card.setCredit(true);
            card.setDebit(false);
        }
        else
        {
            //if debit
            card.setCredit(false);
            card.setDebit(true);
        }

        //add the card to the database
        reference.child("CardDetails").setValue(card, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference)
            {
                if(databaseError != null)
                {
                    //if an error occurred
                    Toast.makeText(PaymentMethodScreen.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //successfully added the card to the database
                    Toast.makeText(PaymentMethodScreen.this, "Updated successfully!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
    }
    public void OnPayClicked(View view)
    {

        //will be called when the pay button is pressed

        //get the user inputs
        String cardName = card_name.getText().toString().trim();
        String cardNo = card_number.getText().toString().trim();
        String exDate = expiry_date.getText().toString();
        String code = security_code.getText().toString().trim();
        boolean isCredit = credit.isChecked();
        boolean isDebit = debit.isChecked();


        //validate user inputs
        if(cardName.equals("") || cardNo.equals("") || exDate.equals("") || code.equals(""))
        {
            Toast.makeText(this, "Please fill out required fields first!", Toast.LENGTH_SHORT).show();
            return;
        }
        //validate card type
        if(!isCredit && !isDebit)
        {
            Toast.makeText(this, "Please select a card type!", Toast.LENGTH_SHORT).show();
            return;
        }

        //confirmation dialog will be displayed
        AlertDialog.Builder builder1 = new AlertDialog.Builder(PaymentMethodScreen.this);
        builder1.setTitle("Are you sure you want to proceed?");
        builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                //if the user press "Yes"
                dialog.show();

                //new delivery object will be created
                Delivery delivery = new Delivery();
                //set the address
                delivery.setAddress(address);
                //set the phone
                delivery.setContactNo(phone);
                //set the delivery charges
                delivery.setDeliveryCharge(deliveryCharges);
                //set the time
                delivery.setDeliveryTime(time);
                //set the discount percentage
                delivery.setDiscountPercentage(discountPercentage);
                //set full name
                delivery.setFullName(fullName);
                //set discount
                delivery.setDiscount(discount);
                //set grand total
                delivery.setGrandTotal(grandTotal);
                //set actual price of the purchased foods
                delivery.setPriceOfPurchasedGoods(goodPrice);
                //set the price after reducing the discount
                delivery.setSubTotal(subTotal);


                //create an id for the transaction
                long id = System.currentTimeMillis();
                //add the transaction to the database
                reference.child("Transactions").child(String.valueOf(id)).setValue(delivery, new DatabaseReference.CompletionListener()
                {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference)
                    {
                        if(databaseError != null)
                        {
                            //if an error occurred, the error toast will be shown
                            Toast.makeText(PaymentMethodScreen.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                        else
                        {
                            //if the transaction was successfully added to the database
                            //we have to clear the cart, since the items are already purchased
                            DeleteCartItems();
                        }

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
        //show the dialog
        builder1.show();


    }

    private void DeleteCartItems()
    {
        //go the users cart

        reference.child("Cart").removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference)
            {
                if(databaseError != null)
                {
                    //if an error occurred, the error toast will be shown
                    Toast.makeText(PaymentMethodScreen.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //the cart has been successfully cleared
                    //the successful dialog will be shown to the user
                    final AlertDialog.Builder builder = new AlertDialog.Builder(PaymentMethodScreen.this);
                    builder.setTitle("We have successfully received your payment, Thank you for choosing Juice World");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            //if the user clicks "Ok"
                            //he will be navigated to the HomeScreen back
                            Intent intent = new Intent(PaymentMethodScreen.this,HomeScreen.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    //show the dialog
                    builder.show();

                }
                dialog.dismiss();
            }
        });

    }

    public void OnCancelPaymentClicked(View view)
    {

        //if on cancel button pressed
        //the confirmation dialog will be shown before cancel the payment
        AlertDialog.Builder builder = new AlertDialog.Builder(PaymentMethodScreen.this);
        builder.setTitle("Are you sure you want to cancel?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener()
        {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    //if the user clicks "Yes" button
                    //the transaction will be cancelled and the user will be navigated to the HomeScreen
                    Intent intent = new Intent(PaymentMethodScreen.this,HomeScreen.class);
                    startActivity(intent);
                    finish();
                }
         });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                //if the users press "No"
                //No action will be taken
            }
        });
        //show the dialog
        builder.show();

    }
}
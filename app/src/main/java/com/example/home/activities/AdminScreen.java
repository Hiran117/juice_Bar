package com.example.home.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.home.Models.FoodItem;
import com.example.home.R;
import com.example.home.config.SharedPrefManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.regex.Pattern;

public class AdminScreen extends AppCompatActivity {

    public static final int PICK_IMAGE = 100;
    private Uri uri = null;
    private ImageView food_image;
    private ProgressDialog dialog;

    private DatabaseReference reference;
    private StorageReference mStorageRef;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && data != null)
        {
            //catch the user picked image from the gallery and set in the UI
            uri = data.getData();
            food_image.setImageURI(uri);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);

        //init views
        food_image = findViewById(R.id.food_image);
        dialog = new ProgressDialog(AdminScreen.this);
        dialog.setMessage("Adding..");
        dialog.setCancelable(false);


        //set firebase database path and firebase storage path
        reference = FirebaseDatabase.getInstance().getReference("Foods");
        mStorageRef = FirebaseStorage.getInstance().getReference();


    }

    public void OnAddClicked(View view)
    {

        //this method will be triggered when the user clicks on Add button in the UI


        //init views
        EditText food_name = findViewById(R.id.food_name);
        EditText food_price = findViewById(R.id.food_price);
        final RadioButton juice = findViewById(R.id.juice);
        final RadioButton mocktail = findViewById(R.id.mocktail);
        final RadioButton desserts = findViewById(R.id.desserts);


        //getting the user inputs
        String foodNameT = food_name.getText().toString().trim();
        String foodPriceT = food_price.getText().toString().trim();


        //validate inputs
        if(foodNameT.equals("") || foodPriceT.equals(""))
        {
            Toast.makeText(this, "Please fill required fields first!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!juice.isChecked() && !mocktail.isChecked() && !desserts.isChecked())
        {
            Toast.makeText(this, "Please select Food Type", Toast.LENGTH_SHORT).show();
            return;
        }
        if(uri == null)
        {
            Toast.makeText(this, "Please pick a image", Toast.LENGTH_SHORT).show();
            return;
        }

        //show dialog
        dialog.show();

        //create new food object to store in the database
        final FoodItem foodItem = new FoodItem();
        //set attributes
        foodItem.setQty(1);
        foodItem.setName(foodNameT);
        foodItem.setPrice(Double.parseDouble(foodPriceT));

        //setting food id
        final long id = System.currentTimeMillis();
        //set firebase storage path
        final StorageReference sRef = mStorageRef.child("Foods/" + id);

        //adding the image to the database and fetch the link
        sRef.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        //fetching the image link
                        sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri)
                            {
                                //image link fetched
                                String link = uri.toString();
                                //setting the fetched link to the previously created object
                                foodItem.setImage(link);


                                //set the food type according to the radio button which has checked by the user
                                if(juice.isChecked())
                                {
                                    //add item as a juice to the database
                                    PutFoodToDatabase("Juices",foodItem,id);
                                }
                                else if(mocktail.isChecked())
                                {
                                    //add item as a Mocktail to the database
                                    PutFoodToDatabase("Mocktails",foodItem,id);
                                }
                                else if(desserts.isChecked())
                                {
                                    //add item as a Dessert to the database
                                    PutFoodToDatabase("Desserts",foodItem,id);
                                }
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception exception)
            {
                //if fail? the dialog will be dismissed
                dialog.dismiss();

            }});










    }

    private void PutFoodToDatabase(String path, FoodItem item,long id)
    {

        //adding the food item to the database
        reference.child(path).child(String.valueOf(id)).setValue(item, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference)
            {
                if(databaseError != null)
                {
                    //an error occurred while adding the item
                    Toast.makeText(AdminScreen.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //successfully added
                    Toast.makeText(AdminScreen.this, "Successfully Added!", Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
            }
        });
    }

    public void ChooseFoodImage(View view)
    {
        //this method will be triggered to pick an image from the gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    public void LogOut(View view)
    {
        //this method will be triggered when the user press the logout button

        //set the user id as -99 to logout the user
        SharedPrefManager.setLoginUserId(AdminScreen.this,-99);
        //and then navigate to Login screen
        Intent intent = new Intent(AdminScreen.this, LoginScreen.class);
        startActivity(intent);
        finish();
    }
}
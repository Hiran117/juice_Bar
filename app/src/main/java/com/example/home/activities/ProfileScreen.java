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
import android.net.Uri;
import android.os.Bundle;
<<<<<<< HEAD
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

=======
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

>>>>>>> origin/master
import com.bumptech.glide.Glide;
import com.example.home.Models.User;
import com.example.home.R;
import com.example.home.config.SharedPrefManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileScreen extends AppCompatActivity {

    private CircleImageView profile_pic;
    private EditText fullname,email,phone,gender,address;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ProgressDialog dialog;
    private StorageReference mStorageRef;


    private Uri uri = null;
    private User loggedUser;


    public static final int PICK_IMAGE = 100;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && data != null)
        {
            //fetch the user selected image from the gallery
            uri = data.getData();
            //and set the image to the imageView in the UI
            profile_pic.setImageURI(uri);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);

        //init views
        profile_pic = findViewById(R.id.profile_pic);
        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        gender = findViewById(R.id.gender);
        address = findViewById(R.id.address);

        //init progress dialog
        dialog = new ProgressDialog(ProfileScreen.this);
        dialog.setMessage("Syncing..");
        dialog.show();
        dialog.setCancelable(false);

        //set database reference
        database = FirebaseDatabase.getInstance();
        //set storage reference to upload the image
        mStorageRef = FirebaseStorage.getInstance().getReference();
        //get the current user id from the shared preferences
        long id = SharedPrefManager.getLoginUserId(ProfileScreen.this);
        reference = database.getReference("Users");

        //get user profile information from the database
        setDetails(id);
    }

    private void setDetails(long id)
    {
        //get the profile details
        reference.child("" + id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                //check whether the user has profile information
                if(dataSnapshot.exists())
                {
                    //set the user as a global variable
                    loggedUser = dataSnapshot.getValue(User.class);

                    //get the user image and set in the ui
                    Glide.with(ProfileScreen.this)
                            .load(loggedUser.getImageLink())
                            .placeholder(R.drawable.profile_picture)
                            .error(R.drawable.profile_picture)
                            .into(profile_pic);


                    //get the full name and set in the UI
                    fullname.setText(loggedUser.getFullName());
                    //get the email and set in the UI
                    email.setText(loggedUser.getEmail());
                    //get the phone number and set in the UI
                    phone.setText(loggedUser.getPhone());
                    //get the gender and set in the UI
                    gender.setText(loggedUser.getGender());
                    //get the address and set in the UI
                    address.setText(loggedUser.getAddress());

                }
                else
                {
                    //if the user has no user information
                    Toast.makeText(ProfileScreen.this, "User was not found, It may have been deleted by the admin", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                if(databaseError != null)
                {
                    //if an error occurred,the toast will be shown
                    Toast.makeText(ProfileScreen.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
    }

    public void PickImage(View view)
    {
        //this method will be triggered when the user tries to pick an image
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    public void UpdateProfile(View view)
    {
        //this method will be triggered when the Update button is pressed

        //get the user inputs
        String name = fullname.getText().toString().trim();
        String phoneT = phone.getText().toString().trim();
        String emailT = email.getText().toString().trim();
        String genderT = gender.getText().toString().trim();
        String addressT = address.getText().toString().trim();

        //validate user inputs
        if(name.equals("") || phone.equals("") || email.equals(""))
        {
            Toast.makeText(this, "Please enter all required fields..", Toast.LENGTH_SHORT).show();
            return;
        }
        //validate email address
        if(!Patterns.EMAIL_ADDRESS.matcher(emailT).matches())
        {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return;
        }

        //show progress dialog
        dialog.setMessage("Updating..");
        dialog.show();

        //create a new user object
        final User user = new User();
        //get the user id from shared preferences and set it in the object
        user.setId(SharedPrefManager.getLoginUserId(ProfileScreen.this));
        //set full name
        user.setFullName(name);
        //set user phone number
        user.setPhone(phoneT);
        //set the user email
        user.setEmail(emailT);
        //set the user's address
        user.setAddress(addressT);
        //set the user name
        user.setUserName(loggedUser.getUserName());
        //set the password
        user.setPassword(loggedUser.getPassword());
        //set the gender
        user.setGender(genderT);

        //check whether the user has picked an image as profile picture
        if(uri != null)
        {
            //if an image has picked
            final StorageReference sRef = mStorageRef.child("ProfilePictures/" + user.getId());
           //the image will be uploaded to the database
            sRef.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                    {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            //and the image link will be fetched
                            sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri)
                                {
                                    //get the image link
                                    String profilePicId = uri.toString();
                                    //set the image link in the user object
                                    user.setImageLink(profilePicId);
                                    //Update the user
                                    UpdateUser(user);
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener()
            {
                @Override
                public void onFailure(@NonNull Exception exception)
                {
                    dialog.dismiss();

                }});

        }
        else
        {
            //no image has been picked
            //the existing image will be set in the user object
            user.setImageLink(loggedUser.getImageLink());
            //update the user
            UpdateUser(user);
        }
    }
    private void UpdateUser(User user)
    {

        //update the user's address in the database
        reference.child(String.valueOf(user.getId())).child("address").setValue(user.getAddress());
        //update the user's email in the database
        reference.child(String.valueOf(user.getId())).child("email").setValue(user.getEmail());
        //update the user's full name in the database
        reference.child(String.valueOf(user.getId())).child("fullName").setValue(user.getFullName());
        //update the user's gender in the database
        reference.child(String.valueOf(user.getId())).child("gender").setValue(user.getGender());
        //update the user's image link in the database
        reference.child(String.valueOf(user.getId())).child("imageLink").setValue(user.getImageLink());
        //update the user's password in the database
        reference.child(String.valueOf(user.getId())).child("password").setValue(user.getPassword());
        //update the user's phone number in the database
        reference.child(String.valueOf(user.getId())).child("phone").setValue(user.getPhone());
        //update the user's user Name in the database
        reference.child(String.valueOf(user.getId())).child("userName").setValue(user.getUserName(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference)
            {
                if(databaseError != null)
                {
                    //an error occurred while updating
                    //error toast will be shown
                    showToast(databaseError.getMessage());
                }
                else
                {
                    //the profile has been successfully updated
                    //successful toast will be shown
                    showToast("Successfully Updated!");
                }

                dialog.dismiss();
            }
        });
    }
    public void showToast(String message)
    {
        //show the toast
        if(message != null && !message.equals(""))
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void LogOut(View view)
    {
        //log out the user
        //set the id as -99 to check the user is logged or not
        //-99 = no user logged to the system
        SharedPrefManager.setLoginUserId(ProfileScreen.this,-99);
        //go to the login screen
        Intent intent = new Intent(ProfileScreen.this, LoginScreen.class);
        startActivity(intent);
        finish();
    }

    public void DeleteProfile(View view)
    {

        //this method will be triggered when the user clicks the delete profile button
        //get the currently logged user id
        final long id = SharedPrefManager.getLoginUserId(ProfileScreen.this);
        //check whether a valid user is logged in to the system
        //if id = -99, no users has been logged
        //if id = -100, an admin has been logged
        //if id = any other value,a valid user is logged in
        if(id == -99 || id == -100)
        {
            Toast.makeText(this, "Please create a valid account first!", Toast.LENGTH_SHORT).show();
            return;
        }

        //show the confirmation dialog before deleting the profile
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileScreen.this);
        builder.setMessage("Are you sure you want to delete the profile?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                    //if the user clicks "Yes" button
                    dialog.show();
                    //the profile will be deleted from the database
                    reference.child(String.valueOf(id)).removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference)
                        {
                            if(databaseError != null)
                            {
                                //if an error occurred, the error will be shown
                                showToast(databaseError.getMessage());
                            }
                            else
                            {
                                //the account deletion is successful
                                showToast("Profile was successfully deleted!");
                                //log out the user from the system
                                //set the id as -99 to represent that
                                SharedPrefManager.setLoginUserId(ProfileScreen.this,-99);
                                //navigate the user into the LoginScreen agin
<<<<<<< HEAD
                                Intent intent = new Intent(ProfileScreen.this,LoginScreen.class);
=======
                                Intent intent = new Intent(ProfileScreen.this, LoginScreen.class);
>>>>>>> origin/master
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
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
                //if the user clicks "No"
                //no action will be taken
            }
        });
        //show the dialog
        builder.show();

    }
}
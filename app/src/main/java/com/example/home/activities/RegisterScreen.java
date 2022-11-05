package com.example.home.activities;

<<<<<<< HEAD
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
=======
>>>>>>> origin/master
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
<<<<<<< HEAD
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.home.Models.User;
import com.example.home.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
=======
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.home.Models.User;
import com.example.home.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
>>>>>>> origin/master
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
<<<<<<< HEAD
import com.google.firebase.storage.OnProgressListener;
=======
>>>>>>> origin/master
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterScreen extends AppCompatActivity {

    private Uri uri = null;
    private DatabaseReference reference;
    private StorageReference mStorageRef;
    private ProgressDialog progressBar;
    private CircleImageView profile_pic;

    public static final int PICK_IMAGE = 100;



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && data != null)
        {
            //fetch the user picked image from the gallery
            uri = data.getData();
            //set the image in the ImageView in the UI
            profile_pic.setImageURI(uri);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        //init views
        profile_pic = findViewById(R.id.profile_pic);
        progressBar = new ProgressDialog(RegisterScreen.this);
        progressBar.setMessage("Registering..");
        progressBar.setCancelable(false);

        //set the database path
        reference = FirebaseDatabase.getInstance().getReference("Users");
        //set the storage path to upload the user image
        mStorageRef = FirebaseStorage.getInstance().getReference();

    }

    public void Register(View view)
    {
        //this method will be executed when the user clicks the register button

        //ini views
        EditText fullname = findViewById(R.id.fullname);
        EditText username = findViewById(R.id.username);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        EditText c_password = findViewById(R.id.c_password);
        EditText phone = findViewById(R.id.phone);

        //get the email text from the input
        String emailT = email.getText().toString().trim();

        //validate full name
        if(fullname.getText().toString().trim().equals(""))
        {
            showToast("Please enter Full name");
            return;
        }
        //validate user name
        if(username.getText().toString().trim().equals(""))
        {
            showToast("Please enter User name");
            return;
        }
        //validate email
        if(email.getText().toString().trim().equals(""))
        {
            showToast("Please enter Email");
            return;
        }
        //validate password
        if(password.getText().toString().trim().equals(""))
        {
            showToast("Please enter Password");
            return;
        }
        //validate confirm password
        if(c_password.getText().toString().trim().equals(""))
        {
            showToast("Please Confirm Password");
            return;
        }
        //validate phone number
        if(phone.getText().toString().trim().equals(""))
        {
            showToast("Please enter Phone Number");
            return;
        }
        //check whether the entered passwords are matched
        if(!password.getText().toString().equals(c_password.getText().toString()))
        {
            showToast("Password mismatched!");
            return;
        }
        //check whether the email is valid
        if(!Patterns.EMAIL_ADDRESS.matcher(emailT).matches())
        {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return;
        }

        //show progress dialog
        //and register user
        progressBar.show();
        registerUser(fullname.getText().toString(),username.getText().toString(),email.getText().toString(),password.getText().toString(),phone.getText().toString());

    }

    private void registerUser(String fullname, String username, String email, String password, String phone)
    {
        //generate a id for the user
        long id = System.currentTimeMillis();

        //create a new user object
        final User user = new User();
        //set the generated id
        user.setId(id);
        //set the full name
        user.setFullName(fullname);
        //set the user name
        user.setUserName(username);
        //set the email
        user.setEmail(email);
        //set the password
        user.setPassword(password);
        //set the phone number
        user.setPhone(phone);
        //set the address
        user.setAddress("");
        //set the gender
        user.setGender("");

        //check whether an image is picked as profile pic or not
        if(uri != null)
        {
            //set the database storage path
            final StorageReference sRef = mStorageRef.child("ProfilePictures/" + id);
            //adding image to the database
            sRef.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                    {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                        {
                            //fetch the image link
                            sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri)
                                {
                                    //fetch link
                                    String profilePicId = uri.toString();
                                    //set the image link in the user object
                                    user.setImageLink(profilePicId);
                                    //register new user
                                    RegisterUser(user);
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener()
                {
                @Override
                public void onFailure(@NonNull Exception exception)
                {
                    progressBar.dismiss();

                }});

        }
        else
        {
            //if an image is not picked as the profile picture

            //set the user image as empty
            user.setImageLink("");
            //register the new user
            RegisterUser(user);
        }


    }
    private void RegisterUser(User user)
    {

        //register user
        reference.child(String.valueOf(user.getId())).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference)
            {
                if(databaseError != null)
                {
                    //an error occurred, the error toast will be shown
                    showToast(databaseError.getMessage());
                }
                else
                {
                    //successfully registered the user
                    showToast("Successfully Registered!");
                    //the user will be navigated to the LoginScreen
<<<<<<< HEAD
                    Intent intent = new Intent(RegisterScreen.this,LoginScreen.class);
=======
                    Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
>>>>>>> origin/master
                    startActivity(intent);
                    finish();
                }

                progressBar.dismiss();

            }

        });
    }

    public void PickImage(View view)
    {
        //this method will be executed to pick an image from the gallery as the profile picture
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }
    public void showToast(String message)
    {
        //show the toast
        if(message != null && !message.equals(""))
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
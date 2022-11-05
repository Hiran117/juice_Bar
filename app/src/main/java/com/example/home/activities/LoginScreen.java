package com.example.home.activities;

<<<<<<< HEAD
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

=======
>>>>>>> origin/master
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

<<<<<<< HEAD
=======
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

>>>>>>> origin/master
import com.example.home.Models.User;
import com.example.home.R;
import com.example.home.config.SharedPrefManager;
import com.example.home.views.SnackBar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginScreen extends AppCompatActivity {

    private DatabaseReference reference;
    private FirebaseDatabase database;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        //init views and firebase database reference
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");
        dialog = new ProgressDialog(LoginScreen.this);
        dialog.setMessage("Checking..");
        dialog.setCancelable(false);

    }

    public void showSignUpScreen(View view)
    {
        //go the sign up screen
        Intent intent = new Intent(LoginScreen.this,RegisterScreen.class);
        startActivity(intent);
    }

    public void Login(View view)
    {
        //this method will be triggered when the login button is pressed
        //init views
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);

        //get the user input
        final String emailText = email.getText().toString().trim();
        final String passwordText = password.getText().toString().trim();

        //validate email and password
        if(emailText.equals(""))
        {
            Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(passwordText.equals(""))
        {
            Toast.makeText(this, "Please enter a valid Password", Toast.LENGTH_SHORT).show();
            return;
        }



        //check whether the credentials are matched with admin
        if(emailText.equals("Admin") || emailText.equals("admin") && passwordText.equals("Admin") || passwordText.equals("admin"))
        {
            //if the admin logs
            //save user id as 100

            //save the user id in the shared preferences
            SharedPrefManager.setLoginUserId(LoginScreen.this,100);
            //go to the AdminScreen
<<<<<<< HEAD
            Intent intent = new Intent(LoginScreen.this,AdminScreen.class);
=======
            Intent intent = new Intent(LoginScreen.this, AdminScreen.class);
>>>>>>> origin/master
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return;
        }

        //validate email
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches())
        {
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return;
        }




        //if the credentials are not matched with admin
        dialog.show();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for(DataSnapshot dss : dataSnapshot.getChildren())
                {
                    //get each user from the database to check whether the entered user name and password matches with some user in the db
                    User user = dss.getValue(User.class);
                    //check whether the user name and password is not null of the user
                    if(user != null && user.getEmail() != null && user.getEmail().equals(emailText) && user.getPassword() != null && user.getPassword().equals(passwordText))
                    {
                        //if the entered email and password matches with some user
                        //the users id will be stored in the shared preferences
                        SharedPrefManager.setLoginUserId(LoginScreen.this,user.getId());

                        dialog.dismiss();
                        //login is success
                        Toast.makeText(LoginScreen.this, "Login Success!", Toast.LENGTH_SHORT).show();
                        //home screen will be shown
<<<<<<< HEAD
                        Intent intent = new Intent(LoginScreen.this,HomeScreen.class);
=======
                        Intent intent = new Intent(LoginScreen.this, HomeScreen.class);
>>>>>>> origin/master
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }

                //no user matches the entered email password in the database
                dialog.dismiss();
                //login failed toast will be shown
                SnackBar.showCustomSnackBar(LoginScreen.this,findViewById(R.id.root),"Login Failed!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                //if an error occurred while getting users
                //the error toast will be shown
                if(databaseError != null)
                Toast.makeText(LoginScreen.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
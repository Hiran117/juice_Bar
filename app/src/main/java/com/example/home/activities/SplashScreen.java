package com.example.home.activities;

<<<<<<< HEAD
import androidx.appcompat.app.AppCompatActivity;

=======
>>>>>>> origin/master
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

<<<<<<< HEAD
=======
import androidx.appcompat.app.AppCompatActivity;

>>>>>>> origin/master
import com.example.home.R;
import com.example.home.config.SharedPrefManager;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        //first screen shows when the app is opened
        //initializing a handler to wait 2 seconds
        //and then decide to which activity to navigate
        //it depends on the user id
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                //get user id from the sharedPreferences
                long userId = SharedPrefManager.getLoginUserId(SplashScreen.this);

                //check if the id = -99
                if(userId == -99)
                {
                    //if id = -99
                    //it means, no user has logged to the system yet
                    //so navigate him to the LoginScreen
                    Intent intent = new Intent(SplashScreen.this,LoginScreen.class);
                    startActivity(intent);
                }
                //check if the id = 100
                else if(userId == 100)
                {
                    //if id = 100
                    //it means, the logged user is an Admin
                    //so navigate him to the AdminScreen
                    Intent intent = new Intent(SplashScreen.this,AdminScreen.class);
                    startActivity(intent);
                }
                //check if the id is not 100 and -99
                //if it is, it means some valid user has logged to the system
                else
                {
                    //so navigate him to the HomeScreen
                    Intent intent = new Intent(SplashScreen.this, HomeScreen.class);
                    startActivity(intent);
                }
                //and finish this SplashScreen activity
                finish();
            }
        },2000);
    }
}
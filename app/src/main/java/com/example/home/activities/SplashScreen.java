package com.example.home.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.home.R;
import com.example.home.config.SharedPrefManager;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        //first screen shows when the app is opened
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
                    Intent intent = new Intent(SplashScreen.this,LoginScreen.class);
                    startActivity(intent);
                }
                //check if the id = 100
                else if(userId == 100)
                {
                    //if id = 100
                    Intent intent = new Intent(SplashScreen.this,AdminScreen.class);
                    startActivity(intent);
                }
                //check if the id is not 100 and -99
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
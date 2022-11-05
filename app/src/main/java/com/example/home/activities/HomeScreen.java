package com.example.home.activities;

<<<<<<< HEAD
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

=======
>>>>>>> origin/master
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

<<<<<<< HEAD
=======
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

>>>>>>> origin/master
import com.example.home.R;
import com.example.home.config.SharedPrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeScreen extends AppCompatActivity {

    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //init views
        navigationView = findViewById(R.id.navigationView);

        //add a listener to the bottom navigation view in the UI
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                //if the user selects the cart
                if(item.getItemId() == R.id.cart)
                {
                    //cart screen will be shown
                    GoToCart();
                }
                //if the user selects the profile
                else if(item.getItemId() == R.id.profile)
                {
                    //profile screen will be shown
                    GoToProfileScreen();
                }
                //if the user selects the reservations
                else if(item.getItemId() == R.id.reservation)
                {
                    //reservation screen will be shown
                    GoToReservation();
                }
                return true;
            }
        });
    }

    private void GoToReservation()
    {
        //checking whether the user is a valid registered user
        //getting the login user's id from shared preferences
        long id = SharedPrefManager.getLoginUserId(HomeScreen.this);
        //and if the id is = -99
        //it says the user already logged out
        if(id == -99)
        {
            //so the please login first toast will be shown
            Toast.makeText(this, "Please login first!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //if there are no any conflicts with the user id
            //the user will be navigate to the ReservationActivity
            Intent intent = new Intent(HomeScreen.this, ReservationActivity.class);
            startActivity(intent);
        }
    }
    public void GoToJuicesScreen(View view)
    {
        //this method will be triggered when the user select the juices
        //and will be visible the JuicesScreen
        Intent intent = new Intent(HomeScreen.this,JuicesScreen.class);
        startActivity(intent);
    }
    public void GoToMocktailScreen(View view)
    {
        //this method will be triggered when the user select the Mocktails
        //and will be visible the MocktailsScreen
        Intent intent = new Intent(HomeScreen.this,MocktailsScreen.class);
        startActivity(intent);
    }
    public void GoToDessertsScreen(View view)
    {
        //this method will be triggered when the user select the DessertsScreen
        //and will be visible the DessertsScreen
        Intent intent = new Intent(HomeScreen.this,DessertsScreen.class);
        startActivity(intent);
    }
    public void GoToPartyReservationScreen(View view)
    {
        //this method will be triggered when the user select the Reservations
        //and will be visible the ReservationScreen
<<<<<<< HEAD
        Intent intent = new Intent(HomeScreen.this,ReservationScreen.class);
=======
        Intent intent = new Intent(HomeScreen.this, ReservationScreen.class);
>>>>>>> origin/master
        startActivity(intent);
    }

    public void GoToProfileScreen()
    {
        //checking whether the user is a valid registered user
        //getting the login user's id from shared preferences
        long id = SharedPrefManager.getLoginUserId(HomeScreen.this);
        //if the user's id = -99, the user is not a valid user
        if(id == -99)
        {
            //show the error toast
            Toast.makeText(this, "Please login first!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //show the ProfileScreen to the user
            Intent intent = new Intent(HomeScreen.this,ProfileScreen.class);
            startActivity(intent);
        }
    }

    public void GoToCart()
    {
        //checking whether the user is a valid registered user
        //getting the login user's id from shared preferences
        long id = SharedPrefManager.getLoginUserId(HomeScreen.this);
        //if the user's id = -99, the user is not a valid user
        if(id == -99)
        {
            //show the error toast
            Toast.makeText(this, "Please login first!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //show the ShoppingCartScreen to the user
            Intent intent = new Intent(HomeScreen.this,ShoppingCartScreen.class);
            startActivity(intent);
        }

    }
}
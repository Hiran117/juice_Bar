package com.example.home.Models;

<<<<<<< HEAD
import android.graphics.drawable.Drawable;

=======
>>>>>>> origin/master
import java.io.Serializable;


public class FoodItem implements Serializable
{
    //This class contains all the variables related to the food item

    private String image;
    private String name;
    private int qty;
    private double price;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public FoodItem(String image, String name, int qty, double price)
    {
        this.image = image;
        this.name = name;
        this.qty = qty;
        this.price = price;
    }
    public FoodItem(){}



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

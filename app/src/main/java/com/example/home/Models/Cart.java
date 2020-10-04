package com.example.home.Models;

import java.io.Serializable;

public class Cart implements Serializable
{
    //This class contains all the variables related to the cart
    private String name;
    private int qty;
    private double price;

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

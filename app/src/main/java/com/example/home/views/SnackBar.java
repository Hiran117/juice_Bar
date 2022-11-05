package com.example.home.views;

import android.content.Context;
<<<<<<< HEAD
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
=======
import android.view.View;
>>>>>>> origin/master

import com.example.home.R;
import com.google.android.material.snackbar.Snackbar;

public class SnackBar
{
    public static void showCustomSnackBar(Context context,View root,String message)
    {
        //custom snackBar
        Snackbar sb = Snackbar.make(root,message,Snackbar.LENGTH_LONG);

        sb.setActionTextColor(context.getResources().getColor(R.color.white));
        View sbView = sb.getView();
        sbView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        sb.show();
    }
}

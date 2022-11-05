package com.example.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
<<<<<<< HEAD
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.home.Models.Delivery;
import com.example.home.Models.Reservation;
import com.example.home.R;
import com.example.home.activities.ReservationActivity;
import com.example.home.config.SharedPrefManager;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
=======

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.home.Models.Reservation;
import com.example.home.R;
import com.example.home.activities.ReservationActivity;
>>>>>>> origin/master

import java.util.ArrayList;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.MyViewHolder>
{
    private Context context;
    private ArrayList<Reservation> list;

    public ReservationAdapter(Context context, ArrayList<Reservation> list)
    {
        //store context and the list to be displayed
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ReservationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //set the viewHolder with the reservation_item layout
        View view = LayoutInflater.from(context).inflate(R.layout.reservation_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationAdapter.MyViewHolder holder, int position)
    {
        //check whether the item list is not null and the size of the list is less than or equal to the item position
        if(list != null && list.size() > position)
        {
            //get the reservation object
            final Reservation res = list.get(position);

            //validate date and set date in the UI
            if(res.getDate() != null)
            {
                holder.date.setText("Date : " + res.getDate());
            }
            //validate time and set time in the UI
            if(res.getTime() != null)
            {
                holder.time.setText("Time : " + res.getTime());
            }
            //validate hall and set hall in the UI
            if(res.getHall() != null)
            {
                holder.hall.setText("Hall : " + res.getHall());

                //set the hall image

                //if hallB
                if(res.getHall().equals("Hall B"))
                {
                    holder.image.setImageResource(R.drawable.hall_b);
                }
                //if Hall A
                else
                {
                    holder.image.setImageResource(R.drawable.hall_a);
                }
            }
            //validate size and set size in the UI
            if(res.getSize() != null)
            {
                holder.size.setText("Size : " + res.getSize());
            }
            //validate number and set number in the UI
            if(res.getPhone() != null)
            {
                holder.phone.setText("Phone : " + res.getPhone());
            }

            //if delete button is pressed
            holder.delete_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {

                    //get the reservation id of the item that needs to be deleted
                    long reservationId = res.getId();
                    if(context instanceof ReservationActivity)
                    {
                        //call the ReservationActivity deleteItem  method to delete the item
                        ReservationActivity activity = (ReservationActivity) context;
                        activity.deleteItem(reservationId);
                    }


                }
            });

            //if the edit button is pressed
            holder.edit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    //get the reservation id of the item that needs to be edited
                    long reservationId = res.getId();
                    if(context instanceof ReservationActivity)
                    {
                        //call the ReservationActivity editItem method to edit the item
                        ReservationActivity activity = (ReservationActivity) context;
                        activity.editItem(reservationId);
                    }
                }
            });

        }
    }

    @Override
    public int getItemCount()
    {
        //return the size of the list
        if(list != null)
            return list.size();
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        //create view objects
        TextView date,time,size,hall,phone;
        ImageView image;
        Button edit_btn,delete_btn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //init views
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            hall = itemView.findViewById(R.id.hall);
            size = itemView.findViewById(R.id.size);
            phone = itemView.findViewById(R.id.phone);
            image = itemView.findViewById(R.id.image);
            edit_btn = itemView.findViewById(R.id.edit_btn);
            delete_btn = itemView.findViewById(R.id.delete_btn);

        }
    }
}

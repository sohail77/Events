package com.sohail.events.m_UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.transcode.BitmapBytesTranscoder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.sohail.events.DetailActivity;
import com.sohail.events.R;
import com.sohail.events.m_Model.Spacecraft;
import com.sohail.events.m_UI.ItemClickListener;
import com.sohail.events.m_UI.MyViewHolder;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;


public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {


    DatabaseReference db;
    com.google.firebase.database.Query eventQuery;
    Context c;
    public ArrayList<Spacecraft> spacecrafts;



    public MyAdapter(Context c, ArrayList<Spacecraft> spacecrafts) {
        this.c = c;
        this.spacecrafts = spacecrafts;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(c).inflate(R.layout.model,parent,false);
        return new MyViewHolder(v);


    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final  Spacecraft s=spacecrafts.get(position);

        holder.nameTxt.setText(s.getName());
        holder.grpTxt.setText(s.getPropellant());
        holder.descTxt.setText(s.getDescription());
        holder.linkTxt.setText(s.getLink());



        Glide.with(c).load(s.getImageUrl()).into(holder.imageView);



        db=FirebaseDatabase.getInstance().getReference();
        long cutoff = new Date().getTime() - TimeUnit.MILLISECONDS.convert(30, TimeUnit.DAYS);
        com.google.firebase.database.Query oldItems = db.child("Spacecraft").orderByChild("timestamp").endAt(cutoff);
        oldItems.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot itemSnapshot: snapshot.getChildren()) {
                    itemSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                //OPEN DETAI ACTIVITY
                openDetailActivity(s.getName(),s.getDescription(),s.getPropellant(),s.getLink(),s.getImageUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        return spacecrafts.size();
    }

    //OPEN DETAIL ACTIVITY
    private void openDetailActivity(String...details)
    {
        Intent i=new Intent(c,DetailActivity.class);

        i.putExtra("NAME_KEY",details[0]);
        i.putExtra("DESC_KEY",details[1]);
        i.putExtra("PROP_KEY",details[2]);
        i.putExtra("LINK_KEY",details[3]);
        i.putExtra("IMAGE_KEY",details[4]);


        c.startActivity(i);
    }

}
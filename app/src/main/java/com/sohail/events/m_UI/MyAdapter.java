package com.sohail.events.m_UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sohail.events.DetailActivity;
import com.sohail.events.R;
import com.sohail.events.m_Model.Spacecraft;
import com.sohail.events.m_UI.ItemClickListener;
import com.sohail.events.m_UI.MyViewHolder;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context c;
    ArrayList<Spacecraft> spacecrafts;

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

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                //OPEN DETAI ACTIVITY
                openDetailActivity(s.getName(),s.getDescription(),s.getPropellant(),s.getLink());
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

        c.startActivity(i);
    }
}
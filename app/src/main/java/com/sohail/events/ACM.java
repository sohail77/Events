package com.sohail.events;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.sohail.events.m_Firebase.FirebaseHelper;
import com.sohail.events.m_Model.Spacecraft;
import com.sohail.events.m_UI.MyAdapter;

import java.util.ArrayList;

public class ACM extends AppCompatActivity  {


    DatabaseReference db;
    Query query;
    ArrayList<Spacecraft> spacecrafts=new ArrayList<>();;
    RecyclerView rv;
    MyAdapter adapter;
    SwipeRefreshLayout swipeRefresh;
    String name;
    TextView noEvents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acm);
        rv=(RecyclerView)findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        swipeRefresh=(SwipeRefreshLayout)findViewById(R.id.swiperefresh);
        noEvents=(TextView)findViewById(R.id.noEvents);

        Bundle nameTxt=getIntent().getExtras();

        name=nameTxt.getString("GROUP_NAME");




        db= FirebaseDatabase.getInstance().getReference();
        query=db.child("Spacecraft").orderByChild("propellant").equalTo(name);
        adapter=new MyAdapter(this,retrieve());
        rv.setAdapter(adapter);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rv.setAdapter(adapter);
                swipeRefresh.setRefreshing(false);
            }
        });


    }

    public ArrayList<Spacecraft> retrieve(){
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                spacecrafts.clear();
                if(dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Spacecraft spacecraft = ds.getValue(Spacecraft.class);
                        spacecrafts.add(spacecraft);

                    }

                    adapter.notifyDataSetChanged();
                }else {
                    rv.setVisibility(View.INVISIBLE);
                    noEvents.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return spacecrafts;
    }

}

package com.sohail.events.m_Firebase;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.sohail.events.OnFirebaseDataChanged;
import com.sohail.events.m_Model.Spacecraft;
import com.sohail.events.m_UI.MyAdapter;

import java.util.ArrayList;


public class FirebaseHelper {

    OnFirebaseDataChanged dataChangeListener;
    DatabaseReference db;
    Boolean saved=null;
    ArrayList<Spacecraft> spacecrafts=new ArrayList<>();


    public FirebaseHelper(DatabaseReference db, OnFirebaseDataChanged dataChangeListener) {
        this.db = db;
        this.dataChangeListener=dataChangeListener;
    }

    //WRITE IF NOT NULL
    public Boolean save(Spacecraft spacecraft)
    {
        if(spacecraft==null)
        {
            saved=false;
        }else
        {
            try
            {
                db.child("Spacecraft").push().setValue(spacecraft);
                saved=true;


            }catch (DatabaseException e)
            {
                e.printStackTrace();
                saved=false;
            }
        }

        return saved;
    }

    //IMPLEMENT FETCH DATA AND FILL ARRAYLIST
    public void fetchData(DataSnapshot dataSnapshot)
    {
        spacecrafts.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            Spacecraft spacecraft=ds.getValue(Spacecraft.class);
            spacecrafts.add(spacecraft);

        }

        dataChangeListener.dataChanged();



    }


    //READ THEN RETURN ARRAYLIST
    public  ArrayList<Spacecraft> retrieve() {

        db.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);



            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                fetchData(dataSnapshot);

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return spacecrafts;
    }






}

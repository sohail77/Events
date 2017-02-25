package com.sohail.events;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sohail.events.m_Firebase.FirebaseHelper;
import com.sohail.events.m_Model.Spacecraft;
import com.sohail.events.m_UI.MyAdapter;
import com.google.firebase.firebase_core.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.http.Query;

/*
1.INITIALIZE FIREBASE DB
2.INITIALIZE UI
3.DATA INPUT
 */
public class MainActivity extends AppCompatActivity {


    private static final int RC_PHOTO_PICKER =  2;

    DatabaseReference db;
    FirebaseHelper helper;
    MyAdapter adapter;
    RecyclerView rv;
    EditText nameEditTxt,grpTxt,descTxt,linkTxt;
    FirebaseAuth.AuthStateListener authListener;
    SwipeRefreshLayout swipeRefresh;
    Uri downloadUrl;
    String Admin_code;
    FirebaseStorage mfirebaseStorage;
    private StorageReference mEventPhotoReference;
    FloatingActionButton fab;
    SharedPreferences sharedPreferences;
    ProgressBar spinner;


    static boolean calledAlready=false;
    public MyAdapter adapter1;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinner = (ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);


        mfirebaseStorage=FirebaseStorage.getInstance();

        if(!calledAlready){
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            calledAlready=true;
        }

        swipeRefresh=(SwipeRefreshLayout)findViewById(R.id.swiperefresh);

        //SETUP RECYCLER
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));



        //INITIALIZE FIREBASE DB
        db= FirebaseDatabase.getInstance().getReference();
        mEventPhotoReference=mfirebaseStorage.getReference().child("Event Photos");
        helper=new FirebaseHelper(db);



        //ADAPTER
        adapter=new MyAdapter(this,helper.retrieve());
        rv.setAdapter(adapter);




        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                rv.setAdapter(adapter);
                swipeRefresh.setRefreshing(false);
            }
        });

        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        Admin_code=sharedPreferences.getString(getString(R.string.Admin_code),getString(R.string.Admin_default_value));

        Log.e("MainActivity","" + Admin_code);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayInputDialog();
            }
        });

        fab.setVisibility(View.GONE);
        showBtn();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onResume() {
        showBtn();
        super.onResume();

    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void showBtn(){
        Admin_code=sharedPreferences.getString(getString(R.string.Admin_code),getString(R.string.Admin_default_value));
        if(Objects.equals(Admin_code, "28011996")){

            fab.setVisibility(View.VISIBLE);

        }
        else
            fab.setVisibility(View.GONE);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            return true;
        }
        if(id==R.id.settings){
            Intent settingsIntent=new Intent(this,Settings.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();

            StorageReference photoRef=mEventPhotoReference.child(selectedImageUri.getLastPathSegment());

            photoRef.putFile(selectedImageUri)
                    .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // When the image has successfully uploaded, we get its download URL
                            downloadUrl = taskSnapshot.getDownloadUrl();
                        }

                    });

        }


    }

    //DISPLAY INPUT DIALOG
    private void displayInputDialog()
    {
        Dialog d=new Dialog(this);
        d.setTitle("Save To Firebase");
        d.setContentView(R.layout.input_dialog);

        nameEditTxt= (EditText) d.findViewById(R.id.nameEditText);
        grpTxt= (EditText) d.findViewById(R.id.propellantEditText);
        descTxt= (EditText) d.findViewById(R.id.descEditText);
        Button saveBtn= (Button) d.findViewById(R.id.saveBtn);
        Button photoBtn=(Button)d.findViewById(R.id.photoBtn);
        linkTxt = (EditText) d.findViewById(R.id.linkEditText);



        photoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });


        //SAVE
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //GET DATA
                String name=nameEditTxt.getText().toString();
                String propellant=grpTxt.getText().toString();
                String desc=descTxt.getText().toString();
                String link=linkTxt.getText().toString();
                Long tsLong = System.currentTimeMillis()/1000;
                String ts = tsLong.toString();


                //SET DATA
                Spacecraft s=new Spacecraft();

                if(downloadUrl==null){
                    Toast.makeText(MainActivity.this,"Photo is necessary to add an Event",Toast.LENGTH_SHORT).show();
                }else {
                    s.setName(name);
                    s.setPropellant(propellant);
                    s.setDescription(desc);
                    s.setLink(link);
                    s.setImageUrl(downloadUrl.toString());
                    s.setTimestamp(ts);
                }
                //SIMPLE VALIDATION
                if(name != null && name.length()>0)
                {
                    //THEN SAVE
                    if(helper.save(s))
                    {
                        //IF SAVED CLEAR EDITXT
                        nameEditTxt.setText("");
                        grpTxt.setText("");
                        descTxt.setText("");
                        linkTxt.setText("");
                        downloadUrl=null;



                        adapter=new MyAdapter(MainActivity.this,helper.retrieve());
                        rv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }
                }else
                {
                    Toast.makeText(MainActivity.this, "Name Must Not Be Empty", Toast.LENGTH_SHORT).show();
                }

            }
        });

        d.show();
    }



}
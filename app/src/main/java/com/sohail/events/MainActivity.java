package com.sohail.events;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
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
public class MainActivity extends AppCompatActivity implements OnFirebaseDataChanged {


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
    private Drawer result = null;
    private AccountHeader header= null;




    static boolean calledAlready=false;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final IProfile profile=new ProfileDrawerItem().withName("GROUPS").withIcon(R.drawable.e).withIdentifier(8);
        header=new AccountHeaderBuilder().withActivity(this).withHeaderBackground(R.drawable.header)
                .addProfiles(profile).withSavedInstance(savedInstanceState)
                .build();



        new DrawerBuilder().withActivity(this).withAccountHeader(header).build();
        final PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Groups");
        SecondaryDrawerItem item2 = new SecondaryDrawerItem().withIdentifier(4).withName("Settings");
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withIdentifier(2).withName("IT"),
                        new PrimaryDrawerItem().withIdentifier(3).withName("CSE"),
                        new PrimaryDrawerItem().withIdentifier(5).withName("ECELL"),
                        new PrimaryDrawerItem().withIdentifier(6).withName("ROBOTICS"),
                        new PrimaryDrawerItem().withIdentifier(7).withName("CIVIL"),
                        new PrimaryDrawerItem().withIdentifier(10).withName("MECHANICAL"),
                        new PrimaryDrawerItem().withIdentifier(11).withName("EED"),
                        new PrimaryDrawerItem().withIdentifier(12).withName("ECE"),

                        new DividerDrawerItem(),
                        item2,
                        new SecondaryDrawerItem().withName("About Me").withIdentifier(13),
                        new SecondaryDrawerItem().withName("Logout").withIdentifier(9)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        if(drawerItem.getIdentifier()==2){
                            Intent intent=new Intent(MainActivity.this,ACM.class);
                            String ACM="IT";
                            intent.putExtra("GROUP_NAME",ACM);
                            startActivity(intent);
                        }else if(drawerItem.getIdentifier()==3){
                            Intent intent=new Intent(MainActivity.this,ACM.class);
                            String ACM="CSE";
                            intent.putExtra("GROUP_NAME",ACM);
                            startActivity(intent);
                        }else if(drawerItem.getIdentifier()==4) {
                            Intent intent = new Intent(MainActivity.this, Settings.class);
                            startActivity(intent);
                        }else if(drawerItem.getIdentifier()==5) {
                            Intent intent = new Intent(MainActivity.this, ACM.class);
                            String ACM = "ECELL";
                            intent.putExtra("GROUP_NAME", ACM);
                            startActivity(intent);
                        }else if(drawerItem.getIdentifier()==6) {
                            Intent intent = new Intent(MainActivity.this, ACM.class);
                            String ACM = "ROBOTICS";
                            intent.putExtra("GROUP_NAME", ACM);
                            startActivity(intent);
                        }else if(drawerItem.getIdentifier()==7) {
                            Intent intent = new Intent(MainActivity.this, ACM.class);
                            String ACM = "CIVIL";
                            intent.putExtra("GROUP_NAME", ACM);
                            startActivity(intent);
                        }else if(drawerItem.getIdentifier()==9) {
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        }else if(drawerItem.getIdentifier()==10) {
                            Intent intent = new Intent(MainActivity.this, ACM.class);
                            String ACM = "MECHANICAL";
                            intent.putExtra("GROUP_NAME", ACM);
                            startActivity(intent);
                        }else if(drawerItem.getIdentifier()==11) {
                            Intent intent = new Intent(MainActivity.this, ACM.class);
                            String ACM = "EED";
                            intent.putExtra("GROUP_NAME", ACM);
                            startActivity(intent);
                        }else if(drawerItem.getIdentifier()==12) {
                            Intent intent = new Intent(MainActivity.this, ACM.class);
                            String ACM = "ECE";
                            intent.putExtra("GROUP_NAME", ACM);
                            startActivity(intent);
                        }else if(drawerItem.getIdentifier()==13) {
                            Intent intent = new Intent(MainActivity.this, AboutMe.class);
                            startActivity(intent);
                        }
                        return true;
                    }
                })
                .build();



        spinner = (ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.VISIBLE);


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
        helper=new FirebaseHelper(db,this);



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


        if(!isNetworkStatusAvialable (getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "Check your Internet Connection", Toast.LENGTH_LONG).show();
        }


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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode& 0xffff) == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();

            final ProgressDialog pd=new ProgressDialog(MainActivity.this);
            StorageReference photoRef=mEventPhotoReference.child(selectedImageUri.getLastPathSegment());


            photoRef.putFile(selectedImageUri).addOnProgressListener(this, new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    pd.setMessage("uploading "+progress+" %");
                    pd.show();
                }
            })
                    .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // When the image has successfully uploaded, we get its download URL
                            downloadUrl = taskSnapshot.getDownloadUrl();
                            pd.hide();
                            Toast.makeText(MainActivity.this,"Photo selected successfully",Toast.LENGTH_LONG).show();
                        }

                    }).addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,"there was a problem uploading photo",Toast.LENGTH_LONG).show();
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


                    s.setName(name);
                    s.setPropellant(propellant);
                    s.setDescription(desc);
                    s.setLink(link);
                    s.setImageUrl(downloadUrl.toString());
                    s.setTimestamp(ts);

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


    @Override
    public void dataChanged() {
        adapter.notifyDataSetChanged();
        spinner.setVisibility(View.GONE);
    }


    public static boolean isNetworkStatusAvialable (Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if(netInfos != null)
                if(netInfos.isConnected())
                    return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

}
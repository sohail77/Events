package com.sohail.events;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.sohail.events.m_Model.Spacecraft;

public class DetailActivity extends AppCompatActivity {

    TextView descTxt, propTxt,linkTxt;
   ImageView img;
    String imgUrl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MultiDex.install(this);
        final FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        frameLayout.getBackground().setAlpha(0);

        final FloatingActionsMenu fab = (FloatingActionsMenu) findViewById(R.id.fab);
        FloatingActionButton regFab=(FloatingActionButton)findViewById(R.id.regFab);
        FloatingActionButton RegisterFab=(FloatingActionButton)findViewById(R.id.RegisterFab);

        CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);

        Spacecraft s=new Spacecraft();

        descTxt= (TextView) findViewById(R.id.descDetailtxt);
        propTxt = (TextView) findViewById(R.id.propellantDetailTxt);
        linkTxt=(TextView) findViewById(R.id.linkDetailTxt);
        descTxt.setTextSize(5 * getResources().getDisplayMetrics().density);
        linkTxt.setTextSize(3 * getResources().getDisplayMetrics().density);
        img=(ImageView)findViewById(R.id.EventImg);


        //GET INTENT
        Intent i=this.getIntent();


        //RECEIVE DATA
        final String name=i.getExtras().getString("NAME_KEY");
        String desc=i.getExtras().getString("DESC_KEY");
       String propellant=i.getExtras().getString("PROP_KEY");
        String link=i.getExtras().getString("LINK_KEY");
        imgUrl=i.getExtras().getString("IMAGE_KEY");


        collapsingToolbarLayout.setTitle(name);


        descTxt.setText(desc);
        propTxt.setText(propellant);
        linkTxt.setText(link);
        Glide.with(this).load(imgUrl).dontTransform().into(img);

        linkTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url= linkTxt.getText().toString();
                Intent u=new Intent(Intent.ACTION_VIEW);
                u.setData(Uri.parse(url));
                startActivity(u);
            }
        });

        fab.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                frameLayout.getBackground().setAlpha(240);
                frameLayout.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        fab.collapse();
                        return true;
                    }
                });
            }

            @Override
            public void onMenuCollapsed() {
                frameLayout.getBackground().setAlpha(0);
                frameLayout.setOnTouchListener(null);
           }
        });

        RegisterFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailActivity.this, RegisterActivity.class);

                String eventName = name;
                i.putExtra("EventName" , eventName);

                startActivity(i);

            }
        });

        regFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(DetailActivity.this,RegistrationViewer.class);
                String EventName=name;
                i.putExtra("EventName",EventName);
                startActivity(i);

            }
        });

    }

}
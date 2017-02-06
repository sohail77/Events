package com.sohail.events;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sohail.events.m_Model.Spacecraft;

public class DetailActivity extends AppCompatActivity {

    TextView nameTxt,descTxt, propTxt,linkTxt;
   ImageView img;
    String imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);

        Spacecraft s=new Spacecraft();

        nameTxt = (TextView) findViewById(R.id.nameDetailTxt);
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


        //BIND DATA
        //img.setImageBitmap(bmp);
//        nameTxt.setText(name);
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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailActivity.this, RegisterActivity.class);

                String eventName = name;
                i.putExtra("EventName" , eventName);

                startActivity(i);

            }
        });
    }

}
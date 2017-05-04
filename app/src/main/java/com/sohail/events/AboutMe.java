package com.sohail.events;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class AboutMe extends AppCompatActivity {


    ImageButton fb,ldin,gmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fb=(ImageButton)findViewById(R.id.fb);
        ldin=(ImageButton)findViewById(R.id.ldin);
        gmail=(ImageButton)findViewById(R.id.gmail);


        final Uri uri=Uri.parse("https://www.facebook.com/mohd.sohail.50951");
        final Uri uri1=Uri.parse("https://www.linkedin.com/in/mohd-sohail-ahmed-651039130/");

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);

            }
        });

        ldin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW,uri1);
                startActivity(intent);
            }
        });


        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/html");
                intent.putExtra(Intent.EXTRA_EMAIL, "sohail778899@gmail.com");

                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });


    }
}

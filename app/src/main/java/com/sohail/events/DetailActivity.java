package com.sohail.events;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView nameTxt,descTxt, propTxt,linkTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        nameTxt = (TextView) findViewById(R.id.nameDetailTxt);
        descTxt= (TextView) findViewById(R.id.descDetailTxt);
        propTxt = (TextView) findViewById(R.id.propellantDetailTxt);
        linkTxt=(TextView) findViewById(R.id.linkDetailTxt);
        descTxt.setTextSize(5 * getResources().getDisplayMetrics().density);
        linkTxt.setTextSize(3 * getResources().getDisplayMetrics().density);

        //GET INTENT
        Intent i=this.getIntent();

        //RECEIVE DATA
        String name=i.getExtras().getString("NAME_KEY");
        String desc=i.getExtras().getString("DESC_KEY");
        String propellant=i.getExtras().getString("PROP_KEY");
        String link=i.getExtras().getString("LINK_KEY");

        //BIND DATA
        nameTxt.setText(name);
        descTxt.setText(desc);
        propTxt.setText(propellant);
        linkTxt.setText(link);

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
                startActivity(i);

            }
        });
    }

}
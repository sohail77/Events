package com.sohail.events;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    EditText nameReg,phoneReg,branchReg,yearReg,eventReg;
    Button regBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameReg = (EditText)findViewById(R.id.nameReg);
        phoneReg = (EditText)findViewById(R.id.phoneReg);
        branchReg = (EditText)findViewById(R.id.branchReg);
        yearReg = (EditText)findViewById(R.id.yearReg);
        eventReg = (EditText)findViewById(R.id.eventReg);
        regBtn = (Button) findViewById(R.id.regBtn);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameReg.setText("");
                phoneReg.setText("");
                branchReg.setText("");
                yearReg.setText("");
                eventReg.setText("");
            }
        });
    }
}

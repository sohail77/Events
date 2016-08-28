package com.sohail.events;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {

    EditText nameReg, phoneReg, branchReg, yearReg, eventReg;
    Button regBtn;


    public static final MediaType FORM_DATA_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    //URL derived from form URL
    public static final String URL = "https://docs.google.com/forms/d/e/1FAIpQLSfSfBoayA_UNznP_0sm-RuY107iqxa7ZMEBd3XDMq2V_eRx2Q/formResponse";
    //input element ids found from the live form page
    public static final String NAME_KEY = "entry.1265098746";
    public static final String PHONE_KEY = "entry.1597085365";
    public static final String EVENT_KEY = "entry.1199583429";
    public static final String BRANCH_KEY = "entry.1804140365";
    public static final String YEAR_KEY = "entry.1910862652";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameReg = (EditText) findViewById(R.id.nameReg);
        phoneReg = (EditText) findViewById(R.id.phoneReg);
        branchReg = (EditText) findViewById(R.id.branchReg);
        yearReg = (EditText) findViewById(R.id.yearReg);
        eventReg = (EditText) findViewById(R.id.eventReg);
        regBtn = (Button) findViewById(R.id.regBtn);


        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                PostDataTask postDataTask = new PostDataTask();

                //execute asynctask
                postDataTask.execute(URL, nameReg.getText().toString(),
                        phoneReg.getText().toString(),
                        eventReg.getText().toString(),
                        branchReg.getText().toString(),
                        yearReg.getText().toString());
                nameReg.setText("");
                phoneReg.setText("");
                eventReg.setText("");
                branchReg.setText("");
                yearReg.setText("");
            }
        });
    }


private class PostDataTask extends AsyncTask<String, Void, Boolean> {

    @Override
    protected Boolean doInBackground(String... contactData) {
        Boolean result = true;
        String url = contactData[0];
        String name = contactData[1];
        String phoneno = contactData[2];
        String event = contactData[3];
        String branch = contactData[4];
        String year = contactData[5];

        String postBody = "";


        try {
            //all values must be URL encoded to make sure that special characters like & | ",etc.
            //do not cause problems
            postBody = NAME_KEY + "=" + URLEncoder.encode(name, "UTF-8") +
                    "&" + PHONE_KEY + "=" + URLEncoder.encode(phoneno, "UTF-8") +
                    "&" + EVENT_KEY + "=" + URLEncoder.encode(event, "UTF-8") +
                    "&" + BRANCH_KEY + "=" + URLEncoder.encode(branch, "UTF-8") +
                    "&" + YEAR_KEY + "=" + URLEncoder.encode(year, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            result = false;
        }
        try {
            //Create OkHttpClient for sending request
            OkHttpClient client = new OkHttpClient();
            //Create the request body with the help of Media Type
            RequestBody body = RequestBody.create(FORM_DATA_TYPE, postBody);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            //Send the request
            okhttp3.Response response = client.newCall(request).execute();
        } catch (IOException exception) {
            result = false;
        }
        return result;
    }
}

    protected void onPostExecute(Boolean result){
        //Print Success or failure message accordingly
        Toast.makeText(this,result?"Message successfully sent!":"There was some error in sending message. Please try again after some time.",Toast.LENGTH_LONG).show();
    }

}


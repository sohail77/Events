package com.sohail.events;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.sohail.events.m_UI.RegistrationAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationViewer extends AppCompatActivity {

    private static final String BASE_URL="http://gsx2json.com";
    private static final String TAG=RegistrationViewer.class.getSimpleName();
    private RegistrationAdapter adapter;
    public String EventName;
    List<Row> rows;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_viewer);

        final RecyclerView recyclerView=(RecyclerView)findViewById(R.id.studentRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Bundle eventText=getIntent().getExtras();
        EventName = eventText.getString("EventName");


        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build();

        RegistrationApi apiService=retrofit.create(RegistrationApi.class);

        Call<Registration> call=apiService.getRows(EventName);
        call.enqueue(new Callback<Registration>() {
            @Override
            public void onResponse(Call<Registration> call, Response<Registration> response) {
                int statusCode=response.code();


                    rows = response.body().getRows();
                    adapter = new RegistrationAdapter(rows);
                    recyclerView.setAdapter(adapter);
                    //recyclerView.setAdapter(new RegistrationAdapter(rows, R.layout.registration_item_view, getApplicationContext()));
                    Log.d(TAG, "no of rows recieved " + rows.size());

            }

            @Override
            public void onFailure(Call<Registration> call, Throwable t) {
                Log.e(TAG,t.toString());
            }
        });
    }

    public  String getEventName(){
        return EventName;
    }
}

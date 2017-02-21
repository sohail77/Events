package com.sohail.events.m_UI;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sohail.events.R;
import com.sohail.events.RegistrationViewer;
import com.sohail.events.Row;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Objects;

/**
 * Created by SOHAIL on 10/02/17.
 */

public class RegistrationAdapter extends RecyclerView.Adapter<RegistrationAdapter.RegistrationViewHolder> {

    private List<Row> rows;
    private Context context;
    private int rowLayout;
    String phoneclick;



    public RegistrationAdapter(Context context,List<Row> rows) {
        this.context=context;
        this.rows=rows;

    }


    @Override
    public RegistrationAdapter.RegistrationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.registration_item_view, parent, false);
        return new RegistrationViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(final RegistrationViewHolder holder, int position) {




            holder.studentName.setText(rows.get(position).getName());
            holder.studentPhone.setText(String.valueOf(rows.get(position).getPhoneno()));
            phoneclick=rows.get(position).getPhoneno();
            holder.studentBranch.setText(rows.get(position).getBranch());
            holder.studentYear.setText(rows.get(position).getYear());

        holder.studentPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneclick, null));
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return rows.size();
    }

    public class RegistrationViewHolder extends RecyclerView.ViewHolder {

        LinearLayout studentLayout;
        TextView studentName;
        TextView studentPhone;
        TextView studentBranch;
        TextView studentYear;



        public RegistrationViewHolder(View itemView) {
            super(itemView);
            studentLayout=(LinearLayout) itemView.findViewById(R.id.studentLayout);
            studentName=(TextView)itemView.findViewById(R.id.studentName);
            studentPhone=(TextView)itemView.findViewById(R.id.studentPhoneNumber);
            studentBranch=(TextView)itemView.findViewById(R.id.studentBranch);
            studentYear=(TextView)itemView.findViewById(R.id.studentYear);


        }
    }

    public RegistrationAdapter( List<Row> rows, int rowLayout,Context context) {
        this.rows = rows;
        this.rowLayout = rowLayout;
        this.context = context;
    }
}

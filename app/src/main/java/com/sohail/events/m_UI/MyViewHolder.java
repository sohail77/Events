package com.sohail.events.m_UI;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sohail.events.R;

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView nameTxt,grpTxt,descTxt,linkTxt;
    ItemClickListener itemClickListener;

    public MyViewHolder(View itemView) {
        super(itemView);

        nameTxt= (TextView) itemView.findViewById(R.id.nameTxt);
        grpTxt= (TextView) itemView.findViewById(R.id.propellantTxt);
        descTxt= (TextView) itemView.findViewById(R.id.descTxt);
        linkTxt=(TextView) itemView.findViewById(R.id.linktxt);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener=itemClickListener;
    }

    @Override
    public void onClick(View view) {
        this.itemClickListener.onItemClick(this.getLayoutPosition());
    }


}
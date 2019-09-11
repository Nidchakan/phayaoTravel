package com.phayaotown.travel.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.phayaotown.travel.Interface.ItemClickListener;
import com.phayaotown.travel.R;

public class TravelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView travel_name;
    public ImageView travel_image;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public TravelViewHolder(@NonNull View itemView) {
        super(itemView);

        travel_name = (TextView)itemView.findViewById(R.id.txt_place_name_fragment);
        travel_image = (ImageView)itemView.findViewById(R.id.place_pic_fragment);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v,getAdapterPosition(),false);

    }
}

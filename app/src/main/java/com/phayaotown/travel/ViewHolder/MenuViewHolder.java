package com.phayaotown.travel.ViewHolder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.phayaotown.travel.Interface.IRecyclerItemSelectedListener;
import com.phayaotown.travel.Interface.ItemClickListener;
import com.phayaotown.travel.R;
import com.phayaotown.travel.model.Food;
import com.phayaotown.travel.model.Place;

import java.util.ArrayList;
import java.util.List;

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

   public TextView txtMenuName,txtMenuTime;
   public ImageView imageView;
   public CardView card_place;

    Context context;
    List<Food> placeList;
    List<CardView> cardViewList;
    LocalBroadcastManager localBroadcastManager;



  // private ItemClickListener itemClickListener;
    IRecyclerItemSelectedListener itemClickListener;
    public MenuViewHolder(@NonNull View itemView) {
        super(itemView);

        txtMenuName = (TextView) itemView.findViewById(R.id.txt_place_name);
        imageView = (ImageView) itemView.findViewById(R.id.txt_place_pic);
        txtMenuTime = (TextView) itemView.findViewById(R.id.txt_place_hour);
        card_place = (CardView) itemView.findViewById(R.id.card_place);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(IRecyclerItemSelectedListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onItemSelectedListener(v,getAdapterPosition());

    }
}

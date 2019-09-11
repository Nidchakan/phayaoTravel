package com.phayaotown.travel.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.phayaotown.travel.Common.Common;
import com.phayaotown.travel.Interface.IRecyclerItemSelectedListener;
import com.phayaotown.travel.model.Food;
import com.phayaotown.travel.model.Place;
import com.phayaotown.travel.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyPlaceAdapter extends RecyclerView.Adapter<MyPlaceAdapter.MyViewHolder> {

    Context context;
    List<Food> placeList;
    List<CardView> cardViewList;
    LocalBroadcastManager localBroadcastManager;




    public MyPlaceAdapter(Context context, List<Food> placeList) {
        this.context = context;
        this.placeList = placeList;
        cardViewList = new ArrayList<>();
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_place,viewGroup,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

        myViewHolder.txt_place_name.setText(placeList.get(i).getName());
        myViewHolder.txt_place_hour.setText(placeList.get(i).getTime());
        Picasso.get().load(placeList.get(i).getImage_url()).into(myViewHolder.txt_place_open);


        if(!cardViewList.contains(myViewHolder.card_place))
            cardViewList.add(myViewHolder.card_place);


        myViewHolder.setiRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
            @Override
            public void onItemSelectedListener(View view, int pos) {
                //Set white bg for all card not be selected
                for(CardView cardView : cardViewList)
                    cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));

                //set selected bg for only selected item
               // myViewHolder.card_place.setCardBackgroundColor(context.getResources().getColor(android.R.color.holo_orange_dark));

                //send BroadCast to tell Schedule Activity enable Next Button
                Intent intent = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
                intent.putExtra(Common.KEY_PLACE ,placeList.get(pos));
                intent.putExtra(Common.KEY_STEP,1);
                localBroadcastManager.sendBroadcast(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return 0;
    }

    /*@Override
    public int getItemCount() {
        return placeList.size();
    }*/

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_place_name,txt_place_hour;
        ImageView txt_place_open;
        CardView card_place;

        IRecyclerItemSelectedListener iRecyclerItemSelectedListener;

        public void setiRecyclerItemSelectedListener(IRecyclerItemSelectedListener iRecyclerItemSelectedListener){
            this.iRecyclerItemSelectedListener = iRecyclerItemSelectedListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            card_place = (CardView)itemView.findViewById(R.id.card_place);
            txt_place_open = (ImageView)itemView.findViewById(R.id.txt_place_pic);
            txt_place_name = (TextView)itemView.findViewById(R.id.txt_place_name);
            txt_place_hour = (TextView) itemView.findViewById(R.id.txt_place_hour);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            iRecyclerItemSelectedListener.onItemSelectedListener(v,getAdapterPosition());

        }
    }
}

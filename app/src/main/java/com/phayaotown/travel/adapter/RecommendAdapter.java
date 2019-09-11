package com.phayaotown.travel.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.phayaotown.travel.Interface.IRecyclerItemSelectedListener;
import com.phayaotown.travel.model.Trip;
import com.phayaotown.travel.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.MyViewHolder> {

    Context context;
    List<Trip> tripList;
    List<CardView> cardViewList;
    LocalBroadcastManager localBroadcastManager;





    public RecommendAdapter (Context context, List<Trip> tripList) {
        this.context = context;
        this.tripList = tripList;
        cardViewList = new ArrayList<>();
        localBroadcastManager = LocalBroadcastManager.getInstance(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_recommend,viewGroup,false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

        myViewHolder.txt_place_name.setText(tripList.get(i).getName());
        myViewHolder.txt_number_of_place.setText(tripList.get(i).getSight());
        Picasso.get().load(tripList.get(i).getPic()).into(myViewHolder.txt_place_pic);


        if(!cardViewList.contains(myViewHolder.card_recommend))
            cardViewList.add(myViewHolder.card_recommend);


        myViewHolder.setiRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
            @Override
            public void onItemSelectedListener(View view, final int pos) {
                //Set white bg for all card not be selected
                for (CardView cardView : cardViewList)
                    cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));

                //set selected bg for only selected item
                myViewHolder.card_recommend.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));

            }
        });

    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_place_name,txt_number_of_place;
        ImageView txt_place_pic;
        CardView card_recommend;
        MaterialSpinner trip_information;

        IRecyclerItemSelectedListener iRecyclerItemSelectedListener;



        public void setiRecyclerItemSelectedListener(IRecyclerItemSelectedListener iRecyclerItemSelectedListener){
            this.iRecyclerItemSelectedListener = iRecyclerItemSelectedListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            card_recommend = (CardView)itemView.findViewById(R.id.card_recommend);
            txt_place_name = (TextView)itemView.findViewById(R.id.txt_place_name);
            txt_place_pic = (ImageView) itemView.findViewById(R.id.txt_place_pic);
            txt_number_of_place = (TextView) itemView.findViewById(R.id.txt_number_of_place);
            trip_information = (MaterialSpinner) itemView.findViewById(R.id.trip_information);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            iRecyclerItemSelectedListener.onItemSelectedListener(v,getAdapterPosition());

        }

    }

}

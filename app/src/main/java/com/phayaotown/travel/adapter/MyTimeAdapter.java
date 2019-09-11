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
import android.widget.TextView;

import com.phayaotown.travel.Common.Common;
import com.phayaotown.travel.Interface.IRecyclerItemSelectedListener;
import com.phayaotown.travel.model.TimeSlot;
import com.phayaotown.travel.R;

import java.util.ArrayList;
import java.util.List;

public class MyTimeAdapter extends RecyclerView.Adapter<MyTimeAdapter.MyViewHolder> {

    Context context;
    List<TimeSlot> timeSlotList;
    List<CardView> cardViewList;
    LocalBroadcastManager localBroadcastManager;

    public MyTimeAdapter(Context context) {
        this.context = context;
        this.timeSlotList = new ArrayList<>();
        this.localBroadcastManager = LocalBroadcastManager.getInstance(context);
        cardViewList = new ArrayList<>();
    }

    public MyTimeAdapter(Context context, List<TimeSlot> timeSlotList) {
        this.context = context;
        this.timeSlotList = timeSlotList;
        this.localBroadcastManager = LocalBroadcastManager.getInstance(context);
        cardViewList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_time,viewGroup,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        myViewHolder.txt_time_slot.setText(new StringBuilder(Common.convertTimeSlotToString(i)).toString());
        if(timeSlotList.size() == 0){

            myViewHolder.cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));

            myViewHolder.txt_time_slot_description.setText("Open");
            myViewHolder.txt_time_slot_description.setTextColor(context.getResources().getColor(android.R.color.black));
            myViewHolder.txt_time_slot.setTextColor(context.getResources().getColor(android.R.color.black));


        }else {
            for(TimeSlot slotValue : timeSlotList)
            {
                int slot = Integer.parseInt(slotValue.getSlot().toString());

                if(slot == i){
                    myViewHolder.cardView.setTag(Common.DISABLE_TAG);
                    myViewHolder.cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));

                    myViewHolder.txt_time_slot_description.setText("Booked");
                    myViewHolder.txt_time_slot_description.setTextColor(context.getResources().getColor(android.R.color.white));
                    myViewHolder.txt_time_slot.setTextColor(context.getResources().getColor(android.R.color.white));
                }
            }
        }

        if(!cardViewList.contains(myViewHolder.cardView))
            cardViewList.add(myViewHolder.cardView);

        if(!timeSlotList.contains(i))
        {
            myViewHolder.setiRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
                @Override
                public void onItemSelectedListener(View view, int pos) {
                    for (CardView cardView1 : cardViewList) {
                        if (cardView1.getTag() == null)
                            cardView1.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));
                    }

                    myViewHolder.cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.holo_orange_dark));

                    Intent intent = new Intent(Common.KEY_ENABLE_BUTTON_NEXT);
                    intent.putExtra(Common.KEY_TIME_SLOT,i);
                    intent.putExtra(Common.KEY_STEP,2);
                    localBroadcastManager.sendBroadcast(intent);

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return Common.TIME_TOTAL;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txt_time_slot,txt_time_slot_description;
        CardView cardView;

        IRecyclerItemSelectedListener iRecyclerItemSelectedListener;

        public void setiRecyclerItemSelectedListener(IRecyclerItemSelectedListener iRecyclerItemSelectedListener){
            this.iRecyclerItemSelectedListener = iRecyclerItemSelectedListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_time_slot = (TextView) itemView.findViewById(R.id.txt_time);
            txt_time_slot_description = (TextView) itemView.findViewById(R.id.txt_description);
            cardView = (CardView) itemView.findViewById(R.id.card_time);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            iRecyclerItemSelectedListener.onItemSelectedListener(v,getAdapterPosition());
        }
    }
}

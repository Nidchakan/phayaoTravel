package com.phayaotown.travel.Fragment.Detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.phayaotown.travel.Fragment.MapFragment;
import com.phayaotown.travel.R;
import com.phayaotown.travel.model.Food;
import com.phayaotown.travel.model.Travel;
import com.squareup.picasso.Picasso;

public class Travel_Detail extends AppCompatActivity {

    TextView name,history,time,information,place,event;
    ImageView img_travel;

    FloatingActionButton near;

    String travelId = "";
    String showAllId = "";

    FirebaseDatabase database;
    DatabaseReference travel;
    DatabaseReference showAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acrivity_travel_detail);

        //Firebase
        database = FirebaseDatabase.getInstance();
        travel = database.getReference("Travel");

        near = (FloatingActionButton)findViewById(R.id.fab_navigate);
        near.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), MapFragment.class));
            }
        });


        name = (TextView)findViewById(R.id.travel_name);
        time = (TextView)findViewById(R.id.travel_time);
        information = (TextView)findViewById(R.id.travel_information);
        history = (TextView)findViewById(R.id.travel_history);
        event = (TextView)findViewById(R.id.travel_event);
        place = (TextView)findViewById(R.id.travel_place);

        img_travel = (ImageView)findViewById(R.id.img_travel);

        if(getIntent() != null) {
            travelId = getIntent().getStringExtra("TravelId");
        }
        if (!travelId.isEmpty()){
            getDetailTravel(travelId);
        }


    }

    private void getDetailTravel(final String travelId) {

        travel.child(travelId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Travel travel = dataSnapshot.getValue(Travel.class);

                //Set Image
                Picasso.get().load(travel.getImage_url()).into(img_travel);

                name.setText(travel.getName());
                event.setText(travel.getEvent());
                time.setText(travel.getTime());
                information.setText(travel.getInformation());
                history.setText(travel.getHistory());
                place.setText(travel.getPlace());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

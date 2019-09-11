package com.phayaotown.travel.Fragment.Detail;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.phayaotown.travel.Fragment.MapFragment;
import com.phayaotown.travel.Interface.IFirebaseLoadDone;
import com.phayaotown.travel.R;
import com.phayaotown.travel.model.Rest;
import com.phayaotown.travel.model.Travel;
import com.phayaotown.travel.model.Type_room;
import com.squareup.picasso.Picasso;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class Rest_Detail extends AppCompatActivity implements IFirebaseLoadDone {

    TextView name,place,checkIn,time,phone,trname,trCost,trinformation;
    SearchableSpinner typeRoomsearch;
    ImageView img_rest,trImage;
    FloatingActionButton near;

    String restId = "";

    FirebaseDatabase database;
    DatabaseReference rest;
    DatabaseReference showAll;
    DatabaseReference typeref;

    IFirebaseLoadDone iFirebaseLoadDone;
    List<Type_room> typeRooms ;
    BottomSheetDialog bottomSheetDialog;


    boolean isFirstTimeClick = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_detail);

        //init dialog
        bottomSheetDialog = new BottomSheetDialog(this);
        final View bottom_sheet_dialog = getLayoutInflater().inflate(R.layout.layout_typeroom,null);
        trname = (TextView)bottom_sheet_dialog.findViewById(R.id.typeRoom_name);
        trCost = (TextView)bottom_sheet_dialog.findViewById(R.id.typeRoom_cost);
        trinformation = (TextView)bottom_sheet_dialog.findViewById(R.id.typeRoom_information);

        trImage = (ImageView)bottom_sheet_dialog.findViewById(R.id.typeRoom_image);

        //set Content
        bottomSheetDialog.setContentView(bottom_sheet_dialog);


        //Firebase
        database = FirebaseDatabase.getInstance();
        rest = database.getReference("Rest");


        near = (FloatingActionButton)findViewById(R.id.fab_navigate);

        near.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), MapFragment.class));
            }
        });

        name = (TextView)findViewById(R.id.rest_name);
        place = (TextView)findViewById(R.id.rest_place);
        checkIn = (TextView)findViewById(R.id.rest_checkIn);
        time = (TextView)findViewById(R.id.rest_time);
        phone = (TextView)findViewById(R.id.rest_phone);
        typeRoomsearch = (SearchableSpinner) findViewById(R.id.type_room);

        img_rest = (ImageView) findViewById(R.id.img_rest);

        if(getIntent() != null) {
            restId = getIntent().getStringExtra("RestId");
        }
        if (!restId.isEmpty()){
            getDetailRest(restId);
        }

        typeref = database.getReference("Rest").child(restId).child("Type_room");
        iFirebaseLoadDone = this;
        //get data
        typeref.addListenerForSingleValueEvent(new ValueEventListener() {
            List<Type_room> typeRooms = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot typeref : dataSnapshot.getChildren()){
                    typeRooms.add(typeref.getValue(Type_room.class));
                }
                iFirebaseLoadDone.onFirebaseLoadSuccess(typeRooms);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                iFirebaseLoadDone.onFirebaseLoadFailed(databaseError.getMessage());
            }
        });

        typeRoomsearch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {

                if(!isFirstTimeClick){
                    Type_room typeRoom = typeRooms.get(i);
                    trname.setText(typeRoom.getName());
                    trCost.setText(typeRoom.getCost());
                    trinformation.setText(typeRoom.getInformation());
                    Picasso.get().load(typeRoom.getImage_url()).into(trImage);

                    bottomSheetDialog.show();
                }
                else
                    isFirstTimeClick = false;
            }

                /*typeref = database.getReference("Rest").child(restId).child("Type_room");

                typeref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(!isFirstTimeClick){
                            Type_room type_room = dataSnapshot.getValue(Type_room.class);

                            tr_name.setText(type_room.getName());
                            tr_cost.setText(type_room.getCost());
                            tr_information.setText(type_room.getInformation());
                            Picasso.get().load(type_room.getImage_url()).into(tr_image);

                            bottomSheetDialog.show();
                        }else
                            isFirstTimeClick = false;
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                /*if(!isFirstTimeClick){
                    Type_room typeRoom = typeRooms.get(i);
                    tr_name.setText(typeRoom.getName());
                    tr_cost.setText(typeRoom.getCost());
                    tr_information.setText(typeRoom.getInformation());
                    Picasso.get().load(typeRoom.getImage_url()).into(tr_image);

                    bottomSheetDialog.show();
                }
                else
                    isFirstTimeClick = false;*/

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void getDetailRest(String restId) {
        rest.child(restId).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Rest rest = dataSnapshot.getValue(Rest.class);

            //Set Image
            Picasso.get().load(rest.getImage_url()).into(img_rest);

            name.setText(rest.getName());
            checkIn.setText(rest.getCheck_In());
            time.setText(rest.getTime());
            phone.setText(rest.getPhone());
            place.setText(rest.getPlace());

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
    }

    @Override
    public void onFirebaseLoadSuccess(List<Type_room> typeRoomList) {
        typeRooms = typeRoomList ;
        //get all name
        List<String> name_list = new ArrayList<>();
        for(Type_room typeRoom : typeRoomList)
            name_list.add(typeRoom.getName());
        //Create adapter and set Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,name_list);
        typeRoomsearch.setAdapter(adapter);
    }

    @Override
    public void onFirebaseLoadFailed(String message) {

    }
}

package com.phayaotown.travel.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.phayaotown.travel.R;
import com.phayaotown.travel.model.Pin;



public class MapFragment extends AppCompatActivity implements OnMapReadyCallback {

    private Marker marker;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference pinRef = database.getReference();
    //MaterialSearchBar materialSearchBar;
    FloatingActionButton fab;

    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 0;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private final static int LOCATION_PERMISSION_REQUEST = 1001;

    private Location mLastLocation;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private int indexFirst,currentIndex;
    private int count = 0;




    String pinId = "";
    int i = 1;

    private GoogleMap mMap;
    private DatabaseReference mPins;


    public MapFragment() {

    }

    static MapFragment instance;

    public static MapFragment getInstance() {

        if (instance == null)
            instance = new MapFragment();

        return instance;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mPins= FirebaseDatabase.getInstance().getReference("Pin");
        mPins.push().setValue(marker);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        //getLastLocation();

        fab = (FloatingActionButton)findViewById(R.id.fab_my_location);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = 0;
                getLastLocation();
               // i = 0;
               // Toast.makeText(getBaseContext(),"Current location",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    currentLocation = location;
                    Toast.makeText(getApplicationContext(),"คุณอยู่ที่นี่",Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment)getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    supportMapFragment.getMapAsync(MapFragment.this);

                }
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        /*if(i == 0) {

            LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                    .title("คุณอยู่ที่นี่");
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            googleMap.addMarker(markerOptions);

        }else if (i == 1);*/
        mMap = googleMap;
        mPins.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()) {
                    Pin pin = s.getValue(Pin.class);
                    LatLng location = new LatLng(pin.getLatitude(), pin.getLongtitude());
                    MarkerOptions markerOptions = new MarkerOptions().position(location)
                            .title(pin.getName());
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(location));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location,15));
                    mMap.addMarker(markerOptions);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Polyline line = googleMap.addPolyline(new PolylineOptions()
                .add(new LatLng(19.169844,99.8987562), new LatLng(19.172462, 99.899433))
                .width(5)
                .color(Color.RED));

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE :
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getLastLocation();
                }
                break;
        }
    }


    private void displayLocation() {

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            requestRuntimePermission();
        }
        else {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if(mLastLocation != null){
                double latitude = mLastLocation.getLatitude();
                double longtitude = mLastLocation.getLongitude();

                //add Marker and Move camera

                LatLng mylocation = new LatLng(latitude,longtitude);
                mMap.addMarker(new MarkerOptions().position(mylocation).title("You Here"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(mylocation));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
            }
            else {
                Toast.makeText(this,"Couldn't get the locatin",Toast.LENGTH_SHORT).show();
            }
        }

    }



    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if(resultCode != ConnectionResult.SUCCESS){
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode)){
                GooglePlayServicesUtil.getErrorDialog(resultCode,this,PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            else {
                Toast.makeText(this,"This devices is not support",Toast.LENGTH_SHORT).show();
                finish();
            }
            return false;

        }
        return true;
    }

    private void requestRuntimePermission() {
        ActivityCompat.requestPermissions(this,new String[]
                {
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                },LOCATION_PERMISSION_REQUEST);
    }


    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }


}




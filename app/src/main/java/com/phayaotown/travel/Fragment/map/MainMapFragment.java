package com.phayaotown.travel.Fragment.map;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.constant.Unit;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.phayaotown.travel.Fragment.DetailFragment;
import com.phayaotown.travel.R;
import com.phayaotown.travel.Utils;
import com.phayaotown.travel.View.CustomAutoCompleteTextView;
import com.phayaotown.travel.View.SlidingUpPanelLayout;
import com.phayaotown.travel.base.BaseFragment;
import com.phayaotown.travel.model.DataModel;
import com.phayaotown.travel.model.ShowAll;
import com.phayaotown.travel.utils.DialogUtil;
import com.phayaotown.travel.utils.ImageLoader;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.Context.LOCATION_SERVICE;
import static android.support.v4.app.NotificationCompat.PRIORITY_MAX;

public class MainMapFragment extends BaseFragment<MainMapFragmentInterface.Presenter>
        implements MainMapFragmentInterface.View, GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback, DirectionCallback, View.OnClickListener {

    @BindView(R.id.iv_info)
    AppCompatImageView ivInfo;
    @BindView(R.id.tv_title_info)
    AppCompatTextView tvTitleInfo;
    @BindView(R.id.tv_detail_info)
    AppCompatTextView tvDetailInfo;
    @BindView(R.id.card_info)
    CardView cardInfo;
    @BindView(R.id.container_card_two)
    View containerCardTwo;
    @BindView(R.id.container_card_one)
    View containerCardOne;
    @BindView(R.id.iv_info_two)
    AppCompatImageView ivInfoTwo;
    @BindView(R.id.tv_title_info_two)
    AppCompatTextView tvTitleInfoTwo;
    @BindView(R.id.tv_detail_info_two)
    AppCompatTextView tvDetailInfoTwo;
    @BindView(R.id.btn_switch)
    View btnSwitch;
    @BindView(R.id.tv_start)
    AppCompatTextView tvStartRote;
    @BindView(R.id.tv_destination)
    AppCompatTextView tvDestinationRote;
    @BindView(R.id.tv_distance)
    AppCompatTextView tvDistanceRote;
    @BindView(R.id.tv_duration)
    AppCompatTextView tvDurationRote;
    @BindView(R.id.btn_directions)
    View btnDirectionRoute;
    @BindView(R.id.container_info_route)
    View containerInfoRoute;
    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout slidingUpPanelLayout;
    @BindView(R.id.fab_my_location)
    FloatingActionButton fabMyLocation;
    @BindView(R.id.autocomplete_view)
    CustomAutoCompleteTextView autoCompleteTextView;
    @BindView(R.id.btn_close_info)
    View btnClose;

    private GoogleMap googleMap;
    private static final int LOCATION_REQUEST_CODE = 2001;
    private static final int LOCATION_NOTIFICATION_ID = 2000;
    private DataModel model;
    private int indexFirst,currentIndex;
    private int count = 0;
    private LatLng origin;
    private LatLng destination;
    private Polyline polyline;
    private List<String> listSearch = new ArrayList<>();
    private boolean isKeyboardShow;
    private SupportMapFragment mapFragment;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Pin");

    public static MainMapFragment newInstance(){
        MainMapFragment fragment = new MainMapFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int layoutToInflate() { return R.layout.fragment_map; }

    @Override
    public MainMapFragmentInterface.Presenter createPresenter() {
        return MainMapFragmentPresenter.create();
    }

    @Override
    protected void startView() {
        checkMapPermissionAndStartMap();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED ||
                slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED){
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        }
    }

    @Override
    protected void stopView() {

    }

    @Override
    protected void bindView(View view) {

    }

    @Override
    protected void setupInstance() {

    }

    @Override
    protected void setupView() {

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        slidingUpPanelLayout.setFadeOnClickListener(v -> slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED));
        slidingUpPanelLayout.addPanelSlideListener(panelSlideListener);
        containerCardTwo.setOnClickListener(this);
        containerCardOne.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        autoCompleteTextView.setListenerKeyDown(() -> {
            autoCompleteTextView.setFocusable(false);
            autoCompleteTextView.setFocusableInTouchMode(false);
            isKeyboardShow = false;
        });

        autoCompleteTextView.setOnEditorActionListener((textView,id,keyEvent) -> {
            if (id == EditorInfo.IME_ACTION_SEARCH) {
                autoCompleteTextView.showDropDown();
                return true;
            }
            return false;
        });
        autoCompleteTextView.setOnItemClickListener((adapterView,view,i,l) -> {
            if (!listSearch.isEmpty()){
                String search = adapterView.getItemAtPosition(i).toString();
                for (int i1 = 0;i1 < listSearch.size();i1++){
                    if (listSearch.get(i1).equals(search)){
                        moveCameraToLocationSearch(i1);
                    }
                }
            }
            clearFocusView();
        });
        autoCompleteTextView.setOnTouchListener((view,motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                view.setFocusable(true);
                view.setFocusableInTouchMode(true);
                view.requestFocus();
                int clickPosition = autoCompleteTextView.getOffsetForPosition(motionEvent.getX(),motionEvent.getY());
                isKeyboardShow = true;
                if(count == 1) {
                    new Handler().postDelayed(this::closeCardOne, 500);
                    Utils.showKeyboard(this.getActivity());
                }else {
                    Utils.showKeyboard(this.getActivity());
                }
            }
            return true;
        });
        btnSwitch.setOnClickListener(view -> {
            int oldIndexFirst = indexFirst;
            indexFirst = oldIndexFirst;
            currentIndex = oldIndexFirst;
            showInfoLocation(model.getDetailModels().get(indexFirst));
            showInfoLocationTwo(model.getDetailModels().get(currentIndex),false);
            tvStartRote.setText(model.getDetailModels().get(indexFirst).getName());
            tvDestinationRote.setText(model.getDetailModels().get(currentIndex).getName());
        });
        fabMyLocation.setOnClickListener(view -> moveCameraTomyLocation());
    }

    @Override
    protected void initialize() {

    }

    private void checkMapPermissionAndStartMap(){
        Dexter.withActivity(getActivity())
                .withPermissions(ACCESS_FINE_LOCATION,ACCESS_COARSE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if(!hasDeniedPermission(report)){
                            startMap();
                        }else {
                            DialogUtil.getInstance().showDialog(getContext(),R.string.permission_denied,
                                    R.string.title_permission_rationale,(dialogInterface,i) -> {
                                        Intent intent = new Intent();
                                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package",getContext().getPackageName(),null);
                                                intent.setData(uri);
                                                startActivity(intent);
                                    });
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }

                    private boolean hasDeniedPermission(MultiplePermissionsReport report){
                        List<PermissionDeniedResponse> deniedPermission = report.getDeniedPermissionResponses();
                        return deniedPermission != null && deniedPermission.size() > 0;
                    }
                }).check();
    }
    private void startMap(){
        if(isLocationEnable()) mapFragment.getMapAsync(this);
        else createLocationNotification(this.getContext());
    }


    @Override
    protected void saveInstanceState(Bundle outState) {

    }

    @Override
    public void restoreView(Bundle savedInstanceState) {

    }

    private void clearFocusView(){
        Utils.hideKeyboard(this.getActivity());
        autoCompleteTextView.setFocusable(false);
        autoCompleteTextView.setFocusableInTouchMode(false);
        isKeyboardShow = false;
    }

    private void moveCameraToLocationSearch(int index){
        double[] location = model.getDetailModels().get(index).getLocation();
        if(isLocationEnable() && location != null){
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location[0], location[1]), 16));
        }
    }

    private SlidingUpPanelLayout.PanelSlideListener panelSlideListener = new SlidingUpPanelLayout.PanelSlideListener(){
        @Override
        public void onPanelSlide(View panel, float slideOffset) {

        }

        @Override
        public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
            if(slidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED && polyline == null){
                btnSwitch.setVisibility(View.GONE);
                containerCardTwo.setVisibility(View.GONE);
                containerInfoRoute.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        configGoogleMap();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
       if (isKeyboardShow){
           clearFocusView();
       }else {
           count++;
           int index = Integer.parseInt(String.valueOf(marker.getTag()));
           if (index == indexFirst || index == currentIndex) return true;
           if(count == 1){
               indexFirst = index;
               showInfoLocation(model.getDetailModels().get(indexFirst));
               slidingUpPanelLayout.setPanelHeight(containerCardOne.getHeight());
               showInfoLocation(model.getDetailModels().get(indexFirst));
           }else {
               currentIndex = index;
               slidingUpPanelLayout.setEnabled(true);
               showInfoLocationTwo(model.getDetailModels().get(currentIndex),true);
           }
       }
       return true;
    }

    private void showInfoLocation(ShowAll showAll) {
        ImageLoader.loadImageUrl(showAll.getImage_url(),ivInfoTwo);
        String name = showAll.getName();
        String time = showAll.getTime();

        tvTitleInfo.setText(name);
        tvDetailInfo.setText(time);
    }


    private void showInfoLocationTwo(ShowAll showAll, boolean isPolyline) {
        ImageLoader.loadImageUrl(showAll.getImage_url(),ivInfoTwo);
        String name = showAll.getName();
        String time = showAll.getTime();

        tvTitleInfoTwo.setText(name);
        tvDetailInfoTwo.setText(time);
        containerCardTwo.setVisibility(View.VISIBLE);
        btnSwitch.setVisibility(View.VISIBLE);
        containerInfoRoute.setVisibility(View.VISIBLE);
        if(isPolyline){
            if(polyline != null) polyline.remove();
            setPolylineMap();
        }
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if(id == R.id.btn_close_info){
            closeCardOne();
        } else if (id == R.id.container_card_one) {
            navigateToDetails(indexFirst);
        } else if (id == R.id.container_card_two) {
            navigateToDetails(currentIndex);
        }

    }

    private void navigateToDetails(int index) {
        ShowAll data = model.getDetailModels().get(index);
        startActivity(new Intent(this.getContext(), DetailFragment.class).putExtra("Name",Parcels.wrap(ref)));
    }

    private void closeCardOne() {
        if(polyline != null){
            polyline.remove();
            polyline = null;
        }
        count = 0;
        indexFirst = currentIndex = -1;
        slidingUpPanelLayout.setPanelHeight(0);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

    private boolean isLocationEnable() {
        LocationManager locationManager = (LocationManager) this.getActivity().getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void configGoogleMap(){
        googleMap.setTrafficEnabled(true);
        googleMap.setOnMarkerClickListener(this);
        enableMyLocationMap();
        if(model == null) getPresenter().loadJsonFromFile();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    private void enableMyLocationMap(){
        if(ActivityCompat.checkSelfPermission(this.getContext(),ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getContext(),ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

        }

        if(googleMap!= null){
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    private void moveCameraTomyLocation(){
        Location location = getLastKnownLocation();
        if(isLocationEnable() && location != null){
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(location.getLatitude(),location.getLongitude()),16));
        }
    }

    private void createLocationNotification(Context context){
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,LOCATION_REQUEST_CODE,intent,PendingIntent.FLAG_CANCEL_CURRENT);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.ic_settings)
                .setSound(alarmSound)
                .setPriority(PRIORITY_MAX)
                .setVibrate(new long[]{0,400})
                .setColor(ContextCompat.getColor(context,R.color.colorButton))
                .setLights(ContextCompat.getColor(context, R.color.colorPrimaryDark), 1000, 1000)
                .setContentTitle(context.getString(R.string.enable_location_service))
                .setContentText(context.getString(R.string.open_location_settings))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(LOCATION_NOTIFICATION_ID, mBuilder.build());
    }

    private void setupPinToMap(){
        if (googleMap != null) {
            int index = 0;
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (ShowAll sA : model.getDetailModels()) {
                LatLng latlng = new LatLng(sA.getLocation()[0],
                        sA.getLocation()[1]);
                MarkerOptions markerOptions = new MarkerOptions().position(latlng)
                        .icon(BitmapDescriptorFactory.fromResource(getDrawableId(sA.getImage_url())));
                Marker marker = googleMap.addMarker(markerOptions);
                marker.setTag(index);
                builder.include(markerOptions.getPosition());
                index++;
            }
            googleMap.setOnMapLoadedCallback(() -> {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 50));

            });
        }
    }

    private void setPolylineMap() {
        origin = new LatLng(model.getDetailModels().get(indexFirst).getLocation()[0]
                , model.getDetailModels().get(indexFirst).getLocation()[1]);
        destination = new LatLng(model.getDetailModels().get(currentIndex).getLocation()[0]
                , model.getDetailModels().get(currentIndex).getLocation()[1]);
        GoogleDirection.withServerKey(getString(R.string.google_server_key))
                .from(origin)
                .to(destination)
                .avoid(AvoidType.FERRIES)
                .avoid(AvoidType.HIGHWAYS)
                .language(getLanguage())
                .unit(Unit.METRIC)
                .transportMode(TransportMode.DRIVING)
                .optimizeWaypoints(true)
                .alternativeRoute(true)
                .execute(this);
    }


    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {

        if (direction.isOK()) {
            ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0)
                    .getLegList().get(0).getDirectionPoint();
            polyline = googleMap.addPolyline(DirectionConverter.createPolyline(this.getContext(), directionPositionList, 4,
                    ContextCompat.getColor(this.getContext(), R.color.colorPolyline)));
            LatLngBounds.Builder bounds = new LatLngBounds.Builder();
            bounds.include(origin);
            bounds.include(destination);
            String originName = model.getDetailModels().get(indexFirst).getName();
            String destinationName = model.getDetailModels().get(currentIndex).getName();

            tvStartRote.setText(originName);
            tvDestinationRote.setText(destinationName);
            tvDistanceRote.setText(direction.getRouteList().get(0).getLegList().get(0).getDistance().getText());
            tvDurationRote.setText(direction.getRouteList().get(0).getLegList().get(0).getDuration().getText());
            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            int padding = (int) (width * 0.15);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), width, height, padding));
            new Handler().postDelayed(() -> slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED), 300);
        } else {
            showDialogError();
        }

    }

    @Override
    public void onDirectionFailure(Throwable t) {
        showDialogError();
    }





    @Override
    public void loadJsonFailure() {

    }

    @Override
    public void loadJsonSuccess(DatabaseReference database) {

        ref = database;
        setupPinToMap();


    }












}

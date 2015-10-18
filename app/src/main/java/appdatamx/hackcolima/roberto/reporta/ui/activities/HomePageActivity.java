package appdatamx.hackcolima.roberto.reporta.ui.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

import appdatamx.hackcolima.roberto.reporta.R;
import appdatamx.hackcolima.roberto.reporta.model.ComplaintModel;
import appdatamx.hackcolima.roberto.reporta.request.ComplaintsRequest;

public class HomePageActivity extends FragmentActivity implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener{

    private GoogleMap googleMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location location;
    private ComplaintsRequest complaintsRequest;
    private int category = 1;
    private ArrayList<ComplaintModel> clientsModelArrayList;
    private HashMap<Marker, ComplaintModel> hashMap = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ViewGroup rLSecurity = (ViewGroup) findViewById(R.id.rlsecurity);
        ViewGroup rLTrafic = (ViewGroup) findViewById(R.id.rltrafic);
        ViewGroup rLUrban = (ViewGroup) findViewById(R.id.rlurban);
        ViewGroup rLUser = (ViewGroup) findViewById(R.id.rluserinfo);
        TextView titleToolbar = (TextView) findViewById(R.id.titletoolbar);

        complaintsRequest = new ComplaintsRequest(getApplicationContext());

        rLSecurity.setOnClickListener(this);
        rLTrafic.setOnClickListener(this);
        rLUrban.setOnClickListener(this);
        rLUser.setOnClickListener(this);

        titleToolbar.setText("Home");

        googleMap =  ((SupportMapFragment) getSupportFragmentManager().findFragmentById(
                R.id.map)).getMap();

        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Intent intent = new Intent(HomePageActivity.this, NewComplaint.class);

                Bundle args = new Bundle();
                args.putParcelable("latLng", latLng);

                intent.putExtra("bundle", args);
                startActivity(intent);

            }
        });

        getTheComplaints();


    }

    private void getTheComplaints() {
        complaintsRequest.getAllComplainst(new ComplaintsRequest.ComplaintsListener() {
            @Override
            public void onSuccess(ArrayList<ComplaintModel> clientsArrayList) {
                clientsModelArrayList = clientsArrayList;
            }

            @Override
            public void onFaliure(String error) {
                Log.d("Roberto", "error " + error);

            }
        });
    }

    private void setMarkers() {
        MarkerOptions marker;
        googleMap.clear();


        switch (category){
            case 1:
                for (ComplaintModel model : clientsModelArrayList){

                    if(model.getCategory_id() == 1) {

                        if((model.getLatitude() != null) && model.getLongitude() != null) {
                            marker = new MarkerOptions()
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin3))
                                    .position(new LatLng(Double.parseDouble(model.getLatitude()),
                                            Double.parseDouble(model.getLongitude())));
                            Marker m = googleMap.addMarker(marker);
                            hashMap.put(m, model);
                        }
                    }
                }

                break;
            case 2:

                for (ComplaintModel model : clientsModelArrayList){

                    if(model.getCategory_id() == 1) {

                        if((model.getLatitude() != null) && model.getLongitude() != null) {
                            marker = new MarkerOptions()
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin1))
                                    .position(new LatLng(Double.parseDouble(model.getLatitude()),
                                            Double.parseDouble(model.getLongitude())));
                            Marker m = googleMap.addMarker(marker);
                            hashMap.put(m, model);
                        }
                    }
                }

                break;
            case 3:

                for (ComplaintModel model : clientsModelArrayList){

                    if(model.getCategory_id() == 1) {

                        if((model.getLatitude() != null) && model.getLongitude() != null) {
                            marker = new MarkerOptions()
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin2))
                                    .position(new LatLng(Double.parseDouble(model.getLatitude()),
                                            Double.parseDouble(model.getLongitude())));
                            Marker m = googleMap.addMarker(marker);
                            hashMap.put(m, model);
                        }
                    }
                }

                break;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (gpsEnabled()){
            loadMap();
        }
    }

    private void loadMap(){
        //createMapView();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }

    private boolean gpsEnabled(){
        LocationManager lm = null;
        boolean gps_enabled = false;
        if(lm==null)
            lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        try{
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }catch(Exception ex){
            Log.d("Robert", ex.getMessage());
        }

        if(!gps_enabled){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Por favor habilita tu gps");
            dialog.setPositiveButton("Abrir configuraciones", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS );
                    startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    finish();
                    Toast.makeText(getApplicationContext(), "Es necesario que habilites tu GPS", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.show();

        }

        return gps_enabled;
    }

    private void createMapView() {
        if(googleMap == null){
            googleMap =  ((SupportMapFragment) getSupportFragmentManager().findFragmentById(
                    R.id.map)).getMap();

            if(null == googleMap) {
                Toast.makeText(getApplicationContext(), "Error creando mapa", Toast.LENGTH_SHORT).show();

            }
        }
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.rlsecurity:
                category = 1;
                setMarkers();
                break;
            case R.id.rltrafic:
                category = 2;
                setMarkers();
                break;
            case R.id.rlurban:
                category = 3;
                setMarkers();
                break;
            case R.id.rluserinfo:
                intent = new Intent(HomePageActivity.this, UserInfoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
        }
    }


    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);


        if (location != null) {

            if(googleMap == null)
                createMapView();

            googleMap.clear();
            googleMap.setMyLocationEnabled(true);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16));

        }else{
            mGoogleApiClient.reconnect();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        ComplaintModel model =  hashMap.get(marker);
        Intent intent = new Intent(HomePageActivity.this, ComplaintDetail.class);
        intent.putExtra("complaint_id", model.getId());
        intent.putExtra("category", model.getCategory_id());
        startActivity(intent);

        return false;
    }
}

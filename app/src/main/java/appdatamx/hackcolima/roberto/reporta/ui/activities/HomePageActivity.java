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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import appdatamx.hackcolima.roberto.reporta.R;

public class HomePageActivity extends FragmentActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap googleMap;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        ViewGroup rLSecurity = (ViewGroup) findViewById(R.id.rlsecurity);
        ViewGroup rLTrafic = (ViewGroup) findViewById(R.id.rltrafic);
        ViewGroup rLUrban = (ViewGroup) findViewById(R.id.rlurban);
        ViewGroup rLUser = (ViewGroup) findViewById(R.id.rluserinfo);
        TextView titleToolbar = (TextView) findViewById(R.id.titletoolbar);

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
                break;
            case R.id.rltrafic:
                break;
            case R.id.rlurban:
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
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16));
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(location.getLatitude(), location.getLongitude()))
                    .title("Mi ubicación"));
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
}

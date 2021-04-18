package com.example.fgo;

import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.List;

public class mapactivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlacesClient placesClient;
    private List<AutocompletePrediction> predictionList;
    private Location mLastKnownLocation;
    private LocationCallback locationCallback;
    private MaterialSearchBar materialSearchBar;
    private View mapView;
    int PROXIMITY_RADIUS=1000;
    double latitude,longitude;
    private final float default_zoom = 20000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapactivity);
        Button bt;

//        materialSearchBar = findViewById(R.id.searchBar);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapView=mapFragment.getView();
        mFusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(mapactivity.this);
        Places.initialize(mapactivity.this,"AIzaSyCsdQhqmE8ry6Rs1136UflCzFQ80QQ8Npk");
        placesClient=Places.createClient(this);
        AutocompleteSessionToken token=AutocompleteSessionToken.newInstance();
//        mMap.clear();
        String bunk="gas stations";
        String url = getUrl(latitude, longitude, bunk);
        Object datatransfer[]=new Object[2];
        datatransfer[0]=mMap;
        datatransfer[1]=url;
//        GetNearbyPlacesData getNearbyPlacesData=new GetNeatbyPlacesData();


    }
private  String getUrl(double latitude,double logitude,String bunk)
{
    StringBuilder googleplacesurl=new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
    googleplacesurl.append("location"+latitude+","+logitude);
    googleplacesurl.append("&radius="+PROXIMITY_RADIUS);
    googleplacesurl.append("&type"+bunk);
    googleplacesurl.append("&sensor=true");
    googleplacesurl.append("&key="+"\"AIzaSyCsdQhqmE8ry6Rs1136UflCzFQ80QQ8Npk");
    return  googleplacesurl.toString();
}
    @Override
    public void onMapReady(GoogleMap googleMap) {

            mMap=googleMap;
            mMap.setMyLocationEnabled(true);

            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            if(mapView!=null&&mapView.findViewById(Integer.parseInt("1"))!=null){
                View locationbutton=((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
                RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams) locationbutton.getLayoutParams();
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP,0);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                layoutParams.setMargins(0,0,0,0);
            }
            LocationRequest locationRequest=LocationRequest.create();
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(50);
            locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            LocationSettingsRequest.Builder builder=new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
            SettingsClient settingsClient= LocationServices.getSettingsClient(mapactivity.this);
        Task<LocationSettingsResponse> task=settingsClient.checkLocationSettings(builder.build());
        task.addOnSuccessListener(mapactivity.this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                getDeviceLocation();
            }
        });
        task.addOnFailureListener(mapactivity.this, new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                ResolvableApiException resolvable=(ResolvableApiException) e;
                try {
                    resolvable.startResolutionForResult(mapactivity.this,51);
                }catch (IntentSender.SendIntentException el){
                    el.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULT_OK){
            getDeviceLocation();
        }
    }
    private void getDeviceLocation(){
        mFusedLocationProviderClient.getLastLocation()
                .addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(Task<Location> task) {
                        if(task.isSuccessful()){
                            mLastKnownLocation=task.getResult();
                            if(mLastKnownLocation!=null)
                            {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLongitude()),default_zoom));

                            }
                            else
                            {
                                final LocationRequest locationRequest=LocationRequest.create();
                                locationRequest.setInterval(100);
                                locationRequest.setFastestInterval(50);
                                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                               locationCallback=new LocationCallback(){
                                   @Override
                                   public void onLocationResult(LocationResult locationResult) {
                                       super.onLocationResult(locationResult);
                                       if(locationResult==null){
                                           return;
                                       }
                                       mLastKnownLocation=locationResult.getLastLocation();
                                       mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLongitude()),default_zoom));
                                       mFusedLocationProviderClient.removeLocationUpdates(locationCallback);

                                   }
                               };
                               mFusedLocationProviderClient.requestLocationUpdates(locationRequest,locationCallback,null);

                            }
                        }
                        else {
                            Toast.makeText(mapactivity.this,"usable to get Loactions",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
}

}
package com.example.fgo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener
{
    private LocationCallback locationCallback;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlacesClient placesClient;
    private Location mLastKnownLocation;
    private final float default_zoom = 20;

    private GoogleMap mMap;
    private GoogleApiClient mgoogleapiclient;
    double currentlatittude,currentlongitude;
   Location mylocaton;
   private final static int REQUES_CHECK_SETTINGS_GPS=0x1;
   private  final static int REQUEST_ID_MULTIPLE_PERMISSIONS=0X2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setUPGClient();
    }
    private void setUPGClient()
    {
        mgoogleapiclient=new GoogleApiClient.Builder(this).enableAutoManage(this,0,this).addConnectionCallbacks(this).enableAutoManage(this,0,this)
                .addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        mgoogleapiclient.connect();
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onConnected(Bundle bundle) {
checkPermission();
    }

    private void checkPermission() {
        int permissionlocation = ContextCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listpermission=new ArrayList<>();
        if(permissionlocation== PackageManager.PERMISSION_GRANTED)
        {
            mFusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(MapsActivity.this);
            Places.initialize(MapsActivity.this,"YOUR_API_KEY");
            placesClient=Places.createClient(this);
            AutocompleteSessionToken token=AutocompleteSessionToken.newInstance();
            getMyLocation();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        int permissionLocation=ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionLocation== PackageManager.PERMISSION_GRANTED)
        {

        }
        else
        {
            checkPermission();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
mylocaton=location;
if(mylocaton!=null)
{
    currentlatittude=location.getLatitude();
    currentlongitude=location.getLongitude();
    BitmapDescriptor icon= BitmapDescriptorFactory.fromResource(R.drawable.marker);
    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentlatittude,currentlongitude),15.0f));
    MarkerOptions markerOptions=new MarkerOptions();
    markerOptions.position(new LatLng(currentlatittude,currentlongitude));
    markerOptions.title("you");
    markerOptions.icon(icon);
    mMap.addMarker(markerOptions);
    getNearbyBunks();
}
     }

    private void getNearbyBunks() {
        StringBuilder stringBuilder=new StringBuilder("https://maps.googlapi.com/maps/api/place/nearbysearch/json?");
        stringBuilder.append("location="+String.valueOf(currentlatittude)+","+String.valueOf(currentlongitude));
        stringBuilder.append("radius=1500");
        stringBuilder.append("&type=petrol bunks");
        stringBuilder.append("&key="+getResources().getString(R.string.google_maps_key));
        String url=stringBuilder.toString();
        Object datatransfer[]=new Object[2];
        datatransfer[0]=mMap;
        datatransfer[1]=url;
        GetNearbyPlacesData getNearbyPlacesData=new GetNearbyPlacesData();
        getNearbyPlacesData.execute(datatransfer);
    }

    private void getMyLocation(){
        GoogleApiClient mGoogleApiClient;
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
                            Toast.makeText(MapsActivity.this,"usable to get Loactions",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

package com.example.fgo;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class Cart extends AppCompatActivity {
private FusedLocationProviderClient fusedLocationProviderClient;

TextView manual_location,current_location;
EditText address1;
Button place_order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        manual_location=findViewById(R.id.manuallocation);
        current_location=findViewById(R.id.Currentlocation);
        address1=findViewById(R.id.address);
        place_order=findViewById(R.id.place_order);
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        manual_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                address1.setVisibility(View.VISIBLE);
                address1.getText();
            }
        });
        current_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ActivityCompat.checkSelfPermission(Cart.this, ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
                {
                    return;
                }
                fusedLocationProviderClient.getLastLocation().addOnSuccessListener(Cart.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        address1.setText(location.toString());
                        address1.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(address1!=null)
                {
                    Toast.makeText(getApplicationContext(),"Order placed successfully",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"Enter Address",Toast.LENGTH_LONG).show();
            }
        });

    }
public void  requestPermission()
{
    ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);
}

}

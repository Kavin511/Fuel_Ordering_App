package com.example.fgo;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class permissions_act extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions_act);

//        if(ContextCompat.checkSelfPermission(permissions_act.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
//        {
//           Intent ik=new Intent(permissions_act.this,mapactivity.class);
//           startActivity(ik);
//            finish();
//            return;
//
//        }
                Dexter.withActivity(permissions_act.this)
                            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                        startActivity( new Intent(permissions_act.this,Card_activity.class));
                        finish();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied())
                        {
                            AlertDialog.Builder alertbuild=new AlertDialog.Builder(permissions_act.this);
                            alertbuild.setTitle("Permission denied").setMessage("Location permission denied permanently")
                                    .setNegativeButton("canel",null)
                                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent=new Intent();
                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            intent.setData(Uri.fromParts("package",getPackageName(),null));

                                        }
                                    })
                                    .show();

                        }
                        else
                        {
                            Toast.makeText(permissions_act.this,"access denied",Toast.LENGTH_SHORT).show();
                        }

                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                        .check();
            }


}

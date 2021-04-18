package com.example.fgo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private AdView mAdView,adview2;
    Button signin,login ;
FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signin=findViewById(R.id.new_user);
        login=findViewById(R.id.Exist_user);
        ActionBar actionBar=getSupportActionBar();
        ColorDrawable colorDrawable=new ColorDrawable(Color.parseColor("#D7EFF5"));
        actionBar.hide();
        ColorDrawable colorText=new ColorDrawable(Color.parseColor("#D7EFF5"));
        MobileAds.initialize(this,"ca-app-pub-3062269684690866~2163066390");
        mAdView = findViewById(R.id.adView);
        adview2=findViewById(R.id.adView2);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        adview2.loadAd(adRequest2);
        AdRequest adRequest = new AdRequest.Builder().build();

        mAdView.loadAd(adRequest);
        mAuth = FirebaseAuth.getInstance();
        String v;
        if(mAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(MainActivity.this,Card_activity.class));
            finish();
        }
        else {

            signin.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"Entering form",Toast.LENGTH_SHORT).show();
                            Intent signin = new Intent(MainActivity.this, New_user.class);
                            startActivity(signin);
                        }

                    });
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"loging in",Toast.LENGTH_SHORT).show();
                    Intent login = new Intent(MainActivity.this, Login_activity.class);
                    startActivity(login);
                }
            });
        }

    }
}

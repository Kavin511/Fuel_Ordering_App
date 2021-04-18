package com.example.fgo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class Card_activity extends AppCompatActivity {
ImageView Mmap,Mbunks,Muser,Mworkshop;
    ProgressBar progressBar;
    AdView mAdView,adview2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_activity);
        Mmap=findViewById(R.id.map);
        Mbunks=findViewById(R.id.bunks);
        Muser=findViewById(R.id.user);
        Mworkshop=findViewById(R.id.workshop);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        MobileAds.initialize(this,"ca-app-pub-3062269684690866~2163066390");
        mAdView = findViewById(R.id.adView);
        adview2=findViewById(R.id.adView2);
        AdRequest adRequest2 = new AdRequest.Builder().build();
        adview2.loadAd(adRequest2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
         Mmap.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
//                 progressBar.setVisibility(View.VISIBLE);
                 startActivity(new Intent(Card_activity.this,mapactivity.class));
             }
         });
         Muser.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
//               progressBar.setVisibility(View.VISIBLE);
                 startActivity(new Intent(Card_activity.this,Loading.class));
             }
         });
         Mbunks.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(Card_activity.this,Fuel_update.class));
             }
         });
    }
}

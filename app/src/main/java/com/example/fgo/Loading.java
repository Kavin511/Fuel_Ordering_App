package com.example.fgo;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class Loading extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        WebView mywebview = (WebView) findViewById(R.id.webView1);
        mywebview.loadUrl("file:///android_asset/kav.html");
    }
}

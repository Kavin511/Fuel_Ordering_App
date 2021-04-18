package com.example.fgo;

import android.os.AsyncTask;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class GetNearbyPlacesData extends AsyncTask<Object,String,String > {
    String googleplacesdata;
    GoogleMap googleMap;
    String url;

    @Override

    protected String doInBackground(Object... objects) {
        googleMap=(GoogleMap)objects[0];
        url=(String)objects[1];
        DownloadUrl downloadUrl=new DownloadUrl();
        try{
            googleplacesdata=downloadUrl.readUrl(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return googleplacesdata;
    }

    protected void onPostExecute(String s) {
        try {
            JSONObject parentobject= new JSONObject(s);
            JSONArray resultarray=parentobject.getJSONArray("results");
            for (int i=0;i<resultarray.length();i++)
            {
                JSONObject jsonObject=resultarray.getJSONObject(i);
                JSONObject locationobj=jsonObject.getJSONObject("geometry").getJSONObject("location");
                String latitude=locationobj.getString("lat");
                String longitude=locationobj.getString("lng");
                JSONObject nameobject=resultarray.getJSONObject(i);
                String name=nameobject.getString("name");
                LatLng  latLng=new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude));
                MarkerOptions markerOptions=new MarkerOptions();
                markerOptions.title(name);
                markerOptions.position(latLng);
                googleMap.addMarker(markerOptions);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}

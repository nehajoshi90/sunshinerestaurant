package com.example.nehajoshi.sunshinerestaurantapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.nehajoshi.sunshinerestaurantapp.restaturant.RestaurantActivity;
import com.example.nehajoshi.sunshinerestaurantapp.utils.Constants;
import com.example.nehajoshi.sunshinerestaurantapp.weather.WeatherActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient mFusedLocationClient;
    private TextView statusTxtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusTxtv = (TextView) findViewById(R.id.status);

        requestPermission();
    }

    void requestPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    Constants.MY_PERMISSIONS_REQUEST_COARSE_LOCATION);
        } else {
            // Permission has already been granted
            getUserCurrentLocation();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Constants.MY_PERMISSIONS_REQUEST_COARSE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getUserCurrentLocation();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    statusTxtv.setText(getString(R.string.permission_not_granted));
                }
                return;
            }
        }
    }

    void getUserCurrentLocation(){
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            statusTxtv.setVisibility(View.GONE);
                            findViewById(R.id.layout).setVisibility(View.VISIBLE);
                        }else{
                            statusTxtv.setText(getString(R.string.unable_to_get_location));
                        }
                    }
                });
    }

    public void viewWeatherForecast(View view){
        startActivity(new Intent(this, WeatherActivity.class));
    }

    public void viewNearrestra(View view){
        startActivity(new Intent(this, RestaurantActivity.class));
    }
}

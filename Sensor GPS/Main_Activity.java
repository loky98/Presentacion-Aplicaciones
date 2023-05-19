package com.example.sensorgps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.SensorEventListener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;

import kotlinx.coroutines.Delay;

public class MainActivity extends AppCompatActivity implements LocationListener {

    TextView Latitud,Longitud;

    private LocationManager ubicacion;
    //String Lat,log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Latitud = (TextView) findViewById(R.id.latitud);
        Longitud = (TextView) findViewById(R.id.longitud);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{
                    android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
            },1000);
        }
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        ubicacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location loc = ubicacion.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        loc.getLatitude();
        loc.getLongitude();

        Latitud.setText(""+loc.getLatitude());
        Longitud.setText(""+loc.getLongitude());


    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{
                    android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
            },1000);
        }

        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        ubicacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location loc = ubicacion.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        loc.getLatitude();
        loc.getLongitude();
        String Lat =""+loc.getLatitude();
        String Log =""+loc.getLongitude();

        Latitud.setText(Lat);
        Longitud.setText(Log);

    }


}

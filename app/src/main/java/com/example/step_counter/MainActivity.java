package com.example.step_counter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.step_counter.db.DBManager;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements LocListenerInterface{
    private LocationManager locationManager;
    private TextView tvDictance, tvVelocity, tvInfo;
    private Location lastLocation;
    private MyLocationListener myLocationListener;
    private int distance = 0;
    private Date currentDate;
    private DBManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Log.d("RRR", "oncreate");

        init();
        checkPermissions();


    }

    @SuppressLint("SetTextI18n")
    private void init(){
        Log.d("RRR", "Init");
        tvDictance = findViewById(R.id.tvDist);
        tvVelocity = findViewById(R.id.tvVel);
        tvInfo = findViewById(R.id.tvInfo);

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        myLocationListener = new MyLocationListener();
        myLocationListener.setLocListenerInterface(this);

        currentDate = new Date();

        dbManager = new DBManager(this);


        /*
        Locale locale = new Locale("ru");
        Locale.setDefault(locale);
        Date now = new Date();

        String.format(locale, "%tD\n", now) + //(MM/DD/YY)

        tvVelocity.setText(String.valueOf(now));
        */
    }

    @Override
    protected void onResume() {
        super.onResume();
            dbManager.openDB();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.closeDB();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("RRR", "onRequestPermissionsResult");
        if(requestCode == 100 && grantResults[0] == RESULT_OK){
            checkPermissions();
        }
        else {
            Toast.makeText(this, "No GPS permission", Toast.LENGTH_LONG).show();
        }
    }

    private void checkPermissions(){
        Log.d("RRR", "checkPermissions");
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},
                    100);
        }
        else {
            Log.d("RRR", "loc request");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2, 1, myLocationListener);
        }
    }

    @Override
    public void OnLocationChanged(Location location) {
        Log.d("RRR", "loc changed");
        Date now = new Date();
        if (!Objects.equals(String.format("%tD", currentDate), String.format("%tD", now))){
            distance = 0;
            currentDate = now;
        }

        if (location.hasSpeed() && lastLocation != null){
            Log.d("RRR", String.valueOf((int) lastLocation.distanceTo(location)));
            distance += (int) lastLocation.distanceTo(location);
        }
        lastLocation = location;
        tvDictance.setText(String.valueOf(distance));
        tvVelocity.setText(String.valueOf(location.getSpeed()));

        dbManager.insertToDB(String.format("%tD", currentDate), distance);

    }

    public void onClickClear(View view) {
        dbManager.clearDB();
    }

    public void onClickShow(View view) {
        tvInfo.setText("");
        for (Pair<String, Integer> data : dbManager.readFromDB()){
            tvInfo.append(data + "\n");
        }
    }
}
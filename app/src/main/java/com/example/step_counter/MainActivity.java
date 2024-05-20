package com.example.step_counter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.step_counter.db.DBConstants;
import com.example.step_counter.db.DBManager;
import com.example.step_counter.location.LocListenerInterface;
import com.example.step_counter.location.MyLocationListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements LocListenerInterface{
    public LocationManager locationManager;

    private Location lastLocation;
    private MyLocationListener myLocationListener;
    private Date currentDate;
    private DBManager dbManager;
    private int distance = 0;



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
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        currentDate = new Date();
        dbManager = new DBManager(this);
        myLocationListener = new MyLocationListener();
        myLocationListener.setLocListenerInterface(this);

        @SuppressLint("RestrictedApi")
        BottomNavigationView bottomNavigationView;
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                // Обработка выбора раздела "Домой"
                FragmentMain main = new FragmentMain();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmcontainer, main);
                fragmentTransaction.commit();
                return true;
            } else if (item.getItemId() == R.id.settings) {
                // Обработка выбора раздела "Настройки"
                FragmentSettings settings = new FragmentSettings();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragmcontainer, settings);
                ft.commit();
                return true;
            } else if (item.getItemId() == R.id.statistic) {
                // Обработка выбора раздела "Статистика"
                FragmentStat stat = new FragmentStat();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragmcontainer, stat);
                ft.commit();
                return true;
            }
            return false;
        });
        FragmentMain main = new FragmentMain();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmcontainer, main);
        fragmentTransaction.commit();
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
    public void OnLocationChanged(Location location) {
        Log.d("RRR", "loc changed");
        Date now = new Date();
        if (!Objects.equals(String.format("%tD", currentDate), String.format("%tD", now))){
            distance = 0;
            currentDate = now;
        }
        if (location.hasSpeed() && lastLocation != null){
            distance += (int) lastLocation.distanceTo(location);
        }
        lastLocation = location;
        dbManager.insertToDB(String.format("%tD", currentDate), distance);
    }
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
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    100);
        }
        else {
            Log.d("RRR", "loc request");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2, 1, myLocationListener);
        }
    }
}
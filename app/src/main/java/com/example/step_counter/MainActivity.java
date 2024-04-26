package com.example.step_counter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import com.example.step_counter.db.DBManager;
import com.example.step_counter.location.LocListenerInterface;
import com.example.step_counter.location.MyLocationListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private LocationManager locationManager;
    private TextView tvDictance, tvVelocity, tvInfo;
    private Location lastLocation;
    private MyLocationListener myLocationListener;
    private int distance = 0;
    private Date currentDate;
    private DBManager dbManager;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Log.d("RRR", "oncreate");

        init();
        //checkPermissions();


    }

    @SuppressLint("SetTextI18n")
    private void init(){
        Log.d("RRR", "Init");
        /*
        tvDictance = findViewById(R.id.tvDist);
        tvVelocity = findViewById(R.id.tvVel);
        tvInfo = findViewById(R.id.tvInfo);

         */
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




        //locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //myLocationListener = new MyLocationListener();

        //myLocationListener.setLocListenerInterface(this);

        //currentDate = new Date();

        //dbManager = new DBManager(this);

    }


    /*
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
    */






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
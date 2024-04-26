package com.example.step_counter;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.step_counter.db.DBManager;
import com.example.step_counter.location.LocListenerInterface;
import com.example.step_counter.location.MyLocationListener;

import java.util.Date;
import java.util.Objects;


public class FragmentMain extends Fragment implements LocListenerInterface {


    private LocationManager locationManager;
    private TextView tvDictance, tvVelocity;
    private Location lastLocation;
    private MyLocationListener myLocationListener;
    private int distance = 0;
    private Date currentDate;
    private DBManager dbManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvDictance = (android.widget.TextView) tvDictance.findViewById(R.id.tvDist);
        tvVelocity = (android.widget.TextView) tvVelocity.findViewById(R.id.tvVel);
        currentDate = new Date();
        dbManager = new DBManager(this);
        //locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        myLocationListener = new MyLocationListener();
        myLocationListener.setLocListenerInterface(this);
        checkPermissions();

    }

    @Override
    public void onResume() {
        super.onResume();
        dbManager.openDB();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbManager.closeDB();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("RRR", "onRequestPermissionsResult");
        if(requestCode == 100 && grantResults[0] == RESULT_OK){
            checkPermissions();
        }
        else {
            Toast.makeText(this.getContext(), "No GPS permission", Toast.LENGTH_LONG).show();
        }
    }

    private void checkPermissions(){
        Log.d("RRR", "checkPermissions");
        if(ActivityCompat.checkSelfPermission(this.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    100);
        }
        else {
            Log.d("RRR", "loc request");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2, 1, myLocationListener);
        }
    }
}
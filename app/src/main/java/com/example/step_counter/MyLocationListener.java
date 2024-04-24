package com.example.step_counter;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONException;

import java.util.List;

public class MyLocationListener implements LocationListener {
    private LocListenerInterface locListenerInterface;

    public void setLocListenerInterface(LocListenerInterface locListenerInterface) {
        this.locListenerInterface = locListenerInterface;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Log.d("RRR", "onLocationChanged");
        try {
            locListenerInterface.OnLocationChanged(location);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }
}

package com.example.step_counter;

import android.location.Location;

import org.json.JSONException;

public interface LocListenerInterface {
    void OnLocationChanged(Location location) throws JSONException;
}

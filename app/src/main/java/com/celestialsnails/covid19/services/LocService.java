package com.celestialsnails.covid19.services;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import androidx.annotation.Nullable;

public class LocService extends Service implements LocationListener {

    public LocationRequest createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
        return locationRequest;
    }

    @Override
    public void onLocationChanged(Location location) {
        createLocationRequest();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

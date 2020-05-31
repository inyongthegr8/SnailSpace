package com.celestialsnails.covid19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.celestialsnails.covid19.constants.ConstantRequests;
import com.celestialsnails.covid19.hotline.HotlineParser;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class EmergencyActivity extends AppCompatActivity {

    @BindView(R.id.tv_suicide_local)
    TextView tvSuicideLocal;
    @BindView(R.id.tv_emergency_local)
    TextView tvEmergencyLocal;
    @BindView(R.id.iv_call_suicide)
    ImageView ivSuicideCall;
    @BindView(R.id.iv_call_emergency)
    ImageView ivEmergencyCall;

    private LocationManager locationManager;
    private LocationCallback locationCallback;
    private FusedLocationProviderClient fusedLocationClient;
    private Geocoder geocoder;
    private HotlineParser hp;
    private LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        initialize();
    }

    private void initialize() {
        ButterKnife.bind(this);
        initHotlines();
        initPermissions();
    }

    private void initHotlines() {
        hp = new HotlineParser();
        hp.setContext(this);
        hp.initialize();
    }

    protected void initLocationRequest() {
        Toast.makeText(this, "We're looking for you, hold on",
                Toast.LENGTH_SHORT).show();
        geocoder = new Geocoder(this, Locale.US);
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(50);
        locationRequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        try {
                            if (location != null) {
                                presentLocation(location);
                            }
                        }
                        catch (IOException io) {
                            Log.d(EmergencyActivity.this.getClass().getName(),
                                    "onSuccess: Location not found");
                        }
                        fusedLocationClient.removeLocationUpdates(locationCallback);
                    }
                }
            }
        };
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
                Log.d(EmergencyActivity.this.getClass().getName(), "onSuccess: Ting ting ting");
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(EmergencyActivity.this,
                                ConstantRequests.LOCATION_REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });
    }

    private void updateLocation() {
        fineLocationCheckPermission();
    }

    private void presentLocation(Location location) throws IOException {
        String current = determineLocationName(geocoder, location);
        final String suicideHotline = hp.getSuicideHotline(current);
        final String emergencyHotline = hp.getEmergencyHotline(current);
        Log.d(EmergencyActivity.this.getClass().getName(), "onSuccess: " + current + " - " +
                suicideHotline);
        Log.d(EmergencyActivity.this.getClass().getName(), "onSuccess: " + current + " - " +
                emergencyHotline);
        tvSuicideLocal.setText(suicideHotline);
        tvEmergencyLocal.setText(emergencyHotline);
        ivSuicideCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMe(suicideHotline);
            }
        });
        ivEmergencyCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callMe(emergencyHotline);
            }
        });
    }

    private void callMe(String hotline) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + hotline));
        if (ActivityCompat.checkSelfPermission(EmergencyActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            callCheckPermission();
            return;
        }
        startActivity(callIntent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initLocationRequest();
        updateLocation();
    }

    private void initPermissions() {
        requestPermissionsCheck();
        fineLocationCheckPermission();
        callCheckPermission();
    }

    private void callCheckPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.CALL_PHONE) ==
                    PackageManager.PERMISSION_GRANTED) {

            } else if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.
                showCallPhoneRequired();
            } else {
                // We can request the permission by launching the ActivityResultLauncher
//                mRequestPermissions.launch(...);
                // The registered ActivityResultCallback gets the result of the request.
                requestPermissions(new String[]{
                        Manifest.permission.CALL_PHONE
                }, ConstantRequests.CALL_PERMISSION);
            }
        }
    }

    private void fineLocationCheckPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
                initLocationRequest();
                fusedLocationClient.requestLocationUpdates(locationRequest,
                        locationCallback, null);
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.
                showLocationPermissionRequired();
            } else {
                // We can request the permission by launching the ActivityResultLauncher
//                mRequestPermissions.launch(...);
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, ConstantRequests.LOCATION_PERMISSION);
                // The registered ActivityResultCallback gets the result of the request.
            }
        }
    }

    private String determineLocationName(Geocoder gcd, Location location) throws IOException {
        List<Address> addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        Log.d(this.getClass().getName(), "determineLocationName: " + addresses.get(0).getCountryName());
        return addresses.get(0).getCountryName();
    }

    private void showCallPhoneRequired() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.location_permission_message)
                .setTitle(R.string.default_permission_title);
        builder.setPositiveButton(R.string.default_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(EmergencyActivity.this,
                        new String[] {
                                Manifest.permission.CALL_PHONE
                        },
                        ConstantRequests.CALL_PERMISSION);
            }
        });
        builder.setNegativeButton(R.string.default_negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showLocationPermissionRequired() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.location_permission_message)
                .setTitle(R.string.default_permission_title);
        builder.setPositiveButton(R.string.default_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(EmergencyActivity.this,
                        new String[] {
                                Manifest.permission.ACCESS_FINE_LOCATION
                        },
                        ConstantRequests.LOCATION_PERMISSION);
            }
        });
        builder.setNegativeButton(R.string.default_negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void requestPermissionsCheck() {
        ActivityCompat.requestPermissions(EmergencyActivity.this,
                new String[] {
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.CALL_PHONE
                },
                ConstantRequests.FIRST_RUN_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ConstantRequests.FIRST_RUN_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                    Log.d(EmergencyActivity.class.getName(),
                            "onRequestPermissionsResult: Pwedeng pwede icheck location mo");
                    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
                    initLocationRequest();
                    fusedLocationClient.requestLocationUpdates(locationRequest,
                            locationCallback, null);
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d(EmergencyActivity.class.getName(),
                            "onRequestPermissionsResult: Ops bawal yan!");
                }
                return;
            }

            case ConstantRequests.LOCATION_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                    Log.d(EmergencyActivity.class.getName(),
                            "onRequestPermissionsResult: Pwedeng pwede icheck location mo");
                    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
                    initLocationRequest();
                    fusedLocationClient.requestLocationUpdates(locationRequest,
                            locationCallback, null);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Log.d(EmergencyActivity.class.getName(),
                            "onRequestPermissionsResult: Ops bawal yan!");
                }
                return;
            }
            default: {

            }
        }
    }
}

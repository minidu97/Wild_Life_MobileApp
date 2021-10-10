package com.example.wildlife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class Map_View extends AppCompatActivity {

    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    LocationRequest locationRequest;
    GoogleMap Mmap;
    ArrayList<LatLng>arrayList = new ArrayList<LatLng>();
    LatLng kalutara = new LatLng(6.5854,79.9607);
    LatLng Mathugama = new LatLng(6.5219,80.1137);
    LatLng panadura = new LatLng(6.7106,79.9074);
    LatLng colombo = new LatLng(6.9271,79.8612);
    LatLng Gampaha = new LatLng(7.0840,80.0098);
    private static final String TAG = "Map_View";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.Google_Map);
        client = LocationServices.getFusedLocationProviderClient(Map_View.this);

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(500);
        locationRequest.setFastestInterval(500);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        arrayList.add(kalutara);
        arrayList.add(Mathugama);
        arrayList.add(panadura);
        arrayList.add(colombo);
        arrayList.add(Gampaha);

        if (ActivityCompat.checkSelfPermission(Map_View.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Latitude : sample test");
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(Map_View.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
        }
        else{
            client.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        client.removeLocationUpdates(locationCallback);
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (ActivityCompat.checkSelfPermission(Map_View.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Map_View.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            //if (Mmap != null){
                //Log.d(TAG, "Latitude : sample test-01");
                //Mmap.setMyLocationEnabled(true);
            //LatLng latLng = new LatLng(locationResult.getLastLocation().getLatitude(),locationResult.getLastLocation().getLatitude());
            //MarkerOptions options = new MarkerOptions().position(latLng).title("My Current Location");
                //Mmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,5));
           //Mmap.addMarker(options);
            //}

        }
    };

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            Mmap = googleMap;
                            if (ActivityCompat.checkSelfPermission(Map_View.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Map_View.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            Mmap.setMyLocationEnabled(true);
                            for (int i=0;i<arrayList.size();i++) {
                                //LatLng latLng = new LatLng(location.getLatitude(),location.getLatitude());
                                LatLng latLng = new LatLng(7.8731,80.7718);
                                //MarkerOptions options = new MarkerOptions().position(latLng).title("My Current Location");
                                MarkerOptions options = new MarkerOptions().position(arrayList.get(i)).title("My Current Location");
                                Mmap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,8));
                                Mmap.addMarker(options);
                                //Log.d(TAG, "Latitude : "+ location.getLatitude());
                                //Log.d(TAG, "Longitude : "+ location.getLongitude());
                            }
                            //for (int i=0;i<arrayList.size();i++){
                               // Mmap.addMarker(new MarkerOptions().position(arrayList.get(i)).title("location"));
                               // Mmap.animateCamera(CameraUpdateFactory.zoomTo(20));
                               // Mmap.animateCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
                            //}
                        }
                    });
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        }
    }
}
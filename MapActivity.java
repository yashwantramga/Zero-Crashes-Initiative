package com.example.mapdemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Mapactivity extends FragmentActivity implements OnMapReadyCallback {
    Location currentLocation;
    FusedLocationProviderClient fusedClient;
    private static final int REQUEST_CODE= 101;
    FrameLayout map;
    GoogleMap gmap;
    Marker marker;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapactivity);
        map=findViewById(R.id.map);
        searchView = findViewById(R.id.search);
        searchView.clearFocus();

        fusedClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String loc= searchView.getQuery().toString();
                if(loc==null)
                {
                    Toast.makeText(Mapactivity.this, "Location Not Found", Toast.LENGTH_SHORT).show();
                }
                else {
                    Geocoder geocoder = new Geocoder(Mapactivity.this, Locale.getDefault());
                    try {
                        List<Address> addressList = geocoder.getFromLocationName(loc,1);
                        if(addressList.size()>0){
                            LatLng latLng = new LatLng(addressList.get(0).getLatitude(),addressList.get(0).getLongitude());
                            if(marker!=null){
                                marker.remove();
                            }
                            MarkerOptions markerOptions=new MarkerOptions().position(latLng).title(loc);
                            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,10);
                            gmap.animateCamera(cameraUpdate);
                            marker = gmap.addMarker(markerOptions);
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }





    private void getLocation(){
        if(ActivityCompat.checkSelfPermission(
                this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }

        Task<Location> task = fusedClient.getLastLocation();

        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location!=null){
                    currentLocation= location;
                    SupportMapFragment supportMapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    assert  supportMapFragment !=null;
                    supportMapFragment.getMapAsync(Mapactivity.this);
                }
            }
        });
    }




    private void addMarker(double latitude, double longitude, String title) {
        LatLng latLng = new LatLng(latitude, longitude);
        MarkerOptions markerOptions;
        markerOptions = new MarkerOptions()
                .position(latLng)
                .title(title)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
        gmap.addMarker(markerOptions);
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.gmap=googleMap;
        LatLng latlng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        MarkerOptions markerOptions=new MarkerOptions().position(latlng).title("My Current Location");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latlng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,13));
        googleMap.addMarker(markerOptions);
        addMarker(9.62790,77.96020, "Vnr Zone");
        addMarker(9.55757,77.94578, "Vnr Zone");
        addMarker(9.57293,77.97231, "Vnr Zone");
        addMarker(9.59177,77.96297, "Vnr Zone");
        addMarker(9.58061,77.95342, "Vnr Zone");
        addMarker(9.58881,77.96231, "Vnr Zone");
        addMarker(9.53912,77.94524, "Vnr Zone");
        addMarker(9.58004,77.96251, "Vnr Zone");
        addMarker(9.62424,77.95829, "Vnr Zone");
        addMarker(9.60442,77.95492, "Vnr Zone");
        addMarker(9.61169,77.95646, "Vnr Zone");
        addMarker(9.57852,77.95962, "Vnr Zone");









    }

    public class NewMapactivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            // Implementation of onMapReady method
            // Add your code here to work with the GoogleMap object
        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mapactivity);

            // Initialize the map
            SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            assert supportMapFragment != null;
            supportMapFragment.getMapAsync(this);
        }






        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if(requestCode == REQUEST_CODE){
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getLocation();
                }
            }
        }
        @Override
        public void onLocationChanged(Location location) {
            // Check if the current location is within 1 km of the target location
            double targetLatitude = 9.604420;
            double targetLongitude = 77.954920;

            Location targetLocation = new Location("");
            targetLocation.setLatitude(targetLatitude);
            targetLocation.setLongitude(targetLongitude);

            float distance = location.distanceTo(targetLocation);

            if (distance <= 500) { // 1 km = 1000 meters
                // Display a pop-up message when the target location is within 1 km
                Toast.makeText(this, "Approaching the Accident Zone!", Toast.LENGTH_SHORT).show();
            }
        }

        // ...
    }



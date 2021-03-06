package com.grapesoftware.paycal_resist;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    Marker marker;
    String latitude, longitude, adress, country;
    SearchView searchview;
    private GoogleMap mMap;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Button buttongo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);
        latitude = preferences.getString("Latitude", null);
        longitude = preferences.getString("Longitude", null);
        editor = preferences.edit();
        buttongo = findViewById(R.id.buttongo);

        buttongo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, CustomerActivity.class);
                startActivity(intent);
            }
        });


        //Button button = findViewById(R.id.btnnextactivity);
        searchview = findViewById(R.id.sv_location);

        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (marker != null) {
                    marker.remove();
                }
                String location = searchview.getQuery().toString();
                List<Address> addressList = null;

                if (location != null || !location.equals("")) {
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (!addressList.isEmpty()) {
                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        MarkerOptions options = new MarkerOptions()
                                .title(address.getLocality())
                                .position(latLng);

                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

                        marker = mMap.addMarker(options);

                        latitude = String.valueOf(latLng.latitude);
                        longitude = String.valueOf(latLng.longitude);
                        adress = addressList.get(0).getAddressLine(0);
                        country = addressList.get(0).getCountryName();

                        editor.putString("Latitude", latitude);
                        editor.putString("Longitude", longitude);
                        editor.putString("Country", country);
                        editor.putString("Adress", adress);
                        editor.commit();
                    } else
                        Toast.makeText(getApplicationContext(), "We couldn't find the address you specified. Please select from the map.", Toast.LENGTH_LONG).show();

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mapFragment.getMapAsync(this);


//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MapsActivity.this, CustomerActivity.class);
//                startActivity(intent);
//
//            }
//        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setTiltGesturesEnabled(true);
        mMap.setMyLocationEnabled(true);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null)
        {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(location.getLatitude(), location.getLongitude()));
            mMap.addMarker(markerOptions);
        }

        mMap.setPadding(0,100,0,100);
        mMap.getUiSettings().setCompassEnabled(true);
//        mMap.getUiSettings().setMyLocationButtonEnabled(true);



        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mMap.clear();
                Geocoder geocoder = new Geocoder(MapsActivity.this);
                List<Address> list;
                try {
                    list = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                } catch (IOException e) {
                    return;
                }
                Address address = list.get(0);
//                if (marker != null) {
//                    marker.remove();
//                }
                MarkerOptions options = new MarkerOptions()
                        .title(address.getLocality())
                        .position(new LatLng(latLng.latitude, latLng.longitude));

                latitude = String.valueOf(latLng.latitude);
                longitude = String.valueOf(latLng.longitude);
                adress = list.get(0).getAddressLine(0);
                country = list.get(0).getCountryName();

                editor.putString("Latitude", latitude);
                editor.putString("Longitude", longitude);
                editor.putString("Country", country);
                editor.putString("Adress", adress);

                editor.commit();

                Log.i("LOCATION","Latitude=" + latLng.latitude + "Longitude" + latLng.longitude);


                marker = mMap.addMarker(options);
            }
        });



        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Your Location"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));

    }

//    public void findLocation(View v) throws IOException {
//
//        EditText et = (EditText) findViewById(R.id.editTextLocate);
//        String location = et.getText().toString();
//        Geocoder geocoder = new Geocoder(this);
//        List<Address> list = geocoder.getFromLocationName(location, 1);
//        Address add = list.get(0);
//        String locality = add.getLocality();
//        LatLng ll = new LatLng(add.getLatitude(), add.getLongitude());
//
//        latitude = String.valueOf(add.getLatitude());
//        longitude = String.valueOf(add.getLongitude());
//
//
//        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 15);
//        mMap.moveCamera(update);
//        if (marker != null)
//            marker.remove();
//        MarkerOptions markerOptions = new MarkerOptions()
//                .title(locality)
//                .position(new LatLng(add.getLatitude(), add.getLongitude()));
//        marker = mMap.addMarker(markerOptions);
//        adress = list.get(0).getAddressLine(0);
//        country = list.get(0).getCountryName();
//
//        editor.putString("Latitude", latitude);
//        editor.putString("Longitude", longitude);
//        editor.putString("Country", country);
//        editor.putString("Adress", adress);
//        editor.commit();
//
//        View view = this.getCurrentFocus();
//        if (view != null) {
//            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }
//
//    }


}

package com.grapesoftware.paycal_resist;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Marker marker;
    String latitude,longitude,adress,country;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        SharedPreferences preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);
        latitude = preferences.getString("Latitude", null);
        longitude=preferences.getString("Longitude",null);


        Button button=findViewById(R.id.btnnextactivity);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MapsActivity.this,CustomerActivity.class);
                startActivity(intent);
            }
        });
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


        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Geocoder geocoder=new Geocoder(MapsActivity.this);
                List<Address> list;
                try {
                    list=geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
                } catch (IOException e){
                    return;
                }
                Address address=list.get(0);
                if(marker !=null){
                    marker.remove();
                }
                MarkerOptions options=new MarkerOptions()
                        .title(address.getLocality())
                        .position(new LatLng(latLng.latitude,latLng.longitude));

                latitude=String.valueOf(latLng.latitude);
                longitude=String.valueOf(latLng.longitude);
                adress=list.get(0).getAddressLine(0);
                country=list.get(0).getCountryName();

                SharedPreferences preferences = getSharedPreferences("session",getApplicationContext().MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Latitude",latitude);
                editor.putString("Longitude",longitude);
                editor.putString("Country",country);
                editor.putString("Adress",adress);
                editor.commit();

                Toast.makeText(getApplicationContext(),"Latitude="+latLng.latitude+"Longitude"+latLng.longitude,Toast.LENGTH_SHORT).show();


                marker=mMap.addMarker(options);
            }
        });


        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(Double.valueOf(latitude), Double.valueOf(longitude));
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Your Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));

    }

    public void findLocation(View v) throws IOException {

        EditText et = (EditText)findViewById(R.id.editTextLocate);
        String location = et.getText().toString();
        Geocoder geocoder = new Geocoder(this);
        List<Address> list = geocoder.getFromLocationName(location, 1);
        Address add = list.get(0);
        String locality = add.getLocality();
        LatLng ll = new LatLng(add.getLatitude(), add.getLongitude());

        latitude=String.valueOf(add.getLatitude());
        longitude=String.valueOf(add.getLongitude());

        SharedPreferences preferences = getSharedPreferences("session",getApplicationContext().MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Latitude",latitude);
        editor.putString("Longitude",longitude);
        editor.commit();

        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 15);
        mMap.moveCamera(update);
        if(marker != null)
            marker.remove();
        MarkerOptions markerOptions = new MarkerOptions()
                .title(locality)
                .position(new LatLng(add.getLatitude(), add.getLongitude()));
        marker = mMap.addMarker(markerOptions);
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);}

    }


}

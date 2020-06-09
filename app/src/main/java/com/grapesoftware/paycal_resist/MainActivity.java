package com.grapesoftware.paycal_resist;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.dd.morphingbutton.MorphingButton;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    FusedLocationProviderClient fusedLocationProviderClient;
    private RequestQueue mQueue;
    private FirebaseAuth mAuth;
    private Button profile;
    private boolean LocationCompleted = false;
    private String Latitude,Longitude,Adress,Country;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQueue = Volley.newRequestQueue(this);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            Toast.makeText(getApplicationContext(), "Please log in.", Toast.LENGTH_SHORT).show();
        }

        final MorphingButton startButton=findViewById(R.id.btn_basla);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        profile=findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
                //finish();
            }
        });




        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                    LocationCompleted = true;
                    final MorphingButton.Params circle = MorphingButton.Params.create()
                            .duration(500)
                            .cornerRadius(R.dimen.mb_height_56)// 56 dp
                            .width((int) getResources().getDimension(R.dimen.mb_height_56)) // 56 dp
                            .height((int) getResources().getDimension(R.dimen.mb_height_56)) // 56 dp
                            .color(getResources().getColor(R.color.color6)) // normal state color
                            .colorPressed(getResources().getColor(R.color.backgroundBlue)) // pressed state color
                            .icon(R.drawable.ic_done); // icon
                    startButton.morph(circle);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            Intent intent=new Intent(MainActivity.this,MapsActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 1500);
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });


//        findViewById(R.id.btn_basla).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, LocationActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//            }
//        });


//        findViewById(R.id.btn_logout).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                FirebaseAuth.getInstance().signOut();
//
//                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//            }
//        });

    }
    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                //Initialize location
                Location location = task.getResult();
                if (location != null) {

                    try {
                        //initialize geoCoder
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        //Initialize address list
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );

                        Latitude = String.valueOf(addresses.get(0).getLatitude());
                        Longitude = String.valueOf(addresses.get(0).getLongitude());
                        Country=String.valueOf(addresses.get(0).getCountryName());
                        Adress=String.valueOf(addresses.get(0).getAddressLine(0));

                        preferences = getSharedPreferences("session",getApplicationContext().MODE_PRIVATE);

                        editor = preferences.edit();
                        editor.putString("Latitude",Latitude);
                        editor.putString("Longitude",Longitude);
                        editor.putString("Country",Country);
                        editor.putString("Adress",Adress);
                        editor.commit();



                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }
}
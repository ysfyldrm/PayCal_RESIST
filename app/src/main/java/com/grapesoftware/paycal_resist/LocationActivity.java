package com.grapesoftware.paycal_resist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity {
//Initialize variable
    Button btLocation;
    TextView textView1, textView2, textView3, textView4, textView5 ;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        //Assign Variable
        btLocation=findViewById(R.id.bt_location);
        textView1=findViewById(R.id.text_view1);
        textView2=findViewById(R.id.text_view2);
        textView3=findViewById(R.id.text_view3);
        textView4=findViewById(R.id.text_view4);
        textView5=findViewById(R.id.text_view5);


//Initialize fusedLocationProviderClient
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check permission
                if(ActivityCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                    getLocation();
                }
                else{
                    ActivityCompat.requestPermissions(LocationActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                }
            }
        });
    }

    private void getLocation() {
fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
    @Override
    public void onComplete(@NonNull Task<Location> task) {
        //Initialize location
        Location location=task.getResult();
        if (location !=null){
            try {
                //initialize geoCoder
                Geocoder geocoder=new Geocoder(LocationActivity.this, Locale.getDefault());
                //Initialize address list
                List<Address> addresses=geocoder.getFromLocation(
                        location.getLatitude(),location.getLongitude(),1
                );

                //set Latitude on TextView
                textView1.setText(Html.fromHtml("<font color='#6200EE'><b>Latitude :</b><br></font>"+addresses.get(0).getLatitude()));

                //set Longitude on TextView
                textView2.setText(Html.fromHtml("<font color='#6200EE'><b>Longitude :</b><br></font>"+addresses.get(0).getLongitude()));

                //set Country Name
                textView3.setText(Html.fromHtml("<font color='#6200EE'><b>Country :</b><br></font>"+addresses.get(0).getCountryName()));

                //set Locality
                textView4.setText(Html.fromHtml("<font color='#6200EE'><b>Locality :</b><br></font>"+addresses.get(0).getLocality()));

                //set address
                textView5.setText(Html.fromHtml("<font color='#6200EE'><b>Address :</b><br></font>"+addresses.get(0).getAddressLine(0)));


            }
            catch (IOException e){
                e.printStackTrace();
            }

        }
    }
});

    }
}

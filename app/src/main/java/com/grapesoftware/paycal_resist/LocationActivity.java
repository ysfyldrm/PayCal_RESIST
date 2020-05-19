package com.grapesoftware.paycal_resist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity {
//Initialize variable
    Button btLocation, backButton;
    TextView textView1, textView2, textView3, textView4, textView5, showResult1, showResult2, showResult3;
    FusedLocationProviderClient fusedLocationProviderClient;
    ProgressBar dataProgressBar;
    private RequestQueue mQueue;

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
        showResult1=findViewById(R.id.solarIrValue_txt);
        showResult2=findViewById(R.id.windSp50Val);
        showResult3=findViewById(R.id.windSp10Val);
        mQueue= Volley.newRequestQueue(this);
        dataProgressBar = findViewById(R.id.dataProgress_Bar);

        backButton=findViewById(R.id.backLocationBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

            }
        });


//Initialize fusedLocationProviderClient
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check permission
                if (btLocation.getText().equals("CONTINUE")){
                    Intent intent = new Intent(LocationActivity.this, CustomerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                if (LocationCompleted == true){
                    dataProgressBar.setVisibility(View.VISIBLE);
                    jsonParse();
                }

                if(ActivityCompat.checkSelfPermission(LocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                    getLocation();
                    LocationCompleted = true;
                    btLocation.setText("GET DATAS");
                }
                else{
                    ActivityCompat.requestPermissions(LocationActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
                }
            }
        });
    }
    private boolean LocationCompleted = false;
    private String Latitude;
    private String Longitude;

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
                textView1.setText(Html.fromHtml("<font color='#181a24'><b>Latitude :</b><br></font>"+addresses.get(0).getLatitude()));
                Latitude= String.valueOf(addresses.get(0).getLatitude());

                //set Longitude on TextView
                textView2.setText(Html.fromHtml("<font color='#181a24'><b>Longitude :</b><br></font>"+addresses.get(0).getLongitude()));
                Longitude=String.valueOf(addresses.get(0).getLongitude());

                //set Country Name
                textView3.setText(Html.fromHtml("<font color='#181a24'><b>Country :</b><br></font>"+addresses.get(0).getCountryName()));

                //set Locality
                textView4.setText(Html.fromHtml("<font color='#181a24'><b>Locality :</b><br></font>"+addresses.get(0).getLocality()));

                //set address
                textView5.setText(Html.fromHtml("<font color='#181a24'><b>Address :</b><br></font>"+addresses.get(0).getAddressLine(0)));


            }
            catch (IOException e){
                e.printStackTrace();
            }

        }
    }
});

    }
    private void jsonParse() {

        String url="https://power.larc.nasa.gov/cgi-bin/v1/DataAccess.py?&request=execute&tempAverage=CLIMATOLOGY&identifier=SinglePoint&parameters=ALLSKY_SFC_LW_DWN,WS50M,WS10M&userCommunity=AG&lon="+Longitude+"&lat="+Latitude+"&outputList=JSON&siteElev=50&user=DOCUMENTATION";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray1=response.getJSONArray("features");
                    for (int i=0; i<jsonArray1.length();i++){
                        JSONObject props=jsonArray1.getJSONObject(0);
                        JSONObject feats=props.getJSONObject("properties");
                        JSONObject params=feats.getJSONObject("parameter");
                        JSONObject allsky=params.getJSONObject("ALLSKY_SFC_LW_DWN");

                        Double a1=allsky.getDouble("1");
                        Double a10=allsky.getDouble("10");
                        Double a11=allsky.getDouble("11");
                        Double a12=allsky.getDouble("12");
                        Double a13=allsky.getDouble("13");
                        Double a2=allsky.getDouble("2");
                        Double a3=allsky.getDouble("3");
                        Double a4=allsky.getDouble("4");
                        Double a5=allsky.getDouble("5");
                        Double a6=allsky.getDouble("6");
                        Double a7=allsky.getDouble("7");
                        Double a8=allsky.getDouble("8");
                        Double a9=allsky.getDouble("9");

                        JSONObject wind10=params.getJSONObject("WS10M");
                        Double wa1=wind10.getDouble("1");
                        Double wa10=wind10.getDouble("10");
                        Double wa11=wind10.getDouble("11");
                        Double wa12=wind10.getDouble("12");
                        Double wa13=wind10.getDouble("13");
                        Double wa2=wind10.getDouble("2");
                        Double wa3=wind10.getDouble("3");
                        Double wa4=wind10.getDouble("4");
                        Double wa5=wind10.getDouble("5");
                        Double wa6=wind10.getDouble("6");
                        Double wa7=wind10.getDouble("7");
                        Double wa8=wind10.getDouble("8");
                        Double wa9=wind10.getDouble("9");

                        JSONObject wind50=params.getJSONObject("WS50M");
                        Double wwa1=wind50.getDouble("1");
                        Double wwa10=wind50.getDouble("10");
                        Double wwa11=wind50.getDouble("11");
                        Double wwa12=wind50.getDouble("12");
                        Double wwa13=wind50.getDouble("13");
                        Double wwa2=wind50.getDouble("2");
                        Double wwa3=wind50.getDouble("3");
                        Double wwa4=wind50.getDouble("4");
                        Double wwa5=wind50.getDouble("5");
                        Double wwa6=wind50.getDouble("6");
                        Double wwa7=wind50.getDouble("7");
                        Double wwa8=wind50.getDouble("8");
                        Double wwa9=wind50.getDouble("9");

                        dataProgressBar.setVisibility(View.GONE);
                        btLocation.setText("CONTINUE");
                        btLocation.setEnabled(false);
                        showResult1.setText(
                                        String.valueOf(a1)+"\n"+
                                        String.valueOf(a2)+"\n"+
                                        String.valueOf(a3)+"\n"+
                                                String.valueOf(a4)+"\n"+
                                                String.valueOf(a5)+"\n"+
                                                String.valueOf(a6)+"\n"+
                                                String.valueOf(a7)+"\n"+
                                                String.valueOf(a8)+"\n"+
                                                String.valueOf(a9)+"\n"+
                                                String.valueOf(a10)+"\n"+
                                                String.valueOf(a11)+"\n"+
                                                String.valueOf(a12)+"\n");

                        showResult2.setText(
                                String.valueOf(wwa1)+"\n"+
                                        String.valueOf(wwa2)+"\n"+
                                        String.valueOf(wwa3)+"\n"+
                                        String.valueOf(wwa4)+"\n"+
                                        String.valueOf(wwa5)+"\n"+
                                        String.valueOf(wwa6)+"\n"+
                                        String.valueOf(wwa7)+"\n"+
                                        String.valueOf(wwa8)+"\n"+
                                        String.valueOf(wwa9)+"\n"+
                                        String.valueOf(wwa10)+"\n"+
                                        String.valueOf(wwa11)+"\n"+
                                        String.valueOf(wwa12)+"\n");

                        showResult3.setText(
                                String.valueOf(wa1)+"\n"+
                                        String.valueOf(wa2)+"\n"+
                                        String.valueOf(wa3)+"\n"+
                                        String.valueOf(wa4)+"\n"+
                                        String.valueOf(wa5)+"\n"+
                                        String.valueOf(wa6)+"\n"+
                                        String.valueOf(wa7)+"\n"+
                                        String.valueOf(wa8)+"\n"+
                                        String.valueOf(wa9)+"\n"+
                                        String.valueOf(wa10)+"\n"+
                                        String.valueOf(wa11)+"\n"+
                                        String.valueOf(wa12)+"\n");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 10000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 10000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        mQueue.add(request);
    }


}

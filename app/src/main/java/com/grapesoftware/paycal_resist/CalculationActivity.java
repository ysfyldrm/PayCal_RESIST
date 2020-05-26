package com.grapesoftware.paycal_resist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class CalculationActivity extends AppCompatActivity {
    String typeforuser,morning,peak,offpeak,tax,avgconsmonth,morconsmonth,avgmonthbill,resgendaily,resgenmonthly,storageperc,restype,turbinetype,turbinecount,consyear,solararea,latitude,longitude;
    ProgressBar dataProgressBar;
    private RequestQueue mQueue;
    Button btLocation, backButton;
    TextView showResult1, showResult2, showResult3;
    Double wwa13=0.00,wa13=0.00;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);

        btLocation = findViewById(R.id.bt_location);
        showResult1 = findViewById(R.id.solarIrValue_txt);
        showResult2 = findViewById(R.id.windSp50Val);
        showResult3 = findViewById(R.id.windSp10Val);
        mQueue = Volley.newRequestQueue(this);
        dataProgressBar = findViewById(R.id.dataProgress_Bar);



        SharedPreferences preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);

        typeforuser = preferences.getString("Type", "Consumer");
        latitude=preferences.getString("Latitude","1.00");
        longitude=preferences.getString("Longitude","1.00");
        morning=preferences.getString("Morning Tariff","1.00");
        peak=preferences.getString("Peak Tariff","1.00");
        offpeak=preferences.getString("Off Peak Tariff","1.00");
        tax=preferences.getString("Tax","1.00");
        avgconsmonth=preferences.getString("Cons Avg Month","1.00");
        morconsmonth=preferences.getString("Morning Cons Month","1.00");
        avgmonthbill=preferences.getString("Avg Month Bill","1.00");
        resgendaily=preferences.getString("RES Gen Daily","1.00");
        resgenmonthly=preferences.getString("RES Gen Monthly","1.00");
        storageperc=preferences.getString("Storage Percentage","1.00");
        restype=preferences.getString("RES Type","1.00");
        turbinecount=preferences.getString("Turbine Count","1.00");
        turbinetype=preferences.getString("Turbine Type","1.00");
        consyear=preferences.getString("Cons Year","1.00");
        solararea=preferences.getString("Solar Area","1.00");


        latitude = latitude.replace(",", ".");
        longitude = longitude.replace(",", ".");
        morning = morning.replace(",", ".");
        peak = peak.replace(",", ".");
        offpeak = offpeak.replace(",", ".");
        tax = tax.replace(",", ".");
        avgconsmonth = avgconsmonth.replace(",", ".");
        morconsmonth = morconsmonth.replace(",", ".");
        avgmonthbill = avgmonthbill.replace(",", ".");
        resgendaily = resgendaily.replace(",", ".");
        resgenmonthly = resgenmonthly.replace(",", ".");
        storageperc = storageperc.replace(",", ".");
        restype = restype.replace(",", ".");
        turbinecount = turbinecount.replace(",", ".");
        turbinetype = turbinetype.replace(",", ".");
        consyear = consyear.replace(",", ".");
        solararea = solararea.replace(",", ".");


        Toast.makeText(getApplicationContext(),
                "Type: "+typeforuser+"\n"+
                "Latitude: "+latitude+"\n"+
                "Longitude: "+longitude+"\n"+
                "Morning Tariff: "+morning+"\n"+
                "Peak Tariff: "+peak+"\n"+
                "Off Peak Tariff: "+offpeak+"\n"+
                "Tax: "+tax+"\n"+
                "Cons Month Avg: "+avgconsmonth+"\n"+
                "Morning Cons Month: "+morconsmonth+"\n"+
                "Avg Month Bill: "+avgmonthbill+"\n"+
                "RES Gen Daily: "+resgendaily+"\n"+
                "RES Gen Monthly: "+resgenmonthly+"\n"+
                "Storage Percentage: "+storageperc+"\n"+
                "RES Type: "+restype+"\n"+
                "Turbine Count: "+turbinecount+"\n"+
                "Turbine Type: "+turbinetype+"\n"+
                "Cons Year: "+consyear+"\n"+
                "Solar Area: "+solararea,Toast.LENGTH_LONG).show();


//        SharedPreferences settings = context.getSharedPreferences("session", Context.MODE_PRIVATE);
//        settings.edit().clear().commit();
        dataProgressBar.setVisibility(View.VISIBLE);

                jsonParse();
                hesaplawindnostorage();


    }

    private void jsonParse() {

        String url = "https://power.larc.nasa.gov/cgi-bin/v1/DataAccess.py?&request=execute&tempAverage=CLIMATOLOGY&identifier=SinglePoint&parameters=ALLSKY_SFC_LW_DWN,WS50M,WS10M&userCommunity=AG&lon=" + longitude + "&lat=" + latitude + "&outputList=JSON&siteElev=50&user=DOCUMENTATION";
        Log.e("LINK", url);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray1 = response.getJSONArray("features");
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        JSONObject props = jsonArray1.getJSONObject(0);
                        JSONObject feats = props.getJSONObject("properties");
                        JSONObject params = feats.getJSONObject("parameter");

                        JSONObject allsky = params.getJSONObject("ALLSKY_SFC_LW_DWN");
                        Double a1 = allsky.getDouble("1");
                        Double a10 = allsky.getDouble("10");
                        Double a11 = allsky.getDouble("11");
                        Double a12 = allsky.getDouble("12");
                        Double a13 = allsky.getDouble("13");
                        Double a2 = allsky.getDouble("2");
                        Double a3 = allsky.getDouble("3");
                        Double a4 = allsky.getDouble("4");
                        Double a5 = allsky.getDouble("5");
                        Double a6 = allsky.getDouble("6");
                        Double a7 = allsky.getDouble("7");
                        Double a8 = allsky.getDouble("8");
                        Double a9 = allsky.getDouble("9");

                        JSONObject wind10 = params.getJSONObject("WS10M");
                        Double wa1 = wind10.getDouble("1");
                        Double wa10 = wind10.getDouble("10");
                        Double wa11 = wind10.getDouble("11");
                        Double wa12 = wind10.getDouble("12");
                        wa13 = wind10.getDouble("13");
                        Double wa2 = wind10.getDouble("2");
                        Double wa3 = wind10.getDouble("3");
                        Double wa4 = wind10.getDouble("4");
                        Double wa5 = wind10.getDouble("5");
                        Double wa6 = wind10.getDouble("6");
                        Double wa7 = wind10.getDouble("7");
                        Double wa8 = wind10.getDouble("8");
                        Double wa9 = wind10.getDouble("9");

                        JSONObject wind50 = params.getJSONObject("WS50M");
                        Double wwa1 = wind50.getDouble("1");
                        Double wwa10 = wind50.getDouble("10");
                        Double wwa11 = wind50.getDouble("11");
                        Double wwa12 = wind50.getDouble("12");
                        wwa13 = wind50.getDouble("13");
                        Double wwa2 = wind50.getDouble("2");
                        Double wwa3 = wind50.getDouble("3");
                        Double wwa4 = wind50.getDouble("4");
                        Double wwa5 = wind50.getDouble("5");
                        Double wwa6 = wind50.getDouble("6");
                        Double wwa7 = wind50.getDouble("7");
                        Double wwa8 = wind50.getDouble("8");
                        Double wwa9 = wind50.getDouble("9");

                        dataProgressBar.setVisibility(View.GONE);


                        showResult1.setText(
                                String.valueOf(a1) + "\n" +
                                        String.valueOf(a2) + "\n" +
                                        String.valueOf(a3) + "\n" +
                                        String.valueOf(a4) + "\n" +
                                        String.valueOf(a5) + "\n" +
                                        String.valueOf(a6) + "\n" +
                                        String.valueOf(a7) + "\n" +
                                        String.valueOf(a8) + "\n" +
                                        String.valueOf(a9) + "\n" +
                                        String.valueOf(a10) + "\n" +
                                        String.valueOf(a11) + "\n" +
                                        String.valueOf(a12) + "\n");

                        showResult2.setText(
                                String.valueOf(wwa1) + "\n" +
                                        String.valueOf(wwa2) + "\n" +
                                        String.valueOf(wwa3) + "\n" +
                                        String.valueOf(wwa4) + "\n" +
                                        String.valueOf(wwa5) + "\n" +
                                        String.valueOf(wwa6) + "\n" +
                                        String.valueOf(wwa7) + "\n" +
                                        String.valueOf(wwa8) + "\n" +
                                        String.valueOf(wwa9) + "\n" +
                                        String.valueOf(wwa10) + "\n" +
                                        String.valueOf(wwa11) + "\n" +
                                        String.valueOf(wwa12) + "\n");

                        showResult3.setText(
                                String.valueOf(wa1) + "\n" +
                                        String.valueOf(wa2) + "\n" +
                                        String.valueOf(wa3) + "\n" +
                                        String.valueOf(wa4) + "\n" +
                                        String.valueOf(wa5) + "\n" +
                                        String.valueOf(wa6) + "\n" +
                                        String.valueOf(wa7) + "\n" +
                                        String.valueOf(wa8) + "\n" +
                                        String.valueOf(wa9) + "\n" +
                                        String.valueOf(wa10) + "\n" +
                                        String.valueOf(wa11) + "\n" +
                                        String.valueOf(wa12) + "\n");

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

    private void hesaplawindnostorage(){

        String price;
        int turbinetypevalue;
        int ratedcapacity;
        Double avgwind=0.00;
        Double windyearkwh,winddaykwh,windanualprofit,windcapitalcost,payback;
        Double windyearcost=0.00;
        Double [] cashflow = new Double[24];
        avgwind=(wwa13+wa13)/2;

        if (turbinetype.equals("1 Kw")) {
            price="3511.57";
            turbinetypevalue=1;
        }
        else if (turbinetype.equals("3 Kw")){
            price="3108.84";
            turbinetypevalue=3;

        }
        else{
            price="2579.95";
            turbinetypevalue=10;
        }

        ratedcapacity=Integer.valueOf(turbinecount)*turbinetypevalue;
        windyearkwh=avgwind*8760;
        winddaykwh=windyearkwh/365;

        if (Double.valueOf(consyear)>=windyearkwh){
            windanualprofit=(windyearkwh*Double.valueOf(morning)*Double.valueOf(tax))+windyearkwh*Double.valueOf(morning);
        }
        else {
            windanualprofit=(Double.valueOf(consyear)*Double.valueOf(morning)+Double.valueOf(consyear)*Double.valueOf(morning)*Double.valueOf(tax))+(windyearkwh-Double.valueOf(consyear))*Double.valueOf(morning);
        }

        windcapitalcost=ratedcapacity*Double.valueOf(price);
        payback=windcapitalcost/(windanualprofit-windyearcost);

        for (int i=0; i<24 ;i++){
            cashflow[i]=-windcapitalcost+((windanualprofit)*i);
            //Toast.makeText(getApplicationContext(),String.valueOf(cashflow[i]),Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(getApplicationContext(),"WindAnualProfit: "+windanualprofit+"\nWind Year Cost: "+ windyearcost+"Payback: "+String.valueOf(payback)+"\n"+"Wind Year Kwh: "+String.valueOf(windyearkwh),Toast.LENGTH_LONG).show();



    }

}

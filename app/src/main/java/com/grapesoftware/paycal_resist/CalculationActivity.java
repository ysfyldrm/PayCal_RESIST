package com.grapesoftware.paycal_resist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class CalculationActivity extends AppCompatActivity {
    final Context context = this;
    String solarselection, adress, country, solarpower, peakmcp, morningmcp, avgpeakmcp, avgmorningmcp, avgyearmcp, typeforuser, morning, peak, offpeak, tax, avgconsmonth, morconsmonth, avgmonthbill, resgendaily, resgenmonthly, storageperc, restype, turbinetype, turbinecount, consyear, solararea, latitude, longitude, storagetype;
    Double eff, a13, windkwarray, storagepercentagee, dod, storage_price, omprice, dsolarpower, dpeakmcp, dmorningmcp, davgpeakmcp, davgmorningmcp, davgyearmcp, dlatitude, dlongitude, dmorning, dpeak, doffpeak, dtax, davgconsmonth, dmorconsmonth, davgmonthbill, dresgendaily, dresgenmonthly, dstorageperc, drestype, dturbinecount, dconsyear, dsolararea;
    Double wwa13 = 0.00, wa13 = 0.00, payback = 0.0, price = 0.0, ratedcapacity = 0.0, windanualprofit = 0.0, windcapitalcost = 0.0, windyearcost = 0.0, windyearkwh = 0.0, winddaykwh = 0.0;
    Double a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12;
    TextView txtView;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    //ProgressBar progressBar;
    Handler hdlr = new Handler();
    int CounterLoader = 0;
    int loadercontentcounter = 0;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);

        mQueue = Volley.newRequestQueue(this);
        txtView = findViewById(R.id.txtLoading);
        //progressBar = (ProgressBar) findViewById(R.id.spin_kit);
        //Wave wave = new Wave();
        //progressBar.setIndeterminateDrawable(wave);
        //progressBar.setVisibility(View.INVISIBLE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("paycal-resist");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d("TAG", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
        final String[] LoaderContext = {"Hold On", "Gathering Values", "Testing Processor", "Benching Network", "Processing Equations", "Overflowing Stack", "Checking Accuracy", "Creating Cashflow Chart", "Calculating Payback", "Ready.", "Ready..", "Ready..."};

        new Thread(new Runnable() {
            public void run() {
                while (CounterLoader < 180) {
                    CounterLoader += 1;
                    // Update the progress bar and display the current value in text view
                    hdlr.post(new Runnable() {
                        public void run() {
                            if (CounterLoader % 20 == 0) {
                                loadercontentcounter++;
                            }
                            txtView.setText(LoaderContext[loadercontentcounter]);
                        }
                    });
                    try {
                        // Sleep for 100 milliseconds to show the progress slowly.
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


        preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);
        editor = preferences.edit();

        typeforuser = preferences.getString("Type", "Consumer");
        latitude = preferences.getString("Latitude", "1.00");
        longitude = preferences.getString("Longitude", "1.00");
        morning = preferences.getString("Morning Tariff", "1.00");
        peak = preferences.getString("Peak Tariff", "1.00");
        offpeak = preferences.getString("Off Peak Tariff", "1.00");
        tax = preferences.getString("Tax", "1.00");
        avgconsmonth = preferences.getString("Cons Avg Month", "1.00");
        morconsmonth = preferences.getString("Morning Cons Month", "1.00");
        avgmonthbill = preferences.getString("Avg Month Bill", "1.00");
        resgendaily = preferences.getString("RES Gen Daily", "1.00");
        resgenmonthly = preferences.getString("RES Gen Monthly", "1.00");
        storageperc = preferences.getString("Storage Percentage", "1.00");
        restype = preferences.getString("RES Type", "PV SOLAR");
        turbinecount = preferences.getString("Turbine Count", "1.00");
        turbinetype = preferences.getString("Turbine Type", "1500.0");
        consyear = preferences.getString("Cons Year", "1.00");
        solararea = preferences.getString("Solar Area", "1.00");
        solarpower = preferences.getString("Solar Power", "1.00");
        storagetype = preferences.getString("Storage Type", null);
        adress = preferences.getString("Adress", null);
        country = preferences.getString("Country", null);
        peakmcp = preferences.getString("Peak MCP", "1.00");
        morningmcp = preferences.getString("Morning MCP", "1.00");
        avgpeakmcp = preferences.getString("AVG Peak MCP", "1.00");
        avgmorningmcp = preferences.getString("AVG Morning MCP", "1.00");
        avgyearmcp = preferences.getString("AVG Year MCP", "1.00");
        solarselection = preferences.getString("Solar Selection", "Power");


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
        storagepercentagee = Double.valueOf(storageperc) / 100;
        restype = restype.replace(",", ".");
        turbinecount = turbinecount.replace(",", ".");
        turbinetype = turbinetype.replace(",", ".");
        solararea = solararea.replace(",", ".");
        peakmcp = peakmcp.replace(",", ".");
        morningmcp = morningmcp.replace(",", ".");
        avgpeakmcp = avgpeakmcp.replace(",", ".");
        avgmorningmcp = avgmorningmcp.replace(",", ".");
        avgyearmcp = avgyearmcp.replace(",", ".");
        solarpower = solarpower.replace(",", ".");

        dlatitude = ParseDouble(latitude);
        dlongitude = ParseDouble(longitude);
        dmorning = ParseDouble(morning);
        dpeak = ParseDouble(peak);
        doffpeak = ParseDouble(offpeak);
        dtax = ParseDouble(tax);
        davgconsmonth = ParseDouble(avgconsmonth);
        dmorconsmonth = ParseDouble(morconsmonth);
        davgmonthbill = ParseDouble(avgmonthbill);
        dresgendaily = ParseDouble(resgendaily);
        dresgenmonthly = ParseDouble(resgenmonthly);
        dstorageperc = ParseDouble(storageperc);
        drestype = ParseDouble(restype);
        dturbinecount = ParseDouble(turbinecount);
        dconsyear = dmorconsmonth * 12;
        dsolararea = ParseDouble(solararea);
        dsolarpower = ParseDouble(solarpower);
        dpeakmcp = ParseDouble(peakmcp);
        dmorningmcp = ParseDouble(morningmcp);
        davgpeakmcp = ParseDouble(avgpeakmcp);
        davgmorningmcp = ParseDouble(avgmorningmcp);
        davgyearmcp = ParseDouble(avgyearmcp);


//        SharedPreferences settings = context.getSharedPreferences("session", Context.MODE_PRIVATE);
//        settings.edit().clear().commit();
        //progressBar.setVisibility(View.VISIBLE);


        if (storagetype.equals("Thermal")) {

            eff = 0.9;
            dod = 1.0;
            storage_price = 0.32;
            omprice = 0.1;


        } else if (storagetype.equals("Li-ion")) {
            eff = 0.96;
            dod = 0.9;
            storage_price = 1260.0;
            omprice = 10.0;

        }
        if (storagetype.equals("Lead Acid")) {
            eff = 0.8;
            dod = 0.6;
            storage_price = 550.0;
            omprice = 10.0;

        }
        jsonParse();
    }


    double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch (Exception e) {
                return -1;   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        } else return 0;
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
                        a1 = allsky.getDouble("1");
                        a10 = allsky.getDouble("10");
                        a11 = allsky.getDouble("11");
                        a12 = allsky.getDouble("12");
                        a13 = allsky.getDouble("13");
                        a2 = allsky.getDouble("2");
                        a3 = allsky.getDouble("3");
                        a4 = allsky.getDouble("4");
                        a5 = allsky.getDouble("5");
                        a6 = allsky.getDouble("6");
                        a7 = allsky.getDouble("7");
                        a8 = allsky.getDouble("8");
                        a9 = allsky.getDouble("9");


//                        JSONObject wind10 = params.getJSONObject("WS10M");
//                        Double wa1 = wind10.getDouble("1");
//                        Double wa10 = wind10.getDouble("10");
//                        Double wa11 = wind10.getDouble("11");
//                        Double wa12 = wind10.getDouble("12");
//                        wa13 = wind10.getDouble("13");
//                        Double wa2 = wind10.getDouble("2");
//                        Double wa3 = wind10.getDouble("3");
//                        Double wa4 = wind10.getDouble("4");
//                        Double wa5 = wind10.getDouble("5");
//                        Double wa6 = wind10.getDouble("6");
//                        Double wa7 = wind10.getDouble("7");
//                        Double wa8 = wind10.getDouble("8");
//                        Double wa9 = wind10.getDouble("9");

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

                        //progressBar.setVisibility(View.GONE);
                    }

                    if (typeforuser.equals("Consumer")) {

                        if (storagetype.equals("NoStorage") && restype.equals("WIND")) {
                            hesaplawindnostorage();
                        } else if (!storagetype.equals("NoStorage") && restype.equals("WIND")) {
                            hesaplawindwithstorage();
                        } else if (storagetype.equals("NoStorage") && restype.equals("SOLAR")) {
                            hesaplasolarnostorage();
                        } else if (!storagetype.equals("NoStorage") && restype.equals("SOLAR")) {
                            hesaplasolarwithstorage();
                        }
                    } else if (typeforuser.equals("Prosumer")) {

                        if (restype.equals("WIND")) {
                            hesaplawindpro();
                        } else if (restype.equals("SOLAR")) {
                            hesaplapvpro();
                        }
                    } else if (typeforuser.equals("Supplier")) {

                        if (storagetype.equals("NoStorage") && restype.equals("WIND")) {
                            hesaplaWindNoStorageSupplier();
                        } else if (!storagetype.equals("NoStorage") && restype.equals("WIND")) {
                            hesaplaWindWithStorageSupplier();
                        } else if (storagetype.equals("NoStorage") && restype.equals("SOLAR")) {
                            hesaplaSolarNoStorageSupplier();
                        } else if (!storagetype.equals("NoStorage") && restype.equals("SOLAR")) {
                            hesaplaSolarWithStorageSupplier();
                        }
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

    private void hesaplaSolarNoStorageSupplier() {
        Double pvnum;
        Double panelpower = 0.265;
        Double panelarea = 1.63;
        Double area;
        Double pvpower;
        Double pvcostconsumer = 1300.0;
        Double omcostconsumer = 20.0;
        Double pvcost = 1100.0;
        Double pvomcost = 17.0;
        Double[] pvrad = {a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12};
        Double[] pvgenmonth = new Double[12];
        Double pvgenyear = 0.0;

        if (solarselection.equals("Power")) {
            pvnum = dsolarpower / panelpower;
            area = pvnum * panelarea;
            pvpower = dsolarpower;
        } else {
            pvnum = dsolararea / panelarea;
            pvpower = pvnum * panelpower;
            area = dsolararea;
        }
        int[] Monthday = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        for (int i = 0; i < 12; i++) {
            pvgenmonth[i] = Monthday[i] * pvrad[i] * area * 0.125;
            pvgenyear = pvgenyear + pvgenmonth[i];
        }
        Double pvgenaverageday = pvgenyear / 365;
        Double pvgenaveragemonth = pvgenyear / 12;
        Double pvcapitalcost = pvpower * pvcost;
        Double pvyearlycost = pvpower * pvomcost;

        Double[] revenuemonth = new Double[12];
        Double[] morningmcparray = {0.0465, 0.0496, 0.047, 0.0336, 0.0293, 0.035, 0.0534, 0.0517, 0.0518, 0.0509, 0.053, 0.0526};
        Double revenueyear = 0.0;
        for (int i = 0; i < 12; i++) {
            revenuemonth[i] = (pvgenmonth[i] * morningmcparray[i]);
            revenueyear = revenueyear + revenuemonth[i];
        }
        Double pvprofityear = revenueyear - pvyearlycost;
        payback = pvcapitalcost / pvprofityear;
        Double[] cashflow = new Double[24];

        for (int i = 0; i < 24; i++) {
            cashflow[i] = -pvcapitalcost + (pvprofityear * i);
            editor.putFloat("Cashflow" + i, cashflow[i].floatValue());
        }
        editor.putString("Payback", payback.toString());

        editor.commit();
        displayResults();
    }

    private void hesaplaSolarWithStorageSupplier() {


        Double pvnum;
        Double panelpower = 0.265;
        Double panelarea = 1.63;
        Double area;
        Double pvpower;
        Double pvcostconsumer = 1300.0;
        Double omcostconsumer = 20.0;
        Double pvcost = 1100.0;
        Double pvomcost = 17.0;
        Double[] pvrad = {a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12};
        Double[] pvgenmonth = new Double[12];
        Double pvgenyear = 0.0;

        if (solarselection.equals("Power")) {
            pvnum = dsolarpower / panelpower;
            area = pvnum * panelarea;
            pvpower = dsolarpower;
        } else {
            pvnum = dsolararea / panelarea;
            pvpower = pvnum * panelpower;
            area = dsolararea;
        }
        int[] Monthday = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        for (int i = 0; i < 12; i++) {
            pvgenmonth[i] = Monthday[i] * pvrad[i] * area * 0.125;
            pvgenyear = pvgenyear + pvgenmonth[i];
        }
        Double pvgenaverageday = pvgenyear / 365;
        Double pvgenaveragemonth = pvgenyear / 12;
        Double pvcapitalcost = pvpower * pvcost;
        Double pvyearlycost = pvpower * pvomcost;
        Double storageusedcapacity = pvgenaverageday * dstorageperc;
        Double storagecapacity = (storageusedcapacity + (storageusedcapacity * (1 - eff * dod)));
        Double storagecapitalcost = storagecapacity * storage_price;
        Double storageyearlycost = storagecapacity * omprice;
        Double[] revenuemonth = new Double[12];
        Double[] morningmcparray = {0.0465, 0.0496, 0.047, 0.0336, 0.0293, 0.035, 0.0534, 0.0517, 0.0518, 0.0509, 0.053, 0.0526};
        Double[] peakmcparray = {0.0527, 0.0577, 0.0548, 0.0434, 0.0444, 0.0457, 0.0561, 0.0559, 0.0547, 0.0551, 0.0569, 0.0548};

        Double revenueyear = 0.0;
        for (int i = 0; i < 12; i++) {
            revenuemonth[i] = (pvgenmonth[i] * dstorageperc * peakmcparray[i]) + (pvgenmonth[i] * (1 - dstorageperc) * morningmcparray[i]);
            revenueyear = revenueyear + revenuemonth[i];
        }

        Double systemcost = storagecapitalcost + pvcapitalcost;
        Double systemyearlycost = storageyearlycost + pvyearlycost;
        Double yearlyprofit = revenueyear - systemyearlycost;
        payback = systemcost / yearlyprofit;

        Double[] cashflow = new Double[24];

        for (int i = 0; i < 24; i++) {
            cashflow[i] = -systemcost + (yearlyprofit * i);
            editor.putFloat("Cashflow" + i, cashflow[i].floatValue());
        }
        editor.putString("Payback", payback.toString());

        editor.commit();
        displayResults();

    }

    private void hesaplaWindWithStorageSupplier() {
        windkwarraycalculate();
        ratedcapacity = dturbinecount * 1500;
        Double turbineprice = 1154.76;
        Double omcost1500kw = 0.03;
        Double windyearkwh = windkwarray * 8760 * dturbinecount;
        Double winddaykwh = windyearkwh / 365;
        Double storageusedcapacity = winddaykwh * dstorageperc;
        Double storagecapacity = (storageusedcapacity + (storageusedcapacity * (1 - eff * dod)));
        Double storagecapitalcost = storagecapacity * storage_price;
        Double storageyearlycost = storagecapacity * omprice;
        Double windanualprofit = windyearkwh * (1 - dstorageperc) * davgmorningmcp + windyearkwh * dstorageperc * davgpeakmcp;
        Double windyearlycost = windyearkwh * omcost1500kw;
        Double windcapitalcost = ratedcapacity * turbineprice;
        Double systemcost = storagecapitalcost + windcapitalcost;
        Double systemyearlycost = windyearlycost + storageyearlycost;
        Double systemprofit = windanualprofit - systemyearlycost;
        payback = windcapitalcost / (systemprofit);
        Double[] cashflow = new Double[24];

        for (int i = 0; i < 24; i++) {
            cashflow[i] = -systemcost + (systemprofit * i);
            editor.putFloat("Cashflow" + i, cashflow[i].floatValue());
        }
        editor.putString("Payback", payback.toString());
        editor.putString("Wind Year Kwh", windyearkwh.toString());
        editor.putString("Wind Capital Cost", windcapitalcost.toString());
        editor.putString("Wind Yearly Cost", windyearlycost.toString());
        editor.putString("Storage Capacity", storagecapacity.toString());
        editor.putString("Storage Capital Cost", storagecapitalcost.toString());
        editor.commit();
        displayResults();
    }

    private void hesaplaWindNoStorageSupplier() {
        ratedcapacity = dturbinecount * 1500;
        Double turbineprice = 1154.76;
        Double omcost = 0.03;
        Double turbineratedpower = ratedcapacity;
        windkwarraycalculate();
        Double windyearkwh = windkwarray * 8760 * dturbinecount;
        Double winddaykwh = windyearkwh / 365;
        Double windannualprofit = windyearkwh * davgmorningmcp;
        Double windyearlycost = windyearkwh * omcost;
        Double windcapitalcost = ratedcapacity * turbineprice;
        payback = windcapitalcost / (windannualprofit - windyearlycost);
        Double[] cashflow = new Double[24];
        Double newavgmonthbill = davgmonthbill - (windannualprofit - windyearlycost) / 12;

        for (int i = 0; i < 24; i++) {
            cashflow[i] = -windcapitalcost + ((windannualprofit - windyearlycost) * i);
            editor.putFloat("Cashflow" + i, cashflow[i].floatValue());
        }
        editor.putString("Payback", payback.toString());
        editor.putString("Wind Year Kwh", windyearkwh.toString());
        editor.putString("Wind Capital Cost", windcapitalcost.toString());
        editor.putString("Wind Yearly Cost", windyearlycost.toString());
        editor.commit();
        displayResults();
    }


    private void displayResults() {

        Intent intent = new Intent(CalculationActivity.this, ChartsActivity.class);
        startActivity(intent);
        finish();
    }


    private void windkwarraycalculate() {

        if (typeforuser.equals("Consumer") || typeforuser.equals("Prosumer")) {
            if (turbinetype.equals("1 Kw")) {
                if (wwa13 <= 1.8) {
                    windkwarray = 0.0;
                } else if (wwa13 > 1.8 && wwa13 <= 2.7) {
                    windkwarray = 25.0;
                } else if (wwa13 > 2.7 && wwa13 <= 3.6) {
                    windkwarray = 75.0;
                } else if (wwa13 > 3.6 && wwa13 <= 4.5) {
                    windkwarray = 125.0;
                } else if (wwa13 > 4.5 && wwa13 <= 5.4) {
                    windkwarray = 200.0;
                } else if (wwa13 > 5.4 && wwa13 <= 6.3) {
                    windkwarray = 275.0;
                } else if (wwa13 > 6.3 && wwa13 <= 7.2) {
                    windkwarray = 325.0;
                } else {
                    windkwarray = 375.0;
                }
            } else if (turbinetype.equals("3 Kw")) {
                if (wwa13 <= 1.8) {
                    windkwarray = 0.0;
                } else if (wwa13 > 1.8 && wwa13 <= 2.7) {
                    windkwarray = 75.0;
                } else if (wwa13 > 2.7 && wwa13 <= 3.6) {
                    windkwarray = 160.0;
                } else if (wwa13 > 3.6 && wwa13 <= 4.5) {
                    windkwarray = 325.0;
                } else if (wwa13 > 4.5 && wwa13 <= 5.4) {
                    windkwarray = 525.0;
                } else if (wwa13 > 5.4 && wwa13 <= 6.3) {
                    windkwarray = 675.0;
                } else if (wwa13 > 6.3 && wwa13 <= 7.2) {
                    windkwarray = 825.0;
                } else {
                    windkwarray = 1000.0;
                }
            } else if (turbinetype.equals("10 Kw")) {
                if (wwa13 <= 3.6) {
                    windkwarray = 410.0;
                } else if (wwa13 > 3.6 && wwa13 <= 4.5) {
                    windkwarray = 820.0;
                } else if (wwa13 > 4.5 && wwa13 <= 5.4) {
                    windkwarray = 1377.0;
                } else if (wwa13 > 5.4 && wwa13 <= 6.3) {
                    windkwarray = 2027.0;
                } else {
                    windkwarray = 2700.0;
                }
            }
        } else if (typeforuser.equals("Supplier")) {
            if (wwa13 <= 3.0) {
                windkwarray = 0.0;
            } else if (wwa13 > 3.0 && wwa13 <= 4.5) {
                windkwarray = 1800.0;
            } else if (wwa13 > 4.5 && wwa13 <= 6.0) {
                windkwarray = 6600.0;
            } else if (wwa13 > 6.0 && wwa13 <= 7.5) {
                windkwarray = 12000.0;
            } else if (wwa13 > 7.5 && wwa13 <= 9.0) {
                windkwarray = 21600.0;
            } else if (wwa13 > 9.0 && wwa13 <= 10.5) {
                windkwarray = 31200.0;
            } else if (wwa13 > 10.5 && wwa13 <= 12.0) {
                windkwarray = 34200.0;
            } else {
                windkwarray = 36000.0;
            }
        }
    }

    private void hesaplawindpro() {
        Double resgenyear = dresgenmonthly * 12;
        Double storageusedcapacity = dresgendaily * dstorageperc;
        Double storagecapacity = (storageusedcapacity + (storageusedcapacity * (1 - eff * dod)));
        Double storagecapitalcost = storagecapacity * storage_price;
        Double storageyearlycost = storagecapacity * omprice;
        Double systemrevenue;
        Double[] cashflow = new Double[24];

        if (davgconsmonth >= dresgenmonthly * (1 - dstorageperc)) {
            systemrevenue = (resgenyear * dstorageperc * dpeak) - (resgenyear * dstorageperc * dmorning + (resgenyear * dstorageperc * dmorning * dtax));
        } else {
            systemrevenue = (resgenyear * dstorageperc * dpeak + (resgenyear * dstorageperc * dpeak * dtax)) - (resgenyear * dstorageperc * dmorning);
        }
        Double systemprofit = systemrevenue - storageyearlycost;
        payback = storagecapitalcost / systemprofit;

        for (int i = 0; i < 24; i++) {
            cashflow[i] = -storagecapitalcost + (systemprofit * i);
            editor.putFloat("Cashflow" + i, cashflow[i].floatValue());
        }
        editor.commit();
        displayResults();
    }

    private void hesaplapvpro() {
        Double resgenyear = dresgenmonthly * 12;
        Double storageusedcapacity = dresgendaily * dstorageperc;
        Double storagecapacity = (storageusedcapacity + (storageusedcapacity * (1 - eff * dod)));
        Double storagecapitalcost = storagecapacity * storage_price;
        Double storageyearlycost = storagecapacity * omprice;
        Double systemrevenue;
        Double[] cashflow = new Double[24];

        if (dmorconsmonth >= dresgenmonthly * (1 - dstorageperc)) {
            systemrevenue = (resgenyear * dstorageperc * dpeak) - (resgenyear * dstorageperc * dmorning + (resgenyear * dstorageperc * dmorning * dtax));
        } else {
            systemrevenue = (resgenyear * dstorageperc * dpeak + (resgenyear * dstorageperc * dpeak * dtax)) - (resgenyear * dstorageperc * dmorning);
        }
        Double systemprofit = systemrevenue - storageyearlycost;
        payback = storagecapitalcost / systemprofit;

        for (int i = 0; i < 24; i++) {
            cashflow[i] = -storagecapitalcost + (systemprofit * i);
            editor.putFloat("Cashflow" + i, cashflow[i].floatValue());
        }
        editor.commit();

        displayResults();

    }

    private void hesaplawindnostorage() {
        Double turbinetypevalue;
        Double[] cashflow = new Double[24];
        windkwarraycalculate();
        if (turbinetype.equals("1 Kw")) {
            price = 3466.45;
            turbinetypevalue = 1.0;
        } else if (turbinetype.equals("3 Kw")) {
            price = 3068.89;
            turbinetypevalue = 3.0;

        } else {
            price = 2545.67;
            turbinetypevalue = 10.0;
        }
        ratedcapacity = dturbinecount * turbinetypevalue;
        windyearkwh = windkwarray * 12 * dturbinecount;
        winddaykwh = windyearkwh / 365;

        if (dconsyear >= windyearkwh) {
            windanualprofit = (windyearkwh * dmorning * dtax) + windyearkwh * dmorning;
        } else {
            windanualprofit = (dconsyear * dmorning + dconsyear * dmorning * dtax) + (windyearkwh - dconsyear) * dmorning;
        }

        windcapitalcost = ratedcapacity * price;
        payback = windcapitalcost / (windanualprofit - windyearcost);
        Double newavgmonthbill = davgmonthbill - (windanualprofit - windyearcost) / 12;

        for (int i = 0; i < 24; i++) {
            cashflow[i] = -windcapitalcost + ((windanualprofit) * i);

            Log.e("CASHFLOW", "Cashflow" + i + ": " + cashflow[i]);

            editor.putFloat("Cashflow" + i, cashflow[i].floatValue());
        }
        editor.putString("Payback", payback.toString());
        editor.putString("Wind Month Kwh", windkwarray.toString());
        editor.putString("Wind Day Kwh", winddaykwh.toString());
        editor.putString("New Avg Month Bill", newavgmonthbill.toString());
        editor.putString("Wind Capital Cost", windcapitalcost.toString());
        editor.commit();
        displayResults();

    }

    private void hesaplawindwithstorage() {

        if (!storagetype.equals("NoStorage")) {
            Double storagepercentage = dstorageperc / 100;
            Double turbinetypevalue;
            Double systemprofit, systemyearlycost, storageusedcapacity, storagecapacity, storagecapitalcost, storageyearlycost, systemcost;
            Double[] cashflow = new Double[24];
            windkwarraycalculate();
            if (turbinetype.equals("1 Kw")) {
                price = 3466.45;
                turbinetypevalue = 1.0;
            } else if (turbinetype.equals("3 Kw")) {
                price = 3068.89;
                turbinetypevalue = 3.0;
            } else {
                price = 2545.67;
                turbinetypevalue = 10.0;
            }
            ratedcapacity = dturbinecount * turbinetypevalue;
            windyearkwh = windkwarray * 12 * dturbinecount;
            winddaykwh = windyearkwh / 365;
            storageusedcapacity = winddaykwh * storagepercentage;
            storagecapacity = (storageusedcapacity + (storageusedcapacity * (1 - eff * dod)));
            storagecapitalcost = storagecapacity * storage_price;
            storageyearlycost = storagecapacity * omprice;
            if (dconsyear >= windyearkwh * (1 - storagepercentage)) {
                windanualprofit = (windyearkwh * (1 - storagepercentage) * dmorning * dtax)
                        + windyearkwh * (1 - storagepercentage) * dmorning
                        + (windyearkwh * storagepercentage * dpeak * dtax)
                        + (windyearkwh * storagepercentage * dpeak);
            } else {
                windanualprofit = (dconsyear * dmorning
                        + dconsyear * dmorning * dtax)
                        + (windyearkwh * (1 - storagepercentage)
                        - dconsyear) * dmorning
                        + (windyearkwh * storagepercentage * dpeak * dtax)
                        + (windyearkwh * storagepercentage * dpeak);
            }
            windcapitalcost = ratedcapacity * price;
            systemcost = storagecapitalcost + windcapitalcost;
            systemyearlycost = storageyearlycost + windyearcost;
            systemprofit = windanualprofit - systemyearlycost;
            payback = windcapitalcost / (systemprofit);
            Double newavgmonthbill = davgmonthbill - systemprofit / 12;

            for (int i = 0; i < 24; i++) {
                cashflow[i] = -systemcost + ((systemprofit) * i);

                editor.putFloat("Cashflow" + i, cashflow[i].floatValue());
                Log.e("CASHFLOW WITH STORAGE", "Cashflow" + i + ": " + cashflow[i]);
            }
            editor.putString("Payback", payback.toString());
            editor.putString("Wind Month Kwh", windkwarray.toString());
            editor.putString("Wind Day Kwh", winddaykwh.toString());
            editor.putString("New Avg Month Bill", newavgmonthbill.toString());
            editor.putString("Wind Capital Cost", windcapitalcost.toString());
            editor.putString("Storage Capacity", storagecapacity.toString());
            editor.putString("Storage Capital Cost", storagecapitalcost.toString());
            editor.commit();
            displayResults();
        }

    }

    private void hesaplasolarnostorage() {

        Double panelarea = 1.63;
        Double panelpower = 0.265;
        Double pvcostconsumer = 1300.0;
        Double omcostconsumer = 20.0;
        Double pvcost = 1100.0;
        Double pvomcost = 17.0;
        Double[] pvrad = {a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12};
        Double[] pvgenmonth = new Double[12];
        Double pvgenyear = 0.0;

        int[] Monthday = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        for (int i = 0; i < 12; i++) {
            pvgenmonth[i] = Monthday[i] * pvrad[i] * dsolararea * 0.125;
            pvgenyear = pvgenyear + pvgenmonth[i];

            Log.e("PVGENMONTH", "Month " + i + ": " + pvgenmonth[i]);
        }
        Double pvgenaveragemonth = pvgenyear / 12;
        Double pvgenaverageday = pvgenyear / 365;
        Double panelnum = dsolararea / panelarea;
        Double pvpower = panelnum * panelpower;
        Double revenueyear = 0.0;
        Double[] revenuemonth = new Double[12];
        for (int i = 0; i < 12; i++) {
            if (dmorconsmonth >= pvgenaveragemonth) {
                revenuemonth[i] = ((pvgenmonth[i] * dmorning) * dtax + (pvgenmonth[i] * dmorning));
                revenueyear = revenueyear + revenuemonth[i];
            } else {
                revenuemonth[i] = ((dmorconsmonth * dmorning) * dtax + (dmorconsmonth * dmorning)) + (pvgenmonth[i] - dmorconsmonth) * dmorning;
                revenueyear = revenueyear + revenuemonth[i];
            }
        }
        Double pvcapitalcost = pvpower * pvcostconsumer;
        Double pvyearlycost = pvpower * omcostconsumer;
        Double pvprofityear = revenueyear - pvyearlycost;
        Double pvprofitmonth = pvprofityear / 12;
        payback = pvcapitalcost / pvprofityear;
        Double[] cashflow = new Double[24];
        for (int i = 0; i < 24; i++) {
            cashflow[i] = -pvcapitalcost + (pvprofityear * i);

            editor.putFloat("Cashflow" + i, cashflow[i].floatValue());
            Log.e("PV NO STORAGE", "Cashflow" + i + ": " + cashflow[i]);
        }
        editor.commit();
        Double newavgmonthbill = davgmonthbill - pvprofitmonth;
        displayResults();

    }

    private void hesaplasolarwithstorage() {
        Double panelarea = 1.63;
        Double panelpower = 0.265;
        Double pvcostconsumer = 1300.0;
        Double omcostconsumer = 20.0;
        Double pvcost = 1100.0;
        Double pvomcost = 17.0;
        Double[] pvrad = {a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12};
        Double[] pvgenmonth = new Double[12];
        Double pvgenyear = 0.0;
        int[] Monthday = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        for (int i = 0; i < 12; i++) {
            pvgenmonth[i] = Monthday[i] * pvrad[i] * dsolararea * 0.125;
            pvgenyear = pvgenyear + pvgenmonth[i];
        }
        Double pvgenaveragemonth = pvgenyear / 12;
        Double pvgenaverageday = pvgenyear / 365;
        Double panelnum = dsolararea / panelarea;
        Double pvpower = panelnum * panelpower;

        Double storageusedcapacity = pvgenaverageday * dstorageperc;
        Double storagecapacity = (storageusedcapacity + (storageusedcapacity * (1 - eff * dod)));
        Double storagecapitalcost = storagecapacity * storage_price;
        Double storagemonthlycost = storagecapacity * omprice;

//        Double revenueyear=0.0;
//        Double [] revenuemonth=new Double[12];
        Double systemrevenue;
        Double morningsell, morningsaving, peaksaving;
        if (dmorconsmonth >= pvgenaveragemonth * (1 - dstorageperc)) {
            systemrevenue = (pvgenyear * dstorageperc * dpeak) + (pvgenyear * (1 - dstorageperc) * dmorning);
            systemrevenue = systemrevenue + systemrevenue * dtax;
        } else {
            morningsell = (pvgenyear * (1 - dstorageperc) - dmorconsmonth * 12) * dmorning;
            morningsaving = dmorconsmonth * 12 * dmorning;
            morningsaving = morningsaving + (morningsaving * dtax);
            peaksaving = pvgenyear * dstorageperc * dpeak;
            peaksaving = peaksaving * (peaksaving * dtax);
            systemrevenue = morningsell + morningsaving + peaksaving;
        }

        Double pvcapitalcost = pvpower * pvcostconsumer;
        Double pvyearlycost = pvpower * omcostconsumer;
        Double systemcost = storagecapitalcost + pvcapitalcost;
        Double systemyearlycost = pvyearlycost + storagemonthlycost;
        Double systemprofit = systemrevenue - systemyearlycost;
        payback = systemcost / systemprofit;
        Double pvprofitmonth = systemprofit / 12;

        Double[] cashflow = new Double[20];
        for (int i = 0; i < 20; i++) {
            cashflow[i] = -systemcost + (systemprofit * i);
            editor.putFloat("Cashflow" + i, cashflow[i].floatValue());
            Log.e("PV WITH STORAGE", "Cashflow" + i + ": " + cashflow[i]);
        }
        editor.commit();
        Double newavgmonthbill = davgmonthbill - pvprofitmonth;
        displayResults();
    }

    private void createCharts() {

    }

}

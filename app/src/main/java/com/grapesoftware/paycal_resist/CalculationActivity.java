package com.grapesoftware.paycal_resist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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

import java.math.RoundingMode;
import java.text.DecimalFormat;


public class CalculationActivity extends AppCompatActivity {
    String typeforuser,morning,peak,offpeak,tax,avgconsmonth,morconsmonth,avgmonthbill,resgendaily,resgenmonthly,storageperc,restype,turbinetype,turbinecount,consyear,solararea,latitude,longitude,storagetype;
    Double dlatitude,dlongitude,dmorning,dpeak,doffpeak,dtax,davgconsmonth,dmorconsmonth,davgmonthbill,dresgendaily,dresgenmonthly,dstorageperc,drestype,dturbinecount,dconsyear,dsolararea;
    ProgressBar dataProgressBar;
    private RequestQueue mQueue;
    Button btLocation, backButton;
    TextView showResult1, showResult2, showResult3;
    Double wwa13=0.00,wa13=0.00;
    Double eff,dod,storage_price,omprice;
    Double a13,storagepercentagee,avgwind;
    Double windkwarray;
    final Context context = this;


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
        storagetype=preferences.getString("Storage Type",null);


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
        storagepercentagee=Double.valueOf(storageperc)/100;
        restype = restype.replace(",", ".");
        turbinecount = turbinecount.replace(",", ".");
        turbinetype = turbinetype.replace(",", ".");
        consyear = consyear.replace(",", ".");
        solararea = solararea.replace(",", ".");

        dlatitude=ParseDouble(latitude);
        dlongitude=ParseDouble(longitude);
        dmorning=ParseDouble(morning);
        dpeak=ParseDouble(peak);
        doffpeak=ParseDouble(offpeak);
        dtax=ParseDouble(tax);
        davgconsmonth=ParseDouble(avgconsmonth);
        dmorconsmonth=ParseDouble(morconsmonth);
        davgmonthbill=ParseDouble(avgmonthbill);
        dresgendaily=ParseDouble(resgendaily);
        dresgenmonthly=ParseDouble(resgenmonthly);
        dstorageperc=ParseDouble(storageperc);
        drestype=ParseDouble(restype);
        dturbinecount=ParseDouble(turbinecount);
        dconsyear=ParseDouble(consyear);
        dsolararea=ParseDouble(solararea);



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

        if (storagetype.equals("Thermal")){
            eff=0.9;
            dod=1.0;
            storage_price=0.32;
            omprice=0.1;


        }
        else if (storagetype.equals("Lion")){
            eff=0.96;
            dod=90.0;
            storage_price=1260.0;
            omprice=10.0;

        }
        if (storagetype.equals("LeadAcid")){
            eff=0.8;
            dod=60.0;
            storage_price=550.0;
            omprice=10.0;

        }


                jsonParse();



    }


    double ParseDouble(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return Double.parseDouble(strNumber);
            } catch(Exception e) {
                return -1;   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        }
        else return 0;
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
                        a13 = allsky.getDouble("13");
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

                        if (storagetype.equals("NoStorage") && restype.equals("WIND")) {
                            hesaplawindnostorage();
                        }
                        else if (!storagetype.equals("NoStorage") && restype.equals("WIND")){
                            hesaplawindwithstorage();
                        }

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

        Double price;
        Double turbinetypevalue;
        Double ratedcapacity;
        Double windyearkwh,winddaykwh,windanualprofit,windcapitalcost,payback;
        Double windyearcost=0.00;
        Double [] cashflow = new Double[24];

        windkwarraycalculate();

        if (turbinetype.equals("1 Kw")) {
            price=3466.45;
            turbinetypevalue=1.0;
        }
        else if (turbinetype.equals("3 Kw")){
            price=3068.89;
            turbinetypevalue=3.0;

        }
        else{
            price=2545.67;
            turbinetypevalue=10.0;
        }

        ratedcapacity=dturbinecount*turbinetypevalue;
        windyearkwh=windkwarray*12*dturbinecount;
        winddaykwh=windyearkwh/365;

        if (dconsyear>=windyearkwh){
            windanualprofit=(windyearkwh*dmorning*dtax)+windyearkwh*dmorning;
        }
        else {
            windanualprofit=(dconsyear*dmorning+dconsyear*dmorning*dtax)+(windyearkwh-dconsyear)*dmorning;
        }

        windcapitalcost=ratedcapacity*price;
        payback=windcapitalcost/(windanualprofit-windyearcost);

        for (int i=0; i<24 ;i++){
            cashflow[i]=-windcapitalcost+((windanualprofit)*i);
            //Toast.makeText(getApplicationContext(),String.valueOf(cashflow[i]),Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(getApplicationContext(),"WindAnualProfit: "+windanualprofit+"\nWind Year Cost: "+ windyearcost+"Payback: "+String.valueOf(payback)+"\n"+"Wind Year Kwh: "+String.valueOf(windyearkwh),Toast.LENGTH_LONG).show();

        //Log.e("WindHesap",+dturbinecount+"\n"+ratedcapacity+"\n"+windyearkwh+"\n"+winddaykwh+"\n"+windcapitalcost+"\n"+windyearcost+"\n"+windanualprofit+"\n"+payback+"\n");

        // dialog nesnesi oluştur ve layout dosyasına bağlan
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.result_viewer);

        // custom dialog elemanlarını tanımla - text, image ve button
        Button ownconfirmbtn=dialog.findViewById(R.id.own_confirm_button);
        final TextView resultPage = dialog.findViewById(R.id.results_txt);

        resultPage.setText("Type: "+typeforuser+"\n"+
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
                "Solar Area: "+solararea+"\n"+
                "Rated Capacity: "+ratedcapacity+"\n"+
                "Wind Day Kwh: "+winddaykwh+"\n"+
                "Wind Year Kwh: "+windyearkwh+"\n"+
                "Wind Array Kwh: "+windkwarray+"\n"+
                "Price: "+price+"\n"+
                "Wind Annual Profit: "+windanualprofit+"\n"+
                "Wind Year Cost: "+windyearcost+"\n"+
                "Wind Capital Cost: "+windcapitalcost+"\n"+
                "Payback: "+payback+"\n"+
                "Average Wind 50M: "+wwa13
        );

        // tamam butonunun tıklanma olayları
        ownconfirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });


        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

    }
    private void windkwarraycalculate(){
        if  (turbinetype.equals("1 Kw")){
            if (wwa13<=1.8){
                windkwarray=0.0;
            }
            else if(wwa13>1.8 &&wwa13<=2.7){
                windkwarray=25.0;
            }
            else if (wwa13>2.7 && wwa13<=3.6){
                windkwarray=75.0;
            }
            else if (wwa13>3.6 && wwa13<=4.5){
                windkwarray=125.0;
            }
            else if (wwa13>4.5 && wwa13<=5.4){
                windkwarray=200.0;
            }
            else if (wwa13>5.4 && wwa13<=6.3){
                windkwarray=275.0;
            }
            else if (wwa13>6.3 && wwa13<=7.2){
                windkwarray=325.0;
            }
            else  {
                windkwarray=375.0;
            }
        }
        else if(turbinetype.equals("3 Kw")){
            if (wwa13<=1.8){
                windkwarray=0.0;
            }
            else if(wwa13>1.8 && wwa13<=2.7){
                windkwarray=75.0;
            }
            else if (wwa13>2.7 && wwa13<=3.6){
                windkwarray=160.0;
            }
            else if (wwa13>3.6 && wwa13<=4.5){
                windkwarray=325.0;
            }
            else if (wwa13>4.5 && wwa13<=5.4){
                windkwarray=525.0;
            }
            else if (wwa13>5.4 && wwa13<=6.3){
                windkwarray=675.0;
            }
            else if (wwa13>6.3 && wwa13<=7.2){
                windkwarray=825.0;
            }
            else  {
                windkwarray=1000.0;
            }
        }
        else if(turbinetype.equals("10 Kw")){
            if (wwa13<=3.6){
                windkwarray=410.0;
            }
            else if(wwa13>3.6 &&wwa13<=4.5){
                windkwarray=820.0;
            }
            else if (wwa13>4.5 && wwa13<=5.4){
                windkwarray=1377.0;
            }
            else if (wwa13>5.4 && wwa13<=6.3){
                windkwarray=2027.0;
            }
            else  {
                windkwarray=2700.0;
            }
        }
        else if(turbinetype.equals("1500 Kw")){
            if (wwa13<=3.0){
                windkwarray=0.0;
            }
            else if(wwa13>3.0 &&wwa13<=4.5){
                windkwarray=1800.0;
            }
            else if (wwa13>4.5 && wwa13<=6.0){
                windkwarray=6600.0;
            }
            else if (wwa13>6.0 && wwa13<=7.5){
                windkwarray=12000.0;
            }
            else if (wwa13>7.5 && wwa13<=9.0){
                windkwarray=21600.0;
            }
            else if (wwa13>9.0 && wwa13<=10.5){
                windkwarray=31200.0;
            }
            else if (wwa13>10.5 && wwa13<=12.0){
                windkwarray=34200.0;
            }
            else  {
                windkwarray=36000.0;
            }
        }
    }

    private void hesaplawindwithstorage(){

        if (!storagetype.equals("NoStorage")){

            Double price;
            Double storagepercentage=dstorageperc/100;
            Double turbinetypevalue;
            Double ratedcapacity;
            Double windyearkwh,winddaykwh,windanualprofit,systemprofit,systemyearlycost,windcapitalcost,payback,storageusedcapacity,storagecapacity,storagecapitalcost,storageyearlycost,systemcost;
            Double windyearcost=0.00;
            Double [] cashflow = new Double[24];

            windkwarraycalculate();
            if (turbinetype.equals("1 Kw")) {
                price=3511.57;
                turbinetypevalue=1.0;
            }
            else if (turbinetype.equals("3 Kw")){
                price=3108.84;
                turbinetypevalue=3.0;
            }
            else{
                price=2579.95;
                turbinetypevalue=10.0;
            }


            ratedcapacity=dturbinecount*turbinetypevalue;
            windyearkwh=windkwarray*12*dturbinecount;
            winddaykwh=windyearkwh/365;
            storageusedcapacity=winddaykwh*storagepercentage;
            storagecapacity=(storageusedcapacity+(storageusedcapacity*(1-eff*dod)));
            storagecapitalcost=storagecapacity*storage_price;
            storageyearlycost=storagecapacity*omprice;
            if (dconsyear>=windyearkwh*(1-storagepercentage)){
                windanualprofit=(windyearkwh*(1-storagepercentage)*dmorning*dtax)
                        +windyearkwh*(1-storagepercentage)*dmorning
                        +(windyearkwh*storagepercentage*dpeak*dtax)
                        +(windyearkwh*storagepercentage*dpeak);
            }
            else {
                windanualprofit=(dconsyear*dmorning
                        +dconsyear*dmorning*dtax)
                        +(windyearkwh*(1-storagepercentage)
                        -dconsyear)*dmorning
                        +(windyearkwh*storagepercentage*dpeak*dtax)
                        +(windyearkwh*storagepercentage*dpeak);
            }
            windcapitalcost=ratedcapacity*price;
            systemcost=storagecapitalcost+windcapitalcost;
            systemyearlycost=storageyearlycost+windyearcost;
            systemprofit=windanualprofit-systemyearlycost;
            payback=windcapitalcost/(systemprofit);

            for (int i=0; i<24 ;i++){
                cashflow[i]=-systemcost+((systemprofit)*i);
            }

            Toast.makeText(getApplicationContext(),"Storage Used Capacity: "+storageusedcapacity+"\nStorage Capacity: "+ storagecapacity+"Payback: "+String.valueOf(payback)+"\n"+"Storage Yearly Cost: "+storageyearlycost,Toast.LENGTH_LONG).show();



            Log.e("Degerler",turbinetypevalue+"\n"+dturbinecount+"\n"+ratedcapacity+"\n"+price+"\n"+windcapitalcost);
            Log.e("WindHesap",price+"\n"+windcapitalcost+"\n"+systemcost+"\n"+systemyearlycost+"\n"+systemprofit+"\n"+payback+"\n"+cashflow[0]+"--"+cashflow[23]);
        }

    }

    private void hesaplasolar(){

        Double panelarea=1.63;
        Double panelpower=0.265;
        Double pvcostconsumer=1300.0;
        Double omcostconsumer=20.0;
        Double pvcost=1100.0;
        Double pvomcost=17.0;
        Double pvgenaveragemonth=Double.valueOf(a13);
        Double pvgenyear=pvgenaveragemonth*12;
        Double pvgenaverageday=pvgenyear/365;
        Double panelnum=Double.valueOf(solararea)/panelarea;
        Double pvpower=panelnum*panelpower;
        Double pvcapitalcost=pvpower*pvcostconsumer;
        Double revenueyear;

//        ((Double.valueOf(morconsmonth)*Double.valueOf(morning))*Double.valueOf(tax))
//                +(Double.valueOf(morconsmonth)*Double.valueOf(morning))
//                +(pvgenaveragemonth-Double.valueOf(morconsmonth))*Double.valueOf(morning);

        //Double pvyearlycost=pvpower*pvomcost;
        //Double pvprofityear=





    }

}

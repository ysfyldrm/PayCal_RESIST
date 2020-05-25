package com.grapesoftware.paycal_resist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;


public class CalculationActivity extends AppCompatActivity {
    String typeforuser,morning,peak,offpeak,tax,avgconsmonth,morconsmonth,avgmonthbill,resgendaily,resgenmonthly,storageperc,restype,turbinetype,turbinecount,consyear,solararea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);


        SharedPreferences preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);
        typeforuser = preferences.getString("Type", "Consumer");
        morning=preferences.getString("Morning Tariff",null);
        peak=preferences.getString("Peak Tariff",null);
        offpeak=preferences.getString("Off Peak Tariff",null);
        tax=preferences.getString("Tax",null);
        avgconsmonth=preferences.getString("Cons Avg Month",null);
        morconsmonth=preferences.getString("Morning Cons Month",null);
        avgmonthbill=preferences.getString("Avg Month Bill",null);
        resgendaily=preferences.getString("RES Gen Daily",null);
        resgenmonthly=preferences.getString("RES Gen Monthly",null);
        storageperc=preferences.getString("Storage Percentage",null);
        restype=preferences.getString("RES Type",null);
        turbinecount=preferences.getString("Turbine Count",null);
        turbinetype=preferences.getString("Turbine Type",null);
        consyear=preferences.getString("Cons Year",null);
        solararea=preferences.getString("Solar Area",null);


        Toast.makeText(getApplicationContext(),
                "Type: "+typeforuser+"\n"+
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

    }
}

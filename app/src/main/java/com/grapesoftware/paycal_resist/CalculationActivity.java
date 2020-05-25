package com.grapesoftware.paycal_resist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;


public class CalculationActivity extends AppCompatActivity {
    String typeforuser;
    String morning;
    String peak;
    String offpeak;
    String tax;
    String avgconsmonth;
    String morconsmonth;
    String avgmonthbill;
    String resgendaily;
    String resgenmonthly;
    String storageperc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);


        SharedPreferences preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);
        typeforuser = preferences.getString("Type", "Consumer");
        morning=preferences.getString("i",null);
        peak=preferences.getString("ii",null);
        offpeak=preferences.getString("iii",null);
        tax=preferences.getString("iv",null);
        avgconsmonth=preferences.getString("v",null);
        morconsmonth=preferences.getString("vi",null);
        avgmonthbill=preferences.getString("vii",null);
        resgendaily=preferences.getString("viii",null);
        resgenmonthly=preferences.getString("ix",null);
        storageperc=preferences.getString("x",null);


        Toast.makeText(getApplicationContext(),typeforuser+"\n"+morning+"\n"+peak+"\n"+offpeak+"\n"+tax+"\n"+avgconsmonth+"\n"+morconsmonth+"\n"+avgmonthbill+"\n"+resgendaily+"\n"+resgenmonthly+"\n"+storageperc,Toast.LENGTH_SHORT).show();


//        SharedPreferences settings = context.getSharedPreferences("session", Context.MODE_PRIVATE);
//        settings.edit().clear().commit();

    }
}

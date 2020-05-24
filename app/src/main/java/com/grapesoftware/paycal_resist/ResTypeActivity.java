package com.grapesoftware.paycal_resist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ResTypeActivity extends AppCompatActivity {
Button  windbtn, pvbtn, biomassbtn;
Bundle bundle1;
String morning,peak,offpeak,tax,avgconsmonth,consyear,morconsmonth,avgmonthbill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_type);

        windbtn=findViewById(R.id.button_wind);
        pvbtn=findViewById(R.id.button_PV);
        biomassbtn=findViewById(R.id.button_biomass);

        bundle1 = getIntent().getExtras();
        morning=bundle1.getString("i");
        peak=bundle1.getString("ii");
        offpeak=bundle1.getString("iii");
        tax=bundle1.getString("iv");
        avgconsmonth=bundle1.getString("v");
        consyear=bundle1.getString("vi");
        morconsmonth=bundle1.getString("vii");
        avgmonthbill=bundle1.getString("viii");

        Toast.makeText(getApplicationContext(),morning+"\n"+peak+"\n"+offpeak+"\n"+tax+"\n"+avgconsmonth+"\n"+consyear+"\n"+morconsmonth+"\n"+avgmonthbill,Toast.LENGTH_LONG).show();





        windbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ResTypeActivity.this,StorageTypeActivity.class);
                startActivity(intent);
            }
        });
        pvbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ResTypeActivity.this,StorageTypeActivity.class);
                startActivity(intent);
            }
        });
        biomassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ResTypeActivity.this,StorageTypeActivity.class);
                startActivity(intent);
            }
        });
    }
}

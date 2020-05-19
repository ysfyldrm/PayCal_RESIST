package com.grapesoftware.paycal_resist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ResTypeActivity extends AppCompatActivity {
Button  windbtn, pvbtn, biomassbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_type);
        windbtn=findViewById(R.id.button_wind);
        pvbtn=findViewById(R.id.button_PV);
        biomassbtn=findViewById(R.id.button_biomass);



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

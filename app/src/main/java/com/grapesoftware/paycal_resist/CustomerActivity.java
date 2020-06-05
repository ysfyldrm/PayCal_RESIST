package com.grapesoftware.paycal_resist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CustomerActivity extends AppCompatActivity {

    private Button btnConsumer, btnProsumer, btnSupplier,backbutton,profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        btnConsumer= findViewById(R.id.button_consumer);
        btnProsumer=findViewById(R.id.button_prosumer);
        btnSupplier=findViewById(R.id.button_supplier);
        profile=findViewById(R.id.profile);
        backbutton=findViewById(R.id.backBtn);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerActivity.this,ProfileActivity.class);
                startActivity(intent);
                //finish();
            }
        });




        btnConsumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(CustomerActivity.this,TariffSelectionActivity.class);

                String type="Consumer";
                Bundle bundle=new Bundle();
                bundle.putString("Type",type);
                intent1.putExtras(bundle);
                startActivity(intent1);

                SharedPreferences preferences = getSharedPreferences("session",getApplicationContext().MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Type", type);
                editor.commit();
            }
        });

        btnProsumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(CustomerActivity.this,TariffSelectionActivity.class);
                Bundle bundle=new Bundle();
                String type="Prosumer";
                bundle.putString("Type",type);
                intent2.putExtras(bundle);
                startActivity(intent2);

                SharedPreferences preferences = getSharedPreferences("session",getApplicationContext().MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Type", type);
                editor.commit();

            }
        });

        btnSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(CustomerActivity.this,SupplierMcpSelectionActivity.class);

                Bundle bundle=new Bundle();
                String type="Supplier";
                bundle.putString("Type",type);
                intent3.putExtras(bundle);

                SharedPreferences preferences = getSharedPreferences("session",getApplicationContext().MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Type", type);
                editor.commit();
                startActivity(intent3);

            }
        });




    }
}

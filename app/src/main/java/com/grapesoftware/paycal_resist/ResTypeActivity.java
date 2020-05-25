package com.grapesoftware.paycal_resist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ResTypeActivity extends AppCompatActivity {
Button  windbtn, pvbtn, biomassbtn;
RadioButton pickedTurbine;
Bundle bundle1;
String morning,peak,offpeak,tax,avgconsmonth,consyear,morconsmonth,avgmonthbill;
String rdbtext;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_type);

        windbtn=findViewById(R.id.button_wind);
        pvbtn=findViewById(R.id.button_PV);
        biomassbtn=findViewById(R.id.button_biomass);

        //SONUC EKRANINA GÖNDERILECEK DIREK BURDA ISI YOK....
//
//        bundle1 = getIntent().getExtras();
//        morning=bundle1.getString("i");
//        peak=bundle1.getString("ii");
//        offpeak=bundle1.getString("iii");
//        tax=bundle1.getString("iv");
//        avgconsmonth=bundle1.getString("v");
//        consyear=bundle1.getString("vi");
//        morconsmonth=bundle1.getString("vii");
//        avgmonthbill=bundle1.getString("viii");
//
//        Toast.makeText(getApplicationContext(),morning+"\n"+peak+"\n"+offpeak+"\n"+tax+"\n"+avgconsmonth+"\n"+consyear+"\n"+morconsmonth+"\n"+avgmonthbill,Toast.LENGTH_LONG).show();
//
//
//


        windbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(ResTypeActivity.this,StorageTypeActivity.class);
//                startActivity(intent);
                SharedPreferences preferences = getSharedPreferences("session",getApplicationContext().MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("restype",windbtn.getText().toString());
                editor.commit();

                showMyCustomWindTurbinDialog();

            }
        });
        pvbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ResTypeActivity.this,StorageTypeActivity.class);


                SharedPreferences preferences = getSharedPreferences("session",getApplicationContext().MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("restype",pvbtn.getText().toString());
                editor.commit();

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

    public void showMyCustomWindTurbinDialog() {

        // dialog nesnesi oluştur ve layout dosyasına bağlan
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.wind_turbine_options);

        // custom dialog elemanlarını tanımla - text, image ve button
        Button ownconfirmbtn=dialog.findViewById(R.id.own_confirm_button);
        final RadioGroup turbineGroup = dialog.findViewById(R.id.turbine_group);
        final EditText turbineCount = dialog.findViewById(R.id.turbine_count_edt);
        turbineCount.setTransformationMethod(null);

        turbineGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                pickedTurbine=radioGroup.findViewById(i);
                rdbtext=pickedTurbine.getText().toString();
            }
        });



        // tamam butonunun tıklanma olayları
        ownconfirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context, rdbtext + "" , Toast.LENGTH_SHORT).show();
                Toast.makeText(context,  rdbtext+ "\n"+ turbineCount.getText() , Toast.LENGTH_SHORT).show();

                Intent intentwind =new Intent(ResTypeActivity.this, StorageTypeActivity.class);
                Intent intentcaluclation=new Intent(ResTypeActivity.this,CalculationActivity.class);
                Bundle bundlewind= new Bundle();
                String turtype=rdbtext;
                String turcount=turbineCount.getText().toString();
                bundlewind.putString("TurbıneType",turtype);
                bundlewind.putString("TurbıneCount",turcount);
                intentcaluclation.putExtras(bundlewind);
                startActivity(intentwind);


            }
        });


        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }
}

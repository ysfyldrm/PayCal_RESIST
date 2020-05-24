package com.grapesoftware.paycal_resist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
    final Context context = this;

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
//                Intent intent=new Intent(ResTypeActivity.this,StorageTypeActivity.class);
//                startActivity(intent);
                showMyCustomWindTurbinDialog();

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

    public void showMyCustomWindTurbinDialog() {

        // dialog nesnesi oluştur ve layout dosyasına bağlan
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.wind_turbine_options);

        // custom dialog elemanlarını tanımla - text, image ve button
        Button ownconfirmbtn=dialog.findViewById(R.id.own_confirm_button);
        final RadioGroup turbineGroup = dialog.findViewById(R.id.turbine_group);
        final EditText turbineCount = dialog.findViewById(R.id.turbine_count_edt);
        turbineCount.setTransformationMethod(null);

        // custom dialog elemanlarına değer ataması yap - text, image



        // tamam butonunun tıklanma olayları
        ownconfirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = turbineGroup.getCheckedRadioButtonId();
                Toast.makeText(context, selectedId + "" , Toast.LENGTH_SHORT).show();
                pickedTurbine = findViewById(selectedId);
                String turbinAdi = pickedTurbine.getText().toString();
                Toast.makeText(context,  turbinAdi+ "\n"+ turbineCount.getText() , Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });


        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }
}

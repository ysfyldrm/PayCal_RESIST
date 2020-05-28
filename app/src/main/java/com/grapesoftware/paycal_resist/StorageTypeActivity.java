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
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class StorageTypeActivity extends AppCompatActivity {

    Bundle gelentype;
    String typeforuser,restype;
    private Button lionBtn, leadAcidBtn, thermalBtn, noStorageBtn;
    final Context context = this;
    SeekBar seekBar;
    TextView percentageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_type);

        lionBtn = findViewById(R.id.button_Li_ion);
        leadAcidBtn = findViewById(R.id.button_leadAcid);
        thermalBtn = findViewById(R.id.button_thermal);
        noStorageBtn = findViewById(R.id.button_no_storage);


        SharedPreferences preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);
        typeforuser = preferences.getString("Type", "Consumer");
        restype=preferences.getString("RES Type",null);

        Toast.makeText(context, restype, Toast.LENGTH_SHORT).show();

        if (typeforuser.equals("Prosumer")){
             noStorageBtn.setVisibility(View.GONE);
        }



        noStorageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent finalize=new Intent(StorageTypeActivity.this, CalculationActivity.class);
                SharedPreferences preferences = getSharedPreferences("session",getApplicationContext().MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Storage Type","NoStorage");
                editor.commit();

                startActivity(finalize);


            }
        });

        lionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (restype.equals("PV SOLAR")) {
                    showMyCustomSolarArea();
                }
                else if(restype.equals("WIND")){

                    showMyPercentageStorage();
                    SharedPreferences preferences = getSharedPreferences("session",getApplicationContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("Storage Type","Lion");
                    editor.commit();

                }

            }
        });

        thermalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (restype.equals("PV SOLAR")) {
                    showMyCustomSolarArea();
                }
                else if(restype.equals("WIND")){
                    showMyPercentageStorage();
                    SharedPreferences preferences = getSharedPreferences("session",getApplicationContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("Storage Type","Thermal");
                    editor.commit();

                }

            }
        });

        leadAcidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (restype.equals("PV SOLAR")) {
                    showMyCustomSolarArea();
                }
                else if(restype.equals("WIND")){
                    SharedPreferences preferences = getSharedPreferences("session",getApplicationContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("Storage Type","LeadAcid");
                    editor.commit();
                    showMyPercentageStorage();
                }

            }
        });

    }
    public void showMyCustomSolarArea() {

        // dialog nesnesi oluştur ve layout dosyasına bağlan
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.solar_area);

        // custom dialog elemanlarını tanımla - text, image ve button
        Button ownconfirmbtn=dialog.findViewById(R.id.own_confirm_button);
        final EditText areaCount = dialog.findViewById(R.id.area_count_edt);
        areaCount.setTransformationMethod(null);


        // tamam butonunun tıklanma olayları
        ownconfirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMyPercentageStorage();

                SharedPreferences preferences = getSharedPreferences("session",getApplicationContext().MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Solar Area", areaCount.getText().toString());
                editor.commit();

                Toast.makeText(context, areaCount.getText(), Toast.LENGTH_SHORT).show();

            }
        });


        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }


    public void showMyPercentageStorage() {

        // dialog nesnesi oluştur ve layout dosyasına bağlan
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.storage_percentage);

        // custom dialog elemanlarını tanımla - text, image ve button
        Button ownconfirmbtn=dialog.findViewById(R.id.own_confirm_button);
        seekBar=dialog.findViewById(R.id.storage_seekbar);
        percentageView = dialog.findViewById(R.id.storage_percent_tview);
        seekBar.setMax(100);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                percentageView.setText(Integer.toString(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        // tamam butonunun tıklanma olayları
        ownconfirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = getSharedPreferences("session",getApplicationContext().MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Storage Percentage", percentageView.getText().toString());
                editor.commit();

                Intent calculate=new Intent(StorageTypeActivity.this,CalculationActivity.class);
                startActivity(calculate);


            }
        });


        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }

}

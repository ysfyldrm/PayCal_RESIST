package com.grapesoftware.paycal_resist;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StorageTypeActivity extends AppCompatActivity {

    Bundle gelentype;
    String typeforuser,restype;
    private Button lionBtn, leadAcidBtn, thermalBtn, noStorageBtn,backbutton,profile;
    final Context context = this;
    SeekBar seekBar;
    TextView percentageView;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_type);

        lionBtn = findViewById(R.id.button_Li_ion);
        leadAcidBtn = findViewById(R.id.button_leadAcid);
        thermalBtn = findViewById(R.id.button_thermal);
        noStorageBtn = findViewById(R.id.button_no_storage);
        backbutton=findViewById(R.id.backBtn);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        profile=findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StorageTypeActivity.this,UserProfile.class);
                startActivity(intent);
                //finish();
            }
        });


        SharedPreferences preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);
        editor = preferences.edit();
        typeforuser = preferences.getString("Type", "Consumer");
        restype=preferences.getString("RES Type","SOLAR");


        if (typeforuser.equals("Prosumer")){
             noStorageBtn.setVisibility(View.GONE);
        }



        noStorageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent finalize=new Intent(StorageTypeActivity.this, CalculationActivity.class);
                editor.putString("Storage Type","NoStorage");
                editor.commit();
                startActivity(finalize);


            }
        });

        lionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (restype.equals("SOLAR")) {
                    editor.putString("Storage Type","Li-ion");
                    editor.commit();
                    showMyPercentageStorage();
                }
                else if(restype.equals("WIND")){


                    editor.putString("Storage Type","Li-ion");
                    editor.commit();
                    showMyPercentageStorage();

                }

            }
        });

        thermalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (restype.equals("SOLAR")) {

                    editor.putString("Storage Type","Thermal");
                    editor.commit();
                    showMyPercentageStorage();
                }
                else if(restype.equals("WIND")){
                    editor.putString("Storage Type","Thermal");
                    editor.commit();
                    showMyPercentageStorage();

                }

            }
        });

        leadAcidBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (restype.equals("SOLAR")) {
                    editor.putString("Storage Type","Lead Acid");
                    editor.commit();
                    showMyPercentageStorage();

                }
                else if(restype.equals("WIND")){
                    editor.putString("Storage Type","Lead Acid");
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
                if (TextUtils.isEmpty(areaCount.getText().toString())) {
                    areaCount.setError("This field cannot be empty");
                    areaCount.requestFocus();
                    return;
                } else {
                    showMyPercentageStorage();
                    editor.putString("Solar Area", areaCount.getText().toString());
                    editor.commit();
                }
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

                editor.putString("Storage Percentage", percentageView.getText().toString());
                editor.commit();
                if (typeforuser.equals("Prosumer"))
                {
                    showMyAfterProsumerTariff();
                }
                else {
                    Intent calculate = new Intent(StorageTypeActivity.this, CalculationActivity.class);
                    startActivity(calculate);
                }

            }
        });


        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }

    private void showMyAfterProsumerTariff() {

        // dialog nesnesi oluştur ve layout dosyasına bağlan
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.after_prosumer_tariff);

        // custom dialog elemanlarını tanımla - text, image ve button
        Button ownconfirmbtn=dialog.findViewById(R.id.wanted_data_confirm_button);
        final EditText consavgmonth=dialog.findViewById(R.id.cons_avgmonth_edt);
        final EditText avgmonthbill=dialog.findViewById(R.id.avg_month_bill_edt);
        final EditText morconsmonth=dialog.findViewById(R.id.morning_cons_month_edt);
        final EditText resgendaily=dialog.findViewById(R.id.res_gen_daily_edt);
        final EditText resgenmonthly=dialog.findViewById(R.id.res_gen_mothly_edt);



        // tamam butonunun tıklanma olayları
        ownconfirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(consavgmonth.getText().toString())) {
                    consavgmonth.setError("This field cannot be empty");
                    consavgmonth.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(avgmonthbill.getText().toString())) {
                    avgmonthbill.setError("This field cannot be empty");
                    avgmonthbill.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(morconsmonth.getText().toString())) {
                    morconsmonth.setError("This field cannot be empty");
                    morconsmonth.requestFocus();
                    return;
                }else if (TextUtils.isEmpty(resgendaily.getText().toString())) {
                    resgendaily.setError("This field cannot be empty");
                    resgendaily.requestFocus();
                    return;
                }else if (TextUtils.isEmpty(resgenmonthly.getText().toString())) {
                    resgenmonthly.setError("This field cannot be empty");
                    resgenmonthly.requestFocus();
                    return;
                } else {
                    editor.putString("Cons Avg Month", consavgmonth.getText().toString());
                    editor.putString("Avg Month Bill", avgmonthbill.getText().toString());
                    editor.putString("Morning Cons Month", morconsmonth.getText().toString());
                    editor.putString("RES Gen Daily", resgendaily.getText().toString());
                    editor.putString("RES Gen Monthly", resgenmonthly.getText().toString());

                    editor.commit();

                    Intent calculate = new Intent(StorageTypeActivity.this, CalculationActivity.class);
                    startActivity(calculate);
                }
            }
        });


        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }

}

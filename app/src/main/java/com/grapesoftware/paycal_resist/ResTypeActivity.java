package com.grapesoftware.paycal_resist;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.RestrictionEntry;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class ResTypeActivity extends AppCompatActivity {
    Button windbtn, pvbtn, biomassbtn, backbutton,profile;
    RadioButton pickedTurbine;
    Bundle bundle1;
    String morning, peak, offpeak, tax, avgconsmonth, consyear, morconsmonth, avgmonthbill, typeforuser;
    String rdbtext;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_type);

        windbtn = findViewById(R.id.button_wind);
        pvbtn = findViewById(R.id.button_PV);
        biomassbtn = findViewById(R.id.button_biomass);
        backbutton = findViewById(R.id.backBtn);

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
                Intent intent=new Intent(ResTypeActivity.this,ProfileActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);
        editor = preferences.edit();

        typeforuser = preferences.getString("Type", null);


        windbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(ResTypeActivity.this,StorageTypeActivity.class);
//                startActivity(intent);


                if (typeforuser.equals("Supplier")) {
                    showMyCustomSuplierWindTurbineDialog();
                } else {
                    showMyCustomWindTurbinDialog();
                }

                editor.putString("RES Type", windbtn.getText().toString());
                editor.commit();

            }
        });
        pvbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (typeforuser.equals("Supplier")) {
                    showMyCustomSuplierSolarSelection();
                } else {
                    showMyCustomSolarArea();
                }
                editor.putString("RES Type", pvbtn.getText().toString());
            }
        });
        biomassbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResTypeActivity.this, StorageTypeActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showMyCustomSuplierSolarSelection() {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.power_or_solar);

        // custom dialog elemanlarını tanımla - text, image ve button
        final Button powerbutton = dialog.findViewById(R.id.power_button);
        final Button areabutton = dialog.findViewById(R.id.area_button);

        // tamam butonunun tıklanma olayları
        powerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                final Dialog dialog1 = new Dialog(context);
                dialog1.setContentView(R.layout.solar_power);
                dialog1.show();

                Button ownconfirmbutton = dialog1.findViewById(R.id.own_confirm_button);
                Button backbutton2=dialog1.findViewById(R.id.backBtn);
                final EditText poweredt = dialog1.findViewById(R.id.power_count_edt);
                poweredt.setTransformationMethod(null);

                backbutton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog1.dismiss();
                        dialog.show();
                    }
                });

                ownconfirmbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(TextUtils.isEmpty(poweredt.getText().toString())) {
                            poweredt.setError("This field cannot be empty");
                            poweredt.requestFocus();
                            return;
                        }
                        else {
                            dialog1.dismiss();
                            Intent intent = new Intent(ResTypeActivity.this, StorageTypeActivity.class);
                            editor.putString("Solar Power", poweredt.getText().toString());
                            editor.putString("Solar Selection", "Power");
                            editor.commit();
                            startActivity(intent);
                        }
                    }
                });
                windowstate(dialog1);
            }
        });

        areabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                final Dialog dialog2 = new Dialog(context);
                dialog2.setContentView(R.layout.solar_area);
                dialog2.show();

                Button ownconfirmbutton = dialog2.findViewById(R.id.own_confirm_button);
                Button backbutton1=dialog2.findViewById(R.id.backBtn);

                final EditText areaedt = dialog2.findViewById(R.id.area_count_edt);
                areaedt.setTransformationMethod(null);
                backbutton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog2.dismiss();
                        dialog.show();
                    }
                });

                ownconfirmbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(TextUtils.isEmpty(areaedt.getText().toString())) {
                            areaedt.setError("This field cannot be empty");
                            areaedt.requestFocus();
                            return;
                        }
                        else {
                            dialog2.dismiss();
                            Intent intent = new Intent(ResTypeActivity.this, StorageTypeActivity.class);
                            editor.putString("Solar Area", areaedt.getText().toString());
                            editor.putString("Solar Selection", "Area");
                            editor.commit();
                            startActivity(intent);
                        }
                    }
                });
                windowstate(dialog2);
            }
        });


        dialog.show();
        windowstate(dialog);
    }
    public void windowstate(Dialog dialog){
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }


    public void showMyCustomSolarArea() {

        // dialog nesnesi oluştur ve layout dosyasına bağlan
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.solar_area);

        // custom dialog elemanlarını tanımla - text, image ve button
        Button ownconfirmbtn = dialog.findViewById(R.id.own_confirm_button);
        final EditText areaCount = dialog.findViewById(R.id.area_count_edt);
        areaCount.setTransformationMethod(null);


        // tamam butonunun tıklanma olayları
        ownconfirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(areaCount.getText().toString())) {
                    areaCount.setError("This field cannot be empty");
                    areaCount.requestFocus();
                    return;
                }
                else {
                    Intent intent = new Intent(ResTypeActivity.this, StorageTypeActivity.class);
                    editor.putString("Solar Area", areaCount.getText().toString());
                    editor.commit();
                    startActivity(intent);
                }

            }
        });


        dialog.show();
        windowstate(dialog);
    }


    public void showMyCustomSuplierWindTurbineDialog() {
        // dialog nesnesi oluştur ve layout dosyasına bağlan
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.wind_turbine_options);

        // custom dialog elemanlarını tanımla - text, image ve button
        Button ownconfirmbtn = dialog.findViewById(R.id.own_confirm_button);
        final RadioGroup turbineGroup = dialog.findViewById(R.id.turbine_group);
        final EditText turbineCount = dialog.findViewById(R.id.turbine_count_edt);
        final LinearLayout linearLayout = dialog.findViewById(R.id.linear_turbines);

        linearLayout.setVisibility(View.GONE);

        turbineCount.setTransformationMethod(null);

        turbineGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                pickedTurbine = radioGroup.findViewById(i);
                rdbtext = pickedTurbine.getText().toString();
            }
        });



        // tamam butonunun tıklanma olayları
        ownconfirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(turbineCount.getText().toString())) {
                    turbineCount.setError("This field cannot be empty");
                    turbineCount.requestFocus();
                    return;
                }

                else {

                    Intent intentwind = new Intent(ResTypeActivity.this, StorageTypeActivity.class);
                    String turtype = rdbtext;
                    String turcount = turbineCount.getText().toString();

                    editor.putString("Turbine Type", turtype);
                    editor.putString("Turbine Count", turcount);
                    editor.commit();
                    startActivity(intentwind);

                }
            }
        });

        dialog.show();
        windowstate(dialog);

    }

    public void showMyCustomWindTurbinDialog() {

        // dialog nesnesi oluştur ve layout dosyasına bağlan
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.wind_turbine_options);

        // custom dialog elemanlarını tanımla - text, image ve button
        Button ownconfirmbtn = dialog.findViewById(R.id.own_confirm_button);
        final RadioGroup turbineGroup = dialog.findViewById(R.id.turbine_group);
        final EditText turbineCount = dialog.findViewById(R.id.turbine_count_edt);
        turbineCount.setTransformationMethod(null);
        turbineGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                pickedTurbine = radioGroup.findViewById(i);
                rdbtext = pickedTurbine.getText().toString();
            }
        });


        // tamam butonunun tıklanma olayları
        ownconfirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(turbineCount.getText().toString())) {
                    turbineCount.setError("This field cannot be empty");
                    turbineCount.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(rdbtext)){
                    Toast.makeText(getApplicationContext(),"You have to choose Turbine Type!",Toast.LENGTH_SHORT).show();
                }

                else {

                    Intent intentwind = new Intent(ResTypeActivity.this, StorageTypeActivity.class);
                    String turtype = rdbtext;
                    String turcount = turbineCount.getText().toString();

                    editor.putString("Turbine Type", turtype);
                    editor.putString("Turbine Count", turcount);
                    editor.commit();
                    startActivity(intentwind);

                }
            }
        });


        dialog.show();
        windowstate(dialog);
    }
}

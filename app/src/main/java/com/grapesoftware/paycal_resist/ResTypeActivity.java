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
String morning,peak,offpeak,tax,avgconsmonth,consyear,morconsmonth,avgmonthbill,typeforuser;
String rdbtext;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_res_type);

        windbtn=findViewById(R.id.button_wind);
        pvbtn=findViewById(R.id.button_PV);
        biomassbtn=findViewById(R.id.button_biomass);


        SharedPreferences preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);
        typeforuser = preferences.getString("Type", "Consumer");

        if(typeforuser.equals("Prosumer")){
            windbtn.setVisibility(View.GONE);
        }


        windbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(ResTypeActivity.this,StorageTypeActivity.class);
//                startActivity(intent);
                SharedPreferences preferences = getSharedPreferences("session",getApplicationContext().MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("RES Type",windbtn.getText().toString());
                editor.commit();

                showMyCustomWindTurbinDialog();

            }
        });
        pvbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                {

                    final Dialog dialog1=new Dialog(context);
                    dialog1.setContentView(R.layout.after_prosumer_tariff);
                    dialog1.show();

                    Button confirm=dialog1.findViewById(R.id.wanted_data_confirm_button);
                    final EditText AvgConsMonth=dialog1.findViewById(R.id.cons_avgmonth_edt);
                    final EditText MorConsMonth=dialog1.findViewById(R.id.morning_cons_month_edt);
                    final EditText AvgMonthBill=dialog1.findViewById(R.id.avg_month_bill_edt);
                    final EditText ResGenDaily=dialog1.findViewById(R.id.res_gen_daily_edt);
                    final EditText ResGenMonthly=dialog1.findViewById(R.id.res_gen_mothly_edt);





                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(),AvgConsMonth.getText()+"\n"+ResGenMonthly.getText()+"\n"+MorConsMonth.getText()+"\n"+AvgMonthBill.getText()+"\n"+ResGenDaily.getText(),Toast.LENGTH_LONG).show();
                            dialog1.dismiss();
                            Intent intent=new Intent(ResTypeActivity.this,StorageTypeActivity.class);


                            String avgconsmonth=AvgConsMonth.getText().toString();
                            String morconsmonth=MorConsMonth.getText().toString();
                            String avgmonthbill=AvgMonthBill.getText().toString();
                            String resgendaily= ResGenDaily.getText().toString();
                            String resgenmonthly=ResGenMonthly.getText().toString();
                            SharedPreferences preferences = getSharedPreferences("session",getApplicationContext().MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("RES Type",pvbtn.getText().toString());
                            editor.putString("Cons Avg Month",avgconsmonth);
                            editor.putString("Morning Cons Month",morconsmonth);
                            editor.putString("Avg Month Bill",avgmonthbill);
                            editor.putString("RES Gen Daily",resgendaily);
                            editor.putString("RES Gen Monthly",resgenmonthly);

                            editor.commit();

                            startActivity(intent);
                        }
                    });





                    Window window = dialog1.getWindow();
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                }










//                Intent intent=new Intent(ResTypeActivity.this,StorageTypeActivity.class);
//                SharedPreferences preferences = getSharedPreferences("session",getApplicationContext().MODE_PRIVATE);
//
//                SharedPreferences.Editor editor = preferences.edit();
//                editor.putString("RES Type",pvbtn.getText().toString());
//                editor.commit();
//
//                startActivity(intent);
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
                String turtype=rdbtext;
                String turcount=turbineCount.getText().toString();

                SharedPreferences preferences = getSharedPreferences("session",getApplicationContext().MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Turbine Type",turtype);
                editor.putString("Turbine Count",turcount);
                editor.commit();
                startActivity(intentwind);


            }
        });


        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }
}

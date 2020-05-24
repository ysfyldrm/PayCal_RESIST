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
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.nio.BufferUnderflowException;

public class TariffSelectionActivity extends AppCompatActivity {

    private Button epdkbtn, useownbtn;
    final Context context = this;
    Bundle gelentype;
    String typeforuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tariff_selection);

        epdkbtn=findViewById(R.id.epdk_button);
        useownbtn=findViewById(R.id.useOwn_button);

        gelentype=getIntent().getExtras();
        typeforuser=gelentype.getString("Type");




        epdkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, typeforuser, Toast.LENGTH_LONG).show();

                if (typeforuser.equals("Consumer")){
                    showMyCustomEPDKAlertDialog();
                }
                else{
                    showMyCustomEPDKAlertDialogPro();
                }
            }
        });


        useownbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, typeforuser, Toast.LENGTH_LONG).show();

                if (typeforuser.equals("Consumer")){
                    showMyCustomOwnAlertDialog();
                }
                else{
                    showMyCustomOwnAlertDialogPro();
                }

            }
        });


    }
//Bunu klassa çekelim her seferinde ayrı ayrı layoutlar için yazmak zorunda kalmayalım..
    public void showMyCustomEPDKAlertDialog() {

        // dialog nesnesi oluştur ve layout dosyasına bağlan
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.consumer_type);

        // custom dialog elemanlarını tanımla - text, image ve button
        Button reslowvolt=dialog.findViewById(R.id.button_res_low_voltage);
        Button resmedvolt=dialog.findViewById(R.id.button_res_med_voltage);
        Button comlowvolt=dialog.findViewById(R.id.button_com_low_voltage);
        Button commedvolt=dialog.findViewById(R.id.button_com_med_voltage);
        Button indlowvolt=dialog.findViewById(R.id.button_ind_low_voltage);
        Button indmedvolt=dialog.findViewById(R.id.button_ind_med_voltage);


        reslowvolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Morning: 0,57 \nPeak: 0,85 \nOffPeak: 0,37 \nTax: 0,24", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

                {

                    final Dialog dialog1=new Dialog(context);
                    dialog1.setContentView(R.layout.after_consumer_tariff);
                    dialog1.show();

                    Button confirm=dialog1.findViewById(R.id.wanted_data_confirm_button);
                    final EditText AvgConsMonth=dialog1.findViewById(R.id.cons_avgmonth_edt);
                    final EditText ConsYear=dialog1.findViewById(R.id.cons_year_edt);
                    final EditText MorConsMonth=dialog1.findViewById(R.id.morning_cons_month_edt);
                    final EditText AvgMonthBill=dialog1.findViewById(R.id.avg_month_bill_edt);


                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getApplicationContext(),AvgConsMonth.getText()+"\n"+ConsYear.getText()+"\n"+MorConsMonth.getText()+"\n"+AvgMonthBill.getText(),Toast.LENGTH_LONG).show();
                            dialog1.dismiss();
                            Intent intent=new Intent(TariffSelectionActivity.this,ResTypeActivity.class);

                            String morning="0,57";
                            String peak="0,85";
                            String offpeak="0,37";
                            String tax="0,24";
                            String avgconsmonth=AvgConsMonth.getText().toString();
                            String consyear=ConsYear.getText().toString();
                            String morconsmonth=MorConsMonth.getText().toString();
                            String avgmonthbill=AvgMonthBill.getText().toString();
                            Bundle bundle=new Bundle();
                            bundle.putString("i",morning);
                            bundle.putString("ii",peak);
                            bundle.putString("iii",offpeak);
                            bundle.putString("iv",tax);
                            bundle.putString("v",avgconsmonth);
                            bundle.putString("vi",consyear);
                            bundle.putString("vii",morconsmonth);
                            bundle.putString("viii",avgmonthbill);
                            intent.putExtras(bundle);

                            startActivity(intent);
                        }
                    });





                    Window window = dialog1.getWindow();
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                }

            }
        });

        resmedvolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Morning: 0,54 \nPeak: 0,82 \nOffPeak: 0,34 \nTax: 0,24", Toast.LENGTH_SHORT).show();
            }
        });

        comlowvolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Morning: 0,76 \nPeak: 1,11 \nOffPeak: 0,49 \nTax: 0,2366", Toast.LENGTH_SHORT).show();
            }
        });

        commedvolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Morning: 0,72 \nPeak: 1,07 \nOffPeak: 0,45 \nTax: 0,2366", Toast.LENGTH_SHORT).show();
            }
        });

        indlowvolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Morning: 0,66 \nPeak: 0,99 \nOffPeak: 0,40 \nTax: 0,2366", Toast.LENGTH_SHORT).show();

            }
        });

        indmedvolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Morning: 0,60 \nPeak: 0,92 \nOffPeak: 0,34 \nTax: 0,2366", Toast.LENGTH_SHORT).show();

            }
        });


        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }
    public void showMyCustomOwnAlertDialog() {

        // dialog nesnesi oluştur ve layout dosyasına bağlan
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.use_own_consumer);

        // custom dialog elemanlarını tanımla - text, image ve button
        Button ownconfirmbtn=dialog.findViewById(R.id.own_confirm_button);

        // custom dialog elemanlarına değer ataması yap - text, image


        // tamam butonunun tıklanma olayları
        ownconfirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Ownconfirme basıldı", Toast.LENGTH_SHORT).show();
            }
        });


        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }
    public void showMyCustomEPDKAlertDialogPro() {

        // dialog nesnesi oluştur ve layout dosyasına bağlan
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.prosumer_type);

        // custom dialog elemanlarını tanımla - text, image ve button
        Button reslowvolt=dialog.findViewById(R.id.button_res_low_voltage);
        Button resmedvolt=dialog.findViewById(R.id.button_res_med_voltage);
        Button comlowvolt=dialog.findViewById(R.id.button_com_low_voltage);
        Button commedvolt=dialog.findViewById(R.id.button_com_med_voltage);
        Button indlowvolt=dialog.findViewById(R.id.button_ind_low_voltage);
        Button indmedvolt=dialog.findViewById(R.id.button_ind_med_voltage);

        // custom dialog elemanlarına değer ataması yap - text, image




        reslowvolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Morning: 0,57 \nPeak: 0,85 \nOffPeak: 0,37 \nTax: 0,24", Toast.LENGTH_SHORT).show();
            }
        });

        resmedvolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Morning: 0,54 \nPeak: 0,82 \nOffPeak: 0,34 \nTax: 0,24", Toast.LENGTH_SHORT).show();
            }
        });

        comlowvolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Morning: 0,76 \nPeak: 1,11 \nOffPeak: 0,49 \nTax: 0,2366", Toast.LENGTH_SHORT).show();
            }
        });

        commedvolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Morning: 0,72 \nPeak: 1,07 \nOffPeak: 0,45 \nTax: 0,2366", Toast.LENGTH_SHORT).show();
            }
        });

        indlowvolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Morning: 0,66 \nPeak: 0,99 \nOffPeak: 0,40 \nTax: 0,2366", Toast.LENGTH_SHORT).show();

            }
        });

        indmedvolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Morning: 0,60 \nPeak: 0,92 \nOffPeak: 0,34 \nTax: 0,2366", Toast.LENGTH_SHORT).show();

            }
        });
        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }



    public void showMyCustomOwnAlertDialogPro() {

        // dialog nesnesi oluştur ve layout dosyasına bağlan
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.use_own_prosumer);

        // custom dialog elemanlarını tanımla - text, image ve button
        Button ownconfirmbtn=dialog.findViewById(R.id.own_confirm_button);

        // custom dialog elemanlarına değer ataması yap - text, image


        // tamam butonunun tıklanma olayları
        ownconfirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Ownconfirme basıldı", Toast.LENGTH_SHORT).show();
            }
        });


        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }
}

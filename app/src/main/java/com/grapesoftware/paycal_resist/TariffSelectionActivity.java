package com.grapesoftware.paycal_resist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

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


        // custom dialog elemanlarına değer ataması yap - text, image
        reslowvolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "reslowvolt basıldı", Toast.LENGTH_SHORT).show();
            }
        });

        resmedvolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
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


        // tamam butonunun tıklanma olayları
        reslowvolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "reslowvolt basıldı", Toast.LENGTH_SHORT).show();
            }
        });
        // iptal butonunun tıklanma olayları
        resmedvolt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
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

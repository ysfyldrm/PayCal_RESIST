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
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SupplierMcpSelectionActivity extends AppCompatActivity {

    private Button TurkeyMcpbtn, useownbtn;
    final Context context = this;
    Bundle gelentype;
    String typeforuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_mcp_selection);

        TurkeyMcpbtn=findViewById(R.id.epdk_button);
        useownbtn=findViewById(R.id.useOwn_button);

        gelentype=getIntent().getExtras();
        typeforuser=gelentype.getString("Type");


        useownbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, typeforuser, Toast.LENGTH_LONG).show();

                if (typeforuser.equals("Supplier")){
                    showMyCustomOwnAlertDialog();
                }
            }
        });

        TurkeyMcpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SupplierMcpSelectionActivity.this,ResTypeActivity.class);
                startActivity(intent);

                // buraya bundle eklemelerini yaparız.


            }
        });


    }

    public void showMyCustomOwnAlertDialog() {

        // dialog nesnesi oluştur ve layout dosyasına bağlan
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.use_own_supplier);

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

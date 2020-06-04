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
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SupplierMcpSelectionActivity extends AppCompatActivity {

    private Button TurkeyMcpbtn, useownbtn, backbutton;
    final Context context = this;
    Bundle gelentype;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String typeforuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier_mcp_selection);

        TurkeyMcpbtn=findViewById(R.id.epdk_button);
        useownbtn=findViewById(R.id.useOwn_button);

        gelentype=getIntent().getExtras();
        typeforuser=gelentype.getString("Type");
        preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);
        editor = preferences.edit();

        backbutton = findViewById(R.id.backBtn);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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


                editor.putString("AVG Peak MCP","0.0527");
                editor.putString("AVG Morning MCP","0.0462");

                editor.commit();
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
        final EditText peakmcp=dialog.findViewById(R.id.peakMCP_edt);
        final EditText morningmcp=dialog.findViewById(R.id.morningMCP_edt);
        final EditText avgpeakmcp=dialog.findViewById(R.id.avg_peakMCP_edt);
        final EditText avgmorningmcp=dialog.findViewById(R.id.avg_morningMCP_edt);
        final EditText avgyearmcp=dialog.findViewById(R.id.avg_yearMCP_edt);

        // tamam butonunun tıklanma olayları
        ownconfirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putString("Peak MCP", peakmcp.getText().toString());
                editor.putString("Morning MCP", morningmcp.getText().toString());
                editor.putString("AVG Peak MCP", avgpeakmcp.toString());
                editor.putString("AVG Morning MCP", avgmorningmcp.getText().toString());
                editor.putString("AVG Year MCP", avgyearmcp.getText().toString());
                editor.commit();

                Intent intent = new Intent(SupplierMcpSelectionActivity.this, ResTypeActivity.class);
                startActivity(intent);

            }
        });


        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }
}

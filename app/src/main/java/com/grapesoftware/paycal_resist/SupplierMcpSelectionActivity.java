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

import androidx.appcompat.app.AppCompatActivity;

public class SupplierMcpSelectionActivity extends AppCompatActivity {

    private Button TurkeyMcpbtn, useownbtn, backbutton,profile;
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
        profile=findViewById(R.id.profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SupplierMcpSelectionActivity.this,UserProfile.class);
                startActivity(intent);
                //finish();
            }
        });

        useownbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


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
                if(TextUtils.isEmpty(peakmcp.getText().toString())) {
                    peakmcp.setError("This field cannot be empty");
                    peakmcp.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(morningmcp.getText().toString())){
                    morningmcp.setError("This field cannot be empty");
                    morningmcp.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(avgpeakmcp.getText().toString())){
                    avgpeakmcp.setError("This field cannot be empty");
                    avgpeakmcp.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(avgmorningmcp.getText().toString())){
                    avgmorningmcp.setError("This field cannot be empty");
                    avgmorningmcp.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(avgyearmcp.getText().toString())){
                    avgyearmcp.setError("This field cannot be empty");
                    avgyearmcp.requestFocus();
                    return;
                }
                else {
                    editor.putString("Peak MCP", peakmcp.getText().toString());
                    editor.putString("Morning MCP", morningmcp.getText().toString());
                    editor.putString("AVG Peak MCP", avgpeakmcp.toString());
                    editor.putString("AVG Morning MCP", avgmorningmcp.getText().toString());
                    editor.putString("AVG Year MCP", avgyearmcp.getText().toString());
                    editor.commit();
                    Intent intent = new Intent(SupplierMcpSelectionActivity.this, ResTypeActivity.class);
                    startActivity(intent);
                }
            }
        });


        dialog.show();
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }
}

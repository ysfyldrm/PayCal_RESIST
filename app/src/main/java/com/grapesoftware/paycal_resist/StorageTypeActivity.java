package com.grapesoftware.paycal_resist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class StorageTypeActivity extends AppCompatActivity {

    private Button lionBtn, leadAcidBtn, thermalBtn, noStorageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_type);

        lionBtn = findViewById(R.id.button_Li_ion);
        leadAcidBtn = findViewById(R.id.button_leadAcid);
        thermalBtn = findViewById(R.id.button_thermal);
        noStorageBtn = findViewById(R.id.button_no_storage);

    }
}

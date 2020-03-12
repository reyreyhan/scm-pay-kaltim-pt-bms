package com.bm.main.fpl.activities;

import android.os.Bundle;

import com.bm.main.pos.R;

public class SettingPrinterAdvanceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_printer_advance);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Setting Printer");
        init(1);
    }
}

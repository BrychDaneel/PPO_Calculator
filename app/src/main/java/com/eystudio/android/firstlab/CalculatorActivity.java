package com.eystudio.android.firstlab;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.TabHost;

public class CalculatorActivity extends Activity {

    final  String mNormalTabTag = "NormalTag";
    final  String mProgramTabTag = "ProgramTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        FragmentManager fragmentManager = getFragmentManager();

        Fragment normalFragment = fragmentManager.findFragmentById(R.id.normal_calculator_container);
        if (normalFragment == null) {
            normalFragment = new CalculatorFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.normal_calculator_container, normalFragment).commit();
        }

        Fragment progFragment = fragmentManager.findFragmentById(R.id.normal_calculator_container);
        if (progFragment == null) {
            progFragment = new CalculatorFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.normal_calculator_container, progFragment).commit();
        }


        TabHost tabHost = findViewById(R.id.tab_host);
        tabHost.setup();

        TabHost.TabSpec normTabSpec = tabHost.newTabSpec(mNormalTabTag);
        normTabSpec.setContent(R.id.normal_calculator_container);
        normTabSpec.setIndicator(getString(R.string.normal_tab));
        tabHost.addTab(normTabSpec);

        TabHost.TabSpec progTabSpec = tabHost.newTabSpec(mProgramTabTag);
        progTabSpec.setContent(R.id.program_calculator_container);
        progTabSpec.setIndicator(getString(R.string.program_tab));
        tabHost.addTab(progTabSpec);

        tabHost.setCurrentTab(0);
    }
}

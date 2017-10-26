package com.eystudio.android.firstlab;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

public class CalculatorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.normal_calculator_container);
        if (fragment == null) {
            fragment = new CalculatorFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.normal_calculator_container, fragment).commit();
        }
    }
}

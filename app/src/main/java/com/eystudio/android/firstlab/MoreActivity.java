package com.eystudio.android.firstlab;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MoreActivity extends AppCompatActivity implements View.OnClickListener{

    final double DEFAULT_VALUE = 0;
    final String VALUE_KEY = "com.eystudio.android.firstlab.more.Value";
    final String VALUE_KEY_RET = "com.eystudio.android.firstlab.more.RetValue";
    double mValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        mValue = getIntent().getDoubleExtra(VALUE_KEY, DEFAULT_VALUE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_more_ok:
                Intent intent = new Intent();
                intent.putExtra(VALUE_KEY_RET, mValue);
                setResult(Activity.RESULT_OK, intent);
                break;
        }
    }
}

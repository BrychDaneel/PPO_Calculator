package com.eystudio.android.firstlab;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MoreActivity extends AppCompatActivity implements View.OnClickListener{

    final double DEFAULT_VALUE = 0;
    final String VALUE_KEY = "com.eystudio.android.firstlab.more.Value";
    final String VALUE_KEY_RET = "com.eystudio.android.firstlab.more.RetValue";

    final String BUBLE_VALUE = "MoreValue";
    final String BUBLE_NEW_VALUE = "MoreNewValue";

    double mValue;
    double mNewValue;

    TextView tvNewValue, tvValue;

    int[] mButtonIDs = new int[] {R.id.button_sin, R.id.button_cos,
                                  R.id.button_tan, R.id.button_ctg,
                                  R.id.button_more_ok};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        tvValue = (TextView) findViewById(R.id.more_value);
        tvNewValue = (TextView) findViewById(R.id.more_new_value);
        mValue = getIntent().getDoubleExtra(VALUE_KEY, DEFAULT_VALUE);
        updateValue();
        for (int id : mButtonIDs)
            findViewById(id).setOnClickListener(this);

        if (savedInstanceState != null){
            mValue = savedInstanceState.getDouble(BUBLE_VALUE);
            mNewValue = savedInstanceState.getDouble(BUBLE_NEW_VALUE);
            updateValue();
            updateNewValue();
        }
    }

    @Override
    public void onClick(View view) {

        try {
            switch (view.getId()) {
                case R.id.button_more_ok:
                    mValue = mNewValue;
                    Intent intent = new Intent();
                    intent.putExtra(VALUE_KEY_RET, mValue);
                    setResult(Activity.RESULT_OK, intent);
                    updateValue();
                    break;
                case R.id.button_sin:
                    mNewValue = Math.sin(mValue);
                    updateNewValue();
                    break;
                case R.id.button_cos:
                    mNewValue = Math.cos(mValue);
                    updateNewValue();
                    break;
                case R.id.button_tan:
                    mNewValue = Math.tan(mValue);
                    updateNewValue();
                    break;
                case R.id.button_ctg:
                    mNewValue = 1 / Math.tan(mValue);
                    updateNewValue();
                    break;
            }
        } catch (ArithmeticException e){
            mNewValue = 0;
            tvNewValue.setText(getText(R.string.error));
        }
    }

    void updateValue(){
        tvValue.setText(String.format("%g", mValue));
    }

    void updateNewValue(){
        tvNewValue.setText(String.format("%g", mNewValue));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble(BUBLE_VALUE, mValue);
        outState.putDouble(BUBLE_NEW_VALUE, mNewValue);
    }
}

package com.eystudio.android.firstlab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.eystudio.android.firstlab.models.OperationType;

public class CalculatorActivity extends AppCompatActivity {

    OperationType mLastOperation = OperationType.Equel;
    int mLastValue = 0;
    int mValue = 0;

    int [] mNumButtonsID = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9};

    Button[] mNumButtons;
    Button mAddButton;
    Button mSubButton;
    Button mMultButton;
    Button mDivButton;
    Button mEquelButton;
    Button mCleanButton;

    TextView mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        for (int i=0; i < mNumButtonsID.length; i++)
            mNumButtons[i] = (Button) findViewById(mNumButtonsID[i]);

        mAddButton = (Button) findViewById(R.id.button_add);
        mSubButton = (Button) findViewById(R.id.button_sub);
        mMultButton = (Button) findViewById(R.id.button_mult);
        mDivButton = (Button) findViewById(R.id.button_div);
        mEquelButton = (Button) findViewById(R.id.button_equal);
        mCleanButton = (Button) findViewById(R.id.button_clean);

        mResult = (TextView) findViewById(R.id.result);
    }
}

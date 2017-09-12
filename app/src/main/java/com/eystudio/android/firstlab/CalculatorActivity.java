package com.eystudio.android.firstlab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eystudio.android.firstlab.models.OperationType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalculatorActivity extends AppCompatActivity {

    OperationType mLastOperation = OperationType.Equel;
    int mLastValue = 0;
    int mValue = 0;

    final String VALUE_KEY = "calculator_value";
    final String LAST_VALUE_KEY = "calculator_last_value";
    final String OPERATION_KEY = "calculator_operation";

    int [] mNumButtonsID = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9};

    final List<Button> mNumButtons = new ArrayList<>();
    Button mAddButton;
    Button mSubButton;
    Button mMultButton;
    Button mDivButton;
    Button mEquelButton;
    Button mCleanButton;

    TextView mResult;

    void updateResult(){
        mResult.setText(Integer.toString(mValue));
    }

    void error(){
        mResult.setText(R.string.error);
    }

    void apply_operation(){

        long newValue = mValue;

        try {
            switch (mLastOperation) {
                case Add:
                    newValue = mLastValue + newValue;
                    break;
                case Sub:
                    newValue = mLastValue - newValue;
                    break;
                case Mult:
                    newValue = mLastValue * newValue;
                    break;
                case Div:
                    newValue = mLastValue / newValue;
                    break;
            }
        } catch (ArithmeticException e){
            mValue = 0;
            error();
            return;
        }

        if (newValue > Integer.MAX_VALUE || newValue < Integer.MIN_VALUE ){
            mValue = 0;
            error();
            return;
        }

        mValue = (int)newValue;
        updateResult();
    }

    void change_operation(OperationType operation){
        apply_operation();
        mLastValue = mValue;
        if (operation != OperationType.Equel)
            mValue = 0;
        mLastOperation = operation;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(VALUE_KEY, mValue);
        outState.putInt(LAST_VALUE_KEY, mLastValue);
        outState.putInt(OPERATION_KEY, mLastOperation.ordinal());
    }

    void bind(){
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_operation(OperationType.Add);
            }
        });

        mSubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_operation(OperationType.Sub);
            }
        });

        mMultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_operation(OperationType.Mult);
            }
        });

        mDivButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_operation(OperationType.Div);
            }
        });

        mEquelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_operation(OperationType.Equel);
            }
        });


        mCleanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mValue = 0;
                updateResult();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        for (int i=0; i < mNumButtonsID.length; i++) {
            final Button button = (Button) findViewById(mNumButtonsID[i]);
            mNumButtons.add(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long sig = 1;
                    if (mValue < 0)
                        sig = -1;

                    long newValue = (long)mValue * 10 +  sig * mNumButtons.indexOf(view);
                    if (newValue > Integer.MAX_VALUE || newValue < Integer.MIN_VALUE ){
                        mValue = 0;
                        error();
                        return;
                    }
                    mValue = (int) newValue;
                    updateResult();
                }
            });
        }

        mAddButton = (Button) findViewById(R.id.button_add);
        mSubButton = (Button) findViewById(R.id.button_sub);
        mMultButton = (Button) findViewById(R.id.button_mult);
        mDivButton = (Button) findViewById(R.id.button_div);
        mEquelButton = (Button) findViewById(R.id.button_equal);
        mCleanButton = (Button) findViewById(R.id.button_clean);

        mResult = (TextView) findViewById(R.id.result);

        bind();

        if (savedInstanceState != null){
            mValue = savedInstanceState.getInt(VALUE_KEY);
            mLastValue = savedInstanceState.getInt(LAST_VALUE_KEY);
            mLastOperation = OperationType.values()[savedInstanceState.getInt(OPERATION_KEY)];
            updateResult();
        }
    }
}

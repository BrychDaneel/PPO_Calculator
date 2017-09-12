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

        switch (mLastOperation) {
            case Add: newValue = mLastValue + mValue; break;
            case Sub: newValue = mLastValue - mValue; break;
            case Mult: newValue = mLastValue * mValue; break;
            case Div: newValue = mLastValue / mValue; break;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        for (int i=0; i < mNumButtonsID.length; i++) {
            final Button button = (Button) findViewById(mNumButtonsID[i]);
            mNumButtons.add(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long newValue = mValue * 10 + mNumButtons.indexOf(view);
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
}

package com.eystudio.android.firstlab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eystudio.android.firstlab.models.FloatInput;
import com.eystudio.android.firstlab.models.OperationType;

import java.util.ArrayList;
import java.util.List;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    final String VALUE_KEY = "calculator_value";
    final String LAST_VALUE_KEY = "calculator_last_value";
    final String OPERATION_KEY = "calculator_operation";
    final int MAX_LENGTH = 10;

    int [] mNumButtonsID = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9};

    final List<Button> mNumButtons = new ArrayList<>();
    Button mAddButton, mSubButton, mMultButton, mDivButton, mEquelButton, mCleanButton, mPointButton;

    TextView mResult;

    OperationType mOperation = OperationType.Equel;
    double mLastValue = 0d;
    FloatInput mValue = new FloatInput(MAX_LENGTH);

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(VALUE_KEY, mValue);
        outState.putDouble(LAST_VALUE_KEY, mLastValue);
        outState.putInt(OPERATION_KEY, mOperation.ordinal());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        getElements();
        bind();

        if (savedInstanceState != null){
            mValue = (FloatInput) savedInstanceState.getSerializable(VALUE_KEY);
            mLastValue = savedInstanceState.getInt(LAST_VALUE_KEY);
            mOperation = OperationType.values()[savedInstanceState.getInt(OPERATION_KEY)];
            updateResult();
        }
    }

    void bind(){
        for (Button button : mNumButtons)
            button.setOnClickListener(this);

        mAddButton.setOnClickListener(this);
        mSubButton.setOnClickListener(this);
        mMultButton.setOnClickListener(this);
        mDivButton.setOnClickListener(this);
        mEquelButton.setOnClickListener(this);
        mCleanButton.setOnClickListener(this);
        mPointButton.setOnClickListener(this);
    }

    void getElements(){
        for (int i=0; i < mNumButtonsID.length; i++) {
            final Button button = (Button) findViewById(mNumButtonsID[i]);
            mNumButtons.add(button);
            button.setOnClickListener(this);
        }

        mAddButton = (Button) findViewById(R.id.button_add);
        mSubButton = (Button) findViewById(R.id.button_sub);
        mMultButton = (Button) findViewById(R.id.button_mult);
        mDivButton = (Button) findViewById(R.id.button_div);
        mEquelButton = (Button) findViewById(R.id.button_equal);
        mCleanButton = (Button) findViewById(R.id.button_clean);
        mPointButton = (Button) findViewById(R.id.button_point);

        mResult = (TextView) findViewById(R.id.result);
    }

    @Override
    public void onClick(View view) {

        if (mNumButtons.contains(view)) {
            try {
                mValue.inputNumber(mNumButtons.indexOf(view));
                updateResult();
            } catch (ArithmeticException e){
                return;
            }
            return;
        }

        switch (view.getId()){
            case R.id.button_add: changeOperation(OperationType.Add); break;
            case R.id.button_sub: changeOperation(OperationType.Sub); break;
            case R.id.button_mult: changeOperation(OperationType.Mult); break;
            case R.id.button_div: changeOperation(OperationType.Div); break;
            case R.id.button_clean: mValue.clear(); updateResult(); break;
            case R.id.button_equal: changeOperation(OperationType.Equel); break;
            case R.id.button_point: mValue.inputPoint(); break;
        }
    }

    void updateResult(){
        mResult.setText(mValue.toString());
    }

    void applyOperation(){
        if (mOperation == OperationType.Equel)
            return;

        double newValue;
        try{
            newValue = mOperation.perfome(mLastValue, mValue.getValue());
        } catch (ArithmeticException e){
            mResult.setText(R.string.error);
            return;
        }

        mValue.setValue(newValue);
        updateResult();
    }

    void changeOperation(OperationType newOperation){
        applyOperation();
        mOperation = newOperation;
        mLastValue = mValue.getValue();
        if (mOperation != OperationType.Equel)
            mValue.clear();
    }

}

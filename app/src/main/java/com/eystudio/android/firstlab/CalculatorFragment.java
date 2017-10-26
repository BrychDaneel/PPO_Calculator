package com.eystudio.android.firstlab;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.eystudio.android.firstlab.models.FloatInput;
import com.eystudio.android.firstlab.models.OperationType;

import java.util.ArrayList;
import java.util.List;

public class CalculatorFragment extends Fragment implements View.OnClickListener {

    final String VALUE_KEY = "calculator_value";
    final String LAST_VALUE_KEY = "calculator_last_value";
    final String OPERATION_KEY = "calculator_operation";
    final int MAX_LENGTH = 10;
    final String MORE_VALUE_KEY = "com.eystudio.android.firstlab.more.Value";
    final String MORE_VALUE_KEY_RET = "com.eystudio.android.firstlab.more.RetValue";
    final int REQUEST_UPDATE_VALUE = 0;

    int [] mNumButtonsID = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9};

    final List<Button> mNumButtons = new ArrayList<>();
    Button mAddButton, mSubButton, mMultButton, mDivButton, mEquelButton,
            mCleanButton, mPointButton, mMoreButton;

    TextView mResult;

    OperationType mOperation = OperationType.Equel;
    double mLastValue = 0d;
    FloatInput mValue = new FloatInput(MAX_LENGTH);

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(VALUE_KEY, mValue);
        outState.putDouble(LAST_VALUE_KEY, mLastValue);
        outState.putInt(OPERATION_KEY, mOperation.ordinal());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator, container, false);
        getElements(view);
        bind();

        if (savedInstanceState != null){
            mValue = (FloatInput) savedInstanceState.getSerializable(VALUE_KEY);
            mLastValue = savedInstanceState.getDouble(LAST_VALUE_KEY);
            mOperation = OperationType.values()[savedInstanceState.getInt(OPERATION_KEY)];
            updateResult();
        }
        return view;
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
        mMoreButton.setOnClickListener(this);
    }

    void getElements(View view){
        for (int aMNumButtonsID : mNumButtonsID) {
            final Button button = view.findViewById(aMNumButtonsID);
            mNumButtons.add(button);
            button.setOnClickListener(this);
        }

        mAddButton = view.findViewById(R.id.button_add);
        mSubButton = view.findViewById(R.id.button_sub);
        mMultButton = view.findViewById(R.id.button_mult);
        mDivButton = view.findViewById(R.id.button_div);
        mEquelButton = view.findViewById(R.id.button_equal);
        mCleanButton = view.findViewById(R.id.button_clean);
        mPointButton = view.findViewById(R.id.button_point);
        mMoreButton = view.findViewById(R.id.button_more);

        mResult = view.findViewById(R.id.result);
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
            case R.id.button_more: more(); break;
        }
    }

    void more(){
        Intent intent = new Intent(this.getActivity(), MoreActivity.class);
        intent.putExtra(MORE_VALUE_KEY, mValue.getValue());
        startActivityForResult(intent, REQUEST_UPDATE_VALUE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_UPDATE_VALUE && resultCode == Activity.RESULT_OK){
            mValue.setValue(data.getDoubleExtra(MORE_VALUE_KEY_RET, 0));
            updateResult();
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

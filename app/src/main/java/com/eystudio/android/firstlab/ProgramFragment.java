package com.eystudio.android.firstlab;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.eystudio.android.firstlab.R;
import com.eystudio.android.firstlab.models.FloatInput;
import com.eystudio.android.firstlab.models.OperationType;

import java.util.ArrayList;
import java.util.List;

public class ProgramFragment extends Fragment implements View.OnClickListener{

    final String VALUE_KEY = "program_value";
    final String LAST_VALUE_KEY = "program_last_value";
    final String OPERATION_KEY = "program_operation";
    final int MAX_LENGTH = 10;
    final int START_VALUE = 0;


    OperationType mOperation = OperationType.Equel;
    double mLastValue = 0d;
    FloatInput mValue = new FloatInput(START_VALUE, MAX_LENGTH, 16);

    int [] mNumButtonsID = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9,
            R.id.buttonA, R.id.buttonB, R.id.buttonC, R.id.buttonD, R.id.buttonE, R.id.buttonF};


    final List<Button> mNumButtons = new ArrayList<>();
    Button mAndButton, mOrButton, mXorButton, mNotButton, mEquelButton,
            mCleanButton, mBinRadio, mDecRadio, mHexRadio;

    TextView mResult;

    void getElements(View view){
        for (int aMNumButtonsID : mNumButtonsID) {
            final Button button = view.findViewById(aMNumButtonsID);
            mNumButtons.add(button);
            button.setOnClickListener(this);
        }

        mAndButton = view.findViewById(R.id.button_and);
        mOrButton = view.findViewById(R.id.button_or);
        mXorButton = view.findViewById(R.id.button_xor);
        mNotButton = view.findViewById(R.id.button_not);
        mEquelButton = view.findViewById(R.id.button_equal);
        mCleanButton = view.findViewById(R.id.button_clean);
        mBinRadio = view.findViewById(R.id.radio_bin);
        mDecRadio = view.findViewById(R.id.radio_dec);
        mHexRadio = view.findViewById(R.id.radio_hex);

        mResult = view.findViewById(R.id.result);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_program, container, false);
        getElements(view);
        bind();
        return view;
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
        if (mOperation.isBinary())
            applyOperation();
        else
            if (mOperation != OperationType.Equel)
                mValue.clear();
    }

    void updateResult(){
        mResult.setText(mValue.toString());
    }


    void bind(){
        for (Button button : mNumButtons)
            button.setOnClickListener(this);

        mAndButton.setOnClickListener(this);
        mOrButton.setOnClickListener(this);
        mXorButton.setOnClickListener(this);
        mNotButton.setOnClickListener(this);
        mEquelButton.setOnClickListener(this);
        mCleanButton.setOnClickListener(this);
        mBinRadio.setOnClickListener(this);
        mDecRadio.setOnClickListener(this);
        mHexRadio.setOnClickListener(this);
    }

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
            case R.id.button_and: changeOperation(OperationType.And); break;
            case R.id.button_or: changeOperation(OperationType.Or); break;
            case R.id.button_xor: changeOperation(OperationType.Xor); break;
            case R.id.button_not: changeOperation(OperationType.Not); break;
            case R.id.button_clean: mValue.clear(); updateResult(); break;
            case R.id.button_equal: changeOperation(OperationType.Equel); break;
            case R.id.radio_bin: changeBase(2); break;
            case R.id.radio_dec: changeBase(10); break;
            case R.id.radio_hex: changeBase(16);break;
        }
    }

    void changeBase(int base){
        for (int i=0; i<base; i++)
            mNumButtons.get(i).setEnabled(true);
        for (int i=base; i<16; i++)
            mNumButtons.get(i).setEnabled(false);
        mValue.setBase(base);
        updateResult();
    }
}

package com.eystudio.android.firstlab.models;

import java.io.Serializable;
import java.util.Formatter;

/**
 * Created by daneel on 17.09.17.
 */

public class FloatInput implements Serializable{
    int mIntCount = 0;
    int mFloatCount = 0;
    int mMaxLength;
    double mValue;
    boolean mPointPressed = false;
    boolean mIsForeignValue = false;

    public FloatInput(int maxLength){
        mMaxLength = maxLength;
    }

    public FloatInput(double mValue, int maxLength){
        this(maxLength);
        setValue(mValue);
    }

    public void inputNumber(int num){

        if (mIsForeignValue)
            clear();

        if (mIntCount + mFloatCount >= mMaxLength)
            throw new ArithmeticException("Limit of numbers exceed.");

        if (!mPointPressed){
            mValue = mValue * 10 + num;
            mIntCount++;
        } else {
            mFloatCount++;
            mValue = mValue + Math.pow(10, -mFloatCount) * num;
        }
    }

    public void inputPoint(){
        mPointPressed = true;
    }

    public void clear(){
        mIsForeignValue = false;
        mValue = 0d;
        mFloatCount = 0;
        mIntCount = 0;
    }

    public int getIntCount() {
        return mIntCount;
    }

    public int getFloatCount() {
        return mFloatCount;
    }

    public double getValue() {
        return mValue;
    }

    public void setValue(double value){
        mIsForeignValue = true;
        mValue = value;
    }

    @Override
    public String toString() {
        Formatter formatter = new Formatter();
        String fmt;

        if (mIsForeignValue)
            if (mValue % 1 == 0)
                fmt = "%" + mMaxLength + ".0f";
            else
                fmt = "%g";
         else
            fmt = formatter.format("%%%d.%df", mIntCount > 0 ? mIntCount : 1, mFloatCount).toString();

        return new Formatter().format(fmt, mValue).toString();
    }
}

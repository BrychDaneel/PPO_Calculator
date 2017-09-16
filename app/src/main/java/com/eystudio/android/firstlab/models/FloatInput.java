package com.eystudio.android.firstlab.models;

import java.util.Formatter;

/**
 * Created by daneel on 17.09.17.
 */

public class FloatInput {
    int mIntCount = 0;
    int mFloatCount = 0;
    int mMaxLength;
    double mValue;
    boolean mPointPressed = false;

    public FloatInput(int mMaxLength){
        this.mMaxLength = mMaxLength;
    }

    public void inputNumber(int num){
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

    @Override
    public String toString() {
        Formatter formatter = new Formatter();
        String fmt = formatter.format("%%f%d.%d", mIntCount, mFloatCount).toString();
        return formatter.format(fmt, mValue).toString();
    }
}

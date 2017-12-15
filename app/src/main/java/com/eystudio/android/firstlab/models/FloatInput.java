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
    int mBase = 10;
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

    public FloatInput(double mValue, int maxLength, int mBase){
        this(mValue, maxLength);
        setBase(mBase);
    }

    public void inputNumber(int num){

        if (mIsForeignValue)
            clear();

        if (mIntCount + mFloatCount >= mMaxLength)
            throw new ArithmeticException("Limit of numbers exceed.");

        if (!mPointPressed){
            mValue = mValue * mBase + num;
            if (!(mIntCount == 0 && num == 0))
                mIntCount++;
        } else {
            if (num == 0)
                return;
            mFloatCount++;
            mValue = mValue + Math.pow(mBase, -mFloatCount) * num;
        }
    }

    public void inputPoint(){
        if (mIsForeignValue)
            clear();
        mPointPressed = true;
    }

    public void clear(){
        mIsForeignValue = false;
        mPointPressed = false;
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

    public int getBase() {
        return mBase;
    }

    public void setBase(int mBase) {
        this.mBase = mBase;
    }

    @Override
    public String toString() {
        Formatter formatter = new Formatter();
        String fmt;

        if (mValue % 1 == 0) {
            int intValue = (int)Math.round(mValue);
            switch (mBase) {
                case 2:
                    return Integer.toBinaryString(intValue);
                case 10:
                    return Integer.toString(intValue);
                case 16:
                    return Integer.toHexString(intValue);
                default:
                    fmt = "%" + mMaxLength + ".0f";
            }
        } else

            if (mIsForeignValue)
                fmt = "%g";
             else
                fmt = formatter.format("%%%d.%df", mIntCount > 0 ? mIntCount : 1, mFloatCount).toString();

        return new Formatter().format(fmt, mValue).toString();
    }
}

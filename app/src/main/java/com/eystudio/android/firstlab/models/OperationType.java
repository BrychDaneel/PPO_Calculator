package com.eystudio.android.firstlab.models;

/**
 * Created by daneel on 12.09.17.
 */

public enum OperationType {
    Equel, Add, Sub, Mult, Div, And, Or, Xor, Not;

    public double perfome(double first_value, double second_value){
        switch (this){
            case Add:
                return first_value + second_value;
            case Sub:
                return first_value - second_value;
            case Mult:
                return first_value * second_value;
            case Div:
                return first_value / second_value;
            case And:
                return Math.round(first_value) & Math.round(second_value);
            case Or:
                return Math.round(first_value) | Math.round(second_value);
            case Xor:
                return Math.round(first_value) ^ Math.round(second_value);
            case Not:
                return ~Math.round(second_value);
            default:
                return  second_value;
        }
    }
}

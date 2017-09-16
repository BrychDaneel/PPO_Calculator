package com.eystudio.android.firstlab.models;

/**
 * Created by daneel on 12.09.17.
 */

public enum OperationType {
    Equel, Add, Sub, Mult, Div;

    double perfom(double first_value, double second_value){
        switch (this){
            case Add:
                return first_value + second_value;
            case Sub:
                return first_value - second_value;
            case Mult:
                return first_value * second_value;
            case Div:
                return first_value / second_value;
            default:
                throw new UnsupportedOperationException("Can't perfom " + this + " to operands.");
        }
    }
}

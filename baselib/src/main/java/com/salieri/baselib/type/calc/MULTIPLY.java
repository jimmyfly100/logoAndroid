package com.salieri.baselib.type.calc;

import com.salieri.baselib.type.NUM;

public class MULTIPLY extends CALCMARK{
    @Override
    public boolean isSuperior() {
        return true;
    }

    @Override
    public NUM calculate(NUM num1, NUM num2) {
        return new NUM(num1.value * num2.value);
    }
}

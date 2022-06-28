package com.salieri.baselib.type.calc;

import com.salieri.baselib.type.NUM;
import com.salieri.baselib.type.TYPE;

public abstract class CALCMARK implements TYPE {
    public abstract boolean isSuperior();
    public abstract NUM calculate(NUM num1, NUM num2);
}

package com.salieri.baselib.type;

import java.util.LinkedList;
import java.util.List;

public class BRACKET implements TYPE {
    public List<TYPE> value;

    public BRACKET(List<TYPE> value) {
        this.value = value;
    }
}

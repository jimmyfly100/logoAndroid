package com.salieri.baselib.core;

import com.salieri.baselib.type.BRACKET;
import com.salieri.baselib.type.NUM;
import com.salieri.baselib.type.TYPE;
import com.salieri.baselib.type.calc.CALCMARK;

import java.util.LinkedList;
import java.util.List;

public class Calculator {
    public String field = "";

    public Calculator(String field) {
        this.field = field;
    }

    public NUM calculate(List<TYPE> typeList) {
        List<TYPE> originList = new LinkedList<>(typeList);
        if (originList.size() == 0) return null;
        if (originList.size() == 1 && originList.get(0) instanceof NUM) {
            return (NUM) originList.get(0);
        }
        LinkedList<TYPE> list = new LinkedList<>();
        for (TYPE type : originList) {
            if (type instanceof NUM || type instanceof CALCMARK) {
                list.add(type);
            } else if (type instanceof BRACKET) {
                list.add(new Calculator(field).calculate(((BRACKET) type).value));
            }
        }
        LinkedList<TYPE> secondList = new LinkedList<>();
        CALCMARK calcmark = null;
        for (TYPE type : list) {
            if (calcmark != null) {
                if (secondList.size() > 0 && secondList.getLast() instanceof NUM && type instanceof NUM) {
                    NUM num = calcmark.calculate((NUM) secondList.removeLast(), (NUM) type);
                    secondList.add(num);
                } else {
                    EngineHolder.getEngine().error("syntax error in calculation");
                }
                calcmark = null;
            } else {
                if (type instanceof CALCMARK && ((CALCMARK) type).isSuperior()) {
                    calcmark = (CALCMARK) type;
                } else {
                    secondList.add(type);
                }
            }
        }
        calcmark = null;
        NUM ans = null;
        for (TYPE type : secondList) {
            if (calcmark != null) {
                if (ans != null  && type instanceof NUM) {
                    ans = calcmark.calculate(ans, (NUM) type);
                } else {
                    EngineHolder.getEngine().error("syntax error in calculation");
                }
                calcmark = null;
            } else {
                if (type instanceof CALCMARK) {
                    calcmark = (CALCMARK) type;
                } else if (ans == null) {
                    ans = (NUM) type;
                } else {
                    EngineHolder.getEngine().error("syntax error in calculation");
                }
            }
        }
        return ans;
    }
}

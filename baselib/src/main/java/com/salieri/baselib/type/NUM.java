package com.salieri.baselib.type;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.salieri.baselib.core.CoreManager;

public class NUM implements TYPE{

    public NUM(double value) {
        this.value = value;
    }

    public double value = 0;

    public double getValue() {
        return value;
//        if (TextUtils.isEmpty(varName) || fromMap) return value;
//        return CoreManager.getInstance().getVarValue(varName).value;
    }
    public String varName = "";

    public static NUM clone(NUM num) {
        if (num == null) return null;
        NUM instance = new NUM(num.value);
        instance.varName = num.varName;
        return instance;
    }

}

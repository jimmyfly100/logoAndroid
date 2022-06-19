package com.salieri.baselib.task.logotask;

import android.util.Log;

import com.salieri.baselib.core.EngineHolder;
import com.salieri.baselib.task.LogoTask;
import com.salieri.baselib.type.NUM;

import java.util.LinkedList;
import java.util.List;

public class FD implements LogoTask {
    private NUM value;

    public FD(NUM value) {
        this.value = value;
    }

    @Override
    public void run() {
        EngineHolder.getEngine().FD(value);
    }

}

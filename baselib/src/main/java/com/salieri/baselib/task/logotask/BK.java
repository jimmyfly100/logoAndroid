package com.salieri.baselib.task.logotask;

import com.salieri.baselib.core.EngineHolder;
import com.salieri.baselib.task.LogoTask;
import com.salieri.baselib.type.NUM;

public class BK implements LogoTask {
    private NUM value;

    public BK(NUM value) {
        this.value = value;
    }

    @Override
    public void run() {
        EngineHolder.getEngine().BK(value);
    }
}

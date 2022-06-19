package com.salieri.baselib.task.logotask;

import com.salieri.baselib.core.EngineHolder;
import com.salieri.baselib.task.LogoTask;
import com.salieri.baselib.type.NUM;

public class LT implements LogoTask {
    private NUM value;

    public LT(NUM value) {
        this.value = value;
    }

    @Override
    public void run() {
        EngineHolder.getEngine().LT(value);
    }
}

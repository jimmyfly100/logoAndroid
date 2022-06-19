package com.salieri.baselib.task.logotask;

import com.salieri.baselib.core.EngineHolder;
import com.salieri.baselib.task.LogoTask;
import com.salieri.baselib.type.NUM;

public class RT implements LogoTask {
    private NUM value;

    public RT(NUM value) {
        this.value = value;
    }

    @Override
    public void run() {
        EngineHolder.getEngine().RT(value);
    }
}

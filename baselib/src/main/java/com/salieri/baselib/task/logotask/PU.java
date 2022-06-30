package com.salieri.baselib.task.logotask;

import com.salieri.baselib.core.EngineHolder;
import com.salieri.baselib.task.LogoTask;

public class PU implements LogoTask {
    @Override
    public void run() {
        EngineHolder.getEngine().PU();
    }
}

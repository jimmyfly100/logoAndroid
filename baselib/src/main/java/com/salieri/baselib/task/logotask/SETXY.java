package com.salieri.baselib.task.logotask;

import com.salieri.baselib.core.EngineHolder;
import com.salieri.baselib.task.LogoTask;
import com.salieri.baselib.type.NUM;

public class SETXY implements LogoTask {
    private NUM x;
    private NUM y;

    public SETXY(NUM x, NUM y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void run() {
        EngineHolder.getEngine().SETXY(x, y);
    }
}

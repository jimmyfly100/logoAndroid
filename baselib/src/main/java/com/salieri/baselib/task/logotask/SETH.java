package com.salieri.baselib.task.logotask;

import com.salieri.baselib.core.EngineHolder;
import com.salieri.baselib.task.LogoTask;
import com.salieri.baselib.type.NUM;

public class SETH implements LogoTask {
    private NUM value;

    public SETH(NUM value) {
        this.value = value;
    }

    @Override
    public void run() {
        EngineHolder.getEngine().SETH(value);
    }
}

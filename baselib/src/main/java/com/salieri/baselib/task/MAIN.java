package com.salieri.baselib.task;

import com.salieri.baselib.core.CoreManager;
import com.salieri.baselib.core.EngineHolder;
import com.salieri.baselib.task.TaskSet;
import com.salieri.baselib.type.CODE;

public class MAIN extends TaskSet {
    public MAIN(CODE code) {
        super(code);
        decoder.setCanDefineFunc(true);
    }

    @Override
    public void setField(String field) {

    }

    @Override
    public void run() {
//        CoreManager.getInstance().clearMainVar();
        super.run();
        EngineHolder.getEngine().drawTurtle();
    }
}

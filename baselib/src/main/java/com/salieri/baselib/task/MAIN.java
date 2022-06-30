package com.salieri.baselib.task;

import android.util.Log;

import com.salieri.baselib.core.CoreManager;
import com.salieri.baselib.core.EngineHolder;
import com.salieri.baselib.task.TaskSet;
import com.salieri.baselib.type.CODE;
import com.salieri.baselib.utils.ThreadUtil;

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
//        super.run();
        ThreadUtil.runOnChildThread(new Runnable() {
            @Override
            public void run() {
                decoder.decode(code);
                for (LogoTask task : taskList) {
                    task.run();
                }
                taskList.clear();
                EngineHolder.getEngine().drawTurtle();
            }
        });
    }
}

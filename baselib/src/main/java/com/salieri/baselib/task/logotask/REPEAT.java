package com.salieri.baselib.task.logotask;

import com.salieri.baselib.task.TaskSet;
import com.salieri.baselib.type.CODE;
import com.salieri.baselib.type.NUM;

public class REPEAT extends TaskSet {
    private int times = 1;
    public REPEAT(NUM num, CODE code) {
        super(code);
        times = (int) Math.round(num.value);
//        for (int i = 1; i < times; i++) {
//            decoder.decode(code);
//        }
    }

    @Override
    public void run() {
        for(int i = 0; i < times; i++) {
            super.run();
        }
//        super.run();
    }
}

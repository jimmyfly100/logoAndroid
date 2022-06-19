package com.salieri.baselib.task;

import android.util.Log;

import com.salieri.baselib.core.CoreManager;
import com.salieri.baselib.core.Decoder;
import com.salieri.baselib.type.CODE;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class TaskSet implements LogoTask{
    protected CODE code;
    protected List<LogoTask> taskList = new LinkedList<>();
    protected String field = CoreManager.MAIN_FIELD;
    protected Decoder decoder;

    public TaskSet(CODE code) {
        this.code = code;
        decoder = new Decoder(this, field);
//        decoder.decode(code);
    }

    public TaskSet(CODE code, String field) {
        this.code = code;
        this.field = field;
        decoder = new Decoder(this, field);
    }

    public void setField(String field) {
        this.field = field;
        decoder.setField(field);
    }

    @Override
    public void run() {
        decoder.decode(code);
        for (LogoTask task : taskList) {
            task.run();
        }
        taskList.clear();
    }

    public void add(LogoTask task) {
        taskList.add(task);
    }

}

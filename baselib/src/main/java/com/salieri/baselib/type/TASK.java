package com.salieri.baselib.type;

import com.salieri.baselib.task.LogoTask;

public class TASK implements TYPE{
    public Class<? extends LogoTask> value;

    public TASK(Class<? extends LogoTask> value) {
        this.value = value;
    }
}

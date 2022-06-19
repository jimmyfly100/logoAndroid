package com.salieri.baselib.core;

import com.salieri.baselib.task.LogoTask;
import com.salieri.baselib.task.TaskSet;
import com.salieri.baselib.task.logotask.BK;
import com.salieri.baselib.task.logotask.FUNC;
import com.salieri.baselib.task.logotask.FD;
import com.salieri.baselib.task.logotask.LT;
import com.salieri.baselib.task.logotask.REPEAT;
import com.salieri.baselib.task.logotask.RT;
import com.salieri.baselib.type.CODE;
import com.salieri.baselib.type.NUM;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CoreManager {
    public static final String MAIN_FIELD = " _MAIN_ ";
    private Set<Class<? extends LogoTask>> taskList = new HashSet<>();
    private Map<String, FUNC.Content> funcMap = new HashMap<>();
    private Map<String, Map<String, NUM>> funcVarMap = new HashMap<>();
    private static CoreManager instance = new CoreManager();
    private CoreManager(){
        taskList.add(FD.class);
        taskList.add(BK.class);
        taskList.add(RT.class);
        taskList.add(LT.class);
        taskList.add(FUNC.class);
        taskList.add(REPEAT.class);
        funcVarMap.put(MAIN_FIELD, new HashMap<>());
    }

    public void init() {

    }

    public boolean isTask(String text) {
        for (Class<? extends LogoTask> clazz : taskList) {
            if (clazz.getSimpleName().equals(text)) return true;
        }
        return false;
    }

    public Class<? extends LogoTask> getTask(String text) {
        for (Class<? extends LogoTask> clazz : taskList) {
            if (clazz.getSimpleName().equals(text)) return clazz;
        }
        return null;
    }

    public static CoreManager getInstance() {
        return instance;
    }


    public void registerFunc(String name, FUNC.Content code) {
        funcMap.put(name, code);
    }

    public boolean isFunc(String name) {
        return funcMap.containsKey(name);
    }

    public boolean isVariable(String field, String name) {
        if (!funcVarMap.containsKey(field)) {
            return false;
        } else {
            return funcVarMap.get(field).containsKey(name);
        }
    }

    public NUM getVarValue(String field, String name) {
        if (!funcVarMap.containsKey(field)) {
            return null;
        } else {
            return NUM.clone(funcVarMap.get(field).get(name));
//            return funcVarMap.get(field).get(name);
        }
    }

    public void registerVar(String field, String name, NUM value) {
        if (funcVarMap.containsKey(field)) {
            funcVarMap.get(field).put(name, value);
        } else {
            Map<String, NUM> map = new HashMap<>();
            map.put(name, value);
            funcVarMap.put(field, map);
        }
    }

    public void clearFuncVar(String field) {
        funcVarMap.remove(field);
    }

    public FUNC.Content getFuncContent(String name) {
        return FUNC.Content.clone(funcMap.get(name));
//        return funcMap.get(name);
    }
}

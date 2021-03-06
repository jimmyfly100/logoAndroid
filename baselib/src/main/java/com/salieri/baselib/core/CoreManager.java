package com.salieri.baselib.core;

import com.salieri.baselib.task.LogoTask;
import com.salieri.baselib.task.logotask.BK;
import com.salieri.baselib.task.FUNC;
import com.salieri.baselib.task.logotask.FD;
import com.salieri.baselib.task.logotask.LT;
import com.salieri.baselib.task.logotask.PD;
import com.salieri.baselib.task.logotask.PPT;
import com.salieri.baselib.task.logotask.PU;
import com.salieri.baselib.task.logotask.PX;
import com.salieri.baselib.task.logotask.REPEAT;
import com.salieri.baselib.task.logotask.RT;
import com.salieri.baselib.task.logotask.SETH;
import com.salieri.baselib.task.logotask.SETXY;
import com.salieri.baselib.type.NUM;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class CoreManager {
    public static final String MAIN_FIELD = " _MAIN_ ";
    private Set<Class<? extends LogoTask>> taskList = new HashSet<>();
    private Map<String, FUNC.Content> funcMap = new HashMap<>();
    private Map<String, Map<String, NUM>> funcVarMap = new LinkedHashMap<>();
    private static CoreManager instance = new CoreManager();
    private Map<String, NUM> mainVarMap = new HashMap<>();
    private CoreManager(){
        taskList.add(FD.class);
        taskList.add(BK.class);
        taskList.add(RT.class);
        taskList.add(LT.class);
//        taskList.add(FUNC.class);
        taskList.add(REPEAT.class);
        taskList.add(PU.class);
        taskList.add(PD.class);
        taskList.add(SETXY.class);
        taskList.add(SETH.class);
        taskList.add(PX.class);
        taskList.add(PPT.class);
        funcVarMap.put(MAIN_FIELD, mainVarMap);
    }

    public void init() {

    }

    public boolean isTask(String text) {
        String upper = text.toUpperCase();
        for (Class<? extends LogoTask> clazz : taskList) {
            if (clazz.getSimpleName().equals(upper)) return true;
        }
        return false;
    }

    public Class<? extends LogoTask> getTask(String text) {
        String upper = text.toUpperCase();
        for (Class<? extends LogoTask> clazz : taskList) {
            if (clazz.getSimpleName().equals(upper)) return clazz;
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

    public void clearMainVar() {
        mainVarMap.clear();
    }

    public void removeFuncVar(String field) {
        funcVarMap.remove(field);
    }

    public FUNC.Content getFuncContent(String name) {
        return FUNC.Content.clone(funcMap.get(name));
//        return funcMap.get(name);
    }

    public Map<String, FUNC.Content> getFuncMap() {
        return funcMap;
    }

    public Map<String, Map<String, NUM>> getFuncVarMap() {
        return funcVarMap;
    }
}

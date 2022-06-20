package com.salieri.baselib.task.logotask;

import com.salieri.baselib.task.LogoTask;
import com.salieri.baselib.core.CoreManager;
import com.salieri.baselib.task.TaskSet;
import com.salieri.baselib.type.CODE;
import com.salieri.baselib.type.NAME;
import com.salieri.baselib.type.NUM;

import java.util.LinkedList;
import java.util.List;

public class FUNC implements LogoTask {
    private String name = "";
    private List<NUM> numList = new LinkedList<>();


    public FUNC(String name, List<NUM> numList) {
        this.name = name;
        this.numList.clear();
        this.numList.addAll(numList);
    }

    @Override
    public void run() {
        Content content = CoreManager.getInstance().getFuncContent(name);
        StringBuilder insertCode = new StringBuilder();
        for (int i = 0; i < content.paramList.size(); i++) {
            NAME paramName = content.paramList.get(i);
            NUM val = new NUM(0);
            if (i < numList.size()) val = numList.get(i);
            insertCode.append(paramName.value).append("=").append(val.value).append(" ");
        }
        content.code = new CODE(insertCode + content.code.value);
        new TaskSet(content.code, name).run();
        CoreManager.getInstance().removeFuncVar(name);
    }

    public static class Content {
        public CODE code;
        public List<NAME> paramList = new LinkedList<>();

        public Content(CODE code, List<NAME> paramList) {
            this.code = code;
            this.paramList.clear();
            this.paramList.addAll(paramList);
        }

        public static Content clone(Content content) {
            if (content == null) return null;
            CODE tmpCode = new CODE(content.code.value);
            List<NAME> tmpList = new LinkedList<>(content.paramList);
            return new Content(tmpCode, tmpList);
        }
    }
}

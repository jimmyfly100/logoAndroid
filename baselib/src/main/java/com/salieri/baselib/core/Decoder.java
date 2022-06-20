package com.salieri.baselib.core;

import android.text.TextUtils;

import com.salieri.baselib.task.LogoTask;
import com.salieri.baselib.task.TaskSet;
import com.salieri.baselib.task.logotask.FUNC;
import com.salieri.baselib.type.CODE;
import com.salieri.baselib.type.ENDMARK;
import com.salieri.baselib.type.NAME;
import com.salieri.baselib.type.NUM;
import com.salieri.baselib.type.TASK;
import com.salieri.baselib.type.TYPE;
import com.salieri.baselib.type.VAR;
import com.salieri.baselib.utils.ToastUtil;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * 解码工具，过程分为三步：
 * 1. 将完整的code string按照空格和换行符分段，得到一个string list
 * 2. 顺序遍历string list，将其parse成type list，这时处理变量声明和运算符表达式的计算
 * 3. 将上述type list解析成对应的函数，包括系统函数和用户自己定义的函数，校验语法通过后生成taskSet
 */
public class Decoder {
    //第一步
    private List<String> splitStrings = new LinkedList<>();
    private String field = CoreManager.MAIN_FIELD;

    //第二步
    private static final int STATE_DEFAULT = 0;
    private static final int STATE_ADD = 1;
    private static final int STATE_MINUS = 2;
    private static final int STATE_MULTIPLY = 3;
    private static final int STATE_DIVIDE = 4;
    private int operateState = STATE_DEFAULT;
    private String curAssigningVar = "";
    private boolean needValue = false; // 赋值运算是否还需要一个值
    private LinkedList<TYPE> typeList = new LinkedList<>();
    //todo: 在这一步进行函数定义的处理，在CoreManager中register func

    //第三步
    private TaskSet taskSet;
    private TASK curParsingTask = null;
    private int curParamIndex = 0;
    private Class<?>[] requiredParams;
    Constructor<?> ctor;
    private List<TYPE> paramList = new LinkedList<>();
    private boolean canDefineFunc = false; //仅在主函数最外层可以自定义函数

    private String curParsingFuncName = "";
    private List<NUM> funcParamList = new LinkedList<>();

    public Decoder(TaskSet taskSet, String field) {
        this.taskSet = taskSet;
        this.field = field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setCanDefineFunc(boolean canDefineFunc) {
        this.canDefineFunc = canDefineFunc;
    }

    public void decode(CODE code) {
        splitStrings.clear();
        typeList.clear();
        String buffer = code.value;
        buffer = preProcess(buffer);
        Collections.addAll(splitStrings, buffer.split(" |\n"));
        classify();
        try {
            taskBuild();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String preProcess(String code) {
        code = code.replace("[", " [ ");
        code = code.replace("]", " ] ");
        code = code.replace("=", " = ");
        code = code.replace("+", " + ");
        code = code.replace("-", " - ");
        code = code.replace("*", " * ");
        code = code.replace("/", " / ");
        return code;
    }

    private void classify() {
        int codeNum = 0;
        StringBuilder curCode = new StringBuilder();
        for (String item : splitStrings) {
            item = item.trim();
            if (TextUtils.isEmpty(item)) continue;
            if (operateState == STATE_DEFAULT) {
                //未处于运算
                if (item.contains("[")) {
                    if (codeNum > 0) {
                        curCode.append(item).append(" ");
                    }
                    codeNum++;
                    continue;
                } else if (item.contains("]")) {
                    codeNum--;
                    if (codeNum < 0) {
                        EngineHolder.getEngine().error("invalid input");
                        break;
                    } else if (codeNum == 0) {
                        typeList.add(new CODE(curCode.toString()));
                        curCode = new StringBuilder();
                    } else {
                        curCode.append(item).append(" ");
                    }
                    continue;
                }

                if (codeNum > 0) {
                    //在括号中，不解析
                    curCode.append(item).append(" ");
                    continue;
                }

                //是否是运算符
                if (checkIsOperator(item)) continue;
                //是否要开始赋值操作
                if (checkStartAssigning(item)) {
                    needValue = true;
                    continue;
                }

                if (!needValue) checkFinishAssigning();

                NUM num = parseNumber(item);
                if (num != null) {
                    typeList.add(num);
                    needValue = false;
                    continue;
                }

//                if (CoreManager.getInstance().isVariable(item)) {
//                    typeList.add(new VAR(item));
//                    needValue = false;
//                    continue;
//                }

                checkFinishAssigning();

                if (CoreManager.getInstance().isTask(item)) {
                    typeList.add(new TASK(CoreManager.getInstance().getTask(item)));
                    continue;
                }

                typeList.add(new NAME(item));
            } else {
                //处于运算
                NUM num = parseNumber(item);
                if (num != null) {
                    //是数字或变量
                    num = doCurOperation(num);
                }
                if (num != null) {
                    typeList.removeLast();
                    typeList.add(num);
                } else {
                    EngineHolder.getEngine().error("invalid operator syntax");
                }
                operateState = STATE_DEFAULT;
            }

        }
        checkFinishAssigning();
        typeList.add(new ENDMARK());
    }

    private void checkFinishAssigning() {
        if (!TextUtils.isEmpty(curAssigningVar)) {
            if (typeList.size() > 0 && typeList.getLast() instanceof NUM) {
                NUM value = (NUM) typeList.removeLast(); // 去除list中给变量的值
                value.varName = curAssigningVar;
                CoreManager.getInstance().registerVar(field, curAssigningVar, value);
            }
            curAssigningVar = "";
        }
    }

    private boolean checkStartAssigning(String text) {
        if (text.contains("=") && typeList.size() > 0) {
            if (typeList.getLast() instanceof NAME) {
                curAssigningVar = ((NAME) typeList.removeLast()).value;
                return true;
            }
            if (typeList.getLast() instanceof NUM) {
                NUM num = (NUM) typeList.getLast();
                if (!TextUtils.isEmpty(num.varName)) {
                    curAssigningVar = num.varName;
                    typeList.removeLast();
                    return true;
                }
            }
        }
        return false;
    }

    private NUM doCurOperation(NUM num) {
        //在运算模式下，取type表的最末尾的数字与当前处理的数字做当前处于的运算
        if (typeList.size() > 0) {
            NUM lastNum = null;
            if (typeList.getLast() instanceof NUM) {
                lastNum = (NUM) typeList.getLast();
            } else if (typeList.getLast() instanceof VAR) {
                lastNum = CoreManager.getInstance().getVarValue(field, ((VAR) typeList.getLast()).name);
            } else {
                EngineHolder.getEngine().error("invalid operator syntax");
                return null;
            }
            if (operateState == STATE_ADD) {
                double ans = lastNum.getValue() + num.getValue();
                return new NUM(ans);
            } else if (operateState == STATE_MINUS) {
                double ans = lastNum.getValue() - num.getValue();
                return new NUM(ans);
            } else if (operateState == STATE_MULTIPLY) {
                double ans = lastNum.getValue() * num.getValue();
                return new NUM(ans);
            } else if (operateState == STATE_DIVIDE) {
                double ans = lastNum.getValue() / num.getValue();
                return new NUM(ans);
            } else if (operateState == STATE_DEFAULT) {
                EngineHolder.getEngine().error("state error");
                return null;
            }
        } else {
            EngineHolder.getEngine().error("invalid operator syntax");
            return null;
        }
        return null;
    }

    private boolean checkIsOperator(String text) {
        if (text.contains("+")) {
            operateState = STATE_ADD;
            return true;
        } else if (text.contains("-")) {
            operateState = STATE_MINUS;
            return true;
        } else if (text.contains("*")) {
            operateState = STATE_MULTIPLY;
            return true;
        } else if (text.contains("/")) {
            operateState = STATE_DIVIDE;
            return true;
        } else {
            operateState = STATE_DEFAULT;
            return false;
        }
    }

    //严格是数字返回true，不校验是否是变量
    private boolean isNumber(String text) {
        try {
            float f = Float.parseFloat(text);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //数字string或变量string都解析成数字
    private NUM parseNumber(String text) {
        try {
            double f = Double.parseDouble(text);
            return new NUM(f);
        } catch (Exception e) {
            if (CoreManager.getInstance().isVariable(field, text)) {
                return CoreManager.getInstance().getVarValue(field, text);
            }
            return null;
        }
    }

    private void taskBuild() throws Exception {
        Iterator<TYPE> iterator = typeList.iterator();
        boolean isFuncDefining = false;
        NAME funcName = null;
        List<NAME> paramNameList = new LinkedList<>();
        while (iterator.hasNext()) {
            TYPE type = iterator.next();
            if (type instanceof TASK) {
                TASK task = (TASK) type;
                if (task.value == FUNC.class) {
                    isFuncDefining = true;
                    iterator.remove();

                } else {
                    isFuncDefining = false;
                    funcName = null;
                    paramNameList.clear();
                }
            } else if (isFuncDefining) {
                if (type instanceof NAME) {
                    NAME name = (NAME) type;
                    if (funcName == null) funcName = name;
                    else paramNameList.add(name);
                    iterator.remove();
                } else if (type instanceof NUM && !TextUtils.isEmpty(((NUM) type).varName)) {
                    NAME name = new NAME(((NUM) type).varName);
                    if (funcName == null) funcName = name;
                    else paramNameList.add(name);
                    iterator.remove();
                } else if (type instanceof CODE) {
                    CODE code = (CODE) type;
                    if (funcName != null) {
                        if (canDefineFunc) {
                            FUNC.Content content = new FUNC.Content(code, paramNameList);
                            CoreManager.getInstance().registerFunc(funcName.value, content);
                        } else {
                            EngineHolder.getEngine().error("Cannot define function here");
                        }
                    }
                    isFuncDefining = false;
                    funcName = null;
                    paramNameList.clear();
                    iterator.remove();
                } else {
                    isFuncDefining = false;
                    funcName = null;
                    paramNameList.clear();
                }
            }
        }



        for (TYPE type : typeList) {
            if (curParsingTask != null) {
                //正在解析函数
                boolean needContinue = true;
                Class<?> clazz = type.getClass();
                if (clazz.equals(requiredParams[curParamIndex])) {
                    paramList.add(type);
                    curParamIndex++;
                } else {
                    EngineHolder.getEngine().error("parameters mismatch with function: " + curParsingTask.value.getSimpleName());
                    clear();
                    needContinue = false;
                }
                if (requiredParams != null && curParamIndex == requiredParams.length) {
                    LogoTask task = (LogoTask) ctor.newInstance(paramList.toArray());
                    if (task instanceof TaskSet) {
                        ((TaskSet) task).setField(field);
                    }
                    taskSet.add(task);
                    clear();
                }
                if (needContinue) continue;
            }
            if (!TextUtils.isEmpty(curParsingFuncName)) {
                //正在解析自定义函数
                if (type instanceof NUM) {
                    funcParamList.add((NUM) type);
                    continue;
                } else {
                    FUNC func = new FUNC(curParsingFuncName, funcParamList);
                    taskSet.add(func);
                    clear();
                }
            }
            if (type instanceof CODE) {
                taskSet.add(new TaskSet((CODE) type));
            } else if (type instanceof TASK) {
                curParsingTask = (TASK) type;
                Constructor<?>[] constructors = curParsingTask.value.getDeclaredConstructors();
                ctor = constructors[0];
                requiredParams = ctor.getParameterTypes();
                if (requiredParams.length == 0) {
                    LogoTask task = (LogoTask) ctor.newInstance();
                    taskSet.add(task);
                    clear();
                }
            } else if (type instanceof NAME) {
                NAME name = (NAME) type;
                if (CoreManager.getInstance().isFunc(name.value)) {
                    curParsingFuncName = name.value;
                }
            }
        }
    }

    private void clear() {
        curParsingTask = null;
        curParamIndex = 0;
        paramList.clear();
        requiredParams = null;
        curParsingFuncName = "";
        funcParamList.clear();
    }

}

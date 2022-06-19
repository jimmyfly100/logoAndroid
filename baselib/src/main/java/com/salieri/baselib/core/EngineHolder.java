package com.salieri.baselib.core;

public class EngineHolder {
    private ILogoEngine engine = new DefaultEngine();
    private EngineHolder(){}
    private static EngineHolder instance = new EngineHolder();
    public static EngineHolder get() {
        return instance;
    }

    public void setEngine(ILogoEngine engine) {
        this.engine = engine;
    }

    public static ILogoEngine getEngine() {
        return instance.engine;
    }


}

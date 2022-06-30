package com.salieri.baselib.core;

import com.salieri.baselib.type.NUM;

public interface ILogoEngine {
    void FD(NUM value);
    void BK(NUM value);
    void RT(NUM value);
    void LT(NUM value);
    void error(String msg);
    void PU();
    void PD();
    void drawTurtle();
    void saveAllFunc();
    void loadAllFunc();

}
